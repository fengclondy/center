package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberSellerDepositDto;

public interface MemberSellerDepositDAO {

	/**
	 * 查询总共有多少商家
	 * 
	 * @param companyName
	 * @param companyCode
	 * @return Long
	 */
	public Long depositInfoCount(@Param("companyName") String companyName, @Param("companyCode") String companyCode);

	/**
	 * 查询商家保证金信息
	 * 
	 * @param companyName
	 * @param companyCode
	 * @param page
	 * @return List<MemberSellerDepositDto>
	 */
	public List<MemberSellerDepositDto> searchDepositInfo(@Param("companyName") String companyName,
			@Param("companyCode") String companyCode, @Param("page") Pager<MemberSellerDepositDto> page);

	/**
	 * 查询有多少商家保证金变动履历
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @return Long
	 */
	public Long depositChangeHistoryCount(@Param("sellerId") Long sellerId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 查询商家保证金变动履历详细
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return List<MemberSellerDepositDto>
	 */
	public List<MemberSellerDepositDto> searchDepositChangeHistory(@Param("sellerId") Long sellerId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("page") Pager<MemberSellerDepositDto> page);

	/**
	 * 商家保证金变动履历添加
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void addDepositChangeHistory(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 商家保证金信息更新记录
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void updateDepositInfo(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 查询商家保证金信息存在与否
	 * 
	 * @param sellerId
	 */
	public Long sellerDepositInfoCount(@Param("sellerId") Long sellerId);

	/**
	 * 商家保证金信息不存在的时候，添加
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void addSellerDepositInfo(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 查询总共有多少商家(外接渠道)
	 * 
	 * @param companyName
	 * @param companyCode
	 * @return Long
	 */
	public Long outerChannelDepositInfoCount(@Param("companyName") String companyName,
			@Param("companyCode") String companyCode);

	/**
	 * 查询商家(外接渠道)保证金信息
	 * 
	 * @param companyName
	 * @param companyCode
	 * @param page
	 * @return List<MemberSellerDepositDto>
	 */
	public List<MemberSellerDepositDto> searchOuterChannelDepositInfo(@Param("companyName") String companyName,
			@Param("companyCode") String companyCode, @Param("page") Pager<MemberSellerDepositDto> page);

	/**
	 * 查询有多少商家(外接渠道)保证金变动履历
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @return Long
	 */
	public Long outerChanneldepositChangeHistoryCount(@Param("sellerId") Long sellerId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 查询商家(外接渠道)保证金变动履历详细
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return List<MemberSellerDepositDto>
	 */
	public List<MemberSellerDepositDto> searchOuterChannelDepositChangeHistory(@Param("sellerId") Long sellerId,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("page") Pager<MemberSellerDepositDto> page);

	/**
	 * 商家(外接渠道)保证金变动履历添加
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void addOuterChannelDepositChangeHistory(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 商家(外接渠道)保证金信息更新记录
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void updateOuterChannelDepositInfo(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 查询商家(外接渠道)保证金信息存在与否
	 * 
	 * @param sellerId
	 */
	public Long sellerOuterChannelDepositInfoCount(@Param("sellerId") Long sellerId);

	/**
	 * 商家(外接渠道)保证金信息不存在的时候，添加
	 * 
	 * @param memberCompanyInfoDto
	 */
	public void addSellerOuterChannelDepositInfo(@Param("entity") MemberSellerDepositDto memberCompanyInfoDto);
}
