package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import cn.htd.common.Pager;
import cn.htd.common.DataGrid;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.SubAdDAO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.QuerySubAdOutDTO;
import com.bjucloud.contentcenter.dto.SubAdDTO;
import com.bjucloud.contentcenter.service.SubAdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * <p>
 * Description: [公告服务的处理接口的实现类]
 * </p>
 */
@Service("subAdService")
public class SubAdServiceImpl implements SubAdService {
	private final static Logger LOGGER = LoggerFactory.getLogger(SubAdServiceImpl.class);

	@Resource
	private SubAdDAO subAdDAO;
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;

	@Override
	public ExecuteResult<DataGrid<SubAdDTO>> queryListByCondition(SubAdDTO record, Pager page) {
		ExecuteResult<DataGrid<SubAdDTO>> result = new ExecuteResult<DataGrid<SubAdDTO>>();
		DataGrid<SubAdDTO> dataGrid = new DataGrid<SubAdDTO>();
		try {
			Long count = subAdDAO.selectCountByCondition(record);
			if (count > 0){
				List<SubAdDTO> subAdDTOList = subAdDAO.selectListByCondition(record, page);
				dataGrid.setRows(subAdDTOList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		}catch (Exception e){
			result.addErrorMessage("执行该方法【selectListByCondition】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<SubAdDTO> queryById(String id) {
		ExecuteResult<SubAdDTO> result = new ExecuteResult<SubAdDTO>();
		try {
			SubAdDTO subAdDTO = subAdDAO.selectById(id);
			result.setResult(subAdDTO);
		}catch (Exception e){
			result.addErrorMessage("执行方法【queryById】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<SubAdDTO> saveSubAd(SubAdDTO subAdDTO) {
		ExecuteResult<SubAdDTO> result = new ExecuteResult<SubAdDTO>();
		try {
			if (subAdDTO == null){
				result.addErrorMessage("参数不为空! ");
			}
			Long id = subAdDTO.getId();
			SubAdDTO beforeSubAdDTO = subAdDAO.selectById(id);;
			if (id != null){
				subAdDAO.update(subAdDTO);
				this.isChangeContent(subAdDTO, beforeSubAdDTO);
			}else {
				subAdDAO.add(subAdDTO);
				this.isAddContent(subAdDTO, beforeSubAdDTO);
			}
			result.setResultMessage("1");
			result.setResult(subAdDTO);
		}catch (Exception e){
			result.addErrorMessage("执行方法【saveSubAd】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<SubAdDTO> queryByAdId(Long id) {
		ExecuteResult<SubAdDTO> result = new ExecuteResult<SubAdDTO>();
		try{
			SubAdDTO dto = this.subAdDAO.selectByAdId(id);
			result.setResult(dto);
			result.setResultMessage("success");
		}catch (Exception e){
			result.addErrorMessage("执行方法【queryByAdId】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyStatusBySubId(String status, String subId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			this.subAdDAO.updateStatusBySubId(status,subId);
			result.setResult("success");
		}catch (Exception e){
			result.addErrorMessage("执行方法【modifyStatusBySubId】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 *
	 * <p>
	 * Discription:[判断城市站内容信息是否变更 变更插入申请表]
	 * </p>
	 */
	private void isAddContent(SubAdDTO dto, SubAdDTO beforeDto) {
		HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
		ediDTO.setModify_type("10");//业务类型
		ediDTO.setRecord_id(dto.getId());// 记录ID
		ediDTO.setOperator_id(dto.getModifyId());// 操作人ID
		ediDTO.setOperator_name(dto.getModifyName());// 操作人名称
		ediDTO.setChange_table_id("sub_ad");// 修改表ID

//		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getStatus(), dto.getStatus())) {
		ediDTO.setContent_name("新增城市站广告");
//		ediDTO.setChange_field_id("status");
//		ediDTO.setBefore_change(beforeDto.getStatus());
//		ediDTO.setAfter_change(dto.getStatus());
		htdAdvertisementDAO.addEditDetail(ediDTO);
//		}
	}
	/**
	 *
	 * <p>
	 * Discription:[判断城市站内容信息是否变更 变更插入申请表]
	 * </p>
	 */
	private void isChangeContent(SubAdDTO dto, SubAdDTO beforeDto) {
		HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
		ediDTO.setModify_type("10");//业务类型
		ediDTO.setRecord_id(dto.getId());// 记录ID
		ediDTO.setOperator_id(dto.getModifyId());// 操作人ID
		ediDTO.setOperator_name(dto.getModifyName());// 操作人名称
		ediDTO.setChange_table_id("sub_ad");// 修改表ID

		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getStatus(), dto.getStatus())) {
			ediDTO.setContent_name("状态");
			ediDTO.setChange_field_id("status");
			ediDTO.setBefore_change(beforeDto.getStatus());
			ediDTO.setAfter_change(dto.getStatus());
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
	}
	private boolean validaIsNotSameBoforeAndAfter(Object before, Object after) {

		if (before == null && after != null) {
			return true;
		} else if (before != null && after != null && !before.equals(after)) {
			return true;
		} else {
			return false;
		}
	}
}