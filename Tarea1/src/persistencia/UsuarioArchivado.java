package persistencia;
import persistencia.TipoUsuario.TipoUsuario;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioArchivado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String nickname;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String nombre;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoUsuario tipoUsuario;
	
	// no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UsuarioArchivado)) return false;
        UsuarioArchivado that = (UsuarioArchivado) object;
        return Objects.equals(id, that.id);
    }

    // no eliminar
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // getters
    public Long getId() {
		return id;
	}
 	public String getNickname() {
 		return nickname;
 	}
 	public String getEmail() {
 		return email;
 	}
 	public String getNombre() {
 		return nombre;
 	}
 	public TipoUsuario getTipoUsuario() {
 		return tipoUsuario;
 	}
 	
 	// setters
 	public void setId(Long id) {
		this.id = id;
	}
 	public void setNickname(String nickname) {
 		this.nickname = nickname;
 	}
 	public void setEmail(String email) {
 		this.email = email;
 	}
 	public void setNombre(String nombre) {
 		this.nombre = nombre;
 	}
 	public void setTipoUsuario(TipoUsuario tipoUsuario) {
 		this.tipoUsuario = tipoUsuario;
 	}
}
