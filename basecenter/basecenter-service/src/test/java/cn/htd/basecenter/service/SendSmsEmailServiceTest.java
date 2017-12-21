package cn.htd.basecenter.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import cn.htd.basecenter.dao.BaseSmsConfigDAO;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.enums.SmsChannelTypeEnum;
import cn.htd.basecenter.enums.SmsEmailTypeEnum;
import cn.htd.common.ExecuteResult;

/**
 * Created by lh on 2016/11/1.
 */
public class SendSmsEmailServiceTest {

	ApplicationContext ctx = null;
	SendSmsEmailService sendSmsEmailService = null;
	BaseSmsConfigDAO baseSmsConfigDAO = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		sendSmsEmailService = (SendSmsEmailService) ctx.getBean("sendSmsEmailService");
		baseSmsConfigDAO = (BaseSmsConfigDAO) ctx.getBean("baseSmsConfigDAO");
	}

	@Test
	public void sendSmsTest() {
		SendSmsDTO sendSmsDTO = new SendSmsDTO();
		List<String> parameterList = new ArrayList<String>();
		parameterList.add("12345678");
		sendSmsDTO.setPhone("13913037054");
		sendSmsDTO.setSmsType("206");
		sendSmsDTO.setParameterList(parameterList);
		ExecuteResult<String> result = sendSmsEmailService.sendSms(sendSmsDTO);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}

	@Test
	public void sendEmailTest() {
		MailWarnInDTO mailWarnInDTO=new MailWarnInDTO();
		List<String> parameterList = new ArrayList<String>();
		parameterList.add("zxl");
		mailWarnInDTO.setRecevierMail("h.sun@htd.cn");
		mailWarnInDTO.setEmailTitle("测试邮件zxl");
		List<MailWarnRow> rowList=Lists.newArrayList();
		
		MailWarnRow row1=new MailWarnRow();
		
		List<MailWarnColumn> row1List=Lists.newArrayList();
		MailWarnColumn mailWarnCol1=new MailWarnColumn();
		mailWarnCol1.setIndex(1);
		mailWarnCol1.setValue("商品编码");
		row1List.add(mailWarnCol1);
		
		MailWarnColumn mailWarnCol2=new MailWarnColumn();
		mailWarnCol2.setIndex(2);
		mailWarnCol2.setValue("错误时间");
		row1List.add(mailWarnCol2);
		
		MailWarnColumn mailWarnCol3=new MailWarnColumn();
		mailWarnCol3.setIndex(3);
		mailWarnCol3.setValue("下行状态");
		row1List.add(mailWarnCol3);
		
		MailWarnColumn mailWarnCol4=new MailWarnColumn();
		mailWarnCol4.setIndex(4);
		mailWarnCol4.setValue("异常原因");
		row1List.add(mailWarnCol4);
		
		row1.setMailWarnColumnList(row1List);
		
		
	    MailWarnRow row2=new MailWarnRow();
		
		List<MailWarnColumn> row2List=Lists.newArrayList();
		MailWarnColumn mailWarnCol21=new MailWarnColumn();
		mailWarnCol21.setIndex(1);
		mailWarnCol21.setValue("100001");
		row2List.add(mailWarnCol21);
		
		MailWarnColumn mailWarnCol22=new MailWarnColumn();
		mailWarnCol22.setIndex(2);
		mailWarnCol22.setValue("2012-01-12 12:23:33");
		row2List.add(mailWarnCol22);
		
		MailWarnColumn mailWarnCol23=new MailWarnColumn();
		mailWarnCol23.setIndex(3);
		mailWarnCol23.setValue("已下行");
		row2List.add(mailWarnCol23);
		
		MailWarnColumn mailWarnCol24=new MailWarnColumn();
		mailWarnCol24.setIndex(4);
		mailWarnCol24.setValue("下行接口报错");
		row2List.add(mailWarnCol24);
		
		row2.setMailWarnColumnList(row2List);
		
		rowList.add(row1);
		
		rowList.add(row2);
		
		mailWarnInDTO.setRowList(rowList);
		ExecuteResult<String> result = sendSmsEmailService.doSendEmailByTemplate(mailWarnInDTO);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}
	
	@Test
	public void test2(){
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
		configCondition.setChannelCode(SmsChannelTypeEnum.MANDAO.getCode());
		System.out.println("----" + JSONObject.toJSONString(configCondition));
		List<BaseSmsConfigDTO> validSmsConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
		System.out.println(JSONObject.toJSONString(validSmsConfigList) + "==");
		ExecuteResult<String> result = sendSmsEmailService.queryBalance(validSmsConfigList.get(0));
		System.out.println(JSONObject.toJSONString(result));
		 
	}

}
