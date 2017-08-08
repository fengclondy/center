package cn.htd.searchcenter.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.PriceDTO;

public interface PriceInfoDAO {

	public BigDecimal queryJDVipPrice(@Param("itemId") Long itemId);

	public List<PriceDTO> queryBuyerGradePrice(@Param("itemId") Long itemId,@Param("isBoxFlag") Integer isBoxFlag);

	public List<PriceDTO> queryGroupPrice(@Param("itemId") Long itemId,@Param("isBoxFlag") Integer isBoxFlag);

	public List<PriceDTO> queryAreaPrice(@Param("itemId") Long itemId,@Param("isBoxFlag") Integer isBoxFlag);
}
