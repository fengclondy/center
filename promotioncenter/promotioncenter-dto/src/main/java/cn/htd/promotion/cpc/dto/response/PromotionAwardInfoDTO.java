package cn.htd.promotion.cpc.dto.response;

/**
 * 促销活动奖品信息DTO
 */
public class PromotionAwardInfoDTO extends PromotionAccumulatyDTO {

    /**
     * 奖品ID
     */
    private Long awardId;
    /**
     * 奖励类型 2：实物奖品，3：话费，4：汇金币，5：谢谢惠顾
     */
    private String awardType;
    /**
     * 奖品值
     */
    private String awardValue;
    /**
     * 奖励名称
     */
    private String awardName;
    /**
     * 奖励规则描述
     */
    private String awardRuleDescribe;
    /**
     * 提供数量
     */
    private Integer provideCount;

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardRuleDescribe() {
        return awardRuleDescribe;
    }

    public void setAwardRuleDescribe(String awardRuleDescribe) {
        this.awardRuleDescribe = awardRuleDescribe;
    }

    public Integer getProvideCount() {
        return provideCount;
    }

    public void setProvideCount(Integer provideCount) {
        this.provideCount = provideCount;
    }
}
