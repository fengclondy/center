package cn.htd.zeus.tc.common.middleware;

import cn.htd.zeus.tc.common.util.SysProperties;

public class MiddlewareInterfaceConstant {
	/**
	 * 中台APPID
	 */
	public static final String MIDDLE_PLATFORM_APP_ID="zhongtai";
	
	public static final String MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS="1";
	/**
	 * 获取TOKEN的接口
	 */
	public static final String MIDDLEWARE_GET_ACCESS_TOKEN_URL=SysProperties.getMiddlewarePath()+"/middleware-erp/token/pay";
	/**
	 * 查询实际库存接口
	 */
	public static final String MIDDLEWARE_GET_ITEM_STOCK_URL=SysProperties.getMiddlewarePath()+"/query/itemStock";
	
	public static final String MIDDLEWARE_CHECK_BEFORE_SPLIT_ORDER_URL=SysProperties.getMiddlewarePath()+"/query/beforeSplitOrder";
	
	/**
	 * 查询特殊商品接口
	 * 
	 */
	public static final String MIDDLEWARE_GET_SPECIAL_ITEM_URL=SysProperties.getMiddlewarePath()+"/query/specialItem";
	
}
