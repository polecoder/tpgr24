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
	        usu.add(a.listarUsuario()); 
	    }
	    for (Organizador o : manejadorUsuarios.getConjuntoOrganizador().values()) {
	        usu.add(o.listarUsuario());
	    }
	    return usu;
	}

	@Override
	public List<DTRegistro> listarRegistrosUsuario(String nickname) {
	    Asistente a = manejadorUsuarios.getAsistente(nickname);             // mu = ManejadorUsuarios.getInstance()
	    List<DTRegistro> out = new ArrayList<>();
	    if (a == null) return out;

	    ManejadorEventos me = ManejadorEventos.getInstancia();

	    for (Registro r : a.getRegistros()) {
	        String nombreEd   = me.nombreEdicionDeRegistro(r);
	        String nombreTipo = me.nombreTipoDeRegistro(r);
	        out.add(new DTRegistro(nombreEd, nombreTipo));
	    }
	    return out;
	}
	
	@Override
	public DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice) {
	    Asistente a = manejadorUsuarios.getAsistente(nickname);
	    if (a == null) return null;

	    List<Registro> regs = a.getRegistros();
	    if (indice < 0 || indice >= regs.size()) return null;

	    Registro r = regs.get(indice);

	    ManejadorEventos me = ManejadorEventos.getInstancia();
	    String nombreEd   = me.nombreEdicionDeRegistro(r);
	    String nombreTipo = me.nombreTipoDeRegistro(r);

	    return new DTDetalleRegistro(
	            nombreEd,
	            a.getNickname(),
	            nombreTipo,
	            r.getFechaRegistro(),
	            r.getCosto()
	    );
	}
}
