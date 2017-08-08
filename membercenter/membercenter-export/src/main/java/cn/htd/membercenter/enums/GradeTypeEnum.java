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

package cn.htd.membercenter.enums;

/**
 * GradeTypeEnum
 * 
 * @author jiangkun
 */
public enum GradeTypeEnum {
	// <value code="ORDER" /><!--商城购买 -->
	// <value code="WITHDRAW" /><!--商城退款 -->
	// <value code="HND" /><!--汇农贷 -->
	// <value code="HXD" /><!--汇信贷 -->
	// <value code="JSYH" /><!--江苏银行 -->
	// <value code="HZF" /><!--汇致富 -->
	// <value code="UPVIP" /><!--升级为VIP -->
	ORDER("商城购买", "1"), WITHDRAW("商城退款", "2"), HND("汇农贷", "3"), HXD("汇信贷", "4"), JSYH("江苏银行", "5"), HZF("汇致富",
			"6"), UPVIP("升级为VIP", "7");

	private String name;
	private String value;

	GradeTypeEnum(String name, String value) {
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
	public static String getGradeTyprName(String value) {
		for (GradeTypeEnum gradeEnum : GradeTypeEnum.values()) {
			if (gradeEnum.getValue().equals(value)) {
				return gradeEnum.name;
			}
		}
		return "";
	}
}
