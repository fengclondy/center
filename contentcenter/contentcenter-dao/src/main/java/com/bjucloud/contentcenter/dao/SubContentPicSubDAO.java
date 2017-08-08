package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.SubContentPicSubDTO;

import java.util.List;

public interface SubContentPicSubDAO extends BaseDAO<SubContentPicSubDTO> {

    /**
     * 根据城市站内容信息ID查询
     * @param subcontentId
     * @return
     */
    List<SubContentPicSubDTO> selectBySubcontentId(Long subcontentId);

    /**
     * 根据城市站内容信息ID删除城市站图片信息
     * @param subContentId
     * @return
     */
    Integer deleteBySubContentId(Long subContentId);

    Integer updateByPrimaryKeySelective(SubContentPicSubDTO subContentPicSubDTO);
}