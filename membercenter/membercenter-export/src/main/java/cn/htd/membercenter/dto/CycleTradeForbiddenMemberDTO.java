package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 互为上下游禁止交易
 * 
 * @author xmz
 * @CreateDate 2018-01-13
 * 
 */
public class CycleTradeForbiddenMemberDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894197709243941262L;

	private String id;            	
	private String memberCode;    //会员编码
	private String memberName;    //会员名称
	private String forbiddenType; //1 禁止销售 2 禁止采购
	private String createId;      //创建人id
	private String createName;    //创建人名称
	private String modifyId;      //修改人id
	private String modifyName;    //修改人名称
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getForbiddenType() {
		return forbiddenType;
	}

	public void setForbiddenType(String forbiddenType) {
		this.forbiddenType = forbiddenType;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}
