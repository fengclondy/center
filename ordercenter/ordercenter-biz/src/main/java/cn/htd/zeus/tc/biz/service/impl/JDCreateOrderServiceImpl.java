package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.zeus.tc.biz.dao.JDOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.JDCreateOrderCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCreateInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.service.JDCreateOrderService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.GoodCenterEnum;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.mq.MQQueueFactoryConfig;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.HttpClientCommon;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.JDResDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderCallBackReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderSkuReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

@Service
public class JDCreateOrderServiceImpl implements JDCreateOrderService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JDCreateOrderServiceImpl.class);
	
	@Autowired
	private JDOrderInfoDAO jdOrderInfoDAO;
	
	@Autowired
	private TradeOrdersDAO traderdersDAO;
	
	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;
	
	@Autowired
	MQQueueFactoryConfig mqQueueFactoryConfig;
	
	@Autowired
	private MemberCenterRAO memberCenterRAO;
	
	@Autowired
	private AmqpTemplate item_jd_submitOrder_template;
	
	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;
	
	@Override
	public List<JDOrderInfoDMO> selectJDOrderNOFromJDOrderInfo(Map paramMap) {
		return jdOrderInfoDAO.selectJDOrderNOFromJDOrderInfo(paramMap);
	}
	/*
	 *  京东确认预占库存接口
	 * @see cn.htd.zeus.tc.biz.service.JDCreateOrderService#jdCreateOrder(cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO[])
	 */
	@Override
	@Transactional
	public void jdSureCreateOrder(JDOrderInfoDMO[] tasks) {
		for(JDOrderInfoDMO requestInfo : tasks){
			JDCreateOrderReqDTO jdCreateOrderReqDTO = new JDCreateOrderReqDTO();
			List<JDCreateOrderSkuReqDTO> skuList = new ArrayList<JDCreateOrderSkuReqDTO>();
			if(null != requestInfo){
				String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
    			LOGGER.info("MessageId:{} 京东确认预占库存(京东确认订单) - 生成日志流水issueLogId:{}",messageId,messageId);
    			
				String orderNo = requestInfo.getOrderNo();
				String orderItemNos = requestInfo.getOrderItemNos();
				String jdorderNo = requestInfo.getJdorderNo();
				if(StringUtilHelper.isNotNull(orderNo,orderItemNos,jdorderNo)){

					String urlToken = middlewareHttpUrlConfig.getOrdercenterMiddleware4token(); 
					String tokenRes="";
					String token = "";
					String orderJson = JSONObject.toJSONString(jdCreateOrderReqDTO);
					
					try {
						tokenRes = HttpClientCommon.httpGet(urlToken);
						LOGGER.info("http请求中间件token返回结果:"+tokenRes);
						JSONObject tokenJson = (JSONObject)JSONObject.parseObject(tokenRes, JSONObject.class);
						token = tokenJson.get("data").toString();
						
						String url =  middlewareHttpUrlConfig.getOrdercenterMiddleware4JDConfirmCreateOrder()+"?jdOrderId="+jdorderNo+"&token="+token;
						LOGGER.info("京东确认预占库存url:{}",url);
						String httpRes = HttpClientCommon.httpGet(url);
						LOGGER.info("京东确认预占库存(京东确认订单) ---http请求返回结果:{}",httpRes);
					   
						JDResDTO jdResDTO = new JDResDTO();
					    if(StringUtilHelper.allIsNotNull(httpRes)){
						   JSONObject jsonObj = (JSONObject)JSONObject.parse(httpRes);
						   jdResDTO = JSONObject.toJavaObject(jsonObj, JDResDTO.class);
						   if(null != jdResDTO.getData()){							   
							   updateJDorderinfo(jdorderNo,orderNo,orderItemNos,jdResDTO,messageId);
						   }
					    }
					} catch (Exception e1) {
						 StringWriter w = new StringWriter();
						 e1.printStackTrace(new PrintWriter(w));
						LOGGER.error(
								"MessageId:{} 京东预占库存(京东确认订单)接口---http请求中间件出现异常:{}",
								"", w.toString());
					}
				
				     LOGGER.info("成功发送京东预占库存(京东确认订单)接口");
				}
		}
	  }
	}
	
	/*
	 * 更新京东待发订单信息表
	 */
	public void updateJDorderinfo(final String jdorderNo,final String orderNo,final String orderItemNos,final JDResDTO jdResDTO,final String messageId) {

		try{
			new Thread(new Runnable(){
				public void run(){
					JDOrderInfoDMO jdOrderInfoDMO = new JDOrderInfoDMO();
					jdOrderInfoDMO.setJdLastEssageId(messageId);
					jdOrderInfoDMO.setJdLastSendTime(DateUtil.getSystemTime());
					boolean data = (boolean)jdResDTO.getData();
					if(data){						
						jdOrderInfoDMO.setJdResultStatus(Integer.valueOf(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_SUC.getCode()));
					}else{
						jdOrderInfoDMO.setJdResultStatus(Integer.valueOf(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_FAIL.getCode()));
					}
					jdOrderInfoDMO.setJdorderNo(jdorderNo);
					String code = jdResDTO.getCode();
					if(StringUtilHelper.isNotNull(code)){
						jdOrderInfoDMO.setJdResultCode(code);
					}
					String msg = jdResDTO.getMsg();
					if(StringUtilHelper.isNotNull(msg)){
						jdOrderInfoDMO.setJdResultMsg(msg);
					}
					int update = jdOrderInfoDAO.updateJDInfo(jdOrderInfoDMO);
					LOGGER.info("京东预占库存(京东确认订单)接口---更新TB_B_JDORDERINFO结果：{}",update);
					
					//如果JdResultStatus返回2(成功),更新字段outer_channel_puchase_status:2
					//如果JdResultStatus返回3(失败),先查询outer_channel_puchase_status是否是2，如果不是更新订单和订单行表的order_error_status和order_item_error_status
					String[] orderItemNoArray= orderItemNos.split(",");
					if(jdOrderInfoDMO.getJdResultStatus().equals(Integer.valueOf(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_SUC.getCode()))){
						    LOGGER.info("京东预占库存(京东确认订单)回调结果:成功");	
						    TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
							tradeOrderItemsDMO.setOrderNo(orderNo);
							tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
							tradeOrderItemsDMO.setOuterChannelPuchaseStatus(OrderStatusEnum.JD_SURE_SATOCK_SUCCESS.getCode());
							LOGGER.info("京东预占库存(京东确认订单)回调结果-- 成功 --更新参数:{}",JSONObject.toJSONString(tradeOrderItemsDMO));
							int updateItem = tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
							LOGGER.info("京东预占库存(京东确认订单)---trade_order_items(成功)结果:{}",updateItem);
					}else{
						LOGGER.info("京东预占库存(京东确认订单)回调结果:失败");
						String orderItemNo = orderItemNoArray[0];
						List<TradeOrderItemsDMO>  tradeOrderItemList = tradeOrderItemsDAO.selectOrderItemsByOrderItemNo(orderItemNo);
						if(null != tradeOrderItemList && tradeOrderItemList.size()>0){
							String outerChannelPuchaseStatus =  tradeOrderItemList.get(0).getOuterChannelPuchaseStatus();
							if(StringUtilHelper.isNull(outerChannelPuchaseStatus) || !outerChannelPuchaseStatus.equals(OrderStatusEnum.JD_SURE_SATOCK_SUCCESS.getCode())){
								TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
								tradeOrderItemsDMO.setOrderNo(orderNo);
								tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
								tradeOrderItemsDMO.setOrderItemErrorStatus(OrderStatusEnum.ORDER_ERROR_STATUS_JD_FAIL.getCode());
								tradeOrderItemsDMO.setOrderItemErrorTime(DateUtil.getSystemTime());
								int updateItem = tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
								LOGGER.info("京东预占库存(京东确认订单)---trade_order_items(失败)结果:{}",updateItem);
								
								TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
								tradeOrdersDMO.setOrderNo(orderNo);
								tradeOrdersDMO.setOrderErrorStatus(OrderStatusEnum.ORDER_ERROR_STATUS_JD_FAIL.getCode());
								tradeOrdersDMO.setOrderErrorTime(DateUtil.getSystemTime());
								int updateOrder = traderdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
								LOGGER.info("京东预占库存(京东确认订单)---trade_orders(失败)结果:{}",updateOrder);
							}
						}
					}
				}
		}).start();
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 京东预占库存(京东确认订单)接口---更新TB_B_JDORDERINFO出现异常:{}","",w.toString());
		}
	}
	
	 /*
     * 京东抛单回调接口
     * 目前走http,没有回调
     */
	@Override
	@Transactional
	public JDCreateOrderCallBackDMO executeJDCreateOrderCallBack(JDCreateOrderCallBackReqDTO jdCreateOrderCallBackReqDTO){
		JDCreateOrderCallBackDMO jdCreateOrderCallBackDMO = new JDCreateOrderCallBackDMO();
		jdCreateOrderCallBackDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		jdCreateOrderCallBackDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		try{
			JDOrderInfoDMO record = new JDOrderInfoDMO();
			record.setOrderNo(jdCreateOrderCallBackReqDTO.getOrderNo());
			record.setJdResultStatus(jdCreateOrderCallBackReqDTO.getJdResultStatus());
			record.setJdResultCode(jdCreateOrderCallBackReqDTO.getJdResultCode());
			record.setJdResultMsg(jdCreateOrderCallBackReqDTO.getJdResultMsg());
			jdOrderInfoDAO.updateJDOrderInfoByOrderNo(record);
			
			TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
			tradeOrderItemsDMO.setOuterChannelOrderNo(jdCreateOrderCallBackReqDTO.getJdOrderId().toString());
			tradeOrderItemsDMO.setOrderNo(jdCreateOrderCallBackReqDTO.getOrderNo());
			tradeOrderItemsDMO.setChannelCode(GoodCenterEnum.JD_SUPPLIER.getCode());
			tradeOrderItemsDAO.updateTradeOrderItemsByOrderNo(tradeOrderItemsDMO);
		}catch(Exception e){
			jdCreateOrderCallBackDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			jdCreateOrderCallBackDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error("MessageId:{} 京东预占库存(京东确认订单)接口回调出现异常:{}","",w.toString());
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return jdCreateOrderCallBackDMO;
	}
	
	/*
	 * 京东创建订单方法
	 */
	@Override
	public OrderCreateInfoDMO createJDOrder(String messageId,JDCreateOrderReqDTO jdCreateOrderReqDTO,OrderCreateListInfoReqDTO orderTemp) {
		OrderCreateInfoDMO orderCreateInfoDMO = new OrderCreateInfoDMO();
		orderCreateInfoDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		try {
			LOGGER.info("MessageId:{}京东抛单-创建订单开始:{}",messageId,JSONUtil.toJSONString(jdCreateOrderReqDTO));
		    List<JDCreateOrderSkuReqDTO> skuList = new ArrayList<JDCreateOrderSkuReqDTO>();
			
			String sellerCode = orderTemp.getSellerCode();
			OtherCenterResDTO<MemberInvoiceDTO> sellerInvoiceInfo = memberCenterRAO.queryMemberInvoiceInfo(sellerCode,GoodCenterEnum.JD_SUPPLIER.getCode(), messageId);
			if(!ResultCodeEnum.SUCCESS.getCode().equals(sellerInvoiceInfo.getOtherCenterResponseCode())){
				LOGGER.warn("从会员中心查询卖家发票信息失败：");
				orderCreateInfoDMO.setResultCode(sellerInvoiceInfo.getOtherCenterResponseCode());
				return orderCreateInfoDMO;
			}
					
		    jdCreateOrderReqDTO.setThirdOrder(orderTemp.getOrderNo());
		    
		    OtherCenterResDTO<MemberConsigAddressDTO> channelAddressDTO = memberCenterRAO.selectChannelAddressDTO4Common(messageId, sellerCode, Constant.PRODUCT_CHANNEL_CODE_OUTLINE);
		    if(!channelAddressDTO.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())){
				orderCreateInfoDMO.setResultCode(channelAddressDTO.getOtherCenterResponseCode());
				orderCreateInfoDMO.setResultMsg(channelAddressDTO.getOtherCenterResponseMsg());
				return orderCreateInfoDMO;
			}
		    MemberConsigAddressDTO memRes = channelAddressDTO.getOtherCenterResult();
		    //等会员中心查询会员基本信息
		    jdCreateOrderReqDTO.setName(memRes.getConsigneeName());
		    jdCreateOrderReqDTO.setProvince(Integer.valueOf(StringUtils.isEmpty(memRes.getConsigneeAddressProvince())?"0":memRes.getConsigneeAddressProvince()));
		    jdCreateOrderReqDTO.setCity(Integer.valueOf(StringUtils.isEmpty(memRes.getConsigneeAddressCity())?"0":memRes.getConsigneeAddressCity()));
		    jdCreateOrderReqDTO.setCounty(Integer.valueOf(StringUtils.isEmpty(memRes.getConsigneeAddressDistrict())?"0":memRes.getConsigneeAddressDistrict()));
		    jdCreateOrderReqDTO.setTown(Integer.valueOf(StringUtils.isEmpty(memRes.getConsigneeAddressTown())?"0":memRes.getConsigneeAddressTown()));
		    jdCreateOrderReqDTO.setAddress(memRes.getConsigneeAddressDetail());
		    jdCreateOrderReqDTO.setZip(memRes.getPostCode());
		    jdCreateOrderReqDTO.setMobile(memRes.getConsigneeMobile());
		    jdCreateOrderReqDTO.setEmail(memRes.getConsigneeEmail());
		    
		    jdCreateOrderReqDTO.setRemark(orderTemp.getOrderRemarks());
		    jdCreateOrderReqDTO.setInvoiceState(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_INVOICESTATE_CENTRALIZED_BILLING.getCode()));
		    jdCreateOrderReqDTO.setInvoiceType(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_INVOICETYPE_VAT.getCode()));
		    jdCreateOrderReqDTO.setSelectedInvoiceTitle(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_SELECTEDINVOICETITLE_COMPANY.getCode()));
		    jdCreateOrderReqDTO.setInvoiceContent(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_INVOICECONTENT_DETAIL.getCode()));
		    jdCreateOrderReqDTO.setPaymentType(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_PAYMENTTYPE_ONLINE_PAY.getCode())); 
		    jdCreateOrderReqDTO.setIsUseBalance(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_USEBALANCE.getCode()));
		    jdCreateOrderReqDTO.setSubmitState(Integer.valueOf(MiddleWareEnum.MIDDLE_WARE_JD_SUBMITSTATE_PRE_STOCK.getCode()));
			 //以下是从会员中心查询出来的
		    MemberInvoiceDTO invoiceInfo = sellerInvoiceInfo.getOtherCenterResult();
		    jdCreateOrderReqDTO.setCompanyName(invoiceInfo.getInvoiceCompanyName());
		    jdCreateOrderReqDTO.setInvoiceName(invoiceInfo.getInvoicePerson());
		    jdCreateOrderReqDTO.setInvoicePhone(invoiceInfo.getContactPhone());
			String province = invoiceInfo.getInvoiceAddressProvince();
			String city = invoiceInfo.getInvoiceAddressCity();
			String county = invoiceInfo.getInvoiceAddressCounty();
			 
			jdCreateOrderReqDTO.setInvoiceProvice(Integer.valueOf(province));
			jdCreateOrderReqDTO.setInvoiceCity(Integer.valueOf(city));
			jdCreateOrderReqDTO.setInvoiceCounty(Integer.valueOf(county));
			jdCreateOrderReqDTO.setInvoiceAddress(invoiceInfo.getInvoiceAddress());
			 
			LOGGER.info("MessageId:{}京东抛单-准备往中间件发送http信息为:{}",messageId,JSONUtil.toJSONString(jdCreateOrderReqDTO));

			String urlToken = middlewareHttpUrlConfig.getOrdercenterMiddleware4token(); 
			String tokenRes="";
			String token = "";
			
			String orderJson = java.net.URLEncoder.encode(net.sf.json.JSONObject.fromObject(jdCreateOrderReqDTO).toString());//TODO  fastJson Boolean 有问题 
			
			tokenRes = HttpClientCommon.httpGet(urlToken);
			LOGGER.info("MessageId:{} http请求中间件返回token结果:{}",messageId,tokenRes);
			JSONObject tokenJson = (JSONObject)JSONObject.parseObject(tokenRes, JSONObject.class);
			token = tokenJson.get("data").toString();
			
			String url =  middlewareHttpUrlConfig.getOrdercenterMiddleware4JDCreateOrder()+"?orderJson="+orderJson+"&token="+token;
			LOGGER.info("MessageId:{} url::::::::::::::::::{}",messageId,url);
			String httpRes = HttpClientCommon.httpGet(url);
			LOGGER.info("MessageId:{} 京东抛单---http请求中间件返回jd结果:{}",messageId,httpRes);
			
		    JSONObject jsonObj = (JSONObject)JSONObject.parse(httpRes);
		    JDResDTO jdResDTO = JSONObject.toJavaObject(jsonObj, JDResDTO.class);
		    if(null != jdResDTO.getCode() && MiddleWareEnum.MIDDLE_SUCCESS.getCode().equals(jdResDTO.getCode())){
			   //TODO 如果成功
			   if(StringUtilHelper.isNotNull(jdResDTO.getData())){
				   String data = jdResDTO.getData().toString();
				   JSONObject resData = (JSONObject)JSONObject.parse(data);
				   Map resDataMap = JSONObject.toJavaObject(resData,Map.class);
				   String jdOrderId = resDataMap.get("jdOrderId")==null?"":resDataMap.get("jdOrderId").toString();
				   LOGGER.info("MessageId:{} 京东抛单---中间件返回京东订单号:{} ",messageId,jdOrderId);
				   if(StringUtilHelper.isNull(jdOrderId)){
					   orderCreateInfoDMO.setResultCode(ResultCodeEnum.JD_CREATE_ORDER_FAIL.getCode());
					   orderCreateInfoDMO.setResultMsg(ResultCodeEnum.JD_CREATE_ORDER_FAIL.getMsg());
					   return orderCreateInfoDMO;
				   }
				   Map<String,Object> extendMap = new HashMap<String,Object>();
				   extendMap.put("outerChannelOrderNo", jdOrderId);
				   extendMap.put("outerChannelPuchaseStatus", OrderStatusEnum.JD_PRE_SATOCK_SUCCESS.getCode());
				   orderTemp.setExtendMap(extendMap);
			   }else{
				   LOGGER.info("MessageId:{} 京东抛单---中间件返回data为空 ",messageId);
				   orderCreateInfoDMO.setResultCode(ResultCodeEnum.JD_CREATE_ORDER_FAIL.getCode());
				   orderCreateInfoDMO.setResultMsg(ResultCodeEnum.JD_CREATE_ORDER_FAIL.getMsg());
				   return orderCreateInfoDMO;
			   }
		     }else{
		    	   orderCreateInfoDMO.setResultCode(ResultCodeEnum.MIDDLEWARE_JD_CREATE_ORDER.getCode());
				   orderCreateInfoDMO.setResultMsg(ResultCodeEnum.MIDDLEWARE_JD_CREATE_ORDER.getMsg());
				   return orderCreateInfoDMO;
		     }
			} catch (Exception e) {
				 StringWriter w = new StringWriter();
				 e.printStackTrace(new PrintWriter(w));
				 LOGGER.error("MessageId:{} 京东抛单---http请求中间件出现异常:{}",messageId,w.toString());
				 orderCreateInfoDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
				 orderCreateInfoDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
				 return orderCreateInfoDMO;
			}
			LOGGER.info("MessageId:{} 成功发送京东抛单",messageId);
			return orderCreateInfoDMO;
	}
	
}
