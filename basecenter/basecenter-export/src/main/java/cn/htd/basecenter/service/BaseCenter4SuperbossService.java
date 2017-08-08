package cn.htd.basecenter.service;

import java.util.Date;

import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [为超级老板做的省市区县服务]
 * </p>
 */
public interface BaseCenter4SuperbossService {
	/**
	 * 查询根据时间取得更新过的地址信息
	 * 
	 * @param modifyStartTime
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<BaseAddressDTO>> queryBaseAddressListByModifyTime(Date modifyStartTime,
			Pager<BaseAddressDTO> pager);
}
