package fatec.com.digital_library.control;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@RequestScoped
public class ChooseBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100519542590020697L;
	private BookDAO bookDao = new BookDAOImpl();
	private Book bookDetails = new Book();
	private List<Book> bookList;
	private boolean isHidden = true;
	private String noStockError;
	@ManagedProperty(value = "#{loader}")
	private Loader loader;

	@PostConstruct
	public void onLoad() {
		bookList = loader.getBookList();
	}
	
	public void loadBookDetails(Book book) {
		if (book.getStockQuantity() == 0) {
			isHidden = false;
			noStockError = DigitalLibraryConstants.NO_STOCK_ERROR_MSG;
		} else {
			isHidden = true;
		}
		bookDetails = bookDao.fetchBook(book);
	}

	public Book getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(Book bookDetails) {
		this.bookDetails = bookDetails;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getNoStockError() {
		return noStockError;
	}

	public void setNoStockError(String noStockError) {
		this.noStockError = noStockError;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}
	
	
	
}	

