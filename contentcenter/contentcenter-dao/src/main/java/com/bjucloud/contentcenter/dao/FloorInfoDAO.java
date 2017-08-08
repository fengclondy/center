package com.bjucloud.contentcenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.FloorInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FloorInfoDAO extends BaseDAO<FloorInfoDTO>{

    /**
     * 添加
     * */
    public void insertSelective(FloorInfoDTO floorInfoDTO);


    /**
     * 删除
     * */
    public Integer delete(FloorInfoDTO floorInfoDTO);

    /**
     * 根据条件修改状态
     * */
    public Integer updateByCondition(FloorInfoDTO floorInfoDTO);

    public Integer updateBySortNum(@Param("status")String status, @Param("sortNum") Long sortNum);

    public List<FloorInfoDTO> queryListAll(@Param("page") Pager page);

    public Long queryListAllCount();
}