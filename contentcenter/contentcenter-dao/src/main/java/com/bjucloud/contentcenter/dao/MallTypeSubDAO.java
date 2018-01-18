package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.HTDMallTypeSubDTO;

import cn.htd.common.dao.orm.BaseDAO;

public interface MallTypeSubDAO extends BaseDAO<HTDMallTypeSubDTO> {
    /**
     * 根据类目ID获取子表信息
     * @param typeId
     * @return
     */
    public List<HTDMallTypeSubDTO> selectByTypeId(Long typeId);
    
    public int deleteByTypeId(@Param("typeId")Long typeId);

}