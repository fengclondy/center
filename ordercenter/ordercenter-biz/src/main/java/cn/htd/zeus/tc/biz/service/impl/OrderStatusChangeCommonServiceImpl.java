package cn.htd.zeus.tc.biz.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

@Service
public class OrderStatusChangeCommonServiceImpl implements OrderStatusChangeCommonService {

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;

	private static final int zero = 0;

	/*
	 * 订单状态变化修改-公共方法
	 * 
	 * @param orderItems 按照逗号分开
	 * @param directUpdateOrder 如果为true直接更新订单表，否则更新完订单行再更新订单表
	 * @param isOrderError 是不是有异常订单,如果为true就更新订单和订单行的errorStatus，如果false就将orderErrorStatus、orderErrorReason更新成空
	 * 有异常订单的时候不需要更新订单状态
	 * 
	 * 如果只更新异常订单 directUpdateOrder:true   isOrderError:true  status:""  statusText:""  orderErrorStatus:有值  orderErrorReason:有值
	 * 如果只更新订单状态 directUpdateOrder:true|false   isOrderError:false  status:有值   statusText：有值  orderErrorStatus:""  orderErrorReason:""
	 */
	@Override
	public void orderStatusChange(String orderNo, String orderItems,
			String status, String statusText, boolean directUpdateOrder,String orderErrorStatus,String orderErrorReason) {
		boolean isOrderError = false;
		if(StringUtilHelper.isNotNull(orderErrorStatus)){
			isOrderError = true;
		}
		
		// 更新订单行表
		TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
		tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
		tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
		tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);

		// 插入订单行履历表
		TradeOrderItemsStatusHistoryDMO recordItem = new TradeOrderItemsStatusHistoryDMO();
		recordItem.setOrderItemStatus(status);
		recordItem.setOrderItemStatusText(statusText);
		String[] orderItemNoArray = orderItems.split(",");
		if (null != orderItemNoArray && orderItemNoArray.length > 0) {
			for (int i = 0; i < orderItemNoArray.length; i++) {
				String orderItemNo = orderItemNoArray[i];
				//订单行有异常 更新异常字段
				if(isOrderError){
					tradeOrderItemsDMO.setOrderItemErrorStatus(orderErrorStatus);
					tradeOrderItemsDMO.setOrderItemErrorReason(orderErrorReason);
					tradeOrderItemsDMO.setOrderItemErrorTime(DateUtil.getSystemTime());
				}else{//订单行没有异常 更新订单行状态，且更新异常状态为空
					tradeOrderItemsDMO.setOrderItemErrorStatus("");
					tradeOrderItemsDMO.setOrderItemErrorReason("");
					tradeOrderItemsDMO.setOrderItemStatus(status);
					recordItem.setOrderItemNo(orderItemNo);
					tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(recordItem);
				}
				tradeOrderItemsDMO.setOrderItemNo(orderItemNo);
				tradeOrderItemsDAO.updateTradeOrderItemsByItemNo(tradeOrderItemsDMO);

				
			}
		}

