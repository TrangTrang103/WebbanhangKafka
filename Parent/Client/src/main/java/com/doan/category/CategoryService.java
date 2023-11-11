package com.doan.category;


import com.doan.mutual.entity.Category;

import java.util.List;

public interface CategoryService {
	
	Category saveCategory(Category category);

	Category getCategoryById(Integer id);

	Category updateCategory(Category category);
	
	List<Category> findAll();

	void deleteCategoryId(Integer id);
}
