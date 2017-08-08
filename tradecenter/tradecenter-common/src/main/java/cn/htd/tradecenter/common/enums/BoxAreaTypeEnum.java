/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	BoxAreaTypeEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 包厢区域标记枚举
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.tradecenter.common.enums;

/**
 * 包厢区域标记枚举
 * 
 * @author jiangkun
 */
public enum BoxAreaTypeEnum {

	BOX("包厢", "1"), AREA("区域", "2");

	private String name;
	private String value;

	BoxAreaTypeEnum(String name, String value) {
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
		for (BoxAreaTypeEnum boxAreaTypeEnum : BoxAreaTypeEnum.values()) {
			if (value.equals(boxAreaTypeEnum.getValue())) {
				return boxAreaTypeEnum.name;
			}
		}
		return "";
	}
}
