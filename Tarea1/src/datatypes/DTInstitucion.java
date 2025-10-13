package datatypes;

public class DTInstitucion { 
	private String nombre;
	private String descripcion;
	private String sitioWeb;
	
	public 
	DTInstitucion(String nombre, String descripcion, String sitioWeb) {
        this.nombre =nombre;
        this.descripcion =descripcion ;
        this.sitioWeb=sitioWeb;
    }
	String getNombre(){
		return this.nombre;
	}
	String getDescripcion(){
		return this.descripcion;
	}
	String getSitioWeb(){
		return this.sitioWeb;
	}
	void setNombre(String nombre){
		  this.nombre =nombre;
	}
	void setDescripcion(String descripcion){
		 this.descripcion =descripcion ;
	}
	void setSitioWeb(String sitioWeb){
		this.sitioWeb=sitioWeb;
	}
}
