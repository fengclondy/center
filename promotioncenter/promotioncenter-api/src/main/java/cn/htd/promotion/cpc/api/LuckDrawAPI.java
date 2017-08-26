package cn.htd.promotion.cpc.api;

public interface LuckDrawAPI {

	/**
	 * 判断汇掌柜首页是否要显示扭蛋机图标
	 * 
	 * @param validateLuckDrawReqDTOJson
	 * @return
	 */
	public String validateLuckDrawPermission(String validateLuckDrawReqDTOJson);

	/**
	 * 抽奖(扭蛋)活动页
	 * 
	 * @param request
	 * @return
	 */
	public String lotteryActivityPage(String lotteryActivityPageReqDTOJson);

	/**
	 * 抽奖(扭蛋)活动规则页
	 * 
	 * @param request
	 * @return
	 */
	public String lotteryActivityRulePage(String lotteryActivityRulePageReqDTOJson);
	
	/**
	 * 粉丝查询自己的中讲过记录
	 * @param winningRecordReqDTOJson
	 * @return
	 */
	public String queryWinningRecord(String winningRecordReqDTOJson);
	
	/**
	 * 分享链接点击处理
	 * @param shareLinkHandleReqDTOJson
	 * @return
	 */
	public String shareLinkHandle(String shareLinkHandleReqDTOJson);
}
