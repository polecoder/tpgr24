
package com.eventosuy.servidor;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.eventosuy.servidor package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AporteInsuficiente_QNAME = new QName("http://servidor/", "AporteInsuficiente");
    private final static QName _ContrasenaIncorrecta_QNAME = new QName("http://servidor/", "ContrasenaIncorrecta");
    private final static QName _DatosTipoRegistroInvalidos_QNAME = new QName("http://servidor/", "DatosTipoRegistroInvalidos");
    private final static QName _EdicionFinalizada_QNAME = new QName("http://servidor/", "EdicionFinalizada");
    private final static QName _EdicionNoExiste_QNAME = new QName("http://servidor/", "EdicionNoExiste");
    private final static QName _EdicionYaArchivada_QNAME = new QName("http://servidor/", "EdicionYaArchivada");
    private final static QName _EventoFinalizado_QNAME = new QName("http://servidor/", "EventoFinalizado");
    private final static QName _EventoNoExiste_QNAME = new QName("http://servidor/", "EventoNoExiste");
    private final static QName _Exception_QNAME = new QName("http://servidor/", "Exception");
    private final static QName _FechaFinEsPasada_QNAME = new QName("http://servidor/", "FechaFinEsPasada");
    private final static QName _FormatoContrasenaInvalido_QNAME = new QName("http://servidor/", "FormatoContrasenaInvalido");
    private final static QName _FormularioIncompletoAsistente_QNAME = new QName("http://servidor/", "FormularioIncompletoAsistente");
    private final static QName _FormularioIncompletoEdicion_QNAME = new QName("http://servidor/", "FormularioIncompletoEdicion");
    private final static QName _FormularioIncompletoInstitucion_QNAME = new QName("http://servidor/", "FormularioIncompletoInstitucion");
    private final static QName _FormularioIncompletoOrganizador_QNAME = new QName("http://servidor/", "FormularioIncompletoOrganizador");
    private final static QName _FormularioIncompletoPatrocinio_QNAME = new QName("http://servidor/", "FormularioIncompletoPatrocinio");
    private final static QName _NombreTipoRegistroRepetido_QNAME = new QName("http://servidor/", "NombreTipoRegistroRepetido");
    private final static QName _PatrocinioInvalido_QNAME = new QName("http://servidor/", "PatrocinioInvalido");
    private final static QName _TipoRegistroNoExiste_QNAME = new QName("http://servidor/", "TipoRegistroNoExiste");
    private final static QName _UsuarioNoExiste_QNAME = new QName("http://servidor/", "UsuarioNoExiste");
    private final static QName _YaAsisteAEdicion_QNAME = new QName("http://servidor/", "YaAsisteAEdicion");
    private final static QName _DtDetalleAsistente_QNAME = new QName("http://servidor/", "dtDetalleAsistente");
    private final static QName _DtDetalleOrganizador_QNAME = new QName("http://servidor/", "dtDetalleOrganizador");
    private final static QName _DtDetallePatrocinio_QNAME = new QName("http://servidor/", "dtDetallePatrocinio");
    private final static QName _DtDetalleRegistro_QNAME = new QName("http://servidor/", "dtDetalleRegistro");
    private final static QName _DtDetalleUsuario_QNAME = new QName("http://servidor/", "dtDetalleUsuario");
    private final static QName _DtEdicionEvento_QNAME = new QName("http://servidor/", "dtEdicionEvento");
    private final static QName _DtEvento_QNAME = new QName("http://servidor/", "dtEvento");
    private final static QName _DtEventoEdicion_QNAME = new QName("http://servidor/", "dtEventoEdicion");
    private final static QName _DtEventoEntry_QNAME = new QName("http://servidor/", "dtEventoEntry");
    private final static QName _DtRegistro_QNAME = new QName("http://servidor/", "dtRegistro");
    private final static QName _DtTipoRegistro_QNAME = new QName("http://servidor/", "dtTipoRegistro");
    private final static QName _DtUsuario_QNAME = new QName("http://servidor/", "dtUsuario");
    private final static QName _Patrocinio_QNAME = new QName("http://servidor/", "patrocinio");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.eventosuy.servidor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AporteInsuficiente }
     * 
     */
    public AporteInsuficiente createAporteInsuficiente() {
        return new AporteInsuficiente();
    }

    /**
     * Create an instance of {@link ContrasenaIncorrecta }
     * 
     */
    public ContrasenaIncorrecta createContrasenaIncorrecta() {
        return new ContrasenaIncorrecta();
    }

    /**
     * Create an instance of {@link DatosTipoRegistroInvalidos }
     * 
     */
    public DatosTipoRegistroInvalidos createDatosTipoRegistroInvalidos() {
        return new DatosTipoRegistroInvalidos();
    }

    /**
     * Create an instance of {@link EdicionFinalizada }
     * 
     */
    public EdicionFinalizada createEdicionFinalizada() {
        return new EdicionFinalizada();
    }

    /**
     * Create an instance of {@link EdicionNoExiste }
     * 
     */
    public EdicionNoExiste createEdicionNoExiste() {
        return new EdicionNoExiste();
    }

    /**
     * Create an instance of {@link EdicionYaArchivada }
     * 
     */
    public EdicionYaArchivada createEdicionYaArchivada() {
        return new EdicionYaArchivada();
    }

    /**
     * Create an instance of {@link EventoFinalizado }
     * 
     */
    public EventoFinalizado createEventoFinalizado() {
        return new EventoFinalizado();
    }

    /**
     * Create an instance of {@link EventoNoExiste }
     * 
     */
    public EventoNoExiste createEventoNoExiste() {
        return new EventoNoExiste();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link FechaFinEsPasada }
     * 
     */
    public FechaFinEsPasada createFechaFinEsPasada() {
        return new FechaFinEsPasada();
    }

    /**
     * Create an instance of {@link FormatoContrasenaInvalido }
     * 
     */
    public FormatoContrasenaInvalido createFormatoContrasenaInvalido() {
        return new FormatoContrasenaInvalido();
    }

    /**
     * Create an instance of {@link FormularioIncompletoAsistente }
     * 
     */
    public FormularioIncompletoAsistente createFormularioIncompletoAsistente() {
        return new FormularioIncompletoAsistente();
    }

    /**
     * Create an instance of {@link FormularioIncompletoEdicion }
     * 
     */
    public FormularioIncompletoEdicion createFormularioIncompletoEdicion() {
        return new FormularioIncompletoEdicion();
    }

    /**
     * Create an instance of {@link FormularioIncompletoInstitucion }
     * 
     */
    public FormularioIncompletoInstitucion createFormularioIncompletoInstitucion() {
        return new FormularioIncompletoInstitucion();
    }

    /**
     * Create an instance of {@link FormularioIncompletoOrganizador }
     * 
     */
    public FormularioIncompletoOrganizador createFormularioIncompletoOrganizador() {
        return new FormularioIncompletoOrganizador();
    }

    /**
     * Create an instance of {@link FormularioIncompletoPatrocinio }
     * 
     */
    public FormularioIncompletoPatrocinio createFormularioIncompletoPatrocinio() {
        return new FormularioIncompletoPatrocinio();
    }

    /**
     * Create an instance of {@link NombreTipoRegistroRepetido }
     * 
     */
    public NombreTipoRegistroRepetido createNombreTipoRegistroRepetido() {
        return new NombreTipoRegistroRepetido();
    }

    /**
     * Create an instance of {@link PatrocinioInvalido }
     * 
     */
    public PatrocinioInvalido createPatrocinioInvalido() {
        return new PatrocinioInvalido();
    }

    /**
     * Create an instance of {@link TipoRegistroNoExiste }
     * 
     */
    public TipoRegistroNoExiste createTipoRegistroNoExiste() {
        return new TipoRegistroNoExiste();
    }

    /**
     * Create an instance of {@link UsuarioNoExiste }
     * 
     */
    public UsuarioNoExiste createUsuarioNoExiste() {
        return new UsuarioNoExiste();
    }

    /**
     * Create an instance of {@link YaAsisteAEdicion }
     * 
     */
    public YaAsisteAEdicion createYaAsisteAEdicion() {
        return new YaAsisteAEdicion();
    }

    /**
     * Create an instance of {@link DTDetalleAsistente }
     * 
     */
    public DTDetalleAsistente createDTDetalleAsistente() {
        return new DTDetalleAsistente();
    }

    /**
     * Create an instance of {@link DTDetalleOrganizador }
     * 
     */
    public DTDetalleOrganizador createDTDetalleOrganizador() {
        return new DTDetalleOrganizador();
    }

    /**
     * Create an instance of {@link DTDetallePatrocinio }
     * 
     */
    public DTDetallePatrocinio createDTDetallePatrocinio() {
        return new DTDetallePatrocinio();
    }

    /**
     * Create an instance of {@link DTDetalleRegistro }
     * 
     */
    public DTDetalleRegistro createDTDetalleRegistro() {
        return new DTDetalleRegistro();
    }

    /**
     * Create an instance of {@link DTDetalleUsuario }
     * 
     */
    public DTDetalleUsuario createDTDetalleUsuario() {
        return new DTDetalleUsuario();
    }

    /**
     * Create an instance of {@link DTEdicionEvento }
     * 
     */
    public DTEdicionEvento createDTEdicionEvento() {
        return new DTEdicionEvento();
    }

    /**
     * Create an instance of {@link DTEvento }
     * 
     */
    public DTEvento createDTEvento() {
        return new DTEvento();
    }

    /**
     * Create an instance of {@link DTEventoEdicion }
     * 
     */
    public DTEventoEdicion createDTEventoEdicion() {
        return new DTEventoEdicion();
    }

    /**
     * Create an instance of {@link DTEventoEntry }
     * 
     */
    public DTEventoEntry createDTEventoEntry() {
        return new DTEventoEntry();
    }

    /**
     * Create an instance of {@link DTRegistro }
     * 
     */
    public DTRegistro createDTRegistro() {
        return new DTRegistro();
    }

    /**
     * Create an instance of {@link DTTipoRegistro }
     * 
     */
    public DTTipoRegistro createDTTipoRegistro() {
        return new DTTipoRegistro();
    }

    /**
     * Create an instance of {@link DTUsuario }
     * 
     */
    public DTUsuario createDTUsuario() {
        return new DTUsuario();
    }

    /**
     * Create an instance of {@link Patrocinio }
     * 
     */
    public Patrocinio createPatrocinio() {
        return new Patrocinio();
    }

    /**
     * Create an instance of {@link HashMap }
     * 
     */
    public HashMap createHashMap() {
        return new HashMap();
    }

    /**
     * Create an instance of {@link Institucion }
     * 
     */
    public Institucion createInstitucion() {
        return new Institucion();
    }

    /**
     * Create an instance of {@link HashSet }
     * 
     */
    public HashSet createHashSet() {
        return new HashSet();
    }

    /**
     * Create an instance of {@link TipoRegistro }
     * 
     */
    public TipoRegistro createTipoRegistro() {
        return new TipoRegistro();
    }

    /**
     * Create an instance of {@link DTEdicionEventoArray }
     * 
     */
    public DTEdicionEventoArray createDTEdicionEventoArray() {
        return new DTEdicionEventoArray();
    }

    /**
     * Create an instance of {@link DTEventoEdicionArray }
     * 
     */
    public DTEventoEdicionArray createDTEventoEdicionArray() {
        return new DTEventoEdicionArray();
    }

    /**
     * Create an instance of {@link DTUsuarioArray }
     * 
     */
    public DTUsuarioArray createDTUsuarioArray() {
        return new DTUsuarioArray();
    }

    /**
     * Create an instance of {@link DTRegistroArray }
     * 
     */
    public DTRegistroArray createDTRegistroArray() {
        return new DTRegistroArray();
    }

    /**
     * Create an instance of {@link DTEventoArray }
     * 
     */
    public DTEventoArray createDTEventoArray() {
        return new DTEventoArray();
    }

    /**
     * Create an instance of {@link DTDetalleRegistroArray }
     * 
     */
    public DTDetalleRegistroArray createDTDetalleRegistroArray() {
        return new DTDetalleRegistroArray();
    }

    /**
     * Create an instance of {@link DTDetalleUsuarioArray }
     * 
     */
    public DTDetalleUsuarioArray createDTDetalleUsuarioArray() {
        return new DTDetalleUsuarioArray();
    }

    /**
     * Create an instance of {@link DTEventoEntryArray }
     * 
     */
    public DTEventoEntryArray createDTEventoEntryArray() {
        return new DTEventoEntryArray();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AporteInsuficiente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AporteInsuficiente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "AporteInsuficiente")
    public JAXBElement<AporteInsuficiente> createAporteInsuficiente(AporteInsuficiente value) {
        return new JAXBElement<AporteInsuficiente>(_AporteInsuficiente_QNAME, AporteInsuficiente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContrasenaIncorrecta }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ContrasenaIncorrecta }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "ContrasenaIncorrecta")
    public JAXBElement<ContrasenaIncorrecta> createContrasenaIncorrecta(ContrasenaIncorrecta value) {
        return new JAXBElement<ContrasenaIncorrecta>(_ContrasenaIncorrecta_QNAME, ContrasenaIncorrecta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatosTipoRegistroInvalidos }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DatosTipoRegistroInvalidos }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "DatosTipoRegistroInvalidos")
    public JAXBElement<DatosTipoRegistroInvalidos> createDatosTipoRegistroInvalidos(DatosTipoRegistroInvalidos value) {
        return new JAXBElement<DatosTipoRegistroInvalidos>(_DatosTipoRegistroInvalidos_QNAME, DatosTipoRegistroInvalidos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdicionFinalizada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EdicionFinalizada }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "EdicionFinalizada")
    public JAXBElement<EdicionFinalizada> createEdicionFinalizada(EdicionFinalizada value) {
        return new JAXBElement<EdicionFinalizada>(_EdicionFinalizada_QNAME, EdicionFinalizada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdicionNoExiste }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EdicionNoExiste }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "EdicionNoExiste")
    public JAXBElement<EdicionNoExiste> createEdicionNoExiste(EdicionNoExiste value) {
        return new JAXBElement<EdicionNoExiste>(_EdicionNoExiste_QNAME, EdicionNoExiste.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EdicionYaArchivada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EdicionYaArchivada }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "EdicionYaArchivada")
    public JAXBElement<EdicionYaArchivada> createEdicionYaArchivada(EdicionYaArchivada value) {
        return new JAXBElement<EdicionYaArchivada>(_EdicionYaArchivada_QNAME, EdicionYaArchivada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventoFinalizado }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventoFinalizado }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "EventoFinalizado")
    public JAXBElement<EventoFinalizado> createEventoFinalizado(EventoFinalizado value) {
        return new JAXBElement<EventoFinalizado>(_EventoFinalizado_QNAME, EventoFinalizado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventoNoExiste }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventoNoExiste }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "EventoNoExiste")
    public JAXBElement<EventoNoExiste> createEventoNoExiste(EventoNoExiste value) {
        return new JAXBElement<EventoNoExiste>(_EventoNoExiste_QNAME, EventoNoExiste.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FechaFinEsPasada }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FechaFinEsPasada }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FechaFinEsPasada")
    public JAXBElement<FechaFinEsPasada> createFechaFinEsPasada(FechaFinEsPasada value) {
        return new JAXBElement<FechaFinEsPasada>(_FechaFinEsPasada_QNAME, FechaFinEsPasada.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormatoContrasenaInvalido }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormatoContrasenaInvalido }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormatoContrasenaInvalido")
    public JAXBElement<FormatoContrasenaInvalido> createFormatoContrasenaInvalido(FormatoContrasenaInvalido value) {
        return new JAXBElement<FormatoContrasenaInvalido>(_FormatoContrasenaInvalido_QNAME, FormatoContrasenaInvalido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoAsistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoAsistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormularioIncompletoAsistente")
    public JAXBElement<FormularioIncompletoAsistente> createFormularioIncompletoAsistente(FormularioIncompletoAsistente value) {
        return new JAXBElement<FormularioIncompletoAsistente>(_FormularioIncompletoAsistente_QNAME, FormularioIncompletoAsistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoEdicion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoEdicion }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormularioIncompletoEdicion")
    public JAXBElement<FormularioIncompletoEdicion> createFormularioIncompletoEdicion(FormularioIncompletoEdicion value) {
        return new JAXBElement<FormularioIncompletoEdicion>(_FormularioIncompletoEdicion_QNAME, FormularioIncompletoEdicion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoInstitucion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoInstitucion }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormularioIncompletoInstitucion")
    public JAXBElement<FormularioIncompletoInstitucion> createFormularioIncompletoInstitucion(FormularioIncompletoInstitucion value) {
        return new JAXBElement<FormularioIncompletoInstitucion>(_FormularioIncompletoInstitucion_QNAME, FormularioIncompletoInstitucion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoOrganizador }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoOrganizador }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormularioIncompletoOrganizador")
    public JAXBElement<FormularioIncompletoOrganizador> createFormularioIncompletoOrganizador(FormularioIncompletoOrganizador value) {
        return new JAXBElement<FormularioIncompletoOrganizador>(_FormularioIncompletoOrganizador_QNAME, FormularioIncompletoOrganizador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoPatrocinio }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FormularioIncompletoPatrocinio }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "FormularioIncompletoPatrocinio")
    public JAXBElement<FormularioIncompletoPatrocinio> createFormularioIncompletoPatrocinio(FormularioIncompletoPatrocinio value) {
        return new JAXBElement<FormularioIncompletoPatrocinio>(_FormularioIncompletoPatrocinio_QNAME, FormularioIncompletoPatrocinio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NombreTipoRegistroRepetido }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NombreTipoRegistroRepetido }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "NombreTipoRegistroRepetido")
    public JAXBElement<NombreTipoRegistroRepetido> createNombreTipoRegistroRepetido(NombreTipoRegistroRepetido value) {
        return new JAXBElement<NombreTipoRegistroRepetido>(_NombreTipoRegistroRepetido_QNAME, NombreTipoRegistroRepetido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatrocinioInvalido }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PatrocinioInvalido }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "PatrocinioInvalido")
    public JAXBElement<PatrocinioInvalido> createPatrocinioInvalido(PatrocinioInvalido value) {
        return new JAXBElement<PatrocinioInvalido>(_PatrocinioInvalido_QNAME, PatrocinioInvalido.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoRegistroNoExiste }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoRegistroNoExiste }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "TipoRegistroNoExiste")
    public JAXBElement<TipoRegistroNoExiste> createTipoRegistroNoExiste(TipoRegistroNoExiste value) {
        return new JAXBElement<TipoRegistroNoExiste>(_TipoRegistroNoExiste_QNAME, TipoRegistroNoExiste.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioNoExiste }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioNoExiste }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "UsuarioNoExiste")
    public JAXBElement<UsuarioNoExiste> createUsuarioNoExiste(UsuarioNoExiste value) {
        return new JAXBElement<UsuarioNoExiste>(_UsuarioNoExiste_QNAME, UsuarioNoExiste.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link YaAsisteAEdicion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link YaAsisteAEdicion }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "YaAsisteAEdicion")
    public JAXBElement<YaAsisteAEdicion> createYaAsisteAEdicion(YaAsisteAEdicion value) {
        return new JAXBElement<YaAsisteAEdicion>(_YaAsisteAEdicion_QNAME, YaAsisteAEdicion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTDetalleAsistente }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTDetalleAsistente }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtDetalleAsistente")
    public JAXBElement<DTDetalleAsistente> createDtDetalleAsistente(DTDetalleAsistente value) {
        return new JAXBElement<DTDetalleAsistente>(_DtDetalleAsistente_QNAME, DTDetalleAsistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTDetalleOrganizador }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTDetalleOrganizador }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtDetalleOrganizador")
    public JAXBElement<DTDetalleOrganizador> createDtDetalleOrganizador(DTDetalleOrganizador value) {
        return new JAXBElement<DTDetalleOrganizador>(_DtDetalleOrganizador_QNAME, DTDetalleOrganizador.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTDetallePatrocinio }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTDetallePatrocinio }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtDetallePatrocinio")
    public JAXBElement<DTDetallePatrocinio> createDtDetallePatrocinio(DTDetallePatrocinio value) {
        return new JAXBElement<DTDetallePatrocinio>(_DtDetallePatrocinio_QNAME, DTDetallePatrocinio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTDetalleRegistro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTDetalleRegistro }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtDetalleRegistro")
    public JAXBElement<DTDetalleRegistro> createDtDetalleRegistro(DTDetalleRegistro value) {
        return new JAXBElement<DTDetalleRegistro>(_DtDetalleRegistro_QNAME, DTDetalleRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTDetalleUsuario }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTDetalleUsuario }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtDetalleUsuario")
    public JAXBElement<DTDetalleUsuario> createDtDetalleUsuario(DTDetalleUsuario value) {
        return new JAXBElement<DTDetalleUsuario>(_DtDetalleUsuario_QNAME, DTDetalleUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTEdicionEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTEdicionEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtEdicionEvento")
    public JAXBElement<DTEdicionEvento> createDtEdicionEvento(DTEdicionEvento value) {
        return new JAXBElement<DTEdicionEvento>(_DtEdicionEvento_QNAME, DTEdicionEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTEvento }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTEvento }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtEvento")
    public JAXBElement<DTEvento> createDtEvento(DTEvento value) {
        return new JAXBElement<DTEvento>(_DtEvento_QNAME, DTEvento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTEventoEdicion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTEventoEdicion }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtEventoEdicion")
    public JAXBElement<DTEventoEdicion> createDtEventoEdicion(DTEventoEdicion value) {
        return new JAXBElement<DTEventoEdicion>(_DtEventoEdicion_QNAME, DTEventoEdicion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTEventoEntry }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTEventoEntry }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtEventoEntry")
    public JAXBElement<DTEventoEntry> createDtEventoEntry(DTEventoEntry value) {
        return new JAXBElement<DTEventoEntry>(_DtEventoEntry_QNAME, DTEventoEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTRegistro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTRegistro }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtRegistro")
    public JAXBElement<DTRegistro> createDtRegistro(DTRegistro value) {
        return new JAXBElement<DTRegistro>(_DtRegistro_QNAME, DTRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTTipoRegistro }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTTipoRegistro }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtTipoRegistro")
    public JAXBElement<DTTipoRegistro> createDtTipoRegistro(DTTipoRegistro value) {
        return new JAXBElement<DTTipoRegistro>(_DtTipoRegistro_QNAME, DTTipoRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTUsuario }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DTUsuario }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "dtUsuario")
    public JAXBElement<DTUsuario> createDtUsuario(DTUsuario value) {
        return new JAXBElement<DTUsuario>(_DtUsuario_QNAME, DTUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Patrocinio }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Patrocinio }{@code >}
     */
    @XmlElementDecl(namespace = "http://servidor/", name = "patrocinio")
    public JAXBElement<Patrocinio> createPatrocinio(Patrocinio value) {
        return new JAXBElement<Patrocinio>(_Patrocinio_QNAME, Patrocinio.class, null, value);
    }

}
