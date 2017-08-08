package cn.htd.zeus.tc.common.enums;

public enum MiddleWareEnum {
	//分销单状态
	MIDDLE_WARE_DELIVERYED("0","已发货"),
	MIDDLE_WARE_REFUND("1","退款"),
	MIDDLE_WARE_RETURN_GOODS("2","退货"),

	MIDDLE_WARE_JDRESULT_STATUS_SUCCESS("2","京东处理成功"),
	MIDDLE_WARE_JDRESULT_STATUS_FAILED("3","京东处理失败"),

	MIDDLE_WARE_ERPRESULT_STATUS_SUCCESS("2","erp处理成功"),
	MIDDLE_WARE_ERPRESULT_STATUS_FAILED("3","erp处理失败"),

	//开票方式
	MIDDLE_WARE_JD_INVOICESTATE_WITH_GOODS("1","随货开票"),
	MIDDLE_WARE_JD_INVOICESTATE_PRE_ORDER("0","订单预借"),
	MIDDLE_WARE_JD_INVOICESTATE_CENTRALIZED_BILLING("2","集中开票"),

	MIDDLE_WARE_JD_INVOICETYPE_COMMON("1","普通发票"),
	MIDDLE_WARE_JD_INVOICETYPE_VAT("2","增值税发票"),
	
	ERP_INVOICETYPE_COMMON("0","普通发票"),
	ERP_INVOICETYPE_VAT("1","增值税发票"),

	MIDDLE_WARE_JD_SELECTEDINVOICETITLE_PERSONAL("4","个人"),
	MIDDLE_WARE_JD_SELECTEDINVOICETITLE_COMPANY("5","单位"),

	MIDDLE_WARE_JD_INVOICECONTENT_DETAIL("1","明细"),
	MIDDLE_WARE_JD_PAYMENTTYPE_ONLINE_PAY("4","在线支付（余额支付）"),

	MIDDLE_WARE_JD_USEBALANCE("1","使用余额"),
	MIDDLE_WARE_JD_SUBMITSTATE_PRE_STOCK("0","预占库存"),

	//中间件返回结果
	SUCCESS_ONE("1","成功"),

	RECHARGE_ORDER_TYPE("0","充值订单"),

	PAY_CODE_PLUS("72","收款方式代码(加)"),

	PAY_CODE_REDUCE("13","收款方式代码(减)"),

    NOT_LOCK_BALANCE_FLAG("0","不锁余额"),

	LOCK_BALANCE_FLAG("1","锁余额"),

	JD_BRAND_ID_PAY("other","京东品牌-支付传入-other"),

	JD_BRAND_ID_ERP("000028","品牌"),
	JD_CLASS_CODE_ERP("50","品类"),

	PRODUCTCODE_ERP("9001","商城"),
	PRODUCTCODE_ERP_EXTERNAL_SUPPLIER("0013","平台公司在POP交易"),
	PRODUCTCODE_RECHARGE("9001","充值"),
	SUPERBOSS_RECHARGE("9002","超级老板充值"),
	POS_RECHARGE("9003","POS机充值"),
	OPERATE_CODE("TN010000","操作员代码"),
	OPERATER_NAME("TINA","操作员名称"),
	SALES_MAN("TINA","业务员名称"),
	SALE_ASSISTANT_CODE("08068888","业务员代码"),
	MIDDLE_SUCCESS("1","中间件返回成功"),
	MIDDLE_FAIL("0","中间件返回失败"),
	
	OUTER_CHANNEL_STATUS_STORAGE("1","已入库"),
	OUTER_CHANNEL_STATUS_WAREHOUSE("2","已出库"),
	
	PRE_SALES_ORDER_MANUFACTURER("JD","厂家名称-JD");



	private MiddleWareEnum(String code,String Msg){
		this.code = code;
		this.Msg = Msg;
	}

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
