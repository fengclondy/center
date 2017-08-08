package com.bjucloud.contentcenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjucloud.contentcenter.common.constants.HelpDocConstants;
import com.bjucloud.contentcenter.common.constants.TransactionRelationStatus;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.dto.TransactionRelationDTO;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public class TransactionRelationServiceTest {

	ApplicationContext ctx = null;
	
	TransactionRelationService transactionRelationService = null;
	
	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		transactionRelationService = (TransactionRelationService) ctx.getBean("transactionRelationService");
	}
	
	@Test
	public void addTransactionRelationTest(){
		TransactionRelationDTO dto = new TransactionRelationDTO();
		dto.setBuyerId("123456");
		dto.setBuyerCode("htd123456");
		dto.setBuyerName("会员01");
		dto.setBuyerType("01");
		dto.setSellerId("654321");
		dto.setSellerCode("htd654321");
		dto.setSellerName("平台公司01");
		dto.setSellerType("02");
		dto.setRelatedTypeCode(TransactionRelationStatus.status_one.getKey());
		dto.setRelatedTypeName(TransactionRelationStatus.status_one.getValue());
		dto.setIsExist("1");
		dto.setIsRelated("1");
		dto.setCreateId("456789");
		dto.setCreateName("creater01");
		dto.setCreateTime(new Date());
//		transactionRelationService.addTransactionRelation(dto);
	}
	
	@Test
	public void getTransactionRelationIsRelated(){
		//会员编码
		String buyerCode = "";
		ExecuteResult<Boolean>  executeResult = transactionRelationService.getTransactionRelationIsRelated(buyerCode);
		if(HelpDocConstants.EXECUTE_RESULT_SUCCESS.equals(executeResult.getCode())){
			List<String> errorMessages = executeResult.getErrorMessages();
			System.out.println("===>result:" + executeResult.getResult());
		}
		
	}
	
	@Test
	public void deleteTransactionRelationByParams(){
		TransactionRelationDTO dto = new TransactionRelationDTO();
		dto.setBuyerCode("htd123456");
		transactionRelationService.deleteTransactionRelationByParams(dto);
	}
	
	@Test
	public void updateTransactionRelation(){
		TransactionRelationDTO dto = new TransactionRelationDTO();
		dto.setId("4");
		dto.setBuyerId("1234567");
		dto.setBuyerCode("htd1234567");
		dto.setBuyerName("会员017");
		dto.setBuyerType("017");
		dto.setSellerId("6543217");
		dto.setSellerCode("htd6543217");
		dto.setSellerName("平台公司017");
		dto.setSellerType("027");
		dto.setRelatedTypeCode(TransactionRelationStatus.status_two.getKey());
		dto.setRelatedTypeName(TransactionRelationStatus.status_two.getValue());
		dto.setIsExist("0");
		dto.setIsRelated("0");
		dto.setModifyId("3456789");
		dto.setModifyName("creater031");
		dto.setModifyTime(new Date());
		transactionRelationService.updateTransactionRelation(dto);
	}
	
	@Test
	public void getTransactionRelationForPage(){
		Pager<TransactionRelationDTO> page = new Pager<TransactionRelationDTO>();
		TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
		transactionRelationService.getTransactionRelationForPage(page, transactionRelationDTO);
	}
	
	@Test
	public void cancleRelations(){
		List<String> list = new ArrayList<String>();
		list.add("htd123456");
		transactionRelationService.cancleRelations(list);
	}
}
