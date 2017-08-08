package cn.htd.goodscenter.service.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;

public class VenusItemSkuShelfStatusUtil {
	/**
	 * 3 为包厢和大厅上架，2为区域上架，1为包厢上架，0为未上架
	 * 
	 * @param list
	 * @return
	 */
	public static String judgeItemSkuShelfStatus(List<ItemSkuPublishInfo> list){
	  
		if(CollectionUtils.isEmpty(list)){
			return "0";
		}
		if(list.size()==2){
			return "3";
		}
		ItemSkuPublishInfo itemSkuPublisInfo=list.get(0);
		
		if(itemSkuPublisInfo ==null){
			return "0";
		}
		if(itemSkuPublisInfo.getIsBoxFlag()==1){
			return "1";
		}
		
		if(itemSkuPublisInfo.getIsBoxFlag()==0){
			return "2";
		}
		return "0";
	}
}
