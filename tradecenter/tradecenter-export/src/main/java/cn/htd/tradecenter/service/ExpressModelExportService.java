package cn.htd.tradecenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.ExpressModelDTO;

/**
 * <p>
 * Description: [快递单打印接口]
 * </p>
 */
public interface ExpressModelExportService {
	/***
	 * 
	 * <p>
	 * Discription:[根据条件查询快递单模版]
	 * </p>
	 */
	public ExecuteResult<ExpressModelDTO> getExpressModelByID(ExpressModelDTO expressModelDto);

	/**
	 * 
	 * <p>
	 * Discription:[查询卖家的模版列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ExpressModelDTO>> getExpressModelList(ExpressModelDTO expressModelDto, Pager<ExpressModelDTO> pager);

	/**
	 * 
	 * <p>
	 * Discription:[新增快递单模版]
	 * </p>
	 */
	public ExecuteResult<String> addExpressModel(ExpressModelDTO expressModelDto);

	/**
	 * 
	 * <p>
	 * Discription:[根据快递单模版id，删除模版]
	 * </p>
	 */
	public ExecuteResult<String> deteleExpressModel(String emid);

	/**
	 * 
	 * <p>
	 * Discription:[修改快递单模版信息]
	 * </p>
	 */
	public ExecuteResult<String> modifyExpressModel(ExpressModelDTO expressModelDto);

	/**
	 * 
	 * <p>
	 * Discription:[根据模版名称查询模版信息]
	 * </p>
	 */
	public ExecuteResult<List<ExpressModelDTO>> getExpressModelByName(ExpressModelDTO expressModelDto);

	/**
	 * 
	 * <p>
	 * Discription:[查询系统模版，根据快递公司]
	 * </p>
	 */
	public ExpressModelDTO getExpressSystemModel(Integer deliveryId);
}
