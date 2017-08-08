package cn.htd.basecenter.service;

import java.util.List;

import cn.htd.basecenter.dto.BaseTypeDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [类型表]
 * </p>
 */
public interface BaseTypeService {

	/**
	 * 查询类型列表
	 */
	public List<BaseTypeDTO> queryBaseTypeList();

	/**
	 * 根据条件查询类型列表
	 */
	public DataGrid<BaseTypeDTO> queryBaseTypeList(BaseTypeDTO typeDTO, Pager<BaseTypeDTO> pager);

	/**
	 * 根据ID查询类型数据
	 */
	public ExecuteResult<BaseTypeDTO> queryBaseTypeById(Long id);

	/**
	 * 新增类型数据
	 */
	public ExecuteResult<BaseTypeDTO> addBaseType(BaseTypeDTO typeDTO);

	/**
	 * 修改类型数据
	 */
	public ExecuteResult<BaseTypeDTO> updateBaseType(BaseTypeDTO typeDTO);

	/**
	 * 启用类型数据
	 */
	public ExecuteResult<BaseTypeDTO> enableBaseType(BaseTypeDTO typeDTO);

	/**
	 * 禁用类型数据
	 */
	public ExecuteResult<BaseTypeDTO> disableBaseType(BaseTypeDTO typeDTO);
}
