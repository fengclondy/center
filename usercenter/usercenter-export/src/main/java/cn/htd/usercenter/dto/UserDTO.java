package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * Description: [用户基本信息DTO]
 * </p>
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id; // 用户ID
    private String name; // 用户名
    private String loginId; // 登录ID
    private String password; // 密码
    private String nickname; // 昵称
    private String email; // 邮件地址
    private String mobile; // 手机号码
    private String avatar; // 头像
    private Integer validFlag; // 用户有效性
    private Integer deletedFlag; // 是否删除标识
    private Integer failedLoginCount; // 登录失败次数
    private Timestamp lastLoginTime; // 上次登录时间
    private Long createId; // 做成用户ID
    private Long lastUpdateId; // 最终更新用户ID

    private String empNo; // 员工编号
    private String empCompanyId; // 员工所属公司
    private String empCompanyName; // 员工所属公司名称
    private String empCompanyType; // 员工所属公司类型
    private String empInchargeCompanies; // 员工负责下级公司
    private Long empSuperiorId; // 上级员工ID
    private String empSuperiorEmpNo; // 上级员工编号
    private Integer isCustomerManager; // 是否客户经理
    private String empStatus; // 员工状态

    private Long cusCompanyId; // 客户所属公司ID
    private Integer isVMSInnerUser; // 是否VMS内部用户

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Long lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpCompanyId() {
        return empCompanyId;
    }

    public void setEmpCompanyId(String empCompanyId) {
        this.empCompanyId = empCompanyId;
    }

    public String getEmpCompanyName() {
        return empCompanyName;
    }

    public void setEmpCompanyName(String empCompanyName) {
        this.empCompanyName = empCompanyName;
    }

    public String getEmpCompanyType() {
        return empCompanyType;
    }

    public void setEmpCompanyType(String empCompanyType) {
        this.empCompanyType = empCompanyType;
    }

    public String getEmpInchargeCompanies() {
        return empInchargeCompanies;
    }

    public void setEmpInchargeCompanies(String empInchargeCompanies) {
        this.empInchargeCompanies = empInchargeCompanies;
    }

    public Long getEmpSuperiorId() {
        return empSuperiorId;
    }

    public void setEmpSuperiorId(Long empSuperiorId) {
        this.empSuperiorId = empSuperiorId;
    }

    public String getEmpSuperiorEmpNo() {
        return empSuperiorEmpNo;
    }

    public void setEmpSuperiorEmpNo(String empSuperiorEmpNo) {
        this.empSuperiorEmpNo = empSuperiorEmpNo;
    }

    public Integer getIsCustomerManager() {
        return isCustomerManager;
    }

    public void setIsCustomerManager(Integer isCustomerManager) {
        this.isCustomerManager = isCustomerManager;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public Long getCusCompanyId() {
        return cusCompanyId;
    }

    public void setCusCompanyId(Long cusCompanyId) {
        this.cusCompanyId = cusCompanyId;
    }

    public Integer getIsVMSInnerUser() {
        return isVMSInnerUser;
    }

    public void setIsVMSInnerUser(Integer isVMSInnerUser) {
        this.isVMSInnerUser = isVMSInnerUser;
    }
}
