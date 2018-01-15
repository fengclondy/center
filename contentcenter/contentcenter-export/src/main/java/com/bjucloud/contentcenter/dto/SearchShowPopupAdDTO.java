package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchShowPopupAdDTO implements Serializable {

    @NotEmpty(message = "messageId不能为空")
    private String messageId;

    @NotEmpty(message = "终端类型参数不能为空")
    private String terminalTypeCode;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTerminalTypeCode() {
        return terminalTypeCode;
    }

    public void setTerminalTypeCode(String terminalTypeCode) {
        this.terminalTypeCode = terminalTypeCode;
    }
}
