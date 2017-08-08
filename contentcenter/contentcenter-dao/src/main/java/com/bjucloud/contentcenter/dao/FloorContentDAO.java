package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.FloorContentDTO;

/**
 * Created by thinkpad on 2017/2/8.
 */
public interface FloorContentDAO extends BaseDAO<FloorContentDTO> {

    /**
     * 根据导航ID查询记录
     * */
    public FloorContentDTO selectByNavId(Long navId);

    /**
     * 根据Id更新记录
     * */
    public Integer updateByCondition(FloorContentDTO floorContentDTO);

}
