package cn.htd.promotion.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionLotteryAPI;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
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
    
    public static void main(String[] args) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		System.out.println(cal.getTime());
	}
    @Resource
    private DictionaryUtils dictionary;
    @Test
    @Rollback(false) 
    public void promotion(){
    	String ss = "{\"buyerRuleDTO\":{\"deleteFlag\":0,\"ruleTargetType\":\"0\"},\"cycleTimeType\":\"1\",\"cycleTimeValue\":\"1\",\"dailyBuyerPartakeTimes\":10,\"dailyBuyerWinningTimes\":10,\"dailyWinningTimes\":100,\"eachEndTime\":23325000,\"eachStartTime\":23312000,\"effectiveTime\":1541001600000,\"invalidTime\":1541088000000,\"isShareTimesLimit\":1,\"isVip\":0,\"messageId\":\"15051126263840000012897\",\"modifyId\":1,\"modifyName\":\"汇通达\",\"promotionAccumulatyList\":[{\"addupType\":\"0\",\"awardId\":188,\"awardName\":\"test食物\",\"awardRuleDescribe\":\"111111\",\"awardType\":\"2\",\"deleteFlag\":0,\"isVip\":0,\"levelAmount\":\"100\",\"levelNumber\":0,\"provideCount\":0,\"quantifierType\":\"4\",\"taskQueueNum\":0}],\"promotionDetailDescribeDTO\":{\"deleteFlag\":0,\"describeContent\":\"  \r\n                           testsetestettwa\r\n                                      \"},\"promotionId\":\"21171429031633\",\"promotionName\":\"test蒋坤 全部会员店\",\"promotionPictureList\":[{\"deleteFlag\":0,\"promotionPictureType\":\"1\",\"promotionPictureUrl\":\"https://b2cimg.htd.cn/hl/1505111343599JdhlFBdY.png\"}],\"promotionProviderType\":\"1\",\"promotionType\":\"21\",\"sellerRuleDTO\":{\"deleteFlag\":0,\"ruleTargetType\":\"2\",\"sellerDetailList\":[],\"targetSellerType\":\"0\"},\"shareExtraPartakeTimes\":1,\"taskQueueNum\":0}";
    	PromotionExtendInfoDTO promotionInfo = JSON.parseObject(ss, PromotionExtendInfoDTO.class);
    	PromotionSellerRuleDTO psr = promotionInfo.getSellerRuleDTO();
    	 List<PromotionSellerDetailDTO> sellerlist = psr.getSellerDetailList();
		if (psr.getRuleTargetType().equals(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
                 DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART)) && (sellerlist == null || sellerlist.isEmpty())) {
    		 System.out.println(sellerlist);
    	 }
		
