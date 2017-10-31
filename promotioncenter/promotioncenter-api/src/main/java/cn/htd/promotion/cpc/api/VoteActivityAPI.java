package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;
import cn.htd.promotion.cpc.dto.response.ImportVoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import java.util.List;
import java.util.Map;

/**
 * 投票活动-活动相关服务
 * @author zhengy
 * @date 2017-10-12
 */
public interface VoteActivityAPI {

	/**
	 * 创建投票活动
	 * @param voteActivityResDTO
	 * @return
     */
	ExecuteResult<String> saveVoteActivity(VoteActivityResDTO voteActivityResDTO);
	
	/**
	 * 删除投票活动
	 * @param voteActivityResDTO
	 * @return
     */
	ExecuteResult<String> deleteVoteActivity(Long voteId);
	
	/**
	 * 编辑投票活动
	 * @param voteActivityResDTO
	 * @return
     */
	ExecuteResult<String> updateVoteActivity(VoteActivityResDTO voteActivityResDTO);

	/**
	 * 会员店列表操作(通过 驳回删除)
	 * @param 
	 * @return
     */
	ExecuteResult<String> updateVoteActivityMember(Long voteMemberId, String deleteFlag, String auditStatus);
	
	/**
	 * 根据主键查询投票活动
	 * 
	 * @param voteId
	 * @return
	 */
	ExecuteResult<VoteActivityResDTO> queryVoteActivityById(Long voteId);

	/**
	 *
	 * 查询投票活动列表
	 *
	 * @param page
	 * @param voteActName
	 * @param actStatus  1 进行中、2 未开始、3 已结束
     * @return
     */
	ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus);

	/**
	 *
	 * 查询投票活动会员店列表
	 *
	 * @param page
	 * @param voteActName
	 * @param actStatus  1 进行中、2 未开始、3 已结束
    * @return
    */
	ExecuteResult<DataGrid<VoteActivityMemListResDTO>> queryVoteActivityMemberList(Pager page, VoteActivityMemListReqDTO voteActivityMemListReqDTO);
	
	/***
	 * 查询当前活动
	 * @return
	 */
	ExecuteResult<VoteActivityResDTO> selectCurrentActivity();

	/***
	 * 根据活动ID和会员编码查询会员店投票活动报名编码
	 * @param voteId
	 * @param memberCode
	 * @return
	 */
	ExecuteResult<VoteActivityMemberResDTO> selectByVoteIdAndMemberCode(Long voteId,String memberCode);

	/***
	 * 根据活动会员id查询投票活动
	 * @param voteMemberId
	 * @return
	 */
	ExecuteResult<VoteActivityMemResDTO> selectByVoteMemberCode(Long voteId, Long voteMemberId);

	/***
	 * 保存会员店投票活动报名信息
	 * @param params
	 * @return
	 */
	ExecuteResult<Boolean> saveVoteActivityMember(Map<String,Object> params);
	
	/***
	 * 投票活动批量导入会员店
	 * @param list
	 * @return
	 */
	ExecuteResult<ImportVoteActivityMemResDTO> importVoteActivityMember(List<VoteActivityMemReqDTO> list);
	
	/***
	 * 投票活动批量导出会员店
	 * @param params 
	 * 最高5w条数据   根据报名时间排序 降序
	 * @return
	 */
	ExecuteResult<List<VoteActivityMemListResDTO>> exportVoteActivityMember(VoteActivityMemListReqDTO voteActivityMemListReqDTO);

	/***
	 * 查询会员店投票信息
	 * @param voteId
	 * @param memberCode
	 * @return
	 */
	ExecuteResult<Map<String,String>> selectMemberVotesData(Long voteId,String memberCode);
}
