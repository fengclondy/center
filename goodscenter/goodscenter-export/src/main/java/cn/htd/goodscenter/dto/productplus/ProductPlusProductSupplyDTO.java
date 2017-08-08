package cn.htd.goodscenter.dto.productplus;

/**
 * 商品加 - 外接渠道商品【增量】DTO
 *  @author chenkang
 */
public class ProductPlusProductSupplyDTO {
    // 商品增加还是修改 0:新增   1：删除
    private Integer delFlag;

    // 新增时传入 商品信息
    private ProductPlusProductImportDTO productPlusProductImportDTO;

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public ProductPlusProductImportDTO getProductPlusProductImportDTO() {
        return productPlusProductImportDTO;
    }

    public void setProductPlusProductImportDTO(ProductPlusProductImportDTO productPlusProductImportDTO) {
        this.productPlusProductImportDTO = productPlusProductImportDTO;
    }
}
