package cn.htd.marketcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.dto.BuyerCouponConditionDTO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;

/**
 * Created by thinkpad on 2016/12/13.
 */
public class BuyerCouponInfoServiceTest {

	ApplicationContext ctx;
	private BuyerCouponInfoService buyerCouponInfoService;
	private DictionaryUtils dictionary;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		buyerCouponInfoService = (BuyerCouponInfoService) ctx.getBean("buyerCouponInfoService");
		dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
	}

	// @Test
	// public void queryCouponCountByCouponIdTest() {
	// BuyerCouponInfoDTO dto = new BuyerCouponInfoDTO();
	// dto.setPromotionId(76l);
	// dto.setBuyerId(456l);
	// DataGrid<BuyerCouponInfoDTO> dataGrid =
	// buyerCouponInfoService.queryCouponCountByPromotionId(dto, null);
	// System.out.println("hah");
	//
	// }

	@Test
	public void getBuyerOwnCouponListTest() {
		BuyerCouponConditionDTO buyerCouponCondition = new BuyerCouponConditionDTO();
		buyerCouponCondition.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS,
				DictionaryConst.OPT_COUPON_STATUS_UNUSED));
		buyerCouponCondition.setBuyerCode("0832");
		ExecuteResult<DataGrid<BuyerCouponInfoDTO>> result = buyerCouponInfoService.getBuyerOwnCouponList("1234",
				buyerCouponCondition, null);
		System.out.println(result.isSuccess());
	}

	// @Test
	// public void testqueryCouponCountByPromotionId() {
	// ExecuteResult<Long> count =
	// buyerCouponInfoService.queryCouponCountByPromotionId(76l);
	// System.out.println("hehe");
	// }

}
