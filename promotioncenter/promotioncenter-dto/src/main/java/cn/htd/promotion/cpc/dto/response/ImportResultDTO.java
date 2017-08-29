package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangjiayong on 2017/8/28.
 */
public class ImportResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer successCount;

    private Integer failCount;

    private List<PromotionAwardDTO> promotionAwardList;

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

    public List<PromotionAwardDTO> getPromotionAwardList() {
        return promotionAwardList;
    }

    public void setPromotionAwardList(List<PromotionAwardDTO> promotionAwardList) {
        this.promotionAwardList = promotionAwardList;
    }
}
