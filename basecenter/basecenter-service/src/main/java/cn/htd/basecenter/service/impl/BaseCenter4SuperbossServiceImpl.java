package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseAddressDAO;
import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.service.BaseCenter4SuperbossService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;

/**
 * <p>
 * Description: [为超级老板做的省市区县服务]
 * </p>
 */
@Service("baseCenter4SuperbossService")
public class BaseCenter4SuperbossServiceImpl implements BaseCenter4SuperbossService {

	@Resource
	private BaseAddressDAO baseAddressDAO;

	@Override
	public ExecuteResult<DataGrid<BaseAddressDTO>> queryBaseAddressListByModifyTime(Date modifyStartTime,
			Pager<BaseAddressDTO> pager) {
		ExecuteResult<DataGrid<BaseAddressDTO>> result = new ExecuteResult<DataGrid<BaseAddressDTO>>();
		DataGrid<BaseAddressDTO> datagrid = new DataGrid<BaseAddressDTO>();
		List<BaseAddressDTO> addressDTOList = new ArrayList<BaseAddressDTO>();
		List<BaseAddress> addressList = new ArrayList<BaseAddress>();
		long count = 0L;
		BaseAddressDTO addressDTO = null;
		String startTimeStr = DateUtils.format(modifyStartTime, DateUtils.YYDDMMHHMMSS);
		try {
			count = baseAddressDAO.queryCount4Superboss(startTimeStr);
			datagrid.setTotal(count);
			if (count > 10000 && pager == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.TOO_MANY_DATA_ERROR, "根据条件查询数据过多超过10000条");
			}
			if (count > 0) {
				addressList = baseAddressDAO.queryList4Superboss(startTimeStr, pager);
				for (BaseAddress obj : addressList) {
					addressDTO = new BaseAddressDTO();
					addressDTO.setId(obj.getId());
					addressDTO.setCode(obj.getCode());
					addressDTO.setParentCode(obj.getParentCode());
					addressDTO.setName(obj.getName());
					addressDTO.setLevel(obj.getLevel());
					addressDTO.setDeleteFlag(obj.getDeleteFlag());
					addressDTO.setCreateId(obj.getCreateId());
					addressDTO.setCreateName(obj.getCreateName());
					addressDTO.setCreateTime(obj.getCreateTime());
					addressDTO.setModifyId(obj.getModifyId());
					addressDTO.setModifyName(obj.getModifyName());
					addressDTO.setModifyTime(obj.getModifyTime());
					addressDTOList.add(addressDTO);
				}
				datagrid.setRows(addressDTOList);
			}
			result.setResult(datagrid);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}
