package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.bjucloud.contentcenter.common.enmu.GlobalConstant;
import com.bjucloud.contentcenter.dao.*;
import com.bjucloud.contentcenter.dto.*;
import com.bjucloud.contentcenter.service.SubContentService;
import com.hp.hpl.sparta.xpath.TrueExpr;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 
 * <p>
 * Description: [城市站内容接口的实现类]
 * </p>
 */
@Service("subContentService")
public class SubContentServiceImpl implements SubContentService {
	private final static Logger LOGGER = LoggerFactory.getLogger(SubContentServiceImpl.class);

	@Resource
	private SubContentDAO subContentDAO;
	@Resource
	private SubAdDAO subAdDAO;
	@Resource
	private SubContentSubDAO subContentSubDAO;
	@Resource
	private SubContentPicSubDAO subContentPicSubDAO;
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;

	@Override
	public ExecuteResult<DataGrid<SubContentDTO>> queryListByCondition(SubContentDTO record, Pager page) {
		ExecuteResult<DataGrid<SubContentDTO>> result = new ExecuteResult<DataGrid<SubContentDTO>>();
		DataGrid<SubContentDTO> dataGrid = new DataGrid<SubContentDTO>();
		try {
			Long count = subContentDAO.selectCountByCondition(record);
			if (count > 0){
				List<SubContentDTO> subContentDTOList = subContentDAO.selectListByCondition(record, page);
				dataGrid.setRows(subContentDTOList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		}catch (Exception e){
			result.addErrorMessage("执行该方法【queryListByCondition】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<SubContentDTO> queryBySubAdId(Long subAdId) {
		ExecuteResult<SubContentDTO> result = new ExecuteResult<SubContentDTO>();
		try {
			SubContentDTO subContentDTO = subContentDAO.selectBySubAdId(subAdId);
			if (subContentDTO == null){
				result.addErrorMessage("该城市站内容信息不存在");
				return result;
			}
			List<SubContentSubDTO> subContentSubDTOs = subContentSubDAO.selectBySubcontentId(subContentDTO.getId());
			subContentDTO.setSubContentSubDTOs(subContentSubDTOs);
			List<SubContentPicSubDTO> subContentPicSubDTOs = subContentPicSubDAO.selectBySubcontentId(subContentDTO.getId());
			subContentDTO.setSubContentPicSubDTOs(subContentPicSubDTOs);
			SubAdDTO subAdDTO = subAdDAO.selectBySubAdId(subContentDTO.getSubAdId());
			if (subAdDTO != null){
				subContentDTO.setSubAdDTO(subAdDTO);
			}

			result.setResult(subContentDTO);
		}catch (Exception e){
			result.addErrorMessage("执行该方法【queryBySubAdId】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> firstAdd(SubContentDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (dto == null){
				result.addErrorMessage("参数不为空! ");
			}
			Long subId = dto.getId();
			//之前的城市站内容信息
			ExecuteResult<SubContentDTO> result1 = this.queryBySubAdId(dto.getSubAdId());
			if(result1.getResult() != null){
				SubContentDTO beforeSubContentDTO = result1.getResult();
				//之前的城市中内容供应商子信息
				List<SubContentSubDTO> beforeSubDTOs = subContentSubDAO.selectBySubcontentId(subId);
				//之前的城市站图片子信息
				List<SubContentPicSubDTO> beforePicSubDTOs = subContentPicSubDAO.selectBySubcontentId(subId);
				beforeSubContentDTO.setSubContentSubDTOs(beforeSubDTOs);
				beforeSubContentDTO.setSubContentPicSubDTOs(beforePicSubDTOs);
//				this.isChangeContent(dto, beforeSubContentDTO);
			}else{
				dto.setCreateId(dto.getModifyId());
				dto.setCreateName(dto.getModifyName());
				this.subContentDAO.insert(dto);
//				this.change(dto);
			}
			// 先将供应商的信息根据城市站内容信息ID全部删掉，重新插入
			subContentSubDAO.deleteBySubContentId(dto.getId());
			// 更新城市站供应商信息
			List<SubContentSubDTO> subDTOs = dto.getSubContentSubDTOs();
			if (subDTOs != null && subDTOs.size()> 0){
				for (int i=0; i<subDTOs.size(); i++){
					subDTOs.get(i).setSubContentId(dto.getId());
					subDTOs.get(i).setSortNum(Long.valueOf(i+1));
//					if (subContentSubDTO.getId() == null || subContentSubDTO.getId() == 0L){
					subContentSubDAO.insert(subDTOs.get(i));
//					}else{
//						subContentSubDAO.updateByPrimaryKeySelective(subContentSubDTO);
//					}
				}
			}
			// 更新城市站图片信息
			List<SubContentPicSubDTO> subpicDTOs = dto.getSubContentPicSubDTOs();
			if (subpicDTOs != null && subpicDTOs.size() > 0){
				for (SubContentPicSubDTO subContentPicSubDTO : subpicDTOs){
					subContentPicSubDTO.setSubContentId(dto.getId());
					if (subContentPicSubDTO.getId() == null || subContentPicSubDTO.getId() == 0L){
						subContentPicSubDAO.insert(subContentPicSubDTO);
					}else{
						subContentPicSubDAO.updateByPrimaryKeySelective(subContentPicSubDTO);
					}
				}
			}
//			this.isChangeContent(dto, beforeSubContentDTO);
			result.setResultMessage("1");
			result.setResult("0");
		}catch (Exception e){
			result.addErrorMessage("执行方法【firstAdd】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modify(SubContentDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (dto == null){
				result.addErrorMessage("参数不为空! ");
			}
			Long subId = dto.getId();
			//之前的城市站内容信息
			ExecuteResult<SubContentDTO> result1 = this.queryBySubAdId(dto.getSubAdId());
			if(result1.getResult() != null){
				SubContentDTO beforeSubContentDTO = result1.getResult();
				//之前的城市中内容供应商子信息
				List<SubContentSubDTO> beforeSubDTOs = subContentSubDAO.selectBySubcontentId(subId);
				//之前的城市站图片子信息
				List<SubContentPicSubDTO> beforePicSubDTOs = subContentPicSubDAO.selectBySubcontentId(subId);
				beforeSubContentDTO.setSubContentSubDTOs(beforeSubDTOs);
				beforeSubContentDTO.setSubContentPicSubDTOs(beforePicSubDTOs);
				this.isChangeContent(dto, beforeSubContentDTO);
			}else{
				dto.setCreateId(dto.getModifyId());
				dto.setCreateName(dto.getModifyName());
				this.subContentDAO.insert(dto);
				this.change(dto);
			}
			// 先将供应商的信息根据城市站内容信息ID全部删掉，重新插入
			subContentSubDAO.deleteBySubContentId(dto.getId());
			// 更新城市站供应商信息
			List<SubContentSubDTO> subDTOs = dto.getSubContentSubDTOs();
			if (subDTOs != null && subDTOs.size()> 0){
				for (int i=0; i<subDTOs.size(); i++){
					subDTOs.get(i).setSubContentId(dto.getId());
					subDTOs.get(i).setSortNum(Long.valueOf(i+1));
//					if (subContentSubDTO.getId() == null || subContentSubDTO.getId() == 0L){
						subContentSubDAO.insert(subDTOs.get(i));
//					}else{
//						subContentSubDAO.updateByPrimaryKeySelective(subContentSubDTO);
//					}
				}
			}
			// 更新城市站图片信息
			List<SubContentPicSubDTO> subpicDTOs = dto.getSubContentPicSubDTOs();
			if (subpicDTOs != null && subpicDTOs.size() > 0){
				this.subContentPicSubDAO.deleteBySubContentId(dto.getId());
				for (SubContentPicSubDTO subContentPicSubDTO : subpicDTOs){
					subContentPicSubDTO.setSubContentId(dto.getId());
					this.subContentPicSubDAO.insert(subContentPicSubDTO);
				}
			}
//			this.isChangeContent(dto, beforeSubContentDTO);
			result.setResultMessage("1");
			result.setResult("0");
		}catch (Exception e){
			result.addErrorMessage("执行方法【saveSubAd】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> saveContentSub(SubContentSubDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (dto == null){
				result.addErrorMessage("参数不为空! ");
			}
			subContentSubDAO.insert(dto);
			result.setResultMessage("1");
			result.setResult("0");
		}catch (Exception e){
			result.addErrorMessage("执行方法【saveContentSub】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}


	@Override
	public ExecuteResult<Boolean> deleteById(String sellerCode) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			subContentSubDAO.deleteBySellerCode(sellerCode);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> saveSubContent(SubContentDTO subContentDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (subContentDTO == null){
				result.addErrorMessage("参数不为空! ");
			}
			subContentDAO.insert(subContentDTO);
			result.setResultMessage("1");
			result.setResult("0");
		}catch (Exception e){
			result.addErrorMessage("执行方法【saveSubContent】报错：" + e.getMessage());
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
	private void isChangeContent(SubContentDTO dto, SubContentDTO beforeDto) {
		HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
		ediDTO.setModify_type("11");//业务类型
		ediDTO.setRecord_id(dto.getId());// 记录ID
		ediDTO.setOperator_id(dto.getModifyId());// 操作人ID
		ediDTO.setOperator_name(dto.getModifyName());// 操作人名称
		ediDTO.setChange_table_id("sub_content");// 修改表ID

		if (this.validaIsNotSameBoforeAndAfter1(beforeDto.getSubContentPicSubDTOs(), dto.getSubContentPicSubDTOs()) ){
			ediDTO.setContent_name("坑位图片");
			ediDTO.setChange_field_id("sub_content_pic_sub");
			ediDTO.setBefore_change(beforeDto.getSubContentPicSubDTOs().toString());
			ediDTO.setAfter_change(dto.getSubContentPicSubDTOs().toString());
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter2(beforeDto.getSubContentSubDTOs(), dto.getSubContentSubDTOs())){
			ediDTO.setContent_name("供应商");
			ediDTO.setChange_field_id("sub_content_sub");
			ediDTO.setBefore_change(beforeDto.getSubContentSubDTOs().toString());
			ediDTO.setAfter_change(dto.getSubContentSubDTOs().toString());
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
	}

	/**
	 *
	 * <p>
	 * Discription:[判断城市站内容信息是否变更 变更插入申请表]
	 * </p>
	 */
	private void change(SubContentDTO dto) {
		HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
		ediDTO.setModify_type("11");//业务类型
		ediDTO.setRecord_id(dto.getId());// 记录ID
		ediDTO.setOperator_id(dto.getModifyId());// 操作人ID
		ediDTO.setOperator_name(dto.getModifyName());// 操作人名称
		ediDTO.setChange_table_id("sub_content");// 修改表ID
		ediDTO.setContent_name("新增城市站内容信息");
		ediDTO.setChange_field_id("");
		ediDTO.setBefore_change("");
		ediDTO.setAfter_change("");
		htdAdvertisementDAO.addEditDetail(ediDTO);
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
	private boolean validaIsNotSameBoforeAndAfter1(List<SubContentPicSubDTO> befores, List<SubContentPicSubDTO> afters) {
		boolean flag  = false;
		if (befores == null && afters != null) {
			flag = true;
		}else if (befores != null && afters != null && befores.size() != afters.size()) {
			flag = true;
		}else if (!flag){
			for(int i=0; i<befores.size(); i++){
				SubContentPicSubDTO before = befores.get(i);
				SubContentPicSubDTO after = afters.get(i);
				if (before == null && after != null) {
					flag = true;
					break;
				} else if (before != null && after != null && validaIsNotSameBoforeAndAfter(before.getSubContentId(),after.getSubContentId())) {
					flag = true;
					break;
				}  else if (before != null && after != null && validaIsNotSameBoforeAndAfter(before.getPicUrl(),after.getPicUrl())) {
					flag = true;
					break;
				} else if (before != null && after != null && validaIsNotSameBoforeAndAfter(before.getLinkUrl(),after.getLinkUrl())) {
					flag = true;
					break;
				}else {
					flag = false;
				}
			}
		}else{
			flag = false;
		}
		return flag;
	}
	private boolean validaIsNotSameBoforeAndAfter2(List<SubContentSubDTO> befores, List<SubContentSubDTO> afters) {
		boolean flag  = false;
		if (befores == null && afters != null) {
			flag = true;
		}else if (befores != null && afters != null && befores.size() != afters.size()) {
			flag = true;
		}else if (!flag){
			for(int i=0; i<befores.size(); i++){
				SubContentSubDTO before = befores.get(i);
				SubContentSubDTO after = afters.get(i);
				if (before == null && after != null) {
					flag = true;
				}  else if (before != null && after != null && validaIsNotSameBoforeAndAfter(before.getSortNum(),after.getSortNum())) {
					flag = true;
				}else {
					flag = false;
				}
			}
		}else{
			flag = false;
		}
		return flag;
	}

}