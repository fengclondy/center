package cn.htd.tradecenter.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import cn.htd.tradecenter.common.utils.ErpPay;
import cn.htd.tradecenter.dao.*;
import cn.htd.tradecenter.dto.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.tradecenter.service.TradeOrderExportService;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiji.openapi.tool.util.DigestUtil;
import com.yiji.openapi.tool.util.Ids;

@Service("tradeOrderExportService")
public class TradeOrderExportServiceImpl implements TradeOrderExportService {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderExportServiceImpl.class);

	@Resource
	private TradeOrdersSellerDAO tradeOrdersSellerDAO;
	@Resource
	private TradeOrderItemsSellerDAO tradeOrderItemsSellerDAO;
	@Resource
	private TradeOrdersDAO tradeOrdersDAO;
	@Resource
	private TradeOrderStatusHistoryDAO TradeOrderStatusHistoryDAO;
	@Resource
	private TradeOrderItemsStatusHistoryDAO tradeOrderItemsStatusHistoryDAO;
	@Resource
	private TradeOrderItemsDAO tradeOrderItemsDAO;
	@Resource
	private TradeOrderItemsPriceHistoryDAO tradeOrderItemsPriceHistoryDAO;
	@Resource
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO;
	@Resource
	private ErpPay erpPay;
	@Resource
	private MemberBaseInfoService memberBaseInfoService;


	/**
	 * 卖家中心订单信息以及订单行信息查询
	 * 
	 * @param tradeOrderQueryInForSellerDTO
	 * @param pager
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<TradeOrderQueryOutForSellerDTO>> queryTradeOrderForSeller(
			TradeOrderQueryInForSellerDTO tradeOrderQueryInForSellerDTO, Pager<TradeOrderQueryInForSellerDTO> pager) {
		ExecuteResult<DataGrid<TradeOrderQueryOutForSellerDTO>> result = new ExecuteResult<DataGrid<TradeOrderQueryOutForSellerDTO>>();
		DataGrid<TradeOrderQueryOutForSellerDTO> dataGrid = new DataGrid<TradeOrderQueryOutForSellerDTO>();
		if (null == tradeOrderQueryInForSellerDTO.getShopId()) {
			result.addErrorMessage("商家不存在，或者店铺不存在");
			return result;
		}
		String promotionId ="";
		List<TradeOrderQueryOutForSellerDTO> dtos = tradeOrdersSellerDAO
				.queryOrderForSeller(tradeOrderQueryInForSellerDTO, pager);
		Long count = tradeOrdersSellerDAO.queryOrderCountsForSeller(tradeOrderQueryInForSellerDTO);
		if (count == 0) {
			result.addErrorMessage("没有订单信息！");
		}
		for (TradeOrderQueryOutForSellerDTO dto : dtos) {
			List<TradeOrderItemsDTO> list1 = tradeOrderItemsSellerDAO.queryTradeOrderItemForSeller(dto.getOrderNo());
			List<TradeOrderItemsDiscountDTO> orderItemDiscountDTOList = tradeOrderItemsDiscountDAO.queryItemDiscountByOrderNo(dto.getOrderNo());
			if(null!=orderItemDiscountDTOList&&orderItemDiscountDTOList.size()>0){
				for(TradeOrderItemsDiscountDTO tradeOrderItemsDiscountDTO:orderItemDiscountDTOList){
					promotionId+=tradeOrderItemsDiscountDTO.getPromotionId()+" ";
				}
			}

			dto.setPromotionId(promotionId);
			if (null == list1) {
				result.addErrorMessage("订单行信息查询失败");
			}
			dto.setItems(list1);
		}
		dataGrid.setRows(dtos);
		dataGrid.setTotal(count);
		result.setResult(dataGrid);
		return result;
	}

	@Override
	public ExecuteResult<Long> queryOrderQty(TradeOrderQueryInForSellerDTO inDTO) {
		ExecuteResult<Long> result = new ExecuteResult<Long>();
		try {
			 result.setResult(tradeOrdersSellerDAO.queryStatusCountsForSeller(inDTO));
		} catch (Exception e) {
			logger.error("执行方法【queryOrderQty】报错：{}", e);
			result.addErrorMessage("执行方法【queryOrderQty】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<Long> modifyOrderStatus(TradeOrderStatusHistoryDTO statusHistoryDTO) {

		ExecuteResult<Long> result = new ExecuteResult<Long>();
		String orderNo = statusHistoryDTO.getOrderNo();
		String orderStatus = statusHistoryDTO.getOrderStatus();
		//更新订单状态
		Long l = tradeOrdersSellerDAO.updateOrderStatus(orderNo,orderStatus);
		TradeOrderStatusHistoryDAO.addTradeOrderStatusHistory(statusHistoryDTO);
		//更新订单行集合状态
		List<TradeOrderItemsDTO> list = tradeOrderItemsSellerDAO.queryTradeOrderItemForSeller(orderNo);
		List<TradeOrderItemsStatusHistoryDTO> orderItemsStatusHistoryDTOList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
		if(null !=list){
			for (TradeOrderItemsDTO list1:list){
				String itemNo = list1.getOrderItemNo();
				//更新订单行状态
				tradeOrderItemsSellerDAO.updateStatus(itemNo,orderStatus);
				TradeOrderItemsStatusHistoryDTO dto = new TradeOrderItemsStatusHistoryDTO();
				dto.setOrderItemNo(itemNo);
				dto.setCreateTime(new Date());
				dto.setCreateName(statusHistoryDTO.getCreateName());
				dto.setCreateId(statusHistoryDTO.getCreateId());
				dto.setOrderItemStatus(statusHistoryDTO.getOrderStatus());
				dto.setOrderItemStatusText(statusHistoryDTO.getOrderStatusText());
				orderItemsStatusHistoryDTOList.add(dto);
			}
			//添加订单行状态履历
			tradeOrderItemsStatusHistoryDAO.addOrderItemsStatusHistoryList(orderItemsStatusHistoryDTOList);
		}

		TradeOrderItemsDTO dto = new TradeOrderItemsDTO();
		tradeOrderItemsSellerDAO.update(dto);
		result.setResult(l);
		return  result;
	}

	@Override
	public ExecuteResult<TradeOrdersDTO> getOrderById(String orderNo) {
		ExecuteResult<TradeOrdersDTO> result = new ExecuteResult<TradeOrdersDTO>();
		try {
			TradeOrdersDTO dto = tradeOrdersDAO.queryTradeOrderByOrderNo(orderNo);
			TradeOrderItemsDTO itemDto = new TradeOrderItemsDTO();
			itemDto.setOrderNo(orderNo);
			List<TradeOrderItemsDTO> list = tradeOrderItemsDAO.queryTradeOrderItemsByOrderNo(itemDto);
			if(null!=list) {
				dto.setOrderItemList(list);
			}
			result.setResult(dto);
		}catch (Exception e) {
				logger.error("执行方法【getOrderById】报错：{}", e);
				result.addErrorMessage("执行方法【getOrderById】报错：" + e.getMessage());
				throw new RuntimeException(e);
			}
		return  result;

	}

	/**
	 * 订单改价
	 * @param tradeOrderDTO
	 * @return
     */
	@Override
	public ExecuteResult<String> changePrice(TradeOrdersDTO tradeOrderDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			int i = tradeOrdersDAO.changePrice(tradeOrderDTO);
			if (null != tradeOrderDTO.getOrderItemList() && tradeOrderDTO.getOrderItemList().size() > 0) {
				for (TradeOrderItemsDTO tradeOrderItemsDTO : tradeOrderDTO.getOrderItemList()) {
					tradeOrderItemsDAO.changePrice(tradeOrderItemsDTO);
					if (tradeOrderItemsDTO.getBargainingGoodsFreight()!=tradeOrderItemsDTO.getGoodsFreight()||tradeOrderItemsDTO.getBargainingGoodsAmount()!=tradeOrderItemsDTO.getGoodsAmount()){
						BigDecimal d = new BigDecimal(0);
						TradeOrderItemsPriceHistoryDTO historyDTO = new TradeOrderItemsPriceHistoryDTO();
						historyDTO.setOrderNo(tradeOrderItemsDTO.getOrderNo()==null? "":tradeOrderItemsDTO.getOrderNo());
						historyDTO.setOrderItemNo(tradeOrderItemsDTO.getOrderItemNo()==null ?"":tradeOrderItemsDTO.getOrderItemNo());
						historyDTO.setChannelCode(tradeOrderItemsDTO.getChannelCode()==null ?"":tradeOrderItemsDTO.getChannelCode());
						historyDTO.setItemCode(tradeOrderItemsDTO.getItemCode()==null ?"":tradeOrderItemsDTO.getItemCode());
						historyDTO.setSkuCode(tradeOrderItemsDTO.getSkuCode()==null ?"":tradeOrderItemsDTO.getSkuCode());
						historyDTO.setBeforeBargainingGoodsPrice(tradeOrderItemsDTO.getGoodsPrice()==null ? d :tradeOrderItemsDTO.getBargainingGoodsPrice());
						historyDTO.setBeforeBargainingGoodsCount(tradeOrderItemsDTO.getGoodsCount()==null ?0:tradeOrderItemsDTO.getBargainingGoodsCount());
						historyDTO.setBeforeTotalPrice(tradeOrderItemsDTO.getTotalGoodsAmount()==null ?d:tradeOrderItemsDTO.getTotalGoodsAmount());
						historyDTO.setBeforeFreight(tradeOrderItemsDTO.getGoodsFreight()==null ?d:tradeOrderItemsDTO.getGoodsFreight());
						historyDTO.setBeforeTotalDiscount(tradeOrderItemsDTO.getTotalDiscountAmount() ==null?d:tradeOrderItemsDTO.getTotalDiscountAmount());
						historyDTO.setBeforeShopDiscount(tradeOrderItemsDTO.getShopDiscountAmount()==null?d:tradeOrderItemsDTO.getShopDiscountAmount());
						historyDTO.setBeforePlatformDiscount(tradeOrderItemsDTO.getPlatformDiscountAmount()==null ?d:tradeOrderItemsDTO.getPlatformDiscountAmount());
						historyDTO.setBeforePaymentPrice(tradeOrderItemsDTO.getGoodsRealPrice()==null?d:tradeOrderItemsDTO.getGoodsRealPrice());
						historyDTO.setAfterBargainingGoodsCount(tradeOrderItemsDTO.getBargainingGoodsCount()==null?0:tradeOrderItemsDTO.getBargainingGoodsCount());
						historyDTO.setAfterBargainingGoodsPrice(tradeOrderItemsDTO.getBargainingGoodsPrice()==null?d:tradeOrderItemsDTO.getBargainingGoodsPrice());
						historyDTO.setAfterTotalPrice(tradeOrderItemsDTO.getBargainingGoodsAmount()==null?d:tradeOrderItemsDTO.getBargainingGoodsAmount());
						historyDTO.setAfterFreight(tradeOrderItemsDTO.getBargainingGoodsFreight()==null?d:tradeOrderItemsDTO.getBargainingGoodsFreight());
						historyDTO.setAfterTotalDiscount(tradeOrderItemsDTO.getTotalDiscountAmount()==null?d:tradeOrderItemsDTO.getTotalDiscountAmount());
						historyDTO.setAfterShopDiscount(tradeOrderItemsDTO.getShopDiscountAmount()==null?d:tradeOrderItemsDTO.getShopDiscountAmount());
						historyDTO.setAfterPlatformDiscount(tradeOrderItemsDTO.getPlatformDiscountAmount()==null?d:tradeOrderItemsDTO.getPlatformDiscountAmount());
						historyDTO.setAfterPaymentPrice(tradeOrderItemsDTO.getOrderItemPayAmount()==null?d:tradeOrderItemsDTO.getOrderItemPayAmount());
						historyDTO.setCreateId(tradeOrderItemsDTO.getModifyId());
						historyDTO.setCreateName(tradeOrderItemsDTO.getModifyName());
						historyDTO.setCreateTime(new Date());
						tradeOrderItemsPriceHistoryDAO.insertItemPriceHistoryInfo(historyDTO);
					}
				}
			}
			result.setResultMessage("success");
		}catch (Exception e) {
			logger.error("执行方法【changePrice】报错：{}", e);
			result.addErrorMessage("执行方法【changePrice】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * confim deliver by orderNo
	 * @param tradeOrdersDTO
	 * @return
     */
	@Override
	public ExecuteResult<String> confimDeliver(TradeOrdersDTO tradeOrdersDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if(null ==tradeOrdersDTO.getOrderNo()){
			result.addErrorMessage("u have no order info,plz confim it");
		}
		try {
			tradeOrdersDAO.confimDeliver(tradeOrdersDTO);
			TradeOrderStatusHistoryDTO statusHistoryDTO = new TradeOrderStatusHistoryDTO();
			statusHistoryDTO.setCreateId(tradeOrdersDTO.getModifyId());
			statusHistoryDTO.setCreateName(tradeOrdersDTO.getModifyName());
			statusHistoryDTO.setOrderNo(tradeOrdersDTO.getOrderNo());
			statusHistoryDTO.setCreateTime(new Date());
			statusHistoryDTO.setOrderStatus("50");
			statusHistoryDTO.setOrderStatusText("卖家中心确认发货");
			ExecuteResult<TradeOrdersDTO> resultTradeOrdersDTO = getOrderById(tradeOrdersDTO.getOrderNo());
			if(!StringUtils.isEmpty(resultTradeOrdersDTO.getResult())){
				List<TradeOrderItemsDTO> listOrderItems = resultTradeOrdersDTO.getResult().getOrderItemList();
				for(TradeOrderItemsDTO tradeOrderItemsDTO :listOrderItems){
					TradeOrderItemsStatusHistoryDTO orderItemsStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
					TradeOrderItemsDTO confimTradeOrderItem = new TradeOrderItemsDTO();
					confimTradeOrderItem.setModifyName(tradeOrdersDTO.getModifyName());
					confimTradeOrderItem.setModifyId(tradeOrdersDTO.getModifyId());
					confimTradeOrderItem.setOrderItemNo(tradeOrderItemsDTO.getOrderItemNo());
					confimTradeOrderItem.setModifyTime(new Date());
					confimTradeOrderItem.setOrderItemStatus("50");
					tradeOrderItemsDAO.confimDeliver(confimTradeOrderItem);
					orderItemsStatusHistoryDTO.setOrderItemNo(tradeOrderItemsDTO.getOrderItemNo());
					orderItemsStatusHistoryDTO.setCreateTime(new Date());
					orderItemsStatusHistoryDTO.setOrderItemStatus("50");
					orderItemsStatusHistoryDTO.setOrderItemStatusText("卖家中心确认发货");
					orderItemsStatusHistoryDTO.setCreateId(tradeOrdersDTO.getModifyId());
					orderItemsStatusHistoryDTO.setCreateName(tradeOrdersDTO.getModifyName());
					List<TradeOrderItemsStatusHistoryDTO> listOrderItemHistory = tradeOrderItemsStatusHistoryDAO.queryStatusHistoryByOrderItemNo(tradeOrderItemsDTO.getOrderItemNo());
					if(null!=listOrderItemHistory&&listOrderItemHistory.size()>0){
						List<String> orderItemStatus = new ArrayList<String>();
						for (TradeOrderItemsStatusHistoryDTO tradeOrderItemsStatusHistoryDTO:listOrderItemHistory){
							orderItemStatus.add(tradeOrderItemsStatusHistoryDTO.getOrderItemStatus());
						}
						if(!orderItemStatus.contains("50")){
							tradeOrderItemsStatusHistoryDAO.addOrderItemsStatusHistory(orderItemsStatusHistoryDTO);
						}
					}else{
						tradeOrderItemsStatusHistoryDAO.addOrderItemsStatusHistory(orderItemsStatusHistoryDTO);
					}

				}
			}
			//订单行状态路履历表添加履历
			//订单状态路履历表添加履历
			List<TradeOrderStatusHistoryDTO> lsitTradeHistory = TradeOrderStatusHistoryDAO.queryStatusHistoryByOrderNo(tradeOrdersDTO.getOrderNo());
			if(null!=lsitTradeHistory&&lsitTradeHistory.size()>0){
				List<String> orderStatus = new ArrayList<String>();
				for (TradeOrderStatusHistoryDTO tradeOrderStatusHistoryDTO:lsitTradeHistory){
					orderStatus.add(tradeOrderStatusHistoryDTO.getOrderStatus());
				}
				if(!orderStatus.contains("50")){
					TradeOrderStatusHistoryDAO.addTradeOrderStatusHistory(statusHistoryDTO);
				}
			}else {
				TradeOrderStatusHistoryDAO.addTradeOrderStatusHistory(statusHistoryDTO);
			}

		}catch (Exception e) {
			logger.error("执行方法【confimDeliver】报错：{}", e);
			result.addErrorMessage("执行方法【confimDeliver】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> checkPOPOrders(String orderNo, String memberCode, String chargeAmount) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		result.setCode("0");
		result.setResult("false");
		if(StringUtils.isEmpty(memberCode)){
			result.addErrorMessage("memberCode is null");
			return result;
		}
		if(StringUtils.isEmpty(orderNo)){
			result.addErrorMessage("orderNo is null");
			return result;
		}
		if(StringUtils.isEmpty(chargeAmount)){
			result.addErrorMessage("chargeAmount is null");
			return result;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnUrl", erpPay.getReturn_url());
		map.put("protocol", "HTTP_FORM_JSON");
		map.put("signType", "MD5");
		map.put("partnerId", erpPay.getPartenerId());
		map.put("requestNo", Ids.oid());
		map.put("service", "checkPOPOrders");
		map.put("version", "1.0");
		map.put("memberCode", memberCode);
		map.put("orderNo", orderNo);
		map.put("chargeAmount", chargeAmount);
		String signedStr = DigestUtil.digest(map, erpPay.getSignKey(), DigestUtil.DigestALGEnum.MD5);
		map.put("sign", signedStr);
		PostMethod method = null;
		HttpClient http = new HttpClient();
		method = new PostMethod(erpPay.getUrl());
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		logger.info("MessageId{}:检查订单在ERP是否正确入参{}", orderNo ,JSON.toJSONString(map));
		NameValuePair[] data = mapToParameters(map);
		method.setRequestBody(data);
		int status;
		try {
			status = http.executeMethod(method);
			if(status == 200){//成功
				logger.info("订单信息传送erp成功");
			}else{
				logger.info("订单信息传送erp失败");
			}
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> m = mapper.readValue(method.getResponseBody(), Map.class);
			logger.info("MessageId{}:检查订单在ERP是否正确出参{}",orderNo , JSON.toJSONString(m));
	        if(null != m){
	            if(Boolean.valueOf(m.get("success").toString())){
	            	if("0".equals(m.get("data").toString())){
						result.addErrorMessage("未开单");
	            	}else if("1".equals(m.get("data").toString())){
	            		result.setResult("success");
						result.setCode("1");
	            		result.setResultMessage("已开单");
	            	}else if("-1".equals(m.get("data").toString())){
	            		result.addErrorMessage("开单金额小于交易金额");
	            	}else if("-2".equals(m.get("data").toString())){
	            		result.addErrorMessage("开单金额大于交易金额");
	            	}
	            }else{
	            	result.setResultMessage("erp处理失败   "+m.get("resultMessage").toString()+","+ m.get("resultDetail").toString());
	            	logger.info("erp处理失败   "+m.get("resultMessage").toString()+","+ m.get("resultDetail").toString());
	            }
	        }
		} catch (HttpException e) {
			result.addErrorMessage(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			result.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public static NameValuePair[] mapToParameters(Map<String, Object> map)
	{
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
            for (Entry<String, ?> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    nvps.add(new NameValuePair(entry.getKey(), null));
                } else {
                    nvps.add(new NameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
            
            NameValuePair[] result = new NameValuePair[nvps.size()];
            return nvps.toArray(result);
        }
		return null;
	}
}
