package logica;

import java.util.Set;

import logica.datatypes.DTInstitucion;

public class ControladorInstitucion {
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
	
	Set<DTInstitucion> listarInstituciones() {
		//TODO
		return null;
	}
	
}
