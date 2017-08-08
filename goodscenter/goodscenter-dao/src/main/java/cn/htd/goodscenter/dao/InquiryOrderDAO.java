package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.InquiryOrderDTO;

import org.apache.ibatis.annotations.Param;

public interface InquiryOrderDAO extends BaseDAO<InquiryOrderDTO> {

	public List<InquiryOrderDTO> queryPage(@Param("pager") Pager pager, @Param("inquiryOrder") InquiryOrderDTO inquiryOrder);

	public Long queryPageCount(@Param("inquiryOrder") InquiryOrderDTO inquiryOrder);

	public InquiryOrderDTO findById(Long id);

	public Integer update(InquiryOrderDTO inquiryOrder);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public InquiryOrderDTO findByInquiryOrderDTO(@Param("inquiryOrder") InquiryOrderDTO inquiryOrder);
}
