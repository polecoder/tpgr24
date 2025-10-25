
package com.eventosuy.servidor;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eEstadoEdicion.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="eEstadoEdicion"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Ingresada"/&gt;
 *     &lt;enumeration value="Aceptada"/&gt;
 *     &lt;enumeration value="Rechazada"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "eEstadoEdicion")
@XmlEnum
public enum EEstadoEdicion {

    @XmlEnumValue("Ingresada")
    INGRESADA("Ingresada"),
    @XmlEnumValue("Aceptada")
    ACEPTADA("Aceptada"),
    @XmlEnumValue("Rechazada")
    RECHAZADA("Rechazada");
    private final String value;

    EEstadoEdicion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EEstadoEdicion fromValue(String v) {
        for (EEstadoEdicion c: EEstadoEdicion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
