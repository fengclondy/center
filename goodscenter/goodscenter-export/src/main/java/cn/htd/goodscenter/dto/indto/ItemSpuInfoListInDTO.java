package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;

/**
 * 模板列表dto
 */
public class ItemSpuInfoListInDTO implements Serializable {

    private String spuName; // 商品模板名称

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }
}
