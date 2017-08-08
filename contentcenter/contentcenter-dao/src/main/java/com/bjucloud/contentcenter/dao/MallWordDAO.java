package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.MallWordDTO;

public interface MallWordDAO {

	public void add(MallWordDTO dto);

	public void delete(@Param("id") Long id);

	public Long queryCount(@Param("entity") MallWordDTO dto);

	@SuppressWarnings("rawtypes")
	public List<MallWordDTO> queryList(@Param("entity") MallWordDTO dto, @Param("page") Pager page);
}
