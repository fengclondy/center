package cn.htd.zeus.tc.api.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.api.OrderSettleMentAPI;
import cn.htd.zeus.tc.biz.service.OrderFreightInfoService;
import cn.htd.zeus.tc.biz.service.OrderSettleMentService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DTOValidateUtil;
import cn.htd.zeus.tc.common.util.ValidateResult;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.OrderFreightInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

@Service("orderSettleMentAPI")
public class OrderSettleMentAPIImpl implements OrderSettleMentAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSettleMentAPIImpl.class);

	@Autowired
	private OrderSettleMentService orderSettleMentService;

	@Autowired
	private OrderFreightInfoService orderFreightInfoService;

	@Override
	public OrderSettleMentResDTO getOrderSettleMentInfo(OrderSettleMentReqDTO orderSettleMentReqDTO) {
		String messageId = orderSettleMentReqDTO.getMessageId();
		String isSeckill = orderSettleMentReqDTO.getIsSeckill();
		OrderSettleMentResDTO resDTO = new OrderSettleMentResDTO();
		resDTO.setMessageId(messageId);
		try {
			resDTO.setIsSeckill(isSeckill);
			// 校验传入参数
			ValidateResult validateResult = DTOValidateUtil.validate(orderSettleMentReqDTO);
			if (!validateResult.isPass()) {
				resDTO.setReponseMsg(validateResult.getReponseMsg());
				resDTO.setResponseCode(ResultCodeEnum.ORDERCENTER_CREAREORDER_PARAM_IS_NULL.getCode());
				return resDTO;
			} else {
				resDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				resDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			}
			if (Constant.PROMOTION_SECKILL_FLAG.equals(isSeckill)) {
				// 封装商品sku详情信息查询参数
				orderSettleMentService.packageSkuInfo4goodsService(orderSettleMentReqDTO, resDTO);
				// // // 处理促销活动信息
				// if (resDTO.getResponseCode() ==
				// ResultCodeEnum.SUCCESS.getCode()) {
				// // 查询可用优惠券集合
				// orderSettleMentService.getCouponAvailableList(orderSettleMentReqDTO,
				// resDTO);
				// }
			} else {
				// 处理秒杀活动信息
				orderSettleMentService.getSeckillInfo(orderSettleMentReqDTO, resDTO);
				if (resDTO.getResponseCode() == ResultCodeEnum.SUCCESS.getCode()) {
					// 锁定秒杀商品库存
					orderSettleMentService.reserveSeckillproductStock(orderSettleMentReqDTO, resDTO);
				}
				// // 处理促销活动信息
				if (resDTO.getResponseCode() == ResultCodeEnum.SUCCESS.getCode()) {
					// 处理秒杀商品数据
					orderSettleMentService.packageSeckillInfo(orderSettleMentReqDTO, resDTO);
				}
			}
			// 查询用户收货地址集合
			// if (resDTO.getResponseCode() ==
			// ResultCodeEnum.SUCCESS.getCode()) {
			// orderSettleMentService.getConsigAddressList(resDTO, memberId,
			// messageId, citySiteCode);
			// }
			// // 校验并整合收货地址信息到返回对象
			// orderSettleMentService.checkAndAssembleRes(res4consig,
			// resDTO, "consig");
			// // 查询用户发票信息
			// OrderSettleMentResDTO res4invoiceInfo =
			// orderSettleMentService.getInvoiceInfo(memberId, messageId);
			// // 校验并整合发票信息到返回对象
			// orderSettleMentService.checkAndAssembleRes(res4invoiceInfo,
			// resDTO, "invoice");
		} catch (Exception e) {
			resDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			resDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			LOGGER.error("MessageId:{} 调用方法OrderSettleMentAPIImpl.getOrderSettleMentInfo出现异常{}", messageId,
					e.toString());
		}
		return resDTO;
	}

	@Override
	public int getJDproductStock(String outerSkuId, String sellerCode, String messageId) {
		return orderSettleMentService.queryJDproductStock(outerSkuId, sellerCode, messageId);
	}

	@Override
	public OtherCenterResDTO<String> getJDproductStock4xj(String outerSkuId, String sellerCode, String messageId) {
		return orderSettleMentService.queryJDproductStock4xj(outerSkuId, sellerCode, messageId);
	}

	@Override
	public OrderFreightInfoDTO queryGoodsFreight4seckill(List<OrderCreateItemListInfoReqDTO> orderItemList,
			String citySiteCode) {
		OrderFreightInfoDTO orderFreightInfoDTO = orderFreightInfoService
				.calculateOrderItemFeight4seckill(orderItemList, citySiteCode);
		return orderFreightInfoDTO;
	}

}
