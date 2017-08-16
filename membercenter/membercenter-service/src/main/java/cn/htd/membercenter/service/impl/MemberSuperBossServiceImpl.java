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
import cn.htd.membercenter.service.MemberSuperBossService;

@Service("memberSuperBossService")
public class MemberSuperBossServiceImpl implements MemberSuperBossService {

	private final static Logger logger = LoggerFactory.getLogger(MemberSuperBossServiceImpl.class);

	@Resource
	private MemberSuperBossDao memberSuperBossDao;

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
					for(MemberBaseInfoDTO memberbase:resList ){ //managerstatus 1.运营待审核 2.运营审核不通过 3.运营审核驳回 4.供应商待审核 5.供应商审核通过
						if(StringUtils.isNotBlank(memberbase.getCooperateVerifyStatus())){
							if(memberbase.getCooperateVerifyStatus().equals("1")){
								memberbase.setManagerStatus("4");
							}else{
								memberbase.setManagerStatus("5");
							}
						}else{
							if(StringUtils.isNotBlank(memberbase.getVerifyStatus())){
								if(memberbase.getVerifyStatus().equals("1")){
									memberbase.setManagerStatus("1");
								}
								if(memberbase.getVerifyStatus().equals("2")){
									memberbase.setManagerStatus("2");
								}
								if(memberbase.getVerifyStatus().equals("3")){
									memberbase.setManagerStatus("3");
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
