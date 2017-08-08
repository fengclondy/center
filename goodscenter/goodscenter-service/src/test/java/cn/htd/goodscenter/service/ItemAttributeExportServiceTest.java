package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import com.alibaba.fastjson.JSON;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.test.common.CommonTest;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ItemAttributeExportServiceTest extends CommonTest {

	@Resource
	ItemAttributeExportService itemAttributeExportService = null;

	@Test
	public void testAddItemAttribute() {
		ItemAttrDTO itemAttr = new ItemAttrDTO();
		itemAttr.setName("测试属性添加");
		ExecuteResult<ItemAttrDTO> result = this.itemAttributeExportService.addItemAttribute(itemAttr);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void testModifyItemAttribute() {
		ItemAttrDTO itemAttr = new ItemAttrDTO();
		itemAttr.setId(5L);
		itemAttr.setName("测试属性修改");
		itemAttr.setModifyId(1111L);
		itemAttr.setModifyName("ck");
		ExecuteResult<ItemAttrDTO> result = this.itemAttributeExportService.modifyItemAttribute(itemAttr);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testDeleteItemAttribute() {
		ItemAttrDTO itemAttr = new ItemAttrDTO();
		itemAttr.setId(533L);
		ExecuteResult<ItemAttrDTO> result = this.itemAttributeExportService.deleteItemAttribute(itemAttr);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testAddItemAttributeValue() {
		ItemAttrValueDTO itemAttrValue = new ItemAttrValueDTO();
		itemAttrValue.setAttrId(1L);
		itemAttrValue.setName("321我");
		ExecuteResult<ItemAttrValueDTO> result = this.itemAttributeExportService.addItemAttrValue(itemAttrValue);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void testModifyItemAttributeValue() {
		ItemAttrValueDTO itemAttrValue = new ItemAttrValueDTO();
		itemAttrValue.setId(5l);
		itemAttrValue.setName("321cm");
		itemAttrValue.setModifyId(10000l);
		itemAttrValue.setModifyName("ck");
		ExecuteResult<ItemAttrValueDTO> result = this.itemAttributeExportService.modifyItemAttrValue(itemAttrValue);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testDeleteItemAttributeValue() {
		ItemAttrValueDTO itemAttrValue = new ItemAttrValueDTO();
		itemAttrValue.setId(1170L);
		ExecuteResult<ItemAttrValueDTO> result = this.itemAttributeExportService.deleteItemAttrValue(itemAttrValue);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testaddItemAttrValueBackTest() {
		CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
		inDTO.setCid(497L);
		inDTO.setSellerId(1000000294L);
		inDTO.setShopId(2000000292L);
		inDTO.setAttrType(1);
		ExecuteResult<List<ItemAttrDTO>> result = this.itemAttributeExportService.addItemAttrValueBack(inDTO, 2);
		System.out.println("result~~~~~~~~~~~~~~~" + JSON.toJSON(result));
		Assert.assertEquals(true, result.isSuccess());
	}
}
