package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.ItemSalesVolumeDTO;

public interface ItemSalesVolumeExportService {

	public ExecuteResult<String> updateItemSalesVolume(List<ItemSalesVolumeDTO> inList);

	public List<ItemSalesVolumeDTO> querySaleVolumeBySyncTime(Date syncTime,@Param("page") Pager pager);
}
