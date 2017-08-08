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
import com.bjucloud.contentcenter.dto.MallNoticeDTO;
import com.bjucloud.contentcenter.service.NoticeExportService;

public class NoticeExportServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeExportServiceImplTest.class);
	ApplicationContext ctx = null;
	NoticeExportService noticeExportService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		noticeExportService = (NoticeExportService) ctx.getBean("noticeExportService");
	}

	@Test
	public void test() {
		Pager page = new Pager();
		MallNoticeDTO dto = new MallNoticeDTO();
		dto.setTitle("公告");
		DataGrid<MallNoticeDTO> res = noticeExportService.queryNoticeList(page, dto);
		System.out.println("----------------res:" + res.getTotal());
		LOGGER.info("操作方法{}，结果信息{}", "queryNoticeList", JSONObject.toJSONString(res));
	}

	@Test
	public void test_add() {
		MallNoticeDTO addDto = new MallNoticeDTO();
		addDto.setPlatformId(0L);
		addDto.setNoticeType(1);
		addDto.setTitle("添加通知测试测试");
		addDto.setUrl("123");
		addDto.setContent("这是测试内容");
		addDto.setThemeId(123);
		ExecuteResult<String> result = noticeExportService.addNotice(addDto);

		LOGGER.info("操作方法{}，结果信息{}", "queryNoticeList", JSONObject.toJSONString(addDto));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void test_updateNoticSortNum() {
		MallNoticeDTO addDto = new MallNoticeDTO();
		addDto = noticeExportService.getNoticeInfo(60L);
		ExecuteResult<MallNoticeDTO> result = noticeExportService.updateNoticSortNum(addDto, 1);

		LOGGER.info("操作方法{}，结果信息{}", "queryNoticeList", JSONObject.toJSONString(addDto));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyNmeanoticeInfo() {
		MallNoticeDTO dto = new MallNoticeDTO();
		dto.setUrl("3455");
		dto.setStatus(1);
		dto.setSortNum(3);
		dto.setTitle("修改测试");
		dto.setContent("修改后的内容");
		dto.setId(66L);
		dto.setNoticeType(2);
		dto.setThemeId(1221);
		ExecuteResult<String> result = this.noticeExportService.modifyNoticeInfo(dto);
	}

	@Test
	public void testModifyNoticeRecommend() {
		noticeExportService.modifyNoticeRecommend(66L, 1);
	}

}
