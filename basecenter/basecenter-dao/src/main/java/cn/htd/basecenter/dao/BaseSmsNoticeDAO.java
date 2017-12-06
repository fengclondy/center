package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.dto.BaseSmsNoticeDTO;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseSmsNoticeDAO extends BaseDAO<BaseSmsNoticeDTO>{

	public void deleteBaseSmsNotice(@Param("noticeDTO") BaseSmsNoticeDTO noticeDTO);
	
	public List<BaseSmsNoticeDTO> queryBaseSmsNotice(@Param("noticeDTO") BaseSmsNoticeDTO noticeDTO, @Param("page") Pager<BaseSmsNoticeDTO> pager);
	
	public int queryBaseSmsNoticeCount(@Param("noticeDTO") BaseSmsNoticeDTO noticeDTO);
}
