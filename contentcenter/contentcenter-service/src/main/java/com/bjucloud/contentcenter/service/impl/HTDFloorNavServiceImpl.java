package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.*;
import com.bjucloud.contentcenter.dto.FloorContentPicSubDTO;
import com.bjucloud.contentcenter.dto.FloorQueryOutDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;
import com.bjucloud.contentcenter.service.HTDFloorNavService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/20.
 */
@Service("htdFloorNavService")
public class HTDFloorNavServiceImpl implements HTDFloorNavService {

    private static final Logger logger = LoggerFactory.getLogger(HTDFloorNavServiceImpl.class);

    @Resource
    private HTDFloorNavDAO htdFloorNavDAO;

    @Resource
    private HTDAdvertisementDAO htdAdvertisementDAO;

    @Resource
    private FloorQueryOutDAO floorQueryOutDAO;

    @Resource
    private FloorContentPicSubDAO floorContentPicSubDAO;


    @Override
    public ExecuteResult<HTDFloorNavDTO> queryById(Long id) {
        ExecuteResult<HTDFloorNavDTO> result = new ExecuteResult<HTDFloorNavDTO>();
        try{
            HTDFloorNavDTO htdFloorNavDTO = htdFloorNavDAO.queryById(id);
            if(htdFloorNavDTO != null){
                FloorContentPicSubDTO floorContentPicSubDTO = new FloorContentPicSubDTO();
                floorContentPicSubDTO.setFloorNavId(id);
                List<FloorContentPicSubDTO> floorContentPicSubDTOs = floorContentPicSubDAO.selectListByCondition(floorContentPicSubDTO,null);
                if(floorContentPicSubDTOs != null && floorContentPicSubDTOs.size() > 0){
                    htdFloorNavDTO.setPicSubDTOs(floorContentPicSubDTOs);
                }
                result.setResult(htdFloorNavDTO);
                result.setResultMessage("success");
            }else {
                result.setResultMessage("没有此条记录");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            result.setResultMessage(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyFloorNavById(HTDFloorNavDTO dto) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            HTDFloorNavDTO beforeDto = htdFloorNavDAO.queryById(dto.getId());
            htdFloorNavDAO.update(dto);
            this.isChange(dto, beforeDto);
            result.setResultMessage("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.setResultMessage(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
        }
        return result;
    }



    /**
     *
     * <p>
     * Discription:[判断店铺信息是否变更 变更插入申请表]
     * </p>
     */
    private void isChange(HTDFloorNavDTO dto, HTDFloorNavDTO beforeDto) {
        HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
        ediDTO.setModify_type("8");
        ediDTO.setRecord_id(dto.getId());
        ediDTO.setOperator_id(dto.getModifyId());
        ediDTO.setOperator_name(dto.getModifyName());
        ediDTO.setChange_table_id("floor_nav");
        if (this.validaIsNotSameBoforeAndAfter(beforeDto.getName(), dto.getName())) {
            // 楼层导航名称
            ediDTO.setContent_name("楼层导航名称");
            ediDTO.setChange_field_id("name");
            ediDTO.setBefore_change(beforeDto.getName());
            ediDTO.setAfter_change(dto.getName());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if (this.validaIsNotSameBoforeAndAfter(beforeDto.getNavTemp(), dto.getNavTemp())) {
            // 楼层导航模板
            ediDTO.setContent_name("楼层导航模板");
            ediDTO.setChange_field_id("nav_temp");
            ediDTO.setBefore_change(beforeDto.getNavTemp());
            ediDTO.setAfter_change(dto.getNavTemp());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if (this.validaIsNotSameBoforeAndAfter(beforeDto.getNavTempSrc(), dto.getNavTempSrc())) {
            // 楼层导航模板样式
            ediDTO.setContent_name("楼层导航模板样式");
            ediDTO.setChange_field_id("nav_temp_src");
            ediDTO.setBefore_change(beforeDto.getNavTempSrc());
            ediDTO.setAfter_change(dto.getNavTempSrc());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if (this.validaIsNotSameBoforeAndAfter(beforeDto.getSortNum(), dto.getSortNum())) {
            //排序号
            ediDTO.setContent_name("排序号");
            ediDTO.setChange_field_id("sort_num");
            ediDTO.setBefore_change(beforeDto.getSortNum().toString());
            ediDTO.setAfter_change(dto.getSortNum().toString());
            htdAdvertisementDAO.addEditDetail(ediDTO);
        }
        if (this.validaIsNotSameBoforeAndAfter(beforeDto.getStatus(), dto.getStatus())) {
            //状态
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

    @Override
    public ExecuteResult<DataGrid<HTDFloorNavDTO>> queryListByCondition(HTDFloorNavDTO record, Pager page) {

        ExecuteResult<DataGrid<HTDFloorNavDTO>> result = new ExecuteResult<DataGrid<HTDFloorNavDTO>>();
        DataGrid<HTDFloorNavDTO> dataGrid = new DataGrid<HTDFloorNavDTO>();
        try {
            Long count = htdFloorNavDAO.selectCountByCondition(record);
            if (count > 0){
                List<HTDFloorNavDTO> subAdDTOList = htdFloorNavDAO.selectListByCondition(record, page);
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
    public ExecuteResult<DataGrid<FloorQueryOutDTO>> queryFloorAndNavListByCondition(FloorQueryOutDTO dto, Pager page) {
        ExecuteResult<DataGrid<FloorQueryOutDTO>> result = new ExecuteResult<DataGrid<FloorQueryOutDTO>>();
        DataGrid<FloorQueryOutDTO> dataGrid = new DataGrid<FloorQueryOutDTO>();
        try{
            Long count = floorQueryOutDAO.selectCountByCondition(dto);
            if(count > 0){
                List<FloorQueryOutDTO> list = floorQueryOutDAO.selectListByCondition(dto,page);
                dataGrid.setRows(list);
            }
            dataGrid.setTotal(count);
            result.setResult(dataGrid);
            result.setResultMessage("success");
        }catch (Exception e){
            result.addErrorMessage("执行该方法【queryFloorAndNavListByCondition】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> add(HTDFloorNavDTO dto) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            htdFloorNavDAO.insert(dto);

            HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
            ediDTO.setModify_type("8");
            ediDTO.setRecord_id(dto.getId());
            ediDTO.setOperator_id(dto.getModifyId());
            ediDTO.setOperator_name(dto.getModifyName());
            ediDTO.setChange_table_id("floor_nav");
            ediDTO.setContent_name("新增楼层导航");
            ediDTO.setChange_field_id("");
            ediDTO.setBefore_change("");
            ediDTO.setAfter_change("");
            htdAdvertisementDAO.addEditDetail(ediDTO);
            result.setResult("success");
        }catch (Exception e){
            result.addErrorMessage("执行该方法【add】报错：" + e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public DataGrid<HTDFloorNavDTO> queryBySortNum(Long sortNum) {
        DataGrid<HTDFloorNavDTO> dataGrid = new DataGrid<HTDFloorNavDTO>();
        try{
            List list = htdFloorNavDAO.selectBySortNum(sortNum);
            dataGrid.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }

    @Override
    public ExecuteResult<String> modifyBySortAndFloorId(HTDFloorNavDTO htdFloorNavDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            htdFloorNavDAO.updateBySortAndFloorId(htdFloorNavDTO);
            result.setResult("success");
        }catch (Exception e){
            result.addErrorMessage("执行该方法【modifyBySortAndFloorId】报错：" + e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public DataGrid<HTDFloorNavDTO> queryByFloorId(Long floorId) {
        DataGrid<HTDFloorNavDTO> dataGrid  = new DataGrid<HTDFloorNavDTO>();
        try{
            List list = htdFloorNavDAO.selectByFloorId(floorId);
            dataGrid.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }

    @Override
    public DataGrid<HTDFloorNavDTO> queryNavName() {
        DataGrid<HTDFloorNavDTO> dataGrid = new DataGrid<HTDFloorNavDTO>();
        try{
            List<HTDFloorNavDTO> list = htdFloorNavDAO.selectRecName();
            dataGrid.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }

}

