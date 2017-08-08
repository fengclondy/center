package cn.htd.goodscenter.service.vip;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.vip.VipItemAddInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListOutDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import net.sf.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/1/19.
 */
public class VipItemExportServiceTest extends CommonTest {

    @Autowired
    private VipItemExportService vipItemExportService;

    @Test
    public void testQueryItemInfoBySkuCode() {
        ExecuteResult<ItemSkuDTO> executeResult = this.vipItemExportService.queryItemInfoBySkuCode("10011533");
        System.out.println(executeResult.getResult());
    }
//    -- 1000029427  1000029428  1000029429
    @Test
    public void testAddOrModifyVipItemEntry() {
        Long itemId = 1000029427L;
        Integer vipItemType = 1;
        Integer vipSyncFlag = 1;
        List< VipItemAddInDTO > vipItemAddInDTOList = new ArrayList<>();
        VipItemAddInDTO vipItemAddInDTO = new VipItemAddInDTO();
        vipItemAddInDTO.setSkuCode("10011533");
        vipItemAddInDTO.setSalePrice(1002L);
        vipItemAddInDTO.setBasePrice(1002L);
        vipItemAddInDTO.setSupplierName("汇通达汇通达111");

        VipItemAddInDTO vipItemAddInDTO1 = new VipItemAddInDTO();
        vipItemAddInDTO1.setSkuCode("10011532");
        vipItemAddInDTO1.setSalePrice(1002L);
        vipItemAddInDTO1.setBasePrice(1002L);
        vipItemAddInDTO1.setSupplierName("汇通达汇通达112");

        vipItemAddInDTOList.add(vipItemAddInDTO);
        vipItemAddInDTOList.add(vipItemAddInDTO1);
        this.vipItemExportService.addOrModifyVipItemEntry(itemId, vipItemType, vipSyncFlag, vipItemAddInDTOList);
    }

    @Test
    public void testQueryVipItemList() {
        VipItemListInDTO vipItemListInDTO = new VipItemListInDTO();
        vipItemListInDTO.setVipSkuCode("1000009351");
//        vipItemListInDTO.setVipItemType(1);
        ExecuteResult<List<VipItemListOutDTO>> executeResult = vipItemExportService.queryVipItemList(vipItemListInDTO);
        Assert.assertTrue(executeResult.isSuccess());
        List<VipItemListOutDTO> list = executeResult.getResult();
        System.out.println(JSONArray.fromObject(list));
    }


}
