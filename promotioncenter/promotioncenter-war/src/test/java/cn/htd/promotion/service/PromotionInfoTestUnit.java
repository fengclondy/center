package cn.htd.promotion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;

import com.alibaba.fastjson.JSON;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class PromotionInfoTestUnit {

	@Resource
	PromotionBargainInfoService promotionBargainInfoService;
	
    @Resource
    private PromotionAccumulatyDAO promotionAccumulatyDAO;
    
    @Resource
    private PromotionSloganDAO promotionSloganDAO;
    
	@Resource
	private PromotionRedisDB promotionRedisDB;
	
    @Resource
    private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	@Test
	@Rollback(false) 
	public void savePromotionInfo() {
		try {
			System.out.println(111);
			String messageId = "001";
			List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
			List<String> sloganList = new ArrayList<String>();
			sloganList.add("汇通达周年庆");
			sloganList.add("汇通达十年庆");
			sloganList.add("汇通达百年庆");
			PromotionBargainInfoResDTO p1 = new PromotionBargainInfoResDTO();
			p1.setPromotionName("汇通达周年庆");
			p1.setPromotionDescribe("赶紧来买吧!!!");
			p1.setPromotionDesc("汇通达周年庆");
			p1.setEffectiveTime(new Date());
			p1.setInvalidTime(DateUtil.getDateBySpecificDate("2017-09-20 12:00:00"));
			p1.setOfflineStartTime(new Date());
			p1.setOfflineEndTime(DateUtil.getDateBySpecificDate("2017-08-29 12:00:00"));
			p1.setGoodsPicture("1.pig");
			p1.setGoodsName("格力空调");
			p1.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p1.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p1.setGoodsNum(2);
			p1.setPartakeTimes(300);
			p1.setPromotionSlogan(JSON.toJSONString(sloganList));
			p1.setTotalPartakeTimes(5L);
			p1.setContactTelephone("1398822111");
			p1.setContactName("胥明忠");
			p1.setContactAddress("江苏");
			p1.setPromotionProviderSellerCode("88888");
			p1.setCreateId(123L);
			p1.setCreateName("sa");
			p1.setCreateTime(new Date());
			p1.setTemplateFlag(3);
			PromotionBargainInfoResDTO p2 = new PromotionBargainInfoResDTO();
			p2.setPromotionName("汇通达周年庆");
			p2.setPromotionDescribe("汇通达周年庆");
			p2.setPromotionDesc("汇通达周年庆");
			p2.setEffectiveTime(new Date());
			p2.setInvalidTime(DateUtil.getDateBySpecificDate("2017-09-20 12:00:00"));
			p2.setOfflineStartTime(new Date());
			p2.setOfflineEndTime(DateUtil.getDateBySpecificDate("2017-08-29 12:00:00"));
			p2.setGoodsPicture("2.pig");
			p2.setGoodsName("格力冰箱");
			p2.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p2.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p2.setGoodsNum(2);
			p2.setPartakeTimes(300);
			p2.setPromotionSlogan(JSON.toJSONString(sloganList));
			p2.setTotalPartakeTimes(5L);
			p2.setContactTelephone("1398822111222");
			p2.setContactName("胥明忠2");
			p2.setContactAddress("江苏2");
			p2.setPromotionProviderSellerCode("88888");
			p2.setCreateId(123L);
			p2.setCreateName("sa");
			p2.setCreateTime(new Date());
			p2.setTemplateFlag(3);
			PromotionBargainInfoResDTO p3 = new PromotionBargainInfoResDTO();
			p3.setPromotionName("汇通达周年庆");
			p3.setPromotionDescribe("汇通达周年庆");
			p3.setPromotionDesc("汇通达周年庆");
			p3.setEffectiveTime(new Date());
			p3.setInvalidTime(DateUtil.getDateBySpecificDate("2017-09-20 12:00:00"));
			p3.setOfflineStartTime(new Date());
			p3.setOfflineEndTime(DateUtil.getDateBySpecificDate("2017-08-29 12:00:00"));
			p3.setGoodsPicture("3.pig");
			p3.setGoodsName("格力手机");
			p3.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p3.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p3.setGoodsNum(2);
			p3.setPartakeTimes(300);
			p3.setPromotionSlogan(JSON.toJSONString(sloganList));
			p3.setTotalPartakeTimes(5L);
			p3.setContactTelephone("1398822111222");
			p3.setContactName("胥明忠2");
			p3.setContactAddress("江苏2");
			p3.setPromotionProviderSellerCode("88888");
			p3.setCreateId(123L);
			p3.setCreateName("sa");
			p3.setCreateTime(new Date());
			p3.setTemplateFlag(3);
			promotionBargainInfoList.add(p1);
			promotionBargainInfoList.add(p2);
			promotionBargainInfoList.add(p3);
			ExecuteResult<List<PromotionBargainInfoResDTO>> result = promotionBargainInfoService.addPromotionBargainInfoRedis(promotionBargainInfoList);
			System.out.println(result.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPromotionInfo() {
		ExecuteResult<List<PromotionBargainInfoResDTO>> data = promotionBargainInfoService.getPromotionBargainInfoList("00001", "22171100370945");
		List<PromotionBargainInfoResDTO> list = data.getResult();
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	@Rollback(false) 
	public void delRedisPromotionInfo() {
		promotionRedisDB.del("B2B_MIDDLE_BARGAIN");
		promotionRedisDB.del("B2B_MIDDLE_BARGAIN_VALID");
	}
	
//	@Test
//	@Rollback(false) 
//	public void delPromotionInfo(){
//		PromotionValidDTO dto = new PromotionValidDTO();
//		dto.setPromotionId("22172046500940");
//		dto.setOperatorId(1L);
//		dto.setOperatorName("xx");
//		ExecuteResult<String> result = promotionBargainInfoService.deleteBargainInfo(dto);
//		System.out.println(result.getErrorMessage());
//		
//		
//	}
//	
	@Test
	@Rollback(false) 
	public void updatePromotionInfo() {
		try {
			System.out.println(111);
			List<String> sloganList = new ArrayList<String>();
			sloganList.add("1");
			sloganList.add("2");
			sloganList.add("3");
			String strSlogan = JSON.toJSONString(sloganList);
			String messageId = "002";
			List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
			PromotionBargainInfoResDTO p1 = new PromotionBargainInfoResDTO();
			p1.setPromotionId("22172230390974");
			p1.setLevelCode("2217223039097411");
			p1.setPromotionName("xmxmx");
			p1.setPromotionDescribe("mxmxmx");
			p1.setPromotionDesc("mxmxmx");
			p1.setEffectiveTime(new Date());
			p1.setInvalidTime(DateUtil.getDateBySpecificDate("2017-08-20 12:00:00"));
			p1.setGoodsPicture("https://b2cimg.htd.cn/b2cBasicOrg/1504016691825t4gUiEk7.png");
			p1.setGoodsName("现买现卖下");
			p1.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p1.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p1.setGoodsNum(3);
			p1.setPartakeTimes(2);
			p1.setPromotionSlogan(strSlogan);
			p1.setTotalPartakeTimes(5L);
			p1.setContactTelephone("2");
			p1.setContactName("3");
			p1.setContactAddress("1");
			p1.setPromotionProviderSellerCode("801781");
			p1.setPromotionProviderShopId(1L);
			p1.setModifyId(123L);
			p1.setModifyName("sa");
			p1.setModifyTime(new Date());
			p1.setDeleteFlag(0);
			promotionBargainInfoList.add(p1);
			ExecuteResult<List<PromotionBargainInfoResDTO>> result = promotionBargainInfoService.updateBargainInfo(promotionBargainInfoList);
			System.out.println(result.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	@Rollback(false) 
//	public void getReids(){
//		PromotionValidDTO dto = new PromotionValidDTO();
//		dto.setShowStatus("3");
//		dto.setPromotionId("22171542040072");
//		dto.setOperatorId(990L);
//		dto.setOperatorName("llm");
//		ExecuteResult<PromotionInfoDTO> result = promotionBargainInfoService.upDownShelvesPromotionInfo(dto);
//		System.out.println(JSON.toJSONString(result));
//	}
	
//	@Test
//	@Rollback(false)
//	public void upDown(){
//		PromotionValidDTO dto = new PromotionValidDTO();
//		dto.setOperatorId(111L);
//		dto.setOperatorName("sasa");
//		dto.setPromotionId("22171100370945");
//		dto.setShowStatus("3");
//		dto.setTemlateFlag("3");
//		ExecuteResult<PromotionInfoDTO> result = promotionBargainInfoService.upDownShelvesPromotionInfo(dto);
//		System.out.println(result.getCode());
//	}
}
