package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dao.MallChannelsDAO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.MallChannelsDTO;
import com.bjucloud.contentcenter.service.HotWordService;
import com.bjucloud.contentcenter.service.MallChannelsService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Service("mallChannelsService")
public class MallChannelsServiceImpl implements MallChannelsService {

	private static final Logger logger = LoggerFactory.getLogger(MallChannelsServiceImpl.class);

	@Resource
	private MallChannelsDAO mallChannelsDAO;
	@Resource
	private HTDAdvertisementDAO htdAdvertisementDAO;


	@Override
	public MallChannelsDTO getMallChannelsById(Long id) {
		MallChannelsDTO dto = null;
		try {
			dto = mallChannelsDAO.queryById(id);
		} catch (Exception e) {
			logger.error("执行方法【getMallChannelsById】报错！{}", e);
			throw new RuntimeException(e);
		}

		return dto;
	}

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:修改楼层]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallChannels(MallChannelsDTO mallRecDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (mallRecDTO != null || mallRecDTO.getId() != null){
				MallChannelsDTO beforeMallChan = mallChannelsDAO.queryById(mallRecDTO.getId());
				if("1".equalsIgnoreCase(mallRecDTO.getStatus())){
					mallChannelsDAO.updateSortNumZero(beforeMallChan.getSortNum());
				}
				if(mallRecDTO.getSortNum() != beforeMallChan.getSortNum()){
					mallChannelsDAO.updateSortNumZero(mallRecDTO.getSortNum());
				}
				mallChannelsDAO.update(mallRecDTO);
				this.isChangeContent(mallRecDTO, beforeMallChan);
				executeResult.setResultMessage("编辑成功");
				executeResult.setResult("success");
			}
		} catch (Exception e) {
			logger.error("执行方法【modifyMallChannels】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:新建楼层的远程调用方法的接口实现]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallChannels(MallChannelsDTO mallRecDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if("1".equalsIgnoreCase(mallRecDTO.getStatus())){
				mallChannelsDAO.updateSortNumZero(mallRecDTO.getSortNum());
			}
			mallChannelsDAO.insert(mallRecDTO);
			HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
			ediDTO.setModify_type("2");//业务类型
			ediDTO.setRecord_id(mallRecDTO.getId());// 记录ID
			ediDTO.setOperator_id(mallRecDTO.getModifyId());// 操作人ID
			ediDTO.setOperator_name(mallRecDTO.getModifyName());// 操作人名称
			ediDTO.setContent_name("创建商城频道导航");
			htdAdvertisementDAO.addEditDetail(ediDTO);
			executeResult.setResultMessage("保存成功");
			executeResult.setResult("success");
		} catch (Exception e) {
			logger.error("执行方法【addMallChannels】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public DataGrid<MallChannelsDTO> queryByCondition(MallChannelsDTO dto, Pager page) {
		DataGrid<MallChannelsDTO> dataGrid = new DataGrid<MallChannelsDTO>();
		try{
			Long count = mallChannelsDAO.selectCountByCondition(dto);
			if(count > 0){
				List<MallChannelsDTO> list = mallChannelsDAO.selectListByCondition(dto,page);
				dataGrid.setRows(list);
			}
			dataGrid.setTotal(count);
		}catch (Exception e){
			logger.error("执行方法【queryByCondition】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<String> delete(Long id) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		mallChannelsDAO.delete(id);
		result.setResult("success");
		return result;
	}

	/**
	 *
	 * <p>
	 * Discription:[判断商城频道导航信息是否变更 变更插入申请表]
	 * </p>
	 */
	private void isChangeContent(MallChannelsDTO dto, MallChannelsDTO beforeDto) {
		HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
		ediDTO.setModify_type("2");//业务类型
		ediDTO.setRecord_id(dto.getId());// 记录ID
		ediDTO.setOperator_id(dto.getModifyId());// 操作人ID
		ediDTO.setOperator_name(dto.getModifyName());// 操作人名称
		ediDTO.setChange_table_id("mall_channels");// 修改表ID

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getName(), dto.getName())) {
			// 名称
			String name = dto.getName();
			String beforeName = beforeDto.getName();
			ediDTO.setContent_name("名称");
			ediDTO.setChange_field_id("name");
			ediDTO.setBefore_change(beforeName);
			ediDTO.setAfter_change(name);
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getNameColor(), dto.getNameColor())) {
			// 名称色值
			String beforeNameColor = beforeDto.getNameColor();
			ediDTO.setContent_name("名称色值");
			ediDTO.setChange_field_id("name_color");
			ediDTO.setBefore_change(beforeNameColor);
			ediDTO.setAfter_change(dto.getNameColor());
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getLinkUrl(), dto.getLinkUrl())) {
			// 指向连接地址
			String beforeLinkUrl = beforeDto.getLinkUrl();
			String linkUrl = dto.getLinkUrl();
			ediDTO.setContent_name("指向连接地址");
			ediDTO.setChange_field_id("link_url");
			ediDTO.setBefore_change(beforeLinkUrl);
			ediDTO.setAfter_change(linkUrl);
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getSortNum(), dto.getSortNum())) {
			// 显示顺序
			Long beforeSortNum = beforeDto.getSortNum();
			Long sortNum = dto.getSortNum();
			ediDTO.setContent_name("显示顺序");
			ediDTO.setChange_field_id("sort_num");
			ediDTO.setBefore_change(beforeSortNum.toString());
			ediDTO.setAfter_change(sortNum.toString());
			htdAdvertisementDAO.addEditDetail(ediDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(beforeDto.getStatus(), dto.getStatus())) {
			// 状态
			String beforeStatus = beforeDto.getStatus();
			String status = dto.getStatus();
			ediDTO.setContent_name("状态");
			ediDTO.setChange_field_id("status");
			ediDTO.setBefore_change(beforeStatus);
			ediDTO.setAfter_change(status);
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
