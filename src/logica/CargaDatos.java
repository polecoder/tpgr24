package logica;

import logica.csv.CSVCategoria;
import logica.csv.CSVEvento;

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
	private Map<String, CSVEvento> eventos;
	
	private CargaDatos() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorInstituciones = ManejadorInstituciones.getInstance();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		this.categorias = new HashMap<>();
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
	                								 CSVCategoria::getNombre,
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
			for (String nombreCategoria: this.categorias.keySet()) {
				this.manejadorEventos.altaCategoria(nombreCategoria);
			}
			this.manejadorEventos.imprimirCategorias();
		}
	}
	
	
	//------------EVENTO-----------------
	public void leerEventos(String ruta) {
        CsvToBean<CSVEvento> eventosCsv;
		try {
			eventosCsv = new CsvToBeanBuilder<CSVEvento>(
			        new FileReader(ruta))
			        .withType(CSVEvento.class)
			        .withSeparator(';')
			        .withIgnoreLeadingWhiteSpace(true)
			        .withIgnoreEmptyLine(true)
			        .build();
			List<CSVEvento> listaEventos = eventosCsv.parse();
	        this.eventos = listaEventos.stream()
	                						 .collect(Collectors.toMap(
	                								 CSVEvento::getNombre,
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
	public void cargarEventos() {
		if (this.eventos!= null) {
			for (String nombreEvento: this.categorias.keySet()) {
				
				//this.manejadorEventos.altaEvento(nombreEvento);
			}
			this.manejadorEventos.imprimirCategorias();
		}
	}
	//------------EVENTO-----------------
}
