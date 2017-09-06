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
