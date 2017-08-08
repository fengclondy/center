package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallIconSer;
import com.bjucloud.contentcenter.dto.MallIconSerDTO;

public interface MallIconSerDAO extends BaseDAO<MallIconSer> {
	public List<MallIconSerDTO> queryListDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag,
			@Param("page") Pager page);

	public Long queryCountDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public List<MallIconSerDTO> queryListDTOToAdmin(@Param("entity") MallIconSerDTO mallIconSerDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountDTOToAdmin(@Param("entity") MallIconSerDTO mallIconSerDTO, @Param("nowTime") String nowTime,
			@Param("publishFlag") String publishFlag);

	public Integer updateStatusById(@Param("id") Integer id, @Param("publishFlag") String publishFlag);

	public Integer updateSortNumberById(@Param("id") Integer id, @Param("sortNum") Integer sortNum);

	public List<MallIconSerDTO> queryMallIconSerByTitle(@Param("title") String title, @Param("id") Integer id);

}
