package fatec.com.digital_library.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Item;
import fatec.com.digital_library.entity.ShoppingCart;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean(name = "shoppingCartControl")
@SessionScoped
public class ShoppingCartControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7541182344290919353L;
	private Book bookDetails;
	private ShoppingCart shoppingCart = new ShoppingCart();
	private String condition;
	private Item item = new Item();
	private Integer quantity;
	private Double totalItemPrice = 0.0;
	private List<Book> bookList = new ArrayList<Book>();

	public void insertBookToCart(Book book) {
		Item itemToInsert = new Item();
		itemToInsert.setBook(book);
		itemToInsert.setQuantity(book.getQuantity());
		if (isBookDuplicate(book)) {
			condition = DigitalLibraryConstants.WARN;
			addMessage(DigitalLibraryConstants.INSERT_BOOK_CART_WARN, condition);
		} else {
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.INSERT_BOOK_CART_SUCCESS, condition);
			bookList.add(book);
			shoppingCart.setItemList(itemToInsert);
		}
	}

	public boolean isBookDuplicate(Book book) {
		if (bookList.contains(book)) {
			return true;
		} else {
			return false;
		}
	}

	public void addMessage(String summary, String condition) {
		FacesMessage message;

		switch (condition) {
		case DigitalLibraryConstants.INFO:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case DigitalLibraryConstants.ERROR:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;

		case DigitalLibraryConstants.WARN:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		default:
			break;
		}

	}

	public void confirmOrder() {

	}

	public void cancelOrder() {

	}

	public void removeItem(Item item) {
		Double subtractTotalPrice = 0.0;
		List<Item> itemList = new ArrayList<Item>(); 
		for (Item itemToRemove : shoppingCart.getItemList()) {
			if (itemToRemove.equals(item)) {
				subtractTotalPrice += itemToRemove.getQuantity() * itemToRemove.getBook().getSalePrice();
				itemList.add(itemToRemove);
			}
		}
		
		for (Item itemToRemove2 : itemList) {
			shoppingCart.getItemList().remove(itemToRemove2);
			bookList.remove(itemToRemove2.getBook());
		}

		totalItemPrice = totalItemPrice - subtractTotalPrice;
		
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Book getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(Book bookDetails) {
		this.bookDetails = bookDetails;
	}

	public Double getTotalItemPrice() {
		totalItemPrice = shoppingCart.getAllItemsValue();
		return totalItemPrice;
	}

	public void setTotalItemPrice(Double totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	
}
