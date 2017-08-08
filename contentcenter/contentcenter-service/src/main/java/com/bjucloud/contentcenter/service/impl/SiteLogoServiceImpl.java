package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dao.SiteLogoDAO;
import com.bjucloud.contentcenter.dto.SiteLogoDTO;
import com.bjucloud.contentcenter.service.SiteLogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/22.
 */
@Service("siteLogoService")
public class SiteLogoServiceImpl implements SiteLogoService{
    private static final Logger logger = LoggerFactory.getLogger(SiteLogoServiceImpl.class);

    @Resource
    private SiteLogoDAO siteLogoDAO;

    @Override
    public ExecuteResult<String> save(SiteLogoDTO record) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try {
            if (record.getId() == null){
                siteLogoDAO.delete(null);
                siteLogoDAO.insert(record);
            }else {
                siteLogoDAO.updateBySelect(record);
            }
            result.setResult("1");
            result.setResultMessage("Logo新增成功");
        }catch (Exception e){
            logger.error("执行方法：【save】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<SiteLogoDTO> getLogo() {
        ExecuteResult<SiteLogoDTO> executeResult = new ExecuteResult<SiteLogoDTO>();
        try {
            List<SiteLogoDTO> logoDTOList = siteLogoDAO.findAll();
            if (logoDTOList != null && logoDTOList.size() > 0) {
                SiteLogoDTO logoDTO = logoDTOList.get(0);
                executeResult.setResult(logoDTO);
            }
            executeResult.setResultMessage("Logo查询成功");
        } catch (Exception e) {
            logger.error("执行方法：【getLogo】报错！{}", e);
            executeResult.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return executeResult;
    }
}
