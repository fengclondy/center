package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;

public class SellerMeetingInfoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerMeetingInfoTest.class);
	ApplicationContext ctx = null;
	SellerMeetingVMSService sellerMeetingVMSService = null;
	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		sellerMeetingVMSService = (SellerMeetingVMSService) ctx.getBean("sellerMeetingVMSService");
	}

	@Test
	public void test() {
		Pager page = new Pager();
		SellerMeetingInfoDTO sellerMeetingInfoDTO = new SellerMeetingInfoDTO();
		sellerMeetingInfoDTO.setSellerId(16239l);
//		ExecuteResult<String> rs = sellerMeetingVMSService.insertSellerMeetingInfoDTO(sellerMeetingInfoDTO);
//		ExecuteResult<SellerMeetingInfoDTO> sd = sellerMeetingVMSService.selectSellerMeetingInfo("htd2945313");
//		ExecuteResult<String> rs = null;
//		if(sd.isSuccess()){
//			SellerMeetingInfoDTO ee = sd.getResult();
//			rs = sellerMeetingVMSService.updateSellerMeetingInfoDTO(ee);
//		}
		ExecuteResult<DataGrid<SellerMeetingInfoDTO>> rs = sellerMeetingVMSService.selectSellerMeetingInfoList(page, 16239l);
		if(rs.isSuccess()){
			System.out.print(rs);
		}

	}
}
