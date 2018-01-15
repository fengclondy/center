package cn.htd.membercenter.dao;

import cn.htd.membercenter.dto.ContractInfoDTO;
import cn.htd.membercenter.dto.ContractSignRemindInfoDTO;
import cn.htd.membercenter.dto.SaveContractInfoDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
 * <Description> 供货合同数据库服务    <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月18日 <br>
 */
public interface ContractDAO {

	/**
	 * Description: 查询有效的合同信息  <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode
	 * @return <br>
	 */ 
	public List<ContractInfoDTO> queryEffectiveContractByMemberCode(@Param("memberCode") String memberCode, @Param("vendorCodeList") List<String> vendorCodeList);

	/**
	 * Description: 查询该会员店是否需要提醒 <br>
	 *
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode
	 * @return <br>
	 */
	public Integer queryRemindFlagByMemberCode(@Param("memberCode") String memberCode);

	/**
	 * Description: 新增会员店提醒信息 <br>
	 *
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param contractSignRemindInfoDTO
	 * @return <br>
	 */
	public int insertContractSignRemindInfo(ContractSignRemindInfoDTO contractSignRemindInfoDTO);

	/**
	 * Description: 更新会员店提醒信息 <br>
	 *
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param contractSignRemindInfoDTO
	 * @return <br>
	 */
	public int updateContractSignRemindInfo(ContractSignRemindInfoDTO contractSignRemindInfoDTO);

	/**
	 * Description: 查询该供应商和一部分会员店下已签订的合同  <br>
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode
	 * @param vendorCodeList
	 * @return <br>
	 */
	public List<ContractInfoDTO> queryContractInfoListByMemberAndVendorCode(@Param("vendorCode") String  vendorCode, @Param("memberCodeList") List<String> memberCodeList);
	
	/**
	 * Description: 创建合同  <br>
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param saveContractInfo
	 * @return <br>
	 */
	public int insertContractInfo(@Param("saveContractInfoList") List<SaveContractInfoDTO> saveContractInfoList);
	
}
