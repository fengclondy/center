package cn.htd.goodscenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.dao.ItemArrivalNotifyDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.ItemArrivalNotify;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.ItemDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

public class ItemArrivalNotifyTask implements IScheduleTaskDealMulti<ItemArrivalNotify> {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemArrivalNotifyTask.class);
	
	@Resource
	private ItemArrivalNotifyDAO itemArrivalNotifyDAO;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private SendSmsEmailService sendSmsEmailService;
	@Resource
	private DictionaryUtils dictionary;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	
	@Override
	public List<ItemArrivalNotify> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList,
			int eachFetchDataNum) throws Exception {
		//查询出待发送通知的数据
		 logger.info("ItemArrivalNotifyTask selectTasks Entrance, taskParameter : {}, ownSign : {}, taskQueueNum : {}, taskItemList : {}," +
	                "eachFetchDataNum : {}", new Object[]{JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskParameter),
	                JSONObject.toJSONString(taskQueueNum), JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		 List<ItemArrivalNotify> itemArrivalNotifyList = Lists.newArrayList();
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
	                itemArrivalNotifyList = itemArrivalNotifyDAO.queryWatingNotifyInfoList(paramMap);
	            }
	        } catch (Exception e){
	            logger.error("ItemArrivalNotifyTask selectTasks error:", e);
	        }
		return itemArrivalNotifyList;
	}

	@Override
	public Comparator<ItemArrivalNotify> getComparator() {
		return new Comparator<ItemArrivalNotify>() {
			@Override
			public int compare(ItemArrivalNotify o1, ItemArrivalNotify o2) {
				boolean comparaeResult=o1.getModifyTime().before(o2.getModifyTime());
				return comparaeResult ? 1 : 0;
			}
		};
	}

	@Override
	public boolean execute(ItemArrivalNotify[] itemArrivalNotifyArr, String ownSign) throws Exception {
		if(ArrayUtils.isEmpty(itemArrivalNotifyArr)){
			return true;
		}
		boolean executeResult=true;
		try{
			for(ItemArrivalNotify itemArrivalNotify:itemArrivalNotifyArr){
				logger.error("ItemArrivalNotifyTask execute:{}", JSON.toJSON(itemArrivalNotify));
				Long skuId=itemArrivalNotify.getSkuId();
				String shelfType="1".equals(itemArrivalNotify.getIsBoxFlag())?"1":"2";
				//查询库存
				ItemSkuPublishInfo itemSkuPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, shelfType, "1");
				
				if(itemSkuPublishInfo==null||itemSkuPublishInfo.getDisplayQuantity()==null){
					continue;
				}
				
				Integer displayQty=itemSkuPublishInfo.getDisplayQuantity();
				
				Integer reserveQty=itemSkuPublishInfo.getReserveQuantity()==null?0:itemSkuPublishInfo.getReserveQuantity();
				//起订量
				Integer minQty=itemSkuPublishInfo.getMimQuantity()==null ? 0 : itemSkuPublishInfo.getMimQuantity();
				
				Integer realAvaliableQty=displayQty-reserveQty-minQty;
				
				//判断是否已经满足通知的条件
				if(realAvaliableQty > 0){
					ItemDTO itemDTO=itemMybatisDAO.getItemDTOById(itemSkuPublishInfo.getItemId());
					if(itemDTO==null){
						continue;
					}
					SendSmsDTO smsDTO=new SendSmsDTO();
					List<String> parameterList=Lists.newArrayList();
					parameterList.add(itemDTO.getItemName());
					smsDTO.setParameterList(parameterList);
					//smsDTO.set("【汇通达】尊敬的客户，飞利浦彩电32PHF3050/T3已到货，您可直接登录商城：https://www.htd.cn/进行购买");
					smsDTO.setSmsType(dictionary.getValueByCode(DictionaryConst.TYPE_SMS_TEMPLATE_TYPE,
							DictionaryConst.OPT_SMS_ITEM_ARRIVAL_NOTICE));
					smsDTO.setPhone(itemArrivalNotify.getNotifyMobile()+"");
					//满足则执行通知短信发送
					ExecuteResult<String> result=sendSmsEmailService.sendSms(smsDTO);
					if(result!=null&&result.isSuccess()){
						//已通知
						itemArrivalNotify.setNotifyStatus("1");
						itemArrivalNotify.setNofityTime(new Date());
						itemArrivalNotify.setModifyTime(new Date());
						//修改状态为已发送
						itemArrivalNotifyDAO.updateItemArrivalNotifyStatus(itemArrivalNotify);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ItemArrivalNotifyTask::execute:"+e);
			executeResult=false;
		}
		return executeResult;
	}
}

