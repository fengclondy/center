package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberSuperBossDao;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.enums.ExternalAuditStatusEnum;
import cn.htd.membercenter.service.MemberSuperBossService;

@Service("memberSuperBossService")
public class MemberSuperBossServiceImpl implements MemberSuperBossService {

	private final static Logger logger = LoggerFactory.getLogger(MemberSuperBossServiceImpl.class);

	@Resource
	private MemberSuperBossDao memberSuperBossDao;

	/**
	 * @desc 
	 * cooperateVerifyStatus:供应商审核状态 1：待审核，2：已通过，3：被驳回
	 * verifyStatus：运营审核状态 1：待审核，2：已通过，3：被驳回
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectMemberByCustmanagerCode(String managerCode, Pager pager) {
		logger.info("MemberSuperBossServiceImpl===selectMemberByCustmanagerCode服务执行开始，参数：managerCode:" + managerCode
				+ ",pager:" + JSONObject.toJSONString(pager));
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			Long count = memberSuperBossDao.selectMemberCountByCustmanagerCode(managerCode);
			if (count > 0) {
				List<MemberBaseInfoDTO> resList = memberSuperBossDao.selectMemberByCustmanagerCode(managerCode, pager);
				if( null !=resList && resList.size()>0){
					for(MemberBaseInfoDTO memberbase:resList ){ //managerstatus 1.运营待审核 2.运营审核不通过 3.运营审核终止/驳回 4.供应商待审核 5.供应商审核通过（或者公海会员运营审核通过默认通过） 
						if(StringUtils.isNotBlank(memberbase.getCooperateVerifyStatus())){ //如果供应商审核状态不为空，则说明运营已经审核通过
							if(memberbase.getCooperateVerifyStatus().equals("1")){//如果cooperateVerifyStatus为1，即供应商待审核状态
								memberbase.setManagerStatus(ExternalAuditStatusEnum.SUPPLIERE_UDIT_PENDING.getCode());//设置超级经理人展示会员注册状态，4，表示供应商待审核
							}else{
								memberbase.setManagerStatus(ExternalAuditStatusEnum.SUPPLIERE_AUDIT_PASS.getCode());//cooperateVerifyStatus为其他状态，说明供应商审核通过，供应商审核驳回，业务要求默认审核通过
							}
						}else{
							if(StringUtils.isNotBlank(memberbase.getVerifyStatus())){ //如果是运行审核，则判断超级经理级展示状态
								if(memberbase.getVerifyStatus().equals("1")){//verifyStatus为1.说明运营待审核
									memberbase.setManagerStatus(ExternalAuditStatusEnum.OPERATE_AUDIT_PENDING.getCode());
								}
								if(memberbase.getVerifyStatus().equals("2")){//2.说明运营审核通过
									memberbase.setManagerStatus(ExternalAuditStatusEnum.SUPPLIERE_AUDIT_PASS.getCode());//如果是公海会员，运营审核通过，默认通过，不用经过供应商审核
								}
								if(memberbase.getVerifyStatus().equals("3")){//3.运行审核不通过
									memberbase.setManagerStatus(ExternalAuditStatusEnum.OPERATE_AUDIT_NOT_PASS.getCode());
								}
								if(memberbase.getVerifyStatus().equals("4")){//4.运营审核终止/驳回
									memberbase.setManagerStatus(ExternalAuditStatusEnum.OPERATE_AUDIT_REJECT.getCode());
								}
								
							}
						}
					}
				}
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("查询数据不存在");
			}
			logger.info("MemberSuperBossServiceImpl===selectMemberByCustmanagerCode服务执行成功，参数：" + managerCode);
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error(
					"MemberSuperBossServiceImpl----->selectMemberByCustmanagerCode执行异常:" + e + ",参数为:" + managerCode);
		}
		return rs;
	}

}
