package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.timelimitedInfoDAO")
public interface TimelimitedInfoDAO {
	
    int insert(TimelimitedInfoReqDTO timelimitedInfoReqDTO);

    int insertSelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
    int updateByPrimaryKey(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
	
    int updateByPrimaryKeySelective(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
    int updateTimelimitedInfoByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO);
    
    int deleteByPrimaryKey(Long timelimitedId);

    TimelimitedInfoResDTO selectByPrimaryKey(Long timelimitedId);
    
    TimelimitedInfoResDTO selectByPromotionId(String promotionId);
    
    
	/**
	 * 根据条件查询秒杀活动总数
	 * @param timelimitedInfoReqDTO
	 * @return
	 */
	int getTimelimitedInfosCount( @Param("dto")TimelimitedInfoReqDTO timelimitedInfoReqDTO);
	
	/**
	 * 分页查询秒杀活动
	 * @param page
	 * @param timelimitedInfoReqDTO
	 * @return
	 */
	List<TimelimitedInfoResDTO> getTimelimitedInfosForPage(@Param("page")Pager<TimelimitedInfoReqDTO> page, @Param("dto")TimelimitedInfoReqDTO timelimitedInfoReqDTO);
	
	/**
	 * 根据条件查询所有秒杀活动
	 * @return
	 */
	List<TimelimitedInfoResDTO> getTimelimitedInfosByParams( @Param("dto")TimelimitedInfoReqDTO timelimitedInfoReqDTO);


    
    
    
}