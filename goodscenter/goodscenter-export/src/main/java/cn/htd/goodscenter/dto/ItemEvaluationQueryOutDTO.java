package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价查询结果输出DTO类]
 * </p>
 */
public class ItemEvaluationQueryOutDTO implements Serializable {

	private static final long serialVersionUID = -2729081174251794580L;
	private Long id;// id
	private Long userId;// 评价人
	private Long userShopId;// 评价人店铺id
	private Long byUserId;// 被评价人
	private Long byShopId;// 被评价店铺id
	private Long orderId;// 订单ID
	private Long itemId;// 商品id
	private Long skuId;// 商品sku
	private Integer skuScope;// 评分
	private Double skuAvgScope;// 平均分
	private String content;// 评价内容
	private String attributes;// 商品属性
	private String type;// 1:买家对卖家评价 2:卖家对买家评价 3:售后评价
	private Date createTime;// 创建时间
	private Date modifyTime;// 编辑时间
	private String itemName;// 商品名称
	private String skuName; // sku名称
	private String resource; // 1:默认回复 2:手动回复
	private Integer deletedFlag; // 是否逻辑删除标记：0在用；1停用，伪删除
	private String replaceContent;// 经过敏感字过滤的评价内容
	private boolean checkConfirm = false;// 订单确认时间是否超过一个月
	private List<ItemEvaluationReplyDTO> itemEvaluationReplyList; // 针对评价的回复

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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

	public Integer getSkuScope() {
		return skuScope;
	}

	public void setSkuScope(Integer skuScope) {
		this.skuScope = skuScope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<ItemEvaluationReplyDTO> getItemEvaluationReplyList() {
		return itemEvaluationReplyList;
	}

	public void setItemEvaluationReplyList(List<ItemEvaluationReplyDTO> itemEvaluationReplyList) {
		this.itemEvaluationReplyList = itemEvaluationReplyList;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getReplaceContent() {
		return replaceContent;
	}

	public void setReplaceContent(String replaceContent) {
		this.replaceContent = replaceContent;
	}

	public boolean isCheckConfirm() {
		return checkConfirm;
	}

	public void setCheckConfirm(boolean checkConfirm) {
		this.checkConfirm = checkConfirm;
	}

	public Double getSkuAvgScope() {
		return skuAvgScope;
	}

	public void setSkuAvgScope(Double skuAvgScope) {
		this.skuAvgScope = skuAvgScope;
	}

}
