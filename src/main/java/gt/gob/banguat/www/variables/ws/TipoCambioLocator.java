/**
 * TipoCambioLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public class TipoCambioLocator extends org.apache.axis.client.Service implements gt.gob.banguat.www.variables.ws.TipoCambio {

/**
 * Tipo de cambio en moneda extranjera
 */

    public TipoCambioLocator() {
    }


    public TipoCambioLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TipoCambioLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TipoCambioSoap
    private java.lang.String TipoCambioSoap_address = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx";

    public java.lang.String getTipoCambioSoapAddress() {
        return TipoCambioSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TipoCambioSoapWSDDServiceName = "TipoCambioSoap";

    public java.lang.String getTipoCambioSoapWSDDServiceName() {
        return TipoCambioSoapWSDDServiceName;
    }

    public void setTipoCambioSoapWSDDServiceName(java.lang.String name) {
        TipoCambioSoapWSDDServiceName = name;
    }

    public gt.gob.banguat.www.variables.ws.TipoCambioSoap getTipoCambioSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TipoCambioSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTipoCambioSoap(endpoint);
    }

    public gt.gob.banguat.www.variables.ws.TipoCambioSoap getTipoCambioSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gt.gob.banguat.www.variables.ws.TipoCambioSoapStub _stub = new gt.gob.banguat.www.variables.ws.TipoCambioSoapStub(portAddress, this);
            _stub.setPortName(getTipoCambioSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTipoCambioSoapEndpointAddress(java.lang.String address) {
        TipoCambioSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gt.gob.banguat.www.variables.ws.TipoCambioSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                gt.gob.banguat.www.variables.ws.TipoCambioSoapStub _stub = new gt.gob.banguat.www.variables.ws.TipoCambioSoapStub(new java.net.URL(TipoCambioSoap_address), this);
                _stub.setPortName(getTipoCambioSoapWSDDServiceName());
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
        if ("TipoCambioSoap".equals(inputPortName)) {
            return getTipoCambioSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "TipoCambio");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "TipoCambioSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TipoCambioSoap".equals(portName)) {
            setTipoCambioSoapEndpointAddress(address);
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
