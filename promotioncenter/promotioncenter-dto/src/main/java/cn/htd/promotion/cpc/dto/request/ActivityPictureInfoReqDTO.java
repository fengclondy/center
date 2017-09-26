package cn.htd.promotion.cpc.dto.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ActivityPictureInfoReqDTO  extends GenricReqDTO  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String pictureId;

    @NotBlank(message = "物料名称不能为空")
    private String pictureName;

    @NotBlank(message = "物料类型不能为空")
    private String pictureType;
    
    @NotNull(message = "开始时间不能为空")
    private Date effectiveTime;
    
    @NotNull(message = "结束时间不能为空")
    private Date invalidTime;

    private String uploadPictureFront;

    private String uploadPictureBack;

    private int isVip;

    private int deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private List<ActivityPictureMemberDetailReqDTO> activityPictureMemberDetailList;
    
    public List<ActivityPictureMemberDetailReqDTO> getActivityPictureMemberDetailList() {
		return activityPictureMemberDetailList;
	}

	public void setActivityPictureMemberDetailList(
			List<ActivityPictureMemberDetailReqDTO> activityPictureMemberDetailList) {
		this.activityPictureMemberDetailList = activityPictureMemberDetailList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId == null ? null : pictureId.trim();
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName == null ? null : pictureName.trim();
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType == null ? null : pictureType.trim();
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getUploadPictureFront() {
        return uploadPictureFront;
    }

    public void setUploadPictureFront(String uploadPictureFront) {
        this.uploadPictureFront = uploadPictureFront == null ? null : uploadPictureFront.trim();
    }

    public String getUploadPictureBack() {
        return uploadPictureBack;
    }

    public void setUploadPictureBack(String uploadPictureBack) {
        this.uploadPictureBack = uploadPictureBack == null ? null : uploadPictureBack.trim();
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}