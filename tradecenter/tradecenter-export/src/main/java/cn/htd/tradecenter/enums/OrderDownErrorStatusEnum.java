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

package cn.htd.tradecenter.enums;

/**
 * 运营系统订单下行异常状态枚举
 * 
 * @author jiangkun
 */
public enum OrderDownErrorStatusEnum {

	POST_STRIKEA_ERROR("收付款接口回调结果异常", "10"), ERP_DOWN_ERROR("五合一接口回调结果异常", "20"), PRE_SALES_ERROR("预售接口回调结果异常", "30");

	private String name;
	private String value;

	OrderDownErrorStatusEnum(String name, String value) {
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
	public static OrderDownErrorStatusEnum getEnum(String value) {
		for (OrderDownErrorStatusEnum enums : OrderDownErrorStatusEnum.values()) {
			if (value.equals(enums.getValue())) {
				return enums;
			}
		}
		return null;
	}
}
