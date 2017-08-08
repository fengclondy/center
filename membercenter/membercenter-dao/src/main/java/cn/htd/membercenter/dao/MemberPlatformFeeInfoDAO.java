package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberPlatformFeeInfoDTO;

public interface MemberPlatformFeeInfoDAO {

	/**
	 * 查询总共有多少商家
	 * 
	 * @param companyName
	 * @param companyCode
	 * @return Long
	 */
	public Long sellerPlatformCount(@Param("companyName") String companyName, @Param("companyCode") String companyCode);

	/**
	 * 查询商家信息
	 * 
	 * @param companyName
	 * @param companyCode
	 * @param page
	 * @return List<MemberPlatformFeeInfoDTO>
	 */
	public List<MemberPlatformFeeInfoDTO> searchSellerPlatform(@Param("companyName") String companyName,
			@Param("companyCode") String companyCode, @Param("page") Pager<MemberPlatformFeeInfoDTO> page);

	/**
	 * 查询有多少商家平台使用费信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @return Long
	 */
	public Long platformFeeInfoCount(@Param("sellerId") Long sellerId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 查询商家平台使用费信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return List<MemberPlatformFeeInfoDTO>
	 */
	public List<MemberPlatformFeeInfoDTO> searchPlatformFeeInfo(@Param("sellerId") Long sellerId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("page") Pager<MemberPlatformFeeInfoDTO> page);

	/**
	 * 商家平台使用费信息添加
	 * 
	 * @param memberPlatformFeeInfoDTO
	 */
	public void addPlatformFeeInfo(@Param("entity") MemberPlatformFeeInfoDTO memberPlatformFeeInfoDTO);

}
