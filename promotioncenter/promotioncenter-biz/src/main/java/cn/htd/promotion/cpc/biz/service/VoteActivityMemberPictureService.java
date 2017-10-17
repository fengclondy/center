package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberPictureResDTO;

import java.util.List;
import java.util.Map;

/***
 * 会员店投票活动报名图片相关Service
 */
public interface VoteActivityMemberPictureService {

    /***
     * 查询投票活动图片集合
     * @param voteMemberId
     * @return
     */
    List<VoteActivityMemberPictureResDTO> selectByVoteMemberId(Long voteMemberId);


    /***
     * 保存会员店报名活动相关图片
     * @param params
     */
    void saveVoteMemberPicture(Map<String,Object> params);
}