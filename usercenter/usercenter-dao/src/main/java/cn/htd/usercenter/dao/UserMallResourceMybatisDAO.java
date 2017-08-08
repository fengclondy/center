package cn.htd.usercenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.UserMallResourceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by thinkpad on 2017/2/6.
 */
public interface UserMallResourceMybatisDAO extends BaseDAO<UserMallResourceDTO>{
    /**
     *
     * <p>
     * Discription:[根据类型 查询所有父级资源]
     * </p>
     *
     * @param type
     * @param modularType
     */
    List<UserMallResourceDTO> queryParentResourceList(@Param("type") Integer type,
                                                      @Param("modularType") Integer modularType);

    /**
     *
     * <p>
     * Discription:[根据用户ID查询出用户拥有的资源]
     * </p>
     */
    List<UserMallResourceDTO> selectMallResourceById(@Param("uid") Long uid, @Param("modularType") Long modularType);
}
