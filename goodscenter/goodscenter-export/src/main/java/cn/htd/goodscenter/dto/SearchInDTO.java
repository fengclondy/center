package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Description: [商品搜索入参]
 * </p>
 */
public class SearchInDTO implements Serializable {

	private static final long serialVersionUID = 8517476617481641622L;

	private List<Long> brandIds = new ArrayList<Long>();// 搜索传入条件
	private List<Long> categoryIds = new ArrayList<Long>();// 类目ID组 外部传入不当做查询条件用
	private Long cid;// 商品类目ID//搜索传入条件
	private String keyword;// 搜索关键字//搜索传入条件
	private String attributes;// 属性键值对字符串//搜索传入条件
	private String areaCode;// 地域编码//搜索传入条件
	private Long shopId;// 店铺ID
	private Long buyerId;// 买家ID
	private Long shopCid;// 店铺id

	private List<String> attrList = new ArrayList<String>();// 外部传入不当做查询条件用
	private List<Long> itemIds = new ArrayList<Long>();// 查询活动下所以商品

	private int orderSort = 2;// 1 时间升序 2时间降序 3评价升序 4评价降序 5销量升序 6销量降序 7销量升序
								// 8销量降序

	private Long payType;// 支付方式，1：货到付款

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<String> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<String> attrList) {
		this.attrList = attrList;
	}

	public int getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(int orderSort) {
		this.orderSort = orderSort;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getShopCid() {
		return shopCid;
	}

	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}

}
