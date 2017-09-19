package cn.htd.promotion.cpc.biz.service;

import java.util.Map;

import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.ScratchCardActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateScratchCardReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.ScratchCardActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateScratchCardResDTO;

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
	 * @param promotionExtendInfoDTO
	 * @return
	 */
	public PromotionExtendInfoDTO addDrawLotteryInfo(PromotionExtendInfoDTO promotionExtendInfoDTO);

	/**
	 * @param promotionExtendInfoDTO
	 * @return
	 */
	public PromotionExtendInfoDTO editDrawLotteryInfo(PromotionExtendInfoDTO promotionExtendInfoDTO);
	
	/**
	 * 参与有效活动的所有卖家信息
	 * @return
	 */
	public PromotionSellerRuleDTO participateActivitySellerInfo(String messageId);

	public PromotionExtendInfoDTO viewDrawLotteryInfo(String promotionInfoId);

	public void updateLotteryResultState(Map<String, Object> map);
	
	/**
	 * 校验刮刮乐图标是否显示
	 * @param requestDTO
	 * @return
	 */
	public ValidateScratchCardResDTO validateScratchCard(ValidateScratchCardReqDTO requestDTO);
	
	/**
	 * 刮刮乐活动页
	 * 
	 * @param request
	 * @return
	 */
	public ScratchCardActivityPageResDTO scratchCardActivityPage(ScratchCardActivityPageReqDTO requestDTO);
}
