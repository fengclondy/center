package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.common.constant.Constants;
import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BasePlacardDAO;
import cn.htd.basecenter.dao.BasePlacardMemberDAO;
import cn.htd.basecenter.dao.BasePlacardScopeDAO;
import cn.htd.basecenter.domain.BasePlacard;
import cn.htd.basecenter.domain.BasePlacardMember;
import cn.htd.basecenter.domain.BasePlacardScope;
import cn.htd.basecenter.dto.BasePlacardDTO;
import cn.htd.basecenter.dto.BasePlacardScopeDTO;
import cn.htd.basecenter.dto.PlacardCondition;
import cn.htd.basecenter.dto.PlacardInfo;
import cn.htd.basecenter.enums.PlacardScopeTypeEnum;
import cn.htd.basecenter.enums.PlacardStatusEnum;
import cn.htd.basecenter.enums.PlacardTypeEnum;
import cn.htd.basecenter.service.BasePlacardService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;

@Service("basePlacardService")
public class BasePlacardServiceImpl implements BasePlacardService {

	private final static Logger logger = LoggerFactory.getLogger(BasePlacardServiceImpl.class);

	@Resource
	private BasePlacardDAO basePlacardDAO;

	@Resource
	private BasePlacardScopeDAO basePlacardScopeDAO;

	@Resource
	private BasePlacardMemberDAO basePlacardMemberDAO;

