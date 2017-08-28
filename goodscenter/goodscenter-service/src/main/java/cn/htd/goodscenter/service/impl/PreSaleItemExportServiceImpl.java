package cn.htd.goodscenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.AddressUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSalesAreaDetailMapper;
import cn.htd.goodscenter.dao.ItemSalesAreaMapper;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.presale.PreSaleProdQueryDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProductSaleAreaDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.PreSaleItemExportService;
import cn.htd.membercenter.dto.SellerInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.google.common.collect.Lists;

@Service("preSaleItemExportService")
public class PreSaleItemExportServiceImpl implements PreSaleItemExportService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PreSaleItemExportServiceImpl.class);

	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private MemberBaseInfoService memberBaseInfoService;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ItemSalesAreaMapper itemSalesAreaMapper;
	@Resource
	private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;
	@Resource
	private AddressUtils addressUtils;

	@Override
	public ExecuteResult<PreSaleProdQueryDTO> queryPreSaleProdInfo(String skuCode) {
		ExecuteResult<PreSaleProdQueryDTO> result=new ExecuteResult<PreSaleProdQueryDTO>();
		try{
			if(StringUtils.isEmpty(skuCode)){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
				return result;
			}

			PreSaleProdQueryDTO preSaleProdQueryDTO=itemMybatisDAO.queryPreSaleItemInfo(skuCode);

			boolean isHtdDepartment=false;

			//补足会员信息
			if(preSaleProdQueryDTO!=null&&preSaleProdQueryDTO.getSellerId()!=null){
				 ExecuteResult<SellerInfoDTO> selleInfoResult=memberBaseInfoService.querySellerBaseInfo(preSaleProdQueryDTO.getSellerId());

				 if(selleInfoResult!=null&&selleInfoResult.isSuccess()&&selleInfoResult.getResult()!=null){
					 SellerInfoDTO sellerInfo=selleInfoResult.getResult();
					 preSaleProdQueryDTO.setSellerCode(sellerInfo.getMemberCode());
					 preSaleProdQueryDTO.setSellerName(sellerInfo.getCompanyName());
					 if(1==preSaleProdQueryDTO.getIsPreSell()){
						//公司编码是0801的为总部预售
						if("0801".equals(sellerInfo.getCompanyCode())){
							isHtdDepartment=true;
							preSaleProdQueryDTO.setIsPreSell(2);
						}else{
							preSaleProdQueryDTO.setIsPreSell(3);
						}
					 }

				 }
			}

			//补足库存信息

			ItemSkuPublishInfo itemSkuPublishInfo=itemSkuPublishInfoMapper.selectBySkuCodeAndShelfType(preSaleProdQueryDTO.getSkuCode(),isHtdDepartment ? 0 : 1);

			if(itemSkuPublishInfo!=null){
				preSaleProdQueryDTO.setKcNum(itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity()); // 虚拟总库存 - 锁定库存
				preSaleProdQueryDTO.setListStatus(itemSkuPublishInfo.getIsVisable()==1 ?1 : 2);
			}else{
				preSaleProdQueryDTO.setKcNum(0);
				preSaleProdQueryDTO.setListStatus(2);
			}

			//查询价格

			ExecuteResult<HzgPriceDTO> hzgPriceResult=itemSkuPriceService.queryHzgTerminalPriceByTerminalType(preSaleProdQueryDTO.getSkuId());

			if(hzgPriceResult!=null&&hzgPriceResult.isSuccess()&&hzgPriceResult.getResult()!=null){
				HzgPriceDTO hzgPrice=hzgPriceResult.getResult();
				preSaleProdQueryDTO.setSalePrice(hzgPrice.getSalePrice());
				preSaleProdQueryDTO.setRecommendPrice(hzgPrice.getRetailPrice());
				preSaleProdQueryDTO.setVipPrice(hzgPrice.getVipPrice());
			}

			//查询类目
			ExecuteResult<Map<String, Object>> catResult=itemCategoryService.queryItemOneTwoThreeCategoryName(preSaleProdQueryDTO.getThirdCatId(), ",");

			if(catResult!=null&&catResult.isSuccess()&&MapUtils.isNotEmpty(catResult.getResult())){
				Map<String, Object> cat=catResult.getResult();
				if(cat.get("firstCategoryId")!=null){
					preSaleProdQueryDTO.setFirstCatId(Long.valueOf(cat.get("firstCategoryId")+""));
				}

				if(cat.get("firstCategoryName")!=null){
					preSaleProdQueryDTO.setFirstCatName(cat.get("firstCategoryName")+"");
				}

				if(cat.get("secondCategoryId")!=null){
					preSaleProdQueryDTO.setSecondCatId(Long.valueOf(cat.get("secondCategoryId")+""));
				}

				if(cat.get("secondCategoryName")!=null){
					preSaleProdQueryDTO.setSecondCatName(cat.get("secondCategoryName")+"");
				}
			}
			//查询销售区域
//			ItemSalesArea itemSalesArea=itemSalesAreaMapper.selectByItemId(preSaleProdQueryDTO.getItemId(), isHtdDepartment ? "2" : "1");
//			if(itemSalesArea!=null){
//				preSaleProdQueryDTO.setIsWholeCountry(itemSalesArea.getIsSalesWholeCountry());
//			}
//			if(itemSalesArea!=null&&itemSalesArea.getIsSalesWholeCountry()!=1){
//				 List<ItemSalesAreaDetail> itemSalesAreaDetailList=itemSalesAreaDetailMapper.selectAreaDetailsBySalesAreaIdAll(itemSalesArea.getSalesAreaId());
//				 List<PreSaleItemRegion> preSaleItemRegionList=Lists.newArrayList();
//				 if(CollectionUtils.isNotEmpty(itemSalesAreaDetailList)){
//					 for(ItemSalesAreaDetail itemSalesAreaDetail:itemSalesAreaDetailList){
//						 PreSaleItemRegion preSaleItemRegion=new PreSaleItemRegion();
//						 preSaleItemRegion.setRegionCode(itemSalesAreaDetail.getAreaCode());
//						 AddressInfo addressInfo=addressUtils.getAddressName(itemSalesAreaDetail.getAreaCode());
//						 if(addressInfo!=null){
//							 preSaleItemRegion.setRegionName(addressInfo.getName());
//						 }
//						 preSaleItemRegion.setRegionType(itemSalesAreaDetail.getSalesAreaType());
//						 preSaleItemRegionList.add(preSaleItemRegion);
//					 }
//				 }
//				 preSaleProdQueryDTO.setPreSaleItemRegionList(preSaleItemRegionList);
//			}
			
			List<PreSaleProductSaleAreaDTO> preSaleItemRegionList=parseSaleArea(preSaleProdQueryDTO.getItemId(),isHtdDepartment ? "2" : "1");
			preSaleProdQueryDTO.setPreSaleItemRegionList(preSaleItemRegionList);

			result.setCode(ErrorCodes.SUCCESS.name());
			result.setResult(preSaleProdQueryDTO);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("PreSaleItemExportServiceImpl::queryPreSaleProdInfo",e);
			result.setCode(ErrorCodes.E00001.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E00001.getErrorMsg()));

		}

		return result;
	}
	
	 private List<PreSaleProductSaleAreaDTO> parseSaleArea(Long itemId, String shelfType) {
	        List<PreSaleProductSaleAreaDTO> preSaleProductSaleAreaDTOs = new ArrayList<>();
	        ItemSalesArea salesAreaPublic = itemSalesAreaMapper.selectByItemId(itemId, shelfType);
	        if (null != salesAreaPublic) {
	            if (salesAreaPublic.getIsSalesWholeCountry().intValue() == 0) {
	                // TODO :详情
	                Long salesAreaId = salesAreaPublic.getSalesAreaId();
	                List<ItemSalesAreaDetail> salesAreaDetailList = itemSalesAreaDetailMapper.selectAreaDetailsBySalesAreaIdAll(salesAreaId);
	                List<String> provinces = new ArrayList<>(); // 省集合
	                List<String> citys = new ArrayList<>(); // 市集合
	                List<String> countrys = new ArrayList<>(); // 区集合
	                for (ItemSalesAreaDetail area : salesAreaDetailList) {
	                    if ("1".equals(area.getSalesAreaType())) {
	                        if (area.getAreaCode().length() == 2) {
	                            provinces.add(area.getAreaCode());
	                        }
	                    }
	                    if ("2".equals(area.getSalesAreaType())) {
	                        if (area.getAreaCode().length() == 4) {
	                            citys.add(area.getAreaCode());
	                        }
	                    }
	                    if ("3".equals(area.getSalesAreaType())) {
	                        if (area.getAreaCode().length() == 6) {
	                            countrys.add(area.getAreaCode());
	                        }
	                    }
	                }
	                for (String area : countrys) { // 从三级开始循环
	                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
	                    preSaleProductSaleAreaDTO.setRegionCounty(area);
	                    preSaleProductSaleAreaDTO.setRegionCity(area.substring(0, 4));
	                    preSaleProductSaleAreaDTO.setRegionProvince(area.substring(0, 2));
	                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCounty()) == null) {
	                        continue;
	                    }
	                    preSaleProductSaleAreaDTO.setRegion(
	                            addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName()
	                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()).getName()
	                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCounty()).getName());
	                    if (citys.contains(preSaleProductSaleAreaDTO.getRegionCity())) {
	                        citys.remove(preSaleProductSaleAreaDTO.getRegionCity());
	                    }
	                    if (provinces.contains(preSaleProductSaleAreaDTO.getRegionProvince())) {
	                        provinces.remove(preSaleProductSaleAreaDTO.getRegionProvince());
	                    }
	                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
	                }
	                for (String area : citys) { // 循环没有三级的二级
	                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
	                    preSaleProductSaleAreaDTO.setRegionCity(area);
	                    preSaleProductSaleAreaDTO.setRegionProvince(area.substring(0, 2));
	                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()) == null) {
	                        continue;
	                    }
	                    preSaleProductSaleAreaDTO.setRegion(
	                            addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName()
	                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()).getName());
	                    if (provinces.contains(preSaleProductSaleAreaDTO.getRegionProvince())) {
	                        provinces.remove(preSaleProductSaleAreaDTO.getRegionProvince());
	                    }
	                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
	                }
	                for (String area : provinces) { // 循环没有二级的一级
	                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
	                    preSaleProductSaleAreaDTO.setRegionProvince(area);
	                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()) == null) {
	                        continue;
	                    }
	                    preSaleProductSaleAreaDTO.setRegion(addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName());
	                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
	                }
	            }
	        }
	        return preSaleProductSaleAreaDTOs;
	    }

}
