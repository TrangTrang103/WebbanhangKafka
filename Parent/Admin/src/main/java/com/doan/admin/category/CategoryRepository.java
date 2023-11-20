package com.doan.admin.category;

import com.doan.mutual.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query(value = "select * from doanspringboot.category where doanspringboot.category.name like %?1% or doanspringboot.category.id like %?1%", nativeQuery = true)
    Page<Category> findByCategory_NameAndId(String inputSearch, Pageable pageable);
//    @Query(value = "select * from doanspringboot.category where doanspringboot.category.id like %?1%", nativeQuery = true)
//    Category findCategoryById(int id);

    Category findCategoryById(Integer id);



}
