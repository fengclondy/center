package com.bjucloud.searchcenter.analyzer.service;

public interface DictionaryService {

	/** 操作类型 */
	public static final String OPER_TYPE = "operType";
	/** 添加分词 */
	public static final String ADD_WORD = "add";
	/** 删除分词 */
	public static final String DELETE_WORD = "delete";
	/** 词典类型 */
	public static final String DICT_TYPE = "dictType";
	/** 主词典 */
	public static final String MAIN_DICT= "main";
	/** 停用词词典 */
	public static final String STOPWORD_DICT= "stopword";
	
	public void dealDict(String operType, String dictType, String word) throws Exception;

}
