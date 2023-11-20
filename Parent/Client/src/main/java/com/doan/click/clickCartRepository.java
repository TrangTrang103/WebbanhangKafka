package com.doan.click;

import com.doan.mutual.entity.ClickCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface clickCartRepository extends JpaRepository<ClickCart,Integer> {
}
