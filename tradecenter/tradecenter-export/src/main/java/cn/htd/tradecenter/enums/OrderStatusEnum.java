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
 * 运营系统订单行状态枚举
 * 
 * @author jiangkun
 */
public enum OrderStatusEnum {

	VERIFY_PENDING("待审核", DictionaryConst.OPT_ORDER_STATUS_VERIFY_PENDING, "10"),
	WAIT_CONFIRM("待确认", DictionaryConst.OPT_ORDER_STATUS_WAIT_CONFIRM, "19"),
	WAIT_PAY("待支付", DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY, "20"),
	VERIFY_WAIT_PAY("审核通过待支付", DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY, "21"),
	PAID("已支付", DictionaryConst.OPT_ORDER_STATUS_PAID, "30"),
	PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST("已支付收付款下行成功待拆单",DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_SUCCESS,"312"),
	WAIT_DELIVERY("待发货", DictionaryConst.OPT_ORDER_STATUS_WAIT_DELIVERY, "40"),
	CONSIGNED("已发货", DictionaryConst.OPT_ORDER_STATUS_CONSIGNED, "50"),
	RECEIVED("已完成", DictionaryConst.OPT_ORDER_STATUS_RECEIVED, "60"),
	VERIFY_REFUSE("审核拒绝", DictionaryConst.OPT_ORDER_STATUS_VERIFY_REFUSE, "81"),
	CLOSE("已关闭", DictionaryConst.OPT_ORDER_STATUS_CLOSE, "82"),
	CANCEL("已取消", DictionaryConst.OPT_ORDER_STATUS_CANCEL, "83"),
	RETURN("已退货", DictionaryConst.OPT_ORDER_STATUS_RETURN, "84"),
	ERROR("订单异常", DictionaryConst.OPT_ORDER_STATUS_ERROR, "90");

	private String name;
	private String code;
	private String value;

	OrderStatusEnum(String name, String code, String value) {
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
	public static OrderStatusEnum getEnum(String value) {
		for (OrderStatusEnum enums : OrderStatusEnum.values()) {
			if (value.equals(enums.getValue())) {
				return enums;
			}
		}
		return null;
	}
}
