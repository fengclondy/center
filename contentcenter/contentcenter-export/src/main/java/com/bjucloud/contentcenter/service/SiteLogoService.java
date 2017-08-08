package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;

/**
 * 
 * <p>
 * Description: [网站logo]
 * </p>
 */
public interface SiteLogoService {

	/**
	 *
	 * <p>Discription:[Logo查询，查询出来只有一条数据，表中只会有一条数据，]</p>
	 */
	public ExecuteResult<SiteLogoDTO> getLogo();

	/**
	 * 新增网址logo,[表中只会有一条数据]
	 * @param record
	 * @return
     */
	public ExecuteResult<String> save(SiteLogoDTO record);

}
