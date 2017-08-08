package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.MemberVerifyInfoDTO;

public interface MemberVerifyInfoService {
	
	/**
	 * //会员待审核页面基本信息
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberVerifyInfoDTO> queryInfoByMemberId(Long memberId);
	
	/**
	 * //会员待审核页面品类品牌
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<CategoryBrandDTO>> queryBrandByMemberId(Pager page,Long memberId);
}
