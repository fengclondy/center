package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;
import com.bjucloud.contentcenter.dto.SiteSloganDTO;

/**
 * 
 * <p>
 * Description: [网站Slogan]
 * </p>
 */
public interface SiteSloganService {

	/**
	 *
	 * <p>Discription:[Logo查询，查询出来只有一条数据，表中只会有一条数据，]</p>
	 */
	public ExecuteResult<SiteSloganDTO> getSlogan();

	/**
	 * 新增网站Slogan,[表中只会有一条数据]
	 * @param record
	 * @return
     */
	public ExecuteResult<String> save(SiteSloganDTO record);

}
