package com.bjucloud.contentcenter.service;


import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallDocumentDTO;
import com.bjucloud.contentcenter.dto.MallTypeDTO;

import java.util.List;

/**
 * 供客户端调用的远程接口 帮助文档列表接口
 * 
 */
public interface MallDocumentService {
	/**
	 * <p>
	 * 分页查询帮助文档列表信息
	 * </p>
	 */
	public DataGrid<MallDocumentDTO> queryMallDocumentList(MallDocumentDTO mallDocumentDTO, Pager pager);

	/**
	 * <p>
	 * 根据id查询帮助文档信息
	 * </p>
	 */
	public MallDocumentDTO getMallDocumentById(Long id);

	/**
	 * <p>
	 * 帮助文档添加功能
	 * </p>
	 */
	public ExecuteResult<String> addMallDocument(MallDocumentDTO mallDocumentDTO);

	/**
	 * <p>
	 * 通过id修改帮助文档内容
	 * </p>
	 */
	public ExecuteResult<String> modifyInfoById(MallDocumentDTO mallDocumentDTO);

	/**
	 * <p>
	 * 通过id修改帮助文档状态
	 * </p>
	 */
	public ExecuteResult<String> modifyStatusById(int id, int status);

	/**
	 * <p>
	 * 根据类型获取分类
	 * </p>
	 */
	public List<MallTypeDTO> queryMallDocumentListByType(String type);

	/**
	 * 根据id删除文档
	 */
	public ExecuteResult<String> delById(Long id);
}
