package fatec.com.digital_library.control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.entity.Address;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@RequestScoped
public class BookControl {

	private Book book = new Book();
	private BookDAO bookDAO = new BookDAOImpl();
	private String message;
	private DigitalLibraryConstants constants;

	public void createBook() {
		if (bookDAO.addBook(book)){
			message = constants.ADD_BOOK_SUCCESS;
		} else {
			message = constants.ADD_BOOK_FAILURE;
		}
		System.out.println(message);
	}

	public void removeBook() {

	}

	public void fetchBook() {

	}

	public void fetchBooks() {

	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
