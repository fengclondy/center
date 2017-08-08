package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.marketcenter.domain.PromotionGroupSignup;
import cn.htd.marketcenter.dto.PromotionGroupSignUpDTO;
import cn.htd.marketcenter.dto.PromotionGroupSignUpRepairDTO;

public interface PromotionGroupDAO {

	public void insertPromotionGroupSignUpInfo(PromotionGroupSignUpDTO promotionGroupSignUpDTO);

	public List<PromotionGroupSignup> queryPromotionGroupSignUpList(PromotionGroupSignUpDTO promotionGroupSignUpDTO);

	public List<PromotionGroupSignup> queryPromotionGroupSignUpCount(PromotionGroupSignUpDTO promotionGroupSignUpDTO);
	
	public int repairPromotionGroupSignupInfo(PromotionGroupSignUpRepairDTO promotionGroupSignUpRepairDTO);
}
