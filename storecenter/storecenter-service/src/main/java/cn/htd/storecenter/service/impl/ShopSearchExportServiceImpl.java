package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopSearchDAO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopSearchExportService;

@Service("shopSearchExportService")
public class ShopSearchExportServiceImpl implements ShopSearchExportService {

	private static final Logger logger = LoggerFactory.getLogger(ShopSearchExportServiceImpl.class);

	@Resource
	private ShopSearchDAO shopSearchDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<DataGrid<ShopDTO>> searchShop(ShopDTO inDTO, Pager page) {
		logger.info("============开始搜索店铺查询==============");
		ExecuteResult<DataGrid<ShopDTO>> result = new ExecuteResult<DataGrid<ShopDTO>>();
		DataGrid<ShopDTO> dg = new DataGrid<ShopDTO>();
		List<ShopDTO> shops = this.shopSearchDAO.searchShop(inDTO, page);
		dg.setRows(shops);
		dg.setTotal(this.shopSearchDAO.searchShopCount(inDTO));
		result.setResult(dg);
		logger.info("数据：" + JSON.toJSONString(dg));
		logger.info("============结束搜索店铺查询==============");
		return result;
	}

}
