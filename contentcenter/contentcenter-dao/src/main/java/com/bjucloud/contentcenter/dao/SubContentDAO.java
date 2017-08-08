package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.SubContent;
import com.bjucloud.contentcenter.dto.SubContentDTO;

public interface SubContentDAO extends BaseDAO<SubContentDTO> {

    /**
     * 根据城市站广告位ID查询城市站内容信息
     * @param subAdId
     * @return
     */
    public SubContentDTO selectBySubAdId(Long subAdId);

    public Integer updateByContentId(SubContentDTO dto);
}