package com.bjucloud.contentcenter.service.convert;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.util.DictionaryUtils;
import com.bjucloud.contentcenter.domain.HomepagePopupAd;
import com.bjucloud.contentcenter.domain.HomepagePopupTerminalAd;
import com.bjucloud.contentcenter.dto.HomepagePopupAdDTO;
import com.bjucloud.contentcenter.enums.PopupAdTerminalTypeEnums;


public class HomepagePopupAdConvert extends AbstractConvert<HomepagePopupAd, HomepagePopupAdDTO> {

    /**
     * 填充目标对象
     *
     * @param homepagePopupAd
     * @return TARGET
     */
    @Override
    protected HomepagePopupAdDTO populateTarget(HomepagePopupAd homepagePopupAd) {
        HomepagePopupAdDTO dto = new HomepagePopupAdDTO();
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
    protected HomepagePopupAd populateSource(HomepagePopupAdDTO homepagePopupAdDTO) {
        return null;
    }
}
