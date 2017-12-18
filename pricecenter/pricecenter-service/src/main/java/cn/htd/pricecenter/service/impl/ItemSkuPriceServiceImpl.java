package cn.htd.pricecenter.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.middleware.JdItemPriceResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.service.ItemSkuExportService;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.pricecenter.common.constants.ErrorCodes;
import cn.htd.pricecenter.common.constants.PriceConstants;
import cn.htd.pricecenter.dao.InnerItemSkuPriceMapper;
import cn.htd.pricecenter.dao.ItemSkuBasePriceMapper;
import cn.htd.pricecenter.dao.ItemSkuLadderPriceMapper;
import cn.htd.pricecenter.dao.ItemSkuTerminalPriceMapper;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.domain.ItemSkuTerminalPrice;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.dto.HzgPriceInDTO;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.OrderItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import cn.htd.pricecenter.enums.PriceTypeEnum;
import cn.htd.pricecenter.enums.TerminalPriceType;
import cn.htd.pricecenter.enums.TerminalTypeEnum;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 价格中心接口
 *  
 * @author zhangxiaolong
 *
 */
@Service("itemSkuPriceService")
public class ItemSkuPriceServiceImpl implements ItemSkuPriceService {
	@Resource
	private ItemSkuBasePriceMapper itemSkuBasePriceMapper;
	@Resource
	private InnerItemSkuPriceMapper innerItemSkuPriceMapper;
	@Resource
	private ItemSkuLadderPriceMapper itemSkuLadderPriceMapper;
	@Resource
	private MallItemExportService mallItemExportService;
	@Resource
	private ItemSkuExportService itemSkuExportService;
	@Resource
	private ItemSkuTerminalPriceMapper itemSkuTerminalPriceMapper;
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(ItemSkuPriceServiceImpl.class);

