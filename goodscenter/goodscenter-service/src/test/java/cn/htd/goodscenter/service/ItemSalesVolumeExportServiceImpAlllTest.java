package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.ItemSalesVolumeDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import cn.htd.common.ExecuteResult;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
 
public class ItemSalesVolumeExportServiceImpAlllTest extends CommonTest {

    @Resource
    private ItemSalesVolumeExportService itemSalesVolumeExportService;
    
    @Test
    public void testUpdateItemSalesVolume(){
    	List<ItemSalesVolumeDTO> inList = new ArrayList<ItemSalesVolumeDTO>();
    	ItemSalesVolumeDTO inDTO = new ItemSalesVolumeDTO();
    	inDTO.setItemId(1L);
    	inDTO.setSalesVolume(100);
    	inDTO.setSellerId(1L);
    	inDTO.setShopId(1L);
    	inDTO.setSkuId(1L);
    	inList.add(inDTO);
    	ExecuteResult<String> result = this.itemSalesVolumeExportService.updateItemSalesVolume(inList);
    	Assert.assertNotEquals(null, result);
    }
	
}
