package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.AthenaEventDTO;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyMemberSearchDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;

/**
 * 会员管理——》我的会员
 * 
 * @author thinkpad
 *
 */
public interface MyMemberService {

	/**
	 * 查询我的会员/担保会员列表
	 * 
	 * @param page
	 * @param sellerId
	 * @param memberSearch
	 * @param type
	 *            -》会员类型：1，非会员，2正式会员，3，担保会员
	 * @return
	 */
	public ExecuteResult<DataGrid<MyMemberDTO>> selectMyMemberList(Pager page, Long sellerId,
			MyMemberSearchDTO memberSearch, String type);

	/**
	 * 导出我的会员/担保会员列表
	 * 
	 * @param vendorId
	 * @param memberSearch
	 * @param type
	 *            -》会员类型：1，非会员，2正式会员，3，担保会员
	 * @return
	 */
	public ExecuteResult<DataGrid<MyMemberDTO>> exportMyMemberList(Long vendorId, MyMemberSearchDTO memberSearch,
			String type);

	/**
	 * 查询非会员列表
	 * 
	 * @param page
	 * @param vendorId
	 * @param memberSearch
	 * @param type
	 *            -》会员类型：1，非会员，2正式会员，3，担保会员
	 * @return
	 */
	public ExecuteResult<DataGrid<MyNoMemberDTO>> selectNoMemberList(Pager page, Long vendorId,
			MyMemberSearchDTO memberSearch, String type);

	/**
	 * 导出非会员列表
	 * 
	 * @param vendorId
	 * @param memberSearch
	 * @param type-》会员类型：1，非会员，2正式会员，3，担保会员
	 * @return
	 */
	public ExecuteResult<DataGrid<MyNoMemberDTO>> exportNoMemberList(Long vendorId, MyMemberSearchDTO memberSearch,
			String type);

	/**
	 * 非会员注册申请
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public ExecuteResult<String> saveNoMemberRegistInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 保存修改非会员基本信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public ExecuteResult<String> modifyNoMemberInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员名称修改审核
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public ExecuteResult<Boolean> saveUnMemberCompanyVerify(MyNoMemberDTO myNoMemberDto);

	/**
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public ExecuteResult<MyNoMemberDTO> getUnMemberModify(Long memberId);

	/**
	 * 计算会员待办事项
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public ExecuteResult<Long> calculationMemberDealEvent(Long sellerId);

	/**
	 * 经营关系事项
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<Long> calculationBusinessRelationDealEvent(Long sellerId);

	/**
	 * 运营代办事项
	 * 
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<List<AthenaEventDTO>> calculationAthenaDealEvent();

	public ExecuteResult<Boolean> getNoMemberName(MyNoMemberDTO myNoMemberDTO);
}
