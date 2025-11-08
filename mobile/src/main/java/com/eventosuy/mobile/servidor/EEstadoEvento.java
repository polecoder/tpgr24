
package com.eventosuy.mobile.servidor;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eEstadoEvento.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="eEstadoEvento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EN_CURSO"/&gt;
 *     &lt;enumeration value="FINALIZADO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "eEstadoEvento")
@XmlEnum
public enum EEstadoEvento {

    EN_CURSO,
    FINALIZADO;

    public String value() {
        return name();
    }

    public static EEstadoEvento fromValue(String v) {
        return valueOf(v);
    }

}
