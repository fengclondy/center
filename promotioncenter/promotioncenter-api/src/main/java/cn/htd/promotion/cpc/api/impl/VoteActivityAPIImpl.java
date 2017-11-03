package cn.htd.promotion.cpc.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import com.google.common.collect.Lists;

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
     * 根据活动会员店id
     * @param voteMemberId
     * @return
     */
    public ExecuteResult<VoteActivityMemResDTO> selectByVoteMemberCode(Long voteId, Long voteMemberId){
        // 返回对象
        ExecuteResult<VoteActivityMemResDTO> result = new ExecuteResult<VoteActivityMemResDTO>();
        if (voteId == null || voteMemberId == null) {
        	result.setErrorMessages(Lists.newArrayList("参数未空 请检查参数"));
        	return result;
        }
        try{
            // 返回值
            result = voteActivityService.queryVoteActivityMemberDetail(voteId, voteMemberId);
        }catch (Exception e){
            // 打印错误消息
            e.printStackTrace();
            logger.error("VoteActivityAPI方法selectByVoteIdAndMemberCode调用出错，信息{}",e.getMessage());
            result.setErrorMessages(Lists.newArrayList("查询详情异常"));
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

            result.setCode(ResultCodeEnum.ERROR.getCode());
        }
        return result;
    }

    /***
     * 查询会员店投票信息
     * @param voteId
     * @param memberCode
     * @return
     */
    public ExecuteResult<Map<String,String>> selectMemberVotesData(Long voteId,String memberCode){
        // 返回对象
        ExecuteResult<Map<String,String>> result = new ExecuteResult<Map<String,String>>();
        try{
            // 正确返回
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            // 返回Map
            Map<String,String> resultMap = new HashMap<>();
            // 查询活动时间确定活动状态
            ExecuteResult<VoteActivityResDTO> voteActivityRes = queryVoteActivityById(voteId);
            if(voteActivityRes != null && voteActivityRes.getResult() != null){
                // 活动
                VoteActivityResDTO voteActivity = voteActivityRes.getResult();
                // 取得时间
                Date date = new Date();
                // 根据时间判断状态
                if(!NUtils.isEmpty(voteActivity.getVoteSignUpStartTime()) && !NUtils.isEmpty(voteActivity.getVoteEndTime())){
                    if(date.before(voteActivity.getVoteSignUpStartTime())){
                        resultMap.put("activityStatus","未开始");
                    }else if(date.after(voteActivity.getVoteSignUpStartTime()) && date.before(voteActivity.getVoteEndTime())){
                        resultMap.put("activityStatus","进行中");
                    }else{
                        resultMap.put("activityStatus","已结束");
                    }
                }else{
                    resultMap.put("activityStatus","已结束");
                }
            }
            // 获取当前会员店排名情况
            Map<String, Object> rankingByMemberCode = this.voteActivityMemberService.selectMemberRankingByMemberCode(voteId, memberCode);
            if (rankingByMemberCode != null) {
                resultMap.put("ranking",NUtils.convertToStr(rankingByMemberCode.get("rowNum")));
                resultMap.put("voteNum",NUtils.convertToStr(rankingByMemberCode.get("voteNum")));
            }else{
                resultMap.put("ranking","0");
                resultMap.put("voteNum","0");
            }

            // 返回值
            VoteActivityMemberResDTO memberResDTO = voteActivityMemberService.selectByVoteIdAndMemberCode(voteId,memberCode);
            // 如果不为空则查询转发数
            if(memberResDTO != null && !NUtils.isEmpty(memberResDTO.getVoteMemberId())){
                // 转发数
                String forwardNum = NUtils.convertToStr(voteActivityMemberService.selectForwordCountByVMId(memberResDTO.getVoteMemberId()));
                resultMap.put("forwardNum",forwardNum);
                resultMap.put("vendorName",memberResDTO.getVendorName());
                // 判断审核状态
                if(memberResDTO.getAuditStatus() !=null) {
                    if (memberResDTO.getAuditStatus().intValue() == 0) {
                        resultMap.put("auditStatus", "待审核");
                    } else if (memberResDTO.getAuditStatus().intValue() == 1) {
                        resultMap.put("auditStatus", "已通过");
                    } else if (memberResDTO.getAuditStatus().intValue() == 2) {
                        resultMap.put("auditStatus", "已驳回");
                    }
                }else{
                    resultMap.put("auditStatus", "");
                }
            }
            result.setResult(resultMap);

        }catch (Exception e){
            // 打印错误消息
            e.printStackTrace();
            logger.error("VoteActivityAPI方法selectMemberVotesData调用出错，信息{}",e.getMessage());

            result.setCode(ResultCodeEnum.ERROR.getCode());
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
		return voteActivityService.exportVoteActivityMember(voteActivityMemListReqDTO);
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
		if (voteMemberId == null) {
			result.setErrorMessages(Lists.newArrayList("参数值为null"));
			return result;
		}
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
				logger.error("updateVoteActivityMember调用出错，信息{}",e.getMessage());
				result.setErrorMessages(Lists.newArrayList(e.getMessage()));
				return result;
			}
		}
		try {
			voteActivityMemberService.updateByPrimaryKeySelective(voteActivityMemberResDTO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateVoteActivityMember调用出错，信息{}",e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
			return result;
		}
		
		return result;
	}


	@Override
	public ExecuteResult<String> updateVoteActivity(VoteActivityResDTO voteActivityResDTO) {
		
		return voteActivityService.updateVoteActivity(voteActivityResDTO);
	}
	
}
