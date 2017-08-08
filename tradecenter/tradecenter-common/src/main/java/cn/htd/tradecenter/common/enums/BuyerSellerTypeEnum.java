/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	DeleteFlagEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 会员商家标记枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.tradecenter.common.enums;

/**
 * 会员商家标记枚举
 * 
 * @author jiangkun
 */
public enum BuyerSellerTypeEnum {

	BUYER("会员", "1"), SELLER("商家", "2");

	private String name;
	private String value;

	BuyerSellerTypeEnum(String name, String value) {
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
	public static String getName(String value) {
		for (BuyerSellerTypeEnum buyerSellerTypeEnum : BuyerSellerTypeEnum.values()) {
			if (value.equals(buyerSellerTypeEnum.getValue())) {
				return buyerSellerTypeEnum.name;
			}
		}
		return "";
	}
}
