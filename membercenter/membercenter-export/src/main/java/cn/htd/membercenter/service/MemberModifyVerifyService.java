package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberModifyDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;

public interface MemberModifyVerifyService {
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectMemberModifyVerify(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<DataGrid<VerifyDetailInfoDTO>> selectModifyVerifyInfo(Long id, String infoType);

	public ExecuteResult<Boolean> saveMemberModifyVerify(MemberModifyDTO memberModifyDTO);

	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectUnMemberVerify(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<VerifyInfo> getVerifyInfoById(Long id);

	public ExecuteResult<Boolean> saveUnMemberVerify(MemberBaseInfoDTO dto);
	
	 /**
	  * 查询非会员公司名称修改审核
		例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	  * @param memberBaseInfoDTO
	  * @param startTime
	  * @param endTime
	  * @param pager
	  * @return
	  */
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectUnMemberCompanyNameVerify(MemberBaseInfoDTO memberBaseInfoDTO,
			String startTime,String endTime,@SuppressWarnings("rawtypes") Pager pager);
}
