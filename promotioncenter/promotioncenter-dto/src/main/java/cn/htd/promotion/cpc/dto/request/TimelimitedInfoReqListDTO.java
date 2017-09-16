package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

/**
 * 秒杀活动批量信息DTO
 *
 * @author neo.tao
 * @since 2017-8-22 15:14
 */
public class TimelimitedInfoReqListDTO implements Serializable {
    private TimelimitedInfoReqDTO timeLimitedInfo;
    private Integer orgId;
    private Integer operation;
    private Integer errorData;

    public TimelimitedInfoReqDTO getTimeLimitedInfo() {
        return timeLimitedInfo;
    }

    public void setTimeLimitedInfo(TimelimitedInfoReqDTO timeLimitedInfo) {
        this.timeLimitedInfo = timeLimitedInfo;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getErrorData() {
        return errorData;
    }

    public void setErrorData(Integer errorData) {
        this.errorData = errorData;
    }
}
