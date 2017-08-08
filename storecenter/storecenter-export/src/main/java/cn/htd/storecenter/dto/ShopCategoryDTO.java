package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

import cn.htd.storecenter.emums.ShopEnums.ShopCategoryStatus;

/**
 * 
 * <p>
 * Description: [店铺经营类目]
 * </p>
 */
public class ShopCategoryDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long[] shopIds;// 入参用店铺ID组
	private Long shopId;// 铺店id
	private Long cid;// 平台类目id
	private Long parentCid; //父级类目ID
	private Integer categoryLevel; //类目级别
	private Long sellerId;// 卖家id
	private String status;// 类目状态;1是申请，2是通过，3是驳回，4是关闭，-1是删除
	private String statusLage; // 类目状态;1是申请，2是通过，3是驳回，4是关闭
	private String comment="";// 批注
	private Long createId; //创建人Id
	private String createName; //创建人名称
	private Date created;// 创建时间
	private Long modifyId; //更新人Id
	private String modifyName; //更新人名称
	private Date modified;// 更新时间

	public String getStatus() {
		return status;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public Date getCreated() {
		return created;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {

		this.modifyName = modifyName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	private Date passTime;// 审核通过时间
	private Long isGroupBy;// 是否根据店铺IDgroupBy

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

	public Long getCid() {
		return this.cid;
	}

	public void setCid(Long value) {
		this.cid = value;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long value) {
		this.sellerId = value;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String value) {
		this.comment = value;
	}



	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date value) {
		this.modified = value;
	}

	public Date getPassTime() {
		return this.passTime;
	}

	public void setPassTime(Date value) {
		this.passTime = value;
	}

	public String getStatusLage() {
		return statusLage;
	}

	public Long[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(Long[] shopIds) {
		this.shopIds = shopIds;
	}

	public void setStatusLage(String status) {
		ShopCategoryStatus shopCategoryStatus = ShopCategoryStatus.getEnumBycode(Integer.parseInt(status));
		if (shopCategoryStatus != null) {
			this.statusLage = shopCategoryStatus.getLabel();
		}
		this.status = status;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public Long getParentCid() {
		return parentCid;
	}

	public void setParentCid(Long parentCid) {
		this.parentCid = parentCid;
	}

}
