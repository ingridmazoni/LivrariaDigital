package fatec.com.digital_library.control;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Item;
import fatec.com.digital_library.entity.ShoppingCart;

@ManagedBean
@SessionScoped
public class ShoppingCartControl {

	Book bookDetails;
	private ShoppingCart shoppingCart = new ShoppingCart();
	private Item item = new Item();

	public void insertBookToCart(Book book) {
		item.setBook(book);
		shoppingCart.getItemList().add(item);
	}
	
	public void confirmOrder() {
		
	}
	
	public void cancelOrder() {
		
	}
	
	public void removeItem(Item item) {
		
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
	
	

}
