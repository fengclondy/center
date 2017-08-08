package cn.htd.membercenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberImportDAO;
import cn.htd.membercenter.dto.MemberImportFailInfoDTO;
import cn.htd.membercenter.dto.MemberImportInfoDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.service.MemberImportService;

@Service("memberImportService")
public class MemberImportServiceImpl implements MemberImportService {

	private final static Logger logger = LoggerFactory.getLogger(MemberImportServiceImpl.class);

	@Resource
	private MemberImportDAO memberImportDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberImportService#memberImport(java.util.
	 * List)
	 */
	@Override
	public ExecuteResult<MemberImportInfoDTO> memberImport(List<MemberImportFailInfoDTO> impInfoList) {
		ExecuteResult<MemberImportInfoDTO> rs = new ExecuteResult<MemberImportInfoDTO>();
		int successCount = 0;
		int failCount = 0;
		List<MemberImportSuccInfoDTO> sList = new ArrayList<MemberImportSuccInfoDTO>();
		List<MemberImportFailInfoDTO> fList = new ArrayList<MemberImportFailInfoDTO>();
		try {
			MemberImportInfoDTO iid = new MemberImportInfoDTO();
			if (impInfoList == null || impInfoList.size() == 0) {
				rs.setResultMessage("fail");
				rs.addErrorMessage("请确认导入列表不为空");
			} else {
				int count = impInfoList.size();
				if (count > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {// 导入最大条数限制
					rs.setResultMessage("导入最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
				} else {
					for (MemberImportFailInfoDTO memberImportFailInfoDTO : impInfoList) {
						MemberImportSuccInfoDTO isd = memberImportDao.queryMemberInfo(memberImportFailInfoDTO);
						if (isd != null) {
							sList.add(isd);
							successCount++;
						} else {
							fList.add(memberImportFailInfoDTO);
							failCount++;
						}
					}
				}
			}
			iid.setFailCount(failCount);
			iid.setSuccCount(successCount);
			iid.setFailInfoList(fList);
			iid.setSuccInfoList(sList);
			rs.setResult(iid);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("更新异常");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberInfo=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberImportService#sellerImport(java.util.
	 * List)
	 */
	@Override
	public ExecuteResult<MemberImportInfoDTO> sellerImport(List<MemberImportFailInfoDTO> impInfoList) {
		ExecuteResult<MemberImportInfoDTO> rs = new ExecuteResult<MemberImportInfoDTO>();
		int successCount = 0;
		int failCount = 0;
		List<MemberImportSuccInfoDTO> sList = new ArrayList<MemberImportSuccInfoDTO>();
		List<MemberImportFailInfoDTO> fList = new ArrayList<MemberImportFailInfoDTO>();
		try {
			MemberImportInfoDTO iid = new MemberImportInfoDTO();
			if (impInfoList == null || impInfoList.size() == 0) {
				rs.setResultMessage("fail");
				rs.addErrorMessage("请确认导入列表不为空");
			} else {
				int count = impInfoList.size();
				if (count > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {// 导入最大条数限制
					rs.setResultMessage("导入最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
				} else {
					for (MemberImportFailInfoDTO memberImportFailInfoDTO : impInfoList) {
						MemberImportSuccInfoDTO isd = memberImportDao.querySellerInfo(memberImportFailInfoDTO);
						if (isd != null) {
							sList.add(isd);
							successCount++;
						} else {
							fList.add(memberImportFailInfoDTO);
							failCount++;
						}
					}
				}
			}
			iid.setFailCount(failCount);
			iid.setSuccCount(successCount);
			iid.setFailInfoList(fList);
			iid.setSuccInfoList(sList);
			rs.setResult(iid);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("更新异常");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberInfo=" + e);
		}
		return rs;
	}

}