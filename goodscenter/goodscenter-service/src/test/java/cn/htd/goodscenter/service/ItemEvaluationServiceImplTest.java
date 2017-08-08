package cn.htd.goodscenter.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemEvaluationDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryInDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryOutDTO;
import cn.htd.goodscenter.dto.ItemEvaluationReplyDTO;
import cn.htd.goodscenter.test.common.CommonTest;

public class ItemEvaluationServiceImplTest extends CommonTest {

	@Resource
	protected ItemExportService itemExportService;
	@Resource
	protected ItemEvaluationService itemEvaluationService;

	@Test
	public void testAddItemEvaluation() {
		ItemEvaluationDTO itemEvaluationDTO = new ItemEvaluationDTO();
		itemEvaluationDTO.setUserId(1L);
		itemEvaluationDTO.setUserShopId(2L);
		itemEvaluationDTO.setByUserId(3L);
		itemEvaluationDTO.setByShopId(4L);
		itemEvaluationDTO.setOrderId(5L);
		itemEvaluationDTO.setSkuId(6L);
		itemEvaluationDTO.setItemId(7L);
		itemEvaluationDTO.setSkuScope(2);
		itemEvaluationDTO.setContent("一般般");
		itemEvaluationDTO.setReplaceContent("一般般");
		itemEvaluationDTO.setType("1");
		ExecuteResult<ItemEvaluationDTO> result = this.itemEvaluationService.addItemEvaluation(itemEvaluationDTO);
		Assert.assertEquals(result.isSuccess(), true);
	}

	@Test
	public void testAddItemEvaluationReply() {
		ItemEvaluationReplyDTO itemEvaluationReplyDTO = new ItemEvaluationReplyDTO();
		itemEvaluationReplyDTO.setEvaluationId(1L);
		itemEvaluationReplyDTO.setContent("这是为什么呢?");
		ExecuteResult<ItemEvaluationReplyDTO> result = this.itemEvaluationService.addItemEvaluationReply(itemEvaluationReplyDTO);
		Assert.assertEquals(result.isSuccess(), true);
	}

	@Test
	public void testQueryItemEvaluationList() {
		ItemEvaluationQueryInDTO itemEvaluationQueryInDTO = new ItemEvaluationQueryInDTO();
		try {
			
			Long[] userIds = new Long[] { 1L };
			itemEvaluationQueryInDTO.setUserIds(userIds);
			DataGrid<ItemEvaluationQueryOutDTO> dg = this.itemEvaluationService.queryItemEvaluationList(itemEvaluationQueryInDTO, null);
			for (int i = 0; i < dg.getTotal(); i++) {
				System.out.println(dg.getRows().get(i).getOrderId());
			}
			Assert.assertEquals(dg != null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryItemEvaluationReplyList() {
		ItemEvaluationReplyDTO itemEvaluationReplyDTO = new ItemEvaluationReplyDTO();
		itemEvaluationReplyDTO.setEvaluationId(1l);
		DataGrid<ItemEvaluationReplyDTO> dg = itemEvaluationService.queryItemEvaluationReplyList(itemEvaluationReplyDTO,null);
		for (int i = 0; i < dg.getTotal(); i++) {
			System.out.println(dg.getRows().get(i).getContent());
		}
		Assert.assertEquals(dg != null, true);

	}

	@Test
	public void testUpdFlagItemEvaluationById() {
		Long itemId = 249L;
		Integer flag = 2;
		ExecuteResult<String> result = itemEvaluationService.updFlagItemEvaluationById(itemId, flag);

		if (result.isSuccess()) {
			System.out.println(">>>>>>>>>>>>>>> " + result.getResult());
		} else {
			System.out.println(">>>>>>>>>>>>>>> ");
			List<String> emList = result.getErrorMessages();
			for (String em : emList) {
				System.out.print(em);
			}
		}
	}

}
