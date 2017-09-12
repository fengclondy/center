package cn.htd.promotion.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext_test.xml",
		"classpath:mybatis/sqlconfig/sqlMapConfig.xml" })
public class TimeLimitedOrderTestUnit {

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private TimelimitedInfoService timelimitedInfoService;

	@Resource
	private PromotionTimelimitedInfoAPI promotionTimelimitedInfoAPI;

	@Resource
	private GeneratorUtils noGenerator;

	// @Test
	// @Rollback(false)
	// public void savePromotionInfo() {
	// Long userId = 10001L;
	// String userName = "admin";
	// String promotionId = noGenerator.generatePromotionId("5");
	// String levelCode = noGenerator.generatePromotionLevelCode(promotionId);
	// // 秒杀商品信息
	// TimelimitedInfoResDTO timelimitedInfoResDTO = new
	// TimelimitedInfoResDTO();
	// timelimitedInfoResDTO.setPromotionId(promotionId);
	// timelimitedInfoResDTO.setLevelCode(levelCode);
	// timelimitedInfoResDTO.setSellerCode("1001");
	// timelimitedInfoResDTO.setItemId(20001L);
	// timelimitedInfoResDTO.setSkuCode("200001");
	// timelimitedInfoResDTO.setSkuName("测试商品");
	// timelimitedInfoResDTO.setFirstCategoryCode("一级类目");
	// timelimitedInfoResDTO.setSecondCategoryCode("二级类目");
	// timelimitedInfoResDTO.setThirdCategoryCode("三级类目");
	// timelimitedInfoResDTO.setSkuCategoryName("类目全称");
	// timelimitedInfoResDTO.setSkuPicUrl("/img/123.jpg");
	// // 商品原价
	// BigDecimal skuCostPrice = new BigDecimal("100.88");
	// timelimitedInfoResDTO.setSkuCostPrice(skuCostPrice);
	// // 商品秒杀价
	// BigDecimal skuTimelimitedPrice = new BigDecimal("90.99");
	// timelimitedInfoResDTO.setSkuTimelimitedPrice(skuTimelimitedPrice);
	// // 参与秒杀商品数量
	// Integer timelimitedSkuCount = 100;
	// timelimitedInfoResDTO.setTimelimitedSkuCount(timelimitedSkuCount);
	// // 每人限秒数量
	// Integer timelimitedThreshold = 2;
	// timelimitedInfoResDTO.setTimelimitedThreshold(timelimitedThreshold);
	// // 秒杀订单有效时间 单位：分钟
	// Integer timelimitedValidInterval = 15;
	// timelimitedInfoResDTO.setTimelimitedValidInterval(timelimitedValidInterval);
	// timelimitedInfoResDTO.setDescribeContent("测试商品详情^_^");
	// timelimitedInfoResDTO.setCreateId(userId);
	// timelimitedInfoResDTO.setCreateName(userName);
	// timelimitedInfoResDTO.setModifyId(userId);
	// timelimitedInfoResDTO.setModifyName(userName);
	// try {
	// timelimitedInfoService.setTimelimitedInfo2Redis(timelimitedInfoResDTO);
	// timelimitedInfoService.setTimelimitedReserveQueue(timelimitedInfoResDTO);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// @Test
	// @Rollback(false)
	// public void reserveStockTest() {
	// try {
	// String messageId = "123456";
	// TimelimitedInfoResDTO timelimitedInfoResDTO =
	// timelimitedInfoService.getTimelimitedInfo("23171047420198");
	// SeckillInfoReqDTO seckillInfoReqDTO = new SeckillInfoReqDTO();
	// seckillInfoReqDTO.setPromotionId(timelimitedInfoResDTO.getPromotionId());
	// seckillInfoReqDTO.setBuyerCode("534382");
	// seckillInfoReqDTO.setCount(1);
	// seckillInfoReqDTO.setUseType(Constants.SECKILL_RESERVE);
	// seckillInfoReqDTO.setOperaterId(timelimitedInfoResDTO.getCreateId());
	// seckillInfoReqDTO.setOperaterName(timelimitedInfoResDTO.getCreateName());
	// promotionTimelimitedInfoAPI.reserveStock(messageId, seckillInfoReqDTO);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Test
	@Rollback(false)
	public void releaseStockTest() {
		try {
			String messageId = "123456";
			TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getTimelimitedInfo("23171047420198");
			SeckillInfoReqDTO seckillInfoReqDTO = new SeckillInfoReqDTO();
			seckillInfoReqDTO.setPromotionId(timelimitedInfoResDTO.getPromotionId());
			seckillInfoReqDTO.setBuyerCode("1158238");
			seckillInfoReqDTO.setCount(1);
			seckillInfoReqDTO.setUseType(Constants.SECKILL_RESERVE);
			seckillInfoReqDTO.setOperaterId(timelimitedInfoResDTO.getCreateId());
			seckillInfoReqDTO.setOperaterName(timelimitedInfoResDTO.getCreateName());
			promotionTimelimitedInfoAPI.releaseStock(messageId, seckillInfoReqDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Rollback(false)
	public void reduceStockTest() {
		try {
			String messageId = "123456";
			TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getTimelimitedInfo("5171405160167");
			SeckillInfoReqDTO seckillInfoReqDTO = new SeckillInfoReqDTO();
			seckillInfoReqDTO.setPromotionId(timelimitedInfoResDTO.getPromotionId());
			seckillInfoReqDTO.setBuyerCode("test123");
			seckillInfoReqDTO.setCount(1);
			seckillInfoReqDTO.setUseType(Constants.SECKILL_RESERVE);
			seckillInfoReqDTO.setOperaterId(timelimitedInfoResDTO.getCreateId());
			seckillInfoReqDTO.setOperaterName(timelimitedInfoResDTO.getCreateName());
			promotionTimelimitedInfoAPI.reduceStock(messageId, seckillInfoReqDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
