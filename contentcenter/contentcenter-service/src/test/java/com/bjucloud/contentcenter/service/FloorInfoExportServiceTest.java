package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.FloorContentDTO;
import com.bjucloud.contentcenter.dto.FloorInfoDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/2/17.
 */
public class FloorInfoExportServiceTest {
    ApplicationContext ctx = null;
    private FloorInfoService floorInfoService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        floorInfoService = (FloorInfoService) ctx.getBean("floorInfoService");

    }

    @Test
    public void testQuyerListAll(){

        DataGrid<FloorInfoDTO> dataGrid = floorInfoService.queryListAll(new Pager());

        System.out.println("sfas");







    }

}
