package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallRecommendAttr;
import com.bjucloud.contentcenter.dto.MallRecAttrDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrECMDTO;

public interface MallRecommendAttrDAO extends BaseDAO<MallRecommendAttr> {

	public List<MallRecAttrDTO> queryPage(@Param("page") Pager page, @Param("entity") MallRecommendAttr mra);

	public void modifyMallRecAttrStatus(@Param("id") Long id, @Param("status") String status);

	public List<MallRecAttrECMDTO> equeryPage(@Param("page") Pager page, @Param("entity") MallRecAttrECMDTO mallRecAttrECMDTO);

	public Long equeryCount(@Param("entity") MallRecAttrECMDTO mallRecAttrECMDTO);
}
