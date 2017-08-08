/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VenusTradeOrderDealServiceImpl.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 新增订单服务  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.enums.TodoTypeEnum;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.utils.ExceptionUtils;
import cn.htd.tradecenter.dao.TradeOrderCountDAO;
import cn.htd.tradecenter.dto.TradeOrderCountDTO;
import cn.htd.tradecenter.service.TradeOrderTodoCountService;

@Service("tradeOrderTodoCountService")
public class TradeOrderTodoCountServiceImpl implements TradeOrderTodoCountService {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderTodoCountServiceImpl.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private TradeOrderCountDAO tradeOrderCountDAO;

	@Override
	public ExecuteResult<List<TradeOrderCountDTO>> queryOrderStatusCount(String sellerCode) {
		ExecuteResult<List<TradeOrderCountDTO>> result = new ExecuteResult<List<TradeOrderCountDTO>>();
		List<TradeOrderCountDTO> queryCount = null;
		List<TradeOrderCountDTO> countResule = new ArrayList<TradeOrderCountDTO>();
		Map<String, TradeOrderCountDTO> countMap = new HashMap<String, TradeOrderCountDTO>();
		List<String> targetStatus = new ArrayList<String>();
		TradeOrderCountDTO countResultDTO = null;
		TradeOrderCountDTO queryCondition = new TradeOrderCountDTO();
		String orderStatus = "";
		int cnt = 0;
		String type = "";
		String key = "";
		try {
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING));
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY));
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP));
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
					DictionaryConst.OPT_ORDER_STATUS_WAIT_DELIVERY));
			targetStatus.add(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst
					.OPT_ORDER_STATUS_CONSIGNED));
			queryCondition.setSellerCode(sellerCode);
			queryCondition.setIsCancelOrder(YesNoEnum.NO.getValue());
			queryCondition.setTargetOrderStatusList(targetStatus);
			queryCount = tradeOrderCountDAO.queryOrderStatusCount(queryCondition);

			if (queryCount != null && queryCount.size() > 0) {
				for (TradeOrderCountDTO countDTO : queryCount) {
					orderStatus = countDTO.getOrderStatus();
					if (dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
							DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING).equals(orderStatus)) {
						type = TodoTypeEnum.ORDER_VERIFY.getValue();
					} else if (dictionary
							.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
									DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY)
							.equals(orderStatus)
							|| dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
									DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY).equals(orderStatus)
							|| dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
									DictionaryConst.OPT_ORDER_STATUS_VMS_WAIT_DOWNERP).equals(orderStatus)) {
						type = TodoTypeEnum.ORDER_PAY.getValue();
					} else if (dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
							DictionaryConst.OPT_ORDER_STATUS_WAIT_DELIVERY).equals(orderStatus)) {
						type = TodoTypeEnum.ORDER_DELIVERY.getValue();
					} else if (dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
							DictionaryConst.OPT_ORDER_STATUS_CONSIGNED).equals(orderStatus)) {
						type = TodoTypeEnum.ORDER_CONSIGNED.getValue();
					} else {
						continue;
					}
					sellerCode = countDTO.getSellerCode();
					cnt = countDTO.getCnt();
					key = sellerCode + "_" + type;
					if (!countMap.containsKey(key)) {
						countDTO.setOrderStatus(type);
						countMap.put(key, countDTO);
					} else {
						countResultDTO = countMap.get(key);
						countResultDTO.setCnt(countResultDTO.getCnt() + cnt);
						countMap.put(key, countResultDTO);
					}
				}
				for (Entry<String, TradeOrderCountDTO> entry : countMap.entrySet()) {
					countResule.add(entry.getValue());
				}
			}
			result.setResult(countResule);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderTodoCountServiceImpl-queryOrderStatusCount",
					ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}