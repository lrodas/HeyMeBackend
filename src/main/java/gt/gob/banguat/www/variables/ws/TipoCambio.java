/**
 * TipoCambio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public interface TipoCambio extends javax.xml.rpc.Service {

/**
 * Tipo de cambio en moneda extranjera
 */
    public java.lang.String getTipoCambioSoapAddress();

    public gt.gob.banguat.www.variables.ws.TipoCambioSoap getTipoCambioSoap() throws javax.xml.rpc.ServiceException;

    public gt.gob.banguat.www.variables.ws.TipoCambioSoap getTipoCambioSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
