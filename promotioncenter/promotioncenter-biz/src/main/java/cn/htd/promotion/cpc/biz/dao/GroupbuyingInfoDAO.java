package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;

public interface GroupbuyingInfoDAO {
	
    int deleteByPrimaryKey(Long groupbuyingId);

    int insert(GroupbuyingInfoReqDTO record);

    int insertSelective(GroupbuyingInfoReqDTO record);

    GroupbuyingInfoResDTO selectByPrimaryKey(Long groupbuyingId);
    
    GroupbuyingInfoResDTO selectByPromotionId(String promotionId);

    int updateByPrimaryKeySelective(GroupbuyingInfoReqDTO record);

    int updateByPrimaryKey(GroupbuyingInfoReqDTO record);
    
    int updateGroupbuyingInfo(GroupbuyingInfoReqDTO record);
    
}