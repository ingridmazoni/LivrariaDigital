package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;

public interface BookAutorDAO {
	
	public String fetchAutorsForBook(String isbn);
	public boolean addAutorsForBook(Book book);
	public boolean updateAutorsForBook(Book book);
	public List<Autor> fetchAutorsForBook(Book book);

}
