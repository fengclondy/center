package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.TranslationInfoDTO;
import cn.htd.goodscenter.dto.TranslationMatDTO;
import cn.htd.goodscenter.dto.TranslationOrderDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import java.util.Map;

/**
 * <p>
 * Description: [商品SKU求购Service]
 * </p>
 */
public interface TranslationExportService {
	/**
	 * <p>
	 * Discription:[根据条件查询求购详情]
	 * </p>
	 */
	public ExecuteResult<TranslationInfoDTO> queryByTranslationInfo(TranslationInfoDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询求购的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<TranslationInfoDTO>> queryTranslationInfoList(TranslationInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询求购的列表包含物料名称等信息]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryTranslationInfoPager(TranslationInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询求购明细详情]
	 * </p>
	 */
	public ExecuteResult<TranslationMatDTO> queryByTranslationMat(TranslationMatDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询求购明细的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryTranslationMatList(TranslationMatDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询求购订单详情]
	 * </p>
	 */
	public ExecuteResult<TranslationOrderDTO> queryByTranslationOrder(TranslationOrderDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询求购订单的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<TranslationOrderDTO>> queryTranslationOrderList(TranslationOrderDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[生成求购详情]
	 * </p>
	 */
	public ExecuteResult<String> addTranslationInfo(TranslationInfoDTO dto);

	/**
	 * <p>
	 * Discription:[生成求购详情]
	 * </p>
	 * 
	 */
	public ExecuteResult<String> addTranslationMat(TranslationMatDTO dto);

	/**
	 * <p>
	 * Discription:[生成求购订单]
	 * </p>
	 */
	public ExecuteResult<String> addTranslationOrder(TranslationOrderDTO dto);

	/**
	 * <p>
	 * Discription:[修改求购详情]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationInfo(TranslationInfoDTO dto);

	/**
	 * <p>
	 * Discription:[修改求购明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationMat(TranslationMatDTO dto);

	/**
	 * <p>
	 * Discription:[修改求购明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationOrder(TranslationOrderDTO dto);

	/**
	 * <p>
	 * Discription:[生成询价编码]
	 * </p>
	 */
	public ExecuteResult<String> createTranslationNo();
}
