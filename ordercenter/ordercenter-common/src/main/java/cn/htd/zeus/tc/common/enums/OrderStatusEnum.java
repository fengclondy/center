package cn.htd.zeus.tc.common.enums;


public enum OrderStatusEnum {
	//订单状态和订单行状态
	PRE_CHECK("10","待审核"),
	PRE_PAY("20","待支付"),
	CHECK_ADOPT_PRE_PAY("21","审核通过待支付"),
	VMS_ORDER_PRE_DOWN_ERP("22","VMS开单待下行ERP"),
	PAYED_PRE_SPLIT_ORDER("30","已支付"),
	PAYED_POST_STRIKEA_DOWNING("311","已支付收付款下行中"),
	PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST("312","已支付收付款下行成功待拆单"),
	PAYED_PRE_SPLIT_ORDER_PRE_OPEN_LIST("31","已支付待拆单"),
	PAYED_PRE_SPLIT_ORDER_PRE("32","拆单完成待下行ERP"),
	PAYED_SPLITED_ORDER_PRE_ERP("33","已支付ERP下行处理中"),
	PRE_DELIVERY("40","待发货"),
	DELIVERYED("50","已发货"),
	BUYER_RECEIPT("61","买家收货"),
	EXPIRE_RECEIPT("62","到期自动收货"),
	ORDER_COMPLETE("70","已完成"),

	//订单异常状态
	PAYED_EXECUTE_ERROR("25","支付后订单资源处理异常"),
	ERP_EXECUTE__ERROR("905","ERP处理异常"),
	POST_STRIKEA_CALL_BACK("906","收付款回调结果异常"),
    PRE_SALES_CALL_BACK_FAIL("909","预售回调处理失败"),
	//订单分销单信息表 ERP下行状态
	ERP_PRE_HANDLE("1","待处理"),
	ERP_HANDING("2","处理中"),
	ERP_DOWN_LINK("3","已下行"),
	ERP_SUCCESS("4","成功"),
	ERP_FAIL("9","失败"),

	//订单行退款状态
	NO_REFUND("0","未退货/退款"),
	REFUNDED("1","已退款"),
	RETURN_GOODS("2","已退货"),

	//订单和订单行表 取消状态
	NOT_CANCLE("0","未取消"),
	CANCLED("1","已取消"),

	//订单行是参与优惠  是否参与优惠 0：否，1：是
	HAS_USED_COUPON("1","是"),
	NOT_USED_COUPON("0","否"),

	//订单表销售方式
	ORDER_SALE_TYPE1("01","正常销售"),
	//订单类型
	ORDER_PRE_SALE_TYPE("1","预售订单"),

	//有外接渠道商品
	HAS_PRODUCTPLUS_FLAG("1","有外接渠道商品"),

	//优惠券提供方类型  1:平台，2:店铺
	COUPON_PROVIDER_TYPE_PLATFORM("1","平台"),

	//促销活动类型 1：优惠券，2:秒杀
	PROMOTION_TYPE_COUPON("1","优惠券"),
	PROMOTION_TYPE_SECKILL("2","秒杀"),
	PROMOTION_TYPE_LIMITED_TIME_PURCHASE("3","限时购"),

	//是否为秒杀订单
	IS_TIMELIMITED_ORDER("1","是秒杀订单"),
	NOT_IS_TIMELIMITED_ORDER("0","不是秒杀订单"),
	IS_GROUP_PURCHASE_ORDER("2","是团购订单"),

	//是否超出配送范围
	OUT_DISTRIBTION("1","超出配送范围"),
	NOT_OUT_DISTRIBTION("0","没有超出配送范围"),

	//京东待发订单信息表 - ERP预售下行状态
	ERP_RESULT_STATUS_NOT_DOWN("0","未下行"),
	ERP_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN("1","已下行MQ未返回 "),
	ERP_RESULT_STATUS_HAVE_DOWN_MQ_RETURN("2","已下行MQ已返回"),

	//京东待发订单信息表 - 京东抛单状态
	JD_RESULT_STATUS_NOT_DOWN("0","未抛单"),
	JD_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN("1","已抛单至MQ未返回 "),
	JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_SUC("2","已抛单至MQ已返回-处理成功"),
	JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_FAIL("3","已抛单至MQ已返回-处理失败"),
	
	ORDER_NOT_DELETE_STATUS("0","未删除状态"),
	ORDER_DELETE_STATUS("1","删除状态"),
	ORDER_THROUGH_DELETE_STATUS("2","彻底删除状态"),
	ORDER_RESTORE_DELETE_STATUS("0","删除订单还原状态"),
	
	ORDER_FROM_MALL("1","商城开单"),
	ORDER_FROM_VMS("2","VMS开单"),
	ORDER_FROM_ERP_VMS("4","VMS开单-(erp五合一专用)"),
	ORDER_FROM_PRESALE("5","预售"),
	
	ORDER_RELEASE_STOCK("1","创建订单锁定商品库存-失败时候需要释放商品库存"),
	ORDER_NOT_RELEASE_STOCK("0","创建订单不锁定商品库存-失败时候不需要释放商品库存"),
	
	JD_PRE_SATOCK_SUCCESS("1","预占订单成功"),
	
	JD_SURE_SATOCK_SUCCESS("2","确认订单成功"),
	
	IS_SKILL_ORDER("1","是秒杀订单"),
	NOT_SKILL_ORDER("0","不是秒杀订单"),
	
	USE_COUPON("1","使用了优惠券"),
	NOT_USE_COUPON("0","没有使用优惠券"),
	
	GOODS_CENTER_PRICE_TYPE_GRADE("3","等级价"),
	BUYER_GRADE_VIP("6","VIP会员"),
	GOODS_PRICE_TYPE_VIP("5","VIP价"),
	
	ORDER_ERROR_STATUS_JD_FAIL("908","商品+订单确失败"),
	
	ORDER_DELIVERY_TYPE_SINCE("2","自提"),
	COUPON_TYPE_SKILL("3","秒杀"),
	
	IS_LIMITED_TIME_PURCHASE("1","是限时购商品"),
	HAS_LIMITED_TIME_PURCHASE("1","含有限时购商品"),
	DOWN_STATUS_SUCCESS("1","成功"),
	DOWN_STATUS_FAIL("2","失败");
	
    // 成员变量
    private String code;
    private String Msg;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private OrderStatusEnum(String code, String Msg) {
        this.code = code;
        this.Msg = Msg;
    }

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