package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

/**
 * 投票活动-活动相关服务
 * @author zhengy
 * @date 2017-10-12
 */
public interface VoteActivityAPI {

	/**
	 * 创建投票活动
	 * 
	 * @param VoteActivityResDTO
	 * @return
	 */
	ExecuteResult<String> saveVoteActivity(VoteActivityResDTO voteActivityResDTO);
	
	
	/**
	 * 根据主键查询投票活动
	 * 
	 * @param voteId
	 * @return
	 */
	ExecuteResult<VoteActivityResDTO> queryVoteActivityById(Long voteId);
	
	/**
	 * 查询投票活动列表
	 * 
	 * @param actStatus  1 进行中、2 未开始、3 已结束
	 * @param voteActivityListReqDTO
	 * @return
	 */
	ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus);
	
	VoteActivityResDTO selectCurrentActivity();
	
}
