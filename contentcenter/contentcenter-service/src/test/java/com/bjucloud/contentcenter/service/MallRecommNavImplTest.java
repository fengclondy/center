package com.bjucloud.contentcenter.service;

import java.util.List;

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
import com.bjucloud.contentcenter.dto.MallRecommNavDTO;
import com.bjucloud.contentcenter.dto.MallRecommNavECMDTO;
import com.bjucloud.contentcenter.service.MallRecommNavService;

public class MallRecommNavImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallRecommNavImplTest.class);
	ApplicationContext ctx = null;
	MallRecommNavService mallRecommNavService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallRecommNavService = (MallRecommNavService) ctx.getBean("mallRecommNavService");
	}

	@Test
	public void testAdd() {
		MallRecommNavDTO dto = new MallRecommNavDTO();
		dto.setTitle("测试楼层导航……");
		dto.setUrl("www.baidu.com");
		dto.setSortNum(2);
		dto.setStatus(1);
		mallRecommNavService.addMallRecNav(dto);
	}

	@Test
	public void testDelete() {
		ExecuteResult<String> result = mallRecommNavService.delete(1);

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
		MallRecommNavDTO mn = new MallRecommNavDTO();
		// mn.setNavTitle(navTitle);
		DataGrid<MallRecommNavDTO> size = mallRecommNavService.queryMallRecNavList(mn, null, pager);
		List<MallRecommNavDTO> li = size.getRows();
		for (MallRecommNavDTO md : li) {
			System.out.println("-----md.getId():" + md.getId());
			System.out.println("-----md.getTitle():" + md.getTitle());
			System.out.println("-----md.getIsImg():" + md.getIsImg());
			System.out.println("-----md.getnType():" + md.getnType());
			System.out.println("-----md.getRecId():" + md.getRecId());
			System.out.println("-----md.getSortNum():" + md.getSortNum());
			System.out.println("-----md.getStatus():" + md.getStatus());
			System.out.println("-----md.getPicSrc():" + md.getPicSrc());
			System.out.println("-----md.getRecommendName():" + md.getRecommendName());
			System.out.println("-----md.getRecId():" + md.getRecId());
			System.out.print("------------------------------------------------");
		}
		LOGGER.info("--------:" + size.getTotal());
	}

	@Test
	public void testequeryMallRecNavList() {
		Pager pager = new Pager();
		// 设置当前页的起始记录
		pager.setPageOffset(2);
		// 设置每页显示的记录数
		pager.setRows(10);
		// 设置当前页
		pager.setPage(1);
		MallRecommNavECMDTO mn = new MallRecommNavECMDTO();
		// mn.setNavTitle(navTitle);
		mn.setThemeType(1);
		mn.setThemeStatus(1);
		mn.setStatus(1);
		DataGrid<MallRecommNavECMDTO> size = mallRecommNavService.equeryMallRecNavList(mn, null, pager);
		System.out.println("------------testequeryMallRecNavList--------------" + size.getTotal());
		// ExecuteResult<String> result =mallRecommNavService.delete(1);

	}

	@Test
	public void testMotifyMallNav() {
		MallRecommNavDTO md = new MallRecommNavDTO();
		md.setId(5);
		md.setTitle("ceshi^^^^^^");
		md.setTitle("www.baidu.com");
		md.setSortNum(1);
		md.setStatus(1);
		mallRecommNavService.motifyMallRecNav(md);
	}

	@Test
	public void testQueryMallNavList2() {
		MallRecommNavDTO md = new MallRecommNavDTO();
		Pager pager = new Pager();
		// 设置当前页的起始记录
		pager.setPageOffset(2);
		// 设置每页显示的记录数
		pager.setRows(10);
		// 设置当前页
		pager.setPage(1);

		// md.setNavLink("www.baidu.com");
		// md.setSortNum(1);
		// md.setStatus(1);
		md.setIsImg(1);
		md.setnType(4);
		md.setRecId(21);
		DataGrid<MallRecommNavDTO> dg = mallRecommNavService.queryMallRecNavList(md, "1", pager);
		MallRecommNavDTO mmn = dg.getRows().get(0);
		System.out.println("----332211-----------mmn.getId():" + mmn.getId() + "|mmn.getIsImg():" + mmn.getIsImg()
				+ "|mmn.getStatus():" + mmn.getStatus() + "|rownum:" + dg.getTotal());

	}

	@Test
	public void testMotifyMallRecNavStatus() {
		mallRecommNavService.motifyMallRecNavStatus(28, "0");
	}
}
