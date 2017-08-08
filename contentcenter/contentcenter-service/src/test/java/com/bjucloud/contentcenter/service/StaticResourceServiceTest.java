package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import com.bjucloud.contentcenter.dto.StaticResourceDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/1/20.
 */
public class StaticResourceServiceTest {
    ApplicationContext ctx = null;
    private StaticResourceService staticResourceService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        staticResourceService = (StaticResourceService) ctx.getBean("staticResourceService");

    }

    @Test
    public void testSueryListByCondition(){
        DataGrid<StaticResourceDTO> dataGrid = staticResourceService.queryListByCondition(null, null);
    }
}
