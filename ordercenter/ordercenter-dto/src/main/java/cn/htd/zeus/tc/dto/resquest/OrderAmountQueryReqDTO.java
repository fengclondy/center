package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 17/9/4.
 */
public class OrderAmountQueryReqDTO extends GenricReqDTO implements Serializable{
    //会员编码
    private String memberCode;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
