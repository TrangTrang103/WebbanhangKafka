package com.doan.product;

import com.doan.mutual.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HOAN HAO
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	@Query(value="select * from product p where p.name like %?1%",nativeQuery = true)
	List<Product> findByProduct_NameContaining(String name);

	List<Product> findTop12ByOrderByQuantityDesc();

	List<Product> findTop12ByOrderByCreatedTimeDesc();
	Page<Product> findAllByCategory_id(int id, Pageable pageable);
	
	Product findById(int id);
	
	@Query(value="select * from doanspringboot.product where doanspringboot.product.name like %?1% and doanspringboot.product.category_id= ?2",nativeQuery = true)
	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);
	
	@Query(value="select * from doanspringboot.product where doanspringboot.product.name like %?1%",nativeQuery = true)
	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);
	
	@Query(value="select * from product p where p.category_id = ?1 ORDER BY p.sold DESC LIMIT 4;",nativeQuery = true)
	List<Product> findTop4ProductByCategory_id(int id);
}
