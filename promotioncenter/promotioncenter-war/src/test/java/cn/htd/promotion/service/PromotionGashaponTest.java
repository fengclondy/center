package cn.htd.promotion.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.service.AwardRecordService;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by xw on 2017/8/23.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class PromotionGashaponTest {

	@Resource
	private PromotionInfoService  promotionInfoService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getAwardRecordByPromotionIdTest(){
    	PromotionInfoReqDTO promotionInfoReqDTO = new PromotionInfoReqDTO();
//    	promotionInfoReqDTO.setModifyTime("2019-04-05 10:0:01");
//    	promotionInfoReqDTO.setEffectiveTimestr("2017-06-28 00:00:00");
//    	promotionInfoReqDTO.setInvalidTimestr("2017-06-28 00:00:00");
//    	promotionInfoReqDTO.setModifyTime("2017-09-04");
    	//promotionInfoReqDTO.setPromotionName("下");
    	promotionInfoReqDTO.setPromotionType("24");
    	Pager<PromotionInfoReqDTO> pager = new Pager<PromotionInfoReqDTO>();
    
        
    	pager.setPage(1);
        
    	pager.setRows(10);
//    	
//    	promotionInfoService.getPromotionGashaponByInfo(dto, pager);
//
//    	DataGrid<PromotionInfoDTO> result =  promotionInfoService.getPromotionGashaponByInfo(dto, pager);
//        System.out.printf("result:"+result);
        
        
		ExecuteResult<DataGrid<PromotionInfoDTO>> result = new ExecuteResult<DataGrid<PromotionInfoDTO>>();
        try{
                DataGrid<PromotionInfoDTO>promotionInfoList = promotionInfoService.getPromotionGashaponByInfo(promotionInfoReqDTO, pager);
                result.setResult(promotionInfoList);
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                if(promotionInfoList.getSize() == 0|| promotionInfoList ==null){
                    result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
                }else{
                    result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
                }
                for (int i = 0; i < result.getResult().getRows().size(); i++) {
                	System.out.println(result.getResult().getRows().get(i).getPromotionName());
                	System.out.println(result.getResult().getRows().get(i).getEffectiveTime());
                	System.out.println(result.getResult().getRows().get(i).getInvalidTime());
				}
                System.out.printf("<><><><>result:"+result.getResult().getRows().size());
        }catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
        }
    }

}
