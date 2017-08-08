package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberPlatformFeeInfoDTO;

public interface MemberPlatformFeeInfoService {

	/**
	 * 查询商家
	 * 
	 * @param bondNam
	 * @param bondNo
	 * @param pager
	 * @return DataGrid<MemberPlatformFeeInfoDTO>
	 */
	public DataGrid<MemberPlatformFeeInfoDTO> searchSellerPlatform(String bondNam, String bondNo,
			Pager<MemberPlatformFeeInfoDTO> pager);

	/**
	 * 查询商家平台使用费信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param pager
	 * @return DataGrid<MemberPlatformFeeInfoDTO>
	 */
	public DataGrid<MemberPlatformFeeInfoDTO> searchPlatformFeeInfo(Long sellerId, String startTime, String endTime,
			Pager<MemberPlatformFeeInfoDTO> pager);

	/**
	 * 商家平台使用费信息添加
	 * 
	 * @param memberPlatformFeeInfoDTO
	 * @return ExecuteResult<MemberPlatformFeeInfoDTO>
	 */
	public ExecuteResult<MemberPlatformFeeInfoDTO> addPlatformFeeInfo(
			MemberPlatformFeeInfoDTO memberPlatformFeeInfoDTO);
}
