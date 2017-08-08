package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberVerifyStatusDAO;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.MembVeriBrandCategDTO;
import cn.htd.membercenter.dto.MemberVerifyInfoDTO;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;
import cn.htd.membercenter.service.MemberVerifyInfoService;

@Service("memberVerifyInfoService")
public class MemberVerifyInfoServiceImpl implements MemberVerifyInfoService {

	private final static Logger logger = LoggerFactory.getLogger(MemberVerifyInfoServiceImpl.class);

	@Resource
	private MemberVerifyStatusDAO verifyInfo;

	// 会员待审核页面个人基本信息
	@Override
	public ExecuteResult<MemberVerifyInfoDTO> queryInfoByMemberId(Long memberId) {
		ExecuteResult<MemberVerifyInfoDTO> rs = new ExecuteResult<MemberVerifyInfoDTO>();
		try {

			MemberVerifyInfoDTO verifyInfoDTO;
			if (memberId != null) {
				verifyInfoDTO = verifyInfo.queryInfoByMemberId(memberId);
				rs.setResult(verifyInfoDTO);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("请选择要查询的数据");
			}

		} catch (Exception e) {
			logger.error("MemberVerifyInfoServiceImpl----->queryByMemberId=" + e);
				rs.setResultMessage("error");
		}
		return rs;
	}

	// 会员待审核页面品类品牌信息
	@Override
	public ExecuteResult<DataGrid<CategoryBrandDTO>> queryBrandByMemberId(Pager page,Long memberId) {

		ExecuteResult<DataGrid<CategoryBrandDTO>> rs = new ExecuteResult<DataGrid<CategoryBrandDTO>>();
		DataGrid<CategoryBrandDTO> dg = new DataGrid<CategoryBrandDTO>();
		try {

			List<CategoryBrandDTO> verifyInfoDTOList =null;
			verifyInfoDTOList = verifyInfo.queryBrandByMemberId(page,memberId);
			try {
				if (verifyInfoDTOList != null) {
					dg.setRows(verifyInfoDTOList);
					dg.setTotal(new Long(verifyInfoDTOList.size()));
					rs.setResult(dg);
				}
				else {
						rs.setResultMessage("没有找到要查询的品类品牌信息");
					}

					rs.setResultMessage("success");
				} 
			catch (Exception e) {
					rs.setResultMessage("error");
					throw new RuntimeException(e);
				}
			
		} catch (Exception e) {
			logger.error("MemberVerifyInfoServiceImpl----->queryBrandByMemberId=" + e);
		}
		return rs;
	}

}
