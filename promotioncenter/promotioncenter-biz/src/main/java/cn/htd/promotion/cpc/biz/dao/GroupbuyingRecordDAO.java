package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;

public interface GroupbuyingRecordDAO {
	
    int deleteByPrimaryKey(Long recordId);

    int insert(GroupbuyingRecordReqDTO record);

    int insertSelective(GroupbuyingRecordReqDTO record);

    GroupbuyingRecordResDTO selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(GroupbuyingRecordReqDTO record);

    int updateByPrimaryKey(GroupbuyingRecordReqDTO record);
}