package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.PurchaseChannelsDAO;
import com.bjucloud.contentcenter.dao.PurchaseChannelsSubDAO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.PurchaseChannelsDTO;
import com.bjucloud.contentcenter.dto.PurchaseChannelsSubDTO;
import com.bjucloud.contentcenter.service.PurchaseChannelsService;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("purchaseChannelsService")
public class PurchaseChannelsServiceImpl implements PurchaseChannelsService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseChannelsServiceImpl.class);

	@Resource
	private PurchaseChannelsDAO purchaseChannelsDAO;
	@Resource
	private PurchaseChannelsSubDAO purchaseChannelsSubDAO;

	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;

	public static final String ad_type = "13";

	@Override
	public ExecuteResult<PurchaseChannelsDTO> getMallTypeById(Long id) {
		ExecuteResult<PurchaseChannelsDTO> executeResult = new ExecuteResult<PurchaseChannelsDTO>();
		try {
			if (id == null || id == 0L) {
				executeResult.addErrorMessage("参数不能为空！");
				return executeResult;
			}
			PurchaseChannelsDTO purchaseChannelsDTO = purchaseChannelsDAO.selectById(id);
			List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOs = purchaseChannelsSubDAO.selectByTypeId(id);
			purchaseChannelsDTO.setPurchaseChannelsSubDTOList(purchaseChannelsSubDTOs);

			executeResult.setResult(purchaseChannelsDTO);
			executeResult.setResultMessage("Logo查询成功");
		} catch (RuntimeException e) {
			logger.error("执行方法：【getMallTypeById】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:修改前台类目]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallType(PurchaseChannelsDTO purchaseChannelsDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			PurchaseChannelsDTO query = purchaseChannelsDAO.selectById(purchaseChannelsDTO.getId());
			if (query != null) {
				if (purchaseChannelsDTO.getStatus().equals("1")) {
					query.setModifyId(purchaseChannelsDTO.getModifyId());
					query.setModifyName(purchaseChannelsDTO.getModifyName());
					purchaseChannelsDAO.updateOrderStatus(query);
				}
				purchaseChannelsDAO.update(purchaseChannelsDTO);
				List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOs = purchaseChannelsDTO
						.getPurchaseChannelsSubDTOList();
				purchaseChannelsSubDAO.deleteByTypeId(purchaseChannelsDTO.getId());
				if (purchaseChannelsSubDTOs != null && purchaseChannelsSubDTOs.size() > 0) {
					for (PurchaseChannelsSubDTO purchaseChannelsSubDTO : purchaseChannelsSubDTOs) {
						purchaseChannelsSubDTO.setTypeId(purchaseChannelsDTO.getId());
						purchaseChannelsSubDAO.insert(purchaseChannelsSubDTO);
					}
				}
				executeResult.setResultMessage("编辑成功");
				if (!query.getName().equalsIgnoreCase(purchaseChannelsDTO.getName())) {
					addEditDetailMethod(ad_type, "频道名称", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getModifyId(),
							purchaseChannelsDTO.getModifyName());
				}
				if (query.getSortNum() != purchaseChannelsDTO.getSortNum()) {
					addEditDetailMethod(ad_type, "显示顺序", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getModifyId(),
							purchaseChannelsDTO.getModifyName());
				}
				if (!query.getStatus().equalsIgnoreCase(purchaseChannelsDTO.getStatus())) {
					addEditDetailMethod(ad_type, "启用状态", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getModifyId(),
							purchaseChannelsDTO.getModifyName());
				}
				addEditDetailMethod(ad_type, "类目选择", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getModifyId(),
						purchaseChannelsDTO.getModifyName());
			}

		} catch (Exception e) {
			logger.error("执行方法【modifyMallType】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> addMallType(PurchaseChannelsDTO purchaseChannelsDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (purchaseChannelsDTO.getStatus().equals("1")) {
				purchaseChannelsDTO.setModifyId(purchaseChannelsDTO.getCreateId());
				purchaseChannelsDTO.setModifyName(purchaseChannelsDTO.getCreateName());
				purchaseChannelsDAO.updateOrderStatus(purchaseChannelsDTO);
			}
			purchaseChannelsDAO.insert(purchaseChannelsDTO);
			List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOs = purchaseChannelsDTO.getPurchaseChannelsSubDTOList();
			if (purchaseChannelsSubDTOs != null && purchaseChannelsSubDTOs.size() > 0) {
				for (PurchaseChannelsSubDTO purchaseChannelsSubDTO : purchaseChannelsSubDTOs) {
					purchaseChannelsSubDTO.setTypeId(purchaseChannelsDTO.getId());
					purchaseChannelsSubDAO.insert(purchaseChannelsSubDTO);
				}
			}
			executeResult.setResultMessage("保存成功");
			addEditDetailMethod(ad_type, "创建采购频道", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getCreateId(),
					purchaseChannelsDTO.getCreateName());
		} catch (Exception e) {
			logger.error("执行方法【addMallType】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public DataGrid<PurchaseChannelsDTO> queryAll(Pager page) {
		DataGrid<PurchaseChannelsDTO> dataGrid = new DataGrid<PurchaseChannelsDTO>();
		try {
			List<PurchaseChannelsDTO> list = purchaseChannelsDAO.selectAll(page);
			Long count = purchaseChannelsDAO.selectAllCount();
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
		} catch (Exception e) {
			logger.error("执行方法【queryAll】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	@Override
	public DataGrid<PurchaseChannelsDTO> queryByName(String name) {
		DataGrid<PurchaseChannelsDTO> result = new DataGrid<PurchaseChannelsDTO>();
		try {
			List<PurchaseChannelsDTO> hotWordDTOs = purchaseChannelsDAO.selectByName(name);
			result.setRows(hotWordDTOs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
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

	@Override
	public DataGrid<HTDEditDetailInfoDTO> queryEditDetail(String adType, Long id, Pager pager) {
		DataGrid<HTDEditDetailInfoDTO> dg = new DataGrid<HTDEditDetailInfoDTO>();
		try {
			dg.setRows(htdAdvertisementDAO.queryEditDetail(adType, id, pager));
			Pager tempp = new Pager();
			tempp.setPageOffset(0);
			tempp.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(htdAdvertisementDAO.queryEditDetail(adType, id, tempp).size()));
		} catch (Exception e) {
			logger.error("【HTDPurchaseHeadAdvertiseServiceImpl】  queryHeadAdvertisement ", e);
		}
		return dg;
	}

	@Override
	public ExecuteResult<String> delete(Long id) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		purchaseChannelsDAO.delete(id);
		purchaseChannelsSubDAO.deleteByTypeId(id);
		result.setResult("success");
		return result;
	}

	@Override
	public ExecuteResult<String> modifyStatus(PurchaseChannelsDTO purchaseChannelsDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			PurchaseChannelsDTO query = purchaseChannelsDAO.selectById(purchaseChannelsDTO.getId());
			if (query != null) {
				if (purchaseChannelsDTO.getStatus().equals("1")) {
					query.setModifyId(purchaseChannelsDTO.getModifyId());
					query.setModifyName(purchaseChannelsDTO.getModifyName());
					purchaseChannelsDAO.updateOrderStatus(query);
				}
				purchaseChannelsDAO.update(purchaseChannelsDTO);
				executeResult.setResultMessage("编辑成功");
				if (!query.getStatus().equalsIgnoreCase(purchaseChannelsDTO.getStatus())) {
					addEditDetailMethod(ad_type, "启用状态", purchaseChannelsDTO.getId(), purchaseChannelsDTO.getModifyId(),
							purchaseChannelsDTO.getModifyName());
				}
			}

		} catch (Exception e) {
			logger.error("执行方法【modifyStatus】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<List<PurchaseChannelsDTO>> queryPurchaseChannelsList() {
		logger.info("PurchaseChannelsServiceImpl-queryPurchaseChannelsList服务执行开始,参数:");
		ExecuteResult<List<PurchaseChannelsDTO>> rs = new ExecuteResult<List<PurchaseChannelsDTO>>();
		try {
			List<PurchaseChannelsDTO> list = purchaseChannelsDAO.queryPurchaseChannelsList();
			if (list.size() > 0) {

				for (PurchaseChannelsDTO purchaseChannelsDTO : list) {
					List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOs = purchaseChannelsSubDAO
							.selectByTypeId(purchaseChannelsDTO.getId());
					purchaseChannelsDTO.setPurchaseChannelsSubDTOList(purchaseChannelsSubDTOs);
				}
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("fail");
				rs.addErrorMessage("查询结果为空");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("PurchaseChannelsServiceImpl-queryPurchaseChannelsList服务执行异常:" + e);
		}
		logger.info("PurchaseChannelsServiceImpl-queryPurchaseChannelsList服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

}