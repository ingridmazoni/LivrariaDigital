package fatec.com.digital_library.control;

import java.io.Serializable;
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
public class Loader implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3256324230982350467L;
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
		loadFormat();
		editorList = editorDAO.fetchEditors();
		categoryList = categoryDAO.fetchAllCategories();
		autorList = autorDAO.fetchAutors();
		bookList = bookDAO.fetchBooks();

	}

	public void loadFormat() {
		formatList = new ArrayList<String>();
		formatList.add(DigitalLibraryConstants.HARDCOVER);
		formatList.add(DigitalLibraryConstants.PAPERBACK);
	}
	
	public void loadAutors() {
		autorList = autorDAO.fetchAutors();
	}

	public void loadEditors() {
		editorList = editorDAO.fetchEditors();
	}

	public void loadCategories() {
		categoryList = categoryDAO.fetchAllCategories();
	}

	public void loadBooks() {
		bookList = bookDAO.fetchBooks(); 
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
