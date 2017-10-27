package cn.htd.goodscenter.service.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.basecenter.enums.ErpStatusEnum;
import cn.htd.common.ExecuteResult;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.goodscenter.common.channel.ItemChannelConstant;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuDescribeMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuPictureMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.domain.spu.ItemSpuDescribe;
import cn.htd.goodscenter.dto.ItemSpuPictureDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.listener.InsertProdRelationCallbackDTO;
import cn.htd.goodscenter.dto.listener.ItemAndSellerRelationshipDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.dto.ShopCategorySellerQueryDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopBrandExportService;
import cn.htd.storecenter.service.ShopCategorySellerExportService;
import cn.htd.storecenter.service.ShopExportService;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

public class Erp2MiddleItemRelationshipListener implements MessageListener{

	private Logger logger = LoggerFactory.getLogger(Erp2MiddleItemRelationshipListener.class);
	@Resource
	private ItemSpuMapper itemSpuMapper;
	@Resource
	private ItemSpuPictureMapper itemSpuPictureMapper;
	@Resource
	private ItemSpuDescribeMapper itemSpuDescribeMapper;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemPictureDAO itemPictureDAO;
	@Resource
	private ItemDescribeDAO itemDescribeDAO;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private MemberBaseInfoService memberBaseInfoService;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ShopCategorySellerExportService shopCategorySellerExportService;
	@Resource
	private ShopBrandExportService shopBrandExportService;
	@Resource
	private ShopExportService shopExportService;
	
