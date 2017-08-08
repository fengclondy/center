/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberCompanyInfoService</p>
* @author youyajun
* @date 2016年12月12日
* <p>Description: 
*			商家(外部)相关接口
* </p>
 */
package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberSellerDepositDto;

public interface MemberSellerDepositService {

	/**
	 * 查询商家保证金数据
	 * 
	 * @param bondNam
	 * @param bondNo
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	public DataGrid<MemberSellerDepositDto> searchDepositInfo(String bondNam, String bondNo,
			Pager<MemberSellerDepositDto> pager);

	/**
	 * 查询商家详细信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	public DataGrid<MemberSellerDepositDto> searchDepositChangeHistory(Long sellerId, String startTime, String endTime,
			Pager<MemberSellerDepositDto> pager);

	/**
	 * 商家保证金变动履历表修正
	 * 
	 * @param memberCompanyInfoDto
	 * @return ExecuteResult<MemberCompanyInfoDto>
	 */
	public ExecuteResult<MemberSellerDepositDto> addUpdateSellerDeposit(MemberSellerDepositDto memberCompanyInfoDto);

	/**
	 * 查询商家外接渠道保证金数据
	 * 
	 * @param bondNam
	 * @param bondNo
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	public DataGrid<MemberSellerDepositDto> searchOuterChannelDepositInfo(String bondNam, String bondNo,
			Pager<MemberSellerDepositDto> pager);

	/**
	 * 查询商家外接渠道详细信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	public DataGrid<MemberSellerDepositDto> searchOuterChannelDepositChangeHistory(Long sellerId, String startTime,
			String endTime, Pager<MemberSellerDepositDto> pager);

	/**
	 * 商家外接渠道保证金变动履历表修正
	 * 
	 * @param memberCompanyInfoDto
	 * @return ExecuteResult<MemberCompanyInfoDto>
	 */
	public ExecuteResult<MemberSellerDepositDto> addUpdateSellerOuterChannel(
			MemberSellerDepositDto memberCompanyInfoDto);
}
