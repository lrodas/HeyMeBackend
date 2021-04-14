/**
 * TipoCambioSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public interface TipoCambioSoap extends java.rmi.Remote {

    /**
     * Despliega las variables (con relación a la moneda) disponibles
     * para consulta.
     */
    public gt.gob.banguat.www.variables.ws.InfoVariable variablesDisponibles() throws java.rmi.RemoteException;

    /**
     * Despliega el tipo de cambio correspondiente a una variable
     * (moneda) dada. (Formato: moneda=2)
     */
    public gt.gob.banguat.www.variables.ws.InfoVariable variables(int variable) throws java.rmi.RemoteException;

    /**
     * Despliega la información del tipo de cambio, en dólares, desde
     * una fecha dada hasta el día corriente. (Formato: fecha_ini=dd/mm/aaaa).
     */
    public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioFechaInicial(java.lang.String fechainit) throws java.rmi.RemoteException;

    /**
     * Despliega la información para dólares en el período de tiempo
     * dado. (Formato: fecha_ini=dd/mm/aaaa fecha_fin=dd/mm/aaaa)
     */
    public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioRango(java.lang.String fechainit, java.lang.String fechafin) throws java.rmi.RemoteException;

    /**
     * Despliega la información para la variable indicada a partir
     * de una fecha y moneda indicada. (Formato: fecha_ini=dd/mm/aaaa moneda=02).
     */
    public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioFechaInicialMoneda(java.lang.String fechainit, int moneda) throws java.rmi.RemoteException;

    /**
     * Despliega la información para la variable indicada en el período
     * de tiempo y la moneda dada. (Formato: fecha_ini=dd/mm/aaaa fecha_fin=dd/mm/aaaa
     * moneda=02)
     */
    public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioRangoMoneda(java.lang.String fechainit, java.lang.String fechafin, int moneda) throws java.rmi.RemoteException;

    /**
     * Devuelve el cambio del día en dólares
     */
    public gt.gob.banguat.www.variables.ws.InfoVariable tipoCambioDia() throws java.rmi.RemoteException;

    /**
     * Devuelve el cambio del día en dólares
     */
    public java.lang.String tipoCambioDiaString() throws java.rmi.RemoteException;
}
