package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BuyerGradeChangeHistory;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.dto.BuyerFinanceExpDTO;
import cn.htd.membercenter.dto.BuyerFinanceHistoryDTO;
import cn.htd.membercenter.dto.BuyerFinanceSyncDTO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.BuyerGradeIntervalDTO;
import cn.htd.membercenter.dto.BuyerScoreIntervalDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGradeHistoryDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;

public interface MemberGradeDAO {
	public List<MemberGradeDTO> queryMemberGradeListInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO,
			@Param("pager") Pager<MemberGradeDTO> pager);

	public long queryMemberGradeInfoCount(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public List<MemberGradeDTO> queryMemberGradeListInfo4export(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public MemberGradeDTO queryMemberGradeInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public List<MemberGradeHistoryDTO> queryMemberGradeHistoryListInfo(
			@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO, @Param("pager") Pager<MemberGradeHistoryDTO> pager);

	public long queryMemberGradeHistoryListInfoCount(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public void updateMemberGradeInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public void insertMemberGradeHistoryInfo(@Param("bgch") BuyerGradeChangeHistory bgch);

	/**
	 * @param orderNo
	 * @param memberId
	 * @return
	 */
	public long hasCalculateOrder(@Param("orderNo") String orderNo, @Param("memberId") long memberId);

	/**
	 * @param memberId
	 * @return
	 */
	public BuyerGradeInfoDTO getHTDMemberGrade(@Param("memberId") long memberId);

	public List<BuyerScoreIntervalDTO> queryMemberScoreSetList(
			@Param("buyerScoreIntervalDTO") BuyerScoreIntervalDTO buyerScoreIntervalDTO);

	public void insertPointHistoryInfo(@Param("bgch") BuyerPointHistory bgch);

	public List<MemberGradeDTO> selectMemberGrade(@Param("buyerGrade") int buyerGrade);

	/**
	 * @return
	 */
	public List<BuyerGradeIntervalDTO> queryBuyerGradeList();

	public void updateByPrimaryKeySelective(BuyerGradeInfoDTO record);

	/**
	 * @return
	 */
	public List<BuyerFinanceSyncDTO> getFinanceDailyAmountSync();

	/**
	 * @param tmpModel
	 * @return
	 */
	public List<BuyerFinanceHistoryDTO> getHTDUserDailyFinanceHis(@Param("bfh") BuyerFinanceHistoryDTO tmpModel);

	/**
	 * @param hisModel
	 */
	public void saveHTDUserDailyFinanceHis(@Param("hisModel") BuyerFinanceHistoryDTO hisModel);

	/**
	 * @param syncModel
	 */
	public void saveBuyerFinanceSync(@Param("syncModel") BuyerFinanceSyncDTO syncModel);

	/**
	 * @param hisModel
	 */
	public void updateHTDUserDailyFinanceHis(@Param("hisModel") BuyerFinanceHistoryDTO hisModel);

	/**
	 * @param syncModel
	 */
	public void updateBuyerFinanceSync(@Param("syncModel") BuyerFinanceSyncDTO syncModel);

	/**
	 * 
	 */
	public List<MemberImportSuccInfoDTO> getHTDAllMemberCnt();

	/**
	 * @param buyerId
	 * @param pointType
	 * @return
	 */
	public BuyerFinanceExpDTO getHTDUserDailyAmount(@Param("buyerId") Long buyerId,
			@Param("pointType") String pointType);

	/**
	 * @param buyerFinanceExpDTO
	 */
	public void saveBuyerFinanceExp(@Param("buyerFinanceExpDTO") BuyerFinanceExpDTO buyerFinanceExpDTO);

	/**
	 * @param buyerGrade
	 * @return
	 */
	public List<MemberGradeDTO> selectMemberByGrade(@Param("buyerGrade") String buyerGrade);

	/**
	 * @param memberGradeModel
	 */
	public void insertGrade(@Param("buyerGrade") BuyerGradeInfoDTO memberGradeModel);

	/**
	 * @param memberGradeModel
	 */
	public void deleteGrade(@Param("buyerId") Long buyerId);

	public void modifyMemberGradeAndPackageTypeById(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	/**
	 * @return
	 */
	public int getYearCnt();

	public int getMonthCnt();

	/**
	 * @param buyerFinanceExpDTO
	 */
	public void updateBuyerFinanceExp(@Param("buyerFinanceExpDTO") BuyerFinanceExpDTO buyerFinanceExpDTO);
}
