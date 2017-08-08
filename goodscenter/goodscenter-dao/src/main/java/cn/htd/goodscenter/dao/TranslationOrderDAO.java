package cn.htd.goodscenter.dao;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.TranslationOrderDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TranslationOrderDAO extends BaseDAO<TranslationOrderDTO> {

	public List<TranslationOrderDTO> queryPage(@Param("pager") Pager pager, @Param("translationOrder") TranslationOrderDTO translationOrder);

	public Long queryPageCount(@Param("translationOrder") TranslationOrderDTO translationOrder);

	public TranslationOrderDTO findById(Long id);

	public Integer update(TranslationOrderDTO translationOrder);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public TranslationOrderDTO findByTranslationOrderDTO(@Param("translationOrder") TranslationOrderDTO translationOrder);
}
