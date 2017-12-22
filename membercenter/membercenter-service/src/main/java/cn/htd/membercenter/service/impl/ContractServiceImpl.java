package cn.htd.membercenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.ContractDAO;
import cn.htd.membercenter.dto.ContractInfoDTO;
import cn.htd.membercenter.dto.ContractSignRemindInfoDTO;
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
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractListByMemberCode(String memberCode, String contractStatus, Pager<String> pager) {
		logger.info("queryContractListBySellerId方法已进入");
		ExecuteResult<DataGrid<ContractInfoDTO>> result = new ExecuteResult<DataGrid<ContractInfoDTO>>();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 查询失败");
			return result;
		}
		if (pager == null) {
			result.addErrorMessage("分页信息为空");
			return result;
		}
		DataGrid<ContractInfoDTO> datagrid = new DataGrid<ContractInfoDTO>();
		List<ContractInfoDTO> contractInfoDTOList = null;
		int page = pager.getPage();
		int size = pager.getRows();
		if (page < 1) {
			page = 1;
		}
		if (size < 1) {
			size = 10;
		}
		int startIndex = (page - 1) * size;
		int endIndex = (startIndex - 1) + size;
		try {
			//首先根据会员编码查询一圈包厢关系
			List<MemberShipDTO> memberShipList = boxRelationshipDAO.queryBoxRelationshipList(memberCode);
			List<String> vendorCodeList = new ArrayList<String>();
			if (memberShipList.isEmpty()) {
				result.setResultMessage("该会员店没有包厢关系 不含合同");
				return result;
			}
			for (MemberShipDTO memberShip : memberShipList) {
				//将供应商编码获取出来
				vendorCodeList.add(memberShip.getMemberCode());
			}
			if (vendorCodeList.isEmpty()) {
				result.setResultMessage("该会员店没有包厢关系 不含合同");
				return result;
			}
			//查询出已经签订的合同
			contractInfoDTOList = contractDAO.queryContractBymemberCode(memberCode, vendorCodeList);
			List<ContractInfoDTO> returnContractInfoDTOList = new ArrayList<ContractInfoDTO>();
			List<ContractInfoDTO> contractInfoDTOHandleList = resultHandle(memberCode, contractStatus, memberShipList,contractInfoDTOList);
			for (int i = 0;i < contractInfoDTOHandleList.size(); i++) {
				if (i >= startIndex && i <= endIndex) {
					returnContractInfoDTOList.add(contractInfoDTOHandleList.get(i));
				}
			}
			datagrid.setRows(returnContractInfoDTOList);
			datagrid.setSize(returnContractInfoDTOList.size());
			datagrid.setTotal((long)contractInfoDTOHandleList.size());
			result.setResult(datagrid);
		} catch (Exception e) {
			result.addErrorMessage("查询异常 异常信息 :" + e);
			logger.error("queryContractListBySellerId 方法查询合同列表异常 异常信息:" + e);
		}
		logger.info("queryContractListBySellerId方法已结束");
		return result;
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
	public List<ContractInfoDTO> resultHandle(String memberCode, String contractStatus, List<MemberShipDTO> memberShipList,
											  List<ContractInfoDTO> contractInfoDTOList) throws Exception {
		logger.info("resultHandle方法已进入 对查询合同列表结果进行处理");
		List<ContractInfoDTO> returnContractInfoDTOList = new ArrayList<ContractInfoDTO>();
		if (contractInfoDTOList.isEmpty()) {
			logger.info("该会员店没有查询到合同");
			//没有查询到合同表示具有包厢关系的供应商全部未签约合同
			for (MemberShipDTO memberShip : memberShipList) {
				//签约类型为空或者为0表示未签约的时候添加进去
				if (StringUtils.isEmpty(contractStatus) || "0".equals(contractStatus)) {
					ContractInfoDTO contractInfoDTO = new ContractInfoDTO();
					contractInfoDTO.setContractId(null);
					contractInfoDTO.setContractCode(null);
					contractInfoDTO.setSignTime(null);
					contractInfoDTO.setContractStatus("0");
					contractInfoDTO.setVendorName(memberShip.getCompanyName());
					contractInfoDTO.setVendorCode(memberShip.getMemberCode());
					contractInfoDTO.setMemberCode(memberCode);
					contractInfoDTO.setContractUrl(null);
					contractInfoDTO.setContractCredit(null);
					contractInfoDTO.setContractTitle(null);
					returnContractInfoDTOList.add(contractInfoDTO);
				}
			}
			logger.info("resultHandle方法已结束");
			return returnContractInfoDTOList;
		}
		for (MemberShipDTO memberShip : memberShipList) {
			//遍历此前拿到的供应商编码
			boolean checkFlag = true;
			for (ContractInfoDTO contractInfoDTO : contractInfoDTOList) {
				//遍历查询到的合同
				if (memberShip.getMemberCode().equals(contractInfoDTO.getVendorCode()) 
						&& ((StringUtils.isEmpty(contractStatus) || "1".equals(contractStatus)))) {
					//如果编码相同即代表与当前供应商存在合同  
					ContractInfoDTO returnContractInfoDTO = new ContractInfoDTO();
					returnContractInfoDTO.setContractId(contractInfoDTO.getContractId());
					returnContractInfoDTO.setContractCode(contractInfoDTO.getContractCode());
					returnContractInfoDTO.setSignTime(contractInfoDTO.getSignTime());
					returnContractInfoDTO.setContractStatus(contractInfoDTO.getContractStatus());
					returnContractInfoDTO.setVendorCode(contractInfoDTO.getVendorCode());
					returnContractInfoDTO.setVendorName(memberShip.getCompanyName());
					returnContractInfoDTO.setMemberCode(contractInfoDTO.getMemberCode());
					returnContractInfoDTO.setMemberName(contractInfoDTO.getMemberName());
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
				} else if (memberShip.getMemberCode().equals(contractInfoDTO.getVendorCode())
						&& "0".equals(contractStatus)) {
					//编码相同存在合同 但是查询类型为0表示未签约时checkFlag应置为false 否则会导致所有已签订合同的也被归纳到未签订合同的里面
					checkFlag = false;
					break;
				}
			}
			//经过遍历 查询出的合同并不包含当前供应商 签约类型为空或者为0表示未签约的时候添加
			if (checkFlag && ((StringUtils.isEmpty(contractStatus) || "0".equals(contractStatus)))) {
				ContractInfoDTO contractInfoDTO = new ContractInfoDTO();
				contractInfoDTO.setContractId(null);
				contractInfoDTO.setContractCode(null);
				contractInfoDTO.setSignTime(null);
				contractInfoDTO.setContractStatus("0");
				contractInfoDTO.setVendorName(memberShip.getCompanyName());
				contractInfoDTO.setVendorCode(memberShip.getMemberCode());
				contractInfoDTO.setContractUrl(null);
				contractInfoDTO.setContractCredit(null);
				contractInfoDTO.setContractTitle(null);
				returnContractInfoDTOList.add(contractInfoDTO);
			}
		}
		logger.info("resultHandle方法已结束");
		return returnContractInfoDTOList;
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
	public ExecuteResult<String> queryRemindFlag(String memberCode) {
		logger.info("queryRemindFlag方法 已进入 会员店编码memberCode=" + memberCode);
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 查询失败");
			return result;
		}
		try {
			//首先根据会员编码查询一次包厢关系
			List<MemberShipDTO> memberShipList = boxRelationshipDAO.queryBoxRelationshipList(memberCode);
			if (memberShipList.isEmpty()) {
				result.setResultMessage("该会员店没有包厢关系 不需要提醒");
				result.setResult("noRemind");
				return result;
			}
			Integer remindFlag = contractDAO.queryRemindFlagByMemberCode(memberCode);
			if (remindFlag == null) {
				//查询到的提醒标志为空 需要提醒
				result.setResultMessage("该会员店没有查询到提醒标识 需要提醒");
				result.setResult("Remind");
			} else if (remindFlag == 0) {
				//查询到的提醒标志为0 需要提醒
				result.setResultMessage("该会员店提醒标志为0 需要提醒");
				result.setResult("Remind");
			} else {
				//查询到的提醒标志不为空 且标志不为0 表示不需要提醒 数据库记录无需更改
				result.setResultMessage("该会员店查询到提醒标识不为0或null 不需要提醒");
				result.setResult("noRemind");
			}
		} catch (Exception e) {
			result.addErrorMessage("查询异常 异常信息 :" + e);
			logger.error("queryRemindFlag 方法查询合同列表异常 异常信息:" + e);
		}
		logger.info("queryRemindFlag方法 已结束");
		return result;
	}

	/**
	 * @Description: 查询签署合同入口是否存在
	 * @param vendorCode
	 * @param memberCode
	 * @return:
	 */
	@Override
	public ExecuteResult<String> queryEntranceExists(List<String> vendorCodeList, String memberCode) {
		logger.info("queryEntranceExists方法 已进入会员店编码 memberCode=" + memberCode);
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			//根据会员店编码和提供的供应商编码查询到的合同
			List<ContractInfoDTO> contractInfoDTOList = contractDAO.queryContractBymemberCodeAndVendorCodeList(memberCode, vendorCodeList);
			result.setResult("noExists");
			//遍历供应商编码
			for (String vendorCode : vendorCodeList) {
				boolean checkExists = true;
				//遍历查询到的合同
				for (ContractInfoDTO contractInfoDTO : contractInfoDTOList) {
					//供应商编码若是跟合同编码相同 则跳出
					if (vendorCode.equals(contractInfoDTO.getVendorCode())) {
						checkExists = false;
						break;
					}
				}
				if (checkExists) {
					//若包含一家供应商并没有签订合同 则入口就应存在 
					result.setResult("Exists");
				}
			}
		} catch (Exception e) {
			result.addErrorMessage("查询合同签订状态异常 异常信息error=" + e);
			logger.error("查询合同签订状态异常 异常信息 error=" + e + ",	会员店编码memberCode=" + memberCode);
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
	public ExecuteResult<String> updateRemindFlagToNotNeed(String memberCode, Long operationId, String operationName) {
		logger.info("saveORUpdateRemindFlag方法 已进入 会员店编码memberCode=" + memberCode);
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (StringUtils.isEmpty(memberCode)) {
			result.addErrorMessage("会员店编码为空 保存失败");
			return result;
		} else if (operationId == null || StringUtils.isEmpty(operationName)) {
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
				String contractCode = getContractCode(contractType, dateYMD, supplyContractNoKey);
				saveContractInfoDTO.setContractCode(contractCode);
				saveContractInfoDTO.setContractStatus(1);
			}
			contractDAO.insertContractInfo(saveContractInfoDTOList);
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
