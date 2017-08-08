package cn.htd.membercenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

public class TestTast {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestTast.class);

	public static void main(String arr[]) throws Exception {
		ApplicationContext ctx = null;
		TBScheduleManagerFactory memberBaseInfoService = null;
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		// memberBaseInfoService = (TBScheduleManagerFactory)
		// ctx.getBean("scheduleManagerFactory");
		// memberBaseInfoService.init();
		LOGGER.info("dsaaaaaaaaaaa===================================================");
	}

}
