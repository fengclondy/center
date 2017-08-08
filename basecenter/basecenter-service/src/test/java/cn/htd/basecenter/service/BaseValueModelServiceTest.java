package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.BaseValueModelDTO;
import cn.htd.common.DataGrid;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by thinkpad on 2016/12/21.
 */
public class BaseValueModelServiceTest {
    ApplicationContext ctx;
    private BaseValueModelService baseValueModelService;


    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        baseValueModelService = (BaseValueModelService) ctx.getBean("baseValueModelService");
    }






    @Test
    public void queryNewValueBySellerIdTest(){
        DataGrid<BaseValueModelDTO> dataGrid = baseValueModelService.queryNewValueBySellerId(519L);
        System.out.println("hah");



    }



}
