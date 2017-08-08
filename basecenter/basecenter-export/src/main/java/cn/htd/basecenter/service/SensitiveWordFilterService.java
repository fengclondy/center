package cn.htd.basecenter.service;

import cn.htd.common.ExecuteResult;

/**
 * <p>
 * Description: [敏感字(关键字)过滤器]
 * </p>
 */
public interface SensitiveWordFilterService {

	/**
	 * <p>
	 * Discription:[刷新敏感词库]
	 * </p>
	 */
	public ExecuteResult<String> initSensitiveWord();

	/**
	 * <p>
	 * Discription:[关键字替换处理]
	 * </p>
	 */
	public ExecuteResult<String> handle(String swf);

}
