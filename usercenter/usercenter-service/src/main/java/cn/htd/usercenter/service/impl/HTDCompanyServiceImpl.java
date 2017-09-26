package cn.htd.usercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dao.HTDCompanyDAO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.service.HTDCompanyService;

@Service("htdCompanyService")
public class HTDCompanyServiceImpl implements HTDCompanyService {
    private final static Logger logger = LoggerFactory.getLogger(HTDCompanyServiceImpl.class);

    @Resource
    private HTDCompanyDAO hTDCompanyDAO;

    @Override
    public ExecuteResult<DataGrid<HTDCompanyDTO>> selectEmployeeByTypeOrComplayId(HTDCompanyDTO htdCompanyDTO) {
        ExecuteResult<DataGrid<HTDCompanyDTO>> rs = new ExecuteResult<DataGrid<HTDCompanyDTO>>();
        try {
            DataGrid<HTDCompanyDTO> dg = new DataGrid<HTDCompanyDTO>();
            List<HTDCompanyDTO> hTDCompanyList = hTDCompanyDAO.selectEmployeeByTypeOrComplayId(htdCompanyDTO);
            try {
                if (hTDCompanyList != null) {
                    dg.setRows(hTDCompanyList);
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
            logger.error("HTDCompanyExportServiceImpl----->selectEmployeeByTypeOrComplayId=" + e);
        }
        return rs;
    }

	@Override
	public ExecuteResult<List<String>> selectSubCompaniesByParentName(
			String name) {
		ExecuteResult<List<String>> result=new ExecuteResult<List<String>>();
		if(StringUtils.isEmpty(name)){
			result.setResultMessage("name为空");
			result.setCode("0");
			return result;
		}
		List<String>  subComCodeList=hTDCompanyDAO.querySubCompaniesByParentName(name);
		result.setCode("1");
		result.setResult(subComCodeList);
		return result;
	}

	@Override
	public ExecuteResult<List<HTDCompanyDTO>> selectParentNameBySubCode(List<String> subComCodeList) {
		 ExecuteResult<List<HTDCompanyDTO>> result= new  ExecuteResult<List<HTDCompanyDTO>>();
		if(CollectionUtils.isEmpty(subComCodeList)){
			result.setResultMessage("subComCodeList为空");
			result.setCode("0");
			return result;
		}
		List<HTDCompanyDTO>  resultList=hTDCompanyDAO.queryParentNameBySubCode(subComCodeList);
		result.setCode("1");
		result.setResult(resultList);
		return result;
	}

	@Override
	public ExecuteResult<List<String>> selectSubCompaniesCodeByName(String name) {
		ExecuteResult<List<String>> result=new ExecuteResult<List<String>>();
		if(StringUtils.isEmpty(name)){
			result.setResultMessage("name为空");
			result.setCode("0");
			return result;
		}
		List<String>  subComCodeList=hTDCompanyDAO.querySubCompaniesCodeByName(name);
		result.setCode("1");
		result.setResult(subComCodeList);
		return result;
	}


}
