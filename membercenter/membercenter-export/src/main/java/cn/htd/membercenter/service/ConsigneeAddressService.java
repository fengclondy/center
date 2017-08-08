package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberJDAddress;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: ConsigneeAddressService
 * </p>
 * 
 * @author thinkpad
 * @date 2016年12月5日
 *       <p>
 *       Description: 收货地址查询与修改
 *
 *       </p>
 */
public interface ConsigneeAddressService {

	/**
	 * 查询收货地址信息列表
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressList(
			Pager page, Long memberId);

	/**
	 * 超级会员 查询收货地址信息列表
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressIds(
			Pager page, Long memberId);

	/**
	 * 查询单个收货地址信息
	 * 
	 * @param addressId
	 */
	public MemberConsigAddressDTO selectConsigAddressID(Long addressId);

	/**
	 * 修改收货人信息,参数memberConsigAddressDto中要传memberId
	 * 
	 * @param addressId
	 * @return
	 */
	public ExecuteResult<String> updateConsigInfo(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 修改发票信息,参数memberConsigAddressDto中要传memberId
	 * 
	 * @param invoiceId
	 * @return
	 */
	public ExecuteResult<String> updatInvoiceInfo(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 根据收货地址ID修改收货地址信息，参数memberConsigAddressDto中要传addressId
	 * 
	 * @param memberConsigAddressDto
	 * @return
	 */
	public ExecuteResult<String> updateConsigAddressInfo(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 根据收货地址ID和发票ID修改收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public ExecuteResult<String> updateAddressList(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 新增收货地址和发票地址信息
	 * 
	 * @param memberConsigAddressDto
	 * @return
	 */
	public ExecuteResult<String> insertAddress(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 新增收货地址信息
	 * 
	 * @param memberConsigAddressDto
	 * @return
	 */
	public ExecuteResult<String> insertConsigAddress(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 删除单个收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public ExecuteResult<String> deleteConsigAddressInfo(
			Long addressId,
			Long modifyId,
			String modifyName);

	/**
	 * 批量删除收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public ExecuteResult<String> deleteConsigAddressInfoBatch(String addressId);

	/**
	 * 查询默认收货地址
	 * */
	public ExecuteResult<MemberConsigAddressDTO> selectDefaultAddress(
			Long memberId);

	/**
	 * 通过会员code和外部渠道编码查询地址信息
	 * 
	 * @param messageId
	 * @param memberCode
	 * @param channelCode
	 * @return
	 */
	public ExecuteResult<MemberConsigAddressDTO> selectChannelAddressDTO(
			String messageId, String memberCode, String channelCode);
	/**
	 * 根据memberId,城市，省code返回地址集合list
	 * @param memberId
	 * @param consigneeAddressCity
	 * @param consigneeAddressProvince
	 * @Param sort 排序
	 * @return
	 */
	public ExecuteResult<List<MemberConsigAddressDTO>> selectAddressCityList(
			Long memberId,String consigneeAddressCity,String consigneeAddressProvince
			,String sort);
	/**
	 * 
	 * @param memberCode
	 * @param channelCode  取字典里面code(PRODUCT_CHANNEL_JD) 获取code例子：DictionaryConst.OPT_PRODUCT_CHANNEL_JD
	 * @return
	 */
	public ExecuteResult<MemberConsigAddressDTO> selectChannelAddress(String memberCode, String channelCode);
	
	
	/**
	 * 查询收货地址信息列表
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressListByPlus(
			Pager page, Long memberId);
	
	/**
	 * 查询所有的商品+收货地址
	 * 
	 * @author tangjiayong
	 * @since 2017-06-02
	 * @param pager
	 * @param dto 
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberJDAddress>> getAllJDAddressForPage(
			Pager<MemberJDAddress> pager, MemberJDAddress dto);
	
}
