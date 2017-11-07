package cn.htd.zeus.tc.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.biz.dao.OrderFreightDAO;
import cn.htd.zeus.tc.biz.dmo.OrderFeightPromotionDMO;
import cn.htd.zeus.tc.biz.dmo.OrderFreightCalcuRuleDMO;
import cn.htd.zeus.tc.biz.dmo.OrderFreightInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderFreightSkuAttrDMO;
import cn.htd.zeus.tc.biz.service.OrderFreightInfoService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.BigDecimalUtil;
import cn.htd.zeus.tc.common.util.CalculateUtils;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;
import cn.htd.zeus.tc.dto.OrderSkuInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderFreightInfoDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;

@Service("orderFreightInfoService")
public class OrderFreightInfoServiceImpl implements OrderFreightInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFreightInfoServiceImpl.class);

	@Resource
	private OrderFreightDAO orderFreightDAO;

	/*
	 * hzy专用
	 * 
	 * @see cn.htd.zeus.tc.biz.service.OrderFreightInfoService#
	 * calculateOrderItemFeight(java.util.List, java.lang.String)
	 */
	@Override
	public void calculateOrderItemFeight(List<OrderSellerInfoDTO> sellerList, String citySiteCode) {
		LOGGER.info("订单结算---计算外部供应商运费开始入参sellerList{}:" + JSONObject.toJSONString(sellerList)
				+ " citySiteCode{}:" + citySiteCode);
		List<Long> templateIdArr = new ArrayList<Long>();
		List<OrderFreightSkuAttrDMO> feightInfoList = new ArrayList<OrderFreightSkuAttrDMO>();
		// 初始化需要计算运费的商品属性
		for (OrderSellerInfoDTO seller : sellerList) {
			List<OrderSkuInfoDTO> skuInfoList = seller.getSkuInfoList();
			for (OrderSkuInfoDTO skuInfo : skuInfoList) {
				if (Constant.PRODUCT_CHANNEL_CODE_OUTER.equals(skuInfo.getItemChannel())) {
					OrderFreightSkuAttrDMO orderFreightInfoDTO = new OrderFreightSkuAttrDMO();
					orderFreightInfoDTO.setSkuCode(skuInfo.getSkuCode());
					orderFreightInfoDTO.setHeight(skuInfo.getHeight());
					orderFreightInfoDTO.setLength(skuInfo.getLength());
					orderFreightInfoDTO.setNetWeight(skuInfo.getNetWeight());
					orderFreightInfoDTO.setPrice(skuInfo.getPrice());
					orderFreightInfoDTO.setTemplateId(skuInfo.getShopFreightTemplateId());
					orderFreightInfoDTO.setWeight(skuInfo.getWeight());
					orderFreightInfoDTO.setWeightUnit(skuInfo.getWeightUnit());
					orderFreightInfoDTO.setProductCount(skuInfo.getProductCount());
					orderFreightInfoDTO.setWidth(skuInfo.getWidth());
					feightInfoList.add(orderFreightInfoDTO);
					long templateId = skuInfo.getShopFreightTemplateId();
					if (!templateIdArr.contains(templateId)) {
						templateIdArr.add(templateId);
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(templateIdArr)) {
			// 处理运费逻辑
			executeFreight(templateIdArr, feightInfoList, citySiteCode);
			for (OrderSellerInfoDTO seller : sellerList) {
				List<OrderSkuInfoDTO> skuInfoList = seller.getSkuInfoList();
				for (OrderSkuInfoDTO skuInfo : skuInfoList) {
					for (OrderFreightSkuAttrDMO skuAttr : feightInfoList) {
						if (skuInfo.getSkuCode().equals(skuAttr.getSkuCode())) {
							skuInfo.setFreight(skuAttr.getFreight());
						}
					}
				}
			}
		}
	}

	/*
	 * zhangding 专用 --创建订单逻辑使用
	 * 
	 * @see cn.htd.zeus.tc.biz.service.OrderFreightInfoService#
	 * calculateOrderItemFeight4CreateOrder(java.util.List, java.lang.String)
	 */
	@Override
	public void calculateOrderItemFeight4CreateOrder(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode) {
		LOGGER.info("MessageId:{} 创建订单---计算外部供应商运费开始入参orderItemList:{} citySiteCode{}" ,"", JSONObject.toJSONString(orderItemList)
				, citySiteCode);
		List<Long> templateIdArr = new ArrayList<Long>();
		List<OrderFreightSkuAttrDMO> feightInfoList = new ArrayList<OrderFreightSkuAttrDMO>();
		// 初始化需要计算运费的商品属性
		for (OrderCreateItemListInfoReqDTO skuInfo : orderItemList) {
			OrderFreightSkuAttrDMO orderFreightInfoDTO = new OrderFreightSkuAttrDMO();
			orderFreightInfoDTO.setSkuCode(skuInfo.getSkuCode());
			orderFreightInfoDTO.setHeight(skuInfo.getHeight());
			orderFreightInfoDTO.setLength(skuInfo.getLength());
			orderFreightInfoDTO.setNetWeight(skuInfo.getNetWeight());
			orderFreightInfoDTO.setPrice(skuInfo.getGoodsPrice());
			orderFreightInfoDTO.setTemplateId(skuInfo.getShopFreightTemplateId());
			orderFreightInfoDTO.setWeight(skuInfo.getWeight());
			orderFreightInfoDTO.setWeightUnit(skuInfo.getWeightUnit());
			orderFreightInfoDTO.setProductCount(skuInfo.getGoodsCount().intValue());
			orderFreightInfoDTO.setWidth(skuInfo.getWidth());
			feightInfoList.add(orderFreightInfoDTO);
			long templateId = skuInfo.getShopFreightTemplateId();
			if (!templateIdArr.contains(templateId)) {
				templateIdArr.add(templateId);
			}
		}

		if (CollectionUtils.isNotEmpty(templateIdArr)) {
			// 处理运费逻辑
			executeFreight(templateIdArr, feightInfoList, citySiteCode);
			for (OrderCreateItemListInfoReqDTO skuInfo : orderItemList) {
				for (OrderFreightSkuAttrDMO skuAttr : feightInfoList) {
					if (skuInfo.getSkuCode().equals(skuAttr.getSkuCode())) {
						skuInfo.setGoodsFreight(skuAttr.getFreight());
					}
				}
			}
		}
	}

	@Override
	public OrderFreightInfoDTO calculateOrderItemFeight4seckill(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode) {
		LOGGER.info("MessageId:{} 创建订单---计算超级老板秒杀运费开始入参orderItemList:{} citySiteCode{}:" ,"", JSONObject.toJSONString(orderItemList)
				, citySiteCode);
		OrderFreightInfoDTO orderFreightDTO = new OrderFreightInfoDTO();
		List<Long> templateIdArr = new ArrayList<Long>();
		List<OrderFreightSkuAttrDMO> feightInfoList = new ArrayList<OrderFreightSkuAttrDMO>();
		// 初始化需要计算运费的商品属性
		for (OrderCreateItemListInfoReqDTO skuInfo : orderItemList) {
			OrderFreightSkuAttrDMO orderFreightInfoDTO = new OrderFreightSkuAttrDMO();
			orderFreightInfoDTO.setSkuCode(skuInfo.getSkuCode());
			orderFreightInfoDTO.setHeight(skuInfo.getHeight());
			orderFreightInfoDTO.setLength(skuInfo.getLength());
			orderFreightInfoDTO.setNetWeight(skuInfo.getNetWeight());
			orderFreightInfoDTO.setPrice(skuInfo.getGoodsPrice());
			orderFreightInfoDTO.setTemplateId(skuInfo.getShopFreightTemplateId());
			orderFreightInfoDTO.setWeight(skuInfo.getWeight());
			orderFreightInfoDTO.setWeightUnit(skuInfo.getWeightUnit());
			orderFreightInfoDTO.setProductCount(skuInfo.getGoodsCount().intValue());
			orderFreightInfoDTO.setWidth(skuInfo.getWidth());
			feightInfoList.add(orderFreightInfoDTO);
			long templateId = skuInfo.getShopFreightTemplateId();
			if (!templateIdArr.contains(templateId)) {
				templateIdArr.add(templateId);
			}
		}

		if (CollectionUtils.isNotEmpty(templateIdArr)) {
			try {
				// 处理运费逻辑
				executeFreight(templateIdArr, feightInfoList, citySiteCode);
				for (OrderCreateItemListInfoReqDTO skuInfo : orderItemList) {
					for (OrderFreightSkuAttrDMO skuAttr : feightInfoList) {
						if (skuInfo.getSkuCode().equals(skuAttr.getSkuCode())) {
							orderFreightDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
							orderFreightDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
							orderFreightDTO.setFreight(skuAttr.getFreight());
						}
					}
				}
			} catch (Exception e) {
				orderFreightDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
				orderFreightDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			}
		}
		return orderFreightDTO;
	}

	/*
	 * 处理运费逻辑
	 */
	private void executeFreight(List<Long> templateIdArr,
			List<OrderFreightSkuAttrDMO> feightInfoList, String citySiteCode) {
		// 需要计算运费的运费模板集合
		List<OrderFreightInfoDMO> templateInfoList = new ArrayList<OrderFreightInfoDMO>();
		for (Long id : templateIdArr) {
			LOGGER.info("计算运费---查询店铺运费模板表,店铺运费优惠表,店铺运费定义表  开始,入参模板id{}:" + id);
			OrderFreightInfoDMO freightInfo = orderFreightDAO.queryOrderFreightInfoByTemplateId(id);
			LOGGER.info("计算运费---查询  店铺运费模板表  结束,查询结果{}:" + JSONObject.toJSONString(freightInfo));
			if (freightInfo != null) {
				OrderFeightPromotionDMO orderFeightPromotionDMO = orderFreightDAO
						.queryOrderFreightPromotionInfoByTemplateId(id);
				LOGGER.info("计算运费---查询  店铺运费优惠表  结束,查询结果{}:"
						+ JSONObject.toJSONString(orderFeightPromotionDMO));
				if (orderFeightPromotionDMO != null) {
					freightInfo.setOrderFeightPromotionDMO(orderFeightPromotionDMO);
				}
				List<OrderFreightCalcuRuleDMO> ruleList = orderFreightDAO
						.queryOrderFreightCalcuRuleByTemplateId(id);
				LOGGER.info("计算运费---查询  店铺运费定义表  结束,查询结果{}:" + JSONObject.toJSONString(ruleList));
				freightInfo.setRule(ruleList);
				for (OrderFreightSkuAttrDMO orderFreightInfoDTO : feightInfoList) {
					if (orderFreightInfoDTO.getTemplateId() == id) {
						freightInfo.getSkuList().add(orderFreightInfoDTO);
					}
				}
				// 如果优惠政策是包邮，则商品运费置为0，不插入运费计算集合
				if (freightInfo != null && "2".equals(freightInfo.getPostageFree())) {
					this.setItemFreight(feightInfoList, id, BigDecimal.ZERO);
				} else {
					templateInfoList.add(freightInfo);
				}
			} else {
				// 如果运费模板规则为空则直接设置运费为空
				this.setItemFreight(feightInfoList, id, BigDecimal.ZERO);
			}
		}
		for (OrderFreightInfoDMO freightInfo : templateInfoList) {
			if (Constant.ORDER_VALUATION_WAY_NUMBER.equals(freightInfo.getValuationWay())) {
				calculateFreight4number(freightInfo, citySiteCode);
			} else if (Constant.ORDER_VALUATION_WAY_VOLUME.equals(freightInfo.getValuationWay())) {
				calculateFreight4volume(freightInfo, citySiteCode);
			} else if (Constant.ORDER_VALUATION_WAY_WEIGHT.equals(freightInfo.getValuationWay())) {
				calculateFreight4weight(freightInfo, citySiteCode);
			}
		}
	}

	/**
	 * 计算计件方式的运费价格
	 * 
	 * @param freightInfo
	 */
	private static void calculateFreight4number(OrderFreightInfoDMO freightInfo,
			String citySiteCode) {
		int productCount = 0;
		double price = 0L;
		List<OrderFreightSkuAttrDMO> skuList = freightInfo.getSkuList();
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			productCount += skuAttr.getProductCount();
			price = BigDecimalUtil.add(price,
					skuAttr.getPrice().doubleValue() * skuAttr.getProductCount());
		}
		OrderFreightCalcuRuleDMO ruleDMO = getCurrentFreightRule(freightInfo, citySiteCode);
		double freight = 0L;
		BigDecimal firstPart = ruleDMO.getFirstPart();
		BigDecimal count = new BigDecimal(productCount);
		if (firstPart.compareTo(count) >= 0) {
			freight = ruleDMO.getFirstPrice().doubleValue();
		} else {
			double sparePart = BigDecimalUtil.sub(count.doubleValue(), firstPart.doubleValue());
			double continuePart = ruleDMO.getContinuePart().doubleValue();
			double scale = Math.ceil(BigDecimalUtil.div(sparePart, continuePart));
			freight = ruleDMO.getFirstPrice().doubleValue()
					+ scale * ruleDMO.getContinuePrice().doubleValue();
		}
		if (freightInfo.getOrderFeightPromotionDMO() != null && StringUtilHelper
				.isNotNull(freightInfo.getOrderFeightPromotionDMO().getStrategy())) {
			// 计算运费优惠
			LOGGER.info("计算运费---计算运费模板优惠,查询结果{}:"
					+ JSONObject.toJSONString(freightInfo.getOrderFeightPromotionDMO()));
			freight = calculateFreightPromotion(freightInfo.getOrderFeightPromotionDMO(),
					productCount, price, freight);
		}
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			skuAttr.setFreight(CalculateUtils.setScale(new BigDecimal(freight / skuList.size())));
		}
	}

	/**
	 * 计算计体积方式的运费价格
	 * 
	 * @param freightInfo
	 */
	private static void calculateFreight4volume(OrderFreightInfoDMO freightInfo,
			String citySiteCode) {
		double price = 0L;
		double volume = 0L;
		List<OrderFreightSkuAttrDMO> skuList = freightInfo.getSkuList();
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			int productCount = skuAttr.getProductCount();
			price = BigDecimalUtil.add(price,
					skuAttr.getPrice().doubleValue() * skuAttr.getProductCount());
			// 计算体积 计算结果单位 立方米
			volume += BigDecimalUtil.mul(
					BigDecimalUtil.mul(
							BigDecimalUtil.mul(skuAttr.getLength().doubleValue() / 10,
									skuAttr.getWidth().doubleValue() / 10),
					skuAttr.getHeight().doubleValue() / 10),
					Double.parseDouble(String.valueOf(productCount)));
		}
		OrderFreightCalcuRuleDMO ruleDMO = getCurrentFreightRule(freightInfo, citySiteCode);
		double freight = 0L;
		BigDecimal firstPart = ruleDMO.getFirstPart();
		if (firstPart.doubleValue() >= volume) {
			freight = ruleDMO.getFirstPrice().doubleValue();
		} else {
			double sparePart = BigDecimalUtil.sub(volume, firstPart.doubleValue());
			double continuePart = ruleDMO.getContinuePart().doubleValue();
			double scale = Math.ceil(BigDecimalUtil.div(sparePart, continuePart));
			freight = ruleDMO.getFirstPrice().doubleValue()
					+ scale * ruleDMO.getContinuePrice().doubleValue();
		}
		if (freightInfo.getOrderFeightPromotionDMO() != null && StringUtilHelper
				.isNotNull(freightInfo.getOrderFeightPromotionDMO().getStrategy())) {
			// 计算运费优惠
			LOGGER.info("计算运费---计算运费模板优惠,查询结果{}:"
					+ JSONObject.toJSONString(freightInfo.getOrderFeightPromotionDMO()));
			freight = calculateFreightPromotion4volumn(freightInfo.getOrderFeightPromotionDMO(),
					volume, price, freight);
		}
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			skuAttr.setFreight(CalculateUtils.setScale(new BigDecimal(freight / skuList.size())));
		}
	}

	/**
	 * 计算计体重方式的运费价格
	 * 
	 * @param freightInfo
	 */
	private static void calculateFreight4weight(OrderFreightInfoDMO freightInfo,
			String citySiteCode) {
		double price = 0L;
		double weight = 0L;
		List<OrderFreightSkuAttrDMO> skuList = freightInfo.getSkuList();
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			price = BigDecimalUtil.add(price,
					skuAttr.getPrice().doubleValue() * skuAttr.getProductCount());
			// 计算体积 计算结果单位 立方米
			weight = BigDecimalUtil.add(weight,
					skuAttr.getWeight().doubleValue() * skuAttr.getProductCount());
		}
		OrderFreightCalcuRuleDMO ruleDMO = getCurrentFreightRule(freightInfo, citySiteCode);
		double freight = 0L;
		BigDecimal firstPart = ruleDMO.getFirstPart();
		if (firstPart.doubleValue() >= weight) {
			freight = ruleDMO.getFirstPrice().doubleValue();
			LOGGER.info("计算运费第一模板价格:" + freight);
		} else {
			double sparePart = BigDecimalUtil.sub(weight, firstPart.doubleValue());
			double continuePart = ruleDMO.getContinuePart().doubleValue();
			double scale = Math.ceil(BigDecimalUtil.div(sparePart, continuePart));
			freight = ruleDMO.getFirstPrice().doubleValue()
					+ scale * ruleDMO.getContinuePrice().doubleValue();
			LOGGER.info("计算运费第二模板价格:" + freight);
		}
		if (freightInfo.getOrderFeightPromotionDMO() != null && StringUtilHelper
				.isNotNull(freightInfo.getOrderFeightPromotionDMO().getStrategy())) {
			// 计算运费优惠
			LOGGER.info("计算运费---计算运费模板优惠,查询结果{}:"
					+ JSONObject.toJSONString(freightInfo.getOrderFeightPromotionDMO()));
			freight = calculateFreightPromotion4weight(freightInfo.getOrderFeightPromotionDMO(),
					weight, price, freight);
		}
		for (OrderFreightSkuAttrDMO skuAttr : skuList) {
			skuAttr.setFreight(CalculateUtils.setScale(new BigDecimal(freight / skuList.size())));
			LOGGER.info("计算运费结果商品skuCode---:" + skuAttr.getSkuCode());
			LOGGER.info("计算运费结果商品运费价格---:" + skuAttr.getFreight());
		}
	}

	private static double compareFreightValue(double d1, double d2) {
		double freight = 0L;
		if (Double.compare(d1, d2) > 0) {
			freight = BigDecimalUtil.sub(d1, d2);
		}
		return freight;
	}

	private static double calculateFreightPromotion(OrderFeightPromotionDMO freightPromotion,
			int productCount, double price, double freight) {
		if ("1".equals(freightPromotion.getStrategy())) {
			// 如果是满减优惠，并且购买商品数量大于优惠规则数量，则在运费基础上减去优惠价格
			if ("1".equals(freightPromotion.getPreferentialWay())
					&& productCount >= freightPromotion.getFull().intValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());
			}
		} else {
			if ("1".equals(freightPromotion.getPreferentialWay())
					&& productCount >= freightPromotion.getFull().intValue()) {
				freight = 0L;
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = 0L;
			}
		}
		return freight;
	}

	private static double calculateFreightPromotion4volumn(OrderFeightPromotionDMO freightPromotion,
			double volumn, double price, double freight) {
		if ("1".equals(freightPromotion.getStrategy())) {
			// 如果是满减优惠，并且购买商品数量大于优惠规则数量，则在运费基础上减去优惠价格
			if ("3".equals(freightPromotion.getPreferentialWay())
					&& volumn >= freightPromotion.getFull().doubleValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());

			}
		} else {
			if ("3".equals(freightPromotion.getPreferentialWay())
					&& volumn >= freightPromotion.getFull().doubleValue()) {
				freight = 0L;
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = 0L;
			}
		}
		return freight;
	}

	private static double calculateFreightPromotion4weight(OrderFeightPromotionDMO freightPromotion,
			double weight, double price, double freight) {
		if ("1".equals(freightPromotion.getStrategy())) {
			// 如果是满减优惠，并且购买商品数量大于优惠规则数量，则在运费基础上减去优惠价格
			if ("2".equals(freightPromotion.getPreferentialWay())
					&& weight >= freightPromotion.getFull().doubleValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = compareFreightValue(freight, freightPromotion.getReduce().doubleValue());
			}
		} else {
			if ("2".equals(freightPromotion.getPreferentialWay())
					&& weight >= freightPromotion.getFull().doubleValue()) {
				freight = 0L;
				// 如果是满元优惠，并且购买商品价格大于优惠规则价格，则在运费基础上减去优惠价格
			} else if ("4".equals(freightPromotion.getPreferentialWay())
					&& price >= freightPromotion.getFull().doubleValue()) {
				freight = 0L;
			}
		}
		return freight;
	}

	/**
	 * 获取当前生效的运费计算规则
	 * 
	 * @param freightInfo
	 * @param citySiteCode
	 * @return
	 */
	private static OrderFreightCalcuRuleDMO getCurrentFreightRule(OrderFreightInfoDMO freightInfo,
			String citySiteCode) {
		boolean flag = false;
		OrderFreightCalcuRuleDMO ruleDMO = new OrderFreightCalcuRuleDMO();
		List<OrderFreightCalcuRuleDMO> ruleList = freightInfo.getRule();
		for (OrderFreightCalcuRuleDMO rule : ruleList) {
			if (rule.getDeliveryTo().contains(citySiteCode)) {
				ruleDMO = rule;
				flag = true;
				break;
			}
		}
		if (!flag) {
			for (OrderFreightCalcuRuleDMO rule : ruleList) {
				if ("0".equals(rule.getDeliveryTo())) {
					ruleDMO = rule;
				}
			}
		}
		return ruleDMO;
	}

	/**
	 * 设置商品运费价格
	 * 
	 * @param feightInfoList
	 * @param templateId
	 * @param freight
	 */
	private void setItemFreight(List<OrderFreightSkuAttrDMO> feightInfoList, long templateId,
			BigDecimal freight) {
		for (OrderFreightSkuAttrDMO freightInfo : feightInfoList) {
			if (freightInfo.getTemplateId() == templateId) {
				freightInfo.setFreight(freight);
			}
		}
	}

	public static void main(String[] args) {
		OrderFreightInfoDMO test = new OrderFreightInfoDMO();
		test.setTemplateId(71);
		test.setPostageFree("1");
		test.setValuationWay("3");
		OrderFeightPromotionDMO promotion = new OrderFeightPromotionDMO();
		promotion.setFull(new BigDecimal("40.0000"));
		promotion.setReduce(new BigDecimal("1000.0000"));
		promotion.setStrategy("1");
		promotion.setPreferentialWay("4");
		test.setOrderFeightPromotionDMO(promotion);
		OrderFreightCalcuRuleDMO rule = new OrderFreightCalcuRuleDMO();
		rule.setDeliveryType("1");
		rule.setFirstPart(new BigDecimal("100.0000"));
		rule.setFirstPrice(new BigDecimal("5.0000"));
		rule.setContinuePart(new BigDecimal("20.0000"));
		rule.setContinuePrice(new BigDecimal("3.0000"));
		rule.setDeliveryTo("0");
		rule.setDeliveryAddress("0");

		OrderFreightCalcuRuleDMO rule2 = new OrderFreightCalcuRuleDMO();
		rule2.setDeliveryType("1");
		rule2.setFirstPart(new BigDecimal("144.0000"));
		rule2.setFirstPrice(new BigDecimal("10.0000"));
		rule2.setContinuePart(new BigDecimal("72.0000"));
		rule2.setContinuePrice(new BigDecimal("5.0000"));
		rule2.setDeliveryTo("3203、3201、32、");
		rule2.setDeliveryAddress("徐州市、南京市、江苏、");

		List<OrderFreightCalcuRuleDMO> ruleList = new ArrayList<OrderFreightCalcuRuleDMO>();
		ruleList.add(rule);
		ruleList.add(rule2);
		test.setRule(ruleList);
		OrderFreightSkuAttrDMO sku1 = new OrderFreightSkuAttrDMO();
		sku1.setSkuCode("1000013647");
		sku1.setTemplateId(71);
		sku1.setPrice(new BigDecimal("50.0000"));
		sku1.setWeight(new BigDecimal("20.0000"));
		sku1.setWeightUnit("pi2");
		sku1.setProductCount(10);
		sku1.setNetWeight(new BigDecimal("10"));
		sku1.setLength(new BigDecimal("100.0000"));
		sku1.setWidth(new BigDecimal("200.0000"));
		sku1.setHeight(new BigDecimal("300.0000"));
		sku1.setProductCount(2);
		List<OrderFreightSkuAttrDMO> skuList = new ArrayList<OrderFreightSkuAttrDMO>();
		skuList.add(sku1);
		test.setSkuList(skuList);
		calculateFreight4volume(test, "3201");
	}

	@Override
	public OrderFreightInfoDTO validateOrderItemList(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode) {
		for (OrderCreateItemListInfoReqDTO orderInfo : orderItemList) {

		}
		return null;
	}

}
