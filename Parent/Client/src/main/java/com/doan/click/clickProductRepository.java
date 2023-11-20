package com.doan.click;

import com.doan.mutual.entity.ClickProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clickProductRepository extends JpaRepository<ClickProduct,Integer> {
}
