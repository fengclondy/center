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
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.QQCustomerService;
import cn.htd.storecenter.service.ShopExportService;
import cn.htd.zeus.tc.biz.rao.StoreCenterRAO;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

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
	public OtherCenterResDTO<ShopDTO> findShopInfoById(long id) {
		OtherCenterResDTO<ShopDTO> other = new OtherCenterResDTO<ShopDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询卖家中心(findShopInfoById查询卖家店铺信息)--组装查询参数开始:{}", id);
			ExecuteResult<ShopDTO> result = shopExportService.queryBySellerId(id);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("查询卖家中心(findShopInfoById查询卖家店铺信息)--返回结果:{}",
					JSONObject.toJSONString(result) + " 耗时:" + (endTime - startTime));

			if (result.getResult() != null) {
				other.setOtherCenterResult(result.getResult());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("查询卖家中心(findShopInfoById查询卖家店铺信息-没有查到数据 返回错误码和错误信息:{}",
						result.getCode() + result.getResultMessage());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SHOPINFO_IS_NULL.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.SHOPINFO_IS_NULL.getCode());
			}

		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法findShopInfoById查询卖家店铺信息出现异常{}", w.toString());
		}
		return other;
	}

}
