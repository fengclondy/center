package cn.htd.goodscenter.service.vip;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.vip.VipItemAddInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListOutDTO;
import cn.htd.goodscenter.dto.vip.VipItemOutDTO;


/**
 * 套餐商品服务类
 * 
 * @author zhangxiaolong
 *
 */
public interface VipItemExportService {
	
	/**
	 * 查询VIP套餐商品列表 。可查询单个
	 * 
	 * @param vipItemListInDTO
	 * @return
	 */
	ExecuteResult<List<VipItemListOutDTO>> queryVipItemList(VipItemListInDTO vipItemListInDTO);

	/**
	 * 根据商品编码选择VIP套餐子商品,
	 * @param itemCode 商品编码
	 * @return
	 */
	ExecuteResult<ItemSkuDTO> queryItemInfoBySkuCode(String itemCode);

	/**
	 * 新增或者修改套餐商品
	 * @param itemId
	 * @param vipItemType  套餐类型 ： 1 VIP套餐 2 智慧门店套餐
	 * @param vipSyncFlag 同步VIP会员标记 : 0 无效 1 有效
	 * @param vipItemAddInDTOList
	 * @return
	 */
	ExecuteResult<String> addOrModifyVipItemEntry(Long itemId, Integer vipItemType, Integer vipSyncFlag, List<VipItemAddInDTO> vipItemAddInDTOList);
}
