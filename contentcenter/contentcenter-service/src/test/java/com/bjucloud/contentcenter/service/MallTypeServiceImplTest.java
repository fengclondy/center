package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.CreditsExchangeDTO;
import com.bjucloud.contentcenter.dto.HTDMallTypeDTO;
import com.bjucloud.contentcenter.dto.HTDMallTypeSubDTO;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MallTypeServiceImplTest {
	ApplicationContext ctx = null;
	MallTypeExportService mallTypeExportService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallTypeExportService = (MallTypeExportService) ctx.getBean("mallTypeExportService");

	}

	@Test
	public void testGetMallTypeById(){
		ExecuteResult<HTDMallTypeDTO> executeResult = mallTypeExportService.getMallTypeById(1L);
	}

	@Test
	public void testmodifyMallType(){
		HTDMallTypeDTO mallTypeDTO = new HTDMallTypeDTO();
		mallTypeDTO.setId(1L);
		mallTypeDTO.setName("modify");
		mallTypeDTO.setModifyName("111L");
		mallTypeDTO.setModifyId(111L);

		HTDMallTypeSubDTO mallTypeSubDTO = new HTDMallTypeSubDTO();
		mallTypeSubDTO.setId(1L);
		mallTypeSubDTO.setName("modify1");
		mallTypeDTO.setModifyName("111L");
		mallTypeDTO.setModifyId(111L);
		List<HTDMallTypeSubDTO> lit = new ArrayList<HTDMallTypeSubDTO>();
		lit.add(mallTypeSubDTO);
		mallTypeDTO.setMallTypeSubDTOs(lit);
		mallTypeExportService.modifyMallType(mallTypeDTO);
	}

	@Test
	public void testQueryAll(){
		Pager page = new Pager();
		DataGrid<HTDMallTypeDTO> dataGrid = mallTypeExportService.queryAll(page);
		System.out.println("dsfas");
	}

}
