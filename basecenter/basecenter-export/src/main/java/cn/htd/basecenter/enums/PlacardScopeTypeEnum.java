/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	PlacardScopeTypeEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月23日
 * Description: 公告范围类型枚举
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月23日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * @author jiangkun
 *
 */
public enum PlacardScopeTypeEnum {

	INNER_SELLER("平台供应商", "1"), OUTER_SELLER("外部供应商", "2"), ALL_BUYER("全部会员", "3"), PART_BUYER("部分会员", "4");

	private String name;
	private String value;

	PlacardScopeTypeEnum(String name, String value) {
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
	public static String getPlacardScopeTypeName(String value) {
		for (PlacardScopeTypeEnum placardScopeTypeEnum : PlacardScopeTypeEnum.values()) {
			if (placardScopeTypeEnum.getValue().equals(value)) {
				return placardScopeTypeEnum.name;
			}
		}
		return "";
	}
}
