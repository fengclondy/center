package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.bjucloud.contentcenter.dao.SpecialResourceDAO;
import com.bjucloud.contentcenter.dto.SpecialResourceDTO;
import com.bjucloud.contentcenter.service.SpecialResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/13.
 */
@Service("specialResourceService")
public class SpecialResourceServiceImpl implements SpecialResourceService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialResourceServiceImpl.class);

    @Resource
    private SpecialResourceDAO specialResourceDAO;


    @Override
    public DataGrid<SpecialResourceDTO> queryListByCondition(SpecialResourceDTO record, Pager page) {
        DataGrid<SpecialResourceDTO> dtoDataGrid = new DataGrid<SpecialResourceDTO>();
        try{
            Long count = specialResourceDAO.selectCountByCondition(record);
            if(count > 0){
                List<SpecialResourceDTO> list = specialResourceDAO.selectListByCondition(record,page);
                dtoDataGrid.setRows(list);
                dtoDataGrid.setTotal(count);
            }
        }catch (Exception e){
            logger.error("执行方法【queryListByCondition】报错! {}", e);
            throw new RuntimeException(e);
        }
        return dtoDataGrid;
    }

    @Override
    public ExecuteResult<String> delById(Long id) {
        ExecuteResult<String> er = new ExecuteResult<String>();
        try {
            int delcount = specialResourceDAO.delete(id);
            if (delcount > 0) {
                er.setResult("操作成功");
                er.setResultMessage("success");
            } else {
                er.addErrorMsg("操作失败");
            }
        } catch (Exception e) {
            er.addErrorMsg("操作失败");
        }
        return er;
    }

    @Override
    public SpecialResourceDTO queryById(Long id) {
        SpecialResourceDTO dto = this.specialResourceDAO.selectById(id);
        return dto;
    }

    @Override
    public ExecuteResult<String> modify(SpecialResourceDTO specialResourceDTO) {
        ExecuteResult<String> er = new ExecuteResult<String>();
        SpecialResourceDTO dto = null;
        Date date = null;
        try {
            if (StringUtils.isBlank(specialResourceDTO.getId().toString())){
                er.addErrorMessage("参数ID不能为空! ");
                return er;
            }

            dto = this.specialResourceDAO.selectById(specialResourceDTO.getId());
            if (dto == null){
                er.addErrorMessage("该静态资源不存在! ");
                return er;
            }

            this.specialResourceDAO.update(specialResourceDTO);
            er.setResult("success");
        } catch (Exception e) {
            logger.error("执行方法【modifyStatus】报错！{}", e);
            er.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return er;
    }

    @Override
    public ExecuteResult<String> save(SpecialResourceDTO specialResourceDTO) {
        ExecuteResult<String> er = new ExecuteResult<String>();
        try {
            this.specialResourceDAO.insert(specialResourceDTO);
            er.setResult("success");
        } catch (Exception e) {
            logger.error("执行方法【save】报错！{}", e);
            er.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return er;
    }

	@Override
	public ExecuteResult<SpecialResourceDTO> queryByLink(String topicCode) {
		ExecuteResult<SpecialResourceDTO> er = new ExecuteResult<SpecialResourceDTO>();
        try {
        	SpecialResourceDTO ss =  specialResourceDAO.queryByLink(topicCode);
            er.setResult(ss);
        } catch (Exception e) {
            logger.error("执行方法【queryByLink】报错！{}", e);
            er.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return er;
	}
}
