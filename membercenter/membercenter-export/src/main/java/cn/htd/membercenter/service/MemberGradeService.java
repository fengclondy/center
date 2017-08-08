package cn.htd.membercenter.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.HTDUserLowestShowDto;
import cn.htd.membercenter.dto.HTDUserUpgradeDistanceDto;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGradeHistoryDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;

public interface MemberGradeService {

	/**
	 * 根据会员名称，手机号，会员类型，账户类型查询会员等级分页信息
	 * 
	 * @param RoleId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberGradeDTO>> queryMemberGradeListInfo(MemberBaseDTO memberBaseDTO,
			Pager<MemberGradeDTO> pager);

	/**
	 * 根据会员编码查询会员等级信息
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<MemberGradeDTO> queryMemberGradeInfo(MemberBaseDTO memberBaseDTO);

	/**
	 * 根据会员名称，手机号，会员类型，账户类型查询会员等级分页信息
	 * 
	 * @param RoleId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberGradeHistoryDTO>> queryMemberGradeHistoryListInfo(MemberBaseDTO memberBaseDTO,
			Pager<MemberGradeHistoryDTO> pager);

	/**
	 * 根据会员编码更改收费会员类型
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<Boolean> modifyMemberGrade4vip(MemberBaseDTO memberBaseDTO);

	/**
	 * 根据会员编码更改会员等级信息
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<Boolean> modifyMemberGrade4Grade(MemberBaseDTO memberBaseDTO);

	/**
	 * 查询会员等级导出信息
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<List<MemberGradeDTO>> queryMemberGradeListInfo4export(MemberBaseDTO memberBaseDTO);

	/**
	 * 计算订单购买经验值
	 * 
	 * @param orderNo
	 * @param memberId
	 * @param orderTime
	 * @param orderPrice
	 * @param orderStatus
	 * @return
	 */
	ExecuteResult<Boolean> modifyUserGradeExp(String orderNo, String memberCode, Date orderTime, BigDecimal orderPrice,
			String orderStatus);

	/**
	 * 查詢會員等級信息列表
	 */
	public ExecuteResult<List<MemberGradeDTO>> selectMemberGrade(int buyerGrade);

	/**
	 * 同步金融产品当日余额
	 * 
	 * @param date
	 * @throws Exception
	 */
	public void syncFinanceDailyAmount(Date date) throws Exception;

	/**
	 * @param memberGradeModel
	 * @param targetDt
	 */
	public void upgradeHTDUserGrade(MemberImportSuccInfoDTO memberImportSuccInfoDTO, BuyerGradeInfoDTO memberGradeModel,
			Date targetDt);

	/**
	 * @param memberGradeModel
	 */
	public void downgradeHTDUserGrade(MemberImportSuccInfoDTO memberImportSuccInfoDTO,
			BuyerGradeInfoDTO memberGradeModel);

	/**
	 * @param memberImportSuccInfoDTO
	 * @param memberGradeModel
	 */
	public void upgradeHTDUserGradeYearly(MemberImportSuccInfoDTO memberImportSuccInfoDTO,
			BuyerGradeInfoDTO memberGradeModel);

	/**
	 * 更新已升级标志控制动画
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<Boolean> updateIsUpgrade(long memberId);

	public ExecuteResult<List<MemberGradeDTO>> selectMemberByGrade(String buyerGrade);

	/**
	 * 根据会员id更新会员等级，会员类型以及会员套餐类型
	 * 
	 * @param memberGradeDTO
	 * @return
	 */
	public ExecuteResult<Boolean> modifyMemberGradeAndPackageTypeById(MemberBaseDTO memberBaseDTO);

	public ExecuteResult<Boolean> insertGrade(BuyerGradeInfoDTO dto);

	/**
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public HTDUserLowestShowDto getHTDUserGradeLowestInfo(Long memberId) throws Exception;

	/**
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<HTDUserUpgradeDistanceDto> calculateHTDUserUpgradePath(Long memberId) throws Exception;
}
