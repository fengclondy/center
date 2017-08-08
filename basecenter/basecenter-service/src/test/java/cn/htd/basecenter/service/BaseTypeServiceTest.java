package cn.htd.basecenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

import cn.htd.basecenter.dto.BaseTypeDTO;
import cn.htd.common.ExecuteResult;
import junit.framework.Assert;

public class BaseTypeServiceTest {
	private BaseTypeService typeService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		typeService = (BaseTypeService) ctx.getBean("typeService");
	}

	@Test
	@Rollback(true)
	public void testTypeAdd() {
		BaseTypeDTO typeDTO = new BaseTypeDTO();
		typeDTO.setName("刘军测试");
		typeDTO.setType("刘军测试");
		typeDTO.setStatus(1);
		typeService.addBaseType(typeDTO);
		Assert.assertNotNull(typeDTO.getId());
	}

	@Test
	public void testQueryTypeById() {
		Long id = 1L;
		ExecuteResult<BaseTypeDTO> result = typeService.queryBaseTypeById(id);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + result.getResult().getName());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + result.getResult().getType());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + result.getResult().getStatus());

	}

	// @Test
	// public void testTypeDel() {
	// Integer id = 19;
	// int status = 0;
	// BaseTypeDTO typeDTO = new BaseTypeDTO();
	// typeDTO.setId(id);
	// typeDTO.setStatus(status);
	// typeService.delType(typeDTO);
	// }
	//
	// @Test
	// public void testTypeUpdate() {
	// Integer id = 18;
	// int status = 0;
	// BaseTypeDTO typeDTO = new BaseTypeDTO();
	// typeDTO.setId(id);
	// typeDTO.setStatus(status);
	// typeService.updType(typeDTO);
	// }
	//
	// @Test
	// public void testQueryTypeByCondition() {
	// BaseTypeDTO typeDTO = new BaseTypeDTO();
	// Pager pager = new Pager();
	// pager.setPage(2);
	// DataGrid<BaseTypeDTO> result = typeService.queryTypeByCondition(typeDTO,
	// pager);
	// for (BaseTypeDTO element : result.getRows()) {
	// System.out.println(element.getType() + "type");
	// }
	// }
	//
	// @Test
	// public void testQueryType() {
	// BaseTypeDTO typeDTO = new BaseTypeDTO();
	// Pager pager = new Pager();
	// pager.setPage(2);
	// List<BaseTypeDTO> result = typeService.queryType();
	// System.out.println(">>>>>>>>>>>>>>>>>>" + result.size());
	// }
	//
	// @Test
	// public void testuniquenessType() {
	// BaseTypeDTO typeDTO = new BaseTypeDTO();
	// typeDTO.setType("sdf");
	// boolean b = typeService.uniquenessType(typeDTO);
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + b);
	// }
}
