package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class HTDUserAmountReturnDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status;

	private String errMsg;

	private List<BuyerFinanceHistoryDTO> returnList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the returnList
	 */
	public List<BuyerFinanceHistoryDTO> getReturnList() {
		return returnList;
	}

	/**
	 * @param returnList
	 *            the returnList to set
	 */
	public void setReturnList(List<BuyerFinanceHistoryDTO> returnList) {
		this.returnList = returnList;
	}

}
