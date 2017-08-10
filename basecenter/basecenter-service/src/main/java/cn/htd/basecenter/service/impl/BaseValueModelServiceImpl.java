package cn.htd.basecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.bi.BaseValueModelDAO;
import cn.htd.basecenter.dto.BaseValueModelDTO;
import cn.htd.basecenter.service.BaseValueModelService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.service.MemberBaseInfoService;

/**
 * Created by thinkpad on 2016/12/20.
 */
@Service("baseValueModelService")
public class BaseValueModelServiceImpl implements BaseValueModelService {
	private final static Logger logger = LoggerFactory.getLogger(BaseValueModelServiceImpl.class);

	@Resource
	private BaseValueModelDAO baseValueModelDAO;
	
//	@Resource
//	private MemberCompanyInfoDao memberCompanyInfoDao;
	
	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	/**
	 * 根据sellerId检索最大周期数据
	 */
	@Override
	public DataGrid<BaseValueModelDTO> queryNewValueBySellerId(Long sellerId) {
		DataGrid<BaseValueModelDTO> dataGrid = new DataGrid<BaseValueModelDTO>();
		try {
			//String companyCode=memberCompanyInfoDao.querySellerIdByCode(sellerId);
			String companyCode="";
			ExecuteResult<String> comResult=memberBaseInfoService.queryCompanyCodeBySellerId(sellerId);
			if(comResult!=null&&comResult.isSuccess()){
				companyCode=comResult.getResult();
			}
			if(StringUtils.isEmpty(companyCode)){
				logger.info("BaseValueModelServiceImpl::queryNewValueBySellerId:companyCode is empty");
				return dataGrid;
			}
			List<BaseValueModelDTO> list = baseValueModelDAO.selectNewValueBySellerId(companyCode);
			dataGrid.setRows(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dataGrid;
	}

	@Override
	public DataGrid<BaseValueModelDTO> queryNewValueByCompanyCode(
			String companyCode) {
		DataGrid<BaseValueModelDTO> dataGrid = new DataGrid<BaseValueModelDTO>();
		try {
			if(StringUtils.isEmpty(companyCode)){
				logger.info("BaseValueModelServiceImpl::queryNewValueByCompanyCode:companyCode is empty");
				return dataGrid;
			}
			List<BaseValueModelDTO> list = baseValueModelDAO.selectNewValueBySellerId(companyCode);
			dataGrid.setRows(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dataGrid;
	}

}
