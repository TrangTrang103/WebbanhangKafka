package com.doan.order;


import com.doan.mutual.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

	List<OrderItem> findAllByOrder_id(int id);

	void deleteById(int id);
	
}
