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
	 * 合同信息 <br>
	 */
	public List<ContractInfoDTO> contractInfoList;
	
	/**
	 * 未签订合同数量总量 <br>
	 */
	public int noSignContractInfoCount;
	
	
	/**
	 * 已签订合同数量总量 <br>
	 */
	public int alreadySignContractInfoCount;


	/** 
	 * get contractInfoList
	 * @return Returns the contractInfoList.<br> 
	 */
	public List<ContractInfoDTO> getContractInfoList() {
		return contractInfoList;
	}


	/** 
	 * set contractInfoList
	 * @param contractInfoList The contractInfoList to set. <br>
	 */
	public void setContractInfoList(List<ContractInfoDTO> contractInfoList) {
		this.contractInfoList = contractInfoList;
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
