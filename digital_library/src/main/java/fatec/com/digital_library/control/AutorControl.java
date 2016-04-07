package fatec.com.digital_library.control;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import fatec.com.digital_library.entity.Autor;

@ManagedBean
@RequestScoped
public class AutorControl {

	Autor autor = new Autor();
	List<Autor> autorList;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Autor> getAutorList() {
		return autorList;
	}

	public void setAutorList(List<Autor> autorList) {
		this.autorList = autorList;
	}

}
