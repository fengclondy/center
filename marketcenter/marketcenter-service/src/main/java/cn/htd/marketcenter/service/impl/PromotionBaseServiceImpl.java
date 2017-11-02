package cn.htd.marketcenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionAccumulatyDAO;
import cn.htd.marketcenter.dao.PromotionBuyerDetailDAO;
import cn.htd.marketcenter.dao.PromotionBuyerRuleDAO;
import cn.htd.marketcenter.dao.PromotionCategoryDetailDAO;
import cn.htd.marketcenter.dao.PromotionCategoryItemRuleDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionItemDetailDAO;
import cn.htd.marketcenter.dao.PromotionSellerDetailDAO;
import cn.htd.marketcenter.dao.PromotionSellerRuleDAO;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.dto.BuyerInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDTO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.PromotionBuyerRuleDefineService;
import cn.htd.marketcenter.service.PromotionCategoryItemRuleDefineService;
import cn.htd.marketcenter.service.PromotionSellerRuleDefineService;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberGroupService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    private PromotionBuyerRuleDAO promotionBuyerRuleDAO;

    @Resource
    private PromotionSellerRuleDAO promotionSellerRuleDAO;

    @Resource
    private PromotionBuyerDetailDAO promotionBuyerDetailDAO;

    @Resource
    private PromotionSellerDetailDAO promotionSellerDetailDAO;

    @Resource
    private PromotionCategoryItemRuleDAO promotionCategoryItemRuleDAO;

    @Resource
    private PromotionCategoryDetailDAO promotionCategoryDetailDAO;

    @Resource
    private PromotionItemDetailDAO promotionItemDetailDAO;

    @Resource
    private PromotionBuyerRuleDefineService promotionBuyerRuleDefineService;

    @Resource
    private PromotionSellerRuleDefineService promotionSellerRuleDefineService;

    @Resource
    private PromotionCategoryItemRuleDefineService promotionCategoryItemRuleDefineService;

    @Resource
    private MemberGroupService memberGroupService;

    @Resource
    private MemberBaseInfoService memberBaseInfoService;

    /**
     * 删除促销活动
     *
     * @param validDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public void deletePromotionInfo(PromotionValidDTO validDTO) throws MarketCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = null;
        try {
            // 根据活动ID获取活动信息
            promotionInfo = promotionInfoDAO.queryById(validDTO.getPromotionId());
            if (promotionInfo == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该促销活动不存在");
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
                    if (promotionInfo.getInvalidTime() != null && !(new Date()).after(promotionInfo.getInvalidTime())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXPIRED,
                                "促销活动还未结束");
                    }
                }
            }
            promotionInfo.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            promotionInfo.setModifyId(validDTO.getOperatorId());
            promotionInfo.setModifyName(validDTO.getOperatorName());
            promotionInfoDAO.updatePromotionStatusById(promotionInfo);
        } catch (MarketCenterBusinessException mcbe) {
            throw mcbe;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据促销活动会员规则判断是否是VIP会员专属
     *
     * @param promotionInfoDTO
     */
    private int getPromotionVipFlag(PromotionInfoDTO promotionInfoDTO) {
        PromotionBuyerRuleDTO buyerRule = promotionInfoDTO.getBuyerRuleDTO();
        List<String> buyerLevelList = null;
        int returnFlg = YesNoEnum.NO.getValue();
        if (buyerRule == null) {
            return returnFlg;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(buyerRule.getRuleTargetType())) {
            buyerLevelList = buyerRule.getTargetBuyerLevelList();
            if (buyerLevelList != null && !buyerLevelList.isEmpty() && buyerLevelList.size() == 1) {
                if (dictionary.getValueByCode(DictionaryConst.TYPE_MEMBER_GRADE, DictionaryConst.OPT_MEMBER_GRADE_VIP)
                        .equals(buyerLevelList.get(0))) {
                    returnFlg = YesNoEnum.YES.getValue();
                }
            }
        }
        return returnFlg;
    }

    /**
     * 插入促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO insertPromotionInfo(PromotionInfoDTO promotionInfo)
            throws MarketCenterBusinessException, Exception {
        String promotionType = "";
        String promotionId = "";
        List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
        PromotionAccumulatyDTO accumulatyDTO = null;
        int vipFlg = -1;
        if (promotionInfo == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动参数不能为空");
        }
        promotionType = promotionInfo.getPromotionType();
        promotionId = noGenerator.generatePromotionId(promotionType);
        promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
        if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动层级不能为空");
        }
        promotionInfo.setPromotionId(promotionId);
        //----- delete by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
//        if (dictionary
//                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
//                .equals(promotionType)) {
//            promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
//        } else {
//            if (StringUtils.isEmpty(promotionInfo.getShowStatus())) {
//                promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//            }
//        }
        //----- delete by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
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
        insertPromotionBuyerRule(promotionInfo);
        insertPromotionSellerRule(promotionInfo);
        insertPromotionCategoryItemRule(promotionInfo);
        vipFlg = getPromotionVipFlag(promotionInfo);
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.add(promotionInfo);
        return promotionInfo;
    }

    /**
     * 保存促销活动会员规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void insertPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        ExecuteResult<PromotionBuyerRuleDefineDTO> buyerRuleDefineResult = null;
        PromotionBuyerRuleDefineDTO buyerRuleDefineDTO = null;
        PromotionBuyerRuleDTO promotionBuyerRule = new PromotionBuyerRuleDTO();
        Long buyerRuleId = promotionInfoDTO.getBuyerRuleId();
        List<PromotionBuyerDetailDefineDTO> buyerDetailDefineList = null;
        PromotionBuyerDetailDTO buyerDetailDTO = null;
        List<PromotionBuyerDetailDTO> buyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
        String buyerLevelStr = "";
        String[] buyerLevelArr = null;
        List<String> buyerLevelList = new ArrayList<String>();
        String buyerGroupStr = "";
        if (buyerRuleId != null && buyerRuleId != 0) {
            buyerRuleDefineResult = promotionBuyerRuleDefineService.queryPromotionBuyerRule(buyerRuleId);
            if (!buyerRuleDefineResult.isSuccess()) {
                throw new MarketCenterBusinessException(buyerRuleDefineResult.getCode(),
                        JSON.toJSONString(buyerRuleDefineResult.getErrorMessages()));
            }
            buyerRuleDefineDTO = buyerRuleDefineResult.getResult();
            promotionBuyerRule.setPromotionId(promotionInfoDTO.getPromotionId());
            promotionBuyerRule.setRuleId(buyerRuleId);
            promotionBuyerRule.setRuleName(buyerRuleDefineDTO.getRuleName());
            promotionBuyerRule.setRuleTargetType(buyerRuleDefineDTO.getRuleTargetType());
            promotionBuyerRule.setTargetBuyerLevel(buyerRuleDefineDTO.getTargetBuyerLevel());
            promotionBuyerRule.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionBuyerRule.setCreateId(promotionInfoDTO.getCreateId());
            promotionBuyerRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionBuyerRuleDAO.add(promotionBuyerRule);
            buyerDetailDefineList = buyerRuleDefineDTO.getBuyerDetailList();
            if (buyerDetailDefineList != null && !buyerDetailDefineList.isEmpty()) {
                for (PromotionBuyerDetailDefineDTO buyerDetailDefineDTO : buyerDetailDefineList) {
                    buyerDetailDTO = new PromotionBuyerDetailDTO();
                    buyerDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    buyerDetailDTO.setRuleId(buyerRuleId);
                    buyerDetailDTO.setBuyerCode(buyerDetailDefineDTO.getBuyerCode());
                    buyerDetailDTO.setBuyerName(buyerDetailDefineDTO.getBuyerName());
                    buyerDetailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    buyerDetailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    buyerDetailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionBuyerDetailDAO.add(buyerDetailDTO);
                    buyerDetailList.add(buyerDetailDTO);
                }
                promotionBuyerRule.setBuyerDetailList(buyerDetailList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                    DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(promotionBuyerRule.getRuleTargetType())) {
                buyerLevelStr = promotionBuyerRule.getTargetBuyerLevel();
                if (!StringUtils.isEmpty(buyerLevelStr)) {
                    buyerLevelArr = buyerLevelStr.split(",");
                    for (String level : buyerLevelArr) {
                        buyerLevelList.add(level);
                    }
                    promotionBuyerRule.setTargetBuyerLevelList(buyerLevelList);
                }
            }
            promotionInfoDTO.setBuyerRuleDesc(buyerRuleDefineDTO.getRuleName());
            promotionInfoDTO.setBuyerRuleDTO(promotionBuyerRule);
        } else if (promotionInfoDTO.getBuyerRuleDTO() != null) {
            promotionBuyerRule = promotionInfoDTO.getBuyerRuleDTO();
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                    DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(promotionBuyerRule.getRuleTargetType())) {
                buyerLevelStr = StringUtils.join(promotionBuyerRule.getTargetBuyerLevelList(), ",");
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                    DictionaryConst.OPT_PROMOTION_BUYER_RULE_GROUP).equals(promotionBuyerRule.getRuleTargetType())) {
                if (promotionBuyerRule.getTargetBuyerGroupList().contains("0")) {
                    buyerGroupStr = "0";
                } else {
                    buyerGroupStr = StringUtils.join(promotionBuyerRule.getTargetBuyerGroupList(), ",");
                }
            }
            promotionBuyerRule.setTargetBuyerGroup(buyerGroupStr);
            promotionBuyerRule.setPromotionId(promotionInfoDTO.getPromotionId());
            promotionBuyerRule.setTargetBuyerLevel(buyerLevelStr);
            promotionBuyerRule.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionBuyerRule.setCreateId(promotionInfoDTO.getCreateId());
            promotionBuyerRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionBuyerRuleDAO.add(promotionBuyerRule);
            buyerDetailList = promotionBuyerRule.getBuyerDetailList();
            if (buyerDetailList != null && !buyerDetailList.isEmpty()) {
                for (PromotionBuyerDetailDTO detailDTO : buyerDetailList) {
                    detailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    detailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    detailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    detailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionBuyerDetailDAO.add(detailDTO);
                }
            }
        }
    }

    /**
     * 保存促销活动卖家规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void insertPromotionSellerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        ExecuteResult<PromotionSellerRuleDefineDTO> sellerRuleDefineResult = null;
        PromotionSellerRuleDefineDTO sellerRuleDefineDTO = null;
        PromotionSellerRuleDTO promotionSellerRule = new PromotionSellerRuleDTO();
        Long sellerRuleId = promotionInfoDTO.getSellerRuleId();
        List<PromotionSellerDetailDefineDTO> sellerDetailDefineList = null;
        PromotionSellerDetailDTO sellerDetailDTO = null;
        List<PromotionSellerDetailDTO> sellerDetailList = new ArrayList<PromotionSellerDetailDTO>();

        if (sellerRuleId != null && sellerRuleId != 0) {
            sellerRuleDefineResult = promotionSellerRuleDefineService.queryPromotionSellerRule(sellerRuleId);
            if (!sellerRuleDefineResult.isSuccess()) {
                throw new MarketCenterBusinessException(sellerRuleDefineResult.getCode(),
                        JSON.toJSONString(sellerRuleDefineResult.getErrorMessages()));
            }
            sellerRuleDefineDTO = sellerRuleDefineResult.getResult();
            promotionSellerRule.setPromotionId(promotionInfoDTO.getPromotionId());
            promotionSellerRule.setRuleId(sellerRuleId);
            promotionSellerRule.setRuleName(sellerRuleDefineDTO.getRuleName());
            promotionSellerRule.setRuleTargetType(sellerRuleDefineDTO.getRuleTargetType());
            promotionSellerRule.setTargetSellerType(sellerRuleDefineDTO.getTargetSellerType());
            promotionSellerRule.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionSellerRule.setCreateId(promotionInfoDTO.getCreateId());
            promotionSellerRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionSellerRuleDAO.add(promotionSellerRule);
            sellerDetailDefineList = sellerRuleDefineDTO.getSellerDetailList();
            if (sellerDetailDefineList != null && !sellerDetailDefineList.isEmpty()) {
                for (PromotionSellerDetailDefineDTO sellerDetailDefineDTO : sellerDetailDefineList) {
                    sellerDetailDTO = new PromotionSellerDetailDTO();
                    sellerDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    sellerDetailDTO.setRuleId(sellerRuleId);
                    sellerDetailDTO.setSellerCode(sellerDetailDefineDTO.getSellerCode());
                    sellerDetailDTO.setSellerName(sellerDetailDefineDTO.getSellerName());
                    sellerDetailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    sellerDetailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    sellerDetailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionSellerDetailDAO.add(sellerDetailDTO);
                    sellerDetailList.add(sellerDetailDTO);
                }
                promotionSellerRule.setSellerDetailList(sellerDetailList);
            }
            promotionInfoDTO.setSellerRuleDesc(sellerRuleDefineDTO.getRuleName());
            promotionInfoDTO.setSellerRuleDTO(promotionSellerRule);
        } else if (promotionInfoDTO.getSellerRuleDTO() != null) {
            promotionSellerRule = promotionInfoDTO.getSellerRuleDTO();
            promotionSellerRule.setPromotionId(promotionInfoDTO.getPromotionId());
            promotionSellerRule.setDeleteFlag(YesNoEnum.NO.getValue());
            promotionSellerRule.setCreateId(promotionInfoDTO.getCreateId());
            promotionSellerRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionSellerRuleDAO.add(promotionSellerRule);
            sellerDetailList = promotionSellerRule.getSellerDetailList();
            if (sellerDetailList != null && !sellerDetailList.isEmpty()) {
                for (PromotionSellerDetailDTO detailDTO : sellerDetailList) {
                    detailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    detailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    detailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    detailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionSellerDetailDAO.add(detailDTO);
                }
            }
        }
    }

    /**
     * 保存促销活动品类商品规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void insertPromotionCategoryItemRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        ExecuteResult<PromotionCategoryItemRuleDefineDTO> categoryItemRuleDefineResult = null;
        PromotionCategoryItemRuleDefineDTO categoryItemRuleDefineDTO = null;
        PromotionCategoryItemRuleDTO categoryItemRule = new PromotionCategoryItemRuleDTO();
        Long ruleId = promotionInfoDTO.getCategoryItemRuleId();
        List<PromotionCategoryDetailDefineDTO> categoryDetailDefineList = null;
        List<PromotionItemDetailDefineDTO> itemDetailDefineList = null;
        PromotionCategoryDetailDTO categoryDetailDTO = null;
        PromotionItemDetailDTO itemDetailDTO = null;
        List<PromotionCategoryDetailDTO> categoryDetailList = new ArrayList<PromotionCategoryDetailDTO>();
        List<PromotionItemDetailDTO> itemDetailList = new ArrayList<PromotionItemDetailDTO>();
        String brandIdList = "";
        String[] brandIdArr = null;
        List<Long> bids = new ArrayList<Long>();
        if (ruleId != null && ruleId != 0) {
            categoryItemRuleDefineResult =
                    promotionCategoryItemRuleDefineService.queryPromotionCategoryItemRule(ruleId);
            if (!categoryItemRuleDefineResult.isSuccess()) {
                throw new MarketCenterBusinessException(categoryItemRuleDefineResult.getCode(),
                        JSON.toJSONString(categoryItemRuleDefineResult.getErrorMessages()));
            }
            categoryItemRuleDefineDTO = categoryItemRuleDefineResult.getResult();
            categoryItemRule.setPromotionId(promotionInfoDTO.getPromotionId());
            categoryItemRule.setRuleId(ruleId);
            categoryItemRule.setRuleName(categoryItemRuleDefineDTO.getRuleName());
            categoryItemRule.setRuleTargetType(categoryItemRuleDefineDTO.getRuleTargetType());
            categoryItemRule.setTargetItemLimit(categoryItemRuleDefineDTO.getTargetItemLimit());
            categoryItemRule.setDeleteFlag(YesNoEnum.NO.getValue());
            categoryItemRule.setCreateId(promotionInfoDTO.getCreateId());
            categoryItemRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionCategoryItemRuleDAO.add(categoryItemRule);
            categoryDetailDefineList = categoryItemRuleDefineDTO.getCategoryList();
            if (categoryDetailDefineList != null && !categoryDetailDefineList.isEmpty()) {
                for (PromotionCategoryDetailDefineDTO categoryDetailDefineDTO : categoryDetailDefineList) {
                    categoryDetailDTO = new PromotionCategoryDetailDTO();
                    categoryDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    categoryDetailDTO.setRuleId(ruleId);
                    categoryDetailDTO.setCategoryId(categoryDetailDefineDTO.getCategoryId());
                    categoryDetailDTO.setBrandIdList(categoryDetailDefineDTO.getBrandIdList());
                    categoryDetailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    categoryDetailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    categoryDetailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionCategoryDetailDAO.add(categoryDetailDTO);
                    brandIdList = categoryDetailDTO.getBrandIdList();
                    if (!StringUtils.isEmpty(brandIdList)) {
                        brandIdArr = brandIdList.split(",");
                        for (String brandId : brandIdArr) {
                            bids.add(Long.parseLong(brandId));
                        }
                        categoryDetailDTO.setBids(bids);
                    }
                    categoryDetailList.add(categoryDetailDTO);
                }
                categoryItemRule.setCategoryDetailList(categoryDetailList);
            }
            itemDetailDefineList = categoryItemRuleDefineDTO.getItemList();
            if (itemDetailDefineList != null && !itemDetailDefineList.isEmpty()) {
                for (PromotionItemDetailDefineDTO itemDetailDefineDTO : itemDetailDefineList) {
                    itemDetailDTO = new PromotionItemDetailDTO();
                    itemDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    itemDetailDTO.setRuleId(ruleId);
                    itemDetailDTO.setSkuCode(itemDetailDefineDTO.getSkuCode());
                    itemDetailDTO.setItemName(itemDetailDefineDTO.getItemName());
                    itemDetailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    itemDetailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    itemDetailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionItemDetailDAO.add(itemDetailDTO);
                    itemDetailList.add(itemDetailDTO);
                }
                categoryItemRule.setItemDetailList(itemDetailList);
            }
            promotionInfoDTO.setCategoryItemRuleDesc(categoryItemRuleDefineDTO.getRuleName());
            promotionInfoDTO.setCategoryItemRuleDTO(categoryItemRule);
        } else if (promotionInfoDTO.getCategoryItemRuleDTO() != null) {
            categoryItemRule = promotionInfoDTO.getCategoryItemRuleDTO();
            categoryItemRule.setPromotionId(promotionInfoDTO.getPromotionId());
            categoryItemRule.setDeleteFlag(YesNoEnum.NO.getValue());
            categoryItemRule.setCreateId(promotionInfoDTO.getCreateId());
            categoryItemRule.setCreateName(promotionInfoDTO.getCreateName());
            promotionCategoryItemRuleDAO.add(categoryItemRule);
            categoryDetailList = categoryItemRule.getCategoryDetailList();
            if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                for (PromotionCategoryDetailDTO detailDTO : categoryDetailList) {
                    detailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    detailDTO.setBrandIdList(StringUtils.join(detailDTO.getBids(), ","));
                    detailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    detailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    detailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionCategoryDetailDAO.add(detailDTO);
                }
            }
            itemDetailList = categoryItemRule.getItemDetailList();
            if (itemDetailList != null && !itemDetailList.isEmpty()) {
                for (PromotionItemDetailDTO detailDTO : itemDetailList) {
                    detailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
                    detailDTO.setDeleteFlag(YesNoEnum.NO.getValue());
                    detailDTO.setCreateId(promotionInfoDTO.getCreateId());
                    detailDTO.setCreateName(promotionInfoDTO.getCreateName());
                    promotionItemDetailDAO.add(detailDTO);
                }
            }
        }
    }

    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)
            throws MarketCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = null;
        List<PromotionAccumulatyDTO> promotionAccumulatyList = null;
        List<String> levelCodeList = null;
        promotionInfo = promotionInfoDAO.queryById(promotionId);
        if (promotionInfo == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "促销活动不存在");
        }
        if (levelCodeArr != null && levelCodeArr.length > 0) {
            levelCodeList = new ArrayList<String>();
            for (String levelCode : levelCodeArr) {
                levelCodeList.add(levelCode);
            }
        }
        promotionAccumulatyList = promotionAccumulatyDAO.queryAccumulatyListByPromotionId(promotionId, levelCodeList);
        if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "促销活动层级不存在");
        }
        promotionInfo.setPromotionAccumulatyList(promotionAccumulatyList);
        queryPromotionBuyerRule(promotionInfo);
        queryPromotionSellerRule(promotionInfo);
        queryPromotionCategoryItemRule(promotionInfo);
        return promotionInfo;
    }

    /**
     * 根据促销活动信息获取促销活动会员规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void queryPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionBuyerRuleDTO ruleDTO = null;
        PromotionBuyerRuleDTO condition = new PromotionBuyerRuleDTO();
        List<PromotionBuyerDetailDTO> detailList = null;
        String[] buyerLevelArr = null;
        String buyerLevelStr = "";
        List<String> buyerLevelList = new ArrayList<String>();
        String[] buyerGroupArr = null;
        String buyerGroupStr = "";
        List<String> buyerGroupList = new ArrayList<String>();
        condition.setPromotionId(promotionInfoDTO.getPromotionId());
        ruleDTO = promotionBuyerRuleDAO.queryByPromotionInfo(condition);
        if (ruleDTO == null) {
            return;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(ruleDTO.getRuleTargetType())) {
            buyerLevelStr = ruleDTO.getTargetBuyerLevel();
            if (!StringUtils.isEmpty(buyerLevelStr)) {
                buyerLevelArr = buyerLevelStr.split(",");
                for (String level : buyerLevelArr) {
                    buyerLevelList.add(level);
                }
                ruleDTO.setTargetBuyerLevelList(buyerLevelList);
            }
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT).equals(ruleDTO.getRuleTargetType())) {
            detailList = promotionBuyerDetailDAO.queryByPromotionInfo(ruleDTO);
            ruleDTO.setBuyerDetailList(detailList);
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GROUP).equals(ruleDTO.getRuleTargetType())) {
            buyerGroupStr = ruleDTO.getTargetBuyerGroup();
            if (!StringUtils.isEmpty(buyerGroupStr)) {
                buyerGroupArr = buyerGroupStr.split(",");
                for (String group : buyerGroupArr) {
                    buyerGroupList.add(group);
                }
                ruleDTO.setTargetBuyerGroupList(buyerGroupList);
            }
        }
        promotionInfoDTO.setBuyerRuleDTO(ruleDTO);
        promotionInfoDTO.setBuyerRuleId(ruleDTO.getRuleId());
        promotionInfoDTO.setBuyerRuleDesc(ruleDTO.getRuleName());
    }

    /**
     * 根据促销活动信息获取促销活动卖家规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void queryPromotionSellerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionSellerRuleDTO ruleDTO = null;
        PromotionSellerRuleDTO condition = new PromotionSellerRuleDTO();
        List<PromotionSellerDetailDTO> detailList = null;

        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
                .equals(promotionInfoDTO.getPromotionType())) {
            return;
        }
        condition.setPromotionId(promotionInfoDTO.getPromotionId());
        ruleDTO = promotionSellerRuleDAO.queryByPromotionInfo(condition);
        if (ruleDTO == null) {
            return;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
                DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART).equals(ruleDTO.getRuleTargetType())) {
            detailList = promotionSellerDetailDAO.queryByPromotionInfo(ruleDTO);
            ruleDTO.setSellerDetailList(detailList);
        }
        promotionInfoDTO.setSellerRuleDTO(ruleDTO);
        promotionInfoDTO.setSellerRuleId(ruleDTO.getRuleId());
        promotionInfoDTO.setSellerRuleDesc(ruleDTO.getRuleName());
    }

    /**
     * 根据促销活动信息获取促销活动品类商品规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void queryPromotionCategoryItemRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionCategoryItemRuleDTO ruleDTO = null;
        PromotionCategoryItemRuleDTO condition = new PromotionCategoryItemRuleDTO();
        List<PromotionCategoryDetailDTO> categoryDetailList = null;
        List<PromotionItemDetailDTO> itemDetailList = null;
        String[] brandIdArr = null;
        String brandIdStr = "";
        List<Long> brandIdList = null;
        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
                .equals(promotionInfoDTO.getPromotionType())) {
            return;
        }
        condition.setPromotionId(promotionInfoDTO.getPromotionId());
        ruleDTO = promotionCategoryItemRuleDAO.queryByPromotionInfo(condition);
        if (ruleDTO == null) {
            return;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
                DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY).equals(ruleDTO.getRuleTargetType())) {
            categoryDetailList = promotionCategoryDetailDAO.queryByPromotionInfo(ruleDTO);
            if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                for (PromotionCategoryDetailDTO categoryDetailDTO : categoryDetailList) {
                    brandIdStr = categoryDetailDTO.getBrandIdList();
                    brandIdList = new ArrayList<Long>();
                    if (!StringUtils.isEmpty(brandIdStr)) {
                        brandIdArr = brandIdStr.split(",");
                        for (String brandId : brandIdArr) {
                            brandIdList.add(new Long(brandId));
                        }
                        categoryDetailDTO.setBids(brandIdList);
                    }
                }
            }
            ruleDTO.setCategoryDetailList(categoryDetailList);
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
                DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM).equals(ruleDTO.getRuleTargetType())) {
            itemDetailList = promotionItemDetailDAO.queryByPromotionInfo(ruleDTO);
            ruleDTO.setItemDetailList(itemDetailList);
        }
        promotionInfoDTO.setCategoryItemRuleDTO(ruleDTO);
        promotionInfoDTO.setCategoryItemRuleId(ruleDTO.getRuleId());
        promotionInfoDTO.setCategoryItemRuleDesc(ruleDTO.getRuleName());
    }

    /**
     * 更新促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO updatePromotionInfo(PromotionInfoDTO promotionInfo)
            throws MarketCenterBusinessException, Exception {
        String promotionType = "";
        String promotionId = "";
        List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = null;
        PromotionAccumulatyDTO accumulatyDTO = null;
        List<PromotionAccumulatyDTO> accumulatyDTOList = null;
        Map<String, PromotionAccumulatyDTO> oldAccumulatyMap = new HashMap<String, PromotionAccumulatyDTO>();
        int maxLevelNum = 0;
        int vipFlg = -1;
        if (promotionInfo == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动参数不能为空");
        }
        promotionId = promotionInfo.getPromotionId();
        promotionType = promotionInfo.getPromotionType();
        promotionAccumulatyList = promotionInfo.getPromotionAccumulatyList();
        if (promotionAccumulatyList == null || promotionAccumulatyList.isEmpty()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动层级不能为空");
        }
        //----- delete by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
//        if (dictionary
//                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
//                .equals(promotionType)) {
//            promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
//        } else {
//            promotionInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//        }
        //----- delete by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
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
        updatePromotionBuyerRule(promotionInfo);
        updatePromotionSellerRule(promotionInfo);
        updatePromotionCategoryItemRule(promotionInfo);
        vipFlg = getPromotionVipFlag(promotionInfo);
        promotionInfo.setIsVip(vipFlg);
        promotionInfoDAO.update(promotionInfo);
        return promotionInfo;
    }

    /**
     * 更新促销活动会员规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void updatePromotionBuyerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionBuyerRuleDTO buyerRuleDTO = new PromotionBuyerRuleDTO();
        PromotionBuyerDetailDTO buyerDetailDTO = new PromotionBuyerDetailDTO();

        buyerRuleDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        buyerRuleDTO.setModifyId(promotionInfoDTO.getModifyId());
        buyerRuleDTO.setModifyName(promotionInfoDTO.getModifyName());

        buyerDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        buyerDetailDTO.setModifyId(promotionInfoDTO.getModifyId());
        buyerDetailDTO.setModifyName(promotionInfoDTO.getModifyName());

        promotionBuyerRuleDAO.delete(buyerRuleDTO);
        promotionBuyerDetailDAO.delete(buyerDetailDTO);
        insertPromotionBuyerRule(promotionInfoDTO);
    }

    /**
     * 更新促销活动卖家规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void updatePromotionSellerRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionSellerRuleDTO sellerRuleDTO = new PromotionSellerRuleDTO();
        PromotionSellerDetailDTO sellerDetailDTO = new PromotionSellerDetailDTO();

        sellerRuleDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        sellerRuleDTO.setModifyId(promotionInfoDTO.getModifyId());
        sellerRuleDTO.setModifyName(promotionInfoDTO.getModifyName());

        sellerDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        sellerDetailDTO.setModifyId(promotionInfoDTO.getModifyId());
        sellerDetailDTO.setModifyName(promotionInfoDTO.getModifyName());

        promotionSellerRuleDAO.delete(sellerRuleDTO);
        promotionSellerDetailDAO.delete(sellerDetailDTO);
        insertPromotionSellerRule(promotionInfoDTO);
    }

    /**
     * 更新促销活动类目商品规则信息
     *
     * @param promotionInfoDTO
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void updatePromotionCategoryItemRule(PromotionInfoDTO promotionInfoDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionCategoryItemRuleDTO categoryItemRuleDTO = new PromotionCategoryItemRuleDTO();
        PromotionCategoryDetailDTO categoryDetailDTO = new PromotionCategoryDetailDTO();
        PromotionItemDetailDTO itemDetailDTO = new PromotionItemDetailDTO();

        categoryItemRuleDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        categoryItemRuleDTO.setModifyId(promotionInfoDTO.getModifyId());
        categoryItemRuleDTO.setModifyName(promotionInfoDTO.getModifyName());

        categoryDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        categoryDetailDTO.setModifyId(promotionInfoDTO.getModifyId());
        categoryDetailDTO.setModifyName(promotionInfoDTO.getModifyName());

        itemDetailDTO.setPromotionId(promotionInfoDTO.getPromotionId());
        itemDetailDTO.setModifyId(promotionInfoDTO.getModifyId());
        itemDetailDTO.setModifyName(promotionInfoDTO.getModifyName());

        promotionCategoryItemRuleDAO.delete(categoryItemRuleDTO);
        promotionCategoryDetailDAO.delete(categoryDetailDTO);
        promotionItemDetailDAO.delete(itemDetailDTO);
        insertPromotionCategoryItemRule(promotionInfoDTO);
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
     * @param promotionAccuDTO
     * @return
     */
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(PromotionAccumulatyDTO promotionAccuDTO) {
        PromotionInfoDTO resultDTO = new PromotionInfoDTO();
        List<PromotionAccumulatyDTO> accuList = new ArrayList<PromotionAccumulatyDTO>();
        accuList.add(promotionAccuDTO);
        promotionAccuDTO.setPromotionAccumulatyList(accuList);
        resultDTO.setPromoionInfo(promotionAccuDTO);
        return resultDTO;
    }

    /**
     * 查询促销活动信息,并将一个层级内容扁平化或者根据指定层级编码取得促销活动
     *
     * @param promotionId
     * @param levelCode
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
            throws MarketCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = queryPromotionInfo(promotionId, levelCode);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 插入只有一个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO insertSingleAccumulatyPromotionInfo(PromotionAccumulatyDTO promotionAccuDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = convertSingleAccumulatyPromotion2DTO(promotionAccuDTO);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        promotionInfo = insertPromotionInfo(promotionInfo);
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 更新只有一个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO updateSingleAccumulatyPromotionInfo(PromotionAccumulatyDTO promotionAccuDTO)
            throws MarketCenterBusinessException, Exception {
        PromotionInfoDTO promotionInfo = convertSingleAccumulatyPromotion2DTO(promotionAccuDTO);
        logger.info("*********** promotionInfo:" + JSON.toJSONString(promotionInfo) + "***********");
        promotionInfo = updatePromotionInfo(promotionInfo);
        PromotionAccumulatyDTO accuDTO = convertSingleAccumulatyPromotion2Info(promotionInfo);
        logger.info("*********** accuDTO:" + JSON.toJSONString(accuDTO) + "***********");
        return accuDTO;
    }

    /**
     * 检查优惠活动会员规则
     *
     * @param promotionInfoDTO
     * @param buyerInfo
     * @return
     */
    public boolean checkPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO, BuyerCheckInfo buyerInfo) {
        PromotionBuyerRuleDTO ruleDTO = promotionInfoDTO.getBuyerRuleDTO();
        List<String> levelList = null;
        List<String> groupList = null;
        List<String> buyerCodeList = null;
        List<PromotionBuyerDetailDTO> detailList = null;
        if (ruleDTO == null) {
            return true;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(ruleDTO.getRuleTargetType())) {
            levelList = ruleDTO.getTargetBuyerLevelList();
            if (levelList == null || levelList.isEmpty()) {
                return true;
            }
            if (levelList.contains(buyerInfo.getBuyerGrade())) {
                return true;
            }
            return false;
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GROUP).equals(ruleDTO.getRuleTargetType())) {
            groupList = ruleDTO.getTargetBuyerGroupList();
            if (groupList == null || groupList.isEmpty()) {
                return false;
            }
            if (groupList.contains("0")) {
                return true;
            }
            if (!StringUtils.isEmpty(buyerInfo.getBuyerGroupId()) && groupList.contains(buyerInfo.getBuyerGroupId())) {
                return true;
            }
            return false;
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT).equals(ruleDTO.getRuleTargetType())) {
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
            detailList = ruleDTO.getBuyerDetailList();
            buyerCodeList = ruleDTO.getTargetBuyerCodeList();
            if ((detailList == null || detailList.isEmpty()) && (buyerCodeList == null || buyerCodeList.isEmpty())) {
                return true;
            }
            if (detailList != null && !detailList.isEmpty()) {
                for (PromotionBuyerDetailDTO detailDTO : detailList) {
                    if (detailDTO.getBuyerCode().equals(buyerInfo.getBuyerCode())) {
                        return true;
                    }
                }
            }
            if (buyerCodeList != null && !buyerCodeList.isEmpty()) {
                if (buyerCodeList.contains(buyerInfo.getBuyerCode())) {
                    return true;
                }
            }
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
            return false;
        }
        return true;
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
     * 校验会员是否在指定卖家的分组内,存在时获取分组ID
     *
     * @param messageId
     * @param buyerInfo
     * @return
     */
    public MemberGroupDTO getBuyerGroupRelationship(String messageId, BuyerInfoDTO buyerInfo) {
        ExecuteResult<Long> buyerResult = null;
        ExecuteResult<Long> sellerResult = null;
        ExecuteResult<MemberGroupDTO> memberGroupResult = null;
        MemberGroupDTO memberGroup = null;
        String buyerCode = buyerInfo.getBuyerCode();
        String sellerCode = buyerInfo.getSellerCode();
        Long buyerId = 0L;
        Long sellerId = 0L;
        try {
            buyerResult = memberBaseInfoService.getMemberIdByCode(buyerCode);
            if (!buyerResult.isSuccess()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.MEMBER_INFO_NOT_EXIST, "会员信息不存在");
            }
            buyerId = buyerResult.getResult();
            if (buyerId.intValue() == 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.MEMBER_INFO_NOT_EXIST, "会员信息不存在");
            }
            sellerResult = memberBaseInfoService.getMemberIdByCode(sellerCode);
            if (!sellerResult.isSuccess()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.MEMBER_INFO_NOT_EXIST, "供应商信息不存在");
            }
            sellerId = sellerResult.getResult();
            if (sellerId.intValue() == 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.MEMBER_INFO_NOT_EXIST, "会员信息不存在");
            }
            memberGroupResult = memberGroupService.queryGroupInfoBySellerBuyerId(buyerId, sellerId);
            if (memberGroupResult.isSuccess()) {
                memberGroup = memberGroupResult.getResult();
            }
        } catch (MarketCenterBusinessException mcbe) {
            logger.info("***********校验会员是否在指定卖家的分组内 messageId:[{}],校验对象信息:[{}],校验结果信息:[{}]***********", messageId,
                    JSON.toJSONString(buyerInfo), JSON.toJSONString(mcbe));
        } catch (Exception e) {
            logger.error("***********校验会员是否在指定卖家的分组内 messageId:[{}],校验对象信息:[{}],错误信息:[{}]***********", messageId,
                    JSON.toJSONString(buyerInfo), ExceptionUtils.getStackTraceAsString(e));
        }
        return memberGroup;
    }

    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
    /**
     * 删除促销活动中无效的数据
     *
     * @param promotionInfo
     */
    public void deletePromotionUselessInfo(PromotionInfoDTO promotionInfo) {

        promotionInfo.setVerifierId(null);
        promotionInfo.setVerifierName(null);
        promotionInfo.setVerifyTime(null);
        promotionInfo.setVerifyRemark(null);
        promotionInfo.setCreateId(null);
        promotionInfo.setCreateName(null);
//        promotionInfo.setCreateTime(null);
        promotionInfo.setModifyId(null);
        promotionInfo.setModifyName(null);
//        promotionInfo.setModifyTime(null);
        promotionInfo.setBuyerRuleId(null);
        promotionInfo.setSellerRuleId(null);
        promotionInfo.setCategoryItemRuleId(null);
        promotionInfo.setPromotionStatusHistoryList(null);
    }
    /**
     * 删除促销卖家规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteBuyerUselessInfo(PromotionInfoDTO promotionInfo) {
        PromotionBuyerRuleDTO buyerRuleDTO = promotionInfo.getBuyerRuleDTO();
        List<String> buyerCodeList = null;
        List<PromotionBuyerDetailDTO> buyerDetailDTOList = null;

        if (buyerRuleDTO != null) {
            buyerRuleDTO.setRuleId(null);
            buyerRuleDTO.setRuleName(null);
            buyerRuleDTO.setCreateId(null);
            buyerRuleDTO.setCreateName(null);
            buyerRuleDTO.setCreateTime(null);
            buyerRuleDTO.setModifyId(null);
            buyerRuleDTO.setModifyName(null);
            buyerRuleDTO.setModifyTime(null);
            buyerRuleDTO.setTargetBuyerGroup(null);
            buyerDetailDTOList = buyerRuleDTO.getBuyerDetailList();
            if (buyerDetailDTOList != null && !buyerDetailDTOList.isEmpty()) {
                buyerCodeList = new ArrayList<String>();
                for (PromotionBuyerDetailDTO buyerDetailDTO : buyerDetailDTOList) {
                    buyerCodeList.add(buyerDetailDTO.getBuyerCode());
                }
                buyerRuleDTO.setTargetBuyerCodeList(buyerCodeList);
                buyerRuleDTO.setBuyerDetailList(null);
            }
        }
    }

    /**
     * 删除促销卖家规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteSellerUselessInfo(PromotionInfoDTO promotionInfo) {
        PromotionSellerRuleDTO sellerRuleDTO = promotionInfo.getSellerRuleDTO();
        List<String> sellerCodeList = null;
        List<PromotionSellerDetailDTO> sellerDetailDTOList = null;

        if (sellerRuleDTO != null) {
            sellerRuleDTO.setRuleId(null);
            sellerRuleDTO.setRuleName(null);
            sellerRuleDTO.setCreateId(null);
            sellerRuleDTO.setCreateName(null);
            sellerRuleDTO.setCreateTime(null);
            sellerRuleDTO.setModifyId(null);
            sellerRuleDTO.setModifyName(null);
            sellerRuleDTO.setModifyTime(null);
            sellerDetailDTOList = sellerRuleDTO.getSellerDetailList();
            if (sellerDetailDTOList != null && !sellerDetailDTOList.isEmpty()) {
                sellerCodeList = new ArrayList<String>();
                for (PromotionSellerDetailDTO sellerDetailDTO : sellerDetailDTOList) {
                    sellerCodeList.add(sellerDetailDTO.getSellerCode());
                }
                sellerRuleDTO.setTargetSellerCodeList(sellerCodeList);
                sellerRuleDTO.setSellerDetailList(null);
            }
        }
    }

    /**
     * 删除促销商品规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteCategoryUselessInfo(PromotionInfoDTO promotionInfo) {
        PromotionCategoryItemRuleDTO categoryItemRuleDTO = promotionInfo.getCategoryItemRuleDTO();
        Map<String, String> categoryCodeMap = null;
        List<String> skuCodeList = null;
        List<PromotionCategoryDetailDTO> categoryDetailDTOList = null;
        List<PromotionItemDetailDTO> itemDetailDTOList = null;

        if (categoryItemRuleDTO != null) {
            categoryItemRuleDTO.setRuleId(null);
            categoryItemRuleDTO.setRuleName(null);
            categoryItemRuleDTO.setCreateId(null);
            categoryItemRuleDTO.setCreateName(null);
            categoryItemRuleDTO.setCreateTime(null);
            categoryItemRuleDTO.setModifyId(null);
            categoryItemRuleDTO.setModifyName(null);
            categoryItemRuleDTO.setModifyTime(null);
            categoryDetailDTOList = categoryItemRuleDTO.getCategoryDetailList();
            itemDetailDTOList = categoryItemRuleDTO.getItemDetailList();
            if (categoryDetailDTOList != null && !categoryDetailDTOList.isEmpty()) {
                categoryCodeMap = new HashMap<String, String>();
                for (PromotionCategoryDetailDTO categoryDetailDTO : categoryDetailDTOList) {
                    categoryCodeMap.put(categoryDetailDTO.getCategoryId().toString(), categoryDetailDTO.getBrandIdList());
                }
                categoryItemRuleDTO.setTargetCategoryCodeMap(categoryCodeMap);
                categoryItemRuleDTO.setCategoryDetailList(null);
            }
            if (itemDetailDTOList != null && !itemDetailDTOList.isEmpty()) {
                skuCodeList = new ArrayList<String>();
                for (PromotionItemDetailDTO itemDetailDTO : itemDetailDTOList) {
                    skuCodeList.add(itemDetailDTO.getSkuCode());
                }
                categoryItemRuleDTO.setTargetItemCodeList(skuCodeList);
                categoryItemRuleDTO.setItemDetailList(null);
            }
        }
    }
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
}
