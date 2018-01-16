package com.bjucloud.contentcenter.service.convert;

import java.util.ArrayList;
import java.util.List;

import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.domain.HomepagePopupTerminalAd;
import com.bjucloud.contentcenter.dto.PopupAdDTO;
import com.bjucloud.contentcenter.enums.PopupAdTerminalTypeEnums;


public class HomepagePopupAdConvert extends AbstractConvert<HomepagePopupAd, PopupAdDTO> {

    /**
     * 填充目标对象
     *
     * @param homepagePopupAd
     * @return TARGET
     */
    @Override
    protected PopupAdDTO populateTarget(HomepagePopupAd homepagePopupAd) {
        PopupAdDTO dto = new PopupAdDTO();
        List<String> terminalTypeList = new ArrayList<String>();
        String terminalTypeStr = "";
        dto.setId(homepagePopupAd.getId());
        dto.setAdName(homepagePopupAd.getAdName());
        dto.setPicUrl(homepagePopupAd.getPicUrl());
        dto.setLinkUrl(homepagePopupAd.getLinkUrl());
        dto.setStartTime(homepagePopupAd.getStartTime());
        dto.setEndTime(homepagePopupAd.getEndTime());
        dto.setOperatorId(homepagePopupAd.getModifyId());
        dto.setOperatorName(homepagePopupAd.getModifyName());
        dto.setOperateTime(homepagePopupAd.getModifyTime());
        if (homepagePopupAd.getTerminalAdList() != null && !homepagePopupAd.getTerminalAdList().isEmpty()) {
            for (HomepagePopupTerminalAd terminalAd : homepagePopupAd.getTerminalAdList()) {
                terminalTypeStr += "," + PopupAdTerminalTypeEnums.getTypeDesc(terminalAd.getTerminalType());
                terminalTypeList.add(terminalAd.getTerminalType());
            }
            dto.setTerminallTypeStr(terminalTypeStr.substring(1));
            dto.setTerminalTypeList(terminalTypeList);
        }
        return dto;
    }

    /**
     * 填充目标对象
     *
     * @param homepagePopupAdDTO
     * @return
     */
    @Override
    protected HomepagePopupAd populateSource(PopupAdDTO homepagePopupAdDTO) {
        HomepagePopupAd popupAd = new HomepagePopupAd();
        List<HomepagePopupTerminalAd> terminalAdList = new ArrayList<HomepagePopupTerminalAd>();
        HomepagePopupTerminalAd terminalAd = null;

        popupAd.setId(homepagePopupAdDTO.getId());
        popupAd.setAdName(homepagePopupAdDTO.getAdName());
        popupAd.setPicUrl(homepagePopupAdDTO.getPicUrl());
        popupAd.setLinkUrl(homepagePopupAdDTO.getLinkUrl());
        popupAd.setStartTime(homepagePopupAdDTO.getStartTime());
        popupAd.setEndTime(homepagePopupAdDTO.getEndTime());
        popupAd.setCreateId(homepagePopupAdDTO.getOperatorId());
        popupAd.setCreateName(homepagePopupAdDTO.getOperatorName());
        popupAd.setModifyId(homepagePopupAdDTO.getOperatorId());
        popupAd.setModifyName(homepagePopupAdDTO.getOperatorName());
        if (homepagePopupAdDTO.getTerminalTypeList() != null && !homepagePopupAdDTO.getTerminalTypeList().isEmpty()) {
           for (String terminalTypeCode : homepagePopupAdDTO.getTerminalTypeList()) {
               terminalAd = new HomepagePopupTerminalAd();
               terminalAd.setAdId(homepagePopupAdDTO.getId());
               terminalAd.setTerminalType(terminalTypeCode);
               terminalAd.setCreateId(homepagePopupAdDTO.getOperatorId());
               terminalAd.setCreateName(homepagePopupAdDTO.getOperatorName());
               terminalAd.setModifyId(homepagePopupAdDTO.getOperatorId());
               terminalAd.setModifyName(homepagePopupAdDTO.getOperatorName());
               terminalAdList.add(terminalAd);
           }
           popupAd.setTerminalAdList(terminalAdList);
        }
        return popupAd;
    }
}
