package com.doan.admin.product;

import com.doan.mutual.entity.Product;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from doanspringboot.product order by doanspringboot.product.id desc ", nativeQuery = true)
    Page<Product> findAll(Pageable pageable);
    @Query(value = "select * from doanspringboot.product where doanspringboot.product.name like %?1% or doanspringboot.product.id like %?1%", nativeQuery = true)
    Page<Product> findByProduct_NameAndId(String inputSearch, Pageable pageable);

    List<Product> findAll();

    @Query(value = "select * from doanspringboot.product where doanspringboot.product.id like %?1%", nativeQuery = true)
    Product findByProductId(Integer id);


}
