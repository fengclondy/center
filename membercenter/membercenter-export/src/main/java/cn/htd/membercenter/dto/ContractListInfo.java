package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * <Description> 查询合同列表时使用 <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月23日 <br>
 */
public class ContractListInfo implements Serializable {

	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 未签订合同信息 <br>
	 */
	public List<ContractInfoDTO> noSignContractInfoList;
	
	/**
	 * 未签订合同数量 <br>
	 */
	public int noSignContractInfoCount;
	
	/**
	 * 已签订合同信息 <br>
	 */
	public List<ContractInfoDTO> alreadySignContractInfoList;
	
	/**
	 * 已签订合同数量 <br>
	 */
	public int alreadySignContractInfoCount;

	/** 
	 * get noSignContractInfoList
	 * @return Returns the noSignContractInfoList.<br> 
	 */
	public List<ContractInfoDTO> getNoSignContractInfoList() {
		return noSignContractInfoList;
	}

	/** 
	 * set noSignContractInfoList
	 * @param noSignContractInfoList The noSignContractInfoList to set. <br>
	 */
	public void setNoSignContractInfoList(List<ContractInfoDTO> noSignContractInfoList) {
		this.noSignContractInfoList = noSignContractInfoList;
	}

	/** 
	 * get noSignContractInfoCount
	 * @return Returns the noSignContractInfoCount.<br> 
	 */
	public int getNoSignContractInfoCount() {
		return noSignContractInfoCount;
	}

	/** 
	 * set noSignContractInfoCount
	 * @param noSignContractInfoCount The noSignContractInfoCount to set. <br>
	 */
	public void setNoSignContractInfoCount(int noSignContractInfoCount) {
		this.noSignContractInfoCount = noSignContractInfoCount;
	}

	/** 
	 * get alreadySignContractInfoList
	 * @return Returns the alreadySignContractInfoList.<br> 
	 */
	public List<ContractInfoDTO> getAlreadySignContractInfoList() {
		return alreadySignContractInfoList;
	}

	/** 
	 * set alreadySignContractInfoList
	 * @param alreadySignContractInfoList The alreadySignContractInfoList to set. <br>
	 */
	public void setAlreadySignContractInfoList(List<ContractInfoDTO> alreadySignContractInfoList) {
		this.alreadySignContractInfoList = alreadySignContractInfoList;
	}

	/** 
	 * get alreadySignContractInfoCount
	 * @return Returns the alreadySignContractInfoCount.<br> 
	 */
	public int getAlreadySignContractInfoCount() {
		return alreadySignContractInfoCount;
	}

	/** 
	 * set alreadySignContractInfoCount
	 * @param alreadySignContractInfoCount The alreadySignContractInfoCount to set. <br>
	 */
	public void setAlreadySignContractInfoCount(int alreadySignContractInfoCount) {
		this.alreadySignContractInfoCount = alreadySignContractInfoCount;
	}
	
	
}
