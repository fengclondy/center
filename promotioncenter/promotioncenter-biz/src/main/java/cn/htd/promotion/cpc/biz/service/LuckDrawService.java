package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

public interface LuckDrawService {

	/**
	 * 判断汇掌柜首页是否要显示扭蛋机图标
	 * 
	 * @param request
	 * @return
	 */
	public ValidateLuckDrawResDTO validateLuckDrawPermission(
			ValidateLuckDrawReqDTO requestDTO);

	/**
	 * 抽奖(扭蛋)活动页
	 * 
	 * @param request
	 * @return
	 */
	public LotteryActivityPageResDTO lotteryActivityPage(
			LotteryActivityPageReqDTO request);
}
