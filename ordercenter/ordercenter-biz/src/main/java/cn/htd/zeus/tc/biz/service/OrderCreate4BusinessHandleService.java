package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;

public interface OrderCreate4BusinessHandleService {
	
	/**
	 * 1:查询外部供应商下的商品是否是限时购商品，如果是就在订单行维度赋值promotionId、promotionType和
	 *   isLimitedTimePurchase
	 * 2:校验买家和买家不能是同一个账号
	 * @param orderCreateInfoReqDTO
	 */
	public void handleLimitedTimePurchaseSkuCode(OrderCreateInfoReqDTO orderCreateInfoReqDTO);
}
