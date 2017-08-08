package cn.htd.marketcenter.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.ReceiveTriggerCouponDTO;

/**
 * Created by thinkpad on 2017/1/6.
 */
public class TriggerCouponReceiveImplTest {

	ApplicationContext act = null;
	private TriggerCouponReceiveService triggerCouponReceiveService;

	@Before
	public void setUp() throws Exception {
		act = new ClassPathXmlApplicationContext("test.xml");
		triggerCouponReceiveService = (TriggerCouponReceiveService) act.getBean("triggerCouponReceiveService");
	}

	@Test
	public void saveReceiveTriggerCouponTest() {
		ReceiveTriggerCouponDTO triggerDTO = new ReceiveTriggerCouponDTO();
		triggerDTO.setBuyerCode("123455");
		triggerDTO.setBuyerName("jiangkun");
		triggerDTO.setCouponAmount(new BigDecimal("100"));
		ExecuteResult<String> result = triggerCouponReceiveService.saveReceiveTriggerCoupon("12345", triggerDTO);
		System.out.print(result.getErrorMessages());
		assertTrue(result.isSuccess());
	}
}
