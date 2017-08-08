package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallTheme;
import com.bjucloud.contentcenter.dto.MallThemeDTO;

public interface MallThemeDAO extends BaseDAO<MallTheme> {

	public List<MallThemeDTO> queryListDTO(@Param("entity") MallThemeDTO mallThemeDTO, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public MallThemeDTO queryById(@Param("id") long id);

	public void add(@Param("entity") MallThemeDTO mallThemeDTO);

	public Integer delete(@Param("id") Long id);

	public Integer updateStatusById(@Param("id") Long id, @Param("publishFlag") String status);

	public long queryListCountDTO(@Param("entity") MallThemeDTO mallThemeDTO, @Param("publishFlag") String publishFlag);

}
