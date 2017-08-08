package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.FloorContentPicSubDTO;

import java.util.List;

/**
 * Created by thinkpad on 2017/2/8.
 */
public interface FloorContentPicSubDAO extends BaseDAO<FloorContentPicSubDTO> {
    /**
     * 根据contentId删除记录
     * */
    public void deleteByContentId(Long contentId);

    /**
     * 根据floorNavId删除记录
     * */
    public void deleteByFloorNavId(Long floorNavId);

    public List<FloorContentPicSubDTO> selectByNavId(Long floorNavId);

}
