package cn.htd.goodscenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSetShelfStatusInDTO;
import cn.htd.goodscenter.service.venus.VenusItemExportService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

public class InternalSupplyItemAuoDownShelfTask implements IScheduleTaskDealMulti<ItemSkuPublishInfo> {

	private static final Logger logger = LoggerFactory.getLogger(InternalSupplyItemAuoDownShelfTask.class);
	
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	
	@Resource
	private VenusItemExportService venusItemExportService;
	
	@Override
	public List<ItemSkuPublishInfo> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList,
			int eachFetchDataNum) throws Exception {
		  logger.info("InternalSupplyItemAuoDownShelfTask selectTasks Entrance, taskParameter : {}, ownSign : {}, taskQueueNum : {}, taskItemList : {}," +
	                "eachFetchDataNum : {}", new Object[]{JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskParameter),
	                JSONObject.toJSONString(taskQueueNum), JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		    List<ItemSkuPublishInfo> itemSkuPublishInfoList = new ArrayList<ItemSkuPublishInfo>();
	        try{
	            List<String> taskIdList = new ArrayList<String>();
	            Map<String, Object> paramMap = new HashMap<>();
	            if (taskItemList != null && taskItemList.size() > 0) {
	                for (TaskItemDefine taskItem : taskItemList) {
	                    taskIdList.add(taskItem.getTaskItemId());
	                }
	                // 根据id和队列数取模，在taskIdList中则被该任务查询并执行
	                paramMap.put("taskQueueNum", taskQueueNum);
	                paramMap.put("taskIdList", taskIdList);
	                // 默认查询数量
	                paramMap.put("rows", eachFetchDataNum > 0 ? eachFetchDataNum : 1000);
	                itemSkuPublishInfoList = itemSkuPublishInfoMapper.queryWaitingAutoDownShelfItem(paramMap);
	            }
	        } catch (Exception e){
	            logger.error("InternalSupplyItemAuoDownShelfTask selectTasks error:", e);
	        }
		return itemSkuPublishInfoList;
	}

	@Override
	public Comparator<ItemSkuPublishInfo> getComparator() {
		return new Comparator<ItemSkuPublishInfo>() {
			@Override
			public int compare(ItemSkuPublishInfo itemSkuPublishInfo1, ItemSkuPublishInfo itemSkuPublishInfo2) {
				boolean comparaeResult=itemSkuPublishInfo1.getAutomaticEndtime().before(itemSkuPublishInfo2.getAutomaticEndtime());
				return comparaeResult ? 1 : 0;
			}
		};
	}

	@Override
	public boolean execute(ItemSkuPublishInfo[] itemSkuPublishInfoList,
			String ownSign) throws Exception {
		if(ArrayUtils.isEmpty(itemSkuPublishInfoList)){
			return true;
		}
		boolean result=true;
		try{
			for(ItemSkuPublishInfo itemSkuPublishInfo:itemSkuPublishInfoList){
				logger.info("InternalSupplyItemAuoDownShelfTask execute {}",JSON.toJSON(itemSkuPublishInfo));
				VenusItemSetShelfStatusInDTO venusItemSetShelfStatusInDTO=new VenusItemSetShelfStatusInDTO();
				venusItemSetShelfStatusInDTO.setBelowFloorPrice(Boolean.FALSE);
				venusItemSetShelfStatusInDTO.setOperatorId(0L);
				venusItemSetShelfStatusInDTO.setOperatorName("system");
				//下架
				venusItemSetShelfStatusInDTO.setSetStatusFlag("0");
				List<Long> skuIdList=Lists.newArrayList();
				skuIdList.add(itemSkuPublishInfo.getSkuId());
				venusItemSetShelfStatusInDTO.setSkuIdList(skuIdList);
				venusItemSetShelfStatusInDTO.setShelfType(itemSkuPublishInfo.getIsBoxFlag()==1?"1":"2");
				ExecuteResult<String> upShelfResult=venusItemExportService.txBatchSetItemSkuOnShelfStatus(venusItemSetShelfStatusInDTO);
				if(upShelfResult!=null&&!upShelfResult.isSuccess()){
					continue;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result=false;
			logger.error("InternalSupplyItemAuoDownShelfTask::execute:"+e);
		}
		
		return result;
	}

}
