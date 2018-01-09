package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.ContractInfoDTO;
import cn.htd.membercenter.dto.ContractListInfo;
import cn.htd.membercenter.dto.SaveContractInfoDTO;

/** 
 * <Description> 合同基础服务  <br> 
 *  
 * @author zhoutng<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月18日 <br>
 */
public interface ContractService {

	/**
	 * <Description> 合同列表  <br>
	 * @param memberCode 会员店编码
	 */
	public ExecuteResult<ContractListInfo> queryContractListByMemberCode(String memberCode, List<String> vendorCodeStrList, String contractStatus, Pager<String> pager);

	/**
	 * <Description> 提醒查询  <br>
	 * @param memberCode 会员店编码
	 * @param operationId 操作人id
	 * @param operationName 操作人名称
	 */
	public ExecuteResult<String> queryRemindFlag(String memberCode);
	
	/**
	 * Description: 修改会员店提醒状态为不提醒 <br> 
	 *  
	 * @author zhoutong <br>
	 * @taskId <br>
	 * @param memberCode 会员店编码
	 * @param operationId 操作人id
	 * @param operationName 操作人名称
	 * @return <br>
	 */ 
	public ExecuteResult<String> updateRemindFlagToNotNeed(String memberCode, Long operationId, String operationName);

	/**
	 * <Description> 查询签订合同信息  <br>
	 * @param memberCode 会员店编码
	 * @param vendorCode 供应商编码
	 */
	public ExecuteResult<List<ContractInfoDTO>> queryContractList(List<String> vendorCodeList, String memberCode);
	
	/**
	 * <Description> 批量查询某供应商下会员店的合同情况  <br>
	 * @param memberCodeList 会员店编码集合
	 * @param vendorCode 供应商编码
	 */
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoListByMemberAndVendorCode(String vendorCode, List<String> memberCodeList);
	
	/**
	 * <Description> 创建合同  <br>
	 * @param saveContractInfoDTO
	 */
	public ExecuteResult<String> saveContractInfo(List<SaveContractInfoDTO> saveContractInfoDTOList, String contractType);

}
