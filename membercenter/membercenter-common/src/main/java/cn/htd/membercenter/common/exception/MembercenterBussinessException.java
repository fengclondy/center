/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	BaseCenterBussinessException.java
 * Author:   	youyj
 * Date:     	2016年12月13日
 * Description: 基础中心业务异常  
 * History: 	
 */

package cn.htd.membercenter.common.exception;

/**
 * @author youyj
 *
 */
public class MembercenterBussinessException extends RuntimeException {

	private static final long serialVersionUID = 8557077207517348224L;

	public MembercenterBussinessException() {
		super();
	}

	public MembercenterBussinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public MembercenterBussinessException(String message) {
		super(message);
	}

	public MembercenterBussinessException(Throwable cause) {
		super(cause);
	}
}
