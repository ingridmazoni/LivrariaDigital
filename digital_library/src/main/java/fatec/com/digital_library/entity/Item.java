package fatec.com.digital_library.entity;

public class Item {

	private Book book;
	private Integer quantity;
	private Double totalValue = 0.0;

	
	public Item() {
		super();
		this.quantity = 1;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalValue() {
		totalValue = quantity * book.getSalePrice();
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

}
