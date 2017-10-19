package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.VoteActivityDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansForwardDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;
import cn.htd.promotion.cpc.dto.response.ImportVoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;


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
    @Resource
    private GeneratorUtils generatorUtils;

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
			result.setErrorMessages(Lists.newArrayList("投票开始时间不能晚于投票结束时间"));
			return  result;
		}
		
		if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteSignUpStartTime())){
			result.setErrorMessages(Lists.newArrayList("报名开始时间不能晚于报名结束时间"));
			return  result;
		}
		
		if(voteActivityResDTO.getVoteSignUpStartTime().after(voteActivityResDTO.getVoteStartTime())){
			result.setErrorMessages(Lists.newArrayList("报名开始时间不能早于投票开始时间"));
			return  result;
		}
		
		
		if(voteActivityResDTO.getVoteSignUpEndTime().after(voteActivityResDTO.getVoteEndTime())){
			result.setErrorMessages(Lists.newArrayList("报名结束时间不能早于投票结束时间"));
			return  result;
		}
		
		//根据voteActivityResDTO.getVoteSignUpStartTime() 和 voteActivityResDTO.getVoteSignUpEndTime()查询是否有活动
		int voceAcitvityNum = voteActivityDAO.queryVoteActivityByTime(voteActivityResDTO.getVoteSignUpStartTime(),
				voteActivityResDTO.getVoteEndTime());
		
		if(voceAcitvityNum > 0){
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
	public ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus) {
		ExecuteResult<DataGrid<VoteActivityListResDTO>> result=new ExecuteResult<DataGrid<VoteActivityListResDTO>>();
		DataGrid<VoteActivityListResDTO> dataGrid=new DataGrid<VoteActivityListResDTO>();
		Long totalCount=voteActivityDAO.selectVoteActivityTotalCount(voteActName,actStatus);
		dataGrid.setTotal(totalCount);
		if(totalCount!=null&&totalCount>0L){
			List<VoteActivityListResDTO> resultList=Lists.newArrayList();
			//查询分页数据
			List<VoteActivityResDTO> voteActList=voteActivityDAO.selectPagedVoteActivity(page.getPageOffset(), page.getRows(), voteActName,actStatus);
			if(CollectionUtils.isNotEmpty(voteActList)){
				for(VoteActivityResDTO voteActResDTO:voteActList){
					
					VoteActivityListResDTO voteActivityListResDTO=new VoteActivityListResDTO();
					try{
						voteActivityMemberDAO.querySignupMemberCount(voteActResDTO.getVoteId());
					}catch(Exception e){
						e.printStackTrace();
					}
					
					List<HashMap<String, Object>> resultMapList=voteActivityMemberDAO.querySignupMemberCount(voteActResDTO.getVoteId());
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
					voteActivityListResDTO.setVoteId(voteActResDTO.getVoteId());
					voteActivityListResDTO.setVoteSignUpStartTime(voteActResDTO.getVoteSignUpStartTime());
					voteActivityListResDTO.setVoteSignUpEndTime(voteActResDTO.getVoteSignUpEndTime());
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


	@Override
	public ExecuteResult<DataGrid<VoteActivityMemListResDTO>> queryPagedVoteActivityMemberList(
			Pager page, VoteActivityMemListReqDTO voteActivityMemListReqDTO) {
		ExecuteResult<DataGrid<VoteActivityMemListResDTO>> result=new ExecuteResult<DataGrid<VoteActivityMemListResDTO>>();
		DataGrid<VoteActivityMemListResDTO> datagrid=new DataGrid<VoteActivityMemListResDTO>();
		if(page==null||voteActivityMemListReqDTO==null){
			result.setErrorMessages(Lists.newArrayList("参数为空"));
		}
		voteActivityMemListReqDTO.setPageSize(page.getRows());
		voteActivityMemListReqDTO.setStart(page.getPageOffset());
		
		Long totalCount=voteActivityMemberDAO.queryTotalSignupMemberInfo(voteActivityMemListReqDTO);
		if(totalCount!=null&&totalCount>0){
			List<VoteActivityMemListResDTO>  resultList=voteActivityMemberDAO.queryPagedSignupMemberInfoList(voteActivityMemListReqDTO);
			datagrid.setRows(resultList);
		}
		result.setResult(datagrid);
		return result;
	}


	@Override
	public ExecuteResult<VoteActivityMemResDTO> queryVoteActivityMemberDetail(
			Long voteMemberId) {
		ExecuteResult<VoteActivityMemResDTO> resut=new ExecuteResult<VoteActivityMemResDTO>();
		
		if(voteMemberId==null||voteMemberId<=0){
			resut.setErrorMessages(Lists.newArrayList("参数为空"));
			return resut;
		}
		
		VoteActivityMemResDTO voteActivityMemResDTO=voteActivityMemberDAO.querySignupMemberDetailInfo(voteMemberId);
		resut.setResult(voteActivityMemResDTO);
		return resut;
	}


	@Override
	public ExecuteResult<ImportVoteActivityMemResDTO> importVoteActivityMember(
			List<VoteActivityMemReqDTO> list) {
		ExecuteResult<ImportVoteActivityMemResDTO> result=new ExecuteResult<ImportVoteActivityMemResDTO>();
		
		if(CollectionUtils.isEmpty(list)){
			result.setErrorMessages(Lists.newArrayList("参数为空"));
			return result;
		}
		
		if(list.size()>1000){
			result.setErrorMessages(Lists.newArrayList("导入记录数过多"));
			return result;
		}
		
		List<VoteActivityMemReqDTO> tempList=Lists.newArrayList();
		Long voteId = null;
		for(VoteActivityMemReqDTO v:list){
			ValidateResult va1lidateResult=DTOValidateUtil.validate(v);
			
			if(!va1lidateResult.isPass()){
				continue;
			}
			voteId = v.getVoteId();
			tempList.add(v);
		}
		ImportVoteActivityMemResDTO importVoteActivityMemResDTO=new ImportVoteActivityMemResDTO();
		voteActivityMemberDAO.batchInsertVoteActMember(tempList);
		//TODO: 查询库，得到成功记录，比对入参，得到失败记录，放到返回结果中
		
		List<String> memberCodeList = voteActivityMemberDAO.querySignUpMemberInfoList(voteId);
		int failCount = 0;
		int successCount = 0;
		int checkCount = 0;
		List<VoteActivityMemReqDTO> faillist = new ArrayList<VoteActivityMemReqDTO>();
		for (VoteActivityMemReqDTO memberCodeImport:tempList) {
			String memberCode = memberCodeImport.getMemberCode();
			for (String memberCodeCheck : memberCodeList) {
				if (memberCodeCheck.equals(memberCode)) {
					successCount ++;
				}
			}
			if (successCount > checkCount) {
				checkCount ++;
			} else {
				faillist.add(memberCodeImport);
			}
		}
		failCount = tempList.size() - successCount;
		importVoteActivityMemResDTO.setFailCount(failCount);
		importVoteActivityMemResDTO.setSuccessCount(successCount);
		importVoteActivityMemResDTO.setFaillist(faillist);
		importVoteActivityMemResDTO.setUniqueId(generatorUtils.generatePromotionId("6"));
		result.setResult(importVoteActivityMemResDTO);
		return result;
	}


	@Override
	public ExecuteResult<List<VoteActivityMemListResDTO>> ExportVoteActivityMember(VoteActivityMemListReqDTO voteActivityMemListReqDTO) {
		ExecuteResult<List<VoteActivityMemListResDTO>> result = new ExecuteResult<List<VoteActivityMemListResDTO>>();
		voteActivityMemListReqDTO.setPageSize(50000);
		voteActivityMemListReqDTO.setStart(1);
		List<VoteActivityMemListResDTO>  resultList = voteActivityMemberDAO.queryPagedSignupMemberInfoList(voteActivityMemListReqDTO);
		result.setResult(resultList);
		return result;
	}


	/**
	 * 删除活动
	 * @param voteId 活动id
	 */
	@Override
	public ExecuteResult<String> deleteVoteActivity(Long voteId) {
		VoteActivityResDTO voteActivityResDTO = new VoteActivityResDTO();
		voteActivityResDTO.setVoteId(voteId);
		//0 未删除 1已删除
		voteActivityResDTO.setDeleteFlag(1);
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			voteActivityDAO.updateByPrimaryKeySelective(voteActivityResDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
		}
		return result;
	}
}
