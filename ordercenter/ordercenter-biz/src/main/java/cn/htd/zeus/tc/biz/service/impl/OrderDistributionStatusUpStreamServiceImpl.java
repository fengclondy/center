package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderDistributionStatusUpStreamService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.HttpUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

@Service
public class OrderDistributionStatusUpStreamServiceImpl implements OrderDistributionStatusUpStreamService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDistributionStatusUpStreamServiceImpl.class);
	
	@Autowired
	private TradeOrderErpDistributionDAO tradeOrderErpDistributionDAO;
	
	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;
	
	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;
	
	private static final int zero = 0;
	
	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;
	
	private static final String SUCCESS = "1";
	
	private static final String FAIL = "0";
	
	private static final boolean TRUE = true;
	
	private static final boolean FLASE = false;
	
	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;
	
	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;
	
	/*
	 * 监听到中间件的结果为退货时候,只需更新订单分销单表
	 * 
	 */
	@Override
	@Transactional
	public void orderDistributionStatusUpStream(String distributionId,String distributionStatus) {
		try{			
			if(MiddleWareEnum.MIDDLE_WARE_RETURN_GOODS.getCode().equals(distributionStatus)){
				updateOrderStatus(distributionId, OrderStatusEnum.RETURN_GOODS.getCode(),"中间件上行状态结果:"+OrderStatusEnum.RETURN_GOODS.getMsg());
			}else if(MiddleWareEnum.MIDDLE_WARE_REFUND.getCode().equals(distributionStatus)){
				updateOrderStatus(distributionId, OrderStatusEnum.REFUNDED.getCode(),"中间件上行状态结果:"+OrderStatusEnum.REFUNDED.getMsg());
				
			}else if(MiddleWareEnum.MIDDLE_WARE_DELIVERYED.getCode().equals(distributionStatus)){
				updateOrderStatusdeliveryed(distributionId);
			}
			TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO = new TradeOrderErpDistributionDMO();
			tradeOrderErpDistributionDMO.setErpSholesalerCode(distributionId);
			tradeOrderErpDistributionDMO.setErpSholesalerStatus(distributionStatus);
			tradeOrderErpDistributionDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrderErpDistributionDMO.setModifyName(Constant.OPERATER_NAME);
			tradeOrderErpDistributionDMO.setModifyTime(DateUtil.getSystemTime());
			tradeOrderErpDistributionDAO.updateTradeOrderErpDistributionByErpSholesalerCode(tradeOrderErpDistributionDMO);
			
			callBackMiddleware(distributionId, SUCCESS,"");
		}catch(Exception e){
	
			callBackMiddleware(distributionId, FAIL,"订单系统更新订单状态或者调用http时候发生异常");
			
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error(w.toString());
		}
	}
	
	public void callBackMiddleware(String distributionId,String result,String errormessage){
		//回调中间件,参数为成功
		Map<String,String> param = new HashMap<String,String>();
		param.put("distributionCode", distributionId);
		param.put("result", result);
		param.put("distributionStatu", result);
		String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
		String tokenResult = HttpUtil.sendGet(tokenUrl);
		String token = (String) net.sf.json.JSONObject.fromObject(tokenResult).get("data");
		param.put("token", token);
		param.put("errormessage", errormessage);
		LOGGER.info("MessageId:{} erp订单状态上行--请求中间件url:{}","",middlewareHttpUrlConfig.getOrdercenterMiddleware4StatusCallback()+"param:"+JSONObject.toJSONString(param));
		HttpUtil.sendPost(middlewareHttpUrlConfig.getOrdercenterMiddleware4StatusCallback(), param);
	}
	/*
	 *refundStatus和 orderItemStatus有且仅有一个可以为空;
	 *更新订单行表字段：退货退款状态、是否取消订单行、订单行取消时间、订单行取消人ID、订单行取消人名称、订单行取消原因、更新时间、更新人id、更新人姓名
	 *依order_no为维度，如果订单行表的“是否取消订单行”字段全部更新成已取消，则更新订单表的字段：是否是取消订单、订单取消时间、订单取消人ID、订单取消人名称、
	 *订单取消原因、更新人ID、更新人名称、更新时间
	 *@param distributionId
	 *@param refundStatus
	 *@param orderItemStatus
	 */
	private void updateOrderStatus(String distributionId,String refundStatus,String msg){
		TradeOrderErpDistributionDMO record = new TradeOrderErpDistributionDMO();
		record.setErpSholesalerCode(distributionId);
		TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO = tradeOrderErpDistributionDAO.selectTradeOrderErpDistributionByErpSholesalerCode(record);
		String orderItemNos =  "";
		String orderNo = "";
		if(null != tradeOrderErpDistributionDMO){
			orderItemNos =  tradeOrderErpDistributionDMO.getOrderItemNos();
			orderNo = tradeOrderErpDistributionDMO.getOrderNo();
		}else{
			TradeOrderItemsDMO recordJD = new TradeOrderItemsDMO();
			recordJD.setErpSholesalerCode(distributionId);
			TradeOrderItemsDMO tradeOrderItemsDMO = tradeOrderItemsDAO.selectOrderNoByErpSholesalerCodeOrderNo(recordJD);
			if(null != tradeOrderItemsDMO){				
				orderItemNos =  tradeOrderItemsDMO.getOrderItemNos();
				orderNo = tradeOrderItemsDMO.getOrderNo();
			}
			LOGGER.warn("MessageId:{} 根据erp的分销单号ErpSholesalerCode:{}","",distributionId+"查询京东订单");
		}
		//更新订单表和订单行表
		if(StringUtilHelper.isNotNull(orderItemNos,orderNo)){	
			updateTradeOrderAndItemCancle(orderItemNos,orderNo,refundStatus,msg,distributionId);
		}else{
			LOGGER.warn("MessageId:{} (退货、退款)状态上行查出的订单号orderNo:{}或者订单行号orderItemNos:{}为空","",orderNo,orderItemNos);
		}
	}
	
	/*
	 * 更新订单行表为已发货,依订单号(order_no)为维度，如果对应的订单行全部为已发货，则更新订单表为已发货
	 * @param distributionId
	 */
	public void updateOrderStatusdeliveryed(String distributionId){
		TradeOrderErpDistributionDMO record = new TradeOrderErpDistributionDMO();
		record.setErpSholesalerCode(distributionId);
		TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO = tradeOrderErpDistributionDAO.selectTradeOrderErpDistributionByErpSholesalerCode(record);
		String orderItemNos =  "";
		String orderNo = "";
		//如果从分销单表里查不到数据，说明是京东商品
		if(null != tradeOrderErpDistributionDMO){
			orderItemNos =  tradeOrderErpDistributionDMO.getOrderItemNos();
			orderNo = tradeOrderErpDistributionDMO.getOrderNo();
		}else{
			//TODO 查询订单行表,更新订单和订单行
			TradeOrderItemsDMO recordJD = new TradeOrderItemsDMO();
			recordJD.setErpSholesalerCode(distributionId);
			TradeOrderItemsDMO tradeOrderItemsDMO = tradeOrderItemsDAO.selectOrderNoByErpSholesalerCodeOrderNo(recordJD);
			if(null != tradeOrderItemsDMO){				
				orderItemNos =  tradeOrderItemsDMO.getOrderItemNos();
				orderNo = tradeOrderItemsDMO.getOrderNo();
			}
			LOGGER.warn("MessageId:{} 根据erp的分销单号ErpSholesalerCode:{}查询京东订单","",distributionId);
		}
		//更新订单表和订单行表
		LOGGER.info("MessageId:{} 分销单状态上行准备更新订单和订单行表的参数为orderItemNos:{}orderNo：{}","",orderItemNos,orderNo);
		if(StringUtilHelper.isNotNull(orderItemNos,orderNo)){			
			updateTradeOrderAndItem(orderItemNos,orderNo,distributionId);
		}else{
			LOGGER.warn("MessageId:{} (已发货)状态上行查出的订单号orderNo:{}或者订单行号orderItemNos:{}为空","",orderNo,orderItemNos);
		}
	}
	
	/*
	 * erp状态上行-退货退款 
	 */
	private void updateTradeOrderAndItemCancle(String orderItemNos,String orderNo,String refundStatus,String msg,String distributionCode){

		String[] orderItemNosArry = orderItemNos.split(",");
		for(String orderItemNosTemp : orderItemNosArry){
			//更新订单行表
			TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
			tradeOrderItemsDMO.setOrderItemNo(orderItemNosTemp);
			tradeOrderItemsDMO.setRefundStatus(refundStatus);
			tradeOrderItemsDMO.setIsCancelOrderItem(Integer.parseInt(OrderStatusEnum.CANCLED.getCode()));
			tradeOrderItemsDMO.setOrderItemCancelTime(DateUtil.getSystemTime());
			tradeOrderItemsDMO.setOrderItemCancelOperatorId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrderItemsDMO.setOrderItemCancelOperatorName(Constant.OPERATER_NAME);
			tradeOrderItemsDMO.setOrderItemCancelReason(msg);
			tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);
			tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
			tradeOrderItemsDAO.updateTradeOrderItemsByItemNo(tradeOrderItemsDMO);
		}
		
		Map<String,String> ordersItemMap = new HashMap<String,String>();
		ordersItemMap.put("orderNo", orderNo);
		long ordersItemCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
		if(ordersItemCount != zero && !"".equals(orderNo)){
			long cancleOrderItem = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoIsNotCancel(orderNo);
			if(cancleOrderItem == zero){
				//更新订单表
				TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
				tradeOrdersDMO.setOrderNo(orderNo);
				tradeOrdersDMO.setIsCancelOrder(Integer.parseInt(OrderStatusEnum.CANCLED.getCode()));
				tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
				tradeOrdersDMO.setOrderCancelMemberId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrdersDMO.setOrderCancelMemberName(Constant.OPERATER_NAME);
				tradeOrdersDMO.setOrderCancelReason(msg);
				tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
				tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
				tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
			}
		}
		//异步延迟修改订单行数据为已取消
		asyncUpdateOrderStatusToCancled(msg,distributionCode,orderNo);
	}
	
	/*
	 * erp状态上行-更新订单表和订单行表
	 */
	private void updateTradeOrderAndItem(String orderItemNos,String orderNo,String distributionCode){
		LOGGER.info("分销单状态上行 跟新订单和订单行表开始 orderItemNos:{} orderNo:{} distributionCode:{}","",orderItemNos, orderNo,distributionCode);
		String[] orderItemNosArry = orderItemNos.split(",");
		for(String orderItemNosTemp : orderItemNosArry){
			//更新订单行表
			TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
			tradeOrderItemsDMO.setOrderItemNo(orderItemNosTemp);
			tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.DELIVERYED.getCode());
			tradeOrderItemsDMO.setOuterChannelStatus(MiddleWareEnum.OUTER_CHANNEL_STATUS_WAREHOUSE.getCode());
			tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);
			tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
			tradeOrderItemsDMO.setOrderItemErrorReason("");
			tradeOrderItemsDMO.setOrderItemErrorStatus("");
			int update = tradeOrderItemsDAO.updateTradeOrderItemsByItemNo(tradeOrderItemsDMO);
			LOGGER.info("分销单状态上行 跟新订单行表结果:{}",update);
			
			//插入订单行履历表
			TradeOrderItemsStatusHistoryDMO record = new TradeOrderItemsStatusHistoryDMO();
			record.setOrderItemNo(orderItemNosTemp);
			record.setOrderItemStatus(OrderStatusEnum.DELIVERYED.getCode());
			record.setOrderItemStatusText(OrderStatusEnum.DELIVERYED.getMsg());
			tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(record);
		}
		Map<String,String> deliveryedMap = new HashMap<String,String>();
		deliveryedMap.put("orderNo", orderNo);
		deliveryedMap.put("orderItemStatus", OrderStatusEnum.DELIVERYED.getCode());
		deliveryedMap.put("isCancelOrderItem", "0");
		long deliveryedCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(deliveryedMap);
		Map<String,String> ordersItemMap = new HashMap<String,String>();
		ordersItemMap.put("orderNo", orderNo);
		ordersItemMap.put("isCancelOrderItem", "0");
		long ordersItemCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
		if(zero != deliveryedCount && zero != ordersItemCount && deliveryedCount == ordersItemCount){
			//更新订单表
			TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
			tradeOrdersDMO.setOrderNo(orderNo);
			tradeOrdersDMO.setOrderStatus(OrderStatusEnum.DELIVERYED.getCode());
			tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
			tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
			tradeOrdersDMO.setOrderErrorStatus("");
			tradeOrdersDMO.setOrderErrorReason("");
			tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
			
			//插入订单行履历表
			TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
			record.setOrderNo(orderNo);
			record.setOrderStatus(OrderStatusEnum.DELIVERYED.getCode());
			record.setOrderStatusText(OrderStatusEnum.DELIVERYED.getMsg());
			tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
		}
		//异步延迟修改订单行数据为已发货
		asyncUpdateOrderStatusToDeliveryed(ordersItemCount,distributionCode,orderNo);
	}
	
	/*
	 * 更新订单表和订单行表为已发货(JD商品)
	 * @see cn.htd.zeus.tc.biz.service.OrderDistributionStatusUpStreamService#jdOrderDistributionStatusUpStream(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void jdOrderDistributionStatusUpStream(String orderNo) {
		LOGGER.info("商品+商品状态上行开始-orderNo:{}",orderNo);
		try{	
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderNo(orderNo);
				tradeOrderItemsDMO.setChannelCode(Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
				tradeOrderItemsDMO.setOuterChannelStatus(MiddleWareEnum.OUTER_CHANNEL_STATUS_STORAGE.getCode());
				tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);
				tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
				int updateResult = tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
				if(updateResult > 0){					
					callBackMiddleware4JDGoods(orderNo, TRUE);
				}else{
				    callBackMiddleware4JDGoods(orderNo, FLASE);
			}
			
		}catch(Exception e){
			
			callBackMiddleware4JDGoods(orderNo, FLASE);
			
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error("MessageId:{} 调用方法OrderDistributionStatusUpStreamServiceImpl.jdOrderDistributionStatusUpStream出现异常:","",w.toString());
		}
	}
	
	public void callBackMiddleware4JDGoods(String orderCode,boolean flag){
		//回调中间件,参数为成功
		LOGGER.info("订单中心回调jd上行状态开始orderCode:"+orderCode+" flag:"+flag);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderCode", orderCode);
		param.put("flag", flag);
		String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
		String tokenResult = HttpUtil.sendGet(tokenUrl);
		String token = (String) net.sf.json.JSONObject.fromObject(tokenResult).get("data");
		param.put("token", token);
		String url = middlewareHttpUrlConfig.getOrdercenterMiddleware4JDStatusCallback()+"?orderCode="+orderCode+"&flag="+flag+"&token="+token;
		LOGGER.info("MessageId:{} 京东状态上行--请求中间件url:","",url);
		HttpUtil.sendGet(url);
	}
	
	
	/*
	 * 异步延迟修改订单行数据为已发货
	 */
	private void asyncUpdateOrderStatusToDeliveryed(final long ordersItemCount,
			final String distributionCode,final String orderNo) {
		try {
			new Thread(new Runnable() {
				public void run() {

					if (StringUtilHelper.isNotNull(distributionCode)) {
						String distributionId = distributionCode.substring(distributionCode
								.length() - 1);
						if (StringUtilHelper.parseNumFormat(distributionId)) {
							int sleepTime = Integer.valueOf(distributionId) % 2;
							long millis = 1000;
							if (sleepTime != 0) {
								millis = 2000;
							}
							try {
								Thread.sleep(millis);
								Map<String,String> deliveryedMap = new HashMap<String,String>();
								deliveryedMap.put("orderNo", orderNo);
								deliveryedMap.put("orderItemStatus", OrderStatusEnum.DELIVERYED.getCode());
								deliveryedMap.put("isCancelOrderItem", "0");
								long deliveryedCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(deliveryedMap);
								if(zero != deliveryedCount && zero != ordersItemCount && deliveryedCount == ordersItemCount){
									//更新订单表
									TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
									tradeOrdersDMO.setOrderNo(orderNo);
									tradeOrdersDMO.setOrderStatus(OrderStatusEnum.DELIVERYED.getCode());
									tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
									tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
									tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
									tradeOrdersDMO.setOrderErrorStatus("");
									tradeOrdersDMO.setOrderErrorReason("");
									int update = tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
									LOGGER.info("分销单状态上行 跟新订单表 结果:"+update);
									//插入订单行履历表
									TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
									record.setOrderNo(orderNo);
									record.setOrderStatus(OrderStatusEnum.DELIVERYED.getCode());
									record.setOrderStatusText(OrderStatusEnum.DELIVERYED.getMsg());
									tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
								}
							} catch (InterruptedException e) {
								StringWriter w = new StringWriter();
								e.printStackTrace(new PrintWriter(w));
								LOGGER.warn("MessageId:{} 线程休眠出现异常:{}" ,"",w.toString());
							}
						}
					}

				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{}分销单状态上行--修改订单表为已发货--出现异常-(此异常不需要回滚)" ,"", w.toString());
		}
	}
	
	/*
	 * 异步延迟修改订单行数据为已取消
	 */
	private void asyncUpdateOrderStatusToCancled(final String msg, final String distributionCode,
			final String orderNo) {

		try {
			new Thread(new Runnable() {
				public void run() {

					if (StringUtilHelper.isNotNull(distributionCode)) {
						String distributionId = distributionCode.substring(distributionCode
								.length() - 1);
						if (StringUtilHelper.parseNumFormat(distributionId)) {

							int sleepTime = Integer.valueOf(distributionId) % 2;
							long millis = 1000;
							if (sleepTime != 0) {
								millis = 2000;
							}
							try {
								Thread.sleep(millis);
								long cancleOrderItem = tradeOrderItemsDAO
										.selectTradeOrderItemsByOrderNoIsNotCancel(orderNo);
								if (cancleOrderItem == zero) {
									// 更新订单表
									TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
									tradeOrdersDMO.setOrderNo(orderNo);
									tradeOrdersDMO.setIsCancelOrder(Integer
											.parseInt(OrderStatusEnum.CANCLED.getCode()));
									tradeOrdersDMO.setOrderCancelTime(DateUtil.getSystemTime());
									tradeOrdersDMO.setOrderCancelMemberId(Long
											.parseLong(Constant.OPERATE_CODE));
									tradeOrdersDMO.setOrderCancelMemberName(Constant.OPERATER_NAME);
									tradeOrdersDMO.setOrderCancelReason(msg);
									tradeOrdersDMO.setModifyId(Long
											.parseLong(Constant.OPERATE_CODE));
									tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
									tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
									tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
								}
							} catch (InterruptedException e) {
								StringWriter w = new StringWriter();
								e.printStackTrace(new PrintWriter(w));
								LOGGER.warn("MessageId:{} 线程休眠出现异常:{}" ,"", w.toString());
							}
						}
					}

				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 分销单状态上行--修改订单表为已取消--出现异常-(此异常不需要回滚)","", w.toString());
		}

	}
}
