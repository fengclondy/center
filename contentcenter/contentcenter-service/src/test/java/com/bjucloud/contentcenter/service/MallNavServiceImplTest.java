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
import com.bjucloud.contentcenter.dto.MallNavDTO;
import com.bjucloud.contentcenter.dto.MallNavInDTO;
import com.bjucloud.contentcenter.service.MallNavService;

public class MallNavServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallNavServiceImplTest.class);
	ApplicationContext ctx = null;
	MallNavService mallNavService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallNavService = (MallNavService) ctx.getBean("mallNavService");
	}

	@Test
	public void testAdd() {
		MallNavInDTO dto = new MallNavInDTO();
		dto.setNavTitle("测试导航……");
		dto.setNavLink("www.hao123.com");
		dto.setSortNum(1);
		dto.setStatus(2);
		mallNavService.addMallNav(dto);
	}

	@Test
	public void testDelete() {
		ExecuteResult<String> result = mallNavService.delete(34L);

		LOGGER.info(JSONObject.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryMallNavList() {
		Pager pager = new Pager();
		// 设置当前页的起始记录
		pager.setPageOffset(2);
		// 设置每页显示的记录数
		pager.setRows(10);
		// 设置当前页
		pager.setPage(1);
		MallNavDTO mn = new MallNavDTO();
		mn.setNavTitle("测试");
		DataGrid<MallNavDTO> size = mallNavService.queryMallNavList(mn, null, pager);
		System.out.println("--------------------size:" + size.getRows().size());
		LOGGER.info("--------:" + size.getTotal());
	}

	@Test
	public void testMotifyMallNav() {
		MallNavInDTO md = new MallNavInDTO();
		md.setId(33);
		md.setNavTitle("ceshi^^^^^^");
		md.setNavLink("www.baidu.com");
		md.setSortNum(1);
		md.setStatus(1);
		mallNavService.motifyMallNav(md);
	}

	@Test
	public void testQueryMallNavList2() {
		MallNavDTO md = new MallNavDTO();
		Pager pager = new Pager();
		// 设置当前页的起始记录
		pager.setPageOffset(2);
		// 设置每页显示的记录数
		pager.setRows(10);
		// 设置当前页
		pager.setPage(1);
		md.setNavTitle("ceshi");
		// md.setNavLink("www.baidu.com");
		// md.setSortNum(1);
		// md.setStatus(1);
		mallNavService.queryMallNavList(md, null, pager);
	}

}
