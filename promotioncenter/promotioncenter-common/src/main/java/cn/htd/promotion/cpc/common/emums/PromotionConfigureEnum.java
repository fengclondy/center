package cn.htd.promotion.cpc.common.emums;

/**
 * Purpose:配置类型常量
 * @author zf.zhang
 * @since 2017-09-06 15:30
 */
public enum PromotionConfigureEnum {
	
	
	/**
	 * 全局说明
	 * 
	 * 配置类型   1：活动渠道，2：支付方式，3：配送方式
	 * 配置值 
	 * 活动渠道：   1：B2B商城，2：超级老板，3：汇掌柜，4：大屏；
	 * 配送方式：   1：供应商配送  2：自提； Delivery mode
	 * 支付方式：   1：余额帐支付，2：平台账户支付，3：在线支付 Payment method
	 */
	
	

	MOVABLE_CHANNEL("1", "活动渠道"), 
	PAYMENT_METHOD("2", "支付方式"), 
	DELIVERY_MODE("21", "配送方式");

	private final String key;
	private final String value;

	private PromotionConfigureEnum(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	public String key() {
		return this.key;
	}

	public String value() {
		return this.value;
	}
	
    // 获取枚举value对应名字方法
    public static String getValue(String key) {
        for (PromotionConfigureEnum promotionConfigureEnum : PromotionConfigureEnum.values()) {
            if (key.equals(promotionConfigureEnum.key())) {
                return promotionConfigureEnum.value();
            }
        }
        return null;
    }
	

    /**
     * 活动渠道
     */
	public enum MovableChannelEnum {
		
		B2B_MALL("1", "B2B商城"), 
		SUPERBOSS("2", "超级老板"), 
		MOBILE_MALL("3", "汇掌柜"), 
		ZHI_HUI_MEN_DIAN("4", "大屏");

		private final String key;
		private final String value;

		private MovableChannelEnum(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (MovableChannelEnum movableChannelEnum : MovableChannelEnum.values()) {
	            if (key.equals(movableChannelEnum.key())) {
	                return movableChannelEnum.value();
	            }
	        }
	        return null;
	    }
	}
	
    /**
     * 支付方式
     */
	public enum PaymentMethodEnum {
		
		BALANCE_ACCOUNT_PAYMENT("1", "余额帐支付"), 
		PLATFORM_ACCOUNT_PAYMENT("2", "平台账户支付"), 
		ONLINE_PAYMENT("3", "在线支付"),
		CASH_ON_DELIVERY("4","货到付款");

		private final String key;
		private final String value;

		private PaymentMethodEnum(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (PaymentMethodEnum paymentMethodEnum : PaymentMethodEnum.values()) {
	            if (key.equals(paymentMethodEnum.key())) {
	                return paymentMethodEnum.value();
	            }
	        }
	        return null;
	    }
	}
	
	
    /**
     * 配送方式
     */
	public enum DeliveryModeEnum {

        SUPPLIER_DISTRIBUTION("1", "供应商配送"), 
        PICK_UP_STATION("2", "自提");

		private final String key;
		private final String value;

		private DeliveryModeEnum(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}
		
	    // 获取枚举value对应名字方法
	    public static String getValue(String key) {
	        for (DeliveryModeEnum deliveryModeEnum : DeliveryModeEnum.values()) {
	            if (key.equals(deliveryModeEnum.key())) {
	                return deliveryModeEnum.value();
	            }
	        }
	        return null;
	    }
	}

}
