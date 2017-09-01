package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoEditReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
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
	
	/**
	 * 抽奖(扭蛋)活动规则页
	 * 
	 * @param request
	 * @return
	 */
	public LotteryActivityRulePageResDTO lotteryActivityRulePage(
			LotteryActivityRulePageReqDTO request);
	
	public WinningRecordResDMO queryWinningRecord(WinningRecordReqDTO request);
	
	/**
	 * 分享链接点击处理
	 * 
	 * @param request
	 * @return
	 */
	public ShareLinkHandleResDTO shareLinkHandle(
			ShareLinkHandleReqDTO request);
	
	
	/**
	 * @param promotionInfoEditReqDTO
	 * @return
	 */
	public DrawLotteryResDTO addDrawLotteryInfo(PromotionInfoEditReqDTO promotionInfoEditReqDTO);

	/**
	 * @param promotionInfoEditReqDTO
	 * @return
	 */
	public DrawLotteryResDTO editDrawLotteryInfo(PromotionInfoEditReqDTO promotionInfoEditReqDTO);
	
	/**
	 * 参与有效活动的所有卖家信息
	 * @return
	 */
	public PromotionSellerRuleDTO participateActivitySellerInfo(String messageId);
}
