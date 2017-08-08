package cn.htd.tradecenter.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;

/**
 * Created by taolei on 17/2/14.
 */
public interface TradeSettlementDetailDAO  extends BaseDAO<TradeSettlementDetailDTO> {

	public List<TradeSettlementDetailDTO> queryTradeSettlementDetailsForPage(@Param("params") Map<String, Object> params, @Param("page") Pager<TradeSettlementDetailDTO> pager);
	
	/**
	 * 添加结算明细
	 * @param tradeSettlementDetailDTO
	 */
	void addTradeSettlementDetail(TradeSettlementDetailDTO tradeSettlementDetailDTO);

	public int countTradeSettlementDetails(@Param("params") Map<String, Object> params);
	
	public Map<String,Object> queryCountMoney(TradeSettlementWithdrawDTO dto);

}
