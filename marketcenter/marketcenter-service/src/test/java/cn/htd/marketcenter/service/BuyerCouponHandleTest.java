package cn.htd.marketcenter.service;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.marketcenter.service.handle.BuyerCouponHandle;

/**
 * Created by thinkpad on 2017/1/6.
 */
public class BuyerCouponHandleTest {

	ApplicationContext act = null;
	private BuyerCouponHandle buyerCouponHandle;

	@Before
	public void setUp() throws Exception {
		act = new ClassPathXmlApplicationContext("test.xml");
		// buyerCouponHandle = (BuyerCouponHandle)
		// act.getBean("buyerCouponHandle");
	}

	// @Test
	// public void testReserveBuyerPromotionDeal() throws Exception {
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
	// buyerCouponHandle.reserveBuyerPromotion(buyerPromotionList);
	//
	// }
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
}
