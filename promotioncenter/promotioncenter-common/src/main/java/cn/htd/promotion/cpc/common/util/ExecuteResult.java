package cn.htd.promotion.cpc.common.util;

import java.io.Serializable;

/**
 * 执行结果信息类，用于封装增删改方法的结果信息 失败时必须将错误信息设置到errorMessages中
 */
public class ExecuteResult<T> implements Serializable {

	private static final long serialVersionUID = -1854616725284151074L;

	private T result;// 执行成功返回结果集
	private String resultMessage;// 执行成功结果信息
	private String errorMessage;// 调用失败时，返回的错误集合
	
	private String code;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
