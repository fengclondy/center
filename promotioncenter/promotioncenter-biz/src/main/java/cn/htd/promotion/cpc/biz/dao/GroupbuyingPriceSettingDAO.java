package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.GroupbuyingPriceSettingReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingPriceSettingResDTO;

public interface GroupbuyingPriceSettingDAO {
	
    int deleteByPrimaryKey(Long priceSettingId);

    int insert(GroupbuyingPriceSettingReqDTO record);

    int insertSelective(GroupbuyingPriceSettingReqDTO record);

    GroupbuyingPriceSettingResDTO selectByPrimaryKey(Long priceSettingId);

    int updateByPrimaryKeySelective(GroupbuyingPriceSettingReqDTO record);

    int updateByPrimaryKey(GroupbuyingPriceSettingReqDTO record);
    
    void pseudoDelete(GroupbuyingPriceSettingReqDTO record);
}