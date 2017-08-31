package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoExtendDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;

import com.alibaba.fastjson.JSON;

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
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	
	@Resource
	private PromotionInfoExtendDAO promotionInfoExtendDAO;

    /**
     * 删除促销活动
     *
     * @param validDTO
     * @throws PromotionCenterBusinessException
     * @throws PromotionCenterBusinessException,Exception
     */
    public void deletePromotionInfo(PromotionValidDTO validDTO) throws PromotionCenterBusinessException,Exception {
        PromotionInfoDTO promotionInfo = null;
        PromotionAccumulatyDTO accumulaty = new PromotionAccumulatyDTO();
        PromotionSloganDTO slogan = new PromotionSloganDTO();
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
                	List<BuyerLaunchBargainInfoDMO> buyerLaunchList = 
                			buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId(validDTO.getPromotionId());
                	if ((promotionInfo.getInvalidTime() != null && !(new Date()).after(promotionInfo.getInvalidTime())) 
                			&& (null != buyerLaunchList && !buyerLaunchList.isEmpty())) {
                        throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_SOMEONE_INVOLVED.getCode(),
                                "砍价活动还未结束并有人参与");
                    }
                }
            }
            promotionInfo.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            promotionInfo.setModifyId(validDTO.getOperatorId());
            promotionInfo.setModifyName(validDTO.getOperatorName());
            accumulaty.setPromoionInfo(promotionInfo);
            promotionAccumulatyDAO.delete(accumulaty);
            slogan.setPromotionId(validDTO.getPromotionId());
            promotionSloganDAO.delete(slogan);
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
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionInfoDTO insertPromotionInfo(PromotionInfoDTO promotionInfo)
            throws  Exception {
        String promotionType = "";
        String promotionId = "";
        List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
        PromotionAccumulatyDTO accumulatyDTO = null;
        PromotionBargainInfoResDTO bargainDTO = null;
        PromotionExtendInfoDTO extendDTO = new PromotionExtendInfoDTO();
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
        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN)
                .equals(promotionType)) {
            promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
        } else {
            if (StringUtils.isEmpty(promotionInfo.getShowStatus())) {
                promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
            }
        }
        //判断时间段内可有活动上架
        Integer isUpPromotionFlag = promotionInfoDAO.queryUpPromotionBargainCount(promotionInfo.getPromotionProviderSellerCode(),
        		promotionInfo.getEffectiveTime(),promotionInfo.getInvalidTime());
        if(null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
        	 throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(), "该时间段内已有活动进行");
        }
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
            bargainDTO = (PromotionBargainInfoResDTO)accumulatyDTO;
            promotionBargainInfoDAO.add(bargainDTO);
            
        }
        if(null !=bargainDTO && "1".equals(bargainDTO.getUpFlag())) {
        	promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                 DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
        }
        extendDTO = (PromotionExtendInfoDTO)bargainDTO;
        promotionInfoExtendDAO.add(extendDTO);
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.add(promotionInfo);
        return promotionInfo;
    }

    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws PromotionCenterBusinessException
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)
            throws  Exception {
        PromotionInfoDTO promotionInfo = null;
        List<PromotionAccumulatyDTO> promotionAccumulatyList = null;
        List<String> levelCodeList = null;
        promotionInfo = promotionInfoDAO.queryById(promotionId);
        if (promotionInfo == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "促销活动不存在");
        }
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
        return promotionInfo;
    }

    /**
     * 更新促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws PromotionCenterBusinessException
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionInfoDTO updatePromotionInfo(PromotionInfoDTO promotionInfo)
            throws  Exception {
        String promotionType = "";
        String promotionId = "";
        List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
        PromotionAccumulatyDTO accumulatyDTO = null;
        PromotionBargainInfoResDTO bargainDTO = null;
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
        promotionType = promotionInfo.getPromotionType();
        promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN)
                .equals(promotionType)) {
            promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
        } else {
            if (StringUtils.isEmpty(promotionInfo.getShowStatus())) {
                promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
            }
        }
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
            if (StringUtils.isEmpty(accumulatyDTO.getLevelCode())) {
                accumulatyDTO.setPromotionId(promotionId);
                accumulatyDTO.setLevelNumber(maxLevelNum + i + 1);
                accumulatyDTO.setLevelCode(noGenerator.generatePromotionLevelCode(promotionId));
                accumulatyDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                accumulatyDTO.setCreateId(promotionInfo.getModifyId());
                accumulatyDTO.setCreateName(promotionInfo.getModifyName());
                promotionAccumulatyDAO.add(accumulatyDTO);
                bargainDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
                promotionBargainInfoDAO.add(bargainDTO);
            } else {
                oldAccumulatyMap.remove(accumulatyDTO.getLevelCode());
                bargainDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
                promotionBargainInfoDAO.update(bargainDTO);
            }
        }
        if (oldAccumulatyMap.size() > 0) {
            for (PromotionAccumulatyDTO oldAccuDTO : oldAccumulatyMap.values()) {
                oldAccuDTO.setModifyId(promotionInfo.getModifyId());
                oldAccuDTO.setModifyName(promotionInfo.getModifyName());
                promotionAccumulatyDAO.delete(oldAccuDTO);
            }
        }
        if(StringUtils.isNotEmpty(promotionId)){
        	slogan =  promotionSloganDAO.queryBargainSloganByPromotionId(promotionId);
        	accumulatyDTO = promotionAccumulatyList.get(0);
        	bargainDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
        	if(null != slogan){ 
        		if(StringUtils.isNotEmpty(slogan.getPromotionSlogan()) && 
        				!slogan.getPromotionSlogan().equals(bargainDTO.getPromotionSlogan())){
        			slogan.setPromotionId(bargainDTO.getPromotionId());
        			slogan.setPromotionSlogan(bargainDTO.getPromotionSlogan());
        			logger.info("slogan dataMessage:" + JSON.toJSONString(slogan));
        			promotionSloganDAO.update(slogan);
        		}
        	}else{
        		slogan = new PromotionSloganDTO();
        		slogan.setPromotionId(promotionId);
        		slogan.setPromotionSlogan(bargainDTO.getPromotionSlogan());
        		slogan.setCreateId(promotionInfo.getModifyId());
        		slogan.setCreateName(promotionInfo.getModifyName());
        		promotionSloganDAO.add(slogan);
        	}
        	 extendDTO = (PromotionExtendInfoDTO)accumulatyDTO;
             promotionInfoExtendDAO.update(extendDTO);
        }
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.update(promotionInfo);
        return promotionInfo;
    }

    /**
     * 将只有一个层级的促销活动转换成一个对象
     *
     * @param promotionInfo
     * @return
     */
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
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(List<PromotionAccumulatyDTO> promotionAccuDTOList) {
        PromotionInfoDTO resultDTO = new PromotionInfoDTO();
    	if(null != promotionAccuDTOList && !promotionAccuDTOList.isEmpty()){
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
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
            throws  Exception {
        PromotionInfoDTO promotionInfo = queryPromotionInfo(promotionId, levelCode);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 插入多个层级的促销活动信息
     *
     * @param promotionAccuDTOList
     * @return
     * @throws PromotionCenterBusinessException
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionAccumulatyDTO insertManyAccumulatyPromotionInfo(List<PromotionAccumulatyDTO> promotionAccuDTOList)
            throws  Exception {
        PromotionInfoDTO promotionInfo = convertSingleAccumulatyPromotion2DTO(promotionAccuDTOList);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        promotionInfo = insertPromotionInfo(promotionInfo);
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 更新多个层级的促销活动信息
     *
     * @param promotionAccuDTOList
     * @return
     * @throws PromotionCenterBusinessException
     * @throws PromotionCenterBusinessException,Exception
     */
    public PromotionAccumulatyDTO updateSingleAccumulatyPromotionInfo(List<PromotionAccumulatyDTO> promotionAccuDTOList)
            throws  Exception {
        PromotionInfoDTO promotionInfo = convertSingleAccumulatyPromotion2DTO(promotionAccuDTOList);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        promotionInfo = updatePromotionInfo(promotionInfo);
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
    public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo) {
        Date nowDt = new Date();
        String status = promotionInfo.getStatus();
        String showStatus = promotionInfo.getShowStatus();
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                DictionaryConst.OPT_PROMOTION_STATUS_DELETE).equals(status)) {
            return status;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst
                .OPT_PROMOTION_VERIFY_STATUS_VALID).equals(showStatus) || dictionary.getValueByCode(DictionaryConst
                .TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(showStatus)) {
            if (nowDt.before(promotionInfo.getEffectiveTime())) {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst
                        .OPT_PROMOTION_STATUS_NO_START);
            } else if (!nowDt.before(promotionInfo.getEffectiveTime())
                    && !nowDt.after(promotionInfo.getInvalidTime())) {
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
}
