package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BoxRelationImportDTO;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.MemberBuyerAuthenticationDTO;
import cn.htd.membercenter.dto.MemberBuyerFinanceDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeChangeHistoryDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerSupplierDTO;

/**
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerService
 * </p>
 * 
 * @author root
 * @date 2016年12月26日
 *       <p>
 *       Description: 会员中心 买家中心
 *       </p>
 */
public interface MemberBuyerService {

	/**
	 * 根据用户loginId查询会员ID
	 * 
	 * @param loginId
	 * @return
	 */
	public ExecuteResult<Long> getMemberIdByLoginId(String loginId);

	/**
	 * 查询会员个人信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerPersonalInfoDTO> queryBuyerPersonalInfo(Long memberId);

	/**
	 * 保存、更新会员个人信息
	 * 
	 * @param memberBuyerPersonalInfo
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerPersonalInfo(MemberBuyerPersonalInfoDTO memberBuyerPersonalInfo);

	/**
	 * 查询会员经验值变动履历
	 * 
	 * @param dto
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BuyerPointHistory>> getBuyerPointHis(BuyerHisPointDTO dto, Pager pager);

	/**
	 * 查询会员等级详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerGradeInfoDTO> queryBuyerGradeInfo(Long memberId);

	/**
	 * 查询会员等级变更履历
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBuyerGradeChangeHistoryDTO>> queryBuyerGradeChangeHistory(BuyerHisPointDTO dto,
			Pager pager);

	/**
	 * 查询会员归属供应商信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerSupplier(Long memberId);

	/**
	 * 查询会员包厢关系供应商基本信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> queryBuyerBusinessSupperlier(Pager page, Long memberId);

	/**
	 * 查询会员包厢关系供应商基本信息byid
	 * 
	 * @param memberId
	 * @param supplierId
	 * @return
	 */
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerBusinessSupperlierById(Long memberId, Long supplierId);

	/**
	 * 申请修改会员基本信息
	 * 
	 * @param memberBuyerPersonalInfoDTO
	 * @return
	 */
	public ExecuteResult<Boolean> ApplyModifyBuyerBaseInfo(MemberBuyerPersonalInfoDTO memberBuyerPersonalInfoDTO);

	/**
	 * 申请添加会员供应商经营关系
	 * 
	 * @param businessRelationshipDTO
	 * @return
	 */
	public ExecuteResult<BoxRelationImportDTO> ApplyAddBusinessRelationship(
			List<ApplyBusiRelationDTO> businessRelatVerifyDtoList);

	/**
	 * 根据品牌品类区域查询供应商列表
	 * 
	 * @param memberBuyerSupplierDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> queryBuyerSupplierListByBCID(String companyName,
			List<String> categoryId, List<String> brandId, List<String> locationProvince, Pager page);

	/**
	 * 查询会员金融法人信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerFinanceDTO> queryBuyerFinance(Long memberId);

	/**
	 * 查询会员金融备用联系人列表
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBuyerFinanceDTO>> queryBuyerBackupContactList(Pager page,
			MemberBuyerFinanceDTO memberBuyerFinanceDTO);

	/**
	 * 添加金融备用联系人
	 * 
	 * @param memberBuyerFinanceDTO
	 * @return
	 */
	public ExecuteResult<Boolean> addBuyerBackupContact(MemberBuyerFinanceDTO memberBuyerFinanceDTO);

	/**
	 * 修改金融备用联系人
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerBackupContact(MemberBuyerFinanceDTO memberBuyerFinanceDTO);

	/**
	 * 保存会员金融法人信息为待审核
	 * 
	 * @param memberBuyerFinanceDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerArticBeVerifyed(MemberBuyerFinanceDTO memberBuyerFinanceDTO);

	/**
	 * 查询会员手机号码验证信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerAuthenticationDTO> queryBuyerTELAuthenticate(Long memberId);

	/**
	 * 保存会员手机号验证标记
	 * 
	 * @param memberBuyerAuthenticationDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerTELAuthenticate(MemberBuyerAuthenticationDTO memberBuyerAuthenticationDTO);

	/**
	 * 修改会员手机号
	 * 
	 * @param memberBuyerAuthenticationDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateBuyerTELAuthenNum(MemberBuyerAuthenticationDTO memberBuyerAuthenticationDTO);

	/**
	 * 查询会员实名制认证信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberBuyerAuthenticationDTO> queryBuyerRealNameAuthenticate(Long memberId);

	/**
	 * 保存会员实名认证信息为待审核状态
	 * 
	 * @param memberBuyerAuthenticationDTO
	 * @return
	 */
	// public ExecuteResult<Boolean> updateBuyerRealNameBeVerified(Long
	// memberId);
	/**
	 * 查询会员包厢关系供应商基本信息byid
	 * 
	 * @param memberId
	 * @param supplierId
	 * @return
	 */
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerBusinessSupperlierAllBySupplierId(Long supplierId);

	/**
	 * 查询是否显示修改身份证提示
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<Boolean> isShowIdMsg(Long memberId);

	/**
	 * 不再提示修改身份证
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<Boolean> updateShowIdMsg(Long memberId);
}
