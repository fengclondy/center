package cn.htd.marketcenter.service.impl.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.marketcenter.common.enums.NoticeTypeEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionDiscountInfoDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dto.DiscountConditionDTO;
import cn.htd.marketcenter.dto.ModifyPromotionCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.marketcenter.dto.VerifyInfoDTO;
import cn.htd.marketcenter.service.DiscountInfoService;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.handle.CouponRedisHandle;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("discountInfoService")
public class DiscountInfoServiceImpl implements DiscountInfoService {

    /**
     * 会员领取优惠券的URL
     */
    private static final String MEMBER_COLLECT_COUPON_BASE_URL = "receive.coupon.url";

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private CouponRedisHandle couponRedisHandle;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private PromotionDiscountInfoDAO promotionDiscountInfoDAO;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;

    @Override
    public ExecuteResult<DataGrid<PromotionDiscountInfoDTO>> queryCouponInfoByCondition(
            DiscountConditionDTO conditionDTO, Pager<PromotionDiscountInfoDTO> page) {
        ExecuteResult<DataGrid<PromotionDiscountInfoDTO>> result =
                new ExecuteResult<DataGrid<PromotionDiscountInfoDTO>>();
        DataGrid<PromotionDiscountInfoDTO> dataGrid = new DataGrid<PromotionDiscountInfoDTO>();
        PromotionDiscountInfoDTO searchConditionDTO = new PromotionDiscountInfoDTO();
        List<PromotionDiscountInfoDTO> couponInfoList = new ArrayList<PromotionDiscountInfoDTO>();
        long count = 0;
        try {
            searchConditionDTO.setPromotionType(dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
            searchConditionDTO.setPromotionName(conditionDTO.getDiscountName());
            searchConditionDTO.setRewardType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE,
                    DictionaryConst.OPT_PROMOTION_REWARD_TYPE_VOUCHER));
            searchConditionDTO.setCouponKind(conditionDTO.getDiscountType());
            searchConditionDTO.setStatus(conditionDTO.getStatus());
            searchConditionDTO.setVerifyStatusList(getDiscountVerifyStatusConsdition(conditionDTO));
            searchConditionDTO.setDeleteStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            count = promotionDiscountInfoDAO.queryCount(searchConditionDTO);
            if (count > 0) {
                couponInfoList = promotionDiscountInfoDAO.queryList(searchConditionDTO, page);
                dataGrid.setRows(couponInfoList);
            }
            dataGrid.setTotal(count);
            result.setResult(dataGrid);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<PromotionDiscountInfoDTO> queryCouponInfo(String promotionId, String levelCode) {
        ExecuteResult<PromotionDiscountInfoDTO> result = new ExecuteResult<PromotionDiscountInfoDTO>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionDiscountInfoDTO couponInfoDTO = null;
        List<PromotionStatusHistoryDTO> historyList = null;
        try {
            accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId, levelCode);
            // 获取优惠券活动信息
            couponInfoDTO = promotionDiscountInfoDAO.queryDiscountInfoById(accuDTO);
            if (couponInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该促销活动不存在!");
            }
            couponInfoDTO.setPromotionAccumulaty(accuDTO);
            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
            couponInfoDTO.setPromotionStatusHistoryList(historyList);
            couponInfoDTO.setReceiveCount(couponRedisHandle.getRedisCouponReceiveCount(promotionId));
            result.setResult(couponInfoDTO);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<PromotionDiscountInfoDTO> addCouponInfo(PromotionDiscountInfoDTO couponInfo) {
        ExecuteResult<PromotionDiscountInfoDTO> result = new ExecuteResult<PromotionDiscountInfoDTO>();
        try {
            couponInfo.setPromotionType(dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
            couponInfo.setEffectiveTime(couponInfo.getEffectiveStartTime());
            couponInfo.setInvalidTime(couponInfo.getEffectiveEndTime());
            checkCouponParamValid(couponInfo);
            couponInfo = saveCouponInfo(couponInfo);
            result.setResult(couponInfo);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ExecuteResult<PromotionDiscountInfoDTO> updateCouponInfo(PromotionDiscountInfoDTO couponInfo) {
        ExecuteResult<PromotionDiscountInfoDTO> result = new ExecuteResult<PromotionDiscountInfoDTO>();
        String promotionId = couponInfo.getPromotionId();
        PromotionInfoDTO promotionInfoDTO = null;
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = null;
        String couponProviceType = couponInfo.getCouponProvideType();
        String couponReceiveUrl = "";
        String modifyTimeStr = "";
        String paramModifyTimeStr = "";
        String pendingStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING);
        String refuseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_FEFUSE);
        try {
            if (StringUtils.isEmpty(promotionId) || StringUtils.isEmpty(couponInfo.getLevelCode())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        "修改优惠券活动的促销活动ID和层级编码不能为空");
            }
            couponInfo.setPromotionType(dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
            couponInfo.setModifyId(couponInfo.getCreateId());
            couponInfo.setModifyName(couponInfo.getCreateName());
            couponInfo.setEffectiveTime(couponInfo.getEffectiveStartTime());
            couponInfo.setInvalidTime(couponInfo.getEffectiveEndTime());
            checkCouponParamValid(couponInfo);
            if (couponInfo.getModifyTime() == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "优惠券活动更新时间参数不能为空");
            }
            paramModifyTimeStr = DateUtils.format(couponInfo.getModifyTime(), DateUtils.YMDHMS);
            // 根据活动ID获取活动信息
            promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
            if (promotionInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "优惠券活动不存在");
            }
            modifyTimeStr = DateUtils.format(promotionInfoDTO.getModifyTime(), DateUtils.YMDHMS);
            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_MODIFIED,
                        "优惠券活动:" + promotionId + " 已被修改请重新确认");
            }
            if (!pendingStatus.equals(promotionInfoDTO.getShowStatus()) && !refuseStatus
                    .equals(promotionInfoDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
                        "优惠券活动:" + promotionId + " 只有待审核或审核拒绝时才能修改");
            }
            couponInfo.setShowStatus(pendingStatus);
            accuDTO = baseService.updateSingleAccumulatyPromotionInfo(couponInfo);
            couponInfo.setPromotionAccumulaty(accuDTO);
            if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                    DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT).equals(couponProviceType)) {
                couponReceiveUrl =
                        SysProperties.getProperty(MEMBER_COLLECT_COUPON_BASE_URL) + "?promotionId=" + couponInfo
                                .getPromotionId() + "&levelCode=" + couponInfo.getLevelCode();
                couponInfo.setCouponReceiveUrl(couponReceiveUrl);
            }
            promotionDiscountInfoDAO.update(couponInfo);
            historyDTO.setPromotionId(couponInfo.getPromotionId());
            historyDTO.setPromotionStatus(couponInfo.getShowStatus());
            historyDTO.setPromotionStatusText("修改优惠券活动信息");
            historyDTO.setCreateId(couponInfo.getCreateId());
            historyDTO.setCreateName(couponInfo.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);
            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
            couponInfo.setPromotionStatusHistoryList(historyList);
            result.setResult(couponInfo);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ExecuteResult<String> updateConfirmedCouponInfo(ModifyPromotionCouponInfoDTO modifyCouponInfo) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionDiscountInfoDTO couponInfoDTO = null;
        PromotionInfoDTO oldPromotionInfo = null;
        String paramModifyTimeStr = "";
        String modifyTimeStr = "";
        String promotionId = modifyCouponInfo.getPromotionId();
        String passStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS);
        String validStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(modifyCouponInfo);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            paramModifyTimeStr = DateUtils.format(modifyCouponInfo.getModifyTime(), DateUtils.YMDHMS);
            // 根据活动ID获取活动信息
            accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId);
            if (accuDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "优惠券活动不存在");
            }
            if (!passStatus.equals(accuDTO.getShowStatus()) && !validStatus.equals(accuDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
                        "优惠券活动:" + promotionId + " 的状态不是审核通过");
            }
            if ((new Date()).after(accuDTO.getInvalidTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED,
                        "优惠券活动:" + promotionId + " 已过有效期不能修改");
            }
            modifyTimeStr = DateUtils.format(accuDTO.getModifyTime(), DateUtils.YMDHMS);
            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_MODIFIED,
                        "优惠券活动:" + promotionId + " 已被修改请重新确认");
            }
            // 获取优惠券活动信息
            couponInfoDTO = promotionDiscountInfoDAO.queryDiscountInfoById(accuDTO);
            if (couponInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该促销活动不存在!");
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                    DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT).equals(couponInfoDTO.getCouponProvideType())) {
                if (modifyCouponInfo.getPrepStartTime() == null || modifyCouponInfo.getPrepEndTime() == null) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "自助领券的开始日和结束日都不能为空");
                }
            } else {
                modifyCouponInfo.setPrepStartTime(null);
                modifyCouponInfo.setPrepEndTime(null);
            }
            couponInfoDTO.setPromotionAccumulaty(accuDTO);
            couponInfoDTO.setPromotionName(modifyCouponInfo.getPromotionName());
            couponInfoDTO.setPromotionDescribe(modifyCouponInfo.getPromotionDescribe());
            couponInfoDTO.setShowStatus(passStatus);
            couponInfoDTO.setEffectiveStartTime(modifyCouponInfo.getEffectiveStartTime());
            couponInfoDTO.setEffectiveEndTime(modifyCouponInfo.getEffectiveEndTime());
            couponInfoDTO.setEffectiveTime(modifyCouponInfo.getEffectiveStartTime());
            couponInfoDTO.setInvalidTime(modifyCouponInfo.getEffectiveEndTime());
            couponInfoDTO.setPrepStartTime(modifyCouponInfo.getPrepStartTime());
            couponInfoDTO.setPrepEndTime(modifyCouponInfo.getPrepEndTime());
            couponInfoDTO.setCreateId(modifyCouponInfo.getOperatorId());
            couponInfoDTO.setCreateName(modifyCouponInfo.getOperatorName());
            couponInfoDTO.setModifyPromotionId(couponInfoDTO.getPromotionId());
            oldPromotionInfo = disableModifiedCouponInfo(couponInfoDTO);
            couponInfoDTO = saveCouponInfo(couponInfoDTO);
            couponRedisHandle.saveCouponValidStatus2Redis(oldPromotionInfo);
            couponInfoDTO.setShowStatus(validStatus);
            couponRedisHandle.addCouponInfo2Redis(couponInfoDTO);
            result.setResult("处理成功");
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ExecuteResult<String> saveVerifiedCouponInfo(VerifyInfoDTO verifyInfo) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = null;
        PromotionDiscountInfoDTO couponInfo = null;
        String promotionId = verifyInfo.getPromotionId();
        String paramModifyTimeStr = "";
        String modifyTimeStr = "";
        String pendingStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING);
        String passStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS);
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(verifyInfo);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            paramModifyTimeStr = DateUtils.format(verifyInfo.getModifyTime(), DateUtils.YMDHMS);
            // 根据活动ID获取活动信息
            accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId);
            if (accuDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "优惠券活动不存在");
            }
            if (!pendingStatus.equals(accuDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
                        "优惠券活动:" + promotionId + " 的状态不是待审核");
            }
            modifyTimeStr = DateUtils.format(accuDTO.getModifyTime(), DateUtils.YMDHMS);
            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_MODIFIED,
                        "优惠券活动:" + promotionId + " 已被修改请重新确认");
            }
            couponInfo = promotionDiscountInfoDAO.queryDiscountInfoById(accuDTO);
            if (couponInfo == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "优惠券活动不存在");
            }
            accuDTO.setShowStatus(verifyInfo.getVerifyResult());
            accuDTO.setVerifierId(verifyInfo.getVerifierId());
            accuDTO.setVerifierName(verifyInfo.getVerifierName());
            accuDTO.setVerifyRemark(verifyInfo.getVerifyRemark());
            accuDTO.setModifyId(verifyInfo.getVerifierId());
            accuDTO.setModifyName(verifyInfo.getVerifierName());
            if (passStatus.equals(accuDTO.getShowStatus()) && (new Date()).after(accuDTO.getInvalidTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED, "优惠券活动已过有效期");
            }
            promotionInfoDAO.updatePromotionVerifyInfo(accuDTO);
            historyDTO = new PromotionStatusHistoryDTO();
            historyDTO.setPromotionId(promotionId);
            historyDTO.setPromotionStatus(verifyInfo.getVerifyResult());
            historyDTO.setPromotionStatusText(dictionary
                    .getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, verifyInfo.getVerifyResult()) + " "
                    + verifyInfo.getVerifyRemark());
            historyDTO.setCreateId(verifyInfo.getVerifierId());
            historyDTO.setCreateName(verifyInfo.getVerifierName());
            promotionStatusHistoryDAO.add(historyDTO);
            if (passStatus.equals(accuDTO.getShowStatus())) {
                couponInfo.setPromotionAccumulaty(accuDTO);
                if (checkPromotionStatusChanged(couponInfo)) {
                    historyDTO = new PromotionStatusHistoryDTO();
                    historyDTO.setPromotionId(promotionId);
                    historyDTO.setPromotionStatus(couponInfo.getStatus());
                    historyDTO.setPromotionStatusText(
                            dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, couponInfo.getStatus()));
                    historyDTO.setCreateId(verifyInfo.getVerifierId());
                    historyDTO.setCreateName(verifyInfo.getVerifierName());
                    promotionStatusHistoryDAO.add(historyDTO);
                    promotionInfoDAO.updatePromotionStatusById(couponInfo);
                }
                couponInfo.setCouponUseRangeDesc(accuDTO.getSellerRuleDesc());
                couponInfo.setCouponItemDesc(accuDTO.getCategoryItemRuleDesc());
                couponRedisHandle.addCouponInfo2Redis(couponInfo);
            }
            result.setResult("处理成功");
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }


    @Override
    public ExecuteResult<String> deleteCouponInfo(PromotionValidDTO validDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        PromotionAccumulatyDTO condition = new PromotionAccumulatyDTO();
        PromotionDiscountInfoDTO couponInfo = null;
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(validDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            condition = baseService.querySingleAccumulatyPromotionInfo(validDTO.getPromotionId());
            couponInfo = promotionDiscountInfoDAO.queryDiscountInfoById(condition);
            if (couponInfo == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.SYSTEM_ERROR, "该优惠券活动不存在");
            }
            baseService.deletePromotionInfo(validDTO);
            historyDTO.setPromotionId(validDTO.getPromotionId());
            historyDTO.setPromotionStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            historyDTO.setPromotionStatusText(
                    dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, historyDTO.getPromotionStatus()));
            historyDTO.setCreateId(validDTO.getOperatorId());
            historyDTO.setCreateName(validDTO.getOperatorName());
            promotionStatusHistoryDAO.add(historyDTO);
            couponRedisHandle.deleteRedisCouponInfo(couponInfo);
            result.setResult("处理成功");
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据查询条件的审核条件判断检索用条件
     *
     * @param conditionDTO
     * @return
     */
    private List<String> getDiscountVerifyStatusConsdition(DiscountConditionDTO conditionDTO) {
        List<String> verifyStatusList = new ArrayList<String>();
        String searchVerifyStatus = conditionDTO.getVerifyStatus();
        String passStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS);

        if (StringUtils.isEmpty(searchVerifyStatus)) {
            return verifyStatusList;
        }
        if (StringUtils.isNotEmpty(
                dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, searchVerifyStatus))) {
            verifyStatusList.add(searchVerifyStatus);
            if (passStatus.equals(searchVerifyStatus)) {
                verifyStatusList.add(dictionary.getCodeByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
            }
        }
        return verifyStatusList;
    }

    /**
     * 校验新增／修改优惠券活动信息的合法性
     *
     * @param couponInfo
     * @throws MarketCenterBusinessException
     */
    private void checkCouponParamValid(PromotionDiscountInfoDTO couponInfo) throws MarketCenterBusinessException {
        // 输入DTO的验证
        ValidateResult validateResult = ValidationUtils.validateEntity(couponInfo);
        // 有错误信息时返回错误信息
        if (validateResult.isHasErrors()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                    validateResult.getErrorMsg());
        }
        if (BigDecimal.ZERO.compareTo(couponInfo.getDiscountThreshold()) >= 0) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "优惠券使用门槛不能低于0元");
        }
        if (BigDecimal.ZERO.compareTo(couponInfo.getDiscountAmount()) >= 0) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "优惠券金额不能低于0元");
        }
        if (couponInfo.getDiscountThreshold().compareTo(couponInfo.getDiscountAmount()) < 0) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "优惠券使用门槛不能低于优惠券金额");
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT).equals(couponInfo.getCouponProvideType())) {
            if (couponInfo.getPrepStartTime() == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "领券结束日期不能为空");
            }
            if (couponInfo.getPrepEndTime() == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "领券结束日期不能为空");
            }
            if (couponInfo.getPrepStartTime().after(couponInfo.getPrepEndTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "领券开始日期必须小于结束日期");
            }
            if (couponInfo.getProvideCount() == null || couponInfo.getProvideCount().intValue() < 1) {
                if (NoticeTypeEnum.NO.getValue() != couponInfo.getIsNeedRemind()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "申请数量不能为空不能为空且必须大于0");
                }
            }
        }
        if (!dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_TRIGGER_SEND).equals(couponInfo.getCouponProvideType())) {
            if (couponInfo.getReceiveLimit() == null || couponInfo.getReceiveLimit().intValue() < 1) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "每人最大领取次数不能为空且必须大于0");
            }
            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
        } else {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "不能新增触发返券类型优惠券");
            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
        }
    }

    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    private boolean checkPromotionStatusChanged(PromotionInfoDTO promotionInfo) {
        Date nowDt = new Date();
        String oldStatus = promotionInfo.getStatus();
        String status = baseService.setPromotionStatusInfo(promotionInfo);
        return oldStatus.equals(status) ? false : true;
    }

    /**
     * 更新已失效的优惠券信息
     *
     * @param modifiedCouponInfo
     * @return
     * @throws Exception
     */
    private PromotionInfoDTO disableModifiedCouponInfo(PromotionInfoDTO modifiedCouponInfo) throws Exception {
        PromotionInfoDTO oldPromotionInfoDTO = new PromotionInfoDTO();
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();

        oldPromotionInfoDTO.setPromoionInfo(modifiedCouponInfo);
        oldPromotionInfoDTO.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
        oldPromotionInfoDTO.setModifyId(modifiedCouponInfo.getCreateId());
        oldPromotionInfoDTO.setModifyName(modifiedCouponInfo.getCreateName());
        promotionInfoDAO.savePromotionValidStatus(oldPromotionInfoDTO);
        historyDTO.setPromotionId(oldPromotionInfoDTO.getPromotionId());
        historyDTO.setPromotionStatus(oldPromotionInfoDTO.getShowStatus());
        historyDTO.setPromotionStatusText("已失效");
        historyDTO.setCreateId(oldPromotionInfoDTO.getModifyId());
        historyDTO.setCreateName(oldPromotionInfoDTO.getModifyName());
        promotionStatusHistoryDAO.add(historyDTO);
        return oldPromotionInfoDTO;
    }

    /**
     * 保存优惠券信息
     *
     * @param couponInfo
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private PromotionDiscountInfoDTO saveCouponInfo(PromotionDiscountInfoDTO couponInfo)
            throws MarketCenterBusinessException, Exception {
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
        String couponProviceType = couponInfo.getCouponProvideType();
        String couponReceiveUrl = "";

        if (StringUtils.isEmpty(couponInfo.getShowStatus())) {
            couponInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
        }
        accuDTO = baseService.insertSingleAccumulatyPromotionInfo(couponInfo);
        couponInfo.setPromotionAccumulaty(accuDTO);
        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT).equals(couponProviceType)) {
            couponReceiveUrl = SysProperties.getProperty(MEMBER_COLLECT_COUPON_BASE_URL) + "?promotionId=" + couponInfo
                    .getPromotionId() + "&levelCode=" + couponInfo.getLevelCode();
            couponInfo.setCouponReceiveUrl(couponReceiveUrl);
        }
        promotionDiscountInfoDAO.add(couponInfo);
        historyDTO.setPromotionId(couponInfo.getPromotionId());
        historyDTO.setCreateId(couponInfo.getCreateId());
        historyDTO.setCreateName(couponInfo.getCreateName());
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(couponInfo.getShowStatus()) || dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(couponInfo.getShowStatus())) {
            historyDTO.setPromotionStatus(couponInfo.getStatus());
            historyDTO.setPromotionStatusText(
                    dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, couponInfo.getStatus()));
        } else {
            historyDTO.setPromotionStatus(couponInfo.getShowStatus());
            historyDTO.setPromotionStatusText(dictionary
                    .getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, couponInfo.getShowStatus()));
        }
        if (!StringUtils.isEmpty(couponInfo.getModifyPromotionId())) {
            historyDTO.setPromotionStatusText(
                    historyDTO.getPromotionStatusText() + "(替代促销活动编号:" + couponInfo.getModifyPromotionId() + ")");
        }
        promotionStatusHistoryDAO.add(historyDTO);
        historyList.add(historyDTO);
        couponInfo.setPromotionStatusHistoryList(historyList);
        return couponInfo;
    }
}
