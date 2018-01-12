package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.HomepagePopupAdConditionDTO;
import com.bjucloud.contentcenter.dto.HomepagePopupAdDTO;

public interface HomepagePopupAdService {

    /**
     * 根据条件查询弹屏广告列表
     *
     * @param conditionDTO
     * @param page
     * @return
     */
    public ExecuteResult<DataGrid<HomepagePopupAdDTO>> queryPopupAdList(HomepagePopupAdConditionDTO conditionDTO, Pager<HomepagePopupAdDTO> page);

    /**
     * 根据广告ID查询弹屏广告信息
     *
     * @param adId
     * @return
     */
    public ExecuteResult<HomepagePopupAdDTO> queryPopupAdInfo(Long adId);

    /**
     * 保存弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    public ExecuteResult<String> addPopupAdInfo(HomepagePopupAdDTO popupAdDTO);

    /**
     * 更新弹屏广告信息
     *
     * @param popupAdDTO
     * @return
     */
    public ExecuteResult<String> updatePopupAdInfo(HomepagePopupAdDTO popupAdDTO);

    /**
     * 删除弹屏广告信息
     *
     * @param adId
     * @return
     */
    public ExecuteResult<String> deletePopupAdInfo(Long adId);
}
