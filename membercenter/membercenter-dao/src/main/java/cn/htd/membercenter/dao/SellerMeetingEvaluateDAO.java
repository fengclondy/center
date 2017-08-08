package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.SellerMeetingEvaluateDTO;

public interface SellerMeetingEvaluateDAO {
	int deleteByPrimaryKey(Long id);

	int insert(SellerMeetingEvaluateDTO record);

	int insertSelective(SellerMeetingEvaluateDTO record);

	SellerMeetingEvaluateDTO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SellerMeetingEvaluateDTO record);

	int updateByPrimaryKey(SellerMeetingEvaluateDTO record);

	Long getCountByMemberCodeAndMeetingNo(@Param("memberCode") String memberCode, @Param("meetingNo") String meetingNo);

}