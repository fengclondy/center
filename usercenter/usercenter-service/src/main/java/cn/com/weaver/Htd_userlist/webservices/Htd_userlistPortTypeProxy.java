package cn.com.weaver.Htd_userlist.webservices;

public class Htd_userlistPortTypeProxy implements cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType {
  private String _endpoint = null;
  private cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType htd_userlistPortType = null;
  
  public Htd_userlistPortTypeProxy() {
    _initHtd_userlistPortTypeProxy();
  }
  
  public Htd_userlistPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initHtd_userlistPortTypeProxy();
  }
  
  private void _initHtd_userlistPortTypeProxy() {
    try {
      htd_userlistPortType = (new cn.com.weaver.Htd_userlist.webservices.Htd_userlistLocator()).getHtd_userlistHttpPort();
      if (htd_userlistPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)htd_userlistPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)htd_userlistPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (htd_userlistPortType != null)
      ((javax.xml.rpc.Stub)htd_userlistPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType getHtd_userlistPortType() {
    if (htd_userlistPortType == null)
      _initHtd_userlistPortTypeProxy();
    return htd_userlistPortType;
  }
  
  public java.lang.String getdata() throws java.rmi.RemoteException{
    if (htd_userlistPortType == null)
      _initHtd_userlistPortTypeProxy();
    return htd_userlistPortType.getdata();
  }
  
  
}