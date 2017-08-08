/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	MemberDownErpTask.java
 * Author:   	bs.xu
 * Date:     	2016年12月14日
 * Description: 会员下行
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * bs.xu		2016年12月14日	1.0			创建
 */
package cn.htd.membercenter.service.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.util.NullValueDtoDealUtil;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.domain.MemberBankInfo;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.domain.MemberInvoiceInfo;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.middleware.common.message.erp.MemberMessage;

public class MemberDownErpTask implements IScheduleTaskDealMulti<MemberDownDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(MemberDownErpTask.class);

	@Resource
	private AmqpTemplate amqpTemplate;
	@Resource
	private MemberTaskDAO memberTaskDAO;

	@Override
	public Comparator<MemberDownDTO> getComparator() {
		return new Comparator<MemberDownDTO>() {
			@Override
			public int compare(MemberDownDTO o1, MemberDownDTO o2) {
				String id1 = o1.getMemberCode();
				String id2 = o2.getMemberCode();
				return id1.compareTo(id2);
			}

		};
	}

	/**
	 * 根据条件，查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskQueueList
	 *            当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MemberDownDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberDownErpTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<MemberDownDTO> dealDictionaryList = null;

		List<Long> ids = null;
		@SuppressWarnings("rawtypes")
		Pager pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<MemberDownDTO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				List<MemberBaseInfoDTO> memberList = memberTaskDAO.selectMemberDownErp(condition, pager,
						ErpStatusEnum.PENDING.getValue());
				int size = memberList.size();
				if (size > 0) {
					ids = new ArrayList<Long>();
					for (int i = 0; i < size; i++) {
						ids.add(memberList.get(i).getId());
					}
					List<MemberCompanyInfo> companyList = memberTaskDAO.selectCompanyListByIds(ids);
					List<MemberInvoiceInfo> invoiceList = memberTaskDAO.selectInvoiceListByIds(ids);
					List<BelongRelationshipDTO> belongRelation = memberTaskDAO.selectBelongRelationListByIds(ids);
					List<Long> sellerIds = new ArrayList<Long>();

					int belongSize = belongRelation.size();
					for (int i = 0; i < belongSize; i++) {
						sellerIds.add(belongRelation.get(i).getBelongSellerId());
					}
					List<MemberBaseInfoDTO> sellerList = null;
					if (sellerIds.size() > 0) {
						sellerList = memberTaskDAO.selectMemberBySellerIds(sellerIds);
					} else {
						sellerList = new ArrayList<MemberBaseInfoDTO>();
					}
					List<MemberConsigAddressDTO> consigList = memberTaskDAO.selectConsigAddressListByIds(ids);
					List<MemberBankInfo> bankList = memberTaskDAO.selectBankListByIds(ids);

					dealDictionaryList = getMemberDownErpList(memberList, companyList, invoiceList, belongRelation,
							sellerList, consigList, bankList);
				}

			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "MemberDownErpTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberDownErpTask-selectTasks",
					JSONObject.toJSONString(dealDictionaryList));
		}
		return dealDictionaryList;
	}

	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组，只能传递OBJECT[]
	 * 
	 * @param tasks
	 *            任务数组
	 * @param ownSign
	 *            当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(MemberDownDTO[] tasks, String ownSign) throws Exception {
		int len = tasks.length;
		MemberDownDTO memberDownDTO = null;
		for (int i = 0; i < len; i++) {
			memberDownDTO = tasks[i];
			MemberMessage msg = new MemberMessage();
			BeanUtils.copyProperties(memberDownDTO, msg);
			msg = NullValueDtoDealUtil.parseObjectToValue(msg);
			amqpTemplate.convertAndSend("membercenter-exchange-key", msg);
			String infoType = "";
			if (memberDownDTO.getIsUpdateFlag().intValue() == 0) {
				infoType = GlobalConstant.INFO_TYPE_ERP_ADD;
			} else {
				infoType = GlobalConstant.INFO_TYPE_ERP_MODIFY;
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			memberTaskDAO.updateErpStatus(Long.valueOf(memberDownDTO.getId()), ErpStatusEnum.DONE.getValue(), infoType,
					trancDate);
		}
		return false;
	}

	/**
	 * 数据转换下行到ERP数据
	 * 
	 * @param memberList
	 *            会员信息
	 * @param companyList
	 *            会员公司信息
	 * @param invoiceList
	 *            会员发票信息
	 * @param bankInfoList
	 *            会员开户行信息
	 * @param belongRelation
	 *            会员归属关系
	 * @return
	 */
	private List<MemberDownDTO> getMemberDownErpList(List<MemberBaseInfoDTO> memberList,
			List<MemberCompanyInfo> companyList, List<MemberInvoiceInfo> invoiceList,
			List<BelongRelationshipDTO> belongRelation, List<MemberBaseInfoDTO> sellerList,
			List<MemberConsigAddressDTO> consigList, List<MemberBankInfo> bankList) {
		List<MemberDownDTO> resultList = new ArrayList<MemberDownDTO>();
		MemberDownDTO resultDTO = null;
		MemberBaseInfoDTO memberBaseInfoDTO = null;
		MemberCompanyInfo memberCompanyInfo = null;
		MemberInvoiceInfo memberInvoiceInfo = null;
		BelongRelationshipDTO relation = null;
		MemberBaseInfoDTO seller = null;
		MemberConsigAddressDTO consig = null;
		MemberBankInfo bank = null;

		int size = memberList.size();
		for (int i = 0; i < size; i++) {
			resultDTO = new MemberDownDTO();
			memberBaseInfoDTO = memberList.get(i);
			resultDTO.setId(memberBaseInfoDTO.getId().toString());
			resultDTO.setMerchOrderNo(memberBaseInfoDTO.getSyncKey());

			resultDTO.setMemberCode(memberBaseInfoDTO.getMemberCode());
			resultDTO.setValidTag(memberBaseInfoDTO.getStatus());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String trancDate = format.format(memberBaseInfoDTO.getCreateTime());
			resultDTO.setOpeningDate(trancDate);
			resultDTO.setMerchantsType("1501");
			if (memberBaseInfoDTO.getIsSeller().intValue() == 1) {
				resultDTO.setNetProperty(GlobalConstant.NET_PROPERTY_SELLER);
			} else {
				resultDTO.setNetProperty(GlobalConstant.NET_PROPERTY_BUYYER);
			}
			resultDTO.setCurrency(GlobalConstant.DEFAULT_ERP_CURRENCY_TYPE);

			if (GlobalConstant.INFO_TYPE_ERP_ADD.equals(memberBaseInfoDTO.getInfoType())) {
				resultDTO.setIsUpdateFlag(GlobalConstant.ERP_ADD);
			} else {
				resultDTO.setIsUpdateFlag(GlobalConstant.ERP_MODIFY);
			}

			int comSize = companyList.size();
			for (int j = 0; j < comSize; j++) {
				memberCompanyInfo = companyList.get(j);
				if (memberCompanyInfo.getMemberId().longValue() == memberBaseInfoDTO.getId().longValue()) {
					String companyName = memberCompanyInfo.getCompanyName();
					resultDTO.setName(companyName);
					if (companyName.length() > 6) {
						resultDTO.setSingleName(memberCompanyInfo.getCompanyName().substring(0, 6));
					} else {
						resultDTO.setSingleName(memberCompanyInfo.getCompanyName().substring(0, companyName.length()));
					}
					resultDTO.setBillingAccount(memberCompanyInfo.getCompanyName());
					resultDTO.setRegisterAddress(memberCompanyInfo.getLocationDetail());
					// resultDTO.setCompanyCode(memberCompanyInfo.getCompanyCode());
					String areaCode = memberCompanyInfo.getLocationTown();
					String areaCountry = memberCompanyInfo.getLocationCounty();
					if (null != areaCode && !"".equals(areaCode)) {
						resultDTO.setAreaCode(areaCode);
					} else if (null != areaCountry && !"".equals(areaCountry)) {
						resultDTO.setAreaCode(areaCountry);
					} else {
						resultDTO.setAreaCode(GlobalConstant.DEFAULT_ERP_AREA_CODE);
					}
					resultDTO.setFaxNumber(memberCompanyInfo.getFaxNumber());
					resultDTO.setMemberFeature(memberCompanyInfo.getBuyerSellerType());
					break;
				}
			}

			int invSize = invoiceList.size();
			for (int j = 0; j < invSize; j++) {
				memberInvoiceInfo = invoiceList.get(j);
				if (memberInvoiceInfo.getMemberId().intValue() == memberBaseInfoDTO.getId().intValue()) {
					resultDTO.setInternalVATDetailAddress(memberInvoiceInfo.getInvoiceAddress());
					resultDTO.setInternalVATICellPhone(memberInvoiceInfo.getContactPhone());
					resultDTO.setDepositBank(memberInvoiceInfo.getBankName());
					resultDTO.setDepositBankNum(memberInvoiceInfo.getBankAccount());
					resultDTO.setEin(memberInvoiceInfo.getTaxManId());
					resultDTO.setRegistrar(memberInvoiceInfo.getInvoicePerson());
					// resultDTO.setBillingAccount(memberInvoiceInfo.getInvoiceNotify());
					resultDTO.setRegistrationTime(DateUtils.format(memberInvoiceInfo.getCreateTime(), "yyyy-MM-dd"));
					break;
				}

			}

			int relationSize = belongRelation.size();
			boolean hasMerchants = false;
			if (relationSize > 0) {
				for (int j = 0; j < relationSize; j++) {
					relation = belongRelation.get(j);
					if (relation.getMemberId().intValue() == memberBaseInfoDTO.getId().intValue()) {
						if (null != relation.getBuyerFeature() && !relation.getBuyerFeature().equals("")) {
							resultDTO.setMemberFeature(relation.getBuyerFeature());
						} else {
							resultDTO.setMemberFeature("51");
						}
						resultDTO.setSellerId(relation.getBelongSellerId());
						hasMerchants = true;
						break;
					}
				}
			}
			// resultDTO.setMemberFeature("51");
			if (!hasMerchants) {
				resultDTO.setMemberFeature("51");
			}

			int sellerSize = sellerList.size();
			for (int j = 0; j < sellerSize; j++) {
				seller = sellerList.get(j);
				if (null != resultDTO.getSellerId()
						&& resultDTO.getSellerId().intValue() == seller.getId().intValue()) {
					resultDTO.setSupplierCode(seller.getMemberCode());
					resultDTO.setCompanyCode(seller.getCompanyCode());
					break;
				}

			}

			int consigSize = consigList.size();
			for (int j = 0; j < consigSize; j++) {
				consig = consigList.get(j);
				if (consig.getMemberId().intValue() == memberBaseInfoDTO.getId().intValue()) {
					resultDTO.setPostCode(consig.getPostCode());
					break;
				}

			}

			int bankSize = bankList.size();
			for (int j = 0; j < bankSize; j++) {
				bank = bankList.get(j);
				if (bank.getMemberId().intValue() == memberBaseInfoDTO.getId().intValue()) {
					resultDTO.setOpeningDate(DateUtils.format(bank.getCreateTime(), "yyyy-MM-dd"));
					break;
				}

			}

			resultDTO.setSentErp(GlobalConstant.SEND_ERP);
			resultDTO.setSentPay(GlobalConstant.SEND_PAY);
			resultList.add(resultDTO);
		}
		return resultList;
	}
}
