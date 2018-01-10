package cn.htd.membercenter.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberCompanyInfoDao;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberExternalDTO;
import cn.htd.membercenter.service.MemberExternalService;

@Service("memberExternalService")
public class MemberExternalServiceImpl implements MemberExternalService {

	private final static Logger logger = LoggerFactory.getLogger(MemberExternalServiceImpl.class);

	@Resource
	private MemberBaseDAO memberBaseDao;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private MemberCompanyInfoDao memberCompanyInfoDao;
	@Resource
	private RedisDB redisDB;

	/**
	 * 收货地址reids信息
	 */
	public static final String REDIS_EXTERNAL_ADDRESS_INFO = "REDIS_EXTERNAL_ADDRESS_INFO";
	public static final String REDIS_EXTERNAL_RECEIVE_PERSON = "REDIS_EXTERNAL_RECEIVE_PERSON";
	public static final String REDIS_EXTERNAL_RECEIVE_PHONE = "REDIS_EXTERNAL_RECEIVE_PHONE";
	public static final String REDIS_EXTERNAL_RECEIVE_ADDRESS = "REDIS_EXTERNAL_RECEIVE_ADDRESS";

	/**
	 * 超级经理人带客下单 根据会员编码查询收货地址相关信息
	 * 
	 * @author li.jun
	 * @time 2018-01-08
	 */
	@Override
	public ExecuteResult<MemberExternalDTO> queryMemberAddressInfo(String memberCode) {
		ExecuteResult<MemberExternalDTO> result = new ExecuteResult<MemberExternalDTO>();
		MemberExternalDTO member = new MemberExternalDTO();
		Map<String, String> resultMap = null;
		try {
			if (StringUtils.isNotEmpty(memberCode)) {
				String redisKey = REDIS_EXTERNAL_ADDRESS_INFO + "_" + memberCode;
				resultMap = redisDB.getHashOperations(redisKey);
				if (resultMap == null || resultMap.isEmpty()) {
					MemberBaseInfoDTO dto = memberBaseOperationDAO.queryMemberCompanyInfo(memberCode);
					if(dto !=null){
						member.setReceivePerson(dto.getArtificialPersonName());
						member.setReceivePhone(dto.getArtificialPersonMobile());
						member.setReceiveAddress(dto.getLocationAddr());	
					}
				}else{
					member.setReceivePerson(resultMap.get(REDIS_EXTERNAL_RECEIVE_PERSON));
					member.setReceivePhone(resultMap.get(REDIS_EXTERNAL_RECEIVE_PHONE));
					member.setReceiveAddress(resultMap.get(REDIS_EXTERNAL_RECEIVE_ADDRESS));	
				}
			} else {
				result.addErrorMessage("会员编码不能为空");
			}
		} catch (Exception e) {
			logger.error("查询地址信息报错" + e);
			result.addErrorMessage("查询地址信息报错");
		}
		result.setResult(member);
		return result;
	}

	/**
	 * 超级经理人带客下单 根据会员编码-新增-修改地址信息 MemberExternalDTO :会员地址相关信息 flag:新增修改标志 1 新增 0
	 * 修改
	 * 
	 * @author li.jun
	 * @time 2018-01-08
	 */
	@Override
	public ExecuteResult<Boolean> addUpdateAddressInfo(MemberExternalDTO dto) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			String redisKey = REDIS_EXTERNAL_ADDRESS_INFO + "_" + dto.getMemberCode();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put(REDIS_EXTERNAL_RECEIVE_PERSON, dto.getReceivePerson());
			resultMap.put(REDIS_EXTERNAL_RECEIVE_PHONE, dto.getReceivePhone());
			resultMap.put(REDIS_EXTERNAL_RECEIVE_ADDRESS, dto.getReceiveAddress());
			redisDB.setHash(redisKey, resultMap);
			result.setResult(true);
		} catch (Exception e) {
			result.addErrorMessage("新增修改地址操作redis失败!");
			result.setResult(false);
		}
		return result;
	}

}
