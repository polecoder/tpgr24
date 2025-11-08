
package com.eventosuy.servidor;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Patrocinio complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Patrocinio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoPatrocinio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="fechaPatrocinio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="aporte" type="{http://www.w3.org/2001/XMLSchema}float" form="qualified"/&gt;
 *         &lt;element name="nivel" type="{http://servidor/}ENivelPatrocinio" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="cuposGratuitos" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/&gt;
 *         &lt;element name="institucion" type="{http://servidor/}institucion" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="tipoRegistro" type="{http://servidor/}tipoRegistro" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patrocinio", propOrder = {
    "codigoPatrocinio",
    "fechaPatrocinio",
    "aporte",
    "nivel",
    "cuposGratuitos",
    "institucion",
    "tipoRegistro"
})
public class Patrocinio {

    protected String codigoPatrocinio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaPatrocinio;
    protected float aporte;
    @XmlSchemaType(name = "string")
    protected ENivelPatrocinio nivel;
    protected int cuposGratuitos;
    protected Institucion institucion;
    protected TipoRegistro tipoRegistro;

    /**
     * Obtiene el valor de la propiedad codigoPatrocinio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPatrocinio() {
        return codigoPatrocinio;
    }

    /**
     * Define el valor de la propiedad codigoPatrocinio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPatrocinio(String value) {
        this.codigoPatrocinio = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPatrocinio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPatrocinio() {
        return fechaPatrocinio;
    }

    /**
     * Define el valor de la propiedad fechaPatrocinio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPatrocinio(XMLGregorianCalendar value) {
        this.fechaPatrocinio = value;
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
     * Obtiene el valor de la propiedad institucion.
     * 
     * @return
     *     possible object is
     *     {@link Institucion }
     *     
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * Define el valor de la propiedad institucion.
     * 
     * @param value
     *     allowed object is
     *     {@link Institucion }
     *     
     */
    public void setInstitucion(Institucion value) {
        this.institucion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoRegistro.
     * 
     * @return
     *     possible object is
     *     {@link TipoRegistro }
     *     
     */
    public TipoRegistro getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Define el valor de la propiedad tipoRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoRegistro }
     *     
     */
    public void setTipoRegistro(TipoRegistro value) {
        this.tipoRegistro = value;
    }

}
