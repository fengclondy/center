package cn.htd.membercenter.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.domain.BelongRelationship;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.SellerBelongRelationDTO;
import cn.htd.membercenter.service.BelongRelationshipService;

@Service("belongRelationshipService")
public class BelongRelationshipServiceImpl implements BelongRelationshipService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBaseServiceImpl.class);

	@Resource
	BelongRelationshipDAO belongRelationshipDao;
	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private RedisDB redisDB;
	
	@Override
	public ExecuteResult<DataGrid<BelongRelationshipDTO>> selectBelongRelationList(Pager page, String companyName,
			String contactMobile, String belongCompanyName) {
		ExecuteResult<DataGrid<BelongRelationshipDTO>> rs = new ExecuteResult<DataGrid<BelongRelationshipDTO>>();
		DataGrid<BelongRelationshipDTO> dg = new DataGrid<BelongRelationshipDTO>();
		try {
			Long count = belongRelationshipDao.selectBelongRelationListCount(companyName, contactMobile, belongCompanyName);
			if (count != null) {
				List<BelongRelationshipDTO> belongRelationList = null;
				belongRelationList = belongRelationshipDao.selectBelongRelationList(page, companyName, contactMobile,
						belongCompanyName);
				dg.setRows(belongRelationList);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResultMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BelongRelationshipServiceImpl----->selectBelongRelationList=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<BelongRelationshipDTO> selectBelongRelationInfo(Long memberId, Long sellerId) {
		ExecuteResult<BelongRelationshipDTO> rs = new ExecuteResult<BelongRelationshipDTO>();
		try {
			BelongRelationshipDTO belongRelationDto = null;
			if (memberId != null) {
				belongRelationDto = belongRelationshipDao.selectBelongRelationInfo(memberId, sellerId);
				if (belongRelationDto != null) {
					List<BelongRelationship> belongRelationshipList;
					belongRelationshipList = belongRelationshipDao.selectBelongHistoryList(memberId);
					if (belongRelationshipList != null) {
						belongRelationDto.setBelongHistory(belongRelationshipList);
					}

					rs.setResult(belongRelationDto);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("fail");
					rs.setResultMessage("要查询的数据不存在！！！");
				}

			} else {
				rs.setResultMessage("请输入要查询的会员ID！！！");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BelongRelationshipServiceImpl----->selectBelongRelationInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Transactional
	@Override
	public ExecuteResult<String> updateBelongRelationInfo(BelongRelationshipDTO belongRelationshipDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {

			if (belongRelationshipDto.getMemberId() != null) {
				belongRelationshipDao.updateBaseInfo(belongRelationshipDto);
				belongRelationshipDao.updateBelongInfo(belongRelationshipDto);
				belongRelationshipDto.setVerifyStatus("3");
				belongRelationshipDao.insertBelongInfo(belongRelationshipDto);
				ApplyBusiRelationDTO applyBusiRelationDto = null;
				applyBusiRelationDto = applyRelationshipDao.queryBoxRelationInfo(belongRelationshipDto.getMemberId(),
						belongRelationshipDto.getCurBelongSellerId());
				if (applyBusiRelationDto == null) {
					// 生成包厢关系
					applyBusiRelationDto = new ApplyBusiRelationDTO();
					applyBusiRelationDto.setMemberId(belongRelationshipDto.getMemberId());
					applyBusiRelationDto.setSellerId(belongRelationshipDto.getCurBelongSellerId());
					applyBusiRelationDto.setCreateId(belongRelationshipDto.getModifyId());
					applyBusiRelationDto.setCreateName(belongRelationshipDto.getModifyName());
					applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
					applyRelationshipDao.insertBoxRelationInfo(applyBusiRelationDto);
					addDownErpStatus(belongRelationshipDto);
				}

				rs.setResultMessage("success");

			} else {
				rs.setResultMessage("请输入要修改的会员ID！！！");
			}

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			logger.error("BelongRelationshipServiceImpl----->updateBelongRelationInfo=" + e);
			rs.setResultMessage("error");

		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> selectIsBelongRelation(Long buyerId, Long sellerId) {
		logger.info("根据买家和卖家id查询是否有归属关系：BelongRelationshipServiceImpl=====>selectIsBelongRelation");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Long i = belongRelationshipDao.selectIsBelongRelation(buyerId, sellerId);
			if (i != null) {
				rs.setResult(true);
				rs.setResultMessage("success");
			} else {
				rs.setResult(false);
				rs.addErrorMessage("fail");
				rs.setResultMessage("没返回数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("查询异常");
			rs.setResultMessage("error");
			logger.error("根据买家和卖家id查询是否有归属关系：BelongRelationshipServiceImpl=====>selectIsBelongRelation=" + e);
		}
		return rs;
	}

	/**
	 * 
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean addDownErpStatus(BelongRelationshipDTO dto) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(new Date()));
		MemberStatusInfo statusInfo = new MemberStatusInfo();
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
		statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setSyncErrorMsg("");
		statusInfo.setModifyId(dto.getModifyId());
		statusInfo.setModifyName(dto.getModifyName());
		statusInfo.setSyncKey("");
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(dto.getMemberId());
		statusInfo.setVerifyId(0L);
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setModifyTime(trancDate);
		statusInfo.setCreateId(dto.getModifyId());
		statusInfo.setCreateName(dto.getModifyName());
		statusInfo.setCreateTime(trancDate);
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

	@Override
	public ExecuteResult<List<SellerBelongRelationDTO>> queryBelongRelationListByMemberCodeList(
			List<String> memberCodeList) {
		ExecuteResult<List<SellerBelongRelationDTO>> rs = new ExecuteResult<List<SellerBelongRelationDTO>>();
		try {
			SellerBelongRelationDTO belongRelationDto = null;
			List<SellerBelongRelationDTO> brlist = new ArrayList<SellerBelongRelationDTO>();
			if (memberCodeList == null || memberCodeList.size() < 1) {
				rs.addErrorMessage("请输入要查询的会员编码！！！");
			} else {
				for (String memberCode : memberCodeList) {
					if (!StringUtils.isEmpty(memberCode)) {
						if (redisDB.existsHash(GlobalConstant.BELONG_RELATION_INFO, memberCode)) {
							String obj = redisDB.getHash(GlobalConstant.BELONG_RELATION_INFO, memberCode);
							try {
								brlist.add(JSON.parseObject(obj, SellerBelongRelationDTO.class));
							} catch (Exception e) {
							}
						} else {
							belongRelationDto = belongRelationshipDao.queryBelongRelationInfoByMemberCode(memberCode);
							if (belongRelationDto != null) {
								brlist.add(belongRelationDto);
								redisDB.setHash(GlobalConstant.BELONG_RELATION_INFO, memberCode, JSON.toJSONString(belongRelationDto));
							}
						}
					}
				}
				rs.setResult(brlist);
				rs.setResultMessage("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BelongRelationshipServiceImpl----->queryBelongRelationInfoByMemberCode=" + e);
			rs.addErrorMessage("error");
		}
		return rs;
	}
}
