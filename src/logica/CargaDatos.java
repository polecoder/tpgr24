package logica;

import logica.csv.CSVCategoria;
import logica.csv.CSVInstitucion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CargaDatos {
	private static CargaDatos instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorInstituciones manejadorInstituciones;
	private ManejadorUsuarios manejadorUsuarios;
	private Map<String, CSVCategoria> categorias;
	private Map<String, CSVInstitucion> instituciones;
	private CargaDatos() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorInstituciones = ManejadorInstituciones.getInstance();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		this.categorias = new HashMap<>();
		this.instituciones = new HashMap<>();
	}
	
	public static CargaDatos getInstancia() {
		if (instancia == null) {
			instancia = new CargaDatos();
		}
        return instancia;
	}
	
	/**
	 * Lee un archivo de categorías desde la ruta indicada por parámetro.
	 * Guarda la colección de CSVs en el Map categorias.
	 * Si existen duplicados, se queda con el último
	 * 
	 * @param ruta
	 */
	public void leerCategorias(String ruta) {
        CsvToBean<CSVCategoria> categoriasCsv;
		try {
			categoriasCsv = new CsvToBeanBuilder<CSVCategoria>(
			        new FileReader(ruta))
			        .withType(CSVCategoria.class)
			        .withSeparator(';')
			        .withIgnoreLeadingWhiteSpace(true)
			        .withIgnoreEmptyLine(true)
			        .build();
			List<CSVCategoria> listaCategorias = categoriasCsv.parse();
	        this.categorias = listaCategorias.stream()
	                						 .collect(Collectors.toMap(
	                								 CSVCategoria::getRef,
	                								 d -> d,
	                								 (existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
            								 ));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga las categorías que se encuentran en la colección categorias al sistema.
	 * Si el mapa está vacío, no hace nada.
	 */
	public void cargarCategorias() {
		if (this.categorias != null) {
			for (CSVCategoria categoria: this.categorias.values()) {
				this.manejadorEventos.altaCategoria(categoria.getNombre());
			}
			this.manejadorEventos.imprimirCategorias();
		}
	}
	
	/**
	 * Lee un archivo de instituciones desde la ruta indicada por parámetro.
	 * Guarda la colección de CSVs en el Map instituciones.
	 * Si existen duplicados, se queda con el último.
	 * 
	 * @param ruta
	 */
	public void leerInstituciones(String ruta) {
        CsvToBean<CSVInstitucion> institucionesCsv;
		try {
			institucionesCsv = new CsvToBeanBuilder<CSVInstitucion>(
			        new FileReader(ruta))
			        .withType(CSVInstitucion.class)
			        .withSeparator(';')
			        .withIgnoreLeadingWhiteSpace(true)
			        .withIgnoreEmptyLine(true)
			        .build();
			List<CSVInstitucion> listaInstituciones = institucionesCsv.parse();
	        this.instituciones = listaInstituciones.stream()
	                						 .collect(Collectors.toMap(
	                								 CSVInstitucion::getRef,
	                								 d -> d,
	                								 (existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
            								 ));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga las instituciones que se encuentran en la colección instituciones al sistema.
	 * Si el mapa está vacío, no hace nada.
	 */
	public void cargarInstituciones() {
		if (this.instituciones != null) {
			for (CSVInstitucion institucion: this.instituciones.values()) {
				this.manejadorInstituciones.altaInstitucion(institucion.getNombre(), institucion.getDescripcion(), institucion.getSitioWeb());
			}
			this.manejadorInstituciones.imprimirInstituciones();
		}
	}
}
