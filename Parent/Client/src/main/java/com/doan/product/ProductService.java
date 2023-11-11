package com.doan.product;


import com.doan.mutual.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
	List<Product> getAllProduct();
	
	Product saveProduct(Product product);

	Product getProductById(Integer id);

	Product updateProduct(Product product);

	void deleteProductById(Integer id);
	
	List<Product> findByProduct_NameContaining(String name);
	
	List<Product> findTop12ProductBestSellers();
	
	List<Product> findTop12ProductNewArrivals();

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);

	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);

	List<Product> findTop4ProductByCategory_id(int name);
}
