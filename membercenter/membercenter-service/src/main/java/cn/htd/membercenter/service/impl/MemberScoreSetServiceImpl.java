package cn.htd.membercenter.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberScoreSetDAO;
import cn.htd.membercenter.dto.MemberGradeRuleHistoryDTO;
import cn.htd.membercenter.dto.MemberScoreSetDTO;
import cn.htd.membercenter.service.MemberScoreSetService;

@Service("memberScoreSetService")
public class MemberScoreSetServiceImpl implements MemberScoreSetService {

	private final static Logger logger = LoggerFactory.getLogger(MemberScoreSetServiceImpl.class);

	@Resource
	private MemberScoreSetDAO memberScoreSetDAO;

	@Override
	public ExecuteResult<List<MemberScoreSetDTO>> queryMemberScoreSetList(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<List<MemberScoreSetDTO>> rs = new ExecuteResult<List<MemberScoreSetDTO>>();
		try {
			List<MemberScoreSetDTO> memberList = memberScoreSetDAO.queryMemberScoreSetList(memberScoreSetDTO);
			try {
				if (memberList != null) {
					rs.setResult(memberList);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("fail");
				}
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->queryMemberScoreSetList=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberScoreSetDTO>> queryMemberScoreRuleList(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<List<MemberScoreSetDTO>> rs = new ExecuteResult<List<MemberScoreSetDTO>>();
		try {
			List<MemberScoreSetDTO> memberList = memberScoreSetDAO.queryMemberScoreRuleList(memberScoreSetDTO);
			try {
				if (memberList != null) {
					rs.setResult(memberList);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->queryMemberScoreRuleList=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> insertMemberScoreSet(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String intervalType = memberScoreSetDTO.getIntervalType();
		String fromAmount = memberScoreSetDTO.getFromAmount();
		String toAmount = memberScoreSetDTO.getToAmount();
		String score = memberScoreSetDTO.getScore();
		// 区间类型：1、商城交易 2、金融产品
		if (StringUtils.isNotBlank(intervalType) && StringUtils.isNotBlank(fromAmount)
				&& StringUtils.isNotBlank(toAmount) && StringUtils.isNotBlank(score)) {
			try {
				String[] fromArr = fromAmount.split(",");
				String[] toArr = toAmount.split(",");
				String[] valArr = score.split(",");
				for (int i = 0; i < fromArr.length; i++) {
					MemberScoreSetDTO ms = new MemberScoreSetDTO();
					ms.setIntervalType(intervalType);
					ms.setFromAmount(fromArr[i]);
					ms.setToAmount(toArr[i]);
					ms.setScore(valArr[i]);
					ms.setOperateId(memberScoreSetDTO.getOperateId());
					ms.setOperateName(memberScoreSetDTO.getOperateName());
					memberScoreSetDAO.insertMemberScoreSet(ms);
				}
				// 设置操作类型为会员经验规则操作
				memberScoreSetDTO.setOperateType(GlobalConstant.MEMBER_SCORE_SET_OPERATE);
				memberScoreSetDAO.insertMemberScoreRuleHistory(setValue(memberScoreSetDTO));
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberScoreSet=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage("参数不符合要求");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> insertMemberScoreRule(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String buyerLevel = memberScoreSetDTO.getBuyerLevel();
		String fromScore = memberScoreSetDTO.getFromScore();
		String toScore = memberScoreSetDTO.getToScore();
		String lowestPoint = memberScoreSetDTO.getLowestPoint();
		if (StringUtils.isNotBlank(buyerLevel) && StringUtils.isNotBlank(fromScore) && StringUtils.isNotBlank(toScore)
				&& StringUtils.isNotBlank(lowestPoint)) {
			try {
				String[] buyerLevelArr = buyerLevel.split(",");
				String[] fromArr = fromScore.split(",");
				String[] toArr = toScore.split(",");
				String[] valArr = lowestPoint.split(",");
				for (int i = 0; i < fromArr.length; i++) {
					MemberScoreSetDTO ms = new MemberScoreSetDTO();
					ms.setBuyerLevel(buyerLevelArr[i]);
					ms.setFromScore(fromArr[i]);
					ms.setToScore(toArr[i]);
					ms.setLowestPoint(valArr[i]);
					ms.setOperateId(memberScoreSetDTO.getOperateId());
					ms.setOperateName(memberScoreSetDTO.getOperateName());
					memberScoreSetDAO.insertMemberScoreRule(ms);
				}
				// 设置操作类型为会员保底经验值操作
				memberScoreSetDTO.setOperateType(GlobalConstant.MEMBER_SCORE_RULE_OPERATE);
				memberScoreSetDAO.insertMemberScoreRuleHistory(setValue(memberScoreSetDTO));
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberScoreRule=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateMemberScoreSet(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String intervalType = memberScoreSetDTO.getIntervalType();
		String fromAmount = memberScoreSetDTO.getFromAmount();
		String toAmount = memberScoreSetDTO.getToAmount();
		String score = memberScoreSetDTO.getScore();
		// 区间类型：1、商城交易 2、金融产品
		if (StringUtils.isNotBlank(intervalType) && StringUtils.isNotBlank(fromAmount)
				&& StringUtils.isNotBlank(toAmount) && StringUtils.isNotBlank(score)) {
			try {
				memberScoreSetDAO.deleteMemberScoreSet(memberScoreSetDTO);
				String[] fromArr = fromAmount.split(",");
				String[] toArr = toAmount.split(",");
				String[] valArr = score.split(",");
				for (int i = 0; i < fromArr.length; i++) {
					MemberScoreSetDTO ms = new MemberScoreSetDTO();
					ms.setIntervalType(intervalType);
					ms.setFromAmount(fromArr[i]);
					ms.setToAmount(toArr[i]);
					ms.setScore(valArr[i]);
					ms.setOperateId(memberScoreSetDTO.getOperateId());
					ms.setOperateName(memberScoreSetDTO.getOperateName());
					memberScoreSetDAO.insertMemberScoreSet(ms);
				}
				// 设置操作类型为会员经验规则操作
				memberScoreSetDTO.setOperateType("1");
				memberScoreSetDAO.insertMemberScoreRuleHistory(setValue(memberScoreSetDTO));
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberScoreSet=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage("参数不符合要求");
		}
		return rs;
	}

	private boolean validateScoreRule(String strArr) {
		boolean flag = false;
		String regex = "^[0-9]*[1-9][0-9]*$";
		return flag;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	@Override
	public ExecuteResult<Boolean> updateMemberScoreRule(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String buyerLevel = memberScoreSetDTO.getBuyerLevel();
		String fromScore = memberScoreSetDTO.getFromScore();
		String toScore = memberScoreSetDTO.getToScore();
		String lowestPoint = memberScoreSetDTO.getLowestPoint();
		if (StringUtils.isNotBlank(buyerLevel) && StringUtils.isNotBlank(fromScore) && StringUtils.isNotBlank(toScore)
				&& StringUtils.isNotBlank(lowestPoint)) {
			try {
				memberScoreSetDAO.deleteMemberScoreRule(memberScoreSetDTO);
				String[] buyerLevelArr = buyerLevel.split(",");
				String[] fromArr = fromScore.split(",");
				String[] toArr = toScore.split(",");
				String[] valArr = lowestPoint.split(",");
				for (int i = 0; i < fromArr.length; i++) {
					MemberScoreSetDTO ms = new MemberScoreSetDTO();
					ms.setBuyerLevel(buyerLevelArr[i]);
					ms.setFromScore(fromArr[i]);
					ms.setToScore(toArr[i]);
					ms.setLowestPoint(valArr[i]);
					ms.setOperateId(memberScoreSetDTO.getOperateId());
					ms.setOperateName(memberScoreSetDTO.getOperateName());
					memberScoreSetDAO.insertMemberScoreRule(ms);
				}
				// 设置操作类型为会员保底经验值操作
				// memberScoreSetDTO.setOperateType(GlobalConstant.MEMBER_SCORE_RULE_OPERATE);
				memberScoreSetDAO.insertMemberScoreRuleHistory(setValue(memberScoreSetDTO));
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberScoreRule=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MemberGradeRuleHistoryDTO>> queryMemberScoreRuleHistory(
			MemberScoreSetDTO memberScoreSetDTO, Pager<MemberGradeRuleHistoryDTO> pager) {
		ExecuteResult<DataGrid<MemberGradeRuleHistoryDTO>> rs = new ExecuteResult<DataGrid<MemberGradeRuleHistoryDTO>>();
		try {
			DataGrid<MemberGradeRuleHistoryDTO> dg = new DataGrid<MemberGradeRuleHistoryDTO>();
			List<MemberGradeRuleHistoryDTO> memberGroupList = memberScoreSetDAO
					.queryMemberScoreRuleHistory(memberScoreSetDTO, pager);
			long count = memberScoreSetDAO.queryMemberScoreRuleHistoryCount(memberScoreSetDTO);
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
			logger.error("MemberScoreSetServiceImpl----->queryMemberScoreRuleHistory=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	private MemberGradeRuleHistoryDTO setValue(MemberScoreSetDTO memberScoreSetDTO) {
		MemberGradeRuleHistoryDTO memberGradeRuleHistoryDTO = new MemberGradeRuleHistoryDTO();
		memberGradeRuleHistoryDTO.setIntervalType(memberScoreSetDTO.getIntervalType());
		memberGradeRuleHistoryDTO.setOperateType(memberScoreSetDTO.getOperateType());
		memberGradeRuleHistoryDTO.setOperateId(memberScoreSetDTO.getOperateId());
		memberGradeRuleHistoryDTO.setOperateName(memberScoreSetDTO.getOperateName());
		return memberGradeRuleHistoryDTO;
	}

	@Override
	public ExecuteResult<Boolean> insertMemberScoreRuleHistory(MemberGradeRuleHistoryDTO memberGradeRuleHistoryDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String operateType = memberGradeRuleHistoryDTO.getOperateType();
		if (StringUtils.isNotBlank(operateType)) {
			try {
				memberScoreSetDAO.insertMemberScoreRuleHistory(memberGradeRuleHistoryDTO);
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberScoreSetServiceImpl----->insertMemberScoreRuleHistory=" + e);
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
	public ExecuteResult<Boolean> updateMemberScoreWeight(MemberScoreSetDTO memberScoreSetDTO) {
		memberScoreSetDAO.deleteMemberScoreWeight(memberScoreSetDTO);
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		String mallWeight = memberScoreSetDTO.getMallWeight();
		String financeWeight = memberScoreSetDTO.getFinanceWeight();
		try {
			if (StringUtils.isNotBlank(mallWeight) && StringUtils.isNotBlank(financeWeight)) {
				String jsonStr = "{\"mallWeight\":\"" + mallWeight + "\",\"financeWeight\":\"" + financeWeight + "\"}";
				memberScoreSetDTO.setJsonStr(jsonStr);
				memberScoreSetDAO.insertMemberScoreWeight(memberScoreSetDTO);
				memberScoreSetDTO.setOperateType(memberScoreSetDTO.getIntervalType());
				memberScoreSetDAO.insertMemberScoreRuleHistory(setValue(memberScoreSetDTO));
				res.setResultMessage("success");
				res.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} else {
				res.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			}
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->updateMemberScoreWeight=" + e);
			res.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			res.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}

		return res;
	}

	@Override
	public ExecuteResult<MemberScoreSetDTO> queryMemberScoreWeight(MemberScoreSetDTO memberScoreSetDTO) {
		ExecuteResult<MemberScoreSetDTO> rs = new ExecuteResult<MemberScoreSetDTO>();
		try {
			MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(memberScoreSetDTO);
			if (mssd != null) {
				rs.setResult(mssd);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
				rs.setResultMessage("fail");
			}
		} catch (Exception e) {
			logger.error("MemberScoreSetServiceImpl----->queryMemberScoreWeight=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}
}
