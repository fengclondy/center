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

import cn.htd.common.constant.DictionaryConst;

/**
 * 运营系统商品+订单行状态枚举
 * 
 * @author jiangkun
 */
public enum OuterChannelOrderStatusEnum {

	PRODUCT_PLUS_OCCUPY("预占订单成功", DictionaryConst.OPT_PRODUCT_PLUS_ORDER_STATUS_OCCUPY, "1"),
	PRODUCT_PLUS_CONFIRM("确认订单成功", DictionaryConst.OPT_PRODUCT_PLUS_ORDER_STATUS_CONFIRM, "2"),
	PRODUCT_PLUS_CONFIRM_ERROR("确认订单失败", DictionaryConst.OPT_ORDER_STATUS_PRODUCT_PLUS_CONFIRM_ERROR, "908");

	private String name;
	private String code;
	private String value;

	OuterChannelOrderStatusEnum(String name, String code, String value) {
		this.name = name;
		this.code = code;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	// 获取枚举value对应名字方法
	public static OuterChannelOrderStatusEnum getEnum(String value) {
		for (OuterChannelOrderStatusEnum enums : OuterChannelOrderStatusEnum.values()) {
			if (value.equals(enums.getValue())) {
				return enums;
			}
		}
		return null;
	}
}
