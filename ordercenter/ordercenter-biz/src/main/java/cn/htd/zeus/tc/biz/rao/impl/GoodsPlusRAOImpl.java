package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.rao.GoodsPlusRAO;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.util.HttpUtil;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.GetJDAmountResDTO;
import cn.htd.zeus.tc.dto.response.StockNewResultVoDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class GoodsPlusRAOImpl implements GoodsPlusRAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsPlusRAOImpl.class);

	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;

	/*
	 * 批量获取库存接口 (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.zeus.tc.biz.rao.GoodsPlusRAO#queryStock4JD(cn.htd.zeus.tc.dto.
	 * resquest.BatchGetStockReqDTO, java.lang.String)
	 */
	@Override
	public OtherCenterResDTO<String> queryStock4JD(BatchGetStockReqDTO batchGetStockReqDTO,
			String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			String url = middlewareHttpUrlConfig.getOrdercenterMiddleware4JDStock();
			String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
			String tokenResult = HttpUtil.sendGet(tokenUrl);
			String token = (String) JSONObject.fromObject(tokenResult).get("data");
			if (null != batchGetStockReqDTO) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("queryString",
						com.alibaba.fastjson.JSONObject.toJSONString(batchGetStockReqDTO));
				paramMap.put("token", token);
				Long startTime = System.currentTimeMillis();
				LOGGER.info("MessageId:{} 调用中间件http接口：查询京东商品库存信息=======queryStock4JD开始：{}",
						messageId, JSONObject.fromObject(paramMap));
				String result = HttpUtil.sendPost(url, paramMap);
				LOGGER.info("MessageId:{} 调用中间件http接口：查询京东商品库存信息=======queryStock4JD结束：{}",
						messageId, result);
				Long endTime = System.currentTimeMillis();
				LOGGER.info("中间件http接口：查询京东商品库存信息 耗时:{}", endTime - startTime);
				JSONObject jsonObj = (JSONObject) JSONObject.fromObject(result);
				if (StringUtils.isNotBlank((String) jsonObj.get("msg"))) {
					otherCenterResDTO.setOtherCenterResponseMsg((String) jsonObj.get("msg"));
					otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
				} else {
					JSONArray jsonArray = JSONArray.fromObject(jsonObj.get("data"));
					@SuppressWarnings("unchecked")
					List<StockNewResultVoDTO> resultDTO = (List<StockNewResultVoDTO>) jsonArray
							.toCollection(jsonArray, StockNewResultVoDTO.class);
					if (resultDTO != null) {
						otherCenterResDTO
								.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
						for (int i = 0; i < resultDTO.size(); i++) {
							if (resultDTO.get(i)
									.getStockStateId() != Constant.JD_PRODUCT_STOCK_AVAILABLE) {
								otherCenterResDTO.setOtherCenterResponseCode(
										ResultCodeEnum.ORDERSETTLEMENT_JD_STOCK_CHECK_FAIL
												.getCode());
								otherCenterResDTO.setOtherCenterResponseMsg(
										ResultCodeEnum.ORDERSETTLEMENT_JD_STOCK_CHECK_FAIL
												.getMsg());
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsPlusRAOImpl.queryStock4JD出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 批量获取库存接口 (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.zeus.tc.biz.rao.GoodsPlusRAO#queryStock4JD(cn.htd.zeus.tc.dto.
	 * resquest.BatchGetStockReqDTO, java.lang.String)
	 */
	@Override
	public OtherCenterResDTO<String> queryProductStock4JD(BatchGetStockReqDTO batchGetStockReqDTO,
			String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			String url = middlewareHttpUrlConfig.getOrdercenterMiddleware4JDStock();
			String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
			String tokenResult = HttpUtil.sendGet(tokenUrl);
			String token = (String) JSONObject.fromObject(tokenResult).get("data");
			if (null != batchGetStockReqDTO) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("queryString",
						com.alibaba.fastjson.JSONObject.toJSONString(batchGetStockReqDTO));
				paramMap.put("token", token);
				Long startTime = System.currentTimeMillis();
				LOGGER.info("MessageId:{}调用中间件http接口：查询京东商品库存信息=======queryProductStock4JD开始：{}",
						messageId, JSONObject.fromObject(paramMap));
				String result = HttpUtil.sendPost(url, paramMap);
				LOGGER.info("MessageId:{}调用中间件http接口：查询京东商品库存信息=======queryProductStock4JD结束：{}",
						messageId, result);
				Long endTime = System.currentTimeMillis();
				LOGGER.info("中间件http接口：查询京东商品库存信息 耗时:{}", endTime - startTime);
				JSONObject jsonObj = (JSONObject) JSONObject.fromObject(result);
				if (StringUtils.isNotBlank((String) jsonObj.get("msg"))) {
					otherCenterResDTO.setOtherCenterResponseMsg((String) jsonObj.get("msg"));
					otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
				} else {
					JSONArray jsonArray = JSONArray.fromObject(jsonObj.get("data"));
					@SuppressWarnings("unchecked")
					List<StockNewResultVoDTO> resultDTO = (List<StockNewResultVoDTO>) jsonArray
							.toCollection(jsonArray, StockNewResultVoDTO.class);
					if (resultDTO != null) {
						if (resultDTO.get(0).getStockStateId() == 33
								|| resultDTO.get(0).getStockStateId() == 39
								|| resultDTO.get(0).getStockStateId() == 40) {
							otherCenterResDTO
									.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
							otherCenterResDTO.setOtherCenterResult(
									String.valueOf(resultDTO.get(0).getRemainNum()));
						} else {
							otherCenterResDTO.setOtherCenterResponseCode(
									ResultCodeEnum.ORDERSETTLEMENT_JD_STOCK_CHECK_FAIL.getCode());
							otherCenterResDTO.setOtherCenterResult(
									String.valueOf(resultDTO.get(0).getRemainNum()));
						}

					} else {
						otherCenterResDTO.setOtherCenterResult("0");
					}
				}
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsPlusRAOImpl.queryStock4JD出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	@Override
	public OtherCenterResDTO<String> queryAccountAmount4JD(String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			String url = middlewareHttpUrlConfig.getOrdercenterMiddleware4JDAmount();
			String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
			String tokenResult = HttpUtil.sendGet(tokenUrl);
			String token = (String) JSONObject.fromObject(tokenResult).get("data");
			if (StringUtils.isNotBlank(token)) {
				Long startTime = System.currentTimeMillis();
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("token", token);
				LOGGER.info(
						"MessageId:{}调用中间件http接口：查询京东商品账户余额信息=======queryStock4JD开始入参：paramMap:{}",
						messageId, com.alibaba.fastjson.JSONObject.toJSONString(paramMap));
				String result = HttpUtil.sendPost(url, paramMap);
				LOGGER.info("MessageId:{}调用中间件http接口：查询京东商品账户余额信息=======queryStock4JD结束：result:{}",
						messageId, result);
				Long endTime = System.currentTimeMillis();
				LOGGER.info("中间件http接口：查询京东商品账户余额信息 耗时:{}", endTime - startTime);
				JSONObject jsonObj = (JSONObject) JSONObject.fromObject(result);
				GetJDAmountResDTO getJDAmountResDTO = (GetJDAmountResDTO) JSONObject.toBean(jsonObj,
						GetJDAmountResDTO.class);
				LOGGER.info("MessageId:{}调用中间件http接口：查询京东商品账户余额信息=======queryStock4JD结束：{}",
						messageId, com.alibaba.fastjson.JSONObject.toJSONString(getJDAmountResDTO));
				if (getJDAmountResDTO.getData() != null) {
					otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
					otherCenterResDTO.setOtherCenterResult(getJDAmountResDTO.getData());
				} else {
					LOGGER.warn("中间件没有返回结果");
				}
			} else {
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_JD_HTTP_ERROR.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.ORDERSETTLEMENT_JD_HTTP_ERROR.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsPlusRAOImpl.queryAccountAmount4JD出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}
}
