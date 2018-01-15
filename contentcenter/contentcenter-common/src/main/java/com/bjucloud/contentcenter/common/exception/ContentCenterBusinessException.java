/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TradeCenterBussinessException.java
 * Author:   	jiangkun
 * Date:     	2016年11月21日
 * Description: 订单中心业务异常
 * History:
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package com.bjucloud.contentcenter.common.exception;

/**
 * 内容中心异常
 *
 * @author jiangkun
 */
public class ContentCenterBusinessException extends RuntimeException {

	private static final long serialVersionUID = 8557077207517348224L;

	private String code = "";

	public ContentCenterBusinessException() {
		super();
	}

	public ContentCenterBusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ContentCenterBusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ContentCenterBusinessException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 重写fillInStackTrace方法，不收集线程的异常栈信息减少异常开销
	 */
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