	@Transactional
	@Override
	public void onMessage(Message message) {
		logger.info("Erp2MiddleItemRelationshipListener enter ...");
		
		if(message==null||message.getBody()==null){
			return;
		}
		
		String messageStr=new String(message.getBody());
		
		ItemAndSellerRelationshipDTO itemAndSellerRelationship=parseMessage(messageStr);
		
		logger.info("Erp2MiddleItemRelationshipListener enter {}",JSON.toJSON(itemAndSellerRelationship));
		
		if(itemAndSellerRelationship==null||
				StringUtils.isEmpty(itemAndSellerRelationship.getProductCode())
				||"null".equals(itemAndSellerRelationship.getProductCode())){
			logger.error("Erp2MiddleItemRelationshipListener::onMessage:itemAndSellerRelationship is null...");
			return;
		}

		//回调请求参数
		InsertProdRelationCallbackDTO prodRelationCallbackDTO=new InsertProdRelationCallbackDTO();
		try{
			//校验商品是否存在

			ItemSpu spu = itemSpuMapper.queryItemSpuBySpuCode(itemAndSellerRelationship.getProductCode());
			if(spu==null
					|| StringUtils.isEmpty(spu.getErpFirstCategoryCode())
					|| StringUtils.isEmpty(spu.getErpFiveCategoryCode())
					|| "0".equals(spu.getErpFirstCategoryCode())
					|| "0".equals(spu.getErpFiveCategoryCode())
					) {
				logger.error("Erp2MiddleItemRelationshipListener::onMessage:spu is null...");
				prodRelationCallbackDTO.setResult(0);
				prodRelationCallbackDTO.setProductCode(itemAndSellerRelationship.getProductCode());
				prodRelationCallbackDTO.setSupplierCode(itemAndSellerRelationship.getSupplierCode());
				prodRelationCallbackDTO.setToken(MiddlewareInterfaceUtil.getAccessToken());
				prodRelationCallbackDTO.setErrormessage("商品模板数据有误, 模板编码 : " + itemAndSellerRelationship.getProductCode());
				if(StringUtils.isEmpty(prodRelationCallbackDTO.getToken())){
					logger.error("Erp2MiddleItemRelationshipListener::onMessage:token is empty..");
					return;
				}
				sendCallBackReq(prodRelationCallbackDTO);
				return;
			}
			Long sellerId=0L;
			
			ExecuteResult<Long> member=memberBaseInfoService.getMemberIdByCode(itemAndSellerRelationship.getSupplierCode());
			
			if(member==null||!member.isSuccess()||member.getResult()==null||member.getResult()<=0L){
				logger.error("Erp2MiddleItemRelationshipListener::onMessage:member.getResult() is wrong...");
				return;
			}
			
			sellerId=member.getResult();
			
			//数据库校验是否已经存在
			Long count=0L;
			if(StringUtils.isNotEmpty(itemAndSellerRelationship.getSupplierCode())){
				count=itemMybatisDAO.queryItemCountBySpuIdAndSellerId(spu.getSpuId(), sellerId);
			}
			if(count==null||count<=0L){
				logger.error("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand 数据库中没有该商品关系");
				//新建一个item 
				Item item = doAddNewItem(itemAndSellerRelationship, spu,sellerId);
				
				doAddItemPicture(spu, item);
				
				doAddItemDescribe(spu, item);
				
				//新建一个item_sku
				doAddItemSku(item);
				
				doAddShopCategoryAndBrand(item.getBrand(),item.getCid(),item.getSellerId());
			}else{
				//查询出已经存在的商品
				Item itemDb=itemMybatisDAO.queryItemInfoBySpuIdAndSellerId(spu.getSpuId(), sellerId);
				logger.error("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand {}",JSON.toJSON(itemDb));
				doAddShopCategoryAndBrand(itemDb.getBrand(),itemDb.getCid(),sellerId);
			}
			prodRelationCallbackDTO.setResult(1);
			prodRelationCallbackDTO.setProductCode(itemAndSellerRelationship.getProductCode());
			prodRelationCallbackDTO.setSupplierCode(itemAndSellerRelationship.getSupplierCode());
			prodRelationCallbackDTO.setToken(MiddlewareInterfaceUtil.getAccessToken());
			if(StringUtils.isEmpty(prodRelationCallbackDTO.getToken())){
				logger.error("Erp2MiddleItemRelationshipListener::onMessage:token is empty..");
				return;
			}
			sendCallBackReq(prodRelationCallbackDTO);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Erp2MiddleItemRelationshipListener::onMessage:上行商品关系回调失败，错误码：" ,e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}

	/**
	 * 发送回调请求
	 * @param prodRelationCallbackDTO
	 */
	private void sendCallBackReq(InsertProdRelationCallbackDTO prodRelationCallbackDTO) {
		try {
			//发送回调请求
			String param="?productCode="+prodRelationCallbackDTO.getProductCode()+"&supplierCode="+prodRelationCallbackDTO.getSupplierCode()
					+"&flag=true&token="+prodRelationCallbackDTO.getToken();

			logger.error("Erp2MiddleItemRelationshipListener::onMessage:param is {}",param);

			String resultJson=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_INSERT_PROD_RELATION_CALLBACK_URL+param,
					Boolean.TRUE);

			logger.error("Erp2MiddleItemRelationshipListener::onMessage:resultJson is {}",resultJson);

			Map mapResult=(Map)JSONObject.toBean(JSONObject.fromObject(resultJson),Map.class);

			if(MapUtils.isEmpty(mapResult) ||mapResult.get("code")==null || !"1".equals(mapResult.get("code")+"")){
				//try again
				logger.error("Erp2MiddleItemRelationshipListener::onMessage:上行商品关系回调失败，错误码：" + mapResult.get("code"));
			}
		} catch (Exception e) {
			logger.error("Erp2MiddleItemRelationshipListener::onMessage:上行商品关系回调失败，错误码：" ,e);
		}
	}

	private Item doAddNewItem(
			ItemAndSellerRelationshipDTO itemAndSellerRelationship, ItemSpu spu,Long sellerId) {
		Item item=new Item();
		if(StringUtils.isNotEmpty(spu.getAfterService())){
			item.setAfterService(spu.getAfterService());
		}
		
		item.setApplyInSpu(true);
		item.setItemSpuId(spu.getSpuId());
		item.setBrand(spu.getBrandId());
		item.setCid(spu.getCategoryId());
		item.setErpCode(spu.getErpCode());
		if(spu.getErpFirstCategoryCode()!=null){
			item.setErpFirstCategoryCode(spu.getErpFirstCategoryCode());
		}
		if(spu.getErpFiveCategoryCode()!=null){
			item.setErpFiveCategoryCode(spu.getErpFiveCategoryCode());
		}
		if(spu.getHigh()!=null){
			item.setHeight(new BigDecimal(spu.getHigh()));
		}
		if(spu.getLength()!=null){
			item.setLength(new BigDecimal(spu.getLength()));
		}
		if(spu.getWide()!=null){
			item.setWidth(new BigDecimal(spu.getWide()));
		}
		
		if(StringUtils.isNotEmpty(spu.getItemQualification())){
			item.setItemQualification(spu.getItemQualification());
		}
		
		if(StringUtils.isNotEmpty(spu.getPackingList())){
			item.setItemQualification(spu.getPackingList());
		}
		
		if(StringUtils.isNotEmpty(spu.getCategoryAttributes())){
			item.setAttributes(spu.getCategoryAttributes());
		}
		
		if(StringUtils.isNotEmpty(spu.getModelType())){
			item.setModelType(spu.getModelType());
		}
		
		if(StringUtils.isNotEmpty(spu.getUnit())){
			item.setWeightUnit(spu.getUnit());
		}
		
		if(spu.getNetWeight()!=null){
			item.setNetWeight(spu.getNetWeight());
		}
		
		if(spu.getGrossWeight()!=null){
			item.setWeight(spu.getGrossWeight());
		}
		
		if(StringUtils.isNotEmpty(spu.getOrigin())){
			item.setOrigin(spu.getOrigin());
		}
		
		if(spu.getTaxRate()!=null){
			item.setTaxRate(spu.getTaxRate());
		}
		
		if(StringUtils.isNotEmpty(spu.getErpCode())){
			item.setErpCode(spu.getErpCode());
			item.setErpStatus(ErpStatusEnum.SUCCESS.getValue());
		}
		
		item.setSellerId(sellerId);
		
		ExecuteResult<ShopDTO> shopResult = shopExportService.queryBySellerId(sellerId);
		if (shopResult.isSuccess() && shopResult.getResult() != null&&shopResult.getResult().getShopId()!=null) {
			item.setShopId(shopResult.getResult().getShopId());
		}
		item.setItemName(spu.getSpuName());
		item.setItemCode(ItemCodeGenerator.generateItemCode());
		item.setProductChannelCode(ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL);
		item.setCreated(new Date());
		item.setCreateId(0L);
		item.setCreateName("system");
		item.setModified(new Date());
		item.setModifyId(0L);
		item.setModifyName("system");
		item.setItemStatus(HtdItemStatusEnum.PASS.getCode());
		itemMybatisDAO.addItem(item);
		return item;
	}

	private void doAddItemPicture(ItemSpu spu, Item item) {
		List<ItemSpuPictureDTO> spuPicList=itemSpuPictureMapper.queryBySpuId(spu.getSpuId());
		List<ItemPicture> itemPicList=Lists.newArrayList();
		ItemPicture itemPic=null;
		for(ItemSpuPictureDTO spuPic:spuPicList){
			itemPic=new ItemPicture();
			itemPic.setPictureId(spuPic.getPictureId());
			itemPic.setPictureUrl(spuPic.getPictureUrl());
			itemPic.setIsFirst(spuPic.getIsFirst());
			itemPic.setSortNumber(spuPic.getSortNum());
			itemPic.setDeleteFlag(0);
			itemPic.setPictureStatus(1);
			itemPic.setSellerId(item.getSellerId());
			itemPic.setShopId(item.getShopId());
			itemPic.setItemId(item.getItemId());
			itemPic.setCreated(new Date());
			itemPic.setCreateId(0L);
			itemPic.setCreateName("system");
			itemPic.setModified(new Date());
			itemPic.setModifyId(0L);
			itemPic.setModifyName("system");
			itemPicList.add(itemPic);
		}
		if(CollectionUtils.isNotEmpty(itemPicList)){
			itemPictureDAO.batchInsert(itemPicList);
		}
	}

	private void doAddItemDescribe(ItemSpu spu, Item item) {
		ItemSpuDescribe spuDescribe=itemSpuDescribeMapper.queryBySpuId(spu.getSpuId());
		if(spuDescribe!=null){
			ItemDescribe describe=new ItemDescribe();
			describe.setItemId(item.getItemId());
			describe.setDescribeContent(spuDescribe.getSpuDesc());
			describe.setCreateTime(new Date());
			describe.setCreateId(0L);
			describe.setCreateName("system");
			describe.setModifyTime(new Date());
			describe.setModifyId(0L);
			describe.setModifyName("system");
			itemDescribeDAO.insertSelective(describe);
		}
	}

	private void doAddItemSku(Item item) {
		ItemSku itemSku=new ItemSku();
		itemSku.setItemId(item.getItemId());
		itemSku.setShopId(0L);
		itemSku.setSkuCode(ItemCodeGenerator.generateSkuCode());
		itemSku.setSellerId(item.getSellerId());
		itemSku.setSkuStatus(1);
		itemSku.setSkuType(1);
		itemSku.setCreateId(0L);
		itemSku.setCreated(new Date());
		itemSku.setCreateName("system");
		itemSku.setModifyId(0L);
		itemSku.setModified(new Date());
		itemSku.setModifyName("system");
		itemSkuDAO.add(itemSku);
	}
	
	  /**
     * 解析文本
     * @param message 文本
     * @return 结果集
     */
    private ItemAndSellerRelationshipDTO parseMessage(String message) {
    	ItemAndSellerRelationshipDTO itemAndSellerRelationshipDTO = null;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            itemAndSellerRelationshipDTO = mapper.readValue(message, ItemAndSellerRelationshipDTO.class);
        } catch (Exception e) {
            logger.error("parseMessage error:", e);
        }
        return itemAndSellerRelationshipDTO;
    }
    
