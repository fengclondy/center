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
import org.springframework.util.Assert;

import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;

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
			String messageId = "001";
			List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
			List<String> sloganList = new ArrayList<String>();
			sloganList.add("汇通达周年庆");
			sloganList.add("汇通达十年庆");
			sloganList.add("汇通达百年庆");
			PromotionBargainInfoResDTO p1 = new PromotionBargainInfoResDTO();
			p1.setGoodsPicture("1.pig");
			p1.setGoodsName("格力空调");
			p1.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p1.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p1.setGoodsNum(2);
			p1.setPartakeTimes(300);
			p1.setPromotionSlogan(JSON.toJSONString(sloganList));
			PromotionBargainInfoResDTO p2 = new PromotionBargainInfoResDTO();
			p2.setGoodsPicture("2.pig");
			p2.setGoodsName("格力冰箱");
			p2.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p2.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p2.setGoodsNum(2);
			p2.setPartakeTimes(300);
			promotionBargainInfoList.add(p1);
			promotionBargainInfoList.add(p2);
			PromotionExtendInfoDTO extendDTO = new PromotionExtendInfoDTO();
			extendDTO.setPromotionName("汇通达周年庆");
			extendDTO.setPromotionDescribe("汇通达周年庆");
			extendDTO.setCreateId(123L);
			extendDTO.setCreateName("sa");
			extendDTO.setCreateTime(new Date());
			extendDTO.setTemplateFlag(3);
			extendDTO.setPromotionAccumulatyList(promotionBargainInfoList);
			extendDTO.setTotalPartakeTimes(5L);
			extendDTO.setContactTelephone("1398822111");
			extendDTO.setContactName("胥明忠");
			extendDTO.setContactAddress("江苏");
			extendDTO.setPromotionProviderSellerCode("88888");
			extendDTO.setPromotionName("汇通达周年庆mamama");
			extendDTO.setEffectiveTime(DateUtil.getDateBySpecificDate("2099-08-20 12:00:00"));
			extendDTO.setInvalidTime(DateUtil.getDateBySpecificDate("2099-09-20 12:00:00"));
			extendDTO.setOfflineStartTime(new Date());
			extendDTO.setOfflineEndTime(DateUtil.getDateBySpecificDate("2088-08-29 12:00:00"));
			ExecuteResult<PromotionExtendInfoDTO> result = promotionBargainInfoService.addPromotionBargainInfo(extendDTO);
			Assert.isTrue(result.getResult() != null);
			System.out.println(result.getErrorMessage());
	}
	
	@Test
	public void getPromotionInfo() {
//		ExecuteResult<List<PromotionBargainInfoResDTO>> data = promotionBargainInfoService.getPromotionBargainInfoList("00001", "22171100370945");
//		List<PromotionBargainInfoResDTO> list = data.getResult();
//		System.out.println(JSON.toJSONString(data));
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
			System.out.println(111);
			List<String> sloganList = new ArrayList<String>();
			sloganList.add("1");
			String strSlogan = JSON.toJSONString(sloganList);
			String messageId = "002";
			List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
			PromotionBargainInfoResDTO p3 = new PromotionBargainInfoResDTO();
			p3.setGoodsPicture("https://b2cimg.htd.cn/b2cBasicOrg/1504016691825t4gUiEk7.png");
			p3.setGoodsName("现买现卖下3");
			p3.setGoodsCostPrice(BigDecimal.valueOf(3000.00));
			p3.setGoodsFloorPrice(BigDecimal.valueOf(2000.00));
			p3.setGoodsNum(3);
			p3.setPartakeTimes(2);
			p3.setPromotionSlogan(strSlogan);
			promotionBargainInfoList.add(p3);
			PromotionExtendInfoDTO extendDTO = new PromotionExtendInfoDTO();
			extendDTO.setPromotionId("22171934550041");
			extendDTO.setPromotionName("汇通达周年庆");
			extendDTO.setPromotionDescribe("汇通达周年庆");
			extendDTO.setModifyId(123L);
			extendDTO.setModifyName("sa");
			extendDTO.setTemplateFlag(3);
			extendDTO.setPromotionAccumulatyList(promotionBargainInfoList);
			extendDTO.setTotalPartakeTimes(5L);
			extendDTO.setContactTelephone("1398822111");
			extendDTO.setContactName("胥明忠");
			extendDTO.setContactAddress("江苏");
			extendDTO.setPromotionProviderSellerCode("88888");
			extendDTO.setPromotionName("汇通达周年庆mamama");
			extendDTO.setEffectiveTime(DateUtil.getDateBySpecificDate("2099-08-20 12:00:00"));
			extendDTO.setInvalidTime(DateUtil.getDateBySpecificDate("2099-09-20 12:00:00"));
			extendDTO.setOfflineStartTime(new Date());
			extendDTO.setOfflineEndTime(DateUtil.getDateBySpecificDate("2088-08-29 12:00:00"));
			ExecuteResult<PromotionExtendInfoDTO> result = promotionBargainInfoService.updateBargainInfo(extendDTO);
			Assert.isTrue(result.getResult() != null);
			System.out.println(result.getErrorMessage());
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
	
	@Test
	@Rollback(false)
	public void upDown(){
//		PromotionBargainInfoResDTO dto = new PromotionBargainInfoResDTO();
//		dto.setPromotionId("");
//		ExecuteResult<List<PromotionBargainInfoResDTO>> result = promotionBargainInfoService.getPromotionBargainInfoList(dto);
	}
}
