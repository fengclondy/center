package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * <Description> 首页提醒信息  <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年1月10日 <br>
 */
public class ContractRemindInfoDTO implements Serializable {

	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否提醒flag
	 */
	public String remindFlag;
	
	/**
	 * 需要签订的合同
	 */
	public List<ContractInfoDTO> needSignContractList;

	/** 
	 * get remindFlag
	 * @return Returns the remindFlag.<br> 
	 */
	public String getRemindFlag() {
		return remindFlag;
	}

	/** 
	 * set remindFlag
	 * @param remindFlag The remindFlag to set. <br>
	 */
	public void setRemindFlag(String remindFlag) {
		this.remindFlag = remindFlag;
	}

	/** 
	 * get needSignContractList
	 * @return Returns the needSignContractList.<br> 
	 */
	public List<ContractInfoDTO> getNeedSignContractList() {
		return needSignContractList;
	}

	/** 
	 * set needSignContractList
	 * @param needSignContractList The needSignContractList to set. <br>
	 */
	public void setNeedSignContractList(List<ContractInfoDTO> needSignContractList) {
		this.needSignContractList = needSignContractList;
	}
	
	
}
