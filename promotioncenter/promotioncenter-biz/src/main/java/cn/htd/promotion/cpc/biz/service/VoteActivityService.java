package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberVoteDetailDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import java.util.Date;

/**
 * 投票活动-活动相关服务
 * @author zhengy
 * @date 2017-10-12
 */
public interface VoteActivityService {

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
	 * @param voteActivityListReqDTO
	 * @return
	 */
	ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page);
	
	VoteActivityResDTO selectCurrentActivity();
	
}
