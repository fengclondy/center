package com.bjucloud.contentcenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallIconSerDTO;
import com.bjucloud.contentcenter.dto.MallIconSerInDTO;
import com.bjucloud.contentcenter.service.MallIconSerService;

public class MallIconSerServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallIconSerServiceImplTest.class);
	ApplicationContext ctx = null;
	MallIconSerService  mallIconSerService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallIconSerService = (MallIconSerService) ctx.getBean("mallIconSerService");
	}

	@Test
	public void testAdd() {

		MallIconSerInDTO dto = new MallIconSerInDTO();
		dto.setTitle("测试数据……");
		dto.setIconLink("www.baidu.com");
		dto.setSortNum(1);
		dto.setStatus(1);
		dto.setSugSize("111*222");
		mallIconSerService.addMallIconSer(dto);
	}
	
	@Test
	public void testDelete() {
		ExecuteResult<String> result =mallIconSerService.delete(1);

		LOGGER.info(JSONObject.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryMallIconSerList(){
		Pager pager = new Pager();
		//设置当前页的起始记录
		pager.setPageOffset(1);
		//设置每页显示的记录数
		pager.setRows(10);
		//设置当前页
		pager.setPage(1);
		MallIconSerDTO mallIconSerDTO = new MallIconSerDTO();
		mallIconSerDTO.setIconType("2");
		DataGrid<MallIconSerDTO> size = mallIconSerService.queryMallIconSerList(mallIconSerDTO, null, pager);
		LOGGER.info("----testQueryMallIconSerList11111----:" + size.getTotal());
	}
	
	@Test
	public void testGetMallIconSerById(){
		
		mallIconSerService.getMallIconSerById(102);
	}
	
	@Test
	public void testQueryMallIconSerList2(){
		Pager pager = new Pager();
		//设置当前页的起始记录
		pager.setPageOffset(2);
		//设置每页显示的记录数
		pager.setRows(10);
		//设置当前页
		pager.setPage(1);
		mallIconSerService.queryMallIconSerList("1", pager);
		
	}
	
	@Test
	public void testMotifyMallIconSer(){
		MallIconSerInDTO ms = new MallIconSerInDTO();
		ms.setId(91);
		ms.setSortNum(2);
		ms.setStatus(1);
		ms.setTitle("下雨了……");
		ms.setIconLink("www.123.com");
		ms.setImgPath("d:/123213/qw");
		mallIconSerService.motifyMallIconSer(ms);
	}
	
	@Test
	public void testMotifyMallIconSerStatus(){
		mallIconSerService.motifyMallIconSerStatus(131, "0");
	}
	@Test
	public void testMotifyMallIconSerSortNum(){
		mallIconSerService.modifyMallIconSerSort(92, 111);
	}

}
