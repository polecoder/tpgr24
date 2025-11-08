
package com.eventosuy.mobile.servidor;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eTipoUsuario.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="eTipoUsuario"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Asistente"/&gt;
 *     &lt;enumeration value="Organizador"/&gt;
 *     &lt;enumeration value="Visitante"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "eTipoUsuario")
@XmlEnum
public enum ETipoUsuario {

    @XmlEnumValue("Asistente")
    ASISTENTE("Asistente"),
    @XmlEnumValue("Organizador")
    ORGANIZADOR("Organizador"),
    @XmlEnumValue("Visitante")
    VISITANTE("Visitante");
    private final String value;

    ETipoUsuario(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ETipoUsuario fromValue(String v) {
        for (ETipoUsuario c: ETipoUsuario.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
