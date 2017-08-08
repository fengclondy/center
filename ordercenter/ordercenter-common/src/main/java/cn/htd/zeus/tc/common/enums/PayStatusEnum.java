package cn.htd.zeus.tc.common.enums;

public enum PayStatusEnum {

	//支付方式
	ERP_PAY("1","余额帐支付"),
	BALANCE_PAY("2", "平台账户支付"),
	ONLINE_PAY("3", "在线支付"),
	POS_PAY("4", "POS机支付"),

	//支付状态
	NON_PAY("0", "待支付"),
	SUCCESS("1", "已支付"),
	FAIL("2", "支付失败"),
	PROCESSING("4", "处理中"),
	TIME_OUT("3", "支付异常"),
	
	//选择支付方式
	NOT_SUPPORT_ERP_BLANCE_PAY("0","不能走erp余额支付场景"),
	SUPPORT_ALL_BLANCE_PAY("1","可走所有支付场景");

	private PayStatusEnum(String code,String Msg){
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
