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
import cn.htd.membercenter.dto.MemberShipDTO;

public class SuperBossTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SuperBossTest.class);
	ApplicationContext ctx = null;
	SuperBossService superBossService = null;
	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		superBossService = (SuperBossService) ctx.getBean("superBossService");
	}

	@Test
	public void test() {
		Pager page = new Pager();
		ExecuteResult<DataGrid<MemberShipDTO>> rs = superBossService.selectMemberShip("2017-02-04 11:20:26",null,null);
		//ExecuteResult<DataGrid<BoxRelationship>> rs = superBossService.selectBoxShip("2017-02-04 11:20:26",null,page);
		//ExecuteResult<DataGrid<BelongRelationship>> rs = superBossService.selectBelongShip("2017-02-04 11:20:26",null,page);
		if(rs.isSuccess()){
			System.out.print(rs);
		}

	}
}
