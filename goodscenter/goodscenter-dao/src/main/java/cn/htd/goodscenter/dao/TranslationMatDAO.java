package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.TranslationMatDTO;

import org.apache.ibatis.annotations.Param;

public interface TranslationMatDAO extends BaseDAO<TranslationMatDTO> {

	public List<Map> queryPage(@Param("pager") Pager pager, @Param("translationMat") TranslationMatDTO translation);

	public Long queryPageCount(@Param("translationMat") TranslationMatDTO translation);

	public TranslationMatDTO findById(Long id);

	public Integer update(TranslationMatDTO translation);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public TranslationMatDTO findByTranslationMatDTO(@Param("translationMat") TranslationMatDTO translation);
}
