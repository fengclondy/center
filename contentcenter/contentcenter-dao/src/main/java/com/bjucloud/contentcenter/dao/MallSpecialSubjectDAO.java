package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallSpecialSubject;
import com.bjucloud.contentcenter.dto.MallSpecialSubjectDTO;

public interface MallSpecialSubjectDAO extends BaseDAO<MallSpecialSubjectDTO> {

	public List<MallSpecialSubjectDTO> querySubjectList(@Param("page") Pager page, @Param("entity") MallSpecialSubjectDTO mallSpecialSubjectDTO);
	
	public long querySubjectCount(@Param("entity") MallSpecialSubjectDTO mallSpecialSubjectDTO);

	public MallSpecialSubjectDTO getSpecialSubjectById(@Param("id") Long id);
	
}
