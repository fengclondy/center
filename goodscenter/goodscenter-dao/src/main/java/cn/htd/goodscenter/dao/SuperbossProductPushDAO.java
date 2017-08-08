package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.SuperbossProductPush;
import cn.htd.goodscenter.dto.SuperbossProductPushDTO;

public interface SuperbossProductPushDAO {
	long querySuperbossProductPushListCount(SuperbossProductPushDTO superbossProductPushDTO);

	List<SuperbossProductPush> querySuperbossProductPushList(
			@Param("superbossProductPushDTO") SuperbossProductPushDTO superbossProductPushDTO,
			@SuppressWarnings("rawtypes") @Param("page") Pager pager);

	SuperbossProductPush querySuperbossProductPushInfo(
			@Param("superbossProductPushDTO") SuperbossProductPushDTO superbossProductPushDTO);

	void insertSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO);

	void updateSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO);

	void deleteSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO);

	Map<String, String> queryHtdRecommendProduct();
}