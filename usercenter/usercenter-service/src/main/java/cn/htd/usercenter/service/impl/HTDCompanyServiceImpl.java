package cn.htd.usercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

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


}
