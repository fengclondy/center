package cn.htd.storecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopRenovationDTO;

public interface ShopRenovationDAO extends BaseDAO<ShopRenovationDTO> {

	void deleteTid(ShopRenovationDTO shopRenovationDTO);

}
