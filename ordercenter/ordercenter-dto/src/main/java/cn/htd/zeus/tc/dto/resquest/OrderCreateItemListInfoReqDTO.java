package cn.htd.zeus.tc.dto.resquest;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class OrderCreateItemListInfoReqDTO extends OrderCreateItemListInfoReqMarketDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1873392277557460721L;

	//订单行号
	@NotEmpty(message = "orderItemNo不能为空")
	private String orderItemNo;
	
	//商品SKU编码
	@NotEmpty(message = "skuCode不能为空")
	private String skuCode;
	
	//商品数量
	@NotNull(message = "goodsCount不能为空")
	private Long goodsCount;
	
	//渠道编码
	private String channelCode;
	
	//是否包厢标志  0：否，1：是
	@NotNull(message = "isBoxFlag不能为空")
	private Integer isBoxFlag;
	
	//是否有经营关系  1 有 0 没有
	@NotNull(message = "isHasDevRelation不能为空")
	private Integer isHasDevRelation;

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public Integer getIsHasDevRelation() {
		return isHasDevRelation;
	}

	public void setIsHasDevRelation(Integer isHasDevRelation) {
		this.isHasDevRelation = isHasDevRelation;
	}
}
