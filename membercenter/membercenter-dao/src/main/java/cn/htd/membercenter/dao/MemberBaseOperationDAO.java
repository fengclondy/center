package cn.htd.membercenter.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.domain.BuyerGroupRelationship;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberBackupContactInfo;
import cn.htd.membercenter.domain.MemberExtendInfo;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.BuyerGroupInfo;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.MemberInvoiceInfoDTO;
import cn.htd.membercenter.dto.SellerInfoDTO;
import cn.htd.membercenter.dto.SellerTypeInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;

public interface MemberBaseOperationDAO {
	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectBaseMemberInfo(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("pager") Pager pager);

	public long selectMemberCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public List<MemberBaseInfoDTO> queryMemberList(List<MemberBaseInfoDTO> memberDto);

	public int updateMemberInfo(List<MemberBaseInfoDTO> dto);

	public List<MemberBaseInfoDTO> getMemBaseInfoForExport(
			@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public MemberBaseInfoDTO getMemberbaseById(@Param("id") Long id, @Param("buyerSellerType") String buyerSellerType);

	public MemberBaseInfoDTO getMemberbaseBySellerId(@Param("id") Long id,
			@Param("buyerSellerType") String buyerSellerType);

	public List<MemberBackupContactInfo> getBackupContactById(@Param("id") Long id);

	public MemberInvoiceInfoDTO getMemberInvoiceById(@Param("id") Long id);

	@SuppressWarnings("rawtypes")
	public List<VerifyDetailInfo> getVerifyById(@Param("id") Long id, @Param("infoType") String infoType,
			@Param("pager") Pager pager);

	public int updateMemberIsvalid(@Param("dto") MemberBaseInfoDTO dto);

	public List<MemberBaseInfoDTO> selectMemberExit(List<MemberBaseInfoDTO> dto);

	@SuppressWarnings("rawtypes")
	public List<BuyerGroupInfo> getMemberGroupInfo(@Param("ids") List<Long> id, @Param("pager") Pager pager);

	@SuppressWarnings("rawtypes")
	public List<BuyerPointHistory> getBuyerPointHis(@Param("dto") BuyerHisPointDTO dto, @Param("pager") Pager pager);

	public long selectMemberGroupCount(@Param("ids") List<Long> id);

	public long selectBuyerPointCount(@Param("dto") BuyerHisPointDTO dto);

	public int updateMemberCenter(@Param("dto") MemberBaseInfoDTO dto);

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectVerifyMember(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("pager") Pager pager);

	public long selectVerifyMemberCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public int updateMemberBaseInfo(@Param("memberBaseInfoDTO") MemberBaseInfoDTO dto);

	public int updateMemberCompanyInfo(@Param("memberBaseInfoDTO") MemberBaseInfoDTO dto);

	public int insertVerifyInfo(List<VerifyDetailInfo> dto);

	public long getVerifyCountById(@Param("id") Long id, @Param("infoType") String infoType);

	public MemberLicenceInfo selectMemberLicenceInfoById(@Param("id") Long id);

	public MemberExtendInfo queryMemberExtendInfoById(@Param("id") Long id);

	public int saveMemberStatusInfo(@Param("dto") MemberStatusInfo dto);

	public int saveVerifyInfo(@Param("dto") VerifyInfo dto);

	public VerifyResultDTO selectVerifyResult(@Param("id") Long id, @Param("infoType") String infoType);

	public VerifyResultDTO selectVerifyByIdAndInfoType(@Param("id") Long id, @Param("infoType") String infoType);

	public MemberBaseInfoDTO getMemberBaseInfoById(@Param("id") Long id,
			@Param("buyerSellerType") String buyerSellerType);

	public VerifyResultDTO selectCooperateVerifyResult(@Param("id") Long id);

	public int insertMemberStatus(@Param("dto") MemberStatusInfo dto);

	public MemberBaseInfoDTO selectCompanyInfoByName(@Param("name") String name);

	public VerifyResultDTO selectVerifyResultById(@Param("id") Long id);

	public int deleteMemberStatus(@Param("dto") MemberStatusInfo dto);

	public List<BuyerGroupRelationship> getBuyerGroupRelation(@Param("id") Long id);

	/**
	 * @param compid
	 * @param memberName
	 * @return
	 */
	public List<MemberImportSuccInfoDTO> selectMemberName(@Param("compid") Long compid,
			@Param("memberName") String memberName);

	public List<MemberImportSuccInfoDTO> selectAllMemberName(@Param("memberName") String memberName,
			@Param("pager") Pager pager);

	public List<MemberImportSuccInfoDTO> selectAllSupplierName(@Param("memberName") String memberName,
			@Param("type") String type, @Param("pager") Pager pager);

	public List<MemberImportSuccInfoDTO> selectSupplierName(@Param("memberName") String memberName,
			@Param("pager") Pager pager);

	public Long selectSupplierNameCount(@Param("memberName") String memberName);

	/**
	 * @param memberName
	 * @return
	 */
	public Long selectAllMemberNameCount(String memberName);

	/**
	 * @param memberName
	 * @return
	 */
	public Long selectAllSupplierNameCount(@Param("memberName") String memberName, @Param("type") String type);

	/**
	 * @param sellerId
	 * @return
	 */
	public MemberImportSuccInfoDTO selectSellerName(@Param("sellerId") Long sellerId);

	/**
	 * 根据member_base_info中的member_code获取info信息 add by jcf 16-12-28
	 * 
	 * @param memberCode
	 * @return
	 */
	public MemberBaseInfoDTO selectMemberBaseInfoByMemberCode(@Param("memberCode") String memberCode);

	/**
	 * @param name
	 * @return
	 */
	public MemberImportSuccInfoDTO querySellerIdByName(@Param("name") String name);

	/**
	 * @param code
	 * @return
	 */
	public MemberImportSuccInfoDTO querySellerIdByCode(@Param("code") String code);

	/**
	 * @param code
	 * @return
	 */
	public MemberImportSuccInfoDTO queryBuyerByCode(@Param("code") String code);

	/**
	 * 通过联系人ID获取金融联系备份人信息
	 */
	public MemberBackupContactInfo getContactId(@Param("contactId") Long contactId);

	/**
	 * 更新账户信息
	 * 
	 * @param dto
	 * @return
	 */
	public int updateMemberCompanyPayInfo(@Param("dto") MemberBaseInfoDTO dto);

	/**
	 * 通过会员ID查询包厢关系
	 * 
	 * @param id
	 * @return
	 */
	public List<BoxRelationship> selectBoxRelationByMemberId(@Param("id") Long id);

	/**
	 * 通过商家类型 1:内部供应商，2:外部供应商'查询供应商信息列表
	 * 
	 * @param sellerType
	 * @return
	 */
	public List<SellerTypeInfoDTO> selectSellerTypeList(@Param("sellerType") String sellerType);

	/**
	 * 根据时间戳查询公司信息
	 * 
	 * @param startTime
	 * @param endDate
	 * @return
	 */
	public List<MemberBaseInfoDTO> selectMemberbaseListByTime(@Param("startTime") Long startTime,
			@Param("endTime") Long endTime);

	/**
	 * 
	 * @param companyCode
	 * @param isSellerBuyer
	 * @return
	 */
	public List<MemberBaseInfoDTO> getMemberInfoByCompanyCode(@Param("companyCode") String companyCode,
			@Param("buyerSellerType") String buyerSellerType);

	/**
	 * @param memberCode
	 * @return
	 */
	public Long getMemberIdByCode(@Param("memberCode") String memberCode);

	/**
	 * @param memberId
	 * @return
	 */
	public String getMemberCodeById(@Param("memberId") Long memberId);

	/**
	 * 
	 * @param memberId
	 * @return
	 */
	public String getMemberCompanyInfoCodeById(@Param("memberId") Long memberId);

	/**
	 * 
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public Long queryBusinessBrandCategory(@Param("buyerId") Long buyerId, @Param("sellerId") Long sellerId,
			@Param("brandCode") Long brandCode, @Param("classCategoryCode") Long classCategoryCode);

	/**
	 * @param mobile
	 * @return
	 */
	public ArrayList<MemberCompanyInfoDTO> checkMemberMobile(@Param("mobile") String mobile,
			@Param("memberId") Long memberId);

	/**
	 * @param compid
	 * @param memberName
	 * @return
	 */
	public List<MemberImportSuccInfoDTO> queryMemberNameAllStatus(@Param("compid") Long compid,
			@Param("memberName") String memberName);

	public List<MemberImportSuccInfoDTO> queryAccountNoListByName(@Param("companyName") String companyName);

	public Long getInnerSellerInfoByName(@Param("companyName") String companyName);

	public MemberBaseInfoDTO getInnerInfoByName(@Param("companyName") String companyName);
	
	public List<MemberCompanyInfoDTO> checkCompanyNameUnique(@Param("companyName") String companyName,@Param("memberId")Long memberId);
	
	public MemberBaseInfoRegisterDTO queryVerifyStatus(@Param("memberId") Long memberId);
	
	public SellerInfoDTO querySellerInfoBySellerId(@Param("sellerId") Long sellerId);
	
	public List<MemberBaseInfoDTO> queryMmeberComCodeList(@Param("name") String name);
	
	public List<MemberBaseDTO> queryMemberComCodeListByMemberId(List<String> list);
	
	public List<MemberBaseInfoDTO> queryMmeberInfoByLittleMemberComCode(List<String> list);
	
	public List<MemberBaseInfoDTO> queryMemberInfoByMemCodeList(List<String> list);

	public MemberBaseInfoDTO queryMemberCompanyInfo(String memberCode);
 }
