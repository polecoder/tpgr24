package logica;

import java.util.Set;

import logica.datatypes.DTInstitucion;

public class ControladorInstitucion implements IControladorInstitucion {
	private 
	static ControladorInstitucion instancia;
	ManejadorInstituciones ManejadorInstituciones;
	ControladorInstitucion() {
		this.ManejadorInstituciones=ManejadorInstituciones.getInstance();
	};
	public 
	static ControladorInstitucion getInstancia() {
		if (instancia==null) {
			instancia= new ControladorInstitucion();
		}
		return instancia;
	}
	
	public Set<DTInstitucion> listarInstituciones() {
		//TODO
		return null;
	}
	
}
