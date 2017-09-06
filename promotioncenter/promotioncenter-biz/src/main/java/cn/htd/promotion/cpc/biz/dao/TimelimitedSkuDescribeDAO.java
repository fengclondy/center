package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuDescribeResDTO;

public interface TimelimitedSkuDescribeDAO {

    int insert(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int insertSelective(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int updateByPrimaryKeySelective(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);

    int updateByPrimaryKey(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO);
    
    int pseudoDelete(TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO_delete);
    
    int deleteByPrimaryKey(Long descId);
    
    TimelimitedSkuDescribeResDTO selectByPrimaryKey(Long descId);
    
    List<TimelimitedSkuDescribeResDTO> selectByPromotionId(String promotionId);
    
    
}