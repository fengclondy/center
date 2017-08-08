package cn.htd.goodscenter.dto.mall;

import cn.htd.membercenter.dto.MemberShipDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 超级老板/商城移动化采购/采购首页
 * @author chenkang
 * @date 2017-06-27
 */
public class MallRecommendItemInDTO implements Serializable {
    /**
     * 买家id
     */
    @NotNull(message = "买家ID不能为NUL")
    private Long buyerId;

    /**
     * 买家Code
     */
    @NotNull(message = "买家Code不能为NUL")
    private String buyerCode;

    /**
     * 注册地的区域(市)
     */
    @NotNull(message = "站点不能为NUL")
    private String areaCode;

    /**
     * 当前会员包厢关系卖家id的集合
     */
    @NotNull(message = "包厢关系卖家id的集合不能为NULL")
    private List<MemberShipDTO> boxSellerDtoList;

    /**
     * 供应商秒杀商品id的集合
     */
    @NotNull(message = "供应商秒杀商品id的集合不能为NULL")
    private List<Long> promotionItemIdList;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public List<Long> getPromotionItemIdList() {
        return promotionItemIdList;
    }

    public void setPromotionItemIdList(List<Long> promotionItemIdList) {
        this.promotionItemIdList = promotionItemIdList;
    }

    public List<MemberShipDTO> getBoxSellerDtoList() {
        return boxSellerDtoList;
    }

    public void setBoxSellerDtoList(List<MemberShipDTO> boxSellerDtoList) {
        this.boxSellerDtoList = boxSellerDtoList;
    }
}
