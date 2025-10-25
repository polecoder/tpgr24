
package com.eventosuy.servidor;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DTDetalleRegistro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DTDetalleRegistro"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nombreEdicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nicknameAsistente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nombreTipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="costo" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="codigoPatrocinio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTDetalleRegistro", propOrder = {
    "nombreEdicion",
    "nicknameAsistente",
    "nombreTipo",
    "fecha",
    "costo",
    "codigoPatrocinio"
})
public class DTDetalleRegistro {

    protected String nombreEdicion;
    protected String nicknameAsistente;
    protected String nombreTipo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    protected float costo;
    protected String codigoPatrocinio;

    /**
     * Obtiene el valor de la propiedad nombreEdicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    /**
     * Define el valor de la propiedad nombreEdicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEdicion(String value) {
        this.nombreEdicion = value;
    }

    /**
     * Obtiene el valor de la propiedad nicknameAsistente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNicknameAsistente() {
        return nicknameAsistente;
    }

    /**
     * Define el valor de la propiedad nicknameAsistente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNicknameAsistente(String value) {
        this.nicknameAsistente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreTipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * Define el valor de la propiedad nombreTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreTipo(String value) {
        this.nombreTipo = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     */
    public float getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     */
    public void setCosto(float value) {
        this.costo = value;
    }

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

}
