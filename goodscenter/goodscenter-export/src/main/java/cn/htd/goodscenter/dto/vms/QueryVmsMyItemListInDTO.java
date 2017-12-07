package cn.htd.goodscenter.dto.vms;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;
import java.io.Serializable;
import java.util.List;

/**
 * 我的商品 - 列表查询入参DTO
 */
public class QueryVmsMyItemListInDTO extends AbstractPagerDTO implements Serializable {
    // 大BID
    private Long sellerId;
    //产品编码
    private String productCode;
    //商品名称
    private String productName;
    // 审核状态（只做展示，区别于商品状态）
    //null : 全部；  0 : 待审核；   1 ： 审核通过；  2：新增审核不通过；   3 :  修改待审核；  4： 修改审核不通过；
    private Integer auditStatus;
    // 一级品类ID
    private Long firstCategoryId;
    // 二级品类ID
    private Long secCategoryId;
    // 三级级品类ID
    private Long thirdCategoryId;
    // 品牌ID
    private String brandName;

    private List<Long> thirdCategoryIdList;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecCategoryId() {
        return secCategoryId;
    }

    public void setSecCategoryId(Long secCategoryId) {
        this.secCategoryId = secCategoryId;
    }

    public Long getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Long thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public List<Long> getThirdCategoryIdList() {
        return thirdCategoryIdList;
    }

    public void setThirdCategoryIdList(List<Long> thirdCategoryIdList) {
        this.thirdCategoryIdList = thirdCategoryIdList;
    }
}
