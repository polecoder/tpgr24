
package com.eventosuy.servidor;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para eNivelPatrocinio.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="eNivelPatrocinio"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Platino"/&gt;
 *     &lt;enumeration value="Oro"/&gt;
 *     &lt;enumeration value="Plata"/&gt;
 *     &lt;enumeration value="Bronce"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "eNivelPatrocinio")
@XmlEnum
public enum ENivelPatrocinio {

    @XmlEnumValue("Platino")
    PLATINO("Platino"),
    @XmlEnumValue("Oro")
    ORO("Oro"),
    @XmlEnumValue("Plata")
    PLATA("Plata"),
    @XmlEnumValue("Bronce")
    BRONCE("Bronce");
    private final String value;

    ENivelPatrocinio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ENivelPatrocinio fromValue(String v) {
        for (ENivelPatrocinio c: ENivelPatrocinio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
