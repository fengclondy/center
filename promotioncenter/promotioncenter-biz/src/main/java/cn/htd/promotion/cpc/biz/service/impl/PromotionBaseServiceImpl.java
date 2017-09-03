package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBuyerRuleDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionConfigureDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionDetailDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoExtendDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionPictureDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSellerDetailDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSellerRuleDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionDetailDescribeDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.request.BuyerCheckInfo;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBuyerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBuyerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionDetailDescribeDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;

@Service("promotionBaseService")
public class PromotionBaseServiceImpl implements PromotionBaseService {

	private static final Logger logger = LoggerFactory
			.getLogger(PromotionBaseServiceImpl.class);
	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private GeneratorUtils noGenerator;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private PromotionAccumulatyDAO promotionAccumulatyDAO;

	@Resource
	private PromotionSloganDAO promotionSloganDAO;

	@Resource
	private PromotionInfoExtendDAO promotionInfoExtendDAO;

	// @Resource
	// private PromotionAwardInfoDAO promotionAwardInfoDAO;

	@Resource
	private PromotionDetailDescribeDAO promotionDetailDescribeDAO;

	@Resource
	private PromotionPictureDAO promotionPictureDAO;

	@Resource
	private PromotionBuyerRuleDAO promotionBuyerRuleDAO;

	@Resource
	private PromotionSellerDetailDAO promotionSellerDetailDAO;

	@Resource
	private PromotionSellerRuleDAO promotionSellerRuleDAO;

	@Resource
	private PromotionConfigureDAO promotionConfigureDAO;
	
    /**
     * 初始化校验用字典信息
     *
     * @param dictMap
     * @param dictKey
     */
    public void initDictionaryMap(Map<String, String> dictMap, String dictKey) {
        List<DictionaryInfo> dictionaryList = null;
        dictionaryList = dictionary.getDictionaryOptList(dictKey);
        if (dictionaryList != null && !dictionaryList.isEmpty()) {
            for (DictionaryInfo dictionaryInfo : dictionaryList) {
                dictMap.put(dictKey + "&" + dictionaryInfo.getCode(), dictionaryInfo.getValue());
            }
        }
    }
 

	/**
	 * 初始化校验优惠券用字典信息
	 *
	 * @return
	 */
	@Override
	public Map<String, String> initPromotionDictMap() {
		Map<String, String> dictMap = new HashMap<String, String>();
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_STATUS);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_RULE_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_SELLER_RULE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_SELLER_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_BUYER_RULE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
		initDictionaryMap(dictMap,
				DictionaryConst.TYPE_PROMOTION_CYCLE_TIME_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_PRICTURE_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_ADDUP_TYPE);
		initDictionaryMap(dictMap,
				DictionaryConst.TYPE_PROMOTION_QUANTIFIER_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
		return dictMap;
	}

