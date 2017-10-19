package cn.htd.promotion.cpc.api.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.VoteActivityAPI;
import cn.htd.promotion.cpc.biz.service.VoteActivityMemberPictureService;
import cn.htd.promotion.cpc.biz.service.VoteActivityMemberService;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.NUtils;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;
import cn.htd.promotion.cpc.dto.response.ImportVoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投票活动相关API
 * @author zhengy
 * @date 2017-10-16
 */
@Service("voteActivityAPI")
public class VoteActivityAPIImpl implements VoteActivityAPI {

    private Logger logger = LoggerFactory.getLogger(VoteActivityAPIImpl.class);

    @Resource
    private VoteActivityService voteActivityService;

    @Resource
    private VoteActivityMemberService voteActivityMemberService;

    @Resource
    private VoteActivityMemberPictureService memberPictureService;

    @Override
    public ExecuteResult<String> saveVoteActivity(VoteActivityResDTO voteActivityResDTO) {
        return voteActivityService.saveVoteActivity(voteActivityResDTO);
    }


    @Override
    public ExecuteResult<VoteActivityResDTO> queryVoteActivityById(Long voteId) {
        return voteActivityService.queryVoteActivityById(voteId);
    }

    @Override
    public ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus) {
        return voteActivityService.queryVoteActivityList(page,voteActName,actStatus);
    }
    
    @Override
	public ExecuteResult<DataGrid<VoteActivityMemListResDTO>> queryVoteActivityMemberList(Pager page,
			VoteActivityMemListReqDTO voteActivityMemListReqDTO) {
		return voteActivityService.queryPagedVoteActivityMemberList(page, voteActivityMemListReqDTO);
	}

    /***
     * 查询当前活动
     * @return
     */
    public ExecuteResult<VoteActivityResDTO> selectCurrentActivity(){
        // 返回对象
        ExecuteResult<VoteActivityResDTO> result = new ExecuteResult<VoteActivityResDTO>();
        try{
            // 正确返回
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            result.setResult(voteActivityService.selectCurrentActivity());
        }catch (Exception e){
            // 打印错误消息
            e.printStackTrace();
            logger.error("VoteActivityAPI方法selectCurrentActivity调用出错，信息{}",e.getMessage());

            result.setCode(ResultCodeEnum.ERROR.getMsg());
        }
        return result;
    }

    /***
     * 根据活动ID和会员编码查询会员店投票活动报名编码
     * @param voteId
     * @param memberCode
     * @return
     */
    public ExecuteResult<VoteActivityMemberResDTO> selectByVoteIdAndMemberCode(Long voteId,String memberCode){
        // 返回对象
        ExecuteResult<VoteActivityMemberResDTO> result = new ExecuteResult<VoteActivityMemberResDTO>();
        try{
            // 正确返回
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            // 返回值
            VoteActivityMemberResDTO memberResDTO = voteActivityMemberService.selectByVoteIdAndMemberCode(voteId,memberCode);
            // 如果不为空则查询相关图片
            if(memberResDTO != null && !NUtils.isEmpty(memberResDTO.getVoteMemberId())){
                // 相关图片集合
                memberResDTO.setMemberPictureResDTOList(memberPictureService.selectByVoteMemberId(memberResDTO.getVoteMemberId()));
            }

            result.setResult(memberResDTO);
        }catch (Exception e){
            // 打印错误消息
            e.printStackTrace();
            logger.error("VoteActivityAPI方法selectByVoteIdAndMemberCode调用出错，信息{}",e.getMessage());

            result.setCode(ResultCodeEnum.ERROR.getMsg());
        }
        return result;
    }

    /***
     * 保存会员店投票活动报名信息
     * @param params
     * @return
     */
    public ExecuteResult<Boolean> saveVoteActivityMember(Map<String,Object> params){
        // 返回对象
        ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
        try{
            // 正确返回
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            // 正确返回
            result.setResult(true);
            // 实例化保存对象
            VoteActivityMemberResDTO memberResDTO = new VoteActivityMemberResDTO();
            // 投票活动主键
            memberResDTO.setVoteMemberId(NUtils.convertToLong(params.get("voteMemberId")));
            // 修改已报名
            memberResDTO.setSignStatus(1);
            // 报名时间为当前时间
            memberResDTO.setSignUpTime(new Date());
            // 活动宣言
            memberResDTO.setMemberActivityDec(NUtils.convertToStr(params.get("desinfo")));
            // 创建人和创建ID
            memberResDTO.setModifyId(NUtils.convertToLong(params.get("createId")));
            memberResDTO.setModifyName(NUtils.convertToStr(params.get("createName")));
            // 更新报名信息
            voteActivityMemberService.updateByPrimaryKeySelective(memberResDTO);
            // 保存图片
            memberPictureService.saveVoteMemberPicture(params);
        }catch (Exception e){
            // 打印错误消息
            e.printStackTrace();
            logger.error("VoteActivityAPI方法saveVoteActivityMember调用出错，信息{}",e.getMessage());

            result.setCode(ResultCodeEnum.ERROR.getMsg());
        }
        return result;
    }


	@Override
	public ExecuteResult<ImportVoteActivityMemResDTO> importVoteActivityMember(List<VoteActivityMemReqDTO> list) {
		return voteActivityService.importVoteActivityMember(list);
	}


	/**
	 * 查询活动会员店导出数据
	 * 5万条 以会员店报名时间desc排序
	 */
	@Override
	public ExecuteResult<List<VoteActivityMemListResDTO>> exportVoteActivityMember(
			VoteActivityMemListReqDTO voteActivityMemListReqDTO) {
		return voteActivityService.ExportVoteActivityMember(voteActivityMemListReqDTO);
	}


	/**
	 * 删除活动
	 */
	@Override
	public ExecuteResult<String> deleteVoteActivity(Long voteId) {
		return voteActivityService.deleteVoteActivity(voteId);
	}


	/**
	 * 活动会员店操作 (删除  通过  驳回)
	 */
	@Override
	public ExecuteResult<String> updateVoteActivityMember(Long voteMemberId, String deleteFlag, String auditStatus) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		VoteActivityMemberResDTO voteActivityMemberResDTO = new VoteActivityMemberResDTO();
		voteActivityMemberResDTO.setVoteMemberId(voteMemberId);
		if (StringUtils.isNotEmpty(deleteFlag) && "1".equals(deleteFlag)) {
			voteActivityMemberResDTO.setDeleteFlag(1);
		}
		if (StringUtils.isNotEmpty(auditStatus)) {
			try {
				voteActivityMemberResDTO.setAuditStatus(Integer.parseInt(auditStatus));
			} catch (Exception e) {
				e.printStackTrace();
				result.setErrorMessages(Lists.newArrayList(e.getMessage()));
				return result;
			}
		}
		try {
			voteActivityMemberService.updateByPrimaryKeySelective(voteActivityMemberResDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
			return result;
		}
		
		return result;
	}
	
}
