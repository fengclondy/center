package com.bjucloud.contentcenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.HTDHeadAdvertiseDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.HTDHeadAdvertiseExportService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDHeadAdvertiseServiceImpl</p>
* @author root
* @date 2017年1月19日
* <p>Description: 
*		cms  总部轮播广告位
* </p>
 */

@Service("htdHeadAdvertiseExportService")
@SuppressWarnings("all")
public class HTDHeadAdvertiseServiceImpl implements HTDHeadAdvertiseExportService{

	private static final Logger logger = LoggerFactory.getLogger(HTDHeadAdvertiseServiceImpl.class);
	
	@Resource
	private HTDHeadAdvertiseDAO htdHeadAdvertiseDAO;
	
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;
	
	@Override
	public ExecuteResult<Boolean> updateHeadAdvStatusInit() {
		ExecuteResult<Boolean> rs  =new ExecuteResult<Boolean>();
		try {
			//将超时的广告设置状态为已结束
			htdHeadAdvertiseDAO.updateTimeOutAdv();
			//总部轮播有6个广告位
			for(Long sortNum=1L;sortNum<7L;sortNum++){
				//获取最合适的广告id
				//有的话，就把其他的都弄掉
				//没有的话  就把所有的都弄掉
				Long advIdtemp = 0L;
				List<HTDAdvertisementDTO> willBeUp = htdHeadAdvertiseDAO.queryWillBeUp(sortNum);
				if(willBeUp != null && willBeUp.size() > 0){
					advIdtemp = willBeUp.get(0).getId();
				}else{
					List<HTDAdvertisementDTO> queryStaticAdvUp = htdHeadAdvertiseDAO.queryHeadAdvUp(sortNum);
					if(queryStaticAdvUp.size() > 0){
						advIdtemp = queryStaticAdvUp.get(0).getId();
					}else{
						//先判断 is_handoff为2的情况  在判断为0的情况
						Long availi = htdHeadAdvertiseDAO.getAvailableAdvId(sortNum,"2");
						if(availi != null){
							advIdtemp = availi;
						}else{
							Long avaSec = htdHeadAdvertiseDAO.getAvailableAdvId(sortNum,"0");
							if(avaSec != null){
								advIdtemp = avaSec;
							}
						} 
					}  
				}
				htdHeadAdvertiseDAO.updateAdvStatusOneToTwo(sortNum,advIdtemp);
				htdHeadAdvertiseDAO.updateHeadAdvStatusInitZero(sortNum,advIdtemp);
				htdHeadAdvertiseDAO.updateHeadAdvStatusInitOne(advIdtemp);
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  updateTopAdvStatusInit ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 静态广告查询列表
	 */
	@Override
	public DataGrid<HTDAdvertisementDTO> queryHeadAdvertisement(Pager page, HTDAdvertisementDTO dto) {
		DataGrid<HTDAdvertisementDTO> dg = new DataGrid<HTDAdvertisementDTO>();
		try {
			dg.setRows(htdHeadAdvertiseDAO.queryHeadAdvertisement(page, dto));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdHeadAdvertiseDAO.queryHeadAdvertisement(tempp, dto).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryHeadAdvertisement ",e);
		}
		return dg;
	}

	/**
	 * 根据id查询广告
	 */
	@Override
	public ExecuteResult<HTDAdvertisementDTO> queryHeadAdvById(Long id) {
		ExecuteResult<HTDAdvertisementDTO> rs= new ExecuteResult<HTDAdvertisementDTO>();
		try {
			HTDAdvertisementDTO dto = htdHeadAdvertiseDAO.queryHeadAdvById(id);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  deleteById ",e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 删除
	 */
	@Override
	public ExecuteResult<Boolean> deleteById(Long advId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			htdHeadAdvertiseDAO.deleteById(advId);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  deleteById ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}
	
	/**
	 * 增加
	 */
	@Override
	public ExecuteResult<Boolean> addHeadAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date nowTime = new Date();
			if(sdf.parse(dto.getStartTimetemp()).getTime() < nowTime.getTime()
					&& sdf.parse(dto.getEndTimetemp()).getTime() > nowTime.getTime()){
				dto.setStatus("1");
				HTDAdvertisementDTO dto1 = new HTDAdvertisementDTO();
				dto1.setIs_handoff("1");
				dto1.setStatus("2");
				dto1.setType(dto.getType());
				dto1.setModify_id(dto.getCreate_id());
				dto1.setModify_name(dto.getCreate_name());
				dto1.setSort_num(dto.getSort_num());
				htdHeadAdvertiseDAO.updateByTypeAndSortNum(dto1);
			}
			int advId = htdHeadAdvertiseDAO.addHeadAdvertisement(dto);
			Long recId = dto.getId();
			addEditDetailMethod("4", "创建总部轮播广告",recId, dto.getCreate_id(), dto.getCreate_name());
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  deleteById ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 更新
	 */
	@Override
	public ExecuteResult<Boolean> updateHeadAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dto.getModify_id() == null || StringUtils.isEmpty(dto.getModify_name())){
				rs.addErrorMessage("修改人Id，修改人名称不能为空");
				throw new RuntimeException();
			}
			HTDAdvertisementDTO query = htdHeadAdvertiseDAO.queryHeadAdvById(dto.getId());
			/*if("1".equalsIgnoreCase(dto.getStatus())){
				//上架的话   将所有的广告状态改为0  
				htdHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(),0L);//
				//将is_handoff 设置为0
				dto.setIs_handoff("0");
			}else if("0".equalsIgnoreCase(dto.getStatus())){
				//手动下架的  将is_handoff设置成为1
				dto.setIs_handoff("1");
			}*/
			if("1".equalsIgnoreCase(dto.getStatus())){
				//将其他is_handoff为2的改为0
				htdHeadAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId());
				//上架的话   将所有的状态改为0  
				htdHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(),0L);//0,1 之外的都是将整个表的状态设置为0
				//将被顶掉的广告的status设置成已结束
				htdHeadAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getId());
				//将is_handoff 设置为0
				dto.setIs_handoff("2");
			}else if("0".equalsIgnoreCase(dto.getStatus())){
				//手动下架的  将is_handoff设置成为1
				dto.setIs_handoff("1");
				dto.setStatus("2");
			}
			//将本身的结束时间修改成小于当前时间的
			if(!StringUtils.isEmpty(dto.getStartTimetemp()) && !StringUtils.isEmpty(dto.getEndTimetemp())){
				if(sdf.parse(dto.getEndTimetemp()).getTime() < System.currentTimeMillis()){
					dto.setStatus("2");//将状态设置成结束
				}
				if(sdf.parse(dto.getStartTimetemp()).getTime() > System.currentTimeMillis()){
					dto.setStatus("0");//将状态设置成编辑中
					dto.setIs_handoff("0");
				}
				if(sdf.parse(dto.getStartTimetemp()).getTime() <= System.currentTimeMillis()
						&& sdf.parse(dto.getEndTimetemp()).getTime() > System.currentTimeMillis()){
					//将其他is_handoff为2的改为0
					htdHeadAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId());
					//上架的话   将所有的状态改为0  
					htdHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(),0L);//0,1 之外的都是将整个表的状态设置为0
					//将被顶掉的广告的status设置成已结束
					htdHeadAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getId());
					dto.setStatus("1");//将状态设置成使用中
					dto.setIs_handoff("2");
				}
			}
			htdHeadAdvertiseDAO.updateHeadAdvertisement(dto);
			if(!query.getPic_url().equalsIgnoreCase(dto.getPic_url())){
				addEditDetailMethod("4", "广告图片",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!(query.getSort_num() == dto.getSort_num())){
				addEditDetailMethod("4", "显示顺序",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!query.getLink_url().equalsIgnoreCase(dto.getLink_url())){
				addEditDetailMethod("4", "指向链接",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getStart_time()).equalsIgnoreCase(dto.getStartTimetemp())){
				addEditDetailMethod("4", "生效开始时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getEnd_time()).equalsIgnoreCase(dto.getEndTimetemp())){
				addEditDetailMethod("4", "生效结束时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  updateHeadAdvertisement ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}


	/**
	 * 增加更改记录私有方法
	 * @return
	 */
	public Boolean addEditDetailMethod(String modifyType,String contentName,Long advId,Long userId,String userName){
		Boolean b = false;
		try {
			HTDEditDetailInfoDTO dto = new HTDEditDetailInfoDTO();
			dto.setModify_type(modifyType);
			dto.setRecord_id(advId);//广告id
			dto.setContent_name(contentName);
			dto.setOperator_id(userId);
			dto.setOperator_name(userName);
			htdAdvertisementDAO.addEditDetail(dto);
			b = true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return b;
	}

	/**
	 * 查询更改记录
	 */
	@Override
	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String modifyType,Long advId,Pager pager) {
		DataGrid<HTDEditDetailInfoDTO> dg = new DataGrid<HTDEditDetailInfoDTO>();
		try {
			dg.setRows(htdAdvertisementDAO.queryEditDetail(modifyType,advId,pager));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdAdvertisementDAO.queryEditDetail(modifyType, advId,tempp).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryHeadAdvertisement ",e);
		}
		return dg;
	}


	
	
	
}
