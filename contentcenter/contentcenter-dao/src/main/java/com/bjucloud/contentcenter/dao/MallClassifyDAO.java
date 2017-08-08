package com.bjucloud.contentcenter.dao;


import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.MallClassify;
import com.bjucloud.contentcenter.dto.MallClassifyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallClassifyDAO extends BaseDAO<MallClassify> {

	public List<MallClassifyDTO> queryMallCassifyList(@Param("mallClassifyDTO") MallClassifyDTO mallClassifyDTO, @Param("page") Pager page);

	public long queryMallCassifyCount(@Param("mallClassifyDTO") MallClassifyDTO mallClassifyDTO);

	public int addMallCassify(MallClassify mallClassify);

	public int modifyStatusById(int id, int status);

	public void delete(int id);
}
