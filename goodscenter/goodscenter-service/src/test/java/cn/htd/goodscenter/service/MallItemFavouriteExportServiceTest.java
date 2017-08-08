package cn.htd.goodscenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.mall.ItemFavouriteInDTO;
import cn.htd.goodscenter.service.mall.MallItemFavouriteExportService;
import cn.htd.goodscenter.test.common.CommonTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;

public class MallItemFavouriteExportServiceTest extends CommonTest {
	@Resource
	MallItemFavouriteExportService itemFavouriteService;

	@Test
	public void testFavouriteItem() {
		ItemFavouriteInDTO itemFavouriteInDTO = new ItemFavouriteInDTO();
		itemFavouriteInDTO.setItemId(1L);
		itemFavouriteInDTO.setSkuId(1000000579L);
		itemFavouriteInDTO.setSellerId(1L);
		itemFavouriteInDTO.setUserId(1L);
		itemFavouriteInDTO.setShopId(1L);
		itemFavouriteInDTO.setChannelCode("3010");
		itemFavouriteInDTO.setCreateId(1l);
		itemFavouriteInDTO.setCreateName("xxx");
		ExecuteResult executeResult = itemFavouriteService.favouriteItem(itemFavouriteInDTO);
		Assert.assertTrue(executeResult.isSuccess());
	}

	@Test
	public void testCancelFavouriteItem() {
		ExecuteResult executeResult  = itemFavouriteService.cancelFavouriteItem(1L, 1L);
		Assert.assertTrue(executeResult.isSuccess());
	}

	@Test
	public void testIsHasFavouriteItem() {
		ExecuteResult executeResult = itemFavouriteService.isHasFavouriteItem(1L, 1L);
		Assert.assertTrue(executeResult.isSuccess());
		System.out.println(executeResult.getResult());
	}

	@Test
	public void testQueryFavouriteItemList() {
		Pager pager = new Pager();
		ExecuteResult executeResult = itemFavouriteService.queryFavouriteItemList(100002746L, pager);
		Assert.assertTrue (executeResult.isSuccess());
		System.out.println(executeResult.getResult());
	}

	@Autowired
	private ItemSkuExportService itemSkuExportService;

	@Test
	public void testQueryItemSkuByCode() {

		ExecuteResult<ItemSkuDTO>  executeResult = this.itemSkuExportService.queryItemSkuByCode("1000006797");
		Assert.assertTrue(executeResult.isSuccess());
		System.out.println(executeResult.getResult());

	}
}
