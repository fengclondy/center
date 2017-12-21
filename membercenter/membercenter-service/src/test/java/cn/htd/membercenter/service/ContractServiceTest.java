package cn.htd.membercenter.service;

import java.util.ArrayList;
import java.util.List;

import cn.htd.common.Pager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.ContractInfoDTO;
import cn.htd.membercenter.dto.SaveContractInfoDTO;

/** 
 * <Description> 合同基础服务测试 <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月18日 <br>
 */

public class ContractServiceTest {
	
	ApplicationContext ctx = null;
	ContractService contractService = null;

	@Before
	public void setUp() {
		try {
			ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
			contractService = (ContractService) ctx.getBean("contractService");
		} catch(Exception e) {
			System.out.println(e);
		}

	}
	
	@Test
	public void queryContractList() {
		Pager<String> pager = new Pager<String>();
		pager.setPage(1);
		pager.setRows(10);

		ExecuteResult<DataGrid<ContractInfoDTO>> result = contractService.queryContractListByMemberCode("926386",pager);
		if (result.isSuccess()) {
			List<ContractInfoDTO> ContractInfoDTOList = result.getResult().getRows();
			for (ContractInfoDTO contractInfoDTO : ContractInfoDTOList) {
				System.out.println(contractInfoDTO.getContractStatus());
			}
		} else {
			for (String e : result.getErrorMessages()) {
				System.out.println(e);
			}
		}
	} 

	@Test
	public void queryContractList2() {
		List<String> memberCodeList = new ArrayList<String>();
		memberCodeList.add("926386");
		memberCodeList.add("928123");
		ExecuteResult<DataGrid<ContractInfoDTO>> result = contractService.queryContractInfoListByMemberAndVendorCode("htd493085",memberCodeList);
		if (result.isSuccess()) {
			List<ContractInfoDTO> ContractInfoDTOList = result.getResult().getRows();
			for (ContractInfoDTO contractInfoDTO : ContractInfoDTOList) {
				System.out.println(contractInfoDTO.getContractStatus());
			}
		} else {
			for (String e : result.getErrorMessages()) {
				System.out.println(e);
			}
		}
	}
	
	@Test
	public void queryRemind() {
		ExecuteResult<String> result = contractService.queryRemindFlag("926386");
		if (result.isSuccess()) {
			System.out.println(result.getResult());
		}
		
	}
	
	@Test
	public void checkExists() {
		List<String> vendorCodeList = new ArrayList<String>();
		vendorCodeList.add("htd493085");
		vendorCodeList.add("htd238861");
		ExecuteResult<String> result = contractService.queryEntranceExists(vendorCodeList, "926386");
		if (result.isSuccess()) {
			System.out.println(result.getResult());
		}
	}
	
	@Test
	public void saveContractSignRemindFlag(){
		ExecuteResult<String> result = contractService.updateRemindFlagToNotNeed("926386", 123L, "system");
		if (result.isSuccess()) {
			System.out.println(result.getResult());
		} 
	}
	
	@Test
	public void saveContract() {
		List<SaveContractInfoDTO> saveContractInfoDTOList = new ArrayList<SaveContractInfoDTO>();
		SaveContractInfoDTO saveContractInfoDTO = new SaveContractInfoDTO();
		saveContractInfoDTO.setContractUrl("contractUrl");
		saveContractInfoDTO.setContractStatus(0);
		saveContractInfoDTO.setVendorCode("htd493085");
		saveContractInfoDTO.setMemberCode("926386");
		saveContractInfoDTO.setContractCredit(0.0D);
		saveContractInfoDTO.setContractTitle("contractTitle");
		saveContractInfoDTO.setSignAuthorCode("signAuthorCode");
		saveContractInfoDTO.setSignAuthorName("signAuthorName");
		saveContractInfoDTO.setCreateId(123L);
		saveContractInfoDTO.setCreateName("createName");
		saveContractInfoDTO.setModifyId(123L);
		saveContractInfoDTO.setModifyName("modifyName");
		saveContractInfoDTOList.add(saveContractInfoDTO);
		SaveContractInfoDTO saveContractInfoDTO1 = new SaveContractInfoDTO();
		saveContractInfoDTO1.setContractUrl("contractUrl");
		saveContractInfoDTO1.setContractStatus(0);
		saveContractInfoDTO1.setVendorCode("htd238861");
		saveContractInfoDTO1.setMemberCode("926386");
		saveContractInfoDTO1.setContractCredit(0.0D);
		saveContractInfoDTO1.setContractTitle("contractTitle");
		saveContractInfoDTO1.setSignAuthorCode("signAuthorCode");
		saveContractInfoDTO1.setSignAuthorName("signAuthorName");
		saveContractInfoDTO1.setCreateId(123L);
		saveContractInfoDTO1.setCreateName("createName");
		saveContractInfoDTO1.setModifyId(123L);
		saveContractInfoDTO1.setModifyName("modifyName");
		saveContractInfoDTOList.add(saveContractInfoDTO1);
		ExecuteResult<String> result = contractService.saveContractInfo(saveContractInfoDTOList,"10");
		if (result.isSuccess()) {
			
		}
	}
}
