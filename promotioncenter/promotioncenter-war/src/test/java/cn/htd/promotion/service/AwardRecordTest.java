package cn.htd.promotion.service;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.biz.service.AwardRecordService;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.request.SeckillOrderReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Test
    public void getAwardRecordByPromotionIdTest(){
    	try {
            String messageId = "342453251345";
            PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
            dto.setPromotionId("21171439460190");
            dto.setPromotionType("21");
            dto.setPage(1);
            dto.setPageSize(10);
           // dto.setBuyerName("买");
            DataGrid<PromotionAwardDTO> result =  awardRecordService.getAwardRecordByPromotionId(dto,messageId);
            System.out.printf("result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @Test
    @Rollback(false)
    public void updateLogisticsInfoTest(){
        try {
            String messageId = "342453251345";
            PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
            dto.setId(1L);
            dto.setLogisticsStatus("00");
            dto.setLogisticsCompany("顺丰");
            dto.setLogisticsNo("334532");
            dto.setModifyTime(new Date());
            dto.setModifyId(343L);
            dto.setModifyName("测试哈哈哈哈");
            dto.setPromotionId("NDJ00010000002");

            int result =  awardRecordService.updateLogisticsInfo(dto,messageId);
            System.out.printf("result:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkOrderTest(){
        Boolean exist = awardRecordService.checkOrder("4563623");
        System.out.println(exist);
    }

    @Test
    @Rollback(false)
    public void insertOrderTest() {
        PromotionAwardReqDTO awardReqDTO = new PromotionAwardReqDTO();
        awardReqDTO.setOrderNo("111111111");
        awardReqDTO.setRewardName("测试商品哈哈哈");
        awardReqDTO.setAwardValue("1000.13");
        awardReqDTO.setWinningContact("1020");
        awardReqDTO.setBelongSuperiorName("哈哈哈");
        awardReqDTO.setBuyerName("汇通达");
        awardReqDTO.setSellerAddress("南京钟灵街");
        awardReqDTO.setOrderStatus("00");
        awardReqDTO.setWinningTime(new Date());
        awardReqDTO.setPromotionName("商品名称");
        awardReqDTO.setPromotionId("21171612120221");
        awardReqDTO.setSellerCode("1020");
        awardReqDTO.setWinnerName("领奖人姓名");
        Integer result = awardRecordService.insertOrder(awardReqDTO);
        System.out.println(result);
    }

    @Test
    @Rollback(false)
    public void updateOrderTest() {
        PromotionAwardReqDTO awardReqDTO = new PromotionAwardReqDTO();
        awardReqDTO.setOrderNo("111111111");
        awardReqDTO.setRewardName("测试商1");
        awardReqDTO.setAwardValue("10010.13");
        awardReqDTO.setWinningContact("10201");
        awardReqDTO.setBelongSuperiorName("哈哈哈1");
        awardReqDTO.setBuyerName("汇通达1");
        awardReqDTO.setSellerAddress("南京钟灵1");
        awardReqDTO.setOrderStatus("待发货1");
        awardReqDTO.setWinningTime(new Date());
        awardReqDTO.setPromotionName("商品名称");
        awardReqDTO.setPromotionId("21171612120221");
        awardReqDTO.setSellerCode("1020");
        awardReqDTO.setWinnerName("领奖人姓名");
        Integer result = awardRecordService.updateOrder(awardReqDTO);
        System.out.println(result);
    }

}
