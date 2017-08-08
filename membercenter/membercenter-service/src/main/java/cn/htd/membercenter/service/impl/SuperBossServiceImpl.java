package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.SuperBossDao;
import cn.htd.membercenter.domain.BelongRelationship;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.MemberShipDTO;
import cn.htd.membercenter.service.SuperBossService;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.UserExportService;

@Service("superBossService")
public class SuperBossServiceImpl implements SuperBossService {
	private final static Logger logger = LoggerFactory.getLogger(SuperBossServiceImpl.class);
	@Resource
	private SuperBossDao superBossDao;

	@Resource
	private UserExportService userExportService;

	@Override
	public ExecuteResult<DataGrid<MemberShipDTO>> selectMemberShip(String startTime,String endTime,Pager page) {
		logger.info("会员关系同步调用接口方法：SuperBossServiceImpl====>selectMemberShip");
		ExecuteResult<DataGrid<MemberShipDTO>> rs = new ExecuteResult<DataGrid<MemberShipDTO>>();
		DataGrid<MemberShipDTO> dg = new DataGrid<MemberShipDTO>();
		try {
			List<MemberShipDTO> list = superBossDao.serachMemberShip(startTime,endTime,page);
			//查询会员同步信息所有总个数
			Long count = superBossDao.serachMemberShipCount(startTime, endTime);
			if (list.size() > 0 && list != null) {
				for (MemberShipDTO memberShipDTO : list) {
					memberShipDTO.setLoginId(memberShipDTO.getMemberCode());
					String passWord = "";
					UserDTO user = userExportService.queryUserByLoginId(memberShipDTO.getMemberCode()).getResult();
					if (user != null && user.getPassword() != null) {
						passWord = user.getPassword();
					}
					memberShipDTO.setPassWord(passWord);
				}
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.addErrorMessage("没查到数据");
				rs.setResultMessage("fail");
			}
		} catch (Exception e) {
			rs.addErrorMessage("后台异常");
			rs.setResultMessage("error");
			logger.error("会员关系同步调用接口方法：SuperBossServiceImpl====>selectMemberShip=" + e);
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<BoxRelationship>> selectBoxShip(String startTime,String endTime,Pager page) {
		logger.info("包厢关系同步调用接口方法：SuperBossServiceImpl====>selectBoxShip");
		ExecuteResult<DataGrid<BoxRelationship>> rs = new ExecuteResult<DataGrid<BoxRelationship>>();
		DataGrid<BoxRelationship> dg = new DataGrid<BoxRelationship>();
		try {
			List<BoxRelationship> list = superBossDao.searchBoxShip(startTime,endTime,page);
			Long count = superBossDao.searchBoxShipCount(startTime, endTime);
			dg.setRows(list);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("后台异常");
			rs.setResultMessage("error");
			logger.error("包厢关系同步调用接口方法：SuperBossServiceImpl====>selectBoxShip=" + e);
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<BelongRelationship>> selectBelongShip(String startTime,String endTime,Pager page) {
		logger.info("归属关系同步调用接口方法：SuperBossServiceImpl====>selectBelongShip");
		ExecuteResult<DataGrid<BelongRelationship>> rs = new ExecuteResult<DataGrid<BelongRelationship>>();
		DataGrid<BelongRelationship> dg = new DataGrid<BelongRelationship>();
		try {
			List<BelongRelationship> list = superBossDao.searchBelongShip(startTime,endTime,page);
			Long count = superBossDao.searchBelongShipCount(startTime, endTime);
			dg.setRows(list);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("后台异常");
			rs.setResultMessage("error");
			logger.error("归属关系同步调用接口方法：SuperBossServiceImpl====>selectBelongShip=" + e);
			e.printStackTrace();
		}
		return rs;
	}

}
