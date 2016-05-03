package fatec.com.digital_library.control;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fatec.com.digital_library.dao.CategoryDAO;
import fatec.com.digital_library.dao.impl.CategoryDAOImpl;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@ViewScoped
public class CategoryControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 860595096279530736L;
	private Category category = new Category();
	private List<Category> categoryList;
	private CategoryDAO categoryDAO = new CategoryDAOImpl();
	private String condition;
	private String oldName;

	@ManagedProperty(value = "#{loader}")
	private Loader loader;

	@PostConstruct
	public void onLoad() {
		categoryList = loader.getCategoryList();
	}

	public void createCategory(Category category) {
		if (categoryDAO.createCategory(category)) {
			loader.loadCategories();
			categoryList.add(category);
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.ADD_CATEGORY_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.ADD_CATEGORY_FAILURE, condition);
		}

	}

	public void removeCategory(Category category) {
		if (categoryDAO.removeCategory(category)) {
			loader.loadCategories();
			categoryList.remove(category);
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.REMOVE_CATEGORY_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.REMOVE_CATEGORY_FAILURE, condition);
		}
	}

	public void alterCategory(Category category) {
		if (categoryDAO.updateCategory(category, oldName)) {
			condition = DigitalLibraryConstants.INFO;
			addMessage(DigitalLibraryConstants.UPDATE_CATEGORY_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.UPDATE_CATEGORY_FAILURE, condition);
		}
	}

	public void loadUpdateCategory(Category category, String oldName) {
		this.oldName = oldName;
		this.category = category;
	}

	public Category fetchCategory(Category category) {
		return categoryDAO.fetchCategory(category);
	}

	public List<Category> fetchCategories() {
		return categoryDAO.fetchAllCategories();
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

}
