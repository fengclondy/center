package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import com.bjucloud.contentcenter.dto.SpecialResourceDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/2/13.
 */
public class SpecialResourceServiceTest {
    ApplicationContext ctx = null;
    private SpecialResourceService specialResourceService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        specialResourceService = (SpecialResourceService) ctx.getBean("specialResourceService");
    }

    @Test
    public void testQueryListByCondition(){
        SpecialResourceDTO dto = new SpecialResourceDTO();
        dto.setName("625单品");
        DataGrid<SpecialResourceDTO> dtoDataGrid = specialResourceService.queryListByCondition(dto,null);
        System.out.println("hehe");
    }
}
