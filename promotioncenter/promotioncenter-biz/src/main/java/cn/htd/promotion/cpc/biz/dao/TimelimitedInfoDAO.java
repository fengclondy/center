package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;


public interface TimelimitedInfoDAO {
	
    int deleteByPrimaryKey(Long timelimitedId);

    int insert(TimelimitedInfoReqDTO timelimitedInfoReqDTO);

    int insertSelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);

    TimelimitedInfoDMO selectByPrimaryKey(Long timelimitedId);

    int updateByPrimaryKeySelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);

    int updateByPrimaryKey(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
}