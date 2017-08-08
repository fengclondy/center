package cn.htd.tradecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.TradeOrderQueryInForSellerDTO;
import cn.htd.tradecenter.dto.TradeOrderQueryOutForSellerDTO;
import cn.htd.tradecenter.dto.TradeOrderStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;

public interface TradeOrderExportService {

	/**
	 * 卖家中心订单以及订单行查询
	 * 
	 * @param tradeOrderQueryInForSellerDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeOrderQueryOutForSellerDTO>> queryTradeOrderForSeller(
			TradeOrderQueryInForSellerDTO tradeOrderQueryInForSellerDTO, Pager<TradeOrderQueryInForSellerDTO> pager);

	public ExecuteResult<Long> queryOrderQty(TradeOrderQueryInForSellerDTO inDTO);

	public ExecuteResult<Long>  modifyOrderStatus(TradeOrderStatusHistoryDTO statusHistoryDTO);

	public ExecuteResult<TradeOrdersDTO> getOrderById(String orderNo);

	public ExecuteResult<String> changePrice(TradeOrdersDTO tradeOrderDTO);

	/**
	 * gen ju ding dan hao xiu gai fa huo xin xi
	 * @param tradeOrdersDTO
	 * @return
     */
	public ExecuteResult<String> confimDeliver (TradeOrdersDTO tradeOrdersDTO);

    public ExecuteResult<String> checkPOPOrders(String orderNo , String memberCode , String chargeAmount);
}
