package cn.htd.membercenter.service;

import cn.htd.membercenter.dto.ErpSellerupDTO;

public interface ErpService {
	/**
	 * 内部供应商ERP上行服务
	 * 
	 * @param dto
	 * @return
	 */
	public boolean saveErpSellerup(ErpSellerupDTO dto);

	/**
	 * 内部供应商上行日志
	 * 
	 * @param dto
	 * @return
	 */
	public boolean saveErpUpLog(ErpSellerupDTO dto);
}
