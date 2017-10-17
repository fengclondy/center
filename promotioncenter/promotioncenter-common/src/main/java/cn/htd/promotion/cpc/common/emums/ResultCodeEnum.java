/**
 *
 */
package cn.htd.promotion.cpc.common.emums;

public enum ResultCodeEnum {

    SUCCESS("00000", "成功"),
    ERROR("99999", "系统异常"),
    PARAMETER_ERROR("00001", "促销活动参数不能为空"),
    NO_BUYER_INFO("00002", "没有取到粉丝信息"),
    NO_SELLER_INFO("00003", "没有取到会员店信息"),

    NORESULT("11111", "数据库没有查到信息！"),
    PROMOTION_PARAM_IS_NULL("10001", "调用砍价接口入参不能为空！"),
    PROMOTION_AWARD_IS_NULL("10002", "调用查询中奖记录接口入参不能为空！"),
    PROMOTION_TIME_NOT_UP("10003", "该时间段内已有活动进行"),

    PROMOTION_NOT_EXIST("26001", "促销活动数据不存在"),
    PROMOTION_NOT_EXPIRED("26002", "促销活动未过有效期"),
    PROMOTION_NOT_VALID("26003", "促销活动未上架"),
    PROMOTION_NO_START("26004", "促销活动未开始"),
    PROMOTION_HAS_EXPIRED("26005", "促销活动已结束"),
    PROMOTION_NO_TYPE("26006", "促销活动类型不能为空"),


    BARGAIN_NOT_VALID("26004", "砍价活动未启用"),
    BARGAIN_RESULT_NOT_EXIST("26005", "砍价活动数据不存在"),
    PROMOTION_SOMEONE_INVOLVED("26006", "促销活动未过有效期并已经有人参与"),
    PROMOTION_STATUS_NOT_CORRECT("26007", "促销活动状态不正确"),
    PROMOTION_HAS_MODIFIED("26008", "促销活动已被修改"),
    PROMOTION_NO_STOCK("26009", "促销活动商品库存不足"),
    POMOTION_SPLIT_PRICE_ERROR("26010", "促销活动拆分金额失败"),
    PROMOTION_BARGAIN_JOIN_QTY("26011", "促销活动参与次数已上限"),
    BARGAIN_NOT_UNAVAILABLE("26012", "砍价活动不可用"),

    LOTTERY_NO_RESULT("26200", "抽奖进行中请继续等待"),
    LOTTERY_NO_DESCRIBE_CONTENT("26201", "抽奖没有活动规则信息"),
    LOTTERY_AWARD_NOT_CORRECT("26202", "抽奖活动奖项设置不正确"),
    LOTTERY_NOT_IN_START_TIME("26203", "当前时间未到抽奖活动开始时间"),
    LOTTERY_BUYER_NO_AUTHIORITY("26204", "粉丝没有抽奖权限"),
    LOTTERY_SELLER_NO_AUTHIORITY("26205", "会员店没有参加本次抽奖活动"),
    LOTTERY_NO_MORE_AWARD_NUM("26206", "抽奖活动目前奖品数量不足"),
    LOTTERY_BUYER_NO_MORE_DRAW_CHANCE("26207", "粉丝已经用完了所有抽奖机会，需分享获得额外抽奖机会"),
    LOTTERY_BUYER_NO_MORE_EXTRA_CHANCE("26208", "粉丝已经用完了自有和分享额外获取的抽奖机会"),
    LOTTERY_BUYER_REACH_WINNING_LIMMIT("26209", "粉丝已达中奖次数上限"),
    LOTTERY_SELLER_REACH_WINNING_LIMMIT("26210", "会员店已达中奖次数上限"),
    LOTTERY_BUYER_NO_WINNING_RECORD("26211", "粉丝没有中奖记录"),
    LOTTERY_ALL_ORG_HAS_AUTHIORITY("26212", "所有会员店都有参加抽奖的权限"),
    LOTTERY_NOT_HAS_PROMOTION_INFO("26213", "没有查到抽奖信息"),
    LOTTERY_HAS_PASSED_END_TIME("26214", "当前时间已过抽奖活动结束时间"),
    LOTTERY_BUYER_CURRENT_DAY_NO_MORE_DRAW_CHANCE("26215", "粉丝当日抽奖次数已用完"),
    LOTTERY_BUYER_NOT_HAVE_QUALIFICATIONS("26216","还没有刮奖资格哦，赶紧下单来刮奖吧！"),
    LOTTERY_ORDER_HAD_LUCK_DRAW("26217","抱歉，这笔订单您已经刮过奖啦~请重新下单刮奖~"),
    LOTTERY_BUYER_ORDER_DISSATISFACTION_PAY_TYPE("26218","该笔订单的支付方式不满足刮奖条件"),
    LOTTERY_BUYER_ORDER_DISSATISFACTION_AMOUNT("26219","该笔订单的支付金额不满足刮奖条件"),
    
    //此错误码写在此处目的是给promotiongateway用，防止返回码重复
    NOT_FOUND_HUI_LIN_ORDER_INFO("26220","根据交易流水号没有找到汇邻订单相关信息"),
    LOTTERY_MEMBER_NO_VALIDATE_FAIL("26221","前台传入的和从汇邻查出的不是同一个粉丝号"),
    LOTTERY_ORDER_NOT_IN_EFFECTIVE_DATE("26222","货到付款的下单时间或者在线支付的支付时间不在活动有效期之内"),
    LOTTERY_ORDER_WRONGFUL("26223","抽奖订单数据不合法"),

    // 投票活动错误码
    VOTE_ACTIVITY_NOT_EXIST("26301", "投票活动不存在"),
    VOTE_ACTIVITY_NOT_EXIST_MEMBER("26302", "投票活动不存在该会员店报名信息"),
    OTE_ACTIVITY_HAVA_NO_VOTE_ACTIVITY("26303", "当前没有投票活动"),
    OTE_ACTIVITY_MEMBER_NOT_SIGN_UP("26304", "会员店未报名"),
    OTE_ACTIVITY_MEMBER_NOT_AUDIT_PASSED("26305", "会员店未审核通过"),
    OTE_ACTIVITY_NOT_MEET_VOTE_STORE_NUM("26306", "您今天已经达到每日可投票门店数上限，明天再来吧！"),
    OTE_ACTIVITY_NOT_MEET_VOTE_NUM_PER_STORE("26307", "您今天已经投过我了，谢谢您！")
    ;

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

    // 获取枚举value对应名字方法
    public static String getName(String code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (code.equals(resultCodeEnum.getCode())) {
                return resultCodeEnum.Msg;
            }
        }
        return "";
    }
}
