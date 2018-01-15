package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.PopupAdConditionDTO;
import com.bjucloud.contentcenter.dto.PopupAdDTO;
import com.bjucloud.contentcenter.dto.PopupAdModifyConditionDTO;
import com.bjucloud.contentcenter.dto.SearchShowPopupAdDTO;

public interface HomepagePopupAdService {

    /**
     * 根据条件查询弹屏广告列表
     *
     * @param conditionDTO
     * @param page
     * @return
     */
    public ExecuteResult<DataGrid<PopupAdDTO>> queryPopupAdList(PopupAdConditionDTO conditionDTO,
            Pager<PopupAdDTO> page);

    /**
     * 根据广告ID查询弹屏广告信息
     *
     * @param adId
     * @return
     */
    public ExecuteResult<PopupAdDTO> queryPopupAdInfo(Long adId);

    /**
     * 保存弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    public ExecuteResult<String> addPopupAdInfo(PopupAdDTO popupAdDTO);

    /**
     * 更新弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    public ExecuteResult<String> updatePopupAdInfo(PopupAdDTO popupAdDTO);

    /**
     * 删除弹屏广告信息
     *
     * @param deleteAdDTO
     * @return
     */
    public ExecuteResult<String> deletePopupAdInfo(PopupAdModifyConditionDTO deleteAdDTO);

    /**
     * 根据展示终端获取展示弹框广告信息
     *
     * @param showPopupAdDTO
     * @return
     */
    public ExecuteResult<PopupAdDTO> searchShowPopupAdInfo(SearchShowPopupAdDTO showPopupAdDTO);
}
