package cn.htd.goodscenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.TranslationInfoDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TranslationInfoDAO extends BaseDAO<TranslationInfoDTO> {

	public List<TranslationInfoDTO> queryPage(@Param("pager") Pager pager, @Param("translationInfo") TranslationInfoDTO translationInfo);

	public Long queryPageCount(@Param("translationInfo") TranslationInfoDTO translationInfo);

	public TranslationInfoDTO findById(Long id);

	public Integer update(TranslationInfoDTO translationInfo);

	public void delete(@Param("codes") List<String> codes);

	public List<TranslationInfoDTO> findAll(@Param("translationInfo") TranslationInfoDTO translationInfo);

	public TranslationInfoDTO findByTranslationInfoDTO(@Param("translationInfo") TranslationInfoDTO translationInfo);

	public List<Map> queryTranslationInfoPager(@Param("pager") Pager pager, @Param("translationInfo") TranslationInfoDTO translationInfo);

	public Long queryTranslationInfoPagerCount(@Param("translationInfo") TranslationInfoDTO translationInfo);
}
