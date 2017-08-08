package cn.htd.goodscenter.service;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.goodscenter.test.common.CommonTest;
import org.junit.Test;

public class RecommendGoodsServiceImplTest extends CommonTest {

	@Resource
	private RecommendGoodsService recommendGoodsService;
	
	@Test
	public void getItemIds(){
		List<Long> list = recommendGoodsService.getItemIds(1,1);
		
		System.out.println(list);
	}
}
