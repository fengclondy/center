package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.ContractOrderDTO;
import cn.htd.goodscenter.dto.ContractPaymentTermDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.ContractInfoDTO;
import cn.htd.goodscenter.dto.ContractMatDTO;

import java.util.Map;

/**
 * <p>
 * Description: [商品SKU协议Service]
 * </p>
 */
public interface ProtocolExportService {
	/**
	 * <p>
	 * Discription:[根据条件查询协议详情]
	 * </p>
	 */
	public ExecuteResult<ContractInfoDTO> queryByContractInfo(ContractInfoDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoList(ContractInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表包含物料名称等信息]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoPager(ContractInfoDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细详情]
	 * </p>
	 */
	public ExecuteResult<ContractMatDTO> queryByContractMat(ContractMatDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryContractMatList(ContractMatDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单详情]
	 * </p>
	 */
	public ExecuteResult<ContractOrderDTO> queryByContractOrder(ContractOrderDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractOrderDTO>> queryContractOrderList(ContractOrderDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[生成协议详情]
	 * </p>
	 */
	public ExecuteResult<String> addContractInfo(ContractInfoDTO dto);

	/**
	 * <p>
	 * Discription:[生成协议明细]
	 * </p>
	 */
	public ExecuteResult<String> addContractMat(ContractMatDTO dto);

	/**
	 * <p>
	 * Discription:[生成协议订单]
	 * </p>
	 */
	public ExecuteResult<String> addContractOrder(ContractOrderDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议详情]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractInfo(ContractInfoDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractMat(ContractMatDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractOrder(ContractOrderDTO dto);

	/**
	 * <p>
	 * Discription:[生成协议编码]
	 * </p>
	 */
	public ExecuteResult<String> createContractNo();

	/**
	 * <p>
	 * Discription:[根据条件查询协议账期]
	 * </p>
	 */
	public ExecuteResult<ContractPaymentTermDTO> queryByContractPaymentTerm(ContractPaymentTermDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询协议账期列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractPaymentTermDTO>> queryContractPaymentTermList(ContractPaymentTermDTO dto,	Pager page);

	/**
	 * <p>
	 * Discription:[生成协议账期]
	 * </p>
	 */
	public ExecuteResult<String> addContractPaymentTerm(ContractPaymentTermDTO dto);

	/**
	 * <p>
	 * Discription:[修改协议账期]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractPaymentTerm(ContractPaymentTermDTO dto);
}
