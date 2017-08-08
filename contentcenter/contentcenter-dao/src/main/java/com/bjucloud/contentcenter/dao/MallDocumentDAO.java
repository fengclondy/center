package com.bjucloud.contentcenter.dao;


import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.MallDocument;
import com.bjucloud.contentcenter.dto.MallDocumentDTO;
import com.bjucloud.contentcenter.dto.MallTypeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallDocumentDAO extends BaseDAO<MallDocument> {
	/**
	 * 
	 * <p>
	 * Discription:[帮助文档列表查询]
	 * </p>
	 */
	public List<MallDocumentDTO> queryMallDocumentList(@Param("entity") MallDocumentDTO entity, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档详情查询]
	 * </p>
	 */
	public MallDocumentDTO getMallDocumentById(Object id);

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档数量查询]
	 * </p>
	 */
	public Long queryMallDocumentCount(@Param("entity") MallDocumentDTO entity);

	/**
	 * <p>
	 * 根据类型获取分类
	 * </p>
	 */
	public List<MallTypeDTO> queryMallDocumentListByType(Integer type);
}
