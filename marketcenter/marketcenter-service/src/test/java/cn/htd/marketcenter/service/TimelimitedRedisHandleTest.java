package cn.htd.marketcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import cn.htd.marketcenter.dto.TimelimitPurchaseMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;

import com.alibaba.fastjson.JSON;

/**
 * Created by thinkpad on 2017/1/6.
 */
public class TimelimitedRedisHandleTest {

	ApplicationContext act = null;
	private TimelimitedRedisHandle timelimitedRedisHandle;
	private DictionaryUtils dictionary;
	private TimelimitedPurchaseService timelimitedPurchaseService;

	@Before
	public void setUp() throws Exception {
		act = new ClassPathXmlApplicationContext("test.xml");
		timelimitedRedisHandle = (TimelimitedRedisHandle) act.getBean("timelimitedRedisHandle");
		timelimitedPurchaseService = (TimelimitedPurchaseService) act.getBean("timelimitedPurchaseService");
		dictionary = (DictionaryUtils) act.getBean("dictionaryUtils");
	}

	@Test
	public void updateRedisUseTimelimitedLogTest() throws Exception {
		List<BuyerUseTimelimitedLog> useLogList = new ArrayList<BuyerUseTimelimitedLog>();
		BuyerUseTimelimitedLog useLog = new BuyerUseTimelimitedLog();
		useLog.setSeckillLockNo("jiangkun12134");
		useLog.setOrderNo("999999");
		useLog.setBuyerCode("888888");
		useLog.setPromotionId("77777");
		useLog.setUseType(dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
				DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE));
		useLogList.add(useLog);
		timelimitedRedisHandle.updateRedisUseTimelimitedLog(useLogList);

	}
	//
	// @Test
	// public void testReduceBuyerPromotionDeal() throws Exception {
	//
	// List<TradeOrderItemPromotionDTO> buyerPromotionList = new
	// ArrayList<TradeOrderItemPromotionDTO>();
	//
	// TradeOrderItemPromotionDTO tradeOrderItemPromotionDTO0 = new
	// TradeOrderItemPromotionDTO();
	// tradeOrderItemPromotionDTO0.setBuyerId(1L);
	// tradeOrderItemPromotionDTO0.setOrderNo("1");
	// tradeOrderItemPromotionDTO0.setOrderItemNo("1");
	// tradeOrderItemPromotionDTO0.setCouponCode("1");
	// tradeOrderItemPromotionDTO0.setOperaterId(111L);
	// tradeOrderItemPromotionDTO0.setOperaterName("lhtest");
	//
	// TradeOrderItemPromotionDTO tradeOrderItemPromotionDTO1 = new
	// TradeOrderItemPromotionDTO();
	// tradeOrderItemPromotionDTO1.setBuyerId(1L);
	// tradeOrderItemPromotionDTO1.setOrderNo("1");
	// tradeOrderItemPromotionDTO1.setOrderItemNo("2");
	// tradeOrderItemPromotionDTO1.setCouponCode("1");
	// tradeOrderItemPromotionDTO1.setOperaterId(111L);
	// tradeOrderItemPromotionDTO1.setOperaterName("lhtest");
	//
	// buyerPromotionList.add(tradeOrderItemPromotionDTO0);
	// buyerPromotionList.add(tradeOrderItemPromotionDTO1);
	//
	// buyerCouponHandle.reduceBuyerPromotion(buyerPromotionList);
	// }
	//
	// @Test
	// public void testReleaseBuyerPromotionDeal() throws Exception {
	// List<TradeOrderItemPromotionDTO> buyerPromotionList = new
	// ArrayList<TradeOrderItemPromotionDTO>();
	//
	// TradeOrderItemPromotionDTO tradeOrderItemPromotionDTO0 = new
	// TradeOrderItemPromotionDTO();
	// tradeOrderItemPromotionDTO0.setBuyerId(1L);
	// tradeOrderItemPromotionDTO0.setOrderNo("1");
	// tradeOrderItemPromotionDTO0.setOrderItemNo("1");
	// tradeOrderItemPromotionDTO0.setPromotionType("1");// 1.满减 2.折扣
	// tradeOrderItemPromotionDTO0.setPromotionId(18L);
	// tradeOrderItemPromotionDTO0.setCouponCode("1");
	// tradeOrderItemPromotionDTO0.setDiscountAmount(BigDecimal.valueOf(0.1f));
	// tradeOrderItemPromotionDTO0.setOperaterId(111L);
	// tradeOrderItemPromotionDTO0.setOperaterName("lhtest");
	//
	// TradeOrderItemPromotionDTO tradeOrderItemPromotionDTO1 = new
	// TradeOrderItemPromotionDTO();
	// tradeOrderItemPromotionDTO1.setBuyerId(1L);
	// tradeOrderItemPromotionDTO1.setOrderNo("1");
	// tradeOrderItemPromotionDTO1.setOrderItemNo("2");
	// tradeOrderItemPromotionDTO1.setPromotionType("1");// 1.满减 2.折扣
	// tradeOrderItemPromotionDTO1.setPromotionId(18L);
	// tradeOrderItemPromotionDTO1.setCouponCode("1");
	// tradeOrderItemPromotionDTO1.setDiscountAmount(BigDecimal.valueOf(0.2f));
	// tradeOrderItemPromotionDTO1.setOperaterId(111L);
	// tradeOrderItemPromotionDTO1.setOperaterName("lhtest");
	//
	// buyerPromotionList.add(tradeOrderItemPromotionDTO0);
	// buyerPromotionList.add(tradeOrderItemPromotionDTO1);
	//
	// buyerCouponHandle.releaseBuyerPromotion(buyerPromotionList);
	// }
	
	@Test
	public void getTimelimitedInfo() throws Exception {
		ExecuteResult<List<TimelimitedInfoDTO>> result = timelimitedPurchaseService.getTimelimitedInfo("1000039612");
		System.out.println(JSON.toJSONString(result));
	}
	
	@Test
	public void updateTimelimitedInfo() throws Exception {
		TimelimitedInfoDTO dto = new TimelimitedInfoDTO();
		dto.setPromotionId("3171516342224");
		dto.setSkuCode("");
		dto.setSalesVolume(1);
		ExecuteResult<String> result = timelimitedPurchaseService.updateTimitedInfoSalesVolumeRedis(dto);
		System.out.println(JSON.toJSONString(result));
	}
}
