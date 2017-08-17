
package cn.htd.membercenter.common.exception;

/**
 * 会员中心异常
 *
 * @author lijun
 */
public class MemberCenterException extends RuntimeException {

    private static final long serialVersionUID = 8557077207517348224L;

    private String code = "";

    public MemberCenterException() {
        super();
    }

    public MemberCenterException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public MemberCenterException(String code, String message) {
        super(message);
        this.code = code;
    }

    public MemberCenterException(String code, Throwable cause) {
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
