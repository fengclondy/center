package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.htd.zeus.tc.dto.OrderConsigAddressDTO;
import cn.htd.zeus.tc.dto.OrderInvoiceDTO;
import cn.htd.zeus.tc.dto.OrderPromotionInfoDTO;
import cn.htd.zeus.tc.dto.OrderSeckillInfoDTO;
import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;

public class OrderSettleMentResDTO extends GenricResDTO implements Serializable {

	private static final long serialVersionUID = 6440255804804055967L;

	public List<OrderSellerInfoDTO> sellerList;

	public List<OrderPromotionInfoDTO> couponUsedList = new ArrayList<OrderPromotionInfoDTO>();

	public List<OrderPromotionInfoDTO> couponUnusedList = new ArrayList<OrderPromotionInfoDTO>();

	public OrderInvoiceDTO orderInvoiceDTO;

	public List<OrderConsigAddressDTO> consigAddressList = new ArrayList<OrderConsigAddressDTO>();

	// 是否VIP套餐商品:0 非vip套餐商品 1 vip套餐商品
	public Integer isVipItem;

	// 是否秒杀活动 1：是 0：不是
	public String isSeckill;

	// 秒杀活动信息
	public OrderSeckillInfoDTO orderSeckillInfoDTO;

	/**
	 * @return the sellerList
	 */
	public List<OrderSellerInfoDTO> getSellerList() {
		return sellerList;
	}

	/**
	 * @param sellerList
	 *            the sellerList to set
	 */
	public void setSellerList(List<OrderSellerInfoDTO> sellerList) {
		this.sellerList = sellerList;
	}

	/**
	 * @return the couponUsedList
	 */
	public List<OrderPromotionInfoDTO> getCouponUsedList() {
		return couponUsedList;
	}

	/**
	 * @param couponUsedList
	 *            the couponUsedList to set
	 */
	public void setCouponUsedList(List<OrderPromotionInfoDTO> couponUsedList) {
		this.couponUsedList = couponUsedList;
	}

	/**
	 * @return the couponUnusedList
	 */
	public List<OrderPromotionInfoDTO> getCouponUnusedList() {
		return couponUnusedList;
	}

	/**
	 * @param couponUnusedList
	 *            the couponUnusedList to set
	 */
	public void setCouponUnusedList(List<OrderPromotionInfoDTO> couponUnusedList) {
		this.couponUnusedList = couponUnusedList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the orderInvoiceDTO
	 */
	public OrderInvoiceDTO getOrderInvoiceDTO() {
		return orderInvoiceDTO;
	}

	/**
	 * @param orderInvoiceDTO
	 *            the orderInvoiceDTO to set
	 */
	public void setOrderInvoiceDTO(OrderInvoiceDTO orderInvoiceDTO) {
		this.orderInvoiceDTO = orderInvoiceDTO;
	}

	/**
	 * @return the consigAddressList
	 */
	public List<OrderConsigAddressDTO> getConsigAddressList() {
		return consigAddressList;
	}

	/**
	 * @param consigAddressList
	 *            the consigAddressList to set
	 */
	public void setConsigAddressList(List<OrderConsigAddressDTO> consigAddressList) {
		this.consigAddressList = consigAddressList;
	}

	/**
	 * @return the isVipItem
	 */
	public Integer getIsVipItem() {
		return isVipItem;
	}

	/**
	 * @param isVipItem
	 *            the isVipItem to set
	 */
	public void setIsVipItem(Integer isVipItem) {
		this.isVipItem = isVipItem;
	}

	/**
	 * @return the isSeckill
	 */
	public String getIsSeckill() {
		return isSeckill;
	}

	/**
	 * @param isSeckill
	 *            the isSeckill to set
	 */
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}

	/**
	 * @return the orderSeckillInfoDTO
	 */
	public OrderSeckillInfoDTO getOrderSeckillInfoDTO() {
		return orderSeckillInfoDTO;
	}

	/**
	 * @param orderSeckillInfoDTO
	 *            the orderSeckillInfoDTO to set
	 */
	public void setOrderSeckillInfoDTO(OrderSeckillInfoDTO orderSeckillInfoDTO) {
		this.orderSeckillInfoDTO = orderSeckillInfoDTO;
	}

}
