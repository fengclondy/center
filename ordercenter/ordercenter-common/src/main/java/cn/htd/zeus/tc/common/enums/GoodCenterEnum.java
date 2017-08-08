package cn.htd.zeus.tc.common.enums;

public enum GoodCenterEnum {

	//商品中心登录状态
	IS_LOGIN("1","已登录"),
	INTERNAL_SUPPLIER("10", "内部供应商"),
	EXTERNAL_SUPPLIER("20", "外部供应商"),
	EXTERNAL_CHANNELS("30","外接渠道商品"),// 30^：外接渠道商品
	JD_SUPPLIER("3010","京东商品"),// 30^：外接渠道商品

	//是否vip套餐商品
	IS_VIP_GOODS("1","vip商品"),
	NOT_VIP_GOODS("0","非vip商品");

	private GoodCenterEnum(String code,String Msg){
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
