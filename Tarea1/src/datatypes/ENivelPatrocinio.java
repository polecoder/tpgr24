package datatypes;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "ENivelPatrocinio")
@XmlEnum
public enum ENivelPatrocinio {
	@XmlEnumValue("Platino")
	Platino,
	@XmlEnumValue("Oro")
	Oro,
	@XmlEnumValue("Plata")
	Plata,
	@XmlEnumValue("Bronce")
	Bronce
}
