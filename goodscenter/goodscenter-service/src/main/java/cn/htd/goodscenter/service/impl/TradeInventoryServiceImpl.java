package cn.htd.goodscenter.service.impl;

import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuTotalStock;
import cn.htd.goodscenter.dto.*;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.TradeInventoryExportService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.service.utils.ItemDTOToDomainUtil;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Description: [库存]
 * </p>
 */
@Service("tradeInventoryExportService")
public class TradeInventoryServiceImpl implements TradeInventoryExportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeInventoryServiceImpl.class);

	@Resource
	private TradeInventoryDAO tradeInventoryDAO;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	@Resource
	private ItemSkuTotalStockMapper itemSkuTotalStockMapper;

	/**
	 * <p>
	 * Discription:[根据skuId查询库存]
	 * </p>
	 */
	@Override
	public ExecuteResult<TradeInventoryDTO> queryTradeInventoryBySkuId(Long skuId) {
		ExecuteResult<TradeInventoryDTO> er = new ExecuteResult<TradeInventoryDTO>();
		try {
			er.setResult(tradeInventoryDAO.queryBySkuId(skuId));
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询库存列表]
	 * </p>
	 */
	@Override
	public ExecuteResult<DataGrid<TradeInventoryOutDTO>> queryTradeInventoryList(TradeInventoryInDTO dto, Pager page) {
		ExecuteResult<DataGrid<TradeInventoryOutDTO>> result = new ExecuteResult<DataGrid<TradeInventoryOutDTO>>();
		DataGrid<TradeInventoryOutDTO> dg = new DataGrid<TradeInventoryOutDTO>();
		try {
			List<TradeInventoryOutDTO> list = tradeInventoryDAO.queryTradeInventoryList(dto, page);
			Long count = tradeInventoryDAO.queryCount(dto);
			for (TradeInventoryOutDTO out : list) {
				// 根据skuId查询商品sku的图片
				List<SkuPictureDTO> pics = itemMybatisDAO.querySkuPics(out.getSkuId());
				out.setSkuPicture(pics);
				// 根据skuId查询商品的名称、编码、状态、类目id、市场价以及sku的销售属性集合：keyId:valueId
				TradeInventoryOutDTO td = tradeInventoryDAO.queryItemBySkuId(out.getSkuId());
				out.setItemName(td.getItemName());
				out.setItemId(td.getItemId());
				out.setItemCode(td.getItemCode());
				out.setItemStatus(td.getItemStatus());
				out.setMarketPrice(td.getMarketPrice());
				out.setSkuStatus(td.getSkuStatus());
				// 根据cid查询类目属性
				ExecuteResult<List<ItemCatCascadeDTO>> er = itemCategoryService.queryParentCategoryList(td.getCid());
				out.setItemCatCascadeDTO(er.getResult());
				// 根据sku的销售属性keyId:valueId查询商品属性
				ExecuteResult<List<ItemAttrDTO>> itemAttr = itemCategoryService.queryCatAttrByKeyVals(td.getAttributes());
				out.setItemAttr(itemAttr.getResult());
				//查询商品sku阶梯价格
				DataGrid<ItemSkuLadderPrice> dataGrid = itemSkuPriceService
						.queryLadderPriceBySellerIdAndSkuId(dto.getSellerId(),out.getSkuId());
				if (dataGrid.getRows() != null && dataGrid.getRows().size() > 0){
					out.setItemSkuLadderPrices(dataGrid.getRows());
				}
			}
			dg.setRows(list);
			dg.setTotal(count);
			result.setResult(dg);
		} catch (Exception e) {
			result.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Discription:[批量修改库存]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyInventoryByIds(List<InventoryModifyDTO> dtos) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		if (dtos == null || dtos.isEmpty()) {
			er.addErrorMessage("参数不能为空！");
			return er;
		}
		ItemSkuTotalStock item = null;
		ItemDTO dbItem = null;
		for (InventoryModifyDTO im : dtos) {
			TradeInventoryDTO dbSkuInv = tradeInventoryDAO.queryBySkuId(im.getSkuId());// 数据库原库存
			tradeInventoryDAO.modifyInventoryBySkuIds(im.getSkuId(), im.getTotalInventory());
			ItemSku sku = this.itemSkuDAO.queryById(im.getSkuId());
			dbItem = this.itemMybatisDAO.getItemDTOById(sku.getItemId());
			item = new ItemSkuTotalStock();
			item.setItemId(sku.getItemId());
			// 计算商品总库存
//			item.setInventory(dbItem.getInventory() - (dbSkuInv.getTotalInventory() - im.getTotalInventory()));
//			this.itemSkuTotalStockMapper.updateItemInventoryByItemId(item);
		}
		return er;
	}

	@Override
	public List<TradeInventoryDTO> querySkuInventoryBySyncTime(Date syncTime, Pager pager) {
		return tradeInventoryDAO.querySkuInventoryBySyncTime(syncTime, pager);
	}

	@Override
	public ExecuteResult<String> saveSkuInventoryList(List<VenusItemSkuPublishInDTO> dtos) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			if(dtos != null && dtos.size() > 0){
				for(VenusItemSkuPublishInDTO dto : dtos){
					ItemSkuPublishInfo itemSkuPublishInfo =  ItemDTOToDomainUtil.ItemPuclishDTO2Domain(dto);
					itemSkuPublishInfoMapper.add(itemSkuPublishInfo);
				}
				result.setResultMessage("success");
			}else{
				result.setResultMessage("list参数为空，插入失败！");
				return result;
			}
		}catch (Exception e){
			result.addErrorMessage(e.getMessage());
			LOGGER.error(e.getMessage());
		}
		return result;
	}

}
