package com.eventosuy.mobile.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utilidades {
	/**
	 * Convierte un Date a XMLGregorianCalendar para uso en web services.
	 * 
	 * @param date la fecha a convertir
	 * @return XMLGregorianCalendar equivalente
	 * @throws Exception si ocurre un error en la conversión
	 */
	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) throws Exception {
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
	}
	
	public static Date XMLGregorianCalendarToDate(XMLGregorianCalendar xmlGeorgianCalendarFecha) {
		return xmlGeorgianCalendarFecha.toGregorianCalendar().getTime();
	}
}
