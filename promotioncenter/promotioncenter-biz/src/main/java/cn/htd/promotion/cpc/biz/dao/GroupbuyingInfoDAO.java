package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;

public interface GroupbuyingInfoDAO {
	
    int deleteByPrimaryKey(Long groupbuyingId);

    int insert(GroupbuyingInfoReqDTO record);

    int insertSelective(GroupbuyingInfoReqDTO record);

    GroupbuyingInfoResDTO selectByPrimaryKey(Long groupbuyingId);

    int updateByPrimaryKeySelective(GroupbuyingInfoReqDTO record);

    int updateByPrimaryKey(GroupbuyingInfoReqDTO record);
    
}