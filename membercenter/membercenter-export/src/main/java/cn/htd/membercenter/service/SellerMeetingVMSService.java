package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;

/**
 * 会议forVMS
 * 
 * @author Administrator
 *
 */
public interface SellerMeetingVMSService {
	/**
	 * 会议信息VMS
	 * 
	 * @param meetingNo
	 * @return
	 */
	public ExecuteResult<SellerMeetingInfoDTO> selectSellerMeetingInfo(String meetingNo);

	/**
	 * 会议列表
	 * 
	 * @param page
	 * @param sellerCode
	 * @return
	 */
	public ExecuteResult<DataGrid<SellerMeetingInfoDTO>> selectSellerMeetingInfoList(Pager page, Long sellerId);

	/**
	 * @param sellerMeetingInfoDTO
	 * @return
	 */
	public ExecuteResult<SellerMeetingInfoDTO> insertSellerMeetingInfoDTO(SellerMeetingInfoDTO sellerMeetingInfoDTO);

	/**
	 * @param sellerMeetingInfoDTO
	 * @return
	 */
	public ExecuteResult<String> updateSellerMeetingInfoDTO(SellerMeetingInfoDTO sellerMeetingInfoDTO);
}
