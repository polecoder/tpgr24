package com.eventosuy.filtros;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

//import logica.Fabrica;
//import logica.IControladorEventos;

import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;

public class FiltroVisitas implements Filter {
	
	private PublicadorService publicadorService;
    private Publicador publicador;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {	
		publicadorService = new PublicadorService();
        publicador = publicadorService.getPublicadorPort();
	}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String accion = req.getParameter("accion");
        
        // solo se suman visitas si entra a consulta evento
        if ("consulta".equals(accion)) {
            String nombreEvento = req.getParameter("nombre");
            if (nombreEvento != null && !nombreEvento.isBlank()) {
            	publicador.registrarVisitaEvento(nombreEvento);
               
            }
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }

}
