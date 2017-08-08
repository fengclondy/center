package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.CreditsExchangeDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Description: [积分兑换dao层]
 * </p>
 */
public interface CreditsExchangeDAO extends BaseDAO<CreditsExchangeDTO> {

	public List<CreditsExchangeDTO> queryCreditsById(@Param("entity") CreditsExchangeDTO dto);
}
