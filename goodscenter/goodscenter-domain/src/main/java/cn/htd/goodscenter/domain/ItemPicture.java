package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [商品图片]
 * </p>
 */
public class ItemPicture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7464344640598994357L;
	private Long pictureId;// 图片id
	private Long itemId;// 商品id
	private String pictureUrl;// 图片url
	private Integer isFirst;//是否是主图
	private Integer sortNumber;// 排序号,排序号最小的作为主图，从1开始
	private Integer deleteFlag;//删除标记
	private Integer pictureStatus;// 图片状态:1 有效 2： 无效
	private Long sellerId;// 卖家id
	private Date created;// 创建日期
	private Date modified;// 修改日期
	private Long shopId;// 店铺id
	private Long createId;//创建人ID
	private String createName;//创建人名称
	private Long modifyId;//更新人ID
	private String modifyName;//更新人名称

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getPictureStatus() {
		return pictureStatus;
	}

	public void setPictureStatus(Integer pictureStatus) {
		this.pictureStatus = pictureStatus;
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
}
