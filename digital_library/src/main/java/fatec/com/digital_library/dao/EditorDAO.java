package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Editor;

public interface EditorDAO {
	
	public boolean addEditor(Editor editor);
	public boolean removeEditor(Editor editor);
	public Editor fetchEditor(Editor editor);
	public List<Editor> fetchEditors();

}
