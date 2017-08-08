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
 * 秒杀活动状态枚举
 * 
 * @author jiangkun
 */
public enum TimelimitedStatusEnum {

	PROCESSING("processing", 1), NO_START("noStart", 2), CLEAR("clear", 3), ENDED("ended", 4);

	private String name;
	private int value;

	TimelimitedStatusEnum(String name, int value) {
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
	public static String getName(int value) {
		for (TimelimitedStatusEnum enumObj : TimelimitedStatusEnum.values()) {
			if (value == enumObj.getValue()) {
				return enumObj.name;
			}
		}
		return "";
	}
}
