/**
 *
 */
package cn.htd.promotion.cpc.common.emums;

public enum ResultCodeEnum {

    //订单状态
    SUCCESS("00000", "成功！"),
    ERROR("99999", "未知异常！"),
    PARAMETER_ERROR("00001", "促销活动参数不能为空"),
    NORESULT("11111", "数据库没有查到信息！"),
    PROMOTION_PARAM_IS_NULL("10001", "调用砍价接口入参不能为空！"),
    PROMOTION_AWARD_IS_NULL("10002", "调用查询中奖记录接口入参不能为空！"),
    PROMOTION_TIME_NOT_UP("10003", "该时间段内已有活动进行"),

    PROMOTION_NOT_EXIST("26001", "促销活动数据不存在"),
    PROMOTION_NOT_EXPIRED("26002", "促销活动未过有效期"),
    PROMOTION_NOT_VALID("26003", "促销活动未上架"),
    PROMOTION_NO_START("26004", "促销活动未开始"),
    PROMOTION_HAS_EXPIRED("26005", "促销活动已结束"),


    BARGAIN_NOT_VALID("26004", "砍价活动未启用"),
    BARGAIN_RESULT_NOT_EXIST("26005", "砍价活动数据不存在"),
    PROMOTION_SOMEONE_INVOLVED("26006", "促销活动未过有效期并已经有人参与"),
    PROMOTION_STATUS_NOT_CORRECT("26007", "促销活动状态不正确"),
    PROMOTION_HAS_MODIFIED("26008", "促销活动已被修改"),
    PROMOTION_NO_STOCK("26009", "促销活动商品库存不足"),
    POMOTION_SPLIT_PRICE_ERROR("26010", "促销活动拆分金额失败"),
    PROMOTION_BARGAIN_JOIN_QTY("26011", "促销活动参与次数已上限"),


    LOTTERY_NO_RESULT("26200", "抽奖进行中请继续等待"),
    LUCK_DRAW_PARAM_IS_NULL("26201", "调用扭蛋抽奖接口入参不能为空！"),
    LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION("26202", "该会员店没有抽奖权限"),
    LOTTERY_NOT_IN_TIME_INTERVAL("26203", "抽奖活动不在当前时间段内"),
    LOTTERY_BUYER_NO_AUTHIORITY("25204", "粉丝没有抽奖权限"),
    LOTTERY_SELLER_NO_AUTHIORITY("25205", "会员店没有参加本次抽奖活动");

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
