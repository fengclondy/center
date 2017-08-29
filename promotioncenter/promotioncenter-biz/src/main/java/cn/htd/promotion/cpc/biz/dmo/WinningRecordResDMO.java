package cn.htd.promotion.cpc.biz.dmo;

import java.util.List;

public class WinningRecordResDMO extends GenericDMO{
	/**
	 * 买家中奖记录集合
	 */
	private List<BuyerWinningRecordDMO> winningRecordList;

	public List<BuyerWinningRecordDMO> getWinningRecordList() {
		return winningRecordList;
	}

	public void setWinningRecordList(List<BuyerWinningRecordDMO> winningRecordList) {
		this.winningRecordList = winningRecordList;
	}
}
