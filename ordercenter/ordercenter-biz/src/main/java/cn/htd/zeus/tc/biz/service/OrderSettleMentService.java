package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

public interface OrderSettleMentService {

	/**
	 * 校验传入参数
	 * 
	 * @param skuList
	 * @param messageId
	 * @param memberId
	 * @return
	 */
	public boolean paramValidate(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);

	/**
	 * 组装商品信息
	 * 
	 * @param skuList
	 * @param messageId
	 * @return
	 */
	public void packageSkuInfo4goodsService(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);

	/**
	 * 查询优惠券信息
	 * 
	 * @param memberId
	 * @param messageId
	 * @return
	 */
	public void getCouponAvailableList(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);

	/**
	 * 查询会员发票信息
	 * 
	 * @param memberId
	 * @return
	 */
	public OrderSettleMentResDTO getInvoiceInfo(String memberId, String messageId);

	/**
	 * 查询会员收货地址信息
	 * 
	 * @param memberId
	 * @return
	 */
	public void getConsigAddressList(OrderSettleMentResDTO resDTO, String memberId,
			String messageId, String citySiteCode);

	/**
	 * 获取秒杀商品信息
	 * 
	 * @param messageId
	 * @return
	 */
	public void getSeckillInfo(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);

	/**
	 * 查询京东商品库存数量
	 * 
	 * @param outerSkuId
	 * @param sellerCode
	 * @param messageId
	 * @return
	 */
	public int queryJDproductStock(String outerSkuId, String sellerCode, String messageId);

	/**
	 * 查询京东商品库存数量
	 * 
	 * @param outerSkuId
	 * @param sellerCode
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<String> queryJDproductStock4xj(String outerSkuId, String sellerCode,
			String messageId);

	/**
	 * 锁定秒杀商品库存
	 * 
	 * @param orderSettleMentReqDTO
	 * @param resDTO
	 */
	public void reserveSeckillproductStock(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);

	/**
	 * 处理秒杀商品信息
	 * 
	 * @param orderSettleMentReqDTO
	 * @param resDTO
	 */
	public void packageSeckillInfo(OrderSettleMentReqDTO orderSettleMentReqDTO,
			OrderSettleMentResDTO resDTO);
}
