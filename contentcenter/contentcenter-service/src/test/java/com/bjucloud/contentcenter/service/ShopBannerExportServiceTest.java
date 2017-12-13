package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.ShopBannerDTO;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by thinkpad on 2017/2/8.
 */
public class ShopBannerExportServiceTest {

    ApplicationContext ctx = null;
    private ShopBannerService shopBannerService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        shopBannerService = (ShopBannerService) ctx.getBean("shopBannerService");

    }

    @Test
    public void testQueryBySellerCode(){ 
    	ShopBannerDTO banner = new ShopBannerDTO();
    	banner.setShopId("532");
        ExecuteResult<List<ShopBannerDTO>> result =shopBannerService.queryVMSBannerBySellerCode(banner);
        System.out.println(result.toString());
    }

}
