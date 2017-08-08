package cn.htd.goodscenter.dao.productplus;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.productplus.SellerCategoryBrandShield;
import cn.htd.goodscenter.dto.productplus.SellerCategoryBrandShieldDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellerCategoryBrandShieldMapper {

    List<SellerCategoryBrandShieldDTO> selectShieldSellerCategoryBrandList(@Param("entity") SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, @Param("page") Pager pager);

    Long selectShieldSellerCategoryBrandListCount(@Param("entity") SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO);

    List<SellerCategoryBrandShieldDTO> selectSellerNoShieldCategoryBrandList(@Param("entity") SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, @Param("page") Pager pager);

    Long selectSellerNoShieldCategoryBrandListCount(@Param("entity") SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO);

    SellerCategoryBrandShield select(SellerCategoryBrandShield sellerCategoryBrandShield);

    int deleteByPrimaryKey(Long categoryBrandShieldId);

    int insert(SellerCategoryBrandShield record);

    int insertSelective(SellerCategoryBrandShield record);

    SellerCategoryBrandShield selectByPrimaryKey(Long categoryBrandShieldId);

    int updateByPrimaryKeySelective(SellerCategoryBrandShield record);

    int updateByPrimaryKey(SellerCategoryBrandShield record);
}