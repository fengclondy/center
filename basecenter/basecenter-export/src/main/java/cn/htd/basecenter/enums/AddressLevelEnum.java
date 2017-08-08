/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	AddressLevelEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 地址等级枚举  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * @author jiangkun
 *
 */
public enum AddressLevelEnum {

	PROVINCE("省", 1), CITY("市", 2), DISTRICT("区、", 3), TOWN("镇", 4);

	private String name;
	private int value;

	AddressLevelEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	// 获取枚举value对应名字方法
	public static String getAddressLevelName(int value) {
		for (AddressLevelEnum addressLevelEnum : AddressLevelEnum.values()) {
			if (value == addressLevelEnum.getValue()) {
				return addressLevelEnum.name;
			}
		}
		return "";
	}
}
