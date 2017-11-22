package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.BaseSmsConfigShowDTO;
import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public interface BaseSmsConfigService {

    /**
     * 查询短信通道的启用列表
     */
    public ExecuteResult<DataGrid<BaseSmsConfigShowDTO>> querySMSConfigStatusList(Pager<BaseSmsConfigDTO> pager);

    /**
     * 启用短信配置信息
     */
    public ExecuteResult<String> updateSMSConfigValid(ValidSmsConfigDTO inDTO);
}
