package cn.htd.storecenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopAuditDTO;

public interface ShopAuditService {

	/**
	 * 
	 * <p>
	 * Discription:[店铺审核信息详情查询查询]
	 * </p>
	 */
	public ExecuteResult<ShopAuditDTO> queryShopAuditInfo(Long shopId);

}
