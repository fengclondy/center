package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.LogoDTO;
/**
 * 
 * <p>Description: [Logo服务接口]</p>
 */
public interface LogoExportService {
	/**
	 * 
	 * <p>Discription:[Logo查询，查询出来只有一条数据，mall_info表中只会有一条数据，]</p>
	 */
	public ExecuteResult<LogoDTO> getMallLogo();

	/**
	 * 
	 * <p>Discription:[Logo修改,根据参数执行全部数据字段修改sql，mall_info表中只会有一条数据]</p>
	 * @param logoName logo名称
	 * @param picUrl logo地址
	 */
	public ExecuteResult<String> modifyMallLogo(String logoName, String picUrl);
	
}
