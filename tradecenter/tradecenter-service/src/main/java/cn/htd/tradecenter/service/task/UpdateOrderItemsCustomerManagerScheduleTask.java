package cn.htd.tradecenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.service.BelongRelationshipService;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.ExceptionUtils;
import cn.htd.tradecenter.dao.TradeOrderItemsDAO;
import cn.htd.tradecenter.domain.TraderOrderItemsCondition;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.service.impl.TradeOrderBaseService;

/**
 * 定时任务 更新订单行表的客户经理信息
 */
public class UpdateOrderItemsCustomerManagerScheduleTask implements IScheduleTaskDealMulti<TradeOrderItemsDTO> {

    protected static transient Logger logger = LoggerFactory
            .getLogger(UpdateOrderItemsCustomerManagerScheduleTask.class);

    @Resource
    private TradeOrderBaseService baseService;

    @Resource
    private TradeOrderItemsDAO orderItemsDAO;
    
    @Resource
    private BelongRelationshipService belongRelationshipService;
    
    @Resource
    private MemberBaseInfoService memberBaseInfoService;

    @Override
    public Comparator<TradeOrderItemsDTO> getComparator() {
        return new Comparator<TradeOrderItemsDTO>() {
            public int compare(TradeOrderItemsDTO o1, TradeOrderItemsDTO o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 根据条件,查询当前调度服务器可处理的任务
     *
     * @param taskParameter 任务的自定义参数
     * @param ownSign 当前环境名称
     * @param taskQueueNum 当前任务类型的任务队列数量
     * @param taskQueueList 当前调度服务器,分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<TradeOrderItemsDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateOrderItemsCustomerManagerScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        List<TradeOrderItemsDTO> dealTargetList = new ArrayList<TradeOrderItemsDTO>();
        TraderOrderItemsCondition condition = new TraderOrderItemsCondition();
        Pager<TradeOrderItemsDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();

        if (eachFetchDataNum > 0) {
            pager = new Pager<TradeOrderItemsDTO>();
            pager.setPageOffset(0);
            pager.setRows(eachFetchDataNum);
        }
        try {
            if (taskQueueList != null && taskQueueList.size() > 0) {
                for (TaskItemDefine taskItem : taskQueueList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                condition.setCancelStatus(YesNoEnum.DELETE.getValue());
                condition.setTaskQueueNum(taskQueueNum);
                condition.setTaskIdList(taskIdList);
                dealTargetList = orderItemsDAO.queryCustomerManagerOrderItems4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateOrderItemsCustomerManagerScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateOrderItemsCustomerManagerScheduleTask-selectTasks",
                    JSON.toJSONString(dealTargetList));
        }
        return dealTargetList;
    }

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
     *
     * @param tasks 任务数组
     * @param ownSign 当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(TradeOrderItemsDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateOrderItemsCustomerManagerScheduleTask-execute",
                JSON.toJSONString(tasks), "ownSign:" + ownSign);
        Map<String, DictionaryInfo> dictMap = baseService.getTradeOrderDictionaryMap();
        String buyerCode = "";
        String sellerCode = "";
        String oldBuyerCode = "";
        String oldSellerCode = "";
        Long buyerId = 0L;
        Long sellerId = 0L;
        int updateNum = 0;
        String sellerErpCode = "";
        Map<String, MemberBusinessRelationDTO> businessRelationMap = null;
        MemberBusinessRelationDTO newRelationDTO = null;
        List<MemberBusinessRelationDTO> newRelationList = null;
        List<TradeOrderItemsDTO> updateItemList = null;
        try {
            for (TradeOrderItemsDTO orderItemsDTO : tasks) {
                try {
                    buyerCode = orderItemsDTO.getBuyerCode();
                    sellerCode = orderItemsDTO.getSellerCode();
                    if (StringUtils.isEmpty(buyerCode) || "0".equals(buyerCode) || StringUtils.isEmpty(sellerCode)
                            || "0".equals(sellerCode)) {
                        continue;
                    }
                    if (StringUtils.isEmpty(oldBuyerCode)) {
                        oldBuyerCode = buyerCode;
                    }
                    if (StringUtils.isEmpty(oldSellerCode)) {
                        oldSellerCode = sellerCode;
                    }
                    if (businessRelationMap == null || !oldBuyerCode.equals(buyerCode)
                            || !oldSellerCode.equals(sellerCode)) {
                        buyerId = 0L;
                        sellerId = 0L;
                        sellerErpCode = "";
                        updateNum += updateTradeOrderItemsCustomerInfo(newRelationList, updateItemList);
                        businessRelationMap = baseService.getTradeOrderBusinessRelation(orderItemsDTO);
                        buyerId = orderItemsDTO.getBuyerId();
                        sellerId = orderItemsDTO.getSellerId();
                        sellerErpCode = orderItemsDTO.getSellerErpCode();
                        newRelationList = new ArrayList<MemberBusinessRelationDTO>();
                        updateItemList = new ArrayList<TradeOrderItemsDTO>();
                        oldBuyerCode = buyerCode;
                        oldSellerCode = sellerCode;
                    }
                    if (buyerId == null || buyerId.equals(0L) || sellerId == null || sellerId.equals(0L)
                            || StringUtils.isEmpty(sellerErpCode)) {
                        continue;
                    }
                    orderItemsDTO.setBuyerId(buyerId);
                    orderItemsDTO.setSellerId(sellerId);
                    orderItemsDTO.setSellerErpCode(sellerErpCode);
                    newRelationDTO = baseService.setCustomerManagerInfo(dictMap, orderItemsDTO, businessRelationMap);
                   //add by zhangxiaolong as use belongs rel to replace business rel start
                    ExecuteResult<BelongRelationshipDTO> belongRelationshipResult= belongRelationshipService.selectBelongRelationInfo(orderItemsDTO.getBuyerId(),
                    		orderItemsDTO.getSellerId());
                    if(belongRelationshipResult!=null&&belongRelationshipResult.isSuccess()&&belongRelationshipResult.getResult()!=null){
                    	String belCustomerManagerCode=belongRelationshipResult.getResult().getCurBelongManagerId();
                    	logger.info("belCustomerManagerCode:{}", belCustomerManagerCode);
                    	
                    	String belBelongManagerName=memberBaseInfoService.getManagerName(orderItemsDTO.getSellerId()+"", belCustomerManagerCode);
                    	
                    	logger.info("belBelongManagerName:{}", belBelongManagerName);
                    	
                    	if(StringUtils.isNotEmpty(belCustomerManagerCode)&&StringUtils.isNotEmpty(belBelongManagerName)){
                    		orderItemsDTO.setCustomerManagerCode(belCustomerManagerCode);
                    		orderItemsDTO.setCustomerManagerName(belBelongManagerName);
                    	}
                    }
                   //add by zhangxiaolong as use belongs rel to replace business rel end
                    if (newRelationDTO != null) {
                        newRelationList.add(newRelationDTO);
                    }
                    if (!StringUtils.isEmpty(orderItemsDTO.getCustomerManagerCode())) {
                        updateItemList.add(orderItemsDTO);
                    }
                } catch (TradeCenterBusinessException tcbe) {
                    logger.info("\n 方法:[{}],异常:[{}]", "UpdateOrderItemsCustomerManagerScheduleTask-execute",
                            tcbe.getMessage());
                    continue;
                } catch (Exception e) {
                    logger.error("\n 方法:[{}],异常:[{}]", "UpdateOrderItemsCustomerManagerScheduleTask-execute",
                            ExceptionUtils.getStackTraceAsString(e));
                    throw e;
                }
            }
            updateNum += updateTradeOrderItemsCustomerInfo(newRelationList, updateItemList);
        } catch (Exception e) {
            return false;
        } finally {
            logger.info("\n 方法:[{}],更新件数:[{}]", "UpdateOrderItemsCustomerManagerScheduleTask-execute", updateNum);
        }
        return true;
    }

    /**
     * 创建新的经营关系，更新订单行表的客户经理信息
     * 
     * @param newRelationList
     * @param updateItemList
     * @return
     */
    public int updateTradeOrderItemsCustomerInfo(List<MemberBusinessRelationDTO> newRelationList,
            List<TradeOrderItemsDTO> updateItemList) {
        int updateNum = 0;
        try {
            if (newRelationList != null && !newRelationList.isEmpty()) {
                baseService.createNewBusinessRelation(newRelationList);
            }
            if (updateItemList != null && !updateItemList.isEmpty()) {
                for (TradeOrderItemsDTO itemsDTO : updateItemList) {
                    if (!StringUtils.isEmpty(itemsDTO.getCustomerManagerCode())) {
                        updateNum += orderItemsDAO.updateTradeOrderItemsCustomerManagerInfo(itemsDTO);
                    }
                }
            }
        } catch (TradeCenterBusinessException tcbe) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("\n 方法:[{}],异常:[{}]",
                    "UpdateOrderItemsCustomerManagerScheduleTask-updateTradeOrderItemsCustomerInfo",
                    ExceptionUtils.getStackTraceAsString(tcbe));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("\n 方法:[{}],异常:[{}]",
                    "UpdateOrderItemsCustomerManagerScheduleTask-updateTradeOrderItemsCustomerInfo",
                    ExceptionUtils.getStackTraceAsString(e));
        }
        return updateNum;
    }
}
