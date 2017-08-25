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

}
