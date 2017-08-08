/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	DeleteFlagEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 公告状态枚举
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * 公告状态枚举
 * 
 * @author jiangkun
 */
public enum PlacardStatusEnum {

	PENDING("待发送", "1"), SENDING("发送中", "2"), SENT("已发送", "3"), EXPIRED("已失效", "4"), DELETED("已删除", "9");

	private String name;
	private String value;

	PlacardStatusEnum(String name, String value) {
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
	public static String getPlacardStatusName(String value) {
		for (PlacardStatusEnum statusEnum : PlacardStatusEnum.values()) {
			if (statusEnum.getValue().equals(value)) {
				return statusEnum.name;
			}
		}
		return "";
	}
}
