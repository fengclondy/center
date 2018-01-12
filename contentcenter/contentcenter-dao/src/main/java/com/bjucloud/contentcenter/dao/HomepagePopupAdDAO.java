package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.dto.HomepagePopupAdConditionDTO;
import org.apache.ibatis.annotations.Param;

public interface HomepagePopupAdDAO extends BaseDAO<HomepagePopupAd> {

    public Long queryCount(@Param("entity") HomepagePopupAdConditionDTO entity);

    public List<HomepagePopupAd> queryList(@Param("entity") HomepagePopupAdConditionDTO entity,
            @Param("page") Pager page);
}