	/**
	 * 删除促销活动
	 *
	 * @param validDTO
	 * @throws PromotionCenterBusinessException
	 * @throws PromotionCenterBusinessException
	 *             ,Exception
	 */
	public void deletePromotionInfo(PromotionValidDTO validDTO)
			throws PromotionCenterBusinessException, Exception {
		PromotionInfoDTO promotionInfo = null;
		PromotionAccumulatyDTO accumulaty = new PromotionAccumulatyDTO();
		try {
			// 根据活动ID获取活动信息
			promotionInfo = promotionInfoDAO.queryById(validDTO
					.getPromotionId());
			if (promotionInfo == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
						"该促销活动不存在");
			}
			// 活动已删除
			if (dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE).equals(
					promotionInfo.getStatus())) {
				return;
			}
			if (dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
					DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(
					promotionInfo.getShowStatus())) {
				if (!dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_END).equals(
						promotionInfo.getStatus())) {
				}
			}
			promotionInfo.setStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
			promotionInfo.setModifyId(validDTO.getOperatorId());
			promotionInfo.setModifyName(validDTO.getOperatorName());
			accumulaty.setPromoionInfo(promotionInfo);
			promotionAccumulatyDAO.delete(accumulaty);
			promotionInfoDAO.updatePromotionStatusById(promotionInfo);
		} catch (PromotionCenterBusinessException mcbe) {
			throw mcbe;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 插入促销活动表
	 *
	 * @param promotionInfo
	 * @return
	 * @throws PromotionCenterBusinessException
	 * @throws PromotionCenterBusinessException
	 *             ,Exception
	 */
	public PromotionExtendInfoDTO insertPromotionInfo(
			PromotionExtendInfoDTO promotionInfo) throws Exception {
		String promotionType = "";
		String promotionId = "";
		List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
		PromotionAccumulatyDTO accumulatyDTO = null;
		PromotionExtendInfoDTO extendDTO = new PromotionExtendInfoDTO();
		int vipFlg = -1;
		if (promotionInfo == null) {
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
		}
		promotionType = promotionInfo.getPromotionType();
		promotionId = noGenerator.generatePromotionId(promotionType);
		promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
		if (promotionAccumulatyList == null
				|| promotionAccumulatyList.isEmpty()) {
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动层级不能为空");
		}
		promotionInfo.setPromotionId(promotionId);
		if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
				DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN).equals(
				promotionType)) {
			promotionInfo.setShowStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
					DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
		} else {
			if (StringUtils.isEmpty(promotionInfo.getShowStatus())) {
				promotionInfo.setShowStatus(dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
			}
		}
		setPromotionStatusInfo(promotionInfo);
		for (int i = 0; i < promotionAccumulatyList.size(); i++) {
			accumulatyDTO = promotionAccumulatyList.get(i);
			accumulatyDTO.setPromotionId(promotionId);
			accumulatyDTO.setLevelNumber(i + 1);
			accumulatyDTO.setLevelCode(noGenerator
					.generatePromotionLevelCode(promotionId));
			accumulatyDTO.setDeleteFlag(YesNoEnum.NO.getValue());
			accumulatyDTO.setCreateId(promotionInfo.getCreateId());
			accumulatyDTO.setCreateName(promotionInfo.getCreateName());
			promotionAccumulatyDAO.add(accumulatyDTO);
		}
		extendDTO = (PromotionExtendInfoDTO) promotionInfo;
		promotionInfoExtendDAO.add(extendDTO);
		promotionInfo.setIsVip(vipFlg);
		promotionInfoDAO.add(promotionInfo);

		PromotionDetailDescribeDMO promotionDetailDescribeDTO = new PromotionDetailDescribeDMO();
		PromotionDetailDescribeDTO piddd = promotionInfo
				.getPromotionDetailDescribeDTO();
		if (piddd != null) {
			promotionDetailDescribeDTO.setDescribeContent(piddd
					.getDescribeContent());
			promotionDetailDescribeDTO.setCreateId(promotionInfo.getCreateId());
			promotionDetailDescribeDTO.setCreateName(promotionInfo
					.getCreateName());
			promotionDetailDescribeDTO.setModifyId(promotionInfo.getCreateId());
			promotionDetailDescribeDTO.setModifyName(promotionInfo
					.getCreateName());
			promotionDetailDescribeDTO.setPromotionId(promotionId);
			promotionDetailDescribeDTO.setDeleteFlag(YesNoEnum.NO.getValue());
			promotionDetailDescribeDAO.add(promotionDetailDescribeDTO);
		}

		List<PromotionPictureDTO> piclist = promotionInfo
				.getPromotionPictureList();
		if (null != piclist && !piclist.isEmpty()) {
			for (PromotionPictureDTO promotionPictureReqDTO : piclist) {
				promotionPictureReqDTO.setDeleteFlag(YesNoEnum.NO.getValue());
				promotionPictureReqDTO.setCreateId(promotionInfo.getCreateId());
				promotionPictureReqDTO.setCreateName(promotionInfo
						.getCreateName());
				promotionPictureReqDTO.setModifyId(promotionInfo.getCreateId());
				promotionPictureReqDTO.setModifyName(promotionInfo
						.getCreateName());
				promotionPictureReqDTO.setPromotionId(promotionId);
				promotionPictureDAO.add(promotionPictureReqDTO);
			}
		}
		PromotionBuyerRuleDTO promotionBuyerRuleReqDTO = promotionInfo
				.getBuyerRuleDTO();
		if (null != promotionBuyerRuleReqDTO) {
			promotionBuyerRuleReqDTO.setPromotionId(promotionId);
			promotionBuyerRuleReqDTO.setDeleteFlag(YesNoEnum.NO.getValue());
			promotionBuyerRuleReqDTO.setCreateId(promotionInfo.getCreateId());
			promotionBuyerRuleReqDTO.setCreateName(promotionInfo
					.getCreateName());
			promotionBuyerRuleReqDTO.setModifyId(promotionInfo.getCreateId());
			promotionBuyerRuleReqDTO.setModifyName(promotionInfo
					.getCreateName());
			promotionBuyerRuleDAO.add(promotionBuyerRuleReqDTO);
		}

		PromotionSellerRuleDTO psr = promotionInfo.getSellerRuleDTO();
		if (psr != null) {
			psr.setPromotionId(promotionId);
			psr.setDeleteFlag(YesNoEnum.NO.getValue());
			psr.setCreateId(promotionInfo.getCreateId());
			psr.setCreateName(promotionInfo.getCreateName());
			psr.setModifyId(promotionInfo.getCreateId());
			psr.setModifyName(promotionInfo.getCreateName());
			promotionSellerRuleDAO.add(psr);
			
			List<PromotionSellerDetailDTO> sellerlist = psr.getSellerDetailList();
			if (null != sellerlist && !sellerlist.isEmpty()) {
				for (PromotionSellerDetailDTO psd : sellerlist) {
					psd.setPromotionId(promotionId);
					psd.setCreateId(promotionInfo.getCreateId());
					psd.setCreateName(promotionInfo.getCreateName());
					psd.setModifyId(promotionInfo.getCreateId());
					psd.setModifyName(promotionInfo.getCreateName());
					psd.setDeleteFlag(YesNoEnum.NO.getValue());
					promotionSellerDetailDAO.add(psd);
				}
			}
		}

		// PromotionSloganDTO psrd = accumulatyDTO.getPromotionSloganDTO();
		// if(psrd!=null){
		// PromotionSloganDTO slogan = new PromotionSloganDTO();
		// slogan.setCreateId(promotionInfo.getCreateId());
		// slogan.setCreateName(promotionInfo.getCreateName());
		// slogan.setModifyId(promotionInfo.getCreateId());
		// slogan.setModifyName(promotionInfo.getCreateName());
		// promotionSloganDAO.add(slogan);
		// }
		List<PromotionConfigureDTO> pclist = promotionInfo
				.getPromotionConfigureList();
		if (pclist != null && pclist.size() > 0) {
			for (PromotionConfigureDTO pcd : pclist) {
				pcd.setPromotionId(promotionId);
				pcd.setCreateId(promotionInfo.getCreateId());
				pcd.setCreateName(promotionInfo.getCreateName());
				pcd.setDeleteFlag(YesNoEnum.NO.getValue());
				promotionConfigureDAO.add(pcd);
			}
		}
		return (PromotionExtendInfoDTO) promotionInfo;
	}

	/**
	 * 查询促销活动信息
	 *
	 * @param promotionId
	 * @param levelCodeArr
	 * @return
	 * @throws PromotionCenterBusinessException
	 * @throws PromotionCenterBusinessException
	 *             ,Exception
	 */
	public PromotionExtendInfoDTO queryPromotionInfo(String promotionId,
			String... levelCodeArr) throws Exception {
		PromotionInfoDTO promotion = new PromotionInfoDTO();
		PromotionExtendInfoDTO promotionInfo = new PromotionExtendInfoDTO();
		List<PromotionAccumulatyDTO> promotionAccumulatyList = null;
		List<String> levelCodeList = null;
		promotion =  promotionInfoDAO.queryById(promotionId);
		if (promotion == null) {
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "促销活动不存在");
		}
		promotionInfo = promotionInfoExtendDAO.queryById(promotionId);

		promotionInfo.setPromoionInfo(promotion); 
		if (levelCodeArr != null && levelCodeArr.length > 0) {
			levelCodeList = new ArrayList<String>();
			for (String levelCode : levelCodeArr) {
				levelCodeList.add(levelCode);
			}
		}
		promotionAccumulatyList = promotionAccumulatyDAO
				.queryAccumulatyListByPromotionId(promotionId, levelCodeList);
		if (promotionAccumulatyList == null
				|| promotionAccumulatyList.isEmpty()) {
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "促销活动层级不存在");
		}
		promotionInfo.setPromotionAccumulatyList(promotionAccumulatyList);

		PromotionDetailDescribeDMO record = new PromotionDetailDescribeDMO();
		record.setPromotionId(promotionId);
		PromotionDetailDescribeDMO promotionDetailDescribeInfo = promotionDetailDescribeDAO
				.selectByPromotionId(record);
		PromotionDetailDescribeDTO promotionDetailDescribeDTO = new PromotionDetailDescribeDTO();
		if (promotionDetailDescribeInfo != null) {
			promotionDetailDescribeDTO
					.setDescribeContent(promotionDetailDescribeInfo
							.getDescribeContent());
			promotionDetailDescribeDTO.setCreateId(promotionDetailDescribeInfo
					.getCreateId());
			promotionDetailDescribeDTO
					.setCreateName(promotionDetailDescribeInfo.getCreateName());
			promotionDetailDescribeDTO
					.setCreateTime(promotionDetailDescribeInfo.getCreateTime());
			promotionDetailDescribeDTO.setModifyId(promotionDetailDescribeInfo
					.getModifyId());
			promotionDetailDescribeDTO
					.setModifyName(promotionDetailDescribeInfo.getModifyName());
			promotionDetailDescribeDTO
					.setModifyTime(promotionDetailDescribeInfo.getModifyTime());
			promotionDetailDescribeDTO.setPromotionId(promotionId);
			promotionDetailDescribeDTO
					.setDeleteFlag(promotionDetailDescribeInfo.getDeleteFlag());
		}

		promotionInfo.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO);

		List<PromotionPictureDTO> piclist = promotionPictureDAO
				.selectByPromotionInfoId(promotionId);
		promotionInfo.setPromotionPictureList(piclist);

		PromotionBuyerRuleDTO promotionBuyerRuleDTO = promotionBuyerRuleDAO
				.selectByPromotionInfoId(promotionId);
		promotionInfo.setBuyerRuleDTO(promotionBuyerRuleDTO);

		PromotionSellerRuleDTO psr = promotionSellerRuleDAO
				.selectByPromotionInfoId(promotionId);
		promotionInfo.setSellerRuleDTO(psr);

		PromotionSloganDTO psd = promotionSloganDAO
				.queryBargainSloganByPromotionId(promotionId);
		promotionInfo.setPromotionSloganDTO(psd);

		List<PromotionConfigureDTO> pclist = promotionConfigureDAO
				.selectByPromotionId(promotionId);
		if (pclist != null && pclist.size() > 0) {
			promotionInfo.setPromotionConfigureList(pclist);
		}
		return promotionInfo;
	}

	/**
	 * 更新促销活动表
	 *
	 * @param promotionInfo
	 * @return
	 * @throws PromotionCenterBusinessException
	 * @throws PromotionCenterBusinessException
	 *             ,Exception
	 */
	public PromotionExtendInfoDTO updatePromotionInfo(
			PromotionExtendInfoDTO promotionInfo) throws Exception {
		String promotionType = "";
		String promotionId = "";
		List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
		PromotionAccumulatyDTO accumulatyDTO = null;
		PromotionSloganDTO slogan = null;
		List<PromotionAccumulatyDTO> accumulatyDTOList = null;
		PromotionExtendInfoDTO extendDTO = null;
		Map<String, PromotionAccumulatyDTO> oldAccumulatyMap = new HashMap<String, PromotionAccumulatyDTO>();
		int maxLevelNum = 0;
		int vipFlg = -1;
		if (promotionInfo == null) {
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
		}
		promotionId = promotionInfo.getPromotionId();
		promotionType = promotionInfo.getPromotionType();
		promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
		if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
				DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN).equals(
				promotionType)) {
			promotionInfo.setShowStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
					DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
		} else {
			if (StringUtils.isEmpty(promotionInfo.getShowStatus())) {
				promotionInfo.setShowStatus(dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
			}
		}
		setPromotionStatusInfo(promotionInfo);
		accumulatyDTOList = promotionAccumulatyDAO
				.queryAccumulatyListByPromotionId(promotionId, null);
		if (accumulatyDTOList != null && !accumulatyDTOList.isEmpty()) {
			for (PromotionAccumulatyDTO tmpAccuDTO : accumulatyDTOList) {
				if (maxLevelNum < tmpAccuDTO.getLevelNumber()) {
					maxLevelNum = tmpAccuDTO.getLevelNumber();
				}
				oldAccumulatyMap.put(tmpAccuDTO.getLevelCode(), tmpAccuDTO);
			}
		}
		for (int i = 0; i < promotionAccumulatyList.size(); i++) {
			accumulatyDTO = promotionAccumulatyList.get(i);
			if (StringUtils.isEmpty(accumulatyDTO.getLevelCode())
					|| "0".equals(accumulatyDTO.getLevelCode())) {
				accumulatyDTO.setPromotionId(promotionId);
				accumulatyDTO.setLevelNumber(maxLevelNum + i + 1);
				accumulatyDTO.setLevelCode(noGenerator
						.generatePromotionLevelCode(promotionId));
				accumulatyDTO.setDeleteFlag(YesNoEnum.NO.getValue());
				accumulatyDTO.setCreateId(promotionInfo.getModifyId());
				accumulatyDTO.setCreateName(promotionInfo.getModifyName());
				promotionAccumulatyDAO.add(accumulatyDTO);
			} else {
				oldAccumulatyMap.remove(accumulatyDTO.getLevelCode());
			}
		}
		if (oldAccumulatyMap.size() > 0) {
			for (PromotionAccumulatyDTO oldAccuDTO : oldAccumulatyMap.values()) {
				oldAccuDTO.setModifyId(promotionInfo.getModifyId());
				oldAccuDTO.setModifyName(promotionInfo.getModifyName());
				promotionAccumulatyDAO.delete(oldAccuDTO);
			}
		}
		if (StringUtils.isNotEmpty(promotionId)) {
			extendDTO = (PromotionExtendInfoDTO) promotionInfo;
			promotionInfoExtendDAO.update(extendDTO);
		}
		promotionInfo.setHasUpFlag(1);
		promotionInfo.setIsVip(vipFlg);
		promotionInfoDAO.update(promotionInfo);

		PromotionDetailDescribeDMO promotionDetailDescribeDTO = new PromotionDetailDescribeDMO();
		PromotionDetailDescribeDTO piddd = promotionInfo
				.getPromotionDetailDescribeDTO();
		if (piddd != null) {
			promotionDetailDescribeDTO.setDescribeContent(piddd
					.getDescribeContent());
			promotionDetailDescribeDTO.setId(piddd.getId());
			promotionDetailDescribeDTO.setModifyId(promotionInfo.getModifyId());
			promotionDetailDescribeDTO.setModifyName(promotionInfo
					.getModifyName());
			promotionDetailDescribeDTO.setPromotionId(promotionInfo
					.getPromotionId());
			promotionDetailDescribeDTO.setDeleteFlag(piddd.getDeleteFlag());
			promotionDetailDescribeDAO.update(promotionDetailDescribeDTO);
		}

		List<PromotionPictureDTO> piclist = promotionInfo
				.getPromotionPictureList();
		if(null != piclist && !piclist.isEmpty()){
			for (PromotionPictureDTO ppic : piclist) {
				ppic.setModifyId(promotionInfo.getModifyId());
				ppic.setModifyName(promotionInfo.getModifyName());
				ppic.setPromotionId(promotionInfo.getPromotionId());
				promotionPictureDAO.update(ppic);
			}
		}

		PromotionBuyerRuleDTO pbr = promotionInfo.getBuyerRuleDTO();
		if (pbr != null) {
			pbr.setPromotionId(promotionInfo.getPromotionId());
			pbr.setModifyId(promotionInfo.getModifyId());
			pbr.setModifyName(promotionInfo.getModifyName());
			promotionBuyerRuleDAO.update(pbr);
		}

		PromotionSellerRuleDTO psr = promotionInfo.getSellerRuleDTO();
		if (psr != null) {
			psr.setPromotionId(promotionInfo.getPromotionId());
			psr.setModifyId(promotionInfo.getModifyId());
			psr.setModifyName(promotionInfo.getModifyName());
			promotionSellerRuleDAO.update(psr);
			
			List<PromotionSellerDetailDTO> sellerlist = psr.getSellerDetailList();
			promotionSellerDetailDAO.deleteByPromotionId(promotionId);
			if(sellerlist != null && !sellerlist.isEmpty()){
				for (PromotionSellerDetailDTO psd : sellerlist) {
					psd.setPromotionId(promotionId);
					psd.setCreateId(promotionInfo.getCreateId());
					psd.setCreateName(promotionInfo.getCreateName());
					psd.setDeleteFlag(YesNoEnum.NO.getValue());
					psd.setModifyId(promotionInfo.getModifyId());
					psd.setModifyName(promotionInfo.getModifyName());
					promotionSellerDetailDAO.add(psd);
				}
			}
		}

		// PromotionSloganDTO psrd = accumulatyDTO.getPromotionSloganDTO();
		// if(psrd!=null){
		// psrd.setModifyId(promotionInfo.getModifyId());
		// psrd.setModifyName(promotionInfo.getModifyName());
		// promotionSloganDAO.update(slogan);
		// }
		List<PromotionConfigureDTO> pclist = promotionInfo
				.getPromotionConfigureList();
		if (pclist != null && pclist.size() > 0) {
			for (PromotionConfigureDTO pcd : pclist) {
				promotionConfigureDAO.update(pcd);
			}
		}
		return (PromotionExtendInfoDTO) promotionInfo;
	}

	/**
	 * 将只有一个层级的促销活动转换成一个对象
	 *
	 * @param promotionInfo
	 * @return
	 */
	public PromotionAccumulatyDTO convertSingleAccumulatyPromotion2Info(
			PromotionInfoDTO promotionInfo) {
		List<? extends PromotionAccumulatyDTO> accumulatyList = promotionInfo
				.getPromotionAccumulatyList();
		PromotionAccumulatyDTO accumulatyLevelDTO = null;
		PromotionAccumulatyDTO accumulatyDTO = new PromotionAccumulatyDTO();
		if (accumulatyList.size() == 1) {
			accumulatyLevelDTO = accumulatyList.get(0);
			accumulatyDTO.setPromotionAccumulaty(accumulatyLevelDTO);
			promotionInfo.setPromotionAccumulatyList(null);
		}
		accumulatyDTO.setPromoionInfo(promotionInfo);
		return accumulatyDTO;
	}

	/**
	 * 将只有一个层级的促销活动转换成层级列表形式DTO
	 *
	 * @param promotionAccuDTOList
	 * @return
	 */
	public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(
			List<PromotionAccumulatyDTO> promotionAccuDTOList) {
		PromotionInfoDTO resultDTO = new PromotionInfoDTO();
		if (null != promotionAccuDTOList && !promotionAccuDTOList.isEmpty()) {
			resultDTO.setPromoionInfo(promotionAccuDTOList.get(0));
			resultDTO.setPromotionAccumulatyList(promotionAccuDTOList);
		}
		return resultDTO;
	}

	/**
	 * 查询促销活动信息,并将一个层级内容扁平化或者根据指定层级编码取得促销活动
	 *
	 * @param promotionId
	 * @param levelCode
	 * @return
	 * @throws PromotionCenterBusinessException
	 * @throws PromotionCenterBusinessException
	 *             ,Exception
	 */
	public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(
			String promotionId, String... levelCode) throws Exception {
		PromotionInfoDTO promotionInfo = queryPromotionInfo(promotionId,
				levelCode);
		logger.info("*********** promotionInfo:"
				+ JSON.toJSONString(promotionInfo) + "***********");
		PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
		logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO)
				+ "***********");
		return accuDTO;
	}

	/**
	 * 根据促销活动的有效期间设定促销活动状态
	 *
	 * @param promotionInfo
	 * @return
	 */
	public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo) {
		Date nowDt = new Date();
		String status = promotionInfo.getStatus();
		String showStatus = promotionInfo.getShowStatus();
		if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_DELETE).equals(status)) {
			return status;
		}
		if (dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(
				showStatus)
				|| dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS)
						.equals(showStatus)) {
			if (nowDt.before(promotionInfo.getEffectiveTime())) {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
			} else if (!nowDt.before(promotionInfo.getEffectiveTime())
					&& !nowDt.after(promotionInfo.getInvalidTime())) {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_START);
			} else {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_END);
			}
		} else if (StringUtils.isEmpty(status)) {
			status = dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
		}
		promotionInfo.setStatus(status);
		return status;
	}

	/**
	 * 检查促销活动会员规则
	 *
	 * @param promotionInfoDTO
	 * @param buyerInfo
	 * @param dictMap
	 * @return
	 */
	@Override
	public boolean checkPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO,
			BuyerCheckInfo buyerInfo, Map<String, String> dictMap) {
		PromotionBuyerRuleDTO ruleDTO = promotionInfoDTO.getBuyerRuleDTO();
		List<String> levelList = null;
		List<String> groupList = null;
		List<PromotionBuyerDetailDTO> detailList = null;
		if (ruleDTO == null) {
			return true;
		}
		if (dictMap.get(
				DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&"
						+ DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT)
				.equals(ruleDTO.getRuleTargetType())) {
			detailList = ruleDTO.getBuyerDetailList();
			if (detailList == null || detailList.isEmpty()) {
				return true;
			}
			for (PromotionBuyerDetailDTO detailDTO : detailList) {
				if (detailDTO.getBuyerCode().equals(buyerInfo.getBuyerCode())) {
					return true;
				}
			}
			return false;
		} else if (dictMap.get(
				DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&"
						+ DictionaryConst.OPT_PROMOTION_BUYER_RULE_FIRST_LOGIN)
				.equals(ruleDTO.getRuleTargetType())) {
			if (buyerInfo.getIsFirstLogin() == 1) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 检查促销活动卖家规则
	 *
	 * @param promotionInfoDTO
	 * @param sellerCode
	 * @param dictMap
	 * @return
	 */
	@Override
	public boolean checkPromotionSellerRule(PromotionInfoDTO promotionInfoDTO,
			String sellerCode, Map<String, String> dictMap) {
		PromotionSellerDetailDTO sellerDetailDTO = null;

		sellerDetailDTO = getPromotionSellerInfo(promotionInfoDTO, sellerCode,
				dictMap);
		if (sellerDetailDTO == null) {
			return false;
		}
		return true;
	}

	/**
	 * 检查促销活动卖家规则
	 *
	 * @param promotionInfoDTO
	 * @param sellerCode
	 * @param dictMap
	 * @return
	 */
	@Override
	public PromotionSellerDetailDTO getPromotionSellerInfo(
			PromotionInfoDTO promotionInfoDTO, String sellerCode,
			Map<String, String> dictMap) {
		PromotionSellerRuleDTO ruleDTO = promotionInfoDTO.getSellerRuleDTO();
		List<PromotionSellerDetailDTO> detailList = null;

		detailList = ruleDTO.getSellerDetailList();
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		for (PromotionSellerDetailDTO detailDTO : detailList) {
			if (detailDTO.getSellerCode().equals(sellerCode)) {
				return detailDTO;
			}
		}
		return null;
	}

	// @Override
	// public PromotionInfoDTO addPromotionInfo(PromotionInfoEditReqDTO pied) {
	// if (pied == null) {
	// throw new
	// PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
	// "促销活动参数不能为空");
	// }
	// PromotionInfoDTO pid = new PromotionInfoDTO();
	// pid.setCostAllocationType(pied.getCostAllocationType());
	// pid.setDealFlag(pied.getDealFlag());
	// pid.setEffectiveTime(pied.getEffectiveTime());
	// pid.setHasRedisClean(pied.getHasRedisClean());
	// pid.setInvalidTime(pied.getInvalidTime());
	// pid.setIsVip(pied.getIsVip());
	// pid.setModifyPromotionId(pied.getModifyPromotionId());
	// pid.setPromotionDescribe(pied.getPromotionDescribe());
	// pid.setPromotionName(pied.getPromotionName());
	// pid.setPromotionProviderSellerCode(pied.getPromotionProviderSellerCode());
	// pid.setPromotionProviderShopId(pied.getPromotionProviderShopId());
	// pid.setPromotionProviderType(pied.getPromotionProviderType());
	// pid.setPromotionType(pied.getPromotionType());
	// pid.setShowStatus(pied.getShowStatus());
	// pid.setStatus(pied.getStatus());
	// pid.setVerifierId(pied.getVerifierId());
	// pid.setVerifierName(pied.getVerifierName());
	// pid.setVerifyRemark(pied.getVerifyRemark());
	// pid.setVerifyTime(pied.getVerifyTime());
	//
	// pid.setCreateId(pied.getCreateId());
	// pid.setCreateName(pied.getCreateName());
	// pid.setModifyId(pied.getCreateId());
	// pid.setModifyName(pied.getCreateName());
	//
	// String promotionId="";
	//
	// promotionId = noGenerator.generatePromotionId(pied.getPromotionType());
	// pid.setPromotionId(promotionId);
	//
	// promotionInfoDAO.add(pid);
	// List<PromotionAccumulatyReqDTO> promotionAccumulatyList =
	// pied.getPromotionAccumulatyList();
	// List<PromotionAccumulatyDTO> paList = new
	// ArrayList<PromotionAccumulatyDTO>();
	// if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty())
	// {
	// throw new
	// PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
	// "促销活动层级不能为空");
	// }
	// PromotionAccumulatyReqDTO accumulatyDTO = null;
	// PromotionAccumulatyDTO accDTO = null;
	// // PromotionAwardInfoDTO padDTO = null;
	// // PromotionAwardInfoReqDTO padrDTO = null;
	// for (int i = 0; i < promotionAccumulatyList.size(); i++) {
	// accDTO = new PromotionAccumulatyDTO();
	// accumulatyDTO = promotionAccumulatyList.get(i);
	// accDTO.setPromotionId(promotionId);
	// accDTO.setLevelNumber(i + 1);
	// String lc = noGenerator.generatePromotionLevelCode(promotionId);
	// accDTO.setLevelCode(lc);
	// accDTO.setDeleteFlag(YesNoEnum.NO.getValue());
	//
	// accDTO.setCreateId(pied.getCreateId());
	// accDTO.setCreateName(pied.getCreateName());
	// accDTO.setModifyId(pied.getCreateId());
	// accDTO.setModifyName(pied.getCreateName());
	//
	// accDTO.setAddupType(accumulatyDTO.getAddupType());
	// accDTO.setLevelAmount(accumulatyDTO.getLevelAmount());
	// accDTO.setQuantifierType(accumulatyDTO.getQuantifierType());
	// promotionAccumulatyDAO.add(accDTO);
	// paList.add(accDTO);
	// // padrDTO = accumulatyDTO.getPromotionAwardInfoReqDTO();
	// // padDTO = new PromotionAwardInfoDTO();
	// //
	// // padDTO.setPromotionId(promotionId);
	// // padDTO.setLevelCode(lc);
	// //
	// // padDTO.setAwardName(padrDTO.getAwardName());
	// // padDTO.setAwardRuleDescribe(padrDTO.getAwardRuleDescribe());
	// // padDTO.setAwardType(padrDTO.getAwardType());
	// // padDTO.setAwardValue(padrDTO.getAwardValue());
	// // padDTO.setProvideCount(padrDTO.getProvideCount());
	// // padDTO.setDeleteFlag(YesNoEnum.NO.getValue());
	// // padDTO.setCreateId(pied.getCreateId());
	// // padDTO.setCreateName(pied.getCreateName());
	// // padDTO.setModifyId(pied.getCreateId());
	// // padDTO.setModifyName(pied.getCreateName());
	// // promotionAwardInfoDAO.add(padDTO);
	// }
	// pid.setPromotionAccumulatyList(paList);
	//
	// PromotionExtendInfoDTO pteDTO = new PromotionExtendInfoDTO();
	// PromotionExtendInfoReqDTO ped = pied.getPromotionExtendInfoReqDTO();
	// pteDTO.setContactAddress(ped.getContactAddress());
	// pteDTO.setContactName(ped.getContactName());
	// pteDTO.setContactTelephone(ped.getContactTelephone());
	// pteDTO.setCycleTimeType(ped.getCycleTimeType());
	// pteDTO.setCycleTimeValue(ped.getCycleTimeValue());
	// pteDTO.setDailyBuyerPartakeTimes(ped.getDailyBuyerPartakeTimes());
	// pteDTO.setDailyBuyerWinningTimes(ped.getDailyBuyerWinningTimes());
	// pteDTO.setDailyWinningTimes(ped.getDailyWinningTimes());
	// pteDTO.setEachEndTime(ped.getEachEndTime());
	// pteDTO.setEachStartTime(ped.getEachStartTime());
	// pteDTO.setIsDailyTimesLimit(ped.getIsDailyTimesLimit());
	// pteDTO.setIsShareTimesLimit(ped.getIsShareTimesLimit());
	// pteDTO.setIsTotalTimesLimit(ped.getIsTotalTimesLimit());
	// pteDTO.setOfflineEndTime(ped.getOfflineEndTime());
	// pteDTO.setOfflineStartTime(ped.getOfflineStartTime());
	// pteDTO.setShareExtraPartakeTimes(ped.getShareExtraPartakeTimes());
	// pteDTO.setTemplateFlag(ped.getTemplateFlag());
	// pteDTO.setTopExtraPartakeTimes(ped.getTopExtraPartakeTimes());
	// pteDTO.setTotalPartakeTimes(ped.getTotalPartakeTimes());
	//
	// pteDTO.setCreateId(pied.getCreateId());
	// pteDTO.setCreateName(pied.getCreateName());
	// pteDTO.setModifyId(pied.getCreateId());
	// pteDTO.setModifyName(pied.getCreateName());
	// pteDTO.setPromotionId(promotionId);
	//
	// promotionInfoExtendDAO.add(pteDTO);
	//
	// PromotionDetailDescribeDMO promotionDetailDescribeDTO = null;
	// promotionDetailDescribeDTO = new PromotionDetailDescribeDMO();
	// PromotionDetailDescribeReqDTO piddd =
	// pied.getPromotionDetailDescribeReqDTO();
	// promotionDetailDescribeDTO.setDescribeContent(piddd.getDescribeContent());
	// promotionDetailDescribeDTO.setCreateId(pied.getCreateId());
	// promotionDetailDescribeDTO.setCreateName(pied.getCreateName());
	// promotionDetailDescribeDTO.setModifyId(pied.getCreateId());
	// promotionDetailDescribeDTO.setModifyName(pied.getCreateName());
	// promotionDetailDescribeDTO.setPromotionId(promotionId);
	// promotionDetailDescribeDTO.setDeleteFlag(YesNoEnum.NO.getValue());
	// promotionDetailDescribeDAO.add(promotionDetailDescribeDTO);
	//
	// List<PromotionPictureReqDTO> piclist = pied.getPromotionPictureReqDTO();
	// PromotionPictureDTO ppic = null;
	// for (PromotionPictureReqDTO promotionPictureReqDTO : piclist) {
	// ppic = new PromotionPictureDTO();
	// ppic.setPromotionPictureType(promotionPictureReqDTO.getPromotionPictureType());
	// ppic.setPromotionPictureUrl(promotionPictureReqDTO.getPromotionPictureUrl());
	// ppic.setDeleteFlag(YesNoEnum.NO.getValue());
	// ppic.setCreateId(pied.getCreateId());
	// ppic.setCreateName(pied.getCreateName());
	// ppic.setModifyId(pied.getCreateId());
	// ppic.setModifyName(pied.getCreateName());
	// ppic.setPromotionId(promotionId);
	// promotionPictureDAO.add(ppic);
	// }
	//
	// PromotionBuyerRuleDTO pbr = new PromotionBuyerRuleDTO();
	// PromotionBuyerRuleReqDTO promotionBuyerRuleReqDTO=
	// pied.getPromotionBuyerRuleReqDTO();
	// pbr.setRuleName(promotionBuyerRuleReqDTO.getRuleName());
	// pbr.setRuleTargetType(promotionBuyerRuleReqDTO.getRuleTargetType());
	// pbr.setTargetBuyerGroup(promotionBuyerRuleReqDTO.getTargetBuyerGroup());
	// pbr.setTargetBuyerLevel(promotionBuyerRuleReqDTO.getTargetBuyerLevel());
	// pbr.setPromotionId(promotionId);
	// pbr.setDeleteFlag(YesNoEnum.NO.getValue());
	// pbr.setCreateId(pied.getCreateId());
	// pbr.setCreateName(pied.getCreateName());
	// pbr.setModifyId(pied.getCreateId());
	// pbr.setModifyName(pied.getCreateName());
	// promotionBuyerRuleDAO.add(pbr);
	//
	// PromotionSellerRuleDTO psr = new PromotionSellerRuleDTO();
	// PromotionSellerRuleReqDTO promotionSellerRuleReqDTO =
	// pied.getPromotionSellerRuleReqDTO();
	// psr.setPromotionId(promotionId);
	// psr.setRuleName(promotionSellerRuleReqDTO.getRuleName());
	// psr.setRuleTargetType(promotionSellerRuleReqDTO.getRuleTargetType());
	// psr.setTargetSellerType(promotionSellerRuleReqDTO.getTargetSellerType());
	// psr.setDeleteFlag(YesNoEnum.NO.getValue());
	// psr.setCreateId(pied.getCreateId());
	// psr.setCreateName(pied.getCreateName());
	// psr.setModifyId(pied.getCreateId());
	// psr.setModifyName(pied.getCreateName());
	// promotionSellerRuleDAO.add(psr);
	//
	// List<PromotionSellerDetailReqDTO> sellerlist =
	// promotionSellerRuleReqDTO.getPromotionSellerDetailList();
	// PromotionSellerDetailDTO psd = null;
	// for (PromotionSellerDetailReqDTO promotionSellerDetailReqDTO :
	// sellerlist) {
	// psd = new PromotionSellerDetailDTO();
	// psd.setBelongSuperiorName(promotionSellerDetailReqDTO.getBelongSuperiorName());
	// psd.setPromotionId(promotionId);
	// psd.setSellerCode(promotionSellerDetailReqDTO.getSellerCode());
	// psd.setSellerName(promotionSellerDetailReqDTO.getSellerName());
	//
	// psd.setCreateId(pied.getCreateId());
	// psd.setCreateName(pied.getCreateName());
	// psd.setModifyId(pied.getCreateId());
	// psd.setModifyName(pied.getCreateName());
	// psd.setDeleteFlag(YesNoEnum.NO.getValue());
	// promotionSellerDetailDAO.add(psd);
	// }
	// PromotionSloganReqDTO psrd = pied.getPromotionSloganReqDTO();
	// if(psrd!=null){
	// PromotionSloganDTO slogan = new PromotionSloganDTO();
	// slogan.setPromotionId(promotionId);
	// slogan.setPromotionSlogan(psrd.getPromotionSlogan());
	// slogan.setCreateId(pied.getCreateId());
	// slogan.setCreateName(pied.getCreateName());
	// slogan.setModifyId(pied.getCreateId());
	// slogan.setModifyName(pied.getCreateName());
	// promotionSloganDAO.add(slogan);
	// }
	// List<PromotionConfigureReqDTO> pclist = pied.getPromotionConfigureList();
	// if(pclist!=null && pclist.size()>0){
	// PromotionConfigureDTO pcd = null;
	// for (PromotionConfigureReqDTO promotionConfigureReqDTO : pclist) {
	// pcd = new PromotionConfigureDTO();
	// pcd.setConfType(promotionConfigureReqDTO.getConfType());
	// pcd.setConfValue(promotionConfigureReqDTO.getConfValue());
	// pcd.setPromotionId(promotionId);
	// pcd.setCreateId(pied.getCreateId());
	// pcd.setCreateName(pied.getCreateName());
	// pcd.setDeleteFlag(YesNoEnum.NO.getValue());
	// promotionConfigureDAO.add(pcd);
	// }
	// }
	// return pid;
	// }

	// @Override
	// public PromotionInfoDTO editPromotionInfo(PromotionInfoEditReqDTO pied) {
	//
	// if (pied == null) {
	// throw new
	// PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
	// "促销活动参数不能为空");
	// }
	// PromotionInfoDTO pid = new PromotionInfoDTO();
	// pid.setId(pied.getId());
	// pid.setCostAllocationType(pied.getCostAllocationType());
	// pid.setDealFlag(pied.getDealFlag());
	// pid.setEffectiveTime(pied.getEffectiveTime());
	// pid.setHasRedisClean(pied.getHasRedisClean());
	// pid.setInvalidTime(pied.getInvalidTime());
	// pid.setIsVip(pied.getIsVip());
	// pid.setModifyPromotionId(pied.getModifyPromotionId());
	// pid.setPromotionDescribe(pied.getPromotionDescribe());
	// pid.setPromotionName(pied.getPromotionName());
	// pid.setPromotionProviderSellerCode(pied.getPromotionProviderSellerCode());
	// pid.setPromotionProviderShopId(pied.getPromotionProviderShopId());
	// pid.setPromotionProviderType(pied.getPromotionProviderType());
	// pid.setPromotionType(pied.getPromotionType());
	// pid.setShowStatus(pied.getShowStatus());
	// pid.setStatus(pied.getStatus());
	// pid.setVerifierId(pied.getVerifierId());
	// pid.setVerifierName(pied.getVerifierName());
	// pid.setVerifyRemark(pied.getVerifyRemark());
	// pid.setVerifyTime(pied.getVerifyTime());
	//
	// pid.setModifyId(pied.getModifyId());
	// pid.setModifyName(pied.getModifyName());
	//
	// pid.setPromotionId(pied.getPromotionId());
	//
	// promotionInfoDAO.update(pid);
	// List<PromotionAccumulatyReqDTO> promotionAccumulatyList =
	// pied.getPromotionAccumulatyList();
	// List<PromotionAccumulatyDTO> paList = new
	// ArrayList<PromotionAccumulatyDTO>();
	// if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty())
	// {
	// throw new
	// PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
	// "促销活动层级不能为空");
	// }
	// PromotionAccumulatyReqDTO accumulatyDTO = null;
	// PromotionAccumulatyDTO accDTO = null;
	// // PromotionAwardInfoDTO padDTO = null;
	// // PromotionAwardInfoReqDTO padrDTO = null;
	// for (int i = 0; i < promotionAccumulatyList.size(); i++) {
	// accDTO = new PromotionAccumulatyDTO();
	// accumulatyDTO = promotionAccumulatyList.get(i);
	// accDTO.setId(accumulatyDTO.getId());
	// accDTO.setPromotionId(pied.getPromotionId());
	// accDTO.setLevelNumber(accumulatyDTO.getLevelNumber());
	// accDTO.setLevelCode(accumulatyDTO.getLevelCode());
	// accDTO.setDeleteFlag(accumulatyDTO.getDeleteFlag());
	//
	// accDTO.setModifyId(pied.getModifyId());
	// accDTO.setModifyName(pied.getModifyName());
	//
	// accDTO.setAddupType(accumulatyDTO.getAddupType());
	// accDTO.setLevelAmount(accumulatyDTO.getLevelAmount());
	// accDTO.setQuantifierType(accumulatyDTO.getQuantifierType());
	// promotionAccumulatyDAO.update(accDTO);
	// paList.add(accDTO);
	// // padrDTO = accumulatyDTO.getPromotionAwardInfoReqDTO();
	// // padDTO = new PromotionAwardInfoDTO();
	// // padDTO.setAwardId(padrDTO.getAwardId());
	// // padDTO.setPromotionId(pied.getPromotionId());
	// // padDTO.setLevelCode(padrDTO.getLevelCode());
	// //
	// // padDTO.setAwardName(padrDTO.getAwardName());
	// // padDTO.setAwardRuleDescribe(padrDTO.getAwardRuleDescribe());
	// // padDTO.setAwardType(padrDTO.getAwardType());
	// // padDTO.setAwardValue(padrDTO.getAwardValue());
	// // padDTO.setProvideCount(padrDTO.getProvideCount());
	// // padDTO.setModifyId(pied.getModifyId());
	// // padDTO.setModifyName(pied.getModifyName());
	// // promotionAwardInfoDAO.update(padDTO);
	// }
	// pid.setPromotionAccumulatyList(paList);
	// PromotionExtendInfoDTO pteDTO = new PromotionExtendInfoDTO();
	// PromotionExtendInfoReqDTO ped = pied.getPromotionExtendInfoReqDTO();
	// pteDTO.setId(ped.getId());
	// pteDTO.setContactAddress(ped.getContactAddress());
	// pteDTO.setContactName(ped.getContactName());
	// pteDTO.setContactTelephone(ped.getContactTelephone());
	// pteDTO.setCycleTimeType(ped.getCycleTimeType());
	// pteDTO.setCycleTimeValue(ped.getCycleTimeValue());
	// pteDTO.setDailyBuyerPartakeTimes(ped.getDailyBuyerPartakeTimes());
	// pteDTO.setDailyBuyerWinningTimes(ped.getDailyBuyerWinningTimes());
	// pteDTO.setDailyWinningTimes(ped.getDailyWinningTimes());
	// pteDTO.setEachEndTime(ped.getEachEndTime());
	// pteDTO.setEachStartTime(ped.getEachStartTime());
	// pteDTO.setIsDailyTimesLimit(ped.getIsDailyTimesLimit());
	// pteDTO.setIsShareTimesLimit(ped.getIsShareTimesLimit());
	// pteDTO.setIsTotalTimesLimit(ped.getIsTotalTimesLimit());
	// pteDTO.setOfflineEndTime(ped.getOfflineEndTime());
	// pteDTO.setOfflineStartTime(ped.getOfflineStartTime());
	// pteDTO.setShareExtraPartakeTimes(ped.getShareExtraPartakeTimes());
	// pteDTO.setTemplateFlag(ped.getTemplateFlag());
	// pteDTO.setTopExtraPartakeTimes(ped.getTopExtraPartakeTimes());
	// pteDTO.setTotalPartakeTimes(ped.getTotalPartakeTimes());
	//
	// pteDTO.setModifyId(pied.getModifyId());
	// pteDTO.setModifyName(pied.getModifyName());
	// pteDTO.setPromotionId(pied.getPromotionId());
	//
	// promotionInfoExtendDAO.update(pteDTO);
	//
	// PromotionDetailDescribeDMO promotionDetailDescribeDTO = new
	// PromotionDetailDescribeDMO();
	// PromotionDetailDescribeReqDTO piddd =
	// pied.getPromotionDetailDescribeReqDTO();
	// promotionDetailDescribeDTO.setDescribeContent(piddd.getDescribeContent());
	// promotionDetailDescribeDTO.setId(piddd.getId());
	// promotionDetailDescribeDTO.setModifyId(pied.getModifyId());
	// promotionDetailDescribeDTO.setModifyName(pied.getModifyName());
	// promotionDetailDescribeDTO.setPromotionId(pied.getPromotionId());
	// promotionDetailDescribeDTO.setDeleteFlag(piddd.getDeleteFlag());
	// promotionDetailDescribeDAO.update(promotionDetailDescribeDTO);
	//
	// List<PromotionPictureReqDTO> piclist = pied.getPromotionPictureReqDTO();
	// PromotionPictureDTO ppic = null;
	// for (PromotionPictureReqDTO promotionPictureReqDTO : piclist) {
	// ppic = new PromotionPictureDTO();
	// ppic.setPromotionPictureType(promotionPictureReqDTO.getPromotionPictureType());
	// ppic.setPromotionPictureUrl(promotionPictureReqDTO.getPromotionPictureUrl());
	// ppic.setDeleteFlag(promotionPictureReqDTO.getDeleteFlag());
	// ppic.setModifyId(pied.getModifyId());
	// ppic.setModifyName(pied.getModifyName());
	// ppic.setPromotionId(pied.getPromotionId());
	// promotionPictureDAO.update(ppic);
	// }
	//
	// PromotionBuyerRuleDTO pbr = new PromotionBuyerRuleDTO();
	// PromotionBuyerRuleReqDTO promotionBuyerRuleReqDTO=
	// pied.getPromotionBuyerRuleReqDTO();
	// pbr.setId(promotionBuyerRuleReqDTO.getId());
	// pbr.setRuleName(promotionBuyerRuleReqDTO.getRuleName());
	// pbr.setRuleTargetType(promotionBuyerRuleReqDTO.getRuleTargetType());
	// pbr.setTargetBuyerGroup(promotionBuyerRuleReqDTO.getTargetBuyerGroup());
	// pbr.setTargetBuyerLevel(promotionBuyerRuleReqDTO.getTargetBuyerLevel());
	// pbr.setPromotionId(pied.getPromotionId());
	// pbr.setDeleteFlag(promotionBuyerRuleReqDTO.getDeleteFlag());
	// pbr.setModifyId(pied.getModifyId());
	// pbr.setModifyName(pied.getModifyName());
	// promotionBuyerRuleDAO.update(pbr);
	//
	// PromotionSellerRuleDTO psr = new PromotionSellerRuleDTO();
	// PromotionSellerRuleReqDTO promotionSellerRuleReqDTO =
	// pied.getPromotionSellerRuleReqDTO();
	// psr.setId(promotionSellerRuleReqDTO.getId());
	// psr.setPromotionId(pied.getPromotionId());
	// psr.setRuleName(promotionSellerRuleReqDTO.getRuleName());
	// psr.setRuleTargetType(promotionSellerRuleReqDTO.getRuleTargetType());
	// psr.setTargetSellerType(promotionSellerRuleReqDTO.getTargetSellerType());
	// psr.setDeleteFlag(promotionSellerRuleReqDTO.getDeleteFlag());
	// psr.setModifyId(pied.getModifyId());
	// psr.setModifyName(pied.getModifyName());
	//
	// promotionSellerRuleDAO.update(psr);
	//
	// List<PromotionSellerDetailReqDTO> sellerlist =
	// promotionSellerRuleReqDTO.getPromotionSellerDetailList();
	// PromotionSellerDetailDTO psd = null;
	// for (PromotionSellerDetailReqDTO promotionSellerDetailReqDTO :
	// sellerlist) {
	// psd = new PromotionSellerDetailDTO();
	// psd.setBelongSuperiorName(promotionSellerDetailReqDTO.getBelongSuperiorName());
	// psd.setId(promotionSellerDetailReqDTO.getId());
	// psd.setPromotionId(pied.getPromotionId());
	// psd.setSellerCode(promotionSellerDetailReqDTO.getSellerCode());
	// psd.setSellerName(promotionSellerDetailReqDTO.getSellerName());
	//
	// psd.setModifyId(pied.getModifyId());
	// psd.setModifyName(pied.getModifyName());
	// psd.setDeleteFlag(promotionSellerDetailReqDTO.getDeleteFlag());
	// promotionSellerDetailDAO.update(psd);
	// }
	// PromotionSloganReqDTO psrd = pied.getPromotionSloganReqDTO();
	// if(psrd!=null){
	// PromotionSloganDTO slogan = new PromotionSloganDTO();
	// slogan.setId(psrd.getId());
	// slogan.setPromotionId(pied.getPromotionId());
	// slogan.setPromotionSlogan(psrd.getPromotionSlogan());
	// slogan.setModifyId(pied.getModifyId());
	// slogan.setModifyName(pied.getModifyName());
	// promotionSloganDAO.update(slogan);
	// }
	// List<PromotionConfigureReqDTO> pclist = pied.getPromotionConfigureList();
	// if(pclist!=null && pclist.size()>0){
	// PromotionConfigureDTO pcd = null;
	// for (PromotionConfigureReqDTO promotionConfigureReqDTO : pclist) {
	// pcd = new PromotionConfigureDTO();
	// pcd.setId(promotionConfigureReqDTO.getId());
	// pcd.setConfType(promotionConfigureReqDTO.getConfType());
	// pcd.setConfValue(promotionConfigureReqDTO.getConfValue());
	// pcd.setPromotionId(pied.getPromotionId());
	// pcd.setDeleteFlag(promotionConfigureReqDTO.getDeleteFlag());
	// promotionConfigureDAO.update(pcd);
	// }
	// }
	// return pid;
	//
	// }
	// @Override
	// public PromotionInfoEditResDTO viewPromotionInfo(String promotionInfoId)
	// {
	// PromotionInfoEditResDTO pid = new PromotionInfoEditResDTO();
	//
	// PromotionInfoDTO pied = promotionInfoDAO.queryById(promotionInfoId);
	// if (pied == null) {
	// throw new
	// PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
	// "促销活动不存在");
	// }
	// pid.setId(pied.getId());
	// pid.setCostAllocationType(pied.getCostAllocationType());
	// pid.setDealFlag(pied.getDealFlag());
	// pid.setEffectiveTime(pied.getEffectiveTime());
	// pid.setHasRedisClean(pied.getHasRedisClean());
	// pid.setInvalidTime(pied.getInvalidTime());
	// pid.setIsVip(pied.getIsVip());
	// pid.setModifyPromotionId(pied.getModifyPromotionId());
	// pid.setPromotionDescribe(pied.getPromotionDescribe());
	// pid.setPromotionName(pied.getPromotionName());
	// pid.setPromotionProviderSellerCode(pied.getPromotionProviderSellerCode());
	// pid.setPromotionProviderShopId(pied.getPromotionProviderShopId());
	// pid.setPromotionProviderType(pied.getPromotionProviderType());
	// pid.setPromotionType(pied.getPromotionType());
	// pid.setShowStatus(pied.getShowStatus());
	// pid.setStatus(pied.getStatus());
	// pid.setVerifierId(pied.getVerifierId());
	// pid.setVerifierName(pied.getVerifierName());
	// pid.setVerifyRemark(pied.getVerifyRemark());
	// pid.setVerifyTime(pied.getVerifyTime());
	// pid.setCreateId(pied.getCreateId());
	// pid.setCreateName(pied.getCreateName());
	// pid.setModifyId(pied.getModifyId());
	// pid.setModifyName(pied.getModifyName());
	// pid.setCreateTime(pied.getCreateTime());
	// pid.setModifyTime(pied.getModifyTime());
	//
	// pid.setPromotionId(pied.getPromotionId());
	//
	// List<PromotionAccumulatyResDTO> promotionAccumulatyResList = new
	// ArrayList<PromotionAccumulatyResDTO>();
	// List<PromotionAccumulatyDTO> promotionAccumulatyList =
	// promotionAccumulatyDAO.queryAccumulatyListByPromotionId(promotionInfoId,
	// null);
	// PromotionAccumulatyResDTO accDTO = null;
	// for (PromotionAccumulatyDTO accumulatyDTO : promotionAccumulatyList) {
	// accDTO = new PromotionAccumulatyResDTO();
	// accDTO.setPromotionId(pied.getPromotionId());
	// accDTO.setLevelNumber(accumulatyDTO.getLevelNumber());
	// accDTO.setLevelCode(accumulatyDTO.getLevelCode());
	// accDTO.setDeleteFlag(accumulatyDTO.getDeleteFlag());
	//
	// accDTO.setCreateId(accumulatyDTO.getCreateId());
	// accDTO.setCreateName(accumulatyDTO.getCreateName());
	// accDTO.setModifyId(accumulatyDTO.getModifyId());
	// accDTO.setModifyName(accumulatyDTO.getModifyName());
	//
	// accDTO.setAddupType(accumulatyDTO.getAddupType());
	// accDTO.setLevelAmount(accumulatyDTO.getLevelAmount());
	// accDTO.setQuantifierType(accumulatyDTO.getQuantifierType());
	// promotionAccumulatyResList.add(accDTO);
	// }
	// pid.setPromotionAccumulatyList(promotionAccumulatyResList);
	//
	//
	// PromotionExtendInfoDTO pteDTO =
	// promotionInfoExtendDAO.queryById(promotionInfoId);
	//
	// pid.setPromotionExtendInfoDTO(pteDTO);
	//
	// PromotionDetailDescribeDMO record = new PromotionDetailDescribeDMO();
	// record.setPromotionId(promotionInfoId);
	// PromotionDetailDescribeDMO promotionDetailDescribeInfo =
	// promotionDetailDescribeDAO
	// .selectByPromotionId(record);
	// PromotionDetailDescribeDTO promotionDetailDescribeDTO = new
	// PromotionDetailDescribeDTO();
	// promotionDetailDescribeDTO.setDescribeContent(promotionDetailDescribeInfo.getDescribeContent());
	// promotionDetailDescribeDTO.setCreateId(promotionDetailDescribeInfo.getCreateId());
	// promotionDetailDescribeDTO.setCreateName(promotionDetailDescribeInfo.getCreateName());
	// promotionDetailDescribeDTO.setCreateTime(promotionDetailDescribeInfo.getCreateTime());
	// promotionDetailDescribeDTO.setModifyId(promotionDetailDescribeInfo.getModifyId());
	// promotionDetailDescribeDTO.setModifyName(promotionDetailDescribeInfo.getModifyName());
	// promotionDetailDescribeDTO.setModifyTime(promotionDetailDescribeInfo.getModifyTime());
	// promotionDetailDescribeDTO.setPromotionId(promotionInfoId);
	// promotionDetailDescribeDTO.setDeleteFlag(promotionDetailDescribeInfo.getDeleteFlag());
	// pid.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO);
	//
	// List<PromotionPictureDTO> piclist =
	// promotionPictureDAO.selectByPromotionInfoId(promotionInfoId);
	// pid.setPromotionPictureDTO(piclist);
	//
	// PromotionBuyerRuleDTO promotionBuyerRuleDTO =
	// promotionBuyerRuleDAO.selectByPromotionInfoId(promotionInfoId);
	// pid.setPromotionBuyerRuleDTO(promotionBuyerRuleDTO);
	//
	// PromotionSellerRuleDTO psr =
	// promotionSellerRuleDAO.selectByPromotionInfoId(promotionInfoId);
	// pid.setPromotionSellerRuleDTO(psr);
	//
	// PromotionSloganDTO psd =
	// promotionSloganDAO.queryBargainSloganByPromotionId(promotionInfoId);
	// pid.setPromotionSloganDTO(psd);
	//
	//
	// List<PromotionConfigureDTO> pclist =
	// promotionConfigureDAO.selectByPromotionId(promotionInfoId);
	// if(pclist!=null && pclist.size()>0){
	// pid.setPromotionConfigureList(pclist);
	// }
	//
	// return pid;
	// }
}
