package logica;
import java.util.Set;
import datatypes.DTInstitucion; 
public interface IControladorInstitucion {
	Set<DTInstitucion> listarInstituciones();
	Institucion getInstitucion(String nombre);
	Set<String> nombresInstituciones();
	void AltaInstitucion(String nombre, String descripcion, String sitioWeb)throws Exception;
}
