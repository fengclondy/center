package cn.htd.membercenter.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.common.util.HttpUtils;
import cn.htd.membercenter.common.util.JSONTransformUtil;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dao.MemberScoreSetDAO;
import cn.htd.membercenter.domain.BuyerGradeChangeHistory;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.dto.BuyerFinanceExpDTO;
import cn.htd.membercenter.dto.BuyerFinanceHistoryDTO;
import cn.htd.membercenter.dto.BuyerFinanceSyncDTO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.BuyerGradeIntervalDTO;
import cn.htd.membercenter.dto.BuyerScoreIntervalDTO;
import cn.htd.membercenter.dto.HTDUserAmountReturnDto;
import cn.htd.membercenter.dto.HTDUserDailyAmountDto;
import cn.htd.membercenter.dto.HTDUserGradeExpDto;
import cn.htd.membercenter.dto.HTDUserLowestShowDto;
import cn.htd.membercenter.dto.HTDUserUpgradeDistanceDto;
import cn.htd.membercenter.dto.HTDUserUpgradeDto;
import cn.htd.membercenter.dto.HTDUserUpgradePathDto;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGradeHistoryDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.MemberScoreSetDTO;
import cn.htd.membercenter.enums.GradeTypeEnum;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberGradeService;

@Service("memberGradeService")
public class MemberGradeServiceImpl implements MemberGradeService {

