package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.SpecialResourceDTO;

/**
 * Created by thinkpad on 2017/2/13.
 */
public interface SpecialResourceDAO extends BaseDAO<SpecialResourceDTO> {

	SpecialResourceDTO queryByLink(@Param("topicCode")String topicCode);
}
