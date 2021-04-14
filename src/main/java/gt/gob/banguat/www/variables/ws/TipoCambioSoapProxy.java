package gt.gob.banguat.www.variables.ws;

public class TipoCambioSoapProxy implements gt.gob.banguat.www.variables.ws.TipoCambioSoap {
  private String _endpoint = null;
  private gt.gob.banguat.www.variables.ws.TipoCambioSoap tipoCambioSoap = null;
  
  public TipoCambioSoapProxy() {
    _initTipoCambioSoapProxy();
  }
  
  public TipoCambioSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initTipoCambioSoapProxy();
  }
  
  private void _initTipoCambioSoapProxy() {
    try {
      tipoCambioSoap = (new gt.gob.banguat.www.variables.ws.TipoCambioLocator()).getTipoCambioSoap();
      if (tipoCambioSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tipoCambioSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tipoCambioSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tipoCambioSoap != null)
      ((javax.xml.rpc.Stub)tipoCambioSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gt.gob.banguat.www.variables.ws.TipoCambioSoap getTipoCambioSoap() {
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap;
  }
  
  public gt.gob.banguat.www.variables.ws.InfoVariable variablesDisponibles() throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.variablesDisponibles();
  }
  
  public gt.gob.banguat.www.variables.ws.InfoVariable variables(int variable) throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.variables(variable);
  }
  
  public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioFechaInicial(java.lang.String fechainit) throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioFechaInicial(fechainit);
  }
  
  public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioRango(java.lang.String fechainit, java.lang.String fechafin) throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioRango(fechainit, fechafin);
  }
  
  public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioFechaInicialMoneda(java.lang.String fechainit, int moneda) throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioFechaInicialMoneda(fechainit, moneda);
  }
  
  public gt.gob.banguat.www.variables.ws.DataVariable tipoCambioRangoMoneda(java.lang.String fechainit, java.lang.String fechafin, int moneda) throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioRangoMoneda(fechainit, fechafin, moneda);
  }
  
  public gt.gob.banguat.www.variables.ws.InfoVariable tipoCambioDia() throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioDia();
  }
  
  public java.lang.String tipoCambioDiaString() throws java.rmi.RemoteException{
    if (tipoCambioSoap == null)
      _initTipoCambioSoapProxy();
    return tipoCambioSoap.tipoCambioDiaString();
  }
  
  
}