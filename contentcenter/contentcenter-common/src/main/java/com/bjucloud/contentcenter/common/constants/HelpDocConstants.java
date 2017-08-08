package com.bjucloud.contentcenter.common.constants;

public class HelpDocConstants {
	
    /**
     * private constructor
     * Creates a new instance of StatusContants.
     */
    private HelpDocConstants() { }
    
    
	// 成功(success)/失败(failed)
	public static final String EXECUTE_RESULT_SUCCESS = "1";
	public static final String EXECUTE_RESULT_SUCCESS_TEXT = "成功";
	public static final String EXECUTE_RESULT_FAILED = "0";
	public static final String EXECUTE_RESULT_FAILED_TEXT = "失败";
	public static final String EXECUTE_RESULT_REPEAT = "2";
	public static final String EXECUTE_RESULT_REPEAT_TEXT = "会员重复";
	
	//分类已经存在
	public static final String EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS = "2";
	public static final String EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS_TEXT = "分类已经存在";
	
	//排序号已经存在
	public static final String EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS = "3";
	public static final String EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS_TEXT = "排序号已经存在";
	
	// 分类层级 [first:1级,second:二级] 
	public static final Integer HELP_DOC_FIRST_CATEGORY = 1;
	public static final Integer HELP_DOC_SECOND_CATEGORY = 2;
	
	
	// 操作类型：1.上下架，2.修改
	public static final String CATEGORY_OPERATE_TYPE_PULISH = "1";
	public static final String CATEGORY_OPERATE_TYPE_UPDATE = "2";
	
    

}
