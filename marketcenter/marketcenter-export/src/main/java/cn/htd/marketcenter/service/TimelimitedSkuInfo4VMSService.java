package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.TimelimitedSkuCountDTO;

/**
 * 秒杀活动服务
 */
public interface TimelimitedSkuInfo4VMSService {

    /**
     * 根据商品编码取得该商品参与的供应商秒杀和平台秒杀的商品总数量
     *
     * @param messageId
     * @param skuCode
     * @return
     */
    public ExecuteResult<TimelimitedSkuCountDTO> getSkuTimelimitedAllCount(String messageId, String skuCode);

}
