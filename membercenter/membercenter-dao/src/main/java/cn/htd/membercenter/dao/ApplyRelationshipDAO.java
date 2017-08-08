package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberContractInfo;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.QueryRegistProcessDTO;

public interface ApplyRelationshipDAO {

	/**
	 * 申请解除会员归属关系
	 * 
	 * @param memberId
	 */
	public void applyNoBelongRelationship(BelongRelationshipDTO belongRelationshipDto);

	/**
	 * 解除会员归属关系，在审核信息表里添加一条数据
	 * 
	 * @param belongRelationshipDto
	 * @param recordType
	 *            业务类型
	 */
	public Long insertVerifyInfo(@Param("dto") BelongRelationshipDTO dto, @Param("modifyType") String modifyType);

	/**
	 * 解除会员归属关系，在审核详细信息表里添加一条数据
	 * 
	 * @param belongRelationshipDto
	 * @param modifyType
	 *            业务类型
	 */
	public Long insertVerifyDetailInfo(@Param("dto") BelongRelationshipDTO dto, @Param("modifyType") String modifyType);

	/**
	 * 非会员转会员申请
	 * 
	 * @param memberId
	 */
	public void applynoMemberToMember(@Param("memberId") Long memberId, @Param("modifyId") Long modifyId,
			@Param("modifyName") String modifyName);

	public void updateMemberVerifyInfo(@Param("memberId") Long memberId, @Param("verifyStatus") String verifyStatus,
			@Param("modifyType") String modifyType, @Param("modifyId") Long modifyId,
			@Param("modifyName") String modifyName);

	public void updateVerifyDetailInfo(@Param("memberId") Long memberId, @Param("modifyType") String modifyType,
			@Param("modifyId") Long modifyId, @Param("modifyName") String modifyName);

	/**
	 * 查询会员申请经营关系待审核列表
	 * 
	 * @param companyName
	 * @return
	 */
	public List<ApplyBusiRelationDTO> selectBusinessRelationship(@Param("curBelongSellerId") Long curBelongSellerId,
			@Param("companyName") String companyName, @Param("memberId") Long memberId);

	/**
	 * 查询注册进度
	 * 
	 * @param memberId
	 * @param companyName
	 * @return
	 */
	public List<QueryRegistProcessDTO> queryRegistProcess(@Param("memberId") Long memberId,
			@Param("companyName") String companyName, @Param("curBelongSellerId") Long curBelongSellerId);

	/**
	 * 查询是否有包厢关系
	 * 
	 * @param memberId
	 */
	public ApplyBusiRelationDTO queryBoxRelationInfo(@Param("memberId") Long memberId,
			@Param("sellerId") Long sellerId);

	/**
	 * 生成包厢关--》在包厢关系表插入数据
	 * 
	 * @param businessRelatVerifyDto
	 */
	public Long insertBoxRelationInfo(ApplyBusiRelationDTO businessRelatVerifyDto);

	/**
	 * 修改经营关系--》在经营关系表中修改经营状态：1待审核，2通过，3驳回
	 * 
	 * @param businessId
	 * @param auditStatus
	 * @param modifyId
	 * @param modifyName
	 * @param remark
	 *            ->点击驳回时记录驳回原因
	 */
	public void updatBusinessRelationship(@Param("businessId") Long businessId,
			@Param("auditStatus") String auditStatus, @Param("modifyId") Long modifyId,
			@Param("modifyName") String modifyName, @Param("remark") String remark,
			@Param("erpStatus") String erpStatus, @Param("customerManagerId") String customerManagerId);

	/**
	 * 外部商家入驻申请-->会员基本信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public Long insertMemberBaseInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->会员/商家信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void insertMemberCompanyInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->会员证照信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void insertMemberLicenceInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->会员扩展信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void insertMemberExtendInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->会员合同信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void insertMemberContractInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->获取合同最后四位编号
	 * 
	 * @return
	 */
	public String getHTCode();

	/**
	 * 外部商家入驻申请-->会员开户行信息表里新增一条数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void insertMemberBankInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->修改会员基本信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public Long updatetMemberBaseInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->修改会员/商家信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void updateMemberCompanyInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->修改会员证照信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void updateMemberLicenceInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->修改会员扩展信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void updateMemberExtendInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请-->修改会员合同信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void updateMemberContractInfo(MemberContractInfo memberContractInfo);

	/**
	 * 外部商家入驻申请-->修改会员开户行信息表里数据
	 * 
	 * @param outDto
	 * @return
	 */
	public void updateMemberBankInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 外部商家入驻申请,修改会员开户行信息表数据进行查询验证
	 * 
	 * @param outDto
	 * @return
	 */
	public MemberOutsideSupplierCompanyDTO selectMemberBankInfo(Long memberId);

	/**
	 * 外部商家入驻申请，修改会员/商家信息表数据进行查询验证
	 * 
	 * @param outDto
	 * @return
	 */
	public MemberOutsideSupplierCompanyDTO selectMemberCompanyInfo(Long memberId);

	/**
	 * 查询待审核的经营关系信息分组个数
	 */
	public List<ApplyBusiRelationDTO> selectBusinessRelationshipCount(
			@Param("curBelongSellerId") Long curBelongSellerId, @Param("companyName") String companyName);

	/**
	 * 外部商家入驻申请——根据memberCode修改会员基本信息表数据
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public Long updateMemberBaseCodeInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 根据memberCode查询memberID
	 */
	public Long getMemberID(@Param("memberCode") String memberCode);

	/**
	 * 根据公司名称，手机号查询非内部供应商会员记录
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public Long selectOutCompanyCheckCount(@Param("dto") MemberOutsideSupplierCompanyDTO outCompanyDto);
}
