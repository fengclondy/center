/**
 *
 */
package cn.htd.zeus.tc.common.enums;

/**
 * @author ly
 *
 */
public enum ResultCodeEnum {

	//订单状态
	SUCCESS("00000","成功！"),
	ORDERCANCEL_PARAM_IS_NULL("10001","调用订单接口入参不能为空！"),
	ORDERCANCEL_PARAM_IS_ERROR("10002","调用订单取消入参有错误！"),
	ORDERCANCEL_ORDERSTATUS_IS_FAIL("10021","取消订单失败，订单状态错误！"),
	ORDERDELETE_ORDERSTATUS_IS_FAIL("10121","删除订单失败，订单状态错误,不可以被删除！"),
	ORDERCANCEL_ORDERSTATUS_IS_NULL("10022","取消订单失败，订单状态为空！"),
	ORDERCANCEL_ORDERSTATUS_IS_CANCEL("10125","取消订单失败，订单已经被取消！！"),
	ORDERDELETE_ORDERSTATUS_IS_NULL("10122","删除订单失败，订单状态为空,不可以被删除！"),
	ORDERDELETE_ORDERSTATUS_IS_TORSION_FAIL("10123","删除订单失败，订单状态不能越级删除,不可以被删除！"),
	ORDERDELETE_ORDERSTATUS_IS_THROUGH_FAIL("10124","删除订单失败，订单状态已经是彻底删除状态,不可以被删除！"),
	ORDERCANCEL_REFUNDSTATUS_IS_REFUNDING("10023","订单行退款处理中，请不要频繁操作！"),
	ORDERCANCEL_REFUNDSTATUS_IS_REFUNDED("10024","订单行退款已经退款成功！"),
	ORDERQUERY_PARAM_LEAST_ONE_NULL("10025","调用订单查询接口入参必须全部非空"),
	ORDERCALLBACK_QUERY_ERP_DISTRIBUTION_IS_NULL("10026","根据订单分销单信息表的ID没有查询到记录"),
	ORDERCALLBACK_PARAM_ORDERID_IS_NULL("10027","中间件传入的订单编码不可以为空"),
	ORDERCALLBACK_PARAM_RESULT_IS_NULL("10028","中间件传入的结果不能为空"),
	ORDERCENTER_PARAM_IS_NULL("10029","messageId不能为空"),
	GOODCENTER_NOT_OUT_DISTRIBUTION("10030","该商品未超出配送范围"),
	GOODCENTER_OUT_DISTRIBUTION("10031","该商品超出配送范围"),
	MARKETCENTER_LOCK_COUPON_SUCCESS("10032","成功锁定优惠券"),
	MARKETCENTER_LOCK_COUPON_FAIL("10033","锁定优惠券失败"),
	ORDERPAYMENTRESULT_IS_ERROR("10034","查询订单支付结果不存在"),
	MEMBERCENTER_QUERY_MEMBERINFO_NOT_RESULT("10035","查询会员中心-根据会员code和会员类型没有查到会员信息"),
	GOODSCENTER_QUERY_SKUINFO_NOT_RESULT("10036","查询商品中心-根据skuCode没有查到商品信息"),
	PRICECENTER_QUERY_PRICE_NOT_RESULT("10037","查询价格中心-没有查到商品的价格信息"),
	ORDERSTATUS_UPDATE_ERROR("10038","更新订单支付状态失败"),
	GOODSCENTER_QUERY_SKUINFO_STOCK_LESS_THAN_ZERO("10039","查询商品中心-(扣减购买数量后)库存不足"),//TODO
	ORDERSTATUS_ORDER_PAY_AMOUNT_ERROR("10040","金额被篡改"),
	GOODSCENTER_BATCH_RESERVE_STOCK_SUCCESS("10041","商品中心批量锁定库存成功"),
	GOODSCENTER_BATCH_RESERVE_STOCK_FAILED("10042","商品中心批量锁定库存失败"),
	GOODSCENTER_BATCH_RELEASE_STOCK_SUCCESS("10043","商品中心批量释放库存成功"),
	GOODSCENTER_BATCH_RELEASE_STOCK_FAILED("10044","商品中心批量释放库存失败"),
	GOODSCENTER_RELEASE_RECHARGE_STOCK_FAILED("10069","商品中心释放议价库存失败"),
	MARKETCENTER_RESERVE_STOCK_SUCCESS("10045","促销中心锁定会员优惠券、秒杀成功"),
	MARKETCENTER_RESERVE_STOCK_FAILED("10046","促销中心锁定会员优惠券、秒杀失败"),
	JD_RESERVE_FUND_NOT_ENOUGH("10047","备用金不足"),
	GOODSCENTER_NOT_STOCKINFO_OR_GOODS_OFF_SHELF("10048","商品中心没有库存信息，或者区域和包厢的已经下架"),
	ORDERCENTER_CREAREORDER_PARAM_IS_NULL("10049","请求入参参数为空"),
	MARKETCENTER_RELEASE_STOCK_SUCCESS("10050","促销中心释放会员优惠券、秒杀成功"),
	MARKETCENTER_RELEASE_STOCK_FAILED("10051","促销中心释放会员优惠券、秒杀失败"),
	MARKETCENTER_CALCULATE_COUPON_DISCOUNT_FAILED("10052","根据选择的优惠券信息计算订单行的分摊金额失败"),
	ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL("10053","更新订单状态失败"),
	MARKETCENTER_JD_EXECUTE_FAILED("10054","京东处理失败"),
	JD_CREATE_ORDER_FAIL("10055","京东创建订单失败"),
	MIDDLEWARE_JD_CREATE_ORDER("10056","京东创建订单-中间件返回失败"),
	ORDER_CREATE_ORDER_ROLLBACK_BATCHRELEASE("10057","插入订单相关表失败回滚后释放商品和优惠券库存"),
	MARKETCENTER_REDUCE_STOCK_FAILED("10058","促销中心扣减会员优惠券、秒杀失败"),
	MARKETCENTER_ROLLBACK_STOCK_FAILED("10059","促销中心回滚会员优惠券、秒杀 失败"),
	GOODSCENTER_BATCH_REDUCE_STOCK_FAILED("10060","商品中心批量扣减库存失败"),
	GOODSCENTER_BATCH_ROLLBACK_STOCK_FAILED("10061","商品中心批量回滚库存失败"),
	GOODSCENTER_UPDATE_PAYORDERINFO_FAILED("10062","支付回调更新收付款下行表失败"),
	GOODSCENTER_INSERT_JDORDERINFO_FAILED("10063","支付回调新增京东京东待发订单信息表失败"),
	GOODSCENTER_MESSAGE_ID_IS_ERROR("10064","messageId格式不正确"),
	GOODSCENTER_PARAM_IS_ERROR("10065","参数格式不正确"),
	VALIDATE_HAS_BARGAIN("10066","打开收银台后含有议价的订单行"),
	VALIDATE_HAS_CANCEL("10067","打开收银台后含有取消的订单行"),
	VALIDATE_COMPARE_ORDER_PAY_AMT("10068","打开收银台后订单支付和中台支付金额不一致"),
	MARKETCENTER_LIMITED_TIME_PURCHASE_IS_NULL("10070","促销中心获取限时购商品信息为空"),
	// 订单结算功能报错信息  102XX开头 haozy使用
	ORDERSETTLEMENT_CHECK_SUCCESS("10200","订单结算信息查询成功"),
	ORDERSETTLEMENT_PRODUCT_IS_NULL("10201", "商品信息为空"),
	ORDERSETTLEMENT_PRODUCT_IS_DOWN_SHELF("10202","订单含有已下架商品"),
	ORDERSETTLEMENT_PRODUCT_STOCK_NULL("10203", "订单含有库存为空的商品"),
	ORDERSETTLEMENT_MEMBER_ID_NULL("10204", "会员编码为空"),
	ORDERSETTLEMENT_MESSAGE_ID_NULL("10205", "接口信息编码为空"),
	ORDERSETTLEMENT_SKU_INFO_NULL("10204", "会员编码为空"),
	ORDERSETTLEMENT_CONSIGADDR_NULL("10205", "收货地址为空"),
	ORDERSETTLEMENT_PRODUCT_PRICE_NULL("10206", "商品价格查询为空"),
	ORDERSETTLEMENT_MEMBER_GRADE_NULL("10207", "会员等级信息查询为空"),
	ORDERSETTLEMENT_SELLER_INFO_NULL("10208", "卖家信息为空"),
	MARKETCENTER_SECKILL_INFO_SUCCESS("10209", "促销中心获取秒杀信息成功"),
	MARKETCENTER_SECKILL_INFO_FAILED("10210", "促销中心获取秒杀信息失败"),
	ORDERSETTLEMENT_DEAL_JDAMOUNT_BEYOND("10211", "订单价格超过京东账户余额"),
	ORDERSETTLEMENT_BUYCOUNT_BEYOND("10212","商品购买数量超出商品库存余额"),
	ORDERSETTLEMENT_MEMBER_INFO_NULL("10213", "会员信息为空"),
	ORDERSETTLEMENT_JD_HTTP_ERROR("10214", "http接口token查询为空"),
	ORDERSETTLEMENT_JD_AMOUNT_CHECK_FAIL("10215", "京东商品订单价格校验失败"),
	ORDERSETTLEMENT_JD_STOCK_CHECK_FAIL("10216", "京东商品库存数量不足"),
	ORDERSETTLEMENT_PROMOTION_ID_NULL("10217", "秒杀活动编码为空"),
	SHOPINFO_IS_NULL("10218", "查询店铺信息返回为空！"),
	MARKERCENTER_LIMITED_TIME_PURCHASE_BUYCOUNT_BEYOND("10219","商品购买数量超过促销中心每单限制数量或超过剩余库存数量"),
	
