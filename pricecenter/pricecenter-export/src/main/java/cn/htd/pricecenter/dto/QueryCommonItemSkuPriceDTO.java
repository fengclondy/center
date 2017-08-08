package cn.htd.pricecenter.dto;

import java.io.Serializable;

public class QueryCommonItemSkuPriceDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6741854779902607526L;
	//商品skuId
	private Long skuId;
	//是否包厢：1 包厢 0 非包厢
	private Integer isBoxFlag;
	//渠道 10 内部供应商 20 外部供应商 3010 外接商品
	private String itemChannelCode;
	//购买区域 ,全国用1 表示
	private String citySiteCode;
	//会员分组
	private Long memberGroupId;
	//买家星级
	private String buyerGrade;
	//是否有经营关系 1 有 0 没有
	private Integer isHasDevRelation;
	//是否已登录 1 已登录 0 未登录
	private Integer isLogin;
	//是否可卖京东商品 1 可以 0 不可以
	private Integer isCanSellProdplusItem;
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}
	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}
	
	public String getItemChannelCode() {
		return itemChannelCode;
	}
	public void setItemChannelCode(String itemChannelCode) {
		this.itemChannelCode = itemChannelCode;
	}
	public String getCitySiteCode() {
		return citySiteCode;
	}
	public void setCitySiteCode(String citySiteCode) {
		this.citySiteCode = citySiteCode;
	}
	
	public Long getMemberGroupId() {
		return memberGroupId;
	}
	public void setMemberGroupId(Long memberGroupId) {
		this.memberGroupId = memberGroupId;
	}
	public String getBuyerGrade() {
		return buyerGrade;
	}
	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}
	public Integer getIsHasDevRelation() {
		return isHasDevRelation;
	}
	public void setIsHasDevRelation(Integer isHasDevRelation) {
		this.isHasDevRelation = isHasDevRelation;
	}
	public Integer getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}
	public Integer getIsCanSellProdplusItem() {
		return isCanSellProdplusItem;
	}
	public void setIsCanSellProdplusItem(Integer isCanSellProdplusItem) {
		this.isCanSellProdplusItem = isCanSellProdplusItem;
	}
	
}
