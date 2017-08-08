package cn.htd.basecenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.common.ExecuteResult;

public class BaseSmsConfigServiceTest {
	ApplicationContext ctx = null;
	BaseSmsConfigService baseSmsConfigService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		baseSmsConfigService = (BaseSmsConfigService) ctx.getBean("baseSmsConfigService");
	}

	@Test
	public void updateSMSConfigValidTest() {
		ValidSmsConfigDTO inDTO = new ValidSmsConfigDTO();
		inDTO.setId(new Long(1));
		inDTO.setOperatorId(new Long(1));
		inDTO.setOperatorName("admin");
		ExecuteResult<String> result = baseSmsConfigService.updateSMSConfigValid(inDTO);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}
}
