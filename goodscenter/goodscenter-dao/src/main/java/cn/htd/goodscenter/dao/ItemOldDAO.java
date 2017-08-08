package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ItemOldDTO;
import cn.htd.goodscenter.dto.indto.ItemOldSeachInDTO;

public interface ItemOldDAO extends BaseDAO<ItemOldDTO> {

	void updateStatus(@Param("itemId") Long itemId, @Param("status") Long status, @Param("comment") String comment, @Param("platformUserId") String platformUserId);

	List<ItemOldDTO> querySeachItemOldList(@Param("entity") ItemOldSeachInDTO itemOldSeachInDTO, @Param("page") Pager page);

}
