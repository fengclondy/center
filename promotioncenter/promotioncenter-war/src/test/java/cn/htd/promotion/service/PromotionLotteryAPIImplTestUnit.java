package cn.htd.promotion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.api.PromotionLotteryAPI;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBuyerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionDetailDescribeDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class PromotionLotteryAPIImplTestUnit {
	
	@Resource
	private PromotionLotteryAPI promotionLotteryAPI;
    @Resource
    private PromotionRedisDB promotionRedisDB;
    
    @Resource
    LuckDrawService luckDrawService;
    
    @Before  
    public void setUp() throws Exception {  
    	
    }
    
    
    @Test
    @Rollback(false) 
    public void promotion(){
    	PromotionExtendInfoDTO promotionExtendInfoDTO = new PromotionExtendInfoDTO();
    	promotionExtendInfoDTO.setPromotionName("扭蛋机23");
    	promotionExtendInfoDTO.setCreateId(1l);
    	promotionExtendInfoDTO.setCreateName("sss");
    	promotionExtendInfoDTO.setPromotionProviderType("3");
    	promotionExtendInfoDTO.setPromotionType("21");
    	promotionExtendInfoDTO.setIsShareTimesLimit(0);
    	promotionExtendInfoDTO.setEffectiveTime(new Date());
    	promotionExtendInfoDTO.setInvalidTime(new Date());
    	List<PromotionConfigureDTO> promotionConfigureList = new ArrayList<PromotionConfigureDTO>();
    	PromotionConfigureDTO e = new PromotionConfigureDTO();
    	e.setConfType("1");
    	e.setConfValue("2");
		promotionConfigureList.add(e );
		promotionExtendInfoDTO.setPromotionConfigureList(promotionConfigureList );
		promotionExtendInfoDTO.setContactName("test");
		PromotionDetailDescribeDTO promotionDetailDescribeDTO = new PromotionDetailDescribeDTO();
		promotionDetailDescribeDTO.setDescribeContent("wert");
		promotionExtendInfoDTO.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO );
		List<PromotionAwardInfoDTO> promotionAccumulatyList = new ArrayList<PromotionAwardInfoDTO>();
		PromotionAwardInfoDTO ee = new PromotionAwardInfoDTO();
		ee.setAwardName("qqwwee");
		ee.setAddupType("1");
		ee.setLevelAmount("5");
		ee.setProvideCount(1);
		promotionAccumulatyList.add(ee);
		
		promotionExtendInfoDTO.setPromotionAccumulatyList(promotionAccumulatyList);
		List<PromotionPictureDTO> promotionPictureList = new ArrayList<PromotionPictureDTO>();
		PromotionPictureDTO eee = new PromotionPictureDTO();
		eee.setPromotionPictureType("1");
		eee.setPromotionPictureUrl("http://ssssssssss");
		promotionPictureList.add(eee );
		promotionExtendInfoDTO.setPromotionPictureList(promotionPictureList );
		PromotionBuyerRuleDTO buyerRuleDTO = new PromotionBuyerRuleDTO();
		buyerRuleDTO.setRuleTargetType("1");
		promotionExtendInfoDTO.setBuyerRuleDTO(buyerRuleDTO );
		
		PromotionSellerRuleDTO sellerRuleDTO=new PromotionSellerRuleDTO();
		sellerRuleDTO.setTargetSellerType("1");
		List<PromotionSellerDetailDTO> sellerDetailList = new ArrayList<>();
		PromotionSellerDetailDTO rrrr = new PromotionSellerDetailDTO();
		rrrr.setSellerCode("801781");
		sellerDetailList.add(rrrr );
		sellerRuleDTO.setSellerDetailList(sellerDetailList );
		promotionExtendInfoDTO.setSellerRuleDTO(sellerRuleDTO);
		luckDrawService.addDrawLotteryInfo(promotionExtendInfoDTO );
//    	
//    	PromotionExtendInfoDTO dbo = luckDrawService.viewDrawLotteryInfo("21171606370093");
//    	luckDrawService.editDrawLotteryInfo(dbo);
    }
