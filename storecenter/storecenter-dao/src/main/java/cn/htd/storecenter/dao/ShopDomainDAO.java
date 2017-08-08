package cn.htd.storecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopDomainDTO;

public interface ShopDomainDAO extends BaseDAO<ShopDomainDTO> {

	public Long existShopUrl(String shopUrl);

}