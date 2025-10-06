package logica;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utilidades {
	public static Date toDate(LocalDate fecha) {
		return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
