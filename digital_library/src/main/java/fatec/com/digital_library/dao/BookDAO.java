package fatec.com.digital_library.dao;

import fatec.com.digital_library.entity.Book;

public interface BookDAO {

	public boolean addBook(Book book);

	public boolean removeBook(Book book);

	public boolean fetchBook(Book book);

	public boolean fetchBooks();

}
