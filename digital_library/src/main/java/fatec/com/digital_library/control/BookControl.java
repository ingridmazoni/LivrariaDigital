package fatec.com.digital_library.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.EditorDAO;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.dao.impl.EditorDAOImpl;
import fatec.com.digital_library.entity.Address;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
public class BookControl implements Serializable {

	private Book book = new Book();
	private Category category = new Category();
	private Autor autor = new Autor();
	private EditorDAO editorDAO = new EditorDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	private String message;
	private Editor editor = new Editor();
	private List<Editor> editorList;
	private List<Category> categoryList;
	private List<Category> selectCategoryList;
	private List<Autor> selectedAutors;
	private List<Autor> autorList;
	private String format;
	private DigitalLibraryConstants constants;
	private String condition;
	
	@ManagedProperty("#{loader}")
	private Loader loader;

	@PostConstruct
	public void init() {
		editorList = loader.getEditorList();
		categoryList = loader.getCategoryList();
		autorList = loader.getAutorList();
	}

	public void createBook(ActionEvent actionEvent) {
		book.setEditor(editor);
        book.setCategory(selectCategoryList);
        book.setAutorList(autorList);
        book.setFormat(format);
		if (bookDAO.addBook(book)) {
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.ADD_BOOK_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.ADD_BOOK_FAILURE, condition);
		}

	}

	public void removeBook() {
		if (bookDAO.removeBook(book)) {
			message = DigitalLibraryConstants.REMOVE_BOOK_SUCCESS;
		} else {
			message = DigitalLibraryConstants.REMOVE_BOOK_FAILURE;
		}
		System.out.println(message);
	}

	public List<Editor> completeEditor(String query) {
		List<Editor> allThemes = editorDAO.fetchEditors();
		List<Editor> filteredEditors = new ArrayList<Editor>();

		for (int i = 0; i < allThemes.size(); i++) {
			Editor edt = allThemes.get(i);
			if (edt.getName().startsWith(query)) {
				filteredEditors.add(edt);
			} else if (edt.getName().toLowerCase().startsWith(query)) {
				filteredEditors.add(edt);
			}
		}

		return filteredEditors;
	}
	
    public void addMessage(String summary, String condition) {
    	FacesMessage message;
    	
    	switch (condition) {
		case DigitalLibraryConstants.INFO:
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			break;
			
		case DigitalLibraryConstants.ERROR:
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			break;			

		case DigitalLibraryConstants.WARN:
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			break;
		default:
			break;
		}
        
    }

	public char getEditor(Editor editor) {
		return editor.getName().charAt(0);
	}

	public void fetchBook() {

	}

	public void fetchBooks() {

	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public List<Editor> getEditorList() {
		return editorList;
	}

	public void setEditorList(List<Editor> editorList) {
		this.editorList = editorList;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Category> getSelectCategoryList() {
		return selectCategoryList;
	}

	public void setSelectCategoryList(List<Category> selectCategoryList) {
		this.selectCategoryList = selectCategoryList;
	}

	public List<Autor> getSelectedAutors() {
		return selectedAutors;
	}

	public void setSelectedAutors(List<Autor> selectedAutors) {
		this.selectedAutors = selectedAutors;
	}

	public List<Autor> getAutorList() {
		return autorList;
	}

	public void setAutorList(List<Autor> autorList) {
		this.autorList = autorList;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}
