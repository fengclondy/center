package cn.htd.basecenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.basecenter.dto.BaseDictionaryDTO;
import cn.htd.basecenter.dto.DictionaryInfo;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
 * Description: [字典表
 * 
 */
public interface BaseDictionaryService {

	/**
	 * 根据字典值取得字典名称(页面处理用)
	 */
	public String getNameByValue(String parentCode, String value) throws Exception;

	/**
	 * 根据字典值取得字典编码(页面处理用)
	 */
	public String getCodeByValue(String parentCode, String value) throws Exception;

	/**
	 * 根据字典编码取得字典值(页面处理用)
	 */
	public String getValueByCode(String parentCode, String code) throws Exception;

	/**
	 * 根据上级编码查询字典信息列表(页面处理用)
	 */
	public List<DictionaryInfo> getDictionaryList(String parentCode) throws Exception;

	/**
	 * 按id查询字典
	 */
	public ExecuteResult<BaseDictionaryDTO> queryBaseDictionaryById(Long id);

	/**
	 * 按条件查询字典
	 */
	public DataGrid<BaseDictionaryDTO> queryBaseDictionaryListByCondition(BaseDictionaryDTO dictionaryDTO,
			Pager<BaseDictionaryDTO> pager);

	/**
	 * 增加字典
	 */
	public ExecuteResult<BaseDictionaryDTO> addBaseDictionary(BaseDictionaryDTO dictionaryDTO);

	/**
	 * 修改字典
	 */
	public ExecuteResult<BaseDictionaryDTO> updateBaseDictionary(BaseDictionaryDTO dictionaryDTO);

	/**
	 * 启用字典
	 */
	public ExecuteResult<BaseDictionaryDTO> enableBaseDictionary(BaseDictionaryDTO dictionaryDTO);

	/**
	 * 禁用字典
	 */
	public ExecuteResult<BaseDictionaryDTO> disableBaseDictionary(BaseDictionaryDTO dictionaryDTO);
	
	/**
	 * 新增商品单位
	 * 
	 * @param unitName
	 * @param createName
	 * @param createTime
	 * @return
	 */
	public ExecuteResult<String> addSingleItemUnit(String unitName,Long createId, String createName);

}
