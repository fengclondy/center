package cn.htd.membercenter.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.HelpDocConstants;
import cn.htd.membercenter.domain.HelpDocCategory;
import cn.htd.membercenter.domain.HelpDocFirstCategory;
import cn.htd.membercenter.domain.HelpDocTopic;

public class HelpDocumentServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelpDocumentServiceTest.class);
	
	ApplicationContext ctx = null;
	HelpDocumentService helpDocumentService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		helpDocumentService = (HelpDocumentService) ctx.getBean("helpDocumentService");
	}

	
	/**
	 * 根据父分类编码查询所有子分类
	 */
	@Test
	public void getCategoriesByParentCategoryCodeTest() {
		String parentCategoryCode = "01";
		ExecuteResult<List<HelpDocCategory>> executeResult = helpDocumentService.getCategoriesByParentCategoryCode(parentCategoryCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			List<HelpDocCategory> helpDocCategoryList = executeResult.getResult();
			LOGGER.info("===>helpDocCategoryList:" + helpDocCategoryList);
		}
		
	}
	
	/**
	 * 根据分类编码查询所有主题
	 */
	@Test
	public void getTopicsByCategoryCodeTest() {
		String categoryCode = "0101";
		ExecuteResult<List<HelpDocTopic>> executeResult = helpDocumentService.getTopicsByCategoryCode(categoryCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			List<HelpDocTopic> helpDocTopicList = executeResult.getResult();
			LOGGER.info("===>helpDocTopicList:" + helpDocTopicList);
		}
		
	}
	
	/**
	 * 根据主题编号查询主题
	 */
	@Test
	public void getTopicByTopictCodeTest() {
		String topictCode = "01010001";
		ExecuteResult<HelpDocTopic> executeResult = helpDocumentService.getTopicByTopictCode(topictCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			HelpDocTopic helpDocTopic = executeResult.getResult();
			LOGGER.info("===>helpDocTopic:" + helpDocTopic);
		}
		
	}
	
	@Test
	public void getFirstCategoriesByFirstCategoryTest() {
		String firstCategory = "10";
		ExecuteResult<HelpDocFirstCategory> executeResult = helpDocumentService.getFirstCategoriesByFirstCategory(firstCategory);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			HelpDocFirstCategory helpDocFirstCategory = executeResult.getResult();
			LOGGER.info("===>helpDocFirstCategory:" + helpDocFirstCategory);
		}
		
	}
	
	@Test
	public void getSecondCategoriesByFirstCategoryTest() {
		String secondCategory = "1010";
		ExecuteResult<HelpDocFirstCategory> executeResult = helpDocumentService.getSecondCategoriesByFirstCategory(secondCategory);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			HelpDocFirstCategory helpDocFirstCategory = executeResult.getResult();
			LOGGER.info("===>helpDocFirstCategory:" + helpDocFirstCategory);
		}
		
	}
	
	@Test
	public void getHelpDocTopicByTopictCodeTest() {
		String topictCode = "10101000";
		ExecuteResult<HelpDocFirstCategory> executeResult = helpDocumentService.getHelpDocTopicByTopictCode(topictCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			HelpDocFirstCategory helpDocFirstCategory = executeResult.getResult();
			LOGGER.info("===>helpDocFirstCategory:" + helpDocFirstCategory);
		}
		
	}
	
}
