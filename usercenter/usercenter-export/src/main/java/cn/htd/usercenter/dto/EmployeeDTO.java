package cn.htd.usercenter.dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId; // 用户ID
    private String type; // 员工类型
    private String empNo; // 员工编号
    private String companyId; // 员工所属公司
    private Long superiorId; // 上级员工ID
    private String superiorEmpNo; // 上级员工编号
    private Integer isCustomerManager; // 是否客户经理
    private Integer status; // 员工状态
    private String inchargeCompanies; // 员工负责下级公司
    private Integer substationId; // 分站员工所属分站
    private Long createId; // 做成用户ID
    private Long lastUpdateId; // 最终更新用户ID
    private String substationName;//分站名称
    private String loginId;//人员名称
    private String password; // 密码
    private String mobile; // 手机号码
    private String name;//用户名
    private Long manager;//分站管理员
    private String companyType;//公司类型
    private String companyName;//公司名称
    
    private String validFlag;//冻结标志
    private String gw;//岗位
    
    public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}
    
	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
    
	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getSubstationName() {
		return substationName;
	}

	public void setSubstationName(String substationName) {
		this.substationName = substationName;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    public String getSuperiorEmpNo() {
        return superiorEmpNo;
    }

    public void setSuperiorEmpNo(String superiorEmpNo) {
        this.superiorEmpNo = superiorEmpNo;
    }

    public Integer getIsCustomerManager() {
        return isCustomerManager;
    }

    public void setIsCustomerManager(Integer isCustomerManager) {
        this.isCustomerManager = isCustomerManager;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInchargeCompanies() {
        return inchargeCompanies;
    }

    public void setInchargeCompanies(String inchargeCompanies) {
        this.inchargeCompanies = inchargeCompanies;
    }

    public Integer getSubstationId() {
        return substationId;
    }

    public void setSubstationId(Integer substationId) {
        this.substationId = substationId;
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
}
