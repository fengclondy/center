package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallRecommNav;
import com.bjucloud.contentcenter.dto.MallRecommNavDTO;
import com.bjucloud.contentcenter.dto.MallRecommNavECMDTO;


public interface MallRecommNavDAO extends BaseDAO<MallRecommNav> {

	public List<MallRecommNavDTO> queryListDTO(@Param("nowTime") String nowTime,
			@Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public List<MallRecommNavDTO> queryListDTOToAdmin(@Param("entity") MallRecommNavDTO mallRecommNavDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountDTOToAdmin(@Param("entity") MallRecommNavDTO mallRecommNavDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public Integer updateStatusById(@Param("id") Integer id, @Param("publishFlag") String publishFlag);

	public Integer updateSortNumberById(@Param("id") Integer id, @Param("sortNum") Integer sortNum);

	public List<MallRecommNavECMDTO> equeryListDTOToAdmin(@Param("entity") MallRecommNavECMDTO mallRecommNavECMDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountECMDTOToAdmin(@Param("entity") MallRecommNavECMDTO mallRecommNavECMDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public MallRecommNavECMDTO queryRecNavECMById(@Param("id") Integer id);
}
