package cn.htd.marketcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.BuyerInfoDTO;
import cn.htd.marketcenter.dto.BuyerTimelimitedInfo4SuperBossDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;

/**
 * 超级老板用秒杀活动服务
 */
public interface BuyerTimelimitedInfo4SuperBossService {

    /**
     * 根据会员编码和商品编码取得当前会员参加秒杀活动信息
     *
     * @param messageId
     * @param buyerCode
     * @param promotionId
     * @return
     */
    public ExecuteResult<BuyerTimelimitedInfo4SuperBossDTO> getBuyerTimelimitedInfo(String messageId, String buyerCode,
            String promotionId);

	/**
	 * 取得所有秒杀活动信息
	 * 
	 * @param messageId
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getAllMallTimelimitedList(String messageId,
			Pager<TimelimitedInfoDTO> page);

    /**
     * 取得供应商秒杀活动信息
     *
     * @param messageId
     * @param buyerInfo
     * @param page
     * @return
     */
    public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getVmsMallTimelimitedList(String messageId,
            BuyerInfoDTO buyerInfo, Pager<TimelimitedInfoDTO> page);
}
