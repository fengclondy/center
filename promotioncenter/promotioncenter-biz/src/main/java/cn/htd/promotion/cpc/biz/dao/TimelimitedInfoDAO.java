package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.timelimitedInfoDAO")
public interface TimelimitedInfoDAO {
	
    int insert(TimelimitedInfoReqDTO timelimitedInfoReqDTO);

    int insertSelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
    int updateByPrimaryKey(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
	
    int updateByPrimaryKeySelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
    int deleteByPrimaryKey(Long timelimitedId);

    TimelimitedInfoResDTO selectByPrimaryKey(Long timelimitedId);


    
    
    
}