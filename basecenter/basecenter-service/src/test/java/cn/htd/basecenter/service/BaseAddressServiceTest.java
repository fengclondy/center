package cn.htd.basecenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.service.task.MailErpExceptionAddressScheduleTask;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.basecenter.dto.AddressInfo;

public class BaseAddressServiceTest {
	private BaseAddressService addressBaseService;
	ApplicationContext ctx;
	MailErpExceptionAddressScheduleTask mailErpExceptionAddressScheduleTask;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		addressBaseService = (BaseAddressService) ctx.getBean("baseAddressService");
		mailErpExceptionAddressScheduleTask = (MailErpExceptionAddressScheduleTask) ctx.getBean("mailErpExceptionAddressScheduleTask");
	}



	@Test
	public void queryqueryBaseAddress2ErpException() {

		ExecuteResult<DataGrid<BaseAddressDTO>> executeResult= addressBaseService.queryBaseAddress2ErpException(null);
		System.out.println(executeResult.getResult().getRows().size());
	}

	@Test
	public void queryAddressBaseTest() {

		List<AddressInfo> ss = addressBaseService.getAddressList("020");
		System.out.println(ss.size());
	}

	@Test
	public void testdownBaseAddress2Erp() {
		BaseAddressDTO baseAddressDTO = new BaseAddressDTO();
		baseAddressDTO.setId(9008L);
		baseAddressDTO.setName("和平里街道");
		baseAddressDTO.setCode("110101010");
		baseAddressDTO.setParentCode("110101011");
		baseAddressDTO.setErpDownTime(new Date());
		ExecuteResult<String>  ss = addressBaseService.downBaseAddress2Erp(baseAddressDTO);
		System.out.println(ss);
	}


	// @Test
	// public void queryNameByIdTest() {
	// Integer id = 6;
	// ExecuteResult<BaseAddressDTO> er = addressBaseService.queryNameById(id);
	// System.out.println(er.getResult().getName());
	// }
	//
	// @Test
	// public void queryNameByCodeTest() {
	// String code = "";
	// ExecuteResult<List<BaseAddressDTO>> er =
	// addressBaseService.queryNameByCode(code);
	// System.out.println(JSON.toJSONString(er.getResult()));
	// }




	@Test
	public void testmailErpExceptionAddressScheduleTask() throws Exception {
		mailErpExceptionAddressScheduleTask.selectTasks("15", "", 1, null, 500);
	}
}
