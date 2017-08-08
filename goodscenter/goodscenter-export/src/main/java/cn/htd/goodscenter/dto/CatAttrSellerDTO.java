package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

public class CatAttrSellerDTO implements Serializable {

	private static final long serialVersionUID = 1462162923118203796L;

	private Long sellerAttrId;
	private Long sellerId;// 卖家ID 必填
	private Long shopId;// 商家ID 必填
	private Long cid;// 平台类目ID 必填
	private Integer attrType;// 属性类型:1:销售属性;2:非销售属性 必填
	private ItemAttrDTO attr;// 卖家商品类目属性 添加时必填
	private ItemAttrValueDTO attrValue;// 卖家商品类目属性值 添加时必填
	private Long selectType;//是否多选  1：单选 2：多选
	private Integer sortNumber; //排序号
	private Integer attrStatus;
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public ItemAttrDTO getAttr() {
		return attr;
	}

	public void setAttr(ItemAttrDTO attr) {
		this.attr = attr;
	}

	public ItemAttrValueDTO getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(ItemAttrValueDTO attrValue) {
		this.attrValue = attrValue;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getAttrStatus() {
		return attrStatus;
	}

	public void setAttrStatus(Integer attrStatus) {
		this.attrStatus = attrStatus;
	}

	public Long getSellerAttrId() {
		return sellerAttrId;
	}

	public void setSellerAttrId(Long sellerAttrId) {
		this.sellerAttrId = sellerAttrId;
	}

	public Long getSelectType() {
		return selectType;
	}

	public void setSelectType(Long selectType) {
		this.selectType = selectType;
	}
}
