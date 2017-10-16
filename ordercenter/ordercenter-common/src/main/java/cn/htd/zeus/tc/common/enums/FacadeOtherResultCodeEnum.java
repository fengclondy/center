/**
 *
 */
package cn.htd.zeus.tc.common.enums;

/*
 * 该类用来包装其他中心的返回码-仅供内部使用,不返回到外部
 * @author 张丁
 */
public enum FacadeOtherResultCodeEnum {

	//状态
	SUCCESS("00000","成功！"),
	/*
	 * 会员中心返回开始
	 */
	MEMBERCENTER_QUERY_NOT_RESULT("13999","从会员中心没查询到数据"),
	MEMBERCENTER_QUERY_NOT_JD_ADRESS("13888","从会员中心没有查到卖家的商品+地址"),
	MEMBERCENTER_QUERY_BUYERGRADE_AND_MEMBERGROUPID_NOT_RESULT("14000","查询会员中心根据卖家code和买家code没有查到数据"),
	/*
	 * 会员中心返回结束
	 */
	/*
	 * 商品中心返回开始
	 */
	//GOODSCenter_QUery_SkuInfo_NOT_RESULT("","查询商品中心根据skuCode没有查到商品信息"),//待商品中心增加
	/*
	 * 商品中心返回结束
	 */
	ERROR("99999","未知异常！");

	private FacadeOtherResultCodeEnum(String code, String msg) {
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
