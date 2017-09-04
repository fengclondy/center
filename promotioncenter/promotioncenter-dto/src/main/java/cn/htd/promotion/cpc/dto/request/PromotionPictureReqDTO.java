package cn.htd.promotion.cpc.dto.request;

import java.util.Date;

public class PromotionPictureReqDTO  extends GenricReqDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String promotionId;

    private String promotionPictureType;

    private String promotionPictureUrl;

    private Byte deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;
    private Long modifyId;//更新ID

    private String modifyName;//更新名称

    private Date modifyTime;//更新时间
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId == null ? null : promotionId.trim();
    }

    public String getPromotionPictureType() {
        return promotionPictureType;
    }

    public void setPromotionPictureType(String promotionPictureType) {
        this.promotionPictureType = promotionPictureType == null ? null : promotionPictureType.trim();
    }

    public String getPromotionPictureUrl() {
        return promotionPictureUrl;
    }

    public void setPromotionPictureUrl(String promotionPictureUrl) {
        this.promotionPictureUrl = promotionPictureUrl == null ? null : promotionPictureUrl.trim();
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}