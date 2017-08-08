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
package cn.htd.tradecenter.common.exception;

/**
 * 订单中心异常
 * 
 * @author jiangkun
 */
public class TradeCenterBusinessException extends RuntimeException {

	private static final long serialVersionUID = 8557077207517348224L;
	private String code = "";

	public TradeCenterBusinessException() {
		super();
	}

	public TradeCenterBusinessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public TradeCenterBusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public TradeCenterBusinessException(String code, Throwable cause) {
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
