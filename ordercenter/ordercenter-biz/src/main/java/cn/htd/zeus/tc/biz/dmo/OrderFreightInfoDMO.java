package cn.htd.zeus.tc.biz.dmo;

import java.util.ArrayList;
import java.util.List;

public class OrderFreightInfoDMO {

	// 运费模板ID
	private long templateId;

	// 是否包邮：1、自定义运费 2、卖家承担运费
	private String postageFree;

	// 运送方式，1快递，2EMS，3平邮.多个运送方式使用逗号隔开
	private String deliveryType;

	// 计价方式，1件数，2重量，3体积
	private String valuationWay;

	private OrderFeightPromotionDMO orderFeightPromotionDMO;

	private List<OrderFreightCalcuRuleDMO> rule = new ArrayList<OrderFreightCalcuRuleDMO>();

	private List<OrderFreightSkuAttrDMO> skuList = new ArrayList<OrderFreightSkuAttrDMO>();

	/**
	 * @return the templateId
	 */
	public long getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the postageFree
	 */
	public String getPostageFree() {
		return postageFree;
	}

	/**
	 * @param postageFree
	 *            the postageFree to set
	 */
	public void setPostageFree(String postageFree) {
		this.postageFree = postageFree;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType
	 *            the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the valuationWay
	 */
	public String getValuationWay() {
		return valuationWay;
	}

	/**
	 * @param valuationWay
	 *            the valuationWay to set
	 */
	public void setValuationWay(String valuationWay) {
		this.valuationWay = valuationWay;
	}

	/**
	 * @return the orderFeightPromotionDMO
	 */
	public OrderFeightPromotionDMO getOrderFeightPromotionDMO() {
		return orderFeightPromotionDMO;
	}

	/**
	 * @param orderFeightPromotionDMO
	 *            the orderFeightPromotionDMO to set
	 */
	public void setOrderFeightPromotionDMO(OrderFeightPromotionDMO orderFeightPromotionDMO) {
		this.orderFeightPromotionDMO = orderFeightPromotionDMO;
	}

	/**
	 * @return the rule
	 */
	public List<OrderFreightCalcuRuleDMO> getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(List<OrderFreightCalcuRuleDMO> rule) {
		this.rule = rule;
	}

	/**
	 * @return the skuList
	 */
	public List<OrderFreightSkuAttrDMO> getSkuList() {
		return skuList;
	}

	/**
	 * @param skuList
	 *            the skuList to set
	 */
	public void setSkuList(List<OrderFreightSkuAttrDMO> skuList) {
		this.skuList = skuList;
	}

}
