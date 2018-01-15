package cn.htd.membercenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberVerifyStatusDAO;
import cn.htd.membercenter.dto.MemberAuditPendingDTO;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;
import cn.htd.membercenter.service.MemberVerifyStatusService;

@Service("memberVerifyStatusService")
public class MemberVerifyStatusServiceImpl implements MemberVerifyStatusService {

	private final static Logger logger = LoggerFactory.getLogger(MemberVerifyStatusServiceImpl.class);

	@Resource
	private MemberVerifyStatusDAO memberVerifyStatusDao;

	// 查询待审核的会员信息列表
	@Override
	public ExecuteResult<DataGrid<MemberVerifyStatusDTO>> selectByStatus(Pager page,String verifyStatus,Long sellerId,String name,
			String isDiffIndustry, Date startTime, Date endTime) {
		ExecuteResult<DataGrid<MemberVerifyStatusDTO>> rs = new ExecuteResult<DataGrid<MemberVerifyStatusDTO>>();
		DataGrid<MemberVerifyStatusDTO> dg = new DataGrid<MemberVerifyStatusDTO>();
		try {
			List<MemberVerifyStatusDTO> count = null;
			count = memberVerifyStatusDao.selectByStatusList(null, verifyStatus,sellerId,name, isDiffIndustry, startTime, endTime);
			List<MemberVerifyStatusDTO> verifyStatusDtoList = null;
			try {
				if (count != null) {
					verifyStatusDtoList = memberVerifyStatusDao.selectByStatusList(page, verifyStatus,sellerId, name, isDiffIndustry, startTime, endTime);
					dg.setRows(verifyStatusDtoList);
					dg.setTotal(new Long(count.size()));
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberVerifyStatusServerImpl----->selectByStatus=" + e);
		}

		return rs;
	}

	
	/**
	 * VMS - 查询待审核会员列表
	 * @author li.jun
	 * @time 2017-12-06
	 */
	@Override
	public ExecuteResult<DataGrid<MemberVerifyStatusDTO>> queryAuditPendingMember(Pager<MemberVerifyStatusDTO> page,
			MemberAuditPendingDTO memberAuditPending) {
		ExecuteResult<DataGrid<MemberVerifyStatusDTO>> result = new ExecuteResult<DataGrid<MemberVerifyStatusDTO>>();
		DataGrid<MemberVerifyStatusDTO> dataGrid = new DataGrid<MemberVerifyStatusDTO>();
		try {
			Long count = memberVerifyStatusDao.queryAuditPendingMemberCount(memberAuditPending);
			if(count > 0){
				List<MemberVerifyStatusDTO> list  = memberVerifyStatusDao.queryAuditPendingMember(page, memberAuditPending);
				dataGrid.setRows(list);
				dataGrid.setTotal(count);
			}
			result.setResult(dataGrid);
		}catch (Exception e) {
			logger.error("查询待审核会员报错  -> queryAuditPendingMember=" + e);
			result.addErrorMessage("查询待审核会员报错!");
		}
		return result;
	}

	/**
	 * 查询待审核会员数量报错
	 */
	@Override
	public ExecuteResult<Long> queryAuditPendingMemberCount(MemberAuditPendingDTO memberAuditPending) {
		ExecuteResult<Long> result = new ExecuteResult<Long>();
		try{
			Long count = memberVerifyStatusDao.queryAuditPendingMemberCount(memberAuditPending);
			result.setResult(count);
		}catch (Exception e) {
			logger.error("查询待审核会员数量报错  -> queryAuditPendingMemberCount=" + e);
			result.addErrorMessage("查询待审核会员数量报错!");
		}
		
		return result;
	}

	

}
