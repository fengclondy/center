package com.bjucloud.contentcenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.HTDMallTypeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallTypeDAO extends BaseDAO<HTDMallTypeDTO>{
    public List<HTDMallTypeDTO> selectAll(@Param("page") Pager page);

    public Long selectAllCount();

    List<HTDMallTypeDTO> selectByName(String name);
}