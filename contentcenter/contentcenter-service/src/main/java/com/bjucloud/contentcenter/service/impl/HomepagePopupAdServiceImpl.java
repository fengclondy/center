package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.common.enmu.ReturnCodeEnum;
import com.bjucloud.contentcenter.common.exception.ContentCenterBusinessException;
import com.bjucloud.contentcenter.common.utils.ExceptionUtils;
import com.bjucloud.contentcenter.common.utils.ValidateResult;
import com.bjucloud.contentcenter.common.utils.ValidationUtils;
import com.bjucloud.contentcenter.dao.HomepagePopupAdDAO;
import com.bjucloud.contentcenter.dao.HomepagePopupAdTerminalDAO;
import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.domain.HomepagePopupTerminalAd;
import com.bjucloud.contentcenter.dto.PopupAdConditionDTO;
import com.bjucloud.contentcenter.dto.PopupAdDTO;
import com.bjucloud.contentcenter.dto.PopupAdModifyConditionDTO;
import com.bjucloud.contentcenter.dto.SearchShowPopupAdDTO;
import com.bjucloud.contentcenter.enums.PopupAdTerminalTypeEnums;
import com.bjucloud.contentcenter.enums.YesNoEnums;
import com.bjucloud.contentcenter.service.HomepagePopupAdService;
import com.bjucloud.contentcenter.service.convert.HomepagePopupAdConvert;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Transactional
@Service("homepagePopupAdService")
public class HomepagePopupAdServiceImpl implements HomepagePopupAdService {

    @Resource
    private HomepagePopupAdDAO homepagePopupAdDAO;

    @Resource
    private HomepagePopupAdTerminalDAO homepagePopupAdTerminalDAO;

