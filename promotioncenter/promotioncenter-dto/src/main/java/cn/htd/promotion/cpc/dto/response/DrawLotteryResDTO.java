package cn.htd.promotion.cpc.dto.response;

public class DrawLotteryResDTO extends GenricResDTO {

    /**
     * 抽奖活动序列号
     */
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
