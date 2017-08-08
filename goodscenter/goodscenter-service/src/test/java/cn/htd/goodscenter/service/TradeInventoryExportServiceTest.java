package cn.htd.goodscenter.service;


import cn.htd.goodscenter.dto.InventoryModifyDTO;
import cn.htd.goodscenter.dto.TradeInventoryDTO;
import cn.htd.goodscenter.dto.TradeInventoryInDTO;
import cn.htd.goodscenter.dto.TradeInventoryOutDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class TradeInventoryExportServiceTest extends CommonTest {

    @Resource
	private TradeInventoryExportService tradeInventoryExportService;

	@Test
	public void queryBySkuIdTest(){
	 ExecuteResult<TradeInventoryDTO> er=tradeInventoryExportService.queryTradeInventoryBySkuId(7L);
	 TradeInventoryDTO t=er.getResult();
	 System.out.println(t.getTotalInventory());
	 System.out.println(t.getModified());
	 System.out.println(t.getCreateUser());
	}
	@Test
	public void queryTest(){
      TradeInventoryInDTO dto=new TradeInventoryInDTO();
//      dto.setShopId(2000000305L);
		dto.setSellerId(1438l);
		dto.setSkuCode("1");
	  Pager page=new Pager();
	 ExecuteResult<DataGrid<TradeInventoryOutDTO>> er=tradeInventoryExportService.queryTradeInventoryList(dto, page);
		System.out.print(er);
	}
	
	@Test
	public void modifyTest(){
		List<InventoryModifyDTO> dtoList=new ArrayList<InventoryModifyDTO>();
		InventoryModifyDTO dto = new InventoryModifyDTO();
		dto.setSkuId(1000018838L);
		dto.setTotalInventory(200);
		dtoList.add(dto);
		dtoList.add(dto);
		ExecuteResult<String> er=tradeInventoryExportService.modifyInventoryByIds(dtoList);
	    Assert.assertEquals(true, er.isSuccess());
	}
}
