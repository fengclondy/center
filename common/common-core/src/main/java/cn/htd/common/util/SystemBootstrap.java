package cn.htd.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class SystemBootstrap implements InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(SystemBootstrap.class);
	/**
	 * configPath 系统变量配置文件路径
	 */
	private String configPath;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream inputStream = null;
		Properties properties = new Properties();
		try {
			inputStream = SystemBootstrap.class.getResourceAsStream(configPath);
			properties.load(inputStream);
			logger.info("系统配置项:" + properties);
		} catch (Exception e) {
			logger.error("读取系统配置文件时发生错误：", e);
			throw e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("关闭文件输入流失败：", e);
				}
			}
		}
		SysProperties.init(properties);
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
}
