package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.Notice;
import com.bjucloud.contentcenter.dto.MallNoticeDTO;

public interface NoticeMybatisDAO extends BaseDAO<Notice> {

	public MallNoticeDTO queryDTOById(Object id);

	public List<MallNoticeDTO> queryListDTO(@Param("entity") MallNoticeDTO notice, @Param("page") Pager page);

	// 根据条件查询公告得最大排序号
	public Long getSortNumByCondation(@Param("entity") MallNoticeDTO notice);

	public List<MallNoticeDTO> queryListByNextSort(@Param("entity") MallNoticeDTO notice, @Param("page") Pager page);

	public Long queryCount(@Param("entity") MallNoticeDTO notice, @Param("page") Pager page);

	public Integer updateDto(@Param("entity") MallNoticeDTO notice);
}
