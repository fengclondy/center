package cn.htd.promotion.cpc.dto.response;

import java.util.Date;

public class PromotionConfigureDTO  extends GenricResDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String promotionId;

    private String confType;

    private String confValue;

    private int deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;
//    private Long modifyId;//更新ID
//
//    private String modifyName;//更新名称
//
//    private Date modifyTime;//更新时间
    
//    public Long getModifyId() {
//		return modifyId;
//	}
//
//	public void setModifyId(Long modifyId) {
//		this.modifyId = modifyId;
//	}
//
//	public String getModifyName() {
//		return modifyName;
//	}
//
//	public void setModifyName(String modifyName) {
//		this.modifyName = modifyName;
//	}
//
//	public Date getModifyTime() {
//		return modifyTime;
//	}
//
//	public void setModifyTime(Date modifyTime) {
//		this.modifyTime = modifyTime;
//	}

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

    public String getConfType() {
        return confType;
    }

    public void setConfType(String confType) {
        this.confType = confType == null ? null : confType.trim();
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue == null ? null : confValue.trim();
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
}