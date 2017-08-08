package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallInfo;
import com.bjucloud.contentcenter.dto.LogoDTO;


public interface MallInfoDAO extends BaseDAO<MallInfo> {
	/**
	 * 
	 * <p>
	 * Discription:[查询所有的logo记录]
	 * </p>
	 */
	public List<LogoDTO> findAll();

	/**
	 * 
	 * <p>
	 * Discription:[Logo修改,根据参数执行全部数据修改，mall_info表中只会有一条数据]
	 * </p>
	 * 
	 * @param logoName
	 * @param picUrl
	 */
	public Integer updateAll(@Param("logoName") String logoName, @Param("picUrl") String picUrl);
}
