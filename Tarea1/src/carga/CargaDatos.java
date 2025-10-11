package carga;

import logica.Asistente;
import logica.Edicion;
import logica.Institucion;
import logica.ManejadorEventos;
import logica.ManejadorInstituciones;
import logica.ManejadorUsuarios;
import logica.TipoRegistro;
import datatypes.EEstadoEdicion;
import datatypes.ENivelPatrocinio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import carga.csv.CSVAsistente;
import carga.csv.CSVCategoria;
import carga.csv.CSVEdicion;
import carga.csv.CSVEvento;
import carga.csv.CSVInstitucion;
import carga.csv.CSVOrganizador;
import carga.csv.CSVPatrocinio;
import carga.csv.CSVRegistro;
import carga.csv.CSVTipoRegistro;
import carga.csv.CSVUsuario;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;

public class CargaDatos {
	private static CargaDatos instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorInstituciones manejadorInstituciones;
	private ManejadorUsuarios manejadorUsuarios;

	private Map<String, CSVCategoria> categorias;
	private Map<String, CSVInstitucion> instituciones;
	private Map<String, CSVUsuario> usuariosAsistente;
	private Map<String, CSVUsuario> usuariosOrganizador;
	private Map<String, CSVAsistente> asistentes;
	private Map<String, CSVOrganizador> organizadores;
	private Map<String, CSVPatrocinio> patrocinios;
	private Map<String, CSVRegistro> registros;
	private Map<String, CSVEvento> eventos;
	private Map<String, CSVEdicion> ediciones;
	private Map<String, CSVTipoRegistro> tiposRegistro;
	
	List<String> nombreArchivosOrdenados = List.of(
			"2025Categorias.csv",
			"2025Usuarios.csv",
			"2025Instituciones.csv",
			"2025Usuarios-Organizadores.csv",
			"2025Usuarios-Asistentes.csv",
			"2025Eventos.csv",
			"2025EdicionesEventos.csv",
			"2025TipoRegistro.csv",
			"2025Patrocinios.csv",
			"2025Registros.csv"
			);

