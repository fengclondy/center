package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;
import com.yiji.openapi.tool.fastjson.JSONObject;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.dao.CycleTradeForbiddenMemberDAO;
import cn.htd.membercenter.domain.CycleTradeForbiddenMember;
import cn.htd.membercenter.dto.CycleTradeForbiddenMemberDTO;
import cn.htd.membercenter.service.CycleTradeForbiddenMemberService;

@Service("cycleTradeForbiddenMemberService")
public class CycleTradeForbiddenMemberServiceImpl implements CycleTradeForbiddenMemberService {

	private Logger logger = Logger.getLogger(CycleTradeForbiddenMemberServiceImpl.class);

	@Resource
	private CycleTradeForbiddenMemberDAO cycleTradeForbiddenMemberDAO;

	@Override
	public ExecuteResult<String> insertCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto) {
		logger.info("新增互为上下游禁止交易会员信息参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (null == dto) {
				result.setCode(MemberCenterCodeEnum.ERROR.getCode());
				result.setResultMessage("参数错误");
				return result;
			}
			CycleTradeForbiddenMemberDTO countDTO = new CycleTradeForbiddenMemberDTO();
			countDTO.setMemberCode(dto.getMemberCode());
			countDTO.setForbiddenType(dto.getForbiddenType());
			long count = cycleTradeForbiddenMemberDAO.selecCycleTradeForbiddenMemberCount(countDTO);
			if(count > 0){
				result.setCode(MemberCenterCodeEnum.ERROR.getCode());
				if(dto.getForbiddenType().equals("1")){
					result.setResultMessage("该数据已在互为上下游禁止销售中存在");
				}else{
					result.setResultMessage("该数据已在互为上下游禁止购买中存在");
				}
				return result;
			}
			cycleTradeForbiddenMemberDAO.insertCycleTradeForbiddenMember(dto);
			result.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MemberCenterCodeEnum.ERROR.getCode());
			result.addErrorMessage("error");
			result.setResultMessage("异常");
			logger.error("CycleTradeForbiddenMemberServiceImpl======>insertCycleTradeForbiddenMember=" + e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto) {
		logger.info("删除互为上下游禁止交易会员信息参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (null == dto || CollectionUtils.isEmpty(dto.getIds())) {
				result.setCode(MemberCenterCodeEnum.ERROR.getCode());
				result.setResultMessage("参数错误");
				return result;
			}
			cycleTradeForbiddenMemberDAO.deleteCycleTradeForbiddenMember(dto);
			result.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MemberCenterCodeEnum.ERROR.getCode());
			result.addErrorMessage("error");
			result.setResultMessage("异常");
			logger.error("CycleTradeForbiddenMemberServiceImpl======>deleteCycleTradeForbiddenMember=" + e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<CycleTradeForbiddenMemberDTO>> selectCycleTradeForbiddenMemberList(
			CycleTradeForbiddenMemberDTO dto, Pager<CycleTradeForbiddenMemberDTO> pager) {
		logger.info(
				"查询互为上下游禁止交易会员信息(分页)参数:" + JSONObject.toJSONString(dto) + ",page=" + JSONObject.toJSONString(pager));
		ExecuteResult<DataGrid<CycleTradeForbiddenMemberDTO>> result = new ExecuteResult<DataGrid<CycleTradeForbiddenMemberDTO>>();
		try {
			if (null == dto) {
				result.setCode(MemberCenterCodeEnum.ERROR.getCode());
				result.setResultMessage("参数错误");
				return result;
			}
			long count = cycleTradeForbiddenMemberDAO.selecCycleTradeForbiddenMemberCount(dto);
			if (count > 0) {
				DataGrid<CycleTradeForbiddenMemberDTO> dg = new DataGrid<CycleTradeForbiddenMemberDTO>();
				List<CycleTradeForbiddenMember> memberList = cycleTradeForbiddenMemberDAO
						.selectCycleTradeForbiddenMemberList(dto, pager);
				if (CollectionUtils.isNotEmpty(memberList)) {
					String str = JSONObject.toJSONString(memberList);
					List<CycleTradeForbiddenMemberDTO> convertList = JSONObject.parseArray(str,
							CycleTradeForbiddenMemberDTO.class);
					dg.setRows(convertList);
					dg.setTotal(count);
					result.setResult(dg);
				}
			} else {
				result.setResultMessage("暂无要禁止会员店购买的数据");
			}
			result.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MemberCenterCodeEnum.ERROR.getCode());
			result.addErrorMessage("error");
			result.setResultMessage("异常");
			logger.error("CycleTradeForbiddenMemberServiceImpl======>selectCycleTradeForbiddenMemberList=" + e);
		}
		return result;
	}

	@Override
	public ExecuteResult<Boolean> isCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto) {
		logger.info("查询该会员是否是互为上下游数据参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			if (null == dto) {
				result.setCode(MemberCenterCodeEnum.ERROR.getCode());
				result.setResultMessage("参数错误");
				return result;
			}
			long count = cycleTradeForbiddenMemberDAO.selecCycleTradeForbiddenMemberCount(dto);
			if (count > 0) {
				result.setResult(true);
			} else {
				result.setResult(false);
			}
			result.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MemberCenterCodeEnum.ERROR.getCode());
			result.addErrorMessage("error");
			result.setResultMessage("异常");
			logger.error("CycleTradeForbiddenMemberServiceImpl======>isCycleTradeForbiddenMember=" + e);
		}
		return result;
	}

}
