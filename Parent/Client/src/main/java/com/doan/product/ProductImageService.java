package com.doan.product;


import com.doan.mutual.entity.ProductImage;
import org.springframework.stereotype.Service;

@Service
public interface ProductImageService {

	void save(ProductImage productImage);

	void deleteById(int id);

}
