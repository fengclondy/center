package cn.htd.promotion.cpc.dto.response;

import java.util.Date;

public class PromotionAccumulatyResDTO extends GenricResDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String promotionId;

    private Integer levelNumber;

    private String levelCode;

    private String addupType;

    private String levelAmount;

    private String quantifierType;

    private int deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;
    
    private PromotionAwardDTO promotionAwardInfoReqDTO;

    public PromotionAwardDTO getPromotionAwardDTO() {
		return promotionAwardInfoReqDTO;
	}

	public void setPromotionAwardDTO(PromotionAwardDTO promotionAwardInfoReqDTO) {
		this.promotionAwardInfoReqDTO = promotionAwardInfoReqDTO;
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

    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    public String getAddupType() {
        return addupType;
    }

    public void setAddupType(String addupType) {
        this.addupType = addupType == null ? null : addupType.trim();
    }

    public String getLevelAmount() {
        return levelAmount;
    }

    public void setLevelAmount(String levelAmount) {
        this.levelAmount = levelAmount == null ? null : levelAmount.trim();
    }

    public String getQuantifierType() {
        return quantifierType;
    }

    public void setQuantifierType(String quantifierType) {
        this.quantifierType = quantifierType == null ? null : quantifierType.trim();
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