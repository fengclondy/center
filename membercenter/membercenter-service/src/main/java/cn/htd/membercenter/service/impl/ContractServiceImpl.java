package cn.htd.membercenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.ContractDAO;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dto.ContractInfoDTO;
import cn.htd.membercenter.dto.ContractListInfo;
import cn.htd.membercenter.dto.ContractRemindInfoDTO;
import cn.htd.membercenter.dto.ContractSignRemindInfoDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberShipDTO;
import cn.htd.membercenter.dto.SaveContractInfoDTO;
import cn.htd.membercenter.service.ContractService;

/** 
 * <Description> 合同基础服务  <br> 
 *  
 * @author zhoutng<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月18日 <br>
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {
	
	private Logger logger = Logger.getLogger(ContractServiceImpl.class);
	
	/**
	 * 合同数据库服务 <br>
	 */
	@Autowired
	private ContractDAO contractDAO;
	
	@Autowired
	private MemberBaseDAO memberBaseDAO;
	
	/**
	 * 包厢关系数据库服务 <br>
	 */
	@Autowired
	private BoxRelationshipDAO boxRelationshipDAO;
	
	@Resource
	private RedisDB redisDB;

	/**
	 * Description: 查询合同列表 <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode
	 * @return <br>
	 */ 
	@Override
	public ExecuteResult<ContractListInfo> queryContractListByMemberCode(String memberCode, List<String> vendorCodeStrList,
			String contractStatus, Pager<String> pager) {
		logger.info("queryContractListBySellerId方法已进入");
		ExecuteResult<ContractListInfo> result = new ExecuteResult<ContractListInfo>();
		ContractListInfo contractListInfo = new ContractListInfo();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 查询失败");
			return result;
		}
		if (pager == null) {
			result.addErrorMessage("分页信息为空");
			return result;
		}
		
		try {
			//根据传入的会员店编码和有可能的供应商编码查询现有合同信息
			List<ContractInfoDTO> returnContractList = contractDAO.queryEffectiveContractByMemberCode(memberCode, vendorCodeStrList);
			List<ContractInfoDTO> returnContracInfotList = new ArrayList<ContractInfoDTO>();
			returnContracInfotList.addAll(returnContractList);
			//首先根据会员编码查询一圈包厢关系
			List<MemberShipDTO> memberShipList = boxRelationshipDAO.queryBoxRelationshipList(memberCode);
			
			//查询会员店信息
			MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
			memberBaseDTO.setMemberCode(memberCode);
			memberBaseDTO.setBuyerSellerType("1");
			MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(memberBaseDTO);
			if (null == vendorCodeStrList || vendorCodeStrList.isEmpty()) {
				
				//如果传入的供应商编码集合为空
				for (MemberShipDTO memberShip : memberShipList) {
					boolean checkExistsFlag = true;
					for (ContractInfoDTO contractInfoDTO : returnContractList) {
						if (memberShip.getMemberCode().equals(contractInfoDTO.getVendorCode())) {
							//如果相同的话置为false证明已经查询
							checkExistsFlag = false;
							break;
						}
					}
					if (checkExistsFlag) {
						//表示对应的供应商没有签订合同
						ContractInfoDTO noSigncontract = getContractInfoDTOByCode(memberBase, memberShip.getMemberCode());
						returnContracInfotList.add(noSigncontract);
					}
				}
			} else {
				//否则 查询传入的供应对应的合同信息
				for (String vendor : vendorCodeStrList) {
					boolean checkExistsFlag = true;
					for (ContractInfoDTO contractInfoDTO : returnContractList) {
						if (vendor.equals(contractInfoDTO.getVendorCode())) {
							//如果相同的话置为false证明已经查询到合同信息
							checkExistsFlag = false;
							break;
						}
					}
					if (checkExistsFlag) {
						//表示对应的供应商没有签订合同
						ContractInfoDTO noSigncontract = getContractInfoDTOByCode(memberBase, vendor);
						returnContracInfotList.add(noSigncontract);
					}
				}
			}
			Map<String, Object> map = resultHandle(memberBase,contractStatus, pager, returnContracInfotList);
			List<ContractInfoDTO> returnContractInfoList = (List<ContractInfoDTO>) map.get("returnContractInfoDTOList");
			contractListInfo.setContractInfoList(returnContractInfoList);
			contractListInfo.setAlreadySignContractInfoCount((Integer)map.get("signContractInfoCount"));
			contractListInfo.setNoSignContractInfoCount((Integer)map.get("noSignContractInfoCount"));
			result.setResult(contractListInfo);							  
		} catch (Exception e) {
			result.addErrorMessage("查询异常 异常信息 :" + e);
			logger.error("queryContractListBySellerId 方法查询合同列表异常 异常信息:" + e);
		}
		logger.info("queryContractListBySellerId方法已结束");
		return result;
	}
	
	/**
	 * Description: 用来获取一些合同的基本信息 <br> 
	 *  
	 * @author zhoutong <br>
	 * @taskId <br>
	 * @param memberBase
	 * @param vendorCode
	 * @return <br>
	 */ 
	public ContractInfoDTO getContractInfoDTOByCode(MemberBaseDTO memberBase, String vendorCode) throws Exception {
		MemberBaseDTO vendorBaseDTO = new MemberBaseDTO();
		vendorBaseDTO.setMemberCode(vendorCode);
		vendorBaseDTO.setBuyerSellerType("2");
		MemberBaseDTO vendorBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(vendorBaseDTO);
		if (null == vendorBase) {
			logger.error("查询不到对应的供应商信息 供应商编码=" + vendorCode);
			throw new Exception("查询不到对应的供应商信息");
		}
		ContractInfoDTO noSigncontract = new ContractInfoDTO();
		noSigncontract.setContractStatus("0");
		noSigncontract.setMemberCode(memberBase.getMemberCode());
		noSigncontract.setMemberArtificialPersonName(memberBase.getArtificialPersonName());
		noSigncontract.setMemberLocationAddr(memberBase.getLocationDetail());
		noSigncontract.setMemberName(memberBase.getCompanyName());
		noSigncontract.setVendorCode(vendorBase.getMemberCode());
		noSigncontract.setVendorName(vendorBase.getCompanyName());
		noSigncontract.setVendorArtificialPersonName(vendorBase.getArtificialPersonName());
		noSigncontract.setVendorLocationAddr(vendorBase.getLocationDetail());
		return noSigncontract;
	}
	
	
	/**
	 * Description: 合同列表查询结果处理 <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberShipList
	 * @param contractInfoDTOList
	 * @return <br>
	 */ 
	public Map<String, Object> resultHandle(MemberBaseDTO memberBase, String contractStatus, Pager pager,
			List<ContractInfoDTO> contractInfoDTOList) throws Exception {
		logger.info("resultHandle方法已进入 对查询合同列表结果进行处理");
		int page = pager.getPage();
		int pageSize = pager.getRows();
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 10) {
			pageSize = 10;
		}
		int startIndex = (page - 1) * pageSize;
		int endIndex = startIndex + pageSize - 1;
		Map<String, Object> map = new HashMap<String, Object>();
		List<ContractInfoDTO> returnContractInfoDTOList = new ArrayList<ContractInfoDTO>();
		List<ContractInfoDTO> signContractInfoDTOList = new ArrayList<ContractInfoDTO>();
		List<ContractInfoDTO> nosignContractInfoDTOList = new ArrayList<ContractInfoDTO>();
		for (ContractInfoDTO contractInfoDTO : contractInfoDTOList) {
			String contractStatusInfo = contractInfoDTO.getContractStatus();
			if (StringUtils.isEmpty(contractStatusInfo)) {
				//合同状态为空重置合同状态为0表示未签订
				contractInfoDTO.setContractStatus("0");
			}
			contractStatusInfo = contractInfoDTO.getContractStatus();
			if ("1".equals(contractStatusInfo)) {
				//合同已签订 收入signContractInfoDTOList
				signContractInfoDTOList.add(contractInfoDTO);
			} else if ("0".equals(contractInfoDTO.getContractStatus())) {
				//合同未签订 收入nosignContractInfoDTOList
				nosignContractInfoDTOList.add(contractInfoDTO);
			}
		}
		ContractInfoDTO noSignContractInfoDTO = new ContractInfoDTO();
		String vendorName = "";
		String vendorCode = "";
		String vendorLocationAddr = "";
		String vendorArtificialPersonName = "";
		for (int i = 0;i < nosignContractInfoDTOList.size(); i++) {
			ContractInfoDTO contractInfoDTO = nosignContractInfoDTOList.get(i);
			if (i == 0) {
				vendorName += contractInfoDTO.getVendorName();
				vendorCode += contractInfoDTO.getVendorCode();
				vendorLocationAddr += contractInfoDTO.getVendorLocationAddr();
				vendorArtificialPersonName += contractInfoDTO.getVendorArtificialPersonName();
			} else {
				vendorName += "," + contractInfoDTO.getVendorName();
				vendorCode += "," + contractInfoDTO.getVendorCode();
				vendorLocationAddr += "," + contractInfoDTO.getVendorLocationAddr();
				vendorArtificialPersonName += "," + contractInfoDTO.getVendorArtificialPersonName();
			}
		}
		noSignContractInfoDTO.setContractStatus("0");
		noSignContractInfoDTO.setVendorName(vendorName);
		noSignContractInfoDTO.setVendorCode(vendorCode);
		noSignContractInfoDTO.setVendorArtificialPersonName(vendorArtificialPersonName);
		noSignContractInfoDTO.setVendorLocationAddr(vendorLocationAddr);
		noSignContractInfoDTO.setMemberArtificialPersonName(memberBase.getArtificialPersonName());
		noSignContractInfoDTO.setMemberCode(memberBase.getMemberCode());
		noSignContractInfoDTO.setMemberLocationAddr(memberBase.getLocationDetail());
		noSignContractInfoDTO.setMemberName(memberBase.getCompanyName());
		if (("".equals(contractStatus) || null == contractStatus) && page == 1) {
			//未签订放进返回
			if (nosignContractInfoDTOList.size() > 0) {
				returnContractInfoDTOList.add(noSignContractInfoDTO);
			}
			if (page == 1 && !returnContractInfoDTOList.isEmpty()) {
				//第一页 且未签订的不为空 结尾需要-1
				endIndex -= 1;
			}
			for (int i = 0; i < signContractInfoDTOList.size(); i++) {
				if (i >= startIndex && i <= endIndex) {
					returnContractInfoDTOList.add(signContractInfoDTOList.get(i));
				}
			}
		}else if ("1".equals(contractStatus)) {
			//需要查询的合同状态为1 手动分页后将已签订的放入
			for (int i = 0; i < signContractInfoDTOList.size(); i++) {
				if (i >= startIndex && i <= endIndex) {
					returnContractInfoDTOList.add(signContractInfoDTOList.get(i));
				}
			}
		} else if ("0".equals(contractStatus)  && page == 1) {
			//未签订的放进返回
			if (nosignContractInfoDTOList.size() > 0) {
				returnContractInfoDTOList.add(noSignContractInfoDTO);
			}
		}
		map.put("returnContractInfoDTOList", returnContractInfoDTOList);
		map.put("noSignContractInfoCount", nosignContractInfoDTOList.size());
		map.put("signContractInfoCount", signContractInfoDTOList.size());
		logger.info("resultHandle方法已结束");
		return map;
	}

	/**
	 * Description: 是否提醒 <br>
	 *
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode
	 * @return <br>
	 */
	@Override
	public ExecuteResult<ContractRemindInfoDTO> queryRemindFlag(String memberCode) {
		logger.info("queryRemindFlag方法 已进入 会员店编码memberCode=" + memberCode);
		ExecuteResult<ContractRemindInfoDTO> result = new ExecuteResult<ContractRemindInfoDTO>();
		ContractRemindInfoDTO contractRemindInfoDTO = new ContractRemindInfoDTO();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 查询失败");
			return result;
		}
		try {
			//首先根据会员编码查询一次包厢关系
			List<MemberShipDTO> memberShipList = boxRelationshipDAO.queryBoxRelationshipList(memberCode);
			if (memberShipList.isEmpty()) {
				result.setResultMessage("该会员店没有包厢关系 不需要提醒");
				contractRemindInfoDTO.setRemindFlag("noRemind");
				result.setResult(contractRemindInfoDTO);
				return result;
			}
			Integer remindFlag = contractDAO.queryRemindFlagByMemberCode(memberCode);
			if (null != remindFlag && remindFlag == 1) {
				//查询到的提醒标志不为空 且标志不为0 表示不需要提醒 数据库记录无需更改
				result.setResultMessage("该会员店查询到提醒标识不为0或null 不需要提醒");
				contractRemindInfoDTO.setRemindFlag("noRemind");
				result.setResult(contractRemindInfoDTO);
				return result;
			} else {
				//数据库中不含该会员店的提醒标志  则查询有效合同如果有未签订合同的包厢关系则需要提醒
				List<ContractInfoDTO> returnContractList = contractDAO.queryEffectiveContractByMemberCode(memberCode, null);
				List<ContractInfoDTO> needContractInfoList = new ArrayList<ContractInfoDTO>();
				for (MemberShipDTO memberShipDTO : memberShipList) {
					boolean checkFlag = true;
					for (ContractInfoDTO contractInfoDTO : returnContractList) {
						if (memberShipDTO.getMemberCode().equals(contractInfoDTO.getVendorCode())) {
							checkFlag = false;
							break;
						}
					}
					if (checkFlag) {
						//查询会员店信息
						MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
						memberBaseDTO.setMemberCode(memberCode);
						memberBaseDTO.setBuyerSellerType("1");
						MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(memberBaseDTO);
						MemberBaseDTO vendorBaseDTO = new MemberBaseDTO();
						vendorBaseDTO.setMemberCode(memberShipDTO.getMemberCode());
						vendorBaseDTO.setBuyerSellerType("2");
						MemberBaseDTO vendorBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(vendorBaseDTO);
						ContractInfoDTO contractInfoDTO = new ContractInfoDTO();
						contractInfoDTO.setMemberCode(memberCode);
						contractInfoDTO.setMemberName(memberBase.getCompanyName());
						contractInfoDTO.setMemberLocationAddr(memberBase.getLocationDetail());
						contractInfoDTO.setMemberArtificialPersonName(memberBase.getArtificialPersonName());
						contractInfoDTO.setVendorCode(vendorBase.getMemberCode());
						contractInfoDTO.setVendorName(vendorBase.getCompanyName());
						contractInfoDTO.setVendorLocationAddr(vendorBase.getLocationDetail());
						contractInfoDTO.setVendorArtificialPersonName(vendorBase.getArtificialPersonName());
						needContractInfoList.add(contractInfoDTO);
						result.setResultMessage("该会员店提醒标志为0 需要提醒");
						contractRemindInfoDTO.setRemindFlag("Remind");
					}
				}
				contractRemindInfoDTO.setNeedSignContractList(needContractInfoList);
				if (StringUtils.isEmpty(contractRemindInfoDTO.getRemindFlag())) {
					contractRemindInfoDTO.setRemindFlag("noRemind");
				}
			}
			result.setResult(contractRemindInfoDTO);
		} catch (Exception e) {
			result.addErrorMessage("查询异常 异常信息 :" + e);
			logger.error("queryRemindFlag 方法查询合同列表异常 异常信息:" + e);
		}
		logger.info("queryRemindFlag方法 已结束");
		return result;
	}

	/**
	 * @Description: 查询签订合同信息
	 * @param vendorCode
	 * @param memberCode
	 * @return:
	 */
	@Override
	public ExecuteResult<List<ContractInfoDTO>> queryContractList(List<String> vendorCodeList, String memberCode) {
		logger.info("queryEntranceExists方法 已进入会员店编码 memberCode=" + memberCode);
		ExecuteResult<List<ContractInfoDTO>> result = new ExecuteResult<List<ContractInfoDTO>>();
		if (vendorCodeList.isEmpty()) {
			result.addErrorMessage("供应商信息为空");
			return result;
		}
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店信息为空");
			return result;
		}
		try {
			//根据传入的会员店编码和有可能的供应商编码查询现有合同信息
			List<ContractInfoDTO> returnContractList = contractDAO.queryEffectiveContractByMemberCode(memberCode, vendorCodeList);
			List<ContractInfoDTO> returnContracInfotList = new ArrayList<ContractInfoDTO>();
			//查询会员店信息
			MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
			memberBaseDTO.setMemberCode(memberCode);
			memberBaseDTO.setBuyerSellerType("1");
			MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(memberBaseDTO);
			returnContracInfotList.addAll(returnContractList);
			for (String vendor : vendorCodeList) {
				boolean checkExistsFlag = true;
				for (ContractInfoDTO contractInfoDTO : returnContractList) {
					if (vendor.equals(contractInfoDTO.getVendorCode())) {
						//如果相同的话置为false证明已经查询到合同信息
						checkExistsFlag = false;
						break;
					}
				}
				if (checkExistsFlag) {
					//表示对应的供应商没有签订合同
					ContractInfoDTO noSigncontract = getContractInfoDTOByCode(memberBase, vendor);
					returnContracInfotList.add(noSigncontract);
				}
			}
			
			result.setResult(returnContracInfotList);
		} catch (Exception e) {
			result.addErrorMessage("查询合同签订信息异常 异常信息error=" + e.getMessage());
			logger.error("查询合同签订信息异常 异常信息 error=" + e.getMessage() + ",	会员店编码memberCode=" + memberCode);
		}
		logger.info("queryEntranceExists方法 已结束");
		return result;
	}

	/**
	 * Description: 批量查询某供应商下会员店合同情况 <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param vendorCode
	 * @param memberCodeList
	 * @return <br>
	 */ 
	@Override
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoListByMemberAndVendorCode(String vendorCode,
			List<String> memberCodeList) {
		logger.info("queryContractInfoListByMemberAndVendorCode方法 已进入");
		ExecuteResult<DataGrid<ContractInfoDTO>> result = new ExecuteResult<DataGrid<ContractInfoDTO>>();
		if (StringUtils.isEmpty(vendorCode)) {
			result.addErrorMessage("供货商编码为空 无法查询");
			return result;
		}
		DataGrid<ContractInfoDTO> datagrid = new DataGrid<ContractInfoDTO>();
		try {
			List<ContractInfoDTO> contractInfoDTOList = 
					contractDAO.queryContractInfoListByMemberAndVendorCode(vendorCode, memberCodeList);
			List<ContractInfoDTO> returnContractInfoDTOList =
					resultListHandle(vendorCode,memberCodeList,contractInfoDTOList);
			datagrid.setRows(returnContractInfoDTOList);
			datagrid.setSize(returnContractInfoDTOList.size());
			result.setResult(datagrid);
		} catch (Exception e) {
			logger.error("queryContractInfoListByMemberAndVendorCode方法批量查询供应商下会员店合同情况异常 error:" + e.getMessage());
			result.addErrorMessage("批量查询供应商下会员店合同情况异常 error:" + e);
		}
		return result;
	}
	
	/**
	 * Description: 针对批量查询某供应商下会员店合同情况结果进行处理 <br> 
	 *  
	 * @author zhoutong <br>
	 * @taskId <br>
	 * @param vendorCode
	 * @param memberCodeList
	 * @param contractInfoDTOList
	 * @return <br>
	 */ 
	public List<ContractInfoDTO> resultListHandle(String vendorCode, List<String> memberCodeList,
			List<ContractInfoDTO> contractInfoDTOList) throws Exception {
		logger.info("resultListHandle方法已进入 对查询合同列表结果进行处理");
		List<ContractInfoDTO> returnContractInfoDTOList = new ArrayList<ContractInfoDTO>();
		if (contractInfoDTOList.isEmpty()) {
			logger.info("该供货商与这一批会员店之间没有查询到合同");
			//没有查询到合同表示具有包厢关系的供应商全部未签约合同
			for (String memberCode : memberCodeList) {
				ContractInfoDTO contractInfoDTO = new ContractInfoDTO();
				contractInfoDTO.setContractId(null);
				contractInfoDTO.setContractCode(null);
				contractInfoDTO.setSignTime(null);
				contractInfoDTO.setContractStatus("0");
				contractInfoDTO.setMemberCode(memberCode);
				contractInfoDTO.setVendorCode(vendorCode);
				contractInfoDTO.setContractUrl(null);
				contractInfoDTO.setContractCredit(null);
				contractInfoDTO.setContractTitle(null);
				returnContractInfoDTOList.add(contractInfoDTO);
			}
			logger.info("resultListHandle方法已结束");
			return returnContractInfoDTOList;
		}
		for (String memberCode : memberCodeList) {
			//遍历此前拿到的供应商编码
			boolean checkFlag = true;
			for (ContractInfoDTO contractInfoDTO : contractInfoDTOList) {
				//遍历查询到的合同
				if (memberCode.equals(contractInfoDTO.getMemberCode())) {
					//如果会员店编码相同即代表与当前供应商存在合同
					ContractInfoDTO returnContractInfoDTO = new ContractInfoDTO();
					returnContractInfoDTO.setContractId(contractInfoDTO.getContractId());
					returnContractInfoDTO.setContractCode(contractInfoDTO.getContractCode());
					returnContractInfoDTO.setSignTime(contractInfoDTO.getSignTime());
					returnContractInfoDTO.setContractStatus(contractInfoDTO.getContractStatus());
					returnContractInfoDTO.setVendorCode(vendorCode);
					returnContractInfoDTO.setMemberCode(memberCode);
					returnContractInfoDTO.setContractUrl(contractInfoDTO.getContractTitle());
					returnContractInfoDTO.setContractCredit(contractInfoDTO.getContractCredit());
					returnContractInfoDTO.setContractTitle(contractInfoDTO.getContractTitle());
					returnContractInfoDTO.setMemberArtificialPersonName(contractInfoDTO.getMemberArtificialPersonName());
					returnContractInfoDTO.setMemberLocationAddr(contractInfoDTO.getMemberLocationAddr());
					returnContractInfoDTO.setVendorArtificialPersonName(contractInfoDTO.getVendorArtificialPersonName());
					returnContractInfoDTO.setVendorLocationAddr(contractInfoDTO.getVendorLocationAddr());
					returnContractInfoDTOList.add(returnContractInfoDTO);
					checkFlag = false;
					break;
				}
			}
			//经过遍历 查询出的合同并不包含当前会员店
			if (checkFlag) {
				ContractInfoDTO returnContractInfoDTO = new ContractInfoDTO();
				returnContractInfoDTO.setContractId(null);
				returnContractInfoDTO.setContractCode(null);
				returnContractInfoDTO.setSignTime(null);
				returnContractInfoDTO.setContractStatus("0");
				returnContractInfoDTO.setVendorCode(vendorCode);
				returnContractInfoDTO.setMemberCode(memberCode);
				returnContractInfoDTO.setContractUrl(null);
				returnContractInfoDTO.setContractCredit(null);
				returnContractInfoDTO.setContractTitle(null);
				returnContractInfoDTOList.add(returnContractInfoDTO);
			}
		}
		logger.info("resultListHandle方法已结束");
		return returnContractInfoDTOList;
	}

	/**
	 * Description:修改会员店提醒状态为不提醒 <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param memberCode 会员店编码
	 * @param operationId 操作人员id
	 * @param operationName 操作人员名称
	 * @return <br>
	 */ 
	@Override
	@Transactional
	public ExecuteResult<String> updateRemindFlagToNotNeed(String memberCode, Long operationId, String operationName) {
		logger.info("saveORUpdateRemindFlag方法 已进入 会员店编码memberCode=" + memberCode);
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 保存失败");
			return result;
		} else if (null == operationId || StringUtils.isEmpty(operationName)) {
			result.addErrorMessage("操作人信息不完整 保存失败");
			return result;
		}
		try {
			Integer remindFlag = contractDAO.queryRemindFlagByMemberCode(memberCode);
			ContractSignRemindInfoDTO contractSignRemindInfoDTO = new ContractSignRemindInfoDTO();
			contractSignRemindInfoDTO.setMemberCode(memberCode);
			contractSignRemindInfoDTO.setModifyId(operationId);
			contractSignRemindInfoDTO.setModifyName(operationName);
			contractSignRemindInfoDTO.setIsNeedRemind(1);
			if (remindFlag == null) {
				//查询到的提醒标志为空 需要提醒 且新增一条无需提醒的记录
				contractSignRemindInfoDTO.setCreateId(operationId);
				contractSignRemindInfoDTO.setCreateName(operationName);
				contractDAO.insertContractSignRemindInfo(contractSignRemindInfoDTO);
				result.setResultMessage("提醒状态保存完成");
				result.setResult("success");
			} else if (remindFlag == 0) {
				//查询到的提醒标志不为空 且标志为0 表示需要提醒 将记录更改为无需提醒
				contractDAO.updateContractSignRemindInfo(contractSignRemindInfoDTO);
				result.setResultMessage("提醒状态修改完成");
				result.setResult("success");
			} else {
				result.setResultMessage("提醒状态无需修改");
				result.setResult("success");
			}
		} catch (Exception e) {
			result.addErrorMessage("新增或者修改提醒信息异常 异常信息 :" + e);
			logger.error("saveORUpdateRemindFlag 方法保存或者修改提醒信息异常 异常信息:" + e);
		}
		logger.info("saveORUpdateRemindFlag方法 已结束");
		return result;
	}

	/**
	 * Description:创建合同 <br> 
	 *  
	 * @author zhoutong<br>
	 * @taskId <br>
	 * @param saveContractInfoDTO
	 * @return <br>
	 */ 
	@Override
	@Transactional
	public ExecuteResult<String> saveContractInfo(List<SaveContractInfoDTO> saveContractInfoDTOList,
			String contractType) {
		logger.info("saveContractInfo方法已进入");
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (saveContractInfoDTOList.isEmpty()) {
			result.addErrorMessage("合同信息为空 合同签订失败");
			return result;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String dateYMD = sdf.format(new Date());
			String supplyContractNoKey = "supply_contract_num" + dateYMD;
			for (SaveContractInfoDTO saveContractInfoDTO : saveContractInfoDTOList) {
				MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
				memberBaseDTO.setMemberCode(saveContractInfoDTO.getMemberCode());
				memberBaseDTO.setBuyerSellerType("1");
				MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(memberBaseDTO);
				MemberBaseDTO vendorBaseDTO = new MemberBaseDTO();
				vendorBaseDTO.setMemberCode(saveContractInfoDTO.getVendorCode());
				vendorBaseDTO.setBuyerSellerType("2");
				MemberBaseDTO vendorBase = memberBaseDAO.queryMemberBaseInfoByMemberCodeAndType(vendorBaseDTO);
				if (null == memberBase) {
					logger.error("查询不到对应的供应商信息 供应商编码=" + saveContractInfoDTO.getMemberCode());
					throw new Exception("查询不到对应的供应商信息");
				}
				if (null == vendorBase) {
					logger.error("查询不到对应的供应商信息 供应商编码=" + saveContractInfoDTO.getVendorCode());
					throw new Exception("查询不到对应的供应商信息");
				}
				String contractCode = getContractCode(contractType, dateYMD, supplyContractNoKey);
				saveContractInfoDTO.setContractCode(contractCode);
				saveContractInfoDTO.setContractStatus(1);
				saveContractInfoDTO.setMemberName(memberBase.getCompanyName());
				saveContractInfoDTO.setMemberLocationAddr(memberBase.getLocationDetail());
				saveContractInfoDTO.setMemberArtificialPersonName(memberBase.getArtificialPersonName());
				saveContractInfoDTO.setVendorName(vendorBase.getCompanyName());
				saveContractInfoDTO.setVendorLocationAddr(vendorBase.getLocationDetail());
				saveContractInfoDTO.setVendorArtificialPersonName(vendorBase.getArtificialPersonName());
			}
			contractDAO.insertContractInfo(saveContractInfoDTOList);
			result.setResult("success");
		} catch (Exception e) {
			logger.error("saveContractInfo方法保存合同信息失败 失败信息 error:" + e);
			result.addErrorMessage("保存合同信息失败 失败信息 error:" + e);
		}
		logger.info("saveContractInfo方法已结束");
		return result;
	}
	
	public String getContractCode(String contractType, String dateYMD, String supplyContractNoKey) throws Exception {
		String contractCode = "";
		if (redisDB.exists(supplyContractNoKey)) {
			//如果存在且不为空
			String redisContractCodeEnd = redisDB.incr(supplyContractNoKey) + "";
			for (int i = 0; i < 4; i++) {
				if (redisContractCodeEnd.length() < 5) {
					redisContractCodeEnd = "0" + redisContractCodeEnd;
				}
			}
			contractCode = contractType + dateYMD + redisContractCodeEnd;
		} else {
			contractCode = contractType + dateYMD + "00001";
			redisDB.setAndExpire(supplyContractNoKey, "1", 172800);
		}
		return contractCode;
	}
	
}
