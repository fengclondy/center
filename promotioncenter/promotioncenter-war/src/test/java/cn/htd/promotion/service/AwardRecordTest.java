package cn.htd.promotion.service;

import cn.htd.promotion.cpc.biz.service.AwardRecordService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by tangjiayong on 2017/8/22.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class AwardRecordTest {

    @Resource
    private AwardRecordService awardRecordService;

    @Before
    public void setUp() throws Exception {

    }

//    @Test
//    public void getAwardRecordByPromotionIdTest(){
//    	try {
//            String messageId = "342453251345";
//            PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
//            dto.setPromotionId("NDJ00010000001");
//            dto.setPage(1);
//            dto.setPageSize(10);
//            dto.setBuyerName("买");
//            DataGrid<PromotionAwardDTO> result =  awardRecordService.getAwardRecordByPromotionId(dto,messageId);
//            System.out.printf("result:"+result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//    }

}
