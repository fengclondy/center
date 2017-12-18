package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MemberGroupDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	// 会员店等级
	private String buyerGrade;
	// 主键(分组ID)
	private long groupId;
	// 会员编码
	private long buyerId;
	// 分组名称
	private String name;
	// 区分会员和分组类型
	private String type;
	// 备注
	private String comment;
	// 商家编码
	private String sellerId;
	// 展示会员数量
	private int memberCount;
	// 会员编码
	private String memberCode;
	// 操作的会员编码
	private String[] memberIdArr;
	// 删除分组编码拼接字符串
	private String groupIds;
	// 新增删除会员编码拼接字符串
	private String buyerIds;
	// 会员所在省
	private String locationProvince;
	// 会员所在城市
	private String locationCity;
	// 会员所在区
	private String locationCounty;
	// 公司名称
	private String companyName;
	private String deleteFlag;
	private String createId;
	private String createName;
	private Date createTime;
	private String modifyId;
	private String modifyName;
	private Date modifyTime;
	private List<MemberGroupRelationDTO> relationList;
	// 会员等级编码集合
	private List<String> gradeList;
	// 会员分组编码集合
	private List<String> groupList;
	//新vms标志 1:新系统
	private String newFlag;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the buyerGrade
	 */
	public String getBuyerGrade() {
		return buyerGrade;
	}

	/**
	 * @param buyerGrade
	 *            the buyerGrade to set
	 */
	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the buyerId
	 */
	public long getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the memberCount
	 */
	public int getMemberCount() {
		return memberCount;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the groupIds
	 */
	public String getGroupIds() {
		return groupIds;
	}

	/**
	 * @param groupIds
	 *            the groupIds to set
	 */
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	/**
	 * @return the buyerIds
	 */
	public String getBuyerIds() {
		return buyerIds;
	}

	/**
	 * @param buyerIds
	 *            the buyerIds to set
	 */
	public void setBuyerIds(String buyerIds) {
		this.buyerIds = buyerIds;
	}

	/**
	 * @return the locationProvince
	 */
	public String getLocationProvince() {
		return locationProvince;
	}

	/**
	 * @param locationProvince
	 *            the locationProvince to set
	 */
	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	/**
	 * @return the locationCity
	 */
	public String getLocationCity() {
		return locationCity;
	}

	/**
	 * @param locationCity
	 *            the locationCity to set
	 */
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	/**
	 * @return the locationCounty
	 */
	public String getLocationCounty() {
		return locationCounty;
	}

	/**
	 * @param locationCounty
	 *            the locationCounty to set
	 */
	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the memberIdArr
	 */
	public String[] getMemberIdArr() {
		return memberIdArr;
	}

	/**
	 * @param memberIdArr
	 *            the memberIdArr to set
	 */
	public void setMemberIdArr(String[] memberIdArr) {
		this.memberIdArr = memberIdArr;
	}

	/**
	 * @param memberCount
	 *            the memberCount to set
	 */
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * @param createId
	 *            the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the relationList
	 */
	public List<MemberGroupRelationDTO> getRelationList() {
		return relationList;
	}

	/**
	 * @param relationList
	 *            the relationList to set
	 */
	public void setRelationList(List<MemberGroupRelationDTO> relationList) {
		this.relationList = relationList;
	}

	/**
	 * @return the gradeList
	 */
	public List<String> getGradeList() {
		return gradeList;
	}

	/**
	 * @param gradeList
	 *            the gradeList to set
	 */
	public void setGradeList(List<String> gradeList) {
		this.gradeList = gradeList;
	}

	/**
	 * @return the groupList
	 */
	public List<String> getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList
	 *            the groupList to set
	 */
	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
}
