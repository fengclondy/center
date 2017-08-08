package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.BaseValueModelDTO;
import cn.htd.common.DataGrid;

/**
 * Created by thinkpad on 2016/12/20.
 */
public interface BaseValueModelService {

	/**
	 * 根据SellerId查询最大周期数据
	 */
	DataGrid<BaseValueModelDTO> queryNewValueBySellerId(Long sellerId);
	
	/**
	 * 根据公司编码查询最大周期数据
	 * 
	 * @param companyCode
	 * @return
	 */
	DataGrid<BaseValueModelDTO> queryNewValueByCompanyCode(String companyCode);

}
