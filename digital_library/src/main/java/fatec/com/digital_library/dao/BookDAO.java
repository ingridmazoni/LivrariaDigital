package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Book;

public interface BookDAO {

	public boolean addBook(Book book);

	public boolean removeBook(Book book);
	
	public boolean updateBook(Book book);

	public Book fetchBook(Book book);

	public List<Book> fetchBooks();

}
