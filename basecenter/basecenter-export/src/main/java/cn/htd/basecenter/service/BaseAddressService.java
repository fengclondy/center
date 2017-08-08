package cn.htd.basecenter.service;

import java.util.List;

import cn.htd.basecenter.dto.BaseAddressErpResult;
import cn.htd.basecenter.dto.AddressInfo;
import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [省市区县服务]
 * </p>
 */
public  interface BaseAddressService {

	/**
	 * 根据地址编码获取地址名称（编码转换用）
	 */
	public String getAddressName(String addressCode);

	/**
	 * 根据地址编码获取下一级地址名称（省市区镇下拉框用）
	 */
	public List<AddressInfo> getAddressList(String parentCode);

	/**
	 * 查询下一级地址列表
	 */
	public List<BaseAddressDTO> queryBaseAddressList(String parentCode);

	/**
	 * 查询下一级地址列表
	 */
	public DataGrid<BaseAddressDTO> queryBaseAddressList(String parentCode, Pager<BaseAddressDTO> pager);

	/**
	 * 根据ID查询地址信息
	 *
	 */
	public ExecuteResult<BaseAddressDTO> queryBaseAddressById(Long id);

	/**
	 * 根据编码查询地址信息
	 *
	 */
	public ExecuteResult<BaseAddressDTO> queryBaseAddressByCode(String code);

	/**
	 * 添加地址信息
	 *
	 */
	public ExecuteResult<BaseAddressDTO> addBaseAddress(BaseAddressDTO baseAddressDTO);

	/**
	 * 修改地址信息
	 *
	 */
	public ExecuteResult<BaseAddressDTO> updateBaseAddress(BaseAddressDTO baseAddressDTO);

	/**
	 * 删除地址信息
	 *
	 */
	public ExecuteResult<BaseAddressDTO> deleteBaseAddress(BaseAddressDTO baseAddressDTO);
	
	/**
	 * 地址信息下行ERP回调接口
	 */
	public ExecuteResult<String> callbackBaseAddress2Erp(BaseAddressErpResult erpResult);

	/**
	 * 查询下行erp异常的基础地址列表
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BaseAddressDTO>> queryBaseAddress2ErpException(Pager pager);

	/**
	 * 下行基础地址信息到ERP
	 * @param baseAddressDTO
	 * @return
	 */
	public ExecuteResult<String> downBaseAddress2Erp(BaseAddressDTO baseAddressDTO);

}
