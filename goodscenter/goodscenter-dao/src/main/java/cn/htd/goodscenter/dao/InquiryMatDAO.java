package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.InquiryMatDTO;

import org.apache.ibatis.annotations.Param;

public interface InquiryMatDAO extends BaseDAO<InquiryMatDTO> {

	public List<Map> queryPage(@Param("pager") Pager pager, @Param("inquiryMat") InquiryMatDTO inquiryMat);

	public Long queryPageCount(@Param("inquiryMat") InquiryMatDTO inquiryMat);

	public InquiryMatDTO findById(Long id);

	public Integer update(InquiryMatDTO inquiryMat);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public InquiryMatDTO findByInquiryMatDTO(@Param("inquiryMat") InquiryMatDTO inquiryMat);
}
