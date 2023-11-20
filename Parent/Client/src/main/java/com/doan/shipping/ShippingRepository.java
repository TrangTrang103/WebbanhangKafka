package com.doan.shipping;

import com.doan.mutual.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping,Integer> {
}
