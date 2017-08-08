package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.ItemOldDTO;
import cn.htd.goodscenter.dto.ItemPictureDTO;
import cn.htd.goodscenter.dto.indto.ItemOldInDTO;
import cn.htd.goodscenter.dto.indto.ItemOldSeachInDTO;
import cn.htd.goodscenter.dto.outdto.ItemOldOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemOldSeachOutDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import com.alibaba.fastjson.JSON;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemOldExportServiceTest  extends CommonTest {

    @Resource
	ItemOldExportService itemOldExportService ;

	@Test
	public void addItemOldTest(){
		ItemOldInDTO itemOldInDTO =new ItemOldInDTO();
		
		ItemOldDTO itemOldDTO=new ItemOldDTO();
		itemOldDTO.setCid(1l);//类目ID 三级类目
		itemOldDTO.setSellerId(1l);//卖家id
		itemOldDTO.setItemName("二手商品");//  二手商品名称
		itemOldDTO.setRecency(10);//  新旧程度 全新10
		itemOldDTO.setPriceOld(BigDecimal.valueOf(100));//  原价
		itemOldDTO.setPrice(BigDecimal.valueOf(80));//  现价
		itemOldDTO.setFreight(BigDecimal.valueOf(10));//  运费
		itemOldDTO.setSellerTel(13888889999l);//  卖家联系方式
		itemOldDTO.setSellerName("小叼");//  卖家姓名
		itemOldDTO.setProvinceCode("1");//  省编码
		itemOldDTO.setProvinceName("1省");//  省名称
		itemOldDTO.setCityCode("101");//  市编码
		itemOldDTO.setCityName("101市");//  市名称
		itemOldDTO.setDistrictCode("10101");//  区编码
		itemOldDTO.setDistrictName("10101区");//  区名称
		itemOldDTO.setDescribeUr("this is very nice~!");//  商品描述url，存在jfs中
		//itemOldDTO.setstatus;//  商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定， 7： 申请解锁
		
		List<ItemPictureDTO> list=new ArrayList<ItemPictureDTO>();
		ItemPictureDTO ip=new ItemPictureDTO();
		
		ip.setPictureUrl("www.pic.coo11");//图片url
		ip.setSortNumber(1);//排序号,排序号最小的作为主图，从1开始
		ip.setSellerId(1l);//卖家id
		
		list.add(ip);
		itemOldInDTO.setItemOldDTO(itemOldDTO);
		itemOldInDTO.setItemPictureDTO(list);
		
		ExecuteResult<String> result = itemOldExportService.addItemOld(itemOldInDTO);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
		
	}
	@Test
	public void modifyItemOldTest(){
		ItemOldInDTO itemOldInDTO =new ItemOldInDTO();
		
		ItemOldDTO itemOldDTO=new ItemOldDTO();
		itemOldDTO.setItemId(1000000333L);
		itemOldDTO.setCid(1l);//类目ID 三级类目
		itemOldDTO.setSellerId(1l);//卖家id
		itemOldDTO.setItemName("二手商品1");//  二手商品名称
		itemOldDTO.setRecency(10);//  新旧程度 全新10
		itemOldDTO.setPriceOld(BigDecimal.valueOf(100));//  原价
		itemOldDTO.setPrice(BigDecimal.valueOf(80));//  现价
		itemOldDTO.setFreight(BigDecimal.valueOf(10));//  运费
		itemOldDTO.setSellerTel(13888889999l);//  卖家联系方式
		itemOldDTO.setSellerName("小叼");//  卖家姓名
		itemOldDTO.setProvinceCode("1");//  省编码
		itemOldDTO.setProvinceName("1省");//  省名称
		itemOldDTO.setCityCode("101");//  市编码
		itemOldDTO.setCityName("101市");//  市名称
		itemOldDTO.setDistrictCode("10101");//  区编码
		itemOldDTO.setDistrictName("10101区");//  区名称
		itemOldDTO.setDescribeUr("this is very nice~!");//  商品描述url，存在jfs中
		//itemOldDTO.setstatus;//  商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定， 7： 申请解锁
		
		List<ItemPictureDTO> list=new ArrayList<ItemPictureDTO>();
		ItemPictureDTO ip=new ItemPictureDTO();
		
		ip.setPictureUrl("www.pic.coo11");//图片url
		ip.setSortNumber(1);//排序号,排序号最小的作为主图，从1开始
		ip.setSellerId(1l);//卖家id
		
		list.add(ip);
		itemOldInDTO.setItemOldDTO(itemOldDTO);
		itemOldInDTO.setItemPictureDTO(list);
		ExecuteResult<String> result = itemOldExportService.modifyItemOld(itemOldInDTO);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}
	@Test
	public void queryItemOldTest(){
		ItemOldDTO itemOldDTO=new ItemOldDTO();
		Integer[] ss=new Integer[]{1,2,3,4};
		itemOldDTO.setStatuss(ss);
		Pager page=new Pager();
		ExecuteResult<DataGrid<ItemOldDTO>> result = itemOldExportService.queryItemOld(itemOldDTO,page);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}
	@Test
	public void getItemOldTest(){
		ExecuteResult<ItemOldOutDTO> result = itemOldExportService.getItemOld(1000000333l);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}
	@Test
	public void modifyItemOldStatusTest(){
		ExecuteResult<String> result = itemOldExportService.modifyItemOldStatus("aa","490778b05e45423f942a4c23c3a19381",3l, 1000000095l);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
		
	}
	@Test
	public void seachItemOldTest(){
		ItemOldSeachInDTO itemOldSeachInDTO=new ItemOldSeachInDTO();
		//itemOldSeachInDTO.setKey("商品");
		//itemOldSeachInDTO.setOrderType(1);
		
		itemOldSeachInDTO.setCid(350l);
		Pager page=new Pager();
		ExecuteResult<ItemOldSeachOutDTO> result = itemOldExportService.seachItemOld(itemOldSeachInDTO, page);
		System.out.println("result------"+JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}
	
	
}
