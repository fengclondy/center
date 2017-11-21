package cn.htd.promotion.cpc.biz.service.impl;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.biz.dao.VoteActivityFansForwardDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberPictureDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityMemberService;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;

import java.util.HashMap;

/***
 * 会员店投票活动报名Service实现类
 */
@Service("voteActivityMemberService")
public class VoteActivityMemberServiceImpl implements VoteActivityMemberService{
	
	private Logger logger = LoggerFactory.getLogger(VoteActivityMemberServiceImpl.class);

    @Resource
    private VoteActivityMemberDAO voteActivityMemberDAO;

    @Resource
    private VoteActivityFansForwardDAO voteActivityFansForwardDAO;
    
    @Resource
    private VoteActivityFansVoteDAO voteActivityFansVoteDAO;
    
    @Resource
    private VoteActivityMemberPictureDAO voteActivityMemberPictureDAO;

    /***
     * 根据活动ID和会员编码查询投票活动
     * @param voteId
     * @param memberCode
     * @return
     */
    public VoteActivityMemberResDTO selectByVoteIdAndMemberCode(Long voteId, String memberCode){
        return voteActivityMemberDAO.selectByVoteIdAndMemberCode(voteId,memberCode);
    }

    /***
     * 根据主键修改会员店投票活动报名信息
     * @param voteActivityMemberResDTO
     * @return
     */
    @Transactional(readOnly = false)
    public int updateByPrimaryKeySelective(VoteActivityMemberResDTO voteActivityMemberResDTO){
    	logger.info("updateByPrimaryKeySelective 方法已进入 voteActivityMemberResDTO=" + voteActivityMemberResDTO.toString());
    	int i = voteActivityMemberDAO.updateByPrimaryKeySelective(voteActivityMemberResDTO);
    	if (voteActivityMemberResDTO.getDeleteFlag() != null && voteActivityMemberResDTO.getDeleteFlag() == 1) {
    		Long voteMemberId = voteActivityMemberResDTO.getVoteMemberId();
    		voteActivityFansForwardDAO.deleteForwordInfoByVoteMemberId(voteMemberId);
    		voteActivityFansVoteDAO.deleteVoteInfoByVoteMemberId(voteMemberId);
    		voteActivityMemberPictureDAO.deleteByVoteMemberId(voteMemberId);
    	}
    	logger.info("updateByPrimaryKeySelective 方法已结束 ");
        return i;
    }

    /***
     * 根据活动ID，会员店编码获取当前会员店的投票排情况
     * @param voteId
     * @param memberCode
     * @return
     */
    public HashMap<String, Object> selectMemberRankingByMemberCode(Long voteId,String memberCode){
        return voteActivityMemberDAO.selectMemberRankingByMemberCode(voteId,memberCode);
    }

    /***
     * 根据会员报名编码查询转发数
     * @param voteMemberId
     * @return
     */
    public Long selectForwordCountByVMId(Long voteMemberId){
        return voteActivityFansForwardDAO.selectForwordCountByVMId(voteMemberId);
    }
}