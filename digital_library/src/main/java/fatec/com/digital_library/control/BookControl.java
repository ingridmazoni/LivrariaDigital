package fatec.com.digital_library.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import fatec.com.digital_library.dao.BookDAO;
import fatec.com.digital_library.dao.EditorDAO;
import fatec.com.digital_library.dao.impl.BookDAOImpl;
import fatec.com.digital_library.dao.impl.EditorDAOImpl;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;
import fatec.com.digital_library.utility.ImageResizer;

@ManagedBean
@ViewScoped
public class BookControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1332582590535166770L;
	private UploadedFile uploadedFile;
	private Book bookDetails;
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
	private String condition;
	private List<Book> bookList;


	@ManagedProperty("#{loader}")
	private Loader loader;

	@PostConstruct
	public void init() {
		editorList = loader.getEditorList();
		categoryList = loader.getCategoryList();
		autorList = loader.getAutorList();
		bookList = loader.getBookList();
	}

	public void createBook() {
		if (validate(editor, autor, book)) {
			book.setEditor(editor);
			book.setCategory(selectCategoryList);
			book.setAutorList(selectedAutors);
			book.setFormat(format);
			
			if (bookDAO.addBook(book)) {
				loader.loadBooks();
				condition = DigitalLibraryConstants.INFO;
				addMessage(DigitalLibraryConstants.ADD_BOOK_SUCCESS, condition);
			} else {
				condition = DigitalLibraryConstants.ERROR;
				addMessage(DigitalLibraryConstants.ADD_BOOK_FAILURE, condition);
			}

		}
	}
	

	public void removeBook(Book book) {
		if (!bookDAO.removeBook(book)) {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.REMOVE_BOOK_FAILURE, condition);
		} else {
			loader.loadBooks();
			bookList = loader.getBookList();
		}
	}

	public void refreshBookList() {
		bookList = loader.getBookList();
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

	public boolean validate(Editor editor, Autor autor, Book book) {
		FacesMessage message;

		if (editor == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, DigitalLibraryConstants.INVALID_EDITOR, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}

		if (autor == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, DigitalLibraryConstants.INVALID_AUTOR, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}

		if (book == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, DigitalLibraryConstants.INVALID_BOOK, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}

		if (book.getPageNumber() == null) {
			book.setPageNumber((short) 0);
		}

		if (book.getSummary() == null) {
			book.setSummary(" ");
		}

		if (book.getIndex() == null) {
			book.setIndex(" ");
		}

		if (selectCategoryList == null) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, DigitalLibraryConstants.INVALID_CATEGORY, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return false;
		}

		return true;
	}

	public void upload(FileUploadEvent event) {
		FacesMessage message;
		UploadedFile uploadedFile = event.getFile();
		StringBuilder builder = new StringBuilder();
		ImageResizer imgResizer = new ImageResizer();
		try {
			InputStream input = uploadedFile.getInputstream();
			Path folder = Paths.get(DigitalLibraryConstants.COVER_IMG_PATH);
			String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
			String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
			Path file = Files.createTempFile(folder, filename + "-", "." + extension);
			Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
			builder.append(DigitalLibraryConstants.COVER_IMG_PATH);
			builder.append(file.getFileName().toString());
			imgResizer.resize(builder.toString(), builder.toString());
			book.setCoverDirectory(file.getFileName().toString());
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, DigitalLibraryConstants.UPLOAD_SUCCESS, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, DigitalLibraryConstants.UPLOAD_FAILURE, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}

	public void updateBook(Book book) {
		if (bookDAO.updateBook(book)) {
			loader.loadBooks();
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.UPDATE_BOOK_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.UPDATE_BOOK_FAILURE, condition);
		}
	}

	public void loadUpdateBook(Book book) {
		this.book = book;
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

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Book getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(Book bookDetails) {
		this.bookDetails = bookDetails;
	}

}
