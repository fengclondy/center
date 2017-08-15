package cn.htd.goodscenter.common.constants;

/**
 * 常量类
 */
public class Constants {
	/**
	 * 区
	 */
	public static final String SALES_AREA_TYPE_OF_DISTRICT = "1";

	/**
	 * 市
	 */
	public static final String SALES_AREA_TYPE_OF_CITY = "2";

	/**
	 * 省
	 */
	public static final String SALES_AREA_TYPE_OF_PROVINCE = "3";

	public static final String CITY_SITE_OF_ALL_COUNTRY = "1";

	/**
	 * VIP会员等级 = 6
	 */
	public static final String VIP_BUYER_GRADE = "6";

	/**
	 * 价格类型 - 会员等级价
	 */
	public static final String PRICE_TYPE_BUYER_GRADE = "3";

	public static final Long SYSTEM_CREATE_ID = 0L;

	public static final String SYSTEM_CREATE_NAME = "admin";

	public static final Integer DELETE_FLAG_NO = 0;

	public static final Integer DELETE_FLAG_YES = 1;

	public static final String SEPARATOR_COMMA = ",";

	public static final String SEPARATOR_UNDERLINE = "_";

	public static final String DELETE_FLAG = "1";

	/**
	 * redis缓存前缀 - 库存锁
	 */
	public static final String REDIS_KEY_PREFIX_STOCK = "B2B_MIDDLE_PRODUCT_STOCK";

	/**
	 * redis缓存前缀 - 品牌
	 */
	public static final String REDIS_KEY_PREFIX_BRAND = "B2B_MIDDLE_PRODUCT_BRAND";

	/**
	 * redis缓存前缀 - 商品基本信息
	 */
	public static final String REDIS_KEY_PREFIX_SKU_BASIC_INFO = "B2B_MIDDLE_PRODUCT_SKU_BASIC_INFO";

	/**
	 * redis缓存前缀 - 属性
	 */
	public static final String REDIS_KEY_PREFIX_ATTRIBUTE = "B2B_MIDDLE_PRODUCT_ATTRIBUTE";

	/**
	 * redis缓存前缀 - 属性值
	 */
	public static final String REDIS_KEY_PREFIX_ATTRIBUTE_VALUE = "B2B_MIDDLE_PRODUCT_ATTRIBUTE_VALUE";

	public static final int CACHE_EXPIRE = 1800; // 半个小时 5 * 6 * 60

	/**
	 * 上架类型 - 包厢
	 */
	public static final String SHELF_TYPE_IS_BOX = "1";
	/**
	 * 上架类型 - 区域
	 */
	public static final String SHELF_TYPE_IS_AREA = "2";
	/**
	 * 包厢标记 - 包厢
	 */
	public static final Integer BOX_FLAG_IS_BOX = 1;
	/**
	 * 包厢标记 - 区域
	 */
	public static final Integer BOX_FLAG_IS_AREA = 0;
	/**
	 * 内部供应商商品是否可卖-未发布
	 */
	public static final String IS_VISABLE_FALSE = "0";
	/**
	 * 内部供应商商品是否可卖-发布
	 */
	public static final String IS_VISABLE_TRUE = "1";

	/**
	 * 预售商品同步时间
	 */
	public static final String LAST_SYSC_PRE_SALE_PRODUCT_TIME = "LAST_SYSC_PRE_SALE_PRODUCT_TIME";

}
