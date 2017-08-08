package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallNav;
import com.bjucloud.contentcenter.dto.MallNavDTO;

public interface MallNavDAO extends BaseDAO<MallNav> {
	public List<MallNavDTO> queryListDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag,
			@Param("page") Pager page);

	public Long queryCountDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public List<MallNavDTO> queryListDTOToAdmin(@Param("entity") MallNavDTO mallNavDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountDTOToAdmin(@Param("entity") MallNavDTO mallNavDTO, @Param("nowTime") String nowTime,
			@Param("publishFlag") String publishFlag);

	public Integer updateStatusById(@Param("id") Long id, @Param("publishFlag") String publishFlag);

	public Integer updateSortNumberById(@Param("id") Long id, @Param("sortNum") Integer sortNum);

	public MallNavDTO queryById(@Param("id") Long id);
}
