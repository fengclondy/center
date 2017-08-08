package cn.htd.zeus.tc.common.constant;

import java.math.BigDecimal;

public class Constant {
	// 加解密key
	public final static String KEY = "986566561254568a47794d4950642227662a403f3b6F325e45774S5s44";

	public final static String MD5KEY = "64da728eb8f3c9ab7ab8a6fd";

	public final static String OPERATE_CODE = "1001";// 操作人员代码

	public final static String OPERATER_NAME = "system";// 操作人员名称

	// 订单异常状态
	public final static String IS_ERROR_ORDER = "1";

	public final static String ORDER_ERROR_REASON = "订单价格被篡改";

	public final static String ORDER_ERROR_REASON2 = "已取消订单被成功支付";

	public final static String ORDER_ERROR_REASON3 = "订单中含有已取消订单行按订单原价支付";

	// 订单已取消状态
	public final static int IS_CANCEL_ORDER = 1;// 操作人员名称
	// 订单非取消状态
	public final static int IS_NOT_CANCEL_ORDER = 0;

	// 订单已VMS已删除状态
	public final static int IS_CANCEL_VMS_DELETE_ORDER = 2;// 操作人员名称

	// 秒杀订单静态值-1
	public final static int IS_TIMELIMITED_ORDER = 1;

	// 非秒杀订单静态值-0
	public final static int IS_NOT_TIMELIMITED_ORDER = 0;

	// 已结算订单标记-1
	public final static int IS_SETTLED_ORDER = 1;

	// 未结算订单标记-0
	public final static int IS_NOT_SETTLED_ORDER = 0;

	// 参与优惠静态值-1
	public final static int HAS_USED_COUPON = 1;

	// 没有参与优惠静态值-0
	public final static int HAS_NOT_USED_COUPON = 0;

	// 订单已退款状态
	public final static String REFUND_STATUS_REFUNDED = "01";

	// 订单退款中状态
	public final static String REFUND_STATUS_REFUNDING = "00";

	// 供应商类型-1代表内部供应商 2-代表外部供应商
	public final static String SELLER_TYPE_IN = "1";

	public final static String SELLER_TYPE_OUT = "2";

	// 优惠券类型-1-优惠类型是优惠券 2-优惠类型是秒杀
	public final static String PROMOTION_TYPE_TIMELIMITED = "2";

	public final static String PROMOTION_TYPE_COUPON = "1";

	// 商品上架标志
	public final static String PRODUCT_UP_SHELF = "1";
	// 商品下架标志
	public final static String PRODUCT_DOWN_SHELF = "0";
	// 内部供应商
	public final static String PRODUCT_CHANNEL_CODE_INNER = "10";
	// 外部供应商
	public final static String PRODUCT_CHANNEL_CODE_OUTER = "20";
	// 外接供应商-京东
	public final static String PRODUCT_CHANNEL_CODE_OUTLINE = "3010";
	// 默认内部供应商运费为0
	public final static BigDecimal PRODUCT_INNER_FREIGHT = new BigDecimal("0");
	// 计价方式，1件数
	public final static String ORDER_VALUATION_WAY_NUMBER = "1";
	// 计价方式，2重量
	public final static String ORDER_VALUATION_WAY_WEIGHT = "2";
	// 计价方式，3体积
	public final static String ORDER_VALUATION_WAY_VOLUME = "3";
	// 秒杀活动标志位 0：非秒杀活动 1：秒杀活动
	public final static String PROMOTION_SECKILL_FLAG = "0";
	// 京东商品库存有货，可立即发货
	public final static int JD_PRODUCT_STOCK_AVAILABLE = 33;

	public final static String ORDER_PREHOLDING_NUMBER = "50";

	// 订单类型为父订单
	public final static String ORDER_TYPE_PARENT = "PARENT";

	// 订单类型为子订单
	public final static String ORDER_TYPE_SUB = "SUB";

	// 内部供应商查询QQ类型
	public final static String CUSTOMER_QQ_TYPE_INNER = "0";

	// 外部供应商查询QQ类型
	public final static String CUSTOMER_QQ_TYPE_OUTER = "1";

	// 此常量就是为了拦截器打印查询的时候为debugger级别
	public final static String ORDER_QUERY = "OrderQuery";

	// 超级老板充值渠道为2
	public final static String RECHARGE_CHANNEL_SUPERBOSS = "2";

	// POS机充值渠道为3
	public final static String RECHARGE_CHANNEL_POS = "3";

}
