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
}
