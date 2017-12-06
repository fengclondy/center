package cn.htd.membercenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.MemberAuditPendingDTO;
import cn.htd.membercenter.dto.MemberVerifyInfoDTO;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;

public interface MemberVerifyStatusDAO {
	
	public List<MemberVerifyStatusDTO> selectByStatusList(@Param("page") Pager page,@Param("verifyStatus") String verifyStatus,
			@Param("sellerId") Long sellerId,	@Param("name") String name,@Param("isDiffIndustry") String isDiffIndustry,
			@Param("startTime") Date startTime,@Param("endTime") Date endTime);
	
	public MemberVerifyInfoDTO queryInfoByMemberId(@Param("memberId") Long memberId);
	
	public List<CategoryBrandDTO> queryBrandByMemberId(@Param("page") Pager page,@Param("memberId") Long memberId);
	
	/**
	 * VMS - 查询待审核会员列表
	 * @author li.jun
	 * @time 2017-12-06
	 */
	public List<MemberVerifyStatusDTO> queryAuditPendingMember(@Param("page") Pager<MemberVerifyStatusDTO> page,@Param("entity") MemberAuditPendingDTO memberAuditPending);
	/**
	 * VMS - 查询待审核会员数量
	 * @author li.jun
	 * @time 2017-12-06
	 */
	public Long queryAuditPendingMemberCount(@Param("entity") MemberAuditPendingDTO memberAuditPending);
	
}
