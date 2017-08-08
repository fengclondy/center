package cn.htd.membercenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.SellerMeetingEvaluateDTO;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingSignDTO;

public class MemberMeetingSignServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceMember.class);
	ApplicationContext ctx = null;
	MemberMeetingService memberMeetingService = null;
	SellerMeetingVMSService sellerMeetingVMSService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberMeetingService = (MemberMeetingService) ctx.getBean("memberMeetingService");

		sellerMeetingVMSService = (SellerMeetingVMSService) ctx.getBean("sellerMeetingVMSService");
	}

	@Test
	public void tesMeetingSign() {
		SellerMeetingSignDTO dto = new SellerMeetingSignDTO();
		dto.setSellerCode("926386");
		dto.setMemberCode("926246");
		dto.setMeetingNo("htd1000000000046");
		ExecuteResult<Boolean> res = memberMeetingService.meetingSign(dto);
		LOGGER.info(res.toString());
	}

	public void tesinsertSellerMeetingInfoDTO() throws ParseException {
		SellerMeetingInfoDTO sellerMeetingInfoDTO = new SellerMeetingInfoDTO();
		sellerMeetingInfoDTO.setSellerId(368l);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(new Date()));
		sellerMeetingInfoDTO.setMeetingEndTime(trancDate);
		sellerMeetingInfoDTO.setMeetingStartTime(trancDate);
		// ExecuteResult<String> res =
		// sellerMeetingVMSService.insertSellerMeetingInfoDTO(sellerMeetingInfoDTO);
		// LOGGER.info(res.toString());
	}

	public void tesmeetingCommand() {
		SellerMeetingEvaluateDTO dto = new SellerMeetingEvaluateDTO();
		dto.setSellerCode("926386");
		dto.setMemberCode("926246");
		dto.setMeetingNo("1");
		ExecuteResult<Boolean> res = memberMeetingService.meetingCommand(dto);
		LOGGER.info(res.toString());
	}
}
