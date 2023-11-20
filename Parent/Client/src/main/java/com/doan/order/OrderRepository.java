package com.doan.order;


import com.doan.mutual.entity.Cart;
import com.doan.mutual.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
	List<Order> findAllByCustomerId(Integer customerId);

	Order findById(int id);
	
	@Query(value="Select * From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5RecentOrder();
	
	@Query(value="Select o.user_id From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<String> findTop5RecentCustomer();
	
	Page<Order> findAll(Pageable pageable);

	void deleteById(int id);
	
	
	@Query(value="select * from `order` o where o.payment_method = ?1",nativeQuery = true)
	List<Order> findAllByPayment_Method(String payment_Method);
	
	@Query(value="Select * From `order` o where o.payment_method = ?1 ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5OrderByPaymentMethod(String payment_method);
	
}
