package cn.htd.marketcenter.common.constant;

public class RedisConst {
    // Redis操作Hash
    public static final String REDIS_PROMOTION_ACTION_HASH = "B2B_MIDDLE_PROMOTION_ACTION_HASH";

    // Redis优惠券有效信息
    public static final String REDIS_COUPON_VALID = "B2B_MIDDLE_COUPON_VALID";
    // Redis优惠券自助领券活动信息
    public static final String REDIS_COUPON_MEMBER_COLLECT = "B2B_MIDDLE_COUPON_MEMBER_COLLECT";
    // Reids优惠券领券会员记录
    public static final String REDIS_COUPON_SEND_LIST = "B2B_MIDDLE_COUPON_SEND_LIST";
    // Redis优惠券触发返券信息
    public static final String REDIS_COUPON_TRIGGER = "B2B_MIDDLE_COUPON_TRIGGER";
    // Redis优惠券自助领券队列
    public static final String REDIS_COUPON_COLLECT = "B2B_MIDDLE_COUPON_COLLECT";
    // Redis优惠券活动被领取数量
    public static final String REDIS_COUPON_RECEIVE_COUNT = "B2B_MIDDLE_COUPON_RECEIVE_COUNT";
    // Redis优惠券待处理队列
    public static final String REDIS_COUPON_NEED_DEAL_LIST = "B2B_MIDDLE_COUPON_NEED_DEAL_LIST";
    // Redis会员获得优惠券信息
    public static final String REDIS_BUYER_COUPON = "B2B_MIDDLE_BUYER_COUPON";
    // Redis会员获得优惠券的剩余金额信息
    public static final String REDIS_BUYER_COUPON_AMOUNT = "B2B_MIDDLE_BUYER_COUPON_AMOUNT";
    // Redis会员获得优惠券的使用信息
    public static final String REDIS_BUYER_COUPON_USELOG = "B2B_MIDDLE_BUYER_COUPON_USELOG";
    // Redis会员获得优惠券的使用信息计数
    public static final String REDIS_BUYER_COUPON_USELOG_COUNT = "B2B_MIDDLE_BUYER_COUPON_USELOG_COUNT";
    // Redis会员获取优惠券数量
    public static final String REDIS_BUYER_COUPON_RECEIVE_COUNT = "B2B_MIDDLE_BUYER_COUPON_RECEIVE_COUNT";
    // Redis会员获取优惠券待保存DB列表
    public static final String REDIS_BUYER_COUPON_NEED_SAVE_LIST = "B2B_MIDDLE_BUYER_COUPON_NEED_SAVE_LIST";
    // Redis会员获取优惠券待保存DB列表
    public static final String REDIS_BUYER_COUPON_NEED_UPDATE_LIST = "B2B_MIDDLE_BUYER_COUPON_NEED_UPDATE_LIST";
    // Redis会员优惠券的使用log信息
    public static final String REDIS_BUYER_COUPON_NEED_SAVE_USELOG = "B2B_MIDDLE_BUYER_COUPON_NEED_SAVE_USELOG";

    // Redis会员促销活动有效信息
    public static final String REDIS_TIMELIMITED_VALID = "B2B_MIDDLE_TIMELIMITED_VALID";
    // Redis秒杀活动数据
    public static final String REDIS_TIMELIMITED = "B2B_MIDDLE_TIMELIMITED";
    // Redis秒杀活动结果数据
    public static final String REDIS_TIMELIMITED_RESULT = "B2B_MIDDLE_TIMELIMITED_RESULT";
    // Redis秒杀活动数据
    public static final String REDIS_TIMELIMITED_INDEX = "B2B_MIDDLE_TIMELIMITED_INDEX";
    // Redis秒杀商品总数量
    public static final String REDIS_TIMELIMITED_TOTAL_COUNT = "TOTAL_COUNT";
    // Redis展示已秒杀商品数量
    public static final String REDIS_TIMELIMITED_SHOW_REMAIN_COUNT = "SHOW_REMAIN_COUNT";
    // Redis展示参与秒杀人数
    public static final String REDIS_TIMELIMITED_SHOW_ACTOR_COUNT = "SHOW_ACTOR_COUNT";
    // Redis真实秒杀商品数量
    public static final String REDIS_TIMELIMITED_REAL_REMAIN_COUNT = "REAL_REMAIN_COUNT";
    // Redis真实参与秒杀人数
    public static final String REDIS_TIMELIMITED_REAL_ACTOR_COUNT = "REAL_ACTOR_COUNT";
    // Redis会员秒杀活动参加信息
    public static final String REDIS_BUYER_TIMELIMITED_COUNT = "B2B_MIDDLE_BUYER_TIMELIMITED_COUNT";
    // Redis会员获得优惠券的使用信息
    public static final String REDIS_BUYER_TIMELIMITED_USELOG = "B2B_MIDDLE_BUYER_TIMELIMITED_USELOG";
    // Redis会员优惠券的使用log信息
    public static final String REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG =
            "B2B_MIDDLE_BUYER_TIMELIMITED_NEED_SAVE_USELOG";

    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
    // Redis同步B2C触发返券活动编号Set
    public static final String REDIS_SYNC_B2C_COUPON_SET = "B2B_MIDDLE_SYNC_B2C_COUPON_SET";
    // Redis处理中B2C触发返券活动Hash
    public static final String REDIS_DEAL_B2C_COUPON_HASH = "B2B_MIDDLE_DEAL_B2C_COUPON_HASH";
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
}
