package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;
import cn.htd.promotion.cpc.dto.response.ImportVoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

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
	 * 删除该投票活动
	 * 
	 * @param VoteActivityResDTO
	 * @return
	 */
	ExecuteResult<String> deleteVoteActivity(Long voteId);
	
	/**
	 * 删除该投票活动
	 * 
	 * @param VoteActivityResDTO
	 * @return
	 */
	ExecuteResult<String> updateVoteActivity(VoteActivityResDTO voteActivityResDTO);
	
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
	ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus);
	
	VoteActivityResDTO selectCurrentActivity();
	
	/**
	 * 查看投票列表详细
	 * 
	 * @param page
	 * @param voteActivityMemListReqDTO
	 * @return
	 */
	ExecuteResult<DataGrid<VoteActivityMemListResDTO>> queryPagedVoteActivityMemberList(Pager page,VoteActivityMemListReqDTO voteActivityMemListReqDTO);
	
	/**
	 * 查询投票会员详细
	 * 
	 * @param voteMemberId
	 * @return
	 */
	ExecuteResult<VoteActivityMemResDTO> queryVoteActivityMemberDetail(Long voteMemberId);
	
	/**
	 * 导入投票活动会员
	 * 
	 * @param memberCodeList
	 * @return
	 */
	ExecuteResult<ImportVoteActivityMemResDTO> importVoteActivityMember(List<VoteActivityMemReqDTO> list);
	
	/**
	 * 导入投票活动会员
	 * 
	 * @param memberCodeList
	 * @return
	 */
	ExecuteResult<List<VoteActivityMemListResDTO>> ExportVoteActivityMember(VoteActivityMemListReqDTO voteActivityMemListReqDTO);
}
