package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.MemberBuyerAuthenticationDTO;
import cn.htd.membercenter.dto.MemberBuyerFinanceDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeChangeHistoryDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeMatrixDTO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerSupplierDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyDetailInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyInfoDTO;

public interface MemberBuyerDao {

	/**
	 * 根据用户loginId查询会员id
	 * 
	 * @param loginId
	 * @return
	 */
	public Long getMemberIdByLoginId(String loginId);

	/**
	 * 查询会员个人信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerPersonalInfoDTO queryBuyerPersonalInfo(Long memberId);

	public String getMemberVerifyStatus(@Param("memberId") Long memberId, @Param("modifyType") String modifyType);

	public String getMemberVerifyRemark(@Param("memberId") Long memberId, @Param("modifyType") String modifyType);

	/**
	 * 更新公司信息表
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerCompanyInfo(MemberBuyerPersonalInfoDTO dto);

	/**
	 * 更新会员个人表
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerPersonInfo(MemberBuyerPersonalInfoDTO dto);
	
	public int updatePersonInfo(MemberBuyerPersonalInfoDTO dto);

	/**
	 * 更新会员证照信息表
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerLicenceInfo(MemberBuyerPersonalInfoDTO dto);

	/**
	 * 查询会员等级详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerGradeInfoDTO queryBuyerGradeInfo(Long memberId);

	/**
	 * 查询会员等级变更履历
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberBuyerGradeChangeHistoryDTO> queryBuyerGradeChangeHistory(@Param("dto") BuyerHisPointDTO dto,
			@Param("pager") Pager pager);

	/**
	 * 查询商城消费等级区间信息
	 * 
	 * @return
	 */
	public List<MemberBuyerGradeMatrixDTO> queryBuyerScoreIntervalMall();

	/**
	 * 查询金融产品等级区间信息
	 * 
	 * @return
	 */
	public List<MemberBuyerGradeMatrixDTO> queryBuyerScoreIntervalFinance();

	/**
	 * 查询会员等级区间信息
	 * 
	 * @return
	 */
	public MemberBuyerGradeMatrixDTO queryBuyerGradeInterval(Long buyerLevel);

	/**
	 * 查询会员消费权重信息
	 * 
	 * @return
	 */
	public MemberBuyerGradeMatrixDTO queryBuyerScoreWeight();

	/**
	 * 查询会员供应商信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerSupplierDTO queryBuyerSupplier(Long memberId);

	/**
	 * 查询会员供应商经营类目id列表
	 * 
	 * @param memberId
	 * @param supplierId
	 * @return
	 */
	public List<Long> queryBuyerCategory(@Param("memberId") Long memberId, @Param("supplierId") Long supplierId,
			@Param("brandId") Long brand_id);

	/**
	 * 查询会员包厢关系供应商基本信息
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberBuyerSupplierDTO> queryBuyerBusinessSupperlier(@Param("page") Pager page,
			@Param("memberId") Long memberId);

	/**
	 * 插入审批信息表
	 * 
	 * @param dto
	 * @return
	 */
	public int addVerifyInfo(MemberBuyerVerifyInfoDTO dto);

	/**
	 * 插入审批详细表
	 * 
	 * @param dto
	 * @return
	 */
	public int addVerifyDetailInfo(MemberBuyerVerifyDetailInfoDTO dto);

	/**
	 * 根据品牌品类区域查询供应商总个数
	 * 
	 * @param categoryId
	 * @param brandId
	 * @return
	 */
	public Long queryBuyerSupplierListByBCIDCount(@Param("companyName") String companyName,
			@Param("categoryId") List<String> categoryId, @Param("brandId") List<String> brandId,
			@Param("locationProvince") List<String> locationProvince);

	/**
	 * 根据品牌品类区域查询供应商列表
	 * 
	 * @param categoryId
	 * @param brandId
	 * @param page
	 * @return
	 */
	public List<MemberBuyerSupplierDTO> queryBuyerSupplierListByBCID(@Param("companyName") String companyName,
			@Param("categoryId") List<String> categoryId, @Param("brandId") List<String> brandId,
			@Param("locationProvince") List<String> locationProvince, @Param("page") Pager page);

	/**
	 * 查询会员金融法人信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerFinanceDTO queryBuyerFinance(Long memberId);

	/**
	 * 查询会员金融备用联系人列表
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberBuyerFinanceDTO> queryBuyerBackupContactList(@Param("dto") MemberBuyerFinanceDTO dto,
			@Param("page") Pager page);

	/**
	 * 添加金融备用联系人
	 * 
	 * @param dto
	 * @return
	 */
	public int addBuyerBackupContact(MemberBuyerFinanceDTO dto);

	/**
	 * 修改金融备用联系人
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerBackupContact(MemberBuyerFinanceDTO dto);

	/**
	 * 查询会员手机号码验证信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerAuthenticationDTO queryBuyerTELAuthenticate(Long memberId);

	/**
	 * 查询会员实名认证信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberBuyerAuthenticationDTO queryBuyerRealNameAuthenticate(Long memberId);

	/**
	 * 更新会员手机号认证状态
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerTELAuthStatus(MemberBuyerAuthenticationDTO dto);

	/**
	 * 更改会员手机号
	 * 
	 * @param dto
	 * @return
	 */
	public int updateBuyerTELAuthNumber(MemberBuyerAuthenticationDTO dto);

	/**
	 * 删除修改记录
	 * 
	 * @param verifyId
	 */
	public int deleteVerifyInfoById(@Param("verifyId") int verifyId);

	/**
	 * 根据id查询会员备用联系人
	 * 
	 * @param contactId
	 * @return
	 */
	public MemberBuyerFinanceDTO queryBuyerBackupContactById(Long contactId);

	public List<Long> queryBuyerBrand(@Param("memberId") Long memberId, @Param("supplierId") Long supplierId);

	public List<MemberBuyerVerifyDetailInfoDTO> queryVerifyInfo(@Param("memberId") Long memberId,
			@Param("modifyType") String modifyType);

	/**
	 * @param supplierId
	 * @return
	 */
	public List<Long> queryBuyerBrandBySupplierId(@Param("supplierId") Long supplierId);

	/**
	 * @param supplierId
	 * @param lg
	 * @return
	 */
	public List<Long> queryBuyerCategoryBySupplierId(@Param("supplierId") Long supplierId, @Param("brandId") Long lg);

}