	private CargaDatos() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorInstituciones = ManejadorInstituciones.getInstance();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		this.categorias = new HashMap<>();
		this.instituciones = new HashMap<>();
		this.usuariosAsistente = new HashMap<>();
		this.usuariosOrganizador = new HashMap<>();
		this.asistentes = new HashMap<>();
		this.organizadores = new HashMap<>();
		this.patrocinios = new HashMap<>();
		this.eventos = new HashMap<>();
		this.ediciones = new HashMap<>();
		this.tiposRegistro = new HashMap<>();
		this.registros = new HashMap<>();
	}

	public static CargaDatos getInstancia() {
		if (instancia == null) {
			instancia = new CargaDatos();
		}
		return instancia;
	}

	public List<String> getNombreArchivosOrdenados() {
		return this.nombreArchivosOrdenados;
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
		}
	}

	/**
	 * Lee un archivo de usuarios desde la ruta indicada por parámetro.
	 * Guarda la colección de CSVs en los Map asistentes y organizadores.
	 * Si existen duplicados, se queda con el último.
	 * 
	 * @param ruta
	 */
	public void leerUsuarios(String ruta) {
		CsvToBean<CSVUsuario> usuariosCsv;
		try {
			usuariosCsv = new CsvToBeanBuilder<CSVUsuario>(
					new FileReader(ruta))
					.withType(CSVUsuario.class)
					.withSeparator(';')
					.withIgnoreLeadingWhiteSpace(true)
					.withIgnoreEmptyLine(true)
					.build();
			List<CSVUsuario> listaUsuarios = usuariosCsv.parse();
			Map<String, List<CSVUsuario>> listaUsuariosPorTipo = listaUsuarios.stream().collect(Collectors.groupingBy(CSVUsuario::getTipo));
			this.usuariosAsistente = listaUsuariosPorTipo.get("A").stream()
					.collect(Collectors.toMap(
							CSVUsuario::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));
			this.usuariosOrganizador = listaUsuariosPorTipo.get("O").stream()
					.collect(Collectors.toMap(
							CSVUsuario::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));

		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lee un archivo de asistentes desde la ruta indicada por parámetro.
	 * Guarda la colección de CSVs en el Map asistentes.
	 * Si existen duplicados, se queda con el último.
	 * 
	 * @param ruta
	 */
	public void leerAsistentes(String ruta) {
		CsvToBean<CSVAsistente> asistentesCsv;
		try {
			asistentesCsv = new CsvToBeanBuilder<CSVAsistente>(
					new FileReader(ruta))
					.withType(CSVAsistente.class)
					.withSeparator(';')
					.withIgnoreLeadingWhiteSpace(true)
					.withIgnoreEmptyLine(true)
					.build();
			List<CSVAsistente> listaAsistentes = asistentesCsv.parse();
			this.asistentes = listaAsistentes.stream()
					.collect(Collectors.toMap(
							CSVAsistente::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lee un archivo de organizadores desde la ruta indicada por parámetro.
	 * Guarda la colección de CSVs en el Map organizadores.
	 * Si existen duplicados, se queda con el último.
	 * 
	 * @param ruta
	 */
	public void leerOrganizadores(String ruta) {
		CsvToBean<CSVOrganizador> organizadoresCsv;
		try {
			organizadoresCsv = new CsvToBeanBuilder<CSVOrganizador>(
					new FileReader(ruta))
					.withType(CSVOrganizador.class)
					.withSeparator(';')
					.withIgnoreLeadingWhiteSpace(true)
					.withIgnoreEmptyLine(true)
					.build();
			List<CSVOrganizador> listaOrganizadores = organizadoresCsv.parse();
			this.organizadores = listaOrganizadores.stream()
					.collect(Collectors.toMap(
							CSVOrganizador::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga las usuarios que se encuentran en las colecciones usuariosAsistente, asistentes, usuariosOrganizador y organizadores al sistema.
	 * Si las colecciones instituciones, asistentes y usuarioAsistente están vacías, entonces no carga los asistentes
	 * Si las colecciones organizador y usuarioOrganizador están vacías, entonces no hace nada con ellas.
	 */
	public void cargarUsuarios() {
		if (this.usuariosAsistente != null && this.asistentes != null && this.instituciones != null) {
			for (Map.Entry<String, CSVUsuario> entry : this.usuariosAsistente.entrySet()) {
				CSVUsuario datosUsuario = entry.getValue();
				CSVAsistente datosAsistente = this.asistentes.get(entry.getKey());
				String refInstitucion = datosAsistente.getInstitucion();
				Institucion institucion = null;
				if (!refInstitucion.isEmpty()) {
					String nombreInstitucion = this.instituciones.get(refInstitucion).getNombre();
					institucion = this.manejadorInstituciones.getInstitucion(nombreInstitucion);
				}
				// TODO: Por la forma de los datos dados, se ingresa una contraseña hardcodeada
				this.manejadorUsuarios.agregarAsistente(datosUsuario.getNickname(), "Eventos.2025", datosUsuario.getNombre(), datosUsuario.getCorreo(), datosAsistente.getApellido(), datosAsistente.getFechaNacimiento(), institucion);
			}
		}
		if (this.usuariosOrganizador != null && this.organizadores != null) {
			for (Map.Entry<String, CSVUsuario> entry : this.usuariosOrganizador.entrySet()) {
				CSVUsuario datosUsuario = entry.getValue();
				CSVOrganizador datosOrganizador = this.organizadores.get(entry.getKey());
				// TODO: Por la forma de los datos dados, se ingresa una contraseña hardcodeada
				this.manejadorUsuarios.agregarOrganizador(datosUsuario.getNickname(), "Eventos.2025!", datosUsuario.getNombre(), datosUsuario.getCorreo(), datosOrganizador.getDescripcion(), datosOrganizador.getSitioWeb());
			}
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
							CSVEvento::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void cargarEventos() {
		if (this.eventos != null) {
			for (String ref : this.eventos.keySet()) {
				List<String> categoriasEvento = new ArrayList<String>();
				CSVEvento evento = this.eventos.get(ref);
				// Separo las categorias
				for (String categoria : evento.getCategorias().split(", ")) {
					categoriasEvento.add(categorias.get(categoria).getNombre()); // Asumo que existe la categoria referenciada
				}
				this.manejadorEventos.altaEvento(evento.getNombre(), evento.getDescripcion(), evento.getFechaAlta(), evento.getSigla(), categoriasEvento,null);
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

	public void cargarEdiciones() {
		if (this.eventos != null && this.ediciones!= null) {
			DateTimeFormatter.ofPattern("d/M/yyyy");
			for (String ref : this.ediciones.keySet()) {
				CSVEdicion edicion = ediciones.get(ref);
				String nombreEvento = eventos.get(edicion.getEvento()).getNombre();
				String organizadorNickname = usuariosOrganizador.get(edicion.getOrganizador()).getNickname();
				// TODO: Ver que se hace con las ímagenes
				Edicion ed = this.manejadorEventos.altaEdicion(nombreEvento, edicion.getNombre(), edicion.getSigla(), edicion.getFechaInicio(), edicion.getFechaFin(), edicion.getFechaAlta(), edicion.getCiudad(), edicion.getPais(), organizadorNickname, null);
				if (edicion.getEstado().equals("Aceptada")) {
					ed.setEstado(EEstadoEdicion.Aceptada);
				} else if (edicion.getEstado().equals("Rechazada")) {
					ed.setEstado(EEstadoEdicion.Rechazada);
				}
				this.manejadorUsuarios.agregarEdicionAOrganizador(ed, organizadorNickname);
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

	public void cargarTipoRegistro() {
		if (this.tiposRegistro!= null) {
			for (String ref : this.tiposRegistro.keySet()) {
				CSVTipoRegistro tipoRegistro = tiposRegistro.get(ref);
				String nombreEdicion = ediciones.get(tipoRegistro.getEdicion()).getNombre();
				String nombreEvento = eventos.get(ediciones.get(tipoRegistro.getEdicion()).getEvento()).getNombre();
				try {
					this.manejadorEventos.altaTipoRegistro(tipoRegistro.getNombre(), tipoRegistro.getDescripcion(), Float.parseFloat(tipoRegistro.getCosto()), Integer.parseInt(tipoRegistro.getCupos()), nombreEvento, nombreEdicion);
				} catch (NumberFormatException | NombreTipoRegistroRepetido e) {
					e.printStackTrace();
				}
			}
		}
	}
	//------------TIPOREGISTRO-----------------

	//------------PATROCINIO-----------------
	public void leerPatrocinios(String ruta) {
		CsvToBean<CSVPatrocinio> patrocinioCsv;
		try {
			patrocinioCsv = new CsvToBeanBuilder<CSVPatrocinio>(
					new FileReader(ruta))
					.withType(CSVPatrocinio.class)
					.withSeparator(';')
					.withIgnoreLeadingWhiteSpace(true)
					.withIgnoreEmptyLine(true)
					.build();
			List<CSVPatrocinio> listaPatrocinios = patrocinioCsv.parse();
			this.patrocinios = listaPatrocinios.stream()
					.collect(Collectors.toMap(
							CSVPatrocinio::getRef,
							d -> d,
							(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
							));
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void cargarPatrocinios() {
		if (this.patrocinios != null && this.eventos != null && this.ediciones != null && this.instituciones != null) {
			for (String ref : this.patrocinios.keySet()) {
				CSVPatrocinio patrocinio = patrocinios.get(ref);
				String nombreEvento = eventos.get(ediciones.get(patrocinio.getEdicion()).getEvento()).getNombre();
				String nombreEdicion = ediciones.get(patrocinio.getEdicion()).getNombre();
				String nombreTipoRegistro = tiposRegistro.get(patrocinio.getTipoRegistro()).getNombre();
				String nombreInstitucion = instituciones.get(patrocinio.getInstitucion()).getNombre();
				this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
				this.manejadorInstituciones.getInstitucion(nombreInstitucion);
				ENivelPatrocinio nivel = ENivelPatrocinio.valueOf(patrocinio.getNivel());
				try {
					this.manejadorEventos.AltaPatrocinio( nombreEvento,patrocinio.getFechaPatrocinio(), nombreEdicion,nombreInstitucion,nivel,patrocinio.getAporte(), patrocinio.getCuposGratuitos(),patrocinio.getCodigoPatrocinio(), nombreTipoRegistro );
				}catch(Exception CPError) {
					
				}
			} 
		}
	}
	//------------PATROCINIO-----------------
	
	//------------REGISTRO-----------------
		public void leerRegistro(String ruta) {
			CsvToBean<CSVRegistro> registrosCsv;
			try {
				registrosCsv = new CsvToBeanBuilder<CSVRegistro>(
						new FileReader(ruta))
						.withType(CSVRegistro.class)
						.withSeparator(';')
						.withIgnoreLeadingWhiteSpace(true)
						.withIgnoreEmptyLine(true)
						.build();
				List<CSVRegistro> listaRegistros = registrosCsv.parse();
				this.registros = listaRegistros.stream()
						.collect(Collectors.toMap(
								CSVRegistro::getRef,
								d -> d,
								(existente, duplicado) -> duplicado // en caso de duplicados, me quedo con el último
								));
			} catch (IllegalStateException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public void cargarRegistros() {
			if (this.asistentes != null && this.ediciones != null && this.tiposRegistro != null) {
				for (String ref : this.registros.keySet()) {
					CSVRegistro registro = registros.get(ref);
					String nicknameAsistente = usuariosAsistente.get(registro.getUsuario()).getNickname();
					Asistente asistente = this.manejadorUsuarios.getAsistente(nicknameAsistente);
					String nombreEvento = eventos.get(ediciones.get(registro.getEdicion()).getEvento()).getNombre();
					String nombreEdicion = ediciones.get(registro.getEdicion()).getNombre();
					String nombreTipoRegistro = tiposRegistro.get(registro.getTipoRegistro()).getNombre();
					Edicion edicion = this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
					TipoRegistro tipoRegistro;
					try {
						tipoRegistro = edicion.getTipoRegistro(nombreTipoRegistro);
					} catch (TipoRegistroNoExiste e) {
						e.printStackTrace();
						return;
					}
					this.manejadorEventos.altaRegistro(asistente, tipoRegistro, null, registro.getFechaRegistro(), registro.getCosto());
				}
			}
		}
		//------------REGISTRO-----------------

	/**
	 * Carga todos los archivos al sistema, basándose en un orden de carga específicado al inicio de la función.
	 * Si falta alguno de los 10 archivos requeridos para la carga, devuelve un error y no continua con la ejecución
	 * 
	 * @param rutas
	 */
	public void cargarArchivos(String[] rutas) {

		// Validar que cada archivo requerido esté presente en las rutas
		for (String requerido : this.nombreArchivosOrdenados) {
			boolean encontrado = Arrays.stream(rutas)
					.anyMatch(ruta -> ruta.endsWith(requerido));
			if (!encontrado) {
				throw new IllegalArgumentException(
						"Falta el archivo requerido: " + requerido
						);
			}
		}

		for (String nombreEsperado : this.nombreArchivosOrdenados) {
			String rutaEncontrada = null;
			for (String ruta : rutas) {
				if (ruta.endsWith(nombreEsperado)) {
					rutaEncontrada = ruta;
					break;
				}
			}	        

			// Llamo a la función adecuada según el archivo
			if (nombreEsperado.equals("2025Categorias.csv")) {
				this.leerCategorias(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Usuarios.csv")) {
				this.leerUsuarios(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Instituciones.csv")) {
				this.leerInstituciones(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Usuarios-Organizadores.csv")) {
				this.leerOrganizadores(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Usuarios-Asistentes.csv")) {
				this.leerAsistentes(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Eventos.csv")) {
				this.leerEventos(rutaEncontrada);
			} else if (nombreEsperado.equals("2025EdicionesEventos.csv")) {
				this.leerEdiciones(rutaEncontrada);
			} else if (nombreEsperado.equals("2025TipoRegistro.csv")) {
				this.leerTipoRegistro(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Patrocinios.csv")) {
				this.leerPatrocinios(rutaEncontrada);
			} else if (nombreEsperado.equals("2025Registros.csv")) {
				this.leerRegistro(rutaEncontrada);
			} else {
				System.err.println("Archivo no reconocido: " + nombreEsperado);
			}
		}
		this.cargarCategorias();
		this.cargarInstituciones();
		this.cargarUsuarios();
		this.cargarEventos();
		this.cargarEdiciones();
		this.cargarTipoRegistro();
		this.cargarPatrocinios();
		this.cargarRegistros();
	}
}
