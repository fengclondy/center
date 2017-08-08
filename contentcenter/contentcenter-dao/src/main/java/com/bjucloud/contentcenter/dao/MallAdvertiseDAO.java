package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallAdvertise;
import com.bjucloud.contentcenter.dto.MallAdDTO;
import com.bjucloud.contentcenter.dto.MallAdQueryDTO;

public interface MallAdvertiseDAO extends BaseDAO<MallAdvertise> {

	public List<MallAdDTO> queryPage(@Param("entity") MallAdQueryDTO mallAdvertise, @Param("page") Pager page);

	public void modifyMallAdStatus(@Param("id") Long id, @Param("status") String status);

	public Long queryCount(@Param("entity") MallAdQueryDTO mallAdQueryDTO);

	public List<MallAdDTO> queryList(@Param("entity") MallAdDTO mallAdQueryDTO, @Param("page") Pager page);

	public MallAdDTO queryToById(Long id);

	public Long queryMobileShopAdCount(@Param("entity") MallAdQueryDTO mallAdQueryDTO);

	public List<MallAdDTO> queryMobileShopAdList(@Param("page")Pager pager,@Param("entity")  MallAdQueryDTO mallAdQueryDTO);

	public MallAdDTO queryMobileShopAdById(Long id);
}
