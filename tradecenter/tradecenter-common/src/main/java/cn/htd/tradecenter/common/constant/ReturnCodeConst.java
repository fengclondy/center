package cn.htd.tradecenter.common.constant;

public class ReturnCodeConst {

	// 处理成功
	public static final String RETURN_SUCCESS = "00000";
	// 系统异常
	public static final String SYSTEM_ERROR = "99999";

	// *******************************************************
	// 通用返回码
	// *******************************************************
	// 入参不合法
	public static final String PARAMETER_ERROR = "16001";
	// 会员信息不存在
	public static final String BUYER_NOT_EXIST = "16002";
	// 卖家信息不存在
	public static final String SELLER_NOT_EXIST = "16003";
	// 店铺信息不存在
	public static final String SHOP_NOT_EXIST = "16004";
	// 会员经营关系不存在
	public static final String BUYER_BUSINESS_RELATION_NOT_EXIST = "16005";
	// 经营关系客户经理不存在
	public static final String CUSTOMER_MANAGER_NOT_EXIST = "16006";
	// 新增经营关系失败
	public static final String CREATE_BUSINESS_RELATION_ERROR = "16007";
	// 导出数据件数超过上限
	public static final String EXPORT_COUNT_OVER_LIMIT = "16008";
	// 买家黑名单信息异常
	public static final String BUYER_BLACK_LIST_ERROR = "16009";

	// *******************************************************
	// 订单返回码
	// *******************************************************
	// 订单数据不存在
	public static final String ORDER_NOT_EXIST = "16100";
	// 订单行没有用优惠券不存在
	public static final String ORDER_USE_COUPON_NOT_EXIST = "16101";
	// VMS开单用户不存在
	public static final String ORDER_OPERATION_USER_NOT_EXIST = "16102";
	// VMS开单用户平台公司不正确
	public static final String ORDER_OPERATION_SELLER_ERROR = "16103";
	// 订单配送方式不正确
	public static final String ORDER_UNSUPPORT_DELIVERY_TYPE = "16104";
	// 订单返利单错误
	public static final String ORDER_NONE_REBATE = "16105";
	// 订单返利单余额不足
	public static final String ORDER_REBATE_AMOUNT_LACK = "16106";
	// 订单状态不能拆单
	public static final String ORDER_STATUS_CANNOT_DISTRIBUTE = "16107";
	// 订单已被取消
	public static final String ORDER_HAS_CANCELED = "16108";
	// 订单状态不能拆单
	public static final String ORDER_ERROR_STATUS_CANNOT_DISTRIBUTE = "16109";
	// 订单已被修改
	public static final String ORDER_HAS_MODIFIED = "16110";
	// 订单分销单信息为空
	public static final String ORDER_DISTRIBUTION_NOT_EXIST = "16111";
	// 订单信息更新失败
	public static final String ORDER_UPDATE_ERROR = "16112";
	// 订单分销单信息更新失败
	public static final String ORDER_DISTRIBUTE_UPDATE_ERROR = "16113";
	// 订单已关闭
	public static final String ORDER_HAS_CLOSED = "16114";
	// 审核订单时订单非待审核状态
	public static final String ORDER_NOT_VERIFY_PENDING = "16115";
	// 订单为异常状态
	public static final String ORDER_ERROR_STATUS = "16116";
	// 订单议价时订单非待审核或待支付状态
	public static final String ORDER_NOT_VERIFY_PAY_PENDING = "16117";
	// 订单议价时秒杀订单不可修改
	public static final String ORDER_IS_TIMELIMITED_ERROR = "16118";
	// 订单已议价过不能再次议价
	public static final String ORDER_HAS_NEGOTIATION = "16119";
	// 订单议价时VMS新增订单不能议价
	public static final String ORDER_IS_VMS_ADDED = "16120";
	// 订单状态不正确
	public static final String ORDER_NOT_ERP_PENDING = "16121";
	// 订单议价没有变化
	public static final String ORDER_NEGOTIATION_NO_CHANGE = "16122";
	// 用户专有账户余额不足
	public static final String ORDER_PRIVATEACCOUNT_NO_ENOUGH = "16123";

	// *******************************************************
	// 订单行返回码
	// *******************************************************
	// 订单行数据不存在
	public static final String ORDER_ITEM_NOT_EXIST = "16200";
	// 订单行商品总金额小于返利金额
	public static final String ORDER_ITEM_AMOUNT_LACK = "16201";
	// 订单行商品数量小于库存数量
	public static final String ORDER_ITEM_COUNT_LACK = "16202";
	// 订单行协议商品编号不正确
	public static final String ORDER_ITEM_NONE_AGREEMENT = "16203";
	// 订单行商品价格过低
	public static final String ORDER_ITEM_PRICE_TOO_LOW = "16204";
	// 订单行已被修改
	public static final String ORDER_ITEM_HAS_MODIFIED = "16205";
	// 订单行拆单数据
	public static final String ORDER_ITEM_WAREHOUSE_NOT_EXIST = "16206";
	// 订单行拆单数量大于可用库存
	public static final String ORDER_ITEM_DISTRIBUTE_INVENTORY_LACK = "16207";
	// 订单行总数量和拆单数量不等
	public static final String ORDER_ITEM_COUNT_WAREHOUSE_NUMER_ERROR = "16208";
	// 订单行不能拆单
	public static final String ORDER_ITEM_CANNOT_DISTRIBUTE = "16209";
	// 订单行已被取消
	public static final String ORDER_ITEM_HAS_CANCELED = "16210";
	// 订单商品没有商品模版
	public static final String ORDER_ITEM_SPU_NOT_EXIST = "16211";
	// 订单商品没有可用库存
	public static final String ORDER_ITEM_AVAILABLE_INVENTORY_LACK = "16212";
	// 订单商品没有价格
	public static final String ORDER_ITEM_NONE_PRICE = "16213";
	// 订单行信息更新失败
	public static final String ORDER_ITEM_UPDATE_ERROR = "16214";
	// 订单议价时使用优惠券订单行不可修改
	public static final String ORDER_ITEM_USED_COUPON_ERROR = "16115";
	// 订单议价时非HTD商品订单行不可修改
	public static final String ORDER_ITEM_NONE_HTD_PRODUCT_ERROR = "16116";
	// 订单行条数超过上限
	public static final String ORDER_ITEM_COUNT_OVER_LIMIT = "16117";
	// 订单行不是可下行ERP状态
	public static final String ORDER_ITEM_NOT_ERP_PENDING = "16118";
}
