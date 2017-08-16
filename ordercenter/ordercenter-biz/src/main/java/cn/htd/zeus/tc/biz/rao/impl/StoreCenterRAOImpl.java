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

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.QQCustomerService;
import cn.htd.storecenter.service.ShopExportService;
import cn.htd.zeus.tc.biz.rao.StoreCenterRAO;

@Service
public class StoreCenterRAOImpl implements StoreCenterRAO {

	@Autowired
	private QQCustomerService qqCustomerService;

	@Autowired
	private ShopExportService shopExportService;

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

	@Override
	public List<ShopDTO> queryShopByids(String messageId, ShopAuditInDTO shopAudiinDTO) {
		ExecuteResult<List<ShopDTO>> shopList = new ExecuteResult<List<ShopDTO>>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询卖家中心(queryShopByids查询所有店铺信息)--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(shopAudiinDTO));
			shopList = shopExportService.queryShopByids(shopAudiinDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询卖家中心(queryShopByids查询所有店铺信息)--返回结果:{}", messageId,
					JSONObject.toJSONString(shopList) + " 耗时:" + (endTime - startTime));
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法shopExportService.queryShopByids出现异常{}", messageId,
					w.toString());
		}
		return shopList.getResult();
	}

}
