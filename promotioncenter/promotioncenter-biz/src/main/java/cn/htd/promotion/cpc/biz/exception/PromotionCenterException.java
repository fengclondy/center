package cn.htd.promotion.cpc.biz.exception;

public class PromotionCenterException extends RuntimeException {

	private static final long serialVersionUID = 3814048990370464832L;

	private String code = "";

	public PromotionCenterException() {
		super();
	}

	public PromotionCenterException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public PromotionCenterException(String code, String message) {
		super(message);
		this.code = code;
	}

	public PromotionCenterException(String code, Throwable cause) {
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
