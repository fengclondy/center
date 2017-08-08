package cn.htd.storecenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.storecenter.dto.ShopFavouriteDTO;
import cn.htd.storecenter.service.ShopFavouriteService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/1/10.
 */
public class ShopFavouriteServiceTest {
    ApplicationContext ctx = null;
    private ShopFavouriteService shopFavouriteService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        shopFavouriteService = (ShopFavouriteService) ctx.getBean("shopFavouriteService");
    }

    @Test
    public void testAddFavouriteShop(){
        ShopFavouriteDTO dto = new ShopFavouriteDTO();
        dto.setModifyName("周杰伦");
        dto.setModifyId(123l);
        dto.setCreateName("陈冠希");
        dto.setCreateId(111l);
        dto.setBuyerId(1l);
        dto.setSellerId(123l);
        dto.setDeleted(0);
        dto.setShopId(123l);
        dto.setUserId(889l);
        shopFavouriteService.addFavouriteShop(dto);
    }

    @Test
    public void testQueryFavouriteShopList(){
        ShopFavouriteDTO dto = new ShopFavouriteDTO();
        dto.setBuyerId(1l);
        DataGrid<ShopFavouriteDTO> dataGrid = shopFavouriteService.queryFavouriteShopList(dto,null);
        System.out.println("sdf");
    }



}
