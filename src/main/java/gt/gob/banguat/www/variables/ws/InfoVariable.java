/**
 * InfoVariable.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public class InfoVariable  implements java.io.Serializable {
    private gt.gob.banguat.www.variables.ws.Variable[] variables;

    private gt.gob.banguat.www.variables.ws.Var[] cambioDia;

    private gt.gob.banguat.www.variables.ws.VarDolar[] cambioDolar;

    private int totalItems;

    public InfoVariable() {
    }

    public InfoVariable(
           gt.gob.banguat.www.variables.ws.Variable[] variables,
           gt.gob.banguat.www.variables.ws.Var[] cambioDia,
           gt.gob.banguat.www.variables.ws.VarDolar[] cambioDolar,
           int totalItems) {
           this.variables = variables;
           this.cambioDia = cambioDia;
           this.cambioDolar = cambioDolar;
           this.totalItems = totalItems;
    }


    /**
     * Gets the variables value for this InfoVariable.
     * 
     * @return variables
     */
    public gt.gob.banguat.www.variables.ws.Variable[] getVariables() {
        return variables;
    }


    /**
     * Sets the variables value for this InfoVariable.
     * 
     * @param variables
     */
    public void setVariables(gt.gob.banguat.www.variables.ws.Variable[] variables) {
        this.variables = variables;
    }


    /**
     * Gets the cambioDia value for this InfoVariable.
     * 
     * @return cambioDia
     */
    public gt.gob.banguat.www.variables.ws.Var[] getCambioDia() {
        return cambioDia;
    }


    /**
     * Sets the cambioDia value for this InfoVariable.
     * 
     * @param cambioDia
     */
    public void setCambioDia(gt.gob.banguat.www.variables.ws.Var[] cambioDia) {
        this.cambioDia = cambioDia;
    }


    /**
     * Gets the cambioDolar value for this InfoVariable.
     * 
     * @return cambioDolar
     */
    public gt.gob.banguat.www.variables.ws.VarDolar[] getCambioDolar() {
        return cambioDolar;
    }


    /**
     * Sets the cambioDolar value for this InfoVariable.
     * 
     * @param cambioDolar
     */
    public void setCambioDolar(gt.gob.banguat.www.variables.ws.VarDolar[] cambioDolar) {
        this.cambioDolar = cambioDolar;
    }


    /**
     * Gets the totalItems value for this InfoVariable.
     * 
     * @return totalItems
     */
    public int getTotalItems() {
        return totalItems;
    }


    /**
     * Sets the totalItems value for this InfoVariable.
     * 
     * @param totalItems
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoVariable)) return false;
        InfoVariable other = (InfoVariable) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.variables==null && other.getVariables()==null) || 
             (this.variables!=null &&
              java.util.Arrays.equals(this.variables, other.getVariables()))) &&
            ((this.cambioDia==null && other.getCambioDia()==null) || 
             (this.cambioDia!=null &&
              java.util.Arrays.equals(this.cambioDia, other.getCambioDia()))) &&
            ((this.cambioDolar==null && other.getCambioDolar()==null) || 
             (this.cambioDolar!=null &&
              java.util.Arrays.equals(this.cambioDolar, other.getCambioDolar()))) &&
            this.totalItems == other.getTotalItems();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getVariables() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVariables());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVariables(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCambioDia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCambioDia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCambioDia(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCambioDolar() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCambioDolar());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCambioDolar(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getTotalItems();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoVariable.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "InfoVariable"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variables");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Variables"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Variable"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Variable"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cambioDia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "CambioDia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Var"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Var"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cambioDolar");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "CambioDolar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "VarDolar"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "VarDolar"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "TotalItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