	private final static Logger logger = LoggerFactory.getLogger(MemberGradeServiceImpl.class);

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Resource
	private MemberBaseService memberBaseService;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Resource
	private MemberScoreSetDAO memberScoreSetDAO;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Override
	public ExecuteResult<DataGrid<MemberGradeDTO>> queryMemberGradeListInfo(MemberBaseDTO memberBaseDTO,
			Pager<MemberGradeDTO> pager) {
		ExecuteResult<DataGrid<MemberGradeDTO>> rs = new ExecuteResult<DataGrid<MemberGradeDTO>>();
		try {
			DataGrid<MemberGradeDTO> dg = new DataGrid<MemberGradeDTO>();
			List<MemberGradeDTO> memberList = memberGradeDAO.queryMemberGradeListInfo(memberBaseDTO, pager);
			long count = memberGradeDAO.queryMemberGradeInfoCount(memberBaseDTO);
			try {
				if (CollectionUtils.isNotEmpty(memberList)) {
					for (MemberGradeDTO member : memberList) {
						String canMallLogin = member.getCanMallLogin();
						String hasGuaranteeLicense = member.getHasGuaranteeLicense();
						String hasBusinessLicense = member.getHasBusinessLicense();
						// 设置会员等级类型
						member.setMemberType(memberBaseService.judgeMemberType(canMallLogin, hasGuaranteeLicense,
								hasBusinessLicense));
					}
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
				dg.setRows(memberList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberGradeServiceImpl----->queryMemberGradeInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGradeDTO>> queryMemberGradeListInfo4export(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<List<MemberGradeDTO>> rs = new ExecuteResult<List<MemberGradeDTO>>();
		try {
			List<MemberGradeDTO> memberList = memberGradeDAO.queryMemberGradeListInfo4export(memberBaseDTO);
			try {
				if (CollectionUtils.isNotEmpty(memberList)) {
					for (MemberGradeDTO member : memberList) {
						String canMallLogin = member.getCanMallLogin();
						String hasGuaranteeLicense = member.getHasGuaranteeLicense();
						String hasBusinessLicense = member.getHasBusinessLicense();
						// 设置会员等级类型
						member.setMemberType(memberBaseService.judgeMemberType(canMallLogin, hasGuaranteeLicense,
								hasBusinessLicense));
					}
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
			logger.error("MemberGradeServiceImpl----->queryMemberGradeListInfo4export=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberGradeDTO> queryMemberGradeInfo(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<MemberGradeDTO> rs = new ExecuteResult<MemberGradeDTO>();
		try {
			String memberId = memberBaseDTO.getMemberId();
			MemberGradeDTO member = null;
			if (StringUtils.isNotBlank(memberId)) {
				member = memberGradeDAO.queryMemberGradeInfo(memberBaseDTO);
				String canMallLogin = member.getCanMallLogin();
				String hasGuaranteeLicense = member.getHasGuaranteeLicense();
				String hasBusinessLicense = member.getHasBusinessLicense();
				// 设置会员等级类型
				member.setMemberType(
						memberBaseService.judgeMemberType(canMallLogin, hasGuaranteeLicense, hasBusinessLicense));
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
			logger.error("MemberGradeServiceImpl----->queryMemberBaseInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MemberGradeHistoryDTO>> queryMemberGradeHistoryListInfo(MemberBaseDTO memberBaseDTO,
			Pager<MemberGradeHistoryDTO> pager) {
		ExecuteResult<DataGrid<MemberGradeHistoryDTO>> rs = new ExecuteResult<DataGrid<MemberGradeHistoryDTO>>();
		try {
			DataGrid<MemberGradeHistoryDTO> dg = new DataGrid<MemberGradeHistoryDTO>();
			List<MemberGradeHistoryDTO> historyList = memberGradeDAO.queryMemberGradeHistoryListInfo(memberBaseDTO,
					pager);
			long count = memberGradeDAO.queryMemberGradeHistoryListInfoCount(memberBaseDTO);
			try {
				if (historyList != null) {
					dg.setRows(historyList);
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
			logger.error("MemberGradeServiceImpl----->queryMemberGradeInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> modifyMemberGrade4vip(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String memberId = memberBaseDTO.getMemberId();
		if (StringUtils.isNotBlank(memberId)) {
			MemberGradeDTO member = memberGradeDAO.queryMemberGradeInfo(memberBaseDTO);
			String isVip = member.getIsVip();
			String memberGrade = member.getBuyerGrade();
			if (StringUtils.isNotBlank(isVip)) {
				try {
					if (!isVip.equals(memberBaseDTO.getIsVip())) {
						if ("0".equals(isVip)) {
							memberBaseDTO.setBuyerGrade(member.getPointGrade());
						}
						memberGradeDAO.updateMemberGradeInfo(memberBaseDTO);
						BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
						bgch.setBuyerId(memberBaseDTO.getMemberId());
						bgch.setAfterGrade(GlobalConstant.MEMBER_SCORE_SET_OPERATE);
						bgch.setChangeGrade(memberBaseDTO.getBuyerGrade());
						bgch.setIsUpgrade("");
						bgch.setOperateId(memberBaseDTO.getOperateId());
						bgch.setOperateName(memberBaseDTO.getOperateName());
						memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
						rs.setResultMessage("success");
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					} else {
						if (!memberGrade.equals(memberBaseDTO.getBuyerGrade())) {
							memberGradeDAO.updateMemberGradeInfo(memberBaseDTO);
							BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
							bgch.setBuyerId(memberBaseDTO.getMemberId());
							bgch.setAfterGrade(GlobalConstant.MEMBER_SCORE_RULE_OPERATE);
							bgch.setChangeGrade(memberBaseDTO.getBuyerGrade());
							if (new Integer(memberGrade).intValue() < new Integer(memberBaseDTO.getBuyerGrade())
									.intValue()) {
								bgch.setIsUpgrade("1");
							} else if (new Integer(memberGrade).intValue() > new Integer(memberBaseDTO.getBuyerGrade())
									.intValue()) {
								bgch.setIsUpgrade("0");
							}
							bgch.setOperateId(memberBaseDTO.getOperateId());
							bgch.setOperateName(memberBaseDTO.getOperateName());
							memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
							rs.setResultMessage("success");
							rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
						} else {
							rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
							rs.addErrorMessage("收费会员等级没有发生变更");
						}
					}
				} catch (Exception e) {
					logger.error("MemberGradeServiceImpl----->modifyMemberGrade4vip=" + e);
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写需要变更的收费等级信息");
			}
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> modifyMemberGrade4Grade(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String memberId = memberBaseDTO.getMemberId();
		if (StringUtils.isNotBlank(memberId)) {
			MemberGradeDTO member = memberGradeDAO.queryMemberGradeInfo(memberBaseDTO);
			String memberGrade = member.getBuyerGrade();
			if (StringUtils.isNotBlank(memberGrade)) {
				try {
					if (!memberGrade.equals(memberBaseDTO.getBuyerGrade())) {
						memberGradeDAO.updateMemberGradeInfo(memberBaseDTO);
						BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
						bgch.setBuyerId(memberBaseDTO.getMemberId());
						bgch.setAfterGrade(memberBaseDTO.getOperateType());
						bgch.setChangeGrade(memberBaseDTO.getBuyerGrade());
						if (new Integer(memberGrade).intValue() > new Integer(memberBaseDTO.getBuyerGrade())
								.intValue()) {
							bgch.setIsUpgrade("0");
						} else if (new Integer(memberGrade).intValue() < new Integer(memberBaseDTO.getBuyerGrade())
								.intValue()) {
							bgch.setIsUpgrade("1");
						}

						bgch.setOperateId(memberBaseDTO.getOperateId());
						bgch.setOperateName(memberBaseDTO.getOperateName());
						memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
						rs.setResultMessage("success");
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					} else {
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage("收费会员等级没有发生变更");
					}
				} catch (Exception e) {
					logger.error("MemberGradeServiceImpl----->modifyMemberGrade4vip=" + e);
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写需要变更的收费等级信息");
			}
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberGradeDTO>> selectMemberGrade(int buyerGrade) {
		ExecuteResult<List<MemberGradeDTO>> rs = new ExecuteResult<List<MemberGradeDTO>>();
		try {
			List<MemberGradeDTO> list = memberGradeDAO.selectMemberGrade(buyerGrade);
			if (list != null) {
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("没有查询到当前会员信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setResultMessage("error");
			logger.error("MemberGradeServiceImpl=======>selectMemberGrade" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#calUserGradeExp(java.lang.
	 * String, long, java.util.Date, java.math.BigDecimal)
	 */
	@Override
	public ExecuteResult<Boolean> modifyUserGradeExp(String orderNo, String memberCode, Date orderTime,
			BigDecimal orderPrice, String orderStatus) {
		logger.debug("modifyUserGradeExp----orderNo:" + orderNo + ":memberCode:" + memberCode + ":orderTime:"
				+ orderTime + ":orderPrice:" + orderPrice + ":orderStatus:" + orderStatus);
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String pointType = getPointTypeByOrderStatus(orderStatus);
		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();

		try {
			long hasCalculateFlg = 0l;
			long memberId = memberBaseInfoService.getMemberIdByCode(memberCode).getResult();
			hasCalculateFlg = memberGradeDAO.hasCalculateOrder(orderNo, memberId);
			if (hasCalculateFlg > 0) {
				rs.addErrorMessage("订单已经被计算");
				rs.setResult(false);
				return rs;
			}

			BigDecimal payCost = orderPrice;
			if (GradeTypeEnum.WITHDRAW.getValue().equals(pointType)) {
				payCost = payCost.negate();
			}

			// 会员最新数据
			BuyerGradeInfoDTO buyerGradeInfoDTO = memberGradeDAO.getHTDMemberGrade(memberId);
			// 计算出的经验值
			HTDUserGradeExpDto orderExpDto = calculateUserOrderExp(orderScoreModelList, financeScoreModelList,
					mallWeight, financeWeight, buyerGradeInfoDTO, payCost);
			// 添加履历
			makeUserOrderPointHis(buyerGradeInfoDTO, orderExpDto, pointType, memberId, orderNo);
			// 计算金融经验
			HTDUserGradeExpDto financeExpDto = calculateUserFinanceExp(orderScoreModelList, financeScoreModelList,
					mallWeight, financeWeight, buyerGradeInfoDTO, null);
			// 计算等级
			BuyerGradeIntervalDTO newBuyerGradeInfoDTO = calculateHTDUserGrade(orderScoreModelList,
					financeScoreModelList, pointList, mallWeight, financeWeight, buyerGradeInfoDTO, orderExpDto,
					financeExpDto);
			buyerGradeInfoDTO = upgradeHTDMemberUserGrade(orderScoreModelList, financeScoreModelList, pointList,
					mallWeight, financeWeight, buyerGradeInfoDTO, newBuyerGradeInfoDTO);

			memberGradeDAO.updateByPrimaryKeySelective(buyerGradeInfoDTO);
			rs.setResult(true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setResult(false);
		}
		return rs;
	}

	/**
	 * 生成商城交易经验值履历
	 * 
	 * @param memberGradeModel
	 * @param expDto
	 * @param pointType
	 * @param orderModel
	 * @return
	 */
	private void makeUserOrderPointHis(BuyerGradeInfoDTO buyerGradeInfoDTO, HTDUserGradeExpDto expDto, String pointType,
			long memberId, String orderNo) {

		BuyerPointHistory bgch = new BuyerPointHistory();

		boolean isVip = buyerGradeInfoDTO.getIsVip().intValue() == 0 ? false
				: buyerGradeInfoDTO.getIsVip().intValue() == 1;
		bgch.setBuyerId(memberId);
		bgch.setPointType(pointType);
		bgch.setOrderId(orderNo);
		BigDecimal nowPoint = expDto.getMonthRealPoint().setScale(0, RoundingMode.DOWN);
		BigDecimal lastPoint = buyerGradeInfoDTO.getYearExp() == null ? BigDecimal.ZERO
				: buyerGradeInfoDTO.getYearExp();
		BigDecimal plusPoint = nowPoint.subtract(lastPoint);

		if (BigDecimal.ZERO.compareTo(plusPoint) != 0) {

			bgch.setProvideTime(new Date());
			bgch.setProvidePoint(plusPoint);
			bgch.setIsVisible(new Byte("1"));
			if (isVip) {
				bgch.setIsVisible(new Byte("0"));
			}
			// TODO 登陆人
			bgch.setCreateId(0l);
			bgch.setCreateName("SYS");
			bgch.setModifyId(0l);
			bgch.setModifyName("SYS");

			memberGradeDAO.insertPointHistoryInfo(bgch);
		}

	}

	/**
	 * 计算会员商品交易经验值
	 * 
	 * @param orderScoreModelList
	 * @param financeScoreModelList
	 * @param pointList
	 * @param mallWeight
	 * @param financeWeight
	 * @param buyerGradeInfoDTO
	 * @param payCost
	 * @return
	 */
	private HTDUserGradeExpDto calculateUserOrderExp(List<BuyerScoreIntervalDTO> orderScoreModelList,
			List<BuyerScoreIntervalDTO> financeScoreModelList, BigDecimal mallWeight, BigDecimal financeWeight,
			BuyerGradeInfoDTO buyerGradeInfoDTO, BigDecimal payCost) {
		BigDecimal oWeight = mallWeight;
		BuyerScoreIntervalDTO orderModel = null;
		HTDUserGradeExpDto retDto = new HTDUserGradeExpDto();
		BigDecimal totalOrderAmount = buyerGradeInfoDTO.getYearOrderAmount() == null ? BigDecimal.ZERO
				: buyerGradeInfoDTO.getYearOrderAmount();
		BigDecimal monthOrderAmount = buyerGradeInfoDTO.getMonthOrderAmount() == null ? BigDecimal.ZERO
				: buyerGradeInfoDTO.getMonthOrderAmount();
		BigDecimal fromAmount = null;
		BigDecimal toAmount = null;
		BigDecimal amountLimit = null;
		BigDecimal score = null;
		BigDecimal realExp = BigDecimal.ZERO;
		BigDecimal levelExp = BigDecimal.ZERO;
		Map<String, BigDecimal> orderAmountMap = new HashMap<String, BigDecimal>();
		String type = "";
		BigDecimal orderAmount = BigDecimal.ZERO;

		totalOrderAmount = totalOrderAmount.add(payCost);
		monthOrderAmount = monthOrderAmount.add(payCost);
		orderAmountMap.put("YEAR", totalOrderAmount);
		orderAmountMap.put("MONTH", monthOrderAmount);
		buyerGradeInfoDTO.setYearOrderAmount(totalOrderAmount);
		buyerGradeInfoDTO.setMonthOrderAmount(monthOrderAmount);

		Iterator<String> it = orderAmountMap.keySet().iterator();
		while (it.hasNext()) {
			type = it.next();
			orderAmount = orderAmountMap.get(type);
			realExp = BigDecimal.ZERO;
			levelExp = BigDecimal.ZERO;
			orderModel = null;
			amountLimit = null;
			score = null;
			for (BuyerScoreIntervalDTO orderScoreModel : orderScoreModelList) {
				fromAmount = orderScoreModel.getFromAmount().multiply(new BigDecimal(10000));
				if (fromAmount.compareTo(BigDecimal.ZERO) == 0) {
					fromAmount = new BigDecimal("-1");
				}
				toAmount = orderScoreModel.getToAmount().multiply(new BigDecimal(10000));
				if (orderAmount.compareTo(fromAmount) > 0) {
					if (toAmount.compareTo(BigDecimal.ZERO) == 0) {
						orderModel = orderScoreModel;
						score = orderScoreModel.getScore();
						break;
					}
					if (orderAmount.compareTo(toAmount) <= 0) {
						orderModel = orderScoreModel;
						amountLimit = toAmount;
						score = orderScoreModel.getScore();
						// if (orderAmount.compareTo(BigDecimal.ZERO) == 0) {
						// score = BigDecimal.ZERO;
						// }
						break;
					}
				}
			}
			if (score != null) {
				levelExp = score.multiply(oWeight);
				realExp = levelExp;
				if (amountLimit != null) {
					realExp = realExp.multiply(orderAmount).divide(amountLimit, 5, RoundingMode.HALF_UP);
				}
			}
			if ("YEAR".equals(type)) {
				retDto.setRealPoint(realExp);
				retDto.setLevelPoint(levelExp);
				retDto.setOrderModel(orderModel);
			} else if ("MONTH".equals(type)) {
				retDto.setMonthRealPoint(realExp);
				retDto.setOrderMonthModel(orderModel);
			}
		}
		return retDto;
	}

	/**
	 * 根据订单状态取得会员经验类型
	 * 
	 * @param orderStatus
	 * @return
	 */
	private String getPointTypeByOrderStatus(String orderStatus) {
		String pointType = "";
		if (GradeTypeEnum.ORDER.getValue().equals(orderStatus)) {
			pointType = GradeTypeEnum.ORDER.getValue();
		} else if (GradeTypeEnum.WITHDRAW.getValue().equals(orderStatus)) {
			pointType = GradeTypeEnum.WITHDRAW.getValue();
		}
		return pointType;
	}

	/**
	 * 计算会员金融产品经验值
	 * 
	 * @param defaultDto
	 * @param memberGradeModel
	 * @param userDailyAmountMap
	 * @return
	 */
	private HTDUserGradeExpDto calculateUserFinanceExp(List<BuyerScoreIntervalDTO> orderScoreModelList,
			List<BuyerScoreIntervalDTO> financeScoreModelList, BigDecimal mallWeight, BigDecimal financeWeight,
			BuyerGradeInfoDTO buyerGradeInfoDTO, Map<String, List<BuyerFinanceExpDTO>> userDailyAmountMap) {

		BigDecimal fWeight = financeWeight;
		HTDUserGradeExpDto retDto = new HTDUserGradeExpDto();

		BigDecimal fromAmount = null;
		BigDecimal toAmount = null;
		BigDecimal amountLimit = null;
		BigDecimal score = null;
		BigDecimal realExp = BigDecimal.ZERO;
		BigDecimal levelExp = BigDecimal.ZERO;
		BigDecimal financeTotalDailyAmount = BigDecimal.ZERO;
		BigDecimal financeMonthDailyAmount = BigDecimal.ZERO;
		Map<String, BigDecimal> financeDailyAmountMap = new HashMap<String, BigDecimal>();
		String type = "";
		BigDecimal financeDailyAmount = BigDecimal.ZERO;
		List<BuyerFinanceExpDTO> userDailyAmountList = null;
		if (userDailyAmountMap != null) {
			userDailyAmountList = userDailyAmountMap.get("YEAR");
			for (BuyerFinanceExpDTO dailyAmountModel : userDailyAmountList) {
				financeTotalDailyAmount = financeTotalDailyAmount.add(dailyAmountModel.getDailyAmountAvg());
			}
			buyerGradeInfoDTO.setYearFinanceAvg(financeTotalDailyAmount);
			userDailyAmountList = userDailyAmountMap.get("MONTH");
			for (BuyerFinanceExpDTO dailyAmountModel : userDailyAmountList) {
				financeMonthDailyAmount = financeMonthDailyAmount.add(dailyAmountModel.getDailyAmountAvg());
			}
			buyerGradeInfoDTO.setMonthFinanceAvg(financeMonthDailyAmount);
		} else {
			financeTotalDailyAmount = buyerGradeInfoDTO.getYearFinanceAvg();
			financeMonthDailyAmount = buyerGradeInfoDTO.getMonthFinanceAvg();
		}

		financeDailyAmountMap.put("YEAR", financeTotalDailyAmount);
		financeDailyAmountMap.put("MONTH", financeMonthDailyAmount);
		BuyerScoreIntervalDTO userFinanceScoreModel = null;
		Iterator<String> it = financeDailyAmountMap.keySet().iterator();
		while (it.hasNext()) {
			type = it.next();
			financeDailyAmount = financeDailyAmountMap.get(type);
			realExp = BigDecimal.ZERO;
			levelExp = BigDecimal.ZERO;
			userFinanceScoreModel = null;
			amountLimit = null;

			score = null;
			for (BuyerScoreIntervalDTO financeScoreModel : financeScoreModelList) {
				fromAmount = financeScoreModel.getFromAmount().multiply(new BigDecimal(10000));
				if (fromAmount.compareTo(BigDecimal.ZERO) == 0) {
					fromAmount = new BigDecimal("-1");
				}
				toAmount = financeScoreModel.getToAmount().multiply(new BigDecimal(10000));
				if (financeDailyAmount.compareTo(fromAmount) > 0) {
					if (toAmount.compareTo(BigDecimal.ZERO) == 0) {
						userFinanceScoreModel = financeScoreModel;
						score = financeScoreModel.getScore();
						break;
					}
					if (financeDailyAmount.compareTo(toAmount) <= 0) {
						userFinanceScoreModel = financeScoreModel;
						amountLimit = toAmount;
						score = financeScoreModel.getScore();
						// if (financeDailyAmount.compareTo(BigDecimal.ZERO) ==
						// 0) {
						// score = BigDecimal.ZERO;
						// }
						break;
					}
				}
			}
			if (score != null) {
				levelExp = score.multiply(fWeight);
				realExp = levelExp;
				if (amountLimit != null) {
					realExp = realExp.multiply(financeDailyAmount).divide(amountLimit, 5, RoundingMode.HALF_UP);
				}
			}
			if ("YEAR".equals(type)) {
				retDto.setRealPoint(realExp);
				retDto.setLevelPoint(levelExp);
				retDto.setFinanceModel(userFinanceScoreModel);
			} else if ("MONTH".equals(type)) {
				retDto.setMonthRealPoint(realExp);
				retDto.setFinanceMonthModel(userFinanceScoreModel);
			}
		}
		return retDto;
	}

	/**
	 * 根据会员经验值计算经验会员等级
	 *
	 * @param defaultDto
	 * @param memberGradeModel
	 * @param orderExpDto
	 * @param financeExpDto
	 * @return
	 */
	private BuyerGradeIntervalDTO calculateHTDUserGrade(List<BuyerScoreIntervalDTO> orderScoreModelList,
			List<BuyerScoreIntervalDTO> financeScoreModelList, List<BuyerGradeIntervalDTO> userGradePointList,
			BigDecimal mallWeight, BigDecimal financeWeight, BuyerGradeInfoDTO buyerGradeInfoDTO,
			HTDUserGradeExpDto orderExpDto, HTDUserGradeExpDto financeExpDto) {

		BigDecimal fromPoint = null;
		BigDecimal toPoint = null;
		BigDecimal totalRealPoint = orderExpDto.getRealPoint().add(financeExpDto.getRealPoint()).setScale(0,
				RoundingMode.DOWN);
		BigDecimal totalLevelPoint = orderExpDto.getLevelPoint().add(financeExpDto.getLevelPoint()).setScale(0,
				RoundingMode.DOWN);
		BigDecimal monthPoint = orderExpDto.getMonthRealPoint().add(financeExpDto.getMonthRealPoint()).setScale(0,
				RoundingMode.DOWN);
		BigDecimal orderPoint = orderExpDto.getMonthRealPoint().setScale(0, RoundingMode.DOWN);
		BigDecimal financePoint = monthPoint.subtract(orderPoint);
		BuyerGradeIntervalDTO userGradePointModel = null;

		Long userGrade = userGradePointList.get(0).getBuyerLevel();
		for (BuyerGradeIntervalDTO gradePointModel : userGradePointList) {
			fromPoint = new BigDecimal(gradePointModel.getFromScore());
			if (fromPoint.compareTo(BigDecimal.ZERO) == 0) {
				fromPoint = new BigDecimal("-1");
			}

			toPoint = new BigDecimal(gradePointModel.getToScore());

			if (totalLevelPoint.compareTo(fromPoint) > 0) {
				if (toPoint.compareTo(BigDecimal.ZERO) == 0) {
					userGrade = gradePointModel.getBuyerLevel();
					userGradePointModel = gradePointModel;
					break;
				}
				if (totalLevelPoint.compareTo(toPoint) <= 0) {
					userGrade = gradePointModel.getBuyerLevel();
					userGradePointModel = gradePointModel;
					break;
				}
			}
		}

		buyerGradeInfoDTO.setPointGrade(userGrade);
		buyerGradeInfoDTO.setMonthExp(monthPoint);
		buyerGradeInfoDTO.setYearExp(totalRealPoint);
		buyerGradeInfoDTO.setLevelExp(totalLevelPoint);
		buyerGradeInfoDTO.setMonthOrderExp(orderPoint);
		buyerGradeInfoDTO.setMonthFinanceExp(financePoint);
		buyerGradeInfoDTO.setYearOrderLevel(orderExpDto.getOrderModel().getId());
		buyerGradeInfoDTO.setYearFinanceLevel(financeExpDto.getFinanceModel().getId());
		return userGradePointModel;
	}

	/**
	 * 升级HTDMember的用户等级
	 * 
	 * @param defaultDto
	 * @param memberGradeModel
	 * @param userGradePointModel
	 * @return
	 */
	private BuyerGradeInfoDTO upgradeHTDMemberUserGrade(List<BuyerScoreIntervalDTO> orderScoreModelList,
			List<BuyerScoreIntervalDTO> financeScoreModelList, List<BuyerGradeIntervalDTO> userGradePointList,
			BigDecimal mallWeight, BigDecimal financeWeight, BuyerGradeInfoDTO buyerGradeInfoDTO,
			BuyerGradeIntervalDTO newBuyerGradeInfoDTO) {

		BigDecimal userGradeFromPoint = BigDecimal.ZERO;
		BigDecimal oldUserGradeFromPoint = BigDecimal.ZERO;
		String oldUserGrade = buyerGradeInfoDTO.getBuyerGrade();
		Byte isVip = buyerGradeInfoDTO.getIsVip().intValue() == 0 ? new Byte("0") : buyerGradeInfoDTO.getIsVip();
		Long pointUserGrade = buyerGradeInfoDTO.getPointGrade();
		BuyerGradeIntervalDTO oldUserGradePointModel = null;
		buyerGradeInfoDTO.setIsVip(isVip);
		if (oldUserGrade == null && userGradePointList != null && userGradePointList.size() > 0) {
			oldUserGrade = userGradePointList.get(0).getBuyerLevel().toString();
		}
		logger.debug("oldUserGrade=" + oldUserGrade);

		if (!(isVip.intValue() == 1)) {
			for (BuyerGradeIntervalDTO gradePointModel : userGradePointList) {
				if (gradePointModel.getBuyerLevel().toString().equals(oldUserGrade)) {
					oldUserGradePointModel = gradePointModel;
					break;
				}
			}
			if (newBuyerGradeInfoDTO != null) {
				userGradeFromPoint = newBuyerGradeInfoDTO.getFromScore().compareTo(0l) == 0 ? BigDecimal.ONE.negate()
						: new BigDecimal(newBuyerGradeInfoDTO.getFromScore());
			}
			if (oldUserGradePointModel != null) {
				oldUserGradeFromPoint = oldUserGradePointModel.getFromScore().compareTo(0l) == 0
						? BigDecimal.ONE.negate() : new BigDecimal(oldUserGradePointModel.getFromScore());
			}

			// 当日升级不降级
			if (userGradeFromPoint.compareTo(oldUserGradeFromPoint) > 0) {
				buyerGradeInfoDTO.setBuyerGrade(pointUserGrade.toString());
				buyerGradeInfoDTO.setIsUpgrade("1");
				buyerGradeInfoDTO.setIsSbUpgrade("1");
				BuyerGradeChangeHistory userGradeChgHisModel = new BuyerGradeChangeHistory();

				userGradeChgHisModel.setBuyerId(buyerGradeInfoDTO.getBuyerId().toString());
				userGradeChgHisModel.setChangeTime(new Date());
				userGradeChgHisModel.setIsUpgrade("1");
				userGradeChgHisModel.setChangeGrade(pointUserGrade.toString());
				userGradeChgHisModel.setOperateId("1");
				userGradeChgHisModel.setOperateName("SYS");
				userGradeChgHisModel.setAfterGrade("");
				memberGradeDAO.insertMemberGradeHistoryInfo(userGradeChgHisModel);
			}

		} else {
			// vip套餐每日更新
			buyerGradeInfoDTO.setBuyerGrade("6");
		}

		return buyerGradeInfoDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#syncFinanceDailyAmount(
	 * java.util.Date)
	 */
	@Override
	public void syncFinanceDailyAmount(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String financeType = null;
		String financeUrl = "";
		List<BuyerFinanceHistoryDTO> userFinanceHisList = null;
		List<BuyerFinanceHistoryDTO> hisModelList = null;
		BuyerFinanceHistoryDTO hisModel = null;
		HTDUserDailyAmountDto postDto = null;
		HTDUserAmountReturnDto retDto = null;
		List<Date> targetDtList = new ArrayList<Date>();
		String erpIPAddress = StaticProperty.toERPIPAddress;
		String hxdIpAddress = "";
		String jsyhIpAddress = StaticProperty.toJSYHIPAddress;
		String hndIpAddress = StaticProperty.toHNDIPAddress;

		String HND_URL = "/middleware/postHndData";
		String HXD_URL = "";
		String JSYH_URL = "/xdpt/mallCredit/queryDebtAmountList";
		String HZF_URL = "/webservice-cxf/restservice/ErpScfService/getFinancialBalance";

		Map<String, String> urlMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(erpIPAddress)) {
			if (!StringUtils.isEmpty(HZF_URL)) {
				urlMap.put("HZF", erpIPAddress + HZF_URL);
			}
		}
		if (!StringUtils.isEmpty(hndIpAddress)) {
			if (!StringUtils.isEmpty(HND_URL)) {
				urlMap.put("HND", hndIpAddress + HND_URL);
			}
		}
		if (!StringUtils.isEmpty(hxdIpAddress)) {
			if (!StringUtils.isEmpty(HXD_URL)) {
				urlMap.put("HXD", hxdIpAddress + HXD_URL);
			}
		}
		if (!StringUtils.isEmpty(jsyhIpAddress)) {
			if (!StringUtils.isEmpty(JSYH_URL)) {
				urlMap.put("JSYH", jsyhIpAddress + JSYH_URL);
			}
		}
		Iterator<String> it = urlMap.keySet().iterator();
		Map<String, BuyerFinanceSyncDTO> financeInitMap = new HashMap<String, BuyerFinanceSyncDTO>();
		List<BuyerFinanceSyncDTO> retList = memberGradeDAO.getFinanceDailyAmountSync();
		for (BuyerFinanceSyncDTO tmpModel : retList) {
			financeInitMap.put(tmpModel.getFinanceType(), tmpModel);
		}
		BuyerFinanceSyncDTO syncModel = null;
		String lastCusNod = "";
		String retStatus = "";
		String hisDtStr = "";
		String newDtStr = "";
		String errMsg = "";
		while (it.hasNext()) {
			financeType = it.next();
			financeUrl = urlMap.get(financeType);
			if (StringUtils.isEmpty(financeUrl)) {
				continue;
			}
			syncModel = null;
			lastCusNod = "";
			if (financeInitMap != null && financeInitMap.size() > 0) {
				if (financeInitMap.containsKey(financeType)) {
					syncModel = financeInitMap.get(financeType);
					lastCusNod = syncModel.getErpCode();
				}
			}
			try {
				int SYNC_PAGE_SIZE = 1000;
				targetDtList = getTargetDateList(syncModel, date);
				for (Date targetDt : targetDtList) {
					while (true) {
						postDto = new HTDUserDailyAmountDto();
						postDto.setTargetDate(sdf.format(targetDt));
						postDto.setLastCusNo(lastCusNod);
						postDto.setPageSize(SYNC_PAGE_SIZE);

						if ("JSYH".equals(financeType)) {
							retDto = genJsyhReturn(financeUrl, postDto);
						} else if ("HZF".equals(financeType)) {
							retDto = genHzfReturn(financeUrl, postDto);
						} else if ("HND".equals(financeType)) {
							retDto = genHndReturn(financeUrl, postDto);
						}
						retStatus = retDto.getStatus() != null ? retDto.getStatus() : "";
						if (!"1".equals(retStatus)) {
							errMsg = "ConnectUrl:" + financeUrl + " responseStatus:" + retStatus + " errMsg:"
									+ retDto.getErrMsg();
							logger.error(errMsg);
							throw new Exception(errMsg);
						}
						lastCusNod = "";
						userFinanceHisList = retDto.getReturnList();
						if (userFinanceHisList != null && userFinanceHisList.size() > 0) {
							for (BuyerFinanceHistoryDTO tmpModel : userFinanceHisList) {
								lastCusNod = tmpModel.getErpCode();
								tmpModel.setFinanceType(financeType);

								Calendar cal = Calendar.getInstance();

								cal.setTime(targetDt);
								cal.set(Calendar.HOUR_OF_DAY, 23);
								cal.set(Calendar.MINUTE, 59);
								cal.set(Calendar.SECOND, 59);
								tmpModel.setTargetDate(cal.getTime());

								hisModelList = memberGradeDAO.getHTDUserDailyFinanceHis(tmpModel);
								tmpModel.setTargetDate(targetDt);
								if (hisModelList != null && hisModelList.size() > 0) {
									hisModel = hisModelList.get(0);
									if (hisModel.getAmount().compareTo(tmpModel.getAmount()) != 0) {
										hisDtStr = sdf.format(hisModel.getTargetDate());
										newDtStr = sdf.format(tmpModel.getTargetDate());
										if (hisDtStr.equals(newDtStr)) {
											hisModel.setAmount(tmpModel.getAmount());
											memberGradeDAO.updateHTDUserDailyFinanceHis(hisModel);
										} else {
											memberGradeDAO.saveHTDUserDailyFinanceHis(tmpModel);
										}
									}
								} else {
									memberGradeDAO.saveHTDUserDailyFinanceHis(tmpModel);
								}
							}
							if (SYNC_PAGE_SIZE > userFinanceHisList.size()) {
								lastCusNod = "";
							}
							if (syncModel == null) {
								syncModel = new BuyerFinanceSyncDTO();
							}
							syncModel.setFinanceType(financeType);
							syncModel.setLastDate(targetDt);
							syncModel.setErpCode(lastCusNod);
							syncModel.setDeleteFlag(new Byte("0"));
							if (syncModel.getId() == null) {
								memberGradeDAO.saveBuyerFinanceSync(syncModel);
								syncModel.setId(0l);
							} else {
								memberGradeDAO.updateBuyerFinanceSync(syncModel);
							}
							if (SYNC_PAGE_SIZE > userFinanceHisList.size()) {
								break;
							}
						} else {
							if (syncModel == null) {
								syncModel = new BuyerFinanceSyncDTO();
							}
							syncModel.setFinanceType(financeType);
							syncModel.setLastDate(targetDt);
							syncModel.setErpCode("");
							syncModel.setDeleteFlag(new Byte("0"));
							if (syncModel.getId() == null) {
								memberGradeDAO.saveBuyerFinanceSync(syncModel);
								syncModel.setId(0l);
							} else {
								memberGradeDAO.updateBuyerFinanceSync(syncModel);
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				logger.error("syncFinanceDailyAmount exception :" + e.getMessage(), e);
				continue;
			}
		}

	}

	/**
	 * 对于未初始化的金融产品取得需要获取的日期List
	 * 
	 * @param syncModel
	 * @param endDt
	 * @return
	 * @throws Exception
	 */
	private List<Date> getTargetDateList(BuyerFinanceSyncDTO syncModel, Date endDt) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> targetDtList = new ArrayList<Date>();
		String endDtStr = sdf.format(endDt);
		Calendar cal = Calendar.getInstance();
		Date startDate = null;
		String startDateStr = "";

		if (syncModel != null) {
			cal.setTime(syncModel.getLastDate());
			if (StringUtils.isEmpty(syncModel.getErpCode())) {
				cal.add(Calendar.DATE, 1);
			}
		} else {
			cal.setTime(endDt);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
		}
		startDate = cal.getTime();
		startDateStr = sdf.format(startDate);
		while (startDateStr.compareTo(endDtStr) <= 0) {
			targetDtList.add(startDate);
			cal.add(Calendar.DATE, 1);
			startDate = cal.getTime();
			startDateStr = sdf.format(startDate);
		}
		return targetDtList;
	}

	/**
	 * 读取金融产品返回会员金融产品余额
	 * 
	 * @param connectUrl
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private HTDUserAmountReturnDto genJsyhReturn(String connectUrl, HTDUserDailyAmountDto dto) throws Exception {

		HTDUserAmountReturnDto retDto = new HTDUserAmountReturnDto();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, String>> tmpMapList = null;
		BuyerFinanceHistoryDTO hisModel = null;
		List<BuyerFinanceHistoryDTO> returnList = new ArrayList<BuyerFinanceHistoryDTO>();
		String resultStr = "";
		String[] resultArr = null;
		String[] paramTemp = null;
		String status = "";
		String errmsg = "";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		try {
			paramList.add(new BasicNameValuePair("targetDate", dto.getTargetDate() + " 23:59:59"));
			paramList.add(new BasicNameValuePair("lastCusNo", dto.getLastCusNo()));
			paramList.add(new BasicNameValuePair("pageSize", String.valueOf(dto.getPageSize())));
			resultStr = sendJsyhPost(connectUrl, paramList);
			if (!resultStr.contains("&")) {
				return retDto;
			}
			resultArr = resultStr.split("&");
			for (String str : resultArr) {
				paramTemp = str.split("=");
				if ("data".equals(paramTemp[0])) {
					resultStr = paramTemp[1];
				}
			}
			if (StringUtils.isEmpty(resultStr)) {
				return retDto;
			}
			resultMap = JSONTransformUtil.toObject(resultStr, HashMap.class);
			if (resultMap != null) {
				status = resultMap.containsKey("errno") ? (String) resultMap.get("errno") : "";
				status = "0".equals(status) ? "1" : "0";
				retDto.setStatus(status);
				errmsg = resultMap.containsKey("errorMsg") ? (String) resultMap.get("errorMsg") : "";
				errmsg = "-".equals(errmsg) ? "" : errmsg;
				retDto.setErrMsg(errmsg);
				if (resultMap.containsKey("result")) {
					tmpMapList = (List<Map<String, String>>) resultMap.get("result");
					if (tmpMapList != null && tmpMapList.size() > 0) {
						for (Map<String, String> tmpMap : tmpMapList) {
							hisModel = new BuyerFinanceHistoryDTO();
							hisModel.setErpCode(tmpMap.get("memberErpcode"));
							hisModel.setAmount(new BigDecimal(String.valueOf(tmpMap.get("loanAmount"))));
							returnList.add(hisModel);
						}
						retDto.setReturnList(returnList);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return retDto;
	}

	/**
	 * 读取汇致富金融产品余额
	 * 
	 * @param connectUrl
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private HTDUserAmountReturnDto genHzfReturn(String connectUrl, HTDUserDailyAmountDto dto) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		HTDUserAmountReturnDto retDto = new HTDUserAmountReturnDto();
		BuyerFinanceHistoryDTO hisModel = null;
		Map<String, Object> resultMap = null;
		List<Map<String, String>> tmpMapList = null;
		List<BuyerFinanceHistoryDTO> returnList = new ArrayList<BuyerFinanceHistoryDTO>();
		StringBuilder sb = new StringBuilder("UserName=JLSOFT&PassWord=888888");
		sb.append("&JSONXML=");
		try {
			sb.append(mapper.writeValueAsString(dto));
			resultMap = sendJlErpJson(connectUrl, sb.toString());
			if (resultMap != null) {
				retDto.setStatus(resultMap.containsKey("JL_State") ? (String) resultMap.get("JL_State") : "");
				retDto.setErrMsg(resultMap.containsKey("JL_ERR") ? (String) resultMap.get("JL_ERR") : "");
				if (resultMap.containsKey("JL_Return")) {
					tmpMapList = (List<Map<String, String>>) resultMap.get("JL_Return");
					if (tmpMapList != null && tmpMapList.size() > 0) {
						for (Map<String, String> tmpMap : tmpMapList) {
							hisModel = new BuyerFinanceHistoryDTO();
							hisModel.setErpCode(tmpMap.get("cusNo"));
							hisModel.setAmount(new BigDecimal(tmpMap.get("amount")));
							returnList.add(hisModel);
						}
						retDto.setReturnList(returnList);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return retDto;
	}

	/**
	 * 读取汇农贷金融产品余额
	 * 
	 * @param connectUrl
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private HTDUserAmountReturnDto genHndReturn(String connectUrl, HTDUserDailyAmountDto dto) throws Exception {

		HTDUserAmountReturnDto retDto = new HTDUserAmountReturnDto();
		BuyerFinanceHistoryDTO hisModel = null;
		String paramStr = JSONTransformUtil.toJsonStr(dto);
		String result = "";
		Map<String, Object> resultMap = null;
		List<Map<String, String>> tmpMapList = null;
		List<BuyerFinanceHistoryDTO> returnList = new ArrayList<BuyerFinanceHistoryDTO>();
		final Map<String, String> postHeaders = new HashMap<String, String>();
		postHeaders.put("Content-Type", "application/json");
		try {
			logger.debug("HND connectUrl:" + connectUrl + "?" + paramStr);
			result = HttpUtils.httpPost(connectUrl, postHeaders, paramStr);
			logger.debug("HND response = " + result);
			resultMap = JSONTransformUtil.toObject(result, HashMap.class);
			if (resultMap != null) {
				retDto.setStatus(resultMap.containsKey("status") ? (String) resultMap.get("status") : "");
				retDto.setErrMsg(resultMap.containsKey("errmsg") ? (String) resultMap.get("errmsg") : "");
				if (resultMap.containsKey("detail")) {
					tmpMapList = (List<Map<String, String>>) resultMap.get("detail");
					if (tmpMapList != null && tmpMapList.size() > 0) {
						for (Map<String, String> tmpMap : tmpMapList) {
							hisModel = new BuyerFinanceHistoryDTO();
							hisModel.setErpCode(tmpMap.get("cusNo"));
							hisModel.setAmount(new BigDecimal(tmpMap.get("amount")));
							returnList.add(hisModel);
						}
						retDto.setReturnList(returnList);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return retDto;
	}

	/**
	 * JLERP系统通信
	 * 
	 * @param connectUrl
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> sendJlErpJson(String connectUrl, String xml) throws Exception {
		// 获取Servlet连接并设置请求的方法
		Map<String, Object> retMap = null;
		String path = connectUrl;
		String url = path + "?" + xml;
		URL realURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) realURL.openConnection();
		logger.debug("HZF connectUrl:" + url);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-type", "text/xml");
		connection.setRequestMethod("GET");

		// 从连接的输入流中取得回执信息
		InputStream inputStream = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader bufreader = new BufferedReader(isr);
		String xmlString = "";
		int c;
		while ((c = bufreader.read()) != -1) {
			xmlString += (char) c;
		}
		logger.debug("HZF response = " + xmlString);
		connection.disconnect();
		retMap = JSONTransformUtil.toObject(xmlString, HashMap.class);
		return retMap;
	}

	/**
	 * 江苏银行系统通信
	 * 
	 * @param url
	 * @param nvps
	 * @return
	 * @throws Exception
	 */
	private String sendJsyhPost(String url, List<NameValuePair> nvps) throws Exception {
		HttpPost httpPost = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		Header[] headers = null;
		HttpEntity entity = null;
		String msginfo = "";
		StringBuffer sb = new StringBuffer();

		try {
			httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			response = httpclient.execute(httpPost);

			headers = response.getAllHeaders();
			entity = response.getEntity();
			if (entity != null) {
				msginfo = EntityUtils.toString(entity, "utf-8");
			}
			sb = new StringBuffer("\r\n---HTTP report --------------------------------------\r\n");
			sb.append("HTTP URL:").append(url).append("\r\n");
			sb.append("Sent Data:").append(nvps.toString()).append("\r\n");
			sb.append("\r\n");
			sb.append(response.getStatusLine().toString()).append("\r\n");
			sb.append(httpPost.toString()).append("\r\n");
			for (int i = 0; i < headers.length; i++) {
				sb.append(headers[i].getName()).append(":").append(headers[i].getValue()).append("\r\n");
			}
			sb.append("Return Data:").append(msginfo).append("\r\n");
			sb.append("-----------------------------------------------------\r\n");
			logger.debug(sb.toString());
		} finally {
			if (entity != null) {
				EntityUtils.consume(entity);
			}
			if (response != null) {
				response.close();
			}
		}
		return msginfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#upgradeHTDUserGrade(cn.htd
	 * .membercenter.dto.BuyerGradeInfoDTO)
	 */
	@Override
	public void upgradeHTDUserGrade(MemberImportSuccInfoDTO memberImportSuccInfoDTO, BuyerGradeInfoDTO memberGradeModel,
			Date targetDt) {
		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();

		Map<String, List<BuyerFinanceExpDTO>> userDailyAmountMap = null;
		try {
			userDailyAmountMap = getHTDUserDailyAmount(memberGradeModel, targetDt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<BuyerFinanceExpDTO> userDailyAmountList = null;
		userDailyAmountList = userDailyAmountMap.get("MONTH");

		// 订单金额应该不用重算
		// calculateHTDUserOrderAmount(memberGradeModel, targetDt);

		HTDUserGradeExpDto financeExpDto = null;

		financeExpDto = calculateUserFinanceExp(orderScoreModelList, financeScoreModelList, mallWeight, financeWeight,
				memberGradeModel, userDailyAmountMap);

		List<BuyerPointHistory> pointChgHisList = null;

		pointChgHisList = makeUserFinancePointHis(orderScoreModelList, financeScoreModelList, mallWeight, financeWeight,
				memberGradeModel, userDailyAmountList, financeExpDto, targetDt);

		HTDUserGradeExpDto orderExpDto = calculateUserOrderExp(orderScoreModelList, financeScoreModelList, mallWeight,
				financeWeight, memberGradeModel, BigDecimal.ZERO);

		BuyerGradeIntervalDTO newBuyerGradeInfoDTO = calculateHTDUserGrade(orderScoreModelList, financeScoreModelList,
				pointList, mallWeight, financeWeight, memberGradeModel, orderExpDto, financeExpDto);
		memberGradeModel = upgradeHTDMemberUserGrade(orderScoreModelList, financeScoreModelList, pointList, mallWeight,
				financeWeight, memberGradeModel, newBuyerGradeInfoDTO);

		logger.debug("*****update memberGradeModel start "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		memberGradeDAO.updateByPrimaryKeySelective(memberGradeModel);
		logger.debug("*****update memberGradeModel end "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		if (userDailyAmountList != null && userDailyAmountList.size() > 0) {
			logger.debug("*****update userDailyAmountModel start "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
			for (BuyerFinanceExpDTO buyerFinanceExpDTO : userDailyAmountList) {
				if (buyerFinanceExpDTO.getId() == null) {
					memberGradeDAO.saveBuyerFinanceExp(buyerFinanceExpDTO);
				} else {
					memberGradeDAO.updateBuyerFinanceExp(buyerFinanceExpDTO);
				}
			}
			logger.debug("*****update userDailyAmountModel end "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		}
		if (pointChgHisList != null && pointChgHisList.size() > 0) {
			logger.debug("*****update userPointChgHisModel start "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
			for (BuyerPointHistory buyerPointHistory : pointChgHisList) {
				memberGradeDAO.insertPointHistoryInfo(buyerPointHistory);
			}
			logger.debug("*****update userPointChgHisModel end "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		}
	}

	/**
	 * @param memberGradeModel
	 * @param targetDt
	 * @return
	 * @throws ParseException
	 */
	private Map<String, List<BuyerFinanceExpDTO>> getHTDUserDailyAmount(BuyerGradeInfoDTO memberGradeModel, Date date)
			throws ParseException {
		List<String> pointTypeList = new ArrayList<String>(Arrays.asList("HND", "HXD", "JSYH", "HZF"));
		List<BuyerFinanceHistoryDTO> hisModelList = null;
		List<BuyerFinanceExpDTO> dailyAmountList = new ArrayList<BuyerFinanceExpDTO>();
		Map<String, List<BuyerFinanceExpDTO>> dailyAmountMap = new HashMap<String, List<BuyerFinanceExpDTO>>();
		BuyerFinanceExpDTO dailyAmountModel = null;
		BuyerFinanceExpDTO newDailyAmountModel = null;
		Date endDt = null;
		Date startDt = null;
		int dayInterval = 0;
		boolean isSkipFlg = false;
		BigDecimal tmpDailyAmount = BigDecimal.ZERO;
		BigDecimal tmpAverageDailyAmount = BigDecimal.ZERO;
		BigDecimal totalAverageDailyAmount = BigDecimal.ZERO;
		Map<String, Date> targetMap = new HashMap<String, Date>();
		Date tmpDate = null;
		String type = "";
		int cnt = 0;
		boolean hasErpCd = true;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, 1);
			tmpDate = sdf.parse(sdf.format(cal.getTime()));
			targetMap.put("MONTH", tmpDate);

			cal.set(Calendar.MONTH, 0);
			tmpDate = sdf.parse(sdf.format(cal.getTime()));
			targetMap.put("YEAR", tmpDate);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator<String> it = targetMap.keySet().iterator();
		MemberBaseInfoDTO member = null;
		try {
			member = memberBaseOperationDAO.getMemberbaseById(memberGradeModel.getBuyerId(), GlobalConstant.IS_BUYER);// 查询会员基本信息
		} catch (Exception e) {
		}

		if (member == null || StringUtils.isEmpty(member.getCompanyCode())) {
			hasErpCd = false;
			logger.warn("memberId=" + memberGradeModel.getBuyerId() + " ERP code is null");
		}
		while (it.hasNext()) {
			type = it.next();
			tmpDate = targetMap.get(type);
			dailyAmountList = new ArrayList<BuyerFinanceExpDTO>();
			for (String pointType : pointTypeList) {
				if (hasErpCd) {
					BuyerFinanceHistoryDTO tmpModel = new BuyerFinanceHistoryDTO();
					tmpModel.setErpCode(member.getCompanyCode());
					tmpModel.setFinanceType(pointType);
					tmpModel.setTargetDate(date);
					hisModelList = memberGradeDAO.getHTDUserDailyFinanceHis(tmpModel);
				} else {
					hisModelList = null;
				}
				dailyAmountModel = memberGradeDAO.getHTDUserDailyAmount(memberGradeModel.getBuyerId(), pointType);
				if (hisModelList != null && hisModelList.size() > 0) {
					totalAverageDailyAmount = BigDecimal.ZERO;
					endDt = date;
					isSkipFlg = false;
					cnt = 0;
					for (BuyerFinanceHistoryDTO financeHisModel : hisModelList) {
						cnt++;
						startDt = financeHisModel.getTargetDate();
						if (!startDt.after(tmpDate)) {
							isSkipFlg = true;
							startDt = tmpDate;
						}
						tmpDailyAmount = financeHisModel.getAmount() == null ? BigDecimal.ZERO
								: financeHisModel.getAmount();
						tmpDailyAmount = BigDecimal.ZERO.compareTo(tmpDailyAmount) > 0 ? BigDecimal.ZERO
								: tmpDailyAmount;
						try {
							dayInterval = daysBetween(startDt, endDt);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (cnt == 1) {
							dayInterval += 1;
						}
						tmpAverageDailyAmount = tmpDailyAmount.multiply(new BigDecimal(dayInterval)).setScale(5,
								RoundingMode.HALF_UP);
						tmpAverageDailyAmount = tmpAverageDailyAmount.divide(new BigDecimal(365), 5,
								RoundingMode.HALF_UP);
						totalAverageDailyAmount = totalAverageDailyAmount.add(tmpAverageDailyAmount);
						endDt = startDt;
						if (isSkipFlg) {
							break;
						}
					}
					totalAverageDailyAmount = BigDecimal.ZERO.compareTo(totalAverageDailyAmount) > 0 ? BigDecimal.ZERO
							: totalAverageDailyAmount;
					if ("MONTH".equals(type)) {
						if (dailyAmountModel != null) {
							dailyAmountModel.setDailyAmountAvg(totalAverageDailyAmount);
							dailyAmountList.add(dailyAmountModel);
						} else {
							newDailyAmountModel = new BuyerFinanceExpDTO();
							newDailyAmountModel.setBuyerId(memberGradeModel.getBuyerId());
							newDailyAmountModel.setFinanceType(pointType);
							newDailyAmountModel.setDailyAmountAvg(totalAverageDailyAmount);
							newDailyAmountModel.setFinanceExp(BigDecimal.ZERO);
							dailyAmountList.add(newDailyAmountModel);
						}
					} else {
						newDailyAmountModel = new BuyerFinanceExpDTO();
						newDailyAmountModel.setBuyerId(memberGradeModel.getBuyerId());
						newDailyAmountModel.setFinanceType(pointType);
						newDailyAmountModel.setDailyAmountAvg(totalAverageDailyAmount);
						newDailyAmountModel.setFinanceExp(BigDecimal.ZERO);
						dailyAmountList.add(newDailyAmountModel);
					}
				} else {
					if ("MONTH".equals(type)) {
						if (dailyAmountModel != null) {
							dailyAmountModel.setDailyAmountAvg(BigDecimal.ZERO);
							dailyAmountList.add(dailyAmountModel);
						}
					}
				}
			}
			dailyAmountMap.put(type, dailyAmountList);
		}
		return dailyAmountMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#downgradeHTDUserGrade(cn.
	 * htd.membercenter.dto.BuyerGradeInfoDTO)
	 */
	@Override
	public void downgradeHTDUserGrade(MemberImportSuccInfoDTO memberImportSuccInfoDTO,
			BuyerGradeInfoDTO memberGradeModel) {
		List<String> pointTypeList = new ArrayList<String>(Arrays.asList("HND", "HXD", "JSYH", "HZF"));

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();

		String userGrade = memberGradeModel.getBuyerGrade();
		if (userGrade == null || userGrade.equals("0")) {
			userGrade = "1";
		}
		String pointUserGrade = memberGradeModel.getPointGrade().toString();
		if (pointUserGrade == null || pointUserGrade.equals("0")) {
			pointUserGrade = "1";
		}
		Long userFromPoint = null;
		Long pointFromPoint = null;
		Double userFromPointDbl = null;
		Double pointFromPointDbl = null;
		BigDecimal lowestPoint = null;
		BigDecimal monthPoint = memberGradeModel.getMonthExp() == null ? BigDecimal.ZERO
				: memberGradeModel.getMonthExp();
		boolean isSkipFlg = false;
		boolean isVip = memberGradeModel.getIsVip().intValue() == 0 ? false
				: memberGradeModel.getIsVip().intValue() == 1;

		Long newUserGrade = null;
		BuyerGradeIntervalDTO userPointModel = null;
		BuyerGradeIntervalDTO pointGradeModel = null;
		BuyerGradeChangeHistory userGradeChgHisModel = null;
		if (!isVip && !userGrade.equals(pointUserGrade)) {
			Collections.reverse(pointList);
			for (BuyerGradeIntervalDTO gradePointModel : pointList) {
				if (isSkipFlg) {
					newUserGrade = gradePointModel.getBuyerLevel();
					break;
				}
				if (gradePointModel.getBuyerLevel().toString().equals(userGrade)) {
					userPointModel = gradePointModel;
					lowestPoint = new BigDecimal(gradePointModel.getLowestPoint());
					isSkipFlg = true;
				}
			}

			for (BuyerGradeIntervalDTO gradePointModel : pointList) {
				if (gradePointModel.getBuyerLevel().toString().equals(pointUserGrade)) {
					pointGradeModel = gradePointModel;
					break;
				}
			}
			if (userPointModel != null) {
				userFromPoint = userPointModel.getFromScore();
				userFromPointDbl = userFromPoint == null ? new Double(-1) : userFromPoint.doubleValue();
				pointFromPoint = pointGradeModel.getFromScore();
				pointFromPointDbl = pointFromPoint == null ? new Double(-1) : pointFromPoint.doubleValue();
				if (userFromPointDbl > pointFromPointDbl) {
					if (newUserGrade != null && lowestPoint != null && monthPoint.compareTo(lowestPoint) < 0) {
						memberGradeModel.setBuyerGrade(newUserGrade.toString());
						memberGradeModel.setIsUpgrade("0");
						memberGradeModel.setIsSbUpgrade("0");
						userGradeChgHisModel = new BuyerGradeChangeHistory();
						userGradeChgHisModel.setBuyerId(memberImportSuccInfoDTO.getMemberId());
						userGradeChgHisModel.setChangeTime(new Date());
						userGradeChgHisModel.setIsUpgrade("0");
						userGradeChgHisModel.setChangeGrade(newUserGrade.toString());
						userGradeChgHisModel.setOperateId("1");
						userGradeChgHisModel.setOperateName("SYS");
						userGradeChgHisModel.setAfterGrade("");
					}
				}
			}
		}
		memberGradeModel.setMonthExp(BigDecimal.ZERO);
		memberGradeModel.setMonthOrderAmount(BigDecimal.ZERO);
		memberGradeModel.setMonthFinanceAvg(BigDecimal.ZERO);
		memberGradeModel.setMonthOrderExp(BigDecimal.ZERO);
		memberGradeModel.setMonthFinanceExp(BigDecimal.ZERO);
		logger.debug("*****update memberGradeModel start "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		memberGradeDAO.updateByPrimaryKeySelective(memberGradeModel);
		logger.debug("*****update memberGradeModel end "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		if (userGradeChgHisModel != null) {
			logger.debug("*****update userGradeChgHisModel start "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
			memberGradeDAO.insertMemberGradeHistoryInfo(userGradeChgHisModel);
			logger.debug("*****update userGradeChgHisModel end "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		}
		logger.debug("*****update userDailyAmountModel start "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");
		for (String pointType : pointTypeList) {
			BuyerFinanceExpDTO dailyAmountModel = memberGradeDAO.getHTDUserDailyAmount(memberGradeModel.getBuyerId(),
					pointType);
			if (dailyAmountModel != null) {
				dailyAmountModel.setDailyAmountAvg(BigDecimal.ZERO);
				dailyAmountModel.setFinanceExp(BigDecimal.ZERO);
				if (dailyAmountModel.getId() == null) {
					memberGradeDAO.saveBuyerFinanceExp(dailyAmountModel);
				} else {
					memberGradeDAO.updateBuyerFinanceExp(dailyAmountModel);
				}
			}
		}
		logger.debug("*****update userDailyAmountModel end "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "*****");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#upgradeHTDUserGradeYearly(
	 * cn.htd.membercenter.dto.BuyerGradeInfoDTO)
	 */
	@Override
	public void upgradeHTDUserGradeYearly(MemberImportSuccInfoDTO memberImportSuccInfoDTO,
			BuyerGradeInfoDTO memberGradeModel) {

		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();
		Long pointGrade = 1l;
		BigDecimal levelPoint = BigDecimal.ZERO;
		if (pointList != null && pointList.size() > 0) {
			pointGrade = pointList.get(0).getBuyerLevel();
		}
		Long orderLevel = 0l;
		if (orderScoreModelList != null && orderScoreModelList.size() > 0) {
			orderLevel = orderScoreModelList.get(0).getId();
		}
		Long financeLevel = 0l;
		if (financeScoreModelList != null && financeScoreModelList.size() > 0) {
			financeLevel = financeScoreModelList.get(0).getId();
		}
		memberGradeModel.setPointGrade(pointGrade);
		memberGradeModel.setYearExp(BigDecimal.ZERO);
		memberGradeModel.setLevelExp(levelPoint);
		memberGradeModel.setYearOrderAmount(BigDecimal.ZERO);

		memberGradeModel.setYearOrderLevel(orderLevel);
		memberGradeModel.setYearFinanceAvg(BigDecimal.ZERO);

		memberGradeModel.setYearFinanceLevel(financeLevel);
		memberGradeDAO.updateByPrimaryKeySelective(memberGradeModel);
	}

	/**
	 * 生成金融产品经验值履历
	 * 
	 * @param defaultDto
	 * @param memberGradeModel
	 * @param financeList
	 * @param expDto
	 * @param targetDt
	 * @return
	 */
	private List<BuyerPointHistory> makeUserFinancePointHis(List<BuyerScoreIntervalDTO> orderScoreModelList,
			List<BuyerScoreIntervalDTO> financeScoreModelList, BigDecimal mallWeight, BigDecimal fWeight,
			BuyerGradeInfoDTO memberGradeModel, List<BuyerFinanceExpDTO> financeList, HTDUserGradeExpDto expDto,
			Date targetDt) {

		List<BuyerPointHistory> userPointHisList = new ArrayList<BuyerPointHistory>();

		BuyerScoreIntervalDTO financeModel = expDto.getFinanceMonthModel();

		BuyerPointHistory userPointHisModel = null;

		boolean isVip = memberGradeModel.getIsVip().intValue() == 0 ? false
				: memberGradeModel.getIsVip().intValue() == 1;

		BigDecimal amountLimit = null;
		BigDecimal score = null;
		BigDecimal tmpAmount = BigDecimal.ZERO;
		BigDecimal nowExp = BigDecimal.ZERO;
		BigDecimal nowPoint = BigDecimal.ZERO;
		BigDecimal lastPoint = BigDecimal.ZERO;
		BigDecimal plusPoint = BigDecimal.ZERO;
		BigDecimal decimalAll = BigDecimal.ZERO;

		if (financeModel != null) {
			amountLimit = financeModel.getToAmount().compareTo(BigDecimal.ZERO) == 0 ? null
					: financeModel.getToAmount().multiply(new BigDecimal(10000));
			score = financeModel.getScore();
			for (BuyerFinanceExpDTO dailyAmountModel : financeList) {
				tmpAmount = dailyAmountModel.getDailyAmountAvg();
				lastPoint = dailyAmountModel.getFinanceExp() == null ? BigDecimal.ZERO
						: dailyAmountModel.getFinanceExp();
				nowExp = score.multiply(fWeight);
				if (amountLimit != null) {
					nowExp = nowExp.multiply(tmpAmount).divide(amountLimit, 5, RoundingMode.HALF_UP);
				}
				nowPoint = nowExp.setScale(0, RoundingMode.DOWN);
				decimalAll = decimalAll.add(nowExp.subtract(nowPoint));
				if (BigDecimal.ONE.compareTo(decimalAll.abs()) <= 0) {
					nowPoint = nowPoint.add(decimalAll.setScale(0, RoundingMode.DOWN));
					decimalAll = decimalAll.subtract(decimalAll.setScale(0, RoundingMode.DOWN));
				}
				plusPoint = nowPoint.subtract(lastPoint);
				if (BigDecimal.ZERO.compareTo(plusPoint) != 0) {
					dailyAmountModel.setFinanceExp(nowPoint);
					userPointHisModel = new BuyerPointHistory();
					userPointHisModel.setBuyerId(memberGradeModel.getBuyerId());
					String type = "";
					for (GradeTypeEnum gradeEnum : GradeTypeEnum.values()) {
						if (gradeEnum.toString().equals(dailyAmountModel.getFinanceType())) {
							type = gradeEnum.getValue();
							break;
						}
					}
					userPointHisModel.setPointType(type);
					userPointHisModel.setProvideTime(targetDt);
					userPointHisModel.setOrderId("");
					userPointHisModel.setProvidePoint(plusPoint);
					userPointHisModel.setIsVisible(new Byte("1"));
					if (isVip) {
						userPointHisModel.setIsVisible(new Byte("0"));
					}
					// TODO 登陆人
					userPointHisModel.setCreateId(0l);
					userPointHisModel.setCreateName("SYS");
					userPointHisModel.setModifyId(0l);
					userPointHisModel.setModifyName("SYS");
					userPointHisList.add(userPointHisModel);
				}
			}
		}
		return userPointHisList;
	}

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hisDtStr = sdf.format(new Date());
		String newDtStr = sdf.format(new Date());
		if (hisDtStr.equals(newDtStr)) {
			System.out.println("111");
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param mdate
	 * @param ndate
	 * @return 相差天数
	 * @throws ParseException
	 */
	private int daysBetween(Date mdate, Date ndate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mdate = sdf.parse(sdf.format(mdate));
		ndate = sdf.parse(sdf.format(ndate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(mdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(ndate);
		long time2 = cal.getTimeInMillis();
		long between_days = Math.abs(time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberGradeService#updateIsUpgrade(long)
	 */
	@Override
	public ExecuteResult<Boolean> updateIsUpgrade(long memberId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		BuyerGradeInfoDTO member = memberGradeDAO.getHTDMemberGrade(memberId);
		String memberGrade = member.getBuyerGrade();
		if (StringUtils.isNotBlank(memberGrade)) {
			try {
				BuyerGradeInfoDTO memberBaseDTO = new BuyerGradeInfoDTO();
				memberBaseDTO.setBuyerId(memberId);
				memberBaseDTO.setIsUpgrade("");
				memberGradeDAO.updateByPrimaryKeySelective(memberBaseDTO);
				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberGradeServiceImpl----->updateIsUpgrade=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		} else {
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage("更新升级标志出错");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberGradeService#selectMemberByGrade(java.
	 * lang.String)
	 */
	@Override
	public ExecuteResult<List<MemberGradeDTO>> selectMemberByGrade(String buyerGrade) {
		ExecuteResult<List<MemberGradeDTO>> rs = new ExecuteResult<List<MemberGradeDTO>>();
		try {
			List<MemberGradeDTO> list = memberGradeDAO.selectMemberByGrade(buyerGrade);
			if (list != null) {
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("没有查询到当前会员信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setResultMessage("error");
			logger.error("MemberGradeServiceImpl=======>selectMemberByGrade" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> modifyMemberGradeAndPackageTypeById(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String memberId = memberBaseDTO.getMemberId();
		if (StringUtils.isNotBlank(memberId)) {
			MemberGradeDTO member = memberGradeDAO.queryMemberGradeInfo(memberBaseDTO);
			try {
				if (StringUtils.isNotBlank(memberBaseDTO.getMemberPackageType())) {
					memberBaseDTO.setPackageUpdateTime(new Date());

					if (memberBaseDTO.getPackageActiveStartTime() != null
							&& memberBaseDTO.getPackageActiveEndTime() == null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(memberBaseDTO.getPackageActiveStartTime());
						// 开始时间设置当前时间+1天
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						memberBaseDTO.setPackageActiveStartTime(calendar.getTime());
						Date currentTime = calendar.getTime();
						calendar.setTime(currentTime);
						// 结束时间设置开始时间+1年
						calendar.add(Calendar.YEAR, 1);
						memberBaseDTO.setPackageActiveEndTime(calendar.getTime());
					}
					Date packageActiveEndTime = member.getPackageActiveEndTime();
					if (packageActiveEndTime != null && packageActiveEndTime.getTime() < new Date().getTime()) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(packageActiveEndTime);
						calendar.add(Calendar.YEAR, 1);
						memberBaseDTO.setPackageActiveEndTime(calendar.getTime());
					}
				}
				if (memberBaseDTO.getIsVip() != null && memberBaseDTO.getIsVip().equals("1")
						&& !memberBaseDTO.getIsVip().equals(member.getIsVip())) {
					// 第二天再生效
					if (StringUtils.isNotEmpty(memberBaseDTO.getBuyerGrade())) {
						memberBaseDTO.setBuyerGrade("6");
					}
				}

				memberGradeDAO.modifyMemberGradeAndPackageTypeById(memberBaseDTO);

				if (new Integer(member.getBuyerGrade()).intValue() != new Integer(memberBaseDTO.getBuyerGrade())
						.intValue()) {
					BuyerGradeInfoDTO memberGradeModel = new BuyerGradeInfoDTO();
					memberGradeModel.setBuyerId(new Long(memberBaseDTO.getMemberId()));
					if (new Integer(member.getBuyerGrade()).intValue() > new Integer(memberBaseDTO.getBuyerGrade())
							.intValue()) {
						memberGradeModel.setIsUpgrade("0");
						memberGradeModel.setIsSbUpgrade("0");
					} else {
						memberGradeModel.setIsUpgrade("1");
						memberGradeModel.setIsSbUpgrade("1");
					}
					memberGradeDAO.updateByPrimaryKeySelective(memberGradeModel);
				}

				// if (memberBaseDTO.getIsVip() != null &&
				// !memberBaseDTO.getIsVip().equals(member.getIsVip())) {
				// BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
				// bgch.setBuyerId(memberBaseDTO.getMemberId());
				// bgch.setAfterGrade(GlobalConstant.MEMBER_SCORE_SET_OPERATE);
				// bgch.setChangeGrade("");
				// bgch.setIsUpgrade(memberBaseDTO.getIsVip());
				// bgch.setOperateId(memberBaseDTO.getOperateId());
				// bgch.setOperateName(memberBaseDTO.getOperateName());
				// memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
				// }

				BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
				bgch.setBuyerId(memberBaseDTO.getMemberId());

				if (memberBaseDTO.getBuyerGrade() != null
						&& !memberBaseDTO.getBuyerGrade().equals(member.getBuyerGrade())) {
					bgch.setAfterGrade(GlobalConstant.MEMBER_SCORE_RULE_OPERATE);
				} else if (memberBaseDTO.getIsVip() != null && !memberBaseDTO.getIsVip().equals(member.getIsVip())) {
					bgch.setAfterGrade(GlobalConstant.MEMBER_SCORE_SET_OPERATE);
				} else if (memberBaseDTO.getMemberPackageType() != null
						&& !memberBaseDTO.getMemberPackageType().equals(member.getMemberPackageType())) {
					bgch.setAfterGrade(GlobalConstant.MEMBER_PACKAGE_TYPE_OPERATE);
				} else if (memberBaseDTO.getPackageActiveStartTime() != null
						&& memberBaseDTO.getPackageActiveEndTime() != null
						&& (!memberBaseDTO.getPackageActiveStartTime().equals(member.getPackageActiveStartTime())
								|| !memberBaseDTO.getPackageActiveEndTime().equals(member.getPackageActiveEndTime()))) {
					bgch.setAfterGrade(GlobalConstant.MEMBER_PACKAGE_TIME_OPERATE);
				}else{
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.setResultMessage("没有修改任何内容，请确认");
					return rs;
				}

				bgch.setChangeGrade(memberBaseDTO.getBuyerGrade());
				if (Integer.valueOf(memberBaseDTO.getBuyerGrade()) > Integer.valueOf(member.getBuyerGrade())) {
					bgch.setIsUpgrade("1");
				} else if (Integer.valueOf(memberBaseDTO.getBuyerGrade()) < Integer.valueOf(member.getBuyerGrade())) {
					bgch.setIsUpgrade("0");
				} else {
					bgch.setIsUpgrade("");
				}
				bgch.setOperateId(memberBaseDTO.getOperateId());
				bgch.setOperateName(memberBaseDTO.getOperateName());
				memberGradeDAO.insertMemberGradeHistoryInfo(bgch);

				// Date defaultDate = DateUtils.parse("0000-00-00 00:00:00",
				// "yyyy-MM-dd HH:mm:ss");
				// if (memberBaseDTO.getPackageActiveStartTime() == null) {
				// memberBaseDTO.setPackageActiveStartTime(defaultDate);
				// }
				// if (memberBaseDTO.getPackageActiveEndTime() == null) {
				// memberBaseDTO.setPackageActiveEndTime(defaultDate);
				// }

				// if (memberBaseDTO.getMemberPackageType() != null
				// &&
				// !memberBaseDTO.getMemberPackageType().equals(member.getMemberPackageType()))
				// {
				// BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
				// bgch.setBuyerId(memberBaseDTO.getMemberId());
				// bgch.setAfterGrade(GlobalConstant.MEMBER_PACKAGE_TYPE_OPERATE);
				// bgch.setChangeGrade("");
				// bgch.setIsUpgrade("");
				// bgch.setOperateId(memberBaseDTO.getOperateId());
				// bgch.setOperateName(memberBaseDTO.getOperateName());
				// memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
				// }
				// if (memberBaseDTO.getPackageActiveStartTime() != null &&
				// memberBaseDTO.getPackageActiveEndTime() != null
				// &&
				// !memberBaseDTO.getPackageActiveStartTime().equals(member.getPackageActiveStartTime())
				// ||
				// !memberBaseDTO.getPackageActiveEndTime().equals(member.getPackageActiveEndTime()))
				// {
				// BuyerGradeChangeHistory bgch = new BuyerGradeChangeHistory();
				// bgch.setBuyerId(memberBaseDTO.getMemberId());
				// bgch.setAfterGrade(GlobalConstant.MEMBER_PACKAGE_TIME_OPERATE);
				// bgch.setChangeGrade("");
				// bgch.setIsUpgrade("");
				// bgch.setOperateId(memberBaseDTO.getOperateId());
				// bgch.setOperateName(memberBaseDTO.getOperateName());
				// memberGradeDAO.insertMemberGradeHistoryInfo(bgch);
				// }
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				logger.error("MemberGradeServiceImpl=======>modifyMemberGradeAndPackageTypeById" + e);
			}
		} else {
			rs.setResult(false);
			rs.setResultMessage("参数缺少会员编码信息");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberGradeService#insertGrade(cn.htd.
	 * membercenter.dto.BuyerGradeInfoDTO)
	 */
	@Override
	public ExecuteResult<Boolean> insertGrade(BuyerGradeInfoDTO dto) {
		// if (dto.getLevelExp().compareTo(BigDecimal.ZERO) == 0) {
		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);
		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();
		if (orderScoreModelList != null && orderScoreModelList.size() > 0) {
			dto.setYearOrderLevel(orderScoreModelList.get(0).getId());
		}
		if (financeScoreModelList != null && financeScoreModelList.size() > 0) {
			dto.setYearFinanceLevel(financeScoreModelList.get(0).getId());
		}
		if (pointList != null && pointList.size() > 0) {
			dto.setLevelExp(new BigDecimal(pointList.get(0).getToScore()));
		}
		// }
		memberGradeDAO.insertGrade(dto);
		return null;
	}

	/**
	 * 取得会员保底经验值的显示信息
	 * 
	 * @param memberGradeModel
	 * @param defaultUserGradeDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public HTDUserLowestShowDto getHTDUserGradeLowestInfo(Long memberId) throws Exception {

		// 会员最新数据
		BuyerGradeInfoDTO buyerGradeInfoDTO = memberGradeDAO.getHTDMemberGrade(memberId);

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();

		String userGradeLevel = null;
		String pointGradeLevel = null;
		BuyerGradeIntervalDTO userPointModel = null;
		BuyerGradeIntervalDTO pointGradeModel = null;
		List<BuyerGradeIntervalDTO> userGradePointList = null;
		BigDecimal userFromPoint = null;
		BigDecimal pointFromPoint = null;
		Double lowestPoint = null;
		Double monthPoint = null;
		Double userFromPointDbl = null;
		Double pointFromPointDbl = null;
		Double pointDistance = null;
		boolean isVip = buyerGradeInfoDTO.getIsVip().intValue() == 1 ? true : false;
		HTDUserLowestShowDto showDto = new HTDUserLowestShowDto();
		showDto.setShowFlg(false);
		if (isVip) {
			return showDto;
		}
		userGradePointList = pointList;
		userGradeLevel = buyerGradeInfoDTO.getBuyerGrade();
		pointGradeLevel = buyerGradeInfoDTO.getPointGrade().toString();
		if (userGradeLevel != null) {
			for (BuyerGradeIntervalDTO gradePointModel : userGradePointList) {
				if (gradePointModel.getBuyerLevel().toString().equals(userGradeLevel)) {
					userPointModel = gradePointModel;
					break;
				}
			}
		}
		if (pointGradeLevel != null) {
			for (BuyerGradeIntervalDTO gradePointModel : userGradePointList) {
				if (gradePointModel.getBuyerLevel().toString().equals(pointGradeLevel)) {
					pointGradeModel = gradePointModel;
					break;
				}
			}
		}
		if (userPointModel != null) {
			userFromPoint = new BigDecimal(userPointModel.getFromScore());
			userFromPointDbl = userFromPoint == null ? new Double(-1) : userFromPoint.doubleValue();
			pointFromPoint = new BigDecimal(pointGradeModel.getFromScore());
			pointFromPointDbl = pointFromPoint == null ? new Double(-1) : pointFromPoint.doubleValue();
			if (userFromPointDbl > pointFromPointDbl) {
				showDto.setShowFlg(true);
				lowestPoint = userPointModel.getLowestPoint().compareTo(0l) == 0 ? null
						: userPointModel.getLowestPoint().doubleValue();
				if (lowestPoint != null) {
					showDto.setLowestPoint(lowestPoint);
					monthPoint = buyerGradeInfoDTO.getMonthExp().doubleValue();
					pointDistance = lowestPoint - monthPoint;
					pointDistance = pointDistance >= 0 ? pointDistance : 0;
					showDto.setPointDistance(pointDistance);
				}
			}
		}
		return showDto;
	}

	/**
	 * 取得会员等级定义
	 * 
	 * @param memberGradeModel
	 * @param defaultUserGradeDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HTDUserUpgradeDistanceDto> calculateHTDUserUpgradePath(Long memberId) throws Exception {

		// 会员最新数据
		BuyerGradeInfoDTO memberGradeModel = memberGradeDAO.getHTDMemberGrade(memberId);

		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);

		List<BuyerGradeIntervalDTO> pointList = memberGradeDAO.queryBuyerGradeList();

		boolean isVip = memberGradeModel.getIsVip().intValue() == 1 ? true : false;
		if (isVip) {
			return null;
		}

		HTDUserUpgradeDto[][] upgradePathArr = getHTDUserGradeMatrix();

		BuyerGradeIntervalDTO nextUserGradePointModel = getHTDMemberNextGrade(memberGradeModel);
		List<HTDUserUpgradePathDto> pathList = null;
		List<HTDUserUpgradeDistanceDto> distanceList = null;
		Long nextUserGrade = null;

		if (nextUserGradePointModel == null) {
			return null;
		}
		nextUserGrade = nextUserGradePointModel.getBuyerLevel();
		pathList = getGradeUpgradePath(memberGradeModel, upgradePathArr, nextUserGrade);
		distanceList = getGradeUpgradeDistance(memberGradeModel, nextUserGradePointModel, pathList);
		return distanceList;
	}

	/**
	 * 取得需要升级的商品交易金额及金融产品余额差距
	 * 
	 * @param memberGradeModel
	 * @param defaultDto
	 * @param nextUserGradePointModel
	 * @param pathList
	 * @return
	 */
	private List<HTDUserUpgradeDistanceDto> getGradeUpgradeDistance(BuyerGradeInfoDTO memberGradeModel,
			BuyerGradeIntervalDTO nextUserGradePointModel, List<HTDUserUpgradePathDto> pathList) {

		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);

		List<HTDUserUpgradeDistanceDto> distanceList = null;
		HTDUserUpgradeDistanceDto distanceDto = null;
		HTDUserUpgradeDto userUpgradeDto = null;
		Long orderModel = memberGradeModel.getYearOrderLevel() == 0l ? orderScoreModelList.get(0).getId()
				: memberGradeModel.getYearOrderLevel();
		Long financeModel = memberGradeModel.getYearFinanceLevel() == 0l ? financeScoreModelList.get(0).getId()
				: memberGradeModel.getYearFinanceLevel();
		BuyerScoreIntervalDTO tmpOrderModel = null;
		BuyerScoreIntervalDTO tmpFinanceModel = null;
		BigDecimal orderAmountDistance = BigDecimal.ZERO;
		BigDecimal financeAmountDistance = BigDecimal.ZERO;
		BigDecimal orderAmount = BigDecimal.ZERO;
		BigDecimal financeAmount = BigDecimal.ZERO;
		boolean needAddFlg = false;
		long orderPk1 = 0;
		long orderPk2 = 0;
		long financePk1 = 0;
		long financePk2 = 0;

		if (pathList != null && pathList.size() > 0) {
			distanceList = new ArrayList<HTDUserUpgradeDistanceDto>();
			for (HTDUserUpgradePathDto upgrdePathDto : pathList) {
				userUpgradeDto = upgrdePathDto.getUserUpgradeDto();
				tmpOrderModel = userUpgradeDto.getOrderModel();
				tmpFinanceModel = userUpgradeDto.getFinanceModel();
				needAddFlg = false;
				orderPk1 = tmpOrderModel.getId();
				orderPk2 = orderModel;
				financePk1 = tmpFinanceModel.getId();
				financePk2 = financeModel;
				if (orderPk1 != orderPk2 || financePk1 != financePk2) {
					distanceDto = new HTDUserUpgradeDistanceDto();
					distanceDto.setNextUserGradePointModel(nextUserGradePointModel);
					distanceDto.setNextUserGrade(nextUserGradePointModel.getBuyerLevel());
					if (orderPk1 != orderPk2) {
						orderAmountDistance = tmpOrderModel.getFromAmount() == null ? BigDecimal.ZERO
								: tmpOrderModel.getFromAmount();
						orderAmount = memberGradeModel.getYearOrderAmount() == null ? BigDecimal.ZERO
								: memberGradeModel.getYearOrderAmount().divide(new BigDecimal(10000), 5,
										RoundingMode.HALF_UP);
						orderAmountDistance = orderAmountDistance.subtract(orderAmount);
						if (BigDecimal.ZERO.compareTo(orderAmountDistance) < 0) {
							distanceDto.setOrderAmountDistance(orderAmountDistance);
							needAddFlg = true;
						}
					}
					if (financePk1 != financePk2) {
						financeAmountDistance = tmpFinanceModel.getFromAmount() == null ? BigDecimal.ZERO
								: tmpFinanceModel.getFromAmount();
						financeAmount = memberGradeModel.getYearFinanceAvg() == null ? BigDecimal.ZERO
								: memberGradeModel.getYearFinanceAvg().divide(new BigDecimal(10000), 5,
										RoundingMode.HALF_UP);
						financeAmountDistance = financeAmountDistance.subtract(financeAmount);
						if (BigDecimal.ZERO.compareTo(financeAmountDistance) < 0) {
							distanceDto.setFinanceAmountDistance(financeAmountDistance);
							needAddFlg = true;
						}
					}
					if (needAddFlg) {
						distanceList.add(distanceDto);
					}
				}
			}
		}
		return distanceList;
	}

	/**
	 * 取得会员等级升级路径
	 * 
	 * @param memberGradeModel
	 * @param defaultDto
	 * @param upgradePathArr
	 * @param nextUserGrade
	 * @return
	 */
	private List<HTDUserUpgradePathDto> getGradeUpgradePath(BuyerGradeInfoDTO memberGradeModel,
			HTDUserUpgradeDto[][] upgradePathArr, Long nextUserGrade) {
		BigDecimal levelPoint = memberGradeModel.getLevelExp();
		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);

		Long orderModel = memberGradeModel.getYearOrderLevel() == 0l ? orderScoreModelList.get(0).getId()
				: memberGradeModel.getYearOrderLevel();
		Long financeModel = memberGradeModel.getYearFinanceLevel() == 0l ? financeScoreModelList.get(0).getId()
				: memberGradeModel.getYearFinanceLevel();

		List<HTDUserUpgradePathDto> pathList = new ArrayList<HTDUserUpgradePathDto>();
		HTDUserUpgradePathDto startPosDto = null;
		HTDUserUpgradeDto[] financeUpgateDtoArr = null;
		HTDUserUpgradeDto tmpUpgateDto = null;
		double point1 = 0;
		double point2 = 0;
		long orderPk1 = 0;
		long orderPk2 = 0;
		long financePk1 = 0;
		long financePk2 = 0;

		for (int i = upgradePathArr.length - 1; i >= 0; i--) {
			financeUpgateDtoArr = upgradePathArr[i];
			for (int j = financeUpgateDtoArr.length - 1; j >= 0; j--) {
				tmpUpgateDto = financeUpgateDtoArr[j];
				point1 = levelPoint.doubleValue();
				point2 = tmpUpgateDto.getLevelPoint().doubleValue();
				if (point1 == point2) {
					orderPk1 = orderModel;
					orderPk2 = tmpUpgateDto.getOrderModel().getId();
					financePk1 = financeModel;
					financePk2 = tmpUpgateDto.getFinanceModel().getId();
					if (orderPk1 == orderPk2 && financePk1 == financePk2) {
						startPosDto = new HTDUserUpgradePathDto();
						startPosDto.setiPos(i);
						startPosDto.setjPos(j);
						break;
					}
				}
			}
			if (startPosDto != null) {
				break;
			}
		}
		if (startPosDto != null) {
			pathList = getUserGradeUpgradePathList(upgradePathArr, startPosDto, nextUserGrade);
		}
		return pathList;
	}

	/**
	 * 取得会员升级路径
	 * 
	 * @param upgradePathArr
	 * @param startPosDto
	 * @param nextUserGrade
	 * @return
	 */
	private List<HTDUserUpgradePathDto> getUserGradeUpgradePathList(HTDUserUpgradeDto[][] upgradePathArr,
			HTDUserUpgradePathDto startPosDto, Long nextUserGrade) {

		Map<Integer, List<HTDUserUpgradePathDto>> allPathMap = new HashMap<Integer, List<HTDUserUpgradePathDto>>();
		List<HTDUserUpgradePathDto> retList = null;
		List<HTDUserUpgradePathDto> tmpList = null;
		List<HTDUserUpgradePathDto> pathList = null;
		HTDUserUpgradeDto[] financeUpgateDtoArr = null;
		HTDUserUpgradeDto tmpUpgateDto = null;
		Long tmpUserGrade = null;
		HTDUserUpgradePathDto tmpPathDto = null;
		int startIPos = startPosDto.getiPos();
		int startJPos = startPosDto.getjPos();
		int weight = 0;
		Iterator<Integer> it = null;
		int jPos = 0;

		for (int i = startIPos; i >= 0; i--) {
			financeUpgateDtoArr = upgradePathArr[i];
			for (int j = startJPos; j >= 0; j--) {
				tmpUpgateDto = financeUpgateDtoArr[j];
				tmpUserGrade = tmpUpgateDto.getUserGrade();
				if (tmpUserGrade.equals(nextUserGrade)) {
					tmpPathDto = new HTDUserUpgradePathDto();
					tmpPathDto.setiPos(i);
					tmpPathDto.setjPos(j);
					weight = startJPos - j;
					weight += startIPos - i;
					tmpPathDto.setWeight(weight);
					tmpPathDto.setUserUpgradeDto(tmpUpgateDto);
					if (allPathMap.containsKey(j)) {
						pathList = allPathMap.get(j);
					} else {
						pathList = new ArrayList<HTDUserUpgradePathDto>();
					}
					pathList.add(tmpPathDto);
					allPathMap.put(j, pathList);
					break;
				}
			}
		}
		if (allPathMap != null && allPathMap.size() > 0) {
			retList = new ArrayList<HTDUserUpgradePathDto>();
			it = allPathMap.keySet().iterator();
			while (it.hasNext()) {
				jPos = it.next();
				tmpList = allPathMap.get(jPos);
				if (tmpList != null && tmpList.size() > 0) {
					Collections.sort(tmpList, new Comparator<HTDUserUpgradePathDto>() {
						public int compare(HTDUserUpgradePathDto arg0, HTDUserUpgradePathDto arg1) {
							Integer score0 = arg0.getWeight();
							Integer score1 = arg1.getWeight();
							return score0.compareTo(score1);
						}
					});
					retList.add(tmpList.get(0));
				}
			}
		}
		return retList;
	}

	/**
	 * 取得会员的下一个等级
	 * 
	 * @param userGrade
	 * @param defaultDto
	 * @return
	 */
	private BuyerGradeIntervalDTO getHTDMemberNextGrade(BuyerGradeInfoDTO userGrade) {

		List<BuyerGradeIntervalDTO> userGradePointList = memberGradeDAO.queryBuyerGradeList();
		Collections.reverse(userGradePointList);
		BuyerGradeIntervalDTO tmpPointModel = null;
		Long tmpUserGrade = null;
		BuyerGradeIntervalDTO nextUserGradePointModel = null;
		boolean hasFindFlg = false;
		for (int i = userGradePointList.size() - 1; i >= 0; i--) {
			tmpPointModel = userGradePointList.get(i);
			tmpUserGrade = tmpPointModel.getBuyerLevel();
			if (hasFindFlg) {
				nextUserGradePointModel = tmpPointModel;
				break;
			}
			if (userGrade.getBuyerGrade().equals(tmpUserGrade.toString())) {
				hasFindFlg = true;
			}
		}
		return nextUserGradePointModel;
	}

	/**
	 * 取得HTD会员积分矩阵
	 * 
	 * @param defaultDto
	 * @return
	 */
	private HTDUserUpgradeDto[][] getHTDUserGradeMatrix() {
		BuyerScoreIntervalDTO buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("1");
		List<BuyerScoreIntervalDTO> orderScoreModelList = memberGradeDAO.queryMemberScoreSetList(buyerScoreIntervalDTO);
		buyerScoreIntervalDTO = new BuyerScoreIntervalDTO();
		buyerScoreIntervalDTO.setIntervalType("2");
		List<BuyerScoreIntervalDTO> financeScoreModelList = memberGradeDAO
				.queryMemberScoreSetList(buyerScoreIntervalDTO);
		Collections.reverse(financeScoreModelList);
		Collections.reverse(orderScoreModelList);

		List<BuyerGradeIntervalDTO> userGradePointList = memberGradeDAO.queryBuyerGradeList();
		BuyerScoreIntervalDTO orderModel = null;
		BuyerScoreIntervalDTO financeModel = null;
		BuyerGradeIntervalDTO levelPointModel = null;
		BigDecimal orderPoint = BigDecimal.ZERO;
		BigDecimal financePoint = BigDecimal.ZERO;
		BigDecimal levelPoint = BigDecimal.ZERO;
		BigDecimal orderScore = BigDecimal.ZERO;
		BigDecimal financeScore = BigDecimal.ZERO;
		MemberScoreSetDTO mssd = memberScoreSetDAO.queryMemberScoreWeight(null);
		String wjson = mssd.getJsonStr();
		JSONObject gradeWeight = JSON.parseObject(wjson);
		BigDecimal mallWeight = gradeWeight.getBigDecimal("mallWeight").divide(new BigDecimal(100));
		BigDecimal financeWeight = gradeWeight.getBigDecimal("financeWeight").divide(new BigDecimal(100));
		BigDecimal totalWight = mallWeight.add(financeWeight);
		mallWeight = mallWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		financeWeight = financeWeight.divide(totalWight, 5, RoundingMode.HALF_UP);
		BigDecimal oPercent = mallWeight;
		BigDecimal fPercent = financeWeight;
		HTDUserUpgradeDto[][] retDtoArr = new HTDUserUpgradeDto[financeScoreModelList.size()][orderScoreModelList
				.size()];
		BigDecimal fromPoint = null;
		BigDecimal toPoint = null;
		HTDUserUpgradeDto userUpgradeDto = null;
		for (int i = 0; i < financeScoreModelList.size(); i++) {
			for (int j = 0; j < orderScoreModelList.size(); j++) {
				financeModel = financeScoreModelList.get(i);
				orderModel = orderScoreModelList.get(j);
				orderScore = orderModel.getScore();
				financeScore = financeModel.getScore();
				orderPoint = orderScore.multiply(oPercent);
				financePoint = financeScore.multiply(fPercent);
				levelPoint = orderPoint.add(financePoint);
				levelPointModel = null;
				for (BuyerGradeIntervalDTO pointModel : userGradePointList) {
					fromPoint = pointModel.getFromScore() == 0l ? new BigDecimal(Double.MAX_VALUE).negate()
							: new BigDecimal(pointModel.getFromScore());
					toPoint = new BigDecimal(pointModel.getToScore());

					if (levelPoint.compareTo(fromPoint) > 0) {
						if (toPoint.compareTo(BigDecimal.ZERO) == 0) {
							levelPointModel = pointModel;
							break;
						}
						if (levelPoint.compareTo(toPoint) <= 0) {
							levelPointModel = pointModel;
							break;
						}
					}
				}
				userUpgradeDto = new HTDUserUpgradeDto();
				userUpgradeDto.setOrderModel(orderModel);
				userUpgradeDto.setFinanceModel(financeModel);
				userUpgradeDto.setPointModel(levelPointModel);
				userUpgradeDto.setLevelPoint(levelPoint);
				userUpgradeDto.setUserGrade(levelPointModel.getBuyerLevel());
				retDtoArr[i][j] = userUpgradeDto;
			}
		}
		return retDtoArr;
	}
}
