package cn.htd.promotion.cpc.biz.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;

public interface GroupbuyingRecordDAO {
	
    int deleteByPrimaryKey(Long recordId);

    int insert(GroupbuyingRecordReqDTO record);

    int insertSelective(GroupbuyingRecordReqDTO record);
    
    int addGroupbuyingRecord(GroupbuyingRecordReqDTO record);

    GroupbuyingRecordResDTO selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(GroupbuyingRecordReqDTO record);

    int updateByPrimaryKey(GroupbuyingRecordReqDTO record);
    
    /**
     * 根据条件查询参团记录
     * @param groupbuyingRecordReqDTO
     * @return
     */
    GroupbuyingRecordResDTO getGroupbuyingRecordByParams(@Param("dto") GroupbuyingRecordReqDTO groupbuyingRecordReqDTO);
	/**
	 * 根据条件查询参团记录总数
	 * @param record
	 * @return
	 */
	int getGroupbuyingRecordCount( @Param("dto")GroupbuyingRecordReqDTO record);
	
	/**
	 * 分页查询参团记录
	 * @param page
	 * @param record
	 * @return
	 */
	List<GroupbuyingRecordResDTO> getGroupbuyingRecordForPage(@Param("page")Pager<GroupbuyingRecordReqDTO> page, @Param("dto")GroupbuyingRecordReqDTO record);
	
	
}