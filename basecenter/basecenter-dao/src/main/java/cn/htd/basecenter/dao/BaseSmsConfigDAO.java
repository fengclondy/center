package cn.htd.basecenter.dao;

import java.util.List;

import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseSmsConfigDAO extends BaseDAO<BaseSmsConfigDTO> {

    /**
     * 获取短信/邮件配置
     *
     * @return
     */
    public void updateSmsConfigUsedFlag(BaseSmsConfigDTO configDTO);

    /**
     * 根据类型查询配置信息
     *
     * @param configCondition
     * @return
     */
    public List<BaseSmsConfigDTO> queryByTypeCode(BaseSmsConfigDTO configCondition);

    /**
     * 根据类型和通道编码取得配置信息
     *
     * @param configCondition
     * @return
     */
    public BaseSmsConfigDTO queryByChannelCode(BaseSmsConfigDTO configCondition);
}