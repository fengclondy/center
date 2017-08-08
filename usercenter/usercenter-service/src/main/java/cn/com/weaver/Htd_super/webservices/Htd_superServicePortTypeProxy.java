package cn.com.weaver.Htd_super.webservices;

public class Htd_superServicePortTypeProxy implements cn.com.weaver.Htd_super.webservices.Htd_superServicePortType {
  private String _endpoint = null;
  private cn.com.weaver.Htd_super.webservices.Htd_superServicePortType htd_superServicePortType = null;
  
  public Htd_superServicePortTypeProxy() {
    _initHtd_superServicePortTypeProxy();
  }
  
  public Htd_superServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initHtd_superServicePortTypeProxy();
  }
  
  private void _initHtd_superServicePortTypeProxy() {
    try {
      htd_superServicePortType = (new cn.com.weaver.Htd_super.webservices.Htd_superServiceLocator()).getHtd_superServiceHttpPort();
      if (htd_superServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)htd_superServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)htd_superServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (htd_superServicePortType != null)
      ((javax.xml.rpc.Stub)htd_superServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.com.weaver.Htd_super.webservices.Htd_superServicePortType getHtd_superServicePortType() {
    if (htd_superServicePortType == null)
      _initHtd_superServicePortTypeProxy();
    return htd_superServicePortType;
  }
  
  public cn.com.weaver.Htd_super.webservices.AnyType2AnyTypeMapEntry[][] getdata(java.lang.String in0) throws java.rmi.RemoteException{
    if (htd_superServicePortType == null)
      _initHtd_superServicePortTypeProxy();
    return htd_superServicePortType.getdata(in0);
  }
  
  
}