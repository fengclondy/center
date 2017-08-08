package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallRec;
import com.bjucloud.contentcenter.dto.MallRecDTO;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:楼层数据交互接口]
 * </p>
 */
public interface MallRecMybatisDAO extends BaseDAO<MallRec> {
	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:楼层的上下架 根据id ]
	 * </p>
	 */
	public void modifyMallRecStatus(@Param("id") Long id, @Param("status") String publishFlag);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:查询楼层的列表 ]
	 * </p>
	 */
	public List<MallRecDTO> queryDTOList(@Param("entity") MallRecDTO mallRecDTO, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:根据id查询楼层列表的详情]
	 * </p>
	 */
	public MallRecDTO queryDTOById(@Param("idDTO") Long idDTO);

	public Long queryCount(@Param("entity") MallRecDTO mallRecDTO);

	public Integer update(@Param("entity") MallRecDTO mallRecDTO);

	public void add(@Param("entity") MallRecDTO mallRecDTO);
}