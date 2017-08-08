package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;

public interface ShopModifyDetailDAO extends BaseDAO<ShopModifyDetailDTO> {

	List<ShopModifyDetailDTO> selectListById(Long id);

	/**
	 *
	 * <p>
	 * Discription:[查询店铺信息修改详细表 ]
	 * </p>
	 *
	 * @param shopIds
	 * @return
	 */
	List<ShopModifyDetailDTO> selectByIds(@Param("shopIds") Long[] shopIds);

}