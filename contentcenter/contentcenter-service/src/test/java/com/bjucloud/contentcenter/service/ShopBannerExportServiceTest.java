package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;

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
    
    /**
     * 根据shopId查询banner信息
     */
    @Test
    public void queryByShopIdTest(){ 
    	String shopId = "532";
        ExecuteResult<List<ShopBannerDTO>> executeResult =shopBannerService.queryByShopId(shopId);
    	if(executeResult.isSuccess()){
    		System.out.println("===>executeResult:" + executeResult.getResult());
    	}else{
    		System.out.println("===>查询失败！！！");
    	}
    }
    

}
