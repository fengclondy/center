package cn.htd.goodscenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemCategory;
import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.indto.QueryItemCategoryInDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2016/12/27.
 */
public class ItemCategoryServiceTest {
    ApplicationContext ctx;
    private ItemCategoryService itemCategoryService;

    @Test
    public void  testimportCategoryAttribute() {
        itemCategoryService.importCategoryAttribute();
    }

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        itemCategoryService = (ItemCategoryService) ctx.getBean("itemCategoryService");
    }



    @Test
    public void testAddItemAttrSeller() {
        CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
        ItemAttrDTO attr = new ItemAttrDTO();
        attr.setName("测试商品属性2");
        inDTO.setCreateId(110l);
        inDTO.setCreateName("周杰伦");
        inDTO.setModifyId(999l);
        inDTO.setModifyName("刘德华");
        inDTO.setAttr(attr);
        inDTO.setCid(1L);
        inDTO.setSellerId(1L);
        inDTO.setShopId(1L);
        inDTO.setAttrType(1);
        inDTO.setSortNumber(1);
        inDTO.setSelectType(1l);
        ExecuteResult<ItemAttrDTO> result = itemCategoryService.addItemAttrSeller(inDTO);
        Assert.assertEquals(true, result.isSuccess());
    }


    @Test
    public void testAddItemAttrValueSeller() {
        CatAttrSellerDTO inDTO = new CatAttrSellerDTO();
        ItemAttrDTO attr = new ItemAttrDTO();
        ItemAttrValueDTO dto = new ItemAttrValueDTO();
        dto.setAttrId(31l);
        dto.setName("白色");
        dto.setIndexKey("b");
        inDTO.setSellerId(1l);
        inDTO.setShopId(1l);
        inDTO.setCid(1l);
        inDTO.setSortNumber(1);
        inDTO.setCreateId(123l);
        inDTO.setCreateName("周杰伦");
        inDTO.setModifyId(999l);
        inDTO.setModifyName("刘德华");
        inDTO.setAttrValue(dto);
        ExecuteResult<ItemAttrValueDTO> result = itemCategoryService.addItemAttrValueSeller(inDTO);
        Assert.assertEquals(true, result.isSuccess());
    }



    @Test
    public void testQueryCatAttrSellerList(){
        CatAttrSellerDTO dto = new CatAttrSellerDTO();
        dto.setAttrType(2);
        dto.setSellerId(1l);
        dto.setShopId(1l);
        dto.setAttrStatus(0);
        dto.setCid(1l);
        ExecuteResult<List<ItemAttrDTO>> result = itemCategoryService.queryCatAttrSellerList(dto);
        System.out.println();
    }

    @Test
    public void testqueryItemCategoryList4SuperBoss() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -60);
        QueryItemCategoryInDTO queryItemCategoryInDTO = new QueryItemCategoryInDTO();
        queryItemCategoryInDTO.setStartTime(calendar.getTime());
        queryItemCategoryInDTO.setEndTime(new Date());
        Pager Pager = new Pager(2, 10);
        itemCategoryService.queryItemCategoryList4SuperBoss(queryItemCategoryInDTO, Pager);
    }

    @Test
    public void testgetCategoryListByCids() {
        Long[] cids = {10L, 13l};
        itemCategoryService.getCategoryListByCids(Arrays.asList(cids));
    }
}
