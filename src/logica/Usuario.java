package logica;

public abstract class Usuario {
	String nickname;
	String nombre;
	String correo;
	
	public String getNickname() {
		try {
			return this.nickname;
		}catch(Exception e) {
			//log.error("Usuario::getNickName() ERROR:", e)
		}
	}
}
