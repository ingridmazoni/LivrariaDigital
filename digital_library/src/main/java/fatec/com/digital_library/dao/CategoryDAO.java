package fatec.com.digital_library.dao;

import java.util.List;

import fatec.com.digital_library.entity.Category;

public interface CategoryDAO {

	public List<Category> fetchAllCategories();
}
