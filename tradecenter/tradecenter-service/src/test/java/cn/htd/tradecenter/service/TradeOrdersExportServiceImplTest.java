package cn.htd.tradecenter.service;

import cn.htd.tradecenter.dto.TradeOrdersDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.tradecenter.dto.TradeOrderQueryInForSellerDTO;
import cn.htd.tradecenter.dto.TradeOrderQueryOutForSellerDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by thinkpad on 2016/11/30.
 */
public class TradeOrdersExportServiceImplTest {

	private TradeOrderExportService tradeOrderExportService;
	private DictionaryUtils dictionary;
	ApplicationContext ctx;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		tradeOrderExportService = (TradeOrderExportService) ctx.getBean("tradeOrderExportService");
		dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
	}

	@Test
	public void testSeller() {
		TradeOrderQueryInForSellerDTO dto = new TradeOrderQueryInForSellerDTO();
		// dto.setSellerId(999999l);
		dto.setShopId(541l);
//		dto.setGoodsName("我是你大爷");
		Pager<TradeOrderQueryInForSellerDTO> pager = new Pager<TradeOrderQueryInForSellerDTO>();
		ExecuteResult<DataGrid<TradeOrderQueryOutForSellerDTO>> result = tradeOrderExportService
				.queryTradeOrderForSeller(dto, pager);
		System.out.println(result.getResult().getRows());
	}
	@Test
	public void testchangePrice(){
		TradeOrdersDTO dto = new TradeOrdersDTO();
		dto.setOrderNo("10017030417182900020");
		dto.setModifyName("胖子拍肚子1");
		dto.setModifyTime(new Date());
		dto.setTotalFreight(BigDecimal.valueOf(100));
		ExecuteResult<String> result = tradeOrderExportService.changePrice(dto);
		System.out.println(result.getResult());

	}

	@Test
	public void confimDe(){
		TradeOrdersDTO dto = new TradeOrdersDTO();
		dto.setOrderNo("10017030416091000007");
		dto.setLogisticsCompany("EMS");
		dto.setLogisticsNo("000000");
		dto.setLogisticsStatus("1");
		dto.setModifyName("胖子拍肚子");
		dto.setModifyId(122l);
		ExecuteResult<String> result = tradeOrderExportService.confimDeliver(dto);
		System.out.print(result);
	}

	@Test
	public void queryQty(){
		TradeOrderQueryInForSellerDTO inDTO1 = new TradeOrderQueryInForSellerDTO();
		inDTO1.setShopId(1l);
		inDTO1.setOrderStatus("20");//代发货
		inDTO1.setIsCancelOrder(0);
		Long d = tradeOrderExportService.queryOrderQty(inDTO1).getResult();
		System.out.print(d);
	}

	@Test
	public void checkPOPOrders(){
		tradeOrderExportService.checkPOPOrders("1017073111231907896","htd40070032","352.000000");
	}
}
