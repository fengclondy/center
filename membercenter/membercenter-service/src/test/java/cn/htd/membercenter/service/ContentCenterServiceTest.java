package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.membercenter.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;

public class ContentCenterServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentCenterServiceTest.class);
	
	ApplicationContext ctx = null;
	ContentCenterService contentCenterService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		contentCenterService = (ContentCenterService) ctx.getBean("contentCenterService");
	}

	public void selectContentCenter() {
		// ExecuteResult<List<Floor>> f = contentCenterService.selectFloor();
		// ExecuteResult<List<HeadAd>> h = contentCenterService.selectHeadAd();
		// ExecuteResult<LoginPage> l = contentCenterService.selectLoginPage();
		// ExecuteResult<List<StaticAd>> s =
		// contentCenterService.selectStaticAd();
		ExecuteResult<List<SubAd>> ad = contentCenterService.selectStoreSubAd("1", 4);
		/*
		 * ExecuteResult<List<SubCarouselAd>> sc = contentCenterService
		 * .selectSubCarouselAd("1");
		 */
		System.out.println("11" + ad);
		/*
		 * System.out.println(f.getResult().toString() +
		 * h.getResult().toString() + l.getResultMessage() +
		 * s.getResultMessage() + ad.getResultMessage() + sc.getResult());
		 */
	}

	@Test
	public void test() {
		ExecuteResult<LoginPage> rs = contentCenterService.selectLoginPage();
		System.out.print(rs);

	}
	
	/**
	 * 查询所有楼层信息
	 * @author zf.zhang
	 * @since 2017-3-13 16:35
	 */
	@Test
	public void selectFloorListTest() {
		ExecuteResult<List<FloorInfoDO>> result =  contentCenterService.getAllFloors();
		System.out.print("selectFloorListTest===>result:" + result.getResult().get(0).getIconUrl());
		
	}

	@Test
	public void selectqueryMobileMallFirstCategoryList() {
		ExecuteResult<List<MobileMallCategory>> result =  contentCenterService.queryMobileMallFirstCategoryList();
		System.out.print(result.getResult());
	}

	@Test
	public void selectqueryMobileMallChildCategoryListByTypeId() {
		ExecuteResult<List<MobileMallCategory>> result =  contentCenterService.queryMobileMallChildCategoryListByTypeId(1L);
		System.out.print(result.getResult());
	}
}
