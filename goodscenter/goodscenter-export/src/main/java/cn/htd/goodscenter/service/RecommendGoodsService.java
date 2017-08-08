package cn.htd.goodscenter.service;

import java.util.List;

public interface RecommendGoodsService {
	List<Long> getItemIds(int cid,int type);
}
