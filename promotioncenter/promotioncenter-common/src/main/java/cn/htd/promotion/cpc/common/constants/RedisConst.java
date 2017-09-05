package cn.htd.promotion.cpc.common.constants;

public class RedisConst {
    // Redis操作Hash
    public static final String PROMOTION_REDIS_PROMOTION_ACTION_HASH = "B2C_MIDDLE_PROMOTION_ACTION_HASH";
    // Redis粉丝中奖信息List异步落表用
    public static final String REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST =
            "B2C_MIDDLE_BUYER_WINNING_RECORD_NEED_SAVE_LIST";

    // Redis砍价促销活动有效信息
    public static final String REDIS_BARGAIN_VALID = "B2C_MIDDLE_BARGAIN_VALID";
    // Redis砍价活动数据
    public static final String REDIS_BARGAIN = "B2C_MIDDLE_BARGAIN";
    // Redis砍价活动结果数据
    public static final String REDIS_BARGAIN_RESULT = "B2C_MIDDLE_BARGAIN_RESULT";
    // Redis砍价活动数据
    public static final String REDIS_BARGAIN_INDEX = "B2C_MIDDLE_BARGAIN_INDEX";
    // Redis砍价商品总数量
    public static final String REDIS_BARGAIN_TOTAL_COUNT = "TOTAL_COUNT";
    // Redis展示参与砍价人数
    public static final String REDIS_BARGAIN_SHOW_ACTOR_COUNT = "SHOW_ACTOR_COUNT";
    // Redis真实砍价商品数量
    public static final String REDIS_BARGAIN_REAL_REMAIN_COUNT = "REAL_REMAIN_COUNT";
    // Redis真实参与砍价人数
    public static final String REDIS_BARGAIN_REAL_ACTOR_COUNT = "REAL_ACTOR_COUNT";
    // Redis砍价活动开始时间
    public static final String REDIS_BARGAIN_START_TIME = "BARGAIN_START_TIME";
    // Redis砍价活动结束时间
    public static final String REDIS_BARGAIN_END_TIME = "BARGAIN_END_TIME";
    // Redis用户参加砍价活动信息
    public static final String REDIS_BUYER_BARGAIN_USELOG = "BUYER_BARGAIN_USELOG";
    // Redis砍价活动金额拆分
    public static final String REDIS_BARGAIN_PRICE_SPLIT = "BARGAIN_PRICE_SPLIT";
    //Redis砍价商品库存
    public static final String REDIS_BARGAIN_ITEM_STOCK = "BARGAIN_ITEM_STOCK";
    
    public static final String BUYER_BARGAIN_RECORD = "BUYER_BARGAIN_RECORD";

