package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;

public interface SellerMeetingInfoDAO {
	int deleteByPrimaryKey(Long meetingId);

	int insert(SellerMeetingInfoDTO record);

	int insertSelective(SellerMeetingInfoDTO record);

	SellerMeetingInfoDTO selectByPrimaryKey(Long meetingId);

	int updateByPrimaryKeySelective(SellerMeetingInfoDTO record);

	int updateByPrimaryKey(SellerMeetingInfoDTO record);

	SellerMeetingInfoDTO getMeetingByNo(@Param("meetingNo") String meetingNo);

	/**
	 * @param sellerCode
	 * @return
	 */
	Long selectSellerMeetingInfoListCount(@Param("sellerId") Long sellerId);

	/**
	 * @param page
	 * @param sellerCode
	 * @return
	 */
	List<SellerMeetingInfoDTO> selectSellerMeetingInfoList(@Param("page") Pager page, @Param("sellerId") Long sellerId);

	/**
	 * @param meetingNo
	 * @return
	 */
	SellerMeetingInfoDTO selectSellerMeetingInfo(String meetingNo);

	/**
	 * 
	 * @return
	 */
	public String getMeetingCode();
}