/**
 *
 */
package cn.htd.promotion.cpc.common.emums;

/**
 * @author ly
 *
 */
public enum ResultCodeEnum {

	//订单状态
	SUCCESS("00000","成功！"),
	NORESULT("11111","数据库没有查到信息！"),
	PROMOTION_PARAM_IS_NULL("10001","调用砍价发起接口入参不能为空！"),
	ERROR("99999","未知异常！");
	

	private ResultCodeEnum(String code, String msg) {
		this.code = code;
		Msg = msg;
	}
    // 成员变量
    private String code;
    private String Msg;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
}
