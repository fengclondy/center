package com.bjucloud.contentcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.common.enmu.ReturnCodeEnum;
import com.bjucloud.contentcenter.common.exception.ContentCenterBusinessException;
import com.bjucloud.contentcenter.common.utils.ExceptionUtils;
import com.bjucloud.contentcenter.dao.HomepagePopupAdDAO;
import com.bjucloud.contentcenter.dao.HomepagePopupAdTerminalDAO;
import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.domain.HomepagePopupTerminalAd;
import com.bjucloud.contentcenter.dto.HomepagePopupAdConditionDTO;
import com.bjucloud.contentcenter.dto.HomepagePopupAdDTO;
import com.bjucloud.contentcenter.service.HomepagePopupAdService;
import com.bjucloud.contentcenter.service.convert.HomepagePopupAdConvert;
import org.springframework.stereotype.Service;

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
    public ExecuteResult<DataGrid<HomepagePopupAdDTO>> queryPopupAdList(HomepagePopupAdConditionDTO conditionDTO,
            Pager<HomepagePopupAdDTO> page) {
        ExecuteResult<DataGrid<HomepagePopupAdDTO>> result = new ExecuteResult<DataGrid<PromotionDiscountInfoDTO>>();
        DataGrid<HomepagePopupAdDTO> dataGrid = new DataGrid<HomepagePopupAdDTO>();
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
    public ExecuteResult<HomepagePopupAdDTO> queryPopupAdInfo(Long adId) {
        ExecuteResult<HomepagePopupAdDTO> result = new ExecuteResult<HomepagePopupAdDTO>();
        HomepagePopupAdDTO popupAdDTO = null;
        HomepagePopupAd popupAd = null;
        List<HomepagePopupTerminalAd> terminalAdList = null;
        HomepagePopupAdConvert convert = null;
        try {
            popupAd = homepagePopupAdDAO.queryById(adId);
            if (popupAd == null) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_NOT_EXISTS.getCode(), ReturnCodeEnum.AD_NOT_EXISTS.getDesc());
            }
            if (popupAd.getDeleteFlag() == 1) {
                throw new ContentCenterBusinessException(ReturnCodeEnum.AD_HAS_DELETED.getCode(), ReturnCodeEnum.AD_HAS_DELETED.getDesc());
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
    public ExecuteResult<String> addPopupAdInfo(HomepagePopupAdDTO popupAdDTO) {
        return null;
    }

    /**
     * 更新弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    @Override
    public ExecuteResult<String> updatePopupAdInfo(HomepagePopupAdDTO popupAdDTO) {
        return null;
    }

    /**
     * 删除弹屏广告信息
     *
     * @param adId
     * @return
     */
    @Override
    public ExecuteResult<String> deletePopupAdInfo(Long adId) {
        return null;
    }
}
