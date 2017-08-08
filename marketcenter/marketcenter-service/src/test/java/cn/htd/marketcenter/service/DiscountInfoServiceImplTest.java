package cn.htd.marketcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.OssUploadUtils;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;

/**
 * Created by lh on 2016/12/12.
 */
public class DiscountInfoServiceImplTest {

	private Logger logger = LoggerFactory.getLogger(OssUploadUtils.class);
	ApplicationContext ctx;
	private DiscountInfoService discountInfoService;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		discountInfoService = (DiscountInfoService) ctx.getBean("discountInfoService");
	}

	@Test
	public void testAdd() {

		String aa = "bbbb";
		String[] abb = aa.split(",");
		System.out.println(abb.length);
		// promotionDiscountInfoService.add(record);
	}

	@Test
	public void testSelectListByCondition() {
		ExecuteResult<PromotionDiscountInfoDTO> result = discountInfoService.queryCouponInfo("1", "2");
		System.out.println("a");
	}

	// @Test
	// public void testSelectById() {
	// ExecuteResult<PromotionDiscountInfoDTO> result =
	// promotionDiscountInfoService.selectById(1L);
	// }
}
