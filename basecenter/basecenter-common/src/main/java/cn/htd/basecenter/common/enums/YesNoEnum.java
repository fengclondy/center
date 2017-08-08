/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	DeleteFlagEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 删除标记枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.common.enums;

/**
 * 删除标记枚举
 * 
 * @author jiangkun
 */
public enum YesNoEnum {

	NO("否", 0), YES("是", 1);

	private String name;
	private int value;

	YesNoEnum(String name, int value) {
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
	public static String getYesNoName(int value) {
		for (YesNoEnum yesNoEnum : YesNoEnum.values()) {
			if (value == yesNoEnum.getValue()) {
				return yesNoEnum.name;
			}
		}
		return "";
	}
}
