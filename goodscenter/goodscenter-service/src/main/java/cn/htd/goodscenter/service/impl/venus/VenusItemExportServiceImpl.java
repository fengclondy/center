package cn.htd.goodscenter.service.impl.venus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.middleware.ItemStockResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.MessageIdUtils;
import cn.htd.goodscenter.common.channel.ItemChannelConstant;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.VenusErrorCodes;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.SpringUtils;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ItemDraftDescribeMapper;
import cn.htd.goodscenter.dao.ItemDraftMapper;
import cn.htd.goodscenter.dao.ItemDraftPictureMapper;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSalesAreaDetailMapper;
import cn.htd.goodscenter.dao.ItemSalesAreaMapper;
import cn.htd.goodscenter.dao.ItemSalesDefaultAreaMapper;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.dao.ItemSkuTotalStockMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuDescribeMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuPictureMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.domain.ItemDraftDescribe;
import cn.htd.goodscenter.domain.ItemDraftPicture;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;
import cn.htd.goodscenter.domain.ItemSalesDefaultArea;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.domain.spu.ItemSpuDescribe;
import cn.htd.goodscenter.domain.spu.ItemSpuPicture;
import cn.htd.goodscenter.dto.ItemCatCascadeDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemSpuPictureDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemStatusEnum;
import cn.htd.goodscenter.dto.middleware.outdto.QueryItemWarehouseOutDTO;
import cn.htd.goodscenter.dto.middleware.outdto.QuerySpecialItemOutDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusBatchDeleteItemSkuInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSetShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuAutoShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInfoInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusQueryDropdownItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusDropdownItemObjDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemListStyleOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemMainDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemListOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelListOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.venus.po.QueryVenusItemListParamDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.converter.Converters;
import cn.htd.goodscenter.service.impl.stock.ItemSkuPublishInfoUtil;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.goodscenter.service.utils.ItemDTOToDomainUtil;
import cn.htd.goodscenter.service.venus.VenusItemExportService;
import cn.htd.marketcenter.service.TimelimitedInfoService;
import cn.htd.middleware.common.message.erp.ProductMessage;
import cn.htd.pricecenter.common.constants.PriceConstants;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.dto.HzgPriceInDTO;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

