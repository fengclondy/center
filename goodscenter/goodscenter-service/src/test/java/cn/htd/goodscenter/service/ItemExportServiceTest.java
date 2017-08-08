package cn.htd.goodscenter.service;

import java.util.ArrayList;
import java.util.List;

import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.*;
import cn.htd.goodscenter.dto.indto.QueryItemStockDetailInDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

/**
 * Created by thinkpad on 2017/1/4.
 */
public class ItemExportServiceTest {
    ApplicationContext ctx;
    private ItemExportService itemExportService;


    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        itemExportService = (ItemExportService) ctx.getBean("itemExportService");
    }

    @Test
    public void testQueryItemList(){
        ItemQueryInDTO dto = new ItemQueryInDTO();
        dto.setSellerId(1l);
        DataGrid<ItemQueryOutDTO> dtoDataGrid = itemExportService.queryItemList(dto,null);
        System.out.println("heh");
    }

    @Test
    public void testQuerySellerItemList(){
        ItemQueryInDTO dto = new ItemQueryInDTO();
        dto.setSellerId(1l);
        dto.setSkuCode("1000029374");
        DataGrid<ItemQueryOutDTO> dtoDataGrid = itemExportService.querySellerItemList(dto,null);
        System.out.println("heh");
    }

    @Test
    public void testModifySellerItemStatus(){
        ItemStatusModifyDTO dto = new ItemStatusModifyDTO();
        List<Long> itemIds = new ArrayList<Long>();
        itemIds.add(1000018838l);
        itemIds.add(1000018839l);
        dto.setItemIds(itemIds);
        dto.setItemStatus(4);
        dto.setUserId(999l);
        dto.setUserName("周杰伦");
        ExecuteResult<String> result = itemExportService.modifySellerItemStatus(dto);
        System.out.println("heh");
    }

    @Test
    public void testGetItemById(){
        ExecuteResult<ItemDTO> result = itemExportService.getItemById(1000018849l,1);
        System.out.println("hehe");
    }

    @Test
    public void testModifyItemAdBatch(){
        List<ItemAdDTO> list = new ArrayList<ItemAdDTO>();
        ItemAdDTO dto = new ItemAdDTO();
        dto.setItemId(1000012111l);
        dto.setAd("这是一条华丽丽的广告！");
        ItemAdDTO dto1 = new ItemAdDTO();
        dto1.setItemId(1000018838l);
        dto1.setAd("呵呵哒");
        list.add(dto);
        list.add(dto1);
        itemExportService.modifyItemAdBatch(list);

    }

    @Test
    public void testModifyItemShopCidBatch(){
        List<ItemShopCidDTO> list = new ArrayList<ItemShopCidDTO>();
        ItemShopCidDTO dto = new ItemShopCidDTO();
        dto.setItemId(1000012111l);
        dto.setShopCid(1l);
        ItemShopCidDTO dto1 = new ItemShopCidDTO();
        dto1.setItemId(1000018838l);
        dto1.setShopCid(1l);
        list.add(dto);
        list.add(dto1);
        itemExportService.modifyItemShopCidBatch(list);

    }
    
    @Test
    public void testAuditInternalSupplierItem(){
    	ItemDTO itemDTO=new ItemDTO();
    	itemDTO.setItemId(233830L);
    	itemDTO.setModifyId(1234L);
    	itemDTO.setModifyName("测试修改人");
    	itemDTO.setItemStatus(HtdItemStatusEnum.PASS.getCode());
        itemDTO.setErpFirstCategoryCode("15");
        itemDTO.setErpFiveCategoryCode("1501010101");
    	itemExportService.auditInternalSupplierItem(itemDTO);
    }

    @Test
    public void testqueryItemListForSaleManageSystem(){
//        ItemDTO itemDTO=new ItemDTO();
//        itemDTO.setItemId(1000018840L);
//        itemDTO.setModifyId(1234L);
//        itemDTO.setModifyName("测试修改人");
//        itemDTO.setItemStatus(HtdItemStatusEnum.PASS.getCode());
        ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
        itemInDTO.setCid(11L);
        itemInDTO.setItemStatus(3);
        itemExportService.queryItemListForSaleManageSystem(itemInDTO, null);
    }

    @Test
    public void testRedisInc() {
        System.out.println(ItemCodeGenerator.generateSpuCode());
    }

    @Test
    public void testQueryWarnItemDownErp(){
        Pager page=new Pager();
        page.setPageOffset(0);
        page.setRows(40);
        DataGrid<ItemWaringOutDTO> result=itemExportService.queryPagedFailedItemWarningList(page);
        Assert.assertNotNull(result.getRows());

    }

    @Test
    public void testqueryItemQuantityInfo() {

        QueryItemStockDetailInDTO queryItemStockDetailInDTO = new QueryItemStockDetailInDTO();
        queryItemStockDetailInDTO.setItemCode("HTDH_0000067773");
        queryItemStockDetailInDTO.setIsBoxFlag(1);
        itemExportService.queryItemQuantityInfo(queryItemStockDetailInDTO);

    }
}
