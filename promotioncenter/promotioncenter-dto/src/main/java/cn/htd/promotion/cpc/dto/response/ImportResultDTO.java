package cn.htd.promotion.cpc.dto.response;

import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangjiayong on 2017/8/28.
 */
public class ImportResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer successCount;

    private Integer failCount;

    private List<PromotionAwardReqDTO> promotionAwardList;

    private List<PromotionAwardReqDTO> successAwardList;

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public List<PromotionAwardReqDTO> getPromotionAwardList() {
        return promotionAwardList;
    }

    public void setPromotionAwardList(List<PromotionAwardReqDTO> promotionAwardList) {
        this.promotionAwardList = promotionAwardList;
    }

    public List<PromotionAwardReqDTO> getSuccessAwardList() {
        return successAwardList;
    }

    public void setSuccessAwardList(List<PromotionAwardReqDTO> successAwardList) {
        this.successAwardList = successAwardList;
    }
}
