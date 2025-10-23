package servidor;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@WebService 
@SOAPBinding (style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {
	private Endpoint endpoint = null;
	public Publicador() {}
	
	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:4230/publicador",this);
	}
	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return this.endpoint;
	}
	@WebMethod
	public String helloWorld() {
		return "Hello World! Client!";
	}
}