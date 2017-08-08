package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dao.LoginPageDAO;
import com.bjucloud.contentcenter.dto.LoginPageDTO;
import com.bjucloud.contentcenter.service.LoginPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by peanut on 2017/2/13.
 */

@Service("loginPageService")
public class LoginPageServiceImpl implements LoginPageService {

    private static final Logger logger = LoggerFactory.getLogger(LoginPageServiceImpl.class);

    @Resource
    private LoginPageDAO loginPageDAO;
    @Override
    public ExecuteResult<LoginPageDTO> getLogo() {
        ExecuteResult<LoginPageDTO> executeResult = new ExecuteResult<LoginPageDTO>();
        try {
            List<LoginPageDTO> logoDTOList = loginPageDAO.findAll();
            if (logoDTOList != null && logoDTOList.size() > 0) {
                LoginPageDTO logoDTO = logoDTOList.get(0);
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

    @Override
    public ExecuteResult<String> save(LoginPageDTO record) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try {
            //loginPageDAO.delete(null);
            loginPageDAO.insert(record);
            result.setResult("1");
            result.setResultMessage("slogo新增成功");
        }catch (Exception e){
            logger.error("执行方法：【save】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }


}
