package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberJDAddress;

/**
 * @version 创建时间：2016年12月2日 下午5:42:24 类说明:收货人地址信息查询、修改
 */
public interface ConsigneeAddressDAO {

	/**
	 * 查询收货地址列表总数
	 */
	public Long selectConsigAddressCount(@Param("memberId") Long memberId);
	
	
	/**
	 * 查询收货地址列表
	 * 
	 * @param page
	 * @param memberId
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectConsigAddressList(
			@Param("page") Pager page, @Param("memberId") Long memberId);

	/**
	 * 超级会员 查询收货地址信息列表总数
	 * @param memberId
	 * @return
	 */
	public Long selectConsigAddressIdsCount(
			 @Param("memberId") Long memberId);
	/**
	 * 超级会员 查询收货地址信息列表
	 * 
	 * @param page
	 * @param memberId
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectConsigAddressIds(
			@Param("page") Pager page, @Param("memberId") Long memberId);

	/**
	 * 查询发票信息列表
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectInvoiceList(
			@Param("memberId") Long memberId);

	/**
	 * 查询收货表信息
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectConsigList(
			@Param("memberId") Long memberId);

	/**
	 * 根据收货地址Id查询收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public MemberConsigAddressDTO selectConsigAddressID(
			@Param("addressId") Long addressId);

	/**
	 * 修改收货地址信息表
	 * 
	 * @param memberConsigAddressDto
	 *            ->addressId
	 * @return
	 */
	public void updateConsigAddressInfo(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 修改发票信息表
	 * 
	 * @param memberConsigAddressDto
	 *            ->invoiceId
	 * @return
	 */
	public void updatInvoiceInfo(MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 新增收货地址信息表
	 * 
	 * @param memberConsigAddressDto
	 * @return
	 */
	public void insertConsigAddressInfo(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 新增发票信息表
	 * 
	 * @param memberConsigAddressDto
	 * @return
	 */

	public void insertInvoiceInfo(MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 删除收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public void deleteConsigAddressInfo(
			@Param("addressId") Long addressId,
			@Param("modifyId") Long modifyId,
			@Param("modifyName") String modifyName);

	/**
	 * 批量删除收货地址信息
	 * 
	 * @param addressId
	 * @return
	 */
	public void deleteConsigAddressInfoBatch(
			MemberConsigAddressDTO memberConsigAddressDto);

	/**
	 * 默认收货地址设置为0
	 */
	public void updateConsigAddressInfoDefault(@Param("memberId") Long memberId);

	/**
	 * 查询默认收货地址
	 * */
	public MemberConsigAddressDTO selectDefaultAddress(
			@Param("memberId") Long memberId);

	/**
	 * 通过MemberCode和外部渠道编码查询地址信息
	 * 
	 * @param memberCode
	 * @param channelCode
	 * @return
	 */
	public MemberConsigAddressDTO selectChannelAddressDTO(
			@Param("memberCode") String memberCode,
			@Param("channelCode") String channelCode);
	/**
	 * 根据memberId,城市，省code返回地址集合list
	 * @param memberId
	 * @param consigneeAddressCity
	 * @param consigneeAddressProvince
	 * @Param sort 排序
	 * @return
	 */
	public List<MemberConsigAddressDTO> searchAddressCityList(
			@Param("memberId") Long memberId,
			@Param("consigneeAddressCity") String consigneeAddressCity,
			@Param("consigneeAddressProvince") String consigneeAddressProvince,
			@Param("sort") String sort,
			@Param("page") Pager page
			);
	
	/**
	 * 根据会员id和外部渠道编码查询发票地址信息
	 */
	public MemberInvoiceDTO selectInvoiceInfoDto(
			@Param("memberId") Long memberId,
			@Param("channelCode") String channelCode,
			@Param("invoiceId") Long invoiceId
			);


	/**
	 * @param page
	 * @param memberId
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectConsigAddressListByPlus(@Param("page") Pager page, @Param("memberId") Long memberId);


	/**
	 * @param memberId
	 * @return
	 */
	public Long selectConsigAddressListByPlusCount(@Param("memberId") Long memberId);

	/**
	 * 查询商品+地址总数
	 * @return
	 */
	public Long getAllVendorAddProductPlusCount(@Param("dto") MemberJDAddress dto);

	/**
	 * 查询所有商品+商品
	 * @param page
	 * @param dto 
	 * @return
	 */
	public List<MemberJDAddress> getAllVendorAddProductPlus(@Param("page") Pager<MemberJDAddress> page,@Param("dto") MemberJDAddress dto);
}