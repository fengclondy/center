package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberPictureDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityMemberPictureService;
import cn.htd.promotion.cpc.common.util.NUtils;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberPictureResDTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/***
 * 会员店投票活动报名图片相关Service
 */
public class VoteActivityMemberPictureServiceImpl implements VoteActivityMemberPictureService{

    @Resource
    private VoteActivityMemberPictureDAO voteActivityMemberPictureDAO;

    /***
     * 查询会员店报名活动图片集合
     * @param voteMemberId
     * @return
     */
    public List<VoteActivityMemberPictureResDTO> selectByVoteMemberId(Long voteMemberId){
        return voteActivityMemberPictureDAO.selectByVoteMemberId(voteMemberId);
    }

    /***
     * 保存会员店报名活动相关图片
     * @param params
     */
    public void saveVoteMemberPicture(Map<String,Object> params){
        // 暂存
        VoteActivityMemberPictureResDTO memberPictureResDTO = null;
        // 会员店主键
        Long voteMemberId = NUtils.convertToLong(params.get("voteMemberId"));
        // 创建人和创建ID
        Long createId = NUtils.convertToLong(params.get("createId"));
        String createName = NUtils.convertToStr(params.get("createName"));
        // 图片地址集合
        String[] imgNames = (String[])params.get("imgNames");
        // 循环保存
        for(String imgName:imgNames){
            memberPictureResDTO = new VoteActivityMemberPictureResDTO();
            memberPictureResDTO.setVoteMemberId(voteMemberId);
            memberPictureResDTO.setPictureUrl(imgName);
            memberPictureResDTO.setDeleteFlag(Byte.decode("0"));
            memberPictureResDTO.setCreateId(createId);
            memberPictureResDTO.setCreateName(createName);
            memberPictureResDTO.setModifyId(createId);
            memberPictureResDTO.setModifyName(createName);
            voteActivityMemberPictureDAO.insert(memberPictureResDTO);
        }
        // 判断是否有删除图片集合
        if(!NUtils.isEmpty(params.get("delImgIds"))){
            // 删除集合
            String[] delImgIds = (String[]) params.get("delImgIds");
            // 循环删除
            for(String id:delImgIds) {
                memberPictureResDTO = new VoteActivityMemberPictureResDTO();
                memberPictureResDTO.setPictureId(NUtils.convertToLong(id));
                memberPictureResDTO.setDeleteFlag(Byte.decode("1"));
                memberPictureResDTO.setModifyId(createId);
                memberPictureResDTO.setModifyName(createName);
                voteActivityMemberPictureDAO.updateByPrimaryKeySelective(memberPictureResDTO);
            }
        }
    }
}