package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberBackupContactInfo;
import cn.htd.membercenter.domain.MemberExtendInfo;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.BoxAddDto;
import cn.htd.membercenter.dto.BuyerGroupInfo;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.CenterUpdateInfo;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.MemberInfoMotifyDTO;
import cn.htd.membercenter.dto.SalemanDTO;
import cn.htd.membercenter.dto.SellerInfoDTO;
import cn.htd.membercenter.dto.SellerTypeInfoDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;

public interface MemberBaseInfoService {
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectBaseMemberInfo(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<CenterUpdateInfo> updateMemberInfo(List<MemberBaseInfoDTO> dto);

	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> getMemBaseInfoForExport(MemberBaseInfoDTO memberBaseInfoDTO);

	public ExecuteResult<MemberDetailInfo> getMemberDetailById(Long id);

	public ExecuteResult<DataGrid<VerifyDetailInfo>> getVerifyById(Long id, String infoType,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<Boolean> updateMemberIsvalid(MemberBaseInfoDTO dto);

	public ExecuteResult<DataGrid<BuyerGroupInfo>> getMemberGroupInfo(Long id,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<DataGrid<BuyerPointHistory>> getBuyerPointHis(BuyerHisPointDTO dto,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<Boolean> updateMemberCenter(MemberBaseInfoDTO dto);

	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectVerifyMember(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager);

	public ExecuteResult<MemberDetailInfo> getMemberVerifyDetailById(Long id);

	public ExecuteResult<Boolean> updateMemberBaseInfo(MemberInfoMotifyDTO dto);

	public ExecuteResult<List<MemberBackupContactInfo>> getMemberBackupContactInfo(Long id);

	/**
	 * 通过联系人ID获取金融联系备份人信息
	 * 
	 * @param contactId
	 * @return
	 */
	public ExecuteResult<MemberBackupContactInfo> getContactId(Long contactId);

	public ExecuteResult<MemberLicenceInfo> selectMemberLicenceInfoById(Long id);

	public ExecuteResult<MemberExtendInfo> queryMemberExtendInfoById(Long id);

	public ExecuteResult<Boolean> saveVerifyInfo(MemberBaseInfoDTO memberBaseInfoDTO);

	public ExecuteResult<MemberDetailInfo> getMemberDetailBySellerId(Long id);

	/**
	 * 重置密码
	 * 
	 * @param userid
	 * @param memberCode
	 * @param password
	 * @param userId
	 * @param userName
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberPassword(Long userid, String memberCode, String password, Long userId,
			String userName);

	/**
	 * 查询会员名称
	 * 
	 * @param compid
	 * @param memberName
	 * @return
	 */
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryMemberName(Long compid, String memberName);

	/**
	 * 模糊查询供应商名称
	 * 
	 * @param compid
	 * @param memberName
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> querySupplierName(String memberName, Pager pager);

	/**
	 * 查询所有公司会员名称
	 * 
	 * @param memberName
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> queryAllMemberName(String memberName, Pager pager);

	/**
	 * 查询所有公司会员名称
	 * 
	 * @param memberName
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> queryAllSupplierName(String memberName, String type,
			Pager pager);

	/**
	 * 查询供应商名称
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerName(Long sellerId);

	/**
	 * 查询供应商IDby名称
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerIdByName(String name);

	/**
	 * 查询供应商IDby公司编码
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerIdByCode(String code);

	/**
	 * 会员注册申请
	 */
	public ExecuteResult<String> insertMemberBaseRegisterInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	/**
	 * 会员注册修改
	 */
	public ExecuteResult<String> updateMemberBaseRegisterInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO);

	/**
	 * 
	 * 根据会员表中的会员编码来获取会员信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberBaseInfoDTO> queryMemberBaseInfoByMemberCode(String memberCode);

	/**
	 * 无手机号找回密码审核
	 */
	public ExecuteResult<String> insertPasswordVerify(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO,
			String modify);

	/**
	 * 修改手机号审核
	 */
	public ExecuteResult<String> updateMobilePhoneVerify(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO,
			Long userId);

	/**
	 * 查询会员注册进度
	 * 
	 */
	public ExecuteResult<MemberBaseInfoRegisterDTO> selectRegisterProgress(Long memberID);

	/**
	 * 根据手机号,当前归属ID,公司名称，法人手机号，公司编码(memberCode)条件查询有效的会员信息返回MemberCompanyInfoDTO
	 * (需要手机号和会员/商家类型)
	 */
	public ExecuteResult<MemberCompanyInfoDTO> selectMobilePhoneMemberId(MemberCompanyInfoDTO memberCompanyInfoDTO);

	/**
	 * 会员注册验证手机号：校验公司名称和法人手机号唯一性
	 * 
	 * @param memberCompanyInfoDTO
	 * @return
	 */
	public ExecuteResult<MemberCompanyInfoDTO> searchVoidMemberCompanyInfo(MemberCompanyInfoDTO memberCompanyInfoDTO);

	/**
	 * 查询修改手机号审核进度
	 */
	public ExecuteResult<String> selectMobilePhoneMemberStatusInfo(Long memberId);

	/**
	 * 查询修改手机号审核详细信息列表(有审核前手机号,担保证明和审核后手机号,担保证明)
	 * 
	 * @param memberId
	 * @param contentName
	 *            内容名称
	 * @return
	 */
	public ExecuteResult<List<VerifyDetailInfoDTO>> selectVerifyDetailInfoDTOList(Long memberId);

	/**
	 * 通过memberCode供应商信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<List<MemberBaseInfoDTO>> selectSupplierByMemberCode(String memberCode);

	/**
	 * 通过sellerType(商家类型 1:内部供应商，2:外部供应商') 查询供应商信息
	 * 
	 * @param sellerType
	 * @return
	 */
	public ExecuteResult<List<SellerTypeInfoDTO>> selectSellerTypeList(String sellerType);

	/**
	 * 根据时间戳查询卖家信息
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExecuteResult<List<MemberBaseInfoDTO>> selectMemberbaseListByTime(Long startTime, Long endTime);

	/**
	 * 根据公司code查询公司信息
	 * 
	 * @param companyCode
	 * @param isSellerBuyer
	 * @return
	 */
	public ExecuteResult<List<MemberBaseInfoDTO>> getMemberInfoByCompanyCode(String companyCode, String isSellerBuyer);

	/**
	 * 查询会员审核信息公司名称是否已经审核
	 * 
	 * @param memberId
	 *            会员id
	 * @param modifyType
	 *            审核类型
	 * @param afterChange
	 *            公司名称
	 * @return
	 */
	public ExecuteResult<Boolean> selectIsBooleanVerifyInfo(Long memberId, String modifyType);

	/**
	 * 根据卖家ID查询有包厢关系的会员信息
	 * 
	 * @param sellerId
	 * @param buyerName
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBaseDTO>> selectBoxRelationBuyerListBySellerId(Long sellerId, String buyerName,
			Pager<MemberBaseDTO> page);

	/**
	 * 根据code查询会员ID
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<Long> getMemberIdByCode(String memberCode);

	/**
	 * 根据id查询会员code
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<String> getMemberCodeById(Long memberId);

	/**
	 * 通过code取客户经理名字
	 * 
	 * @param managerCode
	 * @return
	 */
	public String getManagerName(String sellerId, String managerCode);

	/**
	 * 通过code取客户经理名字
	 * 
	 * @param managerCode
	 * @return
	 */
	public ExecuteResult<List<SalemanDTO>> getManagerList(String memberCode);

	/**
	 * 提供给订单中心，下完订单自动建立包厢关系下行ERP
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> orderAfterDownErp(BoxAddDto dto);

	/**
	 * 通过会员code查询会员信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberImportSuccInfoDTO> queryBuyerByCode(String memberCode);

	public boolean checkMemberMobile(String mobile, Long memberId);

	/**
	 * @param compid
	 * @param memberNameParam
	 * @return
	 */
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryMemberNameAllStatus(Long compid, String memberNameParam);

	/**
	 * 更改企业类型
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberBussinessType(MemberBaseInfoDTO dto);

	/**
	 * 根据公司名查支付账号
	 * 
	 * @param companyName
	 * @return
	 */
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryAccountNoListByName(String companyName);

	/**
	 * 查询外部供应商是否有内部供应商身份 结果：1：是，0：无，2：异常
	 * 
	 * @return
	 */
	public ExecuteResult<String> IsHasInnerComapanyCert(String memberCode);

	/**
	 * 查询外部供应商是否有内部供应商信息
	 * 
	 * @return
	 */
	public ExecuteResult<MemberBaseInfoDTO> getInnerInfoByOuterHTDCode(String memberCode);
	
		
	/**
	 * 根据大b的id查询公司编码
	 * 
	 * @param sellerId
	 * @return
	*/
	public  ExecuteResult<String> queryCompanyCodeBySellerId(Long sellerId);
	
	/**
	 * 通过sellerId查询大b基础信息
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<SellerInfoDTO> querySellerBaseInfo(Long sellerId);

	public ExecuteResult<MemberDetailInfo> getMemberDetailByIdForLogin(Long cusCompanyId);

	public ExecuteResult<MemberDetailInfo> getMemberDetailBySellerIdForLogin(Long curBelongSellerId);
}
