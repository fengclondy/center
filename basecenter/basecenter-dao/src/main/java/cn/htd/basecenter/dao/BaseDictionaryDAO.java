package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.BaseDictionary;
import cn.htd.basecenter.dto.BaseDictionaryDTO;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseDictionaryDAO extends BaseDAO<BaseDictionary> {

	/**
	 * 查询字典信息列表
	 * 
	 * @param baseDictionaryDTO
	 * @return
	 */
	public List<BaseDictionary> queryBaseDictionaryList(BaseDictionaryDTO baseDictionaryDTO);

	/**
	 * 检查输入字典信息的编码和值是否唯一
	 * 
	 * @param baseDictionaryDTO
	 * @return
	 */
	public long checkBaseDictionaryUniqueness(BaseDictionaryDTO baseDictionaryDTO);

	/**
	 * 根据编码查询字典信息
	 * 
	 * @param baseDictionaryDTO
	 * @return
	 */
	public BaseDictionary queryBaseDictionaryByCode(BaseDictionaryDTO baseDictionaryDTO);

	/**
	 * 查询定时任务处理字典列表
	 * 
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<BaseDictionary> queryDictionaryList4Task(@Param("entity") BaseDictionary condition,
			@Param("page") Pager<BaseDictionary> page);

	/**
	 * 更新地址上传Redis状态
	 * 
	 * @param ids
	 */
	public void updateRedisFlagById(List<Long> ids);
	
	/**
	 * 根据名字查询商品单位
	 * 
	 * @param name
	 * @return
	 */
	public BaseDictionary queryItemUnitByName(String name);
	
	/**
	 * 查询ID最大的商品单位
	 * 
	 * @return
	 */
	public BaseDictionary queryMaxItemUnit();
	
	public BaseDictionary queryByValue(String v);
}
