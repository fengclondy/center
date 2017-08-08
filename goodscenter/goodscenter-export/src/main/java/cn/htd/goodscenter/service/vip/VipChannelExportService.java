package cn.htd.goodscenter.service.vip;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.vip.VipChannelItemOutDTO;

import java.util.List;

/**
 * vip商品接口
 */
public interface VipChannelExportService {
    /**
     * 查询VIP频道VIP商品专区商品
     */
    ExecuteResult<List<VipChannelItemOutDTO>> queryVipItemList(Pager pager, String cityCode);

    /**
     * 查询促销秒杀页VIP秒杀专区商品
     */
    ExecuteResult<List<VipChannelItemOutDTO>> queryFlashByVipItemList(List<String> flashByVipSkuCodeList);

    /**
     * 推送vip商品到vip频道
     * @param skuCodes 商品编码以逗号隔开
     */
    ExecuteResult<String> pushVipItemToVipChannel(String skuCodes);
    /**
     * 查询VIP频道VIP商品专区商品
     */
    ExecuteResult<String > queryVipChannelItemStr();
}
