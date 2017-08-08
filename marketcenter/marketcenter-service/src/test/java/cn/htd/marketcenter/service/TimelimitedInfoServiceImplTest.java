package cn.htd.marketcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;

/**
 * Created by lh on 2016/12/08
 */
public class TimelimitedInfoServiceImplTest {
	ApplicationContext ctx;
	private TimelimitedInfoService timelimitedInfoService;

	private BuyerTimelimitedInfo4SuperBossService buyerTimelimitedInfo4SuperBossService;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		timelimitedInfoService = (TimelimitedInfoService) ctx.getBean("timelimitedInfoService");
		buyerTimelimitedInfo4SuperBossService = (BuyerTimelimitedInfo4SuperBossService) ctx.getBean("buyerTimelimitedInfo4SuperBossService");

	}

	// @Test
	// public void testSelectListByCondition() {
	// ExecuteResult<DataGrid<TimelimitedInfoDTO>> result =
	// timelimitedInfoService.selectListByCondition(null, null);
	// }
	//
	// @Test
	// public void testSelectById() {
	// ExecuteResult<TimelimitedInfoDTO> result =
	// timelimitedInfoService.seletById(71L);
	// }
	//
	// @Test
	// public void testAdd(){
	// TimelimitedInfoDTO timelimitedInfoDTO = new TimelimitedInfoDTO();
	// PromotionInfoDTO promotionInfoNewDTO = new PromotionInfoDTO();
	// promotionInfoNewDTO.setPromotionCode("0131");
	// promotionInfoNewDTO.setPromotionName("lhtest");
	// promotionInfoNewDTO.setPromotionDescribe("lhtest");
	// promotionInfoNewDTO.setPromotionProviderType("1");
	// promotionInfoNewDTO.setPromotionProviderSellerId(1L);
	// promotionInfoNewDTO.setPromotionProviderShopId(1L);
	// ExecuteResult<TimelimitedInfoDTO> result =
	// timelimitedInfoService.add(timelimitedInfoDTO);
	//
	// }
	
	 @Test
	 public void testAdd(){
		 String messageId="";
		 String buyercode="htd1146001";
		 String promotionId="2171328280492";
		 String buyerGrade="5";
		 String sellerCode="htd1000000";
		 Pager<TimelimitedInfoDTO> page =new Pager<TimelimitedInfoDTO>();
		 TimelimitedConditionDTO conditionDTO = new TimelimitedConditionDTO();
		 conditionDTO.setSelleCode(sellerCode);
		// buyerTimelimitedInfo4SuperBossService.getVMSTimelimitedInfo(messageId, promotionId, buyercode,sellerCode,buyerGrade); 
		// buyerTimelimitedInfo4SuperBossService.getVMSTimelimitedListByMember(messageId, buyercode, sellerCode, page);
		 //buyerTimelimitedInfo4SuperBossService.getAllVMSTimelimitedList(messageId,sellerCode,page);
		// buyerTimelimitedInfo4SuperBossService.getAllMallTimelimitedList(messageId, page);	
		 timelimitedInfoService.queryTimelimitedListByCondition(conditionDTO, page);
	
	 }
	

}
