package cn.htd.basecenter.service;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.basecenter.dto.WebSiteMessageDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public class BaseWebSiteMessageServiceTest {
	ApplicationContext ctx = null;
	BaseWebSiteMessageService baseWebSiteMessageService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		baseWebSiteMessageService = (BaseWebSiteMessageService) ctx.getBean("baseWebSiteMessageService");
	}

	@Test
	public void textgetWebSiteMessageInfo() {
		WebSiteMessageDTO wsm = new WebSiteMessageDTO();
		wsm.setId(3l);
		ExecuteResult<WebSiteMessageDTO> ss = baseWebSiteMessageService.getWebSiteMessageInfo(wsm);
		System.out.println(ss.getResult());
	}

	@Test
	public void textQueryWebSiteMessageWithOutDel() {
		WebSiteMessageDTO wsm = new WebSiteMessageDTO();
		wsm.setWmCreated(Date.valueOf("2015-05-15"));
		Pager page = new Pager();
		page.setPage(1);
		page.setRows(10000);
		DataGrid<WebSiteMessageDTO> ss = baseWebSiteMessageService.queryWebSiteMessageList(wsm, page);
		System.out.println(ss.getTotal() + "**************************");
	}

	@Test
	public void textModifyWebSiteMessage() {
		String[] ids = { "1" };
		ExecuteResult<String> result = baseWebSiteMessageService.modifyWebSiteMessage(ids, "2");
		System.out.println(result.getResult());
	}
}
