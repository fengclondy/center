package cn.htd.promotion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;

/**
 * Created by zf.zhang on 2017/9/1.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class TimelimitedTestUnit {

    @Resource
    private TimelimitedInfoService timelimitedInfoService;
    
    @Resource
    private GeneratorUtils noGenerator;

    @Before
    public void setUp() throws Exception {

    }

    /**
     * 添加秒杀活动
     */
	@Test
	@Rollback(false) 
    public void addTimelimitedInfoTest(){
    	try {
            String messageId = "342453251349";
    		Long userId = 10001L;
    		String userName = "admin";
    		
    		/**
    		 * 促销活动编码生成方法
    		 * @param platCode 促销活动类型 1:优惠券，2:秒杀，3:扭蛋，4:砍价，5:总部秒杀
    		 * @return
    		 */
    		String promotionId = noGenerator.generatePromotionId("5");
    		
    		String levelCode = noGenerator.generatePromotionLevelCode(promotionId);
            
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();
            
        	//商品图片
        	List<TimelimitedSkuPictureReqDTO> timelimitedSkuPictureReqDTOList = new ArrayList<TimelimitedSkuPictureReqDTO>();
        	TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = new TimelimitedSkuPictureReqDTO();;
        	timelimitedSkuPictureReqDTO.setPictureUrl("/img/123.jpg");
        	timelimitedSkuPictureReqDTOList.add(timelimitedSkuPictureReqDTO);
        		
        	//商品详情
        	TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = new TimelimitedSkuDescribeReqDTO();
        	timelimitedSkuDescribeReqDTO.setDescribeContent("测试商品详情^_^");
            
        	//秒杀商品信息
    		TimelimitedInfoReqDTO timelimitedInfoReqDTO = new TimelimitedInfoReqDTO();
    		//设置图片
    		timelimitedInfoReqDTO.setTimelimitedSkuPictureReqDTOList(timelimitedSkuPictureReqDTOList);
    		//设置详情
    		timelimitedInfoReqDTO.setTimelimitedSkuDescribeReqDTO(timelimitedSkuDescribeReqDTO);

    		timelimitedInfoReqDTO.setPromotionId(promotionId);
    		timelimitedInfoReqDTO.setLevelCode(levelCode);
    		timelimitedInfoReqDTO.setSellerCode("1001");
    		timelimitedInfoReqDTO.setItemId(20001L);
    		timelimitedInfoReqDTO.setSkuCode("200001");
    		timelimitedInfoReqDTO.setSkuName("测试商品");
    		timelimitedInfoReqDTO.setFirstCategoryCode("一级类目");
    		timelimitedInfoReqDTO.setSecondCategoryCode("二级类目");
    		timelimitedInfoReqDTO.setThirdCategoryCode("三级类目");
    		timelimitedInfoReqDTO.setSkuCategoryName("类目全称");
    		timelimitedInfoReqDTO.setSkuPicUrl("/img/123.jpg");
    		// 商品原价
    		BigDecimal skuCostPrice = new BigDecimal("100.88");
    		timelimitedInfoReqDTO.setSkuCostPrice(skuCostPrice);
    		// 商品秒杀价
    		BigDecimal skuTimelimitedPrice = new BigDecimal("90.99");
    		timelimitedInfoReqDTO.setSkuTimelimitedPrice(skuTimelimitedPrice);
    		// 参与秒杀商品数量
    		Integer timelimitedSkuCount = 100;
    		timelimitedInfoReqDTO.setTimelimitedSkuCount(timelimitedSkuCount);
    		// 每人限秒数量
    		Integer timelimitedThreshold = 2;
    		timelimitedInfoReqDTO.setTimelimitedThreshold(timelimitedThreshold);
    		// 秒杀订单有效时间 单位：分钟
    		Integer timelimitedValidInterval = 15;
    		timelimitedInfoReqDTO.setTimelimitedValidInterval(timelimitedValidInterval);

    		timelimitedInfoReqDTO.setCreateId(userId);
        	timelimitedInfoReqDTO.setCreateName(userName);
        	timelimitedInfoReqDTO.setModifyId(userId);
        	timelimitedInfoReqDTO.setModifyName(userName);
    		
            timelimitedInfoService.addTimelimitedInfo(timelimitedInfoReqDTO, messageId);
            
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @Test
    @Rollback(false)
    public void updateTimelimitedInfoTest(){
        try {
            String messageId = "342453251349";
    		Long userId = 10001L;
    		String userName = "admin";
    		
    		/**
    		 * 促销活动编码生成方法
    		 * @param platCode 促销活动类型 1:优惠券，2:秒杀，3:扭蛋，4:砍价，5:总部秒杀
    		 * @return
    		 */
    		String promotionId = "5172134231225";
    		
    		String levelCode = "5172134231225";
            
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();
            
        	//商品图片
        	List<TimelimitedSkuPictureReqDTO> timelimitedSkuPictureReqDTOList = new ArrayList<TimelimitedSkuPictureReqDTO>();
        	TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = new TimelimitedSkuPictureReqDTO();
        	timelimitedSkuPictureReqDTO.setPictureUrl("/img/123456.jpg");
        	TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO_2 = new TimelimitedSkuPictureReqDTO();
        	timelimitedSkuPictureReqDTO_2.setPictureUrl("/img/456789.jpg");
        	timelimitedSkuPictureReqDTOList.add(timelimitedSkuPictureReqDTO);
        	timelimitedSkuPictureReqDTOList.add(timelimitedSkuPictureReqDTO_2);
        		
        	//商品详情
        	TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = new TimelimitedSkuDescribeReqDTO();
        	timelimitedSkuDescribeReqDTO.setDescId(4L);
        	timelimitedSkuDescribeReqDTO.setDescribeContent("测试商品详情修改123^_^");
            
        	//秒杀商品信息
    		TimelimitedInfoReqDTO timelimitedInfoReqDTO = new TimelimitedInfoReqDTO();
    		//设置图片
    		timelimitedInfoReqDTO.setTimelimitedSkuPictureReqDTOList(timelimitedSkuPictureReqDTOList);
    		//设置详情
    		timelimitedInfoReqDTO.setTimelimitedSkuDescribeReqDTO(timelimitedSkuDescribeReqDTO);

    		timelimitedInfoReqDTO.setTimelimitedId(4L);
    		timelimitedInfoReqDTO.setPromotionId(promotionId);
    		timelimitedInfoReqDTO.setLevelCode(levelCode);
//    		timelimitedInfoReqDTO.setSellerCode("1001");
//    		timelimitedInfoReqDTO.setItemId(20001L);
//    		timelimitedInfoReqDTO.setSkuCode("200001");
//    		timelimitedInfoReqDTO.setSkuName("测试商品");
//    		timelimitedInfoReqDTO.setFirstCategoryCode("一级类目修改");
//    		timelimitedInfoReqDTO.setSecondCategoryCode("二级类目修改");
//    		timelimitedInfoReqDTO.setThirdCategoryCode("三级类目修改");
//    		timelimitedInfoReqDTO.setSkuCategoryName("类目全称");
    		timelimitedInfoReqDTO.setSkuPicUrl("/img/123456.jpg");
    		// 商品原价
    		BigDecimal skuCostPrice = new BigDecimal("101.88");
    		timelimitedInfoReqDTO.setSkuCostPrice(skuCostPrice);
    		// 商品秒杀价
    		BigDecimal skuTimelimitedPrice = new BigDecimal("91.99");
    		timelimitedInfoReqDTO.setSkuTimelimitedPrice(skuTimelimitedPrice);
    		// 参与秒杀商品数量
    		Integer timelimitedSkuCount = 102;
    		timelimitedInfoReqDTO.setTimelimitedSkuCount(timelimitedSkuCount);
    		// 每人限秒数量
    		Integer timelimitedThreshold = 3;
    		timelimitedInfoReqDTO.setTimelimitedThreshold(timelimitedThreshold);
    		// 秒杀订单有效时间 单位：分钟
    		Integer timelimitedValidInterval = 15;
    		timelimitedInfoReqDTO.setTimelimitedValidInterval(timelimitedValidInterval);

        	timelimitedInfoReqDTO.setModifyId(userId);
        	timelimitedInfoReqDTO.setModifyName(userName);
            
            timelimitedInfoService.updateTimelimitedInfo(timelimitedInfoReqDTO, messageId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
