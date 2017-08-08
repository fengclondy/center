package cn.htd.usercenter.dto;

import java.io.Serializable;

public class OAWorkerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workCode; // 工号
    private String lastName; // 姓名
    private String subCompanyCode; // 公司编码
    private String password; // 密码
    private String email; // 邮件地址
    private String mobile; // 手机号
    private String superior; // 上级工号
    private int isCM; // 是否客户经理
    private int status; // 员工状态
    private String gw;//岗位
    
    public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubCompanyCode() {
        return subCompanyCode;
    }

    public void setSubCompanyCode(String subCompanyCode) {
        this.subCompanyCode = subCompanyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public int getIsCM() {
        return isCM;
    }

    public void setIsCM(int isCM) {
        this.isCM = isCM;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
