package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;

public interface TradeSettlementWithdrawDAO extends BaseDAO<TradeSettlementWithdrawDTO>{

	public List<TradeSettlementWithdrawDTO> queryTraSetWithdraw(TradeSettlementWithdrawDTO dto);
	
	public int updateTraSetWithdraw(TradeSettlementWithdrawDTO dto);
	
	public int addTraSetWithdraw(TradeSettlementWithdrawDTO dto);
	
	public List<TradeSettlementWithdrawDTO> queryUnSuccessTraSetWithdraw(TradeSettlementWithdrawDTO dto);
}
