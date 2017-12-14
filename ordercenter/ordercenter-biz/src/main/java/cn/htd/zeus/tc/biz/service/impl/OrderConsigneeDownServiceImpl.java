package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.biz.dao.TradeOrderConsigneeDownInfoDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderConsigneeDownInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderConsigneeDownService;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.HttpClientCommon;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.response.JDResDTO;

import com.alibaba.fastjson.JSONObject;

@Service
public class OrderConsigneeDownServiceImpl implements OrderConsigneeDownService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderConsigneeDownServiceImpl.class);

	@Autowired
	private TradeOrderConsigneeDownInfoDAO tradeOrderConsigneeDownInfoDAO;

	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;

	@Override
	public List<TradeOrderConsigneeDownInfoDMO> selectTradeOrderConsigneeDownInfoList(
			Map paramMap) {
		return tradeOrderConsigneeDownInfoDAO
				.selectTradeOrderConsigneeDownInfoList(paramMap);
	}

	@Override
	@Transactional
	public void orderConsigneeDownERP(TradeOrderConsigneeDownInfoDMO[] tasks) {

		for (TradeOrderConsigneeDownInfoDMO requestInfo : tasks) {
			if (null != requestInfo) {
				Date receiptDate = requestInfo.getConsigneeTime();
				String merchOrderNo = requestInfo.getErpLockBalanceCode();
				if (null != receiptDate && StringUtils.isNotEmpty(merchOrderNo)) {
					try {
						String urlToken = middlewareHttpUrlConfig
								.getOrdercenterMiddleware4token();
						String tokenRes = HttpClientCommon.httpGet(urlToken);
						LOGGER.info("http请求中间件token返回结果:"+tokenRes);
						JSONObject tokenJson = (JSONObject)JSONObject.parseObject(tokenRes, JSONObject.class);
						String token = tokenJson.get("data").toString();
						String url = middlewareHttpUrlConfig
								.getOrdercenterMiddleware4ConsigneeDown()
								+ "?merchOrderNo="
								+ merchOrderNo
								+"&receiptDate"
								+ DateUtil.dateToString(receiptDate)
								+ "&token="
								+ token;
						LOGGER.info("确认收货-请求中间件url:{}",url);
						String httpRes = HttpClientCommon.httpGet(url);
						LOGGER.info("确认收货-请求中间件 ---http请求返回结果:{}",httpRes);
						JDResDTO jdResDTO = new JDResDTO();
					    if(StringUtilHelper.allIsNotNull(httpRes)){
						   JSONObject jsonObj = (JSONObject)JSONObject.parse(httpRes);
						   jdResDTO = JSONObject.toJavaObject(jsonObj, JDResDTO.class);
						   String code = jdResDTO.getCode();
						   TradeOrderConsigneeDownInfoDMO record = new TradeOrderConsigneeDownInfoDMO();
						   record.setErpLockBalanceCode(merchOrderNo);
						   if(MiddleWareEnum.SUCCESS_ONE.getCode().equals(code)){		
							   record.setDownStatus(Integer.valueOf(OrderStatusEnum.DOWN_STATUS_SUCCESS.getCode()));
							   int updateRes =tradeOrderConsigneeDownInfoDAO.updateTradeOrderConsigneeDownInfo(record);
							   LOGGER.info("确认收货-中间件回调-处理成功-跟新数据库结果:{}",updateRes);
						   }else{
							   record.setDownStatus(Integer.valueOf(OrderStatusEnum.DOWN_STATUS_FAIL.getCode()));
							   int updateRes =tradeOrderConsigneeDownInfoDAO.updateTradeOrderConsigneeDownInfo(record);
							   LOGGER.info("确认收货-中间件回调-处理失败-跟新数据库结果:{}",updateRes);
						   }
					    }
					} catch (Exception e) {
						StringWriter w = new StringWriter();
						e.printStackTrace(new PrintWriter(w));
						LOGGER.error(
								"MessageId:{},参数：{} 请求中间件-收货订单时间下行出现异常:{}", "",
								JSONObject.toJSONString(requestInfo),
								w.toString());
					}
				}
			}
		}
	}
}
