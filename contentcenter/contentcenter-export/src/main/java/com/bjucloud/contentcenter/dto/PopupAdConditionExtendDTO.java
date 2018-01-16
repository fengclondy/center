package com.bjucloud.contentcenter.dto;

import cn.htd.common.util.DateUtils;

public class PopupAdConditionExtendDTO extends PopupAdConditionDTO {

    private String startTimeStr;

    private String endTimeStr;

    public PopupAdConditionExtendDTO(PopupAdConditionDTO conditionDTO) {
        this.setAdName(conditionDTO.getAdName());
        this.setTerminalCode(conditionDTO.getTerminalCode());
        this.setStartTime(conditionDTO.getStartTime());
        this.setEndTime(conditionDTO.getEndTime());
        this.startTimeStr = DateUtils.format(conditionDTO.getStartTime(), DateUtils.YYDDMMHHMMSS);
        this.endTimeStr = DateUtils.format(conditionDTO.getEndTime(), DateUtils.YYDDMMHHMMSS);
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