	@Override
	public ExecuteResult<Long> getUnReadSellerPlacardCount(Long memberId) {
		ExecuteResult<Long> result = new ExecuteResult<Long>();
		PlacardCondition placardCondition = new PlacardCondition();
		Long count = new Long(0);

		try {
			placardCondition.setMemberId(memberId);
			placardCondition.setStatus(PlacardStatusEnum.DELETED.getValue());
			placardCondition.setPlacardType(PlacardTypeEnum.SELLER.getValue());
			placardCondition.setHasRead(String.valueOf(YesNoEnum.NO.getValue()));
			count = basePlacardDAO.getSellerPlacardCount(placardCondition);
			result.setResult(count);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateAllUnReadSellerPlacardReadStatus(Long memberId, Long modifyId,
			String modifyName) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BasePlacardMember placardMember = new BasePlacardMember();
		try {
			placardMember.setMemberId(memberId);
			placardMember.setStatus(YesNoEnum.YES.getValue());
			placardMember.setModifyId(modifyId);
			placardMember.setModifyName(modifyName);
			placardMember.setDeleteFlag(YesNoEnum.NO.getValue());
			basePlacardMemberDAO.updateAllPlacardReadStatus(placardMember);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateUnReadSellerPlacard2HasRead(Long placardId, Long memberId, Long userId,
			String userName) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BasePlacardMember placardMember = new BasePlacardMember();
		try {
			placardMember.setPlacardId(placardId);
			placardMember.setMemberId(memberId);
			placardMember.setStatus(YesNoEnum.YES.getValue());
			placardMember.setModifyId(userId);
			placardMember.setModifyName(userName);
			placardMember.setDeleteFlag(YesNoEnum.NO.getValue());
			basePlacardMemberDAO.updatePlacardReadStatus(placardMember);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<PlacardInfo>> getPlacardList(PlacardCondition placardCondition,
			Pager<PlacardCondition> pager) {
		ExecuteResult<DataGrid<PlacardInfo>> result = new ExecuteResult<DataGrid<PlacardInfo>>();
		DataGrid<PlacardInfo> datagrid = new DataGrid<PlacardInfo>();
		placardCondition.setStatus(PlacardStatusEnum.DELETED.getValue());
		placardCondition.setSellerSendType(PlacardTypeEnum.SELLER.getValue());
		placardCondition.setPlatformSendType(PlacardTypeEnum.PLATFORM.getValue());
		placardCondition.setDeleteFlag(YesNoEnum.NO.getValue());
		placardCondition.setIsValidForever(YesNoEnum.YES.getValue());
		placardCondition.setAllBuyerType(PlacardScopeTypeEnum.ALL_BUYER.getValue());
		placardCondition.setPartBuyerType(PlacardScopeTypeEnum.PART_BUYER.getValue());
		if (PlacardTypeEnum.ALL.getValue().equals(placardCondition.getPlacardType())) {
			datagrid = getAllPlacardList(placardCondition, pager);
		} else if (PlacardTypeEnum.PLATFORM.getValue().equals(placardCondition.getPlacardType())) {
			datagrid = getPlatformPlacardList(placardCondition, pager);
		} else if (PlacardTypeEnum.SELLER.getValue().equals(placardCondition.getPlacardType())) {
			datagrid = getSellerPlacardList(placardCondition, pager);
		}
		result.setResult(datagrid);
		return result;
	}

	/**
	 * 根据会员ID查询所有公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	private DataGrid<PlacardInfo> getAllPlacardList(PlacardCondition placardCondition, Pager<PlacardCondition> pager) {

		DataGrid<PlacardInfo> result = new DataGrid<PlacardInfo>();
		List<BasePlacard> placardList = null;
		List<PlacardInfo> resultPlacardList = new ArrayList<PlacardInfo>();
		long count = 0;
		count = basePlacardDAO.getAllPlacardCount(placardCondition);
		if (count > 0) {
			placardList = basePlacardDAO.getAllPlacardList(placardCondition, pager);
			resultPlacardList = makePlacardInfoList(placardList);
		}
		result.setRows(resultPlacardList);
		result.setTotal(count);
		return result;
	}

	/**
	 * 根据会员ID查询平台公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	private DataGrid<PlacardInfo> getPlatformPlacardList(PlacardCondition placardCondition,
			Pager<PlacardCondition> pager) {

		DataGrid<PlacardInfo> result = new DataGrid<PlacardInfo>();
		List<BasePlacard> placardList = null;
		List<PlacardInfo> resultPlacardList = new ArrayList<PlacardInfo>();
		long count = 0;
		count = basePlacardDAO.getPlatformPlacardCount(placardCondition);
		if (count > 0) {
			placardList = basePlacardDAO.getPlatformPlacardList(placardCondition, pager);
			resultPlacardList = makePlacardInfoList(placardList);
		}
		result.setRows(resultPlacardList);
		result.setTotal(count);
		return result;
	}

	/**
	 * 根据会员ID查询商家公告列表
	 * 
	 * @param placardCondition
	 * @param pager
	 * @return
	 */
	private DataGrid<PlacardInfo> getSellerPlacardList(PlacardCondition placardCondition,
			Pager<PlacardCondition> pager) {

		DataGrid<PlacardInfo> result = new DataGrid<PlacardInfo>();
		List<BasePlacard> placardList = null;
		List<PlacardInfo> resultPlacardList = new ArrayList<PlacardInfo>();
		long count = 0;
		count = basePlacardDAO.getSellerPlacardCount(placardCondition);
		if (count > 0) {
			placardList = basePlacardDAO.getSellerPlacardList(placardCondition, pager);
			resultPlacardList = makePlacardInfoList(placardList);
		}
		result.setRows(resultPlacardList);
		result.setTotal(count);
		return result;
	}

	/**
	 * 将BasePlacard转成PlacardInfo
	 * 
	 * @param placardList
	 * @return
	 */
	private List<PlacardInfo> makePlacardInfoList(List<BasePlacard> placardList) {
		List<PlacardInfo> resultPlacardList = new ArrayList<PlacardInfo>();
		PlacardInfo placardInfo = null;
		long placardId = 0;
		if (placardList != null && placardList.size() > 0) {
			for (BasePlacard placardObj : placardList) {
				placardInfo = new PlacardInfo();
				placardId = placardObj.getPlacardId();
				placardInfo.setPlacardId(placardId);
				placardInfo.setPlacardCode("Y" + String.format("%09d", placardId));
				placardInfo.setTitle(placardObj.getTitle());
				placardInfo.setContent(placardObj.getContent());
				placardInfo.setPublishTime(placardObj.getPublishTime());
				placardInfo.setPicAttachment(placardObj.getPicAttachment());
				placardInfo.setHasUrl(placardObj.getHasUrl());
				placardInfo.setUrl(placardObj.getUrl());
				placardInfo.setComment(placardObj.getComment());
				placardInfo.setHasRead(placardObj.getHasRead());
				resultPlacardList.add(placardInfo);
			}
		}
		return resultPlacardList;
	}

	@Override
	public ExecuteResult<PlacardInfo> getPlacardDetailById(Long id, Long memberId, Long userId, String userName) {
		ExecuteResult<PlacardInfo> result = new ExecuteResult<PlacardInfo>();
		BasePlacard basePlacard = null;
		PlacardInfo placardInfo = new PlacardInfo();
		BasePlacardMember placardMember = new BasePlacardMember();
		long placardId = 0;
		try {
			basePlacard = basePlacardDAO.queryById(id);
			if (basePlacard == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_PLACARD_ERROR, "不存在ID=" + id + "的公告信息");
			}
			if (PlacardStatusEnum.DELETED.getValue().equals(basePlacard.getStatus())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PLACARD_HAS_DELETE, "公告信息ID=" + id + "已被删除");
			}
			placardId = placardInfo.getPlacardId();
			placardInfo.setPlacardId(placardId);
			placardInfo.setPlacardCode("Y" + String.format("%09d", placardId));
			placardInfo.setTitle(basePlacard.getTitle());
			placardInfo.setContent(basePlacard.getContent());
			placardInfo.setPublishTime(basePlacard.getPublishTime());
			placardInfo.setPicAttachment(basePlacard.getPicAttachment());
			placardInfo.setHasUrl(basePlacard.getHasUrl());
			placardInfo.setUrl(basePlacard.getUrl());
			placardInfo.setComment(basePlacard.getComment());
			placardMember.setPlacardId(id);
			placardMember.setMemberId(memberId);
			placardMember.setStatus(YesNoEnum.YES.getValue());
			placardMember.setModifyId(userId);
			placardMember.setModifyName(userName);
			placardMember.setDeleteFlag(YesNoEnum.NO.getValue());
			basePlacardMemberDAO.updatePlacardReadStatus(placardMember);
			result.setResult(placardInfo);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteSellerPlacard4Member(Long placardId, Long memberId, Long userId,
			String userName) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BasePlacardMember memberParameter = new BasePlacardMember();
		try {
			memberParameter.setPlacardId(placardId);
			memberParameter.setMemberId(memberId);
			memberParameter.setModifyId(userId);
			memberParameter.setModifyName(userName);
			basePlacardMemberDAO.delete(memberParameter);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<BasePlacardDTO>> queryPlatformPlacardList(Pager<BasePlacardDTO> pager) {
		ExecuteResult<DataGrid<BasePlacardDTO>> result = new ExecuteResult<DataGrid<BasePlacardDTO>>();
		DataGrid<BasePlacardDTO> datagrid = null;
		BasePlacardDTO parameter = new BasePlacardDTO();
		parameter.setSendType(PlacardTypeEnum.PLATFORM.getValue());
		datagrid = queryBasePlacardList(parameter, pager);
		result.setResult(datagrid);
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<BasePlacardDTO>> querySellerPlacardList(Long sendSellerId,
			Pager<BasePlacardDTO> pager) {
		ExecuteResult<DataGrid<BasePlacardDTO>> result = new ExecuteResult<DataGrid<BasePlacardDTO>>();
		DataGrid<BasePlacardDTO> datagrid = null;
		BasePlacardDTO parameter = new BasePlacardDTO();
		parameter.setSendType(PlacardTypeEnum.SELLER.getValue());
		parameter.setSendSellerId(sendSellerId);
		datagrid = queryBasePlacardList(parameter, pager);
		result.setResult(datagrid);
		return result;
	}

	/**
	 * 根据检索条件查询公告详细信息
	 * 
	 * @param basePlacardDTO
	 * @param pager
	 * @return
	 */
	private DataGrid<BasePlacardDTO> queryBasePlacardList(BasePlacardDTO basePlacardDTO, Pager<BasePlacardDTO> pager) {
		DataGrid<BasePlacardDTO> result = new DataGrid<BasePlacardDTO>();
		List<BasePlacard> placardList = null;
		BasePlacardDTO placardDTO = null;
		BasePlacard parameter = new BasePlacard();
		List<BasePlacardDTO> placardDTOList = new ArrayList<BasePlacardDTO>();
		List<BasePlacardScopeDTO> placardScopeList = null;
		long count = 0;
		long placardId = 0;
		parameter.setSendType(basePlacardDTO.getSendType());
		parameter.setSendSellerId(basePlacardDTO.getSendSellerId());
		parameter.setStatus(PlacardStatusEnum.DELETED.getValue());
		count = basePlacardDAO.queryCount(parameter);
		if (count > 0) {
			placardList = basePlacardDAO.queryList(parameter, pager);
			for (BasePlacard obj : placardList) {
				placardDTO = new BasePlacardDTO();
				placardId = obj.getPlacardId();
				placardDTO.setPlacardId(placardId);
				placardDTO.setPlacardCode("Y" + String.format("%09d", placardId));
				placardDTO.setTitle(obj.getTitle());
				placardDTO.setContent(obj.getContent());
				placardDTO.setIsPublishImmediately(obj.getIsPublishImmediately());
				placardDTO.setIsValidForever(obj.getIsValidForever());
				placardDTO.setPublishTime(obj.getPublishTime());
				placardDTO.setExpireTime(obj.getExpireTime());
				placardDTO.setApplyTime(obj.getApplyTime());
				placardDTO.setPicAttachment(obj.getPicAttachment());
				placardDTO.setHasUrl(obj.getHasUrl());
				placardDTO.setUrl(obj.getUrl());
				placardDTO.setComment(obj.getComment());
				placardDTO.setStatus(obj.getStatus());
				placardDTO.setStatusName(PlacardStatusEnum.getPlacardStatusName(placardDTO.getStatus()));
				placardDTO.setIsTop(obj.getIsTop());
				placardDTO.setCreateId(obj.getCreateId());
				placardDTO.setCreateName(obj.getCreateName());
				placardDTO.setCreateTime(obj.getCreateTime());
				placardDTO.setModifyId(obj.getModifyId());
				placardDTO.setModifyName(obj.getModifyName());
				placardDTO.setModifyTime(obj.getModifyTime());
				placardScopeList = getBasePlacardScopeByPlacardId(placardId);
				placardDTO.setPlacardScopeList(placardScopeList);
				placardDTOList.add(placardDTO);
			}
			result.setRows(placardDTOList);
			result.setTotal(count);
		}
		return result;
	}

	@Override
	public ExecuteResult<BasePlacardDTO> queryBasePlacardById(Long id) {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		List<BasePlacardScopeDTO> placardScopeList = null;
		BasePlacard basePlacard = null;
		BasePlacardDTO placardDTO = new BasePlacardDTO();
		long placardId = 0;
		try {
			basePlacard = basePlacardDAO.queryById(id);
			if (basePlacard == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_PLACARD_ERROR, "不存在ID=" + id + "的公告信息");
			}
			if (PlacardStatusEnum.DELETED.getValue().equals(basePlacard.getStatus())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PLACARD_HAS_DELETE, "公告信息ID=" + id + "已被删除");
			}
			placardId = basePlacard.getPlacardId();
			placardDTO.setPlacardId(placardId);
			placardDTO.setPlacardCode("Y" + String.format("%09d", placardId));
			placardDTO.setTitle(basePlacard.getTitle());
			placardDTO.setContent(basePlacard.getContent());
			placardDTO.setIsPublishImmediately(basePlacard.getIsPublishImmediately());
			placardDTO.setIsValidForever(basePlacard.getIsValidForever());
			placardDTO.setPublishTime(basePlacard.getPublishTime());
			placardDTO.setExpireTime(basePlacard.getExpireTime());
			placardDTO.setApplyTime(basePlacard.getApplyTime());
			placardDTO.setPicAttachment(basePlacard.getPicAttachment());
			placardDTO.setHasUrl(basePlacard.getHasUrl());
			placardDTO.setUrl(basePlacard.getUrl());
			placardDTO.setComment(basePlacard.getComment());
			placardDTO.setStatus(basePlacard.getStatus());
			placardDTO.setStatusName(PlacardStatusEnum.getPlacardStatusName(placardDTO.getStatus()));
			placardDTO.setIsTop(basePlacard.getIsTop());
			placardDTO.setCreateId(basePlacard.getCreateId());
			placardDTO.setCreateName(basePlacard.getCreateName());
			placardDTO.setCreateTime(basePlacard.getCreateTime());
			placardDTO.setModifyId(basePlacard.getModifyId());
			placardDTO.setModifyName(basePlacard.getModifyName());
			placardDTO.setModifyTime(basePlacard.getModifyTime());
			placardScopeList = getBasePlacardScopeByPlacardId(placardId);
			placardDTO.setPlacardScopeList(placardScopeList);
			result.setResult(placardDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 根据公告ID取得公告发送对象列表
	 * 
	 * @param placardId
	 * @return
	 */
	private List<BasePlacardScopeDTO> getBasePlacardScopeByPlacardId(Long placardId) {
		BasePlacardScope scopeParameter = new BasePlacardScope();
		List<BasePlacardScopeDTO> scopeDTOList = new ArrayList<BasePlacardScopeDTO>();
		List<BasePlacardScope> placardScopeList = null;
		BasePlacardScopeDTO scopeDTO = null;
		String oldScopeType = "";
		String scopeType = "";
		String partType = "";
		String partDetail = "";
		Long buyerGroupId = null;
		List<String> targetBuyerGradeList = new ArrayList<String>();
		List<Long> targetBuyerGroupList = new ArrayList<Long>();

		scopeParameter.setPlacardId(placardId);
		scopeParameter.setDeleteFlag(YesNoEnum.NO.getValue());
		placardScopeList = basePlacardScopeDAO.queryList(scopeParameter, null);
		if (placardScopeList != null && placardScopeList.size() > 0) {
			scopeDTO = new BasePlacardScopeDTO();
			for (BasePlacardScope scope : placardScopeList) {
				scopeType = scope.getScopeType();
				if (!PlacardScopeTypeEnum.PART_BUYER.getValue().equals(scopeType)) {
					scopeDTO.setScopeType(scopeType);
					scopeDTOList.add(scopeDTO);
					scopeDTO = new BasePlacardScopeDTO();
					targetBuyerGradeList = new ArrayList<String>();
					targetBuyerGroupList = new ArrayList<Long>();
					continue;
				}
				if (StringUtils.isBlank(oldScopeType)) {
					oldScopeType = scopeType;
				}
				if (!oldScopeType.equals(scopeType)) {
					scopeDTO.setTargetBuyerGradeList(targetBuyerGradeList);
					scopeDTO.setTargetBuyerGroupList(targetBuyerGroupList);
					scopeDTOList.add(scopeDTO);
					scopeDTO = new BasePlacardScopeDTO();
					targetBuyerGradeList = new ArrayList<String>();
					targetBuyerGroupList = new ArrayList<Long>();
					oldScopeType = scopeType;
				}
				if (oldScopeType.equals(scopeType)) {
					scopeDTO.setScopeType(scopeType);
					partType = scope.getPartType();
					partDetail = scope.getPartDetail();
					if (Constants.PLACARD_SCOPE_BUYER_GRADE.equals(partType)) {
						targetBuyerGradeList.add(partDetail);
					} else if (Constants.PLACARD_SCOPE_BUYER_GROUP.equals(partType)) {
						try {
							buyerGroupId = Long.parseLong(partDetail);
						} catch (NumberFormatException e) {
							continue;
						}
						targetBuyerGroupList.add(buyerGroupId);
					}
				}
			}
			scopeDTO.setTargetBuyerGradeList(targetBuyerGradeList);
			scopeDTO.setTargetBuyerGroupList(targetBuyerGroupList);
			scopeDTOList.add(scopeDTO);
		}
		return scopeDTOList;
	}

	@Override
	public ExecuteResult<BasePlacardDTO> addPlatformBasePlacard(BasePlacardDTO basePlacardDTO) {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		try {
			checkInputParameter("platform", "add", basePlacardDTO);
			basePlacardDTO.setSendType(PlacardTypeEnum.PLATFORM.getValue());
			basePlacardDTO.setStatus(PlacardStatusEnum.PENDING.getValue());
			if (YesNoEnum.YES.getValue() == basePlacardDTO.getIsValidForever()) {
				basePlacardDTO.setStatus(PlacardStatusEnum.SENDING.getValue());
				basePlacardDTO.setPublishTime(new Date());
				basePlacardDTO.setExpireTime(DateUtils.parse("9999-12-31 23:59:59", DateUtils.YYDDMMHHMMSS));
			} else if (YesNoEnum.YES.getValue() == basePlacardDTO.getIsPublishImmediately()) {
				if (!(new Date()).before(basePlacardDTO.getPublishTime())) {
					basePlacardDTO.setStatus(PlacardStatusEnum.SENDING.getValue());
				}
			}
			result = addBasePlacard(basePlacardDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<BasePlacardDTO> addSellerPlacard(BasePlacardDTO basePlacardDTO) {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		try {
			checkInputParameter("seller", "add", basePlacardDTO);
			basePlacardDTO.setSendType(PlacardTypeEnum.SELLER.getValue());
			basePlacardDTO.setStatus(PlacardStatusEnum.PENDING.getValue());
			if (!(new Date()).before(basePlacardDTO.getPublishTime())) {
				basePlacardDTO.setStatus(PlacardStatusEnum.SENT.getValue());
			}
			result = addBasePlacard(basePlacardDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	/**
	 * 新建公告信息
	 * 
	 * @param basePlacardDTO
	 * @return
	 * @throws Exception
	 */
	private ExecuteResult<BasePlacardDTO> addBasePlacard(BasePlacardDTO basePlacardDTO) throws Exception {

		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		BasePlacard placard = new BasePlacard();
		List<BasePlacardScope> scopeList = null;
		Long placardId = null;
		List<BasePlacardMember> memberList = null;

		placard.setSendType(basePlacardDTO.getSendType());
		placard.setSendSellerId(basePlacardDTO.getSendSellerId());
		placard.setTitle(basePlacardDTO.getTitle());
		placard.setContent(basePlacardDTO.getContent());
		placard.setIsPublishImmediately(basePlacardDTO.getIsPublishImmediately());
		placard.setIsValidForever(basePlacardDTO.getIsValidForever());
		placard.setPublishTime(basePlacardDTO.getPublishTime());
		placard.setExpireTime(basePlacardDTO.getExpireTime());
		placard.setPicAttachment(basePlacardDTO.getPicAttachment());
		placard.setHasUrl(basePlacardDTO.getHasUrl());
		placard.setUrl(basePlacardDTO.getUrl());
		placard.setComment(basePlacardDTO.getComment());
		placard.setStatus(basePlacardDTO.getStatus());
		placard.setIsTop(basePlacardDTO.getIsTop());
		placard.setCreateId(basePlacardDTO.getCreateId());
		placard.setCreateName(basePlacardDTO.getCreateName());
		basePlacardDAO.add(placard);
		placardId = placard.getPlacardId();
		basePlacardDTO.setPlacardId(placardId);
		scopeList = makeBasePlacardScopeList(basePlacardDTO);
		basePlacardScopeDAO.addBasePlacardScopeList(scopeList);
		if (basePlacardDTO.getBuyerIdList() != null && basePlacardDTO.getBuyerIdList().size() > 0) {
			memberList = makeBasePlacardMemberList(basePlacardDTO);
			basePlacardMemberDAO.addBasePlacardMemberList(memberList);
		}
		result.setResultMessage("增加成功！");
		return result;
	}

	/**
	 * 商家公告根据输入的公告对象生成公告会员信息数据
	 * 
	 * @param basePlacardDTO
	 * @return
	 */
	private List<BasePlacardMember> makeBasePlacardMemberList(BasePlacardDTO basePlacardDTO) {
		List<BasePlacardMember> memberList = new ArrayList<BasePlacardMember>();
		BasePlacardMember member = null;
		List<Long> buyerIdList = basePlacardDTO.getBuyerIdList();
		Long operatorId = basePlacardDTO.getCreateId() == null ? basePlacardDTO.getModifyId()
				: basePlacardDTO.getCreateId();
		String operatorName = StringUtils.isEmpty(basePlacardDTO.getCreateName()) ? basePlacardDTO.getModifyName()
				: basePlacardDTO.getCreateName();
		if (buyerIdList != null && buyerIdList.size() > 0) {
			for (Long buyerId : buyerIdList) {
				member = new BasePlacardMember();
				member.setPlacardId(basePlacardDTO.getPlacardId());
				member.setMemberId(buyerId);
				member.setStatus(YesNoEnum.NO.getValue());
				member.setCreateId(operatorId);
				member.setCreateName(operatorName);
				memberList.add(member);
			}
		}
		return memberList;
	}

	@Override
	public ExecuteResult<BasePlacardDTO> updatePlatformBasePlacard(BasePlacardDTO basePlacardDTO) {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		try {
			checkInputParameter("platform", "update", basePlacardDTO);
			if (YesNoEnum.YES.getValue() == basePlacardDTO.getIsValidForever()) {
				basePlacardDTO.setStatus(PlacardStatusEnum.SENDING.getValue());
				basePlacardDTO.setPublishTime(new Date());
				basePlacardDTO.setExpireTime(DateUtils.parse("9999-12-31 23:59:59", DateUtils.YYDDMMHHMMSS));
			} else if (YesNoEnum.YES.getValue() == basePlacardDTO.getIsPublishImmediately()) {
				if (!(new Date()).before(basePlacardDTO.getPublishTime())) {
					basePlacardDTO.setStatus(PlacardStatusEnum.SENDING.getValue());
				}
			}
			result = updateBasePlacard(basePlacardDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<BasePlacardDTO> updateSellerBasePlacard(BasePlacardDTO basePlacardDTO) {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		try {
			checkInputParameter("seller", "update", basePlacardDTO);
			if (!(new Date()).before(basePlacardDTO.getPublishTime())) {
				basePlacardDTO.setStatus(PlacardStatusEnum.SENT.getValue());
			}
			result = updateBasePlacard(basePlacardDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	/**
	 * 更新公告信息
	 * 
	 * @param basePlacardDTO
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	private ExecuteResult<BasePlacardDTO> updateBasePlacard(BasePlacardDTO basePlacardDTO)
			throws BaseCenterBusinessException, Exception {
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		BasePlacard basePlacard = null;
		BasePlacard parameter = new BasePlacard();
		BasePlacardScope scopeParameter = new BasePlacardScope();
		BasePlacardMember memberParameter = new BasePlacardMember();
		List<BasePlacardScope> scopeList = new ArrayList<BasePlacardScope>();
		Long id = basePlacardDTO.getPlacardId();
		List<BasePlacardMember> memberList = null;
		basePlacard = basePlacardDAO.queryById(id);

		if (basePlacard == null) {
			throw new BaseCenterBusinessException(ReturnCodeConst.NO_PLACARD_ERROR, "不存在ID=" + id + "的公告信息");
		}
		if (PlacardStatusEnum.DELETED.getValue().equals(basePlacard.getStatus())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PLACARD_HAS_DELETE, "公告信息ID=" + id + "已被删除");
		}
		if (PlacardStatusEnum.SENDING.getValue().equals(basePlacard.getStatus())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PLACARD_IS_SENDING, "公告信息ID=" + id + "正在发送中不能修改");
		}
		if (PlacardStatusEnum.SENT.getValue().equals(basePlacard.getStatus())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PLACARD_HAS_SENT, "公告信息ID=" + id + "已发送不能修改");
		}
		parameter.setPlacardId(basePlacardDTO.getPlacardId());
		parameter.setSendType(basePlacardDTO.getSendType());
		parameter.setSendSellerId(basePlacardDTO.getSendSellerId());
		parameter.setTitle(basePlacardDTO.getTitle());
		parameter.setContent(basePlacardDTO.getContent());
		parameter.setIsPublishImmediately(basePlacardDTO.getIsPublishImmediately());
		parameter.setIsValidForever(basePlacardDTO.getIsValidForever());
		parameter.setPublishTime(basePlacardDTO.getPublishTime());
		parameter.setExpireTime(basePlacardDTO.getExpireTime());
		parameter.setPicAttachment(basePlacardDTO.getPicAttachment());
		parameter.setHasUrl(basePlacardDTO.getHasUrl());
		parameter.setUrl(basePlacardDTO.getUrl());
		parameter.setComment(basePlacardDTO.getComment());
		parameter.setStatus(basePlacardDTO.getStatus());
		parameter.setIsTop(basePlacardDTO.getIsTop());
		parameter.setModifyId(basePlacardDTO.getModifyId());
		parameter.setModifyName(basePlacardDTO.getModifyName());
		basePlacardDAO.update(parameter);
		scopeParameter.setPlacardId(id);
		scopeParameter.setDeleteFlag(YesNoEnum.YES.getValue());
		scopeParameter.setModifyId(basePlacardDTO.getModifyId());
		scopeParameter.setModifyName(basePlacardDTO.getModifyName());
		basePlacardScopeDAO.update(scopeParameter);
		scopeList = makeBasePlacardScopeList(basePlacardDTO);
		basePlacardScopeDAO.addBasePlacardScopeList(scopeList);
		if (basePlacardDTO.getBuyerIdList() != null && basePlacardDTO.getBuyerIdList().size() > 0) {
			memberParameter.setPlacardId(id);
			memberParameter.setDeleteFlag(YesNoEnum.YES.getValue());
			memberParameter.setModifyId(basePlacardDTO.getModifyId());
			memberParameter.setModifyName(basePlacardDTO.getModifyName());
			basePlacardMemberDAO.update(memberParameter);
			memberList = makeBasePlacardMemberList(basePlacardDTO);
			basePlacardMemberDAO.addBasePlacardMemberList(memberList);
		}
		result.setResultMessage("更新成功！");
		return result;
	}

	/**
	 * 根据输入的公告对象生成公告对象信息数据
	 * 
	 * @param basePlacardDTO
	 * @return
	 */
	private List<BasePlacardScope> makeBasePlacardScopeList(BasePlacardDTO basePlacardDTO) {
		List<BasePlacardScope> scopeList = new ArrayList<BasePlacardScope>();
		List<BasePlacardScopeDTO> scopeDTOList = basePlacardDTO.getPlacardScopeList();
		BasePlacardScope scopeObj = null;
		Long placardId = basePlacardDTO.getPlacardId();
		String scopeType = "";
		List<String> targetBuyerGradeList = null;
		List<Long> targetBuyerGroupList = null;
		Long operatorId = basePlacardDTO.getCreateId() == null ? basePlacardDTO.getModifyId()
				: basePlacardDTO.getCreateId();
		String operatorName = StringUtils.isEmpty(basePlacardDTO.getCreateName()) ? basePlacardDTO.getModifyName()
				: basePlacardDTO.getCreateName();
		if (scopeDTOList != null && scopeDTOList.size() > 0) {
			for (BasePlacardScopeDTO dto : scopeDTOList) {
				scopeType = dto.getScopeType();
				if (!PlacardScopeTypeEnum.PART_BUYER.getValue().equals(scopeType)) {
					scopeObj = new BasePlacardScope();
					scopeObj.setPlacardId(placardId);
					scopeObj.setScopeType(scopeType);
					scopeObj.setPartType("");
					scopeObj.setPartDetail("");
					scopeObj.setCreateId(operatorId);
					scopeObj.setCreateName(operatorName);
					scopeList.add(scopeObj);
					continue;
				}
				targetBuyerGradeList = dto.getTargetBuyerGradeList();
				targetBuyerGroupList = dto.getTargetBuyerGroupList();
				if (targetBuyerGradeList != null && targetBuyerGradeList.size() > 0) {
					for (String gradeStr : targetBuyerGradeList) {
						scopeObj = new BasePlacardScope();
						scopeObj.setPlacardId(placardId);
						scopeObj.setScopeType(scopeType);
						scopeObj.setPartType(Constants.PLACARD_SCOPE_BUYER_GRADE);
						scopeObj.setPartDetail(gradeStr);
						scopeObj.setCreateId(operatorId);
						scopeObj.setCreateName(operatorName);
						scopeList.add(scopeObj);
					}
				}
				if (targetBuyerGroupList != null && targetBuyerGroupList.size() > 0) {
					for (Long groupId : targetBuyerGroupList) {
						scopeObj = new BasePlacardScope();
						scopeObj.setPlacardId(placardId);
						scopeObj.setScopeType(scopeType);
						scopeObj.setPartType(Constants.PLACARD_SCOPE_BUYER_GROUP);
						scopeObj.setPartDetail(groupId.toString());
						scopeObj.setCreateId(operatorId);
						scopeObj.setCreateName(operatorName);
						scopeList.add(scopeObj);
					}
				}
			}
		}
		return scopeList;
	}

	/**
	 * 检查输入参数
	 * 
	 * @param basePlacardDTO
	 * @throws BaseCenterBusinessException
	 */
	private void checkInputParameter(String from, String type, BasePlacardDTO basePlacardDTO)
			throws BaseCenterBusinessException {
		if (StringUtils.isBlank(basePlacardDTO.getTitle())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息标题不能是空白");
		}
		if (StringUtils.isBlank(basePlacardDTO.getContent())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息正文不能是空白");
		}
		if (basePlacardDTO.getPlacardScopeList() == null) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告对象不能是空白");
		}
		if (basePlacardDTO.getPlacardScopeList().size() == 0) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告对象不能是空白");
		}
		for (BasePlacardScopeDTO scope : basePlacardDTO.getPlacardScopeList()) {
			if (PlacardScopeTypeEnum.PART_BUYER.getValue().equals(scope.getScopeType())) {
				if (scope.getTargetBuyerGradeList() != null && scope.getTargetBuyerGradeList().size() > 0) {
					continue;
				}
				if (scope.getTargetBuyerGroupList() != null && scope.getTargetBuyerGroupList().size() > 0) {
					continue;
				}
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR,
						"公告对象选择" + PlacardScopeTypeEnum.PART_BUYER.getName() + "时未指定具体明细");
			}
		}
		if ("add".equals(type)) {
			if (basePlacardDTO.getCreateId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息创建人ID不能是空白");
			}
			if (StringUtils.isBlank(basePlacardDTO.getCreateName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息创建人名称不能是空白");
			}
		} else if ("update".equals(type)) {
			if (basePlacardDTO.getPlacardId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息ID不能是空白");
			}
			if (basePlacardDTO.getModifyId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息更新人ID不能是空白");
			}
			if (StringUtils.isBlank(basePlacardDTO.getModifyName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息更新人名称不能是空白");
			}
		}
		if ("platform".equals(from)) {
			if (YesNoEnum.YES.getValue() == basePlacardDTO.getIsPublishImmediately()) {
				if (basePlacardDTO.getPublishTime() == null || basePlacardDTO.getExpireTime() == null) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息的发布时间和失效时间必须输入");
				}
			}
			if (YesNoEnum.YES.getValue() == basePlacardDTO.getHasUrl()
					&& StringUtils.isBlank(basePlacardDTO.getUrl())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息选择有超链接是需要输入链接URL");
			}
		} else if ("seller".equals(from)) {
			if (basePlacardDTO.getPublishTime() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息的发布时间不能是空白");
			}
			if (basePlacardDTO.getBuyerIdList() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息的发送对象不能是空白");
			}
			if (basePlacardDTO.getBuyerIdList().size() == 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息的发送对象不能是空白");
			}
		}
	}

	@Override
	public ExecuteResult<BasePlacardDTO> deleteBasePlacard(List<Long> placardIdList, Long modifyId, String modifyName) {
		logger.info("\n 方法:[{}],入参:[{}]", "BasePlacardServiceImpl-deleteBasePlacard",
				JSONObject.toJSONString(placardIdList), JSONObject.toJSONString(modifyId),
				JSONObject.toJSONString(modifyName));
		ExecuteResult<BasePlacardDTO> result = new ExecuteResult<BasePlacardDTO>();
		BasePlacard parameter = new BasePlacard();
		BasePlacardMember memberParameter = new BasePlacardMember();
		PlacardCondition condition = new PlacardCondition();
		List<BasePlacard> delPlacardList = null;

		try {
			if (placardIdList == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息ID不能是空白");
			}
			if (placardIdList.size() == 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息ID不能是空白");
			}
			if (modifyId == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息更新人ID不能是空白");
			}
			if (StringUtils.isBlank(modifyName)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "公告信息更新人名称不能是空白");
			}
			condition.setPlacardIdList(placardIdList);
			condition.setStatus(PlacardStatusEnum.DELETED.getValue());
			delPlacardList = basePlacardDAO.queryDeletePlacardByIdList(condition);
			if (delPlacardList != null && delPlacardList.size() > 0) {
				placardIdList = new ArrayList<Long>();
				for (BasePlacard placard : delPlacardList) {
					placardIdList.add(placard.getPlacardId());
				}
				parameter.setPlacardIdList(placardIdList);
				parameter.setStatus(PlacardStatusEnum.DELETED.getValue());
				parameter.setModifyId(modifyId);
				parameter.setModifyName(modifyName);
				memberParameter.setPlacardIdList(placardIdList);
				memberParameter.setDeleteFlag(YesNoEnum.YES.getValue());
				memberParameter.setModifyId(modifyId);
				memberParameter.setModifyName(modifyName);
				basePlacardDAO.deletePlacardByIdList(parameter);
				basePlacardMemberDAO.deletePlacardMemberByIdList(memberParameter);
			}
			result.setResultMessage("删除成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;

	}

}
