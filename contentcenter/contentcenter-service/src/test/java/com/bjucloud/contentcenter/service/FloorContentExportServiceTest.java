package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.FloorContentDTO;
import com.bjucloud.contentcenter.dto.FloorContentPicSubDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/8.
 */
public class FloorContentExportServiceTest {

    ApplicationContext ctx = null;
    private FloorContentExportService floorContentExportService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        floorContentExportService = (FloorContentExportService) ctx.getBean("floorContentExportService");

    }


    @Test
    public void testQueryByNavId(){
        ExecuteResult<FloorContentDTO> result = floorContentExportService.queryByNavId(1l);
        System.out.println("heh");
    }

//    @Test
//    public void testSaveOrUpdateFloorContent(){
//        FloorContentDTO floorContentDTO = new FloorContentDTO();
//        List<FloorContentPicSubDTO> list = new ArrayList<FloorContentPicSubDTO>();
//        floorContentDTO.setNavId(2l);
//        floorContentDTO.setStartTime(new Date());
//        floorContentDTO.setEndTime(new Date());
//        floorContentDTO.setShowBrand(0l);
//        FloorContentPicSubDTO floorContentPicSubDTO = new FloorContentPicSubDTO();
//        floorContentPicSubDTO.setPicUrl("test4");
//        floorContentPicSubDTO.setLinkUrl("test4");
//        floorContentPicSubDTO.setSortNum(1l);
//
//        FloorContentPicSubDTO floorContentPicSubDTO1 = new FloorContentPicSubDTO();
//        floorContentPicSubDTO1.setPicUrl("test5");
//        floorContentPicSubDTO1.setLinkUrl("test5");
//        floorContentPicSubDTO1.setSortNum(2l);
//
//        list.add(floorContentPicSubDTO);
//        list.add(floorContentPicSubDTO1);
//        floorContentDTO.setPicSubDTOs(list);
//        ExecuteResult<String> result = floorContentExportService.saveOrUpdateFloorContent(floorContentDTO);
//        System.out.println("heh");
//    }
}
