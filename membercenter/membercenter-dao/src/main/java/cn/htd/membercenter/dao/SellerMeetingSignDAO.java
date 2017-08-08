package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.SellerMeetingSignDTO;

public interface SellerMeetingSignDAO {
	int deleteByPrimaryKey(Long id);

	int insert(SellerMeetingSignDTO record);

	int insertSelective(SellerMeetingSignDTO record);

	SellerMeetingSignDTO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SellerMeetingSignDTO record);

	int updateByPrimaryKey(SellerMeetingSignDTO record);

	Long getCountByMemberCodeAndMeetingNo(@Param("memberCode") String memberCode, @Param("meetingNo") String meetingNo);
}