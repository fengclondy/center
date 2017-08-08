package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberBackupContactInfo;
import cn.htd.membercenter.domain.MemberBaseInfo;
import cn.htd.membercenter.domain.MemberInvoiceInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BuyerGroupInfo;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberBaseInfoVO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyInfoDTO;

public interface MemberBaseInfoDao {
	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectBaseMemberInfo(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("pager") Pager pager);

	public long selectMemberCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public int updateMemberInfo(List<MemberBaseInfoDTO> dto);

	public List<MemberBaseInfoDTO> getMemBaseInfoForExport(
			@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public MemberBaseInfoDTO getMemberbaseById(@Param("id") Long id);

	public MemberBackupContactInfo getBackupContactById(@Param("id") Long id);

	public MemberInvoiceInfo getMemberInvoiceById(@Param("id") Long id);

	public List<VerifyDetailInfo> getVerifyById(@Param("id") Long id);

	public int updayeMemberIsvalid(@Param("dto") MemberBaseInfoDTO dto);

	public List<MemberBaseInfoDTO> selectMemberExit(List<MemberBaseInfoDTO> dto);

	@SuppressWarnings("rawtypes")
	public List<BuyerGroupInfo> getMemberGroupInfo(@Param("id") Long id, @Param("pager") Pager pager);

	@SuppressWarnings("rawtypes")
	public List<BuyerPointHistory> getBuyerPointHis(@Param("id") Long id, @Param("pager") Pager pager);

	public long selectMemberGroupCount(@Param("id") Long id);

	public long selectBuyerPointCount(@Param("id") Long id);

	public int updateMemberCenter(@Param("id") Long id, @Param("isCenterStore") Integer isCenterStore);

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectVerifyMember(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("pager") Pager pager);

	public long selectVerifyMemberCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	void saveOrUpdateMemberBaseInfo(MemberBaseInfoVO memberBaseInfoVO);

	void insertMemberBaseInfo(MemberBaseInfoVO memberBaseInfoVO);

	void updateMemberBaseInfo(MemberBaseInfoVO memberBaseInfoVO);

	public MemberBaseInfo searchMemberBaseInfoById(MemberBaseInfoVO memberBaseInfoVO);

	/* copy */
	public MemberBaseDTO queryMemberBaseInfoById(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public List<MemberGradeDTO> queryMemberGradeListInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO,
			@Param("pager") Pager<MemberGradeDTO> pager);

	public long queryMemberGradeInfoCount(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public MemberGradeDTO queryMemberGradeInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	/**
	 * 新增会员扩展信息表
	 * 
	 * @param MemberBaseInfoRegisterDTO
	 * @return
	 */
	public void insertMemberExtendInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void insertCompanyInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void insertMemberBaseInfoRegister(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void insertMemberLicenceInfoRegister(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public Long insertMemberPersonInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void insertMemberStatusInfoRegister(@Param("memberId") Long memberId, @Param("createId") Long createId,
			@Param("createName") String createName, @Param("infoType") String infoType,
			@Param("verifyId") Long verifyId);

	public Long insertVerifyInfoRegister(@Param("dto") BelongRelationshipDTO dto,
			@Param("modifyType") String modifyType);

	public void insertVerifyDetailInfo(VerifyDetailInfoDTO verifyDetailInfoDTO);

	/**
	 * 修改会员注册
	 */
	public void updateMemberLicenceInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void updateMemberCompanyInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void updateMemberBaseInfoPassword(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	public void updateMobilePhoneMemberStatusInfo(@Param("dto") MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO,
			@Param("infoType") String infoType);

	public int updateVerifyStatus(MemberRemoveRelationshipDTO dto);

	public void updateMemberExtendInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	/**
	 * 查询密码修改审核进度
	 */
	public VerifyInfoDTO selectMobilePhoneMemberStatusInfo(@Param("memberId") Long memberId,
			@Param("modifyType") String modifyType);

	/**
	 * 查询审批详细结果列表
	 */
	public List<VerifyDetailInfoDTO> selectVerifyInfoList(@Param("memberId") Long memberId,
			@Param("verifyId") Long verifyId);

	/**
	 * 根据会员ID查询会员注册信息 buyerSellerType会员/商家类型 1：会员，2：商家'
	 */
	public MemberBaseInfoRegisterDTO queryMemberRegisterInfo(@Param("memberId") Long memberId,
			@Param("modifyType") String modifyType, @Param("buyerSellerType") String buyerSellerType);

	/**
	 * 查询会员审核详细信息公司名称是否已经审核过一遍了
	 * 
	 * @param memberId
	 *            会员id
	 * @param modifyType
	 *            审核类型
	 * @param afterChange
	 *            公司名称
	 * @return
	 */
	public Long selectIsBooleanVerifyInfo(@Param("memberId") Long memberId, @Param("modifyType") String modifyType,
			@Param("afterChange") String afterChange);

	/**
	 * 查询审核
	 * 
	 * @param memberId
	 *            会员id
	 * @param infoType
	 *            审核类型
	 * @param verifyStatus
	 *            审核状态
	 * @return
	 */
	public VerifyInfoDTO selectMemberStatusInfo(@Param("memberId") Long memberId, @Param("infoType") String infoType,
			@Param("verifyStatus") List<String> verifyStatus);

	public Long selectMemberCountByBuyerIdList(@Param("buyerIdList") List<Long> buyerIdList,
			@Param("buyerName") String buyerName);

	public List<MemberBaseDTO> selectMemberInfoListByBuyerIdList(@Param("buyerIdList") List<Long> buyerIdList,
			@Param("buyerName") String buyerName, @Param("page") Pager<MemberBaseDTO> pager);

	public String selectSellerCodeByManager(@Param("managerCode") String managerCode);

}
