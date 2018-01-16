package com.bjucloud.contentcenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.HTDPurchaseHeadAdvertiseDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.PurchaseHeadAdvertiseExportService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: HTDHeadAdvertiseServiceImpl
 * </p>
 * 
 * @author root
 * @date 2017年1月19日
 *       <p>
 * 		Description: cms 总部轮播广告位
 *       </p>
 */

@Service("purchaseHeadAdvertiseExportService")
@SuppressWarnings("all")
public class PurchaseHeadAdvertiseServiceImpl implements PurchaseHeadAdvertiseExportService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseHeadAdvertiseServiceImpl.class);

	@Resource
	private HTDPurchaseHeadAdvertiseDAO htdPurchaseHeadAdvertiseDAO;

	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;

	public static final String ad_type = "12";

	@Override
	public ExecuteResult<Boolean> updateHeadAdvStatusInit() {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			// 将超时的广告设置状态为已结束
			htdPurchaseHeadAdvertiseDAO.updateTimeOutAdv();
			// 总部轮播有6个广告位
			for (Long sortNum = 1L; sortNum < 7L; sortNum++) {
				// 获取最合适的广告id
				// 有的话，就把其他的都弄掉
				// 没有的话 就把所有的都弄掉
				Long advIdtemp = 0L;
				List<HTDAdvertisementDTO> willBeUp = htdPurchaseHeadAdvertiseDAO.queryWillBeUp(sortNum);
				if (willBeUp != null && willBeUp.size() > 0) {
					advIdtemp = willBeUp.get(0).getId();
				} else {
					List<HTDAdvertisementDTO> queryStaticAdvUp = htdPurchaseHeadAdvertiseDAO.queryHeadAdvUp(sortNum);
					if (queryStaticAdvUp.size() > 0) {
						advIdtemp = queryStaticAdvUp.get(0).getId();
					} else {
						// 先判断 is_handoff为2的情况 在判断为0的情况
						Long availi = htdPurchaseHeadAdvertiseDAO.getAvailableAdvId(sortNum, "2");
						if (availi != null) {
							advIdtemp = availi;
						} else {
							Long avaSec = htdPurchaseHeadAdvertiseDAO.getAvailableAdvId(sortNum, "0");
							if (avaSec != null) {
								advIdtemp = avaSec;
							}
						}
					}
				}
				htdPurchaseHeadAdvertiseDAO.updateAdvStatusOneToTwo(sortNum, advIdtemp);
				htdPurchaseHeadAdvertiseDAO.updateHeadAdvStatusInitZero(sortNum, advIdtemp);
				htdPurchaseHeadAdvertiseDAO.updateHeadAdvStatusInitOne(advIdtemp);
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  updateTopAdvStatusInit ", e);
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
			dg.setRows(htdPurchaseHeadAdvertiseDAO.queryHeadAdvertisement(page, dto));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdPurchaseHeadAdvertiseDAO.queryHeadAdvertisement(tempp, dto).size()));
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  queryHeadAdvertisement ", e);
		}
		return dg;
	}

	/**
	 * 根据id查询广告
	 */
	@Override
	public ExecuteResult<HTDAdvertisementDTO> queryHeadAdvById(Long id) {
		ExecuteResult<HTDAdvertisementDTO> rs = new ExecuteResult<HTDAdvertisementDTO>();
		try {
			HTDAdvertisementDTO dto = htdPurchaseHeadAdvertiseDAO.queryHeadAdvById(id);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  deleteById ", e);
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
			htdPurchaseHeadAdvertiseDAO.deleteById(advId);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  deleteById ", e);
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
			if (sdf.parse(dto.getStartTimetemp()).getTime() < nowTime.getTime()
					&& sdf.parse(dto.getEndTimetemp()).getTime() > nowTime.getTime()) {
				dto.setStatus("1");
				HTDAdvertisementDTO dto1 = new HTDAdvertisementDTO();
				dto1.setIs_handoff("1");
				dto1.setStatus("2");
				dto1.setType(dto.getType());
				dto1.setModify_id(dto.getCreate_id());
				dto1.setModify_name(dto.getCreate_name());
				dto1.setSort_num(dto.getSort_num());
				htdPurchaseHeadAdvertiseDAO.updateByTypeAndSortNum(dto1);
			}
			int advId = htdPurchaseHeadAdvertiseDAO.addHeadAdvertisement(dto);
			Long recId = dto.getId();
			addEditDetailMethod(ad_type, "创建采购轮播广告", recId, dto.getCreate_id(), dto.getCreate_name());
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  deleteById ", e);
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
			if (dto.getModify_id() == null || StringUtils.isEmpty(dto.getModify_name())) {
				rs.addErrorMessage("修改人Id，修改人名称不能为空");
				throw new RuntimeException();
			}
			HTDAdvertisementDTO query = htdPurchaseHeadAdvertiseDAO.queryHeadAdvById(dto.getId());
			/*
			 * if("1".equalsIgnoreCase(dto.getStatus())){ //上架的话 将所有的广告状态改为0
			 * htdPurchaseHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(),
			 * 0L);// //将is_handoff 设置为0 dto.setIs_handoff("0"); }else
			 * if("0".equalsIgnoreCase(dto.getStatus())){ //手动下架的 将is_handoff设置成为1
			 * dto.setIs_handoff("1"); }
			 */
			if ("1".equalsIgnoreCase(dto.getStatus())) {
				// 将其他is_handoff为2的改为0
				htdPurchaseHeadAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(), dto.getId());
				// 上架的话 将所有的状态改为0
				htdPurchaseHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(), 0L);// 0,1 之外的都是将整个表的状态设置为0
				// 将被顶掉的广告的status设置成已结束
				htdPurchaseHeadAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(), dto.getId());
				// 将is_handoff 设置为0
				dto.setIs_handoff("2");
			} else if ("0".equalsIgnoreCase(dto.getStatus())) {
				// 手动下架的 将is_handoff设置成为1
				dto.setIs_handoff("1");
				dto.setStatus("2");
			}
			// 将本身的结束时间修改成小于当前时间的
			if (!StringUtils.isEmpty(dto.getStartTimetemp()) && !StringUtils.isEmpty(dto.getEndTimetemp())) {
				if (sdf.parse(dto.getEndTimetemp()).getTime() < System.currentTimeMillis()) {
					dto.setStatus("2");// 将状态设置成结束
				}
				if (sdf.parse(dto.getStartTimetemp()).getTime() > System.currentTimeMillis()) {
					dto.setStatus("0");// 将状态设置成编辑中
					dto.setIs_handoff("0");
				}
				if (sdf.parse(dto.getStartTimetemp()).getTime() <= System.currentTimeMillis()
						&& sdf.parse(dto.getEndTimetemp()).getTime() > System.currentTimeMillis()) {
					// 将其他is_handoff为2的改为0
					htdPurchaseHeadAdvertiseDAO.updateAdvIsHandoffOne(query.getSort_num(), dto.getId());
					// 上架的话 将所有的状态改为0
					htdPurchaseHeadAdvertiseDAO.updateHeadAdvStatusInitZero(query.getSort_num(), 0L);// 0,1
																										// 之外的都是将整个表的状态设置为0
					// 将被顶掉的广告的status设置成已结束
					htdPurchaseHeadAdvertiseDAO.updateAdvStatusOneToTwo(query.getSort_num(), dto.getId());
					dto.setStatus("1");// 将状态设置成使用中
					dto.setIs_handoff("2");
				}
			}
			htdPurchaseHeadAdvertiseDAO.updateHeadAdvertisement(dto);
			if (!query.getPic_url().equalsIgnoreCase(dto.getPic_url())) {
				addEditDetailMethod(ad_type, "广告图片", dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if (!(query.getSort_num() == dto.getSort_num())) {
				addEditDetailMethod(ad_type, "显示顺序", dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if (!query.getLink_url().equalsIgnoreCase(dto.getLink_url())) {
				addEditDetailMethod(ad_type, "指向链接", dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if (!sdf.format(query.getStart_time()).equalsIgnoreCase(dto.getStartTimetemp())) {
				addEditDetailMethod(ad_type, "生效开始时间", dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			if (!sdf.format(query.getEnd_time()).equalsIgnoreCase(dto.getEndTimetemp())) {
				addEditDetailMethod(ad_type, "生效结束时间", dto.getId(), dto.getModify_id(), dto.getModify_name());
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  updateHeadAdvertisement ", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 增加更改记录私有方法
	 * 
	 * @return
	 */
	public Boolean addEditDetailMethod(String modifyType, String contentName, Long advId, Long userId,
			String userName) {
		Boolean b = false;
		try {
			HTDEditDetailInfoDTO dto = new HTDEditDetailInfoDTO();
			dto.setModify_type(modifyType);
			dto.setRecord_id(advId);// 广告id
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
	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String modifyType, Long advId, Pager pager) {
		DataGrid<HTDEditDetailInfoDTO> dg = new DataGrid<HTDEditDetailInfoDTO>();
		try {
			dg.setRows(htdAdvertisementDAO.queryEditDetail(modifyType, advId, pager));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdAdvertisementDAO.queryEditDetail(modifyType, advId, tempp).size()));
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  queryHeadAdvertisement ", e);
		}
		return dg;
	}

	/**
	 * 静态广告查询列表
	 */
	@Override
	public ExecuteResult<List<HTDAdvertisementDTO>> queryHeadAdvertisementList() {
		logger.info("PurchaseHeadAdvertiseServiceImpl-queryHeadAdvertisementList服务执行开始,参数:");
        ExecuteResult<List<HTDAdvertisementDTO>> rs = new ExecuteResult<List<HTDAdvertisementDTO>>();
        try {
            List<HTDAdvertisementDTO> list = htdPurchaseHeadAdvertiseDAO.queryHeadAdvertisementList();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("PurchaseHeadAdvertiseServiceImpl-queryHeadAdvertisementList服务执行异常:" + e);
        }
        logger.info("PurchaseHeadAdvertiseServiceImpl-queryHeadAdvertisementList服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
	}
	
}
