package cn.htd.membercenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.yiji.openapi.tool.YijifuGateway;
import com.yiji.openapi.tool.YijipayConstants;
import com.yiji.openapi.tool.util.Ids;

import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;

public class MemberPayToYijifuTast implements IScheduleTaskDealMulti<YijifuCorporateDTO> {
	protected static transient Logger logger = LoggerFactory.getLogger(MemberPayToYijifuTast.class);
	@Resource
	private MemberTaskDAO memberTaskDAO;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	private String key;// 会员注册私钥

	private String url;// 会员注册URL

	private String partnerId;// 会员注册URL

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 根据条件，查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskQueueList
	 *            当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<YijifuCorporateDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberPayToYijifuTast-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<YijifuCorporateDTO> dealDictionaryList = null;

		List<Long> ids = null;
		@SuppressWarnings("rawtypes")
		Pager pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<MemberDownDTO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				List<MemberBaseInfoDTO> memberList = memberTaskDAO.selectMemberToYijifu(condition, pager,
						ErpStatusEnum.PENDING.getValue());
				int size = memberList.size();
				if (size > 0) {
					ids = new ArrayList<Long>();
					for (int i = 0; i < size; i++) {
						ids.add(memberList.get(i).getId());
					}
					List<MemberCompanyInfo> companyList = memberTaskDAO.selectCompanyListByIds(ids);
					List<MemberLicenceInfo> licenceList = null;// memberTaskDAO.selectLicenceListListByIds(ids);

					dealDictionaryList = getMemberToYijifuErp(memberList, companyList, licenceList);
				}

			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "MemberPayToYijifuTast-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberPayToYijifuTast-selectTasks",
					JSONObject.toJSONString(dealDictionaryList));
		}
		return dealDictionaryList;
	}

	@Override
	public Comparator<YijifuCorporateDTO> getComparator() {
		return new Comparator<YijifuCorporateDTO>() {
			@Override
			public int compare(YijifuCorporateDTO o1, YijifuCorporateDTO o2) {
				String id1 = o1.getOutUserId();
				String id2 = o2.getOutUserId();
				return id1.compareTo(id2);
			}

		};
	}

	@Override
	public boolean execute(YijifuCorporateDTO[] tasks, String ownSign) throws Exception {
		int len = tasks.length;
		YijifuCorporateDTO dto = null;
		for (int i = 0; i < len; i++) {
			dto = tasks[i];
			Map<String, String> map = new HashMap<String, String>();
			JSONObject json = (JSONObject) JSONObject.toJSON(dto);
			Set<String> set = json.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String val = json.getString(key);
				map.put(key, val);
			}
			map.put(YijipayConstants.REQUEST_NO, Ids.oid());
			map.put(YijipayConstants.PARTNER_ID, partnerId);

			try {
				// 集成了签名和验签
				String responseStr = YijifuGateway.getOpenApiClientService().doPost(url, map, key);
				System.out.println("响应报文:" + responseStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private List<YijifuCorporateDTO> getMemberToYijifuErp(List<MemberBaseInfoDTO> memberList,
			List<MemberCompanyInfo> companyList, List<MemberLicenceInfo> licenceList) {
		List<YijifuCorporateDTO> resultList = new ArrayList<YijifuCorporateDTO>();
		YijifuCorporateDTO resultDTO = null;
		MemberBaseInfoDTO memberBaseInfoDTO = null;
		MemberCompanyInfo memberCompanyInfo = null;
		MemberLicenceInfo memberLicenceInfo = null;

		int size = memberList.size();
		for (int i = 0; i < size; i++) {
			resultDTO = new YijifuCorporateDTO();
			memberBaseInfoDTO = memberList.get(i);
			resultDTO.setOutUserId(memberBaseInfoDTO.getId().toString());
			resultDTO.setEmail(memberBaseInfoDTO.getContactEmail());
			if (memberBaseInfoDTO.getIsRealNameAuthenticated().intValue() == GlobalConstant.FLAG_YES) {
				resultDTO.setVerifyRealName("YES");
			} else {
				resultDTO.setVerifyRealName("NO");
			}

			int comSize = companyList.size();
			for (int j = 0; j < comSize; j++) {
				memberCompanyInfo = companyList.get(j);
				if (memberCompanyInfo.getMemberId().longValue() == memberBaseInfoDTO.getId().longValue()) {
					String companyName = memberCompanyInfo.getCompanyName();
					resultDTO.setComName(companyName);
					if ("1".equals(memberCompanyInfo.getBuyerSellerType())) {
						resultDTO.setCorporateUserType("INDIVIDUAL");
					} else {
						resultDTO.setCorporateUserType("BUSINESS");
					}
					resultDTO.setMobileNo(memberCompanyInfo.getArtificialPersonMobile());
					LegalPerson legalPerson = new LegalPerson();
					legalPerson.setAddress(memberCompanyInfo.getLocationDetail());
					legalPerson.setCertNo(memberCompanyInfo.getArtificialPersonIdcard());
					legalPerson.setEmail(memberBaseInfoDTO.getContactEmail());
					legalPerson.setMobileNo(memberCompanyInfo.getArtificialPersonMobile());
					legalPerson.setRealName(memberCompanyInfo.getArtificialPersonName());
					resultDTO.setLegalPerson(legalPerson);
					break;
				}
			}

			int invSize = licenceList.size();
			for (int j = 0; j < invSize; j++) {
				memberLicenceInfo = licenceList.get(j);
				if (memberLicenceInfo.getMemberId().intValue() == memberBaseInfoDTO.getId().intValue()) {
					resultDTO.setLicenceNo(memberLicenceInfo.getBuyerBusinessLicenseId());
					resultDTO.setOrganizationCode(memberLicenceInfo.getOrganizationId());
					resultDTO.setTaxAuthorityNo(memberLicenceInfo.getTaxManId());

					break;
				}

			}
			resultList.add(resultDTO);
		}
		return resultList;
	}

}
