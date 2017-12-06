package cn.htd.basecenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.BaseSmsNoticeDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public class BaseSmsNoticeServiceTest {
	ApplicationContext ctx = null;
	BaseSmsNoticeService baseSmsNoticeService = null;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		baseSmsNoticeService = (BaseSmsNoticeService) ctx.getBean("baseSmsNoticeService");
	}
	
	@Test
	public void queryTest() {
		Pager<BaseSmsNoticeDTO> page = new Pager<BaseSmsNoticeDTO>();
		ExecuteResult<DataGrid<BaseSmsNoticeDTO>> result = baseSmsNoticeService.queryBaseSmsNotice(null, page);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}
	
	@Test
	public void insertTest() {
		BaseSmsNoticeDTO noticeDTO = new BaseSmsNoticeDTO();
		noticeDTO.setNoticeName("胥明忠");
		noticeDTO.setNoticePhone("13913037054");
		noticeDTO.setCreateId(1L);
		noticeDTO.setCreateName("admin");
		ExecuteResult<String> result = baseSmsNoticeService.insertBaseSmsNotice(noticeDTO);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}
	
	@Test
	public void deleteTest(){
		BaseSmsNoticeDTO noticeDTO = new BaseSmsNoticeDTO();
		noticeDTO.setId(1L);
		noticeDTO.setModifyId(1L);
		noticeDTO.setModifyName("sa");
		ExecuteResult<String> result = baseSmsNoticeService.deleteBaseSmsNotice(noticeDTO);
		System.out.println("-------------" + JSONObject.toJSONString(result));
	}
}
