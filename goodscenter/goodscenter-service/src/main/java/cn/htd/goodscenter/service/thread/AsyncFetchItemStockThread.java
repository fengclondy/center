package cn.htd.goodscenter.service.thread;

import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuTotalStockMapper;

public class AsyncFetchItemStockThread implements Runnable{
	private String itemCode;
	private Long operatorId;
	private String operatorName;
	private ItemSkuTotalStockMapper itemSkuTotalStockMapper;
	private String supplierCode;
	private ItemMybatisDAO itemMybatisDAO;
	public AsyncFetchItemStockThread(){
		
	}
	
	public AsyncFetchItemStockThread(String itemCode,ItemSkuTotalStockMapper itemSkuTotalStockMapper,Long operatorId,String operatorName){
		this.itemCode=itemCode;
		this.itemSkuTotalStockMapper=itemSkuTotalStockMapper;
		this.operatorId=operatorId;
		this.operatorName=operatorName;
	}
	
	@Override
	public void run() {
//		String accessToken=MiddlewareInterfaceUtil.getAccessToken();
//		QueryItemStockInDTO queryItemStockInDTO=new QueryItemStockInDTO();
//		if(StringUtils.isEmpty(accessToken)){
//			return;	
//		}
//		queryItemStockInDTO.setToken(accessToken);
//		List<Map<String,Object>> param=Lists.newArrayList();
//		Map<String,Object> productCodeMap=Maps.newHashMap();
//		productCodeMap.put("productCode", itemCode);
//		param.add(productCodeMap);
//		Map<String,Object> supplierCodeParam=Maps.newHashMap();
//		supplierCodeParam.put("supplierCode", operatorId);
//		param.add(supplierCodeParam);
//		queryItemStockInDTO.setData(param);
//		
//		MiddlewareInterfaceUtil.getItemStockList(supplyierCode, spuCodeList)
//		//更新数据库
//		for(QueryItemStockOutDTO itemStockOutDTO:resultList){
//			itemSkuTotalStockMapper.updateItemTotalStockBySkuCodeAndSellerId(Long.valueOf(itemStockOutDTO.getStoreNum()), 
//					operatorId, operatorName, itemStockOutDTO.getProductCode(), itemStockOutDTO.getSupplierCode());
//		}
	}

}
