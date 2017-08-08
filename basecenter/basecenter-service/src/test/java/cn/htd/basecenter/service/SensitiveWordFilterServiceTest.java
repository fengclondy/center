package cn.htd.basecenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;

public class SensitiveWordFilterServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordFilterServiceTest.class);
	private SensitiveWordFilterService sensitiveWordFilterService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		sensitiveWordFilterService = (SensitiveWordFilterService) ctx.getBean("sensitiveWordFilterService");
	}

	@Test
	public void testHandle() {
		String txt = "草草泥马太多的法轮功 2123一部电影在夜3.3 关上电话静静的发呆着。";
		ExecuteResult<String> result = sensitiveWordFilterService.handle(txt);
		if (result.isSuccess()) {
			String swfo = result.getResult();
			System.out.println(txt);
			System.out.println();
			System.out.println("返回数据 ：" + swfo);
			System.out.println();
		}
		LOGGER.info("操作结果{}", JSON.toJSON(result));
	}

}
