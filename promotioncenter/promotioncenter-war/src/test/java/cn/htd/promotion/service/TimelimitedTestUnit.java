package cn.htd.promotion.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBuyerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

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
//    		String promotionId = noGenerator.generatePromotionId("5");
    		
//    		String levelCode = noGenerator.generatePromotionLevelCode(promotionId);
            
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();
            
        	//商品图片
        	List<TimelimitedSkuPictureReqDTO> timelimitedSkuPictureReqDTOList = new ArrayList<TimelimitedSkuPictureReqDTO>();
        	TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = new TimelimitedSkuPictureReqDTO();;
        	timelimitedSkuPictureReqDTO.setPictureUrl("/img/123.jpg");
        	timelimitedSkuPictureReqDTOList.add(timelimitedSkuPictureReqDTO);
        		
        	//商品详情
        	List<TimelimitedSkuDescribeReqDTO> TimelimitedSkuDescribeReqDTOList = new ArrayList<TimelimitedSkuDescribeReqDTO>();
        	TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = new TimelimitedSkuDescribeReqDTO();
        	timelimitedSkuDescribeReqDTO.setPictureUrl("/img/aaa.jpg");
        	TimelimitedSkuDescribeReqDTOList.add(timelimitedSkuDescribeReqDTO);
        	
        	//秒杀商品信息
    		TimelimitedInfoReqDTO timelimitedInfoReqDTO = new TimelimitedInfoReqDTO();
    		//设置图片
    		timelimitedInfoReqDTO.setTimelimitedSkuPictureReqDTOList(timelimitedSkuPictureReqDTOList);
    		//设置详情
    		timelimitedInfoReqDTO.setTimelimitedSkuDescribeReqDTOList(TimelimitedSkuDescribeReqDTOList);

