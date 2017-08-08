package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberDownErpDAO;
import cn.htd.membercenter.dto.MemberErpDTO;
import cn.htd.membercenter.service.MemberDownErpService;

@Service("memberDownErpService")
public class MemberDownErpServiceImpl implements MemberDownErpService {
	private final static Logger logger = LoggerFactory.getLogger(MemberDownErpServiceImpl.class);

	@Resource
	MemberDownErpDAO memberDownErpDAO;

	@Override
	public ExecuteResult<DataGrid<MemberErpDTO>> selectErpDownList(MemberErpDTO dto,
			@SuppressWarnings("rawtypes") Pager pager) {
		logger.info("开始执行MemberDownErpServiceImpl=======selectErpDownList,参数", JSONObject.toJSONString(dto),
				JSONObject.toJSONString(pager));

		ExecuteResult<DataGrid<MemberErpDTO>> rs = new ExecuteResult<DataGrid<MemberErpDTO>>();
		DataGrid<MemberErpDTO> dg = new DataGrid<MemberErpDTO>();
		try {
			if ("1".equals(dto.getErpDownType())) {// 会员下行
				Long count = memberDownErpDAO.selectErpDownListType1Count(dto);
				if (count > 0) {
					List<MemberErpDTO> list = memberDownErpDAO.selectErpDownListType1(dto, pager);
					dg.setRows(list);
					dg.setTotal(count);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("没有查询到数据");
				}
				rs.setResult(dg);
			} else if ("2".equals(dto.getErpDownType())) {// 单位往来关系
				Long count = memberDownErpDAO.selectErpDownListType2Count(dto);
				if (count > 0) {
					List<MemberErpDTO> list = memberDownErpDAO.selectErpDownListType2(dto, pager);
					dg.setRows(list);
					dg.setTotal(count);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("没有查询到数据");
				}
				rs.setResult(dg);

			} else if ("3".equals(dto.getErpDownType())) {// 客商业务员
				Long count = memberDownErpDAO.selectErpDownListType3Count(dto);
				if (count > 0) {
					List<MemberErpDTO> list = memberDownErpDAO.selectErpDownListType3(dto, pager);
					dg.setRows(list);
					dg.setTotal(count);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("没有查询到数据");
				}
				rs.setResult(dg);

			}
			logger.info("MemberDownErpServiceImpl=======selectErpDownList执行成功,结果:", JSONObject.toJSONString(rs),
					JSONObject.toJSONString(pager));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberDownErpServiceImpl==========selectErpDownList" + e);
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> saveErpDownReset(MemberErpDTO dto) {
		logger.info("开始执行MemberDownErpServiceImpl=======saveErpDownReset,参数", JSONObject.toJSONString(dto));
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if ("1".equals(dto.getErpDownType())) {
				memberDownErpDAO.updateMemberDownErp(dto.getId());
			} else if ("2".equals(dto.getErpDownType())) {
				memberDownErpDAO.updateCompanyRelationDownErp(dto.getId());
			} else if ("3".equals(dto.getErpDownType())) {
				memberDownErpDAO.updateSalesManDownErp(dto.getId());
			}
			rs.setResultMessage("success");
			rs.setResult(true);
			logger.info("MemberDownErpServiceImpl=======saveErpDownReset执行成功,结果:", JSONObject.toJSONString(rs));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberDownErpServiceImpl==========saveErpDownReset" + e);
			rs.setResult(false);
			rs.setResultMessage("fail");
			rs.addErrorMessage("重处理设置异常");
		}
		return rs;
	}

}
