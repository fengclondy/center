package cn.htd.goodscenter.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.htd.goodscenter.dto.middleware.indto.DownErpCallbackInDTO;
import cn.htd.goodscenter.test.common.CommonTest;

import com.google.common.collect.Lists;

public class HtdDownErpCallbackServiceTest extends CommonTest{
	@Resource
	private HtdDownErpCallbackService htdDownErpCallbackService;
	@Test
	public void testItemDownErpCallback(){
		List<DownErpCallbackInDTO> downErpCallbackList =Lists.newArrayList();
		DownErpCallbackInDTO d=new DownErpCallbackInDTO();
		d.setErpStatus("4");
		d.setMiddleGroundCode("10004577");
		downErpCallbackList.add(d);
		htdDownErpCallbackService.itemDownErpCallback(downErpCallbackList);
	}
}
