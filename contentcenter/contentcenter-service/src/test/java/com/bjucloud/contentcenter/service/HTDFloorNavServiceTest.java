package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.FloorQueryOutDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/1/20.
 */
public class HTDFloorNavServiceTest {
    ApplicationContext ctx = null;
    private HTDFloorNavService htdFloorNavService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        htdFloorNavService = (HTDFloorNavService) ctx.getBean("htdFloorNavService");

    }

    @Test
    public void testQueryById(){
        ExecuteResult<HTDFloorNavDTO> result = htdFloorNavService.queryById(1l);
        System.out.println("heh");
    }

    @Test
    public void testmodifyFloorNavById(){
        HTDFloorNavDTO dto = new HTDFloorNavDTO();
        dto.setStatus("2");
        dto.setId(1l);
        ExecuteResult<String> result = htdFloorNavService.modifyFloorNavById(dto);
        System.out.println("he");
    }

    @Test
    public void testQueryFloorAndNavListByCondition(){
        FloorQueryOutDTO dto = new FloorQueryOutDTO();
        ExecuteResult<DataGrid<FloorQueryOutDTO>> result = htdFloorNavService.queryFloorAndNavListByCondition(dto,null);
        System.out.println("heh");
    }

    @Test
    public void testQueryBySortNum(){
        DataGrid<HTDFloorNavDTO> list = htdFloorNavService.queryBySortNum(2l);
        System.out.println("sdf");
    }

    @Test
    public void testQueryByFloorId(){
        DataGrid<HTDFloorNavDTO> list = htdFloorNavService.queryByFloorId(1l);
        System.out.println("sdf");
    }

    @Test
    public void testQueryNavName(){
        DataGrid<HTDFloorNavDTO> dataGrid = htdFloorNavService.queryNavName();
        System.out.println("sdf");
    }
}
