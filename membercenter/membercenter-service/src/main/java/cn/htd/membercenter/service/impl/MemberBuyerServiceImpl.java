package cn.htd.membercenter.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.common.util.HttpUtils;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.ContractDAO;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dao.MemberBaseInfoDao;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberBuyerDao;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BoxRelationImportDTO;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.ContractSignRemindInfoDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberBuyerAuthenticationDTO;
import cn.htd.membercenter.dto.MemberBuyerCaListDTO;
import cn.htd.membercenter.dto.MemberBuyerCategoryDTO;
import cn.htd.membercenter.dto.MemberBuyerFinanceDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeChangeHistoryDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeMatrixDTO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerSupplierDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyDetailInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyInfoDTO;
import cn.htd.membercenter.dto.VerifyInfoDTO;
import cn.htd.membercenter.enums.AuditStatusEnum;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberBuyerService;
import cn.htd.membercenter.util.MemberUtil;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberBuyerServiceImpl
 * </p>
 * 
 * @author root
 * @date 2016年12月26日
 *       <p>
 *       Description: 会员中心 买家中心
 *       </p>
 */
@Service("memberBuyerService")
@SuppressWarnings("all")
public class MemberBuyerServiceImpl implements MemberBuyerService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBuyerServiceImpl.class);

	@Resource
	private MemberBuyerDao memberBuyerDao;
	@Resource
	private BaseAddressService baseAddressService;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private ItemBrandExportService itemBrandExportService;
	@Resource
	private BoxRelationshipDAO boxRelationshipDAO;
	@Resource
	private MemberVerifySaveDAO memberVerifySaveDAO;
	@Resource
	private MemberBaseInfoDao memberBaseInfoDao;
	@Autowired
	private MemberBaseService memberBaseService;
	@Resource
	private MemberBusinessRelationDAO memberBusinessRelationDAO;
	@Resource
	private MemberVerifySaveDAO verifyInfoDAO;
	@Resource
	private ContractDAO contractDAO;
	@Resource
	private MemberBaseDAO memberBaseDAO;

	/**
	 * 查询个人信息
	 */
	@Override
	public ExecuteResult<MemberBuyerPersonalInfoDTO> queryBuyerPersonalInfo(Long memberId) {
		ExecuteResult<MemberBuyerPersonalInfoDTO> rs = new ExecuteResult<MemberBuyerPersonalInfoDTO>();
		try {
			// 页面上的注册地址包括省市区镇 返回编码比较合适
			MemberBuyerPersonalInfoDTO queryBuyerPersonalInfo = memberBuyerDao.queryBuyerPersonalInfo(memberId);
			String verifyStatus = memberBuyerDao.getMemberVerifyStatus(memberId, "15");
			if (null != verifyStatus && GlobalConstant.VERIFY_STATUS_WAIT.equals(verifyStatus)) {
				List<MemberBuyerVerifyDetailInfoDTO> dtlist = memberBuyerDao.queryVerifyInfo(memberId, "15");
				if (dtlist != null && dtlist.size() > 0) {
					for (MemberBuyerVerifyDetailInfoDTO dt : dtlist) {
						if ("artificial_person_name".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setArtificialPersonName(dt.getAfterChange());
						}
						if ("company_name".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setCompanyName(dt.getAfterChange());
						}
						if ("birthday".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setBirthday(dt.getAfterChange());
						}
						if ("location_province".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setLocationProvince(dt.getAfterChange());
						}
						if ("location_city".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setLocationCity(dt.getAfterChange());
						}
						if ("location_county".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setLocationCounty(dt.getAfterChange());
						}
						if ("location_town".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setLocationTown(dt.getAfterChange());
						}
						if ("location_detail".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setLocationDetail(dt.getAfterChange());
						}
						if ("buyer_business_license_id".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setBuyerBusinessLicenseId(dt.getAfterChange());
						}
						if ("buyer_guarantee_license_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setBuyerGuaranteeLicensePicSrc(dt.getAfterChange());
						}
						if ("buyer_business_license_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setBuyerBusinessLicensePicSrc(dt.getAfterChange());
						}
						if ("business_license_certificate_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerPersonalInfo.setBusinessLicenseCertificatePicSrc(dt.getAfterChange());
						}
					}
				}
			}

			if (null != queryBuyerPersonalInfo.getCan_mall_login()
					&& null != queryBuyerPersonalInfo.getHas_guarantee_license()
					&& null != queryBuyerPersonalInfo.getHas_business_license()) {
				String memberType = MemberUtil.judgeMemberType(
						String.valueOf(queryBuyerPersonalInfo.getCan_mall_login()),
						String.valueOf(queryBuyerPersonalInfo.getHas_guarantee_license()),
						String.valueOf(queryBuyerPersonalInfo.getHas_business_license()));
				queryBuyerPersonalInfo.setMemberType(memberType);
			}
			if (!StringUtils.isEmpty(queryBuyerPersonalInfo.getLocationProvince())) {
				queryBuyerPersonalInfo.setLocationProvinceString(
						baseAddressService.getAddressName(queryBuyerPersonalInfo.getLocationProvince()));
			}
			if (!StringUtils.isEmpty(queryBuyerPersonalInfo.getLocationCity())) {
				queryBuyerPersonalInfo.setLocationCityString(
						baseAddressService.getAddressName(queryBuyerPersonalInfo.getLocationCity()));
			}
			if (!StringUtils.isEmpty(queryBuyerPersonalInfo.getLocationCounty())) {
				queryBuyerPersonalInfo.setLocationCountyString(
						baseAddressService.getAddressName(queryBuyerPersonalInfo.getLocationCounty()));
			}
			if (!StringUtils.isEmpty(queryBuyerPersonalInfo.getLocationTown())) {
				queryBuyerPersonalInfo.setLocationTownString(
						baseAddressService.getAddressName(queryBuyerPersonalInfo.getLocationTown()));
			}
			queryBuyerPersonalInfo.setVerifyStatus(verifyStatus);
			queryBuyerPersonalInfo.setRemark(memberBuyerDao.getMemberVerifyRemark(memberId, "15"));
			rs.setResult(queryBuyerPersonalInfo);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerPersonalInfo】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 保存、更新会员个人信息
	 */
	@Override
	public ExecuteResult<Boolean> updateBuyerPersonalInfo(MemberBuyerPersonalInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
				rs.addErrorMessage("修改人id，修改人名称 不能为空");
				throw new RuntimeException();
			}
			/**
			 * 注册地址的更改 传入传出的都是地址编码 因为地址是可以更改的
			 */
			/*
			 * if(!StringUtils.isEmpty(dto.getBirthdayYear()) &
			 * !StringUtils.isEmpty(dto.getBirthdayMonth()) &
			 * !StringUtils.isEmpty(dto.getBirthday())){ SimpleDateFormat sdf =
			 * new SimpleDateFormat("yyyyMMdd"); Calendar calendar =
			 * Calendar.getInstance();
			 * calendar.set(Integer.parseInt(dto.getBirthdayYear()),
			 * Integer.parseInt(dto.getBirthdayMonth()) - 1,
			 * Integer.parseInt(dto.getBirthdayDay()));
			 * dto.setBirthday(sdf.format(calendar.getTime())); }
			 */
			memberBuyerDao.updateBuyerCompanyInfo(dto);
			memberBuyerDao.updateBuyerPersonInfo(dto);
			memberBuyerDao.updateBuyerLicenceInfo(dto);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员经验值变动履历
	 */
	@Override
	public ExecuteResult<DataGrid<BuyerPointHistory>> getBuyerPointHis(BuyerHisPointDTO dto, Pager pager) {
		ExecuteResult<DataGrid<BuyerPointHistory>> rs = new ExecuteResult<DataGrid<BuyerPointHistory>>();
		DataGrid<BuyerPointHistory> dg = new DataGrid<BuyerPointHistory>();
		try {
			Long count = memberBaseOperationDAO.selectBuyerPointCount(dto);
			List<BuyerPointHistory> list = memberBaseOperationDAO.getBuyerPointHis(dto, pager);
			if (count > 0) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);

			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getBuyerPointHis=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员等级详细信息
	 */
	@Override
	public ExecuteResult<MemberBuyerGradeInfoDTO> queryBuyerGradeInfo(Long memberId) {
		ExecuteResult<MemberBuyerGradeInfoDTO> rs = new ExecuteResult<MemberBuyerGradeInfoDTO>();
		try {
			MemberBuyerGradeInfoDTO queryBuyerGradeInfo = memberBuyerDao.queryBuyerGradeInfo(memberId);
			// 计算会员升级路径 最高5级 5级返回null
			if (queryBuyerGradeInfo.getPointGrade() >= 1L & queryBuyerGradeInfo.getPointGrade() <= 4) {
				// 查询会员更高一级的会员经验值区间
				MemberBuyerGradeMatrixDTO buyerGradeInterval = memberBuyerDao
						.queryBuyerGradeInterval(queryBuyerGradeInfo.getPointGrade() + 1L);
				BigDecimal lowestPoint = new BigDecimal(buyerGradeInterval.getLowestPoint());
				// 计算差多少经验
				BigDecimal yearExpDistance = lowestPoint.subtract(queryBuyerGradeInfo.getYearExp());
				// 会员商城消费区间
				List<MemberBuyerGradeMatrixDTO> buyerScoreIntervalMall = memberBuyerDao.queryBuyerScoreIntervalMall();
				// 会员金融产品区间
				List<MemberBuyerGradeMatrixDTO> buyerScoreIntervalFinance = memberBuyerDao
						.queryBuyerScoreIntervalFinance();
				// 权重
				MemberBuyerGradeMatrixDTO buyerScoreWeight = memberBuyerDao.queryBuyerScoreWeight();
				JSONObject gradeWeight = JSON.parseObject(buyerScoreWeight.getBuyerGradeWeightJson());
				BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
				BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));

				List<MemberBuyerGradeMatrixDTO> matrixList = new ArrayList<MemberBuyerGradeMatrixDTO>();
				BigDecimal tempfincance = new BigDecimal(0);
				BigDecimal tempmall = new BigDecimal(0);
				// BigDecimal tempboth = new BigDecimal(0);

				// 金融产品 -单独
				for (MemberBuyerGradeMatrixDTO finance : buyerScoreIntervalFinance) {
					// 满足升级经验
					if (financeWeight.multiply(finance.getScore()).compareTo(yearExpDistance) > 0) {
						tempfincance = finance.getFromAmount();
						break;
					}
				}
				// 商城消费-单独
				for (MemberBuyerGradeMatrixDTO mall : buyerScoreIntervalMall) {
					// 满足升级经验
					if (mallWeight.multiply(mall.getScore()).compareTo(yearExpDistance) > 0) {
						tempmall = mall.getFromAmount();
						break;
					}
				}
				List<MemberBuyerGradeMatrixDTO> bothList = new ArrayList<MemberBuyerGradeMatrixDTO>();
				// 组合
				for (MemberBuyerGradeMatrixDTO finance : buyerScoreIntervalFinance) {
					for (MemberBuyerGradeMatrixDTO mall : buyerScoreIntervalMall) {
						if ((financeWeight.multiply(finance.getScore())).add(mallWeight.multiply(mall.getScore()))
								.compareTo(yearExpDistance) > 0 ? true : false) {
							MemberBuyerGradeMatrixDTO m = new MemberBuyerGradeMatrixDTO();
							m.setFinanceDistance(finance.getFromAmount());
							m.setMallDistance(mall.getFromAmount());
							bothList.add(m);
						}
					}
				}
				// 组合 花费最少的组合
				MemberBuyerGradeMatrixDTO tempboth = new MemberBuyerGradeMatrixDTO();
				for (int i = 0; i < bothList.size() - 1; i++) {
					MemberBuyerGradeMatrixDTO before = bothList.get(i);
					MemberBuyerGradeMatrixDTO after = bothList.get(i + 1);
					BigDecimal beforebig = before.getFinanceDistance().add(before.getMallDistance());
					BigDecimal afterbig = after.getFinanceDistance().add(after.getMallDistance());
					if (beforebig.compareTo(afterbig) > 0) {
						tempboth.setFinanceDistance(before.getFinanceDistance());
						tempboth.setMallDistance(before.getMallDistance());
					} else {
						tempboth.setFinanceDistance(after.getFinanceDistance());
						tempboth.setMallDistance(after.getMallDistance());
					}
				}

				BigDecimal bigone = new BigDecimal(0);
				BigDecimal bigtwo = new BigDecimal(0);
				if (tempfincance.compareTo(tempmall) > 0) {
					MemberBuyerGradeMatrixDTO mar = new MemberBuyerGradeMatrixDTO();
					mar.setMallDistance(tempmall);
					matrixList.add(mar);
					MemberBuyerGradeMatrixDTO mar2 = new MemberBuyerGradeMatrixDTO();
					mar2.setFinanceDistance(tempfincance);
					matrixList.add(mar2);
					bigone = tempmall;
					bigtwo = tempfincance;
				} else {
					MemberBuyerGradeMatrixDTO mar2 = new MemberBuyerGradeMatrixDTO();
					mar2.setFinanceDistance(tempfincance);
					matrixList.add(mar2);
					MemberBuyerGradeMatrixDTO mar = new MemberBuyerGradeMatrixDTO();
					mar.setMallDistance(tempmall);
					matrixList.add(mar);
					bigone = tempfincance;
					bigtwo = tempmall;
				}
				if ((tempboth.getFinanceDistance().add(tempboth.getMallDistance())).compareTo(bigtwo) < 0) {
					// 顶掉第二个
					MemberBuyerGradeMatrixDTO mar = new MemberBuyerGradeMatrixDTO();
					mar.setFinanceDistance(tempboth.getFinanceDistance());
					mar.setMallDistance(tempboth.getMallDistance());
					matrixList.set(1, mar);
				}
				// 设置升级路径
				queryBuyerGradeInfo.setPathList(matrixList);
			}
			rs.setResult(queryBuyerGradeInfo);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerGradeInfo】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员等级更改履历
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBuyerGradeChangeHistoryDTO>> queryBuyerGradeChangeHistory(BuyerHisPointDTO dto,
			Pager pager) {
		ExecuteResult<DataGrid<MemberBuyerGradeChangeHistoryDTO>> rs = new ExecuteResult<DataGrid<MemberBuyerGradeChangeHistoryDTO>>();
		DataGrid<MemberBuyerGradeChangeHistoryDTO> dg = new DataGrid<MemberBuyerGradeChangeHistoryDTO>();
		try {
			Pager p = new Pager();
			p.setPageOffset(0);
			p.setRows(Integer.MAX_VALUE);
			dg.setRows(memberBuyerDao.queryBuyerGradeChangeHistory(dto, pager));
			Long count = memberBuyerDao.queryBuyerGradeChangeHistory(dto, p) == null ? 0
					: new Long(memberBuyerDao.queryBuyerGradeChangeHistory(dto, p).size());
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->queryBuyerGradeChangeHistory=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员归属供应商信息
	 */
	@Override
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerSupplier(Long memberId) {
		ExecuteResult<MemberBuyerSupplierDTO> rs = new ExecuteResult<MemberBuyerSupplierDTO>();
		try {
			MemberBuyerSupplierDTO queryBuyerSupplier = memberBuyerDao.queryBuyerSupplier(memberId);
			if (queryBuyerSupplier == null) {
				rs.setResult(queryBuyerSupplier);
				rs.setResultMessage("success");
				return rs;
			}
			List<Long> brandIdsList = memberBuyerDao.queryBuyerBrand(memberId, queryBuyerSupplier.getSupplierId());
			// 最大的品牌品类
			List<MemberBuyerCategoryDTO> categoryList = new ArrayList<MemberBuyerCategoryDTO>();
			if (brandIdsList.size() > 0) {
				for (Long lg : brandIdsList) {
					if (lg.compareTo(0l) == 0) {
						continue;
					}
					ItemBrand brand = itemBrandExportService.queryItemBrandById(lg).getResult();
					if (brand == null) {
						continue;
					}
					MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
					d.setBrandId(brand.getBrandId());
					d.setBrandName(brand.getBrandName());
					List<Long> categoryIdList = memberBuyerDao.queryBuyerCategory(memberId,
							queryBuyerSupplier.getSupplierId(), lg);
					List<MemberBuyerCaListDTO> caList = new ArrayList<MemberBuyerCaListDTO>();
					if (categoryIdList.size() > 0) {
						for (Long l : categoryIdList) {
							if (l.compareTo(0l) == 0) {
								continue;
							}
							MemberBuyerCaListDTO dd = new MemberBuyerCaListDTO();
							ItemCategoryDTO result = itemCategoryService.getCategoryByCid(l).getResult();
							if (result != null) {
								dd.setCategoryId(result.getCategoryCid());
								dd.setCategoryName(result.getCategoryCName());
								caList.add(dd);
							}
						}
					}
					d.setCategoryList(caList);
					categoryList.add(d);
				}
			}
			queryBuyerSupplier.setCategoryList(categoryList);
			rs.setResult(queryBuyerSupplier);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerGradeInfo】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员包厢关系供应商基本信息
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> queryBuyerBusinessSupperlier(Pager page, Long memberId) {
		ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> rs = new ExecuteResult<DataGrid<MemberBuyerSupplierDTO>>();
		DataGrid<MemberBuyerSupplierDTO> dg = new DataGrid<MemberBuyerSupplierDTO>();
		try {
			List<MemberBuyerSupplierDTO> businessSupperlierList = memberBuyerDao.queryBuyerBusinessSupperlier(page,
					memberId);
			if (businessSupperlierList.size() > 0) {
				for (MemberBuyerSupplierDTO mdto : businessSupperlierList) {

					List<Long> brandIdsList = memberBuyerDao.queryBuyerBrand(memberId, mdto.getSupplierId());
					// 最大的品牌品类
					List<MemberBuyerCategoryDTO> categoryList = new ArrayList<MemberBuyerCategoryDTO>();
					if (brandIdsList.size() > 0) {
						for (Long lg : brandIdsList) {
							if (lg.compareTo(0l) == 0) {
								continue;
							}
							ItemBrand brand = itemBrandExportService.queryItemBrandById(lg).getResult();
							if (brand == null) {
								continue;
							}
							MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
							d.setBrandId(brand.getBrandId());
							d.setBrandName(brand.getBrandName());
							List<Long> categoryIdList = memberBuyerDao.queryBuyerCategory(memberId,
									mdto.getSupplierId(), lg);
							List<MemberBuyerCaListDTO> caList = new ArrayList<MemberBuyerCaListDTO>();
							if (categoryIdList.size() > 0) {
								for (Long l : categoryIdList) {
									if (l.compareTo(0l) == 0) {
										continue;
									}
									MemberBuyerCaListDTO dd = new MemberBuyerCaListDTO();
									ItemCategoryDTO result = itemCategoryService.getCategoryByCid(l).getResult();
									if (result != null) {
										dd.setCategoryId(result.getCategoryCid());
										dd.setCategoryName(result.getCategoryCName());
										caList.add(dd);
									}
								}
							}
							d.setCategoryList(caList);
							categoryList.add(d);
						}
					}

					/*
					 * List<Long> categoryIdList =
					 * memberBuyerDao.queryBuyerCategory(memberId,
					 * mdto.getSupplierId()); List<MemberBuyerCategoryDTO>
					 * categoryList = new ArrayList<MemberBuyerCategoryDTO>();
					 * if (categoryIdList.size() > 0) { for (Long l :
					 * categoryIdList) { ItemCategoryDTO result =
					 * itemCategoryService.getCategoryByCid(l).getResult();
					 * 
					 * MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
					 * d.setCategoryId(result.getCategoryCid());
					 * d.setCategoryName(result.getCategoryCName());
					 * categoryList.add(d); } }
					 */
					mdto.setCategoryList(categoryList);
				}
			}
			page.setPageOffset(0);
			page.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(memberBuyerDao.queryBuyerBusinessSupperlier(page, memberId).size()));
			dg.setRows(businessSupperlierList);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerBusinessSupperlier】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 申请修改会员基本信息
	 */
	@Override
	public ExecuteResult<Boolean> ApplyModifyBuyerBaseInfo(MemberBuyerPersonalInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
			rs.addErrorMessage("修改人id，修改人名称 不能为空");
			return rs;
		}

		/**
		 * 注册地址的更改 传入传出的都是地址编码 因为地址是可以更改的
		 */
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); Calendar
		 * calendar = Calendar.getInstance();
		 * calendar.set(Integer.parseInt(dto.getBirthdayYear()),
		 * Integer.parseInt(dto.getBirthdayMonth()) - 1,
		 * Integer.parseInt(dto.getBirthdayDay()));
		 * dto.setBirthday(sdf.format(calendar.getTime()));
		 */

		// 审批信息表 审批详细表插入数据
		// System.out.println("------------查询获取的memberid="+dto.getMemberId()+"-------------------");
		MemberBuyerPersonalInfoDTO querydto = memberBuyerDao.queryBuyerPersonalInfo(dto.getMemberId());
		// 判断审核流
		if (dto.getArtificialPersonName().equals(querydto.getArtificialPersonName())
				&& dto.getCompanyName().equals(querydto.getCompanyName())
				&& dto.getBuyerBusinessLicenseId().equals(querydto.getBuyerBusinessLicenseId())) {
			dto.setLocationAddr(
					memberBaseService.getAddressBaseByCode(dto.getLocationTown()) + dto.getLocationDetail());
			// 更新数据
			memberBuyerDao.updateBuyerCompanyInfo(dto);
			if (!StringUtils.isEmpty(dto.getBirthday())) {
				memberBuyerDao.updateBuyerPersonInfo(dto);
			}
			memberBuyerDao.updateBuyerLicenceInfo(dto);
			addModifyDownErp(dto);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
			return rs;
		}
		if (querydto != null) {
//        ****** add by jytang for 需求673,去掉汇致富相关校验 on 2017-05-10 start *******
//			if (!StringUtils.isEmpty(dto.getArtificialPersonName())) {
//				if (!dto.getArtificialPersonName().equalsIgnoreCase(querydto.getArtificialPersonName())) {
//					// 客户存在未还清的供应链金融贷款或汇致富理财余额
//					try {
//						String tokenResponse = HttpUtils.httpGet(StaticProperty.TOKEN_URL);
//						JSONObject tokenResponseJSON = JSONObject.parseObject(tokenResponse);
//						StringBuffer buffer = new StringBuffer();
//						buffer.append("?");
//						String memberCode = memberBaseOperationDAO.getMemberCodeById(dto.getMemberId());
//						if (StringUtils.isNotEmpty(memberCode)) {
//							buffer.append("memberCode=" + memberCode);
//							String msg = "";
//							if (tokenResponseJSON.getInteger("code") == 1) {
//								buffer.append("&token=" + tokenResponseJSON.getString("data"));
//								String urlParam = buffer.toString();
//								msg = HttpUtils
//										.httpGet(StaticProperty.MIDDLEWAREERP_URL + "hzf/hzfSelectBalance" + urlParam);
//								logger.info(msg);
//							}
//							JSONObject msgjson = JSONObject.parseObject(msg);
//							if (tokenResponseJSON.getInteger("code") == 1) {
//								JSONObject s = (JSONObject) msgjson.get("data");
//								int ss = s.getInteger("existed");
//								if (ss == 1) {
//									rs.addErrorMessage("客户存在未还清的供应链金融贷款或汇致富理财余额");
//									return rs;
//								}
//							}
//						}
//
//					} catch (Exception e) {
//						logger.error("客户存在未还清的供应链金融贷款或汇致富理财余额方法执行失败" + e);
//					}
//				}
//			}
//	        ****** add by jytang for 需求673,去掉汇致富相关校验 on 2017-05-10 end *******
			int count = 0;
			MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
			info.setRecordId(dto.getMemberId());
			info.setRecordType("15");
			info.setCreateId(dto.getModifyId());
			info.setCreateName(dto.getModifyName());
			int verifyId = memberBuyerDao.addVerifyInfo(info);
			verifyId = info.getId().intValue();

			MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberBaseInfoById(dto.getMemberId(),
					GlobalConstant.IS_BUYER);
			Long companyId = memberBase.getCompanyId();
			if (!StringUtils.isEmpty(dto.getArtificialPersonName())) {
				if (!dto.getArtificialPersonName().equalsIgnoreCase(querydto.getArtificialPersonName())) {
					// 法人名称
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "法人名称",
							"member_company_info", "artificial_person_name", querydto.getArtificialPersonName(),
							dto.getArtificialPersonName(), "15", companyId, querydto.getArtificialPersonName(),
							dto.getArtificialPersonName()))
						throw new RuntimeException();
				}
			}
			if (!StringUtils.isEmpty(dto.getCompanyName())) {
				if (!dto.getCompanyName().equalsIgnoreCase(querydto.getCompanyName())) {
					// 公司名称
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "公司名称",
							"member_company_info", "company_name", querydto.getCompanyName(), dto.getCompanyName(),
							"15", companyId, querydto.getCompanyName(), dto.getCompanyName()))
						throw new RuntimeException();
				}
			}
			/*
			 * if(!StringUtils.isEmpty(dto.getBirthdayYear())){
			 * if(!dto.getBirthdayYear().equalsIgnoreCase(querydto.
			 * getBirthdayYear())){ //出生日期-年 count++;
			 * if(!addVerifyMethod(verifyId,dto.getModifyId(),dto.getModifyName(
			 * ),"出生日期-年","member_person_info","birthday_year",
			 * querydto.getBirthdayYear(),dto.getBirthdayYear(),"15")) throw new
			 * RuntimeException(); } }
			 * if(!StringUtils.isEmpty(dto.getBirthdayMonth())){
			 * if(!dto.getBirthdayMonth().equalsIgnoreCase(querydto.
			 * getBirthdayMonth())){ //出生日期-月 count++;
			 * if(!addVerifyMethod(verifyId,dto.getModifyId(),dto.getModifyName(
			 * ),"出生日期-月","member_person_info","birthday_month",
			 * querydto.getBirthdayMonth(),dto.getBirthdayMonth(),"15")) throw
			 * new RuntimeException(); } }
			 * if(!StringUtils.isEmpty(dto.getBirthdayDay())){
			 * if(!dto.getBirthdayDay().equalsIgnoreCase(querydto.getBirthdayDay
			 * ())){ //出生日期-日 count++;
			 * if(!addVerifyMethod(verifyId,dto.getModifyId(),dto.getModifyName(
			 * ),"出生日期-日","member_person_info","birthday_day",
			 * querydto.getBirthdayDay(),dto.getBirthdayDay(),"15")) throw new
			 * RuntimeException(); } }
			 */
			boolean locationModify = false;
			if (!StringUtils.isEmpty(dto.getBirthday())) {
				if (!dto.getBirthday().equalsIgnoreCase(querydto.getBirthday())) {
					// 出生日期-日
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "出生日期", "member_person_info",
							"birthday", querydto.getBirthday(), dto.getBirthday(), "15", dto.getMemberId(),
							querydto.getBirthday(), dto.getBirthday()))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getLocationProvince())) {
				if (!dto.getLocationProvince().equalsIgnoreCase(querydto.getLocationProvince())) {
					// 注册地址-省

					count++;
					String afterLocation = "";
					String beforLocation = "";
					if (null != querydto.getLocationTown()) {
						beforLocation = memberBaseService.getAddressBaseByCode(querydto.getLocationProvince());
					}
					if (null != dto.getLocationTown()) {
						afterLocation = memberBaseService.getAddressBaseByCode(dto.getLocationProvince());
					}
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址-省",
							"member_company_info", "location_province", querydto.getLocationProvince(),
							dto.getLocationProvince(), "15", companyId, beforLocation, afterLocation))
						throw new RuntimeException();
				}
			}
			if (!StringUtils.isEmpty(dto.getLocationCity())) {
				if (!dto.getLocationCity().equalsIgnoreCase(querydto.getLocationCity())) {
					// 注册地址-市
					count++;
					String afterLocation = "";
					String beforLocation = "";
					if (null != querydto.getLocationTown()) {
						beforLocation = memberBaseService.getAddressBaseByCode(querydto.getLocationCity());
					}
					if (null != dto.getLocationTown()) {
						afterLocation = memberBaseService.getAddressBaseByCode(dto.getLocationCity());
					}
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址-市",
							"member_company_info", "location_city", querydto.getLocationCity(), dto.getLocationCity(),
							"15", companyId, beforLocation, afterLocation))
						throw new RuntimeException();
				}
			}
			if (!StringUtils.isEmpty(dto.getLocationCounty())) {
				if (!dto.getLocationCounty().equalsIgnoreCase(querydto.getLocationCounty())) {
					// 注册地址-区
					count++;
					String afterLocation = "";
					String beforLocation = "";
					if (null != querydto.getLocationTown()) {
						beforLocation = memberBaseService.getAddressBaseByCode(querydto.getLocationTown());
					}
					if (null != dto.getLocationTown()) {
						afterLocation = memberBaseService.getAddressBaseByCode(dto.getLocationCounty());
					}
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址-区",
							"member_company_info", "location_county", querydto.getLocationCounty(),
							dto.getLocationCounty(), "15", companyId, beforLocation, afterLocation))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getLocationTown())) {
				if (!dto.getLocationTown().equalsIgnoreCase(querydto.getLocationTown())) {
					// 注册地址-镇
					locationModify = true;
					count++;
					String afterLocation = "";
					String beforLocation = "";
					if (null != querydto.getLocationTown()) {
						beforLocation = memberBaseService.getAddressBaseByCode(querydto.getLocationTown());
					}
					if (null != dto.getLocationTown()) {
						afterLocation = memberBaseService.getAddressBaseByCode(dto.getLocationTown());
					}
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址-镇",
							"member_company_info", "location_town", querydto.getLocationTown(), dto.getLocationTown(),
							"15", companyId, beforLocation, afterLocation))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getLocationDetail())) {
				if (!dto.getLocationDetail().equalsIgnoreCase(querydto.getLocationDetail())) {
					// 注册地址-详细
					count++;
					locationModify = true;
					String afterLocation = dto.getLocationDetail();
					String beforLocation = querydto.getLocationDetail();

					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址-详细",
							"member_company_info", "location_detail", beforLocation, afterLocation, "15", companyId,
							beforLocation, afterLocation))
						throw new RuntimeException();
				}
			}

			if (locationModify) {
				// 修改地址字段
				count++;
				String afterLocation = "";
				String beforLocation = "";
				if (null != querydto.getLocationTown() && null != querydto.getLocationDetail()) {
					beforLocation = memberBaseService.getAddressBaseByCode(querydto.getLocationTown())
							+ querydto.getLocationDetail();
				}
				if (null != dto.getLocationTown() && null != dto.getLocationDetail()) {
					afterLocation = memberBaseService.getAddressBaseByCode(dto.getLocationTown())
							+ dto.getLocationDetail();
				}
				if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "注册地址", "member_company_info",
						"location_addr", beforLocation, afterLocation, "15", companyId, beforLocation, afterLocation))
					throw new RuntimeException();
			}

			if (!StringUtils.isEmpty(dto.getBuyerBusinessLicenseId())) {
				if (!dto.getBuyerBusinessLicenseId().equalsIgnoreCase(querydto.getBuyerBusinessLicenseId())) {
					// 营业执照号
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "营业执照号",
							"member_licence_info", "buyer_business_license_id", querydto.getBuyerBusinessLicenseId(),
							dto.getBuyerBusinessLicenseId(), "15", dto.getMemberId(),
							querydto.getBuyerBusinessLicenseId(), dto.getBuyerBusinessLicenseId()))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getBuyerGuaranteeLicensePicSrc())) {
				if (!dto.getBuyerGuaranteeLicensePicSrc().equalsIgnoreCase(querydto.getBuyerGuaranteeLicensePicSrc())) {
					// 担保证明
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "担保证明",
							"member_licence_info", "buyer_guarantee_license_pic_src",
							querydto.getBuyerGuaranteeLicensePicSrc(), dto.getBuyerGuaranteeLicensePicSrc(), "15",
							dto.getMemberId(), querydto.getBuyerGuaranteeLicensePicSrc(),
							dto.getBuyerGuaranteeLicensePicSrc()))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getBuyerBusinessLicensePicSrc())) {
				if (!dto.getBuyerBusinessLicensePicSrc().equalsIgnoreCase(querydto.getBuyerBusinessLicensePicSrc())) {
					// 营业执照附件
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "营业执照附件",
							"member_licence_info", "buyer_business_license_pic_src",
							querydto.getBuyerBusinessLicensePicSrc(), dto.getBuyerBusinessLicensePicSrc(), "15",
							dto.getMemberId(), querydto.getBuyerBusinessLicensePicSrc(),
							dto.getBuyerBusinessLicensePicSrc()))
						throw new RuntimeException();
				}
			}

			if (!StringUtils.isEmpty(dto.getBusinessLicenseCertificatePicSrc())) {
				if (!dto.getBusinessLicenseCertificatePicSrc()
						.equalsIgnoreCase(querydto.getBusinessLicenseCertificatePicSrc())) {
					// 营业执照变更证明
					count++;
					if (!addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "营业执照变更证明",
							"member_licence_info", "business_license_certificate_pic_src",
							querydto.getBusinessLicenseCertificatePicSrc(), dto.getBusinessLicenseCertificatePicSrc(),
							"15", dto.getMemberId(), querydto.getBusinessLicenseCertificatePicSrc(),
							dto.getBusinessLicenseCertificatePicSrc()))
						throw new RuntimeException();
				}
			}
			if (count == 0) {
				memberBuyerDao.deleteVerifyInfoById(verifyId);
			} else {
				// 判断memberStatusinfo是否已经存在，存在就修改考核状态
				VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(dto.getMemberId(), "15", null);
				if (v != null) {
					MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
					memberBaseInfoRegisterDTO.setMemberId(dto.getMemberId());
					memberBaseInfoRegisterDTO.setModifyId(dto.getModifyId());
					memberBaseInfoRegisterDTO.setModifyName(dto.getModifyName());
					memberBaseInfoRegisterDTO.setVerifyInfoId(new Long(verifyId));
					memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "15");
				} else {
					memberBaseInfoDao.insertMemberStatusInfoRegister(dto.getMemberId(), info.getCreateId(),
							info.getCreateName(), "15", new Long(verifyId));
				}
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 添加审批信息
	 * 
	 * @param dto
	 * @param cont
	 * @param table
	 * @param field
	 * @param before
	 * @param after
	 * @return
	 */
	public boolean addVerifyMethod(int verifyId, Long modifyId, String modifyName, String cont, String table,
			String field, String before, String after, String recordType, Long recordId, String beforChangeDesc,
			String afterChangeDesc) {
		boolean re = false;
		try {
			MemberBuyerVerifyDetailInfoDTO detail = new MemberBuyerVerifyDetailInfoDTO();
			detail.setVerifyId(new Long(verifyId));
			detail.setRecordType("1");
			detail.setRecordId(recordId);
			detail.setContentName(cont);
			detail.setChangeTableId(table);
			detail.setChangeFieldId(field);
			detail.setBeforeChange(before);
			detail.setAfterChange(after);
			detail.setBeforeChangeDesc(beforChangeDesc);
			detail.setAfterChangeDesc(afterChangeDesc);
			detail.setModifyType(recordType);
			detail.setOperatorId(modifyId);
			detail.setOperatorName(modifyName);
			int detailre = memberBuyerDao.addVerifyDetailInfo(detail);
			if (verifyId != 0 & detailre != 0) {
				re = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 申请添加会员供应商经营关系
	 */
	@Override
	public ExecuteResult<BoxRelationImportDTO> ApplyAddBusinessRelationship(
			List<ApplyBusiRelationDTO> businessRelatVerifyDtoList) {
		ExecuteResult<BoxRelationImportDTO> rs = new ExecuteResult<BoxRelationImportDTO>();
		int successCount = 0;
		int failCount = 0;
		List<ApplyBusiRelationDTO> sList = new ArrayList<ApplyBusiRelationDTO>();
		List<ApplyBusiRelationDTO> fList = new ArrayList<ApplyBusiRelationDTO>();
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
						Long memberId = applyBusiRelationDTO.getMemberId();
						Long sellerId = applyBusiRelationDTO.getSellerId();
						ApplyBusiRelationDTO queryBox = applyRelationshipDao.queryBoxRelationInfo(memberId, sellerId);
						if (queryBox == null) {
							applyBusiRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
							applyRelationshipDao.insertBoxRelationInfo(applyBusiRelationDTO);
							//新建包厢关系 重置该会员店提醒信息
							updateSignRemindFlagToIsNeed(applyBusiRelationDTO);
						}
						// List<ApplyBusiRelationDTO> busiRelationList =
						// boxRelationshipDAO
						// .selectBusiRelationList(memberId, sellerId);
						// if (busiRelationList != null &&
						// busiRelationList.size() > 0) {
						// 增加经营关系
						applyBusiRelationDTO.setErpStatus("");
						applyBusiRelationDTO.setAuditStatus("0");// "0",
																	// "待审核"
						if (applyBusiRelationDTO.getCategoryBrand() != null) {
							for (CategoryBrandDTO categoryBrandDTO : applyBusiRelationDTO.getCategoryBrand()) {
								if (categoryBrandDTO.getCategoryId() != null && categoryBrandDTO.getBrandId() != null) {
									applyBusiRelationDTO.setCategoryId(categoryBrandDTO.getCategoryId());
									applyBusiRelationDTO.setBrandId(categoryBrandDTO.getBrandId());

									MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
									try {
										memberBusinessRelationDTO
												.setBuyerId(applyBusiRelationDTO.getMemberId().toString());
										memberBusinessRelationDTO
												.setSellerId(applyBusiRelationDTO.getSellerId().toString());
										memberBusinessRelationDTO
												.setBrandId(applyBusiRelationDTO.getBrandId().longValue());
										memberBusinessRelationDTO
												.setCategoryId(applyBusiRelationDTO.getCategoryId().longValue());
										MemberBusinessRelationDTO count1 = memberBusinessRelationDAO
												.queryMemberBusinessRelationDetail(memberBusinessRelationDTO);

										if (count1 != null) {
											memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
											memberBusinessRelationDTO
													.setAuditStatus(AuditStatusEnum.PENDING_AUDIT.getCode());
											memberBusinessRelationDAO
													.updateMemberBusinessRelationInfo(memberBusinessRelationDTO);
										} else {
											memberVerifySaveDAO.insertBusinessRelationInfo(applyBusiRelationDTO);
											sList.add(queryBox);
											successCount++;
										}

									} catch (Exception e) {
										logger.warn("已有相同数据:" + memberBusinessRelationDTO);
										rs.setResultMessage("已有相同数据");
									}

								}
							}
						}

						// } else {
						// fList.add(queryBox);
						// failCount++;
						// }
					}

				}
				box.setSuccCount(successCount);
				box.setSuccInfoList(sList);
				box.setSuccCount(failCount);
				box.setFailInfoList(fList);
				rs.setResult(box);
				rs.setResultMessage("success");
				rs.setResultMessage("会员经营关系成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBuyerServiceImpl----->ApplyAddBusinessRelationship=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 根据品牌品类区域查询供应商列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> queryBuyerSupplierListByBCID(String companyName,
			List<String> categoryId, List<String> brandId, List<String> locationProvince, Pager page) {
		ExecuteResult<DataGrid<MemberBuyerSupplierDTO>> rs = new ExecuteResult<DataGrid<MemberBuyerSupplierDTO>>();
		DataGrid<MemberBuyerSupplierDTO> dg = new DataGrid<MemberBuyerSupplierDTO>();
		try {
			List<MemberBuyerSupplierDTO> businessSupperlierList = memberBuyerDao
					.queryBuyerSupplierListByBCID(companyName, categoryId, brandId, locationProvince, page);
			Long count = memberBuyerDao.queryBuyerSupplierListByBCIDCount(companyName, categoryId, brandId,
					locationProvince);
			dg.setTotal(count);
			dg.setRows(businessSupperlierList);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerBusinessSupperlier】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员金融法人信息
	 */
	@Override
	public ExecuteResult<MemberBuyerFinanceDTO> queryBuyerFinance(Long memberId) {
		ExecuteResult<MemberBuyerFinanceDTO> rs = new ExecuteResult<MemberBuyerFinanceDTO>();
		try {
			MemberBuyerFinanceDTO queryBuyerFinance = memberBuyerDao.queryBuyerFinance(memberId);
			// 查询审批状态
			/*
			 * VerifyInfoDTO verifyInfoDTO =
			 * memberBaseInfoDao.selectMemberStatusInfo(memberId, "16", null);
			 * if (null != verifyInfoDTO) { //
			 * queryBuyerFinance.setStatus(verifyInfoDTO.getVerifyStatus());
			 * VerifyResultDTO verifyResultDTO = memberBaseOperationDAO
			 * .selectVerifyResultById(verifyInfoDTO.getVerifierId()); if
			 * (verifyResultDTO != null) {
			 * queryBuyerFinance.setStatus(verifyResultDTO.getVerifyStatus());
			 * queryBuyerFinance.setRemark(verifyResultDTO.getRemark()); } }
			 */
			/*
			 * queryBuyerFinance.setStatus(memberBuyerDao.getMemberVerifyStatus(
			 * memberId, "16"));
			 * queryBuyerFinance.setRemark(memberBuyerDao.getMemberVerifyRemark(
			 * memberId, "16"));
			 */
			String verifyStatus = memberBuyerDao.getMemberVerifyStatus(memberId, "16");

			if (null != verifyStatus && GlobalConstant.VERIFY_STATUS_WAIT.equals(verifyStatus)) {
				List<MemberBuyerVerifyDetailInfoDTO> dtlist = memberBuyerDao.queryVerifyInfo(memberId, "16");
				if (dtlist != null && dtlist.size() > 0) {
					for (MemberBuyerVerifyDetailInfoDTO dt : dtlist) {
						if ("contact_name".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactName(dt.getAfterChange());
						}
						if ("contact_mobile".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactMobile(dt.getAfterChange());
						}
						if ("contact_idcard".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactIdcard(dt.getAfterChange());
						}
						if ("contact_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactIdcardPicSrc(dt.getAfterChange());
						}
						if ("contact_idcard_pic_back_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactIdcardPicBackSrc(dt.getAfterChange());
						}
						if ("contact_person_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setContactPersonIdcardPicSrc(dt.getAfterChange());
						}
						if ("artificial_person_name".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setArtificialPersonName(dt.getAfterChange());
						}
						if ("artificial_person_idcard".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setArtificialPersonIdcard(dt.getAfterChange());
						}
						if ("artificial_person_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setArtificialPersonPicSrc(dt.getAfterChange());
						}
						if ("artificial_person_pic_back_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setArtificialPersonPicBackSrc(dt.getAfterChange());
						}
						if ("artificial_person_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							queryBuyerFinance.setArtificialPersonIdcardPicSrc(dt.getAfterChange());
						}
					}
				}
			}

			rs.setResult(queryBuyerFinance);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerFinance】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员金融备用联系人列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBuyerFinanceDTO>> queryBuyerBackupContactList(Pager page,
			MemberBuyerFinanceDTO dto) {
		ExecuteResult<DataGrid<MemberBuyerFinanceDTO>> rs = new ExecuteResult<DataGrid<MemberBuyerFinanceDTO>>();
		DataGrid<MemberBuyerFinanceDTO> dg = new DataGrid<MemberBuyerFinanceDTO>();
		try {
			List<MemberBuyerFinanceDTO> queryBuyerBackupContactList = memberBuyerDao.queryBuyerBackupContactList(dto,
					page);
			if (queryBuyerBackupContactList != null && queryBuyerBackupContactList.size() > 0) {
				for (MemberBuyerFinanceDTO mt : queryBuyerBackupContactList) {
					// smt.setStatus(memberBuyerDao.getMemberVerifyStatus(mt.getContactId(),
					// "28"));
					String verifyStatus = memberBuyerDao.getMemberVerifyStatus(mt.getContactId(), "28");
					if (null != verifyStatus && GlobalConstant.VERIFY_STATUS_WAIT.equals(verifyStatus)) {
						List<MemberBuyerVerifyDetailInfoDTO> dtlist = memberBuyerDao.queryVerifyInfo(mt.getMemberId(),
								"28");
						if (dtlist != null && dtlist.size() > 0) {
							List<MemberBuyerVerifyDetailInfoDTO> dtlistNew = removeRepeatVerify(dtlist);
							for (MemberBuyerVerifyDetailInfoDTO dt : dtlistNew) {
								if ("contact_name".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactName(dt.getAfterChange());
								}
								if ("contact_mobile".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactMobile(dt.getAfterChange());
								}
								if ("contact_idcard".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactIdcard(dt.getAfterChange());
								}
								if ("contact_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactIdcardPicSrc(dt.getAfterChange());
								}
								if ("contact_idcard_pic_back_src".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactIdcardPicBackSrc(dt.getAfterChange());
								}
								if ("contact_person_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
									mt.setContactPersonIdcardPicSrc(dt.getAfterChange());
								}

							}
						}
					}
					mt.setRemark(memberBuyerDao.getMemberVerifyRemark(mt.getContactId(), "28"));
				}
			}
			page.setPageOffset(0);
			page.setRows(Integer.MAX_VALUE);
			dg.setTotal(new Long(memberBuyerDao.queryBuyerBackupContactList(dto, page).size()));
			dg.setRows(queryBuyerBackupContactList);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerBackupContactList】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 添加金融备用联系人
	 */
	@Override
	public ExecuteResult<Boolean> addBuyerBackupContact(MemberBuyerFinanceDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getCreateId() == null && StringUtils.isEmpty(dto.getCreateName())) {
				rs.addErrorMessage("创建人id，创建人名称 不能为空");
				throw new RuntimeException();
			}
			dto.setStatus("1");
			int contactid = memberBuyerDao.addBuyerBackupContact(dto);
			Long contactId = dto.getContactId().longValue();
			MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
			info.setRecordId(contactId);
			info.setRecordType("28");
			info.setCreateId(dto.getModifyId());
			info.setCreateName(dto.getModifyName());
			int verifyId = memberBuyerDao.addVerifyInfo(info);
			verifyId = info.getId().intValue();
			int count = 0;
			// 审批信息表 审批详细表插入数据
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人姓名", "member_backup_contact_info",
					"contact_name", "", dto.getContactName(), "28", contactId, "", dto.getContactName());
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人手机号码", "member_backup_contact_info",
					"contact_mobile", "", dto.getContactMobile(), "28", contactId, "", dto.getContactMobile());
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证号", "member_backup_contact_info",
					"contact_idcard", "", dto.getContactIdcard(), "28", contactId, "", dto.getContactIdcard());
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证正面电子版图片地址",
					"member_backup_contact_info", "contact_idcard_pic_src", "", dto.getContactIdcardPicSrc(), "28",
					contactId, "", dto.getContactIdcardPicSrc());
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证背面电子版图片地址",
					"member_backup_contact_info", "contact_idcard_pic_back_src", "", dto.getContactIdcardPicBackSrc(),
					"28", contactId, "", dto.getContactIdcardPicBackSrc());
			addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人手持身份证电子版图片地址",
					"member_backup_contact_info", "contact_person_idcard_pic_src", "",
					dto.getContactPersonIdcardPicSrc(), "28", contactId, "", dto.getContactPersonIdcardPicSrc());
			// 判断memberStatusinfo是否已经存在，存在就修改考核状态
			VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(dto.getMemberId(), "28", null);
			if (v != null) {
				MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
				memberBaseInfoRegisterDTO.setMemberId(dto.getMemberId());
				memberBaseInfoRegisterDTO.setModifyId(dto.getModifyId());
				memberBaseInfoRegisterDTO.setModifyName(dto.getModifyName());
				memberBaseInfoRegisterDTO.setVerifyInfoId(new Long(verifyId));
				memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "28");
			} else {
				memberBaseInfoDao.insertMemberStatusInfoRegister(dto.getMemberId(), info.getCreateId(),
						info.getCreateName(), "28", new Long(verifyId));
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 修改金融备用联系人
	 */
	@Override
	public ExecuteResult<Boolean> updateBuyerBackupContact(MemberBuyerFinanceDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
				rs.addErrorMessage("修改人id，修改人名称 不能为空");
				throw new RuntimeException();
			}
			if ("4".equalsIgnoreCase(dto.getStatus()) || "9".equalsIgnoreCase(dto.getStatus())) {
				memberBuyerDao.updateBuyerBackupContact(dto);
			} else {
				MemberBuyerFinanceDTO querydto = memberBuyerDao.queryBuyerBackupContactById(dto.getContactId());
				MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
				info.setRecordId(dto.getContactId());
				info.setRecordType("28");
				info.setCreateId(dto.getModifyId());
				info.setCreateName(dto.getModifyName());
				int verifyId = memberBuyerDao.addVerifyInfo(info);
				verifyId = info.getId().intValue();
				int count = 0;
				// 审批信息表 审批详细表插入数据
				if (!querydto.getContactName().equalsIgnoreCase(dto.getContactName())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人姓名",
							"member_backup_contact_info", "contact_name", querydto.getContactName(),
							dto.getContactName(), "28", dto.getContactId(), querydto.getContactName(),
							dto.getContactName());
					count++;
				}
				if (!querydto.getContactMobile().equalsIgnoreCase(dto.getContactMobile())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人手机号码",
							"member_backup_contact_info", "contact_mobile", querydto.getContactMobile(),
							dto.getContactMobile(), "28", dto.getContactId(), querydto.getContactMobile(),
							dto.getContactMobile());
					count++;
				}
				if (!querydto.getContactIdcard().equalsIgnoreCase(dto.getContactIdcard())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证号",
							"member_backup_contact_info", "contact_idcard", querydto.getContactIdcard(),
							dto.getContactIdcard(), "28", dto.getContactId(), querydto.getContactIdcard(),
							dto.getContactIdcard());
					count++;
				}
				if (!querydto.getContactIdcardPicSrc().equalsIgnoreCase(dto.getContactIdcardPicSrc())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证正面电子版图片地址",
							"member_backup_contact_info", "contact_idcard_pic_src", querydto.getContactIdcardPicSrc(),
							dto.getContactIdcardPicSrc(), "28", dto.getContactId(), querydto.getContactIdcardPicSrc(),
							dto.getContactIdcardPicSrc());
					count++;
				}
				if (!querydto.getContactIdcardPicBackSrc().equalsIgnoreCase(dto.getContactIdcardPicBackSrc())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人身份证背面电子版图片地址",
							"member_backup_contact_info", "contact_idcard_pic_back_src",
							querydto.getContactIdcardPicBackSrc(), dto.getContactIdcardPicBackSrc(), "28",
							dto.getContactId(), querydto.getContactIdcardPicBackSrc(),
							dto.getContactIdcardPicBackSrc());
					count++;
				}
				if (!querydto.getContactPersonIdcardPicSrc().equalsIgnoreCase(dto.getContactPersonIdcardPicSrc())) {
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "联系人手持身份证电子版图片地址",
							"member_backup_contact_info", "contact_person_idcard_pic_src",
							querydto.getContactPersonIdcardPicSrc(), dto.getContactPersonIdcardPicSrc(), "28",
							dto.getContactId(), querydto.getContactPersonIdcardPicSrc(),
							dto.getContactPersonIdcardPicSrc());
					count++;
				}
				if (count == 0) {
					memberBuyerDao.deleteVerifyInfoById(verifyId);
				} else {
					MemberBuyerFinanceDTO mdt = new MemberBuyerFinanceDTO();
					mdt.setContactId(dto.getContactId());
					mdt.setStatus("1");
					memberBuyerDao.updateBuyerBackupContact(mdt);
					// 判断memberStatusinfo是否已经存在，存在就修改考核状态
					VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(dto.getMemberId(), "28", null);
					if (v != null) {
						MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
						memberBaseInfoRegisterDTO.setMemberId(dto.getMemberId());
						memberBaseInfoRegisterDTO.setModifyId(dto.getModifyId());
						memberBaseInfoRegisterDTO.setModifyName(dto.getModifyName());
						memberBaseInfoRegisterDTO.setVerifyInfoId(new Long(verifyId));
						memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "28");
					} else {
						memberBaseInfoDao.insertMemberStatusInfoRegister(dto.getMemberId(), info.getCreateId(),
								info.getCreateName(), "28", new Long(verifyId));
					}
				}
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 保存会员金融法人信息为待审核
	 */
	@Override
	public ExecuteResult<Boolean> updateBuyerArticBeVerifyed(MemberBuyerFinanceDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
			rs.addErrorMessage("修改人id，修改人名称 不能为空");
			throw new RuntimeException();
		}
		int count = 0;
		MemberBuyerFinanceDTO querydto = memberBuyerDao.queryBuyerFinance(dto.getMemberId());
		if (querydto != null) {
			MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
			info.setRecordId(dto.getMemberId());
			info.setRecordType("16");
			info.setCreateId(dto.getModifyId());
			info.setCreateName(dto.getModifyName());
			int verifyId = memberBuyerDao.addVerifyInfo(info);
			verifyId = info.getId().intValue();
			// 审批信息表 审批详细表插入数据
			// addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(),
			// "法人名称", "member_company_info",
			// "artificial_person_name", querydto.getArtificialPersonName(),
			// dto.getArtificialPersonName(), "16",
			// dto.getMemberId());
			// count++;
			MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberBaseInfoById(dto.getMemberId(),
					GlobalConstant.IS_BUYER);
			Long companyId = memberBase.getCompanyId();
			if (!querydto.getArtificialPersonIdcard().equalsIgnoreCase(dto.getArtificialPersonIdcard())) {
				addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "法人身份证号", "member_company_info",
						"artificial_person_idcard", querydto.getArtificialPersonIdcard(),
						dto.getArtificialPersonIdcard(), "16", companyId, querydto.getArtificialPersonIdcard(),
						dto.getArtificialPersonIdcard());
				count++;

				// 不显示修改身份证提示
				verifyInfoDAO.updateShowIdMsg(dto.getMemberId(), 0);
			}
			if (!querydto.getArtificialPersonPicSrc().equalsIgnoreCase(dto.getArtificialPersonPicSrc())) {
				addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "法人身份证（正面）", "member_company_info",
						"artificial_person_pic_src", querydto.getArtificialPersonPicSrc(),
						dto.getArtificialPersonPicSrc(), "16", companyId, querydto.getArtificialPersonPicSrc(),
						dto.getArtificialPersonPicSrc());
				count++;
			}
			if (!querydto.getArtificialPersonPicBackSrc().equalsIgnoreCase(dto.getArtificialPersonPicBackSrc())) {
				addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "法人身份证（反面）", "member_company_info",
						"artificial_person_pic_back_src", querydto.getArtificialPersonPicBackSrc(),
						dto.getArtificialPersonPicBackSrc(), "16", companyId, querydto.getArtificialPersonPicBackSrc(),
						dto.getArtificialPersonPicBackSrc());
				count++;
			}
			if (!querydto.getArtificialPersonIdcardPicSrc().equalsIgnoreCase(dto.getArtificialPersonIdcardPicSrc())) {
				addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "法人手持身份证", "member_company_info",
						"artificial_person_idcard_pic_src", querydto.getArtificialPersonIdcardPicSrc(),
						dto.getArtificialPersonIdcardPicSrc(), "16", companyId,
						querydto.getArtificialPersonIdcardPicSrc(), dto.getArtificialPersonIdcardPicSrc());
				count++;
			}
			if (count == 0) {
				memberBuyerDao.deleteVerifyInfoById(verifyId);
			} else {
				// 判断memberStatusinfo是否已经存在，存在就修改考核状态
				VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(dto.getMemberId(), "16", null);
				if (v != null) {
					MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
					memberBaseInfoRegisterDTO.setMemberId(dto.getMemberId());
					memberBaseInfoRegisterDTO.setModifyId(dto.getModifyId());
					memberBaseInfoRegisterDTO.setModifyName(dto.getModifyName());
					memberBaseInfoRegisterDTO.setVerifyInfoId(new Long(verifyId));
					memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "16");
				} else {
					memberBaseInfoDao.insertMemberStatusInfoRegister(dto.getMemberId(), info.getCreateId(),
							info.getCreateName(), "16", new Long(verifyId));
				}
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员手机号码验证信息
	 */
	@Override
	public ExecuteResult<MemberBuyerAuthenticationDTO> queryBuyerTELAuthenticate(Long memberId) {
		ExecuteResult<MemberBuyerAuthenticationDTO> rs = new ExecuteResult<MemberBuyerAuthenticationDTO>();
		try {
			MemberBuyerAuthenticationDTO telAuthenticate = memberBuyerDao.queryBuyerTELAuthenticate(memberId);
			rs.setResult(telAuthenticate);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerTELAuthenticate】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 保存会员手机号验证标记
	 */
	@Override
	public ExecuteResult<Boolean> updateBuyerTELAuthenticate(MemberBuyerAuthenticationDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
				rs.addErrorMessage("修改人id，修改人名称 不能为空");
				throw new RuntimeException();
			}
			if (StringUtils.isEmpty(dto.getIsPhoneAuthenticated())) {
				rs.addErrorMessage("手机号认证状态不能为空");
				throw new RuntimeException();
			}
			memberBuyerDao.updateBuyerTELAuthStatus(dto);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 修改会员手机号
	 */
	@Override
	public ExecuteResult<Boolean> updateBuyerTELAuthenNum(MemberBuyerAuthenticationDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getModifyId() == null && StringUtils.isEmpty(dto.getModifyName())) {
				rs.addErrorMessage("修改人id，修改人名称 不能为空");
				throw new RuntimeException();
			}
			if (StringUtils.isEmpty(dto.getIsPhoneAuthenticated())
					&& StringUtils.isEmpty(dto.getArtificialPersonMobile())) {
				rs.addErrorMessage("手机号认证状态,手机号不能为空");
				throw new RuntimeException();
			}
			// 手机号未认证
			if ("0".equalsIgnoreCase(dto.getIsPhoneAuthenticated())) {
				memberBuyerDao.updateBuyerTELAuthNumber(dto);
			}
			// 手机号以认证
			if ("1".equalsIgnoreCase(dto.getIsPhoneAuthenticated())) {
				MemberBuyerAuthenticationDTO dto2 = memberBuyerDao.queryBuyerTELAuthenticate(dto.getMemberId());
				if (dto2 != null) {
					MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
					info.setRecordId(dto.getMemberId());
					info.setRecordType("3");
					info.setCreateId(dto.getModifyId());
					info.setCreateName(dto.getModifyName());
					int verifyId = memberBuyerDao.addVerifyInfo(info);
					verifyId = info.getId().intValue();
					addVerifyMethod(verifyId, dto.getModifyId(), dto.getModifyName(), "会员手机号更改审核",
							"member_company_info", "artificial_person_mobile", dto2.getArtificialPersonMobile(),
							dto.getArtificialPersonMobile(), "3", dto.getMemberId(), dto2.getArtificialPersonMobile(),
							dto.getArtificialPersonMobile());
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("success");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.setResultMessage("error");
				}
			}
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员实名制认证信息
	 */
	@Override
	public ExecuteResult<MemberBuyerAuthenticationDTO> queryBuyerRealNameAuthenticate(Long memberId) {
		ExecuteResult<MemberBuyerAuthenticationDTO> rs = new ExecuteResult<MemberBuyerAuthenticationDTO>();
		try {
			MemberBuyerAuthenticationDTO realNameAuthenticate = memberBuyerDao.queryBuyerRealNameAuthenticate(memberId);
			rs.setResult(realNameAuthenticate);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerTELAuthenticate】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 根据用户loginId查询会员ID
	 */
	@Override
	public ExecuteResult<Long> getMemberIdByLoginId(String loginId) {
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		try {
			Long memberIdByLoginId = memberBuyerDao.getMemberIdByLoginId(loginId);
			rs.setResult(memberIdByLoginId);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  getMemberIdByLoginId】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberBuyerService#
	 * queryBuyerBusinessSupperlierById(java.lang.Long)
	 */
	@Override
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerBusinessSupperlierById(Long memberId, Long supplierId) {
		ExecuteResult<MemberBuyerSupplierDTO> rs = new ExecuteResult<MemberBuyerSupplierDTO>();
		try {
			MemberBuyerSupplierDTO memberBuyerSupplierDTO = new MemberBuyerSupplierDTO();
			memberBuyerSupplierDTO.setSupplierId(supplierId);
			List<Long> brandIdsList = memberBuyerDao.queryBuyerBrand(memberId, memberBuyerSupplierDTO.getSupplierId());
			// 最大的品牌品类
			List<MemberBuyerCategoryDTO> categoryList = new ArrayList<MemberBuyerCategoryDTO>();
			if (brandIdsList.size() > 0) {
				for (Long lg : brandIdsList) {
					if (lg.compareTo(0l) == 0) {
						continue;
					}
					ItemBrand brand = itemBrandExportService.queryItemBrandById(lg).getResult();
					if (brand == null) {
						continue;
					}
					MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
					d.setBrandId(brand.getBrandId());
					d.setBrandName(brand.getBrandName());
					List<Long> categoryIdList = memberBuyerDao.queryBuyerCategory(memberId,
							memberBuyerSupplierDTO.getSupplierId(), lg);
					List<MemberBuyerCaListDTO> caList = new ArrayList<MemberBuyerCaListDTO>();
					if (categoryIdList.size() > 0) {
						for (Long l : categoryIdList) {
							if (l.compareTo(0l) == 0) {
								continue;
							}
							MemberBuyerCaListDTO dd = new MemberBuyerCaListDTO();
							ItemCategoryDTO result = itemCategoryService.getCategoryByCid(l).getResult();
							if (result != null) {
								dd.setCategoryId(result.getCategoryCid());
								dd.setCategoryName(result.getCategoryCName());
								caList.add(dd);
							}
						}
					}
					d.setCategoryList(caList);
					categoryList.add(d);
				}
			}

			/*
			 * List<Long> categoryIdList =
			 * memberBuyerDao.queryBuyerCategory(memberId, supplierId);
			 * List<MemberBuyerCategoryDTO> categoryList = new
			 * ArrayList<MemberBuyerCategoryDTO>(); if (categoryIdList.size() >
			 * 0) { for (Long l : categoryIdList) { ItemCategoryDTO result =
			 * itemCategoryService.getCategoryByCid(l).getResult();
			 * MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
			 * d.setCategoryId(result.getCategoryCid());
			 * d.setCategoryName(result.getCategoryCName());
			 * categoryList.add(d); } }
			 */
			memberBuyerSupplierDTO.setCategoryList(categoryList);
			rs.setResult(memberBuyerSupplierDTO);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerBusinessSupperlierById】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberBuyerService#
	 * queryBuyerBusinessSupperlierAllBySupplierId(java.lang.Long)
	 */
	@Override
	public ExecuteResult<MemberBuyerSupplierDTO> queryBuyerBusinessSupperlierAllBySupplierId(Long supplierId) {
		ExecuteResult<MemberBuyerSupplierDTO> rs = new ExecuteResult<MemberBuyerSupplierDTO>();
		try {
			MemberBuyerSupplierDTO memberBuyerSupplierDTO = new MemberBuyerSupplierDTO();
			memberBuyerSupplierDTO.setSupplierId(supplierId);
			List<Long> brandIdsList = memberBuyerDao
					.queryBuyerBrandBySupplierId(memberBuyerSupplierDTO.getSupplierId());
			// 最大的品牌品类
			List<MemberBuyerCategoryDTO> categoryList = new ArrayList<MemberBuyerCategoryDTO>();
			if (brandIdsList.size() > 0) {
				for (Long lg : brandIdsList) {
					if (lg.compareTo(0l) == 0) {
						continue;
					}
					ItemBrand brand = itemBrandExportService.queryItemBrandById(lg).getResult();
					if (brand == null) {
						continue;
					}
					MemberBuyerCategoryDTO d = new MemberBuyerCategoryDTO();
					d.setBrandId(brand.getBrandId());
					d.setBrandName(brand.getBrandName());
					List<Long> categoryIdList = memberBuyerDao
							.queryBuyerCategoryBySupplierId(memberBuyerSupplierDTO.getSupplierId(), lg);
					List<MemberBuyerCaListDTO> caList = new ArrayList<MemberBuyerCaListDTO>();
					if (categoryIdList.size() > 0) {
						for (Long l : categoryIdList) {
							if (l.compareTo(0l) == 0) {
								continue;
							}
							MemberBuyerCaListDTO dd = new MemberBuyerCaListDTO();
							ItemCategoryDTO result = itemCategoryService.getCategoryByCid(l).getResult();
							if (result != null) {
								dd.setCategoryId(result.getCategoryCid());
								dd.setCategoryName(result.getCategoryCName());
								caList.add(dd);
							}
						}
					}
					d.setCategoryList(caList);
					categoryList.add(d);

				}
			}

			memberBuyerSupplierDTO.setCategoryList(categoryList);
			rs.setResult(memberBuyerSupplierDTO);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  queryBuyerBusinessSupperlierAllBySupplierId】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBuyerService#isShowIdMsg(java.lang.
	 * Long)
	 */
	@Override
	public ExecuteResult<Boolean> isShowIdMsg(Long memberId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			int isshow = verifyInfoDAO.queryShowIdMsg(memberId);
			if (isshow == 1) {
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			}
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  isShowIdMsg】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBuyerService#updateShowIdMsg(java.lang.
	 * Long)
	 */
	@Override
	public ExecuteResult<Boolean> updateShowIdMsg(Long memberId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			verifyInfoDAO.updateShowIdMsg(memberId, 0);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
		} catch (Exception e) {
			logger.error("执行方法【MemberBuyerServiceImpl -  isShowIdMsg】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 保存会员实名认证信息为待审核状态
	 */
	/*
	 * @Override public ExecuteResult<Boolean>
	 * updateBuyerRealNameBeVerified(Long memberId) { ExecuteResult<Boolean> rs
	 * = new ExecuteResult<Boolean>(); try {
	 * rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
	 * rs.setResultMessage("success"); } catch (Exception e) { logger.error(
	 * "执行方法【MemberBuyerServiceImpl -  updateBuyerPersonalInfo】报错！{}", e);
	 * rs.addErrorMessage("系统异常，请联系系统管理员！");
	 * rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
	 * rs.setResultMessage("error"); } return rs; }
	 */

	private List<MemberBuyerVerifyDetailInfoDTO> removeRepeatVerify(List<MemberBuyerVerifyDetailInfoDTO> list) {
		List<MemberBuyerVerifyDetailInfoDTO> newList = new ArrayList<MemberBuyerVerifyDetailInfoDTO>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			MemberBuyerVerifyDetailInfoDTO dto = list.get(i);
			int repeatSize = newList.size();
			boolean isExit = false;
			if (repeatSize > 0) {
				for (int j = 0; j < repeatSize; j++) {
					MemberBuyerVerifyDetailInfoDTO newDto = newList.get(j);
					if (newDto.getChangeFieldId().equalsIgnoreCase(dto.getChangeFieldId())) {
						isExit = true;
						if (dto.getOperateTime().compareTo(newDto.getOperateTime()) > 0) {
							newList.remove(j);
							newList.add(dto);
						}
						break;
					}
				}
			}
			if (!isExit) {
				newList.add(dto);
			}

		}
		return newList;
	}

	/**
	 * 会员信息修改下行ERP
	 * 
	 * @param dto
	 * @return
	 */
	private boolean addModifyDownErp(MemberBuyerPersonalInfoDTO dto) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate;
		try {
			trancDate = format.parse(format.format(new Date()));
			MemberStatusInfo statusInfo = new MemberStatusInfo();
			statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
			statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
			statusInfo.setSyncErrorMsg("");
			statusInfo.setModifyId(dto.getModifyId());
			statusInfo.setModifyName(dto.getModifyName());
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
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Description: 重置会员店提醒信息 <br> 
	 *  
	 * @author zhoutong <br>
	 * @taskId <br>
	 * @param 
	 * @return <br>
	 */ 
	public void updateSignRemindFlagToIsNeed(ApplyBusiRelationDTO applyBusiRelationDTO) throws Exception {
		logger.info("updateSignRemindFlag方法已进入");
		//查询供应商信息
		MemberBaseDTO vendorBaseDTO = new MemberBaseDTO();
		vendorBaseDTO.setMemberId(applyBusiRelationDTO.getSellerId() + "");
		vendorBaseDTO.setBuyerSellerType("2");
		MemberBaseDTO vendorBase = memberBaseDAO.queryMemberBaseInfoById(vendorBaseDTO);
		if ("0801".equals(vendorBase.getCompanyCode())) {
			//如果是汇通达本部直接return
			return;
		}
		//查询会员店信息
		MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
		memberBaseDTO.setMemberId(applyBusiRelationDTO.getMemberId() + "");
		memberBaseDTO.setBuyerSellerType("1");
		MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfoById(memberBaseDTO);
		String memberCode = memberBase.getMemberCode();
		Integer remindFlag = contractDAO.queryRemindFlagByMemberCode(memberCode);
		ContractSignRemindInfoDTO contractSignRemindInfoDTO = new ContractSignRemindInfoDTO();
		contractSignRemindInfoDTO.setMemberCode(memberCode);
		contractSignRemindInfoDTO.setModifyId(applyBusiRelationDTO.getCreateId());
		contractSignRemindInfoDTO.setModifyName(applyBusiRelationDTO.getCreateName());
		//重置为需要提醒
		contractSignRemindInfoDTO.setIsNeedRemind(0);
		if (remindFlag != null && remindFlag != 0) {
			//查询到的提醒标志不为空 且标志不为0 表示需要提醒更新为0 
			contractDAO.updateContractSignRemindInfo(contractSignRemindInfoDTO);
		}
		logger.info("updateSignRemindFlag方法已结束");
	}
}
