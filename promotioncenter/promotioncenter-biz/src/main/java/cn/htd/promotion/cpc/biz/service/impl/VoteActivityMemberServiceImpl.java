package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityMemberService;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/***
 * 会员店投票活动报名Service实现类
 */
@Service("voteActivityMemberService")
public class VoteActivityMemberServiceImpl implements VoteActivityMemberService{

    @Resource
    private VoteActivityMemberDAO voteActivityMemberDAO;

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
    public int updateByPrimaryKeySelective(VoteActivityMemberResDTO voteActivityMemberResDTO){
        return voteActivityMemberDAO.updateByPrimaryKeySelective(voteActivityMemberResDTO);
    }
}