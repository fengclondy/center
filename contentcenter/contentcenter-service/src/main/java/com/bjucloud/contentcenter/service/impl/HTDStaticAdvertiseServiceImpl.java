package com.bjucloud.contentcenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.HTDStaticAdvertiseDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.HTDStaticAdvertiseExportService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;


@Service("htdStaticAdvertiseExportService")
@SuppressWarnings("all")
public class HTDStaticAdvertiseServiceImpl implements HTDStaticAdvertiseExportService{
	
	private static final Logger logger = LoggerFactory.getLogger(HTDStaticAdvertiseServiceImpl.class);
	
	@Resource
	private HTDStaticAdvertiseDAO htdStaticAdvertiseDAO;
	
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;
	
	@Override
	public ExecuteResult<Boolean> updateStaticAdvStatusInit() {
		ExecuteResult<Boolean> rs  =new ExecuteResult<Boolean>();
		try {
			//将超时的广告设置状态为已结束
			htdStaticAdvertiseDAO.updateTimeOutAdv();
			for(Long sortNum=1L;sortNum<5L;sortNum++){
				//获取最合适的广告id
				//有的话，就把其他的都弄掉
				//没有的话  就把所有的都弄掉
				Long advIdtemp = 0L;
				List<HTDAdvertisementDTO> willBeUp = htdStaticAdvertiseDAO.queryWillBeUp(sortNum);
				if(willBeUp != null && willBeUp.size() > 0){
					advIdtemp = willBeUp.get(0).getId();
				}else{
					List<HTDAdvertisementDTO> queryStaticAdvUp = htdStaticAdvertiseDAO.queryStaticAdvUp(sortNum);
					if(queryStaticAdvUp.size() > 0){
						advIdtemp = queryStaticAdvUp.get(0).getId();
					}else{
						//先判断 is_handoff为2的情况  在判断为0的情况
						Long availi = htdStaticAdvertiseDAO.getAvailableAdvId(sortNum,"2");
						if(availi != null){
							advIdtemp = availi;
						}else{
							Long avaSec = htdStaticAdvertiseDAO.getAvailableAdvId(sortNum,"0");
							if(avaSec != null){
								advIdtemp = avaSec;
							}
						} 
					}
				}
				htdStaticAdvertiseDAO.updateAdvStatusOneToTwo(sortNum,advIdtemp);
				htdStaticAdvertiseDAO.updateStaticAdvStatusInitZero(sortNum,advIdtemp);
				htdStaticAdvertiseDAO.updateStaticAdvStatusInitOne(advIdtemp);
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
	public DataGrid<HTDAdvertisementDTO> queryStaticAdvertisement(Pager page, HTDAdvertisementDTO dto) {
		DataGrid<HTDAdvertisementDTO> dg = new DataGrid<HTDAdvertisementDTO>();
		try {
			dg.setRows(htdStaticAdvertiseDAO.queryStaticAdvertisement(page, dto));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdStaticAdvertiseDAO.queryStaticAdvertisement(tempp, dto).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryStaticAdvertisement ",e);
		}
		return dg;
	}

	/**
	 * 根据id查询广告
	 */
	@Override
	public ExecuteResult<HTDAdvertisementDTO> queryStaticAdvById(Long id) {
		ExecuteResult<HTDAdvertisementDTO> rs= new ExecuteResult<HTDAdvertisementDTO>();
		try {
			HTDAdvertisementDTO dto = htdStaticAdvertiseDAO.queryStaticAdvById(id);
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
			htdStaticAdvertiseDAO.deleteById(advId);
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
	public ExecuteResult<Boolean> addStaticAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			int advId = htdStaticAdvertiseDAO.addStaticAdvertisement(dto);
			Long recId = dto.getId();
			addEditDetailMethod("6", "创建静态广告",recId, dto.getCreate_id(), dto.getCreate_name());
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
	public ExecuteResult<Boolean> updateStaticAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dto.getModify_id() == null || StringUtils.isEmpty(dto.getModify_name())){
				rs.addErrorMessage("修改人Id，修改人名称不能为空");
				throw new RuntimeException();
			}
			HTDAdvertisementDTO query = htdStaticAdvertiseDAO.queryStaticAdvById(dto.getId());
			/*if("1".equalsIgnoreCase(dto.getStatus())){
				//上架的话   将所有的广告状态改为0  
				htdStaticAdvertiseDAO.updateStaticAdvStatusInitZero(query.getSort_num(),0L);//
				//将is_handoff 设置为0
				dto.setIs_handoff("0");
			}else if("0".equalsIgnoreCase(dto.getStatus())){
				//手动下架的  将is_handoff设置成为1
				dto.setIs_handoff("1");
			}*/
			if("1".equalsIgnoreCase(dto.getStatus())){
				//将其他is_handoff为2的改为0
				htdStaticAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId());
				//上架的话   将所有的状态改为0  
				htdStaticAdvertiseDAO.updateStaticAdvStatusInitZero(query.getSort_num(),0L);//0,1 之外的都是将整个表的状态设置为0
				//将被顶掉的广告的status设置成已结束
				htdStaticAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getId());
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
					htdStaticAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId());
					//上架的话   将所有的状态改为0  
					htdStaticAdvertiseDAO.updateStaticAdvStatusInitZero(query.getSort_num(),0L);//0,1 之外的都是将整个表的状态设置为0
					//将被顶掉的广告的status设置成已结束
					htdStaticAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getId());
					dto.setStatus("1");//将状态设置成使用中
					dto.setIs_handoff("2");
				}
			}
			htdStaticAdvertiseDAO.updateStaticAdvertisement(dto);
			if(!query.getPic_url().equalsIgnoreCase(dto.getPic_url())){
				addEditDetailMethod("6", "广告图片",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!(query.getSort_num() == dto.getSort_num())){
				addEditDetailMethod("6", "显示顺序",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!query.getLink_url().equalsIgnoreCase(dto.getLink_url())){
				addEditDetailMethod("6", "指向链接",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getStart_time()).equalsIgnoreCase(dto.getStartTimetemp())){
				addEditDetailMethod("6", "生效开始时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getEnd_time()).equalsIgnoreCase(dto.getEndTimetemp())){
				addEditDetailMethod("6", "生效结束时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  updateStaticAdvertisement ",e);
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
			logger.error("【HTDAdvertisementServiceImpl】  queryStaticAdvertisement ",e);
		}
		return dg;
	}




}
