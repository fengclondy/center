package cn.htd.zeus.tc.biz.service;

import java.util.List;

import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderFreightInfoDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;

public interface OrderFreightInfoService {

	/**
	 * 计算订单商品运费价格 hzy专用
	 * 
	 * @param itemList
	 */
	public void calculateOrderItemFeight(List<OrderSellerInfoDTO> sellerList, String citySiteCode);

	/**
	 * 计算订单商品运费价格--创建订单专用 张丁专用
	 * 
	 * @param itemList
	 */
	public void calculateOrderItemFeight4CreateOrder(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode);

	/**
	 * 计算订单商品运费价格--超级老板秒杀专用
	 * 
	 * @param itemList
	 */
	public OrderFreightInfoDTO calculateOrderItemFeight4seckill(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode);

	/**
	 * 校验计算运费商品信息以及城市编码信息
	 * 
	 * @param orderItemList
	 * @param citySiteCode
	 * @return
	 */
	public OrderFreightInfoDTO validateOrderItemList(
			List<OrderCreateItemListInfoReqDTO> orderItemList, String citySiteCode);
}
