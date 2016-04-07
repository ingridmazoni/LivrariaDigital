package fatec.com.digital_library.control;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import fatec.com.digital_library.entity.Editor;

@ManagedBean
@RequestScoped
public class EditorControl {

	private Editor editor;
	private List<Editor> editorList;

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

}
