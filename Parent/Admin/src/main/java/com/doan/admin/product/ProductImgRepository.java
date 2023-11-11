package com.doan.admin.product;

import com.doan.mutual.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Integer productId);

}
