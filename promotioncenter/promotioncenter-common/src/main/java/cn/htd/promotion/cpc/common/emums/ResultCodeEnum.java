package cn.htd.promotion.cpc.common.emums;

public enum ResultCodeEnum {
	//订单状态
	SUCCESS("00000","成功！"),
	ERROR("99999","异常");
	
    private String code;
    private String Msg;
	
	private ResultCodeEnum(String code, String msg) {
		this.code = code;
		Msg = msg;
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
