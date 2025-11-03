package com.eventosuy.filtros;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import logica.Fabrica;
import logica.IControladorEventos;

@WebFilter(urlPatterns = {"/Eventos"})
public class FiltroVisitas implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String accion = req.getParameter("accion");
        
        // solo se suman visitas si entra a consulta evento
        if ("consulta".equals(accion)) {
            String nombreEvento = req.getParameter("nombre");
            if (nombreEvento != null && !nombreEvento.isBlank()) {
                IControladorEventos controladorEventos = Fabrica.getInstancia().getControladorEvento();
                int total = controladorEventos.registrarVisitaEvento(nombreEvento);  // visitas++
                
                System.out.println("evento='" + nombreEvento + "' total=" + total);
            }
        }
        
        chain.doFilter(request, response);
    }
}
