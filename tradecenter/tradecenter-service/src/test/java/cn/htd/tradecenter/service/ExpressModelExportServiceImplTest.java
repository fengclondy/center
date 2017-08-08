package cn.htd.tradecenter.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.ExpressModelDTO;

public class ExpressModelExportServiceImplTest {
	ExpressModelExportService expressModelExportService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		expressModelExportService = (ExpressModelExportService) ctx.getBean("expressModelExportService");
	}

	@Test
	public void addExpressModelTest() {
		ExpressModelDTO expressModelDto = new ExpressModelDTO();
		// expressModelDto.setCreatetime(new Date());
		expressModelDto.setDeliveryCode("1");
		expressModelDto.setDeliveryName("圆通速递");
		expressModelDto.setExpressPrint("1,2,3");
		expressModelDto.setExpressPicUrl(
				"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png");
		expressModelDto.setIsDefault(1);
		expressModelDto.setModel("<div></div>");
		expressModelDto.setModelHeigh("300");
		expressModelDto.setModelWidth("220");
		expressModelDto.setName("圆通速递");
		// expressModelDto.setSellerid(1000001054);
		// expressModelDto.setShopid(2000000299);
		expressModelDto.setMoveTop("2");
		expressModelDto.setMoveLeft("5");
		ExecuteResult<String> result = expressModelExportService.addExpressModel(expressModelDto);
		System.out.println("添加快递单数据结果：" + result.getResultMessage());
	}

	@Test
	public void getExpressModelTest() {
		ExpressModelDTO expressModelDto = new ExpressModelDTO();
		// expressModelDto.setId(99);

		ExecuteResult<ExpressModelDTO> result = expressModelExportService.getExpressModelByID(expressModelDto);
		System.out.println("查询快递单模版结果：" + result.getResultMessage());
		System.out.println(result.getResult().toString());
	}

	@Test
	public void getExpressModelListTest() {
		ExpressModelDTO expressModelDto = new ExpressModelDTO();
		// expressModelDto.setSellerid(1000001040);
		// expressModelDto.setShopid(2000000293);
		// expressModelDto.setName("圆通快递");

		expressModelDto.setIsDefault(1);
		// expressModelDto.setSellerid(1000001054);
		// expressModelDto.setShopid(2000000299);
		Pager<ExpressModelDTO> pager = new Pager<ExpressModelDTO>();
		pager.setPage(1);
		ExecuteResult<DataGrid<ExpressModelDTO>> ex = expressModelExportService.getExpressModelList(expressModelDto,
				pager);

		System.out.println("查询快递单列表结果：" + ex.getResultMessage());
		if (null != ex && "success".equals(ex.getResultMessage())) {
			System.out.println("结果个数：" + ex.getResult().getTotal().intValue());
		}
	}

	@Test
	public void deteleExpressModelTest() {
		ExecuteResult<String> ex = expressModelExportService.deteleExpressModel("99");
		System.out.println("删除快递单列表结果：" + ex.getResultMessage());
	}

	@Test
	public void modifyExpressModelTest() {
		ExpressModelDTO expressModelDto = new ExpressModelDTO();
		// expressModelDto.setId(99);

		ExecuteResult<ExpressModelDTO> result = expressModelExportService.getExpressModelByID(expressModelDto);

		expressModelDto = result.getResult();
		// expressModelDto.setModifytime(new Date());
		expressModelDto.setDeliveryName("顺丰速递");
		expressModelDto.setDeliveryCode("2");
		expressModelDto.setExpressPrint("1,2,3,4,5,6");
		expressModelDto.setName("顺丰模版");

		ExecuteResult<String> rlt = expressModelExportService.modifyExpressModel(expressModelDto);
		System.out.println("修改快递单模版结果：" + rlt.getResultMessage());

	}

	@Test
	public void getExpressModelByNameTest() {
		ExpressModelDTO expressModelDto = new ExpressModelDTO();
		expressModelDto.setName("圆通快递");
		expressModelDto.setIsDefault(1);
		// expressModelDto.setSellerid(1000001054);
		// expressModelDto.setShopid(2000000299);

		ExecuteResult<List<ExpressModelDTO>> result = expressModelExportService.getExpressModelByName(expressModelDto);
		System.out.println("查询快递单模版结果：" + result.getResultMessage());
		System.out.println("查询快递单模版结果个数：" + result.getResult().size());
	}

	@Test
	public void getExpressSystemModelTest() {
		ExpressModelDTO expressModelDto = expressModelExportService.getExpressSystemModel(2);
		System.out.println("查询成功：" + expressModelDto.toString());
	}
}
