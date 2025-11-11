package casita;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "empresas")
public class ListaEmpresas {


	private List<Empresa> empresas = new ArrayList<>();

	public ListaEmpresas() {
		super();
	}

	public ListaEmpresas(List<Empresa> empresas) {
		super();
		this.empresas = empresas;
	}

	@XmlElement(name = "empresa")
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public void agregarEmpresa(Empresa empresa) {
	    empresas.add(empresa);
	}
	
	
}
