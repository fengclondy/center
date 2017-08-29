/**
 *
 */
package cn.htd.promotion.cpc.common.emums;

public enum ResultCodeEnum {

	//订单状态
	SUCCESS("00000","成功！"),
	NORESULT("11111","数据库没有查到信息！"),
	PROMOTION_PARAM_IS_NULL("10001","调用砍价接口入参不能为空！"),
	PROMOTION_AWARD_IS_NULL("10002","调用查询中奖记录接口入参不能为空！"),
	ERROR("99999","未知异常！"),

    PROMOTION_NOT_EXIST("26001", "促销活动数据不存在"),
    PROMOTION_NOT_EXPIRED("26002", "促销活动未过有效期"),
    PARAMETER_ERROR("26003", "促销活动参数不能为空"),

    BARGAIN_NOT_VALID("26004", "砍价活动未启用"),
    BARGAIN_RESULT_NOT_EXIST("26005", "砍价活动数据不存在"),
    PROMOTION_SOMEONE_INVOLVED("26006", "促销活动未过有效期并已经有人参与"),
    PROMOTION_STATUS_NOT_CORRECT("26007", "促销活动状态不正确"),
    PROMOTION_HAS_MODIFIED("26008", "促销活动已被修改"),
    PROMOTION_NO_STOCK("26009", "促销活动商品库存不足"),
    POMOTION_SPLIT_PRICE_ERROR("26010", "促销活动拆分金额失败"),
    PROMOTION_BARGAIN_JOIN_QTY("26011", "促销活动参与次数已上限"),
	
	LUCK_DRAW_PARAM_IS_NULL("30001","调用扭蛋抽奖接口入参不能为空！"),
	LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION("30002","该会员店没有抽奖权限");
	
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
