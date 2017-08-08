package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [item_attr商家属性关联表]
 * </p>
 */
public class ItemAttrSeller extends AbstractDomain implements Serializable {

	private static final long serialVersionUID = -3655847602085448831L;

	private Long sellerAttrId;// ID
	private Long sellerId;// 买家ID
	private Long categoryId;// 类目ID
	private Long shopId;// 店铺ID
	private Long attrId;// 属性ID
	private String attrName;// 属性名称
	private Integer attrType;// 属性类型:1:销售属性;2:非销售属性
	private Long selectType;// 是否多选。1：单选；2：多选
	private Integer attrStatus;// 记录状态 0、有效；1、无效
	private Integer sortNumber;// 排序号
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private Long createId; //
	private String createName;
	private Long modifyId;
	private String modifyName;


	public Long getSellerAttrId() {
		return sellerAttrId;
	}

	public void setSellerAttrId(Long sellerAttrId) {
		this.sellerAttrId = sellerAttrId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public Long getSelectType() {
		return selectType;
	}

	public void setSelectType(Long selectType) {
		this.selectType = selectType;
	}

	public Integer getAttrStatus() {
		return attrStatus;
	}

	public void setAttrStatus(Integer attrStatus) {
		this.attrStatus = attrStatus;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	@Override
	public Long getCreateId() {
		return createId;
	}

	@Override
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Override
	public String getCreateName() {
		return createName;
	}

	@Override
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Override
	public Long getModifyId() {
		return modifyId;
	}

	@Override
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Override
	public String getModifyName() {
		return modifyName;
	}

	@Override
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}
