package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberPictureResDTO;

import java.util.List;

public interface VoteActivityMemberPictureDAO {
    int deleteByPrimaryKey(Long pictureId);

    int insert(VoteActivityMemberPictureResDTO record);

    int insertSelective(VoteActivityMemberPictureResDTO record);

    VoteActivityMemberPictureResDTO selectByPrimaryKey(Long pictureId);

    int updateByPrimaryKeySelective(VoteActivityMemberPictureResDTO record);

    int updateByPrimaryKey(VoteActivityMemberPictureResDTO record);

    List<VoteActivityMemberPictureResDTO> selectByVoteMemberId(Long voteMemberId);
    
    int deleteByVoteMemberId(Long voteMemberId);
}