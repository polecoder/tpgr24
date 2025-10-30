package persistencia;

import jakarta.persistence.*;

public class OrganizadorArchivado {
	// TODO: Falta relación con usuario
	@Column(nullable = false)
	private String descripcion;
	@Column(name="sitio_web")
	private String sitioWeb;
}
