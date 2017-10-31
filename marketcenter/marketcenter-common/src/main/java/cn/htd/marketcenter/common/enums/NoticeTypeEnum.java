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

package cn.htd.marketcenter.common.enums;

/**
 * 促销活动提醒标记枚举
 * 
 * @author jiangkun
 */
public enum NoticeTypeEnum {

	NO("否", 0), POPUP("弹框提醒", 1), SMS("短信提醒", 2), POPUPSMS("弹框和短信提醒", 3);

	private String name;
	private int value;

	NoticeTypeEnum(String name, int value) {
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
		for (NoticeTypeEnum enumObj : NoticeTypeEnum.values()) {
			if (value == enumObj.getValue()) {
				return enumObj.name;
			}
		}
		return "";
	}
}
