package cn.htd.promotion.cpc.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.PromotionTimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Service("promotionTimelimitedInfoService")
public class PromotionTimelimitedInfoServiceImpl implements PromotionTimelimitedInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionTimelimitedInfoServiceImpl.class);

	@Resource
	private PromotionTimelimitedInfoDAO promotionTimelimitedInfoDAO;

	@Override
	public List<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode)
			throws PromotionCenterBusinessException {
		List<TimelimitedInfoResDTO> timelimitedInfoDTOList = null;
		timelimitedInfoDTOList = promotionTimelimitedInfoDAO.selectTimelimitedInfo(skuCode);
	
		return timelimitedInfoDTOList;
	}


	
}