	SHOPORDER_ANALSIS_PARAM_IS_NULL("10126","店铺经营状况查询参数为空！"),
	SHOPORDER_ANALSIS_DATEPARAM_IS_NULL("10127","查询时间参数格式错误！"),
	ORDERCANCEL_ORDER_IS_NOT_EXIST("10128","取消订单失败，订单不存在！！"),
	ORDERCANCEL_ORDER_IS_UNLOCK_FAIL("10138","取消订单失败，订单为ERP余额支付调用ERP支付解锁失败！！"),
	MARKETCENTER_SECKILL_RESERVE_SUCCESS("10129", "秒杀商品库存锁定成功！"),
	MARKETCENTER_SECKILL_RESERVE_ERROR("10130", "秒杀商品库存锁定失败！"),
	MIDDLEWARE_GETTOKEN_FAIL("10137", "获取中间件TOKEN失败！"),
	ORDERCANCEL_REFUNDSTATUS_IS_DATABASEFAIL("10140","订单取消失败数据库未更新到记录！"),
	ORDERCANCEL_ERP_DOWN_MQ_FAIL("10141","订单取消失败,ERP收付款下行未成功！"),
	ERROR("99999","未知异常！");

	private ResultCodeEnum(String code, String msg) {
		this.code = code;
		Msg = msg;
	}
    // 成员变量
    private String code;
    private String Msg;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
}