		if (directUpdateOrder) {
			updateOrder(status, orderNo, statusText,orderErrorStatus,orderErrorReason,isOrderError);
		} else {

			Map<String, String> ordersItemMap = new HashMap<String, String>();
			ordersItemMap.put("orderNo", orderNo);
			ordersItemMap.put("isCancelOrderItem", "0");
			long ordersItemCount = tradeOrderItemsDAO
					.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
			if (ordersItemCount != zero) {
				Map<String, String> ordersItemStatusMap = new HashMap<String, String>();
				ordersItemStatusMap.put("orderNo", orderNo);
				ordersItemStatusMap.put("isCancelOrderItem", "0");
				ordersItemStatusMap.put("orderItemStatus", status);
				long ordersItemStatusCount = tradeOrderItemsDAO
						.selectTradeOrderItemsByOrderNoOrStatus(ordersItemStatusMap);
				if (ordersItemCount == ordersItemStatusCount) {
					updateOrder(status, orderNo, statusText,orderErrorStatus,orderErrorReason,isOrderError);
				}
			}

		}
	}

	public void updateOrder(final String status, final String orderNo, final String statusText,String orderErrorStatus,String orderErrorReason,
			boolean isOrderError) {
		// 更新订单表
		TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
		tradeOrdersDMO.setOrderNo(orderNo);
		tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
		tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
		tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
		
		//订单行有异常 更新异常字段
		if(isOrderError){
			tradeOrdersDMO.setOrderErrorStatus(orderErrorStatus);
			tradeOrdersDMO.setOrderErrorReason(orderErrorReason);
			tradeOrdersDMO.setOrderErrorTime(DateUtil.getSystemTime());
		}else{
			tradeOrdersDMO.setOrderErrorStatus("");
			tradeOrdersDMO.setOrderErrorReason("");
			tradeOrdersDMO.setOrderStatus(status);
			
			// 插入订单行履历表
			TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
			record.setOrderNo(orderNo);
			record.setOrderStatus(status);
			record.setOrderStatusText(statusText);
			tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
		}
		tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);

		
	}

	/**
	 * 对订单状态、异常展示与否、vms支付完成与否 赋值
	 * 此方法是赋值，不是更新，入参必订单号必须不为空
	 * @param tradeOrdersDMO
	 */
	@Override
	public void updateOrderErrorVMSPayStatus(TradeOrdersDMO tradeOrdersDMO) {
		if(null != tradeOrdersDMO && StringUtilHelper.isNotNull(tradeOrdersDMO.getOrderNo())){
			List<TradeOrderItemsDMO> itemDmo = tradeOrderItemsDAO.selectOrderItemsByOrderNo(tradeOrdersDMO.getOrderNo());
			if(null == itemDmo || itemDmo.size() ==0){
				tradeOrdersDMO.setIsCancelOrder(Integer.valueOf(OrderStatusEnum.CANCLED.getCode()));
				return;
			}
			
			Map<String,String> itemStatusMap = new HashMap<String,String>();
			Map<String,String> itemErrorStatusMap = new HashMap<String,String>();
			int minItemStatus = 70;
			for(TradeOrderItemsDMO tempItemDmo : itemDmo){
				if(StringUtilHelper.isNotNull(tempItemDmo.getOrderItemStatus())
						&& tempItemDmo.getOrderItemStatus().length()>1){
					String orderItemStatus = tempItemDmo.getOrderItemStatus();
					if(orderItemStatus.equals(OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode()) 
							|| orderItemStatus.equals(OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode())){
						itemStatusMap.put(orderItemStatus, orderItemStatus);
					}
					String orderItemErrorStatus = tempItemDmo.getOrderItemErrorStatus();
					if(StringUtilHelper.isNotNull(orderItemErrorStatus)){
						itemErrorStatusMap.put(orderItemErrorStatus, tempItemDmo.getOrderItemErrorReason());
					}
					int tempStatus = Integer.parseInt(orderItemStatus.substring(0,2));
					if(tempStatus - minItemStatus < 0){
						minItemStatus = tempStatus;
					}
				}
			}
			
			if(minItemStatus == 31){
				if(StringUtilHelper.isNotNull(itemStatusMap.get(OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode()))){
					minItemStatus = Integer.valueOf(itemStatusMap.get(OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode()));
				}else if(StringUtilHelper.isNotNull(itemStatusMap.get(OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode()))){
					minItemStatus = Integer.valueOf(itemStatusMap.get(OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getCode()));
				}else{
					return;
				}
			}else if(minItemStatus >= 40){//这段代码为了vms开单而判断
				tradeOrdersDMO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
			}
			if(!itemErrorStatusMap.isEmpty()){
				Entry<String,String> entry = (Entry<String,String>)itemErrorStatusMap.entrySet().iterator().next();
				tradeOrdersDMO.setOrderErrorStatus(entry.getKey());
				tradeOrdersDMO.setOrderErrorReason(entry.getValue());
			}else{
				tradeOrdersDMO.setOrderErrorStatus("");
				tradeOrdersDMO.setOrderErrorReason("");
			}
			tradeOrdersDMO.setOrderStatus(String.valueOf(minItemStatus));
		}
	}
}
