package logica;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTRegistro;

public class ControladorUsuarios implements IControladorUsuarios {
	private static ControladorUsuarios instance = null;
	
	private ManejadorUsuarios manejadorUsuarios;
	
	private ControladorUsuarios() {
		manejadorUsuarios = manejadorUsuarios.getInstance();
	}
	
	public static ControladorUsuarios getInstance() {
		if (instance == null) {
			instance = new ControladorUsuarios();
		}
		return instance;
	}
	
	@Override
	public boolean altaAsistente(String nickname, String correo, String apellido, LocalDate fechaNacimiento) {
		return false;
	}
	
	@Override
	public boolean altaOrganizador(String nickname, String correo, String descripcion, String link) {
		return false;
	}
	
	@Override
	public List<DTAsistente> listarAsistentes() {
		 return manejadorUsuarios.listarAsistentes();
	}
	
	@Override
	public List<DTUsuario> listarUsuarios() {
		List<DTUsuario> usu = new ArrayList<>();
	    for (Asistente a : manejadorUsuarios.getConjuntoAsistentes().values()) {
	        usu.add(a.listarUsuario()); // Usuario.listarUsuario() -> DTUsuario
	    }
	    for (Organizador o : manejadorUsuarios.getConjuntoOrganizador().values()) {
	        usu.add(o.listarUsuario());
	    }
	    return usu;
	}

	public List<DTRegistro> listarRegistrosUsuario(String nickname){
		List<DTRegistro> Reg = new ArrayList<>();
	    Asistente a = manejadorUsuarios.getAsistente(nickname);
	    if (a == null) return Reg;

	    List<Registro> regs = a.getRegistros();
	    for (int i =0; i< regs.size(); i++) {
	    	Registro r = regs.get(i);
	        TipoRegistro tr = r.getTipoRegistro();          
	        Edicion ed = tr.getEdicion();                  

	        Reg.add(new DTRegistro(
	            ed.getNombre(),              
	            tr.getNombre()                        
	        ));
	    }
	    return Reg;
	}
	public DTDetalleRegistro obtenerDetalleRegistro(String nickname, int idRegistro) {
		Asistente a = manejadorUsuarios.getAsistente(nickname);
	    if (a == null) return null;

	    List<Registro> regs = a.getRegistros();
	    if (idRegistro < 0 || idRegistro >= regs.size()) return null;

	    Registro r = regs.get(idRegistro);
	    TipoRegistro tr = r.getTipoRegistro();
	    Edicion ed = tr.getEdicion();

	    return new DTDetalleRegistro(
	        ed.getNombre(),
	        a.getNickname(),
	        tr.getNombre(),
	        r.getFechaRegistro(),
	        r.getCosto()
	    );
	}
}
