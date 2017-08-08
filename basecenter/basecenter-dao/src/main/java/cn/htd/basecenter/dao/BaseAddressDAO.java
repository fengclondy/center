package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseAddressDAO extends BaseDAO<BaseAddress> {

	/**
	 * 根据Code取得基础地址信息
	 * 
	 * @param code
	 * @return
	 */
	public BaseAddress queryBaseAddressByCode(String code);

	/**
	 * 根据上级地址编码取得下级地址信息列表
	 * 
	 * @param parentCode
	 * @return
	 */
	public List<BaseAddress> queryBaseAddressListByParentCode(String parentCode);

	/**
	 * 查询定时任务处理地址列表
	 * 
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<BaseAddress> queryAddressList4Task(@Param("entity") BaseAddress condition,
			@Param("page") Pager<BaseAddress> page);

	/**
	 * 更新地址上传Redis状态
	 * 
	 * @param ids
	 */
	public void updateRedisFlagById(List<Long> ids);

	/**
	 * 查询定时任务处理地址列表
	 * 
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<BaseAddress> queryAddressList4Erp(@Param("entity") BaseAddress condition,
			@Param("page") Pager<BaseAddress> page);

	/**
	 * 更新地址信息下行状态
	 * 
	 * @param address
	 */
	public void updateAddressStatusById(@Param("entity") BaseAddress address);

	/**
	 * 根据更新日期取得之后更新过的地址数据件数
	 * 
	 * @param startTimeStr
	 * @return
	 */
	public long queryCount4Superboss(String startTimeStr);

	/**
	 * 根据更新日期取得之后更新过的地址数据
	 * 
	 * @param startTimeStr
	 * @param pager
	 * @return
	 */
	public List<BaseAddress> queryList4Superboss(@Param("startTimeStr") String startTimeStr,
			@Param("page") Pager<BaseAddressDTO> pager);

	/**
	 * 查询下行erp异常的地址数据
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<BaseAddress> queryAddressList4ErpException(@Param("entity") BaseAddress condition,
								  @Param("page") Pager<BaseAddress> page);

	/**
	 * 查询下行erp异常的地址数据
	 * @param condition
	 * @return
	 */
	public Long queryAddressCount4ErpException(@Param("entity") BaseAddress condition);
}
