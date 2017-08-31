/**
 *
 */
package cn.htd.promotion.cpc.common.emums;

public enum PromotionCodeEnum {

	LOTTERY_EFFECTIVE("3","有效"),
	LOTTERY_INVALID("4","无效");
	
    private PromotionCodeEnum(String code, String msg) {
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