	@Override
	public ExecuteResult<String> saveItemSkuBasePrice(ItemSkuBasePrice itemSkuBasePrice) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.itemSkuBasePriceMapper.insertSelective(itemSkuBasePrice);
		} catch (Exception e) {
			logger.error("saveItemSkuBasePrice error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> updateItemSkuBasePrice(ItemSkuBasePrice itemSkuBasePrice) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.itemSkuBasePriceMapper.updateByPrimaryKeySelective(itemSkuBasePrice);
		} catch (Exception e) {
			logger.error("updateItemSkuBasePrice error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> addInnerItemSkuPrice(InnerItemSkuPrice innerItemSkuPrice) {
		logger.info("新增内部会员价格, 参数 : {}", JSONObject.fromObject(innerItemSkuPrice));
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.innerItemSkuPriceMapper.insertSelective(innerItemSkuPrice);
		} catch (Exception e) {
			logger.error("新增内部会员价格出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> updateInnerItemSkuPrice(InnerItemSkuPrice innerItemSkuPrice) {
		logger.info("更新内部会员价格, 参数 : {}", JSONObject.fromObject(innerItemSkuPrice));
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.innerItemSkuPriceMapper.updateByPrimaryKeySelective(innerItemSkuPrice);
		} catch (Exception e) {
			logger.error("更新内部会员价格出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<InnerItemSkuPrice> queryInnerItemSkuMemberLevelPrice(Long skuId, String priceType, String memberLevel, Integer isBoxFlag) {
		logger.info("查询内部会员等级价格, skuId : {}", skuId);
		ExecuteResult<InnerItemSkuPrice> executeResult = new ExecuteResult<InnerItemSkuPrice>();
		try {
			InnerItemSkuPrice innerItemSkuPriceParam = new InnerItemSkuPrice();
			innerItemSkuPriceParam.setSkuId(skuId);
			innerItemSkuPriceParam.setPriceType(priceType);
			innerItemSkuPriceParam.setBuyerGrade(memberLevel);
			innerItemSkuPriceParam.setIsBoxFlag(isBoxFlag);
			innerItemSkuPriceParam.setDeleteFlag(0);
			InnerItemSkuPrice innerItemSkuPrice = this.innerItemSkuPriceMapper.select(innerItemSkuPriceParam);
			executeResult.setResult(innerItemSkuPrice);
		} catch (Exception e) {
			logger.error("查询内部会员等级价格出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> addItemSkuLadderPrice(ItemSkuLadderPrice itemSkuLadderPrice) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.itemSkuLadderPriceMapper.insert(itemSkuLadderPrice);
		} catch (Exception e) {
			logger.error("addItemSkuLadderPrice error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> updateItemSkuLadderPrice(ItemSkuLadderPrice itemSkuLadderPrice) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			this.itemSkuLadderPriceMapper.updateByPrimaryKey(itemSkuLadderPrice);
		} catch (Exception e) {
			logger.error("updateItemSkuLadderPrice error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> updateItemSkuStandardPrice(StandardPriceDTO standardPriceDTO,Integer isBoxFlag) {
		ItemSkuBasePrice basePrice = standardPriceDTO.getItemSkuBasePrice();

		ExecuteResult<String> result = new ExecuteResult<String>();
		

		if (basePrice != null) {
			basePrice.setAreaSalePrice(wrapDecimal(basePrice.getAreaSalePrice(), 2));
			basePrice.setBoxSalePrice(wrapDecimal(basePrice.getBoxSalePrice(),2));
			basePrice.setRetailPrice(wrapDecimal(basePrice.getRetailPrice(),2));
			basePrice.setCostPrice(wrapDecimal(basePrice.getCostPrice(),2));
			ItemSkuBasePrice basePriceFromDb=itemSkuBasePriceMapper.selectByPrimaryKey(basePrice.getSkuId());
			if(basePriceFromDb==null){
				itemSkuBasePriceMapper.insertSelective(basePrice);
			}else{
				itemSkuBasePriceMapper.updateByPrimaryKeySelective(basePrice);
			}
			
		}
		//先删除内部价格
		innerItemSkuPriceMapper.txDeleteInnerItemSkuPrice(basePrice.getSkuId(),isBoxFlag==null?0:isBoxFlag);
		//会员分组价格
		dealWithItemSkuMemberGroupPrice(standardPriceDTO);
		// 会员等级价
		dealWithItemSkuMemberLevelPrice(standardPriceDTO);
		//区域价格
		dealWithItemSkuAreaPrice(standardPriceDTO);
		result.setCode(ErrorCodes.SUCCESS.getErrorMsg());
		return result;
	}

	private void dealWithItemSkuAreaPrice(StandardPriceDTO standardPriceDTO) {
		List<InnerItemSkuPrice> itemAreaPriceList=standardPriceDTO.getAreaPriceList();
		if(CollectionUtils.isNotEmpty(itemAreaPriceList)){
			List<InnerItemSkuPrice> needInsertItemAreaPriceList = Lists.newArrayList();
			List<InnerItemSkuPrice> needUpdateItemAreaPriceList = Lists.newArrayList();
			for(InnerItemSkuPrice innerItemSkuPrice:itemAreaPriceList){
				if (innerItemSkuPrice == null) {
					continue;
				}

				if (innerItemSkuPrice.getGradePriceId() != null) {
					needUpdateItemAreaPriceList.add(innerItemSkuPrice);
					continue;
				}
				innerItemSkuPrice.setPrice(wrapDecimal(innerItemSkuPrice.getPrice(), 2));
				needInsertItemAreaPriceList.add(innerItemSkuPrice);
			}
			
			if(CollectionUtils.isNotEmpty(needUpdateItemAreaPriceList)){
				for(InnerItemSkuPrice needUpdateItemAreaPrice:needUpdateItemAreaPriceList){
					needUpdateItemAreaPrice.setDeleteFlag(0);
					innerItemSkuPriceMapper.updateByPrimaryKeySelective(needUpdateItemAreaPrice);
				}
				
			}
			
			if(CollectionUtils.isNotEmpty(needInsertItemAreaPriceList)){
				for(InnerItemSkuPrice needInsertItemAreaPrice:needInsertItemAreaPriceList){
					innerItemSkuPriceMapper.insertSelective(needInsertItemAreaPrice);
				}
			}
		}
	}

	private void dealWithItemSkuMemberLevelPrice(
			StandardPriceDTO standardPriceDTO) {
		List<InnerItemSkuPrice> itemSkuMemberLevelPriceList = standardPriceDTO.getItemSkuMemberLevelPriceList();
		if (CollectionUtils.isNotEmpty(itemSkuMemberLevelPriceList)) {
			List<InnerItemSkuPrice> needInsertItemSkuMemberLevelPriceList = Lists.newArrayList();
			List<InnerItemSkuPrice> needUpdateItemSkuMemberLevelPriceList = Lists.newArrayList();

			for (InnerItemSkuPrice innerItemSkuPrice : itemSkuMemberLevelPriceList) {
				if (innerItemSkuPrice == null) {
					continue;
				}

				if (innerItemSkuPrice.getGradePriceId() != null) {
					needUpdateItemSkuMemberLevelPriceList.add(innerItemSkuPrice);
					continue;
				}
				innerItemSkuPrice.setPrice(wrapDecimal(innerItemSkuPrice.getPrice(), 2));
				needInsertItemSkuMemberLevelPriceList.add(innerItemSkuPrice);
			}
			// 更新
			if (CollectionUtils.isNotEmpty(needUpdateItemSkuMemberLevelPriceList)) {
				for (InnerItemSkuPrice itemSkuMemberLevelPrice : needUpdateItemSkuMemberLevelPriceList) {
					itemSkuMemberLevelPrice.setDeleteFlag(0);
					innerItemSkuPriceMapper.updateByPrimaryKeySelective(itemSkuMemberLevelPrice);
				}
			}
			// 新增
			if (CollectionUtils.isNotEmpty(needInsertItemSkuMemberLevelPriceList)) {
				
				for(InnerItemSkuPrice itemSkuMemberLevelPrice : needInsertItemSkuMemberLevelPriceList){
					innerItemSkuPriceMapper.insertSelective(itemSkuMemberLevelPrice);
				}
			}

		}
	}

	private void dealWithItemSkuMemberGroupPrice(
			StandardPriceDTO standardPriceDTO) {
		List<InnerItemSkuPrice> itemSkuMemberGroupPriceList = standardPriceDTO.getItemSkuMemberGroupPriceList();
		// 分组价格
		if (CollectionUtils.isNotEmpty(itemSkuMemberGroupPriceList)) {
			List<InnerItemSkuPrice> needInsertItemSkuMemberGroupPriceList = Lists.newArrayList();
			List<InnerItemSkuPrice> needUpdateItemSkuMemberGroupPriceList = Lists.newArrayList();
			for (InnerItemSkuPrice innerItemSkuMemberGroupPrice : itemSkuMemberGroupPriceList) {
				if (innerItemSkuMemberGroupPrice == null) {
					continue;
				}

				if (innerItemSkuMemberGroupPrice.getGradePriceId() != null) {
					needUpdateItemSkuMemberGroupPriceList.add(innerItemSkuMemberGroupPrice);
					continue;
				}
				innerItemSkuMemberGroupPrice.setPrice(wrapDecimal(innerItemSkuMemberGroupPrice.getPrice(), 2));
				needInsertItemSkuMemberGroupPriceList.add(innerItemSkuMemberGroupPrice);
			}
			// 更新
			if (CollectionUtils.isNotEmpty(needUpdateItemSkuMemberGroupPriceList)) {
				for (InnerItemSkuPrice needUpdateItemSkuMemGroupPrice : needUpdateItemSkuMemberGroupPriceList) {
					needUpdateItemSkuMemGroupPrice.setDeleteFlag(0);
					innerItemSkuPriceMapper.updateByPrimaryKeySelective(needUpdateItemSkuMemGroupPrice);
				}
			}
			// 新增
			if (CollectionUtils.isNotEmpty(needInsertItemSkuMemberGroupPriceList)) {
				for(InnerItemSkuPrice needUpdateItemSkuMemGroupPrice :needInsertItemSkuMemberGroupPriceList ){
					innerItemSkuPriceMapper.insertSelective(needUpdateItemSkuMemGroupPrice);
				}
			}
		}
	}

	@Override
	public ExecuteResult<List<ItemSkuBasePrice>> batchQueryItemSkuBasePrice(
			List<Long> skuIdList) {
		ExecuteResult<List<ItemSkuBasePrice>> result = new ExecuteResult<List<ItemSkuBasePrice>>();

		if (CollectionUtils.isEmpty(skuIdList)) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("skuIdList")));
			return result;
		}

		List<ItemSkuBasePrice> list = itemSkuBasePriceMapper
				.queryItemSkuBasePriceBySkuIdList(skuIdList);
		result.setResult(list);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<StandardPriceDTO> queryStandardPrice4InnerSeller(Long skuId, Integer isBoxFlag) {
		ExecuteResult<StandardPriceDTO> result = new ExecuteResult();
		if (skuId == null || skuId <= 0) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}
		if (isBoxFlag == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("isBoxFlag")));
			return result;
		}
		StandardPriceDTO standardPriceDTO = new StandardPriceDTO();
		// 基础价格
		ItemSkuBasePrice itemSkuBasePrice = itemSkuBasePriceMapper.selectByPrimaryKey(skuId);
		standardPriceDTO.setItemSkuBasePrice(itemSkuBasePrice);
		queryInnerItemSkuPrice(skuId, isBoxFlag, standardPriceDTO);
		
		ExecuteResult<HzgPriceDTO> hzgPriceResult=queryHzgTerminalPriceByTerminalType(skuId);
		
		if(hzgPriceResult!=null&&hzgPriceResult.isSuccess()){
			standardPriceDTO.setHzgPriceDTO(hzgPriceResult.getResult());
		}
		
		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(standardPriceDTO);
		return result;
	}

	private void queryInnerItemSkuPrice(Long skuId, Integer isBoxFlag, StandardPriceDTO standardPriceDTO) {
		List<InnerItemSkuPrice> allInnerItemSkuPriceList = innerItemSkuPriceMapper.selectInnerItemSkuPriceBySkuId(skuId, isBoxFlag);
		// 区域价
		List<InnerItemSkuPrice> areaPriceList = Lists.newArrayList();
		// 分组价
		List<InnerItemSkuPrice> memberGroupPriceList = Lists.newArrayList();
		// 会员等级价
		List<InnerItemSkuPrice> memberLevelPriceList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(allInnerItemSkuPriceList)) {
			for (InnerItemSkuPrice innerItemSkuPrice : allInnerItemSkuPriceList) {
				if ( PriceConstants.PRICE_TYPE_OF_AREA.equals(innerItemSkuPrice.getPriceType())) {
					areaPriceList.add(innerItemSkuPrice);
					continue;
				}
				if (PriceConstants.PRICE_TYPE_OF_MEMBER_GROUP.equals(innerItemSkuPrice.getPriceType())) {
					memberGroupPriceList.add(innerItemSkuPrice);
					continue;
				}
				if (PriceConstants.PRICE_TYPE_OF_MEMBER_LEVEL.equals(innerItemSkuPrice.getPriceType())) {
					memberLevelPriceList.add(innerItemSkuPrice);
					continue;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(memberGroupPriceList)) {
			standardPriceDTO
					.setItemSkuMemberGroupPriceList(memberGroupPriceList);
		}
		if (CollectionUtils.isNotEmpty(memberLevelPriceList)) {
			standardPriceDTO
					.setItemSkuMemberLevelPriceList(memberLevelPriceList);
		}
		if (CollectionUtils.isNotEmpty(areaPriceList)) {
			standardPriceDTO.setAreaPriceList(areaPriceList);
		}
	}

	@Override
	public ExecuteResult<ItemSkuBasePriceDTO> queryItemSkuBasePrice(Long skuId) {
		ExecuteResult<ItemSkuBasePriceDTO> result = new ExecuteResult();
		if (skuId == null || skuId <= 0) {
			result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("skuId"));
			return result;
		}
		try {
			// 基础价格
			ItemSkuBasePrice itemSkuBasePrice = itemSkuBasePriceMapper.selectByPrimaryKey(skuId);
			// 属性映射
			ItemSkuBasePriceDTO itemSkuBasePriceDTO = new ItemSkuBasePriceDTO();
			//京东商品则实时去京东查一下 start
			if("3010".equals(itemSkuBasePrice.getChannelCode())){
				doUpdateJdPriceRightNow(skuId, itemSkuBasePrice);
			}
			if (itemSkuBasePrice != null) {
				BeanUtils.copyProperties(itemSkuBasePrice, itemSkuBasePriceDTO);
			}
			//京东商品则实时去京东查一下 end
			result.setResult(itemSkuBasePriceDTO);
		} catch (Exception e) {
			logger.error("Error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	private void doUpdateJdPriceRightNow(Long skuId,ItemSkuBasePrice itemSkuBasePrice) {
		ExecuteResult<ItemSku> itemSkuResult=itemSkuExportService.queryItemSkuBySkuId(skuId);
		if(itemSkuResult==null||!itemSkuResult.isSuccess()||itemSkuResult.getResult()==null
				||itemSkuResult.getResult().getOuterSkuId()==null){
			return;
		}
		JdItemPriceResponseDTO jdItemPriceResponseDTO=MiddlewareInterfaceUtil.getJdItemRealPrice(itemSkuResult.getResult().getOuterSkuId());
		
		if(jdItemPriceResponseDTO==null){
			return;
		}
		
		BigDecimal costPrice = jdItemPriceResponseDTO.getPrice(); // 外部成本价
		BigDecimal saleLimitedPrice = jdItemPriceResponseDTO.getJdPrice(); // 外部销售价
		BigDecimal priceFloatingRatio = itemSkuBasePrice.getPriceFloatingRatio(); // 价格浮动比例
		BigDecimal retailPriceFloatingRatio = itemSkuBasePrice.getRetailPriceFloatingRatio(); // 零售价格浮动比例
		String vipPriceFloatingRatio = itemSkuBasePrice.getVipPriceFloatingRatio(); // VIP浮动比例
		BigDecimal areaSalePrice = null; // 商城销售价
		if (priceFloatingRatio != null && priceFloatingRatio.floatValue() > 0) {
		   areaSalePrice = costPrice.multiply(new BigDecimal("1").add(priceFloatingRatio)); // 商城销售价 = 外部供货价 乘以 (1 + 价格浮动比例)
		}
		BigDecimal retailPrice = null; // 商城零售价
		if (areaSalePrice != null && areaSalePrice.floatValue() > 0 && retailPriceFloatingRatio != null && retailPriceFloatingRatio.floatValue() > 0) {
		    retailPrice = areaSalePrice.multiply(new BigDecimal("1").add(retailPriceFloatingRatio)); // 商城零售价 = 商城销售价 乘以 (1 + 价格浮动比例)
		}
		
		// 更新VIP价格
		BigDecimal vipPriceFloatingRatioBigDecimal=null;
		if(StringUtils.isNotEmpty(vipPriceFloatingRatio)){
			try {
			    vipPriceFloatingRatioBigDecimal = new BigDecimal(vipPriceFloatingRatio);
			} catch (Exception e) {
			    vipPriceFloatingRatioBigDecimal = null;
			}
		}
		if (vipPriceFloatingRatioBigDecimal != null && vipPriceFloatingRatioBigDecimal.floatValue() >= 0) {
		    BigDecimal vipPrice = costPrice.multiply(new BigDecimal("1").add(vipPriceFloatingRatioBigDecimal));
		    // 更新vip价格
		    ExecuteResult<InnerItemSkuPrice> executeResultInnerPrice = queryInnerItemSkuMemberLevelPrice(itemSkuBasePrice.getSkuId(), Constants.PRICE_TYPE_BUYER_GRADE, Constants.VIP_BUYER_GRADE, 0);
		    InnerItemSkuPrice innerItemSkuPrice = executeResultInnerPrice.getResult();

		    if (vipPrice != null&&innerItemSkuPrice != null) {
		        innerItemSkuPrice.setPrice(wrapDecimal(vipPrice,2));
		        updateInnerItemSkuPrice(innerItemSkuPrice);
		    }
		
		}
		// 更新
		itemSkuBasePrice.setCostPrice(wrapDecimal(costPrice, 2));
		itemSkuBasePrice.setSaleLimitedPrice(wrapDecimal(saleLimitedPrice, 2));
		itemSkuBasePrice.setAreaSalePrice(wrapDecimal(areaSalePrice, 2));
		itemSkuBasePrice.setRetailPrice(wrapDecimal(retailPrice, 2));
		itemSkuBasePrice.setModifyTime(new Date());
		itemSkuBasePrice.setModifyId(0L);
		itemSkuBasePrice.setModifyName("system");
		updateItemSkuBasePrice(itemSkuBasePrice);
	}
	
	  private static BigDecimal wrapDecimal(BigDecimal origin, int newScale) {
	        if (origin == null) {
	            return origin;
	        } else {
	            return origin.setScale(newScale, BigDecimal.ROUND_HALF_UP);
	        }
	    }

	@Override public List<ItemSkuLadderPrice> getSkuLadderPrice(Long skuId) {
		return itemSkuLadderPriceMapper.getSkuLadderPrice(skuId);
	}

	@Override
	public ExecuteResult<StandardPriceDTO> queryAllPrice4InnerSeller(
			Long skuId) {
		ExecuteResult<StandardPriceDTO> result = new ExecuteResult<StandardPriceDTO>();
		if (skuId == null || skuId <= 0) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("skuId")));
			return result;
		}
		StandardPriceDTO standardPriceDTO = new StandardPriceDTO();
		// 基础价格
		ItemSkuBasePrice itemSkuBasePrice = itemSkuBasePriceMapper
				.selectByPrimaryKey(skuId);
		standardPriceDTO.setItemSkuBasePrice(itemSkuBasePrice);
		List<InnerItemSkuPrice> allInnerItemSkuPriceList = innerItemSkuPriceMapper.selectAllInnerItemSkuPriceBySkuId(skuId);
		// 区域价
		List<InnerItemSkuPrice> areaPriceList = Lists.newArrayList();
		// 分组价
		List<InnerItemSkuPrice> memberGroupPriceList = Lists.newArrayList();
		// 会员等级价
		List<InnerItemSkuPrice> areaLevelPriceList = Lists.newArrayList();
		List<InnerItemSkuPrice> boxLevelPriceList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(allInnerItemSkuPriceList)) {
			for (InnerItemSkuPrice innerItemSkuPrice : allInnerItemSkuPriceList) {
				if ( PriceConstants.PRICE_TYPE_OF_AREA.equals(innerItemSkuPrice.getPriceType())) {
					areaPriceList.add(innerItemSkuPrice);
					continue;
				}
				if (PriceConstants.PRICE_TYPE_OF_MEMBER_GROUP.equals(innerItemSkuPrice.getPriceType())) {
					memberGroupPriceList.add(innerItemSkuPrice);
					continue;
				}
				if (PriceConstants.PRICE_TYPE_OF_MEMBER_LEVEL.equals(innerItemSkuPrice.getPriceType())) {
					if (innerItemSkuPrice.getIsBoxFlag() == 1){
						boxLevelPriceList.add(innerItemSkuPrice);
					}else if(innerItemSkuPrice.getIsBoxFlag() == 0){
						areaLevelPriceList.add(innerItemSkuPrice);
					}
					continue;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(memberGroupPriceList)) {
			standardPriceDTO
					.setItemSkuMemberGroupPriceList(memberGroupPriceList);
		}
		if (CollectionUtils.isNotEmpty(boxLevelPriceList)) {
			standardPriceDTO
					.setBoxLevelPriceList(boxLevelPriceList);
		}
		if(CollectionUtils.isNotEmpty(areaLevelPriceList)){
			standardPriceDTO.setAreaLevelPriceList(areaLevelPriceList);
		}
		if (CollectionUtils.isNotEmpty(areaPriceList)) {
			standardPriceDTO.setAreaPriceList(areaPriceList);
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(standardPriceDTO);
		return result;
	}

	@Override public int updateDeleteFlagByItemId(ItemSkuLadderPrice record) {
		return itemSkuLadderPriceMapper.updateDeleteFlagByItemId(record);
	}

	@Override
	public ExecuteResult<String> deleteAreaPrice(Long skuId, String areaCode) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(skuId==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}
		if(StringUtils.isEmpty(areaCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("areaCode")));
			return result;
		}
		
		innerItemSkuPriceMapper.txDeleteInnerItemSkuAreaPrice(areaCode,skuId);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> deleteItemSkuLadderPrice(Long skuId){
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(skuId==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}

		itemSkuLadderPriceMapper.deleteItemSkuLadderPrice(skuId);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> batchUpdateItemSkuLadderPrice(List<ItemSkuLadderPrice> itemSkuLadderPriceList){
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(CollectionUtils.isEmpty(itemSkuLadderPriceList)){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSkuLadderPriceList")));
				return result;
		}
		//先删除之前的阶梯价格
		ItemSkuLadderPrice itemSkuLadderPriceUpdate = new ItemSkuLadderPrice();
		itemSkuLadderPriceUpdate.setItemId(itemSkuLadderPriceList.get(0).getItemId());
		itemSkuLadderPriceUpdate.setDeleteFlag(1);
		itemSkuLadderPriceMapper.updateDeleteFlagByItemId(itemSkuLadderPriceUpdate);

		//再新增新的阶梯价格
		for(ItemSkuLadderPrice itemSkuLadderPriceAdd : itemSkuLadderPriceList){
			itemSkuLadderPriceMapper.insert(itemSkuLadderPriceAdd);
		}
		return  result;
	}

	@Override
	public ExecuteResult<ItemSkuBasePrice> queryItemSkuBasePriceByItemCode(String itemCode) {
		ExecuteResult<ItemSkuBasePrice> result = new ExecuteResult();
		if (StringUtils.isEmpty(itemCode)) {
			result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("itemCode"));
			return result;
		}
		try {
			// 基础价格
			ItemSkuBasePrice itemSkuBasePrice = itemSkuBasePriceMapper.selectByItemCode(itemCode);
			result.setResult(itemSkuBasePrice);
		} catch (Exception e) {
			logger.error("Error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public DataGrid<ItemSkuLadderPrice> queryLadderPriceBySellerIdAndSkuId(Long sellerId, Long skuId) {
		DataGrid<ItemSkuLadderPrice> dataGrid = new DataGrid<ItemSkuLadderPrice>();
		try{
			List<ItemSkuLadderPrice> list = itemSkuLadderPriceMapper.selectPriceBySellerIdAndSkuId(sellerId,skuId);
			dataGrid.setRows(list);
		}catch (Exception e){
			logger.error("Error:", e);
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<String> deleteItemSkuInnerPrice(Long skuId,Integer isBoxFlag) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (skuId==null||skuId<=0) {
			result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("skuId"));
			return result;
		}
		innerItemSkuPriceMapper.txDeleteInnerItemSkuPrice(skuId,isBoxFlag);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<CommonItemSkuPriceDTO> queryCommonItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO) {
		ExecuteResult<CommonItemSkuPriceDTO> result=new ExecuteResult<CommonItemSkuPriceDTO>();
		
		if(queryCommonItemSkuPriceDTO.getSkuId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}
		CommonItemSkuPriceDTO commonItemSkuPrice=new CommonItemSkuPriceDTO();
		//如果是外部供应商，则直接返回阶梯价格
		if("20".equals(queryCommonItemSkuPriceDTO.getItemChannelCode())){
			List<ItemSkuLadderPrice> ladderPriceList=itemSkuLadderPriceMapper.getSkuLadderPrice(queryCommonItemSkuPriceDTO.getSkuId());
			commonItemSkuPrice.setLadderPriceList(ladderPriceList);
			commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.LADDER_PRICE.getCode());
			result.setResult(commonItemSkuPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
		 }
		
		if(queryCommonItemSkuPriceDTO.getSkuId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuId")));
			return result;
		}
		
		if(queryCommonItemSkuPriceDTO.getIsBoxFlag()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("isBoxFlag")));
			return result;
		}
		
		if(queryCommonItemSkuPriceDTO.getIsLogin()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("IsLogin")));
			return result;
		}
		
		if(queryCommonItemSkuPriceDTO.getIsHasDevRelation()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("IsHasDevRelation")));
			return result;
		}
		
		if(queryCommonItemSkuPriceDTO.getItemChannelCode()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("ItemChannelCode")));
			return result;
		}
		ItemSkuBasePrice basePrice=itemSkuBasePriceMapper.selectByPrimaryKey(queryCommonItemSkuPriceDTO.getSkuId());
		
		//京东商品则实时取下价格
		if("3010".equals(queryCommonItemSkuPriceDTO.getItemChannelCode())){
			doUpdateJdPriceRightNow(queryCommonItemSkuPriceDTO.getSkuId(), basePrice);
		}
		
		if(basePrice==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("Base Price")));
			return result;
		}
		//先判断是否登录
		if(queryCommonItemSkuPriceDTO.getIsLogin()==0){
			//未登录，则直接返回零售价
			if(basePrice!=null){
				commonItemSkuPrice.setSalesPrice(basePrice.getRetailPrice());
				commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.SALE_PRICE.getCode());
				result.setResult(commonItemSkuPrice);
				result.setCode(ErrorCodes.SUCCESS.name());
				return result;
			}
		}
		BigDecimal boxSalePrice=basePrice.getBoxSalePrice();
		BigDecimal areaSalePrice=basePrice.getAreaSalePrice();
		//京东商品强制走大厅逻辑
		if("3010".equals(queryCommonItemSkuPriceDTO.getItemChannelCode())){
			//如果不可卖商品＋商品，则返回零售价
			if(null==queryCommonItemSkuPriceDTO.getIsCanSellProdplusItem()||
					0==queryCommonItemSkuPriceDTO.getIsCanSellProdplusItem()){
				commonItemSkuPrice.setSalesPrice(basePrice.getRetailPrice());
				commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.SALE_PRICE.getCode());
				result.setResult(commonItemSkuPrice);
				result.setCode(ErrorCodes.SUCCESS.name());
				return result;
			}
			//queryCommonItemSkuPriceDTO.setIsBoxFlag(0);
			//给出区域价格逻辑
			return getAreaPrice(queryCommonItemSkuPriceDTO,commonItemSkuPrice,basePrice,areaSalePrice);
		}
		//是否包厢上架，隐藏条件要在对应销售区域上架
		boolean isBoxFlag=mallItemExportService.isBoxProduct(queryCommonItemSkuPriceDTO.getSkuId(), queryCommonItemSkuPriceDTO.getCitySiteCode());
		//是否区域上架，隐藏条件要在对应销售区域上架
		boolean isAreaFlag=mallItemExportService.isAreaProduct(queryCommonItemSkuPriceDTO.getSkuId(), queryCommonItemSkuPriceDTO.getCitySiteCode());
		
		//判断是否包厢上架
		if(!isBoxFlag){
			if(!isAreaFlag){//是否区域上架，如果都没上架则异常
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("商品没有上架")));
				return result;
			}
			
			if(areaSalePrice==null){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("AreaSalePrice")));
				return result;
			}
			//给出区域价格逻辑
			return getAreaPrice(queryCommonItemSkuPriceDTO,commonItemSkuPrice,basePrice,areaSalePrice);
		}
		
		//判断是否有经营关系
		if(queryCommonItemSkuPriceDTO.getIsHasDevRelation()==0){
			//包厢上架，但是没有经营关系，且区域上架，则给出区域价格
			if(isAreaFlag&&areaSalePrice!=null){
				return getAreaPrice(queryCommonItemSkuPriceDTO,commonItemSkuPrice,basePrice,areaSalePrice);
			}
			//没有经营关系，且区域没有上架，则返回零售价
			commonItemSkuPrice.setSalesPrice(basePrice.getRetailPrice());
			commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.SALE_PRICE.getCode());
			result.setResult(commonItemSkuPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
			//return getAreaPrice(queryCommonItemSkuPriceDTO,commonItemSkuPrice,basePrice,areaSalePrice);
		}
		
		//包厢价格不为空
		if(boxSalePrice==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("BoxSalePrice")));
			return result;
		}
		//走包厢价格逻辑
		StandardPriceDTO standardPrice=new StandardPriceDTO();
		queryInnerItemSkuPrice(queryCommonItemSkuPriceDTO.getSkuId(), queryCommonItemSkuPriceDTO.getIsBoxFlag(), standardPrice);
		//会员等级价
		List<InnerItemSkuPrice> memberLevelPriceList=standardPrice.getItemSkuMemberLevelPriceList();
		//会员分组价
		List<InnerItemSkuPrice> memberGroupPriceList=standardPrice.getItemSkuMemberGroupPriceList();
		BigDecimal memberLevelSalePrice=null;
		BigDecimal memberGroupSalePrice=null;
		if(CollectionUtils.isNotEmpty(memberLevelPriceList)){
			for(InnerItemSkuPrice memberLevelPrice:memberLevelPriceList){
				if(memberLevelPrice.getBuyerGrade().equals(queryCommonItemSkuPriceDTO.getBuyerGrade())
						&&queryCommonItemSkuPriceDTO.getIsBoxFlag()==memberLevelPrice.getIsBoxFlag()){
					memberLevelSalePrice=memberLevelPrice.getPrice();
					break;
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(memberGroupPriceList)){
			for(InnerItemSkuPrice memberGroupPrice:memberGroupPriceList){
				if(memberGroupPrice.getGroupId().equals(queryCommonItemSkuPriceDTO.getMemberGroupId())
						&&queryCommonItemSkuPriceDTO.getIsBoxFlag()==memberGroupPrice.getIsBoxFlag()){
					memberGroupSalePrice=memberGroupPrice.getPrice();
					break;
				}
			}
		}
		//return the lowest
		if(memberLevelSalePrice!=null&&memberGroupSalePrice!=null){
			boolean isMemberLevelBigger = memberLevelSalePrice.compareTo(memberGroupSalePrice) > 0;
			BigDecimal minSalePrice = isMemberLevelBigger ? memberGroupSalePrice : memberLevelSalePrice;
			commonItemSkuPrice.setSalesPrice(minSalePrice);
			commonItemSkuPrice.setGoodsPriceType(!isMemberLevelBigger ? PriceTypeEnum.MEMBER_LEVEL_PRICE.getCode() : PriceTypeEnum.GROUP_PRICE.getCode());
			result.setResult(commonItemSkuPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
		}
		//return the only one
		if(memberLevelSalePrice!=null||memberGroupSalePrice!=null){
			BigDecimal minSalePrice=memberLevelSalePrice == null?memberGroupSalePrice:memberLevelSalePrice;
			commonItemSkuPrice.setSalesPrice(minSalePrice);
			if(memberLevelSalePrice!=null){
				commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.MEMBER_LEVEL_PRICE.getCode());
			}
			if(memberGroupSalePrice!=null){
				commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.GROUP_PRICE.getCode());
			}
			result.setResult(commonItemSkuPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
		}
		
		//return the box sale price
		commonItemSkuPrice.setSalesPrice(boxSalePrice);
		commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.SALE_PRICE.getCode());
		result.setResult(commonItemSkuPrice);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> deleteLadderPriceBySkuId(ItemSkuLadderPrice itemSkuLadderPrice) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			itemSkuLadderPriceMapper.deleteLadderPriceBySkuId(itemSkuLadderPrice);
			result.setResultMessage("success");
		}catch (Exception e){
			logger.error(e.getMessage());
			result.setResult(e.getMessage());
		}
		return result;
	}

	private ExecuteResult<CommonItemSkuPriceDTO>  getAreaPrice(QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO,
			CommonItemSkuPriceDTO commonItemSkuPrice,ItemSkuBasePrice basePrice,BigDecimal areaSalePrice){
		ExecuteResult<CommonItemSkuPriceDTO> result=new ExecuteResult<CommonItemSkuPriceDTO>();
		StandardPriceDTO standardPrice=new StandardPriceDTO();
		queryInnerItemSkuPrice(queryCommonItemSkuPriceDTO.getSkuId(), queryCommonItemSkuPriceDTO.getIsBoxFlag(), standardPrice);
		List<InnerItemSkuPrice> memberLevelPriceList=standardPrice.getItemSkuMemberLevelPriceList();
		//优先返回会员等级价格
		if(CollectionUtils.isNotEmpty(memberLevelPriceList)){
			for(InnerItemSkuPrice memberLevelPrice:memberLevelPriceList){
				if(memberLevelPrice.getBuyerGrade().equals(queryCommonItemSkuPriceDTO.getBuyerGrade())
						&&queryCommonItemSkuPriceDTO.getIsBoxFlag()==memberLevelPrice.getIsBoxFlag()){
					commonItemSkuPrice.setSalesPrice(memberLevelPrice.getPrice());
					commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.MEMBER_LEVEL_PRICE.getCode());
					result.setResult(commonItemSkuPrice);
					return result;
				}
			}
		}
		//判断是否有区域价格
		List<InnerItemSkuPrice> areaPriceList=standardPrice.getAreaPriceList();
		if(CollectionUtils.isNotEmpty(areaPriceList)){
			for(InnerItemSkuPrice areaPrice:areaPriceList){
				if(queryCommonItemSkuPriceDTO.getIsBoxFlag()==areaPrice.getIsBoxFlag()){
					if(PriceConstants.CITY_SITE_OF_ALL_COUNTRY.equals(queryCommonItemSkuPriceDTO.getCitySiteCode())
							||areaPrice.getAreaCode().equals(queryCommonItemSkuPriceDTO.getCitySiteCode())){
						commonItemSkuPrice.setSalesPrice(areaPrice.getPrice());
						commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.AREA_PRICE.getCode());
						result.setResult(commonItemSkuPrice);
						return result;
					}
				}
			}
		}
		//返回区域销售价
		commonItemSkuPrice.setSalesPrice(areaSalePrice);
		commonItemSkuPrice.setGoodsPriceType(PriceTypeEnum.SALE_PRICE.getCode());
		result.setResult(commonItemSkuPrice);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<BigDecimal> queryMinLadderPriceByItemId(Long itemId) {
		ExecuteResult<BigDecimal> result=new ExecuteResult<BigDecimal>();
		if(itemId==null||itemId<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemId")));
			return result;
		}
		String minPrice=itemSkuLadderPriceMapper.queryMinLadderPriceByItemId(itemId);
		if(StringUtils.isEmpty(minPrice)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("minPrice")));
			return result;
		}
		
		BigDecimal salePrice=new BigDecimal(minPrice);
		result.setResult(salePrice);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<OrderItemSkuPriceDTO> queryOrderItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO) {
		ExecuteResult<OrderItemSkuPriceDTO> result=new ExecuteResult<OrderItemSkuPriceDTO>();
		OrderItemSkuPriceDTO orderItemSkuPriceDTO=new OrderItemSkuPriceDTO();
		//如果是外部供应商，则直接返回阶梯价格
		if("20".equals(queryCommonItemSkuPriceDTO.getItemChannelCode())){
			List<ItemSkuLadderPrice> ladderPriceList=itemSkuLadderPriceMapper.getSkuLadderPrice(queryCommonItemSkuPriceDTO.getSkuId());
			orderItemSkuPriceDTO.setLadderPriceList(ladderPriceList);
			orderItemSkuPriceDTO.setCostPrice(BigDecimal.ZERO);
			orderItemSkuPriceDTO.setPriceFloatingRatio(BigDecimal.ZERO);
			orderItemSkuPriceDTO.setCommissionRatio(BigDecimal.ZERO);
			orderItemSkuPriceDTO.setSalePrice(BigDecimal.ZERO);
			orderItemSkuPriceDTO.setGoodsPriceType(1);
			orderItemSkuPriceDTO.setGoodsPriceInfo(JSONArray.fromObject(ladderPriceList).toString());
			orderItemSkuPriceDTO.setGoodsPrice(BigDecimal.ZERO);
			result.setResult(orderItemSkuPriceDTO);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
		}
		
		//查询汇掌柜价格 start
		try{
			Map<String,Object> paramMap=Maps.newHashMap();
			paramMap.put("terminalType",TerminalTypeEnum.HZG_TYPE.getCode());
			paramMap.put("skuId",queryCommonItemSkuPriceDTO.getSkuId());
			
			List<ItemSkuTerminalPrice> itemSkuTerminalPriceList=itemSkuTerminalPriceMapper.selectBySkuIdAndTerminalType(paramMap);
			
			if(CollectionUtils.isNotEmpty(itemSkuTerminalPriceList)){
				HzgPriceDTO hzgPrice=new HzgPriceDTO();
				
				for(ItemSkuTerminalPrice itemSkuTerminalPrice:itemSkuTerminalPriceList){
					if("0".equals(itemSkuTerminalPrice.getPriceType())){
						hzgPrice.setRetailPrice(itemSkuTerminalPrice.getPrice());
					}
					if("1".equals(itemSkuTerminalPrice.getPriceType())){
						hzgPrice.setSalePrice(itemSkuTerminalPrice.getPrice());
					}
					if("2".equals(itemSkuTerminalPrice.getPriceType())){
						hzgPrice.setVipPrice(itemSkuTerminalPrice.getPrice());
					}
				}
				orderItemSkuPriceDTO.setHzgPrice(hzgPrice);
			}
			
		}catch(Exception e){
			
		}
		//查询汇掌柜价格 end
		
		ExecuteResult<CommonItemSkuPriceDTO> queryCommonItemSkuPrice=queryCommonItemSkuPrice(queryCommonItemSkuPriceDTO);
		
		if(!queryCommonItemSkuPrice.isSuccess()){
			result.setResult(orderItemSkuPriceDTO);
			result.setCode(queryCommonItemSkuPrice.getCode());
			result.setErrorMessages(queryCommonItemSkuPrice.getErrorMessages());
			return result;
		}
		
		ItemSkuBasePrice itemSkuBasePrice=itemSkuBasePriceMapper.selectByPrimaryKey(queryCommonItemSkuPriceDTO.getSkuId());
		
		if(queryCommonItemSkuPrice.isSuccess()){
			
			if("3010".equals(queryCommonItemSkuPriceDTO.getItemChannelCode())){
				//京东则取成本价
				orderItemSkuPriceDTO.setCostPrice(itemSkuBasePrice.getCostPrice());
				//佣金比例
				orderItemSkuPriceDTO.setCommissionRatio(itemSkuBasePrice.getCommissionRatio());
				if(3==queryCommonItemSkuPrice.getResult().getGoodsPriceType()){
					//会员等级价则使用VIP浮动比例
					orderItemSkuPriceDTO.setPriceFloatingRatio(new BigDecimal(itemSkuBasePrice.getVipPriceFloatingRatio()));
				}else{
					//销售价浮动比例
					orderItemSkuPriceDTO.setPriceFloatingRatio(itemSkuBasePrice.getPriceFloatingRatio());
				}
				//京东商品强制走大厅逻辑
				queryCommonItemSkuPriceDTO.setIsBoxFlag(0);
			}else{
				orderItemSkuPriceDTO.setCostPrice(itemSkuBasePrice.getSaleLimitedPrice());//非京东商品，使用分销限价做为成本价
			}
			//是否包厢
			if(queryCommonItemSkuPriceDTO.getIsBoxFlag()==1){
				orderItemSkuPriceDTO.setSalePrice(itemSkuBasePrice.getBoxSalePrice());
			}else{
				orderItemSkuPriceDTO.setSalePrice(itemSkuBasePrice.getAreaSalePrice());
			}
			
			orderItemSkuPriceDTO.setGoodsPrice(queryCommonItemSkuPrice.getResult().getSalesPrice());
			orderItemSkuPriceDTO.setGoodsPriceType(queryCommonItemSkuPrice.getResult().getGoodsPriceType());
			
			StandardPriceDTO standardPrice=new StandardPriceDTO();
			queryInnerItemSkuPrice(queryCommonItemSkuPriceDTO.getSkuId(), queryCommonItemSkuPriceDTO.getIsBoxFlag(), standardPrice);
			//商品单价种类 0 销售价 1 阶梯价 2 分组价 3 等级价 4 区域价 
			if(orderItemSkuPriceDTO.getGoodsPriceType()==2&&
					CollectionUtils.isNotEmpty(standardPrice.getItemSkuMemberGroupPriceList())){
				orderItemSkuPriceDTO.setGoodsPriceInfo(JSONArray.fromObject(standardPrice.getItemSkuMemberGroupPriceList()).toString());
			}
			if(orderItemSkuPriceDTO.getGoodsPriceType()==3&&
					CollectionUtils.isNotEmpty(standardPrice.getItemSkuMemberLevelPriceList())){
				orderItemSkuPriceDTO.setGoodsPriceInfo(JSONArray.fromObject(standardPrice.getItemSkuMemberLevelPriceList()).toString());
			}
			
			if(orderItemSkuPriceDTO.getGoodsPriceType()==4&&
					CollectionUtils.isNotEmpty(standardPrice.getAreaPriceList())){
				orderItemSkuPriceDTO.setGoodsPriceInfo(JSONArray.fromObject(standardPrice.getAreaPriceList()).toString());
			}
			
			result.setResult(orderItemSkuPriceDTO);
			result.setCode(ErrorCodes.SUCCESS.name());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> saveItemSkuTerminalPrice(ItemSkuTerminalPrice itemSkuTerminalPrice) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		try{
			itemSkuTerminalPriceMapper.insertSelective(itemSkuTerminalPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ItemSkuPriceService::saveItemSkuTerminalPrice:",e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateItemSkuTerminalPrice(ItemSkuTerminalPrice itemSkuTerminalPrice) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		try{
			itemSkuTerminalPriceMapper.updateByPrimaryKeySelective(itemSkuTerminalPrice);
			result.setCode(ErrorCodes.SUCCESS.name());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ItemSkuPriceService::updateItemSkuTerminalPrice:",e);
		}
		return result;
	}

	@Override
	public ExecuteResult<List<ItemSkuTerminalPrice>> queryItemSkuTerminalPriceBySkuId(Long skuId) {
		ExecuteResult<List<ItemSkuTerminalPrice>> result=new ExecuteResult<List<ItemSkuTerminalPrice>>();
		if(skuId==null||skuId<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}
		List<ItemSkuTerminalPrice> itemSkuTerminalPriceList=itemSkuTerminalPriceMapper.selectBySkuId(skuId);
		result.setResult(itemSkuTerminalPriceList);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<HzgPriceDTO> queryHzgTerminalPriceByTerminalType(Long skuId) {
		
		ExecuteResult<HzgPriceDTO> result=new ExecuteResult<HzgPriceDTO>();
		if(skuId==null||skuId<=0){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("skuId")));
			return result;
		}
		Map<String,Object> paramMap=Maps.newHashMap();
		paramMap.put("terminalType",TerminalTypeEnum.HZG_TYPE.getCode());
		paramMap.put("skuId",skuId);
		
		List<ItemSkuTerminalPrice> itemSkuTerminalPriceList=itemSkuTerminalPriceMapper.selectBySkuIdAndTerminalType(paramMap);
		HzgPriceDTO hzgPrice=new HzgPriceDTO();
		if(CollectionUtils.isEmpty(itemSkuTerminalPriceList)){
			result.setCode(ErrorCodes.SUCCESS.name());
			result.setResult(hzgPrice);
			return result;
		}
		
		for(ItemSkuTerminalPrice itemSkuTerminalPrice:itemSkuTerminalPriceList){
			if("0".equals(itemSkuTerminalPrice.getPriceType())){
				hzgPrice.setRetailPrice(itemSkuTerminalPrice.getPrice());
			}
			if("1".equals(itemSkuTerminalPrice.getPriceType())){
				hzgPrice.setSalePrice(itemSkuTerminalPrice.getPrice());
			}
			if("2".equals(itemSkuTerminalPrice.getPriceType())){
				hzgPrice.setVipPrice(itemSkuTerminalPrice.getPrice());
			}
		}
		
		result.setResult(hzgPrice);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> saveHzgTerminalPrice(HzgPriceInDTO hzgPriceInDTO) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(hzgPriceInDTO==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("hzgPriceInDTO")));
			return result;
		}
		
		if(hzgPriceInDTO.getItemId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("ItemId")));
			return result;
		}
		if(hzgPriceInDTO.getSkuId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuId")));
			return result;
		}
		
		if(hzgPriceInDTO.getSellerId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SellerId")));
			return result;
		}
		
		if(hzgPriceInDTO.getOperatorId()==null){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("OperatorId")));
			return result;
		}
		
		if(StringUtils.isEmpty(hzgPriceInDTO.getOperatorName())){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("OperatorName")));
			return result;
		}
		
		
		List<ItemSkuTerminalPrice> terminalPriceList=Lists.newArrayList();
		
		if(hzgPriceInDTO.getRetailPrice()!=null){
			ItemSkuTerminalPrice retailPrice=convertDTO2HzgTerminalPrice(hzgPriceInDTO.getRetailPrice(),hzgPriceInDTO.getOperatorId(),hzgPriceInDTO.getOperatorName(),
					hzgPriceInDTO.getSellerId(),hzgPriceInDTO.getItemId(),TerminalPriceType.RETAIL_PRICE.getCode(),hzgPriceInDTO.getSkuId(),
					hzgPriceInDTO.getShopId());
			terminalPriceList.add(retailPrice);
		}
		
		if(hzgPriceInDTO.getSalePrice()!=null){
			ItemSkuTerminalPrice salePrice=convertDTO2HzgTerminalPrice(hzgPriceInDTO.getSalePrice(),hzgPriceInDTO.getOperatorId(),hzgPriceInDTO.getOperatorName(),
					hzgPriceInDTO.getSellerId(),hzgPriceInDTO.getItemId(),TerminalPriceType.SALE_PRICE.getCode(),hzgPriceInDTO.getSkuId(),
					hzgPriceInDTO.getShopId());
			terminalPriceList.add(salePrice);
		}
		
		if(hzgPriceInDTO.getVipPrice()!=null){
			ItemSkuTerminalPrice vipPrice=convertDTO2HzgTerminalPrice(hzgPriceInDTO.getVipPrice(),hzgPriceInDTO.getOperatorId(),hzgPriceInDTO.getOperatorName(),
					hzgPriceInDTO.getSellerId(),hzgPriceInDTO.getItemId(),TerminalPriceType.VIP_PRICE.getCode(),hzgPriceInDTO.getSkuId(),
					hzgPriceInDTO.getShopId());
			terminalPriceList.add(vipPrice);
		}
		
		if(CollectionUtils.isNotEmpty(terminalPriceList)){
			itemSkuTerminalPriceMapper.deleteTerminalByItemId(hzgPriceInDTO.getItemId());
			itemSkuTerminalPriceMapper.insertBatch(terminalPriceList);
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}
	
	private ItemSkuTerminalPrice convertDTO2HzgTerminalPrice(BigDecimal price,Long operatorId,String operatorName,Long sellerId,Long itemId,
			String priceType,Long skuId,Long shopId){
		ItemSkuTerminalPrice terminalPrice=new ItemSkuTerminalPrice();
		terminalPrice.setCreateId(operatorId);
		terminalPrice.setCreateName(operatorName);
		terminalPrice.setItemId(itemId);
		terminalPrice.setModifyId(operatorId);
		terminalPrice.setModifyName(operatorName);
		terminalPrice.setPrice(price);
		terminalPrice.setPriceType(priceType);
		terminalPrice.setSellerId(sellerId);
		terminalPrice.setSkuId(skuId);
		terminalPrice.setTerminalType("0");
		terminalPrice.setShopId(shopId);
		return terminalPrice;
	}

	@Override
	public List<ItemSkuLadderPrice> queryMobileExternalLadderPriceBySkuId(List<String> skuIds) {
		return itemSkuLadderPriceMapper.getSkuLadderPriceMobile(skuIds);
	}
}
