package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.HotWordDTO;

import java.util.List;

public interface HotWordDAO extends BaseDAO<HotWordDTO>{

    List<HotWordDTO> selectByName(String name);

    List<HotWordDTO> selectBySortNum(Long sortNum);

}