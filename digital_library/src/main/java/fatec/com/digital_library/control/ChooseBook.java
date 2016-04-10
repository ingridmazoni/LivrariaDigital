package fatec.com.digital_library.control;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@RequestScoped
public class ChooseBook {

	private BookDAO bookDao = new BookDAOImpl();
	private Book bookDetails;
	private boolean isHidden = true;
	private String noStockError;

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

}	

