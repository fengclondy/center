package cn.htd.goodscenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.SuperbossProductPushDTO;
import cn.htd.goodscenter.test.common.CommonTest;

public class SuperbossProductPushTest extends CommonTest {

	@Resource
	private SuperbossProductPushService superbossProductPushService;

	// @Test
	// public void testDeleteItemValidation() {
	// Pager pager = new Pager();
	// pager.setPage(1);
	// pager.setRows(10);
	// SuperbossProductPushDTO superbossProductPushDTO = new
	// SuperbossProductPushDTO();
	// ExecuteResult<DataGrid<SuperbossProductPushDTO>> result =
	// superbossProductPushService
	// .querySuperbossProductPushList(superbossProductPushDTO, pager);
	// System.out.println(result);
	// }

	// @Test
	// public void testInsert() {
	// SuperbossProductPushDTO superbossProductPushDTO = new
	// SuperbossProductPushDTO();
	// superbossProductPushDTO.setSkuCode("HTDH_0000131298");
	// superbossProductPushDTO.setSortNum(1);
	// superbossProductPushDTO.setRecommendClass("1");
	// superbossProductPushService.insertSuperbossProductPush(superbossProductPushDTO);
	// }

	// @Test
	// public void testUpdate() {
	// SuperbossProductPushDTO superbossProductPushDTO = new
	// SuperbossProductPushDTO();
	// superbossProductPushDTO.setSkuCode("HTDH_0000131298");
	// superbossProductPushDTO.setSortNum(2);
	// superbossProductPushDTO.setRecommendClass("2");
	// superbossProductPushService.updateSuperbossProductPush(superbossProductPushDTO);
	// }

	@Test
	public void testDelete() {
		List<SuperbossProductPushDTO> list = new ArrayList<SuperbossProductPushDTO>();
		SuperbossProductPushDTO superbossProductPushDTO = new SuperbossProductPushDTO();
		superbossProductPushDTO.setSkuCode("HTDH_0000131501");
		SuperbossProductPushDTO superbossProductPushDTO2 = new SuperbossProductPushDTO();
		superbossProductPushDTO2.setSkuCode("HTDH_0000131417");
		list.add(superbossProductPushDTO);
		list.add(superbossProductPushDTO2);
		ExecuteResult<String> result = superbossProductPushService.deleteSuperbossProductPush(list);
		System.out.println(result);
	}

	// @Test
	// public void testDetail() {
	// SuperbossProductPushDTO superbossProductPushDTO = new
	// SuperbossProductPushDTO();
	// superbossProductPushDTO.setSkuCode("HTDH_0000131298");
	// ExecuteResult<SuperbossProductPushDTO> result =
	// superbossProductPushService
	// .querySuperbossProductPushInfo(superbossProductPushDTO);
	// System.out.println(result);
	// }

}
