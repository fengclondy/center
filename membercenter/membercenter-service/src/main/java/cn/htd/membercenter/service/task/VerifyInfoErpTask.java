//package cn.htd.membercenter.service.task;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.fastjson.JSONObject;
//import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
//import com.taobao.pamirs.schedule.TaskItemDefine;
//
//import cn.htd.basecenter.dto.BaseToDoCountDTO;
//import cn.htd.basecenter.enums.TodoTypeEnum;
//import cn.htd.basecenter.service.BaseToDoCountService;
//import cn.htd.common.Pager;
//import cn.htd.membercenter.dao.MemberTaskDAO;
//import cn.htd.membercenter.dto.BusinessRelationshipDTO;
//import cn.htd.membercenter.dto.MemberDownCondition;
//import cn.htd.membercenter.dto.MemberDownDTO;
//import cn.htd.membercenter.dto.VerifyInfoDTO;
//
//public class VerifyInfoErpTask implements IScheduleTaskDealMulti<BaseToDoCountDTO> {
//	protected static transient Logger logger = LoggerFactory.getLogger(VerifyInfoErpTask.class);
//	@Resource
//	private AmqpTemplate amqpTemplateBaseToDoCountDTODown;
//	@Resource
//	private MemberTaskDAO memberTaskDAO;
//	@Autowired
//	private BaseToDoCountService baseToDoCountService;
//
//	@Override
//	public Comparator<BaseToDoCountDTO> getComparator() {
//		return new Comparator<BaseToDoCountDTO>() {
//			@Override
//			public int compare(BaseToDoCountDTO o1, BaseToDoCountDTO o2) {
//				Long id1 = o1.getId();
//				Long id2 = o2.getId();
//				return id1.compareTo(id2);
//			}
//		};
//	}
//
//	/**
//	 * 根据条件，查询当前调度服务器可处理的任务
//	 * 
//	 * @param taskParameter
//	 *            任务的自定义参数
//	 * @param ownSign
//	 *            当前环境名称
//	 * @param taskQueueNum
//	 *            当前任务类型的任务队列数量
//	 * @param taskQueueList
//	 *            当前调度服务器，分配到的可处理队列
//	 * @param eachFetchDataNum
//	 *            每次获取数据的数量
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public List<BaseToDoCountDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
//			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
//		logger.info("\n 方法[{}]，入参：[{}]", "VerifyInfoErpTask", JSONObject.toJSONString(taskParameter),
//				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
//				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
//		MemberDownCondition condition = new MemberDownCondition();
//		List<String> taskIdList = new ArrayList<String>();
//		List<BaseToDoCountDTO> resList = new ArrayList<BaseToDoCountDTO>();
//		try {
//			if (taskItemList != null && taskItemList.size() > 0) {
//				for (TaskItemDefine taskItem : taskItemList) {
//					taskIdList.add(taskItem.getTaskItemId());
//				}
//				condition.setTaskQueueNum(taskQueueNum);
//				condition.setTaskIdList(taskIdList);
//				// 会员注册审核
//				String modifyTypeId1 = "11";
//				List<BaseToDoCountDTO> resList1 = addBaseToDoList(modifyTypeId1, TodoTypeEnum.BUYER_REGIST.getValue(),
//						eachFetchDataNum, condition);
//				if (resList1.size() > 0) {
//					resList.addAll(resList1);
//				}
//				// 会员信息修改审核
//				String modifyTypeId2 = "15";
//				List<BaseToDoCountDTO> resList2 = addBaseToDoList(modifyTypeId2, TodoTypeEnum.BUYER_MODIFY.getValue(),
//						eachFetchDataNum, condition);
//				if (resList2.size() > 0) {
//					resList.addAll(resList2);
//				}
//				// 非会员注册审核
//				String modifyTypeId3 = "13";
//				List<BaseToDoCountDTO> resList3 = addBaseToDoList(modifyTypeId3,
//						TodoTypeEnum.NONE_BUYER_REGIST.getValue(), eachFetchDataNum, condition);
//				if (resList3.size() > 0) {
//					resList.addAll(resList3);
//				}
//				// 非会员转会员审核
//				String modifyTypeId4 = "14";
//				List<BaseToDoCountDTO> resList4 = addBaseToDoList(modifyTypeId4,
//						TodoTypeEnum.NONE2BUYER_TRANSFORM.getValue(), eachFetchDataNum, condition);
//				if (resList4.size() > 0) {
//					resList.addAll(resList4);
//				}
//				// 密码找回审核
//				String modifyTypeId5 = "1,2";
//				List<BaseToDoCountDTO> resList5 = addBaseToDoList(modifyTypeId5,
//						TodoTypeEnum.PASSWORD_RECOVERY.getValue(), eachFetchDataNum, condition);
//				if (resList5.size() > 0) {
//					resList.addAll(resList5);
//				}
//				// 手机号更改审核
//				String modifyTypeId6 = "3";
//				List<BaseToDoCountDTO> resList6 = addBaseToDoList(modifyTypeId6, TodoTypeEnum.PHONE_CHANGE.getValue(),
//						eachFetchDataNum, condition);
//				if (resList6.size() > 0) {
//					resList.addAll(resList6);
//				}
//				// 解除归属关系审核
//				String modifyTypeId7 = "25";
//				List<BaseToDoCountDTO> resList7 = addBaseToDoList(modifyTypeId7,
//						TodoTypeEnum.BELONG_RELATION_RELEASE.getValue(), eachFetchDataNum, condition);
//				if (resList7.size() > 0) {
//					resList.addAll(resList7);
//				}
//				// 会员注册供应商审核
//				String modifyTypeId8 = "12";
//				List<BaseToDoCountDTO> resList8 = addBaseToDoList(modifyTypeId8, TodoTypeEnum.BUYER_REGIST.getValue(),
//						eachFetchDataNum, condition);
//				if (resList8.size() > 0) {
//					resList.addAll(resList8);
//				}
//				// 会员待审核包厢
//				List<BusinessRelationshipDTO> BusinessRelations = memberTaskDAO.selectBusinessRelation();
//				int businessSize = BusinessRelations.size();
//				if (businessSize > 0) {
//					String types = TodoTypeEnum.BUSINESS_RELATION_VERIFY.getValue();
//					for (int i = 0; i < businessSize; i++) {
//						BaseToDoCountDTO baseToDoCountDTO = new BaseToDoCountDTO();
//						String BusinessSellerCode = BusinessRelations.get(i).getSellerCode();
//						Long sellerIdCount = BusinessRelations.get(i).getSellerIdCount();
//						baseToDoCountDTO.setCount(sellerIdCount.intValue());
//						baseToDoCountDTO.setSellerCode(BusinessSellerCode);
//						baseToDoCountDTO.setType(types);
//						if (StringUtils.isNotEmpty(BusinessSellerCode)) {
//							resList.add(baseToDoCountDTO);
//						}
//					}
//				}
//			}
//
//			baseToDoCountService.saveOrUpdateSellerToDo(resList);
//		} catch (Exception e) {
//			logger.error("\n 方法[{}]，异常：[{}]", "VerifyInfoErpTask-selectTasks", e);
//		} finally {
//			logger.info("\n 方法[{}]，出参：[{}]", "VerifyInfoErpTask-selectTasks", JSONObject.toJSONString(resList));
//		}
//		return resList;
//	}
//
//	@Override
//	public boolean execute(BaseToDoCountDTO[] tasks, String ownSign) throws Exception {
//		// int len = tasks.length;
//		// BaseToDoCountDTO baseToDoCountDTO = null;
//		// for (int i = 0; i < len; i++) {
//		// baseToDoCountDTO = tasks[i];
//		// RelationshipMessage msg = new RelationshipMessage();
//		// BeanUtils.copyProperties(baseToDoCountDTO, msg);
//		// amqpTemplateBaseToDoCountDTODown.convertAndSend(
//		// "membercenter-baseToDoCountDTO-exchange-key", msg);
//		// }
//		return true;
//	}
//
//	// 优化代码
//	public List<BaseToDoCountDTO> addBaseToDoList(String modifyTypeId, String typeCode, int eachFetchDataNum,
//			MemberDownCondition condition) {
//		List<BaseToDoCountDTO> resList = new ArrayList<BaseToDoCountDTO>();
//		@SuppressWarnings("rawtypes")
//		Pager pager = null;
//		if (eachFetchDataNum > 0) {
//			pager = new Pager<MemberDownDTO>();
//			pager.setPageOffset(0);
//			pager.setRows(eachFetchDataNum);
//		}
//		List<String> modifyTypeIdList = new ArrayList<String>();
//		if (modifyTypeId.indexOf(",") == -1) {
//			modifyTypeIdList.add(modifyTypeId);
//		} else {
//			String[] arrModifyType = modifyTypeId.split(",");
//			int arrLength = arrModifyType.length;
//			for (int i = 0; i < arrLength; i++) {
//				modifyTypeIdList.add(arrModifyType[i]);
//			}
//		}
//		List<VerifyInfoDTO> sellerIdLists = memberTaskDAO.selectBelongSellers(modifyTypeIdList);
//		int size = sellerIdLists.size();
//		if (size > 0) {
//			for (int i = 0; i < size; i++) {
//				BaseToDoCountDTO baseToDoCountDTO = new BaseToDoCountDTO();
//				Long sellerId = sellerIdLists.get(i).getBelongSellerId();
//				String sellerCode = sellerIdLists.get(i).getBelongSellerCode();
//				int modifySize = modifyTypeIdList.size();
//				int sellerIdSize = 0;
//				for (int k = 0; k < modifySize; k++) {
//					List<VerifyInfoDTO> verifyList = memberTaskDAO.selectVerifyInfoDowns(condition, pager,
//							modifyTypeIdList.get(k), sellerId);
//					sellerIdSize += verifyList.size();
//
//				}
//				if (sellerIdSize > 0) {
//					baseToDoCountDTO.setType(typeCode);
//					baseToDoCountDTO.setCount(sellerIdSize);
//					baseToDoCountDTO.setSellerCode(sellerCode);
//					if (StringUtils.isNotEmpty(sellerCode)) {
//						resList.add(baseToDoCountDTO);
//					}
//				}
//			}
//		}
//		return resList;
//	}
//}
