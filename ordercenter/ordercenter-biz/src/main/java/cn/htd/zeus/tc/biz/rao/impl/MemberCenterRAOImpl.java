package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.service.BoxRelationshipService;
import cn.htd.membercenter.service.ConsigneeAddressService;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBuyerService;
import cn.htd.membercenter.service.MemberCallCenterService;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.common.enums.FacadeOtherResultCodeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

@Service
public class MemberCenterRAOImpl implements MemberCenterRAO {

	@Autowired
	private MemberCallCenterService memberCallCenterService;

	@Autowired
	private ConsigneeAddressService consigneeAddressService;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Autowired
	private MemberBuyerService memberBuyerService;

	@Autowired
	private BoxRelationshipService boxRelationshipService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCenterRAOImpl.class);

	/*
	 * 根据会员code和type查询会员信息
	 */
	@Override
	public OtherCenterResDTO<MemberBaseInfoDTO> selectMemberBaseName(String memberCode,
			String buyerSellerType, String messageId) {
		OtherCenterResDTO<MemberBaseInfoDTO> otherCenterResDTO = new OtherCenterResDTO<MemberBaseInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询会员中心(selectMemberBaseName查询会员详情信息)--组装查询参数开始:" + "MessageId:" + messageId
					+ " memberCode:" + memberCode + " buyerSellerType" + buyerSellerType);
			ExecuteResult<MemberBaseInfoDTO> memberExecuteResult = memberCallCenterService
					.selectMemberBaseName(memberCode, buyerSellerType);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询会员中心(selectMemberBaseName查询会员详情信息)--返回结果:{}", messageId,
					JSONObject.toJSONString(memberExecuteResult) + " 耗时:" + (endTime - startTime));
			if (memberExecuteResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				MemberBaseInfoDTO memberBaseInfoDTO = memberExecuteResult.getResult();
				otherCenterResDTO.setOtherCenterResult(memberBaseInfoDTO);
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn(
						"MessageId:{} 从会员中心-(selectMemberBaseName查询会员详情信息)-没有查到数据 返回错误码和错误信息:{}",
						messageId,
						memberExecuteResult.getCode() + memberExecuteResult.getResultMessage());
				otherCenterResDTO.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.selectMemberBaseName出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调用会员中心-查询会员分组id和买家等级
	 */
	public OtherCenterResDTO<MemberGroupDTO> selectBuyCodeSellCode(String sellerCode,
			String buyerCode, String messageId) {
		OtherCenterResDTO<MemberGroupDTO> otherCenterResDTO = new OtherCenterResDTO<MemberGroupDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询会员中心(selectBuyCodeSellCode查询会员分组id和买家等级信息)--组装查询参数开始:" + "MessageId:"
					+ messageId + " sellerCode:" + sellerCode + " buyerCode" + buyerCode);
			// TODO 明天看看会员中心有没有完成
			ExecuteResult<MemberGroupDTO> memberGroupResDTO = memberCallCenterService
					.selectBuyCodeSellCode(sellerCode, buyerCode);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询会员中心(selectBuyCodeSellCode查询会员分组id和买家等级信息)--返回结果:{}",
					messageId,
					JSONObject.toJSONString(memberGroupResDTO) + " 耗时:" + (endTime - startTime));
			if (memberGroupResDTO.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				MemberGroupDTO memberGroupDTO = memberGroupResDTO.getResult();
				otherCenterResDTO.setOtherCenterResult(memberGroupDTO);
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn(
						"MessageId:{} 从会员中心-(selectBuyCodeSellCode查询会员详情信息)-没有查到数据 返回错误码和错误信息:{}",
						messageId,
						memberGroupResDTO.getCode() + memberGroupResDTO.getResultMessage());
				otherCenterResDTO.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_BUYERGRADE_AND_MEMBERGROUPID_NOT_RESULT
								.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_BUYERGRADE_AND_MEMBERGROUPID_NOT_RESULT
								.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.selectBuyCodeSellCode出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/**
	 * 调用会员中心查询会员等级信息
	 * 
	 * @param memberBaseDTO
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<MemberGradeDTO> queryMemberGradeInfo(String memberCode,
			String messageId) {
		OtherCenterResDTO<MemberGradeDTO> other = new OtherCenterResDTO<MemberGradeDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("订单结算--查询会员等级--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<MemberGradeDTO> gradeResult = memberCallCenterService
					.queryMemberGradeInfo(memberCode);
			LOGGER.info("MessageId:{} 订单结算--查询会员等级--返回结果:{}", messageId,
					JSONObject.toJSONString(gradeResult));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询会员等级信息 耗时:{}", messageId, endTime - startTime);
			MemberGradeDTO gradeDTO = gradeResult.getResult();
			if (gradeDTO != null && "success".equals(gradeResult.getResultMessage())) {
				other.setOtherCenterResult(gradeDTO);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.warn("MessageId:{} 订单结算--查询会员等级(queryMemberGradeInfo查询会员等级信息)-没有查到数据 参数:{}",
						messageId, JSONObject.toJSONString(gradeResult));
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberGradeInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/**
	 * 查询会员收货地址信息
	 * 
	 * @param memberId
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<List<MemberConsigAddressDTO>> queryConsigAddressList(String memberCode,
			String messageId) {
		OtherCenterResDTO<List<MemberConsigAddressDTO>> other = new OtherCenterResDTO<List<MemberConsigAddressDTO>>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("订单结算--查询会员收货信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<List<MemberConsigAddressDTO>> resultList = memberCallCenterService
					.selectConsigAddressList(memberCode);
			LOGGER.info("MessageId:{} 订单结算--查询会员收货信息--返回结果:{}", messageId,
					JSONObject.toJSONString(resultList));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询会员收货信息 耗时:{}", messageId, endTime - startTime);
			List<MemberConsigAddressDTO> addressList = resultList.getResult();
			if (CollectionUtils.isNotEmpty(addressList)) {
				other.setOtherCenterResult(addressList);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法MemberCenterRAOImpl.queryConsigAddressList没有查询到正确的值{}",
						messageId, resultList.getErrorMessages());
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryConsigAddressList出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/**
	 * 查询会员发票信息
	 * 
	 * @param memberId
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode,
			String channelCode, String messageId) {
		OtherCenterResDTO<MemberInvoiceDTO> other = new OtherCenterResDTO<MemberInvoiceDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询会员发票信息--组装查询参数开始:" + "MessageId:" + messageId + " memberCode:"
					+ memberCode + " channelCode:" + channelCode);
			ExecuteResult<MemberInvoiceDTO> result = memberCallCenterService
					.queryMemberInvoiceInfo(memberCode, channelCode);
			LOGGER.info("MessageId:{}查询会员发票信息--返回结果:{}", messageId,
					JSONObject.toJSONString(result));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询会员发票信息 耗时:{}", messageId, endTime - startTime);
			MemberInvoiceDTO invoiceInfo = result.getResult();
			if (invoiceInfo != null && result.getResultMessage().equals("success")) {
				other.setOtherCenterResult(invoiceInfo);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberInvoiceInfo没有查询到正确的值{}",
						messageId, result.getErrorMessages());
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberInvoiceInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<String> queryMemberCodeByMemberId(long memberId, String messageId) {
		OtherCenterResDTO<String> other = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("订单结算--查询会员编码信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<MemberDetailInfo> memberDetail = memberBaseInfoService
					.getMemberDetailBySellerId(memberId);
			LOGGER.info("MessageId:{} 订单结算--查询会员编码信息--返回结果:{}", messageId, memberId);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询会员编码信息 耗时:{}", messageId, endTime - startTime);
			MemberDetailInfo memberInfo = memberDetail.getResult();
			if (memberInfo != null && memberInfo.getMemberBaseInfoDTO() != null) {
				other.setOtherCenterResult(memberInfo.getMemberBaseInfoDTO().getMemberCode());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberCodeByMemberId没有查询到正确的值{}",
						messageId, memberDetail.getErrorMessages());
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberCodeByMemberId出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<MemberBaseInfoDTO> getMemberDetailBySellerId(long memberId,
			String messageId) {
		OtherCenterResDTO<MemberBaseInfoDTO> other = new OtherCenterResDTO<MemberBaseInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询会员信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<MemberDetailInfo> memberDetail = memberBaseInfoService
					.getMemberDetailBySellerId(memberId);
			LOGGER.info("MessageId:{} 查询会员信息--返回结果:{}", messageId, memberId);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 查询会员信息 耗时:{}", messageId, endTime - startTime);
			MemberDetailInfo memberInfo = memberDetail.getResult();
			if (memberInfo != null && memberInfo.getMemberBaseInfoDTO() != null) {
				other.setOtherCenterResult(memberInfo.getMemberBaseInfoDTO());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberCodeByMemberInfo没有查询到正确的值{}",
						messageId, memberDetail.getErrorMessages());
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryMemberCodeByMemberInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/*
	 * 根据卖家code和渠道编码查询卖家的地址
	 * 
	 */
	public OtherCenterResDTO<String> selectChannelAddressDTO(String messageId, String memberCode,
			String channelCode) {
		OtherCenterResDTO<String> other = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询卖家的地址--组装查询参数开始:" + "MessageId:" + messageId + "memberCode:" + memberCode
					+ "channelCode:" + channelCode);
			ExecuteResult<MemberConsigAddressDTO> result = consigneeAddressService
					.selectChannelAddressDTO(messageId, memberCode, channelCode);
			LOGGER.info("MessageId:{} 查询卖家的地址--返回结果:{}", messageId, JSONObject.toJSONString(result)
					+ "耗时:" + (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				MemberConsigAddressDTO consigAddress = result.getResult();
				String province = consigAddress.getConsigneeAddressProvince();
				String city = consigAddress.getConsigneeAddressCity();
				String district = consigAddress.getConsigneeAddressDistrict();
				String town = consigAddress.getConsigneeAddressTown() == null ? "0"
						: consigAddress.getConsigneeAddressTown();
				String area = province + "_" + city + "_" + district + "_" + town;
				other.setOtherCenterResult(area);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}查询卖家的地址(selectChannelAddressDTO)-没有查到数据 参数:{}", messageId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_JD_ADRESS.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_JD_ADRESS.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.selectChannelAddressDTO出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/*
	 * 根据卖家code和渠道编码查询卖家的地址 例子：如果传入卖家code和3010 则查出的就是卖家在京东的收获地址
	 * 重写selectChannelAddressDTO方法
	 */
	public OtherCenterResDTO<MemberConsigAddressDTO> selectChannelAddressDTO4Common(
			String messageId, String memberCode, String channelCode) {
		OtherCenterResDTO<MemberConsigAddressDTO> other = new OtherCenterResDTO<MemberConsigAddressDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询卖家的地址--4common--组装查询参数开始:" + "MessageId:" + messageId + "memberCode:"
					+ memberCode + "channelCode:" + channelCode);
			ExecuteResult<MemberConsigAddressDTO> result = consigneeAddressService
					.selectChannelAddressDTO(messageId, memberCode, channelCode);
			LOGGER.info("MessageId:{} 查询卖家的地址--4common--返回结果:{}", messageId,
					JSONObject.toJSONString(result) + "耗时:"
							+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				MemberConsigAddressDTO consigAddressInfo = result.getResult();
				other.setOtherCenterResult(consigAddressInfo);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}查询卖家的地址(selectChannelAddressDTO4Common)-没有查到数据 参数:{}",
						messageId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法MemberCenterRAOImpl.selectChannelAddressDTO4Common出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/*
	 * 根据code查询id
	 */
	@Override
	public OtherCenterResDTO<Long> getMemberIdByCode(String memberCode, String messageId) {
		OtherCenterResDTO<Long> other = new OtherCenterResDTO<Long>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info(
					"查询会员code--组装查询参数开始:" + "MessageId:" + messageId + "memberCode:" + memberCode);
			ExecuteResult<Long> result = memberBaseInfoService.getMemberIdByCode(memberCode);
			LOGGER.info("MessageId:{} 查询会员code--返回结果:{}", messageId, JSONObject.toJSONString(result)
					+ "耗时:" + (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				Long memberId = result.getResult();
				other.setOtherCenterResult(memberId);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}根据code查询会员的id(getMemberIdByCode)-没有查到数据 参数:{}", messageId,
						memberCode);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.getMemberIdByCode出现异常{}", messageId,
					w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/*
	 * 根据会员id查询注册地址
	 */
	@Override
	public OtherCenterResDTO<MemberDetailInfo> getMemberDetailById(Long memberId,
			String messageId) {
		OtherCenterResDTO<MemberDetailInfo> other = new OtherCenterResDTO<MemberDetailInfo>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询注册地址--组装查询参数开始:" + "MessageId:" + messageId + "memberId:" + memberId);
			ExecuteResult<MemberDetailInfo> result = memberBaseInfoService
					.getMemberDetailById(memberId);
			LOGGER.info("MessageId:{} 查询注册地址--返回结果:{}", messageId, JSONObject.toJSONString(result)
					+ "耗时:" + (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				MemberDetailInfo memberBaseInfo = result.getResult();
				other.setOtherCenterResult(memberBaseInfo);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}查询注册地址(getMemberDetailById)-没有查到数据 参数:{}", messageId,
						memberId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.getMemberDetailById出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/**
	 * 查询会员等级详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	@Override
	public OtherCenterResDTO<MemberBuyerGradeInfoDTO> queryBuyerGradeInfo(Long memberId,
			String messageId) {
		OtherCenterResDTO<MemberBuyerGradeInfoDTO> other = new OtherCenterResDTO<MemberBuyerGradeInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId{}查询会员等级详细信息--组装查询参数开始:MemberId{}", messageId, memberId);
			ExecuteResult<MemberBuyerGradeInfoDTO> result = memberBuyerService
					.queryBuyerGradeInfo(memberId);
			LOGGER.info("MessageId:{} 查询会员等级详细信息--返回结果:{}", messageId,
					JSONObject.toJSONString(result) + "耗时:"
							+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				MemberBuyerGradeInfoDTO memberBuyerGradeInfo = result.getResult();
				other.setOtherCenterResult(memberBuyerGradeInfo);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}查询会员等级详细信息(queryBuyerGradeInfo)-没有查到数据 参数:{}", messageId,
						memberId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.queryBuyerGradeInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/**
	 * 查询外部供应商是否有平台公司身份
	 * 
	 * @param memberCode
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<String> isHasInnerComapanyCert(String memberCode, String messageId) {

		OtherCenterResDTO<String> other = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId{}查询外部供应商是否有平台公司身份--组装查询参数开始:memberCode{}", messageId,
					memberCode);
			ExecuteResult<String> result = memberBaseInfoService.IsHasInnerComapanyCert(memberCode);
			;
			LOGGER.info("MessageId:{} 查询外部供应商是否有平台公司身份--返回结果:{}", messageId,
					JSONObject.toJSONString(result) + "耗时:"
							+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				String isHasInnerComapanyCert = result.getResult();
				other.setOtherCenterResult(isHasInnerComapanyCert);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{}查询外部供应商是否有平台公司身份(isHasInnerComapanyCert)-没有查到数据 参数:{}",
						messageId, memberCode);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.isHasInnerComapanyCert出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;

	}

	public OtherCenterResDTO<MemberBaseInfoDTO> getInnerInfoByOuterHTDCode(String memberCode,
			String messageId) {
		OtherCenterResDTO<MemberBaseInfoDTO> other = new OtherCenterResDTO<MemberBaseInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId{}查询外部供应商对应的平台公司code--组装查询参数开始:memberCode{}", messageId,
					memberCode);
			ExecuteResult<MemberBaseInfoDTO> result = memberBaseInfoService
					.getInnerInfoByOuterHTDCode(memberCode);
			LOGGER.info("MessageId:{} 查询外部供应商对应的平台公司code--返回结果:{}", messageId,
					JSONObject.toJSONString(result) + "耗时:"
							+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				MemberBaseInfoDTO memberBaseInfoDTO = result.getResult();
				other.setOtherCenterResult(memberBaseInfoDTO);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn(
						"MessageId:{}查询外部供应商对应的平台公司code(getInnerInfoByOuterHTDCode)-没有查到数据 参数:{}",
						messageId, memberCode);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MemberCenterRAOImpl.getInnerInfoByOuterHTDCode出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;

	}

	@Override
	public OtherCenterResDTO<ApplyBusiRelationDTO> selectBusiRelation(Long memberId, Long sellerId,
			Long categoryId, Long brandId) {
		OtherCenterResDTO<ApplyBusiRelationDTO> other = new OtherCenterResDTO<ApplyBusiRelationDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("查询买家卖家经营关系--组装查询参数开始:memberId{}sellerId{}categoryId{}brandId{}", memberId,
					sellerId, categoryId, brandId);
			ExecuteResult<ApplyBusiRelationDTO> result = boxRelationshipService
					.selectBusiRelation(memberId, sellerId, categoryId, brandId);
			LOGGER.info("查询买家卖家经营关系--返回结果:{}", JSONObject.toJSONString(result) + "耗时:"
					+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				ApplyBusiRelationDTO applyBusiRelationDTO = result.getResult();
				other.setOtherCenterResult(applyBusiRelationDTO);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn(
						"查询买家卖家经营关系(selectBusiRelation)-没有查到数据 参数:memberId{}sellerId{}categoryId{}brandId{}",
						memberId, sellerId, categoryId, brandId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法MemberCenterRAOImpl.selectBusiRelation出现异常{}", w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<String> getMemberCodeById(Long memberId) {
		OtherCenterResDTO<String> other = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("根据会员ID查询会员CODE--组装查询参数开始:memberId{}", memberId);
			ExecuteResult<String> result = memberBaseInfoService.getMemberCodeById(memberId);
			LOGGER.info("根据会员ID查询会员CODE--返回结果:{}", JSONObject.toJSONString(result) + "耗时:"
					+ (System.currentTimeMillis() - startTime));
			if (null != result.getResult() && result.isSuccess() == true) {
				other.setOtherCenterResult(result.getResult());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("根据会员ID查询会员CODE-没有查到数据 参数:memberId{}", memberId);
				other.setOtherCenterResponseCode(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getCode());
				other.setOtherCenterResponseMsg(
						FacadeOtherResultCodeEnum.MEMBERCENTER_QUERY_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法MemberCenterRAOImpl.getMemberCodeById出现异常{}", w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

}
