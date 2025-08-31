package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Asistente extends Usuario {
	
	private static final Logger logger = Logger.getLogger(Asistente.class.getName());
	private String apellido;
	private Date nacimiento;
	private List<Registro> registros;
	
	private Institucion institucion;
	
	public Asistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento,Institucion institucion) {
		super(nickname, nombre, correo);
		this.setApellido(apellido);
		this.setNacimiento(fechaNacimiento);
		this.registros = new ArrayList<>();
		this.setInstitucion(institucion);
	}
	
	// Getters
	public String getApellido() {return apellido;}
	public Date getNacimiento() {return nacimiento;}
	public List<Registro> getRegistros() {return registros;}
	public Institucion getInstitucion() {return institucion;}
	// Setters
	public void setApellido(String apellido) {this.apellido = apellido;}
	public void setNacimiento(Date nacimiento) {this.nacimiento = nacimiento;}
	public void setInstitucion(Institucion institucion) {this.institucion=institucion;}
	// Metodos
	public void addRegistro(Registro r) {
		try {
			this.registros.add(r);
		}catch(Exception ARError) {
            logger.log(Level.SEVERE, "Asistente::addRegistro() ERROR: ", ARError);
		}
	}
	//
}

