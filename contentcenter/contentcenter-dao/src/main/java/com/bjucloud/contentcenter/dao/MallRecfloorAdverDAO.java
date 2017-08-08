package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallRecfloorAdver;
import com.bjucloud.contentcenter.dto.MallRecfloorAdverDTO;

public interface MallRecfloorAdverDAO extends BaseDAO<MallRecfloorAdver> {

	public List<MallRecfloorAdverDTO> queryListDTO (@Param("entity") MallRecfloorAdverDTO mallRecfloorAdverDTO, @Param("publishFlag") String publishFlag, @Param("page") Pager page);
	
	public Long queryCountDTO(@Param("entity")MallRecfloorAdverDTO mallRecfloorAdverDTO,@Param("publishFlag") String publishFlag);

	public Integer updateStatusById(@Param("id")Integer id,@Param("publishFlag") String publishFlag);
}
