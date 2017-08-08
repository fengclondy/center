package cn.htd.usercenter.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.CustomerDTO;

@Ignore
public class CustomerServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceTest.class);
	ApplicationContext ctx = null;
	CustomerService customerService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		customerService = (CustomerService) ctx.getBean("customerService");
	}

	@Test
	public void testLogin_Ok() {

		// CustomerDTO paramDTO = new CustomerDTO();
		// paramDTO.setUserId(new Long(1));
		// paramDTO.setLastUpdatedId(new Long(1));
		// long userId = 1l;
		// CustomerDTO res = customerService.getCustomerInfo(userId);
		ExecuteResult<DataGrid<CustomerDTO>> res = customerService.selectCustomerList("", 0, 1, 1);
		LOGGER.info("paramDTO:" + res.toString());
	}

}
