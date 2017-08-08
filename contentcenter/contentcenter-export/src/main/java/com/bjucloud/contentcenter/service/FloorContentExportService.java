package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.FloorContentDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;

/**
 * Created by thinkpad on 2017/2/8.
 */
public interface FloorContentExportService {

    /**
     * 根据导航ID查询记录
     * */
    public ExecuteResult<FloorContentDTO> queryByNavId(Long navId);

    /**
     * 更新或保存记录
     * */
    ExecuteResult<String> modifyFloorNavPic(HTDFloorNavDTO htdFloorNavDTO);

}
