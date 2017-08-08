package cn.htd.tradecenter.common.enums;


/**
 * Purpose 结算单常量集合
 * @author zf.zhang
 * @since 2017-02-15 10:40
 */
public class SettlementEnum {
	
	/**
	 * 说明：渠道编码[10 内部供应商 20 外部供应商 3010 京东商品＋]
	 * 
	 */
	public enum ProductChannel
	{
	
	PRODUCT_CHANNEL_10("10", "内部供应商"),
	PRODUCT_CHANNEL_20("20", "外部供应商"),
	PRODUCT_CHANNEL_3010("3010", "京东商品＋");

	private final String key;
	private final String value;

	private ProductChannel(final String key, final String value)
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
		for (ProductChannel statusEnum : ProductChannel.values()) {
			if (key.equals(statusEnum.key())) {
				return statusEnum.value();
			}
		}
		return null;
	}

	}
	
	
	/**
	 * 说明：结算单类型[10 外部供应商 20 平台公司]
	 * 
	 */
	public enum SettlementType
	{
	
    SETTLEMENT_TYPE_10("10", "外部供应商"),
    SETTLEMENT_TYPE_20("20", "平台公司");

	private final String key;
	private final String value;

	private SettlementType(final String key, final String value)
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
		for (SettlementType statusEnum : SettlementType.values()) {
			if (key.equals(statusEnum.key())) {
				return statusEnum.value();
			}
		}
		return null;
	}

	}
	
	
//	public static void main(String[] args) {
//		System.out.println(SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key);
//	}
	
	
}
