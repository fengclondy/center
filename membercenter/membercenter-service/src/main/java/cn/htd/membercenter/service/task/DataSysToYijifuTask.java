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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.service.PayInfoService;

public class DataSysToYijifuTask implements IScheduleTaskDealMulti<MemberBaseInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(DataSysToYijifuTask.class);

	@Resource
	private MemberTaskDAO memberTaskDAO;

	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Autowired
	private PayInfoService payInfoService;

	@Override
	public Comparator<MemberBaseInfoDTO> getComparator() {
		return new Comparator<MemberBaseInfoDTO>() {
			@Override
			public int compare(MemberBaseInfoDTO o1, MemberBaseInfoDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
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
	public List<MemberBaseInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "DataSysToYijifuTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<MemberBaseInfoDTO> resMemberList = new ArrayList<MemberBaseInfoDTO>();
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
				List<MemberBaseInfoDTO> memberList = memberTaskDAO.selectMemberToYijifuTask(condition, pager);
				int size = memberList.size();
				if (size > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (int i = 0; i < size; i++) {
						MemberBaseInfoDTO memberBase = memberList.get(i);
						if (GlobalConstant.IS_SELLER.equals(memberBase.getBuyerSellerType())) {
							ids.add(memberBase.getId());
							resMemberList.add(memberBase);
						}
					}

					for (int i = 0; i < size; i++) {
						MemberBaseInfoDTO memberBase = memberList.get(i);
						boolean isExit = false;
						if (ids.size() > 0) {// 会员与供应商重复的去重
							for (int j = 0; j < ids.size(); j++) {
								if (memberBase.getId().intValue() == ids.get(j).intValue()) {
									isExit = true;
									break;
								}
							}
						}

						if (!isExit) {
							resMemberList.add(memberBase);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "DataSysToYijifuTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "DataSysToYijifuTask-selectTasks", JSONObject.toJSONString(resMemberList));
		}
		return resMemberList;
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
	public boolean execute(MemberBaseInfoDTO[] tasks, String ownSign) throws Exception {
		logger.info("数据一次同步数据开始,同步数据" + JSONObject.toJSONString(tasks));
		int len = tasks.length;
		MemberBaseInfoDTO memberDownDTO = null;
		for (int i = 0; i < len; i++) {
			memberDownDTO = tasks[i];
			memberTaskDAO.updateMemberCompanyInfo(memberDownDTO);
			YijifuCorporateCallBackDTO yijifuBack = downToYijifu(memberDownDTO);
			memberDownDTO.setAccountNo(yijifuBack.getAccountNo());
			memberTaskDAO.updateMemberCompanyInfo(memberDownDTO);
		}
		logger.info("数据一次同步数据结束,同步数据" + JSONObject.toJSONString(tasks));
		return false;
	}

	/**
	 * 生成支付账号
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifu(MemberBaseInfoDTO dto) {
		YijifuCorporateDTO payDto = new YijifuCorporateDTO();
		MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getId());
		MemberBaseInfoDTO baseInfo = null;
		if (GlobalConstant.IS_BUYER.equals(dto.getBuyerSellerType())) {
			baseInfo = memberBaseOperationDAO.getMemberbaseById(dto.getId(), GlobalConstant.IS_BUYER);
			payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_INDIVIDUAL);
			payDto.setLicenceNo(licenceInfo.getBuyerBusinessLicenseId());
		} else {
			baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getId(), GlobalConstant.IS_SELLER);
			payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
			payDto.setLicenceNo(licenceInfo.getBusinessLicenseId());
		}
		payDto.setComName(baseInfo.getCompanyName());
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setAddress(baseInfo.getLocationDetail());
		legalPerson.setCertNo(baseInfo.getArtificialPersonIdcard());
		legalPerson.setEmail(baseInfo.getContactEmail());
		legalPerson.setMobileNo(baseInfo.getArtificialPersonMobile().toString());
		legalPerson.setRealName(baseInfo.getArtificialPersonName());
		payDto.setLegalPerson(legalPerson);

		payDto.setEmail(baseInfo.getContactEmail());
		payDto.setOrganizationCode(licenceInfo.getOrganizationId());
		payDto.setOutUserId(baseInfo.getMemberCode());
		payDto.setTaxAuthorityNo(licenceInfo.getTaxManId());
		payDto.setMobileNo(baseInfo.getArtificialPersonMobile().toString());
		payDto.setVerifyRealName(GlobalConstant.REAL_NAME_NO);
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameSaveVerify(payDto);
		return callBack.getResult();
	}

}
