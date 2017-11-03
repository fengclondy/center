package cn.htd.promotion.cpc.common.constants;

/**
 * Purpose:timelimited Constants
 *
 * @author zf.zhang
 * @since 2017-09-06 15:30
 */
public final class GroupbuyingConstants {

	private GroupbuyingConstants()
	{
		//empty
	}
	
	
	// 真实参团人数KEY
	public static final String GROUPBUYINGINFO_REAL_ACTOR_COUNT_KEY = "realActorCount";
	// 真实拼团价KEY
	public static final String GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE_KEY = "realGroupbuyingPrice";
	// 活动编号KEY
	public static final String GROUPBUYINGINFO_PROMOTIONID_KEY = "promotionId";
	
	
	/**
	 * 说明：团购返回状态码集合
	 */
	public enum CommonStatusEnum
	{
		STATUS_SUCCESS("0", "成功"),
		STATUS_ERROR("-1", "失败"),
		
		//上下架操作返回状态码 [1001.参数为空,1002.活动编码为空,1003.上下架为空,1004.上下架状态不正确,1005.活动不存在,1006.活动已经上架,1007.活动已经下架,1008.redis上下架失败]
		UPDOWN_SHELVES_STATUS_1("1001", "参数为空"),
		UPDOWN_SHELVES_STATUS_2("1002", "活动编码为空"),
		UPDOWN_SHELVES_STATUS_3("1003", "上下架为空"),
		UPDOWN_SHELVES_STATUS_4("1004", "上下架状态不正确"),
		UPDOWN_SHELVES_STATUS_5("1005", "活动不存在"),
		UPDOWN_SHELVES_STATUS_6("1006", "活动已经上架"),
		UPDOWN_SHELVES_STATUS_7("1007", "活动已经下架"),
		UPDOWN_SHELVES_STATUS_8("1008", "redis上下架失败"),
		
		// 删除操作返回状态码 [1021.参数为空,1022.活动编码为空,1023.redis活动删除失败]
		DELGROUPBUYING_PARAM_IS_NULL("1021", "参数为空"),
		DELGROUPBUYING_PROMOTIONID_IS_NULL("1022", "活动编码为空"),
		DELGROUPBUYING_REDIS_REMOVE_ERROR("1023", "redis活动删除失败"),
		
		// 参团操作返回状态码 [1041.参数为空,1042.活动编码为空,1043.请求过于频繁,1044.活动不存在,1045.活动状态不是开团进行中,1046.已经参团过]
		ADDGROUPBUYING_PARAM_IS_NULL("1041", "参数为空"),
		ADDGROUPBUYING_PROMOTIONID_IS_NULL("1042", "活动编码为空"),
		ADDGROUPBUYING_REQUEST_TOO_OFTEN("1043","请求过于频繁"),
		ADDGROUPBUYING_PROMOTION_NOT_EXIST("1044","活动不存在"),
		ADDGROUPBUYING_PROMOTION_NOT_PROCESSING("1045","活动状态不是开团进行中"),
		ADDGROUPBUYING_PROMOTION_IS_RECORD("1046","已经参团过"),
		
		;

		private final String key;
		private final String value;

		private CommonStatusEnum(final String key, final String value)
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
	        for (CommonStatusEnum promotionProviderTypeEnum : CommonStatusEnum.values()) {
	            if (key.equals(promotionProviderTypeEnum.key())) {
	                return promotionProviderTypeEnum.value();
	            }
	        }
	        return null;
	    }
	    
	}
	
	
}