//    		timelimitedInfoReqDTO.setPromotionId(promotionId);
//    		timelimitedInfoReqDTO.setLevelCode(levelCode);
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
    		
    		timelimitedInfoReqDTO.setDescribeContent("测试商品详情^_^");

    		timelimitedInfoReqDTO.setCreateId(userId);
        	timelimitedInfoReqDTO.setCreateName(userName);
        	timelimitedInfoReqDTO.setModifyId(userId);
        	timelimitedInfoReqDTO.setModifyName(userName);
        	
        	//设置活动信息（通用）
        	PromotionExtendInfoDTO promotionExtendInfoDTO = new PromotionExtendInfoDTO();
        	promotionExtendInfoDTO.setCreateId(userId);
        	promotionExtendInfoDTO.setCreateName(userName);
        	setPromotionParam(promotionExtendInfoDTO);
        	timelimitedInfoReqDTO.setPromotionExtendInfoDTO(promotionExtendInfoDTO);
        	
        	
            timelimitedInfoService.addTimelimitedInfo(timelimitedInfoReqDTO, messageId);
            
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
	
	private void setPromotionParam(PromotionExtendInfoDTO promotionExtendInfoDTO){

    	Date nowDt = new Date();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		int hour = 10;
		int minute = 0;
		int second = 0;
		cal.set(year, month, day, hour,minute,second);
		Date startTime = cal.getTime();
		hour = 22;
		cal.set(year, month, day, hour,minute,second);
		Date endTime = cal.getTime();

    	promotionExtendInfoDTO.setPromotionName("总部秒杀--张志峰测试");
//    	promotionExtendInfoDTO.setCreateId(0L);
//    	promotionExtendInfoDTO.setCreateName("sys");
    	promotionExtendInfoDTO.setPromotionProviderType("1");//促销活动发起方类型  1:平台，2:店铺
    	promotionExtendInfoDTO.setPromotionType("23");//活动类型 1：优惠券，2：秒杀，21：扭蛋机，22：砍价，23：总部秒杀
    	promotionExtendInfoDTO.setEffectiveTime(nowDt);
    	promotionExtendInfoDTO.setInvalidTime(DateUtils.addDays(nowDt, 1));
//    	promotionExtendInfoDTO.setStatus("2");//状态 1：活动未开始，2：活动进行中，3：活动已结束，9：已删除   （不需要设置，后台根据  开始时间和结束时间  自动生成）
    	promotionExtendInfoDTO.setShowStatus("3");//审核状态 0：待审核，1：审核通过，2：审核被驳回，3：启用，4：不启用
    	promotionExtendInfoDTO.setDealFlag(1);
    	List<PromotionConfigureDTO> promotionConfigureList = new ArrayList<PromotionConfigureDTO>();
    	/**
    	 * 配置类型   1：活动渠道，2：支付方式，3：配送方式
    	 * 配置值 
    	 * 活动渠道：   1：B2B商城，2：超级老板，3：汇掌柜，4：大屏；
    	 * 配送方式：   1：供应商配送  2：自提； Delivery mode
    	 * 支付方式：   1：余额帐支付，2：平台账户支付，3：在线支付 Payment method
    	 */
    	PromotionConfigureDTO deliveryMode = new PromotionConfigureDTO();
    	deliveryMode.setConfType("3");
    	deliveryMode.setConfValue("2");
    	PromotionConfigureDTO paymentMethod = new PromotionConfigureDTO();
    	paymentMethod.setConfType("2");
    	paymentMethod.setConfValue("3");
		promotionConfigureList.add(deliveryMode);
		promotionConfigureList.add(paymentMethod);
		promotionExtendInfoDTO.setPromotionConfigureList(promotionConfigureList );
//		promotionExtendInfoDTO.setCycleTimeType("1");
//		promotionExtendInfoDTO.setEachStartTime(startTime);
//		promotionExtendInfoDTO.setEachEndTime(endTime);
//		promotionExtendInfoDTO.setIsTotalTimesLimit(0);
//		promotionExtendInfoDTO.setIsDailyTimesLimit(1);
//		promotionExtendInfoDTO.setDailyBuyerPartakeTimes(10L);
//		promotionExtendInfoDTO.setDailyBuyerWinningTimes(2L);
//		promotionExtendInfoDTO.setDailyWinningTimes(30L);
//		promotionExtendInfoDTO.setIsShareTimesLimit(0);
//		promotionExtendInfoDTO.setShareExtraPartakeTimes(1L);
//		promotionExtendInfoDTO.setTopExtraPartakeTimes(5L);
//		promotionExtendInfoDTO.setContactName("test");
//		PromotionDetailDescribeDTO promotionDetailDescribeDTO = new PromotionDetailDescribeDTO();
//		promotionDetailDescribeDTO.setDescribeContent("zzf test test test");
//		promotionExtendInfoDTO.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO );
		
//		List<PromotionPictureDTO> promotionPictureList = new ArrayList<PromotionPictureDTO>();
//		PromotionPictureDTO eee = new PromotionPictureDTO();
//		eee.setPromotionPictureType("1");
//		eee.setPromotionPictureUrl("http://ssssssssss");
//		promotionPictureList.add(eee );
//		promotionExtendInfoDTO.setPromotionPictureList(promotionPictureList );
		
		//设置层级
		List<PromotionAccumulatyDTO> promotionAccumulatyList = new ArrayList<PromotionAccumulatyDTO>();
		PromotionAccumulatyDTO promotionAccumulatyDTO = new PromotionAccumulatyDTO();
		promotionAccumulatyList.add(promotionAccumulatyDTO);
		promotionExtendInfoDTO.setPromotionAccumulatyList(promotionAccumulatyList);
		
		PromotionBuyerRuleDTO buyerRuleDTO = new PromotionBuyerRuleDTO();
		buyerRuleDTO.setRuleTargetType("1");//会员规则对象 1-会员级别，2-指定会员，3-会员分组，4-首次登陆会员
		promotionExtendInfoDTO.setBuyerRuleDTO(buyerRuleDTO );
		
		PromotionSellerRuleDTO sellerRuleDTO=new PromotionSellerRuleDTO();
		sellerRuleDTO.setRuleTargetType("1");//卖家规则对象种类 1：指定供应商类型；2：部分供应商
		sellerRuleDTO.setTargetSellerType("0");//卖家规则对象种类为1时使用  0：全部通用 ；1（平台公司），2（商品+），3（外部供应商）
		List<PromotionSellerDetailDTO> sellerDetailList = new ArrayList<>();
		PromotionSellerDetailDTO rrrr = new PromotionSellerDetailDTO();
		rrrr.setSellerCode("htd123456");
		rrrr.setSellerName("无锡市崇安区阳光家电有限公司");
		rrrr.setBelongSuperiorName("上级测试公司 张志峰");
		sellerDetailList.add(rrrr );
		sellerRuleDTO.setSellerDetailList(sellerDetailList );
		promotionExtendInfoDTO.setSellerRuleDTO(sellerRuleDTO);
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
    		String promotionId = "23172108180146";
            
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
        	List<TimelimitedSkuDescribeReqDTO> TimelimitedSkuDescribeReqDTOList = new ArrayList<TimelimitedSkuDescribeReqDTO>();
        	TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = new TimelimitedSkuDescribeReqDTO();
        	timelimitedSkuDescribeReqDTO.setPictureUrl("/img/aaa123.jpg");
        	TimelimitedSkuDescribeReqDTOList.add(timelimitedSkuDescribeReqDTO);
            
        	//秒杀商品信息
    		TimelimitedInfoReqDTO timelimitedInfoReqDTO = new TimelimitedInfoReqDTO();
    		//设置图片
    		timelimitedInfoReqDTO.setTimelimitedSkuPictureReqDTOList(timelimitedSkuPictureReqDTOList);
    		//设置详情
    		timelimitedInfoReqDTO.setTimelimitedSkuDescribeReqDTOList(TimelimitedSkuDescribeReqDTOList);

    		timelimitedInfoReqDTO.setPromotionId(promotionId);
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
    		
    		timelimitedInfoReqDTO.setDescribeContent("测试商品详情修改^_^");

        	timelimitedInfoReqDTO.setModifyId(userId);
        	timelimitedInfoReqDTO.setModifyName(userName);
        	
        	//设置活动信息（通用）
        	PromotionExtendInfoDTO promotionExtendInfoDTO = new PromotionExtendInfoDTO();
        	promotionExtendInfoDTO.setPromotionId(promotionId);
        	promotionExtendInfoDTO.setModifyId(userId);
        	promotionExtendInfoDTO.setModifyName(userName);
        	setPromotionParam(promotionExtendInfoDTO);
        	
        	timelimitedInfoReqDTO.setPromotionExtendInfoDTO(promotionExtendInfoDTO);
            
            timelimitedInfoService.updateTimelimitedInfo(timelimitedInfoReqDTO, messageId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void getSingleTimelimitedInfoTest(){
    	
        String messageId = "342453251349";
        String promotionId = "23171718090174";
        try {
    		
        	TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getSingleFullTimelimitedInfoByPromotionId(promotionId, messageId);
        	System.out.println("===>timelimitedInfoResDTO:" + timelimitedInfoResDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void getTimelimitedInfosForPageTest(){
    	
        String messageId = "342453251349";
        
        String effectiveTimeString = "2017-09-05 17:28:09";
        String invalidTimeString = "2017-09-07 16:18:09";
        		
        try {
        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        	
    		Pager<TimelimitedInfoReqDTO> page = new Pager<TimelimitedInfoReqDTO>();
    		page.setPage(1);
    		page.setRows(20);
    		TimelimitedInfoReqDTO timelimitedInfoReqDTO = new TimelimitedInfoReqDTO();
    		timelimitedInfoReqDTO.setFirstCategoryCode("一级类目");
//    		timelimitedInfoReqDTO.setSecondCategoryCode("");
//    		timelimitedInfoReqDTO.setThirdCategoryCode("");
    		timelimitedInfoReqDTO.setSkuName("测试商品");//商品名称
    		timelimitedInfoReqDTO.setShowStatus("3");//审核状态 0：待审核，1：审核通过，2：审核被驳回，3：启用，4：不启用
    		timelimitedInfoReqDTO.setEffectiveTime(sdf.parse(effectiveTimeString));
    		timelimitedInfoReqDTO.setInvalidTime(sdf.parse(invalidTimeString));
    		
    		
        	DataGrid<TimelimitedInfoResDTO> data = timelimitedInfoService.getTimelimitedInfosForPage(page, timelimitedInfoReqDTO, messageId);
        	System.out.println("===>data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    

}
