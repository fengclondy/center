package cn.htd.usercenter.dto;

import java.io.Serializable;

public class LoginParamDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String appName;
    private String clientIPAddr;
    private boolean isVMS;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientIPAddr() {
        return clientIPAddr;
    }

    public void setClientIPAddr(String clientIPAddr) {
        this.clientIPAddr = clientIPAddr;
    }

    public boolean isVMS() {
        return isVMS;
    }

    public void setVMS(boolean isVMS) {
        this.isVMS = isVMS;
    }
}
