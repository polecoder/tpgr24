import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import datatypes.DTEdicionEvento;
import datatypes.ETipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;
import logica.Utilidades;

@WebServlet("/test")
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String CU = request.getParameter("caso_de_uso");
		ETipoUsuario tipoUsuario = (ETipoUsuario) request.getSession().getAttribute("tipo_de_usuario");
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
		IControladorEventos controladorEventos = fabrica.getControladorEvento();
		/*
		switch (CU) {
		//case "ConsultaEdicion":
		default:
			DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion(request.getParameter("nombre_evento"), request.getParameter("nombre_edicion"));
			request.setAttribute("ciudad", dtEdicion.getCiudad());
			request.getRequestDispatcher("/WEB-INF/ediciones/consulta-edicion.jsp").forward(request, response);
		case "AltaEdicion":
			request.getRequestDispatcher("/WEB-INF/ediciones/alta-edicion.jsp").forward(request, response);
		case "AltaTipoRegistro":
			request.getRequestDispatcher("/WEB-INF/ediciones/alta-tipo-registro.jsp").forward(request, response);
		case "ConsultaTipoRegistro":
			request.getRequestDispatcher("/WEB-INF/ediciones/consulta-tipo-registro.jsp").forward(request, response);
		}
		*/
		
		DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion("ExpoTech1", "ExpoTech2025");
		request.setAttribute("ciudad", dtEdicion.getCiudad());
		request.getRequestDispatcher("/WEB-INF/consulta-edicion.jsp").forward(request, response);
	}
	
	private void test() {
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
		IControladorEventos controladorEventos = fabrica.getControladorEvento();
		controladorEventos.altaEvento("ExpoTech1", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)), "EXP", Collections.emptyList());
        String nombreEdicion = "ExpoTech2025";
        String sigla = "ET25";
        String ciudad = "Montevideo";
        String pais = "Uruguay";
        Date fechaAlta = Utilidades.toDate(LocalDate.of(2025, 8, 29));
        Date fechaInicio = Utilidades.toDate(LocalDate.of(2025, 9, 10));
        Date fechaFin = Utilidades.toDate(LocalDate.of(2025, 9, 12));
        String organizador = "Organizador3";
        
        try {
        	controladorEventos.altaEdicionEvento("ExpoTech1", nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizador);
        } catch (Exception ex) {
        }
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		test();
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		test();
		processRequest(request, response);
	}
}
