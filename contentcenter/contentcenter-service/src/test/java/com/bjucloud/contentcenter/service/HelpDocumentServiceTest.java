package com.bjucloud.contentcenter.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import com.bjucloud.contentcenter.common.constants.HelpDocConstants;
import com.bjucloud.contentcenter.common.utils.HelpDocumentUtils;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.domain.HelpDocFirstCategory;
import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;

public class HelpDocumentServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelpDocumentServiceTest.class);
	
	ApplicationContext ctx = null;
	HelpDocumentService helpDocumentService = null;
	
	// 帮助中心工具类
	HelpDocumentUtils helpDocumentUtils  = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		helpDocumentService = (HelpDocumentService) ctx.getBean("helpDocumentService");
		helpDocumentUtils = (HelpDocumentUtils) ctx.getBean("helpDocumentUtils");
	}

	
	/**
	 * 添加分类
	 */
	@Test
	public void addHelpDocCategoryTest() {
		
//	     parentCategoryName: parentCategoryName,
//	        parentCategoryCode: parentCategoryCode,
//	        sortNum: sortNum,
//	        categoryName: categoryName '10','买家帮助'
		
		Date currentTime = Calendar.getInstance().getTime();
		Long userId = 10000L;
		String userName = "test";
		
		String parentCategoryCode = "10";
		String parentCategoryName = "买家帮助";
		Integer sortNum = 3;
		String categoryName = "测试二级分类-zzf22";
		
		HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
		helpDocCategoryDTO.setParentCategoryCode(parentCategoryCode);
		helpDocCategoryDTO.setParentCategoryName(parentCategoryName);
		helpDocCategoryDTO.setSortNum(sortNum);
		helpDocCategoryDTO.setCategoryName(categoryName);
		
		helpDocCategoryDTO.setCreateId(userId);
		helpDocCategoryDTO.setCreateName(userName);
		helpDocCategoryDTO.setCreateTime(currentTime);
		helpDocCategoryDTO.setModifyId(userId);
		helpDocCategoryDTO.setModifyName(userName);
		helpDocCategoryDTO.setModifyTime(currentTime);
        
		ExecuteResult<?>  executeResult = helpDocumentService.addHelpDocCategory(helpDocCategoryDTO);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}
		
	}
	
	/**
	 * 根据编码删除分类
	 */
	@Test
	public void deleteHelpDocCategoryByCodeTest() {
		//分类编码
		String code = "1013";
		ExecuteResult<?>  executeResult = helpDocumentService.deleteHelpDocCategoryByCode(code);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}
		
	}
	
	/**
	 * 更新分类
	 */
	@Test
	public void updateHelpDocCategoryTest() {
		//分类编码
		Date currentTime = Calendar.getInstance().getTime();
		Long userId = 10000L;
		String userName = "test2";
		
		String categoryCode = "1013";
		String categoryName = "测试二级分类-zzf12";
		Integer sortNum = 3;
		
		HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
		helpDocCategoryDTO.setCategoryCode(categoryCode);
		helpDocCategoryDTO.setCategoryName(categoryName);
		helpDocCategoryDTO.setSortNum(sortNum);
		
		helpDocCategoryDTO.setModifyId(userId);
		helpDocCategoryDTO.setModifyName(userName);
		helpDocCategoryDTO.setModifyTime(currentTime);
		
		ExecuteResult<?>  executeResult = helpDocumentService.updateHelpDocCategory(helpDocCategoryDTO);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
		
	}
	
	
	/**
	 * 分页查询分类
	 */
	@Test
	public void getHelpDocCategoriesForPageTest() {
		
		Pager<HelpDocCategoryDTO> page = new Pager<HelpDocCategoryDTO>();
		page.setPage(1);
		page.setRows(20);
		HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
		helpDocCategoryDTO.setParentCategoryCode("10");
		ExecuteResult<DataGrid<HelpDocCategory>>  executeResult = helpDocumentService.getHelpDocCategoriesForPage(page,helpDocCategoryDTO);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}
		
	}
	
	/**
	 * 根据分类编号查询单个分类
	 */
	@Test
	public void getSingleHelpDocCategoryByCodeTest() {
		
		String categoryCode = "1010";
		ExecuteResult<HelpDocCategory>  executeResult = helpDocumentService.getSingleHelpDocCategoryByCode(categoryCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}
		
	}
	
	/**
	 * 查询所有一级分类
	 */
	@Test
	public void getTopHelpDocCategoryTest() {
		
		ExecuteResult<List<HelpDocCategory>>  executeResult = helpDocumentService.getTopHelpDocCategory();
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}
		
	}
	
	
	/**
	 * 根据一级分类编码查询所有二级分类
	 */
	@Test
	public void getSecondHelpDocCategoryTest() {
		String firstCategoryCode = "10";
		ExecuteResult<List<HelpDocCategory>>  executeResult = helpDocumentService.getSecondHelpDocCategory(firstCategoryCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
		
	}
	
	/**
	 * 查询所有完成的一级分类(包含发布的二级分类)
	 */
	@Test
	public void getFirstCmptHelpDocCategoryTest() {
		
		ExecuteResult<List<HelpDocFirstCategory>>  executeResult = helpDocumentService.getFirstCmptHelpDocCategory();
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
		
	}
	
	/**
	 * 生成二级分类编号测试
	 */
	@Test
	public void generateSecondCategoryCodeTest() {
		
		String firstCategoryCode = "10";
		String secondCategoryCode = helpDocumentUtils.generateSecondCategoryCode(firstCategoryCode);
		System.out.println("===>secondCategoryCode:" + secondCategoryCode);
		
	}
	
	/**
	 * 生成主题编号测试
	 */
	@Test
	public void generateTopicCodeTest() {
		
		String secondCategoryCode = "1010";
		String topicCode = helpDocumentUtils.generateTopicCode(secondCategoryCode);
		System.out.println("===>topicCode:" + topicCode);
		
	}
	
	/**
	 * 添加帮助文档测试类
	 */
	@Test
	public void addHelpDocumentTest(){
		
		Date currentTime = Calendar.getInstance().getTime();
		Long userId = 10000L;
		String userName = "test";
		String categoryCode = "1013";
		String categoryName = "测试二级分类-zzf12";
		String parentCategoryCode = "10";
		String parentCategoryName = "买家帮助";
		Integer sortNum = 101;
		
		HelpDocTopicDTO dto = new HelpDocTopicDTO();
		dto.setTopictCode("101010000");
		dto.setTopictName("测试添加帮助文档2");
		dto.setCategoryCode(parentCategoryCode);
		dto.setCategoryName(parentCategoryName);
		dto.setSecondCategoryCode(categoryCode);
		dto.setSecondCategoryName(categoryName);
		dto.setSortNum(sortNum);
		dto.setContent("啦啦啦啦啦啦啦啦啦啦啦啦阿里");
		dto.setIsPublish(2);
		dto.setCreateId(userId);
		dto.setCreateName(userName);
		dto.setCreateTime(currentTime);
		dto.setModifyId(userId);
		dto.setModifyName(userName);
		dto.setModifyTime(currentTime);
		
		ExecuteResult<?> result=helpDocumentService.addHelpDocument(dto);
		LOGGER.info("result:"+result.getResultMessage()+"  ,code:"+result.getCode());
	}
	
	/**
	 * 修改帮助文档测试
	 */
	@Test
	public void editHelpDocTopicTest()
	{
		Date currentTime = Calendar.getInstance().getTime();
		Long userId = 10000L;
		String userName = "test";
		HelpDocTopicDTO dto = new HelpDocTopicDTO();
		dto.setTopictCode("101010000");
		dto.setTopictName("修改测试添加帮助文档");
	
		dto.setModifyId(userId);
		dto.setModifyName(userName);
		dto.setModifyTime(currentTime);
		ExecuteResult<?> result=helpDocumentService.editHelpDocTopic(dto);
		LOGGER.info("result:"+result.getResultMessage()+"  ,code:"+result.getCode());
	}
	
	/**
	 * 修改发布状态
	 */
	@Test
	public void updateIsPublisTest(){
		HelpDocTopicDTO dto = new HelpDocTopicDTO();
		dto.setIsPublish(0);
	//	dto.setId(1L);
		dto.setTopictCode("10131007");
		dto.setModifyTime(new Date());
		ExecuteResult<?> result=helpDocumentService.updateIsPublis(dto);
		LOGGER.info("result:"+result.getResultMessage()+"  ,code:"+result.getCode());

	}
	/**
	 * 删除帮助中心
	 */
	@Test
	public void deleteHelpDocTopicTest(){
		HelpDocTopicDTO dto = new HelpDocTopicDTO();
		
		dto.setTopictCode("101010000");
		ExecuteResult<?> result=helpDocumentService.deleteHelpDocTopic(dto.getTopictCode());
		LOGGER.info("result:"+result.getResultMessage()+"  ,code:"+result.getCode());

	}
	/**
	 * 查询单条帮助中心
	 */
	@Test
	public void queryHelpDocByTopicCodeTest(){
		
		ExecuteResult<HelpDocTopic> result = helpDocumentService.queryHelpDocByTopicCode("10131007");
		LOGGER.info(result.getResult().getTopictName());
	}
	/**
	 * 根据条件查询
	 */
	@Test
	public void queryHelpDocTopicForPageTest(){
		Pager<HelpDocTopicDTO> page =new Pager<HelpDocTopicDTO>();
		page.setPage(1);
		page.setRows(10);
		HelpDocTopicDTO dto =new HelpDocTopicDTO();
		dto.setTopictName("测试");
		ExecuteResult<DataGrid<HelpDocTopic>> result = helpDocumentService.queryHelpDocTopicForPage(page, dto);
		LOGGER.info("result:"+result.getResult().getRows());
	}
}
