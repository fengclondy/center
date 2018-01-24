package cn.htd.tradecenter.common.constant;

public class VMSOrderConstants {

    // 1:超区域待审核 2：待支付可议价 3：待客户确认 4：已支付带拆单 5：下发ERP异常
    public static final String SEARCH_CONDITION_OUT_DISTRIBTION = "1";

    public static final String SEARCH_CONDITION_PENDING_PRICE = "2";

    public static final String SEARCH_CONDITION_WAITING_CONFIRM = "3";

    public static final String SEARCH_CONDITION_PENDING_SPLIT = "4";

    public static final String SEARCH_CONDITION_ORDER_ERROR = "5";

    // 订单来源-VMS开单
    public static final String ORDER_FROM_VMS = "2";

    // 超出配送范围标记
    public static final int IS_OUT_DISTRIBTION = 1;

    // 未取消订单标记
    public static final int IS_NOT_CANCEL_ORDER = 0;

    // 未使用优惠券标记
    public static final int HAS_NOT_USED_COUPON = 0;

    // 非秒杀订单标记
    public static final int IS_NOT_TIMELIMITED_ORDER = 0;

    // 订单异常标记
    public static final int IS_ERROR_ORDER = 1;

    // 非异常订单标记
    public static final int IS_NOT_ERROR_ORDER = 0;

    // 订单类型-普通订单
    public static final int ORDER_TYPE_NORMAL = 0;

    // 订单类型-秒杀订单
    public static final int ORDER_TYPE_TIMELIMITED = 1;

    // 订单类型-用券订单
    public static final int ORDER_TYPE_COUPON = 2;
}
