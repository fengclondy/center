package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dao.FloorContentDAO;
import com.bjucloud.contentcenter.dao.FloorContentPicSubDAO;
import com.bjucloud.contentcenter.dao.FloorContentSubDAO;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.domain.Floor;
import com.bjucloud.contentcenter.dto.FloorContentDTO;
import com.bjucloud.contentcenter.dto.FloorContentPicSubDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;
import com.bjucloud.contentcenter.service.FloorContentExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/8.
 */
@Service("floorContentExportService")
public class FloorContentExportServiceImpl implements FloorContentExportService {
    private static final Logger logger = LoggerFactory.getLogger(FloorContentExportServiceImpl.class);

    @Resource
    private FloorContentDAO floorContentDAO;
    @Resource
    private FloorContentPicSubDAO floorContentPicSubDAO;
    @Resource
    private HTDAdvertisementDAO htdAdvertisementDAO;


    @Override
    public ExecuteResult<FloorContentDTO> queryByNavId(Long navId) {
        ExecuteResult<FloorContentDTO> result = new ExecuteResult<FloorContentDTO>();
        try{
            FloorContentDTO floorContentDTO = floorContentDAO.selectByNavId(navId);
            result.setResult(floorContentDTO);
            result.setResultMessage("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyFloorNavPic(HTDFloorNavDTO htdFloorNavDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(htdFloorNavDTO.getId() != null){
                List<FloorContentPicSubDTO> subDTOs = this.floorContentPicSubDAO.selectByNavId(htdFloorNavDTO.getId());
                if(subDTOs != null && subDTOs.size()>0){
                    this.floorContentPicSubDAO.deleteByFloorNavId(htdFloorNavDTO.getId());
                    for(FloorContentPicSubDTO dto : htdFloorNavDTO.getPicSubDTOs()){
                        dto.setFloorNavId(htdFloorNavDTO.getId());
                        this.floorContentPicSubDAO.insert(dto);
                    }
                    HTDFloorNavDTO before = new HTDFloorNavDTO();
                    before.setPicSubDTOs(subDTOs);
                    this.isChange(htdFloorNavDTO,before);
                }else{
                    this.change(htdFloorNavDTO);
                    if(htdFloorNavDTO.getPicSubDTOs() != null && htdFloorNavDTO.getPicSubDTOs().size() > 0){
                        for(FloorContentPicSubDTO dto : htdFloorNavDTO.getPicSubDTOs()){
                            dto.setFloorNavId(htdFloorNavDTO.getId());
                            this.floorContentPicSubDAO.insert(dto);
                        }
                    }
                }
                result.setResult("success");
            }else{
                result.setResult("导航模板Id不能为空！");
                return result;
            }
            result.setResult("success");
        }catch (Exception e){
            logger.error(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    private void isChange(HTDFloorNavDTO dto,HTDFloorNavDTO beforeDto) {
        HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
        ediDTO.setModify_type("9");
        ediDTO.setRecord_id(dto.getId());
        ediDTO.setOperator_id(dto.getModifyId());
        ediDTO.setOperator_name(dto.getModifyName());
        if(dto.getPicSubDTOs() != null && dto.getPicSubDTOs().size() > 0){
            for(FloorContentPicSubDTO afterPic : dto.getPicSubDTOs()){
                for(FloorContentPicSubDTO beforePic : beforeDto.getPicSubDTOs()){
                    if(afterPic.getSortNum()==beforePic.getSortNum()) {
                        if (this.validaIsNotSameBoforeAndAfter(beforePic.getPicUrl(), afterPic.getPicUrl())) {
                            //图片地址
                            ediDTO.setChange_table_id("floor_content_pic_sub");
                            ediDTO.setContent_name("图片地址");
                            ediDTO.setChange_field_id("pic_url");
                            ediDTO.setBefore_change(beforePic.getPicUrl());
                            ediDTO.setAfter_change(afterPic.getPicUrl());
                            htdAdvertisementDAO.addEditDetail(ediDTO);
                        }
                        if (this.validaIsNotSameBoforeAndAfter(beforePic.getLinkUrl(), afterPic.getLinkUrl())) {
                            //图片链接地址
                            ediDTO.setChange_table_id("floor_content_pic_sub");
                            ediDTO.setContent_name("图片链接地址");
                            ediDTO.setChange_field_id("link_url");
                            ediDTO.setBefore_change(beforePic.getLinkUrl());
                            ediDTO.setAfter_change(afterPic.getLinkUrl());
                            htdAdvertisementDAO.addEditDetail(ediDTO);
                        }
                    }
                }
            }
        }
    }


    private void change(HTDFloorNavDTO dto) {
        HTDEditDetailInfoDTO ediDTO = new HTDEditDetailInfoDTO();
        ediDTO.setModify_type("9");
        ediDTO.setRecord_id(dto.getId());
        ediDTO.setOperator_id(dto.getModifyId());
        ediDTO.setOperator_name(dto.getModifyName());
        if (dto.getPicSubDTOs() != null && dto.getPicSubDTOs().size() > 0) {
            //图片地址
            ediDTO.setChange_table_id("floor_content_pic_sub");
            ediDTO.setContent_name("添加图片地址及链接地址");
            ediDTO.setChange_field_id("pic_url");
            ediDTO.setBefore_change("");
            ediDTO.setAfter_change("");
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
