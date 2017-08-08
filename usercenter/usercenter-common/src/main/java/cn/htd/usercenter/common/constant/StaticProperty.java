package cn.htd.usercenter.common.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticProperty {
	private final static Logger logger = LoggerFactory.getLogger(StaticProperty.class);
	public static String OA_URL;
	public static String ERP_URL;
	public static String ERP_USERNAME;
	public static String ERP_PASSWORD;

	static {
		logger.info("class:StaticProperty初始化开始");
		Properties p = new Properties();
		try {
			InputStream in = StaticProperty.class.getResourceAsStream("/usercenter.properties");
			p.load(in);
			in.close();
			OA_URL = p.getProperty("oaurl");
			ERP_URL = p.getProperty("erp.getOrganizations.url");
			ERP_USERNAME = p.getProperty("erp.username");
			ERP_PASSWORD = p.getProperty("erp.password");
			logger.info("class:StaticProperty初始化结束");
		} catch (IOException e) {
			logger.error("class:StaticProperty初始化异常" + e);
		}

	}
}
