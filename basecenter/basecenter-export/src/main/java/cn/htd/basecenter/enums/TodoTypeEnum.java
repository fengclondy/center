/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TodoTypeEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 待办事项类型枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * 待办事项类型枚举
 * 
 * @author jiangkun
 */
public enum TodoTypeEnum {

	PASSWORD_RECOVERY("密码找回审核", "1"),
	PHONE_CHANGE("手机号更改审核", "2"),
	BUYER_REGIST("会员注册审核", "10"),
	BUYER_MODIFY("会员信息修改审核", "11"),
	NONE_BUYER_REGIST("非会员注册审核", "12"),
	NONE2BUYER_TRANSFORM("非会员转会员审核", "13"),
	BELONG_RELATION_RELEASE("解除归属关系审核", "14"),
	BUSINESS_RELATION_VERIFY("待审核包厢", "15"),
	ITEM_VERIFY("供应商商品信息审核", "30"),
	ITEM_PUBLISH("待上架商品", "31"),
	ORDER_VERIFY("待审核订单", "40"),
	ORDER_PAY("待支付订单", "41"),
	ORDER_DELIVERY("待发货订单", "42"),
	ORDER_CONSIGNED("已发货订单", "43"),
	SETTLE_CONFIRM("待确认结算单", "50");

	private String name;
	private String value;

	TodoTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	// 获取枚举value对应名字方法
	public static String getErpStatusName(String value) {
		for (TodoTypeEnum todoTypeEnum : TodoTypeEnum.values()) {
			if (todoTypeEnum.getValue().equals(value)) {
				return todoTypeEnum.name;
			}
		}
		return "";
	}
}
