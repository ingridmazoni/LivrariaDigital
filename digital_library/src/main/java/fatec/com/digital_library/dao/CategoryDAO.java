package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Category;

public interface CategoryDAO {

	public List<Category> fetchAllCategories();
	public boolean createCategory(Category category);
	public boolean removeCategory(Category category);
	public Category fetchCategory(Category category);
	public boolean updateCategory(Category category, String oldName);
}
