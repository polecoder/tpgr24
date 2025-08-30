package logica;
import java.util.Set;
import logica.datatypes.DTInstitucion; 
public interface IControladorInstitucion {
	Set<DTInstitucion> listarInstituciones();
	Institucion getInstitucion(String nombre);
	Set<String> nombresInstituciones();
}
