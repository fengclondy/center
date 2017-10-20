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
	
	
	/**
	 * 说明：团购返回状态码集合
	 */
	public enum CommonStatusEnum
	{
		STATUS_SUCCESS("0", "成功"),
		STATUS_ERROR("-1", "失败"),
		
		//上下架操作返回状态码 [1001.参数为空,1002.活动编码为空,1003.上下架为空,1004.上下架状态不正确,1005.活动不存在,1006.活动已经上架,1007.活动已经下架]
		UPDOWN_SHELVES_STATUS_1("1001", "参数为空"),
		UPDOWN_SHELVES_STATUS_2("1002", "活动编码为空"),
		UPDOWN_SHELVES_STATUS_3("1003", "上下架为空"),
		UPDOWN_SHELVES_STATUS_4("1004", "上下架状态不正确"),
		UPDOWN_SHELVES_STATUS_5("1005", "活动不存在"),
		UPDOWN_SHELVES_STATUS_6("1006", "活动已经上架"),
		UPDOWN_SHELVES_STATUS_7("1007", "活动已经下架"),
		
		// 删除操作返回状态码 [1021.参数为空,1022.活动编码为空]
		DELGROUPBUYING_PARAM_IS_NULL("1021", "参数为空"),
		DELGROUPBUYING_PROMOTIONID_IS_NULL("1022", "活动编码为空"),
		
		
		
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
