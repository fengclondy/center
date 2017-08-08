package cn.htd.basecenter.bi;

import cn.htd.basecenter.dto.BaseValueModelDTO;
import cn.htd.common.dao.orm.BaseDAO;

import java.util.List;

/**
 * Created by thinkpad on 2016/12/20.
 */
public interface BaseValueModelDAO extends BaseDAO<BaseValueModelDTO> {

    List<BaseValueModelDTO> selectNewValueBySellerId(String companyCode);
}
