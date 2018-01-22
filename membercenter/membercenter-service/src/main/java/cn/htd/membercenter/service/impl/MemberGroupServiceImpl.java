package cn.htd.membercenter.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberGroupDAO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberGroupRelationDTO;
import cn.htd.membercenter.service.MemberGroupService;

@Service("memberGroupService")
public class MemberGroupServiceImpl implements MemberGroupService {

	private final static Logger logger = LoggerFactory.getLogger(MemberGroupServiceImpl.class);

	@Resource
	private MemberGroupDAO memberGroupDAO;

	@Override
	public ExecuteResult<DataGrid<MemberGroupDTO>> queryMemberGroupListInfo(MemberGroupDTO memberGroupDTO,
			Pager<MemberGroupDTO> pager) {
		ExecuteResult<DataGrid<MemberGroupDTO>> rs = new ExecuteResult<DataGrid<MemberGroupDTO>>();
		try {
			DataGrid<MemberGroupDTO> dg = new DataGrid<MemberGroupDTO>();
			List<MemberGroupDTO> memberGroupList = memberGroupDAO.queryMemberGroupListInfo(memberGroupDTO, pager);
			long count = memberGroupDAO.queryMemberGroupListCount(memberGroupDTO);
			try {
				if (CollectionUtils.isNotEmpty(memberGroupList)) {
					dg.setRows(memberGroupList);
					dg.setTotal(count);
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
			logger.error("MemberGroupServiceImpl----->queryMemberGroupListInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGroupDTO>> queryMemberNoneGroupListInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<List<MemberGroupDTO>> rs = new ExecuteResult<List<MemberGroupDTO>>();
		try {
			List<MemberGroupDTO> memberGroupList = memberGroupDAO.queryMemberNoneGroupListInfo(memberGroupDTO);
			try {
				if (CollectionUtils.isNotEmpty(memberGroupList)) {
					rs.setResult(memberGroupList);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->queryMemberGroupListInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberGroupDTO> queryMemberGroupInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<MemberGroupDTO> rs = new ExecuteResult<MemberGroupDTO>();
		try {
			long groupId = memberGroupDTO.getGroupId();
			MemberGroupDTO member = null;
			if (groupId != 0) {
				member = memberGroupDAO.queryMemberGroupInfo(memberGroupDTO);
				List<MemberGroupRelationDTO> relationList = memberGroupDAO
						.queryMemberGroupRelationListInfoByGroupId(memberGroupDTO);
				member.setRelationList(relationList);
			}
			try {
				if (member != null) {
					rs.setResult(member);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->queryMemberGroupInfo=" + e);
		}
		return rs;
	}
	
	@Override
	public ExecuteResult<MemberGroupDTO> queryMemberGroupInfoForPage(MemberGroupDTO memberGroupDTO,
			Pager<MemberGroupDTO> pager) {
		ExecuteResult<MemberGroupDTO> rs = new ExecuteResult<MemberGroupDTO>();
		try {
			long groupId = memberGroupDTO.getGroupId();
			MemberGroupDTO member = null;
			if (groupId != 0) {
				member = memberGroupDAO.queryMemberGroupInfo(memberGroupDTO);
				List<MemberGroupRelationDTO> relationList = memberGroupDAO
						.queryMemberGroupRelationListInfoByGroupIdPage(memberGroupDTO,pager);
				member.setRelationList(relationList);
			}
			try {
				if (member != null) {
					rs.setResult(member);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->queryMemberGroupInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> deleteMemberGroupInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String groupIds = memberGroupDTO.getGroupIds();
		if (StringUtils.isNotBlank(groupIds)) {
			try {
				// 设置拼接的查询分组ID参数
				String[] groupIdArr = groupIds.split(",");
				for (int i = 0; i < groupIdArr.length; i++) {
					MemberGroupDTO mg = new MemberGroupDTO();
					mg.setGroupId(Long.valueOf(groupIdArr[i]));
					mg.setOperateId(memberGroupDTO.getOperateId());
					mg.setOperateName(memberGroupDTO.getOperateName());
					mg.setDeleteFlag(String.valueOf(GlobalConstant.DELETED_FLAG_YES));
					memberGroupDAO.deleteMemberGroupInfo(mg);
				}
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberBusinessRelationInfo=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage("参数不全！");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> insertMemberGroupInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String buyerIds = memberGroupDTO.getBuyerIds();
			memberGroupDAO.insertMemberGroupInfo(memberGroupDTO);
			if (StringUtils.isNotBlank(buyerIds)) {
				String[] buyerIdArr = buyerIds.split(",");
				for (int i = 0; i < buyerIdArr.length; i++) {
					MemberGroupRelationDTO memberGroupRelationDTO = new MemberGroupRelationDTO();
					memberGroupRelationDTO.setGroupId(memberGroupDTO.getGroupId());
					memberGroupRelationDTO.setBuyerId(Long.valueOf(buyerIdArr[i]));
					memberGroupRelationDTO.setOperateId(memberGroupDTO.getOperateId());
					memberGroupRelationDTO.setOperateName(memberGroupDTO.getOperateName());
					memberGroupDAO.insertMemberGroupRelationInfo(memberGroupRelationDTO);
				}
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->insertMemberBusinessRelationInfo=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateMemberGroupInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String buyerIds = memberGroupDTO.getBuyerIds();
			memberGroupDAO.updateMemberGroupInfo(memberGroupDTO);
			memberGroupDAO.deleteMemberGroupRelationInfo(memberGroupDTO);
			if (StringUtils.isNotBlank(buyerIds)) {
				String[] buyerIdArr = buyerIds.split(",");
				for (int i = 0; i < buyerIdArr.length; i++) {
					MemberGroupRelationDTO memberGroupRelationDTO = new MemberGroupRelationDTO();
					memberGroupRelationDTO.setBuyerGroupId(memberGroupDTO.getGroupId());
					memberGroupRelationDTO.setBuyerId(Long.valueOf(buyerIdArr[i]));
					memberGroupRelationDTO.setGroupId(memberGroupDTO.getGroupId());
					memberGroupRelationDTO.setOperateId(memberGroupDTO.getOperateId());
					memberGroupRelationDTO.setOperateName(memberGroupDTO.getOperateName());
					memberGroupDAO.insertMemberGroupRelationInfo(memberGroupRelationDTO);
				}
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->insertMemberBusinessRelationInfo=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGroupDTO>> queryMemberGradeAndGroupList(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<List<MemberGroupDTO>> rs = new ExecuteResult<List<MemberGroupDTO>>();
		try {
			List<MemberGroupDTO> memberGroupList = memberGroupDAO.queryMemberGradeAndGroupList(memberGroupDTO);
			try {
				if (memberGroupList != null) {
					rs.setResult(memberGroupList);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->queryMemberGradeAndGroupList=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGroupDTO>> queryMemberGroupListInfo4select(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<List<MemberGroupDTO>> rs = new ExecuteResult<List<MemberGroupDTO>>();
		try {
			List<MemberGroupDTO> memberGroupList = memberGroupDAO.queryMemberGroupListInfo4select(memberGroupDTO);
			if (CollectionUtils.isNotEmpty(memberGroupList)) {
				rs.setResult(memberGroupList);
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->queryMemberGroupListInfo4select=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<String>> querySubMemberIdBySellerId(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<List<String>> rs = new ExecuteResult<List<String>>();
		try {
			List<String> memberIdList = memberGroupDAO.querySubMemberIdBySellerId(memberGroupDTO);
			if (CollectionUtils.isNotEmpty(memberIdList)) {
				rs.setResult(memberIdList);
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->querySubMemberIdBySellerId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<String>> querySubMemberIdByGradeInfoAndGroupInfo(MemberGroupDTO memberGroupDTO) {
		ExecuteResult<List<String>> rs = new ExecuteResult<List<String>>();
		List<String> memberIdList4Grade = new ArrayList<String>();
		List<String> memberIdList4Group = new ArrayList<String>();
		try {
			List<String> gradeList = memberGroupDTO.getGradeList();
			if (CollectionUtils.isNotEmpty(gradeList)) {
				memberIdList4Grade = memberGroupDAO.querySubMemberIdByGradeId(memberGroupDTO);
			}
			List<String> groupList = memberGroupDTO.getGroupList();
			if (CollectionUtils.isNotEmpty(groupList)) {
				memberIdList4Group = memberGroupDAO.querySubMemberIdByGroupId(memberGroupDTO);
			}
			List<String> result = new ArrayList<String>();
			memberIdList4Grade.addAll(memberIdList4Group);
			if (CollectionUtils.isNotEmpty(memberIdList4Grade)) {
				for (String str : memberIdList4Grade) {
					if (!result.contains(str)) {
						result.add(str);
					}
				}
				rs.setResult(result);
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			logger.error("MemberGroupServiceImpl----->querySubMemberIdByGradeInfoAndGroupInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberGroupDTO> queryGroupInfoBySellerBuyerId(Long buyerId, Long sellerId) {
		logger.info("MemberGroupServiceImpl----->queryGroupInfoBySellerBuyerId执行，参数位：sellerId:" + sellerId + "buyerId:"
				+ buyerId);
		ExecuteResult<MemberGroupDTO> rs = new ExecuteResult<MemberGroupDTO>();
		try {
			MemberGroupDTO memberGroupDTO = memberGroupDAO.queryGroupInfoBySellerBuyerId(buyerId, sellerId);
			if (null != memberGroupDTO) {
				rs.setResult(memberGroupDTO);
				rs.setResultMessage("successs");
			} else {
				rs.addErrorMessage("查询不到分组信息");
				rs.setResultMessage("fail");
			}
			logger.info("MemberGroupServiceImpl----->queryGroupInfoBySellerBuyerId执行结束，参数位：sellerId:" + sellerId
					+ "buyerId:" + buyerId + "结果为：" + JSONObject.toJSONString(memberGroupDTO));
		} catch (Exception e) {
			rs.addErrorMessage("查询分组执行异常");
			rs.setResultMessage("fail");
			logger.error("MemberGroupServiceImpl----->queryGroupInfoBySellerBuyerId执行异常，参数位：sellerId:" + sellerId
					+ "buyerId:" + buyerId + e);

		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGroupDTO>> queryChooseMemberGroupInfo(MemberGroupDTO memberGroupDTO) {
		logger.info("MemberGroupServiceImpl -- queryChooseMemberGroupInfo -- 参数sellerId:"+memberGroupDTO.getSellerId());
		ExecuteResult<List<MemberGroupDTO>> rs = new ExecuteResult<List<MemberGroupDTO>>();
		try{
			List<MemberGroupDTO> result = memberGroupDAO.queryChooseMemberGroupInfo(memberGroupDTO);
			if(CollectionUtils.isNotEmpty(result)){
				rs.setResult(result);
				rs.setResultMessage("successs");
			}else{
				rs.addErrorMessage("查询不到分组信息");
				rs.setResultMessage("fail");
			}
		}catch (Exception e) {
			rs.addErrorMessage(e.getMessage());
			rs.setResultMessage("fail");
			logger.error("MemberGroupServiceImpl----->queryGroupInfoBySellerBuyerId执行异常，参数位：sellerId:" + memberGroupDTO.getSellerId()
					 + e);

		}
		return rs;
	}
}
