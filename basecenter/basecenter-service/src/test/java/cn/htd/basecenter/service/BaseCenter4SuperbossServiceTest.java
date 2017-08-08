package cn.htd.basecenter.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;

public class BaseCenter4SuperbossServiceTest {
	private BaseCenter4SuperbossService baseCenter4SuperbossService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		baseCenter4SuperbossService = (BaseCenter4SuperbossService) ctx.getBean("baseCenter4SuperbossService");
	}

	@Test
	public void queryBaseAddressListByModifyTimeTest() {

		Pager<BaseAddressDTO> pager = new Pager<BaseAddressDTO>();
		Date startDt = new Date();
		int pageSize = 0;
		startDt = DateUtils.parse("2016-03-01 00:00:00", DateUtils.YYDDMMHHMMSS);
		pager.setPageOffset(0);
		pager.setRows(100);
		ExecuteResult<DataGrid<BaseAddressDTO>> result = baseCenter4SuperbossService
				.queryBaseAddressListByModifyTime(startDt, pager);
		Long dataSize = 0L;
		String errorMsg = "";
		if (result.isSuccess()) {
			dataSize = result.getResult().getTotal();
			pageSize = result.getResult().getRows().size();
		} else {
			errorMsg = result.getErrorMessages().get(0);
		}
		System.out.println(result.isSuccess() + "-" + dataSize + "-" + pageSize + "-" + errorMsg);
	}
}