    private void doAddShopCategoryAndBrand(Long brandId,Long cid,Long sellerId){
    	// 添加店铺类目
        ExecuteResult<Map<String, Object>> itemCatResult = itemCategoryService.queryItemOneTwoThreeCategoryName(cid, ",");
       
        if (itemCatResult.isSuccess()) {
            String[] categoryNames = itemCatResult.getResult().get("categoryName").toString().split(",");
            ShopCategorySellerQueryDTO sellerCatDto = new ShopCategorySellerQueryDTO();
            // 平台三级类目
            sellerCatDto.setCname(categoryNames[2]);
            // 平台二级类目
            sellerCatDto.setParentCName(categoryNames[1]);
            sellerCatDto.setSellerId(sellerId);
            sellerCatDto.setCreateId(0L);
            sellerCatDto.setCreateName("goodscenter");
            logger.info("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand:{}",JSON.toJSON(sellerCatDto));
            ExecuteResult<ShopCategorySellerQueryDTO> addOrQueryResult= shopCategorySellerExportService.addOrQueryByCondition(sellerCatDto);
            logger.info("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand:{}",JSON.toJSON(addOrQueryResult));
            
        }
            // 新增商品成功后添加店铺品牌
	        ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
	        shopBrandDTO.setBrandId(brandId);
	        shopBrandDTO.setCreateId(0L);
	        shopBrandDTO.setCreateName("goodscenter");
	        shopBrandDTO.setModifyId(0L);
	        shopBrandDTO.setModifyName("goodscenter");
	        shopBrandDTO.setSellerId(sellerId);
	        shopBrandDTO.setCategoryId(cid);
	        if(itemCatResult.getResult().get("secondCategoryId")!=null){
	           shopBrandDTO.setParentCid(Long.parseLong(itemCatResult.getResult().get("secondCategoryId")+""));
	        }
	        logger.info("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand:{}",JSON.toJSON(shopBrandDTO));
            ExecuteResult<String> addShopCatAndBra=shopBrandExportService.addShopCategoryAndBrand(shopBrandDTO);
            logger.info("Erp2MiddleItemRelationshipListener::doAddShopCategoryAndBrand:{}",JSON.toJSON(addShopCatAndBra));
    }

}
