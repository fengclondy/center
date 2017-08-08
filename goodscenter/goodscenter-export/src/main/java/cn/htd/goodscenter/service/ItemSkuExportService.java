package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.dto.ItemArrivalNotifyDTO;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.indto.JudgeRecevieAddressInDTO;
import cn.htd.goodscenter.dto.outdto.CheckPromotionItemSkuOutDTO;
import cn.htd.goodscenter.dto.outdto.QueryFlashbuyItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderImportItemOutDTO;

public interface ItemSkuExportService{
	/**
	 *
	 * <p>
	 * Discription:[查询商品属性]
	 * </p>
	 */
	 String queryItemSkuAttrStr(Long skuId);


	 List<ItemSku> queryByDate(Date syncTime);

	 List<ItemSkuPicture> querySkuPictureBySyncTime(@Param("syncTime") Date syncTime,@Param("page") Pager page);

	List<ItemSkuPicture> selectSkuPictureBySkuId(Long skuId);
	 
	 ExecuteResult<DataGrid<CheckPromotionItemSkuOutDTO>> checkPromotionItemSku(List<String> skuCodeList);

	 ExecuteResult<QueryFlashbuyItemSkuOutDTO> queryItemSkuBySkuCode(String skuCode);
	 
	 ExecuteResult<String> checkPromotionItemSkuStock(Long skuId,Integer promotionQty,String supplierCode);

	 DataGrid<VenusItemSkuOutDTO> queryItemSkuListByCondition(VenusItemSkuOutDTO dto);
	 
	 ExecuteResult<ItemSkuDTO> queryItemSkuByCode(String skuCode);
	 
	 ExecuteResult<String> saveItemArrivalNotify(ItemArrivalNotifyDTO itemArrivalNotifyDTO);
	 
	 ExecuteResult<String> isRecevieAddressInSaleRange(JudgeRecevieAddressInDTO judgeRecevieAddressInDTO);
	 
	 ExecuteResult<ItemSku> queryItemSkuBySkuId(Long skuId);
	 
	 ExecuteResult<VenusOrderImportItemOutDTO> queryOrderImportItemInfo(String skuCode);

}
