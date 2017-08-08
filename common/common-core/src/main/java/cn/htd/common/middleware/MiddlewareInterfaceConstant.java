package cn.htd.common.middleware;

import cn.htd.common.util.SpringApplicationContextHolder;

public class MiddlewareInterfaceConstant {
	/**
	 * 中台APPID
	 */
	public static final String MIDDLE_PLATFORM_APP_ID="zhongtai";

	public static final String MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS="1";
	/**
	 * 获取TOKEN的接口
	 */
	public static final String MIDDLEWARE_GET_ACCESS_TOKEN_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/token/";
	/**
	 * 查询实际库存接口
	 */
	public static final String MIDDLEWARE_GET_ITEM_STOCK_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/batchFindStock";
	
	/**
	 * 拆单前检查
	 * 
	 */
	public static final String MIDDLEWARE_CHECK_BEFORE_SPLIT_ORDER_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/order/findBeforeSplitOrder";

	/**
	 * 查询特殊商品接口
	 *
	 */
	public static final String MIDDLEWARE_GET_SPECIAL_ITEM_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/querySupplieInventory";
	
	/**
	 * 查询协议商品接口
	 *
	 */
	public static final String MIDDLEWARE_GET_SPECIAL_AGGREE_ITEM_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/getProcurementAgreement";
	/**
	 * 检查ERP一级类目与五级类目关系接口
	 */
	public static final String MIDDLEWARE_ChECK_ERP_FIRST_AND_ERP_FIVE_CODE_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/businessBoolean";
	
	/**
	 * 商品关系上行接口
	 */
	public static final String MIDDLEWARE_INSERT_PROD_RELATION_CALLBACK_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/updateZTProductRelationshipCallback";
	
	/**
	 * 商品上行接口
	 */
	public static final String MIDDLEWARE_INSERT_PROD_CALLBACK_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/updateZTProductCallback";
	
	/**
	 * 查询商品分销限价
	 * 
	 */
	public static final String MIDDLEWARE_GET_PROD_PRICE_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-erp/product/findProductPrice";
	
	
	/**
	 * 查询京东商品价格
	 * 
	 */
	public static final String MIDDLEWARE_GET_JD_PRICE_URL=SpringApplicationContextHolder.getBean("middlewarePath")+"/middleware-jd/product/queryPrice";
	
	
}
