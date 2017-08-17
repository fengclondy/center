package cn.htd.zeus.tc.biz.rao;

import java.util.List;

import cn.htd.goodscenter.dto.mall.MallSkuInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuOutDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockOutDTO;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.vip.VipItemEntryInfoDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;

public interface GoodsCenterRAO {
	/*
	 * 从商品中心查询商品基本信息
	 * 
	 * @param isHasDevRelation
	 * 
	 * @param orderItemTemp
	 * 
	 * @return ExecuteResult<List<SkuInfo4CartOutDTO>>
	 */
	public OtherCenterResDTO<MallSkuWithStockOutDTO> queryMallItemDetailWithStock(
			OrderCreateItemListInfoReqDTO orderItemTemp, String site, String messageId);

	/*
	 * 调用商品中心判断是否超出配送范围
	 */
	public OtherCenterResDTO<String> isRecevieAddressInSaleRange(
			OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateItemListInfoReqDTO orderItemTemp, String messageId);

	/*
	 * 调用商品中心批量锁定库存接口
	 */
	public OtherCenterResDTO<String> batchReserveStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId);

	/*
	 * 调商品中心批量释放库存
	 */
	public OtherCenterResDTO<String> batchReleaseStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId);

	/*
	 * 调商品中心批量释放库存-只是用messageId幂等性
	 */
	// public OtherCenterResDTO<String> comboChangeStock(
	// List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId);

	/*
	 * 调商品中心批量扣减库存
	 */
	public OtherCenterResDTO<String> batchReduceStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId);

	/*
	 * 调商品中心批量回滚库存
	 */
	public OtherCenterResDTO<String> batchRollbackStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId);

	/*
	 * 查询商品详细信息列表
	 */
	public OtherCenterResDTO<List<MallSkuWithStockOutDTO>> getMallItemInfoList(
			List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList, String messageId);

	/*
	 * 查询商品详细信息列表
	 */
	public OtherCenterResDTO<Boolean> canProductPlusSaleBySeller(Long sellerId,
			String productChannel, Long categoryId, Long brandId, String messageId);

	/*
	 * 查询vip套餐商品信息List
	 */
	public OtherCenterResDTO<List<VipItemEntryInfoDTO>> queryVipItemList(String vipSkuCode,
			String messageId);

	/*
	 * 根据商品skucode查询商品信息(供汇掌柜创建订单使用)
	 */
	public OtherCenterResDTO<List<MallSkuOutDTO>> queryCartItemList(
			List<MallSkuInDTO> mallSkuInDTOList, String messageId);
}
