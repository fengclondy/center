package cn.htd.goodscenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.goodscenter.dao.RecommendGoodsDAO;
import cn.htd.goodscenter.service.RecommendGoodsService;

@Service("recommendGoodsService")
public class RecommendGoodsServiceImpl implements RecommendGoodsService{

	@Resource
	private RecommendGoodsDAO recommendGoodsDAO;
	
	@Override
	public List<Long> getItemIds(int cid,int type) {
		return recommendGoodsDAO.getItemIds(cid,type);
	}

}
