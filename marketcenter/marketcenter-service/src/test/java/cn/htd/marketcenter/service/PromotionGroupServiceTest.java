package cn.htd.marketcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.CommonTest;
import cn.htd.marketcenter.dto.PromotionGroupSignUpDTO;
import cn.htd.marketcenter.dto.PromotionGroupSignUpRepairDTO;
import cn.htd.marketcenter.service.PromotionGroupService;

/**
 * Created by haozy on 2017/05/15.
 */
public class PromotionGroupServiceTest extends CommonTest{
//	ApplicationContext ctx;
	@Resource
	private PromotionGroupService promotionGroupService;

//	@Before
//	public void setUp() throws Exception {
////		ctx = new ClassPathXmlApplicationContext("test.xml");
////		promotionGroupService = (PromotionGroupService) ctx.getBean("promotionGroupService");
//	}

	// @Test
	// public void insertPromotionGroupSignUpInfo() {
	// PromotionGroupSignUpDTO promotionGroupSignUpDTO = new
	// PromotionGroupSignUpDTO();
	// promotionGroupSignUpDTO.setBuyerAddressProvince("02");
	// promotionGroupSignUpDTO.setBuyerAddressCity("0201");
	// promotionGroupSignUpDTO.setBuyerAddressDistrict("020105");
	// promotionGroupSignUpDTO.setBuyerAddressTown("020105006");
	// promotionGroupSignUpDTO.setBuyerAddressDetail("测试地址");
	// promotionGroupSignUpDTO.setBuyerCode("htd1000000");
	// promotionGroupSignUpDTO.setBuyerMobile("13813812321");
	// promotionGroupSignUpDTO.setBuyerName("测试");
	// promotionGroupSignUpDTO.setPromotionId(1L);
	// promotionGroupSignUpDTO.setSignupChannel("0");
	// promotionGroupSignUpDTO.setProductNum(10);
	// ExecuteResult<Boolean> result =
	// promotionGroupService.insertPromotionGroupSignUpInfo(promotionGroupSignUpDTO);
	// System.out.print(result.isSuccess());
	// }

	@Test
	public void query() {
		PromotionGroupSignUpDTO promotionGroupSignUpDTO = new PromotionGroupSignUpDTO();
		promotionGroupSignUpDTO.setPromotionId(1L);
		ExecuteResult<PromotionGroupSignUpDTO> result = promotionGroupService
				.queryPromotionGroupSignUpInfo(promotionGroupSignUpDTO);
		System.out.print(result.isSuccess());
	}
	
	@Test
	public void repairPromotionGroupSignUp(){
		List<PromotionGroupSignUpRepairDTO> list=new ArrayList<PromotionGroupSignUpRepairDTO>();
		
		PromotionGroupSignUpRepairDTO p1=new PromotionGroupSignUpRepairDTO();
		p1.setMemberCode("htd888888");
		p1.setMobile("15250991603");
		p1.setRecevierName("张小龙");
		list.add(p1);
		PromotionGroupSignUpRepairDTO p2=new PromotionGroupSignUpRepairDTO();
		p2.setMemberCode("htd888888");
		p2.setMobile("15250991603");
		p2.setRecevierName("张小龙");
		list.add(p2);
		promotionGroupService.repairPromotionGroupSignUpInfo(list);
	}

}
