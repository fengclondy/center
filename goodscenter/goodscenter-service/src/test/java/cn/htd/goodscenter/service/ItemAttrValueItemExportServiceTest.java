package cn.htd.goodscenter.service;

import com.alibaba.fastjson.JSON;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueItemDTO;
import cn.htd.goodscenter.test.common.CommonTest;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ItemAttrValueItemExportServiceTest extends CommonTest {

	@Resource
	ItemAttrValueItemExportService itemAttrValueItemExportService = null;

	@Test
	public void addItemAttrValueItemTest() {
		List<ItemAttrValueItemDTO> itemAttrValueItemList = new ArrayList<ItemAttrValueItemDTO>();
		ItemAttrValueItemDTO itemAttrValueItemDTO = new ItemAttrValueItemDTO();
		itemAttrValueItemDTO.setAttrId(1l);// 属性ID
		itemAttrValueItemDTO.setValueId(1l);// 属性值ID
		itemAttrValueItemDTO.setItemId(1l);// 商品ID
		itemAttrValueItemDTO.setAttrType(1);// 属性类型:1:销售属性;2:非销售属性
		itemAttrValueItemDTO.setSortNumber(1);// 排序号。越小越靠前。
		itemAttrValueItemList.add(itemAttrValueItemDTO);
		ExecuteResult<String> result = itemAttrValueItemExportService.addItemAttrValueItem(itemAttrValueItemList);
		System.out.println("result------" + JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyItemAttrValueItemTest() {
		List<ItemAttrValueItemDTO> itemAttrValueItemList = new ArrayList<ItemAttrValueItemDTO>();
		ItemAttrValueItemDTO itemAttrValueItemDTO = new ItemAttrValueItemDTO();
		itemAttrValueItemDTO.setAttrId(2l);// 属性ID
		itemAttrValueItemDTO.setValueId(2l);// 属性值ID
		itemAttrValueItemDTO.setItemId(1l);// 商品ID
		itemAttrValueItemDTO.setAttrType(1);// 属性类型:1:销售属性;2:非销售属性
		itemAttrValueItemDTO.setSortNumber(1);// 排序号。越小越靠前。
		itemAttrValueItemList.add(itemAttrValueItemDTO);
		ExecuteResult<String> result = itemAttrValueItemExportService.modifyItemAttrValueItem(itemAttrValueItemList);
		System.out.println("result------" + JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void deleteItemAttrValueItemTest() {

		ExecuteResult<String> result = itemAttrValueItemExportService.deleteItemAttrValueItem(2l);
		System.out.println("result------" + JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void queryItemAttrValueItemTest() {
		ItemAttrValueItemDTO itemAttrValueItemDTO = new ItemAttrValueItemDTO();
		itemAttrValueItemDTO.setItemId(1000000492l);
		itemAttrValueItemDTO.setAttrType(2);
		ExecuteResult<List<ItemAttrDTO>> result = itemAttrValueItemExportService
				.queryItemAttrValueItem(itemAttrValueItemDTO);
		System.out.println("result------" + JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

}
