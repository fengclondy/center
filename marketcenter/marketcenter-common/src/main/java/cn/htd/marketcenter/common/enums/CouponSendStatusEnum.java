/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TimelimitedStatusEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 秒杀活动状态枚举
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.marketcenter.common.enums;

/**
 * 优惠券发送状态枚举
 * 
 * @author jiangkun
 */
public enum CouponSendStatusEnum {

	NO_SEND("NO_SEND", "未发送"), SENT("SENT", "已发送"), FAILED("FAIL", "发送失败"), GIVE_UP("GIVE_UP",
			"优惠券已过期放弃发送"), ERROR("ERROR", "优惠券数据异常");

	private String code;
	private String desc;

	CouponSendStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getCode() {
		return this.code;
	}

	// 获取枚举value对应名字方法
	public static String getDescByCode(String code) {
		for (CouponSendStatusEnum enumObj : CouponSendStatusEnum.values()) {
			if (code.equals(enumObj.getCode())) {
				return enumObj.desc;
			}
		}
		return "";
	}
}
