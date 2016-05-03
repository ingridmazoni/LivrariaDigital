package fatec.com.digital_library.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fatec.com.digital_library.dao.EditorDAO;
import fatec.com.digital_library.dao.impl.EditorDAOImpl;
import fatec.com.digital_library.entity.Address;
import fatec.com.digital_library.entity.Editor;
import fatec.com.digital_library.utility.DigitalLibraryConstants;

@ManagedBean
@ViewScoped
public class EditorControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7014069298324827429L;
	private Editor editor = new Editor();
	private Address address = new Address();
	private List<Editor> editorList = new ArrayList<Editor>();
	private String condition;
	private EditorDAO editorDAO = new EditorDAOImpl();
	private String oldCnpj;
	@ManagedProperty(value = "#{loader}")
	private Loader loader;
	private String newCnpj;

	@PostConstruct
	public void onLoad() {
		editorList = loader.getEditorList();
	}

	public void createEditor(Editor editor) {
		if (editorDAO.fetchEditor(editor) == null) {
			this.editor.setAddress(this.address);
			if (editorDAO.addEditor(editor)) {
				condition = DigitalLibraryConstants.INFO;
				loader.loadEditors();
				editorList.add(editor);
				addMessage(DigitalLibraryConstants.ADD_EDITOR_SUCCESS, condition);
			} else {
				condition = DigitalLibraryConstants.ERROR;
				addMessage(DigitalLibraryConstants.ADD_EDITOR_FAILURE, condition);
			}
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.EDITOR_EXISTS_ERROR, condition);
		}
	}

	public void alterEditor(Editor editor, String cnpj) {
		if (editorDAO.updateEditor(editor, cnpj)) {
			condition = DigitalLibraryConstants.INFO;
			loader.loadEditors();
			editorList = loader.getEditorList();
			this.newCnpj = editor.getCnpj();
			oldCnpj = newCnpj;
			addMessage(DigitalLibraryConstants.UPDATE_EDITOR_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.UPDATE_EDITOR_FAILURE, condition);
		}
	}

	public void removeEditor(Editor editor) {
		if (editorDAO.removeEditor(editor)) {
			condition = DigitalLibraryConstants.INFO;
			loader.loadEditors();
			editorList.remove(editor);
			addMessage(DigitalLibraryConstants.REMOVE_EDITOR_SUCCESS, condition);
		} else {
			condition = DigitalLibraryConstants.ERROR;
			addMessage(DigitalLibraryConstants.REMOVE_EDITOR_FAILURE, condition);
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

	public List<String> completeCity(String query) {
		List<String> cityList = new ArrayList<String>();

		for (int i = 0; i < loader.getCityList().size(); i++) {
			String city = loader.getCityList().get(i);
			if (city.startsWith(query)) {
				cityList.add(city);
			} else if (city.toLowerCase().startsWith(query)) {
				cityList.add(city);
			}
		}

		return cityList;
	}

	public List<String> completeState(String query) {
		List<String> stateList = new ArrayList<String>();

		for (int i = 0; i < loader.getStateList().size(); i++) {
			String state = loader.getStateList().get(i);
			if (state.startsWith(query)) {
				stateList.add(state);
			} else if (state.toLowerCase().startsWith(query)) {
				stateList.add(state);
			}
		}
		return stateList;
	}

	public void loadUpdateEditor(Editor editor) {
		this.oldCnpj = editor.getCnpj();
		this.editor = editor;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getOldCnpj() {
		return oldCnpj;
	}

	public void setOldCnpj(String oldCnpj) {
		this.oldCnpj = oldCnpj;
	}

}
