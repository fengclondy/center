package com.bjucloud.contentcenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.common.ExecuteResult;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.CreditsExchangeDTO;
import com.bjucloud.contentcenter.service.CreditsExchangeService;

import junit.framework.TestCase;

public class CreditsExchangeServiceImplTest extends TestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditsExchangeServiceImplTest.class);
	ApplicationContext ctx = null;
	CreditsExchangeService creditsExchangeService = null;

	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		creditsExchangeService = (CreditsExchangeService) ctx.getBean("creditsExchangeService");
	}

	@Test
	public void testAddCreditExchange() throws Exception {
		CreditsExchangeDTO dto = new CreditsExchangeDTO();
		dto.setTitle("标题1");
		dto.setItemType(1L);
		dto.setPointNum(100L);
		dto.setSortNum(1L);
		dto.setSpuId(10000L);
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		dto.setStatus(1L);
		ExecuteResult<CreditsExchangeDTO> ex = creditsExchangeService.insert(dto);
		Assert.assertNotNull(ex);
	}

	@Test
	public void testUpdateCreditExchange() throws Exception {
		CreditsExchangeDTO dto = new CreditsExchangeDTO();
		dto.setId(1L);
		dto.setTitle("修改标题1");
		dto.setItemType(2L);
		dto.setPointNum(1000L);
		dto.setSortNum(10L);
		dto.setSpuId(10000L);
		dto.setCreateTime(new Date());
		dto.setUpdateTime(new Date());
		dto.setStatus(2L);
		ExecuteResult<String> ex = creditsExchangeService.update(dto);
		Assert.assertNotNull(ex);
	}

	@Test
	public void testDeleteCreditExchange() throws Exception {
		ExecuteResult<String> ex = creditsExchangeService.delete(1L);
		Assert.assertNotNull(ex);
	}

	@Test
	public void testqueryCreditExchangeList() throws Exception {
		Pager<CreditsExchangeDTO> pager = new Pager<CreditsExchangeDTO>();
		pager.setRows(10);
		CreditsExchangeDTO dto = new CreditsExchangeDTO();
		dto.setItemType(1L);
		ExecuteResult<DataGrid<CreditsExchangeDTO>> result = creditsExchangeService.queryCreditExchangeList(dto, pager);
		Assert.assertNotNull(result);
	}

	@Test
	public void testqueryCreditsByCredits() throws Exception {
		CreditsExchangeDTO dto = new CreditsExchangeDTO();
		dto.setItemType(1L);
		List<CreditsExchangeDTO> listCreditsExchangeDTO = creditsExchangeService.queryCreditsByCredits(dto);
		Assert.assertNotNull(listCreditsExchangeDTO);
	}

}
