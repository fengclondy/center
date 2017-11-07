package cn.htd.membercenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BoxRelationImportDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberShipDTO;
import cn.htd.membercenter.service.BoxRelationshipService;

@Service("boxRelationshipService")
public class BoxRelationshipServiceImpl implements BoxRelationshipService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBaseServiceImpl.class);

	@Resource
	BoxRelationshipDAO boxRelationshipDao;
	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;
	@Resource
	private BelongRelationshipDAO belongRelationshipDao;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Override
	public ExecuteResult<DataGrid<BelongRelationshipDTO>> selectBoxRelationList(Pager page, String companyName,
			String artificialPersonMobile, String boxCompanyName) {
		ExecuteResult<DataGrid<BelongRelationshipDTO>> rs = new ExecuteResult<DataGrid<BelongRelationshipDTO>>();
		DataGrid<BelongRelationshipDTO> dg = new DataGrid<BelongRelationshipDTO>();
		try {
			List<BelongRelationshipDTO> belongRelationList = null;
			List<ApplyBusiRelationDTO> applyBusiRelationDto = null;
			Long count = boxRelationshipDao.selectBoxRelationListCount(companyName, artificialPersonMobile, boxCompanyName, null);
			if (count != null) {
				belongRelationList = boxRelationshipDao.selectBoxRelationList(page, companyName, artificialPersonMobile,
						boxCompanyName, null);
				for (int i = 0; i < belongRelationList.size(); i++) {
					Long memberId = belongRelationList.get(i).getMemberId();
					Long seller = belongRelationList.get(i).getSellerId();
					try {
						// 根据会员ID和商家ID查询是否有经营关系
						applyBusiRelationDto = boxRelationshipDao.selectBusiRelationList(memberId, seller);
						if (applyBusiRelationDto.isEmpty()) {
							belongRelationList.get(i).setIsBusinessRelation("否");
						} else {
							belongRelationList.get(i).setIsBusinessRelation("是");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dg.setRows(belongRelationList);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResultMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectBelongRelationList=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<BelongRelationshipDTO> selectBoxRelationInfo(Long boxId) {
		ExecuteResult<BelongRelationshipDTO> rs = new ExecuteResult<BelongRelationshipDTO>();
		try {
			List<BelongRelationshipDTO> belongRelationList = null;
			BelongRelationshipDTO belongRelationshipDto = null;
			List<ApplyBusiRelationDTO> applyBusiRelationDto = null;
			belongRelationList = boxRelationshipDao.selectBoxRelationList(null, null, null, null, boxId);
			if (belongRelationList != null) {
				belongRelationshipDto = belongRelationList.get(0);
				Long memberId = belongRelationList.get(0).getMemberId();
				Long seller = belongRelationList.get(0).getSellerId();
				try {
					// 根据会员ID和商家ID查询是否有经营关系
					applyBusiRelationDto = boxRelationshipDao.selectBusiRelationList(memberId, seller);
					MemberBaseInfoDTO member = memberBaseOperationDAO.getMemberBaseInfoById(memberId,
							GlobalConstant.IS_BUYER);
					if (null != member) {
						belongRelationshipDto.setMemberType(member.getMemberType());
					}
					if (applyBusiRelationDto.isEmpty()) {
						belongRelationList.get(0).setIsBusinessRelation("否");
					} else {
						belongRelationList.get(0).setIsBusinessRelation("是");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				rs.setResult(belongRelationshipDto);
			} else {
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectBoxRelationInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Long> selectCompanyID(String companyName, String buyerSellerType) {
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
        List<MemberBaseDTO> memberList=null;
        String memberId = "";
		try {
			memberList = boxRelationshipDao.selectCompanyID(companyName, buyerSellerType);
			if (memberList == null) {
				rs.setResultMessage("要查询的会员/公司不存在！！");
				return rs;
			}
			if(memberList !=null && memberList.size()>1){
				for(MemberBaseDTO memberbase:memberList){
					if(memberbase.getSellerType().equals("2")){ //外部供应商优先
						memberId = memberbase.getMemberId();
					}
				}
			}else{
				memberId = memberList.get(0).getMemberId();
			}
			rs.setResult(memberId=="" ? null:Long.valueOf(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectCompanyID=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<BoxRelationImportDTO> importBoxRelation(
			List<ApplyBusiRelationDTO> businessRelatVerifyDtoList) {
		ExecuteResult<BoxRelationImportDTO> rs = new ExecuteResult<BoxRelationImportDTO>();
		int successCount = 0;
		int failCount = 0;
		List<ApplyBusiRelationDTO> sList = new ArrayList<ApplyBusiRelationDTO>();
		List<ApplyBusiRelationDTO> fList = new ArrayList<ApplyBusiRelationDTO>();
		List<Long> boxIds = new ArrayList<Long>();
		try {
			BoxRelationImportDTO box = new BoxRelationImportDTO();
			if (businessRelatVerifyDtoList == null || businessRelatVerifyDtoList.size() == 0) {
				rs.setResultMessage("fail");
				rs.addErrorMessage("请确认导入列表不为空");
			} else {
				int count = businessRelatVerifyDtoList.size();
				if (count > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {// 导入最大条数限制
					rs.setResultMessage("导入最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
				} else {
					for (int i = 0; i < businessRelatVerifyDtoList.size(); i++) {
						ApplyBusiRelationDTO applyBusiRelationDTO = businessRelatVerifyDtoList.get(i);
						String memberCode = applyBusiRelationDTO.getMemberCode();
						Long sellerId = applyBusiRelationDTO.getSellerId();
						Long memberId = 0L;
						if (null != applyBusiRelationDTO.getMemberId()
								&& 0 != applyBusiRelationDTO.getMemberId().intValue()) {
							memberId = applyBusiRelationDTO.getMemberId();
						} else if (null != memberCode && !"".equals(memberCode)) {
							memberId = memberBaseOperationDAO.getMemberIdByCode(applyBusiRelationDTO.getMemberCode());
						}
						if (memberId == null) {
							memberId = 0L;
						}
						if (memberId == null || 0 == memberId.intValue() || sellerId == null
								|| sellerId.intValue() == 0) {
							rs.setResultMessage("输入的memberId和sellerId为空");
							memberId = null;
							fList.add(applyBusiRelationDTO);
							failCount++;
						}

						if (memberId != null) {
							if (memberId.intValue() == sellerId.intValue()) {
								fList.add(applyBusiRelationDTO);
								failCount++;
							} else {
								ApplyBusiRelationDTO queryBox = applyRelationshipDao.queryBoxRelationInfo(memberId,
										sellerId);
								if (queryBox == null) {
									applyBusiRelationDTO.setMemberId(memberId);
									applyBusiRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
									try {
										Long num = applyRelationshipDao.insertBoxRelationInfo(applyBusiRelationDTO);
										if (num != null) {
											boxIds.add(applyBusiRelationDTO.getBoxId());
											sList.add(applyBusiRelationDTO);
											successCount++;
										}
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("BoxRelationshipServiceImpl----->importBoxRelation=" + e);
										rs.setResultMessage("error");
										fList.add(applyBusiRelationDTO);
										failCount++;
									}
								} else {
									boxIds.add(queryBox.getBoxId());
									applyBusiRelationDTO.setMemberId(memberId);
									applyBusiRelationDTO.setMemberCode(memberCode);
									applyBusiRelationDTO.setBoxId(queryBox.getBoxId());
									sList.add(applyBusiRelationDTO);
									successCount++;
								}
							}

						}
					}

				}
				box.setBoxIds(boxIds);
				box.setSuccCount(successCount);
				box.setSuccInfoList(sList);
				box.setFailCount(failCount);
				box.setFailInfoList(fList);
				rs.setResult(box);
				rs.setResultMessage("success");
				rs.setResultMessage("导入会员包厢关系成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->importBoxRelation=" + e);
			rs.setResultMessage("error");
		}

		return rs;
	}

	@Override
	public ExecuteResult<ApplyBusiRelationDTO> selectBusiRelation(Long memberId, Long sellerId, Long categoryId,
			Long brandId) {
		ExecuteResult<ApplyBusiRelationDTO> rs = new ExecuteResult<ApplyBusiRelationDTO>();

		try {
			if (memberId != null && sellerId != null && categoryId != null && brandId != null) {
				ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
				applyBusiRelationDto = boxRelationshipDao.selectBusiRelation(memberId, sellerId, categoryId, brandId);
				rs.setResult(applyBusiRelationDto);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("输入参数不够！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectBusiRelation=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Integer> selectMemberIdSellerIdLong(Long sellerId, Long memberId) {
		// TODO Auto-generated method stub
		ExecuteResult<Integer> rs = new ExecuteResult<Integer>();
		try {
			ApplyBusiRelationDTO applyBusiRelationDTO = applyRelationshipDao.queryBoxRelationInfo(memberId, sellerId);
			if (applyBusiRelationDTO != null) {
				rs.setResult(1);
				rs.setResultMessage("success");
			} else {
				rs.setResult(0);
				rs.setResultMessage("没有包厢关系");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectMemberIdSellerIdLong=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Integer> selectBusiRelationListLong(Long memberId, Long sellerId) {
		// TODO Auto-generated method stub
		ExecuteResult<Integer> rs = new ExecuteResult<Integer>();
		try {
			List<ApplyBusiRelationDTO> applyBusiRelationDTOList = boxRelationshipDao.selectBusiRelationList(memberId,
					sellerId);
			if (applyBusiRelationDTOList != null && applyBusiRelationDTOList.size() > 0) {
				rs.setResult(1);
				rs.setResultMessage("success");
			} else {
				rs.setResult(0);
				rs.setResultMessage("没有经营关系");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectBusiRelationListLong=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<ApplyBusiRelationDTO>> selectBusiRelationBuyerIdList(Long memberId) {
		ExecuteResult<List<ApplyBusiRelationDTO>> rs = new ExecuteResult<List<ApplyBusiRelationDTO>>();
		try {
			List<ApplyBusiRelationDTO> list = boxRelationshipDao.selectBusiRelationBuyerIdList(memberId);
			if (list.size() > 0 && list != null) {
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("数据为空");
			}
		} catch (Exception e) {
			logger.error("BoxRelationshipServiceImpl----->selectBusiRelationBuyerIdList=" + e);
			rs.setResultMessage("error");
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.BoxRelationshipService#
	 * selectBoxRelationListByMemberId(java.lang.Long)
	 */
	@Override
	public ExecuteResult<DataGrid<BoxRelationship>> selectBoxRelationListByMemberId(Long memberId) {
		ExecuteResult<DataGrid<BoxRelationship>> rs = new ExecuteResult<DataGrid<BoxRelationship>>();
		DataGrid<BoxRelationship> dg = new DataGrid<BoxRelationship>();
		try {
			List<BoxRelationship> belongRelationList = null;
			belongRelationList = boxRelationshipDao.selectBoxRelationListByMemberId(memberId);
			dg.setRows(belongRelationList);
			rs.setResult(dg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BoxRelationshipServiceImpl----->selectBoxRelationListByMemberId=" + e);
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberShipDTO>> selectBoxRelationship(String memberCode) {
		logger.info("根据会员code查询包厢关系名称和BoxRelationshipServiceImpl=====>selectBoxRelationship");
		ExecuteResult<List<MemberShipDTO>> rs = new ExecuteResult<List<MemberShipDTO>>();
		try {
			List<MemberShipDTO> memberShipDTOList = boxRelationshipDao.selectBoxRelationshipList(memberCode);
			if (memberShipDTOList != null && memberShipDTOList.size() > 0) {
				rs.setResult(memberShipDTOList);
				rs.setResultMessage("success");
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("没返回数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("查询异常");
			rs.setResultMessage("error");
			logger.error("根据会员code查询包厢关系名称和code：BoxRelationshipServiceImpl=====>selectBoxRelationship=" + e);
		}
		return rs;
	}

}
