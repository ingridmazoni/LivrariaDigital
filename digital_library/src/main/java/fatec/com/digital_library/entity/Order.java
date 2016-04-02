package fatec.com.digital_library.entity;

import java.sql.Date;
import java.util.List;

public class Order {

	private Client client;
	private List<Book> book;
	private String status;
	private Date originDate;
	private Double totalValue;

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOriginDate() {
		return originDate;
	}

	public void setOriginDate(Date originDate) {
		this.originDate = originDate;
	}
}
