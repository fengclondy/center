package cn.htd.goodscenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.goodscenter.dto.*;
import cn.htd.goodscenter.dto.constants.CategoryConst;
import cn.htd.goodscenter.test.common.CommonTest;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public class ItemCategoryServiceImpAlllTest extends CommonTest {

	@Resource
	private ItemCategoryService itemCategoryService;

	// 平台类目添加方法测试类
	@Test
	public void addItemCategoryTest() {
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCategoryCName("冰箱2");
		itemCategoryDTO.setCategoryParentCid(9L);
		itemCategoryDTO.setCategoryStatus(CategoryConst.CATEGORY_STATUS_VALID);
		itemCategoryDTO.setCategoryCreateId(100000000L);
		itemCategoryDTO.setCategoryCreateName("chenakng");
		itemCategoryDTO.setCategoryHasLeaf(CategoryConst.CATEGORY_LEAF_YES);
		itemCategoryDTO.setCategorySortNumber(1);
		itemCategoryDTO.setCategoryHomeShow(CategoryConst.CATEGORY_HOME_SHOW_YES);
		itemCategoryDTO.setCategoryLev(CategoryConst.CATEGORY_LEV_3);
		ExecuteResult executeResult = itemCategoryService.addItemCategory(itemCategoryDTO);
		System.out.println("=========:" + executeResult);
	}

	// 平台所有类目列表查询测试类
	@Test
	public void queryItemCategoryAllListTest() {
//		Pager pager = new Pager();
//		pager.setPage(1);
//		pager.setRows(5);
		ExecuteResult<DataGrid<ItemCategoryDTO>> dataGridExecuteResult = itemCategoryService.queryItemCategoryAllList(null);
	}

	// 根据父id查询类目列表测试类
	@Test
	public void queryItemCategoryListTest() {
		Long parentId = 0L;
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid<ItemCategoryDTO>();
//		dataGrid = itemCategoryService.queryItemCategoryList(parentId);
//		System.out.println(dataGrid.getTotal());
	}

	// 根据类目级别查询对应级别的所有类目
	@Test
	public void queryItemByCategoryLev() {
		Integer categoryLev = 1;
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid<ItemCategoryDTO>();
		dataGrid = itemCategoryService.queryItemByCategoryLev(categoryLev);
		System.out.println(dataGrid.getTotal());
	}

	// 平台类目属性添加方法测试类
	@Test
	public void addCategoryAttrTest() {
		Long cid = 353L;
		String attrName = "变频/定频";
		int attrType = 2;
		ExecuteResult<Long> ss = itemCategoryService.addCategoryAttr(cid, attrName, attrType, 1, 100000L, "ck");
		System.out.println(ss.getResult());
	}

	// 平台类目属性删除
	@Test
	public void deleteCategoryAttrTest() {
		ExecuteResult<String> ss = itemCategoryService.deleteCategoryAttr(2L, 3L, 1);
		System.out.println("========================结果:" + ss);
	}

	@Test
	public void deleteCategoryAttrValueTest() {
		ExecuteResult<String> ss = itemCategoryService.deleteCategoryAttrValue(2L, 3L, 2L, 1);
		System.out.println("========================结果:" + ss);
	}

	// 平台类目属性值查询方法测试类
	@Test
	public void queryCategoryAttrList() {
		Long cid = 353L;
		int type = 2;
		ExecuteResult<DataGrid<CategoryAttrDTO>> executeResult = itemCategoryService.queryCategoryAttrList(cid, type);
		System.out.println(executeResult.getResult().getRows());
	}

	// 平台类目属性值添加方法测试类
	@Test
	public void addCategoryAttrTest2() {
		Long cid = 79L;
		Long attrId = 159L;
		String valueName = "型号4";
		itemCategoryService.addCategoryAttrValue(cid, attrId, valueName, 1, 100000L, "ck");
	}

	@Test
	public void testQueryParentCategoryList() {
//		Long[] ids = new Long[]{429L,432L};// --3
		// Long[] ids = new Long[]{428L,431L};// --2
//		Long[] ids = new Long[] { 427L, 430L };// --1
		ExecuteResult<List<ItemCatCascadeDTO>> result = itemCategoryService.queryParentCategoryList(3, new Long[]{35L, 33l});
		List<ItemCatCascadeDTO> itemList = result.getResult();
		for (ItemCatCascadeDTO item : itemList) {
			System.out.println(item);
		}
	}

	@Test
	public void testQueryItemOneTwoThreeCategoryName() {
		ExecuteResult<Map<String ,Object>> result = itemCategoryService.queryItemOneTwoThreeCategoryName(12L, ">>");
		System.out.println(result);
	}


	@Test
	public void testAddItemAttrSeller() {
		CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
		ItemAttrDTO attr = new ItemAttrDTO();
		attr.setName("测试商品属性1");
		inDTO.setCreateId(110l);
		inDTO.setCreateName("周杰伦");
		inDTO.setModifyId(999l);
		inDTO.setModifyName("刘德华");
		inDTO.setAttr(attr);
		inDTO.setCid(1L);
		inDTO.setSellerId(1L);
		inDTO.setShopId(1L);
		inDTO.setAttrType(1);
		inDTO.setSortNumber(1);
		inDTO.setSelectType(1l);
		ExecuteResult<ItemAttrDTO> result = itemCategoryService.addItemAttrSeller(inDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testAddItemAttrValueSeller() {
		CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
		ItemAttrValueDTO iav = new ItemAttrValueDTO();
		iav.setName("测试商品属性值1");
		iav.setAttrId(113L);
		inDTO.setAttrValue(iav);
		inDTO.setCid(1L);
		inDTO.setSellerId(1L);
		inDTO.setShopId(1L);
		ExecuteResult<ItemAttrValueDTO> result = itemCategoryService.addItemAttrValueSeller(inDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryCatAttrSellerList() {
		CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
		inDTO.setCid(1L);
		inDTO.setSellerId(1L);
		inDTO.setShopId(1L);
		inDTO.setAttrType(1);
		ExecuteResult<List<ItemAttrDTO>> result = itemCategoryService.queryCatAttrSellerList(inDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryCatAttrByKeyVals() {
		String attrStr = "113:99;";
		ExecuteResult<List<ItemAttrDTO>> result = this.itemCategoryService.queryCatAttrByKeyVals(attrStr);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testDeleteCategory() {
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCategoryCid(13L);
		itemCategoryDTO.setCategoryModifyId(100000l);
		itemCategoryDTO.setCategoryModifyName("test");
		ExecuteResult<String> result = this.itemCategoryService.deleteItemCategory(itemCategoryDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testgetCategoryByChildCid() {
		ExecuteResult<ItemCategoryDTO> test = itemCategoryService.getCategoryByCid(11L);
		System.out.println(test.getResult());
	}

	@Test
	public void getAttrNameByAttrId() {
		String name = itemCategoryService.getAttrNameByAttrId(1903L);
		String name2 = itemCategoryService.getAttrValueNameByAttrValueId(10117L);
		System.out.println(name + name2);
	}

	@Test
	public void testUpdateCategory() {
		ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
		itemCategoryDTO.setCategoryCid(28L);
		itemCategoryDTO.setCategoryModifyId(111111L);
		itemCategoryDTO.setCategoryModifyName("ck");
		itemCategoryDTO.setCategoryCName("xxx");
		this.itemCategoryService.updateCategory(itemCategoryDTO);
	}

	@Test
	public void testQueryThirdCategoryByParentId() {
		ExecuteResult<List<ItemCategoryDTO>> executeResult = this.itemCategoryService.queryThirdCategoryByParentId(7l);
		System.out.println(executeResult.getResult());
	}

	@Test
	public void testqueryItemCategoryTree() {
		ExecuteResult<List<ItemCatCascadeDTO>> executeResult = this.itemCategoryService.queryItemCategoryTree();
		System.out.println(executeResult.getResult());
	}

	@Test
	public void testqueryItemCategoryTreeByThirdCid() {
		List<Long> thirdCidList = new ArrayList<>();
		thirdCidList.add(35L);
		thirdCidList.add(33L);
		thirdCidList.add(31L);
		ExecuteResult<List<ItemCatCascadeDTO>> executeResult = this.itemCategoryService.queryItemCategoryTreeByThirdCid(thirdCidList);
		System.out.println(JSON.toJSONString(executeResult));
	}

}
