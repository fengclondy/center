/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	CompanyRelationDownErpTask.java
 * Author:   	bs.xu
 * Date:     	2016年12月18日
 * Description: 客商业务员关系下行
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * bs.xu		2016年12月18日	1.0			创建
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
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.util.NullValueDtoDealUtil;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.dto.BusinessRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.SalesmanDownDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.middleware.common.message.erp.SalesmanMessage;

public class SalesmanDownErpTask implements IScheduleTaskDealMulti<SalesmanDownDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(MemberDownErpTask.class);

	@Resource
	private AmqpTemplate amqpTemplateSalesman;
	@Resource
	private MemberTaskDAO memberTaskDAO;
	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Override
	public Comparator<SalesmanDownDTO> getComparator() {
		return new Comparator<SalesmanDownDTO>() {
			@Override
			public int compare(SalesmanDownDTO o1, SalesmanDownDTO o2) {
				Long id1 = o1.getBusinessId();
				Long id2 = o2.getBusinessId();
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
	public List<SalesmanDownDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "SalesmanDownErpTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<SalesmanDownDTO> resList = null;
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
				List<BusinessRelationshipDTO> relationList = memberTaskDAO.selectBusinessRelationDown(condition, pager,
						ErpStatusEnum.PENDING.getValue());
				int size = relationList.size();
				if (size > 0) {
					ids = new ArrayList<Long>();
					sellerIds = new ArrayList<Long>();
					for (int i = 0; i < size; i++) {
						ids.add(relationList.get(i).getBuyerId());
						sellerIds.add(relationList.get(i).getSellerId());
					}
					List<MemberBaseInfoDTO> sellerList = memberTaskDAO.selectMemberBySellerIds(sellerIds);
					List<MemberBaseInfoDTO> buyerList = memberTaskDAO.selectMemberByIds(ids);
					resList = getSalesmanDownErpList(relationList, sellerList, buyerList);
				}
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "SalesmanDownErpTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "SalesmanDownErpTask-selectTasks", JSONObject.toJSONString(resList));
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
	public boolean execute(SalesmanDownDTO[] tasks, String ownSign) throws Exception {
		int len = tasks.length;
		SalesmanDownDTO salesmanDownDTO = null;
		for (int i = 0; i < len; i++) {
			salesmanDownDTO = tasks[i];
			SalesmanMessage msg = new SalesmanMessage();
			BeanUtils.copyProperties(salesmanDownDTO, msg);
			msg = NullValueDtoDealUtil.parseObjectToValue(msg);
			amqpTemplateSalesman.convertAndSend("membercenter-salesman-exchange-key", msg);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			memberTaskDAO.updateBusinessErpStatus(salesmanDownDTO.getBusinessId(), ErpStatusEnum.DONE.getValue(),
					trancDate);
		}
		return false;
	}

	/**
	 * 组装任务返回数据
	 * 
	 * @param relationList
	 *            经营关系列表
	 * @param companyList
	 *            公司信息列表
	 * @param sellerList
	 *            供应商信息列表
	 * @return
	 */
	private List<SalesmanDownDTO> getSalesmanDownErpList(List<BusinessRelationshipDTO> relationList,
			List<MemberBaseInfoDTO> sellerList, List<MemberBaseInfoDTO> buyerList) {
		List<SalesmanDownDTO> resList = new ArrayList<SalesmanDownDTO>();
		int size = relationList.size();
		SalesmanDownDTO salesDown = null;
		BusinessRelationshipDTO relation = null;
		MemberBaseInfoDTO seller = null;
		MemberBaseInfoDTO buyer = null;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				relation = relationList.get(i);
				salesDown = new SalesmanDownDTO();
				// salesDown.setBrandCode(relation.getBrandId());
				salesDown.setBrandCode("000028");
				salesDown.setCustomerManagerCode(relation.getCustomerManagerId());
				// EmployeeDTO employeeDTO =
				// employeeService.getEmployeeInfo(relation.getCustomerManagerId());
				// String mangerName = "";
				// String manageCode = "";
				// if (null != employeeDTO) {
				// manageCode = employeeDTO.getEmpNo();
				// mangerName = employeeDTO.getName();
				// }
				salesDown.setCustomerManagerName(memberBaseInfoService.getManagerName(relation.getSellerId().toString(),
						relation.getCustomerManagerId()));
				// salesDown.setCustomerManagerCode(manageCode);
				salesDown.setFirstClassCategoryCode("50");
				// salesDown.setFirstClassCategoryCode(relation.getCategoryId().toString());
				salesDown.setIsUpdateFlag(GlobalConstant.ERP_ADD);
				salesDown.setBusinessId(relation.getBusinessId());
				salesDown.setMerchOrderNo(
						GlobalConstant.ERP_DEAL_STR + GlobalConstant.ERP_DEAL_STR_SPIL + relation.getBusinessId());

				int sellerSize = sellerList.size();
				int buyerSize = buyerList.size();

				for (int j = 0; j < sellerSize; j++) {
					seller = sellerList.get(j);
					if (seller.getId().equals(relation.getSellerId())) {
						salesDown.setSupplierCode(seller.getMemberCode());
						break;
					}
				}

				for (int j = 0; j < buyerSize; j++) {
					buyer = buyerList.get(j);
					if (buyer.getId().equals(relation.getBuyerId())) {
						salesDown.setMemberCode(buyer.getMemberCode());
						break;
					}
				}

				resList.add(salesDown);
			}
		}
		return resList;
	}
}
