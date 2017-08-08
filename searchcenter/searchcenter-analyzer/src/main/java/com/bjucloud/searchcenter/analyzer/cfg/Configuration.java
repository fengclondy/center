package com.bjucloud.searchcenter.analyzer.cfg;

/**
 * 配置管理类接口
 */
public interface Configuration {

	/**
	 * 返回useSmart标志位 useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * 
	 * @return useSmart
	 */
	public boolean useSmart();

	/**
	 * 设置useSmart标志位 useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * 
	 * @param useSmart
	 */
	public void setUseSmart(boolean useSmart);

	/**
	 * 获取主词典路径
	 */
	public String getMainDictionary();

	/**
	 * 获取扩展字典配置路径
	 */
	public String getExtDictionary();
	
	/**
	 * 获取量词词典路径
	 */
	public String getQuantifierDicionary();

	/**
	 * 获取扩展停止词典配置路径
	 */
	public String getStopWordDictionary();

}
