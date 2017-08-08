package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RecommendGoodsDAO {

	List<Long> getItemIds(@Param("cid") int cid,@Param("type") int type);
}
