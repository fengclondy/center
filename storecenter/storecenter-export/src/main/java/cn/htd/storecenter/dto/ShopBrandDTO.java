package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShopBrandDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long[] shopIds;// 入参用店铺ID组
	private Long shopId;// 铺店id
	private Long brandId;// 品牌id
	private String status;// 店铺品牌状态：1为申请，2为通过，3为驳回，-1为删除
	private Date createTime;// 创建时间
	private Date modifyTime;// 更新时间
	private Long sellerId;// 卖家id
	private Long categoryId;// 三级类目id
	private Date passTime;//审核通过时间
	private Long createId; //创建人ID
	private String createName; //创建人姓名
	private Long modifyId; //修改人ID
	private String modifyName; //修改人姓名
	private Long isGroupBy;// 是否根据店铺IDgroupBy
	private List<Long> brandIds; //品牌Id集合
	private Long parentCid;
	private String brandName;
	private String categoryName;

	/**
	 *  品类编码集合 - 目前供新系统VMS用
	 */
	private List<Long> categoryIdList;
	
	/**
	 *  品牌编码集合 - 目前供新系统VMS用
	 */
	private List<Long> brandIdList;

	/**
	 * 排序类型 空：老系统；1：新vms
	 */
	private int orderByType;

	public Long getIsGroupBy() {
		return isGroupBy;
	}

	public void setIsGroupBy(Long isGroupBy) {
		this.isGroupBy = isGroupBy;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long value) {
		this.shopId = value;
	}

	public Long getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Long value) {
		this.brandId = value;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public Date getPassTime() {
		return passTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long value) {
		this.sellerId = value;
	}


	public Long[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(Long[] shopIds) {
		this.shopIds = shopIds;
	}

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public Long getParentCid() {
		return parentCid;
	}

	public void setParentCid(Long parentCid) {
		this.parentCid = parentCid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	

	public List<Long> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Long> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public List<Long> getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(List<Long> brandIdList) {
		this.brandIdList = brandIdList;
	}

	@Override
	public String toString() {
		return "ShopBrandDTO [id=" + id + ", shopIds=" + Arrays.toString(shopIds) + "，shopId=" + shopId + ", brandId="
				+ brandId + ", status=" + status + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", sellerId="
				+ sellerId + ", categoryId=" + categoryId + ", passTime=" + passTime + ", createId=" + createId +
				", createName="+createName+", modifyId="+modifyId+", modifyName="+modifyName+ "，isGroupBy=" + isGroupBy + "]";
	}

	public int getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(int orderByType) {
		this.orderByType = orderByType;
	}
}
