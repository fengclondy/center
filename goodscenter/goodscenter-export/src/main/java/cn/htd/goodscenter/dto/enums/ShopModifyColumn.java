package cn.htd.goodscenter.dto.enums;

/**商品更改记录对应数据库的字段与列名
 * Created by GZG on 2016/12/22.
 */
public enum ShopModifyColumn {

    item_spu("商品模板表"),
    item("商品表"),
    item_describe("商品详情表"),
    item_spu_describe("商品模板详情表"),
    brand("品牌编码"),
    brand_id("品牌编码"),
    cid("平台三级类目"),
    category_id("平台三级类目"),
    shop_cid("店铺二级类目"),
    is_spu("是否为新增商品"),
    ad("广告语"),
    weight("商品毛重"),
    gross_weight("商品毛重"),
    net_weight("商品净重"),
    unit("商品单位"),
    height("商品高"),
    model_type("商品型号"),
    tax_rate("商品税率"),
    origin("商品产地"),
    product_channel_code("商品来源"),
    erp_first_category_code("ERP一级类目编码"),
    erp_five_category_code("ERP五级类目编码"),
    length("商品长"),
    width("商品宽"),
    attr_sale("商品销售属性"),
    category_attributes("类目属性"),
    item_qualification("商品参数"),
    packing_list("装箱清单"),
    spu_desc("商品描述"),
    describe_content("商品描述");

    private String label;

    ShopModifyColumn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
