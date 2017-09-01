package cn.htd.promotion.cpc.dto.request;

import java.util.Date;

public class PromotionBuyerRuleReqDTO  extends GenricReqDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String promotionId;

    private Long ruleId;

    private String ruleName;

    private String ruleTargetType;

    private String targetBuyerLevel;

    private String targetBuyerGroup;

    private Byte deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getRuleTargetType() {
        return ruleTargetType;
    }

    public void setRuleTargetType(String ruleTargetType) {
        this.ruleTargetType = ruleTargetType == null ? null : ruleTargetType.trim();
    }

    public String getTargetBuyerLevel() {
        return targetBuyerLevel;
    }

    public void setTargetBuyerLevel(String targetBuyerLevel) {
        this.targetBuyerLevel = targetBuyerLevel == null ? null : targetBuyerLevel.trim();
    }

    public String getTargetBuyerGroup() {
        return targetBuyerGroup;
    }

    public void setTargetBuyerGroup(String targetBuyerGroup) {
        this.targetBuyerGroup = targetBuyerGroup == null ? null : targetBuyerGroup.trim();
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