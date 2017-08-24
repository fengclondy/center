package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

public class GenricResDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1186301269812058781L;

    private String messageId = null;

    private String responseCode = null;

    private String responseMsg = null;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }


}