//    	GoldRecordEntity goldRecordEntity = new GoldRecordEntity();
//		goldRecordEntity.setMemberno("11111");
//		if(!StringUtils.isEmpty("1")){
//			goldRecordEntity.setGold(new Integer("1"));
//		}else{
//			goldRecordEntity.setGold(0);
//		}
//		goldRecordEntity.setBptype("1");
//		goldRecordEntity.setDescribe("扭蛋机活动加金币");
//		goldRecordEntity.setRemark("test"+":"+"t1");
//		goldRecordEntity.setOperatorid("sys");
//		boolean rt = goldService.addGoldByPromotion(goldRecordEntity );
//    	Date nowDt = new Date();
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int day = cal.get(Calendar.DATE);
//		int hour = 10;
//		int minute = 0;
//		int second = 0;
//		cal.set(year, month, day, hour,minute,second);
//		Date startTime = cal.getTime();
//		hour = 22;
//		cal.set(year, month, day, hour,minute,second);
//		Date endTime = cal.getTime();
//
//
//    	PromotionExtendInfoDTO promotionExtendInfoDTO = new PromotionExtendInfoDTO();
//    	promotionExtendInfoDTO.setPromotionName("扭蛋机--蒋坤测试");
//    	promotionExtendInfoDTO.setCreateId(0L);
//    	promotionExtendInfoDTO.setCreateName("sys");
//    	promotionExtendInfoDTO.setPromotionProviderType("1");
//    	promotionExtendInfoDTO.setPromotionType("21");
//    	promotionExtendInfoDTO.setEffectiveTime(nowDt);
//    	promotionExtendInfoDTO.setInvalidTime(DateUtils.addDays(nowDt, 1));
//    	promotionExtendInfoDTO.setStatus("2");
//    	promotionExtendInfoDTO.setShowStatus("3");
//    	promotionExtendInfoDTO.setDealFlag(1);
////    	List<PromotionConfigureDTO> promotionConfigureList = new ArrayList<PromotionConfigureDTO>();
////    	PromotionConfigureDTO e = new PromotionConfigureDTO();
////    	e.setConfType("1");
////    	e.setConfValue("2");
////		promotionConfigureList.add(e );
////		promotionExtendInfoDTO.setPromotionConfigureList(promotionConfigureList );
//		promotionExtendInfoDTO.setCycleTimeType("1");
//		promotionExtendInfoDTO.setEachStartTime(startTime);
//		promotionExtendInfoDTO.setEachEndTime(endTime);
//		promotionExtendInfoDTO.setIsTotalTimesLimit(0);
//		promotionExtendInfoDTO.setIsDailyTimesLimit(1);
//		promotionExtendInfoDTO.setDailyBuyerPartakeTimes(10L);
//		promotionExtendInfoDTO.setDailyBuyerWinningTimes(2L);
//		promotionExtendInfoDTO.setDailyWinningTimes(30L);
//		promotionExtendInfoDTO.setIsShareTimesLimit(0);
//		promotionExtendInfoDTO.setShareExtraPartakeTimes(1L);
//		promotionExtendInfoDTO.setTopExtraPartakeTimes(5L);
//		promotionExtendInfoDTO.setContactName("test");
//		PromotionDetailDescribeDTO promotionDetailDescribeDTO = new PromotionDetailDescribeDTO();
//		promotionDetailDescribeDTO.setDescribeContent("jiangkun test test test");
//		promotionExtendInfoDTO.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO );
//		List<PromotionAwardInfoDTO> promotionAccumulatyList = new ArrayList<PromotionAwardInfoDTO>();
//		for (int i=1; i <=5; i++) {
//			PromotionAwardInfoDTO ee = new PromotionAwardInfoDTO();
//			if (i == 1) {
//				ee.setAwardType("2");
//				ee.setAwardValue("");
//				ee.setAwardName("iphone X 手机");
//				ee.setAwardName("iphone X 手机 蒋坤 测试");
//			} else if (i==2) {
//				ee.setAwardType("3");
//				ee.setAwardValue("1");
//				ee.setAwardName("话费1元");
//				ee.setAwardName("话费1元 蒋坤 测试");
//
//			} else if (i==3) {
//				ee.setAwardType("4");
//				ee.setAwardValue("10");
//				ee.setAwardName("汇金币10个");
//				ee.setAwardName("汇金币10个 蒋坤 测试");
//
//			} else if (i==4) {
//				ee.setAwardType("2");
//				ee.setAwardValue("");
//				ee.setAwardName("华为 mate 10 手机");
//				ee.setAwardName("华为 mate 10 手机 手机 蒋坤 测试");
//
//			} else if (i==5) {
//				ee.setAwardType("5");
//				ee.setAwardValue("");
//				ee.setAwardName("谢谢惠顾");
//				ee.setAwardName("谢谢惠顾 蒋坤 测试");
//			}
//			ee.setAwardRuleDescribe("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
//			ee.setProvideCount(100);
//			ee.setLevelAmount(String.valueOf(i*5 + 5));
//			ee.setQuantifierType("4");
//			ee.setAddupType("0");
//			promotionAccumulatyList.add(ee);
//		}
//		
//		promotionExtendInfoDTO.setPromotionAccumulatyList(promotionAccumulatyList);
////		List<PromotionPictureDTO> promotionPictureList = new ArrayList<PromotionPictureDTO>();
////		PromotionPictureDTO eee = new PromotionPictureDTO();
////		eee.setPromotionPictureType("1");
////		eee.setPromotionPictureUrl("http://ssssssssss");
////		promotionPictureList.add(eee );
////		promotionExtendInfoDTO.setPromotionPictureList(promotionPictureList );
////		PromotionBuyerRuleDTO buyerRuleDTO = new PromotionBuyerRuleDTO();
////		buyerRuleDTO.setRuleTargetType("1");
////		promotionExtendInfoDTO.setBuyerRuleDTO(buyerRuleDTO );
//		
//		PromotionSellerRuleDTO sellerRuleDTO=new PromotionSellerRuleDTO();
//		sellerRuleDTO.setTargetSellerType("2");
//		List<PromotionSellerDetailDTO> sellerDetailList = new ArrayList<>();
//		PromotionSellerDetailDTO rrrr = new PromotionSellerDetailDTO();
//		rrrr.setSellerCode("801781");
//		rrrr.setSellerName("无锡市崇安区阳光家电有限公司");
//		rrrr.setBelongSuperiorName("上级测试公司 蒋坤");
//		sellerDetailList.add(rrrr );
//		sellerRuleDTO.setSellerDetailList(sellerDetailList );
//		promotionExtendInfoDTO.setSellerRuleDTO(sellerRuleDTO);
//		luckDrawService.addDrawLotteryInfo(promotionExtendInfoDTO );
////    	
//    	PromotionExtendInfoDTO dbo = luckDrawService.viewDrawLotteryInfo("23171718090174");
//    	System.out.println(dbo);
    	Map<String, String> keyset = promotionRedisDB.getHashOperations(RedisConst.REDIS_LOTTERY_VALID);
		Set<Entry<String, String>> mset = keyset.entrySet();
		String promotionId = "";
		Set<String> sset = null;
		String buyerDailyDrawTimes = "";
		Integer buyerDailyDrawTimesint = 0;
		String buyerDailyWinningTimes = "";
		String swt = "";
		String BUYER_SHARE_TIMES = "";
		String BUYER_SHARE_EXTRA_PARTAKE_TIMES = "";
		Integer BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
		Integer BUYER_SHARE_TIMESint = 0;
		String BUYER_TOP_EXTRA_PARTAKE_TIMES = "";
		Integer BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
		for (Entry<String, String> entry : mset) {
			if (entry.getValue().equals("3")) {
				promotionId = entry.getKey();
				// 粉丝每日抽奖次数限制
				buyerDailyDrawTimes = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES);
				buyerDailyWinningTimes = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_DAILY_WINNING_TIMES);
				BUYER_SHARE_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES);
				BUYER_TOP_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES);
				if (StringUtils.isEmpty(BUYER_SHARE_EXTRA_PARTAKE_TIMES)) {
					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
				} else {
					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_SHARE_EXTRA_PARTAKE_TIMES);
				}
				if (StringUtils.isEmpty(BUYER_TOP_EXTRA_PARTAKE_TIMES)) {
					BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
				} else {
					BUYER_TOP_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_TOP_EXTRA_PARTAKE_TIMES);
				}
				if (StringUtils.isEmpty(buyerDailyDrawTimes)) {
					buyerDailyDrawTimes = "0";
					buyerDailyDrawTimesint = 0;
				} else {
					buyerDailyDrawTimesint = Integer.valueOf(buyerDailyDrawTimes);
				}
				if (StringUtils.isEmpty(buyerDailyWinningTimes)) {
					buyerDailyWinningTimes = "0";
				}
				sset = promotionRedisDB.getStringRedisTemplate()
						.keys(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_*");
				for (String b2bMiddleLotteryBuyerTimesInfo : sset) {
					if (promotionRedisDB.existsHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES)) {
						BUYER_SHARE_TIMES = promotionRedisDB.getHash(b2bMiddleLotteryBuyerTimesInfo,
								RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);

						if (StringUtils.isEmpty(BUYER_SHARE_TIMES)) {
							BUYER_SHARE_TIMESint = 0;
						} else {
							BUYER_SHARE_TIMESint = Integer.valueOf(BUYER_SHARE_TIMES);
						}
						if (BUYER_TOP_EXTRA_PARTAKE_TIMESint
								.compareTo(BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint) > 0) {
							buyerDailyDrawTimesint = buyerDailyDrawTimesint
									+ (BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint);
						} else {
							buyerDailyDrawTimesint = buyerDailyDrawTimesint + BUYER_TOP_EXTRA_PARTAKE_TIMESint;
						}
					}

					// 粉丝活动粉丝当日剩余参与次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES, buyerDailyDrawTimesint.toString());
					// 粉丝当日中奖次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES, buyerDailyWinningTimes);
				}

				sset = promotionRedisDB.getStringRedisTemplate()
						.keys(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_*");
				for (String string : sset) {
					swt = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
							RedisConst.REDIS_LOTTERY_SELLER_DAILY_TOTAL_TIMES);
					promotionRedisDB.set(string, swt);
				}
			}
		}
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
