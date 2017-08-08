package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemAttrValueItemDTO;

public interface ItemAttrValueItemDAO extends BaseDAO<ItemAttrValueItemDTO> {

	/**
	 * 
	 * <p>
	 * Discription:[根据商品ID修改状态为2删除]
	 * </p>
	 */
	void updatestatus(Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[根据属性值ID修改状态为2删除]
	 * </p>
	 */
	void updatestatusbyValueId(Long valueId);

	/**
	 * 
	 * <p>
	 * Discription:[查询属性]
	 * </p>
	 */
	List<ItemAttrDTO> queryAttrList(@Param("entity") ItemAttrValueItemDTO itemAttrValueItemDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询属性值]
	 * </p>
	 */
	List<ItemAttrValueDTO> queryValueList(@Param("entity") ItemAttrValueItemDTO itemAttrValueItemDTO);

}
