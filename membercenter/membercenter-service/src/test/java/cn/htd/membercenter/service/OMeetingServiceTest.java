package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.OMeetingEvaluateDTO;
import cn.htd.membercenter.dto.OMeetingSignDTO;

public class OMeetingServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OMeetingServiceTest.class);

	ApplicationContext ctx = null;
	OMeetingService oMeetingService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		oMeetingService = (OMeetingService) ctx.getBean("oMeetingService");
	}

	/**
	 * 会议签到测试
	 */
	public void queryOMeetingSignForPageTest() {
		OMeetingSignDTO signDto = new OMeetingSignDTO();
		Pager<OMeetingSignDTO> page = new Pager<OMeetingSignDTO>();

		page.setPage(1);
		page.setRows(10);
		ExecuteResult<?> result = oMeetingService.queryOMeetingSignForPage(page, signDto);
		logger.info(result.getCode() + "," + result.getResult());
	}

	public void queryOMeetingSignTest() {
		OMeetingSignDTO signDto = new OMeetingSignDTO();
		ExecuteResult<?> result = oMeetingService.queryOMeetingSign(signDto);
		logger.info(result.getCode() + "," + result.getResult());
	}

	/**
	 * 会议评价测试
	 */
	// @Test
	public void queryOMeetingEvaluateForPageTest() {
		OMeetingEvaluateDTO evalDto = new OMeetingEvaluateDTO();
		Pager<OMeetingEvaluateDTO> page = new Pager<OMeetingEvaluateDTO>();
		page.setPage(1);
		page.setRows(10);
		ExecuteResult<?> result = oMeetingService.queryOMeetingEvaluateForPage(page, evalDto);
		logger.info(result.getCode() + "," + result.getResult());
		logger.info(result.getCode() + "," + result.getResultMessage());
	}

	public void queryOMeetingEvaluateTest() {
		OMeetingEvaluateDTO evalDto = new OMeetingEvaluateDTO();
		ExecuteResult<?> result = oMeetingService.queryOMeetingEvaluate(evalDto);
		logger.info(result.getCode() + "," + result.getResult());
		logger.info(result.getCode() + "," + result.getResultMessage());
	}

	public void queryOMeetingEvaluateByMeetingNo() {
		OMeetingEvaluateDTO evalDto = new OMeetingEvaluateDTO();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		ExecuteResult<?> result = oMeetingService.queryOMeetingEvaluateByMeetingNo(pager, "htd1000000070001");
		logger.info(result.getCode() + "," + result.getResult());
		logger.info(result.getCode() + "," + result.getResultMessage());
	}

	@Test
	public void queryOMeetingSignByMeetingNo() {
		OMeetingEvaluateDTO evalDto = new OMeetingEvaluateDTO();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		ExecuteResult<?> result = oMeetingService.queryOMeetingSignByMeetingNo(pager, "htd1000000070001");
		logger.info(result.getCode() + "," + result.getResult());
		logger.info(result.getCode() + "," + result.getResultMessage());
	}

}
