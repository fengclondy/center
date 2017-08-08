package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallSubTab;
import com.bjucloud.contentcenter.dto.MallSubTabDTO;
import com.bjucloud.contentcenter.dto.MallSubTabECMDTO;


public interface MallSubTabDAO extends BaseDAO<MallSubTab> {

	public List<MallSubTabDTO> queryMallSubTabPage(@Param("page") Pager pager, @Param("entity") MallSubTab mallSubTab);

	public Long queryMallSubCount(@Param("entity") MallSubTab mllSubTab);

	public void updateStatus(@Param("status") String status, @Param("id") Long id);

	public void deleteMallSub(@Param("id") Long id);

	public List<MallSubTabECMDTO> equeryMallSubTabPage(@Param("page") Pager pager, @Param("entity") MallSubTabECMDTO mallSubTabECMDTO);

	public Long equeryMallSubCount(@Param("entity") MallSubTabECMDTO mallSubTabECMDTO);

	public MallSubTabECMDTO queryByIdECM(@Param("id") Long id);
}
