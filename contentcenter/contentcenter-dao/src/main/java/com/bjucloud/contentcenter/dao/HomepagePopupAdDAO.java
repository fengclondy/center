package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.dto.PopupAdConditionDTO;
import com.bjucloud.contentcenter.dto.PopupAdConditionExtendDTO;
import com.bjucloud.contentcenter.dto.PopupAdDTO;
import org.apache.ibatis.annotations.Param;

public interface HomepagePopupAdDAO extends BaseDAO<HomepagePopupAd> {

    /**
     * 根据条件查询弹屏活动数量
     *
     * @param entity
     * @return
     */
    public Long queryCount(@Param("entity") PopupAdConditionExtendDTO entity);

    /**
     * 分页查询弹屏活动列表
     *
     * @param entity
     * @param page
     * @return
     */
    public List<HomepagePopupAd> queryList(@Param("entity") PopupAdConditionExtendDTO entity, @Param("page") Pager page);

    /**
     * 校验弹屏活动是否和已有活动的期间重叠
     *
     * @param popupAdDTO
     * @return
     */
    public Long checkPopupAdPeriodRepeat(PopupAdDTO popupAdDTO);

    /**
     * 根据展示终端获取展示弹框广告信息
     *
     * @param terminalTypeCode
     * @return
     */
    public HomepagePopupAd queryShowPopupAd(String terminalTypeCode);
}
