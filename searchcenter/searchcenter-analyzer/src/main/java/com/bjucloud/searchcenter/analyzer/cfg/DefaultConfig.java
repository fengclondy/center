package com.bjucloud.searchcenter.analyzer.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class DefaultConfig implements Configuration {

	// 分词器配置文件路径
	private static final String FILE_NAME = "analyzer.xml";
	// 配置属性--主词典
	private static final String MAIN_DICT = "main_dict";
	// 配置属性--数量词典
	private static final String QUANTIFIER = "quantifier";
	// 配置属性--扩展字典
	private static final String EXT_DICT = "ext_dict";
	// 配置属性--扩展停止词典
	private static final String STOP_DICT = "stopwords";
	// 是否使用smart方式分词。true：智能切分策略、 false：细粒度切分
	private boolean useSmart;

	private Properties props;

	/**
	 * 返回单例
	 */
	public static Configuration getInstance() {
		return new DefaultConfig();
	}

	/**
	 * 初始化配置文件
	 */
	private DefaultConfig() {
		props = new Properties();

		InputStream input = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
		if (input != null) {
			try {
				props.loadFromXML(input);
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取主词典路径
	 */
	public String getMainDictionary() {
		return props.getProperty(MAIN_DICT);
	}

	/**
	 * 获取量词词典路径
	 */
	public String getQuantifierDicionary() {
		return props.getProperty(QUANTIFIER);
	}

	/**
	 * 获取扩展字典配置路径
	 */
	public String getExtDictionary() {
		return props.getProperty(EXT_DICT);
	}

	/**
	 * 获取停用词典配置路径
	 */
	public String getStopWordDictionary() {
		return props.getProperty(STOP_DICT);
	}

	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}
}
