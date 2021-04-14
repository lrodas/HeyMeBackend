/**
 * Var.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gt.gob.banguat.www.variables.ws;

public class Var  implements java.io.Serializable {
    private int moneda;

    private java.lang.String fecha;

    private float venta;

    private float compra;

    public Var() {
    }

    public Var(
           int moneda,
           java.lang.String fecha,
           float venta,
           float compra) {
           this.moneda = moneda;
           this.fecha = fecha;
           this.venta = venta;
           this.compra = compra;
    }


    /**
     * Gets the moneda value for this Var.
     * 
     * @return moneda
     */
    public int getMoneda() {
        return moneda;
    }


    /**
     * Sets the moneda value for this Var.
     * 
     * @param moneda
     */
    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }


    /**
     * Gets the fecha value for this Var.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this Var.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the venta value for this Var.
     * 
     * @return venta
     */
    public float getVenta() {
        return venta;
    }


    /**
     * Sets the venta value for this Var.
     * 
     * @param venta
     */
    public void setVenta(float venta) {
        this.venta = venta;
    }


    /**
     * Gets the compra value for this Var.
     * 
     * @return compra
     */
    public float getCompra() {
        return compra;
    }


    /**
     * Sets the compra value for this Var.
     * 
     * @param compra
     */
    public void setCompra(float compra) {
        this.compra = compra;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Var)) return false;
        Var other = (Var) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.moneda == other.getMoneda() &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            this.venta == other.getVenta() &&
            this.compra == other.getCompra();
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
        _hashCode += getMoneda();
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        _hashCode += new Float(getVenta()).hashCode();
        _hashCode += new Float(getCompra()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Var.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "Var"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moneda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "moneda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("venta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "venta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.banguat.gob.gt/variables/ws/", "compra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
