package cn.htd.goodscenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.middleware.ItemStockResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.ItemArrivalNotifyDAO;
import cn.htd.goodscenter.dao.ItemAttributeDAO;
import cn.htd.goodscenter.dao.ItemAttributeValueDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSalesAreaMapper;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.dao.ItemSkuTotalStockMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemArrivalNotify;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemArrivalNotifyDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.indto.JudgeRecevieAddressInDTO;
import cn.htd.goodscenter.dto.outdto.CheckPromotionItemSkuOutDTO;
import cn.htd.goodscenter.dto.outdto.QueryFlashbuyItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderImportItemOutDTO;
import cn.htd.goodscenter.service.ItemSkuExportService;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.google.common.collect.Lists;

@Service("itemSkuExportService")
public class ItemSkuExportServiceImpl implements ItemSkuExportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemSkuExportServiceImpl.class);

	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private ItemAttributeDAO itemAttributeDAO;
	@Resource
	private ItemAttributeValueDAO itemAttributeValueDAO;
	@Resource
	private ItemSkuTotalStockMapper itemSkuTotalStockMapper;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	@Resource
	private ItemArrivalNotifyDAO itemArrivalNotifyDAO;
	@Resource
	private ItemPictureDAO itemPictureDAO;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemSpuMapper itemSpuMapper;
	@Resource
	private ItemSalesAreaMapper itemSalesAreaMapper;


	public String queryItemSkuAttrStr(Long skuId) {
		ItemSku itemSku = itemSkuDAO.queryItemSkuBySkuId(skuId);
		if (itemSku != null) {
			String[] arr = itemSku.getAttributes().split(";");
			StringBuffer itemSkuAttrStr = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				Long skuAttr = Long.valueOf(arr[i].split(":")[0]);
				ItemAttrDTO itemAttr = itemAttributeDAO.queryById(skuAttr);
				Long skuAttrValue = Long.valueOf(arr[i].split(":")[1]);
				ItemAttrValueDTO itemAttrValue = itemAttributeValueDAO.queryById(skuAttrValue);
				itemSkuAttrStr.append(itemAttr.getName()).append(":").append(itemAttrValue.getName()).append(";");
			}
			return itemSkuAttrStr.substring(0, itemSkuAttrStr.length() - 1);
		} else {
			return "";
		}
	}

	@Override
	public List<ItemSku> queryByDate(Date syncTime) {
		return itemSkuDAO.queryByDate(syncTime, null);
	}

	@Override
	public List<ItemSkuPicture> querySkuPictureBySyncTime(Date syncTime, Pager page) {
		return itemSkuDAO.querySkuPictureBySyncTime(syncTime, page);
	}

	@Override
	public List<ItemSkuPicture> selectSkuPictureBySkuId(Long skuId){
		return itemSkuDAO.selectSkuPictureBySkuId(skuId);
	}

	@Override
	public ExecuteResult<DataGrid<CheckPromotionItemSkuOutDTO>> checkPromotionItemSku(
			List<String> skuCodeList) {
		ExecuteResult<DataGrid<CheckPromotionItemSkuOutDTO>> result=new ExecuteResult<DataGrid<CheckPromotionItemSkuOutDTO>>();
		if(CollectionUtils.isEmpty(skuCodeList)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuCodeList")));
			return result;
		}
		
		if(CollectionUtils.size(skuCodeList)>5000){
			result.setCode(ErrorCodes.E10003.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10003.getErrorMsg("skuCodeList more than 5000")));
			return result;
		}
		List<CheckPromotionItemSkuOutDTO> list=itemSkuDAO.querySkuInfoForCheckPromotionActivity(skuCodeList);
		
		List<String> checkedPromotionItemSkuCodeList=Lists.newArrayList();
		for(CheckPromotionItemSkuOutDTO promotionItemSkuOutDTO:list){
			promotionItemSkuOutDTO.setLegal(true);
			checkedPromotionItemSkuCodeList.add(promotionItemSkuOutDTO.getSkuCode());
		}
		
		for(String skuCode:skuCodeList){
			if(CollectionUtils.isNotEmpty(checkedPromotionItemSkuCodeList)&&checkedPromotionItemSkuCodeList.contains(skuCode)){
				continue;
			}
			CheckPromotionItemSkuOutDTO tempPromotionItemSkuOutDTO=new CheckPromotionItemSkuOutDTO();
			tempPromotionItemSkuOutDTO.setSkuCode(skuCode);
			tempPromotionItemSkuOutDTO.setLegal(false);
			list.add(tempPromotionItemSkuOutDTO);
		}
		DataGrid<CheckPromotionItemSkuOutDTO> dataGrid=new DataGrid<CheckPromotionItemSkuOutDTO>();
		result.setCode(ErrorCodes.SUCCESS.name());
		dataGrid.setRows(list);
		result.setResult(dataGrid);
		return result;
	}

	@Override
	public ExecuteResult<QueryFlashbuyItemSkuOutDTO> queryItemSkuBySkuCode(
			String skuCode) {
		ExecuteResult<QueryFlashbuyItemSkuOutDTO> result=new ExecuteResult<QueryFlashbuyItemSkuOutDTO>();
		if(StringUtils.isEmpty(skuCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuCode")));
			return result;
		}
		QueryFlashbuyItemSkuOutDTO flashbuyItemSkuOutDTO=itemSkuDAO.queryFlashbuyItemSkuBySkuCode(skuCode);
		if(flashbuyItemSkuOutDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList("根据 skuCode:"+skuCode+",没有查询到结果"));
			return result;
		}
		List<ItemPicture> picList=itemPictureDAO.queryItemPicsById(flashbuyItemSkuOutDTO.getItemId());
		if(CollectionUtils.isNotEmpty(picList)){
			for(ItemPicture pic:picList){
				flashbuyItemSkuOutDTO.setFirstPicture(pic);
				if(pic.getIsFirst()==1){
					flashbuyItemSkuOutDTO.setFirstPicture(pic);
					break;
				}
			}
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(flashbuyItemSkuOutDTO);
		return result;
	}

	@Override
	public ExecuteResult<String> checkPromotionItemSkuStock(Long skuId,
			Integer promotionQty,String supplierCode) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		
	    ItemSku itemSku=itemSkuDAO.queryItemSkuBySkuId(skuId);
	   

	    if(itemSku==null){
	    	result.setCode(ErrorCodes.E10000.name());
	    	result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSku")));
	 		return result;
	    }
		
		Item item=itemMybatisDAO.queryItemByPk(itemSku.getItemId());

	    if(item==null){
	    	result.setCode(ErrorCodes.E10000.name());
	    	result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("item")));
	 		return result;
	    }
	    ItemSpu spu=itemSpuMapper.selectById(item.getItemSpuId());
	    if(spu==null){
	    	result.setCode(ErrorCodes.E10000.name());
	    	result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("spu")));
	 		return result;
	    }
	    ItemStockResponseDTO itemStockResponse=MiddlewareInterfaceUtil.getSingleItemStock(supplierCode, spu.getSpuCode());

	    if(itemStockResponse==null){
	    	result.setCode(ErrorCodes.E10000.name());
	    	result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemStockResponse")));
	 		return result;
	    }
	    
	  //先查询实际总库存

	   Integer totalStock=(itemStockResponse.getStoreNum()==null ||
	                itemStockResponse.getStoreNum()<=0) ? 0 : itemStockResponse.getStoreNum();

		if(totalStock==null||totalStock<=0){
			result.setCode(ErrorCodes.E10402.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10402.getErrorMsg()));
			return result;		
		}
		
		Integer alreadyDispatchCount=0;
	    ItemSkuPublishInfo boxPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, "1",null);
	    if(boxPublishInfo!=null&&boxPublishInfo.getDisplayQuantity()!=null){
	    	alreadyDispatchCount=alreadyDispatchCount+boxPublishInfo.getDisplayQuantity();
	    }
	    
	    
	    ItemSkuPublishInfo areaPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, "2",null);
	    if(areaPublishInfo!=null&&areaPublishInfo.getDisplayQuantity()!=null){
	    	alreadyDispatchCount=alreadyDispatchCount+areaPublishInfo.getDisplayQuantity();
	    }
	    Integer leftQty=totalStock-alreadyDispatchCount-promotionQty;
	    
	    if(leftQty<0){
	    	result.setCode(ErrorCodes.E10402.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10402.getErrorMsg()));
			return result;		
	    }
	    result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public DataGrid<VenusItemSkuOutDTO> queryItemSkuListByCondition(VenusItemSkuOutDTO dto) {
		DataGrid<VenusItemSkuOutDTO> dataGrid = new DataGrid<VenusItemSkuOutDTO>();
		try{

			List<VenusItemSkuOutDTO> list = itemSkuDAO.queryItemSkuListByCondition(dto);
			if(list != null && list.size() > 0){
				for(VenusItemSkuOutDTO viso : list){
					List<ItemSkuLadderPrice> itemSkuLadderPrices = this.itemSkuPriceService.getSkuLadderPrice(viso.getSkuId());
					if(itemSkuLadderPrices != null && itemSkuLadderPrices.size() > 0){
						viso.setItemSkuLadderPrices(itemSkuLadderPrices);
					}
				}
				dataGrid.setRows(list);
			}

		}catch (Exception e){
			LOGGER.error(e.getMessage());
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<ItemSkuDTO> queryItemSkuByCode(String skuCode) {
		ExecuteResult<ItemSkuDTO> result=new ExecuteResult<ItemSkuDTO>();
		if(StringUtils.isEmpty(skuCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
			return result;
		}
		ItemSkuDTO  itemSku = itemSkuDAO.queryItemSkuDetailBySkuCode(skuCode);
		result.setResult(itemSku);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> saveItemArrivalNotify(ItemArrivalNotifyDTO itemArrivalNotifyDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(itemArrivalNotifyDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemArrivalNotifyDTO")));
			return result;
		}
		
		ValidateResult validateResult=DTOValidateUtil.validate(itemArrivalNotifyDTO);
		
		if(!validateResult.isPass()){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(validateResult.getMessage()));
			return result;
		}
		//入库
		ItemArrivalNotify itemArrivalNotify=new ItemArrivalNotify();
		BeanUtils.copyProperties(itemArrivalNotifyDTO,itemArrivalNotify);
		itemArrivalNotify.setCtoken("");
		itemArrivalNotify.setCreateId(itemArrivalNotifyDTO.getOperatorId());
		itemArrivalNotify.setCreateName(itemArrivalNotifyDTO.getOperatorName());
		itemArrivalNotify.setCreateTime(new Date());
		itemArrivalNotify.setModifyId(itemArrivalNotifyDTO.getOperatorId());
		itemArrivalNotify.setModifyName(itemArrivalNotifyDTO.getOperatorName());
		itemArrivalNotify.setModifyTime(new Date());
		itemArrivalNotifyDAO.insert(itemArrivalNotify);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> isRecevieAddressInSaleRange(
			JudgeRecevieAddressInDTO judgeRecevieAddressInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(judgeRecevieAddressInDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("judgeRecevieAddressInDTO")));
			return result;
		}
		
		if(StringUtils.isEmpty(judgeRecevieAddressInDTO.getIsBoxFlag())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("isBoxFlag")));
			return result;
		}
		
		ItemSkuDTO skuInfo=itemSkuDAO.queryItemSkuDetailBySkuCode(judgeRecevieAddressInDTO.getSkuCode());

		if(skuInfo==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuInfo")));
			return result;
		}
		//包厢，优先判断包厢上架区域
		if("1".equals(judgeRecevieAddressInDTO.getIsBoxFlag())){
			ItemSalesArea boxSaleArea=itemSalesAreaMapper.selectByItemId(skuInfo.getItemId(), "1");
			if(boxSaleArea!=null&&boxSaleArea.getIsSalesWholeCountry()==1){
				result.setCode(ErrorCodes.SUCCESS.name());
				return result;
			}
		}else{
			  //判断区域上架区域
			ItemSalesArea areaSaleArea=itemSalesAreaMapper.selectByItemId(skuInfo.getItemId(), "2");
			
			if(areaSaleArea!=null&&areaSaleArea.getIsSalesWholeCountry()==1){
				result.setCode(ErrorCodes.SUCCESS.name());
				return result;
			}
		}
		
		
		Long count=itemSkuDAO.queryItemCountBySaleAreaCode(judgeRecevieAddressInDTO);
		if(count==null||count<=0){
			result.setCode(ErrorCodes.E10501.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10501.getErrorMsg()));
			return result;
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<ItemSku> queryItemSkuBySkuId(Long skuId) {
		
		ExecuteResult<ItemSku> result=new ExecuteResult<ItemSku>();
	    
		ItemSku itemSku=itemSkuDAO.queryItemSkuBySkuId(skuId);

	    if(itemSku==null){
	    	result.setCode(ErrorCodes.E10000.name());
	    	result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSku")));
	 		return result;
	    }
	    
	    result.setResult(itemSku);
	    
	    return result;
	}

	@Override
	public ExecuteResult<VenusOrderImportItemOutDTO> queryOrderImportItemInfo(String itemCode) {
		ExecuteResult<VenusOrderImportItemOutDTO> result=new ExecuteResult<VenusOrderImportItemOutDTO>();
		if(StringUtils.isEmpty(itemCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuCode")));
			return result;
		}
		VenusOrderImportItemOutDTO orderImportItemOutDTO=itemSkuDAO.queryVenusOrderImportItemInfo(itemCode);
		if(orderImportItemOutDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("orderImportItemOutDTO")));
			return result;
		}
		ExecuteResult<ItemSkuBasePriceDTO> priceresult=itemSkuPriceService.queryItemSkuBasePrice(orderImportItemOutDTO.getSkuId());
		if(priceresult!=null&&priceresult.isSuccess()&&priceresult.getResult()!=null){
			orderImportItemOutDTO.setItemSkuBasePrice(priceresult.getResult());
		}
		List<ItemSkuPublishInfo> itemSkuPublishInfoList=itemSkuPublishInfoMapper.queryBySkuId(orderImportItemOutDTO.getSkuId());
		orderImportItemOutDTO.setItemSkuPublishInfo(itemSkuPublishInfoList);
		result.setResult(orderImportItemOutDTO);
		return result;
	}
}
