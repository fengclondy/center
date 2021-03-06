/**
 * Htd_userlistLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.Htd_userlist.webservices;

import cn.htd.usercenter.common.constant.StaticProperty;

public class Htd_userlistLocator extends org.apache.axis.client.Service implements cn.com.weaver.Htd_userlist.webservices.Htd_userlist {

    public Htd_userlistLocator() {
    }


    public Htd_userlistLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Htd_userlistLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Htd_userlistHttpPort
    private java.lang.String Htd_userlistHttpPort_address = StaticProperty.OA_URL+"Htd_userlist";

    public java.lang.String getHtd_userlistHttpPortAddress() {
        return Htd_userlistHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Htd_userlistHttpPortWSDDServiceName = "Htd_userlistHttpPort";

    public java.lang.String getHtd_userlistHttpPortWSDDServiceName() {
        return Htd_userlistHttpPortWSDDServiceName;
    }

    public void setHtd_userlistHttpPortWSDDServiceName(java.lang.String name) {
        Htd_userlistHttpPortWSDDServiceName = name;
    }

    public cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType getHtd_userlistHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Htd_userlistHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHtd_userlistHttpPort(endpoint);
    }

    public cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType getHtd_userlistHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.weaver.Htd_userlist.webservices.Htd_userlistHttpBindingStub _stub = new cn.com.weaver.Htd_userlist.webservices.Htd_userlistHttpBindingStub(portAddress, this);
            _stub.setPortName(getHtd_userlistHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHtd_userlistHttpPortEndpointAddress(java.lang.String address) {
        Htd_userlistHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.weaver.Htd_userlist.webservices.Htd_userlistHttpBindingStub _stub = new cn.com.weaver.Htd_userlist.webservices.Htd_userlistHttpBindingStub(new java.net.URL(Htd_userlistHttpPort_address), this);
                _stub.setPortName(getHtd_userlistHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Htd_userlistHttpPort".equals(inputPortName)) {
            return getHtd_userlistHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("webservices.Htd_userlist.weaver.com.cn", "Htd_userlist");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("webservices.Htd_userlist.weaver.com.cn", "Htd_userlistHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Htd_userlistHttpPort".equals(portName)) {
            setHtd_userlistHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
