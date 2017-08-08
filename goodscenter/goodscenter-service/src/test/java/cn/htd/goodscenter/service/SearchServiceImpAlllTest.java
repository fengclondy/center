package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.SearchInDTO;
import cn.htd.goodscenter.dto.SearchOutDTO;
import cn.htd.goodscenter.service.task.MailErpExceptionBrandTask;
import cn.htd.goodscenter.test.common.CommonTest;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;

public class SearchServiceImpAlllTest extends CommonTest{

    @Resource
    private SearchItemExportService searchService;

    @Test
    public void testSearchItem(){
    	SearchInDTO inDTO = new SearchInDTO();
        inDTO.setOrderSort(6);
        ArrayList<Long> itemIds = new ArrayList<Long>();
		itemIds.add(1000004465L);
		itemIds.add(1000008529L);
		itemIds.add(1000000459L);
		inDTO.setItemIds(itemIds);
    	SearchOutDTO result = this.searchService.searchItem(inDTO, null);
    	Assert.assertNotEquals(null, result);
    }

    @Test
    public void testDeleteItemValidation(){
        SearchInDTO searchInDTO = new SearchInDTO();
        Long cid = 9L;
        searchInDTO.setShopCid(cid);
        SearchOutDTO searchOutDTO = searchService.deleteItemValidation(searchInDTO, null);
        System.out.println("success...");
    }

    @Autowired
    private MailErpExceptionBrandTask mailErpExceptionBrandTask;


    @Test
    public void testMsgSend() throws Exception {
        mailErpExceptionBrandTask.selectTasks("15", "", 1, null, 500);
    }
}
