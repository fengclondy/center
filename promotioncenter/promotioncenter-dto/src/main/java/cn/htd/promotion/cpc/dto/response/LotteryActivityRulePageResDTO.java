package cn.htd.promotion.cpc.dto.response;

public class LotteryActivityRulePageResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8070330421748469993L;

	/**
	 * 活动规则内容
	 */
	private String activityRuleContent;

	public String getActivityRuleContent() {
		return activityRuleContent;
	}

	public void setActivityRuleContent(String activityRuleContent) {
		this.activityRuleContent = activityRuleContent;
	}
}
