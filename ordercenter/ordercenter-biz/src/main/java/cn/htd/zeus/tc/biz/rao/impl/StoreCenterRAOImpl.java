package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.storecenter.service.QQCustomerService;
import cn.htd.zeus.tc.biz.rao.StoreCenterRAO;

@Service
public class StoreCenterRAOImpl implements StoreCenterRAO {

	@Autowired
	private QQCustomerService qqCustomerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreCenterRAOImpl.class);

	@Override
	public List<QQCustomerDTO> searchQQCustomerByCondition(Long shopId, String messageId,
			String customerType) {
		List<QQCustomerDTO> qqCustomerDTOList = new ArrayList<QQCustomerDTO>();
		try {
			QQCustomerDTO queryQQCustomerDTO = new QQCustomerDTO();
			queryQQCustomerDTO.setShopId(shopId);
			// 店铺客服
			queryQQCustomerDTO.setCustomerType(customerType);
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询卖家中心(searchQQCustomerByCondition查询卖家QQ)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(queryQQCustomerDTO));
			qqCustomerDTOList = qqCustomerService.searchQQCustomerByCondition(queryQQCustomerDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询卖家中心(searchQQCustomerByCondition查询卖家QQ)--返回结果:{}", messageId,
					JSONObject.toJSONString(qqCustomerDTOList) + " 耗时:" + (endTime - startTime));
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法qqCustomerService.searchQQCustomerByCondition出现异常{}",
					messageId, w.toString());
		}
		return qqCustomerDTOList;
	}

}
