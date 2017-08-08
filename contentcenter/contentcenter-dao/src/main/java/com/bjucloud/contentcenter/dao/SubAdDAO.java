package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.SubAd;
import com.bjucloud.contentcenter.dto.QuerySubAdOutDTO;
import com.bjucloud.contentcenter.dto.SubAdDTO;
import org.apache.ibatis.annotations.Param;

public interface SubAdDAO extends BaseDAO<SubAdDTO> {

    SubAdDTO selectBySubAdId(Long subAdId);

    SubAdDTO selectByAdId(Long id);

    Integer updateStatusBySubId(@Param("status")String status,@Param("subId")String subId);
}