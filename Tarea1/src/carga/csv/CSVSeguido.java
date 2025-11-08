package carga.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVSeguido {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "RefSeguidor")
	private String refSeguidor;
	@CsvBindByName(column = "NickNameSeguidor")
	private String nicknameSeguidor;
	@CsvBindByName(column = "RefSeguido")
	private String refSeguido;
	@CsvBindByName(column = "NickNameSeguido")
	private String nicknameSeguido;
	
	public String getRef() {
		return ref;
	}
	public String getRefSeguidor() {
		return refSeguidor;
	}
	public String getNicknameSeguidor() {
		return nicknameSeguidor;
	}
	public String getRefSeguido() {
		return refSeguido;
	}
	public String getNicknameSeguido() {
		return nicknameSeguido;
	}
}
