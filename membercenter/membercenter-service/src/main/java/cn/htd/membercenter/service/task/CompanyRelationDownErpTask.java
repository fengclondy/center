/**

 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	CompanyRelationDownErpTask.java
 * Author:   	bs.xu
 * Date:     	2016年12月16日
 * Description: 会员单位往来关系下行
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * bs.xu		2016年12月16日	1.0			创建
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
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.util.NullValueDtoDealUtil;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.dto.CompanyRelationDownDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.middleware.common.message.erp.RelationshipMessage;

public class CompanyRelationDownErpTask implements IScheduleTaskDealMulti<CompanyRelationDownDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(MemberDownErpTask.class);

	@Resource
	private AmqpTemplate amqpTemplateCompanyRelationDown;
	@Resource
	private MemberTaskDAO memberTaskDAO;

	@Override
	public Comparator<CompanyRelationDownDTO> getComparator() {
		return new Comparator<CompanyRelationDownDTO>() {
			@Override
			public int compare(CompanyRelationDownDTO o1, CompanyRelationDownDTO o2) {
				Long id1 = o1.getBoxId();
				Long id2 = o2.getBoxId();
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
	public List<CompanyRelationDownDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "CompanyRelationDownErpTask", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<CompanyRelationDownDTO> resList = null;
		List<Long> ids;
		List<Long> sellerIds;

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
				List<BoxRelationship> boxList = memberTaskDAO.selectCompanyRelationDown(condition, pager,
						ErpStatusEnum.PENDING.getValue());
				int size = boxList.size();
				if (size > 0) {
					ids = new ArrayList<Long>();
					sellerIds = new ArrayList<Long>();
					for (int i = 0; i < size; i++) {
						ids.add(boxList.get(i).getBuyerId());
						sellerIds.add(boxList.get(i).getSellerId());
					}
					List<MemberBaseInfoDTO> buyerList = memberTaskDAO.selectMemberBaseListByIds(ids);
					List<MemberCompanyInfo> companyInfo = memberTaskDAO.selectCompanyListByIds(ids);
					List<MemberBaseInfoDTO> sellerList = memberTaskDAO.selectMemberBySellerIds(sellerIds);
					resList = getCompanyRelationDownErpList(boxList, buyerList, sellerList, companyInfo);
				}
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "CompanyRelationDownErpTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "CompanyRelationDownErpTask-selectTasks",
					JSONObject.toJSONString(resList));
		}
		return resList;
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
	public boolean execute(CompanyRelationDownDTO[] tasks, String ownSign) throws Exception {
		int len = tasks.length;
		CompanyRelationDownDTO companyRelationDownDTO = null;
		for (int i = 0; i < len; i++) {
			companyRelationDownDTO = tasks[i];
			RelationshipMessage msg = new RelationshipMessage();
			BeanUtils.copyProperties(companyRelationDownDTO, msg);
			msg = NullValueDtoDealUtil.parseObjectToValue(msg);
			amqpTemplateCompanyRelationDown.convertAndSend("membercenter-companyrelation-exchange-key", msg);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			memberTaskDAO.updateBoxErpStatus(companyRelationDownDTO.getBoxId(), ErpStatusEnum.DONE.getValue(),
					trancDate);
		}
		return false;
	}

	/**
	 * 组装下行ERP包厢关系数据
	 * 
	 * @param relationList
	 *            包厢列表
	 * @param buyerList
	 *            会员列表
	 * @param sellerList
	 *            供应商列表
	 * @param companyList
	 *            会员公司列表
	 * @return
	 */
	private List<CompanyRelationDownDTO> getCompanyRelationDownErpList(List<BoxRelationship> relationList,
			List<MemberBaseInfoDTO> buyerList, List<MemberBaseInfoDTO> sellerList,
			List<MemberCompanyInfo> companyList) {
		List<CompanyRelationDownDTO> resList = new ArrayList<CompanyRelationDownDTO>();
		int size = relationList.size();
		CompanyRelationDownDTO companyRelationDown = null;
		BoxRelationship relation = null;
		MemberCompanyInfo company = null;
		MemberBaseInfoDTO seller = null;
		MemberBaseInfoDTO buyer = null;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				relation = relationList.get(i);
				companyRelationDown = new CompanyRelationDownDTO();
				companyRelationDown.setBoxId(relation.getBoxId());
				companyRelationDown.setMerchOrderNo(
						GlobalConstant.ERP_DEAL_STR + GlobalConstant.ERP_DEAL_STR_SPIL + relation.getBoxId());
				companyRelationDown.setIsUpdateFlag(GlobalConstant.ERP_ADD);

				int buyerSize = buyerList.size();
				for (int j = 0; j < buyerSize; j++) {
					buyer = buyerList.get(j);
					if (buyer.getId().equals(relation.getBuyerId())) {
						companyRelationDown.setMemberCode(buyer.getMemberCode());
						break;
					}
				}

				int sellerSize = sellerList.size();
				for (int j = 0; j < sellerSize; j++) {
					seller = sellerList.get(j);
					if (seller.getId().equals(relation.getSellerId())) {
						companyRelationDown.setSupplierCode(seller.getMemberCode());
						break;
					}
				}

				int companySize = companyList.size();
				for (int j = 0; j < companySize; j++) {
					company = companyList.get(j);
					if (company.getMemberId().equals(relation.getBuyerId())) {
						companyRelationDown.setMemberName(company.getCompanyName());
						break;
					}
				}

				resList.add(companyRelationDown);
			}
		}
		return resList;
	}
}
