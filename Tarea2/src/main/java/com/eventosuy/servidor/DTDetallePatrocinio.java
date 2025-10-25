
package com.eventosuy.servidor;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DTDetallePatrocinio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DTDetallePatrocinio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://servidor/}record"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nombreInstitucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="aporte" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="nivel" type="{http://servidor/}eNivelPatrocinio" minOccurs="0"/&gt;
 *         &lt;element name="cuposGratuitos" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tipoRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTDetallePatrocinio", propOrder = {
    "nombreInstitucion",
    "codigo",
    "fechaAlta",
    "aporte",
    "nivel",
    "cuposGratuitos",
    "tipoRegistro"
})
public class DTDetallePatrocinio
    extends Record
{

    protected String nombreInstitucion;
    protected String codigo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAlta;
    protected float aporte;
    @XmlSchemaType(name = "string")
    protected ENivelPatrocinio nivel;
    protected int cuposGratuitos;
    protected String tipoRegistro;

    /**
     * Obtiene el valor de la propiedad nombreInstitucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * Define el valor de la propiedad nombreInstitucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreInstitucion(String value) {
        this.nombreInstitucion = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAlta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Define el valor de la propiedad fechaAlta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAlta(XMLGregorianCalendar value) {
        this.fechaAlta = value;
    }

    /**
     * Obtiene el valor de la propiedad aporte.
     * 
     */
    public float getAporte() {
        return aporte;
    }

    /**
     * Define el valor de la propiedad aporte.
     * 
     */
    public void setAporte(float value) {
        this.aporte = value;
    }

    /**
     * Obtiene el valor de la propiedad nivel.
     * 
     * @return
     *     possible object is
     *     {@link ENivelPatrocinio }
     *     
     */
    public ENivelPatrocinio getNivel() {
        return nivel;
    }

    /**
     * Define el valor de la propiedad nivel.
     * 
     * @param value
     *     allowed object is
     *     {@link ENivelPatrocinio }
     *     
     */
    public void setNivel(ENivelPatrocinio value) {
        this.nivel = value;
    }

    /**
     * Obtiene el valor de la propiedad cuposGratuitos.
     * 
     */
    public int getCuposGratuitos() {
        return cuposGratuitos;
    }

    /**
     * Define el valor de la propiedad cuposGratuitos.
     * 
     */
    public void setCuposGratuitos(int value) {
        this.cuposGratuitos = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Define el valor de la propiedad tipoRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
    }

}
