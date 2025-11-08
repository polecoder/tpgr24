package webwrappers;

import datatypes.DTEvento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTEventoEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEventoEntry {
    private String key;
    private DTEvento value;
    
    public DTEventoEntry() {
    }
    
    public DTEventoEntry(String key, DTEvento value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public DTEvento getValue() {
        return value;
    }
    
    public void setValue(DTEvento value) {
        this.value = value;
    }
}