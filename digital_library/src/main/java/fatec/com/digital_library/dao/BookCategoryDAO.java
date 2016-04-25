package fatec.com.digital_library.dao;


import java.util.List;

import fatec.com.digital_library.entity.Book;
import fatec.com.digital_library.entity.Category;

public interface BookCategoryDAO {
	
	public String fetchCategoriesForBook(String isbn);
	public boolean updateCategoriesForBook(Book book);
	public boolean addCategoriesForBook(Book book);
	public List<Category> fetchCategoriesForBook(Book book);

}
