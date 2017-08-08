package cn.htd.membercenter.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberGroupDTO;

public class MemberGroupServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberGroupServiceTest.class);
	ApplicationContext ctx = null;
	MemberGroupService memberGroupService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberGroupService = (MemberGroupService) ctx.getBean("memberGroupService");
	}

	public void queryMemberNoneGroupListInfo() {
		MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
		memberGroupDTO.setSellerId("1");
		// memberGroupDTO.setLocationProvince("2");
		// memberGroupDTO.setCompanyName("1");
		ExecuteResult<List<MemberGroupDTO>> res = memberGroupService.queryMemberNoneGroupListInfo(memberGroupDTO);
		Assert.assertTrue(res.isSuccess());
	}

	// @Test
	// public void queryMemberGroupInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setGroupId("1");
	// ExecuteResult<MemberGroupDTO> res =
	// memberGroupService.queryMemberGroupInfo(memberGroupDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void insertMemberGroupInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setBuyerIds("1,2");
	// memberGroupDTO.setName("haozy");
	// memberGroupDTO.setComment("test");
	// memberGroupDTO.setSellerId("1111");
	// memberGroupDTO.setOperateId("1");
	// memberGroupDTO.setOperateName("1");
	// ExecuteResult<Boolean> res =
	// memberGroupService.insertMemberGroupInfo(memberGroupDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void updateMemberGroupInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setBuyerIds("3,4");
	// memberGroupDTO.setName("zhang");
	// memberGroupDTO.setComment("test");
	// memberGroupDTO.setGroupId(16);
	// memberGroupDTO.setOperateId("1");
	// memberGroupDTO.setOperateName("test");
	// ExecuteResult<Boolean> res =
	// memberGroupService.updateMemberGroupInfo(memberGroupDTO);
	// Assert.assertTrue(res.isSuccess());
	// }
	//
	// @Test
	// public void queryMemberGradeAndGroupList() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setSellerId("1");
	// Pager<MemberGroupDTO> pager = new Pager<MemberGroupDTO>();
	// pager.setPage(1);
	// pager.setRows(10);
	// ExecuteResult<DataGrid<MemberGroupDTO>> res =
	// memberGroupService.queryMemberGradeAndGroupList(memberGroupDTO,
	// pager);
	// }

	// @Test
	// public void deleteMemberGroupInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setGroupIds("1,2");
	// memberGroupDTO.setOperateId("1");
	// memberGroupDTO.setOperateName("test");
	// ExecuteResult<Boolean> res =
	// memberGroupService.deleteMemberGroupInfo(memberGroupDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void querySubMemberIdByGradeInfoAndGroupInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setSellerId("1");
	// List<String> gradeList = new ArrayList<String>();
	// gradeList.add("2");
	// gradeList.add("3");
	// memberGroupDTO.setGradeList(gradeList);
	// List<String> groupList = new ArrayList<String>();
	// groupList.add("1");
	// memberGroupDTO.setGroupList(groupList);
	// ExecuteResult<List<String>> res =
	// memberGroupService.querySubMemberIdByGradeInfoAndGroupInfo(memberGroupDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void queryMemberGroupListInfo() {
	// MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
	// memberGroupDTO.setSellerId("1");
	// Pager<MemberGroupDTO> pager = new Pager<MemberGroupDTO>();
	// pager.setPage(1);
	// pager.setRows(10);
	// ExecuteResult<DataGrid<MemberGroupDTO>> res =
	// memberGroupService.queryMemberGroupListInfo(memberGroupDTO,
	// pager);
	// }

	@Test
	public void queryGroupInfoBySellerBuyerId() {
		MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
		memberGroupDTO.setSellerId("1");
		// memberGroupDTO.setLocationProvince("2");
		// memberGroupDTO.setCompanyName("1");
		ExecuteResult<MemberGroupDTO> res = memberGroupService.queryGroupInfoBySellerBuyerId(17651l, 17606l);
		Assert.assertTrue(res.isSuccess());
	}
}
