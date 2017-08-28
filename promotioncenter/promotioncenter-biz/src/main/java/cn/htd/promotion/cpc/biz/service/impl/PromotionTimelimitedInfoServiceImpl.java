package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionTimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;
import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Service("promotionTimelimitedInfoService")
public class PromotionTimelimitedInfoServiceImpl implements PromotionTimelimitedInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionTimelimitedInfoServiceImpl.class);

	@Resource
	private PromotionTimelimitedInfoDAO promotionTimelimitedInfoDAO;

	@Override
	public List<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode)
			throws PromotionCenterBusinessException {
		List<TimelimitedInfoDMO> timelimitedInfoDMOList = null;
		List<TimelimitedInfoResDTO> timelimitedInfoDTOList = null;
		timelimitedInfoDMOList = promotionTimelimitedInfoDAO.selectTimelimitedInfo(skuCode);
	
		String marketCouponListString = JSON.toJSONString(timelimitedInfoDMOList);
		timelimitedInfoDTOList = JSON.parseObject(marketCouponListString,new TypeReference<ArrayList<TimelimitedInfoResDTO>>() {});
		
		return timelimitedInfoDTOList;
	}


	
}
