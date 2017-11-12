package cn.htd.promotion.cpc.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.biz.dao.*;
import cn.htd.promotion.cpc.dto.response.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.request.VoteActivityMemReqDTO;

import com.google.common.collect.Lists;


@Service("voteActivityService")
public class VoteActivityServiceImpl implements VoteActivityService{
	
	private Logger logger = LoggerFactory.getLogger(VoteActivityServiceImpl.class);
	
    @Resource
    private VoteActivityDAO voteActivityDAO;
    @Resource
    private VoteActivityMemberDAO voteActivityMemberDAO;
    @Resource
    private VoteActivityFansVoteDAO voteActivityFansVoteDAO;
    @Resource
    private VoteActivityFansForwardDAO voteActivityFansForwardDAO;
    @Resource
    private VoteActivityMemberPictureDAO voteActivityMemberPictureDAO;

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
		try{
			//校验时间先后
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			String today = simpleDateFormat.format(now) + " 00:00:00";
			Date todayTime = simpleDateFormat.parse(today);
			if(voteActivityResDTO.getVoteSignUpStartTime().before(todayTime)){
				result.setErrorMessages(Lists.newArrayList("报名开始时间不能早于当天"));
				return  result;
			}

			if(voteActivityResDTO.getVoteEndTime().before(voteActivityResDTO.getVoteStartTime())){
				result.setErrorMessages(Lists.newArrayList("投票开始时间不能晚于投票结束时间"));
				return  result;
			}

			if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteSignUpStartTime())){
				result.setErrorMessages(Lists.newArrayList("报名开始时间不能晚于报名结束时间"));
				return  result;
			}

			if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteStartTime())){
				result.setErrorMessages(Lists.newArrayList("报名结束时间不能早于投票开始时间"));
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
					voteActivityResDTO.getVoteEndTime(), null);

			if(voceAcitvityNum > 0){
				result.setErrorMessages(Lists.newArrayList("同一时间段内，不可有多个投票活动，请确认！"));
				return  result;
			}
			if(voteActivityResDTO.getVoteId() != null){
				voteActivityDAO.updateByPrimaryKeySelective(voteActivityResDTO);
			}else{
				voteActivityDAO.insertSelective(voteActivityResDTO);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveVoteActivity方法异常 异常信息=", e.getMessage());
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
					int authShopCount = 0;
					List<HashMap<String, Object>> resultMapList=voteActivityMemberDAO.querySignupMemberCount(voteActResDTO.getVoteId());
					if(CollectionUtils.isNotEmpty(resultMapList)){
						for(Map resultMap:resultMapList){
							if(resultMap!=null&&MapUtils.isNotEmpty(resultMap)&&resultMap.get("sign_status")!=null){
								if("0".equals(resultMap.get("sign_status")+"")){
									authShopCount += resultMap.get("c")==null?0:Integer.valueOf(resultMap.get("c")+"");
								}
								if("1".equals(resultMap.get("sign_status")+"")){
									int signupShopCount=resultMap.get("c")==null?0:Integer.valueOf(resultMap.get("c")+"");
									voteActivityListResDTO.setSignupShopCount(signupShopCount);
									authShopCount += resultMap.get("c")==null?0:Integer.valueOf(resultMap.get("c")+"");
									continue;
								}
							}
								
						}
						
					}
					voteActivityListResDTO.setAuthShopCount(authShopCount);
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
		if(page==null||voteActivityMemListReqDTO==null || voteActivityMemListReqDTO.getVoteId() == null){
			result.setErrorMessages(Lists.newArrayList("参数为空"));
			result.setResult(datagrid);
			return result;
		}
		voteActivityMemListReqDTO.setPageSize(page.getRows());
		voteActivityMemListReqDTO.setStart(page.getPageOffset());
		Long totalCount = null;
		try {
			totalCount=voteActivityMemberDAO.queryTotalSignupMemberInfo(voteActivityMemListReqDTO);
			datagrid.setTotal(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryPagedVoteActivityMemberList方法异常 异常信息=", e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
			result.setResult(datagrid);
			return result;
		}
		
		if(totalCount!=null&&totalCount>0){
			try {
				List<VoteActivityMemListResDTO>  resultList=voteActivityMemberDAO.queryPagedSignupMemberInfoList(voteActivityMemListReqDTO);
				datagrid.setRows(resultList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("queryPagedVoteActivityMemberList方法异常 异常信息=", e.getMessage());
				result.setErrorMessages(Lists.newArrayList(e.getMessage()));
			}
		}
		result.setResult(datagrid);
		return result;
	}


	@Override
	public ExecuteResult<VoteActivityMemResDTO> queryVoteActivityMemberDetail(Long voteId, Long voteMemberId) {
		ExecuteResult<VoteActivityMemResDTO> resut=new ExecuteResult<VoteActivityMemResDTO>();
		if(voteMemberId==null||voteMemberId<=0){
			resut.setErrorMessages(Lists.newArrayList("参数为空"));
			return resut;
		}
		VoteActivityMemResDTO voteActivityMemResDTO = voteActivityMemberDAO.querySignupMemberDetailInfo(voteId,voteMemberId);
		List<VoteActivityMemberPictureResDTO> pictureResDTOList = this.voteActivityMemberPictureDAO.selectByVoteMemberId(voteMemberId);
		List<String> picList = new ArrayList<>();
		for (VoteActivityMemberPictureResDTO voteActivityMemberPictureResDTO : pictureResDTOList) {
			picList.add(voteActivityMemberPictureResDTO.getPictureUrl());
		}
		voteActivityMemResDTO.setMemberPicList(picList);
		resut.setResult(voteActivityMemResDTO);
		return resut;
	}


	@Override
	public ExecuteResult<ImportVoteActivityMemResDTO> importVoteActivityMember(
			List<VoteActivityMemReqDTO> list) {
		ExecuteResult<ImportVoteActivityMemResDTO> result=new ExecuteResult<ImportVoteActivityMemResDTO>();
		
		if(CollectionUtils.isEmpty(list)){
			result.setCode("1001");
			result.setErrorMessages(Lists.newArrayList("参数为空"));
			return result;
		}
		
		if(list.size()>1000){
			result.setCode("1002");
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
		if (tempList.isEmpty() || tempList.size() == 0) {
			result.setCode("1003");
			result.setErrorMessages(Lists.newArrayList("导入数据均不符合规则"));
			return result;
		}
		List<String> memberCodeList = voteActivityMemberDAO.querySignUpMemberInfoList(voteId);
		
		List<VoteActivityMemReqDTO> alreadyExistsList = new ArrayList<VoteActivityMemReqDTO>();
		for (VoteActivityMemReqDTO memberCodeImport:list) {
			int checkFlag = 0;
			for (String memberCodeCheck : memberCodeList) {
				
				if(StringUtils.isEmpty(memberCodeCheck)){
					continue;	
				}
				
				if (memberCodeCheck.equals(memberCodeImport.getMemberCode())) {
					checkFlag=1;
					break;
				}
			}
			
			if (checkFlag==1) {
				alreadyExistsList.add(memberCodeImport);
				tempList.remove(memberCodeImport);
			}
		}
		if (!alreadyExistsList.isEmpty()) {
			result.addErrorMessage("存在已经导入的会员店");
		}
		importVoteActivityMemResDTO.setAlreadyExistsList(alreadyExistsList);
		try {
			//throw new Exception("故意为之 测试导出错误数据时使用的");
			if (tempList.size() > 0) {
				voteActivityMemberDAO.batchInsertVoteActMember(tempList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("importVoteActivityMember 方法异常 异常信息" + e.getMessage());
			result.addErrorMessage("方法异常");
		}
		
		//TODO: 查询库，得到成功记录，比对入参，得到失败记录，放到返回结果中
		
		List<String> memberCodeList2 = voteActivityMemberDAO.querySignUpMemberInfoList(voteId);
		List<VoteActivityMemReqDTO> faillist = new ArrayList<VoteActivityMemReqDTO>();
		for (VoteActivityMemReqDTO memberCodeImport:list) {
			int checkFlag = 0;
			for (String memberCodeCheck : memberCodeList2) {
				
				if(StringUtils.isEmpty(memberCodeCheck)){
					continue;	
				}
				
				if (memberCodeCheck.equals(memberCodeImport.getMemberCode())) {
					checkFlag=1;
					break;
				}
			}
			
			if (checkFlag==0) {
				faillist.add(memberCodeImport);
			}
		}
		
		int failCount = faillist.size();
		importVoteActivityMemResDTO.setFailCount(failCount);
		importVoteActivityMemResDTO.setSuccessCount(list.size()-failCount);
		importVoteActivityMemResDTO.setFaillist(faillist);
		importVoteActivityMemResDTO.setUniqueId(generatorUtils.generatePromotionId("6"));
		result.setResult(importVoteActivityMemResDTO);
		return result;
	}


	@Override
	public ExecuteResult<List<VoteActivityMemListResDTO>> exportVoteActivityMember(VoteActivityMemListReqDTO voteActivityMemListReqDTO) {
		ExecuteResult<List<VoteActivityMemListResDTO>> result = new ExecuteResult<List<VoteActivityMemListResDTO>>();
		if(voteActivityMemListReqDTO==null || voteActivityMemListReqDTO.getVoteId() == null){
			result.setErrorMessages(Lists.newArrayList("voteActivityMemListReqDTO参数为null或活动ID为null"));
			return result;
		}
		
		voteActivityMemListReqDTO.setPageSize(50000);
		voteActivityMemListReqDTO.setStart(0);
		List<VoteActivityMemListResDTO>  resultList = new ArrayList<VoteActivityMemListResDTO>();
		try {
			resultList = voteActivityMemberDAO.queryPagedSignupMemberInfoListOrderBySignUpTime(voteActivityMemListReqDTO);
		} catch (Exception e) {
			logger.error("exportVoteActivityMember方法异常 异常信息" + e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
		}
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
			logger.error("deleteVoteActivity方法异常 异常信息=", e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
		}
		return result;
	}


	@Override
	public ExecuteResult<String> updateVoteActivity(VoteActivityResDTO voteActivityResDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(voteActivityResDTO==null){
			result.setErrorMessages(Lists.newArrayList("voteActivityResDTO为null"));
			return result;
		}
		if (voteActivityResDTO.getVoteId() == null) {
			result.setErrorMessages(Lists.newArrayList("voteId为null"));
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
		
		if(voteActivityResDTO.getVoteSignUpEndTime().before(voteActivityResDTO.getVoteStartTime())){
			result.setErrorMessages(Lists.newArrayList("报名结束时间不能早于投票开始时间"));
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
		int voceAcitvityNum = 0;
		try {
			voceAcitvityNum = voteActivityDAO.queryVoteActivityByTime(voteActivityResDTO.getVoteSignUpStartTime(),
					voteActivityResDTO.getVoteEndTime(), voteActivityResDTO.getVoteId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateVoteActivity方法异常 异常信息=", e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
			return result;
		}
			
		
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
			logger.error("updateVoteActivity方法异常 异常信息=", e.getMessage());
			result.setErrorMessages(Lists.newArrayList(e.getMessage()));
		}
		return result;
	}
}
