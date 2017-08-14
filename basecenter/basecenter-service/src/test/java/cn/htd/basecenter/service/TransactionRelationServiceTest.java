package cn.htd.basecenter.service;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.htd.basecenter.common.constant.HelpDocConstants;
import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.common.ExecuteResult;

public class TransactionRelationServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRelationServiceTest.class);

	ApplicationContext ctx = null;
	
	TransactionRelationService transactionRelationService = null;
	
	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		transactionRelationService = (TransactionRelationService) ctx.getBean("transactionRelationService");
	}
	
	/**
	 * 查询会员是否关联
	 */
	@Test
	public void getTransactionRelationIsRelatedTest(){
		
		// executeResult.getResult() [true:关联,false:没有关联;关联是不可以下订单]
		//会员编码
		String buyerCode = "htd1087000";
		ExecuteResult<Boolean>  executeResult = transactionRelationService.getTransactionRelationIsRelated(buyerCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
	}
	
	/**
	 * 更新关联交易
	 */
	@Test
	public void updateTransactionRelationTest(){
		TransactionRelationDTO dto = new TransactionRelationDTO();
		dto.setId("73");
//		dto.setBuyerId(1L);
//		dto.setBuyerCode("htd1087000");
//		dto.setBuyerName("会员017");
		dto.setIsExist("1");//是否系统存在[true.是,false.否]
		dto.setModifyId("1");
		dto.setModifyName("creater031");
		dto.setModifyTime(new Date());
		
		ExecuteResult<?>  executeResult = transactionRelationService.updateTransactionRelation(dto);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
		
	}
	
	/**
	 * 查询单个关联交易
	 */
	@Test
	public void getSingleTransactionRelationByParamsTest(){
		
		TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
		transactionRelationDTO.setBuyerName("南京市江宁区家电中心");
		
		ExecuteResult<TransactionRelationDTO>  executeResult = transactionRelationService.getSingleTransactionRelationByParams(transactionRelationDTO);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			LOGGER.info("===>result:" + executeResult.getResult());
		}else{
			LOGGER.info("===>getErrorMessages:" + executeResult.getErrorMessages());
		}
	}
	
}
