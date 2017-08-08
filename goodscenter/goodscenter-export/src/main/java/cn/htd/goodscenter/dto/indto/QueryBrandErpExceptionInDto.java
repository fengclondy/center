package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 查询下行异常的品牌
 */
public class QueryBrandErpExceptionInDto implements Serializable {
    /**
     * erp 状态 （2：下行中  3：下行失败）
     */
    private String erpStatus;

    /**
     * 超过多少分钟的异常数据。单位分钟
     */
    private Integer period;

    private Date limitTime;

    public String getErpStatus() {
        return erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
        if (period != null && period > 0) {
            // 设置limitTime
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, -period);
            limitTime = calendar.getTime();
        }
    }

    public Date getLimitTime() {
        return limitTime;
    }

}
