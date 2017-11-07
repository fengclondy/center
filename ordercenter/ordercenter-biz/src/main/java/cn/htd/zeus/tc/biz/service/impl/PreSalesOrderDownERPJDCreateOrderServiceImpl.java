package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.goodscenter.dto.vip.VipItemEntryInfoDTO;
import cn.htd.goodscenter.service.vip.VipItemExportService;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.zeus.tc.biz.dao.JDOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.PreSalesOrderCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.biz.service.PreSalesOrderDownERPJDCreateOrderService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.PayTypeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.mq.MQQueueFactoryConfig;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.PreSalesOrderDownDTO;
import cn.htd.zeus.tc.dto.resquest.PreSalesOrderCallBackReqDTO;

import com.alibaba.fastjson.JSONObject;

@Service
public class PreSalesOrderDownERPJDCreateOrderServiceImpl implements PreSalesOrderDownERPJDCreateOrderService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PreSalesOrderDownERPJDCreateOrderServiceImpl.class);

	@Autowired
	private JDOrderInfoDAO jdOrderInfoDAO;

	@Autowired
	private TradeOrdersDAO traderdersDAO;
	
	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;
	
	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;
	
	@Autowired
	private GoodsCenterRAO goodsCenterRAO;
	
	@Autowired
	private MemberCenterRAO memberCenterRAO;
	
	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;
	
	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;
	
	@Autowired
	private OrderStatusChangeCommonService orderStatusChangeCommonService;
	
	@Autowired
	private VipItemExportService vipItemExportService;

	@Autowired
	private AmqpTemplate item_erp_postPreorder_template;

	@Autowired
	MQQueueFactoryConfig mqQueueFactoryConfig;
	
	private static final int zero = 0;
	
	DecimalFormat df1 = new DecimalFormat("0.0000");

	/*
     * 京东抛单和预售下行-单京东待发订单信息表
     */
	@Override
	public List<JDOrderInfoDMO> selectERPOrderNOFromJDOrderInfo(Map paramMap) {
		return jdOrderInfoDAO.selectERPOrderNOFromJDOrderInfo(paramMap);
	}

	/*
     * 处理预售下行接口业务逻辑
     */
	@Override
	public void preSalesOrderDown(JDOrderInfoDMO[] tasks) {
		for(JDOrderInfoDMO requestInfo : tasks){
			PreSalesOrderDownDTO preSalesOrderDownDTO = new PreSalesOrderDownDTO();
			List<Map<String,Object>> productDetail = new ArrayList<Map<String,Object>>();
			if(null != requestInfo){
				
				String messageId =  GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
				String orderNo = orderNo = requestInfo.getOrderNo();
				LOGGER.info("预售下行订单号为 orderNo:{}预售下行生成日志流水issueLogId:{}",orderNo,messageId);
				
				String orderItemNos = requestInfo.getOrderItemNos();
				if(StringUtilHelper.isNotNull(orderNo,orderItemNos)){
					
					//校验收付款的收付款下行状态是不是为2,如果不为2 continue
					PayOrderInfoDMO record = new PayOrderInfoDMO();
		    		record.setOrderNo(orderNo);
		    		PayOrderInfoDMO payOrderInfoDMO = payOrderInfoDAO.selectPayOrderByOrderNo(record);
		    		
		    		if(null != payOrderInfoDMO && StringUtilHelper.isNotNull(payOrderInfoDMO.getPayType()) && payOrderInfoDMO.getPayType().toString().equals(PayStatusEnum.ERP_PAY.getCode())){
		    			if(StringUtilHelper.isNull(payOrderInfoDMO.getPayStatus()) || !payOrderInfoDMO.getPayStatus().equals(PayTypeEnum.SUCCESS.getCode())){
	    					LOGGER.warn("预售下行-没有从收付款待下行信息表里查到数据或者收付款支付不是SUCCESS,入参orderNo:{}",orderNo);
		    				continue;
	    				}
		    		}else if(null == payOrderInfoDMO || StringUtilHelper.isNull(payOrderInfoDMO.getPayResultStatus()) || !payOrderInfoDMO.getPayResultStatus().toString().equals(PayTypeEnum.PAY_RESULT_STATUS_SUCC.getCode())){
		    			LOGGER.warn("预售下行-没有从收付款待下行信息表里查到数据或者收付款下行状态不是回调成功,入参orderNo:{}",orderNo);
		    			continue;
		    		}
		    		
		    		Integer orderType = requestInfo.getOrderType();
					if(null != orderType && orderType.intValue() == zero){
						preSalesOrderDownDTO.setSourceId(Integer.valueOf(0));
					}else{						
						preSalesOrderDownDTO.setSourceId(Integer.valueOf(1));
					}
		    		
					//查询订单行信息表
					OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
					orderQueryParamDMO.setOrderNo(orderNo);
					orderQueryParamDMO.setOrderItemNoList(Arrays.asList(orderItemNos.split(",")));
					TradeOrdersDMO tradeOrdersDMOTemp = traderdersDAO.selectOrderByOrderNoANDOrderItemNo(orderQueryParamDMO);
					if(null != tradeOrdersDMOTemp){
						 List<TradeOrderItemsDMO> orderItemsList = tradeOrdersDMOTemp.getOrderItemsList();
						 if(null != orderItemsList){
							 for(TradeOrderItemsDMO orderItemsTemp : orderItemsList){
								 
								 if (null != orderType && orderType.intValue() == zero) {
									 Map<String,Object> preSalesOrderMap = new HashMap<String,Object>();
									 preSalesOrderMap.put("productName", orderItemsTemp.getGoodsName());
									 String price = df1.format(orderItemsTemp.getSalePrice()==null?"":orderItemsTemp.getSalePrice());
									 preSalesOrderMap.put("price", price);
									 preSalesOrderMap.put("num", orderItemsTemp.getGoodsCount()==null?"":orderItemsTemp.getGoodsCount().toString());
									 String payment = df1.format(orderItemsTemp.getOrderItemPayAmount()==null?"":orderItemsTemp.getOrderItemPayAmount());
									 preSalesOrderMap.put("payment", payment);
									 String costPrice = df1.format(orderItemsTemp.getCostPrice()==null?"":orderItemsTemp.getCostPrice());
									 preSalesOrderMap.put("costPrice", costPrice);
									 preSalesOrderMap.put("manufacturer", MiddleWareEnum.PRE_SALES_ORDER_MANUFACTURER.getCode());
									 productDetail.add(preSalesOrderMap);
								 } else {
									 LOGGER.info("预售下行--下行VIP套餐组装List参数开始orderType:{}",orderType);
									 String[] orderItemNoArray = orderItemNos.split(","); 
									 if(null != orderItemNoArray && orderItemNoArray.length >0){
										 for(int i=0;i<orderItemNoArray.length;i++ ){											 
											 String orderItemNo = orderItemNoArray[i];
											 List<TradeOrderItemsDMO>  itemList = tradeOrderItemsDAO.selectOrderItemsByOrderItemNo(orderItemNo);
											 if(null != itemList && itemList.size()>0 && null != itemList.get(0)
													 && StringUtilHelper.isNotNull(itemList.get(0).getSkuCode())){
												 
												 OtherCenterResDTO<List<VipItemEntryInfoDTO>> res = goodsCenterRAO.queryVipItemList(itemList.get(0).getSkuCode(), messageId);
												 if(ResultCodeEnum.SUCCESS.getCode().equals(res.getOtherCenterResponseCode())){
													 if(null != res && null != res.getOtherCenterResult() && res.getOtherCenterResult().size()>0){
														 for(VipItemEntryInfoDTO vipItemEntryInfoDTO : res.getOtherCenterResult()){
															 OtherCenterResDTO otherCenterResDTO = handleVIPPackage(orderNo,vipItemEntryInfoDTO,productDetail,messageId);
															 if(!ResultCodeEnum.SUCCESS.getCode().equals(otherCenterResDTO.getOtherCenterResponseCode())){
															    	continue;
															  }
														 }
													 }else{
														 LOGGER.warn("预售下行-根据SkuCode查询VIP套餐--没有查到数据,入参SkuCode:{}",itemList.get(0).getSkuCode());
											    	     continue;
													 }
												 }else{
													 LOGGER.warn("预售下行-根据SkuCode查询VIP套餐--没有查到数据或者查询异常,入参SkuCode:{}",itemList.get(0).getSkuCode());
										    	     continue;
												 }
												
											 }
										 }
									 }
									 
								 }
								 
								 updateJDorderinfo(orderNo,orderItemsTemp,messageId);
							 }
						 }
					}
					preSalesOrderDownDTO.setSupplierCode(tradeOrdersDMOTemp.getSellerCode());
					preSalesOrderDownDTO.setOrderNo(tradeOrdersDMOTemp.getOrderNo());
					preSalesOrderDownDTO.setMasterOrderNo(tradeOrdersDMOTemp.getOrderNo());
					preSalesOrderDownDTO.setCustomerName(tradeOrdersDMOTemp.getBuyerName());
					preSalesOrderDownDTO.setPaymentMethod(tradeOrdersDMOTemp.getPayType());
					preSalesOrderDownDTO.setSaleType(tradeOrdersDMOTemp.getSalesType());
					
					String consigneeAddressTown = tradeOrdersDMOTemp.getConsigneeAddressTown();
					String serviceArea = "";
					if(StringUtilHelper.isNull(consigneeAddressTown)){
						serviceArea = tradeOrdersDMOTemp.getConsigneeAddressDistrict();
					}else{
						serviceArea = consigneeAddressTown;
					}
					preSalesOrderDownDTO.setServiceArea(serviceArea);
					preSalesOrderDownDTO.setRecieverAddress(tradeOrdersDMOTemp.getConsigneeAddress());
					preSalesOrderDownDTO.setRecieverPhone(tradeOrdersDMOTemp.getConsigneePhoneNum());
					preSalesOrderDownDTO.setRecieverName(tradeOrdersDMOTemp.getConsigneeName());
					preSalesOrderDownDTO.setDeliveryNote(tradeOrdersDMOTemp.getBuyerRemarks());
					preSalesOrderDownDTO.setProductDetail(productDetail);

	    			LOGGER.info("预售下行-准备往中间件发送MQ信息为:{}",JSONUtil.toJSONString(preSalesOrderDownDTO));
	    			item_erp_postPreorder_template.convertAndSend(mqQueueFactoryConfig.getMiddlewareErpPostPreorder(),JSONUtil.toJSONString(preSalesOrderDownDTO));
	    			LOGGER.info("成功发送mq---- 预售下行对列名:{}",mqQueueFactoryConfig.getMiddlewareErpPostPreorder());
	    			//直接更新订单表
	    			changeOrderStatus(orderNo,orderItemNos, OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode(), 
	    					OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getMsg(),true,"","");
				}
			}
		}
	}
	
	/*
	 * 处理vip套餐组装参数
	 */
	private OtherCenterResDTO handleVIPPackage(String orderNo,VipItemEntryInfoDTO vipItemEntryInfoDTO,List<Map<String,Object>> productDetail,String messageId){
		OtherCenterResDTO otherCenterResDTO = new OtherCenterResDTO();
		try{
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			
			Map<String,Object> preSalesOrderMap = new HashMap<String,Object>();
			 preSalesOrderMap.put("productName", vipItemEntryInfoDTO.getItemName());
			 preSalesOrderMap.put("price", vipItemEntryInfoDTO.getSalePrice());
			 preSalesOrderMap.put("num", vipItemEntryInfoDTO.getProductNumber());
			 preSalesOrderMap.put("payment", vipItemEntryInfoDTO.getProductAmount());
			 preSalesOrderMap.put("costPrice", vipItemEntryInfoDTO.getBasePrice());
			 if(StringUtilHelper.isNotNull(vipItemEntryInfoDTO.getSupplierName())){
				 preSalesOrderMap.put("manufacturer", vipItemEntryInfoDTO.getSupplierName());
			 }else{
				 TradeOrdersDMO tradeOrdersDMO = traderdersDAO.selectOrderByOrderNo(orderNo);
				 OtherCenterResDTO<Long> memberInfo = memberCenterRAO.getMemberIdByCode(tradeOrdersDMO.getBuyerCode(), messageId);
				    if(!ResultCodeEnum.SUCCESS.getCode().equals(memberInfo.getOtherCenterResponseCode())){
				    	otherCenterResDTO.setOtherCenterResponseCode(memberInfo.getOtherCenterResponseCode());
				    	otherCenterResDTO.setOtherCenterResponseMsg(memberInfo.getOtherCenterResponseMsg());
						return otherCenterResDTO;
					}
				    Long memberId = memberInfo.getOtherCenterResult();
				    
				    OtherCenterResDTO<MemberDetailInfo> memDetailRes =  memberCenterRAO.getMemberDetailById(memberId, messageId);
				    if(!ResultCodeEnum.SUCCESS.getCode().equals(memDetailRes.getOtherCenterResponseCode())){
				    	otherCenterResDTO.setOtherCenterResponseCode(memDetailRes.getOtherCenterResponseCode());
				    	otherCenterResDTO.setOtherCenterResponseMsg(memDetailRes.getOtherCenterResponseMsg());
						return otherCenterResDTO;
				    }
				    Long sellerId = memDetailRes.getOtherCenterResult().getMemberBaseInfoDTO().getCurBelongSellerId();
				    
				    OtherCenterResDTO<MemberBaseInfoDTO> memSellerRes = memberCenterRAO.getMemberDetailBySellerId(sellerId, messageId);
				    if(!ResultCodeEnum.SUCCESS.getCode().equals(memSellerRes.getOtherCenterResponseCode())){
				    	otherCenterResDTO.setOtherCenterResponseCode(memSellerRes.getOtherCenterResponseCode());
				    	otherCenterResDTO.setOtherCenterResponseMsg(memSellerRes.getOtherCenterResponseMsg());
						return otherCenterResDTO;
				    }
				    String companyName =  memSellerRes.getOtherCenterResult().getCompanyName();
				    preSalesOrderMap.put("manufacturer", companyName);
			 }
			 productDetail.add(preSalesOrderMap);
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法PreSalesOrderDownERPJDCreateOrderServiceImpl.handleVIPPackage出现异常:{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		
		 
		 return otherCenterResDTO;
	}
	
	private void changeOrderStatus(final String orderNo,final String orderItems,final String status,final String statusText,final boolean directUpdateOrder,
			final String orderErrorStatus,final String orderErrorReason){
		try{
			new Thread(new Runnable(){
				public void run(){
					
					orderStatusChangeCommonService.orderStatusChange(orderNo, orderItems, status, statusText,directUpdateOrder,orderErrorStatus,
							orderErrorReason);
				}
			}).start();
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("预售下行 - 修改订单和订单行表装填出现异常-(此异常不需要回滚)");
		}
	}

	/*
	 * 更新京东待发订单信息表
	 */
	public void updateJDorderinfo(final String orderNo,final TradeOrderItemsDMO orderItem,final String messageId) {

		try{
			new Thread(new Runnable(){
				public void run(){
					JDOrderInfoDMO jdOrderInfoDMO = new JDOrderInfoDMO();
					jdOrderInfoDMO.setErpLastMessageId(messageId);
					jdOrderInfoDMO.setErpLastSendTime(DateUtil.getSystemTime());
					jdOrderInfoDMO.setErpResultStatus(Integer.valueOf(OrderStatusEnum.ERP_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN.getCode()));
					jdOrderInfoDMO.setOrderNo(orderNo);
					jdOrderInfoDMO.setOrderItemNo(orderItem.getOrderItemNo());
					int update = jdOrderInfoDAO.updateERPJDInfo(jdOrderInfoDMO);
					LOGGER.info("MessageId:{} 预售下行---更新TB_B_JDORDERINFO结果:{}",messageId,update);
						}
		}).start();
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 预售下行---更新TB_B_JDORDERINFO出现异常",messageId,w.toString());
		}
	}
	
	/*
     * 预售下行回调接口
     */
	@Override
	@Transactional
	public PreSalesOrderCallBackDMO executeJDCreateOrderCallBack(
			PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO) {
		LOGGER.info("预售回调参数:{}",JSONObject.toJSONString(preSalesOrderCallBackReqDTO));
		PreSalesOrderCallBackDMO preSalesOrderCallBackDMO = new PreSalesOrderCallBackDMO();
		preSalesOrderCallBackDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		preSalesOrderCallBackDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		try{
			JDOrderInfoDMO record = new JDOrderInfoDMO();
			String orderNo = preSalesOrderCallBackReqDTO.getOrderNo();
			String erpResultStatus = preSalesOrderCallBackReqDTO.getErpResultStatus().toString();
			record.setOrderNo(orderNo);
			record.setErpResultStatus(Integer.valueOf(erpResultStatus));
			record.setErpResultCode(preSalesOrderCallBackReqDTO.getErpResultCode());
			String erpResultMsg = preSalesOrderCallBackReqDTO.getErpResultMsg()==null?"":preSalesOrderCallBackReqDTO.getErpResultMsg();
			if(erpResultMsg.length()>64){
				erpResultMsg = erpResultMsg.substring(0, 64);
			}
			record.setErpResultMsg(erpResultMsg);
			jdOrderInfoDAO.updateJDOrderInfoByOrderNo(record);
			
			if(erpResultStatus.equals(MiddleWareEnum.MIDDLE_WARE_ERPRESULT_STATUS_SUCCESS.getCode())){				
				//更新订单行-传入参数为订单号和渠道编码(3010)
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderNo(orderNo);
				tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
				tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
				tradeOrderItemsDMO.setErpSholesalerCode(preSalesOrderCallBackReqDTO.getSaleNo());
				tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
				
				//插入订单行履历表
				TradeOrderItemsStatusHistoryDMO recordStatus = new TradeOrderItemsStatusHistoryDMO();
				recordStatus.setOrderItemStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
				recordStatus.setOrderItemStatusText(OrderStatusEnum.PRE_DELIVERY.getMsg());
				
				TradeOrderItemsDMO recordTemp = new TradeOrderItemsDMO();
				recordTemp.setOrderNo(orderNo);
				TradeOrderItemsDMO recordTempRes = tradeOrderItemsDAO.selectOrderNoByErpSholesalerCodeOrderNo(recordTemp);
				if(null != recordTempRes){					
					String[] orderItemArray = recordTempRes.getOrderItemNos().split(",");
					for(String orderItemNosTemp : orderItemArray){
						recordStatus.setOrderItemNo(orderItemNosTemp);
						tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(recordStatus);
					}
				}
				
				Map<String,String> ordersItemMap = new HashMap<String,String>();
				ordersItemMap.put("orderNo", orderNo);
				ordersItemMap.put("isCancelOrderItem", "0");
				long ordersItemCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
				if(ordersItemCount != zero){
					Map<String,String> ordersItemStatusMap = new HashMap<String,String>();
					ordersItemStatusMap.put("orderNo", orderNo);
					ordersItemStatusMap.put("orderItemStatus", OrderStatusEnum.PRE_DELIVERY.getCode());
					ordersItemStatusMap.put("isCancelOrderItem", "0");
					long ordersItemStatusCount = tradeOrderItemsDAO.selectTradeOrderItemsByOrderNoOrStatus(ordersItemStatusMap);
					if(ordersItemCount == ordersItemStatusCount){
						//更新订单表为待发货
						TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
						tradeOrdersDMO.setOrderStatus( OrderStatusEnum.PRE_DELIVERY.getCode());
						tradeOrdersDMO.setOrderNo(orderNo);
						tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
						tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
						tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
						traderdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
						
						//插入订单行履历表
						TradeOrderStatusHistoryDMO recordHistory = new TradeOrderStatusHistoryDMO();
						recordHistory.setOrderNo(orderNo);
						recordHistory.setOrderStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
						recordHistory.setOrderStatusText(OrderStatusEnum.PRE_DELIVERY.getMsg());
						tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(recordHistory);
					}
				}
			}else{
				TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
				tradeOrderItemsDMO.setOrderNo(orderNo);
				TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
				tradeOrdersDMO.setOrderNo(orderNo);
				handleOrderAndItemParam(tradeOrderItemsDMO, tradeOrdersDMO, preSalesOrderCallBackReqDTO);
			}
			
			
		}catch(Exception e){
			preSalesOrderCallBackDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			preSalesOrderCallBackDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error("MessageId:{} 预售下行回调出现异常:{}","",w.toString());
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return preSalesOrderCallBackDMO;
	}
	
	/*
	 * 预售下行回调--erp处理失败的情况
	 */
	private void handleOrderAndItemParam(TradeOrderItemsDMO tradeOrderItemsDMO,TradeOrdersDMO tradeOrdersDMO,PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO){
		tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
		tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
		tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);
		tradeOrderItemsDMO.setOrderItemErrorStatus(OrderStatusEnum.PRE_SALES_CALL_BACK_FAIL.getCode());
		tradeOrderItemsDMO.setOrderItemErrorTime(DateUtil.getSystemTime());
		tradeOrderItemsDMO.setOrderItemErrorReason(preSalesOrderCallBackReqDTO.getErpResultMsg());
		tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
		tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
		
		tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
		tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
		tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
		tradeOrdersDMO.setOrderErrorStatus(OrderStatusEnum.PRE_SALES_CALL_BACK_FAIL.getCode());
		tradeOrdersDMO.setOrderErrorTime(DateUtil.getSystemTime());
		tradeOrdersDMO.setOrderErrorReason(preSalesOrderCallBackReqDTO.getErpResultMsg());
		traderdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
	}
}
