/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	BaseCenterBussinessException.java
 * Author:   	jiangkun
 * Date:     	2016年11月21日
 * Description: 基础中心业务异常  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月21日	1.0			创建
 */

package cn.htd.basecenter.common.exception;

/**
 * @author jiangkun
 *
 */
public class BaseCenterBusinessException extends RuntimeException {

	private static final long serialVersionUID = 8557077207517348224L;

	private String code = "";

	public BaseCenterBusinessException() {
		super();
	}

	public BaseCenterBusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BaseCenterBusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BaseCenterBusinessException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
