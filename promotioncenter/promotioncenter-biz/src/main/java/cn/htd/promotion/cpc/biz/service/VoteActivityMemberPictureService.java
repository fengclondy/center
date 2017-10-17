package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberPictureResDTO;

import java.util.List;

/***
 * 会员店投票活动报名图片相关Service
 */
public interface VoteActivityMemberPictureService {

    List<VoteActivityMemberPictureResDTO> selectByVoteMemberId(Long voteMemberId);
}