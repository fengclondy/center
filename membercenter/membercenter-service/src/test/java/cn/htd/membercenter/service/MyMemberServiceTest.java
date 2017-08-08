package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyMemberSearchDTO;

public class MyMemberServiceTest {
	ApplicationContext ctx = null;
	MyMemberService myMemberService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		myMemberService = (MyMemberService) ctx.getBean("myMemberService");
	}

	@Test
	public void testExport() {
		ExecuteResult<DataGrid<MyMemberDTO>> rs = myMemberService.exportMyMemberList(1L, new MyMemberSearchDTO(), "2");
		System.out.println(rs);
	}
}
