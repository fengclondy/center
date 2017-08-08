package com.bjucloud.contentcenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.HTDAdvertisementExportService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("htdAdvertisementExportService")
@SuppressWarnings("all")
public class HTDAdvertisementServiceImpl implements HTDAdvertisementExportService{
	
	private static final Logger logger = LoggerFactory.getLogger(HTDAdvertisementServiceImpl.class);
	
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDao;

	/**
	 * 查询广告列表前整体更新状态
	 */
	@Override
	public ExecuteResult<Boolean> updateTopAdvStatusInit() {
		ExecuteResult<Boolean> rs  =new ExecuteResult<Boolean>();
		try {
			
			//将超时的广告设置状态为已结束
			htdAdvertisementDao.updateTimeOutAdv();
			//获取最合适的广告id
			//有的话，就把其他的都弄掉
			//没有的话  就把所有的都弄掉
			Long advIdtemp = 0L;
			List<HTDAdvertisementDTO> willBeUp = htdAdvertisementDao.queryWillBeUp();
			if(willBeUp != null && willBeUp.size() > 0){
				advIdtemp = willBeUp.get(0).getId();
			}else{
				List<HTDAdvertisementDTO> queryTopAdvUp = htdAdvertisementDao.queryTopAdvUp();
				if(queryTopAdvUp.size() > 0){
					advIdtemp = queryTopAdvUp.get(0).getId();
				}else{
					//先判断 is_handoff为2的情况  在判断为0的情况
					Long availi = htdAdvertisementDao.getAvailableAdvId("2");
					if(availi != null){
						advIdtemp = availi;
					}else{
						Long avaSec = htdAdvertisementDao.getAvailableAdvId("0");
						if(avaSec != null){
							advIdtemp = avaSec;
						}
					} 
				}
			}
			htdAdvertisementDao.updateAdvStatusOneToTwo(advIdtemp);
			htdAdvertisementDao.updateTopAdvStatusInitZero(advIdtemp);
			htdAdvertisementDao.updateTopAdvStatusInitOne(advIdtemp);
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
	 * 顶通广告查询列表
	 */
	@Override
	public DataGrid<HTDAdvertisementDTO> queryTopAdvertisement(Pager page, HTDAdvertisementDTO dto) {
		DataGrid<HTDAdvertisementDTO> dg = new DataGrid<HTDAdvertisementDTO>();
		try {
			dg.setRows(htdAdvertisementDao.queryTopAdvertisement(page, dto));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdAdvertisementDao.queryTopAdvertisement(tempp, dto).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryTopAdvertisement ",e);
		}
		return dg;
	}

	
	/**
	 * 更新广告
	 */
	@Override
	public ExecuteResult<Boolean> updateTopAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dto.getModify_id() == null || StringUtils.isEmpty(dto.getModify_name())){
				rs.addErrorMessage("修改人Id，修改人名称不能为空");
				throw new RuntimeException();
			}

			HTDAdvertisementDTO query = htdAdvertisementDao.queryTopAdvById(dto.getId());
			if("1".equalsIgnoreCase(dto.getStatus())){
				//将其他is_handoff为2的改为0
				htdAdvertisementDao.updateAdvIsHandoffOne(dto.getId());
				//上架的话   将所有的状态改为0  
				htdAdvertisementDao.updateTopAdvStatusInitZero(0L);//0,1 之外的都是将整个表的状态设置为0
				//将被顶掉的广告的status设置成已结束
				htdAdvertisementDao.updateAdvStatusOneToTwo(dto.getId());
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
					htdAdvertisementDao.updateAdvIsHandoffOne(dto.getId());
					//上架的话   将所有的状态改为0  
					htdAdvertisementDao.updateTopAdvStatusInitZero(0L);//0,1 之外的都是将整个表的状态设置为0
					//将被顶掉的广告的status设置成已结束
					htdAdvertisementDao.updateAdvStatusOneToTwo(dto.getId());
					dto.setStatus("1");//将状态设置成使用中
					dto.setIs_handoff("2");
				}
			}
			htdAdvertisementDao.updateTopAdvertisement(dto);
			if(!query.getPic_url().equalsIgnoreCase(dto.getPic_url())){
				addEditDetailMethod("1", "广告图片",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!query.getLink_url().equalsIgnoreCase(dto.getLink_url())){
				addEditDetailMethod("1", "指向链接",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getStart_time()).equalsIgnoreCase(dto.getStartTimetemp())){
				addEditDetailMethod("1", "生效开始时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getEnd_time()).equalsIgnoreCase(dto.getEndTimetemp())){
				addEditDetailMethod("1", "生效结束时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  updateTopAdvertisement ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 删除广告
	 */
	@Override
	public ExecuteResult<Boolean> deleteById(Long advId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			htdAdvertisementDao.deleteById(advId);
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
	 * 根据id查询广告
	 */
	@Override
	public ExecuteResult<HTDAdvertisementDTO> queryTopAdvById(Long advId) {
		ExecuteResult<HTDAdvertisementDTO> rs= new ExecuteResult<HTDAdvertisementDTO>();
		try {
			HTDAdvertisementDTO dto = htdAdvertisementDao.queryTopAdvById(advId);
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
	 * 增加广告
	 */
	@Override
	public ExecuteResult<Boolean> addTopAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			int advId = htdAdvertisementDao.addTopAdvertisement(dto);
			Long recId = dto.getId();
			addEditDetailMethod("1", "创建顶通广告",recId, dto.getCreate_id(), dto.getCreate_name());
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
			htdAdvertisementDao.addEditDetail(dto);
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
			dg.setRows(htdAdvertisementDao.queryEditDetail(modifyType,advId,pager));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdAdvertisementDao.queryEditDetail(modifyType, advId,tempp).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryEditDetail ",e);
		}
		return dg;
	}

	@Override
	public DataGrid<HTDEditDetailInfoDTO> queryByModityType(String modifyType) {
		DataGrid<HTDEditDetailInfoDTO> dataGrid = new DataGrid<HTDEditDetailInfoDTO>();
		try{
			List<HTDEditDetailInfoDTO> list = htdAdvertisementDao.queryByType(modifyType);
			dataGrid.setRows(list);
		}catch (Exception e){
			logger.error("【HTDAdvertisementServiceImpl】  queryByModityType ",e);
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<String> saveEditDetail(HTDEditDetailInfoDTO htdEditDetailInfoDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			htdAdvertisementDao.addEditDetail(htdEditDetailInfoDTO);
			result.setResult("success");
		}catch (Exception e){
			logger.error(e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}


}
