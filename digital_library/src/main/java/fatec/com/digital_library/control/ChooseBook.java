package fatec.com.digital_library.control;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import fatec.com.digital_library.entity.Book;

@ManagedBean
@RequestScoped
public class ChooseBook {

	private Book bookDetails;
	private List<Book> bookList;
	
	@ManagedProperty(value = "#{bookControl}")
	private BookControl bookControl;

	public void loadBooks() {
		bookList = bookControl.getBookList();
	}

	public void loadBookDetails(Book book) {
		bookDetails = book;
	}

	public Book getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(Book bookDetails) {
		this.bookDetails = bookDetails;
	}

	public BookControl getBookControl() {
		return bookControl;
	}

	public void setBookControl(BookControl bookControl) {
		this.bookControl = bookControl;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	
}
