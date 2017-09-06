package cn.htd.promotion.cpc.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.PromotionTimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Service("promotionTimelimitedInfoService")
public class PromotionTimelimitedInfoServiceImpl implements PromotionTimelimitedInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionTimelimitedInfoServiceImpl.class);

	@Resource
	private PromotionTimelimitedInfoDAO promotionTimelimitedInfoDAO;

	@Override
	public List<TimelimitedInfoResDTO> getPromotionTimelimitedInfoByBuyerCode(String messageId, String buyerCode)
			throws PromotionCenterBusinessException {
		List<TimelimitedInfoResDTO> timelimitedInfoDTOList = null;
		timelimitedInfoDTOList = promotionTimelimitedInfoDAO.selectTimelimitedInfo(buyerCode);
	
		return timelimitedInfoDTOList;
	}

	@Override
	public List<PromotionSellerDetailDTO> getPromotionSellerDetailDTOByBuyerCode(String promotionId,String buyerCode)
			throws PromotionCenterBusinessException {
		List<PromotionSellerDetailDTO> timelimitedInfoDTOList = null;
		timelimitedInfoDTOList = promotionTimelimitedInfoDAO.selectPromotionSellerDetailInfo(promotionId,buyerCode);
		return timelimitedInfoDTOList;
	}


	
}
