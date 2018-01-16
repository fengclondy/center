package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.PurchaseChannelsDTO;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface PurchaseChannelsDAO extends BaseDAO<PurchaseChannelsDTO>{
	 public List<PurchaseChannelsDTO> selectAll(@Param("page") Pager page);

	    public Long selectAllCount();

	    List<PurchaseChannelsDTO> selectByName(String name);

		public void updateOrderStatus(PurchaseChannelsDTO query);

		public List<PurchaseChannelsDTO> queryPurchaseChannelsList();

}