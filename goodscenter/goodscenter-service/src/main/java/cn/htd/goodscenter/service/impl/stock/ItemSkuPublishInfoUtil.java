package cn.htd.goodscenter.service.impl.stock;

import java.util.Date;
import java.util.Map;

import cn.htd.common.util.SpringApplicationContextHolder;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.dao.ItemSkuTotalStockMapper;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuTotalStock;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;

import com.google.common.collect.Maps;

public class ItemSkuPublishInfoUtil {
	public static void doUpdateItemSkuPublishInfo(
			Long operatorId,String operatorName, ItemSku itemSku,
			Integer stockNum, ItemSkuPublishInfo itemSkuPublishInfo) {
		
		ItemSkuPublishInfoMapper itemSkuPublishInfoMapper=SpringApplicationContextHolder.getBean(ItemSkuPublishInfoMapper.class);
		ItemMybatisDAO itemMybatisDAO=SpringApplicationContextHolder.getBean(ItemMybatisDAO.class);
		ItemSkuTotalStockMapper itemSkuTotalStockMapper=SpringApplicationContextHolder.getBean(ItemSkuTotalStockMapper.class);
		if(itemSkuPublishInfoMapper==null||itemMybatisDAO==null||itemSkuTotalStockMapper==null){
			return;
		}
		 //更新实际库存
		ItemSkuTotalStock totalStock=itemSkuTotalStockMapper.queryBySkuId(itemSkuPublishInfo.getSkuId());
		//勾选过同步标记
		if(1==itemSkuPublishInfo.getErpSync()&&totalStock!=null&&totalStock.getInventory()!=null){
//			 String shelfType=itemSkuPublishInfo.getIsBoxFlag()==1 ? "2":"1";
//
//		     ItemSkuPublishInfo anotherPublishinfo=itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSkuPublishInfo.getSkuId(),shelfType, null);

		    // Integer anotherDisplayQuantity=anotherPublishinfo==null?0:anotherPublishinfo.getDisplayQuantity();

		     Integer moreStockQuantity = stockNum-totalStock.getInventory(); 
		     
		     //如果实际库存已经变多，则加到对应可见库存上去
		     if(moreStockQuantity>0){
//		    	  itemSkuPublishInfo.setDisplayQuantity(itemSkuPublishInfo.getDisplayQuantity() + moreStockQuantity);
//		    	  itemSkuPublishInfo.setModifyId(operatorId);
//		          itemSkuPublishInfo.setModifyName(operatorName);
//		          itemSkuPublishInfo.setModifyTime(new Date());
		          Map<String,Object> paramMap=Maps.newHashMap();
				  paramMap.put("id", itemSkuPublishInfo.getId());
				  paramMap.put("operatorId", operatorId);
				  paramMap.put("operatorName", operatorName);
				  paramMap.put("moreStockQuantity", moreStockQuantity);
				  itemSkuPublishInfoMapper.updateDisplayQuantityByPk(paramMap);
//		          itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
		     }
		   
		}
		
		//只判断小于的逻辑，如果不足且处于上架状态，则下架
		if(stockNum<itemSkuPublishInfo.getDisplayQuantity()&&itemSkuPublishInfo.getIsVisable()==1){
		 	 //执行下架
//		 	 itemSkuPublishInfo.setModifyId(operatorId);
//		     itemSkuPublishInfo.setModifyName(operatorName);
//		     itemSkuPublishInfo.setModifyTime(new Date());
//		     itemSkuPublishInfo.setInvisableTime(new Date());
//		     itemSkuPublishInfo.setIsVisable(0);
//		     itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
		     Map<String,Object> paramMap=Maps.newHashMap();
		     paramMap.put("id", itemSkuPublishInfo.getId());
		     paramMap.put("operatorId", operatorId);
		     paramMap.put("operatorName", operatorName);
		     itemSkuPublishInfoMapper.updateVisibleStateByPk(paramMap);
		     //判断当前商品是否存在上架状态数据，如果没有置为下架状态
		     Integer itemSkuOnShelvedCount=itemSkuPublishInfoMapper.queryItemSkuOnShelvedCount(itemSku.getSkuId());
		     if(itemSkuOnShelvedCount==null||itemSkuOnShelvedCount<=0){
		         itemMybatisDAO.updateItemStatusByPk(itemSku.getItemId(), HtdItemStatusEnum.NOT_SHELVES.getCode(), 0L, "system");
		     }
		 }
		
		
		if(totalStock==null){
			totalStock=new ItemSkuTotalStock();
			totalStock.setItemId(itemSku.getItemId());
			totalStock.setSkuCode(itemSku.getSkuCode());
			totalStock.setInventory(stockNum);
			totalStock.setLastStockSyncTime(new Date());
			totalStock.setCreateId(0L);
			totalStock.setCreateName("system");
			totalStock.setCreateTime(new Date());
			totalStock.setModifyId(0L);
			totalStock.setModifyName("system");
			totalStock.setModifyTime(new Date());
			totalStock.setSellerId(itemSku.getSellerId());
			itemSkuTotalStockMapper.insertSelective(totalStock);
		}else{
			totalStock.setInventory(stockNum);
			totalStock.setModifyId(0L);
			totalStock.setModifyName("system");
			totalStock.setModifyTime(new Date());
			totalStock.setLastStockSyncTime(new Date());
			itemSkuTotalStockMapper.updateByPrimaryKey(totalStock);
		}
	}
}
