package cn.htd.basecenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.basecenter.dto.BasePlacardDTO;
import cn.htd.basecenter.dto.BasePlacardScopeDTO;
import cn.htd.basecenter.dto.PlacardCondition;
import cn.htd.basecenter.dto.PlacardInfo;
import cn.htd.basecenter.enums.PlacardScopeTypeEnum;
import cn.htd.basecenter.enums.PlacardTypeEnum;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.util.DictionaryUtils;

public class BasePlacardServiceTest {

	ApplicationContext ctx = null;
	private BasePlacardService basePlacardService;
	private DictionaryUtils dictionary;

	@Before
	public void set() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		basePlacardService = (BasePlacardService) ctx.getBean("basePlacardService");
		dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
	}

	@Test
	public void addBasePlacardTest() {
		BasePlacardDTO basePlacard = new BasePlacardDTO();
		List<BasePlacardScopeDTO> placardScopeList = new ArrayList<BasePlacardScopeDTO>();
		basePlacard.setSendType("1");
		basePlacard.setTitle("jiangkun test");
		basePlacard.setContent("jiangkun test");
		basePlacard.setPublishTime(new Date());
		basePlacard.setApplyTime(new Date());
		basePlacard.setIsValidForever(1);
		basePlacard.setComment("jiangkun test");
		basePlacard.setPicAttachment("1");
		basePlacard.setHasUrl(0);
		basePlacard.setCreateId(1L);
		basePlacard.setCreateName("jiangkun");
		basePlacard.setModifyId(1L);
		basePlacard.setModifyName("jiangkun");
		BasePlacardScopeDTO dto = new BasePlacardScopeDTO();
		dto.setScopeType(PlacardScopeTypeEnum.INNER_SELLER.getValue());
		placardScopeList.add(dto);
		basePlacard.setPlacardScopeList(placardScopeList);

		basePlacardService.addPlatformBasePlacard(basePlacard);
		System.out.println("aaaaa");
	}

	@Test
	public void getPlacardListTest() {
		PlacardCondition placardCondition = new PlacardCondition();
		placardCondition.setMemberId(1L);
		placardCondition.setPlacardType(PlacardTypeEnum.PLATFORM.getValue());
		placardCondition.setBuyerGradeValue("1");
		ExecuteResult<DataGrid<PlacardInfo>> result = basePlacardService.getPlacardList(placardCondition, null);
		System.out.println("aaaaa");
	}

	@Test
	public void getUnReadSellerPlacardCountTest() {
		ExecuteResult<Long> result = basePlacardService.getUnReadSellerPlacardCount(1L);
		System.out.println("aaaaa");
	}
}
