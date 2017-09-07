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

    private static final Logger logger = LoggerFactory.getLogger(PromotionBaseServiceImpl.class);
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
    @Override
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
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_RULE_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_SELLER_RULE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_BUYER_RULE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_CYCLE_TIME_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_PRICTURE_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_ADDUP_TYPE);
        initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_QUANTIFIER_TYPE);
        return dictMap;
    }

    /**
     * 删除促销活动
     *
     * @param validDTO
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public void deletePromotionInfo(PromotionValidDTO validDTO) throws PromotionCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = null;
        PromotionAccumulatyDTO accumulaty = new PromotionAccumulatyDTO();
        try {
            // 根据活动ID获取活动信息
            promotionInfo = promotionInfoDAO.queryById(validDTO.getPromotionId());
            if (promotionInfo == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "该促销活动不存在");
            }
            // 活动已删除
            if (dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE)
                    .equals(promotionInfo.getStatus())) {
                return;
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(promotionInfo.getShowStatus())) {
                if (!dictionary
                        .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_END)
                        .equals(promotionInfo.getStatus())) {
                }
            }
            promotionInfo.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
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

    /**
     * 插入促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public PromotionExtendInfoDTO insertPromotionInfo(PromotionExtendInfoDTO promotionInfo)
            throws PromotionCenterBusinessException, Exception {
        String promotionType = "";
        String promotionId = "";
        List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
        PromotionAccumulatyDTO accumulatyDTO = null;
        int vipFlg = -1;
        if (promotionInfo == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
        }
        promotionType = promotionInfo.getPromotionType();
        promotionId = noGenerator.generatePromotionId(promotionType);
        promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
        if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty()) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动层级不能为空");
        }
        promotionInfo.setPromotionId(promotionId);
        setPromotionStatusInfo(promotionInfo);
        for (int i = 0; i < promotionAccumulatyList.size(); i++) {
            accumulatyDTO = promotionAccumulatyList.get(i);
            accumulatyDTO.setPromotionId(promotionId);
            accumulatyDTO.setLevelNumber(i + 1);
            accumulatyDTO.setLevelCode(noGenerator.generatePromotionLevelCode(promotionId));
            accumulatyDTO.setDeleteFlag(YesNoEnum.NO.getValue());
            accumulatyDTO.setCreateId(promotionInfo.getCreateId());
            accumulatyDTO.setCreateName(promotionInfo.getCreateName());
            promotionAccumulatyDAO.add(accumulatyDTO);
        }
        promotionInfoExtendDAO.add(promotionInfo);
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.add(promotionInfo);

        PromotionDetailDescribeDMO promotionDetailDescribeDTO = new PromotionDetailDescribeDMO();
        PromotionDetailDescribeDTO piddd = promotionInfo.getPromotionDetailDescribeDTO();
        if (piddd != null) {
            promotionDetailDescribeDTO.setDescribeContent(piddd.getDescribeContent());
            promotionDetailDescribeDTO.setCreateId(promotionInfo.getCreateId());
            promotionDetailDescribeDTO.setCreateName(promotionInfo.getCreateName());
            promotionDetailDescribeDTO.setModifyId(promotionInfo.getCreateId());
            promotionDetailDescribeDTO.setModifyName(promotionInfo.getCreateName());
            promotionDetailDescribeDTO.setPromotionId(promotionId);
            promotionDetailDescribeDTO.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionDetailDescribeDAO.add(promotionDetailDescribeDTO);
        }

        List<PromotionPictureDTO> piclist = promotionInfo.getPromotionPictureList();
        if (null != piclist && !piclist.isEmpty()) {
            for (PromotionPictureDTO promotionPictureReqDTO : piclist) {
                promotionPictureReqDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                promotionPictureReqDTO.setCreateId(promotionInfo.getCreateId());
                promotionPictureReqDTO.setCreateName(promotionInfo.getCreateName());
                promotionPictureReqDTO.setModifyId(promotionInfo.getCreateId());
                promotionPictureReqDTO.setModifyName(promotionInfo.getCreateName());
                promotionPictureReqDTO.setPromotionId(promotionId);
                promotionPictureDAO.add(promotionPictureReqDTO);
            }
        }
        PromotionBuyerRuleDTO promotionBuyerRuleReqDTO = promotionInfo.getBuyerRuleDTO();
        if (null != promotionBuyerRuleReqDTO) {
            promotionBuyerRuleReqDTO.setPromotionId(promotionId);
            promotionBuyerRuleReqDTO.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionBuyerRuleReqDTO.setCreateId(promotionInfo.getCreateId());
            promotionBuyerRuleReqDTO.setCreateName(promotionInfo.getCreateName());
            promotionBuyerRuleReqDTO.setModifyId(promotionInfo.getCreateId());
            promotionBuyerRuleReqDTO.setModifyName(promotionInfo.getCreateName());
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
        List<PromotionConfigureDTO> pclist = promotionInfo.getPromotionConfigureList();
        if (pclist != null && pclist.size() > 0) {
            for (PromotionConfigureDTO pcd : pclist) {
                pcd.setPromotionId(promotionId);
                pcd.setCreateId(promotionInfo.getCreateId());
                pcd.setCreateName(promotionInfo.getCreateName());
                pcd.setDeleteFlag(YesNoEnum.NO.getValue());
                promotionConfigureDAO.add(pcd);
            }
        }
        return promotionInfo;
    }

    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public PromotionExtendInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)
            throws PromotionCenterBusinessException, Exception {
        PromotionInfoDTO promotion = new PromotionInfoDTO();
        PromotionExtendInfoDTO promotionInfo = new PromotionExtendInfoDTO();
        List<PromotionAccumulatyDTO> promotionAccumulatyList = null;
        List<String> levelCodeList = null;
        promotion = promotionInfoDAO.queryById(promotionId);
        if (promotion == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "促销活动不存在");
        }
        promotionInfo = promotionInfoExtendDAO.queryById(promotionId);

        promotionInfo.setPromoionInfo(promotion);
        if (levelCodeArr != null && levelCodeArr.length > 0) {
            levelCodeList = new ArrayList<String>();
            for (String levelCode : levelCodeArr) {
                levelCodeList.add(levelCode);
            }
        }
        promotionAccumulatyList = promotionAccumulatyDAO.queryAccumulatyListByPromotionId(promotionId, levelCodeList);
        if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty()) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "促销活动层级不存在");
        }
        promotionInfo.setPromotionAccumulatyList(promotionAccumulatyList);

        PromotionDetailDescribeDMO record = new PromotionDetailDescribeDMO();
        record.setPromotionId(promotionId);
        PromotionDetailDescribeDMO promotionDetailDescribeInfo = promotionDetailDescribeDAO.selectByPromotionId(record);
        PromotionDetailDescribeDTO promotionDetailDescribeDTO = new PromotionDetailDescribeDTO();
        if (promotionDetailDescribeInfo != null) {
            promotionDetailDescribeDTO.setDescribeContent(promotionDetailDescribeInfo.getDescribeContent());
            promotionDetailDescribeDTO.setCreateId(promotionDetailDescribeInfo.getCreateId());
            promotionDetailDescribeDTO.setCreateName(promotionDetailDescribeInfo.getCreateName());
            promotionDetailDescribeDTO.setCreateTime(promotionDetailDescribeInfo.getCreateTime());
            promotionDetailDescribeDTO.setModifyId(promotionDetailDescribeInfo.getModifyId());
            promotionDetailDescribeDTO.setModifyName(promotionDetailDescribeInfo.getModifyName());
            promotionDetailDescribeDTO.setModifyTime(promotionDetailDescribeInfo.getModifyTime());
            promotionDetailDescribeDTO.setPromotionId(promotionId);
            promotionDetailDescribeDTO.setDeleteFlag(promotionDetailDescribeInfo.getDeleteFlag());
        }

        promotionInfo.setPromotionDetailDescribeDTO(promotionDetailDescribeDTO);

        List<PromotionPictureDTO> piclist = promotionPictureDAO.selectByPromotionInfoId(promotionId);
        promotionInfo.setPromotionPictureList(piclist);

        PromotionBuyerRuleDTO promotionBuyerRuleDTO = promotionBuyerRuleDAO.selectByPromotionInfoId(promotionId);
        promotionInfo.setBuyerRuleDTO(promotionBuyerRuleDTO);

        PromotionSellerRuleDTO psr = promotionSellerRuleDAO.selectByPromotionInfoId(promotionId);
        promotionInfo.setSellerRuleDTO(psr);

        PromotionSloganDTO psd = promotionSloganDAO.queryBargainSloganByPromotionId(promotionId);
        promotionInfo.setPromotionSloganDTO(psd);

        List<PromotionConfigureDTO> pclist = promotionConfigureDAO.selectByPromotionId(promotionId);
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
     * @throws Exception
     */
    @Override
    public PromotionExtendInfoDTO updatePromotionInfo(PromotionExtendInfoDTO promotionInfo)
            throws PromotionCenterBusinessException, Exception {
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
            throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
        }
        promotionId = promotionInfo.getPromotionId();
        promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
        setPromotionStatusInfo(promotionInfo);
        accumulatyDTOList = promotionAccumulatyDAO.queryAccumulatyListByPromotionId(promotionId, null);
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
            if (StringUtils.isEmpty(accumulatyDTO.getLevelCode()) || "0".equals(accumulatyDTO.getLevelCode())) {
                accumulatyDTO.setPromotionId(promotionId);
                accumulatyDTO.setLevelNumber(maxLevelNum + i + 1);
                accumulatyDTO.setLevelCode(noGenerator.generatePromotionLevelCode(promotionId));
                accumulatyDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                accumulatyDTO.setCreateId(promotionInfo.getModifyId());
                accumulatyDTO.setCreateName(promotionInfo.getModifyName());
                promotionAccumulatyDAO.add(accumulatyDTO);
            } else {
                oldAccumulatyMap.remove(accumulatyDTO.getLevelCode());
                accumulatyDTO.setModifyId(promotionInfo.getModifyId());
                accumulatyDTO.setModifyName(promotionInfo.getModifyName());
                promotionAccumulatyDAO.update(accumulatyDTO);
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
            extendDTO = promotionInfo;
            promotionInfoExtendDAO.update(extendDTO);
        }
        promotionInfo.setHasUpFlag(YesNoEnum.YES.getValue());
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.update(promotionInfo);

        PromotionDetailDescribeDMO promotionDetailDescribeDTO = new PromotionDetailDescribeDMO();
        PromotionDetailDescribeDTO piddd = promotionInfo.getPromotionDetailDescribeDTO();
        if (piddd != null) {
            promotionDetailDescribeDTO.setDescribeContent(piddd.getDescribeContent());
            promotionDetailDescribeDTO.setId(piddd.getId());
            promotionDetailDescribeDTO.setModifyId(promotionInfo.getModifyId());
            promotionDetailDescribeDTO.setModifyName(promotionInfo.getModifyName());
            promotionDetailDescribeDTO.setPromotionId(promotionInfo.getPromotionId());
            promotionDetailDescribeDTO.setDeleteFlag(piddd.getDeleteFlag());
            promotionDetailDescribeDAO.update(promotionDetailDescribeDTO);
        }

        List<PromotionPictureDTO> piclist = promotionInfo.getPromotionPictureList();
        if (null != piclist && !piclist.isEmpty()) {
            for (PromotionPictureDTO ppic : piclist) {
                ppic.setModifyId(promotionInfo.getModifyId());
                ppic.setModifyName(promotionInfo.getModifyName());
                ppic.setPromotionId(promotionInfo.getPromotionId());
                promotionPictureDAO.update(ppic);
            }
        }

        PromotionBuyerRuleDTO pbr = promotionInfo.getBuyerRuleDTO();
        if (pbr != null) {
            pbr.setPromotionId(promotionId);
            pbr.setModifyId(promotionInfo.getModifyId());
            pbr.setModifyName(promotionInfo.getModifyName());
            PromotionBuyerRuleDTO pbrold = promotionBuyerRuleDAO.selectByPromotionInfoId(promotionId);
            if(pbrold==null){
            	pbr.setDeleteFlag(YesNoEnum.NO.getValue());
            	pbr.setCreateId(promotionInfo.getModifyId());
            	pbr.setCreateName(promotionInfo.getModifyName());
            	promotionBuyerRuleDAO.add(pbr);
            }else{
            	promotionBuyerRuleDAO.update(pbr);
            }
        }
        
        PromotionSellerRuleDTO psr = promotionInfo.getSellerRuleDTO();
        if (psr != null) {
            psr.setPromotionId(promotionId);
            psr.setModifyId(promotionInfo.getModifyId());
            psr.setModifyName(promotionInfo.getModifyName());
            PromotionSellerRuleDTO psrold = promotionSellerRuleDAO.selectByPromotionInfoId(promotionId);
            if(psrold==null){
                psr.setDeleteFlag(YesNoEnum.NO.getValue());
                psr.setCreateId(promotionInfo.getCreateId());
                psr.setCreateName(promotionInfo.getCreateName());
                promotionSellerRuleDAO.add(psr);
            }else{
                promotionSellerRuleDAO.update(psr);
            }

            List<PromotionSellerDetailDTO> sellerlist = psr.getSellerDetailList();
            promotionSellerDetailDAO.deleteByPromotionId(promotionId);
            if (sellerlist != null && !sellerlist.isEmpty()) {
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
        List<PromotionConfigureDTO> pclist = promotionInfo.getPromotionConfigureList();
        if (pclist != null && pclist.size() > 0) {
            for (PromotionConfigureDTO pcd : pclist) {
                promotionConfigureDAO.update(pcd);
            }
        }
        return promotionInfo;
    }

    /**
     * 将只有一个层级的促销活动转换成一个对象
     *
     * @param promotionInfo
     * @return
     */
    @Override
    public PromotionAccumulatyDTO convertSingleAccumulatyPromotion2Info(PromotionInfoDTO promotionInfo) {
        List<? extends PromotionAccumulatyDTO> accumulatyList = promotionInfo.getPromotionAccumulatyList();
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
    @Override
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(List<PromotionAccumulatyDTO> promotionAccuDTOList) {
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
     * @throws Exception
     */
    @Override
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
            throws PromotionCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = queryPromotionInfo(promotionId, levelCode);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    @Override
    public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo) {
        Date nowDt = new Date();
        String status = promotionInfo.getStatus();
        String showStatus = promotionInfo.getShowStatus();
        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE)
                .equals(status)) {
            return status;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(showStatus) || dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(showStatus)) {
            if (nowDt.before(promotionInfo.getEffectiveTime())) {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
            } else if (!nowDt.before(promotionInfo.getEffectiveTime()) && !nowDt
                    .after(promotionInfo.getInvalidTime())) {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_START);
            } else {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_END);
            }
        } else if (StringUtils.isEmpty(status)) {
            status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
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
    public boolean checkPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO, BuyerCheckInfo buyerInfo,
            Map<String, String> dictMap) {
        PromotionBuyerRuleDTO ruleDTO = promotionInfoDTO.getBuyerRuleDTO();
        List<PromotionBuyerDetailDTO> detailList = null;
        if (ruleDTO == null) {
            return true;
        }
        if (dictMap
                .get(DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&" + DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT)
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
        } else if (dictMap.get(DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&"
                + DictionaryConst.OPT_PROMOTION_BUYER_RULE_FIRST_LOGIN).equals(ruleDTO.getRuleTargetType())) {
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
    public boolean checkPromotionSellerRule(PromotionInfoDTO promotionInfoDTO, String sellerCode,
            Map<String, String> dictMap) {
        PromotionSellerRuleDTO ruleDTO = promotionInfoDTO.getSellerRuleDTO();
        List<PromotionSellerDetailDTO> detailList = null;
        if (ruleDTO == null) {
            return true;
        }
        if (dictMap
                .get(DictionaryConst.TYPE_PROMOTION_SELLER_RULE + "&" + DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART)
                .equals(ruleDTO.getRuleTargetType())) {
            detailList = ruleDTO.getSellerDetailList();
            if (detailList == null || detailList.isEmpty()) {
                return false;
            }
            for (PromotionSellerDetailDTO detailDTO : detailList) {
                if (detailDTO.getSellerCode().equals(sellerCode)) {
                    return true;
                }
            }
        }
        return true;
    }
}