@Service("venusItemExportService")
public class VenusItemExportServiceImpl implements VenusItemExportService{
	private static final Logger logger = LoggerFactory.getLogger(VenusItemExportServiceImpl.class);
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemPictureDAO itemPictureDAO;
	@Resource 
	private ItemSkuDAO itemSkuDAO;
	@Resource 
	private ItemDescribeDAO itemDescribeDAO;
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private ItemSalesAreaMapper itemSalesAreaMapper;
	@Resource
	private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;
	@Resource
	private ItemSkuTotalStockMapper itemSkuTotalStockMapper;
	@Resource
	private ItemSalesDefaultAreaMapper itemSalesDefaultAreaMapper;
	@Resource
	private ItemSpuMapper itemSpuMapper;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ItemSpuDescribeMapper itemSpuDescribeMapper;
	@Resource
	private ItemSpuPictureMapper itemSpuPictureMapper;
	@Resource
	private ItemDraftMapper itemDraftMapper;
	@Resource
	private ItemDraftPictureMapper itemDraftPictureMapper;
	@Resource
	private ItemDraftDescribeMapper itemDraftDescribeMapper;
	@Resource
	private TimelimitedInfoService timelimitedInfoService;
	@Resource
	private DictionaryUtils dictionaryUtils;
	
	
	@Transactional
	@Override
	public ExecuteResult<String> addItem(VenusItemInDTO venusItemDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		//前置校验
		if(venusItemDTO == null){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg()));
			return  result;
		}
		ValidateResult va1lidateResult=DTOValidateUtil.validate(venusItemDTO);
		
		if(!va1lidateResult.isPass()){
			result.setCode(ErrorCodes.E10006.name());
			result.setErrorMessages(Lists.newArrayList(StringUtils.split(va1lidateResult.getMessage(),DTOValidateUtil.ERROR_MSG_SEPERATOR)));
			return  result;
		}
		
		if(CollectionUtils.isEmpty(venusItemDTO.getPictures())){
			result.setCode(VenusErrorCodes.E1040001.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040001.getErrorMsg()));
			return  result;
		}
		
		if(venusItemDTO.getDescribe()==null||StringUtils.isEmpty(venusItemDTO.getDescribe().getDescribeContent())){
			result.setCode(VenusErrorCodes.E1040002.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040002.getErrorMsg()));
			return  result;
		}
		try{
			//DTO转化为domain
			Item item=Converters.convert(venusItemDTO, Item.class);
			//自动创建一条商品SPU
			ItemSpu itemSpu = doAddItemSpu(venusItemDTO, item);
			item.setItemSpuId(itemSpu.getSpuId());
			//税率一致校验
			if (itemSpu.getTaxRate().compareTo(item.getTaxRate()) != 0) {
				result.setCode(VenusErrorCodes.E1040020.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040020.getErrorMsg()));
				return result;
			}
			//存储item到数据库
		    itemMybatisDAO.addItem(item);
			//生成SKU
			doAddItemSku(venusItemDTO, item);
			//存储商品图片 
		    doAddItemPicture(venusItemDTO, item);
			//存储商品描述
		    doAddItemDescribe(venusItemDTO, item);
		    //存储草稿
		    doAddItemDraft(venusItemDTO, item);
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::addItem:",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	    result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	private ItemSpu doAddItemSpu(VenusItemInDTO venusItemDTO, Item item) {
		//根据名称去spu表判断一下，该商品是否已经存在，如果不存在，则要新加一条spu数据
		ItemSpu itemSpu=itemSpuMapper.queryItemSpuByName(item.getItemName());
		if(itemSpu == null || itemSpu.getDeleteFlag() == 1){ // 判断模板是否已经删除
			//新生成一份spu的数据
			itemSpu=Converters.convert(item, ItemSpu.class);
			itemSpuMapper.insertSelective(itemSpu);
			//图片
			List<ItemSpuPicture> itemSpuPicturelist=Lists.newArrayList();
			for(ItemPicture picture:venusItemDTO.getPictures()){
				ItemSpuPicture itemSpuPicture=new ItemSpuPicture();
				itemSpuPicture.setSpuId(itemSpu.getSpuId());
				itemSpuPicture.setIsFirst(picture.getIsFirst());
				itemSpuPicture.setModifyId(venusItemDTO.getOperatorId());
				itemSpuPicture.setModifyName(venusItemDTO.getOperatorName());
				itemSpuPicture.setModifyTime(new Date());
				itemSpuPicture.setPictureUrl(picture.getPictureUrl());
				itemSpuPicture.setSortNum(picture.getSortNumber()==null?0:picture.getSortNumber());
				itemSpuPicture.setCreateTime(new Date());
				itemSpuPicture.setCreateId(venusItemDTO.getOperatorId());
				itemSpuPicture.setCreateName(venusItemDTO.getOperatorName());
				itemSpuPicturelist.add(itemSpuPicture);
			}
			 itemSpuPictureMapper.batchInsert(itemSpuPicturelist);
			//描述
    		ItemSpuDescribe itemSpuDescribe= new ItemSpuDescribe();
			
			ItemDescribe itemDescribe=venusItemDTO.getDescribe();
			itemSpuDescribe.setSpuId(itemSpu.getSpuId());
			itemSpuDescribe.setSpuDesc(itemDescribe.getDescribeContent());
			itemSpuDescribe.setCreateTime(new Date());
			itemSpuDescribe.setCreateId(venusItemDTO.getOperatorId());
			itemSpuDescribe.setCreateName(venusItemDTO.getOperatorName());
		    itemSpuDescribeMapper.insertSelective(itemSpuDescribe);
			
		} else {
			item.setApplyInSpu(true);
		}
		return itemSpu;
	}

	private void doAddItemSku(VenusItemInDTO venusItemDTO, Item item) {
		ItemSku itemSku=Converters.convert(venusItemDTO, ItemSku.class);
		
		if(itemSku.getItemId()==null){
			itemSku.setItemId(item.getItemId());
		}
		//存储SKU
	    itemSkuDAO.add(itemSku);
	}

	private void doAddItemPicture(VenusItemInDTO venusItemDTO, Item item) {
		List<ItemPicture> picturesList= venusItemDTO.getPictures();
	    for(ItemPicture picture:picturesList){
	    	picture.setItemId(item.getItemId());
	    	if(picture.getSellerId()==null){
	    		picture.setSellerId(item.getSellerId()==null?0:venusItemDTO.getHtdVendorId());
	    	}
	    	if(picture.getShopId()==null){
	    		picture.setShopId(item.getShopId()==null?0:item.getShopId());
	    	}
	    	picture.setCreated(new Date());
	    	picture.setCreateId(venusItemDTO.getOperatorId());
	    	picture.setCreateName(venusItemDTO.getOperatorName());
	    	picture.setModified(new Date());
	    	picture.setModifyId(venusItemDTO.getOperatorId());
	    	picture.setModifyName(venusItemDTO.getOperatorName());
	    	picture.setPictureStatus(1);
	    }
	    itemPictureDAO.batchInsert(picturesList);
	}

	private void doAddItemDescribe(VenusItemInDTO venusItemDTO, Item item) {
		ItemDescribe describe= venusItemDTO.getDescribe();
		//清空id
		describe.setDesId(null);
	    describe.setItemId(item.getItemId());
	    describe.setCreateTime(new Date());
	    describe.setCreateId(venusItemDTO.getOperatorId());
	    describe.setCreateName(venusItemDTO.getOperatorName());
	    describe.setModifyTime(new Date());
	    describe.setModifyId(venusItemDTO.getOperatorId());
	    describe.setModifyName(venusItemDTO.getOperatorName());
	    itemDescribeDAO.insertSelective(describe);
	}

	private void doAddItemDraft(VenusItemInDTO venusItemDTO, Item item) {
		ItemDraft itemDraft=new ItemDraft();
		//三级目录
	    itemDraft.setCid(venusItemDTO.getThirdLevelCategoryId());
	    //shopId
	    itemDraft.setShopId(venusItemDTO.getShopId()==null?0L:venusItemDTO.getShopId());
	    //seller_id
	    itemDraft.setSellerId(venusItemDTO.getHtdVendorId()==null?0L:venusItemDTO.getHtdVendorId());
		//品牌
	    itemDraft.setBrand(venusItemDTO.getBrandId());
		//型号
	    itemDraft.setModelType(venusItemDTO.getSerial());
		//商品名称
	    itemDraft.setItemName(venusItemDTO.getProductName());
		//单位
	    itemDraft.setWeightUnit(venusItemDTO.getUnit());
		//税率
	    if(StringUtils.isNotEmpty(venusItemDTO.getTaxRate())){
	    	BigDecimal newTaxRate=new BigDecimal(venusItemDTO.getTaxRate());
		    itemDraft.setTaxRate(newTaxRate.setScale(4));
	    }
	    //毛重量
	    if(StringUtils.isNotEmpty(venusItemDTO.getGrossWeight())){
	    	BigDecimal newWeight=new BigDecimal(venusItemDTO.getGrossWeight());
			itemDraft.setWeight(newWeight.setScale(4));
	    }
		//净重
	    if(StringUtils.isNotEmpty(venusItemDTO.getNetWeight())){
	    	BigDecimal defaultNetWeight=new BigDecimal(venusItemDTO.getNetWeight());
			itemDraft.setNetWeight(defaultNetWeight.setScale(4));
	    }
		//长
	    if(StringUtils.isNotEmpty(venusItemDTO.getLength())){
	    	itemDraft.setLength(new BigDecimal(venusItemDTO.getLength()).setScale(4));
	    }
		//宽 
	    if(StringUtils.isNotEmpty(venusItemDTO.getWidth())){
	    	itemDraft.setWidth(new BigDecimal(venusItemDTO.getWidth()).setScale(4));
	    }
	    //高
		if(StringUtils.isNotEmpty(venusItemDTO.getHeight())){
			itemDraft.setHeight(new BigDecimal(venusItemDTO.getHeight()).setScale(4));
		}
		itemDraft.setIsSpu(1);
		itemDraft.setItemSpuId(item.getItemSpuId());
		//颜色
		itemDraft.setAttrSale(venusItemDTO.getColor());
		//广告语
		itemDraft.setAd(venusItemDTO.getAd());
        //生产地
		itemDraft.setOrigin(venusItemDTO.getOriginPlace());
		//目录属性
		itemDraft.setAttributes(venusItemDTO.getCategoryAttribute());
		itemDraft.setItemId(item.getItemId());
		itemDraft.setCreateId(venusItemDTO.getOperatorId());
		itemDraft.setCreateName(venusItemDTO.getOperatorName());
		itemDraft.setModifyId(venusItemDTO.getOperatorId());
		itemDraft.setModifyName(venusItemDTO.getOperatorName());
		itemDraft.setModified(new Date());
		itemDraftMapper.insertSelective(itemDraft);
		
		//图片
		 List<ItemDraftPicture> draftPicturesList= Lists.newArrayList();
		    for(ItemPicture picture:venusItemDTO.getPictures()){
		    	ItemDraftPicture draftPic=new ItemDraftPicture();
		    	draftPic.setItemDraftId(itemDraft.getItemDraftId());
		    	draftPic.setPictureUrl(picture.getPictureUrl());
		    	draftPic.setIsFirst(picture.getIsFirst());
		    	draftPic.setSortNumber(picture.getSortNumber());
		    	//sellerId
		    	if(picture.getSellerId()==null){
		    		draftPic.setSellerId(item.getSellerId());
		    	}else{
		    		draftPic.setSellerId(picture.getSellerId());
		    	}
		    	if(picture.getShopId()==null){
		    		draftPic.setShopId(venusItemDTO.getShopId()==null?0L:venusItemDTO.getShopId());
		    	}else{
		    		draftPic.setShopId(picture.getShopId());
		    	}
		    	draftPic.setPictureStatus(1);
		    	draftPic.setCreated(new Date());
		    	draftPic.setCreateId(venusItemDTO.getOperatorId());
		    	draftPic.setCreateName(venusItemDTO.getOperatorName());
		    	draftPic.setModified(new Date());
		    	draftPic.setModifyId(venusItemDTO.getOperatorId());
		    	draftPic.setModifyName(venusItemDTO.getOperatorName());
		    	
		    	draftPicturesList.add(draftPic);
		    }
		itemDraftPictureMapper.batchInsert(draftPicturesList);
		
		ItemDraftDescribe describeFromDb=new ItemDraftDescribe();
		describeFromDb.setCreateId(venusItemDTO.getOperatorId());
		describeFromDb.setCreateName(venusItemDTO.getOperatorName());
		describeFromDb.setCreateTime(new Date());
		describeFromDb.setDescribeContent(venusItemDTO.getDescribe().getDescribeContent());
		describeFromDb.setItemDraftId(itemDraft.getItemDraftId());
		describeFromDb.setModifyId(venusItemDTO.getOperatorId());
		describeFromDb.setModifyName(venusItemDTO.getOperatorName());
		describeFromDb.setModifyTime(new Date());
		itemDraftDescribeMapper.insertSelective(describeFromDb);
	}
	
	@Transactional
	@Override
	public ExecuteResult<String> updateItem(VenusItemInDTO venusItemDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(venusItemDTO == null){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg()));
			return result;
		}
		//做更新，主键不能为空
		if(venusItemDTO.getItemId() ==null || venusItemDTO.getItemId() <=0 ){
			result.setCode(ErrorCodes.E10001.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10001.getErrorMsg("itemId")));
			return result;
		}
		//先校验
	    ValidateResult va1lidateResult=DTOValidateUtil.validate(venusItemDTO);
		
		if(!va1lidateResult.isPass()){
			result.setCode(ErrorCodes.E10006.name());
			result.setErrorMessages(Lists.newArrayList(StringUtils.split(va1lidateResult.getMessage(),DTOValidateUtil.ERROR_MSG_SEPERATOR)));
			return  result;
		}
		
		if(CollectionUtils.isEmpty(venusItemDTO.getPictures())){
			result.setCode(VenusErrorCodes.E1040001.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040001.getErrorMsg()));
			return  result;
		}
		
		if(venusItemDTO.getDescribe()==null||StringUtils.isEmpty(venusItemDTO.getDescribe().getDescribeContent())){
			result.setCode(VenusErrorCodes.E1040002.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040002.getErrorMsg()));
			return  result;
		}
		//查询数据库中item
		try{
			Item itemFromDb=itemMybatisDAO.queryItemByPk(venusItemDTO.getItemId());
			if(itemFromDb == null){
				result.setCode(VenusErrorCodes.E1040003.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040003.getErrorMsg()));
				return  result;
			}
			ItemDraft itemDraftFromDB = doUpdateItemDraft(venusItemDTO, itemFromDb);
			
			doUpdateItemDraftPicture(venusItemDTO, itemFromDb, itemDraftFromDB);
			
			doUpdateItemDraftDescribe(venusItemDTO, itemDraftFromDB);
			
			//ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(itemFromDb.getItemId());
			
			//ModifyDetailInfoUtil.saveChangedRecordForVenusItem((ItemDTO)Converters.convert(venusItemDTO,ItemDTO.class), dbItem);
			
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::updateItem:",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	private ItemDraft doUpdateItemDraft(VenusItemInDTO venusItemDTO,
			Item itemFromDb) {
		//比对变化字段
		boolean isItemInfoChanged=false;
		
		//数据保存到item_draft
		ItemDraft changedVenusItemDTO=itemDraftMapper.selectByItemId(itemFromDb.getItemId());
		
		if(changedVenusItemDTO==null){
			changedVenusItemDTO=new ItemDraft();
			//如果曾经没有草稿信息，则认为是审核通过商品
			changedVenusItemDTO.setStatus(1);
			changedVenusItemDTO.setVerifyName("管理员");
			changedVenusItemDTO.setVerifyStatus(1);
		}
		logger.error("doUpdateItemDraft::{}",JSON.toJSONString(changedVenusItemDTO));
		differItemInfo(venusItemDTO, itemFromDb,isItemInfoChanged, changedVenusItemDTO);
		logger.error("after differItemInfo ::{}",JSON.toJSONString(changedVenusItemDTO));
		
		//模版
		ItemSpu itemSpu=itemSpuMapper.queryItemSpuByName(changedVenusItemDTO.getItemName());
		
		//if(isItemInfoChanged){
			changedVenusItemDTO.setItemId(itemFromDb.getItemId());
			changedVenusItemDTO.setModifyId(venusItemDTO.getOperatorId());
			changedVenusItemDTO.setModifyName(venusItemDTO.getOperatorName());
			changedVenusItemDTO.setModified(new Date());
			changedVenusItemDTO.setStatus(HtdItemStatusEnum.AUDITING.getCode());
			if(itemSpu!=null){
				changedVenusItemDTO.setItemSpuId(itemSpu.getSpuId());
			}
			logger.error("doUpdateItemDraft::{}",JSON.toJSONString(changedVenusItemDTO));
			if(changedVenusItemDTO.getItemDraftId()!=null){
				//update
				itemDraftMapper.updateByPrimaryKeySelective(changedVenusItemDTO);
				}else{
				//insert
				itemDraftMapper.insertSelective(changedVenusItemDTO);
			}
			//更新下item的状态
			if(HtdItemStatusEnum.REJECTED.getCode()==itemFromDb.getItemStatus()){
				itemMybatisDAO.updateItemStatusByPk(itemFromDb.getItemId(), HtdItemStatusEnum.AUDITING.getCode(), venusItemDTO.getOperatorId(), venusItemDTO.getOperatorName());
			}
			//itemMybatisDAO.updateItemStatusByPk(itemFromDb.getItemId(), HtdItemStatusEnum.AUDITING.getCode(), venusItemDTO.getOperatorId(), venusItemDTO.getOperatorName());
		//}
		return changedVenusItemDTO;
	}

	private void doUpdateItemDraftPicture(VenusItemInDTO venusItemDTO,
			Item itemFromDb, ItemDraft itemDraftFromDB) {
		//图片
		 List<ItemDraftPicture> draftPicturesList= Lists.newArrayList();
		    for(ItemPicture picture:venusItemDTO.getPictures()){
		    	ItemDraftPicture draftPic=new ItemDraftPicture();
		    	draftPic.setItemDraftId(itemDraftFromDB.getItemDraftId());
		    	draftPic.setPictureUrl(picture.getPictureUrl());
		    	//isFirst
		    	draftPic.setIsFirst(picture.getIsFirst());
		    	//sortNumber
		    	draftPic.setSortNumber(picture.getSortNumber());
		    	//sellerId
		    	if(picture.getSellerId()==null){
		    		draftPic.setSellerId(itemFromDb.getSellerId());
		    	}else{
		    		draftPic.setSellerId(picture.getSellerId());
		    	}
		    	if(picture.getShopId()==null){
		    		draftPic.setShopId(itemFromDb.getShopId());
		    	}else{
		    		draftPic.setShopId(picture.getShopId());
		    	}
		    	draftPic.setCreated(new Date());
		    	draftPic.setCreateId(venusItemDTO.getOperatorId());
		    	draftPic.setCreateName(venusItemDTO.getOperatorName());
		    	draftPic.setModified(new Date());
		    	draftPic.setModifyId(venusItemDTO.getOperatorId());
		    	draftPic.setModifyName(venusItemDTO.getOperatorName());
		    	
		    	draftPicturesList.add(draftPic);
		    }
		itemDraftPictureMapper.deleteDraftPicByItemDraftId(itemDraftFromDB.getItemDraftId());
		//更新图片
		itemDraftPictureMapper.batchInsert(draftPicturesList);
	}

	private void doUpdateItemDraftDescribe(VenusItemInDTO venusItemDTO,
			ItemDraft itemDraftFromDB) {
		//查询数据库中描述
		ItemDraftDescribe describeFromDb=itemDraftDescribeMapper.selectByItemDraftId(itemDraftFromDB.getItemDraftId());
		if(describeFromDb==null){
			describeFromDb=new ItemDraftDescribe();
			describeFromDb.setCreateId(venusItemDTO.getOperatorId());
			describeFromDb.setCreateName(venusItemDTO.getOperatorName());
			describeFromDb.setCreateTime(new Date());
			describeFromDb.setDescribeContent(venusItemDTO.getDescribe().getDescribeContent());
			describeFromDb.setItemDraftId(itemDraftFromDB.getItemDraftId());
			describeFromDb.setModifyId(venusItemDTO.getOperatorId());
			describeFromDb.setModifyName(venusItemDTO.getOperatorName());
			describeFromDb.setModifyTime(new Date());
			itemDraftDescribeMapper.insertSelective(describeFromDb);
		}else{
			describeFromDb.setModifyId(venusItemDTO.getOperatorId());
			describeFromDb.setModifyName(venusItemDTO.getOperatorName());
			describeFromDb.setModifyTime(new Date());
			describeFromDb.setDescribeContent(venusItemDTO.getDescribe().getDescribeContent());
			itemDraftDescribeMapper.updateByPrimaryKeyWithBLOBs(describeFromDb);
		}
	}

	private boolean differItemInfo(VenusItemInDTO venusItemDTO,
			Item itemFromDb, boolean isItemInfoChanged,
			ItemDraft changedVenusItemDTO) {
		logger.error("differItemInfo::{}",JSON.toJSONString(changedVenusItemDTO));
		//三级目录
		if(changedVenusItemDTO.getCid()==null||
				!changedVenusItemDTO.getCid().equals(venusItemDTO.getThirdLevelCategoryId())){
			isItemInfoChanged=true;
			changedVenusItemDTO.setCid(venusItemDTO.getThirdLevelCategoryId());
		}
		//品牌
		if(changedVenusItemDTO.getBrand()==null||
				!changedVenusItemDTO.getBrand().equals(venusItemDTO.getBrandId())){
			isItemInfoChanged=true;
			changedVenusItemDTO.setBrand(venusItemDTO.getBrandId());
		}
		//型号
		if(!StringUtils.trim(venusItemDTO.getSerial()).equals(changedVenusItemDTO.getModelType())){
			isItemInfoChanged=true;
			changedVenusItemDTO.setModelType(venusItemDTO.getSerial());
		}
		//商品名称
		if(!StringUtils.trim(venusItemDTO.getProductName()).equals(changedVenusItemDTO.getItemName())){
			isItemInfoChanged=true;
			changedVenusItemDTO.setItemName(venusItemDTO.getProductName());
		}
		//单位
		if(!StringUtils.trim(venusItemDTO.getUnit()).equals(changedVenusItemDTO.getWeightUnit())){
			isItemInfoChanged=true;
			changedVenusItemDTO.setWeightUnit(venusItemDTO.getUnit());
		}
		//税率
		if(!StringUtils.trim(venusItemDTO.getTaxRate()).equals(changedVenusItemDTO.getTaxRate())){
			BigDecimal newTaxRate=new BigDecimal(venusItemDTO.getTaxRate());
			changedVenusItemDTO.setTaxRate(newTaxRate.setScale(4));
		}
		
//		if(!changedVenusItemDTO.getNetWeight().equals(new BigDecimal(venusItemDTO.getGrossWeight()).setScale(4))
//				||!changedVenusItemDTO.getNetWeight().equals(new BigDecimal(venusItemDTO.getNetWeight()).setScale(4))
//				||!changedVenusItemDTO.getLength().equals(new BigDecimal(venusItemDTO.getLength()).setScale(4))
//				||!changedVenusItemDTO.getWidth().equals(new BigDecimal(venusItemDTO.getWidth()).setScale(4))
//				||!changedVenusItemDTO.getHeight().equals(new BigDecimal(venusItemDTO.getHeight()).setScale(4))
//				||!StringUtils.trim(changedVenusItemDTO.getAttrSale()).equals(venusItemDTO.getColor())
//				||!StringUtils.trim(changedVenusItemDTO.getAd()).equals(venusItemDTO.getAd())
//				||!StringUtils.trim(venusItemDTO.getOriginPlace()).equals(changedVenusItemDTO.getOrigin())
//				||!StringUtils.trim(venusItemDTO.getCategoryAttribute()).equals(changedVenusItemDTO.getAttributes())){
//			
//			isItemInfoChanged=true;
			//毛重量
			BigDecimal newWeight=new BigDecimal(venusItemDTO.getGrossWeight());
			changedVenusItemDTO.setWeight(newWeight.setScale(4));
			//净重
			BigDecimal defaultNetWeight=new BigDecimal(venusItemDTO.getNetWeight());
			changedVenusItemDTO.setNetWeight(defaultNetWeight.setScale(4));
			//长
			changedVenusItemDTO.setLength(new BigDecimal(venusItemDTO.getLength()).setScale(4));
			//宽 
			changedVenusItemDTO.setWidth(new BigDecimal(venusItemDTO.getWidth()).setScale(4));
			//高
			changedVenusItemDTO.setHeight(new BigDecimal(venusItemDTO.getHeight()).setScale(4));
			//颜色
			changedVenusItemDTO.setAttrSale(venusItemDTO.getColor());
			//广告语
			changedVenusItemDTO.setAd(venusItemDTO.getAd());
	        //生产地
			changedVenusItemDTO.setOrigin(venusItemDTO.getOriginPlace());
			//目录属性
			changedVenusItemDTO.setAttributes(venusItemDTO.getCategoryAttribute());
//		}
		
		logger.error("differItemInfo::{}",JSON.toJSONString(changedVenusItemDTO));
		
		return isItemInfoChanged;
	}
	
	@Deprecated
	@Transactional
	@Override
	public ExecuteResult<String> applyHtdProduct(List<Long> itemIdList,String sellerId,String shopId,String operatorId,String operatorName) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(CollectionUtils.isEmpty(itemIdList)){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg("itemIdList")));
			return result;
		}
		try{
			for(Long itemId:itemIdList){
				if(itemId==null||itemId<=0){
					continue;
				}
				//根据code查询商品数据
				ItemDTO itemDTO=itemMybatisDAO.getItemDTOById(itemId);
				if(itemDTO ==null){
					result.setCode(VenusErrorCodes.E1040003.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040003.getErrorMsg("item商品数据")));
					return result;
				}
				List<ItemSku> itemSkuList=itemSkuDAO.queryByItemId(itemDTO.getItemId());
				if(CollectionUtils.isEmpty(itemSkuList)){
					result.setCode(VenusErrorCodes.E1040003.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040003.getErrorMsg("sku")));
					return result;
				}
				//ItemSku itemSku=itemSkuList.get(0);
				//校验商品的品类
				if(itemDTO.getCid()==null){
					result.setCode(VenusErrorCodes.E1040004.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040004.getErrorMsg("商品的品类")));
					return result;
				}
				//校验商品的品牌
				if(itemDTO.getBrand()==null){
					result.setCode(VenusErrorCodes.E1040005.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040005.getErrorMsg("商品的品牌")));
					return result;
				}
				//校验商品的单位
				if(itemDTO.getWeightUnit()==null){
					result.setCode(VenusErrorCodes.E1040006.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040006.getErrorMsg("商品的单位")));
					return result;
				}
				//校验当前供应商是否已经存在该商品
				Integer count=itemMybatisDAO.queryItemByName(itemDTO.getItemName(), sellerId, shopId);
				if(count!=null&&count>=1){
					result.setCode(VenusErrorCodes.E1040007.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040007.getErrorMsg(itemDTO.getItemName()+",已经存在该商品")));
					return result;
				}
				//将商品转化为该大b的商品
				itemDTO.setSellerId(Long.valueOf(sellerId));
				itemDTO.setShopId(Long.valueOf(shopId));
				//itemDTO.setShopCid(0L);
				//itemDTO.setShopFreightTemplateId(0L);
				itemDTO.setCreated(new Date());
				itemDTO.setCreateId(Long.valueOf(operatorId));
				itemDTO.setCreateName(operatorName);
				itemDTO.setModified(new Date());
				itemDTO.setModifyId(Long.valueOf(operatorId));
				itemDTO.setModifyName(operatorName);
				//商品状态以及其他一些字段
				itemDTO.setItemStatus(ItemStatusEnum.PASS.getCode());
				//保存item
				Item newItem=ItemDTOToDomainUtil.itemDTO2Item(itemDTO);
				newItem.setItemCode(ItemCodeGenerator.generateItemCode());
				newItem.setProductChannelCode(ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL);
				itemMybatisDAO.addItem(newItem);
				//图片
				VenusItemInDTO venusItemDTO=new VenusItemInDTO();
				venusItemDTO.setOperatorId(StringUtils.isEmpty(operatorId)?0:Long.valueOf(operatorId));
				venusItemDTO.setOperatorName(operatorName);
				List<ItemPicture> picList=itemPictureDAO.queryItemPicsById(itemId);
				if(CollectionUtils.isNotEmpty(picList)){
					venusItemDTO.setPictures(picList);
					doAddItemPicture(venusItemDTO, newItem);
				}
				//描述
				ItemDescribe describe=itemDescribeDAO.getDescByItemId(itemId);
				if(describe!=null){
					venusItemDTO.setDescribe(describe);
					doAddItemDescribe(venusItemDTO, newItem);
				}
				//新建sku
				ItemSku newItemSku=Converters.convert(newItem, ItemSku.class);
				//保存sku
				itemSkuDAO.add(newItemSku);
				//根据当前申请商品保存大B的CBCVIEW(调用会员中心)，交由页面去调了
			}
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::applyHtdProduct:",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}
	
	@Override
	public ExecuteResult<VenusItemSkuDetailOutDTO> queryItemSkuDetail(Long itemSkuId) {
		ExecuteResult<VenusItemSkuDetailOutDTO> result=new ExecuteResult<VenusItemSkuDetailOutDTO>();
		if(itemSkuId==null || itemSkuId<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg()));
			return result;
		}
		
		try{
			VenusItemSkuDetailOutDTO venusItemSkuDetailOutDTO=itemSkuDAO.queryItemSkuDetail(itemSkuId);
			VenusItemSkuDetailOutDTO draftItemSkuDetailOutDTO=itemDraftMapper.queryItemSkuDraftDetail(itemSkuId);
			
			if(draftItemSkuDetailOutDTO!=null){
				if(Integer.valueOf(HtdItemStatusEnum.AUDITING.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())
						||Integer.valueOf(HtdItemStatusEnum.REJECTED.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())){
					    draftItemSkuDetailOutDTO.setStatusChangeReason(venusItemSkuDetailOutDTO.getStatusChangeReason());
					    draftItemSkuDetailOutDTO.setErpCode(venusItemSkuDetailOutDTO.getErpCode());
						venusItemSkuDetailOutDTO=draftItemSkuDetailOutDTO;
				}
			}
			
			
			//查询描述
			ItemDescribe descirbe=itemDescribeDAO.getDescByItemId(venusItemSkuDetailOutDTO.getItemId());
			if(draftItemSkuDetailOutDTO!=null){
				if(Integer.valueOf(HtdItemStatusEnum.AUDITING.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())
						||Integer.valueOf(HtdItemStatusEnum.REJECTED.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())){
					ItemDescribe draftDescirbe=itemDraftDescribeMapper.selectByItemId(venusItemSkuDetailOutDTO.getItemId());
					if(draftDescirbe!=null){
						descirbe=draftDescirbe;
					}
				}
			}
			venusItemSkuDetailOutDTO.setDescribe(descirbe);
			//查询图片
			List<ItemPicture> pictures=itemPictureDAO.queryItemPicsById(venusItemSkuDetailOutDTO.getItemId());
			if(draftItemSkuDetailOutDTO!=null){
				if(Integer.valueOf(HtdItemStatusEnum.AUDITING.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())
						||Integer.valueOf(HtdItemStatusEnum.REJECTED.getCode()).equals(draftItemSkuDetailOutDTO.getItemStatus())){
					List<ItemPicture> draftPictures=itemDraftPictureMapper.queryItemDraftPicsByItemId(venusItemSkuDetailOutDTO.getItemId());
					if(CollectionUtils.isNotEmpty(draftPictures)){
						pictures=draftPictures;
					}
				}
			}
			venusItemSkuDetailOutDTO.setPictures(pictures);
			//补足一级品类和三级品牌
			VenusOrderItemSkuDetailOutDTO venusOrderItemSkuDetailOutDTO=new VenusOrderItemSkuDetailOutDTO();
			venusOrderItemSkuDetailOutDTO.setThirdCatId(venusItemSkuDetailOutDTO.getThirdCatId());
			makeUpFirstAndSecondCat(venusOrderItemSkuDetailOutDTO);
			venusItemSkuDetailOutDTO.setFirstCatId(venusOrderItemSkuDetailOutDTO.getFirstCatId());
			venusItemSkuDetailOutDTO.setFirstCatName(venusOrderItemSkuDetailOutDTO.getFirstCatName());
			venusItemSkuDetailOutDTO.setSecondCatId(venusOrderItemSkuDetailOutDTO.getSecondCatId());
			venusItemSkuDetailOutDTO.setSecondCatName(venusOrderItemSkuDetailOutDTO.getSecondCatName());
			result.setResult(venusItemSkuDetailOutDTO);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemSkuDetail:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> queryItemSkuList(
			QueryVenusItemListParamDTO queryVenusItemListPo,Pager<String> page) {
		
		ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> result=new ExecuteResult<DataGrid<VenusItemListStyleOutDTO>>();
		
		if(queryVenusItemListPo==null || page==null || page.getPage()<=0 || page.getRows()<=0){
			result.setCode(VenusErrorCodes.E1040009.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
			return result;
		}
		
		if(queryVenusItemListPo.getHtdVendorId()==null||queryVenusItemListPo.getHtdVendorId()<=0){
			result.setCode(VenusErrorCodes.E1040010.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040010.getErrorMsg()));
			return result;
		}
		try{
			//待审核则取草稿表数据
			if(String.valueOf(ItemStatusEnum.AUDITING.getCode()).equals(queryVenusItemListPo.getProductStatus())){
				return doQueryItemDraftList(queryVenusItemListPo, page);
			}
			
			Long totalCount=itemSkuDAO.queryItemSkuListCount(queryVenusItemListPo);
			
			if(totalCount==null||totalCount<=0){
				result.setCode(ErrorCodes.SUCCESS.name());
				DataGrid<VenusItemListStyleOutDTO> dataGrid=new DataGrid<VenusItemListStyleOutDTO>();
				result.setResult(dataGrid);
				return result;
			}
			page.setTotalCount(totalCount.intValue());
			queryVenusItemListPo.setStart((page.getPage()-1) * page.getRows());
			queryVenusItemListPo.setPageSize(page.getRows());
			List<VenusItemListStyleOutDTO> list=itemSkuDAO.queryItemSkuList(queryVenusItemListPo);
			if(String.valueOf(ItemStatusEnum.REJECTED.getCode()).equals(queryVenusItemListPo.getProductStatus())){
				for(VenusItemListStyleOutDTO v:list){
					v.setProductStatus(String.valueOf(ItemStatusEnum.REJECTED.getCode()));
				}
			}
			DataGrid<VenusItemListStyleOutDTO> dataGrid=new DataGrid<VenusItemListStyleOutDTO>(list);
			dataGrid.setTotal(totalCount);
			dataGrid.setPageSize(page.getRows());
			result.setResult(dataGrid);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemSkuList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	private ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> doQueryItemDraftList(
			QueryVenusItemListParamDTO queryVenusItemListPo,
			Pager<String> page) {
		ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> result=new ExecuteResult<DataGrid<VenusItemListStyleOutDTO>>();
		
		try{
			Long totalCount=itemDraftMapper.queryDraftItemSkuListCount(queryVenusItemListPo);
			if(totalCount==null||totalCount<=0){
				result.setCode(ErrorCodes.SUCCESS.name());
				DataGrid<VenusItemListStyleOutDTO> dataGrid=new DataGrid<VenusItemListStyleOutDTO>();
				result.setResult(dataGrid);
				return result;
			}
			
			page.setTotalCount(totalCount.intValue());
			queryVenusItemListPo.setStart((page.getPage()-1) * page.getRows());
			queryVenusItemListPo.setPageSize(page.getRows());
			List<VenusItemListStyleOutDTO> list=itemDraftMapper.queryDraftItemSkuList(queryVenusItemListPo);
			DataGrid<VenusItemListStyleOutDTO> dataGrid=new DataGrid<VenusItemListStyleOutDTO>(list);
			dataGrid.setTotal(totalCount);
			dataGrid.setPageSize(page.getRows());
			result.setResult(dataGrid);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::doQueryItemDraftList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<VenusItemSkuPublishInfoOutDTO>> queryItemSkuPublishInfoList(
			VenusItemSkuPublishInfoInDTO venusItemSkuPublishInfo,Pager<String> page){
		ExecuteResult<DataGrid<VenusItemSkuPublishInfoOutDTO>> result=new ExecuteResult<DataGrid<VenusItemSkuPublishInfoOutDTO>>();
		if(venusItemSkuPublishInfo==null){
			result.setCode(VenusErrorCodes.E1040009.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
			return result;
		}
		try{
			Long totalCount=itemSkuDAO.queryVenusItemSkuPublishInfoListCount(venusItemSkuPublishInfo);
			if(totalCount==null||totalCount<=0){
				result.setCode(ErrorCodes.SUCCESS.name());
				DataGrid<VenusItemSkuPublishInfoOutDTO> dataGrid=new DataGrid<VenusItemSkuPublishInfoOutDTO>();
				result.setResult(dataGrid);
				return result;
			}
			page.setTotalCount(totalCount.intValue());
			venusItemSkuPublishInfo.setStart((page.getPage()-1) * page.getRows());
			venusItemSkuPublishInfo.setPageSize(page.getRows());
			if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(page.getSort()))){
				venusItemSkuPublishInfo.setSortColumn(page.getSort());
			}
			venusItemSkuPublishInfo.setSortType("1");//默认升序
			if("desc".equals(page.getOrder())){//如果是DESC则更改为降序
				venusItemSkuPublishInfo.setSortType("2");
			}
			List<VenusItemSkuPublishInfoOutDTO> list=itemSkuDAO.queryVenusItemSkuPublishInfoList(venusItemSkuPublishInfo);
			//调用订单中心接口，获取价格
			makeUpPriceInfo4ItemSku(list,venusItemSkuPublishInfo.getSupplierCode());
			DataGrid<VenusItemSkuPublishInfoOutDTO> dataGrid=new DataGrid<VenusItemSkuPublishInfoOutDTO>(list);
			dataGrid.setTotal(totalCount);
			dataGrid.setPageSize(page.getRows());
			result.setResult(dataGrid);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemSkuPublishInfoList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}
	
	private void makeUpPriceInfo4ItemSku(List<VenusItemSkuPublishInfoOutDTO> venusItemSkuPublishInfoList,String supplierCode){
		if(CollectionUtils.isEmpty(venusItemSkuPublishInfoList)){
			return;
		}
		List<Long> skuIdList=Lists.newArrayList();
		List<String> spuCodeList=Lists.newArrayList();
		for(VenusItemSkuPublishInfoOutDTO venusItemSkuPublishInfoOutDTO:venusItemSkuPublishInfoList){
			if(venusItemSkuPublishInfoOutDTO.getSkuId()!=null){
				skuIdList.add(venusItemSkuPublishInfoOutDTO.getSkuId());
			}
			if(StringUtils.isNotEmpty(venusItemSkuPublishInfoOutDTO.getSpuCode())){
				spuCodeList.add(venusItemSkuPublishInfoOutDTO.getSpuCode());
			}
		} 
		logger.info("请求中间件查询总库存开始, 供应商编码 :{}, spuCode : {}", supplierCode, JSONArray.fromObject(spuCodeList));
		List<ItemStockResponseDTO> itemStockList=MiddlewareInterfaceUtil.getItemStockList(supplierCode, spuCodeList);
		logger.info("请求中间件查询总库存结束, 结果 : {}", JSONArray.fromObject(itemStockList));
		ExecuteResult<List<ItemSkuBasePrice>> basePriceList=itemSkuPriceService.batchQueryItemSkuBasePrice(skuIdList);
		if(basePriceList==null ||CollectionUtils.isEmpty(basePriceList.getResult())){
			return;      
		}
		for(VenusItemSkuPublishInfoOutDTO venusItemSkuPublishInfoOutDTO:venusItemSkuPublishInfoList){
			
			if(CollectionUtils.isNotEmpty(itemStockList)){
				for(ItemStockResponseDTO itemStock:itemStockList){
					if(itemStock.getStoreNum()!=null&&itemStock.getStoreNum()>0L
							&&itemStock.getProductCode().equals(venusItemSkuPublishInfoOutDTO.getSpuCode())){
						venusItemSkuPublishInfoOutDTO.setTotalStock(Long.valueOf(itemStock.getStoreNum()));
					}
				}
				
			}
			
			for(ItemSkuBasePrice price:basePriceList.getResult()){
				if(price.getSkuId().equals(venusItemSkuPublishInfoOutDTO.getSkuId())){
					//分销限价
					price.getSaleLimitedPrice();
					if(price.getSaleLimitedPrice()!=null){
						venusItemSkuPublishInfoOutDTO.setSaleLimitedPrice(String.valueOf(price.getSaleLimitedPrice()));
					}
					//包厢价格
					if(null!=price.getBoxSalePrice()&&"1".equals(venusItemSkuPublishInfoOutDTO.getShelfType())){
						venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(price.getBoxSalePrice()));
					}
					//大厅价格
					if(null!=price.getAreaSalePrice()&&"2".equals(venusItemSkuPublishInfoOutDTO.getShelfType())){
						venusItemSkuPublishInfoOutDTO.setSalePrice(String.valueOf(price.getAreaSalePrice()));
					}
					//零售价
					if(null!=price.getRetailPrice()){
						venusItemSkuPublishInfoOutDTO.setRetailPrice(String.valueOf(price.getRetailPrice()));
					}
					break;
				}
			}
		}
	}

	@Override
	public ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> queryItemSkuPublishInfoDetail(
			QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO) {
		ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> result=new ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO>();
		if(querySkuPublishInfoDetailParamDTO==null){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg()));
			return result;
		}
		if(querySkuPublishInfoDetailParamDTO.getSkuId()==null||querySkuPublishInfoDetailParamDTO.getSkuId()<=0){
			result.setCode(VenusErrorCodes.E1040011.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040011.getErrorMsg()));
			return result;
		}
		if(StringUtils.isEmpty(querySkuPublishInfoDetailParamDTO.getShelfType())){
			result.setCode(VenusErrorCodes.E1040012.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040012.getErrorMsg()));
			return result;
		}
		
		try{
			 ItemSku itemSku=itemSkuDAO.queryItemSkuBySkuId(querySkuPublishInfoDetailParamDTO.getSkuId());
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
			
		    //查询实际库存
			ItemStockResponseDTO itemStockResponse=MiddlewareInterfaceUtil.getSingleItemStock(querySkuPublishInfoDetailParamDTO.getSupplierCode(), spu.getSpuCode());
			
			//先同步下实际库存
			syncTotalStock(itemSku,itemStockResponse);
			
			VenusItemSkuPublishInfoDetailOutDTO venusItemSkuPublishInfoDetailOutDTO=
					itemSkuPublishInfoMapper.queryPublishInfoDetailBySkuId(querySkuPublishInfoDetailParamDTO);
			if(venusItemSkuPublishInfoDetailOutDTO ==null){
				result.setCode(VenusErrorCodes.E1040013.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040013.getErrorMsg(querySkuPublishInfoDetailParamDTO.getSkuId()+"")));
				return result;
			}
			
			if(itemStockResponse!=null&&itemStockResponse.getStoreNum()!=null){
				venusItemSkuPublishInfoDetailOutDTO.setTotalStock(itemStockResponse.getStoreNum()+"");
			}
			//查询描述
			ItemDescribe descirbe=itemDescribeDAO.getDescByItemId(venusItemSkuPublishInfoDetailOutDTO.getItemId());
			venusItemSkuPublishInfoDetailOutDTO.setDescribe(descirbe);
			//查询图片
			List<ItemPicture> pictures=itemPictureDAO.queryItemPicsById(venusItemSkuPublishInfoDetailOutDTO.getItemId());
			venusItemSkuPublishInfoDetailOutDTO.setPictures(pictures);
			//销售区域
			ItemSalesArea itemSaleArea=itemSalesAreaMapper.selectByItemId(venusItemSkuPublishInfoDetailOutDTO.getItemId(),querySkuPublishInfoDetailParamDTO.getShelfType());
			if(itemSaleArea!=null&&itemSaleArea.getSalesAreaId()!=null){
				venusItemSkuPublishInfoDetailOutDTO.setItemSaleArea(itemSaleArea);
				List<ItemSalesAreaDetail> itemSaleAreaDetailList=itemSalesAreaDetailMapper.selectAreaDetailsBySalesAreaIdAll(itemSaleArea.getSalesAreaId());
				venusItemSkuPublishInfoDetailOutDTO.setItemSaleAreaDetailList(itemSaleAreaDetailList);
			}
			//价格政策
			String shelfType = querySkuPublishInfoDetailParamDTO.getShelfType(); // TODO : 1 包厢 2 区域
			Integer isBoxFlag = shelfType.equals("1") ? 1 : 0;
			ExecuteResult<StandardPriceDTO> queryStandardPriceExeResult=itemSkuPriceService.queryStandardPrice4InnerSeller(querySkuPublishInfoDetailParamDTO.getSkuId(), isBoxFlag);
			if(queryStandardPriceExeResult.isSuccess()){
				venusItemSkuPublishInfoDetailOutDTO.setStandardPriceDTO(queryStandardPriceExeResult.getResult());
			}
			//查询促销锁定库存
			ExecuteResult<Integer> timelimitedInfoDTOResult=
					timelimitedInfoService.getSkuTimelimitedAllCount(MessageIdUtils.generateMessageId(), venusItemSkuPublishInfoDetailOutDTO
					.getSkuCode());
			
			if(timelimitedInfoDTOResult!=null && timelimitedInfoDTOResult.isSuccess()){
				String promotionQty=timelimitedInfoDTOResult.getResult()==null ? "0":
					timelimitedInfoDTOResult.getResult() + "";
				venusItemSkuPublishInfoDetailOutDTO.setPromotionReserveQty(promotionQty);
			}
			
			result.setCode(ErrorCodes.SUCCESS.name());
			result.setResult(venusItemSkuPublishInfoDetailOutDTO);
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemSkuPublishInfoDetail:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}
	
	private void syncTotalStock(ItemSku itemSku,ItemStockResponseDTO itemStockResponse){
		if(itemSku==null||itemStockResponse==null){
			return;
		}
        //查询库存上架信息
        List<ItemSkuPublishInfo> itemSkuPublishInfoList=itemSkuPublishInfoMapper.queryItemSkuShelfStatus(itemSku.getSkuId());

        if(CollectionUtils.isEmpty(itemSkuPublishInfoList)){
            return;
        }


        Integer stockNum=(itemStockResponse.getStoreNum()==null ||
                itemStockResponse.getStoreNum()<=0) ? 0 : itemStockResponse.getStoreNum();

        for(ItemSkuPublishInfo itemSkuPublishInfo:itemSkuPublishInfoList){
        	ItemSkuPublishInfoUtil.doUpdateItemSkuPublishInfo(0L, "system", itemSku, stockNum, itemSkuPublishInfo);
        }

    }
	
	@Transactional
	@Override
	public ExecuteResult<String> txPublishItemSkuInfo(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		result=validatePublishData(venusItemSkuPublishInDTO);
		if(!result.isSuccess()){
			return result;
		}
		try{
			//从数据库中查询sku
			ItemSku itemSkuFromDb=itemSkuDAO.queryById(venusItemSkuPublishInDTO.getSkuId());
			
			if(itemSkuFromDb==null){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSku")));
				return result;
			}
			
			//校验状态
			Item item=itemMybatisDAO.queryItemByPk(itemSkuFromDb.getItemId());
			if(item==null||Integer.valueOf(HtdItemStatusEnum.AUDITING.getCode()).equals(item.getItemStatus())
					||Integer.valueOf(HtdItemStatusEnum.REJECTED.getCode()).equals(item.getItemStatus())
					||Integer.valueOf(HtdItemStatusEnum.ERP_STOCKPRICE_OR_OUTPRODUCTPRICE.getCode()).equals(item.getItemStatus())
					||Integer.valueOf(HtdItemStatusEnum.DELETED.getCode()).equals(item.getItemStatus())
					){
				result.setCode(VenusErrorCodes.E1040016.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040016.getErrorMsg()));
				return result;
			}
			//更新sku表
			if(StringUtils.isNotEmpty(venusItemSkuPublishInDTO.getSubTitle())&&
					!StringUtils.trimToEmpty(itemSkuFromDb.getSubTitle()).equals(venusItemSkuPublishInDTO.getSubTitle())){
				itemSkuFromDb.setSubTitle(StringUtils.trim(venusItemSkuPublishInDTO.getSubTitle()));
				itemSkuFromDb.setModified(new Date());
				itemSkuFromDb.setModifyId(venusItemSkuPublishInDTO.getOperatorId());
				itemSkuFromDb.setModifyName(venusItemSkuPublishInDTO.getOperatorName());
				itemSkuDAO.update(itemSkuFromDb);
			}
			//数据库中查询PublishInfo
			ItemSkuPublishInfo itemSkuPublishInfoFromDb=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(venusItemSkuPublishInDTO.getSkuId(), venusItemSkuPublishInDTO.getShelfType(),"0");
			//已锁定数量
			Integer alreadyReserve=itemSkuPublishInfoFromDb==null?0:itemSkuPublishInfoFromDb.getReserveQuantity();
			//校验库存
			boolean isPublishDisplayQtyIsEnough=checkPublishDisplayQtyIsEnough(Integer.parseInt(venusItemSkuPublishInDTO.getDisplayQty()),alreadyReserve, 
					venusItemSkuPublishInDTO.getSkuId(), venusItemSkuPublishInDTO.getShelfType(),venusItemSkuPublishInDTO.getItemId(),
					venusItemSkuPublishInDTO.getSupplierCode(),itemSkuFromDb.getSkuCode());
			if(!isPublishDisplayQtyIsEnough){
				result.setCode(VenusErrorCodes.E1040015.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040015.getErrorMsg()));
				return result;
			}
			
			dealWithItemSkuPublishInfo(venusItemSkuPublishInDTO, itemSkuFromDb,
					itemSkuPublishInfoFromDb);
			//同步库存
