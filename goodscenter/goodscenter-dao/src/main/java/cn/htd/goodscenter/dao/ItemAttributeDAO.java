package cn.htd.goodscenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ItemAttrDTO;

import java.util.List;
import java.util.Map;

public interface ItemAttributeDAO extends BaseDAO<ItemAttrDTO> {
	/**
	 * <p>
	 * Discription:[通过Id查询属性名称]
	 * </p>
	 */
	ItemAttrDTO queryById(@Param("id") Long id);


	List<ItemAttrDTO> selectItemAttributeListTask(Map<String, Object> map);

	public void delteItemAttrByAttrId(@Param("attrId") Long attrId, @Param("cid") Long cid,@Param("shopId")Long shopId);
}
