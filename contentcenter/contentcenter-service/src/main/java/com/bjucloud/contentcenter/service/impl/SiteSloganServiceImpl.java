package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dao.SiteLogoDAO;
import com.bjucloud.contentcenter.dao.SiteSloganDAO;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;
import com.bjucloud.contentcenter.dto.SiteSloganDTO;
import com.bjucloud.contentcenter.service.SiteLogoService;
import com.bjucloud.contentcenter.service.SiteSloganService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/22.
 */
@Service("siteSloganService")
public class SiteSloganServiceImpl implements SiteSloganService{
    private static final Logger logger = LoggerFactory.getLogger(SiteSloganServiceImpl.class);

    @Resource
    private SiteSloganDAO siteSloganDAO;

    @Override
    public ExecuteResult<String> save(SiteSloganDTO record) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try {
           // siteSloganDAO.delete(null);
            siteSloganDAO.insert(record);
            result.setResult("1");
            result.setResultMessage("slogo新增成功");
        }catch (Exception e){
            logger.error("执行方法：【save】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<SiteSloganDTO> getSlogan() {
        ExecuteResult<SiteSloganDTO> executeResult = new ExecuteResult<SiteSloganDTO>();
        try {
            List<SiteSloganDTO> logoDTOList = siteSloganDAO.findAll();
            if (logoDTOList != null && logoDTOList.size() > 0) {
                SiteSloganDTO logoDTO = logoDTOList.get(0);
                executeResult.setResult(logoDTO);
            }
            executeResult.setResultMessage("slogo查询成功");
        } catch (Exception e) {
            logger.error("执行方法：【getSlogan】报错！{}", e);
            executeResult.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return executeResult;
    }
}