    /**
     * 根据条件查询弹屏广告列表
     *
     * @param conditionDTO
     * @param page
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<PopupAdDTO>> queryPopupAdList(PopupAdConditionDTO conditionDTO,
            Pager<PopupAdDTO> page) {
        ExecuteResult<DataGrid<PopupAdDTO>> result = new ExecuteResult<DataGrid<PopupAdDTO>>();
        DataGrid<PopupAdDTO> dataGrid = new DataGrid<PopupAdDTO>();
        List<HomepagePopupAd> adList = null;
        List<HomepagePopupTerminalAd> terminalAdList = null;
        HomepagePopupAdConvert convert = null;
        long count = 0;
        try {
            count = homepagePopupAdDAO.queryCount(conditionDTO);
            if (count > 0) {
                adList = homepagePopupAdDAO.queryList(conditionDTO, page);
                for (HomepagePopupAd popupAd : adList) {
                    terminalAdList = homepagePopupAdTerminalDAO.queryByAdId(popupAd.getId());
                    popupAd.setTerminalAdList(terminalAdList);
                }
                convert = new HomepagePopupAdConvert();
                dataGrid.setRows(convert.toTarget(adList));
            }
            dataGrid.setTotal(count);
            result.setResult(dataGrid);
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 根据广告ID查询弹屏广告信息
     *
     * @param adId
     * @return
     */
    @Override
    public ExecuteResult<PopupAdDTO> queryPopupAdInfo(Long adId) {
        ExecuteResult<PopupAdDTO> result = new ExecuteResult<PopupAdDTO>();
        PopupAdDTO popupAdDTO = null;
        HomepagePopupAd popupAd = null;
        List<HomepagePopupTerminalAd> terminalAdList = null;
        HomepagePopupAdConvert convert = null;
        try {
            popupAd = homepagePopupAdDAO.queryById(adId);
            if (popupAd == null) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_NOT_EXISTS.getCode(),
                        ReturnCodeEnum.AD_NOT_EXISTS.getDesc());
            }
            if (popupAd.getDeleteFlag() == YesNoEnums.YES.getValue()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_HAS_DELETED.getCode(),
                        ReturnCodeEnum.AD_HAS_DELETED.getDesc());
            }
            terminalAdList = homepagePopupAdTerminalDAO.queryByAdId(popupAd.getId());
            popupAd.setTerminalAdList(terminalAdList);
            convert = new HomepagePopupAdConvert();
            popupAdDTO = convert.toTarget(popupAd);
            result.setResult(popupAdDTO);
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 保存弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    @Override
    public ExecuteResult<String> addPopupAdInfo(PopupAdDTO popupAdDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        HomepagePopupAd popupAd = null;
        HomepagePopupAdConvert convert = null;
        try {
            ValidateResult validateResult = ValidationUtils.validateEntity(popupAdDTO);
            if (validateResult.isHasErrors()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            checkTerminalTypeValid(popupAdDTO.getTerminalTypeList());
            checkPopupAdPeriodRepeat(popupAdDTO);
            convert = new HomepagePopupAdConvert();
            popupAd = convert.toSource(popupAdDTO);
            insertPopupAdInfo(popupAd);
            result.setResult("处理成功");
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 更新弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    @Override
    public ExecuteResult<String> updatePopupAdInfo(PopupAdDTO popupAdDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        Long adId = popupAdDTO.getId();
        HomepagePopupAd popupAd = null;
        HomepagePopupAdConvert convert = null;
        try {
            ValidateResult validateResult = ValidationUtils.validateEntity(popupAdDTO);
            if (validateResult.isHasErrors()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            if (adId == null) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_NOT_NULL.getCode(), "弹屏广告ID不能为空");
            }
            checkTerminalTypeValid(popupAdDTO.getTerminalTypeList());
            popupAd = homepagePopupAdDAO.queryById(adId);
            if (popupAd == null) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_NOT_EXISTS.getCode(),
                        ReturnCodeEnum.AD_NOT_EXISTS.getDesc());
            }
            if (popupAd.getDeleteFlag() == YesNoEnums.YES.getValue()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_HAS_DELETED.getCode(),
                        ReturnCodeEnum.AD_HAS_DELETED.getDesc());
            }
            checkPopupAdPeriodRepeat(popupAdDTO);
            convert = new HomepagePopupAdConvert();
            popupAd = convert.toSource(popupAdDTO);
            updatePopupAdInfo(popupAd);
            result.setResult("处理成功");
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 删除弹屏广告信息
     *
     * @param deleteAdDTO
     * @return
     */
    @Override
    public ExecuteResult<String> deletePopupAdInfo(PopupAdModifyConditionDTO deleteAdDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        Long adId = deleteAdDTO.getAdId();
        HomepagePopupAd popupAd = null;
        try {
            ValidateResult validateResult = ValidationUtils.validateEntity(deleteAdDTO);
            if (validateResult.isHasErrors()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            popupAd = homepagePopupAdDAO.queryById(adId);
            if (popupAd == null) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_NOT_EXISTS.getCode(),
                        ReturnCodeEnum.AD_NOT_EXISTS.getDesc());
            }
            if (popupAd.getDeleteFlag() == YesNoEnums.YES.getValue()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_HAS_DELETED.getCode(),
                        ReturnCodeEnum.AD_HAS_DELETED.getDesc());
            }
            popupAd = new HomepagePopupAd();
            popupAd.setId(deleteAdDTO.getAdId());
            popupAd.setModifyId(deleteAdDTO.getOperatorId());
            popupAd.setModifyName(deleteAdDTO.getOperatorName());
            homepagePopupAdDAO.delete(popupAd);
            result.setResult("处理成功");
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 根据展示终端获取展示弹框广告信息
     *
     * @param showPopupAdDTO
     * @return
     */
    @Override
    public ExecuteResult<PopupAdDTO> searchShowPopupAdInfo(SearchShowPopupAdDTO showPopupAdDTO) {
        ExecuteResult<PopupAdDTO> result = new ExecuteResult<PopupAdDTO>();
        HomepagePopupAdConvert convert = null;
        HomepagePopupAd popupAd = null;
        PopupAdDTO popupAdDTO = null;
        String terminalDesc = "";
        try {
            ValidateResult validateResult = ValidationUtils.validateEntity(showPopupAdDTO);
            if (validateResult.isHasErrors()) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            terminalDesc = PopupAdTerminalTypeEnums.getTypeDesc(showPopupAdDTO.getTerminalTypeCode());
            if (StringUtils.isEmpty(terminalDesc)) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_VALUE_ERROR.getCode(), "展示终端类型不正确");
            }
            popupAd = homepagePopupAdDAO.queryShowPopupAd(showPopupAdDTO.getTerminalTypeCode());
            if (popupAd != null) {
                convert = new HomepagePopupAdConvert();
                popupAdDTO = convert.toTarget(popupAd);
                result.setResult(popupAdDTO);
            }
        } catch (ContentCenterBusinessException ccbe) {
            result.setCode(ccbe.getCode());
            result.addErrorMessage(ccbe.getMessage());
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 校验弹屏活动是否和已有活动的期间重叠
     *
     * @param popupAdDTO
     */
    private void checkPopupAdPeriodRepeat(PopupAdDTO popupAdDTO) {
        Long count = 0L;
        count = homepagePopupAdDAO.checkPopupAdPeriodRepeat(popupAdDTO);
        if (count > 0) {
            throw new ContentCenterBusinessException(ReturnCodeEnum.AD_PERIOD_REPEAT.getCode(),
                    ReturnCodeEnum.AD_PERIOD_REPEAT.getDesc());
        }
    }

    /**
     * 插入弹屏广告信息
     *
     * @param popupAd
     * @throws Exception
     */
    private void insertPopupAdInfo(HomepagePopupAd popupAd) throws Exception {
        Long adId = 0L;

        try {
            homepagePopupAdDAO.add(popupAd);
            adId = popupAd.getId();
            if (popupAd.getTerminalAdList() != null && !popupAd.getTerminalAdList().isEmpty()) {
                for (HomepagePopupTerminalAd popupTerminalAd : popupAd.getTerminalAdList()) {
                    popupTerminalAd.setAdId(adId);
                }
                homepagePopupAdTerminalDAO.insertList(popupAd.getTerminalAdList());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    /**
     * 更新弹屏广告信息
     *
     * @param popupAd
     * @throws Exception
     */
    private void updatePopupAdInfo(HomepagePopupAd popupAd) throws Exception {
        Long adId = popupAd.getId();
        int count = 0;
        HomepagePopupTerminalAd terminalCondition = new HomepagePopupTerminalAd();
        try {
            terminalCondition.setAdId(adId);
            terminalCondition.setModifyId(popupAd.getModifyId());
            terminalCondition.setModifyName(popupAd.getModifyName());
            count = homepagePopupAdDAO.update(popupAd);
            if (count != 1) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_NOT_EXISTS.getCode(),
                        ReturnCodeEnum.AD_NOT_EXISTS.getDesc());
            }
            homepagePopupAdTerminalDAO.deleteByAdId(terminalCondition);
            if (popupAd.getTerminalAdList() != null && !popupAd.getTerminalAdList().isEmpty()) {
                homepagePopupAdTerminalDAO.insertList(popupAd.getTerminalAdList());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    /**
     * 校验参数的终端类型是否合法
     * @param terminalTypeList
     */
    private void checkTerminalTypeValid(List<String> terminalTypeList) {
        String desc = "";
        if (terminalTypeList != null && !terminalTypeList.isEmpty()) {
            for (String terminalType : terminalTypeList) {
                desc = PopupAdTerminalTypeEnums.getTypeDesc(terminalType);
                if (StringUtils.isEmpty(desc)) {
                    throw new ContentCenterBusinessException(ReturnCodeEnum.PARAMETER_VALUE_ERROR.getCode(),
                            "终端类型的值不正确 类型:" + terminalType);
                }
            }
        }
    }
}
