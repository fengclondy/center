/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	ErpStatusEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: ERP状态枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.enums;

/**
 * 删除标记枚举
 * 
 * @author jiangkun
 */
public enum ErpStatusEnum {

	PENDING("待下行", "1"),
//	DOING("处理中", "2"), // TODO : 为了不影响数据库已有的状态，所有不修改数字。
    DOWNING("下行中", "3"), SUCCESS("成功", "4"), FAILURE("失败", "9");

	private String name;
	private String value;

	ErpStatusEnum(String name, String value) {
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
		for (ErpStatusEnum erpStatusEnum : ErpStatusEnum.values()) {
			if (erpStatusEnum.getValue().equals(value)) {
				return erpStatusEnum.name;
			}
		}
		return "";
	}
}
