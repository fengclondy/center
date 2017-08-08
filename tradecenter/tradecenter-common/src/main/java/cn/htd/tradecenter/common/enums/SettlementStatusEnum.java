package cn.htd.tradecenter.common.enums;


/**
 * Purpose 结算单状态集合
 * @author zf.zhang
 * @since 2017-02-15 10:40
 */
public enum SettlementStatusEnum {
	
	
    // 结算单状态 [10.待财务确认,11.待商家提款,12.商家提款处理中,13.结算已完成,14.结算单失效]
    SETTLEMENT_STATUS_10("10", "待财务确认"),
    SETTLEMENT_STATUS_11("11", "待商家提款"),
    SETTLEMENT_STATUS_12("12", "商家提款处理中"),
    SETTLEMENT_STATUS_13("13", "结算已完成"),
    SETTLEMENT_STATUS_14("14", "结算单失效"),
    SETTLEMENT_STATUS_15("15", "商品+结算处理中"),
	SETTLEMENT_CHANNEL_10("10", "内部供应商"),
	SETTLEMENT_CHANNEL_20("20", "外部供应商"),
	SETTLEMENT_CHANNEL_3010("3010", "京东商品+");

	private final String key;
	private final String value;

	private SettlementStatusEnum(final String key, final String value)
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
	
	// 获取枚举key对应的value
	public static String getValue(String key) {
		for (SettlementStatusEnum statusEnum : SettlementStatusEnum.values()) {
			if (key.equals(statusEnum.key())) {
				return statusEnum.value();
			}
		}
		return null;
	}


	
	
}
