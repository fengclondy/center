package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberGradeRuleHistoryDTO;
import cn.htd.membercenter.dto.MemberScoreSetDTO;

public interface MemberScoreSetService {

	/**
	 * 根据区间类型查询会员等级区间计算规则
	 * 
	 * @param memberScoreSetDTO
	 *            intervalType
	 * @return
	 */
	public ExecuteResult<List<MemberScoreSetDTO>> queryMemberScoreSetList(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 查询会员等级区间经验规则
	 * 
	 * @param memberScoreSetDTO
	 *            intervalType
	 * @return
	 */
	public ExecuteResult<List<MemberScoreSetDTO>> queryMemberScoreRuleList(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 保存会员消费金额区间规则信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMemberScoreSet(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 保存会员金融区间规则信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMemberScoreRule(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 更新会员等级规则信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberScoreSet(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 更新会员经验规则信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberScoreRule(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 查询会员等级规则履历信息列表
	 * 
	 * @param memberScoreSetDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberGradeRuleHistoryDTO>> queryMemberScoreRuleHistory(
			MemberScoreSetDTO memberScoreSetDTO, Pager<MemberGradeRuleHistoryDTO> pager);

	/**
	 * 保存会员规则变更履历信息
	 * 
	 * @param memberGradeRuleHistoryDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMemberScoreRuleHistory(MemberGradeRuleHistoryDTO memberGradeRuleHistoryDTO);

	/**
	 * 保存会员权利比例信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberScoreWeight(MemberScoreSetDTO memberScoreSetDTO);

	/**
	 * 查询会员权重比例信息
	 * 
	 * @param memberScoreSetDTO
	 * @return
	 */
	public ExecuteResult<MemberScoreSetDTO> queryMemberScoreWeight(MemberScoreSetDTO memberScoreSetDTO);
}
