package cn.htd.zeus.tc.common.enums;

public enum PayTypeEnum {

	//支付方式
	DEDUCT_DEPOSIT("DEDUCT_DEPOSIT","代扣充值"),
	NET_DEPOSIT("NET_DEPOSIT", "网银充值"),
	WITHDRAW("WITHDRAW", "提现"),
	NET_DEPOSIT_BACK("NET_DEPOSIT_BACK", "网银充退"),
	DEDUCT_DEPOSIT_BACK("DEDUCT_DEPOSIT_BACK", "代扣充退"),
	BALANCE_PAY("BALANCE_PAY", "余额支付"),
	DEDUCT_PAY("DEDUCT_PAY", "代扣支付"),
	NET_DEPOSIT_PAY("NET_DEPOSIT_PAY", "网银支付"),
	POS_PAY("POS_PAY", "pos支付"),
	NOT_ORDER_PAY("NOT_ORDER_PAY", "无订单支付"),
	ERP_PAY("ERP_PAY", "ERP支付"),
	WCHAT_PUBLIC_PAY("WCHAT_PUBLIC_PAY", "微信公众号支付"),
	WCHAT_APP_PAY("WCHAT_APP_PAY", "微信APP支付"),
	WCHAT_SWING_CARD_PAY("WCHAT_SWING_CARD_PAY", "微信刷卡支付"),
	WCHAT_SCAN_CODE_PAY("WCHAT_SCAN_CODE_PAY", "微信扫码支付"),
	ALIPAY_APP_PAY("ALIPAY_APP_PAY", "支付宝APP支付"),
	ALIPAY_PAGE_PAY("ALIPAY_PAGE_PAY", "支付宝H5支付"),
	ALIPAY_PC_PAGE_PAY("ALIPAY_PC_PAGE_PAY", "支付宝PC页面支付"),
	ALIPAY_SWING_CARD_PAY("ALIPAY_SWING_CARD_PAY", "支付宝刷卡支付"),
	ALIPAY_SCAN_CODE_PAY("ALIPAY_SCAN_CODE_PAY", "支付宝扫码支付"),
	WCHAT_ALI_PAY_BACK("WCHAT_ALI_PAY_BACK", "微信,支付宝支付退款"),
	PROFIT_CHARGE("PROFIT_CHARGE", "清分收费"),
	PROFIT("PROFIT", "清分"),
	OFFLINE_DEPOSIT("OFFLINE_DEPOSIT", "离线充值"),
	OFFLINE_WITHDRAW("OFFLINE_WITHDRAW", "离线提现"),

	//支付状态
	INIT("INIT", "初始状态"),
	PROCESSING("PROCESSING", "支付中"),
	SUCCESS("SUCCESS", "交易成功"),
	FAIL("FAIL", "交易失败"),
	CANCEL("CANCEL", "交易撤销"),
	CLOSE("CLOSE", "交易关闭"),
	PAY_RESULT_STATUS_SUCC("2","已下行MQ已返回(返回处理成功)"),
	PAY_RESULT_STATUS_FAIL("3","已下行MQ已返回(返回处理失败)");

	private PayTypeEnum(String code,String Msg){
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
