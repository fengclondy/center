package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.InquiryInfoDTO;

import org.apache.ibatis.annotations.Param;

public interface InquiryInfoDAO extends BaseDAO<InquiryInfoDTO> {

	public List<InquiryInfoDTO> queryPage(@Param("pager") Pager pager, @Param("inquiryInfo") InquiryInfoDTO inquiryInfo);

	public Long queryPageCount(@Param("inquiryInfo") InquiryInfoDTO inquiryInfo);

	public InquiryInfoDTO findById(Long id);

	public Integer update(InquiryInfoDTO inquiryInfo);

	public void delete(@Param("codes") List<String> codes);

	public List<InquiryInfoDTO> findAll(@Param("inquiryInfo") InquiryInfoDTO inquiryInfo);

	public InquiryInfoDTO findByInquiryInfoDTO(@Param("inquiryInfo") InquiryInfoDTO inquiryInfo);

	public List<Map> queryInquiryInfoPager(@Param("pager") Pager pager, @Param("inquiryInfo") InquiryInfoDTO inquiryInfo);

	public Long queryInquiryInfoPagerCount(@Param("inquiryInfo") InquiryInfoDTO inquiryInfo);
}
