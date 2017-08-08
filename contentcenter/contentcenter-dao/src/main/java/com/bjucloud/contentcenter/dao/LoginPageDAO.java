package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.LoginPageDTO;

import java.util.List;

/**
 * Created by peanut on 2017/2/13.
 */
public interface LoginPageDAO extends BaseDAO<LoginPageDTO>{

    /**
     *
     * <p>
     * Discription:[查询所有的LoginPage记录]
     * </p>
     */
    public List<LoginPageDTO> findAll();
}
