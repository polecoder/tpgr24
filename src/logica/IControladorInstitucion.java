package logica;
import logica.datatypes.DTInstitucion; 

public interface IControladorInstitucion {
	IControladorInstitucion getInstancia();
	DTInstitucion[] listarInstituciones();
}
