package cn.htd.goodscenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;

import java.util.List;
import java.util.Map;

public interface ItemAttributeValueDAO extends BaseDAO<ItemAttrValueDTO> {

	/**
	 * <p>
	 * Discription:[根据商品属性删除商品的属性值]
	 * </p>
	 */
	public void deleteByAttrId(Long attr_id);

	/**
	 * <p>
	 * Discription:[通过id查询属性值名称]
	 * </p>
	 */
	ItemAttrValueDTO queryById(@Param("id") Long id);

	List<ItemAttrValueDTO> selectItemAttributeValueListTask(Map<String, Object> map);

	List<ItemAttrValueDTO> selectByAttrId(Long attrId);
}
