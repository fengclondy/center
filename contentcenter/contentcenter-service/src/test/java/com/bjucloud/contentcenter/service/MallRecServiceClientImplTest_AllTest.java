package com.bjucloud.contentcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecDTO;
import com.bjucloud.contentcenter.service.MallRecExportService;

/**
 * 
 * <p>
 * Description: [楼层基本信息的单元测试 ]
 * </p>
 */
public class MallRecServiceClientImplTest_AllTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallRecServiceClientImplTest_AllTest.class);

	ApplicationContext ctx = null;
	MallRecExportService mallRecExportService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallRecExportService = (MallRecExportService) ctx.getBean("mallRecExportService");
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据id查询详情]
	 * </p>
	 */
	@Test
	public void testgetMallRecById() {
		// 根据id查询详情
		MallRecDTO mallRecDTO = mallRecExportService.getMallRecById(33L);
		System.out.println("------------mallRecDTO.name:" + mallRecDTO.getTitleDTO());
		LOGGER.info(mallRecDTO.getTitleDTO());
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:单元测试 新增楼层]
	 * </p>
	 */
	@Test
	public void testaddMallRec() {
		// Unknown table 'SEQ_mall_recommend_ID' in field list
		MallRecDTO mallRecDTO = new MallRecDTO();
		mallRecDTO.setCategoryIdDTO(123l);
		mallRecDTO.setFloorNumDTO(121);
		mallRecDTO.setRecTypeDTO(123);
		mallRecDTO.setTitleDTO("title-2");
		// Data too long for column 'smalltitle' at row 1
		mallRecDTO.setSmalltitleDTO("nih");
		mallRecDTO.setColorrefDTO("#000000");
		mallRecDTO.setThemeId(112);
		mallRecExportService.addMallRec(mallRecDTO);
		LOGGER.info(mallRecDTO.getSmalltitleDTO());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testqueryMallRecList() {
		MallRecDTO mallRecDTO = new MallRecDTO();
		// mallRec.setFloorNum(1);;
		// 分页类
		Pager pager = new Pager();
		// 设置当前页的起始记录
		pager.setPageOffset(2);
		// 设置每页显示的记录数
		pager.setRows(10);
		// 设置当前页
		pager.setPage(1);
		// mallRecDTO.setTitleDTO("test");
		// mallRecDTO.setTitleDTO("TEST");
		mallRecDTO.setThemeId(11);
		mallRecDTO.setStatusDTO(0);
		DataGrid<MallRecDTO> size = mallRecExportService.queryMallRecList(mallRecDTO, pager);
		System.out.println("----------------size:" + size.getTotal());
		LOGGER.info("" + size.getTotal());
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述：单元测试 根据id修改楼层的信息]
	 * </p>
	 */
	@Test
	public void testmodifyMallRec() {
		MallRecDTO mallRecDTO = new MallRecDTO();
		mallRecDTO.setFloorNumDTO(111);
		mallRecDTO.setIdDTO(25L);
		mallRecDTO.setRecTypeDTO(123);
		mallRecDTO.setThemeId(22);
		mallRecDTO.setTitleDTO("1234567");
		mallRecExportService.modifyMallRec(mallRecDTO);
		LOGGER.info(mallRecDTO.getSmalltitleDTO());
	}

	@Test
	public void testdelete() {
		mallRecExportService.deleteMallRec(36L);
	}
}
