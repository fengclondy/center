package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemDraftPicture implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5391075440691226350L;

	private Long itemDraftPicId;

    private Long itemDraftId;

    private String pictureUrl;

    private Integer isFirst;

    private Integer sortNumber;

    private Integer deleteFlag;

    private Integer pictureStatus;

    private Long sellerId;

    private Date created;

    private Date modified;

    private Long shopId;

    private Long createId;

    private String createName;

    private Long modifyId;

    private String modifyName;

    public Long getItemDraftPicId() {
        return itemDraftPicId;
    }

    public void setItemDraftPicId(Long itemDraftPicId) {
        this.itemDraftPicId = itemDraftPicId;
    }

    public Long getItemDraftId() {
        return itemDraftId;
    }

    public void setItemDraftId(Long itemDraftId) {
        this.itemDraftId = itemDraftId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
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
        this.createName = createName == null ? null : createName.trim();
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }
}