package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.FloorContentSubDAO;
import com.bjucloud.contentcenter.dao.FloorInfoDAO;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dto.FloorContentSubDTO;
import com.bjucloud.contentcenter.dto.FloorInfoDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.service.FloorInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/22.
 */
@Service("floorInfoService")
public class FloorInfoServiceImpl implements FloorInfoService{
    private static final Logger logger = LoggerFactory.getLogger(FloorInfoServiceImpl.class);

    @Resource
    private FloorInfoDAO floorInfoDAO;
    @Resource
    private HTDAdvertisementDAO htdAdvertisementDAO;

    @Resource
    private FloorContentSubDAO floorContentSubDAO;

    @Override
    public DataGrid<FloorInfoDTO> queryListByCondition(FloorInfoDTO record, Pager page) {
        // 查询数据库中所有的数据 返回list
        DataGrid<FloorInfoDTO> dataGrid = new DataGrid<FloorInfoDTO>();
        try {
            List<FloorInfoDTO> floorInfoDTOList = floorInfoDAO.selectListByCondition(record, page);
            Long size = floorInfoDAO.selectCountByCondition(record);
            dataGrid.setRows(floorInfoDTOList);
            dataGrid.setTotal(size);
        }catch (Exception e){
            logger.error("执行方法【queryListByCondition】报错！{}", e);
            throw new RuntimeException(e);
        }
        return dataGrid;
    }

