package com.bjucloud.contentcenter.dao;


import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.SubContentSubDTO;

import java.util.List;

public interface SubContentSubDAO extends BaseDAO<SubContentSubDTO> {

    /**
     * 根据城市站内容信息ID查询城市站内容子信息
     * @param subcontentId
     * @return
     */
    List<SubContentSubDTO> selectBySubcontentId(Long subcontentId);

    /**
     * 根据城市站内容信息ID更新城市站内容信息子表
     * @param dto
     * @return
     */
    Integer updateByContentId(SubContentSubDTO dto);

    /**
     * 根据供应商编码删除供应商
     * @param sellerCode
     * @return
     */
    Integer deleteBySellerCode(String sellerCode);

    /**
     * 根据城市站内容信息ID 删除所有对应供应商信息
     * @param subContentId
     * @return
     */
    Integer deleteBySubContentId(Long subContentId);

    /**
     * 根据主键更新
     * @param subContentSubDTO
     * @return
     */
    Integer updateByPrimaryKeySelective(SubContentSubDTO subContentSubDTO);
}