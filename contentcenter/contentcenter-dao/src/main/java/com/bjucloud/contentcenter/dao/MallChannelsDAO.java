package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.MallChannelsDTO;

public interface MallChannelsDAO extends BaseDAO<MallChannelsDTO>{

	public int  updateSortNumZero(@Param("sortNum")Long sortNum);

}