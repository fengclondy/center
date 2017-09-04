package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuDescribeResDTO;

public interface TimelimitedSkuDescribeDAO {

    int insert(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int insertSelective(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int updateByPrimaryKeySelective(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int updateByPrimaryKeyWithBLOBs(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int updateByPrimaryKey(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);
    
    int deleteByPrimaryKey(Long descId);
    
    TimelimitedSkuDescribeResDTO selectByPrimaryKey(Long descId);
    
    TimelimitedSkuDescribeResDTO selectByPromotionId(String promotionId);
    
    
}