//    @Test
//  @Rollback(false) 
//  public void queryWinningRecord(){
//    	Map<String, String> keyset = promotionRedisDB.getHashOperations(RedisConst.REDIS_LOTTERY_VALID);
//		Set<Entry<String, String>> mset = keyset.entrySet();
//		String promotionId = "";
//		Set<String> sset = null;
//		String buyerDailyDrawTimes = "";
//		Integer buyerDailyDrawTimesint = 0;
//		String buyerDailyWinningTimes = "";
//		String swt = "";
//		String BUYER_SHARE_TIMES = "";
//		String BUYER_SHARE_EXTRA_PARTAKE_TIMES = "";
//		Integer BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
//		Integer BUYER_SHARE_TIMESint = 0;
//		String BUYER_TOP_EXTRA_PARTAKE_TIMES = "";
//		Integer BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
//		for (Entry<String, String> entry : mset) {
//			if (entry.getValue().equals("3")) {
//				promotionId = entry.getKey();
//				// 粉丝每日抽奖次数限制
//				buyerDailyDrawTimes = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
//						RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES);
//				buyerDailyWinningTimes = promotionRedisDB.getHash(
//						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
//						RedisConst.REDIS_LOTTERY_BUYER_DAILY_WINNING_TIMES);
//				BUYER_SHARE_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
//						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
//						RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES);
//				BUYER_TOP_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
//						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
//						RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES);
//				if (StringUtils.isEmpty(BUYER_SHARE_EXTRA_PARTAKE_TIMES)) {
//					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
//				} else {
//					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_SHARE_EXTRA_PARTAKE_TIMES);
//				}
//				if (StringUtils.isEmpty(BUYER_TOP_EXTRA_PARTAKE_TIMES)) {
//					BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
//				} else {
//					BUYER_TOP_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_TOP_EXTRA_PARTAKE_TIMES);
//				}
//				if (StringUtils.isEmpty(buyerDailyDrawTimes)) {
//					buyerDailyDrawTimes = "0";
//					buyerDailyDrawTimesint = 0;
//				} else {
//					buyerDailyDrawTimesint = Integer.valueOf(buyerDailyDrawTimes);
//				}
//				if (StringUtils.isEmpty(buyerDailyWinningTimes)) {
//					buyerDailyWinningTimes = "0";
//				}
//				sset = promotionRedisDB.getStringRedisTemplate()
//						.keys(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_*");
//				for (String b2bMiddleLotteryBuyerTimesInfo : sset) {
//					if (promotionRedisDB.existsHash(b2bMiddleLotteryBuyerTimesInfo,
//							RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES)) {
//						BUYER_SHARE_TIMES = promotionRedisDB.getHash(b2bMiddleLotteryBuyerTimesInfo,
//								RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);
//
//						if (StringUtils.isEmpty(BUYER_SHARE_TIMES)) {
//							BUYER_SHARE_TIMESint = 0;
//						} else {
//							BUYER_SHARE_TIMESint = Integer.valueOf(BUYER_SHARE_TIMES);
//						}
//						if (BUYER_TOP_EXTRA_PARTAKE_TIMESint
//								.compareTo(BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint) > 0) {
//							buyerDailyDrawTimesint = buyerDailyDrawTimesint
//									+ (BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint);
//						} else {
//							buyerDailyDrawTimesint = buyerDailyDrawTimesint + BUYER_TOP_EXTRA_PARTAKE_TIMESint;
//						}
//					}
//
//					// 粉丝活动粉丝当日剩余参与次数
//					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
//							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES, buyerDailyDrawTimesint.toString());
//					// 粉丝当日中奖次数
//					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
//							RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES, buyerDailyWinningTimes);
//				}
//
//				sset = promotionRedisDB.getStringRedisTemplate()
//						.keys(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_*");
//				for (String string : sset) {
//					swt = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
//							RedisConst.REDIS_LOTTERY_SELLER_DAILY_TOTAL_TIMES);
//					promotionRedisDB.set(string, swt);
//				}
//			}
//		}
//		
//    }

//    @Test
//    @Rollback(false) 
//    public void queryWinningRecord(){
//    	WinningRecordReqDTO  dto = new WinningRecordReqDTO();
//    	dto.setMemberNo("htd21343432");
//    	dto.setStartNo(1);
//    	dto.setEndNo(12);
//    	dto.setMessageId("122121222");
//    	
//    	String winningRecordReqDTOJson = JSONObject.toJSONString(dto);
//    	String res = promotionLotteryAPI.queryWinningRecord(winningRecordReqDTOJson);
//    	System.out.println("queryWinningRecord:"+res);
//    }
//
//    @Test
//    @Rollback(false) 
//    public void validateLuckDrawPermission(){
//    	ValidateLuckDrawReqDTO  dto = new ValidateLuckDrawReqDTO();
//    	dto.setOrgId("8131911");
//    	dto.setMessageId("122121222");
//    	
//    	String validateLuckDrawReqDTOJson = JSONObject.toJSONString(dto);
//    	String res = promotionLotteryAPI.validateLuckDrawPermission(validateLuckDrawReqDTOJson);
//    	System.out.println("validateLuckDrawPermission:"+res);
//    }
//    
//    @Test
//    @Rollback(false) 
//    public void lotteryActivityPage(){
//    	LotteryActivityPageReqDTO  dto = new LotteryActivityPageReqDTO();
//    	dto.setMemberNo("3253");
//    	dto.setOrgId("813191");
//    	dto.setPromotionId("2171730080044");
//    	dto.setMessageId("122121222");
//    	
//    	String lotteryActivityPageReqDTOJson = JSONObject.toJSONString(dto);
//    	String res = promotionLotteryAPI.lotteryActivityPage(lotteryActivityPageReqDTOJson);
//    	System.out.println("validateLuckDrawPermission:"+res);
//    }
//    
//    @Test
//    @Rollback(false) 
//    public void lotteryActivityRulePage(){
//    	LotteryActivityRulePageReqDTO  dto = new LotteryActivityRulePageReqDTO();
//    	dto.setPromotionId("2171730080044");
//    	dto.setMessageId("122121222");
//    	
//    	String lotteryActivityRulePageReqDTOJson = JSONObject.toJSONString(dto);
//    	String res = promotionLotteryAPI.lotteryActivityRulePage(lotteryActivityRulePageReqDTOJson);
//    	System.out.println("validateLuckDrawPermission:"+res);
//    }
//    
//    @Test
//    @Rollback(false) 
//    public void shareLinkHandle(){
//    	ShareLinkHandleReqDTO  dto = new ShareLinkHandleReqDTO();
//    	dto.setMemberNo("1234");
//    	dto.setOrgId("813191");
//    	dto.setPromotionId("2171730080044");
//    	dto.setMessageId("122121222");
//    	
//    	String shareLinkHandleReqDTOJson = JSONObject.toJSONString(dto);
//    	String res = promotionLotteryAPI.shareLinkHandle(shareLinkHandleReqDTOJson);
//    	System.out.println("shareLinkHandle:"+res);
//    }
//    
//    @Test
//    @Rollback(false) 
//    public void participateActivitySellerInfo(){
//    	String res = promotionLotteryAPI.participateActivitySellerInfo("122121222");
//    	System.out.println("participateActivitySellerInfo:"+res);
//    }
}
