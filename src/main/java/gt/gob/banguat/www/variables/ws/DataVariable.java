/**
 * DataVariable.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public class DataVariable  implements java.io.Serializable {
    private gt.gob.banguat.www.variables.ws.Var[] vars;

    private int totalItems;

    public DataVariable() {
    }

    public DataVariable(
           gt.gob.banguat.www.variables.ws.Var[] vars,
           int totalItems) {
           this.vars = vars;
           this.totalItems = totalItems;
    }


    /**
     * Gets the vars value for this DataVariable.
     * 
     * @return vars
     */
    public gt.gob.banguat.www.variables.ws.Var[] getVars() {
        return vars;
    }


    /**
     * Sets the vars value for this DataVariable.
     * 
     * @param vars
     */
    public void setVars(gt.gob.banguat.www.variables.ws.Var[] vars) {
        this.vars = vars;
    }


    /**
     * Gets the totalItems value for this DataVariable.
     * 
     * @return totalItems
     */
    public int getTotalItems() {
        return totalItems;
    }


    /**
     * Sets the totalItems value for this DataVariable.
     * 
     * @param totalItems
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataVariable)) return false;
        DataVariable other = (DataVariable) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vars==null && other.getVars()==null) || 
             (this.vars!=null &&
              java.util.Arrays.equals(this.vars, other.getVars()))) &&
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
        if (getVars() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVars());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVars(), i);
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
        new org.apache.axis.description.TypeDesc(DataVariable.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "DataVariable"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vars");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Vars"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Var"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Var"));
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
