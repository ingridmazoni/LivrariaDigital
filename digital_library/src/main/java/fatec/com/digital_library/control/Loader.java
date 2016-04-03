package fatec.com.digital_library.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import fatec.com.digital_library.dao.AutorDAO;
import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.CategoryDAO;
import fatec.com.digital_library.dao.EditorDAO;
import fatec.com.digital_library.dao.impl.AutorDAOImpl;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.dao.impl.CategoryDAOImpl;
import fatec.com.digital_library.dao.impl.EditorDAOImpl;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean(name = "loader", eager = true)
@ApplicationScoped
public class Loader {
	private EditorDAO editorDAO = new EditorDAOImpl();
	private CategoryDAO categoryDAO = new CategoryDAOImpl();
	private AutorDAO autorDAO = new AutorDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();

	private List<String> formatList;
	private List<Editor> editorList;
	private List<Category> categoryList;
	private List<Autor> autorList;
	private List<Book> bookList;

	@PostConstruct
	public void onLoad() {
		formatList = loadFormat();
		editorList = loadEditors();
		categoryList = loadCategories();
		autorList = loadAutors();
		bookList = loadBooks();

	}

	public List<String> loadFormat() {
		ArrayList<String> formatList = new ArrayList<String>();
		formatList.add(DigitalLibraryConstants.HARDCOVER);
		formatList.add(DigitalLibraryConstants.PAPERBACK);
		return formatList;
	}

	public List<Autor> loadAutors() {
		return autorDAO.fetchAutors();
	}

	public List<Editor> loadEditors() {
		return editorDAO.fetchEditors();
	}

	public List<Category> loadCategories() {
		return categoryDAO.fetchAllCategories();
	}

	public List<Book> loadBooks() {
		return bookDAO.fetchBooks();
	}

	public List<String> getFormatList() {
		return formatList;
	}

	public void setFormatList(List<String> formatList) {
		this.formatList = formatList;
	}

	public List<Editor> getEditorList() {
		return editorList;
	}

	public void setEditorList(List<Editor> editorList) {
		this.editorList = editorList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Autor> getAutorList() {
		return autorList;
	}

	public void setAutorList(List<Autor> autorList) {
		this.autorList = autorList;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
	

}
