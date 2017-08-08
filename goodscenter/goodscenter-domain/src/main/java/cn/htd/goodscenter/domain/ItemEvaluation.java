package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [商品评价domain类]
 * </p>
 */
public class ItemEvaluation implements Serializable {

	private static final long serialVersionUID = 2915057628225610266L;
	private Long id;// id
	private Long userId;// 评价人
	private Long userShopId;// 评价人店铺id
	private Long byUserId;// 被评价人
	private Long byShopId;// 被评价店铺id
	private Long orderId;// 订单ID
	private Long itemId;// 商品id
	private Long skuId;// 商品sku
	private Integer skuScope;// sku评分
	private String content;// 评价内容
	private String resource = "2"; // 1：默认评价 2:手动评价
	private String type;// 1:买家对卖家评价 2:卖家对买家评价 3:售后评价
	private Date createTime;// 创建时间
	private Date modifyTime;// 编辑时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserShopId() {
		return userShopId;
	}

	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
	}

	public Long getByUserId() {
		return byUserId;
	}

	public void setByUserId(Long byUserId) {
		this.byUserId = byUserId;
	}

	public Long getByShopId() {
		return byShopId;
	}

	public void setByShopId(Long byShopId) {
		this.byShopId = byShopId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuScope() {
		return skuScope;
	}

	public void setSkuScope(Integer skuScope) {
		this.skuScope = skuScope;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
