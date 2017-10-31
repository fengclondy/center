/**
 * 
 */
package cn.htd.promotion.cpc.common.constants;

/**
 * @author htd_ly
 *
 */
public class Constants {

	public final static String ORDER_QUERY = "OrderQuery";

	// 秒杀库存锁定操作
	public final static String SECKILL_RESERVE = "1";

	// 秒杀库存释放操作
	public final static String SECKILL_RELEASE = "2";

	// 秒杀库存扣减操作
	public final static String SECKILL_REDUCE = "3";

	// 秒杀库存回滚操作
	public final static String SECKILL_ROLLBACK = "4";

	// 秒杀商城订单删除锁定资格
	public final static String SECKILL_DELHASH = "5";

	// 已经释放库存标志
	public final static String HAS_RELEASE_FLAG = "1";

	// 秒杀预占订单号前缀
	public final static String ORDER_PREHOLDING_NUMBER = "60";

	// 商品已经售罄
	public static final String PROMOTION_NO_STOCK = "10001";

	// 商品已经被砍完
	public static final String PROMOTION_IS_BARGAIN_OVER = "10002";

	// 已经参与过砍价
	public static final String PROMOTION_IS_PATYIESIN = "10003";

	// 参与砍价人数已经超过上限
	public static final String PROMOTION_PATYIESIN_IS_EXCEED = "10004";

	// 促销活动已经结束
	public static final String PROMOTION_DATETIME_IS_OVER = "10005";

	// 促销活动已经结束
	public static final String PROMOTION_DATETIME_IS_NOSTART = "10006";

	// 今天活动已经结束
	public static final String PROMOTION_DATETIME_IS_GAMEOVER = "10007";

	// 今天活动未开始
	public static final String PROMOTION_DATETIME_NOTBEGIN = "10008";

	// 判断下架
	public static final String IS_DOWN_SELF = "10009";

	// 判断是否帮忙砍过价
	public static final String IS_BUYER_BARGAIN = "IS_BUYER_BARGAIN";

	// 砍价发起常量
	public static final String BUYER_LAUNCH_BARGAIN_INFO = "BUYER_LAUNCH_BARGAIN_INFO";

	// 砍价记录常量
	public static final String BUYER_BARGAIN_RECORD = "BUYER_BARGAIN_RECORD";

	// 秒杀库存锁
	public static final String REDIS_KEY_PREFIX_STOCK = "B2B_MIDDLE_SECKILL_STOCK";
}
