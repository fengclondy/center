package cn.htd.goodscenter.service.impl.mall;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.domain.ItemFavourite;
import cn.htd.goodscenter.dto.mall.ItemFavouriteInDTO;
import cn.htd.goodscenter.dto.mall.ItemFavouriteOutDTO;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.goodscenter.service.mall.MallItemFavouriteExportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import cn.htd.goodscenter.dao.ItemFavouriteDAO;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品收藏
 * @author chenkang
 * @date 2017-01-07
 */
@Service("mallItemFavouriteService")
public class MallItemFavouriteServiceImpl implements MallItemFavouriteExportService {

	@Resource
	private ItemFavouriteDAO itemFavouriteDAO;

	@Qualifier("mallItemExportService")
	@Autowired
	private MallItemExportService mallItemExportService;

	private static final Logger logger = LoggerFactory.getLogger(MallItemFavouriteServiceImpl.class);

	@Override
	public ExecuteResult<String> favouriteItem(ItemFavouriteInDTO itemFavouriteInDTO) {
		logger.info("收藏商品, 参数 : {}", JSONObject.fromObject(itemFavouriteInDTO));
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			ValidateResult validateResult = DTOValidateUtil.validate(itemFavouriteInDTO);
			if (!validateResult.isPass()) {
				logger.error("favouriteItem validate fail : {}", validateResult.getMessage());
				throw new Exception(validateResult.getMessage());
			}
			ItemFavourite itemFavourite = new ItemFavourite();
			itemFavourite.setItemId(itemFavouriteInDTO.getItemId());
			itemFavourite.setSkuId(itemFavouriteInDTO.getSkuId());
			itemFavourite.setSellerId(itemFavouriteInDTO.getSellerId());
			itemFavourite.setUserId(itemFavouriteInDTO.getUserId());
			itemFavourite.setShopId(itemFavouriteInDTO.getShopId());
			itemFavourite.setChannelCode(itemFavouriteInDTO.getChannelCode());
			itemFavourite.setCreateId(itemFavouriteInDTO.getCreateId());
			itemFavourite.setCreateName(itemFavouriteInDTO.getCreateName());
			this.itemFavouriteDAO.insert(itemFavourite);
		} catch (Exception e) {
			logger.error("收藏商品出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> batchCancelFavouriteItem(List<Long> itemFavouriteIdList) {
		logger.info("批量取消收藏商品, 参数 : {}", JSONArray.fromObject(itemFavouriteIdList));
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (itemFavouriteIdList != null && itemFavouriteIdList.size() > 0) {
				this.itemFavouriteDAO.batchDelete(itemFavouriteIdList);
			}
		} catch (Exception e) {
			logger.error("批量取消收藏商品出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> cancelFavouriteItem(Long userId, Long skuId) {
		logger.info("取消收藏商品, userId : {}, skuId : {}", userId, skuId);
		ExecuteResult<String> executeResult = new ExecuteResult();
		try {
			ItemFavourite itemFavourite = new ItemFavourite();
			itemFavourite.setSkuId(skuId);
			itemFavourite.setUserId(userId);
			int result = this.itemFavouriteDAO.delete(itemFavourite);
			if (result == 0) {
				throw new RuntimeException("取消收藏商品失败");
			}
		} catch (Exception e) {
			logger.error("取消收藏商品出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 查询用户收藏商品 基本信息、销售价格、主图、上下架状态
	 * @param userId 用户id
	 * @param pager 分页
	 * @return 用户收藏的商品
	 */
	@Override
	public ExecuteResult<DataGrid<ItemFavouriteOutDTO>> queryFavouriteItemList(Long userId, Pager pager) {
		logger.info("查询用户收藏商品列表, 用户ID : {}, 分页参数 : {}", userId, JSONObject.fromObject(pager));
		ExecuteResult executeResult = new ExecuteResult();
		DataGrid<ItemFavouriteOutDTO> dtoDataGrid = new DataGrid<ItemFavouriteOutDTO>();
		if (userId == null) {
			executeResult.addErrorMessage("userId is null");
			return executeResult;
		}
		if (pager == null) {
			executeResult.addErrorMessage("pager is null");
			return executeResult;
		}
		try {
			/** 查询用户收藏的商品 **/
			ItemFavourite itemFavouriteParam = new ItemFavourite();
			itemFavouriteParam.setUserId(userId);
			itemFavouriteParam.setDeleteFlag(0);
			Long count = this.itemFavouriteDAO.selectListPageCount(itemFavouriteParam);
			List<ItemFavourite> itemFavouriteList = new ArrayList<ItemFavourite>();
			List<ItemFavouriteOutDTO> itemFavouriteOutDTOList = new ArrayList<ItemFavouriteOutDTO>();
			if (count > 0) {
				itemFavouriteList = this.itemFavouriteDAO.selectListPage(itemFavouriteParam, pager);
				/** 查询商品基本信息 **/
				for (ItemFavourite itemFavourite : itemFavouriteList) {
					ItemFavouriteOutDTO itemFavouriteOutDTO = this.mallItemExportService.queryFavouriteItemInfo(itemFavourite.getSkuId());
					if (itemFavouriteOutDTO != null) {
						itemFavouriteOutDTO.setFavouriteId(itemFavourite.getId());
						itemFavouriteOutDTO.setUserId(itemFavourite.getUserId());
						itemFavouriteOutDTOList.add(itemFavouriteOutDTO);
					}
				}
			}
			dtoDataGrid.setRows(itemFavouriteOutDTOList);
			dtoDataGrid.setTotal(count);
			executeResult.setResult(dtoDataGrid);
		} catch (Exception e) {
			logger.error("查询用户收藏商品列表出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<Boolean> isHasFavouriteItem(Long userId, Long skuId) {
		ExecuteResult<Boolean> executeResult = new ExecuteResult<Boolean>();
		Boolean isFavourite = null;
		try {
			ItemFavourite itemFavouriteParam = new ItemFavourite();
			itemFavouriteParam.setUserId(userId);
			itemFavouriteParam.setSkuId(skuId);
			itemFavouriteParam.setDeleteFlag(0);
			ItemFavourite itemFavourite = this.itemFavouriteDAO.select(itemFavouriteParam);
			isFavourite = itemFavourite != null;
			executeResult.setResult(isFavourite);
		} catch (Exception e) {
			logger.error("查询用户是否收藏该商品出错, 错误信息 : ", e);
		}
		return executeResult;
	}
}