//			ItemDTO itemDTO=itemMybatisDAO.getItemDTOById(itemSkuFromDb.getItemId());
//			if(itemDTO!=null&&StringUtils.isNotEmpty(itemDTO.getItemCode())){
//				ExecutorService threadPool=Executors.newSingleThreadExecutor();
//				threadPool.execute(new AsyncFetchItemStockThread(itemDTO.getItemCode(),itemSkuTotalStockMapper,
//						venusItemSkuPublishInDTO.getOperatorId(),venusItemSkuPublishInDTO.getOperatorName()));
//				threadPool.shutdown();
//			}
			if (venusItemSkuPublishInDTO.getPreSaleFlag() != null) {
				itemMybatisDAO.updatePreSaleFlagByItemId(venusItemSkuPublishInDTO.getPreSaleFlag(),
						venusItemSkuPublishInDTO.getItemId());
			}

			//更新价格
			if(venusItemSkuPublishInDTO.getStandardPrice()!=null){
				Integer isBoxFlag="1".equals(venusItemSkuPublishInDTO.getShelfType()) ? 1 : 0;
				itemSkuPriceService.updateItemSkuStandardPrice(venusItemSkuPublishInDTO.getStandardPrice(),isBoxFlag);
				// add by zhangxiaolong for presale 20170815 start
				HzgPriceDTO hzgPriceDTO=venusItemSkuPublishInDTO.getStandardPrice().getHzgPriceDTO();
				if(1==venusItemSkuPublishInDTO.getPreSaleFlag()&&hzgPriceDTO!=null){
					HzgPriceInDTO hzgPriceInDTO=new HzgPriceInDTO();
					hzgPriceInDTO.setItemId(itemSkuFromDb.getItemId());
					hzgPriceInDTO.setOperatorId(venusItemSkuPublishInDTO.getOperatorId());
					hzgPriceInDTO.setOperatorName(venusItemSkuPublishInDTO.getOperatorName());
					hzgPriceInDTO.setRetailPrice(hzgPriceDTO.getRetailPrice());
					hzgPriceInDTO.setSalePrice(hzgPriceDTO.getSalePrice());
					hzgPriceInDTO.setVipPrice(hzgPriceDTO.getVipPrice());
					hzgPriceInDTO.setSellerId(item.getSellerId());
					hzgPriceInDTO.setShopId(item.getShopId());
					hzgPriceInDTO.setSkuId(itemSkuFromDb.getSkuId());
					itemSkuPriceService.saveHzgTerminalPrice(hzgPriceInDTO);
				}
				// add by zhangxiaolong for presale 20170815 end
			}
			//销售区域
			dealWithPublishSalesArea(venusItemSkuPublishInDTO, itemSkuFromDb);
						
			//处理item表的主状态,判断当前商品如果是非上架状态，则要修改为上架状态
			dealWithPublishItemStatus(venusItemSkuPublishInDTO.getSkuId(),venusItemSkuPublishInDTO.getIsVisible(),
					venusItemSkuPublishInDTO.getOperatorId(),venusItemSkuPublishInDTO.getOperatorName(), item);
			
			
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::txPublishItemSkuInfo:",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	private void dealWithPublishItemStatus(
			Long skuId,String visible,Long operatorId,String operatorName, Item item) {
		if(!Integer.valueOf(HtdItemStatusEnum.SHELVED.getCode()).equals(item.getItemStatus())
				&&"1".equals(visible)){//上架
			itemMybatisDAO.updateItemStatusByPk(item.getItemId(), HtdItemStatusEnum.SHELVED.getCode(), operatorId, operatorName);
		}
		if("0".equals(visible)){//下架
			//判断当前商品是否存在上架状态数据，如果没有置为下架状态
			Integer itemSkuOnShelvedCount=itemSkuPublishInfoMapper.queryItemSkuOnShelvedCount(skuId);
			if(itemSkuOnShelvedCount==null||itemSkuOnShelvedCount<=0){
				itemMybatisDAO.updateItemStatusByPk(item.getItemId(), HtdItemStatusEnum.NOT_SHELVES.getCode(), operatorId, operatorName);
			}
		}
		//修改商品更新时间
		Integer upShelf=StringUtils.isEmpty(visible)?0:Integer.parseInt(visible);
		itemMybatisDAO.updateItemModifyTimeByItemId(item.getItemId(),upShelf);
	}

	private void dealWithPublishSalesArea(
			VenusItemSkuPublishInDTO venusItemSkuPublishInDTO,
			ItemSku itemSkuFromDb) {
		if(venusItemSkuPublishInDTO.getItemSaleArea()!=null){
			ItemSalesArea itemSalesArea=venusItemSkuPublishInDTO.getItemSaleArea();
			itemSalesArea.setCreateId(venusItemSkuPublishInDTO.getOperatorId());
			itemSalesArea.setCreateName(venusItemSkuPublishInDTO.getOperatorName());
			itemSalesArea.setCreateTime(new Date());
			itemSalesArea.setModifyId(venusItemSkuPublishInDTO.getOperatorId());
			itemSalesArea.setModifyName(venusItemSkuPublishInDTO.getOperatorName());
			itemSalesArea.setModifyTime(new Date());
			itemSalesArea.setDeleteFlag(0);
			ItemSalesArea itemSalesAreaFromDb=itemSalesAreaMapper.selectByItemId(itemSkuFromDb.getItemId(), venusItemSkuPublishInDTO.getShelfType());
			Long salesAreaId=null;
			if(itemSalesAreaFromDb ==null){
				//insert
				itemSalesAreaMapper.insertSelective(itemSalesArea);
				salesAreaId=itemSalesArea.getSalesAreaId();
			}else{
				//update
				itemSalesArea.setSalesAreaId(itemSalesAreaFromDb.getSalesAreaId());
				itemSalesArea.setModifyId(venusItemSkuPublishInDTO.getOperatorId());
				itemSalesArea.setModifyName(venusItemSkuPublishInDTO.getOperatorName());
				itemSalesArea.setModifyTime(new Date());
				itemSalesArea.setDeleteFlag(0);
				itemSalesAreaMapper.updateByPrimaryKeySelective(itemSalesArea);
				salesAreaId=itemSalesAreaFromDb.getSalesAreaId();
			}
			//处理salesareadetail
			if(CollectionUtils.isNotEmpty(venusItemSkuPublishInDTO.getItemSaleAreaDetailList())){
				for(ItemSalesAreaDetail salesAreaDetail:venusItemSkuPublishInDTO.getItemSaleAreaDetailList()){
					salesAreaDetail.setCreateId(venusItemSkuPublishInDTO.getOperatorId());
					salesAreaDetail.setCreateName(venusItemSkuPublishInDTO.getOperatorName());
					salesAreaDetail.setModifyId(venusItemSkuPublishInDTO.getOperatorId());
					salesAreaDetail.setModifyName(venusItemSkuPublishInDTO.getOperatorName());
					salesAreaDetail.setSalesAreaId(salesAreaId);
				}
				//先删除
				itemSalesAreaDetailMapper.deleteBySalesAreaId(salesAreaId);
				//再批量插入
				itemSalesAreaDetailMapper.batchInsertSalesAreaDetail(venusItemSkuPublishInDTO.getItemSaleAreaDetailList());
			}
			
		}
	}

	private void dealWithItemSkuPublishInfo(
			VenusItemSkuPublishInDTO venusItemSkuPublishInDTO,
			ItemSku itemSkuFromDb, ItemSkuPublishInfo itemSkuPublishInfoFromDb) {
		//插入
		if(itemSkuPublishInfoFromDb ==null){
			ItemSkuPublishInfo itemSkuPublishInfo=Converters.convert(venusItemSkuPublishInDTO, ItemSkuPublishInfo.class);
			if(itemSkuPublishInfo.getItemId()==null){
				itemSkuPublishInfo.setItemId(itemSkuFromDb.getItemId());
			}
			itemSkuPublishInfoMapper.insertSelective(itemSkuPublishInfo);
			return;
		}
		
		//更新
		ItemSkuPublishInfo itemSkuPublishInfo=Converters.convert(venusItemSkuPublishInDTO, ItemSkuPublishInfo.class);
		if(itemSkuPublishInfo.getId()==null){
			itemSkuPublishInfo.setId(itemSkuPublishInfoFromDb.getId());
		}
		if(itemSkuPublishInfo.getItemId()==null){
			itemSkuPublishInfo.setItemId(itemSkuPublishInfoFromDb.getItemId());
		}
		//要保留一些不应该更新的字段，比如createId等，我又给改回到数据库中的值；逻辑有点问题，但是减少了再来一遍set
		itemSkuPublishInfo.setCreateId(itemSkuPublishInfoFromDb.getCreateId());
		itemSkuPublishInfo.setCreateName(itemSkuPublishInfoFromDb.getCreateName());
		itemSkuPublishInfo.setCreateTime(itemSkuPublishInfoFromDb.getCreateTime());
		
		itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
	}
	
	private ExecuteResult<String> validatePublishData(VenusItemSkuPublishInDTO venusItemSkuPublishInDTO){
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(venusItemSkuPublishInDTO==null||venusItemSkuPublishInDTO.getSkuId()==null||venusItemSkuPublishInDTO.getSkuId()<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusItemSkuPublishInDTO")));
			return result;
		}
		
		ValidateResult va1lidateResult=DTOValidateUtil.validate(venusItemSkuPublishInDTO);
		
		if(!va1lidateResult.isPass()){
			result.setCode(ErrorCodes.E10006.name());
			result.setErrorMessages(Lists.newArrayList(StringUtils.split(va1lidateResult.getMessage(),DTOValidateUtil.ERROR_MSG_SEPERATOR)));
			return  result;
		}
		//校验销售区域
		if(venusItemSkuPublishInDTO.getItemSaleArea() != null && !(1==venusItemSkuPublishInDTO.getItemSaleArea().getIsSalesWholeCountry())){
			if(venusItemSkuPublishInDTO.getItemSaleArea()!=null&&CollectionUtils.isEmpty(venusItemSkuPublishInDTO.getItemSaleAreaDetailList())){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("saleAreaDetail")));
				return  result;
			}
			
			if(venusItemSkuPublishInDTO.getItemSaleArea()==null&&CollectionUtils.isNotEmpty(venusItemSkuPublishInDTO.getItemSaleAreaDetailList())){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("SaleArea")));
				return  result;
			}
		}
		
		if(venusItemSkuPublishInDTO.getItemSaleArea() != null){
			StringBuilder errorMsgSb=new StringBuilder();
			if(venusItemSkuPublishInDTO.getItemSaleArea().getIsBoxFlag()==null){
				errorMsgSb.append("isBoxFlag ");
			}
			if(venusItemSkuPublishInDTO.getItemSaleArea().getIsSalesWholeCountry()==null){
				errorMsgSb.append("isSalesWholeCountry ");
			}
			if(venusItemSkuPublishInDTO.getItemSaleArea().getItemId()==null){
				errorMsgSb.append("ItemId ");
			}
//			if(venusItemSkuPublishInDTO.getItemSaleArea().getSaleAreaType()==null){
//				errorMsgSb.append("SaleAreaType ");
//			}
			if(StringUtils.isNotEmpty(errorMsgSb.toString())){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg(errorMsgSb.toString())));
				return result;
			}
			
		}
		
		//校验价格
		if(venusItemSkuPublishInDTO.getStandardPrice()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("StandardPrice")));
			return result;
		}
		
		if(venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("ItemSkuBasePrice")));
			return result;
		}
		//零售价格
		if(venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice().getRetailPrice()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("RetailPrice")));
			return result;
		}
		//包厢销售价格
		if("1".equals(venusItemSkuPublishInDTO.getShelfType())&&
				venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice().getBoxSalePrice()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("BoxSalePrice")));
			return result;
		}
		//区域销售价格
		if("2".equals(venusItemSkuPublishInDTO.getShelfType())&&
				venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice().getAreaSalePrice()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("AreaSalePrice")));
			return result;
		}
		
		//汇掌柜价格
		if(venusItemSkuPublishInDTO.getPreSaleFlag() != null && (1 == venusItemSkuPublishInDTO.getPreSaleFlag()) && venusItemSkuPublishInDTO.getStandardPrice()!=null){
			HzgPriceDTO hzgPriceDTO=venusItemSkuPublishInDTO.getStandardPrice().getHzgPriceDTO();
			if(hzgPriceDTO==null){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("StandardPrice::HzgPriceDTO")));
				return result;
			}
			
			if(hzgPriceDTO.getRetailPrice()==null&&hzgPriceDTO.getSalePrice()==null&&hzgPriceDTO.getVipPrice()==null){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("StandardPrice::HzgPriceDTO")));
				return result;
			}
		}
		
		if(venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice().getSellerId()==null||
				venusItemSkuPublishInDTO.getStandardPrice().getItemSkuBasePrice().getShopId()==null){
			result.setCode(VenusErrorCodes.E1040014.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("Base Price sellerid or shopid is null")));
			return result;
		}
		
		boolean isPriceParamWrong=false;
		if(CollectionUtils.isNotEmpty(venusItemSkuPublishInDTO.getStandardPrice().getAreaPriceList())){
			for(InnerItemSkuPrice innerItemSkuPrice:venusItemSkuPublishInDTO.getStandardPrice().getAreaPriceList()){
				if(!PriceConstants.PRICE_TYPE_OF_AREA.equals(innerItemSkuPrice.getPriceType())){
					logger.info("innerItemSkuPrice AreaPrice PriceType is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(StringUtils.isEmpty(innerItemSkuPrice.getAreaCode())){
					logger.info("innerItemSkuPrice AreaPrice AreaCode is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getPrice()==null){
					logger.info("innerItemSkuPrice AreaPrice Price is not correct");
					isPriceParamWrong=true;
					break;
				}
				
				if(innerItemSkuPrice.getSellerId()==null||innerItemSkuPrice.getShopId()==null){
					logger.info("innerItemSkuPrice AreaPrice sellerid or shopid is null");
					isPriceParamWrong=true;
					break;
				}
			}
			if(isPriceParamWrong){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("AreaPrice")));
				return result;
			}
			
		}
		
		if(CollectionUtils.isNotEmpty(venusItemSkuPublishInDTO.getStandardPrice().getItemSkuMemberGroupPriceList())){
			for(InnerItemSkuPrice innerItemSkuPrice:venusItemSkuPublishInDTO.getStandardPrice().getItemSkuMemberGroupPriceList()){
				if(!PriceConstants.PRICE_TYPE_OF_MEMBER_GROUP.equals(innerItemSkuPrice.getPriceType())){
					logger.info("innerItemSkuPrice MemberGroup PriceType is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getGroupId()==null){
					logger.info("innerItemSkuPrice MemberGroup AreaCode is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getPrice()==null){
					logger.info("innerItemSkuPrice MemberGroup Price is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getSellerId()==null||innerItemSkuPrice.getShopId()==null){
					logger.info("innerItemSkuPrice AreaPrice sellerid or shopid is null");
					isPriceParamWrong=true;
					break;
				}
			}
			if(isPriceParamWrong){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("MemberGroup")));
				return result;
			}
		
		}
		
		if(CollectionUtils.isNotEmpty(venusItemSkuPublishInDTO.getStandardPrice().getItemSkuMemberLevelPriceList())){
			for(InnerItemSkuPrice innerItemSkuPrice:venusItemSkuPublishInDTO.getStandardPrice().getItemSkuMemberLevelPriceList()){
				if(!PriceConstants.PRICE_TYPE_OF_MEMBER_LEVEL.equals(innerItemSkuPrice.getPriceType())){
					logger.info("innerItemSkuPrice MemberLevel PriceType is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getBuyerGrade()==null){
					logger.info("innerItemSkuPrice MemberLevel BuyerGrade is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getPrice()==null){
					logger.info("innerItemSkuPrice MemberLevel Price is not correct");
					isPriceParamWrong=true;
					break;
				}
				if(innerItemSkuPrice.getSellerId()==null||innerItemSkuPrice.getShopId()==null){
					logger.info("innerItemSkuPrice AreaPrice sellerid or shopid is null");
					isPriceParamWrong=true;
					break;
				}
			}
			if(isPriceParamWrong){
				result.setCode(VenusErrorCodes.E1040014.name());
				result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040014.getErrorMsg("MemberLevel")));
				return result;
			}
			
		}
		return result;
	}
	
	@Transactional
	@Override
	public ExecuteResult<String> txBatchSetItemSkuOnShelfStatus(
			VenusItemSetShelfStatusInDTO venusItemSetShelfStatusInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(StringUtils.isEmpty(venusItemSetShelfStatusInDTO.getShelfType())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("ShelftType")));
			return result;
		}
		
		if(StringUtils.isEmpty(venusItemSetShelfStatusInDTO.getSetStatusFlag())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("StatusFlag")));
			return result;
		}
		
		if(CollectionUtils.isEmpty(venusItemSetShelfStatusInDTO.getSkuIdList())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuIdList")));
			return result;
		}
		try{
			
			for(Long skuId:venusItemSetShelfStatusInDTO.getSkuIdList()){
				
				if(skuId == null || skuId<=0){
					logger.info("txBatchSetItemSkuOnShelfStatus skuId is null or is less zero");
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
					return result;
				}
				
				logger.info("txBatchSetItemSkuOnShelfStatus skuId is :"+skuId);
				
				ItemSku itemSku=itemSkuDAO.queryById(skuId);
				
				if(itemSku ==null){
					logger.info("txBatchSetItemSkuOnShelfStatus itemSku is null "+skuId);
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSku")));
					return result;
				}
				
				//基础数据验证
				Item item=itemMybatisDAO.queryItemByPk(itemSku.getItemId());
				if(item==null){
					logger.error("txBatchSetItemSkuOnShelfStatus item is null");
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("item")));
					return result;
				}
				//品牌
				if(item.getBrand()==null||item.getBrand()<=0){
					logger.error("txBatchSetItemSkuOnShelfStatus 品牌 is null");
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("品牌")));
					return result;
				}
				//品类
				if(item.getCid()==null||item.getCid()<=0){
					logger.error("txBatchSetItemSkuOnShelfStatus 品类 is null");
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("品类")));
					return result;
				}
				//单位
				if(StringUtils.isEmpty(item.getWeightUnit())){
					logger.error("txBatchSetItemSkuOnShelfStatus 单位 is null");
					result.setCode(ErrorCodes.E10000.name());
					result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("单位")));
					return result;
				}
				
				QuerySkuPublishInfoDetailParamDTO skuPublishInfoDetailParamDTO=new QuerySkuPublishInfoDetailParamDTO();
				skuPublishInfoDetailParamDTO.setShelfType(venusItemSetShelfStatusInDTO.getShelfType());
				skuPublishInfoDetailParamDTO.setSkuId(skuId);
				//String shelfStatus=VenusItemSkuShelfStatusUtil.judgeItemSkuShelfStatus(itemSkuPublishInfoMapper.queryItemSkuShelfStatus(skuId));
				ItemSkuPublishInfo itemSkuPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, venusItemSetShelfStatusInDTO.getShelfType(),"0");
				
				//下架处理
				if("0".equals(venusItemSetShelfStatusInDTO.getSetStatusFlag())){
					//没有上架信息
					if(itemSkuPublishInfo==null||itemSkuPublishInfo.getIsVisable()==0){
						result.setCode(VenusErrorCodes.E1040017.name());
						result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040017.getErrorMsg(itemSku.getSkuCode()+"")));
						return result;
					}
					//修改为下架状态
					itemSkuPublishInfo.setIsVisable(0);
					itemSkuPublishInfo.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
					itemSkuPublishInfo.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
					itemSkuPublishInfo.setModifyTime(new Date());
					itemSkuPublishInfo.setInvisableTime(new Date());
					itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
					
					//处理item表的主状态,判断当前商品如果是非上架状态，则要修改为上架状态
					dealWithPublishItemStatus(itemSku.getSkuId(),"0",
							venusItemSetShelfStatusInDTO.getOperatorId(),venusItemSetShelfStatusInDTO.getOperatorName(), item);
				}
				
				ItemSalesArea itemSalesArea=null;
				List<ItemSalesDefaultArea> defaultSalesAreaList=null;
				//上架处理
				if("1".equals(venusItemSetShelfStatusInDTO.getSetStatusFlag())){
					//设置默认销售区域
					itemSalesArea=itemSalesAreaMapper.selectByItemId(itemSku.getItemId(),venusItemSetShelfStatusInDTO.getShelfType());
					if(itemSalesArea==null){
						//使用默认销售区域
						defaultSalesAreaList=itemSalesDefaultAreaMapper.selectDefaultSalesAreaBySellerId(itemSku.getSellerId());
						if(CollectionUtils.isEmpty(defaultSalesAreaList)){
							logger.error("默认销售区域不存在");
							result.setCode(ErrorCodes.E10000.name());
							result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg(itemSku.getSkuCode()+"")));
							return result;
						}
					}
					//设置及验证价格
					String shelfType = venusItemSetShelfStatusInDTO.getShelfType(); // TODO : 1 包厢 2 区域
					Integer isBoxFlag = shelfType.equals("1") ? 1 : 0;
					ExecuteResult<StandardPriceDTO> standardPrice=itemSkuPriceService.queryStandardPrice4InnerSeller(skuId, isBoxFlag);
					if(!standardPrice.isSuccess()||standardPrice.getResult()==null){
						logger.error("验证价格查询价格接口不成功");
						result.setCode(VenusErrorCodes.E1040018.name());
						result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040018.getErrorMsg(itemSku.getSkuCode()+"")));
						return result;
					}
					ItemSkuBasePrice itemSkuBasePrice=standardPrice.getResult().getItemSkuBasePrice();
					if(itemSkuBasePrice==null){
						logger.error("itemSkuBasePrice is null");
						result.setCode(ErrorCodes.E10000.name());
						result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSkuBasePrice")));
						return result;
					}
					
					if(itemSkuBasePrice.getRetailPrice()==null||itemSkuBasePrice.getRetailPrice().compareTo(BigDecimal.ZERO)<=0){
						logger.error("RetailPrice is null or is zero");
						result.setCode(ErrorCodes.E10000.name());
						result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg(skuId+" RetailPrice")));
						return result;
					}
					
					if(itemSkuPublishInfo==null){
						//先判断剩余可用库存
						Integer leftAvaliableStockQty=getLeftAvaliableStockQty(item.getItemId(),venusItemSetShelfStatusInDTO.getSupplierCode());
						
						Integer promotionQty=0;//需要获取促销占用的库存数据
						
						//查询促销锁定库存
						ExecuteResult<Integer> timelimitedInfoDTOResult=
								timelimitedInfoService.getSkuTimelimitedAllCount(MessageIdUtils.generateMessageId(), itemSku.getSkuCode());
						
						if(timelimitedInfoDTOResult!=null && timelimitedInfoDTOResult.isSuccess()&&
								timelimitedInfoDTOResult.getResult()!=null){
							promotionQty=timelimitedInfoDTOResult.getResult();
						}
						
						leftAvaliableStockQty=leftAvaliableStockQty-promotionQty;
						
						//获得另外一种上架模式数据
						ItemSkuPublishInfo anotherPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, shelfType.equals("1")?"2":"1","1");
						
						Integer anotherPublishInfoDisplayQty=0;
						if(anotherPublishInfo != null){
							anotherPublishInfoDisplayQty=anotherPublishInfo.getDisplayQuantity()==null?0:anotherPublishInfo.getDisplayQuantity();
						}
						
						leftAvaliableStockQty=leftAvaliableStockQty-anotherPublishInfoDisplayQty;
						
						//剩余库存不足
						if(leftAvaliableStockQty == null|| leftAvaliableStockQty<=0){
							continue; 
						}
						//执行首次上架，自动使用默认值进行上架
						itemSkuPublishInfo = new ItemSkuPublishInfo();
						itemSkuPublishInfo.setDisplayQuantity(leftAvaliableStockQty);
						itemSkuPublishInfo.setItemId(itemSku.getItemId());
						itemSkuPublishInfo.setSkuCode(itemSku.getSkuCode());
						itemSkuPublishInfo.setSkuId(itemSku.getSkuId());
						itemSkuPublishInfo.setErpSync(0);
						itemSkuPublishInfo.setIsBoxFlag("1".equals(venusItemSetShelfStatusInDTO.getShelfType()) ? 1 : 0);
						itemSkuPublishInfo.setVisableTime(new Date());
						itemSkuPublishInfo.setIsVisable(1);
						itemSkuPublishInfo.setMimQuantity(1);
						itemSkuPublishInfo.setIsPurchaseLimit(0);
						itemSkuPublishInfo.setCreateId(venusItemSetShelfStatusInDTO.getOperatorId());
						itemSkuPublishInfo.setCreateName(venusItemSetShelfStatusInDTO.getOperatorName());
						itemSkuPublishInfo.setCreateTime(new Date());
						itemSkuPublishInfo.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
						itemSkuPublishInfo.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
						itemSkuPublishInfo.setModifyTime(new Date());
						//入库
						itemSkuPublishInfoMapper.insert(itemSkuPublishInfo);
					}else{
						//如果已经是上架状态，则不处理
						if(itemSkuPublishInfo.getIsVisable()==1){
							continue;
						}
						//已经存在上下架数据
						Integer dispalyQty=itemSkuPublishInfo.getDisplayQuantity();
						
						
						if(dispalyQty!=null && dispalyQty>0&&null==venusItemSetShelfStatusInDTO.getAvaliableStockFlag()){
							boolean isQtyEnough=checkPublishDisplayQtyIsEnough(itemSkuPublishInfo.getDisplayQuantity(),itemSkuPublishInfo.getReserveQuantity(),
									skuId, venusItemSetShelfStatusInDTO.getShelfType(),item.getItemId(),
									venusItemSetShelfStatusInDTO.getSupplierCode(),itemSku.getSkuCode());
							if(!isQtyEnough){
								logger.error("库存不足");
								result.setCode(VenusErrorCodes.E1040019.name());
								result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040019.getErrorMsg(itemSku.getSkuCode())));
								return result;
							}
						}
						else{
							Integer leftAvaliableStockQty=getLeftAvaliableStockQty(item.getItemId(),venusItemSetShelfStatusInDTO.getSupplierCode());
							
							//获得另外一种上架模式数据
							ItemSkuPublishInfo anotherPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, shelfType.equals("1")?"2":"1","1");
							
							Integer anotherPublishInfoDisplayQty=0;
							if(anotherPublishInfo != null){
								anotherPublishInfoDisplayQty=anotherPublishInfo.getDisplayQuantity()==null?0:anotherPublishInfo.getDisplayQuantity();
							}
							
							leftAvaliableStockQty=leftAvaliableStockQty-anotherPublishInfoDisplayQty;
							
							if(leftAvaliableStockQty==null||leftAvaliableStockQty<=0){
								logger.error("库存不足");
								result.setCode(VenusErrorCodes.E1040019.name());
								result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040019.getErrorMsg(itemSku.getSkuCode())));
								return result;
							}
							Integer realPublishQty=leftAvaliableStockQty>dispalyQty?dispalyQty:leftAvaliableStockQty;
							//按照库存自动上下架，则直接用剩余库存全部
							if((itemSkuPublishInfo.getAutomaticVisableWithStock()!=null)
									&&(1==itemSkuPublishInfo.getAutomaticVisableWithStock())){
								realPublishQty=leftAvaliableStockQty;
							}
							
							if(realPublishQty==null||realPublishQty<=0){
								logger.error("库存不足");
								result.setCode(VenusErrorCodes.E1040019.name());
								result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040019.getErrorMsg(itemSku.getSkuCode()+",dispalyQty"+dispalyQty)));
								return result;
							}
							
							itemSkuPublishInfo.setDisplayQuantity(realPublishQty);
						}
						
						//设置默认限购
						if(itemSkuPublishInfo.getMimQuantity()==null){
							itemSkuPublishInfo.setMimQuantity(1);
						}
						if(itemSkuPublishInfo.getIsPurchaseLimit()==null){
							itemSkuPublishInfo.setIsPurchaseLimit(0);
						}
						
						//修改为上架状态
						itemSkuPublishInfo.setIsVisable(1);
						itemSkuPublishInfo.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
						itemSkuPublishInfo.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
						itemSkuPublishInfo.setModifyTime(new Date());
						itemSkuPublishInfo.setVisableTime(new Date());
						itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
						
					}
					
					//
					//设置默认销售区域
					if(itemSalesArea==null){
						//使用默认销售区域
						if(CollectionUtils.isNotEmpty(defaultSalesAreaList)){
							itemSalesArea=new ItemSalesArea();
							itemSalesArea.setCreateId(venusItemSetShelfStatusInDTO.getOperatorId());
							itemSalesArea.setCreateName(venusItemSetShelfStatusInDTO.getOperatorName());
							itemSalesArea.setCreateTime(new Date());
							if("1".equals(venusItemSetShelfStatusInDTO.getShelfType())){
								itemSalesArea.setIsBoxFlag(1);
							}
							itemSalesArea.setItemId(itemSku.getItemId());
							itemSalesArea.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
							itemSalesArea.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
							itemSalesArea.setModifyTime(new Date());
							itemSalesAreaMapper.insertSelective(itemSalesArea);
							
							List<ItemSalesAreaDetail> itemSalesAreaDetailList=Lists.newArrayList();
							for(ItemSalesDefaultArea itemSalesDefaultArea:defaultSalesAreaList){
								ItemSalesAreaDetail itemSalesAreaDetail=new ItemSalesAreaDetail();
								itemSalesAreaDetail.setAreaCode(itemSalesDefaultArea.getAreaCode());
								itemSalesAreaDetail.setCreateId(venusItemSetShelfStatusInDTO.getOperatorId());
								itemSalesAreaDetail.setCreateName(venusItemSetShelfStatusInDTO.getOperatorName());
								itemSalesAreaDetail.setCreateTime(new Date());
								itemSalesAreaDetail.setItemId(itemSku.getItemId());
								itemSalesAreaDetail.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
								itemSalesAreaDetail.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
								itemSalesAreaDetail.setModifyTime(new Date());
								itemSalesAreaDetail.setSalesAreaId(itemSalesArea.getSalesAreaId());
								itemSalesAreaDetail.setSalesAreaType(Constants.SALES_AREA_TYPE_OF_DISTRICT);
								itemSalesAreaDetailList.add(itemSalesAreaDetail);
							}
							itemSalesAreaDetailMapper.batchInsertSalesAreaDetail(itemSalesAreaDetailList);
						}
					}
					
					boolean isSalePriceChanged=false;
					if("1".equals(venusItemSetShelfStatusInDTO.getShelfType())){
						if(itemSkuBasePrice.getBoxSalePrice()==null
								||itemSkuBasePrice.getRetailPrice().compareTo(itemSkuBasePrice.getBoxSalePrice()) < 0){
							itemSkuBasePrice.setBoxSalePrice(itemSkuBasePrice.getRetailPrice());
							isSalePriceChanged=true;
						}
					}
					else{
						if(itemSkuBasePrice.getAreaSalePrice()==null
								||itemSkuBasePrice.getRetailPrice().compareTo(itemSkuBasePrice.getAreaSalePrice()) < 0){
							itemSkuBasePrice.setAreaSalePrice(itemSkuBasePrice.getRetailPrice());
							isSalePriceChanged=true;
						}
					}
					//更新基础价格
					if(isSalePriceChanged==true){
						itemSkuBasePrice.setModifyId(venusItemSetShelfStatusInDTO.getOperatorId());
						itemSkuBasePrice.setModifyName(venusItemSetShelfStatusInDTO.getOperatorName());
						itemSkuBasePrice.setModifyTime(new Date());
						itemSkuPriceService.updateItemSkuBasePrice(itemSkuBasePrice);
					}
					
					//处理item表的主状态,判断当前商品如果是非上架状态，则要修改为上架状态
					dealWithPublishItemStatus(itemSku.getSkuId(),"1",
							venusItemSetShelfStatusInDTO.getOperatorId(),venusItemSetShelfStatusInDTO.getOperatorName(), item);
				}
				
			}
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::txBatchSetItemSkuOnShelfStatus:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}
	private Integer getLeftAvaliableStockQty(Long itemId,String supplierCode){
		if(itemId==null||StringUtils.isEmpty(supplierCode)){
			return 0;
		}
		Item item=itemMybatisDAO.queryItemByPk(itemId);
		if(item==null){
			return 0;
		}
		ItemSpu spu=itemSpuMapper.selectByPrimaryKey(item.getItemSpuId());
		
		if(spu==null||StringUtils.isEmpty(spu.getSpuCode())){
			return 0;
		}
		//先查询实际总库存
		ItemStockResponseDTO itemStockResponseDTO=MiddlewareInterfaceUtil.getSingleItemStock(supplierCode, spu.getSpuCode());
		//if less zero， then zero
		Integer totalStock = (itemStockResponseDTO==null||itemStockResponseDTO.getStoreNum()<=0) ? 0 : itemStockResponseDTO.getStoreNum();
		return totalStock;
	}
	
	private boolean checkPublishDisplayQtyIsEnough(Integer displayQuantity,Integer reserveQuantity,Long skuId,String shelfType,Long itemId
			,String supplierCode,String skuCode){
		if(displayQuantity==null||displayQuantity<=0||StringUtils.isEmpty(skuCode)){
			return false;
		}
		//获取实际库存
		Integer realStockQty=getLeftAvaliableStockQty(itemId,supplierCode);
		//上架可见数大于实际库存
		if(displayQuantity>realStockQty){
			return false;
		}
		//上架可见数小于锁定数
		if(displayQuantity<reserveQuantity){
			return false;
		}
		//获得另外一种上架模式数据
		ItemSkuPublishInfo anotherPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, shelfType.equals("1")?"2":"1","1");
		
		Integer anotherPublishInfoDisplayQty=0;
		if(anotherPublishInfo != null){
			anotherPublishInfoDisplayQty=anotherPublishInfo.getDisplayQuantity()==null?0:anotherPublishInfo.getDisplayQuantity();
		}
		
		Integer promotionQty=0;//需要获取促销占用的库存数据
		
		//查询促销锁定库存
		ExecuteResult<Integer> timelimitedInfoDTOResult=
				timelimitedInfoService.getSkuTimelimitedAllCount(MessageIdUtils.generateMessageId(), skuCode);
		
		if(timelimitedInfoDTOResult!=null && timelimitedInfoDTOResult.isSuccess()&&
				timelimitedInfoDTOResult.getResult()!=null){
			promotionQty=timelimitedInfoDTOResult.getResult();
		}
		
		//计算最终能够上架的库存数
		Integer qty=realStockQty-anotherPublishInfoDisplayQty-promotionQty;
		//大于实际可用库存
		if(displayQuantity>qty){
			return false;
		}
		return true;
	}
	
	
	@Transactional
	@Override
	public ExecuteResult<String> txBatchDeleteItemSku(
			VenusBatchDeleteItemSkuInDTO venusBatchDeleteItemSkuInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(venusBatchDeleteItemSkuInDTO ==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusBatchDeleteItemSkuInDTO")));
			return result;
		}
		if(CollectionUtils.isEmpty(venusBatchDeleteItemSkuInDTO.getSkuIdList())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuIdList")));
			return result;
		}
		if(venusBatchDeleteItemSkuInDTO.getOperatorId()==null||venusBatchDeleteItemSkuInDTO.getOperatorId()<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("OperatorId")));
			return result;
		}
		if(StringUtils.isEmpty(venusBatchDeleteItemSkuInDTO.getOperatorName())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("OperatorName")));
			return result;
		}
		try{
			itemMybatisDAO.batchUpdateHtdItemStatus(venusBatchDeleteItemSkuInDTO.getSkuIdList(), "", HtdItemStatusEnum.DELETED.getCode(),
					venusBatchDeleteItemSkuInDTO.getOperatorId(),  venusBatchDeleteItemSkuInDTO.getOperatorName());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::txBatchDeleteItemSku:"+e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Deprecated
	@Override
	public ExecuteResult<DataGrid<VenusItemMainDataOutDTO>> queryItemMainDataList(
			VenusItemMainDataInDTO venusItemSpuInDTO,Pager<String> page) {
		ExecuteResult<DataGrid<VenusItemMainDataOutDTO>> result=new ExecuteResult<DataGrid<VenusItemMainDataOutDTO>>();
		if(venusItemSpuInDTO==null){
			result.setCode(VenusErrorCodes.E1040009.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
			return result;
		}
		try{
			Long totalCount=itemSkuDAO.queryItemMainDataForVenusCount(venusItemSpuInDTO);
			if(totalCount==null||totalCount<=0){
				result.setCode(ErrorCodes.SUCCESS.name());
				DataGrid<VenusItemMainDataOutDTO> dataGrid=new DataGrid<VenusItemMainDataOutDTO>();
				result.setResult(dataGrid);
				return result;
			}
			page.setTotalCount(totalCount.intValue());
			venusItemSpuInDTO.setStart((page.getPage()-1) * page.getRows());
			venusItemSpuInDTO.setPageSize(page.getRows());
			List<VenusItemMainDataOutDTO> list=itemSkuDAO.queryItemMainDataForVenus(venusItemSpuInDTO);
			DataGrid<VenusItemMainDataOutDTO> dataGrid=new DataGrid<VenusItemMainDataOutDTO>(list);
			dataGrid.setTotal(totalCount);
			dataGrid.setPageSize(page.getRows());
			result.setResult(dataGrid);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemMainDataList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	@Override
	public ExecuteResult<VenusOrderItemSkuDetailOutDTO> queryOrderItemSkuDetail(
			String itemSkuCode,String shelfType) {
		ExecuteResult<VenusOrderItemSkuDetailOutDTO> result=new ExecuteResult<VenusOrderItemSkuDetailOutDTO>();
		if(StringUtils.isEmpty(itemSkuCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSkuCode")));
			return result;
		}
		try{
			ItemSku itemSku=itemSkuDAO.selectItemSkuBySkuCode(itemSkuCode);
			if(itemSku==null || itemSku.getSkuId()==null){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSku")));
				return result;
			}
			VenusOrderItemSkuDetailOutDTO venusOrderItemSkuDetailOutDTO=itemSkuDAO.queryVenusOrderItemSkuDetail(itemSku.getSkuId());
			
			if(venusOrderItemSkuDetailOutDTO==null){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusOrderItemSkuDetailOutDTO")));
				return result;
			}
			
			ItemSpu spu=itemSpuMapper.selectById(venusOrderItemSkuDetailOutDTO.getItemSpuId());
			if(spu!=null){
				venusOrderItemSkuDetailOutDTO.setItemSpuCode(spu.getSpuCode());
			}
			//补足一级和二级目录
			 makeUpFirstAndSecondCat(venusOrderItemSkuDetailOutDTO);
			//查询图片
			List<ItemPicture> pictures=itemPictureDAO.queryItemPicsById(venusOrderItemSkuDetailOutDTO.getItemId());
			if(CollectionUtils.isNotEmpty(pictures)){
				for(ItemPicture p:pictures){
					if(p.getIsFirst() == 1){
						venusOrderItemSkuDetailOutDTO.setPictureUrl(p.getPictureUrl());
						break;
					}
				}
			}
			//查询价格
			ExecuteResult<ItemSkuBasePriceDTO> priceResult=itemSkuPriceService.queryItemSkuBasePrice(itemSku.getSkuId());
			if(priceResult.isSuccess()){
				venusOrderItemSkuDetailOutDTO.setItemSkuBasePrice(priceResult.getResult());
			}
			
			//上架信息
			ItemSkuPublishInfo itemSkuPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSku.getSkuId(), shelfType, null);
			venusOrderItemSkuDetailOutDTO.setItemSkuPublishInfo(itemSkuPublishInfo);
			
			result.setResult(venusOrderItemSkuDetailOutDTO);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryOrderItemSkuDetail:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	private void makeUpFirstAndSecondCat(VenusOrderItemSkuDetailOutDTO venusOrderItemSkuDetailOutDTO) {
		ExecuteResult<List<ItemCatCascadeDTO>> catResult=itemCategoryService.queryParentCategoryList(venusOrderItemSkuDetailOutDTO.getThirdCatId());
		if(!catResult.isSuccess()){
			return;
		}
		List<ItemCatCascadeDTO> itemCatCascadeList=catResult.getResult();
		
		if(CollectionUtils.isEmpty(itemCatCascadeList)){
			return;
		}

		 ItemCatCascadeDTO firstCat= itemCatCascadeList.get(0);
		 if(firstCat==null){
			 return;
		 }

		 venusOrderItemSkuDetailOutDTO.setFirstCatId(firstCat.getCid());
		 venusOrderItemSkuDetailOutDTO.setFirstCatName(firstCat.getCname());
		 
		 List<ItemCatCascadeDTO> childCatCascadeList =firstCat.getChildCats();
		 
		 if(CollectionUtils.isEmpty(childCatCascadeList)){
			 return;
		 }

		 ItemCatCascadeDTO secondCat =childCatCascadeList.get(0);
		
		 if(secondCat == null){
			return;
		 }
	 
		 venusOrderItemSkuDetailOutDTO.setSecondCatId(secondCat.getCid());
		 venusOrderItemSkuDetailOutDTO.setSecondCatName(secondCat.getCname());
	 
	}

	@Override
	public ExecuteResult<String> queryVenusStockItemList(VenusStockItemInDTO venusStockItemInDTO) {
		
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		try{
			
			String param = "1".equals(venusStockItemInDTO.getIsAgreement())?
					buildQueryAgreementParam(venusStockItemInDTO):buildQueryStockParam(venusStockItemInDTO);
					
			String url="1".equals(venusStockItemInDTO.getIsAgreement())?
					MiddlewareInterfaceConstant.MIDDLEWARE_GET_SPECIAL_AGGREE_ITEM_URL:MiddlewareInterfaceConstant.MIDDLEWARE_GET_SPECIAL_ITEM_URL;
			
			String response=MiddlewareInterfaceUtil.httpGet(url+param,true);
			
			Map map=(Map)JSONObject.toBean(JSONObject.fromObject(response),Map.class);
			
			if(MapUtils.isNotEmpty(map)&&MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(map.get("code")+"")){
				Map<String,Object> mapData=(Map)JSONObject.toBean(JSONObject.fromObject(map.get("data")),Map.class);
				JSONArray storeList=JSONArray.fromObject(mapData.get("storeList"));
				List<QuerySpecialItemOutDTO> querySpecialItemList=storeList.toList(storeList,QuerySpecialItemOutDTO.class );
				
				List<String> erpCodeList=extractSpuCode(querySpecialItemList);
				
				if(CollectionUtils.isNotEmpty(erpCodeList)){
					List<Map<String,Object>> resultMapList=itemMybatisDAO.batchQueryItemCodeBySpuCodeAndSellerId(venusStockItemInDTO.getSupplierId(), erpCodeList);
					
					if(CollectionUtils.isNotEmpty(resultMapList)){
						
						for(QuerySpecialItemOutDTO querySpecialItemOutDTO:querySpecialItemList){
							if(StringUtils.isEmpty(querySpecialItemOutDTO.getErpProductCode())){
								continue;
							}
							for(Map<String,Object> mapObj:resultMapList){
								if(mapObj.get("erpCode")!=null&&querySpecialItemOutDTO.getErpProductCode().equals(mapObj.get("erpCode")+"")){
									querySpecialItemOutDTO.setProductCode(mapObj.get("itemCode")+"");
									if(StringUtils.isNumeric(mapObj.get("cid")+"")){
									    ExecuteResult<Map<String, Object>> catResult=itemCategoryService.queryItemOneTwoThreeCategoryName(Long.valueOf(mapObj.get("cid")+""), "-");
										if(catResult!=null&&catResult.isSuccess()&&MapUtils.isNotEmpty(catResult.getResult())){
											  querySpecialItemOutDTO.setCatName(catResult.getResult().get("categoryName")+"");
										}
									 
									}
								
									break;
								}
							}
							
						}
						
						mapData.put("storeList", querySpecialItemList);
						map.put("data",mapData);
					}
					
				}
				
			}
			result.setCode(ErrorCodes.SUCCESS.name());
			System.out.println(JSONObject.fromObject(map).toString());
			result.setResult(JSONObject.fromObject(map).toString());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryVenusStockItemList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		
		return result;
	}
	
	 private String buildQueryStockParam(VenusStockItemInDTO querySpecialItemInDTO){
		   StringBuilder sb=new StringBuilder("?");
		   sb.append("supplierCode="+querySpecialItemInDTO.getSupplierCode());
		   sb.append("&isAgreement="+querySpecialItemInDTO.getIsAgreement());
		   sb.append("&pageCount="+querySpecialItemInDTO.getPageCount());
		   sb.append("&pageIndex="+querySpecialItemInDTO.getPageIndex());
		   if(querySpecialItemInDTO.getCanSellNum()==null){
			   querySpecialItemInDTO.setCanSellNum("0"); 
		   }
		   sb.append("&canSellNum="+querySpecialItemInDTO.getCanSellNum());
//		   if(querySpecialItemInDTO.getDistributionNum()==null){
//			   querySpecialItemInDTO.setDistributionNum("0"); 
//		   }
//		   sb.append("&distributionNum="+querySpecialItemInDTO.getDistributionNum());
//		   if(querySpecialItemInDTO.getSurplusNum()==null){
//			   querySpecialItemInDTO.setSurplusNum("0");
//		   }
//		   sb.append("&surplusNum="+querySpecialItemInDTO.getSurplusNum());
		   //productName
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getProductName())){
			   if(StringUtils.isNumeric(querySpecialItemInDTO.getProductName())){
				   sb.append("&productCode="+querySpecialItemInDTO.getProductName());
			   }else{
				   sb.append("&productName="+querySpecialItemInDTO.getProductName());
			   }
		   }
		   
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getCategroyName())){
			   sb.append("&categroyName="+querySpecialItemInDTO.getCategroyName());
		   }
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getBrandName())){
			   sb.append("&brandName="+querySpecialItemInDTO.getBrandName());
		   }
		   sb.append("&hasPage=1");
		   sb.append("&token="+MiddlewareInterfaceUtil.getAccessToken());
		   return sb.toString();
	   }
	 
	
	   private String buildQueryAgreementParam(VenusStockItemInDTO querySpecialItemInDTO){
		   StringBuilder sb=new StringBuilder("?");
		   sb.append("supplierCode="+querySpecialItemInDTO.getSupplierCode());
//		   sb.append("&isAgreement="+querySpecialItemInDTO.getIsAgreement());
		   sb.append("&pageCount="+querySpecialItemInDTO.getPageCount());
		   sb.append("&pageIndex="+querySpecialItemInDTO.getPageIndex());
//		   if(querySpecialItemInDTO.getCanSellNum()==null){
//			   querySpecialItemInDTO.setCanSellNum("0"); 
//		   }
//		   sb.append("&canSellNum="+querySpecialItemInDTO.getCanSellNum());
		   if(querySpecialItemInDTO.getDistributionNum()==null){
			   querySpecialItemInDTO.setDistributionNum("0"); 
		   }
		   sb.append("&distributionNum="+querySpecialItemInDTO.getDistributionNum());
		   if(querySpecialItemInDTO.getSurplusNum()==null){
			   querySpecialItemInDTO.setSurplusNum("0");
		   }
		   sb.append("&surplusNum="+querySpecialItemInDTO.getSurplusNum());
		   //productName
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getProductName())){
			   if(StringUtils.isNumeric(querySpecialItemInDTO.getProductName())){
				   sb.append("&productCode="+querySpecialItemInDTO.getProductName());
			   }else{
				   sb.append("&productName="+querySpecialItemInDTO.getProductName());
			   }
		   }
		   
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getCategroyName())){
			   sb.append("&categroyName="+querySpecialItemInDTO.getCategroyName());
		   }
		   if(StringUtils.isNotEmpty(querySpecialItemInDTO.getBrandName())){
			   sb.append("&brandName="+querySpecialItemInDTO.getBrandName());
		   }
		   sb.append("&hasPage=1");
		   sb.append("&token="+MiddlewareInterfaceUtil.getAccessToken());
		   return sb.toString();
	   }


	@Override
	public ExecuteResult<String> queryVenusAggrementItemList(VenusStockItemInDTO venusStockItemInDTO) {
		return queryVenusStockItemList(venusStockItemInDTO);
	}
	
	private List<String> extractSpuCode(List<QuerySpecialItemOutDTO> querySpecialItemList){
		List<String> erpCodeList=Lists.newArrayList();
		
		if(CollectionUtils.isEmpty(querySpecialItemList)){
			return erpCodeList;
		}
		
		for(QuerySpecialItemOutDTO querySpecialItemOutDTO:querySpecialItemList){
			if(StringUtils.isEmpty(querySpecialItemOutDTO.getErpProductCode())){
				continue;
			}
			erpCodeList.add(querySpecialItemOutDTO.getErpProductCode());
		}
		
		return erpCodeList;
	}
	
	@Transactional
	@Override
	public ExecuteResult<String> txBatchSetItemSkuAutoOnShelfStatus(
			VenusItemSkuAutoShelfStatusInDTO venusItemSkuAutoShelfStatusInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		//校验参数
		if(venusItemSkuAutoShelfStatusInDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusItemSetShelfStatusInDTO")));
			return result;
		}
		
		ValidateResult validateResult=DTOValidateUtil.validate(VenusItemSkuAutoShelfStatusInDTO.class);
		if(!validateResult.isPass()){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(validateResult.getMessage()));
			return result;
		}
		try{
			for(Long skuId:venusItemSkuAutoShelfStatusInDTO.getSkuIdList()){
				VenusOrderItemSkuDetailOutDTO venusItemSkuDetailOutDTO=itemSkuDAO.queryVenusOrderItemSkuDetail(skuId);
				if(venusItemSkuDetailOutDTO==null){
					continue;
				}
				//根据skuid去查询
				ItemSkuPublishInfo itemSkuPublishInfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId,venusItemSkuAutoShelfStatusInDTO.getShelfType(),null);
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				//不存在，则创建
				if(itemSkuPublishInfo==null){
					itemSkuPublishInfo=new ItemSkuPublishInfo();
					itemSkuPublishInfo.setSkuId(skuId);
					itemSkuPublishInfo.setItemId(venusItemSkuDetailOutDTO.getItemId());
					itemSkuPublishInfo.setIsBoxFlag(venusItemSkuAutoShelfStatusInDTO.getShelfType().equals("1") ? 1:0);
					itemSkuPublishInfo.setSkuCode(venusItemSkuDetailOutDTO.getSkuCode());
					itemSkuPublishInfo.setIsAutomaticVisable(1);
					if(venusItemSkuAutoShelfStatusInDTO.getSortByStockStatus()!=null){
						itemSkuPublishInfo.setAutomaticVisableWithStock(Integer.parseInt(venusItemSkuAutoShelfStatusInDTO.getSortByStockStatus()));
					}
					
					try{
						if(venusItemSkuAutoShelfStatusInDTO.getUpShelfTime()!=null){
							itemSkuPublishInfo.setAutomaticStarttime(sdf.parse(venusItemSkuAutoShelfStatusInDTO.getUpShelfTime()));
						}
						if(venusItemSkuAutoShelfStatusInDTO.getDownShelfTime()!=null){
							itemSkuPublishInfo.setAutomaticEndtime(sdf.parse(venusItemSkuAutoShelfStatusInDTO.getDownShelfTime()));
						}
					}catch(Exception e){
						logger.error("VenusItemExportServiceImpl::txBatchSetItemSkuAutoOnShelfStatus",e);
					}
					itemSkuPublishInfo.setCreateId(venusItemSkuAutoShelfStatusInDTO.getOperatorId());
					itemSkuPublishInfo.setCreateTime(new Date());
					itemSkuPublishInfo.setCreateName(venusItemSkuAutoShelfStatusInDTO.getOperatorName());
					itemSkuPublishInfo.setModifyId(venusItemSkuAutoShelfStatusInDTO.getOperatorId());
					itemSkuPublishInfo.setModifyTime(new Date());
					itemSkuPublishInfo.setModifyName(venusItemSkuAutoShelfStatusInDTO.getOperatorName());
					itemSkuPublishInfoMapper.insertSelective(itemSkuPublishInfo);
					
					//itemMybatisDAO.updateItemModifyTimeByItemId(venusItemSkuDetailOutDTO.getItemId(),1);
				}
				else{//存在，则更新
					itemSkuPublishInfo.setIsAutomaticVisable(1);
					if(itemSkuPublishInfo.getAutomaticVisableWithStock()!=null){
						itemSkuPublishInfo.setAutomaticVisableWithStock(Integer.parseInt(venusItemSkuAutoShelfStatusInDTO.getSortByStockStatus()));
					}
					
					try{
						if(venusItemSkuAutoShelfStatusInDTO.getUpShelfTime()!=null){
							itemSkuPublishInfo.setAutomaticStarttime(sdf.parse(venusItemSkuAutoShelfStatusInDTO.getUpShelfTime()));
						}
						if(venusItemSkuAutoShelfStatusInDTO.getDownShelfTime()!=null){
							itemSkuPublishInfo.setAutomaticEndtime(sdf.parse(venusItemSkuAutoShelfStatusInDTO.getDownShelfTime()));
						}
					}catch(Exception e){
						logger.error("VenusItemExportServiceImpl::txBatchSetItemSkuAutoOnShelfStatus:",e);
					}
					itemSkuPublishInfo.setModifyId(venusItemSkuAutoShelfStatusInDTO.getOperatorId());
					itemSkuPublishInfo.setModifyTime(new Date());
					itemSkuPublishInfo.setModifyName(venusItemSkuAutoShelfStatusInDTO.getOperatorName());
					itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
					//itemMybatisDAO.updateItemModifyTimeByItemId(venusItemSkuDetailOutDTO.getItemId(),1);
				}
			}
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::txBatchSetItemSkuAutoOnShelfStatus:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<List<ItemSalesDefaultArea>> queryDefaultSalesAreaList(Long sellerId) {
		ExecuteResult<List<ItemSalesDefaultArea>> result=new ExecuteResult<List<ItemSalesDefaultArea>>();
		if(sellerId==null||sellerId<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("sellerId")));
		}
		try{
			List<ItemSalesDefaultArea> list=itemSalesDefaultAreaMapper.selectDefaultSalesAreaBySellerId(sellerId);
			result.setResult(list);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryDefaultSalesAreaList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	@Transactional
	@Override
	public ExecuteResult<String> saveDefaultSalesArea(List<ItemSalesDefaultArea> itemSalesDefaultAreaList) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(CollectionUtils.isEmpty(itemSalesDefaultAreaList)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSalesDefaultAreaList")));
		}
		try{
			//先做删除
			itemSalesDefaultAreaMapper.deleteBySellerId(itemSalesDefaultAreaList.get(0).getSellerId());
			
			for(ItemSalesDefaultArea itemSalesDefaultArea:itemSalesDefaultAreaList){
				itemSalesDefaultAreaMapper.insertSelective(itemSalesDefaultArea);
			}
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::saveDefaultSalesArea:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<List<VenusQueryDropdownItemListOutDTO>> queryDropdownItemList(
			VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO) {
		ExecuteResult<List<VenusQueryDropdownItemListOutDTO>> result=new ExecuteResult<List<VenusQueryDropdownItemListOutDTO>>();
		if(venusQueryDropdownItemInDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusQueryDropdownItemInDTO")));
			return result;
		}
		
		if(venusQueryDropdownItemInDTO.getBrandId()==null||venusQueryDropdownItemInDTO.getCatId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("brandId or catId")));
			return result;
		}
		

		if(venusQueryDropdownItemInDTO.getSellerId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("sellerId")));
			return result;
		}
		try{
			List<VenusQueryDropdownItemListOutDTO> venusQueryDropdownItemList=itemMybatisDAO.selectItemInfoByCatAndBrandId(venusQueryDropdownItemInDTO);
			result.setResult(venusQueryDropdownItemList);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryDropdownItemList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}

	@Override
	public ExecuteResult<VenusQueryDropdownItemDetailOutDTO> queryDropdownItemDetail(
			VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO) {

		ExecuteResult<VenusQueryDropdownItemDetailOutDTO> result=new ExecuteResult<VenusQueryDropdownItemDetailOutDTO>();

		if(venusQueryDropdownItemInDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("venusQueryDropdownItemInDTO")));
			return result;
		}

		if(venusQueryDropdownItemInDTO.getSkuId()==null||venusQueryDropdownItemInDTO.getSkuId()<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("brandId or catId")));
			return result;
		}


		if(venusQueryDropdownItemInDTO.getSellerId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("sellerId")));
			return result;
		}
		
		if(StringUtils.isEmpty(venusQueryDropdownItemInDTO.getSupplierCode())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("supplierCode")));
			return result;
		}


		try{
			VenusOrderItemSkuDetailOutDTO itemSkuDetailOutDTO=itemSkuDAO.queryVenusOrderItemSkuDetail(venusQueryDropdownItemInDTO.getSkuId());
			
			if(itemSkuDetailOutDTO==null){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("VenusOrderItemSkuDetailOutDTO")));
				return result;
			}
			VenusQueryDropdownItemDetailOutDTO dropdownItemDetailOutDTO=new VenusQueryDropdownItemDetailOutDTO();
			dropdownItemDetailOutDTO.setItemName(itemSkuDetailOutDTO.getItemName());
			//获取ERP数据
			try{
				dropdownItemDetailOutDTO.setItemWarehouseList(queryItemWarehouseList(itemSkuDetailOutDTO.getItemSpuCode(),venusQueryDropdownItemInDTO.getSupplierCode()));
			}catch(Exception ex){
				ex.printStackTrace();
			}

			List<ItemSkuPublishInfo> itemSkuPublishInfoList=itemSkuPublishInfoMapper.queryItemSkuShelfStatus(itemSkuDetailOutDTO.getSkuId());

			if(CollectionUtils.isEmpty(itemSkuPublishInfoList)){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSkuPublishInfoList")));
				return result;
			}

			ExecuteResult<ItemSkuBasePriceDTO> queryBasePriceResult=itemSkuPriceService.queryItemSkuBasePrice(itemSkuDetailOutDTO.getSkuId());
			if(queryBasePriceResult!=null&&queryBasePriceResult.isSuccess()){

				ItemSkuBasePriceDTO basePrice=queryBasePriceResult.getResult();

				List<VenusDropdownItemObjDTO> venusDropdownItemObjList=Lists.newArrayList();

				for(ItemSkuPublishInfo itemSkuPublishInfo:itemSkuPublishInfoList){
					VenusDropdownItemObjDTO venusDropdownItemObj=new VenusDropdownItemObjDTO();

					if(itemSkuPublishInfo.getDisplayQuantity()!=null){
						venusDropdownItemObj.setDisplayQty(String.valueOf(itemSkuPublishInfo.getDisplayQuantity()));
					}
					if(itemSkuPublishInfo.getReserveQuantity()!=null){
						venusDropdownItemObj.setReserveQuantity(String.valueOf(itemSkuPublishInfo.getReserveQuantity()));
					}
					if(basePrice.getSaleLimitedPrice()!=null){
						venusDropdownItemObj.setSalesLimitPrice(basePrice.getSaleLimitedPrice().setScale(4));
					}
					if(basePrice.getSaleLimitedPrice()!=null){
						venusDropdownItemObj.setSalesPrice(basePrice.getBoxSalePrice().setScale(4));
					}
					if(itemSkuPublishInfo.getIsBoxFlag()!=null){
						venusDropdownItemObj.setShelfType(itemSkuPublishInfo.getIsBoxFlag()==1 ? "1" : "2");
					}

					venusDropdownItemObj.setIsVisible(itemSkuPublishInfo.getIsVisable()==1?"1":"0");

					venusDropdownItemObjList.add(venusDropdownItemObj);
				}
				dropdownItemDetailOutDTO.setVenusDropdownItemObjList(venusDropdownItemObjList);
			}
			dropdownItemDetailOutDTO.setSkuCode(itemSkuDetailOutDTO.getSkuCode());
			dropdownItemDetailOutDTO.setItemCode(itemSkuDetailOutDTO.getItemCode());
			result.setCode(ErrorCodes.SUCCESS.name());
			result.setResult(dropdownItemDetailOutDTO);
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryDropdownItemDetail:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	}
	
	private List<QueryItemWarehouseOutDTO> queryItemWarehouseList(String spuCode,String supplierCode){
		String accessToken=MiddlewareInterfaceUtil.getAccessToken();
		if(StringUtils.isEmpty(accessToken)){
			return null;
		}
		String paramStr="?productCode="+spuCode+"&supplierCode="+supplierCode+"&token="+accessToken;
		String response=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_CHECK_BEFORE_SPLIT_ORDER_URL+paramStr,Boolean.TRUE);
		
		if(StringUtils.isEmpty(response)){
			return null;
		}
		
		Map map=(Map)JSONObject.toBean(JSONObject.fromObject(response),Map.class);
		
		if(MapUtils.isEmpty(map)){
			return null;
		}
		
		if(!MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(map.get("code")+"")){
			return null;
		}
		//转换成对象
		JSONArray jsonArray=JSONArray.fromObject(map.get("data"));
		List<QueryItemWarehouseOutDTO> resultList=jsonArray.toList(jsonArray, QueryItemWarehouseOutDTO.class);
		return resultList;
	}

	@Override
	public ExecuteResult<VenusWarningStockLevelDataOutDTO> getWarningStockLevelProductsData(
			Long sellerId) {
		ExecuteResult<VenusWarningStockLevelDataOutDTO> result=new ExecuteResult<VenusWarningStockLevelDataOutDTO>();
		
		if(sellerId==null||sellerId<=0){
			result.setCode(ErrorCodes.E10001.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10001.getErrorMsg()));
			return result;
		}
		
		try{
			List<Map<String,Integer>> mapResult=itemSkuTotalStockMapper.queryWarningStockLevelProductsData(sellerId);
			if(CollectionUtils.isEmpty(mapResult)){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("WarningStockLevelProductsData")));
				return result;
			}
			VenusWarningStockLevelDataOutDTO venusWarningStockLevelDataOutDTO=new VenusWarningStockLevelDataOutDTO();
			
			for(Map<String,Integer> map:mapResult){
				if(map.get("type")==0){
					 venusWarningStockLevelDataOutDTO.setSumOfProductsWithoutWarning(new Integer(map.get("cnu")+""));
				}
				
				if(map.get("type")==1){
					venusWarningStockLevelDataOutDTO.setSumOfWarningProducts(new Integer(map.get("cnu")+""));
				}
			}
			result.setResult(venusWarningStockLevelDataOutDTO);
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::getWarningStockLevelProductsData:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<List<VenusWarningStockLevelListOutDTO>> getWarningStockLevelProductsInfoList(
			Long sellerId) {
		ExecuteResult<List<VenusWarningStockLevelListOutDTO>> result=new ExecuteResult<List<VenusWarningStockLevelListOutDTO>>();
		
		if(sellerId==null||sellerId<=0){
			result.setCode(ErrorCodes.E10001.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10001.getErrorMsg()));
			return result;
		}
		try{
			List<VenusWarningStockLevelListOutDTO> list=itemSkuTotalStockMapper.queryWarningStockLevelProductsInfoList(sellerId);
			//补充可见库存
			if(CollectionUtils.isNotEmpty(list)){
				for(VenusWarningStockLevelListOutDTO venusWarningStockLevelListOutDTO:list){
					 List<ItemSkuPublishInfo>  itemSkuPublishInfoList=itemSkuPublishInfoMapper.queryBySkuId(venusWarningStockLevelListOutDTO.getSkuId());
					 Integer displayQty=0;
					 if(CollectionUtils.isNotEmpty(itemSkuPublishInfoList)){
						 for(ItemSkuPublishInfo itemSkuPublishInfo:itemSkuPublishInfoList){
							 displayQty+=itemSkuPublishInfo.getDisplayQuantity();
						 }
					 }
					 venusWarningStockLevelListOutDTO.setDisplayQty(String.valueOf(displayQty));
				}
			}
			result.setResult(list);
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::getWarningStockLevelProductsInfoList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> queryItemSpuDataList(
			VenusItemMainDataInDTO venusItemSpuInDTO, Pager<String> page) {

		ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> result=new ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>>();
		if(venusItemSpuInDTO==null){
			result.setCode(VenusErrorCodes.E1040009.name());
			result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040009.getErrorMsg()));
			return result;
		}
		try{
			Long totalCount=itemSpuMapper.queryItemSpuDataCount(venusItemSpuInDTO);
			if(totalCount==null||totalCount<=0){
				result.setCode(ErrorCodes.SUCCESS.name());
				DataGrid<VenusItemSpuDataOutDTO> dataGrid=new DataGrid<VenusItemSpuDataOutDTO>();
				result.setResult(dataGrid);
				return result;
			}
			page.setTotalCount(totalCount.intValue());
			venusItemSpuInDTO.setStart((page.getPage()-1) * page.getRows());
			venusItemSpuInDTO.setPageSize(page.getRows());
			List<VenusItemSpuDataOutDTO> list=itemSpuMapper.queryItemSpuDataList(venusItemSpuInDTO);
			DataGrid<VenusItemSpuDataOutDTO> dataGrid=new DataGrid<VenusItemSpuDataOutDTO>(list);
			dataGrid.setTotal(totalCount);
			dataGrid.setPageSize(page.getRows());
			result.setResult(dataGrid);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::queryItemSpuDataList:",e);
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));
		}
		return result;
	
	}

	@Override
	public ExecuteResult<String> applyItemSpu2HtdProduct(List<Long> spuIdList,
			String sellerId, String shopId, String operatorId,
			String operatorName) {

		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(StringUtils.isEmpty(sellerId)){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg("sellerId")));
			return result;
		}
		
		if(StringUtils.isEmpty(shopId)){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg("shopId")));
			return result;
		}
		
		if(CollectionUtils.isEmpty(spuIdList)){
			result.setCode(ErrorCodes.E10005.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10005.getErrorMsg("spuIdList")));
			return result;
		}
		
		try{
			for(Long spuId:spuIdList){
				
				if(spuId==null||spuId<=0){
					continue;
				}
				
				ItemSpu itemSpu=itemSpuMapper.selectByPrimaryKey(spuId);
				
				if(itemSpu ==null){
					result.setCode(VenusErrorCodes.E1040003.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040003.getErrorMsg("item商品数据")));
					return result;
				}
				
				//校验商品的品类
				if(itemSpu.getCategoryId()==null){
					result.setCode(VenusErrorCodes.E1040004.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040004.getErrorMsg("商品的品类")));
					return result;
				}
				//校验商品的品牌
				if(itemSpu.getBrandId()==null){
					result.setCode(VenusErrorCodes.E1040005.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040005.getErrorMsg("商品的品牌")));
					return result;
				}
				//校验商品的单位
				if(StringUtils.isEmpty(itemSpu.getUnit())){
					result.setCode(VenusErrorCodes.E1040006.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040006.getErrorMsg("商品的单位")));
					return result;
				}
				//校验当前供应商是否已经存在该商品
				Long count=itemMybatisDAO.queryItemCountBySpuIdAndSellerId(spuId, Long.valueOf(sellerId));
				
				if(count!=null&&count>=1L){
					result.setCode(VenusErrorCodes.E1040007.name());
					result.setErrorMessages(Lists.newArrayList(VenusErrorCodes.E1040007.getErrorMsg(itemSpu.getSpuName()+",已经存在该商品")));
					return result;
				}
				Item newItem = doCreateNewItem(sellerId, shopId, operatorId,operatorName, spuId, itemSpu);
				//图片
				doApplyItemPicture(sellerId, shopId, operatorId, operatorName, spuId,newItem);
				//描述
				doApplyItemDescribe(operatorId,operatorName, spuId, newItem);
				//新建sku
				ItemSku newItemSku=Converters.convert(newItem, ItemSku.class);
				//保存sku
				itemSkuDAO.add(newItemSku);
				//根据当前申请商品保存大B的CBCVIEW(调用会员中心)，交由页面去调了
				
				//再次下行erp
				doItemDownErp(itemSpu,newItem);
			}
		}catch(Exception e){
			logger.error("VenusItemExportServiceImpl::applyItemSpu2HtdProduct:",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	
	}

	private void doApplyItemDescribe(String operatorId,
			String operatorName, Long spuId, Item newItem) {
		ItemSpuDescribe spuDescribe=itemSpuDescribeMapper.queryBySpuId(spuId);
		
		if(spuDescribe!=null){
			ItemDescribe describe= new ItemDescribe();
			//清空id
			describe.setDesId(null);
			describe.setDescribeContent(spuDescribe.getSpuDesc());
		    describe.setItemId(newItem.getItemId());
		    describe.setCreateTime(new Date());
		    describe.setCreateId(Long.valueOf(operatorId));
		    describe.setCreateName(operatorName);
		    describe.setModifyTime(new Date());
		    describe.setModifyId(Long.valueOf(operatorId));
		    describe.setModifyName(operatorName);
		    itemDescribeDAO.insertSelective(describe);
		}
	}

	private void doApplyItemPicture(String sellerId,
			String shopId, String operatorId, String operatorName, Long spuId,
			Item newItem) {
		List<ItemSpuPictureDTO> itemSpuPictureList=itemSpuPictureMapper.queryBySpuId(spuId);
		
		if(CollectionUtils.isNotEmpty(itemSpuPictureList)){
			List<ItemPicture> picturesList=Lists.newArrayList();
			int i=1;
			for(ItemSpuPictureDTO spuPicture:itemSpuPictureList){
				 ItemPicture picture=new ItemPicture();
			     picture.setItemId(newItem.getItemId());
			     picture.setPictureUrl(spuPicture.getPictureUrl());
			     picture.setSellerId(StringUtils.isEmpty(sellerId)?0:Long.valueOf(sellerId));
			     picture.setShopId(StringUtils.isEmpty(shopId)?0:Long.valueOf(shopId));
			     picture.setCreated(new Date());
			     picture.setCreateId(Long.valueOf(operatorId));
			     picture.setCreateName(operatorName);
			     picture.setModified(new Date());
			     picture.setModifyId(Long.valueOf(operatorId));
			     picture.setModifyName(operatorName);
			     picture.setPictureStatus(1);
			     picture.setIsFirst(i==1?1:0); 
			     picture.setSortNumber(i);
			     i++;
			     picturesList.add(picture);
			}
			if(CollectionUtils.isNotEmpty(picturesList)){
				itemPictureDAO.batchInsert(picturesList);
			}
		}
	}

	private Item doCreateNewItem(String sellerId, String shopId,
			String operatorId, String operatorName, Long spuId, ItemSpu itemSpu) {
		//保存item
		Item newItem=new Item();
		newItem.setItemCode(ItemCodeGenerator.generateItemCode());
		newItem.setItemName(itemSpu.getSpuName());
		newItem.setItemStatus(ItemStatusEnum.PASS.getCode());
		newItem.setSellerId(Long.valueOf(sellerId));
		newItem.setShopId(Long.valueOf(shopId));
		newItem.setCid(itemSpu.getCategoryId());
		newItem.setBrand(itemSpu.getBrandId());
		
		if(StringUtils.isNotEmpty(itemSpu.getModelType())){
			newItem.setModelType(itemSpu.getModelType());
		}
		
		newItem.setWeightUnit(itemSpu.getUnit());
		if(itemSpu.getGrossWeight()!=null){
			newItem.setWeight(itemSpu.getGrossWeight());
		}
		
		if(itemSpu.getNetWeight()!=null){
			newItem.setNetWeight(itemSpu.getNetWeight());
		}
		
		if(itemSpu.getLength()!=null){
			newItem.setLength(new BigDecimal(itemSpu.getLength()));
		}
		if(itemSpu.getWide()!=null){
			newItem.setWidth(new BigDecimal(itemSpu.getWide()));
		}
		if(itemSpu.getHigh()!=null){
			newItem.setHeight(new BigDecimal(itemSpu.getHigh()));
		}
		if(StringUtils.isNotEmpty(itemSpu.getOrigin())){
			newItem.setOrigin(itemSpu.getOrigin());
		}
		if(StringUtils.isNotEmpty(itemSpu.getItemQualification())){
			newItem.setItemQualification(itemSpu.getItemQualification());
		}
		newItem.setProductChannelCode(ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL);
		newItem.setApplyInSpu(true);
		newItem.setIsSpu(0);
		newItem.setItemSpuId(spuId);
		if(StringUtils.isNotEmpty(itemSpu.getPackingList())){
			newItem.setPackingList(itemSpu.getPackingList());
		}
		if(StringUtils.isNotEmpty(itemSpu.getAfterService())){
			newItem.setAfterService(itemSpu.getAfterService());
		}
		if(StringUtils.isNotEmpty(itemSpu.getErpCode())){
			newItem.setErpCode(itemSpu.getErpCode());
		}
		if(BigDecimal.ZERO.compareTo(itemSpu.getTaxRate()) < 0){
			newItem.setTaxRate(itemSpu.getTaxRate());
		}else{
			newItem.setTaxRate(new BigDecimal("0.17"));
		}
		newItem.setErpStatus("4");
		newItem.setCreated(new Date());
		newItem.setCreateId(Long.valueOf(operatorId));
		newItem.setCreateName(operatorName);
		newItem.setModified(new Date());
		newItem.setModifyId(Long.valueOf(operatorId));
		newItem.setModifyName(operatorName);
		
		if(StringUtils.isNotEmpty(itemSpu.getErpFirstCategoryCode())){
			newItem.setErpFirstCategoryCode(itemSpu.getErpFirstCategoryCode());
		}
		
		if(StringUtils.isNotEmpty(itemSpu.getErpFiveCategoryCode())){
			newItem.setErpFiveCategoryCode(itemSpu.getErpFiveCategoryCode());
		}
		
		if(StringUtils.isNotEmpty(itemSpu.getCategoryAttributes())){
			newItem.setAttributes(itemSpu.getCategoryAttributes());
		}
		
		itemMybatisDAO.addItem(newItem);
		return newItem;
	}
	
	 private void doItemDownErp(ItemSpu itemSpu, Item dbItem) {
		    logger.info("ERPdoItemDownErp start "+itemSpu.getSpuCode());
	        MQSendUtil mqSendUtil = SpringUtils.getBean(MQSendUtil.class);
	        ProductMessage productMessage = new ProductMessage();
	        productMessage.setProductCode(itemSpu.getSpuCode());
	        productMessage.setBrandCode(dbItem.getBrand().toString());
	        productMessage.setCategoryCode(dbItem.getErpFiveCategoryCode());
	        productMessage.setChargeUnit(dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, dbItem.getWeightUnit()));
	        productMessage.setMarque(StringUtils.isEmpty(dbItem.getModelType())?"0":dbItem.getModelType());
	        productMessage.setProductSpecifications(StringUtils.isEmpty(StringUtils.trimToEmpty(dbItem.getItemQualification()))?
	        		"0":dbItem.getItemQualification());
	        productMessage.setProductName(dbItem.getItemName());
	        productMessage.setOutputRate(dbItem.getTaxRate()==null?"0":dbItem.getTaxRate().toString());
	        productMessage.setProductColorCode("0");
	        productMessage.setProductColorName("0");
	        productMessage.setOrigin(StringUtils.isEmpty(dbItem.getOrigin())?"0":dbItem.getOrigin());
	        productMessage.setQualityGrade("0");
	        productMessage.setFunctionIntroduction("0");
	        productMessage.setValidTags(1);
	        productMessage.setIncomeTaxRates("0");
	        productMessage.setPackingContent("1");
	        if(StringUtils.isEmpty(itemSpu.getErpCode())||"0".equals(itemSpu.getErpCode())){
	        	productMessage.setIsUpdateFlag(0);
	        }else{
	        	productMessage.setIsUpdateFlag(1);
	        }
	        productMessage.setItemId(dbItem.getItemId()+"");
	        logger.info("商品开始下行ERP, 下行信息 : {}", JSONObject.fromObject(productMessage));
	        mqSendUtil.sendToMQWithRoutingKey(productMessage, MQRoutingKeyConstant.ITEM_DOWN_ERP_ROUTING_KEY);
	        itemMybatisDAO.updateErpStatus(dbItem.getItemId(), ItemErpStatusEnum.DOWNING.getCode(), "");
	        logger.info("ERPdoItemDownErp end "+itemSpu.getSpuCode());
	    }

}
