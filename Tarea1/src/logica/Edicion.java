package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import excepciones.AsistenteYaRegistrado;
import excepciones.NoHayCuposDisponibles;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import datatypes.DTEdicionEvento;
import datatypes.DTTipoRegistro;
import datatypes.EEstadoEdicion;

public class Edicion {
    private String nombre;
    private String sigla;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaAlta;
    private String ciudad;
    private String pais;
    private EEstadoEdicion estado;
    private Map<String, TipoRegistro> tiposRegistro;
    private Map<String, Patrocinio> patrocinios;
    private String organizadorNickname;
    private String nombreEvento;
    private byte[] imagen; 

    public Edicion(
            String nombre,
            String sigla,
            Date fechaInicio,
            Date fechaFin,
            Date fechaAlta,
            String ciudad,
            String pais,
            String organizadorNickname,
            String nombreEvento,
            byte[] imagen) {

        this.setNombre(nombre);
        this.setSigla(sigla);
        this.setFechaInicio(fechaInicio);
        this.setFechaFin(fechaFin);
        this.setFechaAlta(fechaAlta);
        this.setCiudad(ciudad);
        this.setPais(pais);
        this.setEstado(EEstadoEdicion.Ingresada);
        this.setPatrocinios(new HashMap<>());
        this.setTiposRegistro(new HashMap<>());
        this.setOrganizadorNickname(organizadorNickname);
        this.setNombreEvento(nombreEvento);
        this.setImagen(imagen);
    }

    // ==== Getters y Setters ====

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Date getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public EEstadoEdicion getEstado() { return estado; }
    public void setEstado(EEstadoEdicion estado) { this.estado = estado; }

    public Patrocinio getPatrocinio(String codigoPatrocinio) {
        return this.patrocinios.get(codigoPatrocinio);
    }

    public Map<String, Patrocinio> getPatrocinios() { return patrocinios; }
    public void setPatrocinios(Map<String, Patrocinio> patrocinios) { this.patrocinios = patrocinios; }

    public void agregarPatrocinio(Patrocinio patrocinio) {
        this.patrocinios.put(patrocinio.getCodigoPatrocinio(), patrocinio);
    }

    public void eliminarPatrocinio(String nombrePatrocinio) {
        this.patrocinios.remove(nombrePatrocinio);
    }

    public TipoRegistro getTipoRegistro(String nombreTipoRegistro) throws TipoRegistroNoExiste {
        if (!this.tiposRegistro.containsKey(nombreTipoRegistro)) {
            throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
        } else {
            return this.tiposRegistro.get(nombreTipoRegistro);
        }
    }

    public Map<String, TipoRegistro> getTiposRegistro() { return tiposRegistro; }
    public void setTiposRegistro(Map<String, TipoRegistro> tiposRegistro) { this.tiposRegistro = tiposRegistro; }

    public void agregarTipoRegistro(String nombre, String descripcion, float costo, int cuposMax)
            throws NombreTipoRegistroRepetido {
        if (this.tiposRegistro.containsKey(nombre)) {
            throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
        } else {
            TipoRegistro tipoRegistro = new TipoRegistro(nombre, descripcion, costo, cuposMax, cuposMax);
            this.tiposRegistro.put(nombre, tipoRegistro);
        }
    }

    public void agregarTipoRegistro(TipoRegistro tipoRegistro) {
        if (tipoRegistro == null || tipoRegistro.getNombre() == null) return;
        this.tiposRegistro.put(tipoRegistro.getNombre(), tipoRegistro);
    }

    public void eliminarTipoRegistro(String nombreTipoRegistro) {
        this.tiposRegistro.remove(nombreTipoRegistro);
    }

    public DTEdicionEvento listarEdicion() {
        return new DTEdicionEvento(
                this.nombre,
                this.sigla,
                this.fechaInicio,
                this.fechaFin,
                this.fechaAlta,
                this.ciudad,
                this.pais,
                this.organizadorNickname,
                this.imagen,
                this.estado
        );
    }

    public boolean validar(String nicknameAsistente, String nombreTipoRegistro) throws NoHayCuposDisponibles, AsistenteYaRegistrado {
        try {
            TipoRegistro tiporegistro = tiposRegistro.get(nombreTipoRegistro);
            int cupos = tiporegistro.getCuposDisponibles();
            if (cupos > 0) {
                return tiporegistro.validar(nicknameAsistente);
            } else {
                throw new NoHayCuposDisponibles("No hay cupos disponibles");
            }
        } catch (AsistenteYaRegistrado exception) {
            throw exception;
        }
    }

    public boolean check(String codigoPatrocinio, String nicknameAsistente, String nombreTipoRegistro) {
        boolean pertenece = patrocinios.containsKey(codigoPatrocinio);
        if (pertenece) {
            Patrocinio patrocinio = patrocinios.get(codigoPatrocinio);
            boolean checkA = patrocinio.checkAsistente(nicknameAsistente);
            boolean checkTR = nombreTipoRegistro.equals(patrocinio.getTipoRegistro().getNombre());
            return checkA && pertenece && checkTR;
        } else {
            return false;
        }
    }

    public Map<String, DTTipoRegistro> listarTipoRegistro() {
        Map<String, DTTipoRegistro> mList = new HashMap<>();
        for (TipoRegistro r : tiposRegistro.values()) {
            String nombre = r.getNombre();
            String descripcion = r.getDescripcion();
            float costo = r.getCosto();
            int cupoM = r.getCuposMax();
            int cupoD = r.getCuposDisponibles();
            mList.put(nombre, new DTTipoRegistro(nombre, descripcion, costo, cupoM, cupoD));
        }
        return mList;
    }

    public DTTipoRegistro getDTTipoRegistro(String nombreTipoRegistro) throws TipoRegistroNoExiste {
        TipoRegistro tipoRegistro;
        try {
            tipoRegistro = getTipoRegistro(nombreTipoRegistro);
        } catch (TipoRegistroNoExiste ex) {
            throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
        }
        return tipoRegistro.getDTTipoRegistro();
    }

    public String getOrganizadorNickname() { return organizadorNickname; }
    public void setOrganizadorNickname(String organizadorNickname) { this.organizadorNickname = organizadorNickname; }

    public String getNombreEvento() { return nombreEvento; }
    public void setNombreEvento(String nombreEvento) { this.nombreEvento = nombreEvento; }
    
    public Patrocinio getPatrocinioInstitucion(String institucion) {
    	for (Patrocinio patrocinio : this.patrocinios.values()) {
    		if (institucion.equals(patrocinio.getInstitucion().getNombre())) {
    			return patrocinio;
    		}
    	}
    	return null;
    }
}

