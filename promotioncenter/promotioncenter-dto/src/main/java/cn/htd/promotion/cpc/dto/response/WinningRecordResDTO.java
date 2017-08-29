package cn.htd.promotion.cpc.dto.response;

import java.util.List;

public class WinningRecordResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5426197626019076503L;

	/**
	 * 买家中奖记录集合
	 */
	private List<WinningRecordListResDTO> winningRecordList;

	public List<WinningRecordListResDTO> getWinningRecordList() {
		return winningRecordList;
	}

	public void setWinningRecordList(List<WinningRecordListResDTO> winningRecordList) {
		this.winningRecordList = winningRecordList;
	}
	
}
