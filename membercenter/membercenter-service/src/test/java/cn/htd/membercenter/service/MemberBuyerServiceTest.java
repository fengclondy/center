package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBuyerFinanceDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeMatrixDTO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerSupplierDTO;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerServiceTest
 * </p>
 * 
 * @author root
 * @date 2016年12月26日
 *       <p>
 *       Description: 买家中心接口测试
 *       </p>
 */
public class MemberBuyerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MemberBuyerServiceTest.class);

	ApplicationContext ctx = null;
	MemberBuyerService memberBuyerService = null;

	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBuyerService = (MemberBuyerService) ctx.getBean("memberBuyerService");
	}

	@Test
	public void test001() {
		// 查询个人信息
		ExecuteResult<MemberBuyerPersonalInfoDTO> executeResult = memberBuyerService.queryBuyerPersonalInfo(25L);
		System.out.println(executeResult.isSuccess());
	}

	public void test002() {
		// 查询等级信息
		ExecuteResult<MemberBuyerGradeInfoDTO> queryBuyerGradeInfo = memberBuyerService.queryBuyerGradeInfo(24L);
		System.out.println(queryBuyerGradeInfo.getResult().getPointGrade());
		if (queryBuyerGradeInfo.getResult().getPathList().size() > 0) {
			for (MemberBuyerGradeMatrixDTO dto : queryBuyerGradeInfo.getResult().getPathList()) {
				System.out.println(dto.getFinanceDistance());
				System.out.println(dto.getMallDistance());
			}
		}
	}

	public void test003() {
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		// 查询等级信息
		ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> queryBuyerGradeInfo = memberBuyerService
				.queryBuyerBusinessSupperlier(pager, 420L);
		System.out.println(queryBuyerGradeInfo.getResult());
	}

	@Test
	public void test004() {
		Pager pager = new Pager();
		MemberBuyerFinanceDTO dto = new MemberBuyerFinanceDTO();
		dto.setMemberId(9l);
		pager.setRows(10);
		pager.setPage(0);
		// 查询等级信息
		ExecuteResult<DataGrid<MemberBuyerFinanceDTO>> queryBuyerGradeInfo = memberBuyerService
				.queryBuyerBackupContactList(pager, dto);
		System.out.println(queryBuyerGradeInfo.getResult());
	}

}
