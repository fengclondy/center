package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.FloorContentSubDTO;

/**
 * Created by thinkpad on 2017/2/8.
 */
public interface FloorContentSubDAO extends BaseDAO<FloorContentSubDTO> {
    /**
     * 根据contentId删除记录
     * */
    public void deleteByContentId(Long contentId);

    /**
     * 根据floorId删除记录
     * */
    public void deleteByFloorId(Long floorId);

}