    @Override
    public ExecuteResult<String> addFloorInfo(FloorInfoDTO floorInfoDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(floorInfoDTO != null){
                floorInfoDAO.insertSelective(floorInfoDTO);
                if(floorInfoDTO.getSubDTOList() != null && floorInfoDTO.getSubDTOList().size() > 0){
                    for(FloorContentSubDTO subDTO : floorInfoDTO.getSubDTOList()){
                        subDTO.setFloorId(floorInfoDTO.getId());
                        this.floorContentSubDAO.insert(subDTO);
                    }
                }

                HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
                ediDTO.setModify_type("7");
                ediDTO.setRecord_id(floorInfoDTO.getId());
                ediDTO.setOperator_id(floorInfoDTO.getModifyId());
                ediDTO.setOperator_name(floorInfoDTO.getModifyName());
                ediDTO.setChange_table_id("floor_info");
                ediDTO.setContent_name("添加楼层");
                ediDTO.setChange_field_id("");
                ediDTO.setBefore_change("");
                ediDTO.setAfter_change("");
                htdAdvertisementDAO.addEditDetail(ediDTO);
                result.setResultMessage("success");
            }else{
                result.setResultMessage("入参不能为空！");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }


    @Override
    public ExecuteResult<String> delete(Integer id) {
        ExecuteResult<String> er = new ExecuteResult();
        try {
            this.floorInfoDAO.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            er.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return er;
    }

    @Override
    public ExecuteResult<FloorInfoDTO> queryById(Long id) {
        ExecuteResult<FloorInfoDTO> result = new ExecuteResult<FloorInfoDTO>();
        try{
            FloorInfoDTO floorInfoDTO = floorInfoDAO.selectById(id);
            if(floorInfoDTO != null){
                FloorContentSubDTO floorContentSubDTO = new FloorContentSubDTO();
                floorContentSubDTO.setFloorId(id);
                List<FloorContentSubDTO> floorContentSubDTOs = floorContentSubDAO.selectListByCondition(floorContentSubDTO,null);
                if(floorContentSubDTOs != null && floorContentSubDTOs.size() > 0){
                    floorInfoDTO.setSubDTOList(floorContentSubDTOs);
                }
                result.setResult(floorInfoDTO);
                result.setResultMessage("success");
            }else {
                result.setResultMessage("没有查到相关信息！");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> delete(FloorInfoDTO floorInfoDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            floorInfoDAO.delete(floorInfoDTO);
            result.setResult("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyByCondition(FloorInfoDTO dto) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(dto.getId() == null){
                result.setResult("Id不能为空！");
                result.getErrorMessages().add("Id不能为空！");
                return result;
            }
            FloorInfoDTO before = floorInfoDAO.selectById(dto.getId());
            floorInfoDAO.updateByCondition(dto);
            floorContentSubDAO.deleteByFloorId(dto.getId());
            if(dto.getSubDTOList() != null && dto.getSubDTOList().size() > 0){
                for(FloorContentSubDTO subDTO : dto.getSubDTOList()){
                    subDTO.setFloorId(dto.getId());
                    this.floorContentSubDAO.insert(subDTO);
                }
            }
            isChange(before,dto);
            result.setResult("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyStatus(FloorInfoDTO floorInfoDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            FloorInfoDTO beforeDto = floorInfoDAO.selectById(floorInfoDTO.getId());
            floorInfoDAO.updateByCondition(floorInfoDTO);

            HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
            ediDTO.setModify_type("7");
            ediDTO.setRecord_id(floorInfoDTO.getId());
            ediDTO.setOperator_id(floorInfoDTO.getModifyId());
            ediDTO.setOperator_name(floorInfoDTO.getModifyName());
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setContent_name("状态");
            ediDTO.setChange_field_id("status");
            ediDTO.setBefore_change(beforeDto.getStatus());
            ediDTO.setAfter_change(floorInfoDTO.getStatus());
            htdAdvertisementDAO.addEditDetail(ediDTO);
            result.setResult("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyStatusBySortNum(String status,Long sortNum) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            floorInfoDAO.updateBySortNum(status,sortNum);
            result.setResult("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public DataGrid<FloorInfoDTO> queryListAll(Pager page) {
        DataGrid<FloorInfoDTO> dataGrid = new DataGrid<FloorInfoDTO>();
        try{
            List<FloorInfoDTO> list = floorInfoDAO.queryListAll(page);
            Long count = floorInfoDAO.queryListAllCount();
            dataGrid.setRows(list);
            dataGrid.setTotal(count);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return dataGrid;
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


    private void isChange(FloorInfoDTO before,FloorInfoDTO after) {
        HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
        ediDTO.setModify_type("7");
        ediDTO.setRecord_id(before.getId());
        ediDTO.setOperator_id(after.getModifyId());
        ediDTO.setOperator_name(after.getModifyName());
        if(validaIsNotSameBoforeAndAfter(before.getName(),after.getName())){
            ediDTO.setContent_name("楼层名称");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("name");
            ediDTO.setBefore_change(before.getName());
            ediDTO.setAfter_change(after.getName());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getIconUrl(),after.getIconUrl())){
            ediDTO.setContent_name("楼层图标");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("icon_url");
            ediDTO.setBefore_change(before.getIconUrl());
            ediDTO.setAfter_change(after.getIconUrl());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getLeftPicTitle(),after.getLeftPicTitle())){
            ediDTO.setContent_name("左侧图片标题");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("left_pic_title");
            ediDTO.setBefore_change(before.getLeftPicTitle());
            ediDTO.setAfter_change(after.getLeftPicTitle());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getLeftPicLinkUrl(),after.getLeftPicLinkUrl())){
            ediDTO.setContent_name("左侧图片链接地址");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("left_pic_link_url");
            ediDTO.setBefore_change(before.getLeftPicLinkUrl());
            ediDTO.setAfter_change(after.getLeftPicLinkUrl());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getLeftPicUrl(),after.getLeftPicUrl())){
            ediDTO.setContent_name("左侧图片地址");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("left_pic_url");
            ediDTO.setBefore_change(before.getLeftPicUrl());
            ediDTO.setAfter_change(after.getLeftPicUrl());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getSortNum(),after.getSortNum())){
            ediDTO.setContent_name("排序号");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("sort_num");
            ediDTO.setBefore_change(before.getSortNum().toString());
            ediDTO.setAfter_change(after.getSortNum().toString());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if(validaIsNotSameBoforeAndAfter(before.getShowBrand(),after.getShowBrand())){
            ediDTO.setContent_name("是否展示品牌");
            ediDTO.setChange_table_id("floor_info");
            ediDTO.setChange_field_id("show_brand");
            ediDTO.setBefore_change(before.getShowBrand());
            ediDTO.setAfter_change(after.getShowBrand());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
    }

}
