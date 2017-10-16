package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.VoteActivityDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansForwardDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import com.google.common.collect.Lists;


@Service("voteActivityService")
public class VoteActivityServiceImpl implements VoteActivityService{
	
    @Resource
    private VoteActivityDAO voteActivityDAO;
    @Resource
    private VoteActivityMemberDAO voteActivityMemberDAO;
    @Resource
    private VoteActivityFansVoteDAO voteActivityFansVoteDAO;
    
    @Resource
    private VoteActivityFansForwardDAO voteActivityFansForwardDAO;

	@Override
	public ExecuteResult<String> saveVoteActivity(VoteActivityResDTO voteActivityResDTO) {
		
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(voteActivityResDTO==null){
			result.setErrorMessages(Lists.newArrayList("voteActivityResDTO为null"));
			return result;
		}
		ValidateResult va1lidateResult=DTOValidateUtil.validate(voteActivityResDTO);
		
		if(!va1lidateResult.isPass()){
			result.setErrorMessages(Lists.newArrayList(StringUtils.split(va1lidateResult.getErrorMsg(),DTOValidateUtil.ERROR_MSG_SEPERATOR)));
			return  result;
		}
		//校验时间先后
		
		if(voteActivityResDTO.getVoteEndTime().before(voteActivityResDTO.getVoteStartTime())){
			result.setErrorMessages(Lists.newArrayList("活动开始时间不能晚于活动结束时间"));
			return  result;
		}
		
		if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteSignUpStartTime())){
			result.setErrorMessages(Lists.newArrayList("报名开始时间不能晚于报名结束时间"));
			return  result;
		}
		
		if(voteActivityResDTO.getVoteSignUpStartTime().before(voteActivityResDTO.getVoteStartTime())){
			result.setErrorMessages(Lists.newArrayList("报名开始时间不能早于活动开始时间"));
			return  result;
		}
		
		
		if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteEndTime())){
			result.setErrorMessages(Lists.newArrayList("报名结束时间不能早于活动结束时间"));
			return  result;
		}
		
		//根据voteActivityResDTO.getVoteSignUpStartTime() 和 voteActivityResDTO.getVoteSignUpEndTime()查询是否有活动
		VoteActivityResDTO currentVoteAct=voteActivityDAO.queryVoteActivityByTime(voteActivityResDTO.getVoteSignUpStartTime(),
				voteActivityResDTO.getVoteSignUpEndTime());
		
		if(currentVoteAct!=null){
			result.setErrorMessages(Lists.newArrayList("同一时间段内，不可有多个投票活动，请确认！"));
			return  result;
		}
		
		try{
			if(voteActivityResDTO.getVoteId() != null){
				voteActivityDAO.updateByPrimaryKeySelective(voteActivityResDTO);
			}else{
				voteActivityDAO.insertSelective(voteActivityResDTO);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
		}
		return result;
	}


	@Override
	public ExecuteResult<VoteActivityResDTO> queryVoteActivityById(Long voteId) {
		ExecuteResult<VoteActivityResDTO> result=new ExecuteResult<VoteActivityResDTO>();
		if(voteId==null){
			result.setErrorMessages(Lists.newArrayList("voteId为null"));
			return  result;
		}
		VoteActivityResDTO voteActResDTO=voteActivityDAO.selectByPrimaryKey(voteId);
		result.setResult(voteActResDTO);
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page) {
		ExecuteResult<DataGrid<VoteActivityListResDTO>> result=new ExecuteResult<DataGrid<VoteActivityListResDTO>>();
		DataGrid<VoteActivityListResDTO> dataGrid=new DataGrid<VoteActivityListResDTO>();
		Long totalCount=voteActivityDAO.selectVoteActivityTotalCount();
		dataGrid.setTotal(totalCount);
		if(totalCount==null||totalCount<=0){
			List<VoteActivityListResDTO> resultList=Lists.newArrayList();
			//查询分页数据
			List<VoteActivityResDTO> voteActList=voteActivityDAO.selectPagedVoteActivity(page.getPageOffset(), page.getRows());
			if(CollectionUtils.isNotEmpty(voteActList)){
				for(VoteActivityResDTO voteActResDTO:voteActList){
					
					VoteActivityListResDTO voteActivityListResDTO=new VoteActivityListResDTO();
					
					List<Map> resultMapList=voteActivityMemberDAO.querySignupMemberCount(voteActResDTO.getVoteId());
					if(CollectionUtils.isNotEmpty(resultMapList)){
						for(Map resultMap:resultMapList){
							if(resultMap!=null&&MapUtils.isNotEmpty(resultMap)&&resultMap.get("sign_status")!=null){
								if("0".equals(resultMap.get("sign_status")+"")){
									int authShopCount=resultMap.get("c")==null?0:Integer.valueOf(resultMap.get("c")+"");
									voteActivityListResDTO.setAuthShopCount(authShopCount);
									continue;
								}
								
								if("1".equals(resultMap.get("sign_status")+"")){
									int signupShopCount=resultMap.get("c")==null?0:Integer.valueOf(resultMap.get("c")+"");
									voteActivityListResDTO.setSignupShopCount(signupShopCount);
									continue;
								}
							}
								
						}
						
					}
					Long forwardCount=voteActivityFansForwardDAO.selectVoteActivityForwardCount(voteActResDTO.getVoteId());
					voteActivityListResDTO.setForwardCount(forwardCount);
					
					int status=sentenceVoteActivityStatus(voteActResDTO.getVoteStartTime(),voteActResDTO.getVoteEndTime());
					voteActivityListResDTO.setStatus(status);
					
					voteActivityListResDTO.setVoteActivityName(voteActResDTO.getVoteName());
					//查询粉丝投票数量
					Long voteCount=voteActivityFansVoteDAO.selectVoteCountByActivityId(voteActResDTO.getVoteId());
					voteActivityListResDTO.setVoteCount(voteCount);
					voteActivityListResDTO.setVoteEndTime(voteActResDTO.getVoteEndTime());
					voteActivityListResDTO.setVoteStartTime(voteActResDTO.getVoteStartTime());
					
					resultList.add(voteActivityListResDTO);
				}
			}
			dataGrid.setRows(resultList);
		}
		result.setResult(dataGrid);
		return result;
	}
	
	private int sentenceVoteActivityStatus(Date startTime,Date endTime){
		if(startTime==null||endTime==null){
			return 0;
		}
		//1 进行中、2 未开始、3 已结束
		Date now=new Date();
		if(now.after(startTime)&&now.before(endTime)){
			return 1;
		}
		if(now.after(endTime)){
			return 3;
		}
		
		if(now.before(startTime)){
			return 2;
		}
		
		return 0;
	}

	  /***
     * 查询当前活动
     * @return
     */
    public VoteActivityResDTO selectCurrentActivity(){
        return voteActivityDAO.selectCurrentActivity();
    }
}
