package cn.htd.storecenter.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.storecenter.service.QQCustomerService;

import java.util.Date;

public class QQCustomerServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(QQCustomerServiceTest.class);
	ApplicationContext ctx = null;
	QQCustomerService qqCustomerService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		qqCustomerService = (QQCustomerService) ctx.getBean("qqCustomerService");
	}

	@Test
	public void findQQCustomerByCondition() {
		QQCustomerDTO qqCustomerDTO = new QQCustomerDTO();
		qqCustomerDTO.setSellerId(123l);
		Pager<QQCustomerDTO> page = new Pager<QQCustomerDTO>();
		DataGrid<QQCustomerDTO> dataGrid = qqCustomerService.findQQCustomerByCondition(qqCustomerDTO, page);
		LOGGER.info("操作方法{}，结果信息{}", "findQQCustomerByCondition", dataGrid.getTotal());
	}

	@Test
	public void deleteQQCustomer() {
		QQCustomerDTO dto = new QQCustomerDTO();
		dto.setModifyId(999l);
		dto.setModifyName("王家卫");
		dto.setId(1l);
		qqCustomerService.deleteQQCustomer(dto);
	}

	@Test
	public void modifyQQCustomer() {
		QQCustomerDTO qqCustomerDTO = new QQCustomerDTO();
		qqCustomerDTO.setId(2L);
		qqCustomerDTO.setModifyName("王家卫");
		qqCustomerDTO.setModifyId(999l);
		qqCustomerDTO.setCustomerQQNumber("555555555");
		ExecuteResult<String> executeResult = qqCustomerService.modifyQQCustomer(qqCustomerDTO);
		LOGGER.info("操作方法{}，结果信息{}", "deleteQQCustomer", executeResult.getResultMessage());
	}

	@Test
	public void setDefaultCustomer() {
		QQCustomerDTO dto = new QQCustomerDTO();
		dto.setId(1l);
		dto.setShopId(2000000354l);
		dto.setModifyId(666l);
		dto.setModifyName("呵呵哒");
		dto.setIsDefault(1);
		qqCustomerService.setDefaultCustomer(dto);
	}

	@Test
	public void addQQCustomerTest() {
		QQCustomerDTO dto = new QQCustomerDTO();
		dto.setCustomerType("1");
		dto.setSellerId(123l);
		dto.setShopId(2000000354l);
		dto.setShopName("我的店铺");
		dto.setCustomerQQNumber("565240746");
		dto.setIsDefault(1);
		dto.setCustomerSortNumber(2);
		dto.setDeleted(0);
		dto.setCreateId(123l);
		dto.setCreateName("周星驰");
		dto.setCreateTime(new Date());
		dto.setModifyId(999l);
		dto.setModifyName("刘德华");
		dto.setModifyTime(new Date());
		qqCustomerService.addQQCustomer(dto);
	}

}
