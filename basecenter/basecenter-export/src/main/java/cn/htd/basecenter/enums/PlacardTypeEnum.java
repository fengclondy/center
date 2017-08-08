/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	PlacardTypeEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月23日
 * Description: 公告类型枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月23日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * @author jiangkun
 *
 */
public enum PlacardTypeEnum {

	ALL("全部", "all"), PLATFORM("平台", "1"), SELLER("商家", "2");
	private String name;
	private String value;

	PlacardTypeEnum(String name, String value) {
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
	public static String getPlacardTypeName(String value) {
		for (PlacardTypeEnum placardTypeEnum : PlacardTypeEnum.values()) {
			if (placardTypeEnum.getValue().equals(value)) {
				return placardTypeEnum.name;
			}
		}
		return "";
	}
}
