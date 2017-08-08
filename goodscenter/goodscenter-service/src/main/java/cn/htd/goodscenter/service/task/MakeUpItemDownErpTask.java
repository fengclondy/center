package cn.htd.goodscenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.utils.SpringUtils;
import cn.htd.goodscenter.dao.ItemDraftMapper;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.middleware.common.message.erp.ProductMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 商品下行补偿定时任务
 * 
 * @author zhangxiaolong
 *
 */
public class MakeUpItemDownErpTask implements IScheduleTaskDealMulti<Item>{
	
	private static final Logger logger = LoggerFactory.getLogger(MakeUpItemDownErpTask.class);
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
    @Resource
    private ItemSpuMapper itemSpuMapper;
    @Resource
    private ItemDraftMapper itemDraftMapper;
    @Resource
   	private DictionaryUtils dictionaryUtils;
	
	@Override
	public List<Item> selectTasks(String taskParameter, String ownSign,
			int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		 //查询出待发送通知的数据
		 logger.info("MakeUpItemDownErpTask selectTasks Entrance, taskParameter : {}, ownSign : {}, taskQueueNum : {}, taskItemList : {}," +
	                "eachFetchDataNum : {}", new Object[]{JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskParameter),
	                JSONObject.toJSONString(taskQueueNum), JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		 List<Item> itemList=Lists.newArrayList();
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
	                itemList =itemMybatisDAO.queryFailedDownErpItemList(paramMap);
	            }
	       } catch (Exception e){
	            logger.error("MakeUpItemDownErpTask selectTasks error:", e);
	       }
		return itemList;
	}

	@Override
	public Comparator<Item> getComparator() {
		 return new Comparator<Item>() {
	            public int compare(Item o1, Item o2) {
	            	boolean comparaeResult=o1.getModified().before(o2.getModified());
					return comparaeResult ? 1 : 0;
	            }
	        };
	}

	@Override
	public boolean execute(Item[] itemArr, String ownSign)
			throws Exception {
		if(ArrayUtils.isEmpty(itemArr)){
			return true;
		}
		boolean executeResult=true;
		try{
			for(Item item:itemArr){
				logger.error("MakeUpItemDownErpTask:execute {}",JSON.toJSON(item));
				MQSendUtil mqSendUtil = SpringUtils.getBean(MQSendUtil.class);
		        ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(Long.valueOf(item.getItemSpuId()));
		        //查草稿
		        ItemDraft itemDraft=itemDraftMapper.selectByItemId(item.getItemId());
		        if(itemDraft!=null && 0==itemDraft.getStatus()){
		        	logger.info("ERPdoItemDownErp 存在待审核的草稿数据 {}",JSON.toJSON(itemDraft));
		        	//用草稿来覆盖
		        	if(StringUtils.isNotEmpty(itemDraft.getItemName())){
		        		item.setItemName(itemDraft.getItemName());
		        	}
		        	
		        	if(itemDraft.getCid()!=null&&itemDraft.getCid()>0){
		        		item.setCid(itemDraft.getCid());
		        	}
		        	
		        	if(itemDraft.getBrand()!=null&&itemDraft.getBrand()>0){
		        		item.setBrand(itemDraft.getBrand());
		        	}
		        	
		        	if(StringUtils.isNotEmpty(itemDraft.getModelType())){
		        		item.setModelType(itemDraft.getModelType());
		        	}
		        	
		        	if(StringUtils.isNotEmpty(dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, itemDraft.getWeightUnit()))){
		        		item.setWeightUnit(itemDraft.getWeightUnit());
		        	}
		        	
		        	if(itemDraft.getTaxRate()!=null){
		        		item.setTaxRate(itemDraft.getTaxRate());
		        	}
		        	
		        	if(StringUtils.isNotEmpty(itemDraft.getOrigin())){
		        		item.setOrigin(itemDraft.getOrigin());
		        	}
		        }
		        
		        ProductMessage productMessage = new ProductMessage();
		        if(itemSpu.getSpuCode()!=null){
		        	 productMessage.setProductCode(itemSpu.getSpuCode());
		        }
		        productMessage.setBrandCode(item.getBrand().toString());
		        productMessage.setCategoryCode(item.getErpFiveCategoryCode());
		        productMessage.setChargeUnit(item.getWeightUnit());
		        productMessage.setMarque(item.getModelType());
		        productMessage.setProductSpecifications(StringUtils.isEmpty(StringUtils.trimToEmpty(item.getItemQualification()))?
		        		"0":item.getItemQualification());
		        productMessage.setProductName(item.getItemName());
		        productMessage.setOutputRate(item.getTaxRate()==null?"":item.getTaxRate().toString());
		        productMessage.setProductColorCode("0");
		        productMessage.setProductColorName("0");
		        productMessage.setOrigin(item.getOrigin());
		        productMessage.setQualityGrade("0");
		        productMessage.setFunctionIntroduction("0");
		        productMessage.setValidTags(1);
		        productMessage.setIncomeTaxRates("0.17");
		        productMessage.setPackingContent("1");
		        productMessage.setIsUpdateFlag(1);
		        productMessage.setItemId(item.getItemId()+"");
		        logger.info("补偿商品开始下行ERP, 下行信息 : {}", net.sf.json.JSONObject.fromObject(productMessage));
		        mqSendUtil.sendToMQWithRoutingKey(productMessage,MQRoutingKeyConstant.ITEM_DOWN_ERP_ROUTING_KEY);
		        logger.info("补偿商品完成下行ERP, 下行信息 ");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("MakeUpItemDownErpTask::execute:"+e);
			executeResult=false;
		}
		return executeResult;
	}

}
