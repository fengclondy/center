package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;
import com.bjucloud.contentcenter.dto.SiteSloganDTO;

import java.util.List;

public interface SiteSloganDAO extends BaseDAO<SiteSloganDTO>{
    /**
     *
     * <p>
     * Discription:[查询所有的网站Slogan记录]
     * </p>
     */
    public List<SiteSloganDTO> findAll();
}