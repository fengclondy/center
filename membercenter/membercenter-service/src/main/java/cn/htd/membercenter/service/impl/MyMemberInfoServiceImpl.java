package cn.htd.membercenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberModifyVerifyDAO;
import cn.htd.membercenter.dao.MyMemberDAO;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MyMemberInfoService;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MyMemberInfoService
 * </p>
 * 
 * @author thinkpad
 * @date 2016年11月26日
 *       <p>
 *       Description: 查询我的会员/担保会员/非会员详细信息接口实现
 *
 *       </p>
 */
@Service("myMemberInfoService")
public class MyMemberInfoServiceImpl implements MyMemberInfoService {

	private final static Logger logger = LoggerFactory.getLogger(MyMemberInfoServiceImpl.class);

	@Resource
	private MyMemberDAO myMemberDao;

	@Autowired
	private MemberBaseService memberBaseService;

	@Resource
	private MemberModifyVerifyDAO memberModifyVerifyDAO;

	@Resource
	MemberBaseOperationDAO memberBaseOperationDAO;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	/**
	 * 查询我的会员/担保会员详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	@Override
	public ExecuteResult<MyMemberDTO> selectMyMemberInfo(Long memberId) {
		ExecuteResult<MyMemberDTO> rs = new ExecuteResult<MyMemberDTO>();
		try {

			MyMemberDTO myMemberDTO;
			if (memberId != null) {
				String url;
				// 根据会员ID查询查询我的会员/担保会员详细信息
				myMemberDTO = myMemberDao.selectMyMemberInfo(memberId);
				// 根据会员是否有营业执照或者担保证明判断我的会员或者担保会员
				if (myMemberDTO.getBuyerBusinessLicensePicSrc() == null
						|| myMemberDTO.getBuyerBusinessLicensePicSrc().equals("")) {
					url = myMemberDTO.getBuyerGuaranteeLicensePicSrc();
					myMemberDTO.setQualificationDocuments(url);

				} else {
					url = myMemberDTO.getBuyerBusinessLicensePicSrc();
					myMemberDTO.setQualificationDocuments(url);
				}
				// if (null != myMemberDTO.getBelongManagerId() &&
				// !"0".equals(myMemberDTO.getBelongManagerId())) {
				// EmployeeDTO employeeDTO =
				// employeeService.getEmployeeInfo(myMemberDTO.getBelongManagerId());
				// if (null != employeeDTO) {
				myMemberDTO.setBelongManagerName(memberBaseInfoService
						.getManagerName(myMemberDTO.getSellerId().toString(), myMemberDTO.getBelongManagerId()));
				// }
				// }
				// 拼接省市区镇详细地址
				String locationAddr = "";
				if (!StringUtils.isEmpty(myMemberDTO.getLocationTown()) && !myMemberDTO.getLocationTown().equals("0")) {
					locationAddr = memberBaseService.getAddressBaseByCode(myMemberDTO.getLocationTown())
							+ myMemberDTO.getLocationDetail();
				} else {
					locationAddr = memberBaseService.getAddressBaseByCode(myMemberDTO.getLocationCounty())
							+ myMemberDTO.getLocationDetail();
				}

				myMemberDTO.setLocationAddr(locationAddr);

				rs.setResult(myMemberDTO);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("请选择要查询的数据");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MyMemberInfoServiceImpl----->selectMyMemberInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询非会员详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	@Override
	public ExecuteResult<MyNoMemberDTO> selectNoMemberInfo(Long memberId) {
		ExecuteResult<MyNoMemberDTO> rs = new ExecuteResult<MyNoMemberDTO>();
		try {
			MyNoMemberDTO noMemberDTO;
			if (memberId != null) {
				noMemberDTO = myMemberDao.selectNoMemberInfo(memberId);
				if (noMemberDTO.getLocationTown() != null) {
					StringBuffer address = new StringBuffer();
					if (!StringUtils.isEmpty(noMemberDTO.getLocationTown())
							&& !noMemberDTO.getLocationTown().equals("0")) {
						address.append(memberBaseService.getAddressBaseByCode(noMemberDTO.getLocationTown()));
					} else {
						address.append(memberBaseService.getAddressBaseByCode(noMemberDTO.getLocationCounty()));
					}
					address.append(noMemberDTO.getLocationDetail());
					noMemberDTO.setAddress(address.toString());
				}

				// 获取修改名称
				if (!StringUtils.isEmpty(noMemberDTO.getCompanyNameModifyStatus())
						&& GlobalConstant.VERIFY_STATUS_WAIT.equals(noMemberDTO.getCompanyNameModifyStatus())) {
					VerifyResultDTO verifyInfo = memberBaseOperationDAO.selectVerifyByIdAndInfoType(memberId,
							GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);// 查询审核id
					List<Long> verifyIds = new ArrayList<Long>();
					verifyIds.add(verifyInfo.getVerifyId());
					List<VerifyDetailInfoDTO> verList = memberModifyVerifyDAO.selectVerifyByVerifyIds(verifyIds);
					if (verList.size() > 0) {
						noMemberDTO.setCompanyName(verList.get(0).getAfterChange());
					}
				}
				rs.setResult(noMemberDTO);
				noMemberDTO.setCurBelongManagerName(memberBaseInfoService.getManagerName(
						noMemberDTO.getCurBelongSellerId().toString(), noMemberDTO.getCurBelongManagerId().toString()));
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("请选择要查询的数据");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MyMemberInfoServiceImpl----->selectNoMemberInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

}
