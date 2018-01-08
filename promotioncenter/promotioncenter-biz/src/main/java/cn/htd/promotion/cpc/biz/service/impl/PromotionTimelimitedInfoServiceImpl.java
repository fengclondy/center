package cn.htd.promotion.cpc.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.dto.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.PromotionTimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;

@Service("promotionTimelimitedInfoService")
public class PromotionTimelimitedInfoServiceImpl implements PromotionTimelimitedInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionTimelimitedInfoServiceImpl.class);

	@Resource
	private PromotionTimelimitedInfoDAO promotionTimelimitedInfoDAO;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private PromotionAccumulatyDAO promotionAccumulatyDAO;

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

	@Override
	public TimelimitedInfoResDTO getPromotionTimelimitedInfoBySkuCode(String messageId, String skuCode)
			throws PromotionCenterBusinessException {
		TimelimitedInfoResDTO timelimitedInfoResDTO = promotionTimelimitedInfoDAO.selectTimelimitedInfoBySkuCode(skuCode);
		return timelimitedInfoResDTO;
	}

	/**
	 * 删除促销活动
	 *
	 * @param validDTO
	 * @throws PromotionCenterBusinessException
	 * @throws Exception
	 */
	@Override
	public void deletePromotionInfo(String messageId,PromotionValidDTO validDTO) throws PromotionCenterBusinessException, Exception {
		PromotionInfoDTO promotionInfo = null;
		PromotionAccumulatyDTO accumulaty = new PromotionAccumulatyDTO();
		try {
			// 根据活动ID获取活动信息
			promotionInfo = promotionInfoDAO.queryById(validDTO.getPromotionId());
			if (promotionInfo == null) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "该促销活动不存在");
			}
			// 活动已删除
			if ("9".equals(promotionInfo.getStatus())) {
				return;
			}
			promotionInfo.setStatus("9");
			promotionInfo.setModifyId(validDTO.getOperatorId());
			promotionInfo.setModifyName(validDTO.getOperatorName());
			accumulaty.setPromoionInfo(promotionInfo);
			promotionAccumulatyDAO.delete(accumulaty);
			promotionInfoDAO.updatePromotionStatusById(promotionInfo);
		} catch (PromotionCenterBusinessException pcbe) {
			throw pcbe;
		} catch (Exception e) {
			throw e;
		}
	}

	
}
