package com.doan.admin.category;

import com.doan.mutual.entity.Category;
import com.doan.mutual.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getAll(Pageable page){
        return categoryRepository.findAll(page);
    }
    public List<Category> getAllCategory(){
        return (List<Category>) categoryRepository.findAll();
    }
    public Page<Category> searchCategory(String inputSearch, Pageable pageable){
        return categoryRepository.findByCategory_NameAndId(inputSearch,pageable);
    }

    public Category saveCategory(Category category){
        Category cate = new Category();
        cate.setName(category.getName());
        cate.setEnable(true);
        cate.setNote(category.getNote());
        categoryRepository.save(cate);
        return cate;
    }

    public void deleteCategory(Integer id){
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Integer id){
       return categoryRepository.findCategoryById(id);
    }
    public Category saveEditCategory(Category category){
        Category cate = categoryRepository.findCategoryById(category.getId());
        cate.setName(category.getName());
        cate.setEnable(true);
        cate.setNote(category.getNote());
        categoryRepository.save(cate);
        return cate;
    }
}
