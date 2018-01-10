package cn.htd.membercenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.domain.MemberBankInfo;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.domain.MemberInvoiceInfo;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BusinessRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberErpDTO;
import cn.htd.membercenter.dto.VerifyInfoDTO;

public interface MemberTaskDAO {

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectMemberDownErp(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager, @Param("erpStatus") String erpStatus);

	public List<MemberCompanyInfo> selectCompanyListBySellerIds(List<Long> ids);

	public List<MemberCompanyInfo> selectCompanyListByIds(List<Long> ids);

	public List<MemberInvoiceInfo> selectInvoiceListByIds(List<Long> ids);

	public int updateErpStatus(@Param("id") Long id, @Param("status") String status, @Param("infoType") String infoType,
			@Param("date") Date date);

	@SuppressWarnings("rawtypes")
	public List<BusinessRelationshipDTO> selectBusinessRelationDown(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager, @Param("erpStatus") String erpStatus);

	public List<MemberBaseInfoDTO> selectMemberByIds(List<Long> ids);

	public int updateBusinessErpStatus(@Param("id") Long id, @Param("status") String status, @Param("date") Date date);

	@SuppressWarnings("rawtypes")
	public List<BoxRelationship> selectCompanyRelationDown(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager, @Param("erpStatus") String erpStatus);

	public List<MemberBaseInfoDTO> selectMemberBaseListByIds(List<Long> ids);

	public List<MemberBankInfo> selectBankListByIds(List<Long> ids);

	public List<MemberConsigAddressDTO> selectConsigAddressListByIds(List<Long> ids);

	public int updateBoxErpStatus(@Param("id") Long id, @Param("status") String status, @Param("date") Date date);

	public List<BelongRelationshipDTO> selectBelongRelationListByIds(List<Long> ids);

	@SuppressWarnings("rawtypes")
	public List<VerifyInfoDTO> selectVerifyInfoDowns(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager, @Param("modifyType") String modifyType,
			@Param("belongSellerId") Long belongSellerId);

	public List<VerifyInfoDTO> selectBelongSellers(List<String> modifyType);

	public List<BusinessRelationshipDTO> selectBusinessRelation();

	public List<MemberBuyerPersonalInfoDTO> selectSendBirthday(@Param("memberId") Long memberId);

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectMemberToYijifu(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager, @Param("erpStatus") String erpStatus);

	public List<MemberBaseInfoDTO> selectMemberBySellerIds(List<Long> ids);

	/**
	 * 数据需要同步到支付的数据
	 * 
	 * @param entry
	 * @param pager
	 * @return
	 */
	public List<MemberBaseInfoDTO> selectMemberToYijifuTask(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);

	/**
	 * @param condition
	 * @param pager
	 * @return
	 */
	public List<MemberBuyerPersonalInfoDTO> selectSendBirthday(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);

	public int updateMemberCompanyInfo(@Param("dto") MemberBaseInfoDTO dto);

	/**
	 * 查询下行异常会员数据
	 * 
	 * @param entry
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<MemberErpDTO> selectErpDownListType1(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);

	/**
	 * 查询往来关系异常数据
	 * 
	 * @param entry
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<MemberErpDTO> selectErpDownListType2(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);

	/**
	 * 查询经营关系异常数据
	 * 
	 * @param entry
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<MemberErpDTO> selectErpDownListType3(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);

	/**
	 * 修改支付
	 * 
	 * @param entry
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectMemberModifyToYijifuTask(@Param("entry") MemberDownCondition entry,
			@Param("pager") Pager pager);
	
	public List<MemberBaseInfoDTO> selectCompanyInfo(@Param("entity") MemberDownCondition entry,	@Param("pager") Pager pager);

}
