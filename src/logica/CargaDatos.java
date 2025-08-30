package logica;

import logica.csv.CSVCategoria;
import logica.csv.CSVEdicion;
import logica.csv.CSVEvento;
import logica.csv.CSVInstitucion;
import logica.csv.CSVTipoRegistro;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CargaDatos {
	private static CargaDatos instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorInstituciones manejadorInstituciones;
	private ManejadorUsuarios manejadorUsuarios;
	
	private Map<String, CSVCategoria> categorias;
	private Map<String, CSVEvento> eventos;
	private Map<String, CSVEdicion> ediciones;
	private Map<String, CSVTipoRegistro> tiposRegistro;
	private Map<String, CSVInstitucion> instituciones;
	
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
	
	//------------CATEGORIA-----------------
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
	//------------CATEGORIA-----------------
	
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
	                								 CSVEvento::getRef,
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
			List<String> categoriasEvento = new ArrayList<String>();
			DateTimeFormatter.ofPattern("d/M/yyyy");
			for (String ref : this.eventos.keySet()) {
				CSVEvento evento = eventos.get(ref);
				// Separo las categorias
				for (String categoria : evento.getCategorias().split(",")) {
					categoriasEvento.add(categorias.get(categoria).getNombre()); // Asumo que existe la categoria referenciada
				}
				LocalDate fechaAlta = LocalDate.parse(evento.getFechaAlta());
				this.manejadorEventos.altaEvento(evento.getNombre(), evento.getDescripcion(), fechaAlta, evento.getSigla(), categoriasEvento);
			}
		}
	}
	//------------EVENTO-----------------
	
	//------------EDICION-----------------
	public void leerEdiciones(String ruta) {
        CsvToBean<CSVEdicion> edicionesCsv;
		try {
			edicionesCsv = new CsvToBeanBuilder<CSVEdicion>(
			        new FileReader(ruta))
			        .withType(CSVEdicion.class)
			        .withSeparator(';')
			        .withIgnoreLeadingWhiteSpace(true)
			        .withIgnoreEmptyLine(true)
			        .build();
			List<CSVEdicion> listaEdiciones = edicionesCsv.parse();
	        this.ediciones = listaEdiciones.stream()
	                						 .collect(Collectors.toMap(
	                								 CSVEdicion::getRef,
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
	public void cargarEdiciones() {
		if (this.eventos!= null) {
			DateTimeFormatter.ofPattern("d/M/yyyy");
			for (String ref : this.ediciones.keySet()) {
				CSVEdicion edicion = ediciones.get(ref);
				String nombreEvento = eventos.get(edicion.getEvento()).getNombre();
				LocalDate fechaInicio = LocalDate.parse(edicion.getFechaInicio());
				LocalDate fechaFin = LocalDate.parse(edicion.getFechaFin());
				LocalDate fechaAlta = LocalDate.parse(edicion.getFechaAlta());
				this.manejadorEventos.altaEdicion(nombreEvento, edicion.getNombre(), edicion.getSigla(), fechaInicio, fechaFin, fechaAlta, edicion.getCiudad(), edicion.getPais());
			}
		}
	}
	//------------EDICION-----------------
	
	//------------TIPOREGISTRO-----------------
	public void leerTipoRegistro(String ruta) {
	    CsvToBean<CSVTipoRegistro> tipoRegistroCsv;
		try {
			tipoRegistroCsv = new CsvToBeanBuilder<CSVTipoRegistro>(
			        new FileReader(ruta))
			        .withType(CSVTipoRegistro.class)
			        .withSeparator(';')
			        .withIgnoreLeadingWhiteSpace(true)
			        .withIgnoreEmptyLine(true)
			        .build();
			List<CSVTipoRegistro> listaTipoRegistro = tipoRegistroCsv.parse();
	        this.tiposRegistro = listaTipoRegistro.stream()
	                						 .collect(Collectors.toMap(
	                								 CSVTipoRegistro::getRef,
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
	public void cargarTipoRegistro() {
		if (this.tiposRegistro!= null) {
			for (String ref : this.tiposRegistro.keySet()) {
				CSVTipoRegistro tipoRegistro = tiposRegistro.get(ref);
				String nombreEdicion = ediciones.get(tipoRegistro.getEdicion()).getNombre();
				String nombreEvento = eventos.get(ediciones.get(tipoRegistro.getEdicion()).getEvento()).getNombre();
				this.manejadorEventos.altaTipoRegistro(tipoRegistro.getNombre(), tipoRegistro.getDescripcion(), Float.parseFloat(tipoRegistro.getCosto()), Integer.parseInt(tipoRegistro.getCupos()), nombreEvento, nombreEdicion);
			}
		}
	}
	//------------TIPOREGISTRO-----------------
	
	//------------INSTITUCION-----------------
		public void leerInstitucion(String ruta) {
		    CsvToBean<CSVInstitucion> institucionCsv;
			try {
				institucionCsv = new CsvToBeanBuilder<CSVInstitucion>(
				        new FileReader(ruta))
				        .withType(CSVInstitucion.class)
				        .withSeparator(';')
				        .withIgnoreLeadingWhiteSpace(true)
				        .withIgnoreEmptyLine(true)
				        .build();
				List<CSVInstitucion> listaInstituciones = institucionCsv.parse();
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
		 * Carga las categorías que se encuentran en la colección categorias al sistema.
		 * Si el mapa está vacío, no hace nada.
		 */
		public void cargarInstitucion() {
			if (this.instituciones!= null) {
				for (String ref : this.instituciones.keySet()) {
					CSVInstitucion institucion = instituciones.get(ref);
					this.manejadorInstituciones.altaInstitucion(institucion.getNombre(), institucion.getDescripcion(), institucion.getSitioWeb());
				}
			}
		}
		//------------INSTITUCION-----------------
}
