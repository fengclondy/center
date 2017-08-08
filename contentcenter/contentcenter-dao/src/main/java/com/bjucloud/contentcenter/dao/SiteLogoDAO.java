package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;

import java.util.List;

public interface SiteLogoDAO extends BaseDAO<SiteLogoDTO>{
    /**
     *
     * <p>
     * Discription:[查询所有的logo记录]
     * </p>
     */
    public List<SiteLogoDTO> findAll();
}