    // Redis抽奖活动有效信息
    public static final String REDIS_LOTTERY_VALID = "B2C_MIDDLE_LOTTERY_VALID";
    // Redis抽奖活动信息
    public static final String REDIS_LOTTERY_INFO = "B2C_MIDDLE_LOTTERY_INFO";
    // Redis抽奖次数信息
    public static final String REDIS_LOTTERY_TIMES_INFO = "B2C_MIDDLE_LOTTERY_TIMES_INFO";
    // Redis抽奖活动奖品中奖概率
    public static final String REDIS_LOTTERY_AWARD_WINNING_PERCENTAGE = "AWARD_WINNING_PERCENTAGE";
    // Redis抽奖活动每日抽奖次数
    public static final String REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES = "BUYER_DAILY_DRAW_TIMES";
    // Redis抽奖活动每日中奖次数
    public static final String REDIS_LOTTERY_BUYER_DAILY_WINNING_TIMES = "BUYER_DAILY_WINNING_TIMES";
    // Redis抽奖活动卖家每日中奖总次数
    public static final String REDIS_LOTTERY_SELLER_DAILY_TOTAL_TIMES = "SELLER_DAILY_TOTAL_TIMES";
    // Redis抽奖活动买家每次分享获得抽奖次数
    public static final String REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES = "BUYER_SHARE_EXTRA_PARTAKE_TIMES";
    // Redis抽奖活动买家分享获得抽奖次数上限
    public static final String REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES = "BUYER_TOP_EXTRA_PARTAKE_TIME";
    // Redis抽奖活动奖品总数
    public static final String REDIS_LOTTERY_AWARD_TOTAL_COUNT = "AWARD_TOTAL_COUNT";
    // Redis抽奖活动索引
    public static final String REDIS_LOTTERY_INDEX = "B2C_MIDDLE_LOTTERY_INDEX";
    // Redis抽奖活动索引-扭蛋活动前缀
    public static final String REDIS_GASHAPON_PREFIX = "GASHAPON_";
    // Redis抽奖活动奖品池
    public static final String REDIS_LOTTERY_AWARD_PREFIX = "B2C_MIDDLE_LOTTERY_";
    // Redis抽奖活动买家抽奖次数信息
    public static final String REDIS_LOTTERY_BUYER_TIMES_INFO = "B2C_MIDDLE_LOTTERY_BUYER_TIMES_INFO";
    // Reids抽奖活动买家当日参与次数
    public static final String REDIS_LOTTERY_BUYER_PARTAKE_TIMES = "BUYER_PARTAKE_TIMES";
    // Redis抽奖活动买家当日中奖次数
    public static final String REDIS_LOTTERY_BUYER_WINNING_TIMES = "BUYER_WINNING_TIMES";
    // Redis抽奖活动买家分享次数
    public static final String REIDS_LOTTERY_BUYER_SHARE_TIMES = "BUYER_SHARE_TIMES";
    // Redis粉丝已经达到分享获得抽奖次数上限
    public static final String REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES = "BUYER_HAS_TOP_EXTRA_TIMES";
    // Redis抽奖活动卖家每日中奖次数
    public static final String REDIS_LOTTERY_SELLER_WINED_TIMES = "B2C_MIDDLE_LOTTERY_SELLER_WINED_TIMES";
    // Redis粉丝抽奖结果信息
    public static final String REDIS_LOTTERY_BUYER_AWARD_INFO = "B2C_MIDDLE_LOTTERY_BUYER_AWARD_INFO";

    // Redis会员促销活动有效信息
    public static final String PROMOTION_REDIS_TIMELIMITED_VALID = "B2C_MIDDLE_TIMELIMITED_VALID";
    // Redis秒杀活动数据
    public static final String PROMOTION_REDIS_TIMELIMITED = "B2C_MIDDLE_TIMELIMITED";
    // Redis秒杀活动结果数据
    public static final String PROMOTION_REDIS_TIMELIMITED_RESULT = "B2C_MIDDLE_TIMELIMITED_RESULT";
    // Redis秒杀活动数据
    public static final String PROMOTION_REDIS_TIMELIMITED_INDEX = "B2C_MIDDLE_TIMELIMITED_INDEX";
    // Redis秒杀商品总数量
    public static final String PROMOTION_REDIS_TIMELIMITED_TOTAL_COUNT = "PROMOTION_B2C_TOTAL_COUNT";
    // Redis展示已秒杀商品数量
    public static final String PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT = "PROMOTION_B2C_SHOW_REMAIN_COUNT";
    // Redis展示参与秒杀人数
    public static final String PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT = "PROMOTION_B2C_SHOW_ACTOR_COUNT";
    // Redis真实秒杀商品数量
    public static final String PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT = "PROMOTION_B2C_REAL_REMAIN_COUNT";
    // Redis真实参与秒杀人数
    public static final String PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT = "PROMOTION_B2C_REAL_ACTOR_COUNT";
    // Redis会员秒杀活动参加信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_COUNT = "B2C_MIDDLE_BUYER_TIMELIMITED_COUNT";
    // Redis会员秒杀log信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG = "B2C_MIDDLE_BUYER_TIMELIMITED_USELOG";
    // Redis会员秒杀的使用log信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG =
            "B2C_MIDDLE_BUYER_TIMELIMITED_NEED_SAVE_USELOG";
    // Redis秒杀订单信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_ORDER_INFO = "B2C_MIDDLE_BUYER_TIMELIMITED_ORDER_INFO";
}
