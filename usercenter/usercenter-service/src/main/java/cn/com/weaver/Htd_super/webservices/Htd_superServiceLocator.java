/**
 * Htd_superServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.Htd_super.webservices;

import cn.htd.usercenter.common.constant.StaticProperty;

public class Htd_superServiceLocator extends org.apache.axis.client.Service implements cn.com.weaver.Htd_super.webservices.Htd_superService {

    public Htd_superServiceLocator() {
    }


    public Htd_superServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Htd_superServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Htd_superServiceHttpPort
    private java.lang.String Htd_superServiceHttpPort_address = StaticProperty.OA_URL+"Htd_superService";
    		//"http://oa.htd.cn/services/Htd_superService";

    public java.lang.String getHtd_superServiceHttpPortAddress() {
        return Htd_superServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Htd_superServiceHttpPortWSDDServiceName = "Htd_superServiceHttpPort";

    public java.lang.String getHtd_superServiceHttpPortWSDDServiceName() {
        return Htd_superServiceHttpPortWSDDServiceName;
    }

    public void setHtd_superServiceHttpPortWSDDServiceName(java.lang.String name) {
        Htd_superServiceHttpPortWSDDServiceName = name;
    }

    public cn.com.weaver.Htd_super.webservices.Htd_superServicePortType getHtd_superServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Htd_superServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHtd_superServiceHttpPort(endpoint);
    }

    public cn.com.weaver.Htd_super.webservices.Htd_superServicePortType getHtd_superServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.weaver.Htd_super.webservices.Htd_superServiceHttpBindingStub _stub = new cn.com.weaver.Htd_super.webservices.Htd_superServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getHtd_superServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHtd_superServiceHttpPortEndpointAddress(java.lang.String address) {
        Htd_superServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.com.weaver.Htd_super.webservices.Htd_superServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.weaver.Htd_super.webservices.Htd_superServiceHttpBindingStub _stub = new cn.com.weaver.Htd_super.webservices.Htd_superServiceHttpBindingStub(new java.net.URL(Htd_superServiceHttpPort_address), this);
                _stub.setPortName(getHtd_superServiceHttpPortWSDDServiceName());
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
        if ("Htd_superServiceHttpPort".equals(inputPortName)) {
            return getHtd_superServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("webservices.Htd_super.weaver.com.cn", "Htd_superService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("webservices.Htd_super.weaver.com.cn", "Htd_superServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Htd_superServiceHttpPort".equals(portName)) {
            setHtd_superServiceHttpPortEndpointAddress(address);
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
