package cn.htd.promotion.cpc.common.constants;

public class RedisConst {
	// Redis操作Hash
	public static final String REDIS_PROMOTION_ACTION_HASH = "B2B_MIDDLE_PROMOTION_ACTION_HASH";
	// Redis砍价促销活动有效信息
	public static final String REDIS_BARGAIN_VALID = "B2B_MIDDLE_BARGAIN_VALID";
	// Redis砍价活动数据
	public static final String REDIS_BARGAIN = "B2B_MIDDLE_BARGAIN";
	// Redis砍价活动结果数据
	public static final String REDIS_BARGAIN_RESULT = "B2B_MIDDLE_BARGAIN_RESULT";
	// Redis砍价活动数据
	public static final String REDIS_BARGAIN_INDEX = "B2B_MIDDLE_BARGAIN_INDEX";
	// Redis砍价商品总数量
	public static final String REDIS_BARGAIN_TOTAL_COUNT = "TOTAL_COUNT";
	// Redis展示参与砍价人数
	public static final String REDIS_BARGAIN_SHOW_ACTOR_COUNT = "SHOW_ACTOR_COUNT";
	// Redis真实砍价商品数量
	public static final String REDIS_BARGAIN_REAL_REMAIN_COUNT = "REAL_REMAIN_COUNT";
	// Redis真实参与砍价人数
	public static final String REDIS_BARGAIN_REAL_ACTOR_COUNT = "REAL_ACTOR_COUNT";
	//Redis砍价活动开始时间
	public static final String REDIS_BARGAIN_START_TIME = "BARGAIN_START_TIME";
	//Redis砍价活动结束时间 
	public static final String REDIS_BARGAIN_END_TIME = "BARGAIN_END_TIME";
	//Redis用户参加砍价活动信息
	public static final String REDIS_BUYER_BARGAIN_USELOG =  "BUYER_BARGAIN_USELOG";
	//Redis砍价活动金额拆分
	public static final String REDIS_BARGAIN_PRICE_SPLIT = "BARGAIN_PRICE_SPLIT";
	
	public static final String BUYER_BARGAIN_RECORD = "BUYER_BARGAIN_RECORD";
	
	

    // Redis会员促销活动有效信息
    public static final String PROMOTION_REDIS_TIMELIMITED_VALID = "PROMOTION_B2C_MIDDLE_TIMELIMITED_VALID";
    // Redis秒杀活动数据
    public static final String PROMOTION_REDIS_TIMELIMITED = "PROMOTION_B2C_MIDDLE_TIMELIMITED";
    // Redis秒杀活动结果数据
    public static final String PROMOTION_REDIS_TIMELIMITED_RESULT = "PROMOTION_B2C_MIDDLE_TIMELIMITED_RESULT";
    // Redis秒杀活动数据
    public static final String PROMOTION_REDIS_TIMELIMITED_INDEX = "PROMOTION_B2C_MIDDLE_TIMELIMITED_INDEX";
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
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_COUNT = "PROMOTION_B2C_MIDDLE_BUYER_TIMELIMITED_COUNT";
    // Redis会员秒杀log信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG = "PROMOTION_B2B_MIDDLE_BUYER_TIMELIMITED_USELOG";
    // Redis会员秒杀的使用log信息
    public static final String PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG = "PROMOTION_B2B_MIDDLE_BUYER_TIMELIMITED_NEED_SAVE_USELOG";
}
