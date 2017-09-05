package cn.htd.promotion.cpc.dto.request;

import java.util.Date;

public class PromotionAwardInfoReqDTO  extends GenricReqDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long awardId;

    private String promotionId;

    private String levelCode;

    private String awardType;

    private String awardValue;

    private String awardName;

    private String awardRuleDescribe;

    private Integer provideCount;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId == null ? null : promotionId.trim();
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType == null ? null : awardType.trim();
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue == null ? null : awardValue.trim();
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName == null ? null : awardName.trim();
    }

    public String getAwardRuleDescribe() {
        return awardRuleDescribe;
    }

    public void setAwardRuleDescribe(String awardRuleDescribe) {
        this.awardRuleDescribe = awardRuleDescribe == null ? null : awardRuleDescribe.trim();
    }

    public Integer getProvideCount() {
        return provideCount;
    }

    public void setProvideCount(Integer provideCount) {
        this.provideCount = provideCount;
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