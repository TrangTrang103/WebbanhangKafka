package com.doan.category;


import com.doan.mutual.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	
	Category getById(Integer id);

}
