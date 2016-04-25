package fatec.com.digital_library.control;

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
public class ShoppingCartControl {

	Book bookDetails;
	private ShoppingCart shoppingCart = new ShoppingCart();
	private Item item = new Item();
	private String condition;
	private Double totalItemPrice = 0.0;

	public void insertBookToCart(Book book) {
		item.setBook(book);
		if (shoppingCart.getItemList().contains(item)) {
			condition = DigitalLibraryConstants.WARN;
			addMessage(DigitalLibraryConstants.INSERT_BOOK_CART_WARN, condition);
		} else {
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.INSERT_BOOK_CART_SUCCESS, condition);
			shoppingCart.setItemList(item);
		}
	}
	
	public void teste() {
		System.out.println(this.shoppingCart.getItemList().get(0).getBook().getTitle());
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
		for(Item itemToRemove : shoppingCart.getItemList()) {
			if (itemToRemove.equals(item)) {
				shoppingCart.getItemList().remove(item);
			}
		}
		
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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
	
}
