package logica;
import java.util.Set;
import datatypes.DTInstitucion;
import excepciones.FormularioIncompletoInstitucion; 
public interface IControladorInstitucion {
	Set<DTInstitucion> listarInstituciones();
	Institucion getInstitucion(String nombre);
	Set<String> nombresInstituciones();
	void altaInstitucion(String nombre, String descripcion, String sitioWeb)throws FormularioIncompletoInstitucion;
}
