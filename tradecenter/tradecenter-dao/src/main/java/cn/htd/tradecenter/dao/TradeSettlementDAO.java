package cn.htd.tradecenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;

public interface TradeSettlementDAO extends BaseDAO<TradeSettlementDAO>{

	public List<TradeSettlementDTO> queryTradeSettlementsForPage(@Param("params") Map<String, Object> params, @Param("page") Pager<TradeSettlementDTO> pager);
	
	public List<TradeSettlementDTO> queryTradeSettlementsByParams(@Param("params") Map<String, Object> params);
		
	public List<TradeSettlementConstDTO> getTradeSetConst(@Param("params") Map<String, Object> params);
	
	public int updateTradeSetStatus(@Param("params") Map<String, Object> params);
	
	public int countTradeSettlement(@Param("params") Map<String, Object> params);
	/**
	 * 生成结算单号
	 * @return
	 */
	String generateTheBillingNumber();
	
	/**
	 * 根据结算单号查询结算单
	 * @param settlementNo
	 * @return
	 */
	TradeSettlementDTO getTradeSettlementBySettlementNo(@Param("settlementNo") String settlementNo);
	
	/**
	 * 添加结算单
	 * @param tradeSettlementDTO
	 */
	void addTradeSettlement(TradeSettlementDTO tradeSettlementDTO);
	
	/**
	 * 根据条件更新结算单状态
	 * @param tradeSettlementDTO
	 */
	int updateTradeSetStatusByParams(TradeSettlementDTO tradeSettlementDTO);

	/**
	 * 查询子结算单的订单号
	 * @param settlementNo
	 * @return
	 */
	public List<String> getMerchantOrderNoList(TradeSettlementWithdrawDTO dto);
	/**
	 * 查询指定卖家的未结算的结算单数量
	 * @param params
	 * @return
	 */
	public int queryUnSettlementCount(@Param("productChannelCode") String productChannelCode);

	/**
	 * 查询指定卖家的未结算的结算单数量
	 * @param status 
	 * @param params
	 * @return
	 */

	public int queryUnSettlementCountBySellerCode(@Param("sellerCode")  String sellerCode,@Param("status") String status);


}
