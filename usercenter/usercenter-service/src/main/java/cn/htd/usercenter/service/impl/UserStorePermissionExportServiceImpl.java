package cn.htd.usercenter.service.impl;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dao.UserMallResourceMybatisDAO;
import cn.htd.usercenter.dto.UserMallResourceDTO;
import cn.htd.usercenter.service.UserStorePermissionExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/6.
 */
@Service("userStorePermissionExportService")
public class UserStorePermissionExportServiceImpl implements UserStorePermissionExportService {

    private final static Logger logger = LoggerFactory.getLogger(UserStorePermissionExportServiceImpl.class);
    @Resource
    private UserMallResourceMybatisDAO userMallResourceMybatisDAO;
    @Override
    public ExecuteResult<List<UserMallResourceDTO>> queryParentResourceList(Integer type, Integer modularType) {
        ExecuteResult<List<UserMallResourceDTO>> result = new ExecuteResult<List<UserMallResourceDTO>>();

        try {
            List<UserMallResourceDTO> list = userMallResourceMybatisDAO.queryParentResourceList(type, modularType);
            UserMallResourceDTO umrDTO = new UserMallResourceDTO();
            for (UserMallResourceDTO userMallResourceDTO : list) {
                umrDTO.setParentId(userMallResourceDTO.getId());
                List<UserMallResourceDTO> list1 = userMallResourceMybatisDAO.selectList(umrDTO, null);
                userMallResourceDTO.setUserMallResourceList(list1);
            }
            result.setResult(list);
            result.setResultMessage("success");
        } catch (Exception e) {
            result.getErrorMessages().add(e.getMessage());
            result.setResultMessage("error");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<UserMallResourceDTO> queryResourceNameById(Long id) {
        ExecuteResult<UserMallResourceDTO> result = new ExecuteResult<UserMallResourceDTO>();
        try {
            UserMallResourceDTO dto = userMallResourceMybatisDAO.queryById(id);
            result.setResult(dto);
            result.setResultMessage("success");
        } catch (Exception e) {
            result.getErrorMessages().add(e.getMessage());
            result.setResultMessage("error");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }


}
