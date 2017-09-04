package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class DrawLotteryResultReqDTO extends DrawLotteryReqDTO{

    /**
     * 领奖编码
     */
    @NotEmpty(message = "活动编码不能为空")
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
