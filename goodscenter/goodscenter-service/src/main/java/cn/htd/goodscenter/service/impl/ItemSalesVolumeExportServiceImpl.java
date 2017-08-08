package cn.htd.goodscenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.ItemSalesVolumeDAO;
import cn.htd.goodscenter.dto.ItemSalesVolumeDTO;
import cn.htd.goodscenter.service.ItemSalesVolumeExportService;

@Service("itemSalesVolumeExportService")
public class ItemSalesVolumeExportServiceImpl implements ItemSalesVolumeExportService {

	@Resource
	private ItemSalesVolumeDAO itemSalesVolumeDAO;

	@Override
	public ExecuteResult<String> updateItemSalesVolume(List<ItemSalesVolumeDTO> inList) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (inList == null || inList.size() <= 0) {
			result.addErrorMessage("入参为空！");
			return result;
		}
		this.itemSalesVolumeDAO.deleteAll();
		this.itemSalesVolumeDAO.addList(inList);
		return result;
	}

	@Override
	public List<ItemSalesVolumeDTO> querySaleVolumeBySyncTime(Date syncTime, Pager pager) {
		return itemSalesVolumeDAO.querySaleVolumeBySyncTime(syncTime, pager);
	}

}
