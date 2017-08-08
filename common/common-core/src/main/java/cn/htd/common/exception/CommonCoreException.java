package cn.htd.common.exception;

public class CommonCoreException extends RuntimeException {
	private static final long serialVersionUID = -1L;

	public CommonCoreException() {
		super();
	}

	public CommonCoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonCoreException(String message) {
		super(message);
	}

	public CommonCoreException(Throwable cause) {
		super(cause);
	}
}
