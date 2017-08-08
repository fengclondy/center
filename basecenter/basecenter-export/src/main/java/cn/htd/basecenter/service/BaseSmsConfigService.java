package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.common.ExecuteResult;

public interface BaseSmsConfigService {

	/**
	 * 启用短信配置信息
	 */
	public ExecuteResult<String> updateSMSConfigValid(ValidSmsConfigDTO inDTO);
}
