package cn.htd.goodscenter.service;

import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.InquiryInfoDTO;
import cn.htd.goodscenter.dto.InquiryMatDTO;
import cn.htd.goodscenter.dto.InquiryOrderDTO;

/**
 * <p>
 * Description: [商品SKU协议Service]
 * </p>
 */
public interface InquiryExportService {
	/**
	 * <p>
	 * Discription:[根据条件查询协议详情]
	 * </p>
	 */
	public ExecuteResult<InquiryInfoDTO> queryByInquiryInfo(InquiryInfoDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<InquiryInfoDTO>> queryInquiryInfoList(InquiryInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表包含物料名称等信息]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryInquiryInfoPager(InquiryInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细详情]
	 * </p>
	 */
	public ExecuteResult<InquiryMatDTO> queryByInquiryMat(InquiryMatDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryInquiryMatList(InquiryMatDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单详情]
	 * </p>
	 */
	public ExecuteResult<InquiryOrderDTO> queryByInquiryOrder(InquiryOrderDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<InquiryOrderDTO>> queryInquiryOrderList(InquiryOrderDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[生成协议详情]
	 * </p>
	 */
	public ExecuteResult<String> addInquiryInfo(InquiryInfoDTO dto);

	/**
	 * <p>
	 * Discription:[生成协议详情]
	 * </p>
	 */
	public ExecuteResult<String> addInquiryMat(InquiryMatDTO dto);

	/**
	 * <p>
	 * Discription:[生成协议订单]
	 * </p>
	 */
	public ExecuteResult<String> addInquiryOrder(InquiryOrderDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议详情]
	 * </p>
	 */
	public ExecuteResult<String> modifyInquiryInfo(InquiryInfoDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议详情根据ID]
	 * </p>
	 */
	public ExecuteResult<String> modifyInquiryInfoById(InquiryInfoDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyInquiryMat(InquiryMatDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyInquiryOrder(InquiryOrderDTO dto);

	/**
	 * <p>
	 * Discription:[生成询价编码]
	 * </p>
	 */
	public ExecuteResult<String> createInquiryNo();
}
