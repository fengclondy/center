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
import com.bjucloud.contentcenter.dao.HTDCityCarouselAdvertiseDAO;
import com.bjucloud.contentcenter.dao.HTDHeadAdvertiseDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.HTDCityCarouselAdvertiseExportService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDCityCarouselAdvertiseImpl</p>
* @author root
* @date 2017年2月14日
* <p>Description: 
*			城市轮播广告
* </p>
 */
@Service("htdCityCarouselAdvertiseExportService")
@SuppressWarnings("all")
public class HTDCityCarouselAdvertiseImpl implements HTDCityCarouselAdvertiseExportService {

private static final Logger logger = LoggerFactory.getLogger(HTDCityCarouselAdvertiseImpl.class);
	
	@Resource
	private HTDCityCarouselAdvertiseDAO htdCityCarouselAdvertiseDAO;
	
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;
	
	@Override
	public ExecuteResult<Boolean> updateCityAdvStatusInit(List<Integer> SubstationList) {
		ExecuteResult<Boolean> rs  =new ExecuteResult<Boolean>();
		try {
			//将超时的广告设置状态为已结束
			htdCityCarouselAdvertiseDAO.updateTimeOutAdv();
			//查询城市id
			if(SubstationList != null && SubstationList.size() > 0){
				for(Integer sub:SubstationList){
					//城市轮播有6个广告位
					for(Long sortNum=1L;sortNum<3L;sortNum++){
						//获取最合适的广告id
						//有的话，就把其他的都弄掉
						//没有的话  就把所有的都弄掉
						Long advIdtemp = 0L;
						List<HTDAdvertisementDTO> willBeUp = htdCityCarouselAdvertiseDAO.queryWillBeUp(sortNum);
						if(willBeUp != null && willBeUp.size() > 0){
							advIdtemp = willBeUp.get(0).getId();
						}else{
							List<HTDAdvertisementDTO> queryStaticAdvUp = htdCityCarouselAdvertiseDAO.queryCityAdvUp(sortNum,""+sub);
							if(queryStaticAdvUp.size() > 0){
								advIdtemp = queryStaticAdvUp.get(0).getId();
							}else{
								//先判断 is_handoff为2的情况  在判断为0的情况
								Long availi = htdCityCarouselAdvertiseDAO.getAvailableAdvId(sortNum,"2",""+sub);
								if(availi != null){
									advIdtemp = availi;
								}else{
									Long avaSec = htdCityCarouselAdvertiseDAO.getAvailableAdvId(sortNum,"0",""+sub);
									if(avaSec != null){
										advIdtemp = avaSec;
									}
								} 
							}  
						}
						htdCityCarouselAdvertiseDAO.updateAdvStatusOneToTwo(sortNum,""+sub,advIdtemp);
						htdCityCarouselAdvertiseDAO.updateCityAdvStatusInitZero(sortNum,advIdtemp,""+sub);
						htdCityCarouselAdvertiseDAO.updateCityAdvStatusInitOne(advIdtemp);
					}
				}
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
	public DataGrid<HTDAdvertisementDTO> queryCityAdvertisement(Pager page, HTDAdvertisementDTO dto) {
		DataGrid<HTDAdvertisementDTO> dg = new DataGrid<HTDAdvertisementDTO>();
		try {
			dg.setRows(htdCityCarouselAdvertiseDAO.queryCityAdvertisement(page, dto));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdCityCarouselAdvertiseDAO.queryCityAdvertisement(tempp, dto).size()));
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  queryCityAdvertisement ",e);
		}
		return dg;
	}

	/**
	 * 根据id查询广告
	 */
	@Override
	public ExecuteResult<HTDAdvertisementDTO> queryCityAdvById(Long id) {
		ExecuteResult<HTDAdvertisementDTO> rs= new ExecuteResult<HTDAdvertisementDTO>();
		try {
			HTDAdvertisementDTO dto = htdCityCarouselAdvertiseDAO.queryCityAdvById(id);
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
			htdCityCarouselAdvertiseDAO.deleteById(advId);
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
	public ExecuteResult<Boolean> addCityAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			int advId = htdCityCarouselAdvertiseDAO.addCityAdvertisement(dto);
			Long recId = dto.getId();
			addEditDetailMethod("5", "创建城市站轮播广告",recId, dto.getCreate_id(), dto.getCreate_name());
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  deleteById ",e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> modidfyBySortNum(String status, Long sortNum) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			this.htdCityCarouselAdvertiseDAO.updateBySortNum(status,sortNum);
			result.setResult("success");
		}catch (Exception e){
			logger.error("【HTDAdvertisementServiceImpl】  modidfyBySortNum ",e);
			result.setResultMessage("error");
		}
		return result;
	}

	/**
	 * 更新
	 */
	@Override
	public ExecuteResult<Boolean> updateCityAdvertisement(HTDAdvertisementDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dto.getModify_id() == null || StringUtils.isEmpty(dto.getModify_name())){
				rs.addErrorMessage("修改人Id，修改人名称不能为空");
				throw new RuntimeException();
			}
			HTDAdvertisementDTO query = htdCityCarouselAdvertiseDAO.queryCityAdvById(dto.getId());
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
				htdCityCarouselAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId(),dto.getSub_id());
				//上架的话   将所有的状态改为0  
				htdCityCarouselAdvertiseDAO.updateCityAdvStatusInitZero(query.getSort_num(),0L,dto.getSub_id());//0,1 之外的都是将整个表的状态设置为0
				//将被顶掉的广告的status设置成已结束
				htdCityCarouselAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getSub_id(),dto.getId());
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
					htdCityCarouselAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(),dto.getId(),dto.getSub_id());
					//上架的话   将所有的状态改为0  
					htdCityCarouselAdvertiseDAO.updateCityAdvStatusInitZero(query.getSort_num(),0L,dto.getSub_id());//0,1 之外的都是将整个表的状态设置为0
					//将被顶掉的广告的status设置成已结束
					htdCityCarouselAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(),dto.getSub_id(),dto.getId());
					dto.setStatus("1");//将状态设置成使用中
					dto.setIs_handoff("2");
				}
			}
			htdCityCarouselAdvertiseDAO.updateCityAdvertisement(dto);
			if(!query.getSub_id().equalsIgnoreCase(dto.getSub_id())){
				addEditDetailMethod("5", "城市站",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!query.getPic_url().equalsIgnoreCase(dto.getPic_url())){
				addEditDetailMethod("5", "广告图片",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!(query.getSort_num() == dto.getSort_num())){
				addEditDetailMethod("5", "显示顺序",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!query.getLink_url().equalsIgnoreCase(dto.getLink_url())){
				addEditDetailMethod("5", "指向链接",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getStart_time()).equalsIgnoreCase(dto.getStartTimetemp())){
				addEditDetailMethod("5", "生效开始时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if(!sdf.format(query.getEnd_time()).equalsIgnoreCase(dto.getEndTimetemp())){
				addEditDetailMethod("5", "生效结束时间",dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDAdvertisementServiceImpl】  updateCityAdvertisement ",e);
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
			logger.error("【HTDAdvertisementServiceImpl】  queryEditDetail ",e);
		}
		return dg;
	}

	
}
