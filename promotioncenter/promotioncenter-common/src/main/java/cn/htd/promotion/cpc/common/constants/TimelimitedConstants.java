package cn.htd.promotion.cpc.common.constants;

/**
 * Purpose:timelimited Constants
 *
 * @author zf.zhang
 * @since 2017-09-06 15:30
 */
public final class TimelimitedConstants {

	private TimelimitedConstants()
	{
		//empty
	}

	
	/**
	 * 说明：上下架操作返回状态码 UPDOWN_SHELVES_STATUS_*
	 * 0.成功, 1.参数为空, 2.活动编码为空, 3.上下架为空, 4.上下架状态不正确, 5.秒杀活动不存在, 6.秒杀活动已经上架,
	 * 7.下架状态的秒杀商品库存小于1, 8.秒杀开始时间小于或等于当前时间, 9.秒杀结束时间小于或等于当前时间, 10.秒杀开始时间大于或等于结束时间, 11.活动已经处于下架状态
	 * -1 系统异常
	 */
	public static final String UPDOWN_SHELVES_STATUS_SUCCESS = "0";
	public static final String UPDOWN_SHELVES_STATUS_ERROR = "-1";
	public static final String UPDOWN_SHELVES_STATUS_1 = "1";
	public static final String UPDOWN_SHELVES_STATUS_2 = "2";
	public static final String UPDOWN_SHELVES_STATUS_3 = "3";
	public static final String UPDOWN_SHELVES_STATUS_4 = "4";
	public static final String UPDOWN_SHELVES_STATUS_5 = "5";
	public static final String UPDOWN_SHELVES_STATUS_6 = "6";
	public static final String UPDOWN_SHELVES_STATUS_7 = "7";
	public static final String UPDOWN_SHELVES_STATUS_8 = "8";
	public static final String UPDOWN_SHELVES_STATUS_9 = "9";
	public static final String UPDOWN_SHELVES_STATUS_10 = "10";
	public static final String UPDOWN_SHELVES_STATUS_11 = "11";
	
	// 数据库商品库存
	public static final String TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT = "1";
	// redis商品真实库存 
	public static final String TYPE_REDIS_TIMELIMITED_REAL_REMAIN_COUNT = "2";
	
	
	
	/**
	 * 促销活动发起方类型  1:平台，2:店铺
	 */
	public enum PromotionProviderTypeEnum
	{
		PLATFORM("1", "平台"),
		SHOP("2", "店铺");

		private final String key;
		private final String value;

		private PromotionProviderTypeEnum(final String key, final String value)
		{
			this.key = key;
			this.value = value;
		}

		public String key()
		{
			return this.key;
		}

		public String value()
		{
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (PromotionProviderTypeEnum promotionProviderTypeEnum : PromotionProviderTypeEnum.values()) {
	            if (key.equals(promotionProviderTypeEnum.key())) {
	                return promotionProviderTypeEnum.value();
	            }
	        }
	        return null;
	    }
	    
	}
	
	
	/**
	 * 活动类型 1：优惠券，2：秒杀，21：扭蛋机，22：砍价，23：总部秒杀
	 */
	public enum PromotionTypeEnum
	{
		COUPON("1", "优惠券"),
		SECKILL("2", "秒杀"),
		DRAW_LOTTERY("21", "扭蛋机"),
		BARGAIN("22", "砍价"),
		TIMELIMITED("23", "总部秒杀");

		private final String key;
		private final String value;

		private PromotionTypeEnum(final String key, final String value)
		{
			this.key = key;
			this.value = value;
		}

		public String key()
		{
			return this.key;
		}

		public String value()
		{
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (PromotionTypeEnum promotionTypeEnum : PromotionTypeEnum.values()) {
	            if (key.equals(promotionTypeEnum.key())) {
	                return promotionTypeEnum.value();
	            }
	        }
	        return null;
	    }
	}
	
	/**
	 * 促销活动展示状态 
	 * 审核状态 0：待审核，1：审核通过，2：审核被驳回，3：启用，4：不启用
	 * redis里存储的审核状态
	 * PROMOTION_VERIFY_STATUS_PENDING&0  待审核& 
	 * PROMOTION_VERIFY_STATUS_PASS&1     审核通过& 
	 * PROMOTION_VERIFY_STATUS_FEFUSE&2   审核驳回&
	 * PROMOTION_VERIFY_STATUS_VALID&3    启用& 
	 * PROMOTION_VERIFY_STATUS_INVALID&4  未启用&
	 * 
	 */
	public enum PromotionShowStatusEnum
	{
		PENDING("0", "待审核"),
		PASS("1", "审核通过"),
		FEFUSE("2", "审核驳回"),
		VALID("3", "启用"),
		INVALID("4", "未启用");

		private final String key;
		private final String value;

		private PromotionShowStatusEnum(final String key, final String value)
		{
			this.key = key;
			this.value = value;
		}

		public String key()
		{
			return this.key;
		}

		public String value()
		{
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (PromotionShowStatusEnum promotionShowStatusEnum : PromotionShowStatusEnum.values()) {
	            if (key.equals(promotionShowStatusEnum.key())) {
	                return promotionShowStatusEnum.value();
	            }
	        }
	        return null;
	    }
	}
	
	
	
}
