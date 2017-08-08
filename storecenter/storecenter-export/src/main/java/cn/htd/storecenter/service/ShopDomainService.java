package cn.htd.storecenter.service;

import cn.htd.common.ExecuteResult;

/**
 * 
 * <p>
 * Description: [店铺自定义域名]
 * </p>
 */
public interface ShopDomainService {

	/**
	 * 
	 * <p>
	 * Discription:[校验店铺域名是否重复]
	 * </p>
	 */
	public ExecuteResult<Boolean> existShopUrl(String shopUrl);

}
