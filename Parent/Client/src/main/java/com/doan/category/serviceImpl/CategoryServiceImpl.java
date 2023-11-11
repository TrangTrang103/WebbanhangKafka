package com.doan.category.serviceImpl;


import com.doan.category.CategoryRepository;
import com.doan.category.CategoryService;
import com.doan.mutual.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Category saveCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		return categoryRepository.getById(id);
	}

	@Override
	public Category updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deleteCategoryId(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

}
